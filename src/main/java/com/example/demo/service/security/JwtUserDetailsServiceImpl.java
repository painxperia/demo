package com.example.demo.service.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.domain.UserInfo;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.pojo.Role;
import com.example.demo.pojo.UserRole;
import com.example.demo.pojo.ZpUser;
import com.example.demo.service.redis.RedisManager;
import com.example.demo.util.mapstruct.UserConverter;
import com.example.demo.util.security.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangjun
 * @date 2021/10/20  14:41
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisManager redisManager;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<ZpUser> queryWrapper = new QueryWrapper<ZpUser>();
        queryWrapper.eq("username", s);
        ZpUser zpUser = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(zpUser)) {
            throw new UsernameNotFoundException(String.format("No user with name %s", s));
        } else {
            String username = String.format("%s-user", zpUser.getUsername());
            JwtUser jwtUser = redisManager.getCache(username);
            if (Objects.isNull(jwtUser)) {
                QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
                userRoleQueryWrapper.eq("user_id", zpUser.getId());
                UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);
                Role role = roleMapper.selectById(userRole.getRoleId());
                List<String> list = new ArrayList<>();
                list.add("ROLE_" + role.getRoleName());
                UserInfo userInfo = UserConverter.INSTANCE.toUser(zpUser);
                userInfo.setRoles(list);
                jwtUser = JwtUserFactory.create(userInfo);
                redisManager.setCacheDuration(username, jwtUser, Duration.ofMinutes(30));
            }
            return jwtUser;
        }
    }
}
