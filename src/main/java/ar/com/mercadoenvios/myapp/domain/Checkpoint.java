package ar.com.mercadoenvios.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import ar.com.mercadoenvios.myapp.domain.enumeration.Status;

import ar.com.mercadoenvios.myapp.domain.enumeration.SubStatus;

/**
 * A Checkpoint.
 */
@Entity
@Table(name = "checkpoint")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Checkpoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_status")
    private SubStatus subStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = "checkpoints", allowSetters = true)
    private Shipment shipment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public Checkpoint status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public SubStatus getSubStatus() {
        return subStatus;
    }

    public Checkpoint subStatus(SubStatus subStatus) {
        this.subStatus = subStatus;
        return this;
    }

    public void setSubStatus(SubStatus subStatus) {
        this.subStatus = subStatus;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public Checkpoint shipment(Shipment shipment) {
        this.shipment = shipment;
        return this;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Checkpoint)) {
            return false;
        }
        return id != null && id.equals(((Checkpoint) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Checkpoint{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", subStatus='" + getSubStatus() + "'" +
            "}";
    }
}
