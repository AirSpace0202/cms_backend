package com.jixuan.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.jixuan.project.common.BaseResponse;
import com.jixuan.project.common.ResultUtils;
import com.jixuan.project.model.dto.msg.MsgQueryRequest;
import com.jixuan.project.model.entity.Msg;
import com.jixuan.project.model.vo.MsgVO;
import com.jixuan.project.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/msg")
public class MsgController {
    @Resource
    MsgService msgService;

    @GetMapping("/list/page")
    public BaseResponse<Page<MsgVO>> listMsgByPage(MsgQueryRequest msgQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        Msg msgQuery = new Msg();
        if (msgQueryRequest != null) {
            BeanUtils.copyProperties(msgQueryRequest, msgQuery);
            current = msgQueryRequest.getCurrent();
            size = msgQueryRequest.getPageSize();
        }
        QueryWrapper<Msg> queryWrapper = new QueryWrapper<>(msgQuery);
        Page<Msg> msgPage = msgService.page(new Page<>(current, size), queryWrapper);
        Page<MsgVO> msgVOPage = new PageDTO<>(msgPage.getCurrent(), msgPage.getSize(), msgPage.getTotal());
        List<MsgVO> msgVOList = msgPage.getRecords().stream().map(msg -> {
            MsgVO msgVO = new MsgVO();
            BeanUtils.copyProperties(msg, msgVO);
            return msgVO;
        }).collect(Collectors.toList());
        msgVOPage.setRecords(msgVOList);
        return ResultUtils.success(msgVOPage);
    }
}
