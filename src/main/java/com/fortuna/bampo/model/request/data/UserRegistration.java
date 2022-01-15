package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 用户注册数据
 *
 * @author Eva7
 * @since 0.1.5
 */
@Data
public class UserRegistration implements BaseRegistration {
    private String identity;
    private String username;
    private String password;
    private String fullName;
    private String city;
    private String email;
    private Long phoneNumber;
}
