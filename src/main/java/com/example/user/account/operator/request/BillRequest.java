package com.example.user.account.operator.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class BillRequest implements Serializable {
    private String barCode;
    private String description;
    private BigDecimal value;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillRequest that = (BillRequest) o;
        return Objects.equals(barCode, that.barCode) && Objects.equals(description, that.description) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barCode, description, value);
    }
}
