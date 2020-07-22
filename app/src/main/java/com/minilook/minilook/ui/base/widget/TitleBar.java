package com.minilook.minilook.ui.base.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.search.SearchActivity;

public class TitleBar extends ConstraintLayout {

    @BindView(R.id.img_titlebar_logo) ImageView logoImageView;
    @BindView(R.id.img_titlebar_back) ImageView backImageView;
    @BindView(R.id.txt_titlebar_title) TextView titleTextView;
    @BindView(R.id.img_titlebar_search_keyword) ImageView keywordSearchImageView;
    @BindView(R.id.img_titlebar_search_filter) ImageView filterSearchImageView;
    @BindView(R.id.img_titlebar_shoppingbag) ImageView shoppingbagImageView;

    private Activity activity;

    private boolean isShowLogo;
    private boolean isShowBack;
    private boolean isShowKeywordSearch;
    private boolean isShowFilterSearch;
    private boolean isShowShoppingBag;
    private boolean isShowTitle;
    private String title;

    public TitleBar(@NonNull Context context) {
        this(context, null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.activity = getActivity();

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);
        isShowLogo = typedArray.getBoolean(R.styleable.TitleBar_showLogo, false);
        isShowBack = typedArray.getBoolean(R.styleable.TitleBar_showBack, false);
        isShowKeywordSearch = typedArray.getBoolean(R.styleable.TitleBar_showKeywordSearch, false);
        isShowFilterSearch = typedArray.getBoolean(R.styleable.TitleBar_showFilterSearch, false);
        isShowShoppingBag = typedArray.getBoolean(R.styleable.TitleBar_showShoppingBag, false);
        isShowTitle = typedArray.getBoolean(R.styleable.TitleBar_showTitle, false);
        title = typedArray.getString(R.styleable.TitleBar_setTitle);
        typedArray.recycle();

        initView();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, getResources().getDimensionPixelSize(R.dimen.dp_30));
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_titlebar, this));
        updateUI();
    }

    private void updateUI() {
        logoImageView.setVisibility(isShowLogo ? VISIBLE : GONE);
        backImageView.setVisibility(isShowBack ? VISIBLE : GONE);
        keywordSearchImageView.setVisibility(isShowKeywordSearch ? VISIBLE : GONE);
        filterSearchImageView.setVisibility(isShowFilterSearch ? VISIBLE : GONE);
        shoppingbagImageView.setVisibility(isShowShoppingBag ? VISIBLE : GONE);
        titleTextView.setVisibility(isShowTitle ? VISIBLE : GONE);
        titleTextView.setText(title);
    }

    public void setShowLogo(boolean visible) {
        isShowLogo = visible;
        updateUI();
    }

    public void setShowBack(boolean visible) {
        isShowBack = visible;
        updateUI();
    }

    public void setShowKeywordSearch(boolean visible) {
        isShowKeywordSearch = visible;
        updateUI();
    }

    public void setShowFilterSearch(boolean visible) {
        isShowFilterSearch = visible;
        updateUI();
    }

    public void setShowShoppingBag(boolean visible) {
        isShowShoppingBag = visible;
        updateUI();
    }


    public void setShowTitle(boolean visible) {
        isShowTitle = visible;
        updateUI();
    }

    public void setTitle(String text) {
        title = text;
        updateUI();
    }

    @OnClick(R.id.img_titlebar_search_keyword)
    void onKeywordSearchClick() {
        if (activity != null) SearchActivity.start(activity);
    }

    @OnClick(R.id.img_titlebar_search_filter)
    void onFilterSearchClick() {
        if (activity != null) SearchActivity.start(activity);
    }

    @OnClick(R.id.img_titlebar_shoppingbag)
    void onShoppingBagClick() {

    }

    @OnClick(R.id.img_titlebar_back)
    void onBackClick() {
        if (activity != null) activity.finish();
    }

    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
