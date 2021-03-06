package com.fortuna.bampo.model.response;

import com.fortuna.bampo.model.response.data.BaseAbstract;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * ζθ¦εεΊ
 *
 * @author Eva7
 * @since 0.3.10
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AbstractResponse<E extends BaseAbstract> extends BaseDataResponse<E> {
}
