package com.minilook.minilook.data.model.shopping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class ShoppingOptionDataModel {
    @Expose @SerializedName("optionNo")
    private int optionNo;                   // 옵션 아아디
    @Expose @SerializedName("cartNo")
    private int shoppingbagNo;              // 장바구니 아이디
    @Expose @SerializedName("colorCode")
    private String colorCode;               // 색상 코드
    @Expose @SerializedName("colorName")
    private String colorName;               // 색상 이름
    @Expose @SerializedName("sizeCode")
    private int sizeCode;                   // 사이즈 코드
    @Expose @SerializedName("sizeName")
    private String sizeName;                // 사이즈 이름
    @Expose @SerializedName("quantity")
    private int quantity;                   // 선택 수량
    @Expose @SerializedName("stockQty")
    private int stock;                      // 재고
    @Expose @SerializedName("extraAmount")
    private int priceAdd;                   // 추가금

    // Control Data
    private int limitQuantity;              // 주문 가능 수량
    private int priceSum;                   // 합산금액 (상품가격 + 추가금)
    private int brandNo;
}
