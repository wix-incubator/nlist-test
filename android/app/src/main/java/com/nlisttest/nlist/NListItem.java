package com.nlisttest.nlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReadableMap;

public class NListItem extends ReactRootView implements View.OnTouchListener {
    private static final String ROOT_VIEW_TAG = "_item";

    private int position;
    private ReadableMap data;
    private DataBinding[] bindings;
    private Action[] actions;
    private NListEventListener listener;

    public NListItem(Context context, int height, NListEventListener listener) {
        super(context);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1, height);
        this.setLayoutParams(lp);

        this.listener = listener;

        NListHelper.setNativeId(this, ROOT_VIEW_TAG);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        bind();
    }

    public void setData(int position,
                        @NonNull ReadableMap data,
                        @NonNull DataBinding[] bindings,
                        @NonNull Action[] actions) {
        this.position = position;
        this.data = data;
        this.bindings = bindings;
        this.actions = actions;
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

        if (actions != null) {
            for (Action action : actions) {
                ActionBinder.bind(action, this);
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (MotionEvent.ACTION_UP == motionEvent.getAction()) {
            String nativeId = NListHelper.getNativeId(view);
            if (nativeId != null) {
                listener.onListEvent(new NListEvent("touch", position, nativeId));
            }
        }
        return true;
    }
}
