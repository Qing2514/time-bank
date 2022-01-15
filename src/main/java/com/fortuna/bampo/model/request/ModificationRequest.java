package com.fortuna.bampo.model.request;

import com.fortuna.bampo.model.request.data.BaseModification;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 修改请求
 *
 * @author Ttanjo, CMT
 * @since 0.2.3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModificationRequest<E extends BaseModification> extends BaseDataRequest<E> {
}
