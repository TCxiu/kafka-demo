package com.xiu.kafka.demo.service;

import com.alibaba.fastjson.JSON;
import com.xiu.kafka.demo.model.Message;
import com.xiu.kafka.demo.model.User;
import com.xiu.kafka.demo.responsitory.MessageResponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Auther 创建者: Tc李
 * @Date 创建时间: 2018/6/15 13:34
 * @Description 类描述:
 */

@Component
public class SendMsgService {

    @Autowired
    MessageResponsitory messageResponsitory;

    @Autowired
    KafkaTemplate kafkaTemplate;


    public boolean sendMsg(Long id,User user){
        user.setId(id);
        Message message = new Message();
        message.setStatus("预发送");
        message.setMessage(JSON.toJSONString(user));
        messageResponsitory.save(message);

        kafkaTemplate.send("test","a",message);
        return true;
    }
}
