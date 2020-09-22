package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum DisplayCode {
    DISPLAY(1),
    SOLD_OUT(2),
    STOP_PRODUCT(3),
    STOP_SELLING(4);

    private int value;
}
