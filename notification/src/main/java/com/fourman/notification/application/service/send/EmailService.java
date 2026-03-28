package com.fourman.notification.application.service.send;

import java.util.Map;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fourman.common.exception.ResponseException;
import com.fourman.notification.domain.exception.BadRequestError;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final ThymeleafEmailService templateService;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendTemplateEmail(
            String to, String subject, String templateName, Map<String, Object> templateVariables) {
        try {
            // Xử lý template với Thymeleaf
            String htmlContent = templateService.processTemplate(templateName, templateVariables);

            // Tạo và gửi email
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = isHtml

            mailSender.send(mimeMessage);
            log.info("Template email '{}' đã gửi thành công đến: {}", templateName, to);
        } catch (MessagingException e) {
            log.error("Lỗi gửi template email '{}' đến {}: {}", templateName, to, e.getMessage());
            throw new ResponseException(BadRequestError.CANT_SEND_EMAIL);
        }
    }
}
