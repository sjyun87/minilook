package com.minilook.minilook.data.model.category;

import lombok.Data;

@Data public class CategoryDataModel {
    private int id;
    private String name;

    // 운영 데이터
    private int position;
    private boolean isSelect;
}
