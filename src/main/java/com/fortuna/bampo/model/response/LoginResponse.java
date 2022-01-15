package com.fortuna.bampo.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 登录响应
 *
 * @author Eva7
 * @since 0.3.7
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends BaseResponse {
    private String accessToken;
    private String refreshToken;
}
