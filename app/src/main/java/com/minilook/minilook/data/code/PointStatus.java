package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum PointStatus {
    PLUS(1),
    MINUS(2),
    EXPIRY(3);

    private int value;
}
