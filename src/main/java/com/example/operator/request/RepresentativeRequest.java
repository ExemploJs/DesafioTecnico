package com.example.operator.request;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class RepresentativeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private BigDecimal value;

    public RepresentativeRequest() {
    }

    public RepresentativeRequest(final BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
