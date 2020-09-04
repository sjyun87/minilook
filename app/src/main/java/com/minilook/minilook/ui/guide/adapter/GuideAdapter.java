package com.minilook.minilook.ui.guide.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.ui.guide.viewholder.GuideVH;

public class GuideAdapter extends RecyclerView.Adapter<GuideVH> {

    @NonNull @Override public GuideVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GuideVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull GuideVH holder, int position) {
        holder.bind(position);
    }

    @Override public int getItemCount() {
        return 4;
    }
}
