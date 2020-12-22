package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum ReviewSizes {
    VERY_BIG(1, "많이커요"),
    LITTLE_BIG(2, "조금커요"),
    PERFECTLY(3, "딱맞아요"),
    LITTLE_SMALL(4, "조금작아요"),
    VERY_SMALL(5, "많이작아요");

    private final int code;
    private final String value;
}
