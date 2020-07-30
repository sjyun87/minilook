package com.minilook.minilook.data.model.order;

import lombok.Data;

@Data public class OrderProductDataModel {
    private String color;
    private String size;
    private int count;
    private int price;
    private int price_add;
    private int price_total;
    private int price_final;
}