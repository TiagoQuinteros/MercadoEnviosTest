package ar.com.mercadoenvios.myapp.service.dto;

import ar.com.mercadoenvios.myapp.domain.enumeration.Status;
import ar.com.mercadoenvios.myapp.domain.enumeration.SubStatus;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link ar.com.mercadoenvios.myapp.domain.Checkpoint} entity.
 */
public class CheckpointDTO implements Serializable {
    private Long id;

    private Status status;

    private SubStatus subStatus;

    @NotNull
    private Long shipmentId;

    public CheckpointDTO(Long id, Status status, SubStatus subStatus, @NotNull Long shipmentId) {
        super();
        this.id = id;
        this.status = status;
        this.subStatus = subStatus;
        this.shipmentId = shipmentId;
    }

    public CheckpointDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public SubStatus getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(SubStatus subStatus) {
        this.subStatus = subStatus;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckpointDTO)) {
            return false;
        }

        return id != null && id.equals(((CheckpointDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckpointDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", subStatus='" + getSubStatus() + "'" +
            ", shipmentId=" + getShipmentId() +
            "}";
    }
}
