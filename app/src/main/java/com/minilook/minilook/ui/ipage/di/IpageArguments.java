package com.minilook.minilook.ui.ipage.di;

import com.minilook.minilook.ui.ipage.IpagePresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class IpageArguments {
    private final IpagePresenter.View view;
}