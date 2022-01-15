package com.fortuna.bampo.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 搜索响应
 *
 * @author Eva7
 * @since 0.3.9
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SearchResponse extends BaseDataResponse<List<String>> {
}
