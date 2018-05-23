package com.revolut.persistence.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author teyma
 * @since 21/05/2018
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Account {

    public Account() {
        this.currentBalance = BigDecimal.ZERO;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private BigDecimal currentBalance;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Transaction> transactions;
}
