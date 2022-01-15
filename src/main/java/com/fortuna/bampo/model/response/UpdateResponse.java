package com.fortuna.bampo.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 更新响应
 *
 * @author Eva7
 * @since 0.4.2
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UpdateResponse extends BaseResponse {
}
