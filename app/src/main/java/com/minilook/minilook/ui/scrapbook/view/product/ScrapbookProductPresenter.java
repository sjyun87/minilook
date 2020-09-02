package com.minilook.minilook.ui.scrapbook.view.product;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ScrapbookProductPresenter extends LifecycleObserver {

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  void onCreate();

  interface View {

      void setupRecyclerView();

      void refresh(int start, int end);
  }
}
