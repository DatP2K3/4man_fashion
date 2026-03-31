package com.fourman.common.support;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SecurityContextUtils {

    // Context Keys
    private static final String KEY_TIME = "time";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IP_ADDRESS = "ipAddress";
    private static final String KEY_DEVICE = "device";
    private static final String KEY_LOCATION = "location";

    // Common Values
    private static final String UNKNOWN = "Unknown";
    private static final String ANONYMOUS_USER = "anonymousUser";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIMEZONE_UTC = "UTC";
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
    private static final String LOCALHOST_IPV4 = "127.0.0.1";
    private static final String SEPARATOR_COMMA = ",";
    
    // Header names for IP extraction
    private static final String[] IP_HEADER_NAMES = {
        "X-Forwarded-For",
        "HTTP_X_FORWARDED_FOR",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_CLIENT_IP",
        "HTTP_X_FORWARDED",
        "X-Real-IP",
        "X-FORWARDED-FOR"
    };

    // Browsers
    private static final String BROWSER_CHROME = "Chrome";
    private static final String BROWSER_SAFARI = "Safari";
    private static final String BROWSER_FIREFOX = "Firefox";
    private static final String BROWSER_EDGE = "Edge";
    private static final String BROWSER_EDG_SHORT = "Edg";
    private static final String BROWSER_MSIE = "MSIE";
    private static final String BROWSER_TRIDENT = "Trident";
    private static final String BROWSER_IE_FULL = "Internet Explorer";
    private static final String UNKNOWN_BROWSER = "Unknown Browser";

    // Operating Systems
    private static final String OS_WINDOWS = "Windows";
    private static final String OS_MAC_OS_X = "Mac OS X";
    private static final String OS_MACOS_DISPLAY = "macOS";
    private static final String OS_ANDROID = "Android";
    private static final String OS_IPHONE = "iPhone";
    private static final String OS_IPAD = "iPad";
    private static final String OS_IOS_DISPLAY = "iOS";
    private static final String OS_LINUX = "Linux";
    private static final String UNKNOWN_OS = "Unknown OS";

    // Device Types
    private static final String DEVICE_TABLET = "Tablet";
    private static final String DEVICE_MOBILE = "Mobile";
    
    // Display Formats
    private static final String DISPLAY_IPAD = " (iPad)";
    private static final String DISPLAY_TABLET = " (Tablet)";
    private static final String DISPLAY_IPHONE = " (iPhone)";
    private static final String DISPLAY_MOBILE = " (Mobile)";
    private static final String SEPARATOR_ON = " on ";

    public static Map<String, Object> getSecurityContextMap() {
        Map<String, Object> contextMap = new HashMap<>();

        contextMap.put(KEY_TIME, getCurrentUtcTime());
        contextMap.put(KEY_USERNAME, getCurrentUsername());

        // Try to get IP from current request if available
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            contextMap.put(KEY_IP_ADDRESS, getClientIpAddress(request));
            contextMap.put(KEY_DEVICE, getDeviceInfo(request));
            contextMap.put(KEY_LOCATION, UNKNOWN); // Would need geo IP service
        } else {
            contextMap.put(KEY_IP_ADDRESS, UNKNOWN);
            contextMap.put(KEY_DEVICE, UNKNOWN);
            contextMap.put(KEY_LOCATION, UNKNOWN);
        }

        return contextMap;
    }

    public static String getCurrentUtcTime() {
        return java.time.format.DateTimeFormatter.ofPattern(DATE_FORMAT)
                .withZone(java.time.ZoneId.of(TIMEZONE_UTC))
                .format(java.time.Instant.now());
    }

    public static String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !auth.getPrincipal().toString().equals(ANONYMOUS_USER)) {
            return auth.getName();
        }
        return UNKNOWN;
    }

    private static HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    private static String getClientIpAddress(HttpServletRequest request) {
        for (String headerName : IP_HEADER_NAMES) {
            String header = request.getHeader(headerName);
            if (StrUtils.isPresent(header) && !UNKNOWN.equalsIgnoreCase(header)) {
                // X-Forwarded-For có thể chứa nhiều IP, lấy IP đầu tiên (client gốc)
                if (header.contains(SEPARATOR_COMMA)) {
                    return header.split(SEPARATOR_COMMA)[0].trim();
                }
                return header.trim();
            }
        }

        // Fallback to remote address
        String remoteAddr = request.getRemoteAddr();

        // Xử lý trường hợp IPv6 localhost
        if (LOCALHOST_IPV6.equals(remoteAddr)) {
            // Trả về IPv4 localhost thay vì IPv6
            return LOCALHOST_IPV4;
        }

        return remoteAddr;
    }

    private static String getDeviceInfo(HttpServletRequest request) {
        String userAgent = request.getHeader(HEADER_USER_AGENT);
        if (StrUtils.isNullOrEmpty(userAgent)) {
            return UNKNOWN;
        }

        StringBuilder deviceInfo = new StringBuilder();

        // Xác định trình duyệt
        if (userAgent.contains(BROWSER_CHROME) && userAgent.contains(BROWSER_SAFARI)) {
            deviceInfo.append(BROWSER_CHROME);
        } else if (userAgent.contains(BROWSER_FIREFOX)) {
            deviceInfo.append(BROWSER_FIREFOX);
        } else if (userAgent.contains(BROWSER_SAFARI) && !userAgent.contains(BROWSER_CHROME)) {
            deviceInfo.append(BROWSER_SAFARI);
        } else if (userAgent.contains(BROWSER_EDGE) || userAgent.contains(BROWSER_EDG_SHORT)) {
            deviceInfo.append(BROWSER_EDGE);
        } else if (userAgent.contains(BROWSER_MSIE) || userAgent.contains(BROWSER_TRIDENT)) {
            deviceInfo.append(BROWSER_IE_FULL);
        } else {
            deviceInfo.append(UNKNOWN_BROWSER);
        }

        deviceInfo.append(SEPARATOR_ON);

        // Xác định hệ điều hành
        if (userAgent.contains(OS_WINDOWS)) {
            deviceInfo.append(OS_WINDOWS);
        } else if (userAgent.contains(OS_MAC_OS_X)) {
            deviceInfo.append(OS_MACOS_DISPLAY);
        } else if (userAgent.contains(OS_ANDROID)) {
            deviceInfo.append(OS_ANDROID);
        } else if (userAgent.contains(OS_IPHONE) || userAgent.contains(OS_IPAD)) {
            deviceInfo.append(OS_IOS_DISPLAY);
        } else if (userAgent.contains(OS_LINUX)) {
            deviceInfo.append(OS_LINUX);
        } else {
            deviceInfo.append(UNKNOWN_OS);
        }

        // Xác định loại thiết bị
        if (userAgent.contains(DEVICE_TABLET) || userAgent.contains(OS_IPAD)) {
            if (userAgent.contains(OS_IPAD)) {
                deviceInfo.append(DISPLAY_IPAD);
            } else {
                deviceInfo.append(DISPLAY_TABLET);
            }
        } else if (userAgent.contains(DEVICE_MOBILE)) {
            if (userAgent.contains(OS_IPHONE)) {
                deviceInfo.append(DISPLAY_IPHONE);
            } else {
                deviceInfo.append(DISPLAY_MOBILE);
            }
        }

        return deviceInfo.toString();
    }
}
