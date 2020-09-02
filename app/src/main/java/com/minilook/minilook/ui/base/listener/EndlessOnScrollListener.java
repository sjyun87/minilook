package com.minilook.minilook.ui.base.listener;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import lombok.Builder;

public class EndlessOnScrollListener extends RecyclerView.OnScrollListener {

    private int currentItemCount = 0;
    private int visibleThreshold = 1;
    private int rows = 10;
    private RecyclerView.LayoutManager layoutManager;
    private OnLoadMoreListener onLoadMoreListener;

    @Builder
    private EndlessOnScrollListener(RecyclerView.LayoutManager layoutManager, OnLoadMoreListener onLoadMoreListener,
        int visibleThreshold, int rows) {
        this.layoutManager = layoutManager;
        this.onLoadMoreListener = onLoadMoreListener;
        this.visibleThreshold = visibleThreshold;
        this.rows = rows;
    }

    @Override public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        final int itemCount = layoutManager.getItemCount();
        final int lastVisibleItem = getLastVisibleItem();
        if (currentItemCount != itemCount && itemCount != 0 && lastVisibleItem != -1
            && rows <= itemCount
            && itemCount <= (lastVisibleItem + visibleThreshold)) {

            currentItemCount = itemCount;

            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore();
            }
        }
    }

    private int getLastVisibleItem() {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else {
            try {
                return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } catch (ClassCastException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
