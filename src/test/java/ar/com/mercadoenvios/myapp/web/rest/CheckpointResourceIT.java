package ar.com.mercadoenvios.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.mercadoenvios.myapp.MercadoEnviosTestApp;
import ar.com.mercadoenvios.myapp.domain.Checkpoint;
import ar.com.mercadoenvios.myapp.domain.enumeration.Status;
import ar.com.mercadoenvios.myapp.domain.enumeration.SubStatus;
import ar.com.mercadoenvios.myapp.repository.CheckpointRepository;
import ar.com.mercadoenvios.myapp.service.CheckpointService;
import ar.com.mercadoenvios.myapp.service.ShipmentService;
import ar.com.mercadoenvios.myapp.service.dto.CheckpointDTO;
import ar.com.mercadoenvios.myapp.service.dto.ShipmentDTO;
import ar.com.mercadoenvios.myapp.service.mapper.CheckpointMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CheckpointResource} REST controller.
 */
@SpringBootTest(classes = MercadoEnviosTestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CheckpointResourceIT {
    private static final Status DEFAULT_STATUS = Status.HANDLING;
    private static final Status UPDATED_STATUS = Status.READY_TO_SHIP;

    private static final SubStatus DEFAULT_SUB_STATUS = SubStatus.MANUFACTURING;
    private static final SubStatus UPDATED_SUB_STATUS = SubStatus.READY_TO_PRINT;

    @Autowired
    private CheckpointRepository checkpointRepository;

    @Autowired
    private CheckpointMapper checkpointMapper;

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckpointMockMvc;

    private Checkpoint checkpoint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Checkpoint createEntity(EntityManager em) {
        Checkpoint checkpoint = new Checkpoint().status(DEFAULT_STATUS).subStatus(DEFAULT_SUB_STATUS);
        return checkpoint;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Checkpoint createUpdatedEntity(EntityManager em) {
        Checkpoint checkpoint = new Checkpoint().status(UPDATED_STATUS).subStatus(UPDATED_SUB_STATUS);
        return checkpoint;
    }

    @BeforeEach
    public void initTest() {
        checkpoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckpoint() throws Exception {
        shipmentService.save(new ShipmentDTO(1L));
        int databaseSizeBeforeCreate = checkpointRepository.findAll().size();
        // Create the Checkpoint
        CheckpointDTO checkpointDTO = checkpointMapper.toDto(checkpoint);
        checkpointDTO.setShipmentId(1L);
        restCheckpointMockMvc
            .perform(
                post("/api/checkpoints").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkpointDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Checkpoint in the database
        List<Checkpoint> checkpointList = checkpointRepository.findAll();
        assertThat(checkpointList).hasSize(databaseSizeBeforeCreate + 1);
        Checkpoint testCheckpoint = checkpointList.get(checkpointList.size() - 1);
        assertThat(testCheckpoint.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCheckpoint.getSubStatus()).isEqualTo(DEFAULT_SUB_STATUS);
    }

    @Test
    @Transactional
    public void createCheckpointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkpointRepository.findAll().size();

        // Create the Checkpoint with an existing ID
        checkpoint.setId(1L);
        CheckpointDTO checkpointDTO = checkpointMapper.toDto(checkpoint);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckpointMockMvc
            .perform(
                post("/api/checkpoints").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkpointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Checkpoint in the database
        List<Checkpoint> checkpointList = checkpointRepository.findAll();
        assertThat(checkpointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCheckpoints() throws Exception {
        // Initialize the database
        checkpointRepository.saveAndFlush(checkpoint);

        // Get all the checkpointList
        restCheckpointMockMvc
            .perform(get("/api/checkpoints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkpoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].subStatus").value(hasItem(DEFAULT_SUB_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getCheckpoint() throws Exception {
        // Initialize the database
        checkpointRepository.saveAndFlush(checkpoint);

        // Get the checkpoint
        restCheckpointMockMvc
            .perform(get("/api/checkpoints/{id}", checkpoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkpoint.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.subStatus").value(DEFAULT_SUB_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCheckpoint() throws Exception {
        // Get the checkpoint
        restCheckpointMockMvc.perform(get("/api/checkpoints/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckpoint() throws Exception {
        // Initialize the database
        checkpointRepository.saveAndFlush(checkpoint);

        int databaseSizeBeforeUpdate = checkpointRepository.findAll().size();

        // Update the checkpoint
        Checkpoint updatedCheckpoint = checkpointRepository.findById(checkpoint.getId()).get();
        // Disconnect from session so that the updates on updatedCheckpoint are not directly saved in db
        em.detach(updatedCheckpoint);
        updatedCheckpoint.status(UPDATED_STATUS).subStatus(UPDATED_SUB_STATUS);
        CheckpointDTO checkpointDTO = checkpointMapper.toDto(updatedCheckpoint);

        restCheckpointMockMvc
            .perform(
                put("/api/checkpoints").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkpointDTO))
            )
            .andExpect(status().isOk());

        // Validate the Checkpoint in the database
        List<Checkpoint> checkpointList = checkpointRepository.findAll();
        assertThat(checkpointList).hasSize(databaseSizeBeforeUpdate);
        Checkpoint testCheckpoint = checkpointList.get(checkpointList.size() - 1);
        assertThat(testCheckpoint.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCheckpoint.getSubStatus()).isEqualTo(UPDATED_SUB_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckpoint() throws Exception {
        int databaseSizeBeforeUpdate = checkpointRepository.findAll().size();

        // Create the Checkpoint
        CheckpointDTO checkpointDTO = checkpointMapper.toDto(checkpoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckpointMockMvc
            .perform(
                put("/api/checkpoints").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkpointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Checkpoint in the database
        List<Checkpoint> checkpointList = checkpointRepository.findAll();
        assertThat(checkpointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCheckpoint() throws Exception {
        // Initialize the database
        checkpointRepository.saveAndFlush(checkpoint);

        int databaseSizeBeforeDelete = checkpointRepository.findAll().size();

        // Delete the checkpoint
        restCheckpointMockMvc
            .perform(delete("/api/checkpoints/{id}", checkpoint.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Checkpoint> checkpointList = checkpointRepository.findAll();
        assertThat(checkpointList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
