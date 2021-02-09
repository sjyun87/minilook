package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum ReviewSizes {
    NONE("", ""),
    VERY_BIG("veryBig", "많이커요"),
    LITTLE_BIG("littleBig", "조금커요"),
    PERFECTLY("good", "딱맞아요"),
    LITTLE_SMALL("littleSmall", "조금작아요"),
    VERY_SMALL("verySmall", "많이작아요");

    private final String code;
    private final String value;

    public static ReviewSizes toType(String $code) {
        for (ReviewSizes sizes : ReviewSizes.values()) {
            if (sizes.getCode().equals($code)) {
                return sizes;
            }
        }
        return NONE;
    }
}
