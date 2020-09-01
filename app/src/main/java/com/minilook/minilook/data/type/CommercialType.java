package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum CommercialType {
    PROMOTION("promotion"),
    EVENT("event"),
    PRODUCT("product");

    private String value;

    public static CommercialType toType(String $value) {
        for (CommercialType type : CommercialType.values()) {
            if (type.value.equals($value)) {
                return type;
            }
        }
        return null;
    }
}
