package com.fortuna.bampo.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

/**
 * 注册响应
 *
 * @author Eva7
 * @since 0.3.6
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class RegistrationResponse extends BaseResponse {
    @Builder.Default
    protected Integer status = HttpStatus.OK.value();
}
