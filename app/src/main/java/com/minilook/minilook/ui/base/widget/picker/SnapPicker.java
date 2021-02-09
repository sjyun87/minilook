package com.minilook.minilook.ui.base.widget.picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.DimenRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.LayoutSnapPickerBinding;
import com.minilook.minilook.ui.base.ResourcesProvider;
import com.minilook.minilook.ui.base.widget.picker.adapter.SnapPickerAdapter;
import com.minilook.minilook.ui.base.widget.picker.listener.SnapPagerScrollListener;
import com.minilook.minilook.ui.base.widget.picker.viewholder.SnapPickerItemVH;
import com.minilook.minilook.util.DeviceUtil;
import java.util.List;
import lombok.Setter;

public class SnapPicker extends FrameLayout implements SnapPagerScrollListener.OnChangeListener {

    @DimenRes int dp_40 = R.dimen.dp_40;

    private LayoutSnapPickerBinding binding;
    private ResourcesProvider resources;
    private SnapPickerAdapter adapter;

    private List<Integer> data;
    private int selectedPosition = 0;

    @Setter private OnPickChangeListener onPickChangeListener;

    public SnapPicker(Context context) {
        this(context, null);
    }

    public SnapPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnapPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SnapPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView();
        setupRecyclerView();
    }

    private void initView() {
        binding = LayoutSnapPickerBinding.inflate(LayoutInflater.from(getContext()), this);
        resources = new ResourcesProvider(getContext());

        binding.curtain.setOnClickListener(view -> hide());
        binding.imgClose.setOnClickListener(view -> hide());
        binding.txtApply.setOnClickListener(view -> {
            if (onPickChangeListener != null) onPickChangeListener.onPick(data.get(selectedPosition));
            hide();
        });
    }

    private void setupRecyclerView() {
        int padding = (DeviceUtil.getDeviceWidth(getContext()) / 2) - (resources.getDimen(dp_40) / 2);
        binding.rcvPicker.setPadding(padding, 0, padding, 0);
        binding.rcvPicker.setHasFixedSize(true);
        binding.rcvPicker.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        adapter = new SnapPickerAdapter();
        binding.rcvPicker.setAdapter(adapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.rcvPicker);
        binding.rcvPicker.addOnScrollListener(new SnapPagerScrollListener(snapHelper, this));
    }

    private void showCurtain() {
        binding.curtain.setVisibility(View.VISIBLE);
    }

    private void hideCurtain() {
        binding.curtain.setVisibility(View.GONE);
    }

    public boolean isShow() {
        return binding.curtain.getVisibility() == VISIBLE;
    }

    public void setTitle(String title) {
        binding.txtTitle.setText(title);
    }

    public void setUnit(String unit) {
        binding.txtPickUnit.setText(unit);
    }

    public void setData(List<Integer> $data) {
        this.data = $data;
        adapter.set(data);
        adapter.refresh();
    }

    public void setItem(Integer item) {
        this.selectedPosition = adapter.get(item);
        binding.rcvPicker.scrollToPosition(selectedPosition);
    }

    public void show() {
        YoYo.with(Techniques.SlideInUp)
            .duration(150)
            .onStart(animator -> {
                showCurtain();
                binding.layoutPickerPanel.setVisibility(View.VISIBLE);
            })
            .playOn(binding.layoutPickerPanel);
    }

    public void hide() {
        YoYo.with(Techniques.SlideOutDown)
            .duration(150)
            .onEnd(animator -> {
                binding.layoutPickerPanel.setVisibility(View.GONE);
                hideCurtain();
            })
            .playOn(binding.layoutPickerPanel);
    }

    private void unselected(int position) {
        SnapPickerItemVH viewHolder = ((SnapPickerItemVH) binding.rcvPicker.findViewHolderForAdapterPosition(position));
        if (viewHolder != null) viewHolder.unselected();
    }

    private void selected(int position) {
        SnapPickerItemVH viewHolder = ((SnapPickerItemVH) binding.rcvPicker.findViewHolderForAdapterPosition(position));
        if (viewHolder != null) viewHolder.selected();
        binding.txtPick.setText(String.valueOf(data.get(position)));
    }

    @Override public void onSnapped(int position) {
        unselected(selectedPosition);
        selected(position);
        this.selectedPosition = position;
    }

    public interface OnPickChangeListener {
        void onPick(int item);
    }
}


