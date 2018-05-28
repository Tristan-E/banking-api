package com.revolut.persistence.model;

import lombok.*;

import javax.persistence.*;
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
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Movement> movements;
}
