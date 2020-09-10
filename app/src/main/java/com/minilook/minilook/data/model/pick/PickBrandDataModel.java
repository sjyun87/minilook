package com.minilook.minilook.data.model.pick;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data public class PickBrandDataModel implements Serializable {
    @Expose @SerializedName("brandNo")
    private int brand_id;                               // 브랜드 아이디
    @Expose @SerializedName("brandName")
    private String brand_name;                          // 브랜드 이름
    @Expose @SerializedName("logo")
    private String brand_logo;                          // 브랜드 로고
    @Expose @SerializedName("shippingType")
    private int shipping_type_code;                     // 배송타입 코드
    @Expose @SerializedName("basicShippingFee")
    private int shipping_price;                         // 기본 배송비
    @Expose @SerializedName("conditinShippingFee")
    private int condition_shipping_price;               // 조건부 기본 배송비
    @Expose @SerializedName("freeShippingCondition")
    private int condition_free_shipping;                // 무료배송 조건
    @Expose @SerializedName("products")
    private List<PickProductDataModel> products;        // 상품

    // -- Control Data
    private boolean isFreeShipping;                     // 무료배송 여부
    private int final_shipping_price;                   // 최종 배송비
    private int total_products_price;                   // 브랜드 합산 상품금액
    private int total_option_count;                     // 총 옵션 수
    private int total_selected_product;
    private boolean isBillVisible = false;              // 계산서 노출 여부
}
