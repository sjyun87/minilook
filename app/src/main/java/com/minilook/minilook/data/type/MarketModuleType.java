package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum MarketModuleType {
    TYPE_PROMOTION(0),
    TYPE_LIMITED(1),
    TYPE_RECOMMEND_4(2),
    TYPE_RECOMMEND_5(3),
    TYPE_RECOMMEND_6(4),
    TYPE_RECOMMEND_9(5),
    TYPE_NEW(6),
    TYPE_BRAND(7),
    TYPE_CATEGORY(8);

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
