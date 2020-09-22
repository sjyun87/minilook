package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum PreorderType {
    TYPE_OPEN(0),
    TYPE_COMING(1);

    private int value;

    public static PreorderType toType(int $value) {
        for (PreorderType type : PreorderType.values()) {
            if (type.value == $value) {
                return type;
            }
        }
        return null;
    }
}
