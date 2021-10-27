package com.example.demo.domain;

import com.example.demo.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangjun
 * @date 2021/8/3  13:51
 */
@Getter
@Setter
public class CarInfo <T extends Car>{
    private String message;


    public static void main(String[] args) {
        AdCar adCar = new AdCar();
        CarInfo<AdCar> adCarCarInfo = new CarInfo<>();
        CarInfo<BmwCar> userCarInfo = new CarInfo<BmwCar>();
    }
}
