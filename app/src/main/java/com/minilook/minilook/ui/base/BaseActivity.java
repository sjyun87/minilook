package com.minilook.minilook.ui.base;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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

    private CompositeDisposable disposable = new CompositeDisposable();
    private Unbinder binder;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        binder = ButterKnife.bind(this);
        createPresenter();
        toRxBusObservable();
    }

    @Override protected void onDestroy() {
        if (binder != null) binder.unbind();
        clearDisposable();
        super.onDestroy();
    }

    private void toRxBusObservable() {
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
