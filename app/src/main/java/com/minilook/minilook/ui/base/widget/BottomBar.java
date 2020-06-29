package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.minilook.minilook.R;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;

public class BottomBar extends LinearLayout {
    private static final int POSITION_LOOKBOOK = 0;
    private static final int POSITION_MARKET = 1;
    private static final int POSITION_CATEGORY = 2;
    private static final int POSITION_SCRAPBOOK = 3;

    @BindView(R.id.img_bottombar_lookbook) ImageView lookbookImageView;
    @BindView(R.id.txt_bottombar_lookbook) TextView lookbookTextView;
    @BindView(R.id.img_bottombar_market) ImageView marketImageView;
    @BindView(R.id.txt_bottombar_market) TextView marketTextView;
    @BindView(R.id.img_bottombar_category) ImageView categoryImageView;
    @BindView(R.id.txt_bottombar_category) TextView categoryTextView;
    @BindView(R.id.img_bottombar_scrapbook) ImageView scrapbookImageView;
    @BindView(R.id.txt_bottombar_scrapbook) TextView scrapbookTextView;

    @BindColor(R.color.color_FFFFFFFF) int FFFFFFFF;
    @BindColor(R.color.color_FFA9A9A9) int FFA9A9A9;
    @BindColor(R.color.color_FF835AFF) int FF835AFF;

    @BindDrawable(R.drawable.bg_bottombar_white) Drawable whiteTheme;
    @BindDrawable(R.drawable.bg_bottombar_dark) Drawable darkTheme;

    @BindDrawable(R.drawable.ic_lookbook_on) Drawable lookbookOn;
    @BindDrawable(R.drawable.ic_lookbook_off) Drawable lookbookOff;
    @BindDrawable(R.drawable.ic_market_on) Drawable marketOn;
    @BindDrawable(R.drawable.ic_market_off) Drawable marketOff;
    @BindDrawable(R.drawable.ic_category_on) Drawable categoryOn;
    @BindDrawable(R.drawable.ic_category_off) Drawable categoryOff;
    @BindDrawable(R.drawable.ic_scrapbook_on) Drawable scrapbookOn;
    @BindDrawable(R.drawable.ic_scrapbook_off) Drawable scrapbookOff;

    private int position = POSITION_LOOKBOOK;
    private boolean isWhiteTheme;

    @Setter private OnTabChangeListener onTabChangeListener;

    public BottomBar(@NonNull Context context) {
        this(context, null);
    }

    public BottomBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BottomBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BottomBar);
        isWhiteTheme = typedArray.getBoolean(R.styleable.BottomBar_whiteTheme, false);
        typedArray.recycle();

        initView();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, getResources().getDimensionPixelSize(R.dimen.dp_30));
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_bottombar, this));
        setGravity(Gravity.CENTER_VERTICAL);

        updateUI();
    }

    private void updateUI() {
        if (isWhiteTheme) {
            setBackground(whiteTheme);
        } else {
            setBackground(darkTheme);
        }

        switch (position) {
            case POSITION_LOOKBOOK:
                lookbookImageView.setImageDrawable(lookbookOn);
                marketImageView.setImageDrawable(marketOff);
                categoryImageView.setImageDrawable(categoryOff);
                scrapbookImageView.setImageDrawable(scrapbookOff);
                lookbookTextView.setTextColor(FFFFFFFF);
                marketTextView.setTextColor(FFA9A9A9);
                categoryTextView.setTextColor(FFA9A9A9);
                scrapbookTextView.setTextColor(FFA9A9A9);
                break;

            case POSITION_MARKET:
                lookbookImageView.setImageDrawable(lookbookOff);
                marketImageView.setImageDrawable(marketOn);
                categoryImageView.setImageDrawable(categoryOff);
                scrapbookImageView.setImageDrawable(scrapbookOff);
                lookbookTextView.setTextColor(FFA9A9A9);
                marketTextView.setTextColor(FF835AFF);
                categoryTextView.setTextColor(FFA9A9A9);
                scrapbookTextView.setTextColor(FFA9A9A9);
                break;

            case POSITION_CATEGORY:
                lookbookImageView.setImageDrawable(lookbookOff);
                marketImageView.setImageDrawable(marketOff);
                categoryImageView.setImageDrawable(categoryOn);
                scrapbookImageView.setImageDrawable(scrapbookOff);
                lookbookTextView.setTextColor(FFA9A9A9);
                marketTextView.setTextColor(FFA9A9A9);
                categoryTextView.setTextColor(FF835AFF);
                scrapbookTextView.setTextColor(FFA9A9A9);
                break;

            case POSITION_SCRAPBOOK:
                lookbookImageView.setImageDrawable(lookbookOff);
                marketImageView.setImageDrawable(marketOff);
                categoryImageView.setImageDrawable(categoryOff);
                scrapbookImageView.setImageDrawable(scrapbookOn);
                lookbookTextView.setTextColor(FFA9A9A9);
                marketTextView.setTextColor(FFA9A9A9);
                categoryTextView.setTextColor(FFA9A9A9);
                scrapbookTextView.setTextColor(FF835AFF);
                break;
        }
    }

    @OnClick(R.id.btn_bottombar_lookbook)
    void onLookBookClick() {
        position = POSITION_LOOKBOOK;
        isWhiteTheme = false;
        updateUI();
        if (onTabChangeListener != null) onTabChangeListener.onTabChanged(position);
    }

    @OnClick(R.id.btn_bottombar_market)
    void onMarketClick() {
        position = POSITION_MARKET;
        isWhiteTheme = true;
        updateUI();
        if (onTabChangeListener != null) onTabChangeListener.onTabChanged(position);
    }

    @OnClick(R.id.btn_bottombar_category)
    void onCategoryClick() {
        position = POSITION_CATEGORY;
        isWhiteTheme = true;
        updateUI();
        if (onTabChangeListener != null) onTabChangeListener.onTabChanged(position);
    }

    @OnClick(R.id.btn_bottombar_scrapbook)
    void onScrapBookClick() {
        position = POSITION_SCRAPBOOK;
        isWhiteTheme = true;
        updateUI();
        if (onTabChangeListener != null) onTabChangeListener.onTabChanged(position);
    }

    public interface OnTabChangeListener {
        void onTabChanged(int position);
    }
}
