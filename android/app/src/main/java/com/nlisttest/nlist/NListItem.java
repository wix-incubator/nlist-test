package com.nlisttest.nlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReadableMap;

public class NListItem extends ReactRootView {
    private ReadableMap data;
    private DataBinding[] bindings;

    public NListItem(Context context, int height) {
        super(context);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1, height);
        this.setLayoutParams(lp);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        bind();
    }

    public void setData(@NonNull ReadableMap data, @NonNull DataBinding[] bindings) {
        this.data = data;
        this.bindings = bindings;
        bind();
    }

    void bind() {
        post(new Runnable() {
            @Override
            public void run() {
                _bind();
            }
        });
    }

    private void _bind() {
        if (data != null && bindings != null) {
            for (DataBinding binding : bindings) {
                DataBinder.bind(binding, data, this);
            }
        }
    }

}
