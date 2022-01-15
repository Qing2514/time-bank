package com.fortuna.bampo.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 验证响应
 *
 * @author Eva7
 * @since 0.3.8
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class VerificationResponse extends BaseResponse {
}
