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
@Table(name = "phones")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "phones")
public class PhoneDataSet extends BaseDataSet {
    @Column(name = "code")
    private int code;
    @Column(name = "number")
    private String number;

    public PhoneDataSet() {
    }

    public PhoneDataSet(int code, String number) {
        this.code = code;
        this.number = number;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone number: " + code + number;
    }
}
