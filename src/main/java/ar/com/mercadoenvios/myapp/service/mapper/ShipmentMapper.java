package ar.com.mercadoenvios.myapp.service.mapper;


import ar.com.mercadoenvios.myapp.domain.*;
import ar.com.mercadoenvios.myapp.service.dto.ShipmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shipment} and its DTO {@link ShipmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShipmentMapper extends EntityMapper<ShipmentDTO, Shipment> {


    @Mapping(target = "checkpoints", ignore = true)
    @Mapping(target = "removeCheckpoint", ignore = true)
    Shipment toEntity(ShipmentDTO shipmentDTO);

    default Shipment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shipment shipment = new Shipment();
        shipment.setId(id);
        return shipment;
    }
}
