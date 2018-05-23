package com.revolut.persistence.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author teyma
 * @since 19/05/2018
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private BigDecimal amount;

    @OneToOne
    private Account sourceAccount;

    @OneToOne
    private Account destinationAccount;

    private ZonedDateTime date;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    private TransactionMethod method;
}
