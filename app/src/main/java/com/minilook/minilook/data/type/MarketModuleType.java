package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum MarketModuleType {
    TYPE_PROMOTION(0),
    TYPE_MD_PICK(1),
    TYPE_NEW(2),
    TYPE_BRAND(3);

    private int value;

    public static MarketModuleType toType(int $value) {
        for (MarketModuleType type : MarketModuleType.values()) {
            if (type.value == $value) {
                return type;
            }
        }
        return null;
    }
}
