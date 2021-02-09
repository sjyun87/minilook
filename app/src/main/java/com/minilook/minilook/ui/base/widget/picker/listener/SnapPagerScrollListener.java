package com.minilook.minilook.ui.base.widget.picker.listener;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class SnapPagerScrollListener extends RecyclerView.OnScrollListener {

    public interface OnChangeListener {
        void onSnapped(int position);
    }

    private final SnapHelper snapHelper;
    private final OnChangeListener listener;
    private int snapPosition;

    public SnapPagerScrollListener(SnapHelper snapHelper, OnChangeListener listener) {
        this.snapHelper = snapHelper;
        this.listener = listener;
        this.snapPosition = RecyclerView.NO_POSITION;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        notifyListenerIfNeeded(getSnapPosition(recyclerView));
    }

    private int getSnapPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return RecyclerView.NO_POSITION;
        }

        View snapView = snapHelper.findSnapView(layoutManager);
        if (snapView == null) {
            return RecyclerView.NO_POSITION;
        }
        return layoutManager.getPosition(snapView);
    }

    private void notifyListenerIfNeeded(int newSnapPosition) {
        if (snapPosition != newSnapPosition) {
            listener.onSnapped(newSnapPosition);
            snapPosition = newSnapPosition;
        }
    }
}