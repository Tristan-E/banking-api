package com.revolut.mapper;

import com.revolut.dto.MovementDTO;
import com.revolut.dto.TransactionDTO;
import com.revolut.persistence.model.Account;
import com.revolut.persistence.model.Movement;
import com.revolut.persistence.model.Transaction;
import com.revolut.persistence.model.TransactionStatus;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author teyma
 * @since 28/05/18
 */
public class MovementMapperTest {
    @Test
    public void shouldMapMovementToMovementDTO() {
        //given
        Movement movement = new Movement();
        movement.setId(55L);
        movement.setAmount(new BigDecimal(999));

        //when
        MovementDTO movementDTO = MovementMapper.INSTANCE.movementToMovementDTO(movement);

        //then
        defaultAssertions(movement, movementDTO);
    }

    @Test
    public void shouldMapMovementDTOtoMovement() {
        //given
        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setAmount(new BigDecimal(1234));
        movementDTO.setId(44L);

        //when
        Movement movement = MovementMapper.INSTANCE.movementDTOToMovement(movementDTO);

        //then
        defaultAssertions(movement, movementDTO);
    }

    private void defaultAssertions(Movement movement, MovementDTO movementDTO) {
        Assert.assertEquals(movement.getAmount(), movementDTO.getAmount());
        Assert.assertEquals(movement.getId(), movementDTO.getId());
    }
}
