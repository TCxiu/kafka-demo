package com.xiu.kafka.demo.listener;


import com.alibaba.fastjson.JSON;
import com.xiu.kafka.demo.model.Message;
import com.xiu.kafka.demo.model.User;
import com.xiu.kafka.demo.responsitory.MessageResponsitory;
import com.xiu.kafka.demo.responsitory.UserRespository;
import com.xiu.kafka.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageListener {

    @Autowired
    UserService userService;

    @Autowired
    MessageResponsitory messageResponsitory;

    @KafkaListener(topics = {"user"},containerFactory = "kafkaListenerContainerFactory")
    public void received(Message msg, Acknowledgment acknowledgment){

        User record = JSON.parseObject(msg.getMessage(), User.class);

        User user = userService.finduserById(record.getId());
        user.setUpdateTime(new Date());
        user.setName(record.getName());
        user.setMoney(user.getMoney().add(record.getMoney().negate()));
        userService.updateUser(user.getId(),user);

        msg.setStatus("已接收");
        msg.setMessage(JSON.toJSONString(user));
        messageResponsitory.save(msg);
        acknowledgment.acknowledge();
    }
}
