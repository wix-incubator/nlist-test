package com.nlisttest.nlist;

import android.support.annotation.NonNull;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.views.image.ReactImageView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class DataBinder {
    @SuppressWarnings("UnusedReturnValue")
    static boolean bind(@NonNull DataBinding dataBinding,
                        @NonNull ReadableMap data,
                        @NonNull View root) {

        final View view = NListHelper.findViewWithNativeId(root, dataBinding.getViewTag());
        if (view == null) {
            return false;
        }

        if (treatSpecialCases(dataBinding, data, view)) {
            return true;
        }

        Method method = dataBinding.getMethod();

        if (method == null) {
            try {
                ReadableType type = data.getType(dataBinding.getDataAttr());
                method = resolveMethod(view.getClass(), dataBinding.getMethodName(), type);
                if (method == null) {
                    return false;
                }

                method.setAccessible(true); // to improve performance of invoke

                dataBinding.setMethod(method);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            method.invoke(view, getValue(data, dataBinding.getDataAttr(), method.getParameterTypes()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static boolean treatSpecialCases(@NonNull final DataBinding dataBinding,
                                             @NonNull final ReadableMap data,
                                             @NonNull View view) {

        if (view instanceof ReactImageView && "setSource".equals(dataBinding.getMethodName())) {
            PipelineDraweeControllerBuilder mControllerBuilder = Fresco.newDraweeControllerBuilder();
            DraweeController controller =
                    mControllerBuilder
                            .setUri(data.getString(dataBinding.getDataAttr()))
                            .setOldController(((ReactImageView) view).getController())
                            .build();
            ((ReactImageView) view).setController(controller);
            return true;
        }

        return false;
    }

    private static Object getValue(@NonNull ReadableMap data,
                                   @NonNull String dataAttr,
                                   @NonNull Class<?>[] parameterTypes) throws IllegalArgumentException {
        if (parameterTypes.length != 1) {
            throw new IllegalArgumentException();
        }

        Class<?> type = parameterTypes[0];

        if (type.isAssignableFrom(String.class)) {
            return data.getString(dataAttr);
        }

        if (type.isAssignableFrom(boolean.class)) {
            return data.getBoolean(dataAttr);
        }

        if (type.isAssignableFrom(double.class)) {
            return data.getDouble(dataAttr);
        }

        if (type.isAssignableFrom(float.class)) {
            return (float) data.getDouble(dataAttr);
        }

        if (type.isAssignableFrom(int.class)) {
            return data.getInt(dataAttr);
        }

        if (type.isAssignableFrom(ReadableArray.class)) {
            return data.getArray(dataAttr);
        }

        throw new IllegalArgumentException();
    }

    private static Method resolveMethod(@NonNull Class<?> clazz,
                                        @NonNull String methodName,
                                        @NonNull ReadableType readableType) throws NoSuchMethodException {

        Class<?>[] types = getClasses(readableType);
        for (Class<?> type : types) {
            try {
                Method method = clazz.getMethod(methodName, type);
                if (method != null) {
                    return method;
                }
            } catch (Exception ignore) {

            }
        }
        return null;
    }

    private static Class<?>[] getClasses(@NonNull ReadableType type) throws NoSuchMethodException {
        switch (type) {
            case Boolean:
                return new Class<?>[]{boolean.class};

            case Number:
                return new Class<?>[]{double.class, float.class, int.class};

            case String:
                return new Class<?>[]{String.class, CharSequence.class};

            case Array:
                return new Class<?>[]{ReadableArray.class};

            default:
                throw new NoSuchMethodException();
        }
    }

}
