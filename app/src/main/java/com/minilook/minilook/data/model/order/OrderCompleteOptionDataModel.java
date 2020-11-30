package com.minilook.minilook.data.model.order;

import lombok.Data;

@Data public class OrderCompleteOptionDataModel {
    private int shoppingbag_id;
    private int option_id;
    private int per_payment_price;      // 옵션 1수량 당 결제금액
    private int per_coupon_value;       // 옵션 1수량 당 쿠폰 값
    private int per_point_value;        // 옵션 1수량 당 포인트 값
    private String optionRequestMemo;
}
