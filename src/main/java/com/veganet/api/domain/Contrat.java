package com.veganet.api.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.veganet.api.domain.enumeration.TypePeriode;

import com.veganet.api.domain.enumeration.TypeCommission;

import com.veganet.api.domain.enumeration.TypeMontant;

/**
 * A Contrat.
 */
@Entity
@Table(name = "contrat")
public class Contrat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datedeb")
    private LocalDate datedeb;

    @Column(name = "datefin")
    private LocalDate datefin;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_periode")
    private TypePeriode typePeriode;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_commission")
    private TypeCommission typeCommission;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_montant")
    private TypeMontant typeMontant;

    @Column(name = "montant_commission")
    private Double montantCommission;

    @Column(name = "par_palier")
    private Boolean parPalier;

    @OneToOne
    @JoinColumn(unique = true)
    private Convention convention;

    @OneToMany(mappedBy = "contrat")
    private Set<RegleCommission> regleCommissions = new HashSet<>();

    @OneToMany(mappedBy = "contrat")
    private Set<CAEasylink> cAEasylinks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatedeb() {
        return datedeb;
    }

    public Contrat datedeb(LocalDate datedeb) {
        this.datedeb = datedeb;
        return this;
    }

    public void setDatedeb(LocalDate datedeb) {
        this.datedeb = datedeb;
    }

    public LocalDate getDatefin() {
        return datefin;
    }

    public Contrat datefin(LocalDate datefin) {
        this.datefin = datefin;
        return this;
    }

    public void setDatefin(LocalDate datefin) {
        this.datefin = datefin;
    }

    public TypePeriode getTypePeriode() {
        return typePeriode;
    }

    public Contrat typePeriode(TypePeriode typePeriode) {
        this.typePeriode = typePeriode;
        return this;
    }

    public void setTypePeriode(TypePeriode typePeriode) {
        this.typePeriode = typePeriode;
    }

    public TypeCommission getTypeCommission() {
        return typeCommission;
    }

    public Contrat typeCommission(TypeCommission typeCommission) {
        this.typeCommission = typeCommission;
        return this;
    }

    public void setTypeCommission(TypeCommission typeCommission) {
        this.typeCommission = typeCommission;
    }

    public TypeMontant getTypeMontant() {
        return typeMontant;
    }

    public Contrat typeMontant(TypeMontant typeMontant) {
        this.typeMontant = typeMontant;
        return this;
    }

    public void setTypeMontant(TypeMontant typeMontant) {
        this.typeMontant = typeMontant;
    }

    public Double getMontantCommission() {
        return montantCommission;
    }

    public Contrat montantCommission(Double montantCommission) {
        this.montantCommission = montantCommission;
        return this;
    }

    public void setMontantCommission(Double montantCommission) {
        this.montantCommission = montantCommission;
    }

    public Boolean isParPalier() {
        return parPalier;
    }

    public Contrat parPalier(Boolean parPalier) {
        this.parPalier = parPalier;
        return this;
    }

    public void setParPalier(Boolean parPalier) {
        this.parPalier = parPalier;
    }

    public Convention getConvention() {
        return convention;
    }

    public Contrat convention(Convention convention) {
        this.convention = convention;
        return this;
    }

    public void setConvention(Convention convention) {
        this.convention = convention;
    }

    public Set<RegleCommission> getRegleCommissions() {
        return regleCommissions;
    }

    public Contrat regleCommissions(Set<RegleCommission> regleCommissions) {
        this.regleCommissions = regleCommissions;
        return this;
    }

    public Contrat addRegleCommission(RegleCommission regleCommission) {
        this.regleCommissions.add(regleCommission);
        regleCommission.setContrat(this);
        return this;
    }

    public Contrat removeRegleCommission(RegleCommission regleCommission) {
        this.regleCommissions.remove(regleCommission);
        regleCommission.setContrat(null);
        return this;
    }

    public void setRegleCommissions(Set<RegleCommission> regleCommissions) {
        this.regleCommissions = regleCommissions;
    }

    public Set<CAEasylink> getCAEasylinks() {
        return cAEasylinks;
    }

    public Contrat cAEasylinks(Set<CAEasylink> cAEasylinks) {
        this.cAEasylinks = cAEasylinks;
        return this;
    }

    public Contrat addCAEasylink(CAEasylink cAEasylink) {
        this.cAEasylinks.add(cAEasylink);
        cAEasylink.setContrat(this);
        return this;
    }

    public Contrat removeCAEasylink(CAEasylink cAEasylink) {
        this.cAEasylinks.remove(cAEasylink);
        cAEasylink.setContrat(null);
        return this;
    }

    public void setCAEasylinks(Set<CAEasylink> cAEasylinks) {
        this.cAEasylinks = cAEasylinks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contrat)) {
            return false;
        }
        return id != null && id.equals(((Contrat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contrat{" +
            "id=" + getId() +
            ", datedeb='" + getDatedeb() + "'" +
            ", datefin='" + getDatefin() + "'" +
            ", typePeriode='" + getTypePeriode() + "'" +
            ", typeCommission='" + getTypeCommission() + "'" +
            ", typeMontant='" + getTypeMontant() + "'" +
            ", montantCommission=" + getMontantCommission() +
            ", parPalier='" + isParPalier() + "'" +
            "}";
    }
}
