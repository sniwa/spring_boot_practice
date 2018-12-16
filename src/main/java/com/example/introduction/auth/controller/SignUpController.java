package com.example.introduction.auth.controller;

import com.example.introduction.auth.component.LoginComponent;
import com.example.introduction.form.SignUpForm;
import com.example.introduction.gen.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignUpController {

    private final LoginComponent loginComponent;

    @Autowired
    public SignUpController(LoginComponent loginComponent) {
        this.loginComponent = loginComponent;
    }

    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    public String display(@ModelAttribute("signUp") SignUpForm signUpForm) {
        return "sign_up";
    }

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public String register(@ModelAttribute("signUp")SignUpForm signUpForm, HttpServletRequest request) {
        User user = loginComponent.register(signUpForm);
        if (user == null) {
            // request.getSession().invalidate();
            return "sign_up";
        }

        return "redirect: /";
    }
}
