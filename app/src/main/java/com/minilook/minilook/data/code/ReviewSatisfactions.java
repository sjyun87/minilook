package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum ReviewSatisfactions {
    GOOD(1, "좋아요"),
    NORMAL(2, "보통이에요"),
    BAD(3, "별로에요");

    private final int code;
    private final String value;
}
