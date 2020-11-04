package com.minilook.minilook.ui.base;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public Context context;
    public T data;
    public Resources resources;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        resources = itemView.getResources();
        ButterKnife.bind(this, itemView);
    }

    @CallSuper
    public void bind(T $data) {
        this.data = $data;
    }

    public void onAttach() {
    }

    public void onDetach() {
    }
}
