package com.minilook.minilook.ui.base;

import android.content.Context;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public Context context;
    public ResourcesProvider resources;
    public T data;
    public int position;

    public BaseViewHolder(@NonNull ViewBinding $binding) {
        super($binding.getRoot());
        context = itemView.getContext();
        resources = new ResourcesProvider(context);
    }

    @CallSuper
    public void bind(T $data) {
        this.data = $data;
    }

    // Optional
    @CallSuper
    public void bind(int position, T $data) {
        this.position = position;
        this.data = $data;
    }

    public void onAttach() {
    }

    public void onDetach() {
    }
}
