package com.dgut.service;

import com.dgut.dao.UserDao;
import com.dgut.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Baller_Xiao
 * @Description
 * @create_time 2020-12-10 17:23
 * @return
 * @Version
 */
@Service
@Transactional
public class UserServiceImpl implements  UserService
{
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user)
    {
        return userDao.login(user);
    }
}
