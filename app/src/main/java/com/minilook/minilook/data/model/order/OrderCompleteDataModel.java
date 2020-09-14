package com.minilook.minilook.data.model.order;

import com.minilook.minilook.data.model.brand.BrandShippingDataModel;
import java.util.List;
import lombok.Data;

@Data public class OrderCompleteDataModel {
    private int user_id;
    private String order_id;
    private int payment_price;
    private int use_point_value;
    private int coupon_id;
    private int use_coupon_value;
    private int total_product_price;
    private int total_discount_price;
    private int total_shipping_price;
    private String receipt_id;
    private String receipt_name;
    private String receipt_phone;
    private int address_id;
    private String zip;
    private String address;
    private String address_detail;
    private String shipping_memo;
    private List<BrandShippingDataModel> brand_shipping;
    private List<OrderCompleteOptionDataModel> complete_option;
    private boolean isDirectOrder;
}
