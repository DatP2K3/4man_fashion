package com.evo.common.webapp.i18n;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Helper to resolve i18n messages from any layer.
 * Usage: messageHelper.getMessage("error.product.not_found")
 */
@Component
public class MessageHelper {
    private final MessageSource messageSource;

    public MessageHelper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Get localized message using current request's locale (from Accept-Language header).
     */
    public String getMessage(String messageKey, Object... params) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, params, messageKey, locale);
    }

    /**
     * Get localized message with a specific locale.
     */
    public String getMessage(String messageKey, Locale locale, Object... params) {
        return messageSource.getMessage(messageKey, params, messageKey, locale);
    }
}
