package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data public class OrderBrandDataModel implements Serializable {
    @Expose @SerializedName("brandNo")
    private int brand_id;                               // 브랜드 아이디
    @Expose @SerializedName("brandName")
    private String brand_name;                          // 브랜드 이름
    @Expose @SerializedName("logo")
    private String brand_logo;                          // 브랜드 로고
    @Expose @SerializedName("shippingMehtod")
    private int shipping_type_code;                     // 배송타입 코드
    @Expose @SerializedName("shippingMehtodName")
    private String shipping_type_name;                  // 배송타입 이름
    @Expose @SerializedName("shippingFee")
    private int shipping_price;                         // 기본 배송비
    @Expose @SerializedName("shippingLimit")
    private int free_shipping_conditions;               // 무료배송 조건
    @Expose @SerializedName("products")
    private List<OrderProductDataModel> products;       // 상품
    @Expose @SerializedName("goods")
    private List<OrderGoodsDataModel>  goods;           // 굿즈

    // -- Control Data
    private boolean isShippingFree;                     // 무료배송 여부
    private int total_products_price;                   // 브랜드 합산 상품금액
    private int free_shipping_left;                     // 무료배송까지 남은 금액
    private boolean isBillDisplay = false;              // 계산서 노출 여부
}
