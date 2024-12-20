package com.jixuan.project.model.dto.order;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单添加请求
 */
@Data
public class OrderAddRequest implements Serializable {

    /**
     * 客户编号
     */
    private Long custId;

    /**
     * 商品名
     */
    private String product;

    /**
     * 订单金额
     */
    private BigDecimal totalNum;

    /**
     * 订单状态 0-未完成 1-已完成
     */
    private Integer status;

    /**
     * 支付方式 0-支付宝 1-微信
     */
    private Integer method;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}