package com.example.demo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangjun
 * @date 2021/6/15  15:41
 */
@Getter
@Setter
public class User2 {

    private User2(){

    }

    public static User2 getUser(){
        return new User2();
    }

    private Long id;

    private String name;

    private Integer age;

    public void a(){
        System.out.println("111");
    }
}
