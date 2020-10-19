package com.minilook.minilook.ui.preorder_detail;

import com.google.gson.Gson;
import com.minilook.minilook.data.code.DisplayCode;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.preorder.PreorderRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder_detail.di.PreorderDetailArguments;
import com.minilook.minilook.ui.preorder_detail.viewholder.PreorderDetailProductVH;
import com.minilook.minilook.ui.profile.ProfilePresenterImpl;
import com.minilook.minilook.ui.shipping.ShippingPresenterImpl;
import com.minilook.minilook.ui.verify.VerifyActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import timber.log.Timber;

public class PreorderDetailPresenterImpl extends BasePresenterImpl implements PreorderDetailPresenter {

    private final View view;
    private final int preorderNo;
    private final BaseAdapterDataModel<String> imageAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;

    private final PreorderRequest preorderRequest;

    private Gson gson = new Gson();

    public PreorderDetailPresenterImpl(PreorderDetailArguments args) {
        view = args.getView();
        preorderNo = args.getPreorderNo();
        imageAdapter = args.getImageAdapter();
        productAdapter = args.getProductAdapter();
        preorderRequest = new PreorderRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupViewPager();
        view.setupTabLayout();
        view.setupWebView();
        view.setupRecyclerView();

        reqPreorder();
    }

    @Override public void onTabClick(int position) {
        switch (position) {
            case 0:
                view.scrollToPreorderInfo();
                break;
            case 1:
                view.scrollToProduct();
                break;
            case 2:
                view.scrollToShippingNRefund();
                break;
        }
    }

    private void reqPreorder() {
        addDisposable(preorderRequest.getPreorder(preorderNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), PreorderDataModel.class))
            .subscribe(this::resPreorder, Timber::e)
        );
    }

    private void resPreorder(PreorderDataModel data) {
        int status = data.getStatus();

        if (status == DisplayCode.DISPLAY.getValue()) {
            view.setEnableLabelBackground();
            view.enableBuyButton();
        } else {
            view.setDisableLabelBackground();
            view.disableBuyButton(data.getStatusName());
            view.showCloseTextView();
        }
        view.setLabel(data.getStatusName());

        imageAdapter.set(data.getImages());
        view.imageRefresh();

        view.setBrandName(data.getBrandName());
        view.setTitle(data.getTitle());

        view.setRemainDate(getRemainDate(data.getEndDate()));
        view.setTermDate(getTermDate(data.getStartDate(), data.getEndDate()));
        view.setDeliveryDate(getDeliveryDate(data.getDeliveryDate()));

        //view.setPreorderWebView("https://minilook.page.link/5jKst77ZNRg5irkd7");

        productAdapter.set(data.getProducts());
        view.productRefresh();
    }

    private int getRemainDate(long endDate) {
        long todayDay = Calendar.getInstance().getTimeInMillis() / (24 * 60 * 60 * 1000);
        long targetDay = endDate / (24 * 60 * 60 * 1000);
        return (int) (targetDay - todayDay);
    }

    private String getTermDate(long startDate, long endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        String formatStartData = format.format(new Date(startDate));
        String formatEndData = format.format(new Date(endDate));
        return formatStartData + " ~ " + formatEndData;
    }

    private String getDeliveryDate(long deliveryDate) {
        SimpleDateFormat format = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREA);
        return format.format(new Date(deliveryDate));
    }


    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof PreorderDetailProductVH.RxEventPreorderProductClick) {
                String title = ((PreorderDetailProductVH.RxEventPreorderProductClick) o).getTitle();
                int productNo = ((PreorderDetailProductVH.RxEventPreorderProductClick) o).getProductNo();
                view.navigateToPreorderProductDetail(title, preorderNo, productNo);
            }
        }, Timber::e));
    }
}