package com.minilook.minilook.data.model.lookbook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class LookBookModuleDataModel {
    @Expose @SerializedName("lookbookNo")
    private int lookbook_no;
    @Expose @SerializedName("label")
    private String label;
    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("description")
    private String desc;
    @Expose @SerializedName("style_tag_description")
    private String tag;
    @Expose @SerializedName("productsTitle")
    private String simple_info;
    @Expose @SerializedName("main_image")
    private String background_url;
    @Expose @SerializedName("detailImages")
    private List<String> styles;
    @Expose @SerializedName("products")
    private List<ProductDataModel> products;

    // -- Controller
    private int type;
}
