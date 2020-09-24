package com.minilook.minilook.ui.brand_info;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.brand_info.di.BrandInfoArguments;

public class BrandInfoActivity extends BaseActivity implements BrandInfoPresenter.View {

    public static void start(Context context, int brandNo) {
        Intent intent = new Intent(context, BrandInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("brandNo", brandNo);
        context.startActivity(intent);
    }

    @BindView(R.id.txt_cs_time) TextView csTimeTextView;
    @BindView(R.id.txt_cs_tel) TextView csTelTextView;
    @BindView(R.id.layout_cs_sns_panel) LinearLayout csSnsPanel;
    @BindView(R.id.txt_cs_sns) TextView csSnsTextView;
    @BindView(R.id.txt_cs_email) TextView csEmailTextView;
    @BindView(R.id.txt_guide) TextView guideTextView;

    private BrandInfoPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_brand_info;
    }

    @Override protected void createPresenter() {
        presenter = new BrandInfoPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private BrandInfoArguments provideArguments() {
        return BrandInfoArguments.builder()
            .view(this)
            .brandNo(getIntent().getIntExtra("brandNo", -1))
            .build();
    }

    @Override public void setupCStime(String time) {
        csTimeTextView.setText(time);
    }

    @Override public void setupCStel(String tel) {
        csTelTextView.setText(tel);
    }

    @Override public void hideCSsnsPanel() {
        csSnsPanel.setVisibility(View.GONE);
    }

    @Override public void setupCSsns(String sns) {
        csSnsTextView.setText(sns);
    }

    @Override public void setupCSemail(String email) {
        csEmailTextView.setText(email);
    }

    @Override public void setupGuide(String guide) {
        guideTextView.setText(guide);
    }
}
