package com.minilook.minilook.data.model.brand;

import lombok.Data;

@Data public class StoreInfoDataModel {
    private int id;
    private String name;
    private String address;
    private String rep_name;
    private String rep_tel;
    private String cs_date;
    private String cs_time;
    private String cs_tel;
    private String cs_email;
    private String cs_sns;
    private String info_shipping;
}