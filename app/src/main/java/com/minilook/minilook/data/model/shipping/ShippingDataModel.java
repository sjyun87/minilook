package com.minilook.minilook.data.model.shipping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

@Data public class ShippingDataModel implements Serializable {
    @Expose @SerializedName(value = "addressNo", alternate = "address_no")
    private int address_id;
    @Expose @SerializedName("name")
    private String name;
    @Expose @SerializedName("phone")
    private String phone;
    @Expose @SerializedName("zipcode")
    private String zipcode;
    @Expose @SerializedName("address1")
    private String address;
    @Expose @SerializedName("address2")
    private String address_detail;
    @Expose @SerializedName("isDefault")
    private boolean isDefault;
}
