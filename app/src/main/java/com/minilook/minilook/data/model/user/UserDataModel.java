package com.minilook.minilook.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

@Data public class UserDataModel implements Serializable {
    @Expose @SerializedName("deviceId")
    private String device_id;
    @Expose @SerializedName("memberNo")
    private String user_id;
    @Expose @SerializedName("email")
    private String email;
    @Expose @SerializedName("name")
    private String name;
    @Expose @SerializedName("birthDate")
    private String birth;
    @Expose @SerializedName("phoneNumber")
    private String phone;
    @Expose @SerializedName("snsTypeCode")
    private String type;
    @Expose @SerializedName("pushToken")
    private String token_push;
    @Expose @SerializedName("isAgreeCommercial")
    private boolean isCommercialNoti;
    @Expose @SerializedName("ci")
    private String ci;
}
