package com.minilook.minilook.ui.review.di;

import com.minilook.minilook.ui.review.ReviewPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ReviewArguments {
    private final ReviewPresenter.View view;
}