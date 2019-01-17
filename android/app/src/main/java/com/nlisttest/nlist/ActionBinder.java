package com.nlisttest.nlist;

import android.support.annotation.NonNull;
import android.view.View;

public class ActionBinder {

    @SuppressWarnings("UnusedReturnValue")
    public static boolean bind(@NonNull Action action, @NonNull NListItem listItem) {
        View view = listItem.findViewWithTag(action.getViewTag());
        if (view == null) {
            return false;
        }

        switch (action.getActionName()) {
            case "touch":
                view.setOnTouchListener(listItem);
                return true;
            default:
                return false;
        }
    }

}
