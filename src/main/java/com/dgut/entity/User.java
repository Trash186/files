package com.dgut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Baller_Xiao
 * @Description
 * @create_time 2020-12-10 17:13
 * @return
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain=true)
public class User
{
    private Integer id;
    private String username;
    private String password;
}
