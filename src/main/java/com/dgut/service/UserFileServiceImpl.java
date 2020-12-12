package com.dgut.service;

import com.dgut.dao.UserDao;
import com.dgut.dao.UserFilesDao;
import com.dgut.entity.User;
import com.dgut.entity.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Baller_Xiao
 * @Description
 * @create_time 2020-12-10 17:23
 * @return
 * @Version
 */
@Service
@Transactional
public class UserFileServiceImpl implements  UserFileService
{

    @Autowired
    private UserFilesDao userFilesDao;
    @Override
    public List<UserFile> findByUserId(Integer id)
    {
        return userFilesDao.findByUserId(id);
    }

    @Override
    public void save(UserFile userFile)
    {
        String isImg = userFile.getType().startsWith("image")?"是":"否";
        userFile.setIsImg(isImg);
        userFile.setDownCounts(0);
        userFile.setUploadTime(new Date());
        userFilesDao.save(userFile);
    }

    @Override
    public UserFile findById(String id)
    {
        return userFilesDao.findById(id);
    }

    @Override
    public void update(UserFile userFile)
    {
        userFilesDao.update(userFile);
    }

    @Override
    public void delete(String id)
    {
        userFilesDao.delete(id);
    }
}
