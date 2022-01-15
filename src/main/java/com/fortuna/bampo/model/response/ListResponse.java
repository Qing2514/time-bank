package com.fortuna.bampo.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * ListResponse
 *
 * @author Ttanjo
 * @since 0.3.10
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ListResponse extends BaseDataResponse<List<String>> {
}
