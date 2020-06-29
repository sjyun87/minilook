package com.minilook.minilook.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseFragment extends Fragment {

    private CompositeDisposable disposable = new CompositeDisposable();
    private Unbinder binder;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutID(), container, false);
        binder = ButterKnife.bind(this, view);
        createPresenter();
        return view;
    }

    @Override public void onDestroyView() {
        if (binder != null) binder.unbind();
        clearDisposable();
        super.onDestroyView();
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
