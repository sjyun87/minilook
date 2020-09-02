package com.minilook.minilook.ui.lookbook.view.preview;

import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.lookbook.view.preview.adapter.LookBookModuleAdapter;
import com.minilook.minilook.ui.lookbook.view.preview.di.LookBookPreviewArguments;

public class LookBookPreviewFragment extends BaseFragment implements LookBookPreviewPresenter.View {

    public static LookBookPreviewFragment newInstance() {
        return new LookBookPreviewFragment();
    }

    @BindView(R.id.viewpager) ViewPager2 viewPager;

    private LookBookPreviewPresenter presenter;
    private LookBookModuleAdapter adapter = new LookBookModuleAdapter();
    private BaseAdapterDataView<LookBookModuleDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_lookbook_preview;
    }

    @Override protected void createPresenter() {
        presenter = new LookBookPreviewPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private LookBookPreviewArguments provideArguments() {
        return LookBookPreviewArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void onDestroyView() {
        viewPager.unregisterOnPageChangeCallback(OnPageChangeCallback);
        super.onDestroyView();
    }

    @Override public void setupViewPager() {
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.registerOnPageChangeCallback(OnPageChangeCallback);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int start, int end) {
        viewPager.post(() -> adapterView.refresh(start, end));

    }

    private ViewPager2.OnPageChangeCallback OnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override public void onPageSelected(int position) {
            presenter.onPageSelected(position);
        }
    };
}
