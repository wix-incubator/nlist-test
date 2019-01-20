package com.nlisttest.nlist;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

class NListHelper {
    static View findViewWithNativeId(@NonNull View root, @NonNull String nativeId) {
        Object tag = root.getTag(com.facebook.react.R.id.view_tag_native_id);
        if (nativeId.equals(tag)) {
            return root;
        }

        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                View view = findViewWithNativeId(child, nativeId);
                if (view != null) {
                    return view;
                }
            }
        }

        return null;
    }

    static String getNativeId(@NonNull View view) {
        Object tag = view.getTag(com.facebook.react.R.id.view_tag_native_id);
        return tag instanceof String ? (String) tag : null;
    }

    static void setNativeId(@NonNull View view, @NonNull String tag) {
        view.setTag(com.facebook.react.R.id.view_tag_native_id, tag);
    }
}
