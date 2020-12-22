package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum GenderCode {
    MALE(1),
    FEMALE(2);

    private final int code;
}
