package com.fortuna.bampo.model.request;

import com.fortuna.bampo.model.request.data.BaseRegistration;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注册请求
 *
 * @author Eva7
 * @since 0.1.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegistrationRequest<E extends BaseRegistration> extends BaseDataRequest<E> {
}
