package com.minilook.minilook.ui.preorder;

import android.net.Uri;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder.di.PreorderArguments;
import com.minilook.minilook.ui.preorder.view.close.PreorderClosePresenterImpl;
import com.minilook.minilook.ui.preorder.view.open.viewholder.PreorderOpenHeaderVH;
import com.minilook.minilook.ui.preorder.view.open.viewholder.PreorderOpenItemVH;
import com.minilook.minilook.util.DynamicLinkManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class PreorderPresenterImpl extends BasePresenterImpl implements PreorderPresenter {

    private final View view;
    private final DynamicLinkManager dynamicLinkManager;

    public PreorderPresenterImpl(PreorderArguments args) {
        view = args.getView();
        dynamicLinkManager = args.getDynamicLinkManager();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupTabLayout();
        view.setupViewPager();
    }

    @Override public void onTabClick(int position) {
        view.setupCurrentPage(position);
    }

    private void sendShareLink(PreorderDataModel data) {
        dynamicLinkManager.createShareLink(DynamicLinkManager.TYPE_PREORDER, data.getPreorderNo(), data.getTitle(), data.getThumbUrl(),
            new DynamicLinkManager.OnCompletedListener() {
                @Override public void onSuccess(Uri uri) {
                    view.sendLink(uri.toString());
                }

                @Override public void onFail() {
                    view.showErrorMessage();
                }
            });
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof PreorderOpenHeaderVH.RxBusEventPreorderInfoClick) {
                view.navigateToPreorderInfo();
            } else if (o instanceof PreorderClosePresenterImpl.RxBusEventClosePreorderEmpty) {
                view.hideClosePreorderTab();
            } else if (o instanceof PreorderOpenItemVH.RxBusEventPreorderClick) {
                int preorderNo = ((PreorderOpenItemVH.RxBusEventPreorderClick) o).getPreorderNo();
                view.navigateToPreorderDetail(preorderNo);
            } else if (o instanceof RxEventPreorderShareClick) {
                PreorderDataModel data = ((RxEventPreorderShareClick) o).getData();
                sendShareLink(data);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventPreorderShareClick {
        private PreorderDataModel data;
    }
}