package com.minilook.minilook.ui.preorder;

import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder.di.PreorderArguments;
import com.minilook.minilook.ui.preorder.view.close.PreorderClosePresenterImpl;
import com.minilook.minilook.ui.preorder.view.open.viewholder.PreorderOpenHeaderVH;
import com.minilook.minilook.util.DynamicLinkUtil;
import com.minilook.minilook.util.TrackingUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class PreorderPresenterImpl extends BasePresenterImpl implements PreorderPresenter {

    private final View view;

    public PreorderPresenterImpl(PreorderArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupTabLayout();
        view.setupViewPager();
    }

    @Override public void onResume() {
        TrackingUtil.pageTracking("프리오더페이지", PreorderFragment.class.getSimpleName());
    }

    @Override public void onTabClick(int position) {
        view.setupCurrentPage(position);
    }

    private void sendShareLink(PreorderDataModel data) {
        String title =
            data.getTitle() + " (" + parseToDate(data.getStartDate()) + "~" + parseToDate(data.getEndDate()) + ")";
        DynamicLinkUtil.sendDynamicLink(DynamicLinkUtil.TYPE_PREORDER, data.getPreorderNo(), title, data.getThumbUrl());
    }

    private String parseToDate(long date) {
        Date endDate = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd", Locale.KOREA);
        return format.format(endDate);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof PreorderOpenHeaderVH.RxBusEventPreorderInfoClick) {
                view.navigateToPreorderInfo();
            } else if (o instanceof PreorderClosePresenterImpl.RxBusEventClosePreorderEmpty) {
                view.hideClosePreorderTab();
            } else if (o instanceof RxBusEventPreorderClick) {
                int preorderNo = ((RxBusEventPreorderClick) o).getPreorderNo();
                view.navigateToPreorderDetail(preorderNo);
            } else if (o instanceof RxEventPreorderShareClick) {
                PreorderDataModel data = ((RxEventPreorderShareClick) o).getData();
                sendShareLink(data);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventPreorderClick {
        int preorderNo;
    }

    @AllArgsConstructor @Getter public final static class RxEventPreorderShareClick {
        private PreorderDataModel data;
    }
}