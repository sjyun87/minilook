package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum ReviewSatisfactions {
    NONE("", ""),
    GOOD("good", "좋아요"),
    NORMAL("normal", "보통이에요"),
    BAD("notGood", "별로에요");

    private final String code;
    private final String value;

    public static ReviewSatisfactions toType(String $code) {
        for (ReviewSatisfactions satisfactions : ReviewSatisfactions.values()) {
            if (satisfactions.getCode().equals($code)) {
                return satisfactions;
            }
        }
        return NONE;
    }
}
