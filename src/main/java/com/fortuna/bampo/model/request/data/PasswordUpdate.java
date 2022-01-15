package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 密码更新
 *
 * @author Eva7
 * @since 0.2.3
 */
@Data
public class PasswordUpdate implements BaseUpdate {
    private String password;
}
