package com.minilook.minilook.data.model.lookbook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class LookBookModuleDataModel {
    @Expose @SerializedName("lookbookNo")
    private int lookbookNo;
    @Expose @SerializedName("label")
    private String label;
    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("description")
    private String desc;
    @Expose @SerializedName("style_tag_description")
    private String tag;
    @Expose @SerializedName("productsTitle")
    private String simpleInfo;
    @Expose @SerializedName("main_image")
    private String backgroundUrl;
    @Expose @SerializedName("detailImages")
    private List<String> styles;
    @Expose @SerializedName("products")
    private List<ProductDataModel> products;

    // -- Optional Data
    private int type;
}
