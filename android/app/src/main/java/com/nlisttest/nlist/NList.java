package com.nlisttest.nlist;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.ThemedReactContext;

@SuppressLint("ViewConstructor")
public class NList extends RecyclerView {
    private ReactNativeHost host;

    private String templates;
    private int height;
    private DataBinding[] bindings;
    private ReadableArray data;

    public NList(ThemedReactContext reactContext, ReactNativeHost host) {
        super(reactContext);
        this.host = host;

        setLayoutManager(new LinearLayoutManager(getContext()));
        setItemViewCacheSize(0);

    }

    public void setItemTemplates(@NonNull String templates) {
        this.templates = templates;
        createAdapterIfReady();
    }

    public void setItemHeight(int height) {
        this.height = height;
        createAdapterIfReady();
    }

    public void setBindings(@NonNull DataBinding[] bindings) {
        this.bindings = bindings;
        createAdapterIfReady();
    }

    public void setData(@NonNull ReadableArray data) {
        this.data = data;
        createAdapterIfReady();
    }

    private void createAdapterIfReady() {
        if (templates != null && bindings != null && data != null && height != 0) {
            final NListAdapter adapter = new NListAdapter(getContext(), host, templates, height, data, bindings);
            setAdapter(adapter);

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.stopPrerendering();
                    smoothScrollToPosition(0);
                }
            }, 100);

        }
    }
}
