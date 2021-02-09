package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum ReviewSizeRatings {
    NONE("", ""),
    VERY_BIG("veryBig", "많이커요"),
    LITTLE_BIG("littleBig", "조금커요"),
    PERFECTLY("good", "딱맞아요"),
    LITTLE_SMALL("littleSmall", "조금작아요"),
    VERY_SMALL("verySmall", "많이작아요");

    private final String code;
    private final String value;

    public static ReviewSizeRatings toType(String $code) {
        for (ReviewSizeRatings sizes : ReviewSizeRatings.values()) {
            if (sizes.getCode().equals($code)) {
                return sizes;
            }
        }
        return NONE;
    }
}
