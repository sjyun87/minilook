package com.minilook.minilook.ui.base;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import butterknife.ButterKnife;
import butterknife.Optional;

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public Context context;
    public Resources resources;
    public T data;

    public BaseViewHolder(@NonNull ViewBinding $binding) {
        super($binding.getRoot());
        context = itemView.getContext();
        resources = itemView.getResources();
    }

    @CallSuper
    public void bind(T $data) {
        this.data = $data;
    }

    // Optional
    public void bind(int position, T $data) {
    }

    public void onAttach() {
    }

    public void onDetach() {
    }
}
