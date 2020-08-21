package com.minilook.minilook.ui.review_write.di;

import com.minilook.minilook.ui.review_write.ReviewWritePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ReviewWriteArguments {
    private final ReviewWritePresenter.View view;
}