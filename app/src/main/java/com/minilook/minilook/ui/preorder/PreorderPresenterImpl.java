package com.minilook.minilook.ui.preorder;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder.di.PreorderArguments;
import com.minilook.minilook.ui.preorder.view.close.PreorderClosePresenterImpl;
import com.minilook.minilook.ui.preorder.view.open.viewholder.PreorderOpenHeaderVH;
import com.minilook.minilook.ui.preorder.view.open.viewholder.PreorderOpenItemVH;
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

    @Override public void onTabClick(int position) {
        view.setupCurrentPage(position);
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
            }
        }, Timber::e));
    }
}