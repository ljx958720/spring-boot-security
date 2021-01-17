package com.example.demo.user.service.impl;

import com.example.demo.user.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    /**
     * 自已定义的认证逻辑方法，如果不懂看我写的原理篇
     *
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        //保证权限的集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        //生产中是从数据库拿的
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority("ROLE_ROOT");
        authorities.add(auth);
        UserDetails user = new User(username
                , "$2a$10$YOWyHqvtg.gqrbiSTlYQx.nu2j0psWsrs/JIiuzav7IDX7r93WGIe"
                , true//是否可用
                , true//账号是否过期
                , true//密码过期
                , true//账号被锁琮
                , authorities);
        return user;
    }
}