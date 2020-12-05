package com.minilook.minilook.ui.brand_info;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.minilook.minilook.databinding.ActivityBrandInfoBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.brand_info.di.BrandInfoArguments;
import com.minilook.minilook.ui.dialog.manager.DialogManager;

public class BrandInfoActivity extends BaseActivity implements BrandInfoPresenter.View {

    public static void start(Context context, int brandNo) {
        Intent intent = new Intent(context, BrandInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("brandNo", brandNo);
        context.startActivity(intent);
    }

    private ActivityBrandInfoBinding binding;
    private BrandInfoPresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivityBrandInfoBinding.inflate(getLayoutInflater());
        return binding.getRoot();
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

    @Override public void setCStime(String time) {
        binding.txtCsTime.setText(time);
    }

    @Override public void setCStel(String tel) {
        binding.txtCsTel.setText(tel);
    }

    @Override public void hideCSsnsPanel() {
        binding.layoutCsSnsPanel.setVisibility(View.GONE);
    }

    @Override public void setCSsns(String sns) {
        binding.txtCsSns.setText(sns);
    }

    @Override public void setCSemail(String email) {
        binding.txtCsEmail.setText(email);
    }

    @Override public void setGuide(String guide) {
        binding.txtGuide.setText(guide);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }
}
