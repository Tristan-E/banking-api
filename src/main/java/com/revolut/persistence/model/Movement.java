package com.revolut.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author teyma
 * @since 28/05/18
 */
@Entity
@Getter
@Setter
@ToString(exclude = "account")
@EqualsAndHashCode
@NoArgsConstructor
public class Movement {

    public Movement(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Transaction transaction;

    private BigDecimal amount;

}
