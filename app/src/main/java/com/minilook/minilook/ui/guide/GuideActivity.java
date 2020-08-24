package com.minilook.minilook.ui.guide;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.guide.adapter.GuideAdapter;
import com.minilook.minilook.ui.guide.di.GuideArguments;
import com.minilook.minilook.ui.lookbook.adapter.LookBookPagerAdapter;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;

public class GuideActivity extends BaseActivity implements GuidePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.vp_guide) ViewPager2 viewPager;

    private GuidePresenter presenter;
    private GuideAdapter adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_guide;
    }

    @Override protected void createPresenter() {
        presenter = new GuidePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private GuideArguments provideArguments() {
        return GuideArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupViewPager() {
        viewPager.setAdapter(adapter);
    }
}
