package com.minilook.minilook.ui.leave;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.type.LoginType;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.leave.di.LeaveArguments;
import com.minilook.minilook.ui.main.MainActivity;
import com.minilook.minilook.util.StringUtil;

public class LeaveActivity extends BaseActivity implements LeavePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, LeaveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.txt_point) TextView pointTextView;
    @BindView(R.id.txt_coupon) TextView couponTextView;
    @BindView(R.id.img_chaim_sns) ImageView snsImageView;

    @BindString(R.string.leave_point) String format_point;
    @BindString(R.string.leave_coupon) String format_coupon;

    @BindDrawable(R.drawable.ic_kakao) Drawable img_kakao;
    @BindDrawable(R.drawable.ic_naver) Drawable img_naver;

    private LeavePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_leave;
    }

    @Override protected void createPresenter() {
        presenter = new LeavePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private LeaveArguments provideArguments() {
        return LeaveArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupPoint(int point) {
        pointTextView.setText(String.format(format_point, StringUtil.toDigit(point)));
    }

    @Override public void setupCoupon(int coupon) {
        couponTextView.setText(String.format(format_coupon, StringUtil.toDigit(coupon)));
    }

    @Override public void setupChainSNS(String type) {
        if (type.equals(LoginType.KAKAO.getValue())) {
            snsImageView.setImageDrawable(img_kakao);
        } else if (type.equals(LoginType.NAVER.getValue())) {
            snsImageView.setImageDrawable(img_naver);
        }
    }

    @Override public void navigateToMain() {
        MainActivity.start(this, BottomBar.POSITION_LOOKBOOK);
    }

    @OnClick(R.id.txt_ok)
    void onLeaveClick() {
        presenter.onLeaveClick();
    }
}
