package com.evo.dashboard.service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.evo.common.dto.response.OrderDTO;
import com.evo.common.enums.DashboardTime;
import com.evo.dashboard.adapter.order.client.OrderClient;
import com.evo.dashboard.dto.response.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final OrderClient orderClient;

    @Override
    public DashboardDTO getDashboardData(DashboardTime dashboardTime) {
        if (dashboardTime == DashboardTime.DAY) {
            return getTodayDashboardData();
        } else if (dashboardTime == DashboardTime.WEEK) {
            return getWeekDashboardData();
        } else if (dashboardTime == DashboardTime.MONTH) {
            return getMonthDashboardData();
        }
        return null;
    }

    private DashboardDTO getMonthDashboardData() {
        ZoneId vietnamZone = ZoneId.of("Asia/Ho_Chi_Minh");
        int currentYear = LocalDate.now(vietnamZone).getYear();

        // Tạo danh sách các YearMonth cho năm trước và năm hiện tại
        List<YearMonth> yearMonths = IntStream.rangeClosed(currentYear - 1, currentYear)
                .boxed()
                .flatMap(year -> IntStream.rangeClosed(1, 12).mapToObj(month -> YearMonth.of(year, month)))
                .collect(Collectors.toList());

        Instant startDate = yearMonths.get(0).atDay(1).atStartOfDay(vietnamZone).toInstant();
        LocalDate today = LocalDate.now(vietnamZone);
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        ZonedDateTime zonedEndOfDay = endOfDay.atZone(vietnamZone);
        Instant endDate = zonedEndOfDay.toInstant();

        List<OrderDTO> orderDTOs = orderClient
                .searchOrders("", null, null, startDate, endDate, null, 1, 10000, null)
                .getData();

        Map<String, List<OrderDTO>> ordersByMonthLabel = orderDTOs.stream().collect(Collectors.groupingBy(order -> {
            LocalDate date = order.getCreatedAt().atZone(vietnamZone).toLocalDate();
            return "Tháng " + date.getMonthValue() + " - " + date.getYear();
        }));

        List<RevenueAndOrdersDTO> revenueAndOrdersDTOS = ordersByMonthLabel.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    List<OrderDTO> orders = entry.getValue();
                    long totalRevenue =
                            orders.stream().mapToLong(OrderDTO::getTotalPrice).sum();
                    long totalOrders = orders.size();
                    return new RevenueAndOrdersDTO(entry.getKey(), totalRevenue, totalOrders);
                })
                .collect(Collectors.toList());

        List<OrderDTO> todayOrders = orderDTOs.stream()
                .filter(order ->
                        order.getCreatedAt().atZone(vietnamZone).toLocalDate().equals(today))
                .toList();

        long totalRevenue =
                todayOrders.stream().mapToLong(OrderDTO::getTotalPrice).sum();
        long totalOrders = todayOrders.size();

        long totalNewUsers = 0;
        SummaryTodayDTO todaySummary = new SummaryTodayDTO(totalRevenue, totalOrders, totalNewUsers);

        List<OrderStatisticByCityDTO> orderStatisticByCityDTOS =
                orderDTOs.stream()
                        .collect(Collectors.groupingBy(OrderDTO::getToCity, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .map(entry -> new OrderStatisticByCityDTO(entry.getKey().toString(), entry.getValue()))
                        .collect(Collectors.toList());

        return DashboardDTO.builder()
                .orderByCity(orderStatisticByCityDTOS)
                .revenueAndOrders(revenueAndOrdersDTOS)
                .todaySummary(todaySummary)
                .build();
    }

    private DashboardDTO getWeekDashboardData() {
        ZoneId vietnamZone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate today = LocalDate.now(vietnamZone);

        List<YearMonth> recentMonths = List.of(
                YearMonth.from(today.minusMonths(2)), YearMonth.from(today.minusMonths(1)), YearMonth.from(today));

        Instant startDate =
                recentMonths.get(0).atDay(1).atStartOfDay(vietnamZone).toInstant();
        Instant endDate = recentMonths
                .get(2)
                .atEndOfMonth()
                .atTime(LocalTime.MAX)
                .atZone(vietnamZone)
                .toInstant();

        List<OrderDTO> orderDTOs = orderClient
                .searchOrders("", null, null, startDate, endDate, null, 1, 10000, null)
                .getData();

        Map<String, List<OrderDTO>> ordersByWeekLabel = orderDTOs.stream().collect(Collectors.groupingBy(order -> {
            LocalDate date = order.getCreatedAt().atZone(vietnamZone).toLocalDate();
            int weekOfMonth = date.get(WeekFields.of(Locale.getDefault()).weekOfMonth());
            YearMonth ym = YearMonth.from(date);
            return "Tuần " + weekOfMonth + " - " + ym.getMonthValue() + "/" + ym.getYear();
        }));

        List<RevenueAndOrdersDTO> revenueAndOrdersDTOS = ordersByWeekLabel.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    List<OrderDTO> orders = entry.getValue();
                    long totalRevenue =
                            orders.stream().mapToLong(OrderDTO::getTotalPrice).sum();
                    long totalOrders = orders.size();
                    return new RevenueAndOrdersDTO(entry.getKey(), totalRevenue, totalOrders);
                })
                .collect(Collectors.toList());

        List<OrderDTO> todayOrders = orderDTOs.stream()
                .filter(order ->
                        order.getCreatedAt().atZone(vietnamZone).toLocalDate().equals(today))
                .toList();

        long totalRevenue =
                todayOrders.stream().mapToLong(OrderDTO::getTotalPrice).sum();
        long totalOrders = todayOrders.size();

        long totalNewUsers = 0;
        SummaryTodayDTO todaySummary = new SummaryTodayDTO(totalRevenue, totalOrders, totalNewUsers);

        List<OrderStatisticByCityDTO> orderStatisticByCityDTOS =
                orderDTOs.stream()
                        .collect(Collectors.groupingBy(OrderDTO::getToCity, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .map(entry -> new OrderStatisticByCityDTO(entry.getKey().toString(), entry.getValue()))
                        .collect(Collectors.toList());

        return DashboardDTO.builder()
                .orderByCity(orderStatisticByCityDTOS)
                .revenueAndOrders(revenueAndOrdersDTOS)
                .todaySummary(todaySummary)
                .build();
    }

    private DashboardDTO getTodayDashboardData() {
        ZoneId vietnamZone = ZoneId.of("Asia/Ho_Chi_Minh");
        Instant startDate =
                LocalDate.now().withDayOfMonth(1).atStartOfDay(vietnamZone).toInstant();
        LocalDate today = LocalDate.now(vietnamZone);
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        ZonedDateTime zonedEndOfDay = endOfDay.atZone(vietnamZone);
        Instant endDate = zonedEndOfDay.toInstant();

        List<OrderDTO> orderDTOs = orderClient
                .searchOrders("", null, null, startDate, endDate, null, 1, 1000, null)
                .getData();
        Map<LocalDate, DailyOrderStatisDTO> statsPerDay = orderDTOs.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().atZone(vietnamZone).toLocalDate(),
                        Collectors.collectingAndThen(Collectors.toList(), ordersInDay -> {
                            long totalRevenue = ordersInDay.stream()
                                    .mapToLong(OrderDTO::getTotalPrice)
                                    .sum();
                            long totalOrders = ordersInDay.size();
                            return new DailyOrderStatisDTO(totalRevenue, totalOrders);
                        })));
        List<RevenueAndOrdersDTO> revenueAndOrdersDTOS = statsPerDay.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Sắp xếp theo ngày tăng dần
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    DailyOrderStatisDTO stats = entry.getValue();
                    String dateLabel = date.format(DateTimeFormatter.ofPattern("dd/MM"));
                    return new RevenueAndOrdersDTO(dateLabel, stats.getTotalRevenue(), stats.getTotalOrders());
                })
                .collect(Collectors.toList());

        List<OrderStatisticByCityDTO> orderStatisticByCityDTOS =
                orderDTOs.stream()
                        .collect(Collectors.groupingBy(OrderDTO::getToCity, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .map(entry -> new OrderStatisticByCityDTO(entry.getKey().toString(), entry.getValue()))
                        .collect(Collectors.toList());

        List<OrderDTO> todayOrders = orderDTOs.stream()
                .filter(order ->
                        order.getCreatedAt().atZone(vietnamZone).toLocalDate().equals(today))
                .toList();

        long totalRevenue =
                todayOrders.stream().mapToLong(OrderDTO::getTotalPrice).sum();
        long totalOrders = todayOrders.size();

        long totalNewUsers = 0;
        SummaryTodayDTO todaySummary = new SummaryTodayDTO(totalRevenue, totalOrders, totalNewUsers);
        return DashboardDTO.builder()
                .orderByCity(orderStatisticByCityDTOS)
                .revenueAndOrders(revenueAndOrdersDTOS)
                .todaySummary(todaySummary)
                .build();
    }
}
