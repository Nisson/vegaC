package com.veganet.api.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

import com.veganet.api.domain.enumeration.TypePeriode;

/**
 * A CAEasylink.
 */
@Entity
@Table(name = "ca_easylink")
public class CAEasylink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gain")
    private Double gain;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_periode")
    private TypePeriode typePeriode;

    @ManyToOne
    @JsonIgnoreProperties("cAEasylinks")
    private Contrat contrat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGain() {
        return gain;
    }

    public CAEasylink gain(Double gain) {
        this.gain = gain;
        return this;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }

    public TypePeriode getTypePeriode() {
        return typePeriode;
    }

    public CAEasylink typePeriode(TypePeriode typePeriode) {
        this.typePeriode = typePeriode;
        return this;
    }

    public void setTypePeriode(TypePeriode typePeriode) {
        this.typePeriode = typePeriode;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public CAEasylink contrat(Contrat contrat) {
        this.contrat = contrat;
        return this;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CAEasylink)) {
            return false;
        }
        return id != null && id.equals(((CAEasylink) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CAEasylink{" +
            "id=" + getId() +
            ", gain=" + getGain() +
            ", typePeriode='" + getTypePeriode() + "'" +
            "}";
    }
}
