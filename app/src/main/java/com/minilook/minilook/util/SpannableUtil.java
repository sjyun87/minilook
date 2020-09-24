package com.minilook.minilook.util;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import androidx.annotation.NonNull;

public class SpannableUtil {

    public static SpannableString styleSpan(String text, String target, int style) {
        return styleSpan(new SpannableString(text), target, style);
    }

    public static SpannableString styleSpan(SpannableString span, String target, int style) {
        int startIndex = span.toString().indexOf(target);
        int endIndex = startIndex + target.length();
        if (startIndex == -1) return span;
        span.setSpan(new StyleSpan(style), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static SpannableString foregroundColorSpan(String text, String target, int color) {
        return foregroundColorSpan(new SpannableString(text), target, color);
    }

    public static SpannableString foregroundColorSpan(SpannableString span, String target, int color) {
        int startIndex = span.toString().indexOf(target);
        int endIndex = startIndex + target.length();
        if (startIndex == -1) return span;
        span.setSpan(new ForegroundColorSpan(color), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static SpannableString fontSpan(String text, String target, Typeface font) {
        return fontSpan(new SpannableString(text), target, font);
    }

    public static SpannableString fontSpan(SpannableString span, String target, Typeface font) {
        int startIndex = span.toString().indexOf(target);
        int endIndex = startIndex + target.length();
        if (startIndex == -1) return span;
        span.setSpan(new FontSpan(font), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    private static class FontSpan extends TypefaceSpan {

        private static final int WRONG_TYPEFACE = 0;
        private Typeface font;

        public FontSpan(@NonNull Typeface typeface) {
            super("");
            font = typeface;
        }

        @Override public void updateMeasureState(@NonNull TextPaint textPaint) {
            updateTypeface(textPaint);
        }

        @Override public void updateDrawState(TextPaint textPaint) {
            updateTypeface(textPaint);
        }

        private void updateTypeface(TextPaint textPaint) {
            int oldStyle = getOldStyle();
            if (oldStyle != WRONG_TYPEFACE) {
                font = Typeface.create(font, oldStyle);
            }
            textPaint.setTypeface(font);
        }

        private int getOldStyle() {
            if (font != null) {
                return font.getStyle();
            } else {
                return WRONG_TYPEFACE;
            }
        }
    }
}
