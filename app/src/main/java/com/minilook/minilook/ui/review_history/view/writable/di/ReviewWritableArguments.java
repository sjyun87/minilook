package com.minilook.minilook.ui.review_history.view.writable.di;

import com.minilook.minilook.ui.review_history.view.writable.ReviewWritablePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ReviewWritableArguments {
    private final ReviewWritablePresenter.View view;
}