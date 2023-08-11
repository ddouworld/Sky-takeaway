package com.sky.biz.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.sky.biz.ReportBisService;
import com.sky.constant.OrdersConstant;
import com.sky.service.OrdersService;
import com.sky.service.UserService;
import com.sky.utils.DateTimeUtil;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 尹志伟
 * @date 2023/7/16 17:13:57
 * @Description
 */
@Slf4j
@Service
public class ReportBisServiceImpl implements ReportBisService {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UserService userService;

    /**
     * 报表管理-营业额统计接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @Override
    public TurnoverReportVO turnoverStatistics(String begin, String end) {
        //当日期不存在的时候 给与默认值
        if (StrUtil.isBlank(begin) || StrUtil.isBlank(end)) {
            String nowDate = DateUtil.formatDate(new Date());
            begin = nowDate;
            end = nowDate;
        }
        List<LocalDate> dateList = DateTimeUtil.getBetweenList(begin, end);
        ArrayList<String> turnoverList = new ArrayList<String>(dateList.size());
        for (LocalDate date : dateList) {
            String dateStr = date.toString();
            String dateStrBegin = dateStr + " 00:00:00";
            String dateStrEnd = dateStr + " 23:59:59";
            BigDecimal sumAmount = ordersService.getSumAmountByDateAndStatus(dateStrBegin, dateStrEnd, OrdersConstant.STATUS_5);
            turnoverList.add(sumAmount.toString());
        }
        String date = StrUtil.join(",", dateList);
        String turnover = StrUtil.join(",", turnoverList);
        return TurnoverReportVO.builder().dateList(date).turnoverList(turnover).build();
    }

    /**
     * 报表管理-用户统计接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @Override
    public UserReportVO userStatistics(String begin, String end) {
        //日期集合
        List<LocalDate> dateList = DateTimeUtil.getBetweenList(begin, end);
        //用户总量集合
        ArrayList<String> totalUserList = new ArrayList<String>(dateList.size());
        //新增用户集合
        ArrayList<String> newUserList = new ArrayList<String>(dateList.size());
        for (LocalDate date : dateList) {
            String dateStr = date.toString();
            String dateStrBegin = dateStr + " 00:00:00";
            String dateStrEnd = dateStr + " 23:59:59";
            //获取用户总量
            Integer totalDateCount = userService.countByDate("", dateStrEnd);
            //获取新增用户量
            Integer newTotalDateCount = userService.countByDate(dateStrBegin, dateStrEnd);
            totalUserList.add(totalDateCount.toString());
            newUserList.add(newTotalDateCount.toString());
        }
        return UserReportVO.builder()
                .dateList(StrUtil.join(",", dateList))
                .totalUserList(StrUtil.join(",", totalUserList))
                .newUserList(StrUtil.join(",", newUserList))
                .build();
    }

    /**
     * 报表管理-订单统计接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @Override
    public OrderReportVO ordersStatistics(String begin, String end) {
        //获取订单总数
        Integer totalOrderCount = ordersService.countByDateAndStatus(begin, end, null);
        //获取有效订单数量
        Integer validOrderCount = ordersService.countByDateAndStatus(begin, end, OrdersConstant.STATUS_5);
        //订单完成率
        Double completionRateOrder = (double) ((validOrderCount / totalOrderCount));

        //日期集合
        List<LocalDate> dateList = DateTimeUtil.getBetweenList(begin, end);
        //每日订单数
        ArrayList<String> orderCountList = new ArrayList<String>(dateList.size());
        //每日有效订单数
        ArrayList<String> validOrderCountList = new ArrayList<String>(dateList.size());
        for (LocalDate date : dateList) {
            String dateStr = date.toString();
            String dateStrBegin = dateStr + " 00:00:00";
            String dateStrEnd = dateStr + " 23:59:59";
            orderCountList.add(ordersService.countByDateAndStatus(dateStrBegin, dateStrEnd, null).toString());
            validOrderCountList.add(ordersService.countByDateAndStatus(dateStrBegin, dateStrEnd, OrdersConstant.STATUS_5).toString());
        }
        return OrderReportVO.builder()
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(completionRateOrder)
                .dateList(StrUtil.join(",", dateList))
                .orderCountList(StrUtil.join(",", orderCountList))
                .validOrderCountList(StrUtil.join(",", validOrderCountList)).build();
    }

    /**
     * 报表管理-查询销量排名top10接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @Override
    public SalesTop10ReportVO top10(String begin, String end) {
        if (StrUtil.isNotBlank(begin)) {
            begin = begin + " 00:00:00";
        }
        if (StrUtil.isNotBlank(end)) {
            end = end + " 23:59:59";
        }
        List<Map<String, Object>> listMap = ordersService.top10LimitNumDesc(begin, end, 10);
        ArrayList<String> nameList = new ArrayList<>(listMap.size());
        ArrayList<String> numberList = new ArrayList<>(listMap.size());
        if (CollUtil.isNotEmpty(listMap)) {
            for (Map<String, Object> map : listMap) {
                String name = (String) map.get("name");
                BigDecimal number = (BigDecimal) map.get("number");
                nameList.add(name);
                numberList.add(number.toString());
            }
        }
        return SalesTop10ReportVO.builder()
                .nameList(StrUtil.join(",", nameList))
                .numberList(StrUtil.join(",", numberList))
                .build();
    }
}
