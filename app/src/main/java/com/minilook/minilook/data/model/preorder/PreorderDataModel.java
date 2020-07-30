package com.minilook.minilook.data.model.preorder;

import lombok.Data;

@Data public class PreorderDataModel {
    private int flag;
    private int id;
    private String title;
    private String desc;
    private String brand;
    private String url_thumb;
    private String date_start;
    private String date_end;
}
