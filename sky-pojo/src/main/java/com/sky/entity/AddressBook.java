package com.sky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 地址簿
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AddressBook对象", description="地址簿")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "收货人")
    private String consignee;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "省级区划编号")
    private String provinceCode;

    @ApiModelProperty(value = "省级名称")
    private String provinceName;

    @ApiModelProperty(value = "市级区划编号")
    private String cityCode;

    @ApiModelProperty(value = "市级名称")
    private String cityName;

    @ApiModelProperty(value = "区级区划编号")
    private String districtCode;

    @ApiModelProperty(value = "区级名称")
    private String districtName;

    @ApiModelProperty(value = "详细地址")
    private String detail;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "默认 0 否 1是")
    private Boolean isDefault;


}
