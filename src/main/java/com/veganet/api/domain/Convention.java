package com.veganet.api.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Convention.
 */
@Entity
@Table(name = "convention")
public class Convention implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titleconvention")
    private String titleconvention;

    @Column(name = "amountconvention")
    private Double amountconvention;

    @Column(name = "description")
    private String description;

    @Column(name = "enddate")
    private LocalDate enddate;

    @Column(name = "startdate")
    private LocalDate startdate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "totalprice")
    private Double totalprice;

    @ManyToOne
    @JsonIgnoreProperties("conventions")
    private Amicale amicale;

    @ManyToOne
    @JsonIgnoreProperties("conventions")
    private Provider provider;

    @OneToMany(mappedBy = "convention")
    private Set<Transaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleconvention() {
        return titleconvention;
    }

    public Convention titleconvention(String titleconvention) {
        this.titleconvention = titleconvention;
        return this;
    }

    public void setTitleconvention(String titleconvention) {
        this.titleconvention = titleconvention;
    }

    public Double getAmountconvention() {
        return amountconvention;
    }

    public Convention amountconvention(Double amountconvention) {
        this.amountconvention = amountconvention;
        return this;
    }

    public void setAmountconvention(Double amountconvention) {
        this.amountconvention = amountconvention;
    }

    public String getDescription() {
        return description;
    }

    public Convention description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public Convention enddate(LocalDate enddate) {
        this.enddate = enddate;
        return this;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public Convention startdate(LocalDate startdate) {
        this.startdate = startdate;
        return this;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public Integer getDuration() {
        return duration;
    }

    public Convention duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public Convention totalprice(Double totalprice) {
        this.totalprice = totalprice;
        return this;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public Amicale getAmicale() {
        return amicale;
    }

    public Convention amicale(Amicale amicale) {
        this.amicale = amicale;
        return this;
    }

    public void setAmicale(Amicale amicale) {
        this.amicale = amicale;
    }

    public Provider getProvider() {
        return provider;
    }

    public Convention provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Convention transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public Convention addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setConvention(this);
        return this;
    }

    public Convention removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setConvention(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Convention)) {
            return false;
        }
        return id != null && id.equals(((Convention) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Convention{" +
            "id=" + getId() +
            ", titleconvention='" + getTitleconvention() + "'" +
            ", amountconvention=" + getAmountconvention() +
            ", description='" + getDescription() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", duration=" + getDuration() +
            ", totalprice=" + getTotalprice() +
            "}";
    }
}
