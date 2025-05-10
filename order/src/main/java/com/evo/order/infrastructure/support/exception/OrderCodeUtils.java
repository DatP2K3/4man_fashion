package com.evo.order.infrastructure.support.exception;

import java.util.Random;

public class OrderCodeUtils {
    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return "PD" + sb.toString();
    }
}
