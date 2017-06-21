package ru.torchikov.jdbc.datasets;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ru.torchikov.jdbc.datasets.base.BaseDataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by sergei on 21.06.17.
 */
@Entity
@Table(name = "addresses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "addresses")
public class AddressDataSet extends BaseDataSet {
    @Column(name = "street")
    private String street;
    @Column(name = "zip")
    private int zip;

    public AddressDataSet() {
    }

    public AddressDataSet(String street, int zip) {
        this.street = street;
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Address: " + street + " street, zip code: " + zip;
    }
}
