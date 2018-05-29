package com.tulover.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author teyma
 * @since 21/05/2018
 */
@Entity
@Getter
@Setter
@ToString(exclude = "movements")
@EqualsAndHashCode
public class Account {

    public Account() {
        this.movements = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Movement> movements;
}
