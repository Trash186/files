package com.dgut.dao;

import com.dgut.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author Baller_Xiao
 * @Description
 * @create_time 2020-12-10 17:16
 * @return
 * @Version
 */
@Repository
public interface UserDao
{
    User login(User user);
}
