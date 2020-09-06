package com.minilook.minilook.data.model.search;

import lombok.Data;

@Data public class OptionMenuDataModel {
    private String name;
    private int value;
    private int position;
    private boolean isSelected;
}
