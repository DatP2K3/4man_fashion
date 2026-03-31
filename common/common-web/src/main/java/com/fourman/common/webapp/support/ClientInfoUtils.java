package com.fourman.common.webapp.support;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public final class ClientInfoUtils {

    // ── Internal Parser Constants ─────────────────────────────────────
    private static final String KEY_MAC_OS = "mac os";
    private static final String KEY_MACINTOSH = "macintosh";
    private static final String KEY_IPHONE = "iphone";
    private static final String KEY_IPAD = "ipad";
    private static final String KEY_IPOD = "ipod";
    private static final String KEY_CROS = "cros";
    private static final String KEY_EDG = "Edg/";
    private static final String KEY_EDGE = "Edge/";
    private static final String KEY_OPR = "OPR/";
    private static final String KEY_OPERA = "Opera/";
    private static final String KEY_CHROME = "Chrome/";
    private static final String KEY_SAFARI = "Safari/";
    private static final String KEY_CHROMIUM = "Chromium/";
    private static final String KEY_FIREFOX = "Firefox/";

    private ClientInfoUtils() {}

    public static ClientInfo extract(HttpServletRequest request) {
        String ip = extractClientIp(request);
        String userAgent = request.getHeader(StringPool.USER_AGENT);
        if (!StringUtils.hasLength(userAgent)) {
            userAgent = StringPool.UNKNOWN;
        }
        String os = parseOs(userAgent);
        String browser = parseBrowser(userAgent);
        String device = parseDevice(userAgent);
        return new ClientInfo(ip, os, browser, device, userAgent);
    }

    public static String extractClientIp(HttpServletRequest request) {
        String ip = request.getHeader(StringPool.X_FORWARDED_FOR);
        if (StringUtils.hasLength(ip) && !StringPool.UNKNOWN.equalsIgnoreCase(ip)) {
            int idx = ip.indexOf(StringPool.COMMA);
            return idx != -1 ? ip.substring(0, idx).trim() : ip.trim();
        }
        ip = request.getHeader(StringPool.X_REAL_IP);
        if (StringUtils.hasLength(ip) && !StringPool.UNKNOWN.equalsIgnoreCase(ip)) {
            return ip.trim();
        }
        return request.getRemoteAddr();
    }

    static String parseOs(String ua) {
        if (ua == null) return StringPool.UNKNOWN;
        String lower = ua.toLowerCase();
        if (lower.contains(StringPool.OS_WINDOWS.toLowerCase())) return StringPool.OS_WINDOWS;
        if (lower.contains(KEY_MAC_OS) || lower.contains(KEY_MACINTOSH)) return StringPool.OS_MAC;
        if (lower.contains(StringPool.OS_ANDROID.toLowerCase())) return StringPool.OS_ANDROID;
        if (lower.contains(KEY_IPHONE) || lower.contains(KEY_IPAD) || lower.contains(KEY_IPOD))
            return StringPool.OS_IOS;
        if (lower.contains(StringPool.OS_LINUX.toLowerCase())) return StringPool.OS_LINUX;
        if (lower.contains(KEY_CROS)) return StringPool.OS_CHROME_OS;
        return StringPool.UNKNOWN;
    }

    static String parseBrowser(String ua) {
        if (ua == null) return StringPool.UNKNOWN;
        if (ua.contains(KEY_EDG) || ua.contains(KEY_EDGE)) return StringPool.BROWSER_EDGE;
        if (ua.contains(KEY_OPR) || ua.contains(KEY_OPERA)) return StringPool.BROWSER_OPERA;
        if (ua.contains(KEY_CHROME) && !ua.contains(KEY_EDG)) return StringPool.BROWSER_CHROME;
        if (ua.contains(KEY_SAFARI) && !ua.contains(KEY_CHROME) && !ua.contains(KEY_CHROMIUM))
            return StringPool.BROWSER_SAFARI;
        if (ua.contains(KEY_FIREFOX)) return StringPool.BROWSER_FIREFOX;
        return StringPool.UNKNOWN;
    }

    static String parseDevice(String ua) {
        if (ua == null) return StringPool.UNKNOWN;
        String lower = ua.toLowerCase();
        if (lower.contains(StringPool.DEVICE_MOBILE.toLowerCase())
                || lower.contains(KEY_IPHONE)
                || lower.contains(StringPool.OS_ANDROID.toLowerCase())
                        && !lower.contains(StringPool.DEVICE_TABLET.toLowerCase())) return StringPool.DEVICE_MOBILE;
        if (lower.contains(StringPool.DEVICE_TABLET.toLowerCase()) || lower.contains(KEY_IPAD))
            return StringPool.DEVICE_TABLET;
        return StringPool.DEVICE_DESKTOP;
    }
}
