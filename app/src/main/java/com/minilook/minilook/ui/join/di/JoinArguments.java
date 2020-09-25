package com.minilook.minilook.ui.join.di;

import com.minilook.minilook.data.model.member.MemberDataModel;
import com.minilook.minilook.ui.join.JoinPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class JoinArguments {
    private final JoinPresenter.View view;
    private final MemberDataModel userData;
}