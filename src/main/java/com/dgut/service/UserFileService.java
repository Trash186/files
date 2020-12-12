package com.dgut.service;

import com.dgut.entity.User;
import com.dgut.entity.UserFile;

import java.util.List;

/**
 * @author Baller_Xiao
 * @Description
 * @create_time 2020-12-10 17:22
 * @return
 * @Version
 */
public interface UserFileService
{
    List<UserFile> findByUserId(Integer id);

    void save(UserFile userFile);

    UserFile findById(String id);

    void update(UserFile userFile);

    void delete(String id);
}
