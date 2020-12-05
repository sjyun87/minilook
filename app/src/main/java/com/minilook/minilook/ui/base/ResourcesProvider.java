package com.minilook.minilook.ui.base;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class ResourcesProvider {

    private final Context context;

    public ResourcesProvider(Context context) {
        this.context = context;
    }

    public int getColor(@ColorRes int id) {
        return ContextCompat.getColor(context, id);
    }

    public Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(context, id);
    }

    public Typeface getFont(@FontRes int id) {
        return ResourcesCompat.getFont(context, id);
    }

    public String getString(@StringRes int id) {
        return context.getResources().getString(id);
    }

    public int getDimen(@DimenRes int id) {
        return context.getResources().getDimensionPixelSize(id);
    }
}
