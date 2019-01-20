package com.nlisttest.nlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

public class ReactNListManager extends SimpleViewManager<NList> {

    private static final String REACT_CLASS = "RCTNList";
    private static final String LOG_TAG = ReactNListManager.class.getSimpleName();

    private Context context;
    private ReactNativeHost host;

    @SuppressWarnings("WeakerAccess")
    public ReactNListManager(ReactNativeHost host) {
        this.host = host;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected NList createViewInstance(ThemedReactContext reactContext) {
        this.context = reactContext;
        return new NList(reactContext, host);
    }

    @SuppressWarnings("unused")
    @ReactProp(name = "items")
    public void setItemTemplates(@NonNull NList list, @Nullable String itemTemplates) {
        if (TextUtils.isEmpty(itemTemplates)) {
            throw new IllegalArgumentException("itemTemplates is empty");
        }

        Log.d(LOG_TAG, "itemTemplates: " + itemTemplates);
        list.setItemTemplates(itemTemplates);
    }

    @SuppressWarnings("unused")
    @ReactProp(name = "itemHeight")
    public void setItemHeight(@NonNull NList list, int height) {
        Log.d(LOG_TAG, "itemHeight: " + height);

        list.setItemHeight(px(context, height));
    }

    @SuppressWarnings("unused")
    @ReactProp(name = "data")
    public void setData(@NonNull NList list, @Nullable ReadableArray data) {
        if (data == null) {
            throw new IllegalArgumentException("data is empty");
        }

        Log.d(LOG_TAG, "data: " + data);

        list.setData(data);
    }

    @SuppressWarnings("unused")
    @ReactProp(name = "binding")
    public void setBinding(@NonNull NList list, @Nullable ReadableArray bindingArray) {
        if (bindingArray == null || bindingArray.size() == 0) {
            throw new IllegalArgumentException("binding is empty");
        }

        Log.d(LOG_TAG, "binding: " + bindingArray);

        DataBinding[] bindings = new DataBinding[bindingArray.size()];
        for (int i = 0; i < bindingArray.size(); i++) {
            ReadableMap map = bindingArray.getMap(i);
            String attrName = map.getString("data");
            String view = map.getString("view");
            String[] viewDetails = view.split("\\.");
            String viewTag = viewDetails[0];
            String methodName = "set" + capFirstChar(viewDetails[1]);

            bindings[i] = new DataBinding(attrName, viewTag, methodName);
        }

        list.setBindings(bindings);
    }

    @SuppressWarnings("unused")
    @ReactProp(name = "actions")
    public void setActions(@NonNull NList list, @Nullable ReadableArray actionsArray) {
        if (actionsArray == null) {
            throw new IllegalArgumentException("actions is absent");
        }

        Log.d(LOG_TAG, "actions: " + actionsArray);

        Action[] actions = new Action[actionsArray.size()];
        for (int i = 0; i < actionsArray.size(); i++) {
            ReadableMap map = actionsArray.getMap(i);
            String actionString = map.getString("action");
            String[] actionDetails = actionString.split("\\.");
            String viewTag = actionDetails[0];
            String actionName = actionDetails[1];

            actions[i] = new Action(viewTag, actionName);
        }

        list.setActions(actions);
    }

    @javax.annotation.Nullable
    @Override
    public Map<String, Object> getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.<String, Object>builder()
                .put("listEvent",
                        MapBuilder.of(
                                "phasedRegistrationNames",
                                MapBuilder.of("bubbled", "onListEvent")))
                .build();
    }

    @NonNull
    private String capFirstChar(@NonNull String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private int px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
