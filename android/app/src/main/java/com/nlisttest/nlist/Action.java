package com.nlisttest.nlist;

import android.support.annotation.NonNull;

public class Action {
    @NonNull
    private String viewTag;

    @NonNull
    private String actionName;

    public Action(@NonNull String viewTag, @NonNull String actionName) {
        this.viewTag = viewTag;
        this.actionName = actionName;
    }

    @NonNull
    public String getViewTag() {
        return viewTag;
    }

    @NonNull
    public String getActionName() {
        return actionName;
    }
}
