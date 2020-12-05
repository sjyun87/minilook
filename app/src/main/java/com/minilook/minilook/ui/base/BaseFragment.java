package com.minilook.minilook.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.data.rx.SchedulersFacade;
import com.minilook.minilook.ui.base.listener.OnLoginListener;
import com.minilook.minilook.ui.base.listener.OnScrapListener;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseFragment extends Fragment implements OnLoginListener, OnScrapListener {

    private final CompositeDisposable disposable = new CompositeDisposable();
    protected ResourcesProvider resources;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = getBindingView();
        resources = new ResourcesProvider(view.getContext());

        createPresenter();
        toRxBusObservable();
        return view;
    }

    @Override public void onDestroyView() {
        clearDisposable();
        super.onDestroyView();
    }

    protected abstract View getBindingView();

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

    private void toRxBusObservable() {
        addDisposable(
            RxBus.toObservable().observeOn(SchedulersFacade.ui()).subscribe(o -> {
                if (o instanceof RxBusEvent.RxBusEventLogin) {
                    onLogin();
                } else if (o instanceof RxBusEvent.RxBusEventLogout) {
                    onLogout();
                } else if (o instanceof RxBusEvent.RxBusEventProductScrap) {
                    ProductDataModel data = ((RxBusEvent.RxBusEventProductScrap) o).getData();
                    onProductScrap(data);
                } else if (o instanceof RxBusEvent.RxBusEventBrandScrap) {
                    BrandDataModel data = ((RxBusEvent.RxBusEventBrandScrap) o).getData();
                    onBrandScrap(data);
                }
            })
        );
    }

    @Override public void onLogin() {
    }

    @Override public void onLogout() {
    }

    @Override public void onProductScrap(ProductDataModel data) {
    }

    @Override public void onBrandScrap(BrandDataModel data) {
    }
}
