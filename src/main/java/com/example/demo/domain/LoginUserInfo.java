package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhangjun
 * @date 2021/10/21  16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfo {

    private String username;
    private String password;

}
