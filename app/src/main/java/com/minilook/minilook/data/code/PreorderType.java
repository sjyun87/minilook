package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum PreorderType {
    END(0),         // 주문종료
    ING(1),         // 진행중
    WILL(2);        // 진행예정

    private final int value;
}