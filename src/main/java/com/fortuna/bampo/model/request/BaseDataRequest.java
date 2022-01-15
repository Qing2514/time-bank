package com.fortuna.bampo.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * HTTP 请求体的抽象泛型类
 *
 * @author Eva7
 * @since 0.1.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDataRequest<E> extends BaseRequest {
    private E data;
}