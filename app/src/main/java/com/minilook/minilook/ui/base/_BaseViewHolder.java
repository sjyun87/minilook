package com.minilook.minilook.ui.base;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public class _BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public Context context;
    public Resources resources;
    public T data;
    public int position;

    public _BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        resources = itemView.getResources();
        ButterKnife.bind(this, itemView);
    }

    @CallSuper
    public void bind(T data) {
        this.data = data;
    }

    @CallSuper
    public void bind(int position, T data) {
        this.data = data;
        this.position = position;
    }

    public void onAttach() {
    }

    public void onDetach() {
    }
}
