package com.dgut.controller;

import com.dgut.entity.User;
import com.dgut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author Baller_Xiao
 * @Description
 * @create_time 2020-12-10 17:31
 * @return
 * @Version
 */
@Controller
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;

    /**
     * 登录方法
     */

    @PostMapping("/login")
    public String Login(User user, HttpSession session)
    {
        User userDB = userService.login(user);
        if(userDB!=null)
        {
            session.setAttribute("user",userDB);
            return "redirect:/file/showAll";
        }
        else
        {
            return "redirect:/index";
        }
    }
}
