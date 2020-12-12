package com.dgut.dao;

import com.dgut.entity.User;
import com.dgut.entity.UserFile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Baller_Xiao
 * @Description
 * @create_time 2020-12-10 17:16
 * @return
 * @Version
 */
@Repository
public interface UserFilesDao
{
   List<UserFile> findByUserId(Integer id);

   void save(UserFile userFile);

   UserFile findById(String id);

   void update(UserFile userFile);

   void delete(String id);
}
