package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum SearchModuleType {
    TYPE_POPULAR(0),
    TYPE_BRAND(1);

    private int value;

    public static SearchModuleType toType(int $value) {
        for (SearchModuleType type : SearchModuleType.values()) {
            if (type.value == $value) {
                return type;
            }
        }
        return null;
    }
}
