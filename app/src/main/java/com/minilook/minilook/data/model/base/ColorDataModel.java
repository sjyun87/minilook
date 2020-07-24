package com.minilook.minilook.data.model.base;

import java.util.List;
import lombok.Data;

@Data public class ColorDataModel {
    private String name;
    private String color;
    private boolean soldout;
    private List<SizeDataModel> size;
}