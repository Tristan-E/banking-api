package com.tulover.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author
 * @since 28/05/18
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class MovementDTO {

    private long id;

    private BigDecimal amount;
}
