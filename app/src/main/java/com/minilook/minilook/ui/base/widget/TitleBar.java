package com.minilook.minilook.ui.base.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.LayoutTitlebarBinding;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.main.MainActivity;
import com.minilook.minilook.ui.question_write.QuestionWriteActivity;
import com.minilook.minilook.ui.search_filter.SearchFilterActivity;
import com.minilook.minilook.ui.setting.SettingActivity;
import com.minilook.minilook.ui.shoppingbag.ShoppingBagActivity;
import com.minilook.minilook.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

public class TitleBar extends ConstraintLayout {

    @ColorRes int color_FFFFFFFF = R.color.color_FFFFFFFF;

    @Getter private LayoutTitlebarBinding binding;

    private final Activity activity;

    private boolean isShowTitle;
    private boolean isShowCount;
    private boolean isShowLogo;
    private boolean isShowBack;
    private boolean isShowHome;
    private boolean isShowKeywordSearch;
    private boolean isShowFilterSearch;
    private boolean isShowShoppingBag;
    private boolean isShowSetting;
    private boolean isShowClose;
    private boolean isShowWrite;
    private boolean isShowShare;
    private String title;
    private int count;

    @Setter private int productNo;

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
        isShowTitle = typedArray.getBoolean(R.styleable.TitleBar_showTitle, false);
        isShowCount = typedArray.getBoolean(R.styleable.TitleBar_showCount, false);
        isShowLogo = typedArray.getBoolean(R.styleable.TitleBar_showLogo, false);
        isShowBack = typedArray.getBoolean(R.styleable.TitleBar_showBack, false);
        isShowHome = typedArray.getBoolean(R.styleable.TitleBar_showHome, false);
        isShowKeywordSearch = typedArray.getBoolean(R.styleable.TitleBar_showKeywordSearch, false);
        isShowFilterSearch = typedArray.getBoolean(R.styleable.TitleBar_showFilterSearch, false);
        isShowShoppingBag = typedArray.getBoolean(R.styleable.TitleBar_showShoppingBag, false);
        isShowSetting = typedArray.getBoolean(R.styleable.TitleBar_showSetting, false);
        isShowClose = typedArray.getBoolean(R.styleable.TitleBar_showClose, false);
        isShowWrite = typedArray.getBoolean(R.styleable.TitleBar_showWrite, false);
        isShowShare = typedArray.getBoolean(R.styleable.TitleBar_showShare, false);
        title = typedArray.getString(R.styleable.TitleBar_setTitle);
        count = typedArray.getInteger(R.styleable.TitleBar_setCount, 0);
        typedArray.recycle();

        initView();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, getResources().getDimensionPixelSize(R.dimen.dp_30));
    }

    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getResources().getDimensionPixelSize(R.dimen.dp_30);
        setLayoutParams(params);
    }

    private void initView() {
        binding = LayoutTitlebarBinding.inflate(LayoutInflater.from(getContext()), this);
        setBackgroundColor(ContextCompat.getColor(getContext(), color_FFFFFFFF));
        setupAction();
        updateUI();
    }

    private void setupAction() {
        binding.imgTitlebarLogo.setOnClickListener(view -> onLogoClick());
        binding.imgTitlebarBack.setOnClickListener(view -> onBackClick());
        binding.imgTitlebarHome.setOnClickListener(view -> onHomeClick());
        binding.imgTitlebarSearchKeyword.setOnClickListener(view -> onKeywordSearchClick());
        binding.imgTitlebarSearchFilter.setOnClickListener(view -> onFilterSearchClick());
        binding.imgTitlebarShoppingbag.setOnClickListener(view -> onShoppingBagClick());
        binding.imgTitlebarSetting.setOnClickListener(view -> onSettingClick());
        binding.imgTitlebarClose.setOnClickListener(view -> onCloseClick());
        binding.imgTitlebarWrite.setOnClickListener(view -> onWriteClick());
        binding.imgTitlebarShare.setOnClickListener(view -> onShareClick());
    }

    private void updateUI() {
        binding.txtTitlebarTitle.setVisibility(isShowTitle ? VISIBLE : GONE);
        binding.txtTitlebarCount.setVisibility(isShowCount ? VISIBLE : GONE);
        binding.imgTitlebarLogo.setVisibility(isShowLogo ? VISIBLE : GONE);
        binding.imgTitlebarBack.setVisibility(isShowBack ? VISIBLE : GONE);
        binding.imgTitlebarHome.setVisibility(isShowHome ? VISIBLE : GONE);
        binding.imgTitlebarSearchKeyword.setVisibility(isShowKeywordSearch ? VISIBLE : GONE);
        binding.imgTitlebarSearchFilter.setVisibility(isShowFilterSearch ? VISIBLE : GONE);
        binding.imgTitlebarShoppingbag.setVisibility(isShowShoppingBag ? VISIBLE : GONE);
        binding.imgTitlebarSetting.setVisibility(isShowSetting ? VISIBLE : GONE);
        binding.imgTitlebarClose.setVisibility(isShowClose ? VISIBLE : GONE);
        binding.imgTitlebarWrite.setVisibility(isShowWrite ? VISIBLE : GONE);
        binding.imgTitlebarShare.setVisibility(isShowShare ? VISIBLE : GONE);

        binding.txtTitlebarTitle.setText(title);
        binding.txtTitlebarCount.setText(StringUtil.toDigit(count));
    }

    public void setShowTitle(boolean visible) {
        isShowTitle = visible;
        updateUI();
    }

    public void setShowCount(boolean visible) {
        isShowCount = visible;
        updateUI();
    }

    public void setShowLogo(boolean visible) {
        isShowLogo = visible;
        updateUI();
    }

    public void setShowBack(boolean visible) {
        isShowBack = visible;
        updateUI();
    }

    public void setShowHome(boolean visible) {
        isShowHome = visible;
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

    public void setShowSetting(boolean visible) {
        isShowSetting = visible;
        updateUI();
    }

    public void setShowClose(boolean visible) {
        isShowClose = visible;
        updateUI();
    }

    public void setShowWrite(boolean visible) {
        isShowWrite = visible;
        updateUI();
    }

    public void setShowShare(boolean visible) {
        isShowShare = visible;
        updateUI();
    }

    public void setTitle(String text) {
        title = text;
        updateUI();
    }

    public void setCount(int number) {
        count = number;
        updateUI();
    }

    private void onLogoClick() {
    }

    private void onBackClick() {
        if (activity != null) activity.finish();
    }

    private void onHomeClick() {
        if (activity != null) MainActivity.start(activity, BottomBar.POSITION_MARKET);
    }

    private void onKeywordSearchClick() {
    }

    private void onFilterSearchClick() {
        if (activity != null) SearchFilterActivity.start(activity);
    }

    private void onShoppingBagClick() {
        if (activity != null) {
            if (App.getInstance().isLogin()) {
                ShoppingBagActivity.start(activity);
            } else {
                LoginActivity.start(activity);
            }
        }
    }

    private void onSettingClick() {
        if (activity != null) SettingActivity.start(activity);
    }

    private void onCloseClick() {
        if (activity != null) activity.finish();
    }

    private void onWriteClick() {
        if (activity != null) {
            if (App.getInstance().isLogin()) {
                QuestionWriteActivity.start(activity, productNo);
            } else {
                LoginActivity.start(activity);
            }
        }
    }

    private void onShareClick() {
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
