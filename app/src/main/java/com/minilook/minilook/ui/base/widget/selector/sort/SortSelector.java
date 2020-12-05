package com.minilook.minilook.ui.base.widget.selector.sort;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.databinding.LayoutSortSelectorBinding;
import com.minilook.minilook.ui.base.widget.selector.sort.adpater.SortAdapter;
import java.util.List;

public class SortSelector extends FrameLayout {

    private LayoutSortSelectorBinding binding;
    private SortAdapter adapter;

    public SortSelector(Context context) {
        this(context, null);
    }

    public SortSelector(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SortSelector(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView();
        setupRecyclerView();
    }

    private void initView() {
        binding = LayoutSortSelectorBinding.inflate(LayoutInflater.from(getContext()), this);
        binding.curtain.setOnClickListener(view -> hide());
    }

    private void setupRecyclerView() {
        binding.rcvSort.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SortAdapter();
        binding.rcvSort.setAdapter(adapter);
    }

    private void showCurtain() {
        binding.curtain.setVisibility(View.VISIBLE);
    }

    private void hideCurtain() {
        binding.curtain.setVisibility(View.GONE);
    }

    public void bind(List<CodeDataModel> data, OnSortSelectListener listener) {
        adapter.setOnSortSelectListener(listener);

        adapter.set(data);
        adapter.refresh();
    }

    public boolean isShow() {
        return binding.curtain.getVisibility() == VISIBLE;
    }

    public void show() {
        YoYo.with(Techniques.SlideInUp)
            .duration(150)
            .onStart(animator -> {
                showCurtain();
                binding.layoutSortPanel.setVisibility(View.VISIBLE);
            })
            .playOn(binding.layoutSortPanel);
    }

    public void hide() {
        YoYo.with(Techniques.SlideOutDown)
            .duration(150)
            .onEnd(animator -> {
                binding.layoutSortPanel.setVisibility(View.GONE);
                hideCurtain();
            })
            .playOn(binding.layoutSortPanel);
    }

    public interface OnSortSelectListener {
        void onSortSelected(CodeDataModel data);
    }
}


