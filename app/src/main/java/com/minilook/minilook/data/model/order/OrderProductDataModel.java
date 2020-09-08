package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data public class OrderProductDataModel implements Serializable {
    @Expose @SerializedName("stockCode")
    private int display_code;                   // 진열상태 코드
    @Expose @SerializedName("productNo")
    private int product_id;                     // 상품 아이디
    @Expose @SerializedName("productName")
    private String product_name;                // 상품 이름
    @Expose @SerializedName("image")
    private String thumb_url;                   // 상품 이미지
    @Expose @SerializedName("isdiscount")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isDiscount;                 // 할인여부
    @Expose @SerializedName("discountPercent")
    private int discount_percent;               // 할인율
    @Expose @SerializedName("price")
    private int price;                          // 가격
    @Expose @SerializedName("maxQuantity")
    private int quantity_limit;                 // 한번에 주문 가능한 수량
    @Expose @SerializedName("options")
    private List<OrderOptionDataModel> options; // 옵션

    // Control Data
    private boolean isSelected = true;          // 선택여부
}
