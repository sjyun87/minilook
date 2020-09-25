package com.minilook.minilook.data.model.member;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

@Data public class MemberDataModel implements Serializable {
    @Expose @SerializedName("memberNo")
    private int memberNo;
    @Expose @SerializedName("snsAccount")
    private String snsId;
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
    private int addressNo;
    @Expose @SerializedName("shippingName")
    private String shippingName;
    @Expose @SerializedName("shippingPhone")
    private String shippingPhone;
    @Expose @SerializedName("zipcode")
    private String shippingZipcode;
    @Expose @SerializedName("shippingAddress1")
    private String shippingAddress;
    @Expose @SerializedName("shippingAddress2")
    private String shippingAddressDetail;
}
