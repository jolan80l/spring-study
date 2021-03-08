package com.jolan.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author jolan80
 * @date 2021-03-08 21:35
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(){
        String sql = "insert into tbl_user(username, age) values(?, ?)";
        String userName = UUID.randomUUID().toString().substring(0, 5);
        jdbcTemplate.update(sql, userName, 19);
    }
}
