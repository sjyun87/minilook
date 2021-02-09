package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum GenderCode {
    NONE("", ""),
    MALE("boy", "남아"),
    FEMALE("girl", "여아");

    private final String code;
    private final String value;

    public static GenderCode toType(String $code) {
        for (GenderCode gender : GenderCode.values()) {
            if (gender.getCode().equals($code)) {
                return gender;
            }
        }
        return NONE;
    }
}
