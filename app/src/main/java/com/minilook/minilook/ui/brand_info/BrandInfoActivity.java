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

    public static void start(Context context, int brand_id) {
        Intent intent = new Intent(context, BrandInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("brand_id", brand_id);
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
            .brand_id(getIntent().getIntExtra("brand_id", -1))
            .build();
    }

    @Override public void setupCStime(String text) {
        csTimeTextView.setText(text);
    }

    @Override public void setupCStel(String text) {
        csTelTextView.setText(text);
    }

    @Override public void hideCSsnsPanel() {
        csSnsPanel.setVisibility(View.GONE);
    }

    @Override public void setupCSsns(String text) {
        csSnsTextView.setText(text);
    }

    @Override public void setupCSemail(String text) {
        csEmailTextView.setText(text);
    }

    @Override public void setupGuide(String text) {
        guideTextView.setText(text);
    }
}
