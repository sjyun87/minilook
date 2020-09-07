package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class GoodsDataModel {
    @Expose @SerializedName("optionNo")
    private int goods_id;                   // 굿즈 아아디
    @Expose @SerializedName("cartNo")
    private int shoppingbag_id;             // 장바구니 아이디
    @Expose @SerializedName("colorCode")
    private String color;                   // 색상 코드
    @Expose @SerializedName("colorName")
    private String color_name;              // 색상 이름
    @Expose @SerializedName("sizeCode")
    private int size;                       // 사이즈 코드
    @Expose @SerializedName("sizeName")
    private String size_name;               // 사이즈 이름
    @Expose @SerializedName("quantity")
    private int selected_quantity;          // 선택 수량
    @Expose @SerializedName("stockQty")
    private int goods_stock;                // 재고
    @Expose @SerializedName("extraAmount")
    private int price_add;                  // 추가금

    // Control Data
    private int order_available_quantity;   // 주문 가능 수량
    private int price;
}
