package com.example.introduction.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class LoginForm implements Serializable {
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
