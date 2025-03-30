package com.evotek.iam.application.service.impl.command;

import java.text.ParseException;
import java.util.UUID;

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
import com.evotek.iam.domain.command.LoginCmd;
import com.evotek.iam.domain.command.WriteLogCmd;
import com.evotek.iam.domain.repository.UserDomainRepository;
import com.evotek.iam.infrastructure.adapter.keycloak.KeycloakService;
import com.evotek.iam.infrastructure.support.exception.AuthErrorCode;
import com.evotek.iam.infrastructure.support.exception.AuthException;
import com.evotek.iam.infrastructure.support.exception.ErrorNormalizer;

import feign.FeignException;

@Component("selfIdpAuthCommandService")
public class SelfIDPAuthCommandServiceImpl extends AuthAbstractServiceCommand {

    public SelfIDPAuthCommandServiceImpl(
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
        LoginCmd loginCmd = commandMapper.from(loginRequest);
        User user = userDomainRepository.getByUsername((loginCmd.getUsername()));
        boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!authenticated) throw new AuthException(AuthErrorCode.UNAUTHENTICATED);
        var accessToken = generateToken(user, false, false);
        var refreshToken = generateToken(user, false, true);
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logoutIam(HttpServletRequest request, String refreshToken) {
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            var signedJWT = verifyToken(token);
            var accessExpiryTimeMillis =
                    signedJWT.getJWTClaimsSet().getExpirationTime().getTime();
            long currentTimeMillis = System.currentTimeMillis();
            long accessRemainingTimeMillis = accessExpiryTimeMillis - currentTimeMillis;
            long refreshRemainingTimeMillis = currentTimeMillis + refreshableDuration * 1000;
            addAccessTokenToBlacklist(token, accessRemainingTimeMillis);
            addRefreshTokenToBlacklist(token, refreshRemainingTimeMillis);
            UUID userId = UUID.fromString(
                    signedJWT.getJWTClaimsSet().getClaim("userId").toString());
            User user = userDomainRepository.getById(userId);
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
        try {
            var signedJWT = verifyToken(refreshToken);
            String username = signedJWT.getJWTClaimsSet().getSubject();
            User user = userDomainRepository.getByUsername(username);
            var accessToken = generateToken(user, false, false);
            return TokenDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (ParseException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }
}
