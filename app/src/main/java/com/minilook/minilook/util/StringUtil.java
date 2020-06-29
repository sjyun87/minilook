package com.minilook.minilook.util;

import java.text.DecimalFormat;

public class StringUtil {

    public static String toDigit(int num) {
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(num);
    }
}
