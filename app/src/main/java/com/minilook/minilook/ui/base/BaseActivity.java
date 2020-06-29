package com.minilook.minilook.ui.base;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

    private CompositeDisposable disposable = new CompositeDisposable();
    private Unbinder binder;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        binder = ButterKnife.bind(this);
        createPresenter();
    }

    @Override protected void onDestroy() {
        if (binder != null) binder.unbind();
        clearDisposable();
        super.onDestroy();
    }

    protected abstract int getLayoutID();

    protected abstract void createPresenter();

    protected void addDisposable(Disposable d) {
        disposable.add(d);
    }

    protected void clearDisposable() {
        disposable.clear();
    }

    protected CompositeDisposable getDisposable() {
        return disposable;
    }
}
