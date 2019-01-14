package com.example.demo.entity;

import com.example.demo.util.RegexpUtil;
import com.example.demo.util.TimeUtil;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * @Auther: liuxiangtao90
 * @Date: 2019/1/14 09:11
 * @Description:
 */
public class User {
    @NotNull(message = "姓名不能为空")
    private String name;

    @NotNull(message = "生日不能为空")
    @DateTimeFormat(pattern = TimeUtil.DAY1)
    private LocalDate age;

    @NotNull(message = "请输入电话号码")
    @Pattern(regexp = RegexpUtil.REGEX_MOBILE, message = "电话号码不能为空")
    private String phone;

    @NotNull(message = "请输入邮箱")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getAge() {
        return age;
    }

    public void setAge(LocalDate age) {
        this.age = age;
    }
}
