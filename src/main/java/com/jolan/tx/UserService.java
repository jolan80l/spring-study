package com.jolan.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jolan80
 * @date 2021-03-08 21:34
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void inserUser(){
        userDao.insert();
        //otherDao.otherMethod()
        System.out.println("插入完成");
        int i = 10/0;
    }

}
