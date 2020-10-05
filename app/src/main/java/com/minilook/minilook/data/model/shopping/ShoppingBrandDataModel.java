package com.minilook.minilook.data.model.shopping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class ShoppingBrandDataModel {
    @Expose @SerializedName("brandNo")
    private int brandNo;                               // 브랜드 아이디
    @Expose @SerializedName("brandName")
    private String brandName;                          // 브랜드 이름
    @Expose @SerializedName("logo")
    private String brandLogo;                          // 브랜드 로고
    @Expose @SerializedName("shippingType")
    private int shippingType;                           // 배송타입 코드
    @Expose @SerializedName("basicShippingFee")
    private int shippingPrice;                         // 기본 배송비
    @Expose @SerializedName("conditinShippingFee")
    private int conditionShippingPrice;               // 조건부 기본 배송비
    @Expose @SerializedName("freeShippingCondition")
    private int conditionFreeShipping;                // 무료배송 조건
    @Expose @SerializedName("products")
    private List<ShoppingProductDataModel> products;    // 상품

    // -- Optional Data
    private boolean isFreeShipping;                     // 무료배송 여부
    private int finalShippingPrice;                   // 최종 배송비
    private int totalProductsPrice;                   // 브랜드 합산 상품금액
    private int totalOptionCount;                     // 총 옵션 수
    private int totalSelectedProduct;
    private boolean isBillVisible = false;              // 계산서 노출 여부
    private boolean isIsland;                           // 도서산간지역 여부
    private int islandShippingPrice;                  // 도서산간지역 추가배송비
}
