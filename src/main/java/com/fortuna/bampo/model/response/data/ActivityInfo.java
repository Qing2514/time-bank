package com.fortuna.bampo.model.response.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ActivityInfo
 *
 * @author Eva7
 * @since 0.2.6
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ActivityInfo extends ActivityAbstract implements BaseInfo {
    private String address;
    @NotNull
    private List<String> volunteers;
    private LocalDateTime datePublished;
}
