package com.jixuan.project.model.dto.msg;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jixuan.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息表
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MsgQueryRequest extends PageRequest implements Serializable {
    /**
     * 消息编号
     */
    private Long id;

    /**
     * 客户编号
     */
    private Long custId;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}