package com.nlisttest.nlist;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

@SuppressLint("ViewConstructor")
public class NList extends RecyclerView implements NListEventListener {
    private ReactNativeHost host;

    private String templates;
    private int height;
    private DataBinding[] bindings;
    private ReadableArray data;
    private Action[] actions;

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

    public void setActions(@NonNull Action[] actions) {
        this.actions = actions;
        createAdapterIfReady();
    }

    private void createAdapterIfReady() {
        if (templates != null && bindings != null && data != null && height != 0 && actions != null) {
            final NListAdapter adapter = new NListAdapter(getContext(), host, templates, height,
                    data, bindings, actions, this);
            setAdapter(adapter);

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.stopPrerendering();
                    smoothScrollToPosition(0);

                    Log.d("NList_bench", "Ready to work: " + System.currentTimeMillis());
                }
            }, 100);

        }
    }

    @Override
    public void onListEvent(@NonNull NListEvent listEvent) {
        WritableMap event = Arguments.createMap();
        event.putString("eventName", listEvent.getEventName());
        event.putInt("index", listEvent.getIndex());
        event.putString("viewTag", listEvent.getViewTag());
        ReactContext reactContext = (ReactContext)getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                "listEvent",
                event);
    }

}
