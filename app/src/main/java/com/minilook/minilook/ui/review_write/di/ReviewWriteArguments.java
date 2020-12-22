package com.minilook.minilook.ui.review_write.di;

import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.ui.review_write.ReviewWritePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ReviewWriteArguments {
    private final ReviewWritePresenter.View view;
    private final String orderNo;
    private final String orderDate;
    private final OrderProductDataModel data;
}