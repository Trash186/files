package com.dgut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dgut.dao")//扫描dao
public class FilesApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(FilesApplication.class, args);
    }

}
