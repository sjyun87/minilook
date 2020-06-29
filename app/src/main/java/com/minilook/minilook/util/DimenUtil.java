package com.minilook.minilook.util;

import android.content.Context;
import android.util.TypedValue;

public class DimenUtil {

    public static int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
            context.getResources().getDisplayMetrics());
    }

    public static int spToPx(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
            context.getResources().getDisplayMetrics());
    }

    public static int dpToSp(Context context, float dp) {
        return (int) (dpToPx(context, dp) / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
