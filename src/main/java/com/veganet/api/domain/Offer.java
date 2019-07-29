package com.veganet.api.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Offer.
 */
@Entity
@Table(name = "offer")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "data")
    private Integer data;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "maxoffer")
    private Integer maxoffer;

    @Column(name = "priceoffer")
    private Double priceoffer;

    @ManyToOne
    @JsonIgnoreProperties("offers")
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Offer title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getData() {
        return data;
    }

    public Offer data(Integer data) {
        this.data = data;
        return this;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public Integer getDiscount() {
        return discount;
    }

    public Offer discount(Integer discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getMaxoffer() {
        return maxoffer;
    }

    public Offer maxoffer(Integer maxoffer) {
        this.maxoffer = maxoffer;
        return this;
    }

    public void setMaxoffer(Integer maxoffer) {
        this.maxoffer = maxoffer;
    }

    public Double getPriceoffer() {
        return priceoffer;
    }

    public Offer priceoffer(Double priceoffer) {
        this.priceoffer = priceoffer;
        return this;
    }

    public void setPriceoffer(Double priceoffer) {
        this.priceoffer = priceoffer;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Offer transaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offer)) {
            return false;
        }
        return id != null && id.equals(((Offer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", data=" + getData() +
            ", discount=" + getDiscount() +
            ", maxoffer=" + getMaxoffer() +
            ", priceoffer=" + getPriceoffer() +
            "}";
    }
}
