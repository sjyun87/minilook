package com.minilook.minilook.data.model.brand;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.common.StyleDataModel;
import java.util.List;
import lombok.Data;

@Data public class BrandInfoDataModel {
    @Expose @SerializedName("workingTime")
    private String cs_time;
    @Expose @SerializedName("tel")
    private String cs_tel;
    @Expose @SerializedName("sns")
    private String cs_sns;
    @Expose @SerializedName("email")
    private String cs_email;
    @Expose @SerializedName("policy")
    private String guide;
}