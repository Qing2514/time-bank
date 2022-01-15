package com.fortuna.bampo.model.response.data;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

import static com.fortuna.bampo.config.properties.ValidationProperties.*;

/**
 * 交易摘要
 *
 * @author Ttanjo, Eva7
 * @since 0.3.4
 */
@Data
@Builder
public class TransactionInfo {

    @NotNull
    @Length(min = BASE64_ENCODED_UUID_LENGTH, max = BASE64_ENCODED_UUID_LENGTH)
    protected String id;
    @NotNull
    @Pattern(regexp = NAME_REGEX)
    @Length(min = NAME_LENGTH_LOWER, max = NAME_LENGTH_UPPER)
    protected String payee;
    @NotNull
    @Pattern(regexp = NAME_REGEX)
    @Length(min = NAME_LENGTH_LOWER, max = NAME_LENGTH_UPPER)
    protected String payer;
    protected Integer amount;
    protected LocalDateTime dateBilled;
    protected Boolean isSuccess;
}
