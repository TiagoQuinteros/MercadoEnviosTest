package ar.com.mercadoenvios.myapp.service.impl;

import ar.com.mercadoenvios.myapp.domain.Checkpoint;
import ar.com.mercadoenvios.myapp.repository.CheckpointRepository;
import ar.com.mercadoenvios.myapp.service.CheckpointService;
import ar.com.mercadoenvios.myapp.service.dto.CheckpointDTO;
import ar.com.mercadoenvios.myapp.service.mapper.CheckpointMapper;
import ar.com.mercadoenvios.myapp.web.rest.errors.BadRequestAlertException;
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
 * Service Implementation for managing {@link Checkpoint}.
 */
@Service
@Transactional
public class CheckpointServiceImpl implements CheckpointService {
    private final Logger log = LoggerFactory.getLogger(CheckpointServiceImpl.class);

    private final CheckpointRepository checkpointRepository;

    private final CheckpointMapper checkpointMapper;

    public CheckpointServiceImpl(CheckpointRepository checkpointRepository, CheckpointMapper checkpointMapper) {
        this.checkpointRepository = checkpointRepository;
        this.checkpointMapper = checkpointMapper;
    }

    @Override
    public CheckpointDTO save(CheckpointDTO checkpointDTO) throws BadRequestAlertException {
        log.debug("Request to save Checkpoint : {}", checkpointDTO);
        validateSubStatus(checkpointDTO);
        Checkpoint checkpoint = checkpointMapper.toEntity(checkpointDTO);
        checkpoint = checkpointRepository.save(checkpoint);
        return checkpointMapper.toDto(checkpoint);
    }

    private void validateSubStatus(CheckpointDTO checkpoint) throws BadRequestAlertException {
        if (
            !(
                checkpoint.getStatus().getValidSubstatus().contains(checkpoint.getSubStatus()) ||
                (checkpoint.getStatus().canBeTrue() && checkpoint.getSubStatus() == null)
            )
        ) throw new BadRequestAlertException("Invalid sub estatus", "SubStatus", "invalidSubStatus");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CheckpointDTO> findAll() {
        log.debug("Request to get all Checkpoints");
        return checkpointRepository.findAll().stream().map(checkpointMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckpointDTO> findOne(Long id) {
        log.debug("Request to get Checkpoint : {}", id);
        return checkpointRepository.findById(id).map(checkpointMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Checkpoint : {}", id);
        checkpointRepository.deleteById(id);
    }
}
