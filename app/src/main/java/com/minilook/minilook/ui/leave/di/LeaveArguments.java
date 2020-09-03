package com.minilook.minilook.ui.leave.di;

import com.minilook.minilook.ui.leave.LeavePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class LeaveArguments {
    private final LeavePresenter.View view;
}