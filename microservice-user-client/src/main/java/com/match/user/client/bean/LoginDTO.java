package com.match.user.client.bean;


import javax.validation.constraints.NotNull;

/**
 * @Author zhangchao
 * @Date 2019/5/17 13:45
 * @Version v1.0
 */
public class LoginDTO {

    @NotNull
    private LoginType method;
    @NotNull
    private String phone;
    @NotNull
    private String mark;

    public LoginType getMethod() {
        return method;
    }

    public void setMethod(LoginType method) {
        this.method = method;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
