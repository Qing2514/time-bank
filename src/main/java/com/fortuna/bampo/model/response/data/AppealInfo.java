package com.fortuna.bampo.model.response.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 申诉信息
 *
 * @author Eva7
 * @since 0.3.1
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AppealInfo extends AppealAbstract implements BaseInfo {
    private Integer compensation;
    @NotNull
    private List<String> passedVerifiers;
    @NotNull
    private List<String> rejectedVerifiers;
}
