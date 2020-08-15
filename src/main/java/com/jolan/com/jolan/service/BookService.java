package com.jolan.com.jolan.service;

import com.jolan.com.jolan.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jolan80
 * @date 2020-08-08 15:40
 */
@Service
public class BookService {
//    @Qualifier("bookDao2")
//    @Autowired
    @Resource
    private BookDao bookDao;

    public void print(){
        System.out.println(bookDao);
    }
}
