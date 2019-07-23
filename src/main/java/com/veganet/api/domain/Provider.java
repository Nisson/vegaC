package com.veganet.api.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Provider.
 */
@Entity
@Table(name = "provider")
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adress")
    private String adress;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "logo")
    private String logo;

    @OneToMany(mappedBy = "provider")
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

    public Provider adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public Provider email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public Provider firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Provider lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogo() {
        return logo;
    }

    public Provider logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<Convention> getConventions() {
        return conventions;
    }

    public Provider conventions(Set<Convention> conventions) {
        this.conventions = conventions;
        return this;
    }

    public Provider addConvention(Convention convention) {
        this.conventions.add(convention);
        convention.setProvider(this);
        return this;
    }

    public Provider removeConvention(Convention convention) {
        this.conventions.remove(convention);
        convention.setProvider(null);
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
        if (!(o instanceof Provider)) {
            return false;
        }
        return id != null && id.equals(((Provider) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Provider{" +
            "id=" + getId() +
            ", adress='" + getAdress() + "'" +
            ", email='" + getEmail() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", logo='" + getLogo() + "'" +
            "}";
    }
}
