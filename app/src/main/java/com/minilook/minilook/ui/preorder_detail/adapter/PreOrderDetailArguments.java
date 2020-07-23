package com.minilook.minilook.ui.preorder_detail.adapter;

import com.minilook.minilook.ui.preorder_detail.PreOrderDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreOrderDetailArguments {
    private final PreOrderDetailPresenter.View view;
}