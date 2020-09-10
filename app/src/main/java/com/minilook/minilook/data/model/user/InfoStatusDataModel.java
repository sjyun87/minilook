package com.minilook.minilook.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import lombok.Data;

@Data public class InfoStatusDataModel {
    @Expose @SerializedName("isMarketingAgree")
    private boolean isMarketingInfo;
    @Expose @SerializedName("isInfoAgree")
    private boolean isOrderInfo;
}
