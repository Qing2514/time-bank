package com.fortuna.bampo.model.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * HTTP 响应抽象类
 *
 * @author Eva7
 * @since 0.3.6
 */
@Data
@SuperBuilder
public abstract class BaseResponse {
    protected String timestamp;
    protected Integer status;
    protected String error;
    protected String trace;
    protected String message;
    protected String path;
}
