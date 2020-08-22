package com.minilook.minilook.ui.setting.di;

import com.minilook.minilook.ui.setting.SettingPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SettingArguments {
    private final SettingPresenter.View view;
}