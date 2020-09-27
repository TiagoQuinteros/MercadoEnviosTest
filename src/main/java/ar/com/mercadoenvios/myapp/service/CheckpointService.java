package ar.com.mercadoenvios.myapp.service;

import ar.com.mercadoenvios.myapp.service.dto.CheckpointDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ar.com.mercadoenvios.myapp.domain.Checkpoint}.
 */
public interface CheckpointService {

    /**
     * Save a checkpoint.
     *
     * @param checkpointDTO the entity to save.
     * @return the persisted entity.
     */
    CheckpointDTO save(CheckpointDTO checkpointDTO);

    /**
     * Get all the checkpoints.
     *
     * @return the list of entities.
     */
    List<CheckpointDTO> findAll();


    /**
     * Get the "id" checkpoint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckpointDTO> findOne(Long id);

    /**
     * Delete the "id" checkpoint.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
