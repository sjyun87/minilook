package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.LayoutBottombarBinding;
import com.minilook.minilook.ui.base.ResourcesProvider;
import lombok.Setter;

public class BottomBar extends LinearLayout {
    public static final int POSITION_LOOKBOOK = 0;
    public static final int POSITION_MARKET = 1;
    public static final int POSITION_CATEGORY = 2;
    public static final int POSITION_PREORDER = 3;
    public static final int POSITION_IPAGE = 4;

    @DrawableRes int bg_white = R.drawable.bg_bottombar_white;
    @DrawableRes int bg_dark = R.drawable.bg_bottombar_dark;

    @DrawableRes int icon_lookbook_purple = R.drawable.ic_bottombar_lookbook_purple;
    @DrawableRes int icon_lookbook_white = R.drawable.ic_bottombar_lookbook_white;
    @DrawableRes int icon_lookbook_gray = R.drawable.ic_bottombar_lookbook_gray;
    @DrawableRes int icon_market_purple = R.drawable.ic_bottombar_market_purple;
    @DrawableRes int icon_market_white = R.drawable.ic_bottombar_market_white;
    @DrawableRes int icon_market_gray = R.drawable.ic_bottombar_market_gray;
    @DrawableRes int icon_category_purple = R.drawable.ic_bottombar_category_purple;
    @DrawableRes int icon_category_white = R.drawable.ic_bottombar_category_white;
    @DrawableRes int icon_category_gray = R.drawable.ic_bottombar_category_gray;
    @DrawableRes int icon_preorder_purple = R.drawable.ic_bottombar_preorder_purple;
    @DrawableRes int icon_preorder_white = R.drawable.ic_bottombar_preorder_white;
    @DrawableRes int icon_preorder_gray = R.drawable.ic_bottombar_preorder_gray;
    @DrawableRes int icon_ipage_purple = R.drawable.ic_bottombar_ipage_purple;
    @DrawableRes int icon_ipage_white = R.drawable.ic_bottombar_ipage_white;
    @DrawableRes int icon_ipage_gray = R.drawable.ic_bottombar_ipage_gray;

    @ColorRes int color_FFFFFFFF = R.color.color_FFFFFFFF;
    @ColorRes int color_FFA9A9A9 = R.color.color_FFA9A9A9;
    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;

    @DimenRes int dp_30 = R.dimen.dp_30;

    private LayoutBottombarBinding binding;
    private ResourcesProvider resources;

    private int position = POSITION_LOOKBOOK;
    private boolean isWhiteTheme;

