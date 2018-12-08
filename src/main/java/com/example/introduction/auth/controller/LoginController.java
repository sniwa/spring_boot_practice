package com.example.introduction.auth.controller;

import com.example.introduction.auth.component.LoginComponent;
import com.example.introduction.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    private final LoginComponent loginComponent;

    @Autowired
    public LoginController(LoginComponent loginComponent) {
        this.loginComponent = loginComponent;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String display(@ModelAttribute("login") LoginForm loginForm) {
      return "login";
    };

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("login") @Validated LoginForm loginForm, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "login";
        }

        if (!loginComponent.auth(loginForm)) {
            ObjectError error = new FieldError("authorization", "failed", "");
            result.addError(error);
            return "login";
        }

        request.getSession().setAttribute("LOGIN", true);

        return "redirect:";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:";
    }
}
