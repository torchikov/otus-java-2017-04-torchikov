package ru.torchikov.jdbc.datasets;

import ru.torchikov.jdbc.datasets.base.BaseDataSet;

import javax.persistence.*;

/**
 * Created by sergei on 21.06.17.
 */
@Entity
@Table(name = "phones")
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
