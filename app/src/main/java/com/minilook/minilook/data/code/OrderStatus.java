package com.minilook.minilook.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum OrderStatus {
    ORDER_COMPLETED(2, "주문완료"),
    PREPARE_DELIVERY(3, "배송준비"),
    DELIVERY(4, "배송중"),
    DELIVERY_COMPLETED(5, "배송완료"),
    RETURN_COMPLETED(7, "반품완료"),
    EXCHANGE_COMPLETED(8, "교환완료"),
    CANCEL_COMPLETED(9, "취소완료"),
    RETURN(10, "반품진행"),
    EXCHANGE(11, "교환진행"),
    CANNOT_RETURN(12, "반품불가"),
    CANNOT_EXCHANGE(13, "교환불가"),
    PURCHASE_CONFIRM(14, "구매확정"),
    CANCEL(15, "취소접수"),
    PREORDER(16, "주문제작"),
    ERROR(-1, "");

    private int code;
    private String name;

    public static OrderStatus toStatus(int $value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode() == $value) {
                return status;
            }
        }
        return ERROR;
    }

    public static String toName(int $value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode() == $value) {
                return status.getName();
            }
        }
        return ERROR.getName();
    }
}
