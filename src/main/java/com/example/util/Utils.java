package com.example.util;

import java.math.BigDecimal;

public class Utils {

    public static String getValueInBRLFormattedCurrency(final BigDecimal value) {
        return "R$" + value.toString().replace(".", ",");
    }

    public static String parseData(final String data) {
        return data.substring(1, data.length() - 1).replaceAll("\\\\", "");
    }
}
