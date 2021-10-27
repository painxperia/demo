package com.example.demo.service.auth;

import com.example.demo.Result;
import com.example.demo.domain.LoginUser;
import com.example.demo.domain.UserInfo;

/**
 * @author zhangjun
 * @date 2021/10/21  14:54
 */
public interface AuthService {

    /**
     * 注册
     *
     * @param userInfo
     * @return
     */
    Result<String> register(UserInfo userInfo);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    Result<LoginUser> login(String username, String password);

    /**
     * 刷新token
     *
     * @param oldToken
     * @return
     */
    String refresh(String oldToken);

    /**
     * 退出登录
     * @param username
     * @return
     */
    Result<Boolean> logout(String username);

}
