package com.minilook.minilook.ui.base.widget.picker.age;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.ArrayRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.LayoutAgePickerBinding;
import com.minilook.minilook.ui.base.ResourcesProvider;
import com.minilook.minilook.ui.base.widget.picker.SnapPagerScrollListener;
import com.minilook.minilook.ui.base.widget.picker.age.adapter.AgePickerAdapter;
import com.minilook.minilook.util.DeviceUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Setter;

public class AgePicker extends FrameLayout implements SnapPagerScrollListener.OnChangeListener {

    @DimenRes int dp_60 = R.dimen.dp_60;

    @StringRes int str_age_format = R.string.age_picker_format;
    @ArrayRes int arr_age = R.array.picker_age;

    private LayoutAgePickerBinding binding;
    private ResourcesProvider resources;
    private AgePickerAdapter adapter;

    private List<String> ages;

    @Setter private OnPickChangeListener onPickChangeListener;

    public AgePicker(Context context) {
        this(context, null);
    }

    public AgePicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AgePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AgePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView();
        setupRecyclerView();
        setData();
    }

    private void initView() {
        binding = LayoutAgePickerBinding.inflate(LayoutInflater.from(getContext()), this);
        resources = new ResourcesProvider(getContext());

        binding.curtain.setOnClickListener(view -> hide());
    }

    private void setupRecyclerView() {
        int padding = (DeviceUtil.getDeviceWidth(getContext()) / 2) - (resources.getDimen(dp_60) / 2);
        binding.rcvPicker.setPadding(padding, 0, padding, 0);
        binding.rcvPicker.setHasFixedSize(true);
        binding.rcvPicker.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        adapter = new AgePickerAdapter();
        binding.rcvPicker.setAdapter(adapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.rcvPicker);
        binding.rcvPicker.addOnScrollListener(new SnapPagerScrollListener(snapHelper, this));
    }

    private void setData() {
        ages = new ArrayList<>();
        Collections.addAll(ages, resources.getStringArray(arr_age));
        adapter.set(ages);
        adapter.refresh();
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

    public void setSelectedAge(String age) {
        binding.txtAge.setText(String.format(resources.getString(str_age_format), age));
    }

    @Override public void onSnapped(int position) {
        setSelectedAge(ages.get(position));
    }

    public interface OnPickChangeListener {
        void onChanged(int age);
    }
}


