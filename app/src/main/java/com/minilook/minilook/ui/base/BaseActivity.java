package com.minilook.minilook.ui.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.data.rx.SchedulersFacade;
import com.minilook.minilook.ui.base.listener.OnLoginListener;
import com.minilook.minilook.ui.base.listener.OnScrapListener;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity implements OnLoginListener, OnScrapListener {

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getBindingView());
        createPresenter();
        toRxObservable();
    }

    @Override protected void onDestroy() {
        clearDisposable();
        super.onDestroy();
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

    private void toRxObservable() {
        addDisposable(
            RxBus.toObservable().observeOn(SchedulersFacade.ui()).subscribe(o -> {
                if (o instanceof RxBusEvent.RxBusEventLogin) {
                    onLogin();
                } else if (o instanceof RxBusEvent.RxBusEventLogout) {
                    onLogout();
                } else if (o instanceof RxBusEvent.RxBusEventProductScrap) {
                    boolean isScrap = ((RxBusEvent.RxBusEventProductScrap) o).isScrap();
                    ProductDataModel product = ((RxBusEvent.RxBusEventProductScrap) o).getProduct();
                    onProductScrap(isScrap, product);
                } else if (o instanceof RxBusEvent.RxBusEventBrandScrap) {
                    boolean isScrap = ((RxBusEvent.RxBusEventBrandScrap) o).isScrap();
                    BrandDataModel brand = ((RxBusEvent.RxBusEventBrandScrap) o).getBrand();
                    onBrandScrap(isScrap, brand);
                }
            })
        );
    }

    @Override public void onLogin() {
    }

    @Override public void onLogout() {
    }

    @Override public void onProductScrap(boolean isScrap, ProductDataModel product) {
    }

    @Override public void onBrandScrap(boolean isScrap, BrandDataModel brand) {
    }
}
