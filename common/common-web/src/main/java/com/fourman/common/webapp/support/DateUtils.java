package com.fourman.common.webapp.support;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.*;

public class DateUtils {

    public static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    /* ===========================
     * == DAY LEVEL OPERATIONS ==
     * =========================== */

    public static Instant getStartOfDayInstant(LocalDate date) {
        return date.atStartOfDay(VIETNAM_ZONE).toInstant();
    }

    public static Instant getEndOfDayInstant(LocalDate date) {
        return date.atTime(LocalTime.MAX).atZone(VIETNAM_ZONE).toInstant();
    }

    public static Instant getStartOfTodayInstant() {
        return getStartOfDayInstant(LocalDate.now(VIETNAM_ZONE));
    }

    public static Instant getEndOfTodayInstant() {
        return getEndOfDayInstant(LocalDate.now(VIETNAM_ZONE));
    }

    /* ===========================
     * == WEEK LEVEL OPERATIONS ==
     * =========================== */

    public static Instant getStartOfWeekInstant(LocalDate date) {
        LocalDate monday = date.with(MONDAY);
        return monday.atStartOfDay(VIETNAM_ZONE).toInstant();
    }

    public static Instant getEndOfWeekInstant(LocalDate date) {
        LocalDate sunday = date.with(SUNDAY);
        return sunday.atTime(LocalTime.MAX).atZone(VIETNAM_ZONE).toInstant();
    }

    public static Instant getStartOfCurrentWeekInstant() {
        return getStartOfWeekInstant(LocalDate.now(VIETNAM_ZONE));
    }

    public static Instant getEndOfCurrentWeekInstant() {
        return getEndOfWeekInstant(LocalDate.now(VIETNAM_ZONE));
    }

    /* ===========================
     * == MONTH LEVEL OPERATIONS ==
     * =========================== */

    public static Instant getStartOfMonthInstant(LocalDate date) {
        LocalDate first = date.withDayOfMonth(1);
        return first.atStartOfDay(VIETNAM_ZONE).toInstant();
    }

    public static Instant getEndOfMonthInstant(LocalDate date) {
        LocalDate last = date.withDayOfMonth(date.lengthOfMonth());
        return last.atTime(LocalTime.MAX).atZone(VIETNAM_ZONE).toInstant();
    }

    public static Instant getStartOfCurrentMonthInstant() {
        return getStartOfMonthInstant(LocalDate.now(VIETNAM_ZONE));
    }

    public static Instant getEndOfCurrentMonthInstant() {
        return getEndOfMonthInstant(LocalDate.now(VIETNAM_ZONE));
    }

    /* ===========================
     * == CONVERSIONS ==
     * =========================== */

    public static LocalDateTime toLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, VIETNAM_ZONE);
    }

    public static LocalDate toLocalDate(Instant instant) {
        return instant.atZone(VIETNAM_ZONE).toLocalDate();
    }

    public static Instant toInstant(LocalDateTime localDateTime) {
        return localDateTime.atZone(VIETNAM_ZONE).toInstant();
    }

    /* ===========================
     * == HELPERS ==
     * =========================== */

    public static boolean isBetween(Instant target, Instant start, Instant end) {
        return !target.isBefore(start) && !target.isAfter(end);
    }
}
