package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum GenderCode {
    NONE(""),
    MALE("boy"),
    FEMALE("girl");

    private final String code;

    public static GenderCode toType(String $code) {
        for (GenderCode gender : GenderCode.values()) {
            if (gender.getCode().equals($code)) {
                return gender;
            }
        }
        return NONE;
    }
}
