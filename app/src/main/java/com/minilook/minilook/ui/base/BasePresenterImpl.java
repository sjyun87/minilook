package com.minilook.minilook.ui.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BasePresenterImpl {

    private CompositeDisposable disposable = new CompositeDisposable();

    protected void addDisposable(Disposable d) {
        disposable.add(d);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void clearDisposable() {
        disposable.clear();
    }
}