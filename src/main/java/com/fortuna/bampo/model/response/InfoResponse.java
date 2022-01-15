package com.fortuna.bampo.model.response;

import com.fortuna.bampo.model.response.data.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 详情响应
 *
 * @author Eva7
 * @since 0.3.9
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class InfoResponse<E extends BaseInfo> extends BaseDataResponse<E> {
}
