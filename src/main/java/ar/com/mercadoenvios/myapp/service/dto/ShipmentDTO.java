package ar.com.mercadoenvios.myapp.service.dto;

import ar.com.mercadoenvios.myapp.domain.Checkpoint;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link ar.com.mercadoenvios.myapp.domain.Shipment} entity.
 */
public class ShipmentDTO implements Serializable {
    private Long id;

    private Set<Checkpoint> checkpoints = new HashSet<>();

    public ShipmentDTO() {
        super();
    }

    public ShipmentDTO(Long id) {
        super();
        this.id = id;
    }

    public Set<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Set<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipmentDTO)) {
            return false;
        }

        return id != null && id.equals(((ShipmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipmentDTO{" +
            "id=" + getId() +
            "}";
    }
}
