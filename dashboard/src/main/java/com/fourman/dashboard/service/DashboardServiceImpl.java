package com.fourman.dashboard.service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.fourman.common.dto.response.OrderDTO;
import com.fourman.common.enums.DashboardTime;
import com.fourman.dashboard.adapter.order.client.OrderClient;
import com.fourman.dashboard.adapter.user.client.ProfileClient;
import com.fourman.dashboard.dto.response.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    private final OrderClient orderClient;
    private final ProfileClient profileClient;

    @Override
    public DashboardDTO getDashboardData(DashboardTime dashboardTime) {
        return switch (dashboardTime) {
            case DAY -> getTodayDashboardData();
            case WEEK -> getWeekDashboardData();
            case MONTH -> getMonthDashboardData();
        };
    }

    // ===== Time period methods =====

    private DashboardDTO getMonthDashboardData() {
        int currentYear = LocalDate.now(VIETNAM_ZONE).getYear();

        List<YearMonth> yearMonths = IntStream.rangeClosed(currentYear - 1, currentYear)
                .boxed()
                .flatMap(year -> IntStream.rangeClosed(1, 12).mapToObj(month -> YearMonth.of(year, month)))
                .collect(Collectors.toList());

        Instant startDate =
                yearMonths.get(0).atDay(1).atStartOfDay(VIETNAM_ZONE).toInstant();
        Instant endDate = endOfToday();

        List<OrderDTO> allOrders = fetchOrders(startDate, endDate);

        // Group by month label
        Function<OrderDTO, String> monthGrouper = order -> {
            LocalDate date = toLocalDate(order);
            return "Tháng " + date.getMonthValue() + " - " + date.getYear();
        };

        return buildDashboard(allOrders, startDate, endDate, monthGrouper);
    }

    private DashboardDTO getWeekDashboardData() {
        LocalDate today = LocalDate.now(VIETNAM_ZONE);

        Instant startDate = YearMonth.from(today.minusMonths(2))
                .atDay(1)
                .atStartOfDay(VIETNAM_ZONE)
                .toInstant();
        Instant endDate = YearMonth.from(today)
                .atEndOfMonth()
                .atTime(LocalTime.MAX)
                .atZone(VIETNAM_ZONE)
                .toInstant();

        List<OrderDTO> allOrders = fetchOrders(startDate, endDate);

        // Group by week label
        Function<OrderDTO, String> weekGrouper = order -> {
            LocalDate date = toLocalDate(order);
            int weekOfMonth = date.get(WeekFields.of(Locale.getDefault()).weekOfMonth());
            YearMonth ym = YearMonth.from(date);
            return "Tuần " + weekOfMonth + " - " + ym.getMonthValue() + "/" + ym.getYear();
        };

        return buildDashboard(allOrders, startDate, endDate, weekGrouper);
    }

    private DashboardDTO getTodayDashboardData() {
        Instant startDate = LocalDate.now(VIETNAM_ZONE)
                .withDayOfMonth(1)
                .atStartOfDay(VIETNAM_ZONE)
                .toInstant();
        Instant endDate = endOfToday();

        List<OrderDTO> allOrders = fetchOrders(startDate, endDate);

        // Group by day label (dd/MM)
        Function<OrderDTO, String> dayGrouper =
                order -> toLocalDate(order).format(DateTimeFormatter.ofPattern("dd/MM"));

        return buildDashboard(allOrders, startDate, endDate, dayGrouper);
    }

    // ===== Common extracted methods =====

    /**
     * Build a complete DashboardDTO from orders, using the provided grouping function
     * for revenue/orders chart labels.
     */
    private DashboardDTO buildDashboard(
            List<OrderDTO> allOrders, Instant startDate, Instant endDate, Function<OrderDTO, String> labelGrouper) {
        List<RevenueAndOrdersDTO> revenueAndOrders = buildRevenueAndOrders(allOrders, labelGrouper);
        List<OrderStatisticByCityDTO> orderByCity = buildOrderByCity(allOrders);
        SummaryTodayDTO todaySummary = buildTodaySummary(allOrders, startDate, endDate);

        return DashboardDTO.builder()
                .revenueAndOrders(revenueAndOrders)
                .orderByCity(orderByCity)
                .todaySummary(todaySummary)
                .build();
    }

    /**
     * Group orders by label and calculate revenue + count per group.
     */
    private List<RevenueAndOrdersDTO> buildRevenueAndOrders(
            List<OrderDTO> orders, Function<OrderDTO, String> labelGrouper) {
        return orders.stream().collect(Collectors.groupingBy(labelGrouper)).entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new RevenueAndOrdersDTO(
                        entry.getKey(),
                        entry.getValue().stream()
                                .mapToLong(OrderDTO::getTotalPrice)
                                .sum(),
                        entry.getValue().size()))
                .collect(Collectors.toList());
    }

    /**
     * Group orders by city.
     */
    private List<OrderStatisticByCityDTO> buildOrderByCity(List<OrderDTO> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(OrderDTO::getToCity, Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> new OrderStatisticByCityDTO(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Build today's summary: revenue, order count, new users.
     */
    private SummaryTodayDTO buildTodaySummary(List<OrderDTO> allOrders, Instant startDate, Instant endDate) {
        LocalDate today = LocalDate.now(VIETNAM_ZONE);

        List<OrderDTO> todayOrders = allOrders.stream()
                .filter(order -> toLocalDate(order).equals(today))
                .toList();

        long totalRevenue =
                todayOrders.stream().mapToLong(OrderDTO::getTotalPrice).sum();
        long totalOrders = todayOrders.size();
        long totalNewUsers =
                profileClient.searchProfiles("", startDate, endDate).getPage().getTotal();

        return new SummaryTodayDTO(totalRevenue, totalOrders, totalNewUsers);
    }

    // ===== Utility methods =====

    private List<OrderDTO> fetchOrders(Instant startDate, Instant endDate) {
        return orderClient
                .searchOrders("", null, null, startDate, endDate, null, 1, 10000, null)
                .getData();
    }

    private LocalDate toLocalDate(OrderDTO order) {
        return order.getCreatedAt().atZone(VIETNAM_ZONE).toLocalDate();
    }

    private Instant endOfToday() {
        return LocalDate.now(VIETNAM_ZONE)
                .atTime(LocalTime.MAX)
                .atZone(VIETNAM_ZONE)
                .toInstant();
    }
}
