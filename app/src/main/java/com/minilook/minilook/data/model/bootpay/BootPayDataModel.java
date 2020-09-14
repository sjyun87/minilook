package com.minilook.minilook.data.model.bootpay;

import java.util.List;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;
import lombok.Data;

@Data public class BootPayDataModel {
    private BootUser bootUser;
    private BootExtra bootExtra;
    private Method method;
    private String orderId;
    private String name;
    private int price;
    private List<BootPayItemDataModel> items;
}