package com.minilook.minilook.data.model.order;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data public class OrderCancelDataModel implements Serializable {
    private String orderNo;
    private String orderDate;
    private String receiptId;
    private String brandName;
    private List<OrderGoodsDataModel> goods;
}
