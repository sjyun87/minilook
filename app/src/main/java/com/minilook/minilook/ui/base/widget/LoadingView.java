package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.ButterKnife;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.minilook.minilook.R;

public class LoadingView extends ConstraintLayout {

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setVisibility(GONE);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_laoding_view, this));
    }

    public void show() {
        YoYo.with(Techniques.FadeIn)
            .duration(300)
            .onStart(animator -> setVisibility(VISIBLE))
            .playOn(this);
    }

    public void hide() {
        YoYo.with(Techniques.FadeOut)
            .duration(300)
            .onEnd(animator -> setVisibility(GONE))
            .playOn(this);
    }
}
