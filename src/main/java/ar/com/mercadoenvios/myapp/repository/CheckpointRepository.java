package ar.com.mercadoenvios.myapp.repository;

import ar.com.mercadoenvios.myapp.domain.Checkpoint;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Checkpoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {
}
