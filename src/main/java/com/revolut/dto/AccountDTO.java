package com.revolut.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author teyma
 * @since 28/05/18
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class AccountDTO {

    private long id;

    private List<MovementDTO> movements;

    private BigDecimal balance;
}
