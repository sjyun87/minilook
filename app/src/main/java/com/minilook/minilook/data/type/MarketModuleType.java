package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum MarketModuleType {
    TYPE_COMMERCIAL(1),
    TYPE_LIMITED(2),
    TYPE_RECOMMEND_4(3),
    TYPE_RECOMMEND_5(4),
    TYPE_RECOMMEND_6(5),
    TYPE_RECOMMEND_9(6),
    TYPE_NEW_ARRIVALS(7),
    TYPE_BRAND(8),
    TYPE_FILTER(9);

    private int value;

    public static MarketModuleType toModuleType(int $value) {
        for (MarketModuleType moduleType : MarketModuleType.values()) {
            if (moduleType.value == $value) {
                return moduleType;
            }
        }
        return null;
    }
}
