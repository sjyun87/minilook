package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum OptionType {
    PRODUCT_INFO("상품정보", 0),
    GENDER("성별", 1),
    AGE("연령", 1),
    PRICE("가격", 2),
    COLOR("색상", 3),
    STYLE("스타일", 4);

    private String name;
    private int value;

    // TODO 검색 기능 활성화 시 최우선 수정사항
}
