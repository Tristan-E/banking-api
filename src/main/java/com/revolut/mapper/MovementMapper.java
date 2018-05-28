package com.revolut.mapper;

import com.revolut.dto.MovementDTO;
import com.revolut.dto.TransactionDTO;
import com.revolut.persistence.model.Movement;
import com.revolut.persistence.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author teyma
 * @since 28/05/18
 */
@Mapper
public interface MovementMapper {

    MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

    MovementDTO movementToMovementDTO(Movement movement);

    List<MovementDTO> movementsToMovementDTOs(List<Movement> movements);

    Movement movementDTOToMovement(MovementDTO movementDTO);
}
