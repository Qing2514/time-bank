package com.fortuna.bampo.model.request;

import com.fortuna.bampo.model.request.data.UserLogin;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录请求
 *
 * @author Eva7
 * @since 0.1.2
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class UserLoginRequest extends BaseDataRequest<UserLogin> {
    private boolean remember = false;
}
