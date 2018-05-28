package com.revolut.dto;

import com.revolut.persistence.model.Account;
import com.revolut.persistence.model.Movement;
import com.revolut.persistence.model.TransactionMethod;
import com.revolut.persistence.model.TransactionStatus;
import lombok.*;

import javax.persistence.*;
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
