package com.veganet.api.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activationstatus")
    private String activationstatus;

    @Column(name = "address")
    private String address;

    @Column(name = "amountsubstraction")
    private Double amountsubstraction;

    @Column(name = "startdate")
    private LocalDate startdate;

    @Column(name = "enddate")
    private LocalDate enddate;

    @Column(name = "totalamount")
    private Double totalamount;

    @Column(name = "totalpaid")
    private Double totalpaid;

    @Column(name = "advancedamount")
    private Double advancedamount;

    @OneToMany(mappedBy = "transaction")
    private Set<Offer> offers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private Convention convention;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivationstatus() {
        return activationstatus;
    }

    public Transaction activationstatus(String activationstatus) {
        this.activationstatus = activationstatus;
        return this;
    }

    public void setActivationstatus(String activationstatus) {
        this.activationstatus = activationstatus;
    }

    public String getAddress() {
        return address;
    }

    public Transaction address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAmountsubstraction() {
        return amountsubstraction;
    }

    public Transaction amountsubstraction(Double amountsubstraction) {
        this.amountsubstraction = amountsubstraction;
        return this;
    }

    public void setAmountsubstraction(Double amountsubstraction) {
        this.amountsubstraction = amountsubstraction;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public Transaction startdate(LocalDate startdate) {
        this.startdate = startdate;
        return this;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public Transaction enddate(LocalDate enddate) {
        this.enddate = enddate;
        return this;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public Double getTotalamount() {
        return totalamount;
    }

    public Transaction totalamount(Double totalamount) {
        this.totalamount = totalamount;
        return this;
    }

    public void setTotalamount(Double totalamount) {
        this.totalamount = totalamount;
    }

    public Double getTotalpaid() {
        return totalpaid;
    }

    public Transaction totalpaid(Double totalpaid) {
        this.totalpaid = totalpaid;
        return this;
    }

    public void setTotalpaid(Double totalpaid) {
        this.totalpaid = totalpaid;
    }

    public Double getAdvancedamount() {
        return advancedamount;
    }

    public Transaction advancedamount(Double advancedamount) {
        this.advancedamount = advancedamount;
        return this;
    }

    public void setAdvancedamount(Double advancedamount) {
        this.advancedamount = advancedamount;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public Transaction offers(Set<Offer> offers) {
        this.offers = offers;
        return this;
    }

    public Transaction addOffer(Offer offer) {
        this.offers.add(offer);
        offer.setTransaction(this);
        return this;
    }

    public Transaction removeOffer(Offer offer) {
        this.offers.remove(offer);
        offer.setTransaction(null);
        return this;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Convention getConvention() {
        return convention;
    }

    public Transaction convention(Convention convention) {
        this.convention = convention;
        return this;
    }

    public void setConvention(Convention convention) {
        this.convention = convention;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", activationstatus='" + getActivationstatus() + "'" +
            ", address='" + getAddress() + "'" +
            ", amountsubstraction=" + getAmountsubstraction() +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", totalamount=" + getTotalamount() +
            ", totalpaid=" + getTotalpaid() +
            ", advancedamount=" + getAdvancedamount() +
            "}";
    }
}
