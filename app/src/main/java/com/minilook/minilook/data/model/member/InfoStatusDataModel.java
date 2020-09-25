package com.minilook.minilook.data.model.member;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class InfoStatusDataModel {
    @Expose @SerializedName("isMarketingAgree")
    private boolean isMarketingInfo;
    @Expose @SerializedName("isInfoAgree")
    private boolean isOrderInfo;
}
