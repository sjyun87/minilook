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
    @Expose @SerializedName("nickname")
    private String nick;
    @Expose @SerializedName("birthDate")
    private String birth;
    @Expose @SerializedName(value = "phoneNumber", alternate = "cellphone")
    private String phone;
    @Expose @SerializedName(value = "snsTypeCode", alternate = "snsType")
    private String type;
    @Expose @SerializedName("isAgreeCommercial")
    private boolean isCommercialInfo;
    @Expose @SerializedName("ci")
    private String ci;
    @Expose @SerializedName("addressNo")
    private int address_id;
    @Expose @SerializedName("shippingName")
    private String shipping_name;
    @Expose @SerializedName("shippingPhone")
    private String shipping_phone;
    @Expose @SerializedName("zipcode")
    private String shipping_zipcode;
    @Expose @SerializedName("shippingAddress1")
    private String shipping_address;
    @Expose @SerializedName("shippingAddress2")
    private String shipping_address_detail;
}
