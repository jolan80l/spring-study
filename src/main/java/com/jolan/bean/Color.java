package com.jolan.bean;

/**
 * @author jolan80
 * @date 2020-08-09 10:14
 */
public class Color {
    public Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Color{" +
                "car=" + car +
                '}';
    }
}
