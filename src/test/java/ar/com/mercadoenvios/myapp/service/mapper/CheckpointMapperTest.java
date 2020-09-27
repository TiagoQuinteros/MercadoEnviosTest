package ar.com.mercadoenvios.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CheckpointMapperTest {

    private CheckpointMapper checkpointMapper;

    @BeforeEach
    public void setUp() {
        checkpointMapper = new CheckpointMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(checkpointMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(checkpointMapper.fromId(null)).isNull();
    }
}
