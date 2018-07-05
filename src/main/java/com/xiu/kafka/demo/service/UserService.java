package com.xiu.kafka.demo.service;

import com.xiu.kafka.demo.model.User;
import com.xiu.kafka.demo.responsitory.UserRespository;
import com.xiu.kafka.demo.utils.IdFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther 创建者: Tc李
 * @Date 创建时间: 2018/07/02 13:53
 * @Description 类描述:
 */
@Service
public class UserService {

    @Autowired
    UserRespository userRespository;


    public boolean addUser(User user){
        user.setId(IdFactory.nextId());
        user.setCreateTime(new Date());
        User save = userRespository.save(user);
        return save == null ? false : true;
    }

    public void delUser(Long id){
        userRespository.deleteById(id);
    }

    public boolean updateUser(Long id,User record){
        User find = userRespository.findById(id).get();
        record.setId(id);
        record.setCreateTime(find.getCreateTime());
        record.setUpdateTime(new Date());
        User save = userRespository.save(record);
        return save == null ? false : true;
    }
    public User finduserById(Long id){
        return userRespository.findById(id).get();
    }
    public List<User> listUser(){

        ArrayList<User> users = new ArrayList<>();
        Iterable<User> all = userRespository.findAll();
        all.forEach((user -> {
            users.add(user);
        }));
        return users;
    }
}
