package com.jixuan.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jixuan.project.model.entity.Msg;
import com.jixuan.project.service.MsgService;
import com.jixuan.project.mapper.MsgMapper;
import org.springframework.stereotype.Service;

/**
* @author Jaychou
* @description 针对表【msg(消息表)】的数据库操作Service实现
* @createDate 2024-12-19 15:12:23
*/
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg>
    implements MsgService{

}




