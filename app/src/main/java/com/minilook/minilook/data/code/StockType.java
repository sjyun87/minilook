package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum StockType {
    SIZE("size"),
    COLOR("color"),
    EMPTY("");

    private String value;

    public static StockType toType(String $value) {
        for (StockType type : StockType.values()) {
            if (type.value.equals($value)) {
                return type;
            }
        }
        return EMPTY;
    }
}
