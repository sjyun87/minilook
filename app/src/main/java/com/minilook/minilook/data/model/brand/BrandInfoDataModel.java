package com.minilook.minilook.data.model.brand;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class BrandInfoDataModel {
    @Expose @SerializedName("workingTime")
    private String csTime;
    @Expose @SerializedName("tel")
    private String csTel;
    @Expose @SerializedName("sns")
    private String csSNS;
    @Expose @SerializedName("email")
    private String csEmail;
    @Expose @SerializedName("policy")
    private String guide;
}