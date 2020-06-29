package com.minilook.minilook.ui.main.fragment.scrapbook.di;

import com.minilook.minilook.ui.main.fragment.scrapbook.ScrapBookPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ScrapBookArguments {
  private final ScrapBookPresenter.View view;
}