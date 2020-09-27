package ar.com.mercadoenvios.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.mercadoenvios.myapp.web.rest.TestUtil;

public class CheckpointDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckpointDTO.class);
        CheckpointDTO checkpointDTO1 = new CheckpointDTO();
        checkpointDTO1.setId(1L);
        CheckpointDTO checkpointDTO2 = new CheckpointDTO();
        assertThat(checkpointDTO1).isNotEqualTo(checkpointDTO2);
        checkpointDTO2.setId(checkpointDTO1.getId());
        assertThat(checkpointDTO1).isEqualTo(checkpointDTO2);
        checkpointDTO2.setId(2L);
        assertThat(checkpointDTO1).isNotEqualTo(checkpointDTO2);
        checkpointDTO1.setId(null);
        assertThat(checkpointDTO1).isNotEqualTo(checkpointDTO2);
    }
}
