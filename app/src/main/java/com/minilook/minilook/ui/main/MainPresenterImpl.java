package com.minilook.minilook.ui.main;

import com.minilook.minilook.App;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.network.scrap.ScrapRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.lookbook.view.detail.LookBookDetailPresenterImpl;
import com.minilook.minilook.ui.lookbook.view.preview.viewholder.LookBookImageModuleVH;
import com.minilook.minilook.ui.main.di.MainArguments;
import com.minilook.minilook.data.firebase.DynamicLinkManager;
import com.pixplicity.easyprefs.library.Prefs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class MainPresenterImpl extends BasePresenterImpl implements MainPresenter {

    private final View view;
    private final MemberRequest memberRequest;
    private final ScrapRequest scrapRequest;

    public MainPresenterImpl(MainArguments args) {
        view = args.getView();
        memberRequest = new MemberRequest();
        scrapRequest = new ScrapRequest();
    }

    @Override public void onCreate() {
        view.showLoadingView();

        toRxObservable();
        view.setupViewPager();
        view.setupBottomBar();

        checkMarketingInfoDialog();
    }

    @Override public void onTabChanged(int position) {
        if (position != 0) {
            RxBus.send(new LookBookPresenterImpl.RxEventScrollToPreview(false));
            RxBus.send(new LookBookDetailPresenterImpl.RxEventLookBookDetailScrollToTop());
        }
        view.setupCurrentPage(position);
    }

    @Override public void onProductScrap(boolean isScrap, ProductDataModel product) {
        reqProductScrap(isScrap, product);
    }

    @Override public void onBrandScrap(boolean isScrap, BrandDataModel brand) {
        reqBrandScrap(isScrap, brand);
    }

    @Override public void onMarketingAgree() {
        reqUpdateMarketingInfo();
    }

    @Override public void onMarketingDismiss() {
        checkCoachMark();
    }

    @Override public void onCoachMarkEnd() {
        checkDynamicLink();
    }

    private void checkMarketingInfoDialog() {
        boolean isVisible = Prefs.getBoolean(PrefsKey.KEY_MARKETING_DIALOG_VISIBLE, false);
        Prefs.putBoolean(PrefsKey.KEY_MARKETING_DIALOG_VISIBLE, true);
        if (!App.getInstance().isLogin() && !isVisible) {
            view.showMarketingDialog();
        } else {
            checkCoachMark();
        }
    }

    private void checkCoachMark() {
        boolean isVisible = Prefs.getBoolean(PrefsKey.KEY_LOOKBOOK_COACH_VISIBLE, false);
        if (!isVisible) {
            view.showLookBookCoachMark();
        } else {
            checkDynamicLink();
        }
    }

    private void checkDynamicLink() {
        if (App.getInstance().isDynamicLink()) {
            //String type = App.getInstance().getDynamicLinkType();
            //int itemNo = App.getInstance().getDynamicLinkItemNo();
            //
            //switch (type) {
            //    case DynamicLinkManager.TYPE_PROMOTION:
            //        view.navigateToPromotionDetail(itemNo);
            //        break;
            //    case DynamicLinkManager.TYPE_EVENT:
            //        view.navigateToEventDetail(itemNo);
            //        break;
            //    case DynamicLinkManager.TYPE_PRODUCT:
            //        view.navigateToProductDetail(itemNo);
            //        break;
            //    case DynamicLinkManager.TYPE_BRAND:
            //        view.navigateToBrandDetail(itemNo);
            //        break;
            //    case DynamicLinkManager.TYPE_PREORDER:
            //        view.navigateToPreorderDetail(itemNo);
            //        break;
            //}
        }
    }

    private void reqUpdateMarketingInfo() {
        addDisposable(memberRequest.updateMarketingInfo(true)
            .subscribe());
    }

    private void reqProductScrap(boolean isScrap, ProductDataModel product) {
        addDisposable(scrapRequest.updateProductScrap(isScrap, product.getProductNo())
            .subscribe());
    }

    private void reqBrandScrap(boolean isScrap, BrandDataModel brand) {
        addDisposable(scrapRequest.updateBrandScrap(isScrap, brand.getBrandNo())
            .subscribe());
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventLookBookPrePageChanged) {
                int position = ((RxEventLookBookPrePageChanged) o).getPosition();
                view.setupBottomBarTheme(position != 0);
            } else if (o instanceof RxEventNavigateToPage) {
                int position = ((RxEventNavigateToPage) o).getPosition();
                view.setupCurrentPage(position);
            } else if (o instanceof LookBookImageModuleVH.RxBusEventLookBookReady) {
                view.hideLoadingView();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookPrePageChanged {
        private int position;
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToPage {
        private int position;
    }
}