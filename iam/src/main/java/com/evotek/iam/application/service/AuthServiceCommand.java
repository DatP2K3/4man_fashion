package com.evotek.iam.application.service;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.evotek.iam.application.dto.request.LoginRequest;
import com.evotek.iam.application.dto.request.VerifyOtpRequest;
import com.evotek.iam.application.dto.response.TokenDTO;
import com.evotek.iam.domain.command.ResetKeycloakPasswordCmd;

@Service
public interface AuthServiceCommand {
    TokenDTO authenticate(LoginRequest loginRequest);

    TokenDTO verifyOtp(VerifyOtpRequest verifyOtpRequest);

    void logoutIam(HttpServletRequest request, String refreshToken);

    TokenDTO refresh(String refreshToken);

    void requestPasswordReset(String username, ResetKeycloakPasswordCmd resetKeycloakPasswordCmd);

    void resetPassword(ResetKeycloakPasswordCmd resetKeycloakPasswordCmd);
}
