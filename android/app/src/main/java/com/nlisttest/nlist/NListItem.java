package com.nlisttest.nlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReadableMap;

public class NListItem extends ReactRootView implements View.OnTouchListener {
    private int position;
    private ReadableMap data;
    private DataBinding[] bindings;
    private Action[] actions;

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
            Toast.makeText(getContext(), "Clicked " + view.getTag() + ", item " + (position + 1), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
