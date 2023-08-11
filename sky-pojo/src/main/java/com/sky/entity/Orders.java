package com.sky.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Orders对象", description="订单表")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String number;

    @ApiModelProperty(value = "订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款")
    private Integer status;

    @ApiModelProperty(value = "下单用户")
    private Long userId;

    @ApiModelProperty(value = "地址id")
    private Long addressBookId;

    @ApiModelProperty(value = "下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty(value = "结账时间")
    private LocalDateTime checkoutTime;

    @ApiModelProperty(value = "支付方式 1微信,2支付宝")
    private Integer payMethod;

    @ApiModelProperty(value = "支付状态 0未支付 1已支付 2退款")
    private Integer payStatus;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "收货人")
    private String consignee;

    @ApiModelProperty(value = "订单取消原因")
    private String cancelReason;

    @ApiModelProperty(value = "订单拒绝原因")
    private String rejectionReason;

    @ApiModelProperty(value = "订单取消时间")
    private LocalDateTime cancelTime;

    @ApiModelProperty(value = "预计送达时间")
    private LocalDateTime estimatedDeliveryTime;

    @ApiModelProperty(value = "配送状态  1立即送出  0选择具体时间")
    private Boolean deliveryStatus;

    @ApiModelProperty(value = "送达时间")
    private LocalDateTime deliveryTime;

    @ApiModelProperty(value = "打包费")
    private Integer packAmount;

    @ApiModelProperty(value = "餐具数量")
    private Integer tablewareNumber;

    @ApiModelProperty(value = "餐具数量状态  1按餐量提供  0选择具体数量")
    private Boolean tablewareStatus;


}
