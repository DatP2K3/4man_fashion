package com.evotek.iam.application.service.impl.command;

import java.text.ParseException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.evo.common.dto.event.SendNotificationEvent;
import com.evotek.iam.application.config.TokenProvider;
import com.evotek.iam.application.dto.request.LoginRequest;
import com.evotek.iam.application.dto.response.TokenDTO;
import com.evotek.iam.application.mapper.CommandMapper;
import com.evotek.iam.application.service.AuthAbstractServiceCommand;
import com.evotek.iam.domain.User;
import com.evotek.iam.domain.UserActivityLog;
import com.evotek.iam.domain.command.WriteLogCmd;
import com.evotek.iam.domain.repository.UserDomainRepository;
import com.evotek.iam.infrastructure.adapter.keycloak.KeycloakService;
import com.evotek.iam.infrastructure.support.exception.AuthErrorCode;
import com.evotek.iam.infrastructure.support.exception.AuthException;
import com.evotek.iam.infrastructure.support.exception.ErrorNormalizer;
import com.nimbusds.jwt.SignedJWT;

import feign.FeignException;

@Component("keycloakAuthCommandService")
public class KeycloakAuthCommandServiceImpl extends AuthAbstractServiceCommand {

    public KeycloakAuthCommandServiceImpl(
            TokenProvider tokenProvider,
            KeycloakService keycloakService,
            PasswordEncoder passwordEncoder,
            RedisTemplate<String, Object> redisTemplate,
            ErrorNormalizer errorNormalizer,
            UserDomainRepository userDomainRepository,
            CommandMapper commandMapper,
            KafkaTemplate<String, SendNotificationEvent> kafkaTemplate) {
        super(
                tokenProvider,
                keycloakService,
                passwordEncoder,
                redisTemplate,
                errorNormalizer,
                userDomainRepository,
                commandMapper,
                kafkaTemplate);
    }

    @Override
    public TokenDTO performLogin(LoginRequest loginRequest) {
        return keycloakService.authenticate(loginRequest);
    }

    @Override
    public void logoutIam(HttpServletRequest request, String refreshToken) {
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            SignedJWT signedJWT = SignedJWT.parse(token);
            long accessExpiryTimeMillis =
                    signedJWT.getJWTClaimsSet().getExpirationTime().getTime();
            long currentTimeMillis = System.currentTimeMillis();
            long refreshExpiryTimeMillis = currentTimeMillis + refreshableDuration * 1000;
            addAccessTokenToBlacklist(token, accessExpiryTimeMillis);
            addRefreshTokenToBlacklist(token, refreshExpiryTimeMillis);
            keycloakService.logout(token, refreshToken);

            String username = signedJWT.getJWTClaimsSet().getStringClaim("preferred_username");
            User user = userDomainRepository.getByUsername(username);

            WriteLogCmd cmd = commandMapper.from("Logout");
            UserActivityLog userActivityLog = new UserActivityLog(cmd);
            user.setUserActivityLog(userActivityLog);
            userDomainRepository.save(user);
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeyCloakException(exception);
        } catch (ParseException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    @Override
    public TokenDTO refresh(String refreshToken) {
        return keycloakService.refreshToken(refreshToken);
    }
}
