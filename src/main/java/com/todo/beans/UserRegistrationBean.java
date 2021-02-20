package com.todo.beans;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class UserRegistrationBean implements Serializable {
    private static final long serialVersionUID = 413581654624527834L;

    @Size(max = 100)
    private String username;
    @Size(min = 8, max = 20)
    private String password;
    @Size(min = 8, max = 20)
    private String password_conf;
    @Min(1)
    private int company_id;


    @AssertTrue
    public boolean isPasswordConfirmed() {
        if (password == null && password_conf == null) {
            return true;
        }
        return password.equals(password_conf);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_conf() {
        return password_conf;
    }

    public void setPassword_conf(String password_conf) {
        this.password_conf = password_conf;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return company_id
     */
    public int getCompany_id() {
        return company_id;
    }

    /**
     * @param company_id セットする company_id
     */
    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }
}