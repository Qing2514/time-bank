package com.fortuna.bampo.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * HTTP 响应数据抽象类
 *
 * @author Eva7
 * @since 0.3.6
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDataResponse<E> extends BaseResponse {
    protected E data;
}
