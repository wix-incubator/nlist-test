package com.nlisttest.nlist;

import android.support.annotation.NonNull;

import java.lang.reflect.Method;

import javax.annotation.Nullable;

class DataBinding {
    @NonNull
    private String dataAttr;

    @NonNull
    private String viewTag;

    @NonNull
    private String methodName;

    @Nullable
    private Method method;

    DataBinding(@NonNull String dataAttr, @NonNull String viewTag, @NonNull String methodName) {
        this.dataAttr = dataAttr;
        this.viewTag = viewTag;
        this.methodName = methodName;
    }

    @NonNull
    String getDataAttr() {
        return dataAttr;
    }

    @NonNull
    String getViewTag() {
        return viewTag;
    }

    @NonNull
    String getMethodName() {
        return methodName;
    }

    @Nullable
    Method getMethod() {
        return method;
    }

    void setMethod(@NonNull Method method) {
        this.method = method;
    }
}
