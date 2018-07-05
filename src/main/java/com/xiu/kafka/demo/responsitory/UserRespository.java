package com.xiu.kafka.demo.responsitory;

import com.xiu.kafka.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @Auther 创建者: Tc李
 * @Date 创建时间: 2018/07/02 13:36
 * @Description 类描述:
 */

@Component
public interface UserRespository extends CrudRepository<User,Long> {
}
