package com.minilook.minilook.ui.preorder_detail.adapter;

import com.minilook.minilook.ui.preorder_detail.PreorderDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderDetailArguments {
    private final PreorderDetailPresenter.View view;
}