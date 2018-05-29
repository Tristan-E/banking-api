package com.tulover.mapper;

import com.tulover.dto.MovementDTO;
import com.tulover.persistence.model.Movement;
import org.mapstruct.Mapper;
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
