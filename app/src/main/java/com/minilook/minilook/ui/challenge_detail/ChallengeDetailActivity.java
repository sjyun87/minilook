package com.minilook.minilook.ui.challenge_detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.minilook.minilook.databinding.ActivityChallengeDetailBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.challenge_detail.di.ChallengeDetailArguments;
import com.minilook.minilook.ui.dialog.manager.DialogManager;

public class ChallengeDetailActivity extends BaseActivity implements ChallengeDetailPresenter.View {

    public static void start(Context context, int challengeNo) {
        Intent intent = new Intent(context, ChallengeDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("challengeNo", challengeNo);
        context.startActivity(intent);
    }

    private ActivityChallengeDetailBinding binding;
    private ChallengeDetailPresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivityChallengeDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ChallengeDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ChallengeDetailArguments provideArguments() {
        return ChallengeDetailArguments.builder()
            .view(this)
            .challengeNo(getIntent().getIntExtra("challengeNo", -1))
            .build();
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }
}
