package com.minilook.minilook.ui.main;

import com.minilook.minilook.App;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.firebase.DynamicLinkManager;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.lookbook.view.detail.LookBookDetailPresenterImpl;
import com.minilook.minilook.ui.main.di.MainArguments;
import com.pixplicity.easyprefs.library.Prefs;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class MainPresenterImpl extends BasePresenterImpl implements MainPresenter {

    private final View view;
    private final MemberRequest memberRequest;

    private int step = 0;
    private boolean isLookBookReady = false;
    private boolean isWaitingCoachMark = false;

    public MainPresenterImpl(MainArguments args) {
        view = args.getView();
        memberRequest = new MemberRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.showLoadingView();
        view.setupViewPager();
        view.setupBottomBar();

        checkAction(step);
    }

    @Override public void onTabChanged(int position) {
        if (position != 0) {
            RxBus.send(new LookBookPresenterImpl.RxEventScrollToPreview(false));
            RxBus.send(new LookBookDetailPresenterImpl.RxEventLookBookDetailScrollToTop());
        }
        view.setupCurrentPage(position);
    }

    @Override public void onMarketingAgree() {
        updateMarketingAgree(true);
    }

    @Override public void onMarketingDisagree() {
        updateMarketingAgree(false);
    }

    @Override public void onCoachMarkEnd() {
        Prefs.putBoolean(PrefsKey.KEY_LOOKBOOK_COACH_VISIBLE, true);
        checkAction(++step);
    }

    private void checkAction(int step) {
        switch (step) {
            case 0:
                checkMarketingDialog();
                break;
            case 1:
                checkCoachMark();
                break;
            case 2:
                checkDynamicLink();
                break;
        }
    }

    private void checkMarketingDialog() {
        if (!Prefs.getBoolean(PrefsKey.KEY_MARKETING_DIALOG_VISIBLE, false)) {
            view.showMarketingDialog();
            Prefs.putBoolean(PrefsKey.KEY_MARKETING_DIALOG_VISIBLE, true);
        } else {
            checkAction(++step);
        }
    }

    private void checkCoachMark() {
        if (!Prefs.getBoolean(PrefsKey.KEY_LOOKBOOK_COACH_VISIBLE, false)) {
            if (isLookBookReady) {
                view.showLookBookCoachMark();
            } else {
                isWaitingCoachMark = true;
            }
        } else {
            checkAction(++step);
        }
    }

    private void checkDynamicLink() {
        if (App.getInstance().isDynamicLink()) {
            Map<String, String> dynamicData = App.getInstance().getDynamicLinkData();
            String type = dynamicData.get("type");
            int itemNo = Integer.parseInt(dynamicData.get("id"));

            switch (type) {
                case DynamicLinkManager.TYPE_PROMOTION:
                    view.navigateToPromotionDetail(itemNo);
                    break;
                case DynamicLinkManager.TYPE_EVENT:
                    view.navigateToEventDetail(itemNo);
                    break;
                case DynamicLinkManager.TYPE_PRODUCT:
                    view.navigateToProductDetail(itemNo);
                    break;
                case DynamicLinkManager.TYPE_BRAND:
                    view.navigateToBrandDetail(itemNo);
                    break;
                case DynamicLinkManager.TYPE_PREORDER:
                    view.navigateToPreorderDetail(itemNo);
                    break;
            }
        }
    }

    private void updateMarketingAgree(boolean enable) {
        addDisposable(memberRequest.updateMarketingInfo(enable)
            .compose(Transformer.applySchedulers())
            .subscribe(model -> onResUpdateMarketingAgree(enable), Timber::e));
    }

    private void onResUpdateMarketingAgree(boolean enable) {
        view.updateMarketingAgreeToast(enable);
        checkAction(++step);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxBusEventLookBookReady) {
                view.hideLoadingView();
                if (isWaitingCoachMark) {
                    view.showLookBookCoachMark();
                    isWaitingCoachMark = false;
                } else {
                    isLookBookReady = true;
                }
            } else if (o instanceof RxEventChangeBottomBarTheme) {
                boolean flag = ((RxEventChangeBottomBarTheme) o).isFlag();
                view.setBottomBarTheme(flag);
            } else if (o instanceof RxEventNavigateToPage) {
                int position = ((RxEventNavigateToPage) o).getPosition();
                view.setupCurrentPage(position);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventLookBookReady {
    }

    @AllArgsConstructor @Getter public final static class RxEventChangeBottomBarTheme {
        private final boolean flag;
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToPage {
        private final int position;
    }
}