package com.xiu.kafka.demo.controller;

import com.xiu.kafka.demo.model.User;
import com.xiu.kafka.demo.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther 创建者: Tc李
 * @Date 创建时间: 2018/07/02 14:37
 * @Description 类描述:
 */
@RestController
@RequestMapping("msg")
public class MsgController {

    @Autowired
    SendMsgService sendMsgService;

    @PostMapping("/{id}")
    public Object sendMsg(@PathVariable Long id, @RequestBody User user){
        boolean flag = sendMsgService.sendMsg(id,user);
        return flag;
    }
}
