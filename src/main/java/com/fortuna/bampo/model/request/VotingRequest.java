package com.fortuna.bampo.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 投票请求
 *
 * @author lhx
 * @since 0.2.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VotingRequest extends BaseRequest {
    String id;
    String username;
}
