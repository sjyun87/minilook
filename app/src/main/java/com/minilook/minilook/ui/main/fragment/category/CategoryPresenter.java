package com.minilook.minilook.ui.main.fragment.category;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface CategoryPresenter extends LifecycleObserver {

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  void onCreate();

  interface View {

      void setupRecyclerView();

      void refresh();
  }
}
