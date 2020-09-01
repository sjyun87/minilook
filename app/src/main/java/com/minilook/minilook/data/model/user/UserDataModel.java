package com.minilook.minilook.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

@Data public class UserDataModel implements Serializable {
    @Expose @SerializedName("memberNo")
    private int user_id;
    @Expose @SerializedName("snsAccount")
    private String sns_id;
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
    @Expose @SerializedName("isAgreeCommercial")
    private boolean isCommercialInfo;
    @Expose @SerializedName("ci")
    private String ci;
}
