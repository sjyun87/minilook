package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum LookBookModuleType {
    TYPE_IMAGE(0);
    //TYPE_VIDEO(1),

    private int value;

    public static LookBookModuleType toType(int $value) {
        for (LookBookModuleType type : LookBookModuleType.values()) {
            if (type.value == $value) {
                return type;
            }
        }
        return null;
    }
}
