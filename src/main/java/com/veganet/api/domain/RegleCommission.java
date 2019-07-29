package com.veganet.api.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

import com.veganet.api.domain.enumeration.TypeMontant;

import com.veganet.api.domain.enumeration.TypeCommission;

/**
 * A RegleCommission.
 */
@Entity
@Table(name = "regle_commission")
public class RegleCommission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "min_ca")
    private Double minCA;

    @Column(name = "max_ca")
    private Double maxCa;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_montant")
    private TypeMontant typeMontant;

    @Column(name = "montantregle")
    private Double montantregle;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_commission")
    private TypeCommission typeCommission;

    @ManyToOne
    @JsonIgnoreProperties("regleCommissions")
    private Contrat contrat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinCA() {
        return minCA;
    }

    public RegleCommission minCA(Double minCA) {
        this.minCA = minCA;
        return this;
    }

    public void setMinCA(Double minCA) {
        this.minCA = minCA;
    }

    public Double getMaxCa() {
        return maxCa;
    }

    public RegleCommission maxCa(Double maxCa) {
        this.maxCa = maxCa;
        return this;
    }

    public void setMaxCa(Double maxCa) {
        this.maxCa = maxCa;
    }

    public TypeMontant getTypeMontant() {
        return typeMontant;
    }

    public RegleCommission typeMontant(TypeMontant typeMontant) {
        this.typeMontant = typeMontant;
        return this;
    }

    public void setTypeMontant(TypeMontant typeMontant) {
        this.typeMontant = typeMontant;
    }

    public Double getMontantregle() {
        return montantregle;
    }

    public RegleCommission montantregle(Double montantregle) {
        this.montantregle = montantregle;
        return this;
    }

    public void setMontantregle(Double montantregle) {
        this.montantregle = montantregle;
    }

    public TypeCommission getTypeCommission() {
        return typeCommission;
    }

    public RegleCommission typeCommission(TypeCommission typeCommission) {
        this.typeCommission = typeCommission;
        return this;
    }

    public void setTypeCommission(TypeCommission typeCommission) {
        this.typeCommission = typeCommission;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public RegleCommission contrat(Contrat contrat) {
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
        if (!(o instanceof RegleCommission)) {
            return false;
        }
        return id != null && id.equals(((RegleCommission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegleCommission{" +
            "id=" + getId() +
            ", minCA=" + getMinCA() +
            ", maxCa=" + getMaxCa() +
            ", typeMontant='" + getTypeMontant() + "'" +
            ", montantregle=" + getMontantregle() +
            ", typeCommission='" + getTypeCommission() + "'" +
            "}";
    }
}
