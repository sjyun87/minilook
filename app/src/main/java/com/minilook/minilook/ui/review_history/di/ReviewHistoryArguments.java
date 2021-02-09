package com.minilook.minilook.ui.review_history.di;

import com.minilook.minilook.ui.review_history.ReviewHistoryPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ReviewHistoryArguments {
    private final ReviewHistoryPresenter.View view;
}