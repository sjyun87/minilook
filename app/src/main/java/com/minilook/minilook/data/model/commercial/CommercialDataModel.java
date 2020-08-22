package com.minilook.minilook.data.model.commercial;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class CommercialDataModel {

    @Expose @SerializedName("itemNo")
    private int id;
    @Expose @SerializedName("itemType")
    private String type;
    @Expose @SerializedName("itemName")
    private String title;
    @Expose @SerializedName("image")
    private String image_url;
}
