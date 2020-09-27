package ar.com.mercadoenvios.myapp.service.mapper;


import ar.com.mercadoenvios.myapp.domain.*;
import ar.com.mercadoenvios.myapp.service.dto.CheckpointDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Checkpoint} and its DTO {@link CheckpointDTO}.
 */
@Mapper(componentModel = "spring", uses = {ShipmentMapper.class})
public interface CheckpointMapper extends EntityMapper<CheckpointDTO, Checkpoint> {

    @Mapping(source = "shipment.id", target = "shipmentId")
    CheckpointDTO toDto(Checkpoint checkpoint);

    @Mapping(source = "shipmentId", target = "shipment")
    Checkpoint toEntity(CheckpointDTO checkpointDTO);

    default Checkpoint fromId(Long id) {
        if (id == null) {
            return null;
        }
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setId(id);
        return checkpoint;
    }
}
