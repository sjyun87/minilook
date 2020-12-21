package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum PointStatus {
    PLUS(1),        // 적립
    MINUS(2),       // 차감
    EXPIRY(3);      // 만료

    private final int value;
}
