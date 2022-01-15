package com.fortuna.bampo.model.request;

import com.fortuna.bampo.model.request.data.BaseUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新请求
 *
 * @author Eva7
 * @since 0.2.3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateRequest<E extends BaseUpdate> extends BaseDataRequest<E> {
    private String password;
}
