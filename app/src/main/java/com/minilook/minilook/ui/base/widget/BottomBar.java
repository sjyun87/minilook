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

    @BindColor(R.color.color_FFFFFFFF) int color_FFFFFFFF;
    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    @BindDrawable(R.drawable.bg_bottombar_white) Drawable bg_white;
    @BindDrawable(R.drawable.bg_bottombar_dark) Drawable bg_dark;

    @BindDrawable(R.drawable.ic_lookbook_purple) Drawable icon_lookbook_purple;
    @BindDrawable(R.drawable.ic_lookbook_white) Drawable icon_lookbook_white;
    @BindDrawable(R.drawable.ic_lookbook_gray) Drawable icon_lookbook_gray;
    @BindDrawable(R.drawable.ic_preorder_purple) Drawable icon_preorder_purple;
    @BindDrawable(R.drawable.ic_preorder_white) Drawable icon_preorder_white;
    @BindDrawable(R.drawable.ic_preorder_gray) Drawable icon_preorder_gray;
    @BindDrawable(R.drawable.ic_market_purple) Drawable icon_market_purple;
    @BindDrawable(R.drawable.ic_market_white) Drawable icon_market_white;
    @BindDrawable(R.drawable.ic_market_gray) Drawable icon_market_gray;
    @BindDrawable(R.drawable.ic_ipage_purple) Drawable icon_ipage_purple;
    @BindDrawable(R.drawable.ic_ipage_white) Drawable icon_ipage_white;
    @BindDrawable(R.drawable.ic_ipage_gray) Drawable icon_ipage_gray;

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

    public void setupWhiteTheme(boolean flag) {
        this.isWhiteTheme = flag;
        updateUI();
    }

    private void updateUI() {
        if (isWhiteTheme) {
            setBackground(bg_white);
        } else {
            setBackground(bg_dark);
        }

        switch (position) {
            case POSITION_LOOKBOOK:
                if (isWhiteTheme) {
                    lookbookImageView.setImageDrawable(icon_lookbook_purple);
                    preorderImageView.setImageDrawable(icon_preorder_gray);
                    marketImageView.setImageDrawable(icon_market_gray);
                    ipageImageView.setImageDrawable(icon_ipage_gray);
                    lookbookTextView.setTextColor(color_FF8140E5);
                    preorderTextView.setTextColor(color_FFA9A9A9);
                    marketTextView.setTextColor(color_FFA9A9A9);
                    ipageTextView.setTextColor(color_FFA9A9A9);
                } else {
                    lookbookImageView.setImageDrawable(icon_lookbook_white);
                    preorderImageView.setImageDrawable(icon_preorder_white);
                    marketImageView.setImageDrawable(icon_market_white);
                    ipageImageView.setImageDrawable(icon_ipage_white);
                    lookbookTextView.setTextColor(color_FFFFFFFF);
                    preorderTextView.setTextColor(color_FFFFFFFF);
                    marketTextView.setTextColor(color_FFFFFFFF);
                    ipageTextView.setTextColor(color_FFFFFFFF);
                }
                break;

            case POSITION_PREORDER:
                lookbookImageView.setImageDrawable(icon_lookbook_gray);
                preorderImageView.setImageDrawable(icon_preorder_purple);
                marketImageView.setImageDrawable(icon_market_gray);
                ipageImageView.setImageDrawable(icon_ipage_gray);
                lookbookTextView.setTextColor(color_FFA9A9A9);
                preorderTextView.setTextColor(color_FF8140E5);
                marketTextView.setTextColor(color_FFA9A9A9);
                ipageTextView.setTextColor(color_FFA9A9A9);
                break;

            case POSITION_MARKET:
                lookbookImageView.setImageDrawable(icon_lookbook_gray);
                preorderImageView.setImageDrawable(icon_preorder_gray);
                marketImageView.setImageDrawable(icon_market_purple);
                ipageImageView.setImageDrawable(icon_ipage_gray);
                lookbookTextView.setTextColor(color_FFA9A9A9);
                preorderTextView.setTextColor(color_FFA9A9A9);
                marketTextView.setTextColor(color_FF8140E5);
                ipageTextView.setTextColor(color_FFA9A9A9);
                break;

            case POSITION_IPAGE:
                lookbookImageView.setImageDrawable(icon_lookbook_gray);
                preorderImageView.setImageDrawable(icon_preorder_gray);
                marketImageView.setImageDrawable(icon_market_gray);
                ipageImageView.setImageDrawable(icon_ipage_purple);
                lookbookTextView.setTextColor(color_FFA9A9A9);
                preorderTextView.setTextColor(color_FFA9A9A9);
                marketTextView.setTextColor(color_FFA9A9A9);
                ipageTextView.setTextColor(color_FF8140E5);
                break;
        }
    }

    @OnClick(R.id.btn_bottombar_lookbook)
    void onLookBookClick() {
        if (position == POSITION_LOOKBOOK) return;
        position = POSITION_LOOKBOOK;
        isWhiteTheme = false;
        updateUI();
        if (onTabChangeListener != null) onTabChangeListener.onTabChanged(position);
    }

    @OnClick(R.id.btn_bottombar_preorder)
    void onPreorderClick() {
        if (position == POSITION_PREORDER) return;
        position = POSITION_PREORDER;
        isWhiteTheme = true;
        updateUI();
        if (onTabChangeListener != null) onTabChangeListener.onTabChanged(position);
    }

    @OnClick(R.id.btn_bottombar_market)
    void onMarketClick() {
        if (position == POSITION_MARKET) return;
        position = POSITION_MARKET;
        isWhiteTheme = true;
        updateUI();
        if (onTabChangeListener != null) onTabChangeListener.onTabChanged(position);
    }

    @OnClick(R.id.btn_bottombar_ipage)
    void onIpageClick() {
        if (position == POSITION_IPAGE) return;
        position = POSITION_IPAGE;
        isWhiteTheme = true;
        updateUI();
        if (onTabChangeListener != null) onTabChangeListener.onTabChanged(position);
    }

    public interface OnTabChangeListener {
        void onTabChanged(int position);
    }
}
