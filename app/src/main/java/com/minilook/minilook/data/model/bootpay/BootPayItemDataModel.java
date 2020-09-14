package com.minilook.minilook.data.model.bootpay;

import lombok.Data;

@Data public class BootPayItemDataModel {
    private String id;
    private String name;
    private int price;
    private int quantity;
    private String category;
}