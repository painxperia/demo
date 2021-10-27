package com.example.demo.service.auth.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.Result;
import com.example.demo.domain.LoginUser;
import com.example.demo.domain.UserInfo;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.pojo.Role;
import com.example.demo.pojo.UserRole;
import com.example.demo.pojo.ZpUser;
import com.example.demo.service.auth.AuthService;
import com.example.demo.service.redis.RedisManager;
import com.example.demo.service.security.JwtUser;
import com.example.demo.util.mapstruct.UserConverter;
import com.example.demo.util.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhangjun
 * @date 2021/10/21  15:00
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisManager redisManager;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> register(UserInfo userInfo) {
        String username = userInfo.getUsername();
        QueryWrapper<ZpUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        ZpUser zpUser = userMapper.selectOne(queryWrapper);
        if (Objects.nonNull(zpUser)) {
            return Result.fail("账户已存在");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = userInfo.getPassword();
        ZpUser user = UserConverter.INSTANCE.toZpUser(userInfo);
        user.setPassword(encoder.encode(password));
        user.setLastPasswordResetDate(new Date());
        try {
            int insert = userMapper.insert(user);
            if (insert > 0) {
                Long id = user.getId();
                QueryWrapper<Role> userRoleQueryWrapper = new QueryWrapper<>();
                userRoleQueryWrapper.eq("role_name", userInfo.getRoles().get(0));
                Role role = roleMapper.selectOne(userRoleQueryWrapper);
                if (Objects.nonNull(role)) {
                    UserRole userRoleNew = new UserRole();
                    userRoleNew.setRoleId(role.getId());
                    userRoleNew.setUserId(id);
                    userRoleMapper.insert(userRoleNew);
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.fail(String.format("%s不存在", userInfo.getRoles().get(0)));
                }
            }
        } catch (Exception e) {
            log.error("AuthServiceImpl[register] error:{}", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(e.getMessage());
        }
        return Result.success();
    }

    @Override
    public Result<LoginUser> login(String username, String password) {
        LoginUser loginUser = new LoginUser();
        try {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            JwtUser jwtUser = (JwtUser) userDetails;
            Long id = jwtUser.getId();
            String token = jwtTokenUtil.generateToken(userDetails);
            Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
            List<String> roleList = authorities.stream().map(a -> a.toString()).collect(Collectors.toList());
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(jwtUser.getUsername());
            userInfo.setId(id);
            userInfo.setRoles(roleList);
            userInfo.setEmail(jwtUser.getEmail());
            userInfo.setLastPasswordResetDate(jwtUser.getLastPasswordResetDate());
            loginUser.setUserInfo(userInfo);
            loginUser.setToken(token);
        } catch (AuthenticationException e) {
            log.error("AuthServiceImpl[login] no username error:", e);
            return Result.fail("账号或密码错误");
        } catch (Exception e) {
            log.error("AuthServiceImpl[login] error:{}", e);
            return Result.fail("登录失败，请稍后再试");
        }
        return Result.success(loginUser);
    }

    @Override
    public String refresh(String oldToken) {
        return null;
    }

    @Override
    public Result<Boolean> logout(String username) {
//        SecurityContextHolder.clearContext();
        return Result.success(redisManager.deleteCache(username));
    }
}
