package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum CommercialType {
    PROMOTION("promotion"),
    EVENT("event"),
    PRODUCT("product"),
    NO_TYPE("");

    private final String value;

    public static CommercialType toType(String $value) {
        for (CommercialType type : CommercialType.values()) {
            if (type.value.equals($value)) {
                return type;
            }
        }
        return NO_TYPE;
    }
}
