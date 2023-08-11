package com.sky.controller.admin.report;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.sky.base.BaseController;
import com.sky.biz.ReportBisService;
import com.sky.biz.WorkspaceBizService;
import com.sky.result.Result;
import com.sky.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/16 17:02:54
 * @Description
 */
@RestController
@RequestMapping("/admin/report")
@Slf4j
@Api(tags = "报表管理")
public class ReportController extends BaseController {

    @Autowired
    private ReportBisService reportBisService;

    @Autowired
    private WorkspaceBizService workspaceBizService;

    /**
     * 报表管理-营业额统计接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @ApiOperation("报表管理-营业额统计接口")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(@RequestParam("begin") @DateTimeFormat(pattern = "yyyy-MM-dd") String begin,
                                                       @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") String end) {
        log.info("turnoverStatistics begin ：{} , end:{}", begin, end);
        TurnoverReportVO vo = reportBisService.turnoverStatistics(begin, end);
        return Result.success(vo);
    }


    /**
     * 报表管理-用户统计接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @ApiOperation("报表管理-用户统计接口")
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(@RequestParam("begin") @DateTimeFormat(pattern = "yyyy-MM-dd") String begin,
                                               @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") String end) {
        log.info("userStatistics begin ：{} , end:{}", begin, end);
        UserReportVO vo = reportBisService.userStatistics(begin, end);
        return Result.success(vo);
    }


    /**
     * 报表管理-订单统计接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @ApiOperation("报表管理-订单统计接口")
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> ordersStatistics(@RequestParam("begin") @DateTimeFormat(pattern = "yyyy-MM-dd") String begin,
                                                  @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") String end) {
        log.info("ordersStatistics begin ：{} , end:{}", begin, end);
        OrderReportVO vo = reportBisService.ordersStatistics(begin, end);
        return Result.success(vo);
    }

    /**
     * 报表管理-查询销量排名top10接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @ApiOperation("报表管理-查询销量排名top10接口")
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> top10(@RequestParam("begin") @DateTimeFormat(pattern = "yyyy-MM-dd") String begin,
                                            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") String end) {
        log.info("top10 begin ：{} , end:{}", begin, end);
        SalesTop10ReportVO vo = reportBisService.top10(begin, end);
        return Result.success(vo);
    }

    /**
     * 报表管理-导出运营数据30天的
     */
    @ApiOperation("报表管理-导出运营数据30天的")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        //读取模板文件
        String templateFileName = this.getClass().getResource("/template/data.xlsx").getPath();

        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            //假设今天：2023年3月31日
            //1.计算的得出近30日的日期，开始日期(2023年3月1日)和结束日期(2023年3月30日)
            LocalDate begin = LocalDate.now().minusDays(30);
            LocalDate end = LocalDate.now().minusDays(1);

            //2.计算出开始日期最小时间(2023年3月1日 00:00)和结束日期最大时间(2023年3月30日 23:59:59)
            LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

            //3.调用WorkspaceService的getBusinessData方法获取近30天概览的运营数据，得到BusinessDataVO
            BusinessDataVO businessData = workspaceBizService.getBusinessData(beginTime, endTime);


            //输出统计数据
            excelWriter.fill(businessData, writeSheet);

            List<BusinessDataVO> businessDataVOList = new ArrayList<>();
            for (int i = 0; i < 30; i++) {

                //8.1 计算出每一天的最小时间和最大时间
                LocalDate date = begin.plusDays(i);
                beginTime = LocalDateTime.of(date,LocalTime.MIN);
                endTime = LocalDateTime.of(date,LocalTime.MAX);

                //8.2 用WorkspaceService的getBusinessData方法获取当天概览的运营数据，得到BusinessDataVO
                businessData = workspaceBizService.getBusinessData(beginTime, endTime);
                businessData.setDate(date.toString());
                businessDataVOList.add(businessData);
            }


            // 输出集合每日数据
            excelWriter.fill(businessDataVOList, writeSheet);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
