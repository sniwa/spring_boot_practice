package com.example.introduction.auth.component;

import com.example.introduction.form.LoginForm;
import com.example.introduction.form.SignUpForm;
import com.example.introduction.gen.entity.User;
import com.example.introduction.gen.entity.UserExample;
import com.example.introduction.gen.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LoginComponent {
    private UserMapper userMapper;

    public LoginComponent(@Autowired UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User auth(LoginForm loginForm) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andMailAddressEqualTo(loginForm.getEmail())
                .andPasswordEqualTo(loginForm.getPassword());
        List<User> userList =
                userMapper.selectByExample(userExample);

        if (userList.size() == 0) {
            return null;
        }

        return userList.get(0);
    }

    public User register(SignUpForm signUpForm) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andMailAddressEqualTo(signUpForm.getEmail());
        if (userMapper.selectByExample(userExample).size() != 0) {
            return null;
        }

        User user = new User();
        user.setMailAddress(signUpForm.getEmail());
        user.setPassword(signUpForm.getPassword());
        user.setName(signUpForm.getName());
        user.setCreated(new Date());

        int result = userMapper.insert(user);
        if (result == 0) {
            return null;
        }

        return user;
    }
}
