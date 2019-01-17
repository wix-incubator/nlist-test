package com.nlisttest.nlist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ViewCloner {

    public static View clone(@NonNull Context context, @NonNull View source) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends View> clazz = source.getClass();

        // create instance
        Constructor<?> ctor = clazz.getConstructor(Context.class);
        Object result = ctor.newInstance(context);

        // copy field values
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Log.d("ViewCloner", "Field: " + field.getName());
                Object value = field.get(source);
                field.set(result, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // clone children
        if (source instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) source;
            View[] children = new View[group.getChildCount()];

            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                View childCopy = clone(context, child);
                children[i] = childCopy;
            }

            ViewGroup resultGroup = (ViewGroup)result;

            resultGroup.removeAllViews();
            for (View child : children) {
                resultGroup.addView(child);
            }
        }

        return (View) result;
    }

    public static void test(@NonNull Context context) {
        TextView textView = new TextView(context);
        textView.setBackgroundColor(Color.RED);
        textView.setText("Example");

        try {
            View copy = clone(context, textView);

            Log.d("ViewCloner", "Text: " + ((TextView)copy).getText());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }
}
