package com.minilook.minilook.ui.base;

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
    protected ResourcesProvider resources;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getBindingView();
        resources = new ResourcesProvider(view.getContext());

        setContentView(view);
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
