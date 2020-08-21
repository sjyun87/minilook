package com.minilook.minilook.ui.scrap.view.product;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ScrapProductPresenter extends LifecycleObserver {

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  void onCreate();

  interface View {

      void setupRecyclerView();

      void refresh();
  }
}
