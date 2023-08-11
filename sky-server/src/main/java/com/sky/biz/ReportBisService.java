package com.sky.biz;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

/**
 * @author 尹志伟
 * @date 2023/7/16 17:13:50
 * @Description
 */
public interface ReportBisService {
    /**
     * 报表管理-营业额统计接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    TurnoverReportVO turnoverStatistics(String begin, String end);

    /**
     * 报表管理-用户统计接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    UserReportVO userStatistics(String begin, String end);

    /**
     * 报表管理-订单统计接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    OrderReportVO ordersStatistics(String begin, String end);

    /**
     * 报表管理-查询销量排名top10接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    SalesTop10ReportVO top10(String begin, String end);
}
