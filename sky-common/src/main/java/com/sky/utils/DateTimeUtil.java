package com.sky.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/16 17:22:59
 * @Description
 */
public class DateTimeUtil {

    /**
     * 获取两个时间段之间的日期集合
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return
     */
    public static List<LocalDate> getBetweenList(String begin, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(begin, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);
        List<LocalDate> dateList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 0; i <= daysBetween; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            dateList.add(currentDate);
        }
        return dateList;
    }

}
