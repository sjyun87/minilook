package com.minilook.minilook.data.model;

import lombok.Data;

@Data public class StoreDataModel {
    private int id;
    private String name_ko;
    private String name_en;
    private String address;
    private String rep_name_ko;
    private String rep_name_en;
    private String rep_tel;
    private String cs_date;
    private String cs_time;
    private String cs_tel;
    private String cs_email;
    private String cs_sns;
    private String info_shipping;
    private String info_refund;
}