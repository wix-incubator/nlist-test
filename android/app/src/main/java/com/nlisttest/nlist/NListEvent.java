package com.nlisttest.nlist;

import android.support.annotation.NonNull;

class NListEvent {
    @NonNull
    private String eventName;

    private int index;

    @NonNull
    private String viewTag;

    NListEvent(@NonNull String eventName, int index, @NonNull String viewTag) {
        this.eventName = eventName;
        this.index = index;
        this.viewTag = viewTag;
    }

    @NonNull
    String getViewTag() {
        return viewTag;
    }

    int getIndex() {
        return index;
    }

    @NonNull
    String getEventName() {
        return eventName;
    }
}
