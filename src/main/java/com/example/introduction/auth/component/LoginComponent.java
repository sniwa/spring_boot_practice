package com.example.introduction.auth.component;

import com.example.introduction.form.LoginForm;
import com.example.introduction.gen.entity.User;
import com.example.introduction.gen.entity.UserExample;
import com.example.introduction.gen.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginComponent {
    private UserMapper userMapper;

    public LoginComponent(@Autowired UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public boolean auth(LoginForm loginForm) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andMailAddressEqualTo(loginForm.getEmail())
                .andPasswordEqualTo(loginForm.getPassword());
        List<User> userList =
                userMapper.selectByExample(userExample);
        return userList.size() > 0;
    }
}
