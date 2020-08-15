package com.jolan.com.jolan.dao;

import org.springframework.stereotype.Repository;

/**
 * @author jolan80
 * @date 2020-08-08 15:41
 */
@Repository
public class BookDao {
    private String label = "1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "label='" + label + '\'' +
                '}';
    }
}
