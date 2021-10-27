package com.example.demo.domain;

import lombok.*;

/**
 * @author zhangjun
 * @date 2021/8/3  13:50
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {

    private Long id;

    private String name;

    public static void main(String[] args) {
        Car car = Car.builder().id(1L).name("1").build();
    }
}
