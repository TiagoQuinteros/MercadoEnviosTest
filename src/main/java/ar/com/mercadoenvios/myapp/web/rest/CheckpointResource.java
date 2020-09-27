package ar.com.mercadoenvios.myapp.web.rest;

import ar.com.mercadoenvios.myapp.service.CheckpointService;
import ar.com.mercadoenvios.myapp.service.dto.CheckpointDTO;
import ar.com.mercadoenvios.myapp.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link ar.com.mercadoenvios.myapp.domain.Checkpoint}.
 */
@RestController
@RequestMapping("/api")
public class CheckpointResource {
    private final Logger log = LoggerFactory.getLogger(CheckpointResource.class);

    private static final String ENTITY_NAME = "checkpoint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckpointService checkpointService;

    public CheckpointResource(CheckpointService checkpointService) {
        this.checkpointService = checkpointService;
    }

    /**
     * {@code POST  /checkpoints} : Create a new checkpoint.
     *
     * @param checkpointDTO the checkpointDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkpointDTO, or with status {@code 400 (Bad Request)} if the checkpoint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkpoints")
    public ResponseEntity<CheckpointDTO> createCheckpoint(@Valid @RequestBody CheckpointDTO checkpointDTO)
        throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to save Checkpoint : {}", checkpointDTO);
        if (checkpointDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkpoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckpointDTO result = checkpointService.save(checkpointDTO);
        return ResponseEntity
            .created(new URI("/api/checkpoints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checkpoints} : Updates an existing checkpoint.
     *
     * @param checkpointDTO the checkpointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkpointDTO,
     * or with status {@code 400 (Bad Request)} if the checkpointDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkpointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkpoints")
    public ResponseEntity<CheckpointDTO> updateCheckpoint(@RequestBody CheckpointDTO checkpointDTO) throws URISyntaxException {
        log.debug("REST request to update Checkpoint : {}", checkpointDTO);
        if (checkpointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckpointDTO result = checkpointService.save(checkpointDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkpointDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /checkpoints} : get all the checkpoints.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkpoints in body.
     */
    @GetMapping("/checkpoints")
    public List<CheckpointDTO> getAllCheckpoints() {
        log.debug("REST request to get all Checkpoints");
        return checkpointService.findAll();
    }

    /**
     * {@code GET  /checkpoints/:id} : get the "id" checkpoint.
     *
     * @param id the id of the checkpointDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkpointDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkpoints/{id}")
    public ResponseEntity<CheckpointDTO> getCheckpoint(@PathVariable Long id) {
        log.debug("REST request to get Checkpoint : {}", id);
        Optional<CheckpointDTO> checkpointDTO = checkpointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkpointDTO);
    }

    /**
     * {@code DELETE  /checkpoints/:id} : delete the "id" checkpoint.
     *
     * @param id the id of the checkpointDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkpoints/{id}")
    public ResponseEntity<Void> deleteCheckpoint(@PathVariable Long id) {
        log.debug("REST request to delete Checkpoint : {}", id);
        checkpointService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
