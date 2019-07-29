package com.veganet.api.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Amicale.
 */
@Entity
@Table(name = "amicale")
public class Amicale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adress")
    private String adress;

    @Column(name = "companyname")
    private String companyname;

    @Column(name = "logo")
    private String logo;

    @OneToMany(mappedBy = "amicale")
    private Set<Convention> conventions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public Amicale adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCompanyname() {
        return companyname;
    }

    public Amicale companyname(String companyname) {
        this.companyname = companyname;
        return this;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getLogo() {
        return logo;
    }

    public Amicale logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<Convention> getConventions() {
        return conventions;
    }

    public Amicale conventions(Set<Convention> conventions) {
        this.conventions = conventions;
        return this;
    }

    public Amicale addConvention(Convention convention) {
        this.conventions.add(convention);
        convention.setAmicale(this);
        return this;
    }

    public Amicale removeConvention(Convention convention) {
        this.conventions.remove(convention);
        convention.setAmicale(null);
        return this;
    }

    public void setConventions(Set<Convention> conventions) {
        this.conventions = conventions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Amicale)) {
            return false;
        }
        return id != null && id.equals(((Amicale) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Amicale{" +
            "id=" + getId() +
            ", adress='" + getAdress() + "'" +
            ", companyname='" + getCompanyname() + "'" +
            ", logo='" + getLogo() + "'" +
            "}";
    }
}
