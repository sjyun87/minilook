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
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.data.rx.SchedulersFacade;
import com.minilook.minilook.ui.base.listener.OnLoginListener;
import com.minilook.minilook.ui.base.listener.OnScrapListener;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class _BaseFragment extends Fragment implements OnLoginListener, OnScrapListener {

    private CompositeDisposable disposable = new CompositeDisposable();
    private Unbinder binder;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutID(), container, false);
        binder = ButterKnife.bind(this, view);
        createPresenter();
        toRxBusObservable();
        return view;
    }

    @Override public void onDestroyView() {
        if (binder != null) binder.unbind();
        clearDisposable();
        super.onDestroyView();
    }

    private void toRxBusObservable() {
        addDisposable(
            RxBus.toObservable().observeOn(SchedulersFacade.ui()).subscribe(o -> {
                if (o instanceof RxBusEvent.RxBusEventLogin) {
                    onLogin();
                } else if (o instanceof RxBusEvent.RxBusEventLogout) {
                    onLogout();
                } else if (o instanceof RxBusEvent.RxBusEventProductScrap) {
                    //boolean isScrap = ((RxBusEvent.RxBusEventProductScrap) o).isScrap();
                    //ProductDataModel product = ((RxBusEvent.RxBusEventProductScrap) o).getProduct();
                    //onProductScrap(isScrap, product);
                } else if (o instanceof RxBusEvent.RxBusEventBrandScrap) {
                    //boolean isScrap = ((RxBusEvent.RxBusEventBrandScrap) o).isScrap();
                    //BrandDataModel brand_id = ((RxBusEvent.RxBusEventBrandScrap) o).getBrand();
                    //onBrandScrap(isScrap, brand_id);
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