    @Setter private OnBottomBarListener onBottomBarListener;

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
        setMeasuredDimension(widthMeasureSpec, getResources().getDimensionPixelSize(dp_30));
    }

    private void initView() {
        binding = LayoutBottombarBinding.inflate(LayoutInflater.from(getContext()), this);
        resources = new ResourcesProvider(getContext());
        setGravity(Gravity.CENTER_VERTICAL);
        setupAction();
        updateUI();
    }

    private void setupAction() {
        binding.btnBottombarLookbook.setOnClickListener(view -> onLookBookClick());
        binding.btnBottombarMarket.setOnClickListener(view -> onMarketClick());
        binding.btnBottombarCategory.setOnClickListener(view -> onCategoryClick());
        binding.btnBottombarPreorder.setOnClickListener(view -> onPreorderClick());
        binding.btnBottombarIpage.setOnClickListener(view -> onIpageClick());
    }

    private void updateUI() {
        setBackgroundResource(isWhiteTheme ? bg_white : bg_dark);

        switch (position) {
            case POSITION_LOOKBOOK:
                if (isWhiteTheme) {
                    binding.imgBottombarLookbook.setImageResource(icon_lookbook_purple);
                    binding.imgBottombarMarket.setImageResource(icon_market_gray);
                    binding.imgBottombarCategory.setImageResource(icon_category_gray);
                    binding.imgBottombarPreorder.setImageResource(icon_preorder_gray);
                    binding.imgBottombarIpage.setImageResource(icon_ipage_gray);

                    binding.txtBottombarLookbook.setTextColor(resources.getColor(color_FF8140E5));
                    binding.txtBottombarMarket.setTextColor(resources.getColor(color_FFA9A9A9));
                    binding.txtBottombarCategory.setTextColor(resources.getColor(color_FFA9A9A9));
                    binding.txtBottombarPreorder.setTextColor(resources.getColor(color_FFA9A9A9));
                    binding.txtBottombarIpage.setTextColor(resources.getColor(color_FFA9A9A9));
                } else {
                    binding.imgBottombarLookbook.setImageResource(icon_lookbook_white);
                    binding.imgBottombarMarket.setImageResource(icon_market_white);
                    binding.imgBottombarCategory.setImageResource(icon_category_white);
                    binding.imgBottombarPreorder.setImageResource(icon_preorder_white);
                    binding.imgBottombarIpage.setImageResource(icon_ipage_white);

                    binding.txtBottombarLookbook.setTextColor(resources.getColor(color_FFFFFFFF));
                    binding.txtBottombarMarket.setTextColor(resources.getColor(color_FFFFFFFF));
                    binding.txtBottombarCategory.setTextColor(resources.getColor(color_FFFFFFFF));
                    binding.txtBottombarPreorder.setTextColor(resources.getColor(color_FFFFFFFF));
                    binding.txtBottombarIpage.setTextColor(resources.getColor(color_FFFFFFFF));
                }
                break;

            case POSITION_MARKET:
                binding.imgBottombarLookbook.setImageResource(icon_lookbook_gray);
                binding.imgBottombarMarket.setImageResource(icon_market_purple);
                binding.imgBottombarCategory.setImageResource(icon_category_gray);
                binding.imgBottombarPreorder.setImageResource(icon_preorder_gray);
                binding.imgBottombarIpage.setImageResource(icon_ipage_gray);

                binding.txtBottombarLookbook.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarMarket.setTextColor(resources.getColor(color_FF8140E5));
                binding.txtBottombarCategory.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarPreorder.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarIpage.setTextColor(resources.getColor(color_FFA9A9A9));
                break;

            case POSITION_CATEGORY:
                binding.imgBottombarLookbook.setImageResource(icon_lookbook_gray);
                binding.imgBottombarMarket.setImageResource(icon_market_gray);
                binding.imgBottombarCategory.setImageResource(icon_category_purple);
                binding.imgBottombarPreorder.setImageResource(icon_preorder_gray);
                binding.imgBottombarIpage.setImageResource(icon_ipage_gray);

                binding.txtBottombarLookbook.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarMarket.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarCategory.setTextColor(resources.getColor(color_FF8140E5));
                binding.txtBottombarPreorder.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarIpage.setTextColor(resources.getColor(color_FFA9A9A9));
                break;

            case POSITION_PREORDER:
                binding.imgBottombarLookbook.setImageResource(icon_lookbook_gray);
                binding.imgBottombarMarket.setImageResource(icon_market_gray);
                binding.imgBottombarCategory.setImageResource(icon_category_gray);
                binding.imgBottombarPreorder.setImageResource(icon_preorder_purple);
                binding.imgBottombarIpage.setImageResource(icon_ipage_gray);

                binding.txtBottombarLookbook.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarMarket.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarCategory.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarPreorder.setTextColor(resources.getColor(color_FF8140E5));
                binding.txtBottombarIpage.setTextColor(resources.getColor(color_FFA9A9A9));
                break;

            case POSITION_IPAGE:
                binding.imgBottombarLookbook.setImageResource(icon_lookbook_gray);
                binding.imgBottombarMarket.setImageResource(icon_market_gray);
                binding.imgBottombarCategory.setImageResource(icon_category_gray);
                binding.imgBottombarPreorder.setImageResource(icon_preorder_gray);
                binding.imgBottombarIpage.setImageResource(icon_ipage_purple);

                binding.txtBottombarLookbook.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarMarket.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarCategory.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarPreorder.setTextColor(resources.getColor(color_FFA9A9A9));
                binding.txtBottombarIpage.setTextColor(resources.getColor(color_FF8140E5));
                break;
        }
    }

    public void setWhiteTheme(boolean flag) {
        this.isWhiteTheme = flag;
        updateUI();
    }

    public void setCurrentPage(int position) {
        switch (position) {
            case POSITION_LOOKBOOK:
                onLookBookClick();
                break;
            case POSITION_MARKET:
                onMarketClick();
                break;
            case POSITION_CATEGORY:
                onCategoryClick();
                break;
            case POSITION_PREORDER:
                onPreorderClick();
                break;
            case POSITION_IPAGE:
                onIpageClick();
                break;
        }
    }

    public int getCurrentPage() {
        return position;
    }

    void onLookBookClick() {
        if (position == POSITION_LOOKBOOK) return;
        position = POSITION_LOOKBOOK;
        isWhiteTheme = false;
        updateUI();
        if (onBottomBarListener != null) onBottomBarListener.onBottomTabClick(position);
    }

    void onMarketClick() {
        if (position == POSITION_MARKET) return;
        position = POSITION_MARKET;
        isWhiteTheme = true;
        updateUI();
        if (onBottomBarListener != null) onBottomBarListener.onBottomTabClick(position);
    }

    void onCategoryClick() {
        if (position == POSITION_CATEGORY) return;
        position = POSITION_CATEGORY;
        isWhiteTheme = true;
        updateUI();
        if (onBottomBarListener != null) onBottomBarListener.onBottomTabClick(position);
    }

    void onPreorderClick() {
        if (position == POSITION_PREORDER) return;
        position = POSITION_PREORDER;
        isWhiteTheme = true;
        updateUI();
        if (onBottomBarListener != null) onBottomBarListener.onBottomTabClick(position);
    }

    void onIpageClick() {
        if (position == POSITION_IPAGE) return;
        position = POSITION_IPAGE;
        isWhiteTheme = true;
        updateUI();
        if (onBottomBarListener != null) onBottomBarListener.onBottomTabClick(position);
    }

    public interface OnBottomBarListener {
        void onBottomTabClick(int position);
    }
}
