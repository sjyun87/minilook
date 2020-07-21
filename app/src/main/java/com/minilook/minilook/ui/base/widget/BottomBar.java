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
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.minilook.minilook.R;
import lombok.Setter;

public class BottomBar extends LinearLayout {
    private static final int POSITION_LOOKBOOK = 0;
    private static final int POSITION_PREORDER = 1;
    private static final int POSITION_MARKET = 2;
    private static final int POSITION_IPAGE = 3;

    @BindView(R.id.img_bottombar_lookbook) ImageView lookbookImageView;
    @BindView(R.id.txt_bottombar_lookbook) TextView lookbookTextView;
    @BindView(R.id.img_bottombar_preorder) ImageView preorderImageView;
    @BindView(R.id.txt_bottombar_preorder) TextView preorderTextView;
    @BindView(R.id.img_bottombar_market) ImageView marketImageView;
    @BindView(R.id.txt_bottombar_market) TextView marketTextView;
    @BindView(R.id.img_bottombar_ipage) ImageView ipageImageView;
    @BindView(R.id.txt_bottombar_ipage) TextView ipageTextView;

    @BindColor(R.color.color_FFFFFFFF) int FFFFFFFF;
    @BindColor(R.color.color_FFA9A9A9) int FFA9A9A9;
    @BindColor(R.color.color_FF8140E5) int FF8140E5;

    @BindDrawable(R.drawable.bg_bottombar_white) Drawable whiteTheme;
    @BindDrawable(R.drawable.bg_bottombar_dark) Drawable darkTheme;

    @BindDrawable(R.drawable.ic_lookbook_white) Drawable lookbookWhite;
    @BindDrawable(R.drawable.ic_lookbook_gray) Drawable lookbookGray;
    @BindDrawable(R.drawable.ic_preorder_purple) Drawable preorderPurple;
    @BindDrawable(R.drawable.ic_preorder_white) Drawable preorderWhite;
    @BindDrawable(R.drawable.ic_preorder_gray) Drawable preorderGray;
    @BindDrawable(R.drawable.ic_market_purple) Drawable marketPurple;
    @BindDrawable(R.drawable.ic_market_white) Drawable marketWhite;
    @BindDrawable(R.drawable.ic_market_gray) Drawable marketGray;
    @BindDrawable(R.drawable.ic_ipage_purple) Drawable ipagePurple;
    @BindDrawable(R.drawable.ic_ipage_white) Drawable ipageWhite;
    @BindDrawable(R.drawable.ic_ipage_gray) Drawable ipageGray;

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
                lookbookImageView.setImageDrawable(lookbookWhite);
                preorderImageView.setImageDrawable(preorderWhite);
                marketImageView.setImageDrawable(marketWhite);
                ipageImageView.setImageDrawable(ipageWhite);
                lookbookTextView.setTextColor(FFFFFFFF);
                preorderTextView.setTextColor(FFFFFFFF);
                marketTextView.setTextColor(FFFFFFFF);
                ipageTextView.setTextColor(FFFFFFFF);
                break;

            case POSITION_PREORDER:
                lookbookImageView.setImageDrawable(lookbookGray);
                preorderImageView.setImageDrawable(preorderPurple);
                marketImageView.setImageDrawable(marketGray);
                ipageImageView.setImageDrawable(ipageGray);
                lookbookTextView.setTextColor(FFA9A9A9);
                preorderTextView.setTextColor(FF8140E5);
                marketTextView.setTextColor(FFA9A9A9);
                ipageTextView.setTextColor(FFA9A9A9);
                break;

            case POSITION_MARKET:
                lookbookImageView.setImageDrawable(lookbookGray);
                preorderImageView.setImageDrawable(preorderGray);
                marketImageView.setImageDrawable(marketPurple);
                ipageImageView.setImageDrawable(ipageGray);
                lookbookTextView.setTextColor(FFA9A9A9);
                preorderTextView.setTextColor(FFA9A9A9);
                marketTextView.setTextColor(FF8140E5);
                ipageTextView.setTextColor(FFA9A9A9);
                break;

            case POSITION_IPAGE:
                lookbookImageView.setImageDrawable(lookbookGray);
                preorderImageView.setImageDrawable(preorderGray);
                marketImageView.setImageDrawable(marketGray);
                ipageImageView.setImageDrawable(ipagePurple);
                lookbookTextView.setTextColor(FFA9A9A9);
                preorderTextView.setTextColor(FFA9A9A9);
                marketTextView.setTextColor(FFA9A9A9);
                ipageTextView.setTextColor(FF8140E5);
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

    @OnClick(R.id.btn_bottombar_preorder)
    void onPreorderClick() {
        position = POSITION_PREORDER;
        isWhiteTheme = true;
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

    @OnClick(R.id.btn_bottombar_ipage)
    void onIpageClick() {
        position = POSITION_IPAGE;
        isWhiteTheme = true;
        updateUI();
        if (onTabChangeListener != null) onTabChangeListener.onTabChanged(position);
    }

    public interface OnTabChangeListener {
        void onTabChanged(int position);
    }
}
