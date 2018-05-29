package com.tulover.dto;

import com.tulover.persistence.model.TransactionMethod;
import com.tulover.persistence.model.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author teyma
 * @since 28/05/2018
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TransactionDTO {

    private long id;

    private BigDecimal amount;

    private long sourceAccountId;

    private long destinationAccountId;

    private ZonedDateTime date;

    private TransactionStatus status;

    private TransactionMethod method;

    private List<MovementDTO> movements;
}
