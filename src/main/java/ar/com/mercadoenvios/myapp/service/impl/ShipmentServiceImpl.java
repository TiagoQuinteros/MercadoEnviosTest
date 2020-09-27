package ar.com.mercadoenvios.myapp.service.impl;

import ar.com.mercadoenvios.myapp.domain.Shipment;
import ar.com.mercadoenvios.myapp.repository.ShipmentRepository;
import ar.com.mercadoenvios.myapp.service.CheckpointService;
import ar.com.mercadoenvios.myapp.service.ShipmentService;
import ar.com.mercadoenvios.myapp.service.dto.ShipmentDTO;
import ar.com.mercadoenvios.myapp.service.mapper.ShipmentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Shipment}.
 */
@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {
    private final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    private final ShipmentRepository shipmentRepository;

    private final ShipmentMapper shipmentMapper;

    @Autowired
    private CheckpointService checkpointService;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, ShipmentMapper shipmentMapper) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentMapper = shipmentMapper;
    }

    @Override
    public ShipmentDTO save(ShipmentDTO shipmentDTO) {
        log.debug("Request to save Shipment : {}", shipmentDTO);
        Shipment shipment = shipmentMapper.toEntity(shipmentDTO);
        shipment = shipmentRepository.save(shipment);
        return shipmentMapper.toDto(shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShipmentDTO> findAll() {
        log.debug("Request to get all Shipments");
        return shipmentRepository.findAll().stream().map(shipmentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentDTO> findOne(Long id) {
        log.debug("Request to get Shipment : {}", id);
        return shipmentRepository.findById(id).map(shipmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shipment : {}", id);

        checkpointService.findByShipmentId(id).forEach(checkpoint -> checkpointService.delete(checkpoint.getId()));

        shipmentRepository.deleteById(id);
    }
}
