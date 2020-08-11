package com.jolan.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author jolan80
 * @date 2020-08-11 20:08
 *
 * 创建Spring定义的FactoryBean
 */
public class ColorFactoryBean implements FactoryBean<Color> {
    //返回一个Color对象，这个对象会添加到容器中
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean...getObject()");
        return new Color();
    }

    public Class<?> getObjectType() {
        return Color.class;
    }
    //是否为单例
    public boolean isSingleton() {
        return true;
    }
}
