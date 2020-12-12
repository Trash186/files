package com.dgut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Baller_Xiao
 * @Description
 * @create_time 2020-12-10 17:31
 * @return
 * @Version
 */
@Controller
public class IndexController
{


    /**
     * 展示所有文件
     */

    @GetMapping("/index")
    public String toLogin()
    {
       return "login";
    }
}
