package com.example.demo.controller.auth;

import com.example.demo.Result;
import com.example.demo.domain.LoginUser;
import com.example.demo.domain.LoginUserInfo;
import com.example.demo.domain.UserInfo;
import com.example.demo.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangjun
 * @date 2021/10/21  15:21
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserInfo userInfo) {
        return authService.register(userInfo);
    }

    @PostMapping("/login")
    public Result<LoginUser> login(@RequestBody LoginUserInfo loginUserInfo) {
        return authService.login(loginUserInfo.getUsername(), loginUserInfo.getPassword());
    }

//    @PostMapping("/logoutUser")
//    public Result<Boolean> logout(@RequestParam String username) {
//        return authService.logout(username);
//    }
}
