package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum MarketModuleType {
    TYPE_COMMERCIAL(1, "COMMERCIAL"),
    TYPE_CATEGORY(2, "CATEGORY"),
    TYPE_RECOMMEND(3, "RECOMMEND"),
    TYPE_PREORDER(4, "PREORDER"),
    TYPE_TREND(5, "TREND"),
    TYPE_NEW_ARRIVAL(6, "NEW"),
    TYPE_BANNER(7, "BANNER"),
    TYPE_BRAND(8, "BRAND"),
    TYPE_THEME(9, "THEME");

    private int type;
    private String value;

    public static int toModuleType(String $value) {
        for (MarketModuleType moduleType : MarketModuleType.values()) {
            if (moduleType.value.equals($value)) {
                return moduleType.type;
            }
        }
        return -1;
    }
}
