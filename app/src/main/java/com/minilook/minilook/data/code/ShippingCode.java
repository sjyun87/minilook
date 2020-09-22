package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum ShippingCode {
    FREE(1),            // 무료배송
    CASH(2),            // 착불배송
    PAID(3),            // 유료배송
    CONDITIONAL(4);     // 조건부 무료배송

    private int value;
}
