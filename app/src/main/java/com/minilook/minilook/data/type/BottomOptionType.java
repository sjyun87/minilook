package com.minilook.minilook.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum BottomOptionType {
    PRODUCT_INFO("상품정보", 0),
    GENDER_N_AGE("성별/연령", 1),
    PRICE("가격", 2),
    COLOR("색상", 3),
    STYLE("스타일", 4);

    private String name;
    private int value;
}
