package com.minilook.minilook.data.model.category;

import lombok.Data;

@Data public class CategoryDataModel {
    private int id;
    private String name;
    private int position;
    private boolean isSelect;
}
