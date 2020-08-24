package com.minilook.minilook.ui.ipage;

import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.coupon.CouponActivity;
import com.minilook.minilook.ui.ipage.di.IpageArguments;
import com.minilook.minilook.ui.order_history.OrderHistoryActivity;
import com.minilook.minilook.ui.point.PointActivity;
import com.minilook.minilook.ui.profile.ProfileActivity;
import com.minilook.minilook.ui.question.QuestionActivity;
import com.minilook.minilook.ui.scrap.ScrapActivity;

public class IpageFragment extends BaseFragment implements IpagePresenter.View {

    public static IpageFragment newInstance() {
        return new IpageFragment();
    }

    private IpagePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_ipage;
    }

    @Override protected void createPresenter() {
        presenter = new IpagePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private IpageArguments provideArguments() {
        return IpageArguments.builder()
            .view(this)
            .build();
    }

    //@OnClick(R.id.img_profile)
    //void onProfileClick() {
    //    ProfileActivity.start(getContext());
    //}
    //
    //@OnClick(R.id.layout_question_panel)
    //void onQuestionClick() {
    //    QuestionActivity.start(getContext());
    //}
    //
    //@OnClick(R.id.layout_point_panel)
    //void onPointClick() {
    //    PointActivity.start(getContext());
    //}
    //
    //@OnClick(R.id.layout_coupon_panel)
    //void onCouponClick() {
    //    CouponActivity.start(getContext());
    //}
    //
    //@OnClick(R.id.layout_scrap_panel)
    //void onScrapClick() {
    //    ScrapActivity.start(getContext());
    //}
    //
    //@OnClick(R.id.txt_order_more)
    //void onOrderMoreClick() {
    //    OrderHistoryActivity.start(getContext());
    //}
}
