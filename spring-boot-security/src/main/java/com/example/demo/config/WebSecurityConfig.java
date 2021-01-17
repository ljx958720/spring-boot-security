package com.example.demo.config;


import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Date;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


   @Autowired
   private UserService userService;

   //密码加密
   @Resource
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   //将认证后信息存数据库
   @Resource
   private DataSource dataSource;

   @Resource
   private PersistentTokenRepository persistentTokenRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("{noop}admin")//不加密
//                .authorities("ADMIN");
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
    //自定义过滤器链
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        System.out.println("拦截");
        //super.configure(http);
        httpSecurity.authorizeRequests() // 设置哪些页面可以直接访问，哪些需要验证
                .antMatchers("/login.html","/error.html").permitAll() // 放过
//                .antMatchers("/query").hasRole("admin")
////                .antMatchers("/save").hasRole("ROOT")
                .anyRequest().authenticated() // 剩下的所有的地址都是需要在认证状态下才可以访问
                .and()
                .formLogin()//其实就是添加一个过滤器
                .loginPage("/login.html") // 指定指定要的登录页面
                .loginProcessingUrl("/login.do") // 处理认证路径的请求
                .defaultSuccessUrl("/index.html")
                .failureForwardUrl("/error.html")//失败页面
                .and()
                .logout()
                .logoutUrl("/logout")//退出路径
                .logoutSuccessUrl("/login.html")//退出成功跳转页面
                .and()
                .rememberMe()//记住密码功能配置
                .tokenRepository(persistentTokenRepository)//覆盖掉默认的基于内存存储的方式，我们要扩展哪个扩展点就点哪个功能进去查，这里面是点rememberMe功能查的
//                .and().csrf().disable();//跨域攻击关闭
        ;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        // 第一次帮我们创建表结构，这样就不用我们自己建表了，第二次运行时这段注解掉
//         repository.setCreateTableOnStartup(true);
        return repository;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}