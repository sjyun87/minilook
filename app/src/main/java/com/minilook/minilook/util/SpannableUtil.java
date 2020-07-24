package com.minilook.minilook.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

public class SpannableUtil {

    public static SpannableString styleSpan(String text, String target, int style) {
        return styleSpan(new SpannableString(text), target, style);
    }

    public static SpannableString styleSpan(SpannableString span, String target, int style) {
        int startIndex = span.toString().indexOf(target);
        int endIndex = startIndex + target.length();
        span.setSpan(new StyleSpan(style), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static SpannableString foregroundColorSpan(String text, String target, int color) {
        return foregroundColorSpan(new SpannableString(text), target, color);
    }

    public static SpannableString foregroundColorSpan(SpannableString span, String target, int color) {
        int startIndex = span.toString().indexOf(target);
        int endIndex = startIndex + target.length();
        span.setSpan(new ForegroundColorSpan(color), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
}
