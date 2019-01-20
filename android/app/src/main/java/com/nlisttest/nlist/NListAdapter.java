package com.nlisttest.nlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReadableArray;

public class NListAdapter extends RecyclerView.Adapter<NListAdapter.ViewHolder> {

    private Context context;
    private ReactNativeHost host;
    private String template;
    private int height;
    private ReadableArray data;
    private DataBinding[] bindings;
    private Action[] actions;

    @NonNull
    private NListEventListener listener;

    private boolean prerendering;

    NListAdapter(Context context,
                 ReactNativeHost host,
                 String templates,
                 int height,
                 ReadableArray data,
                 DataBinding[] bindings,
                 Action[] actions,
                 @NonNull NListEventListener listener) {
        this.context = context;
        this.host = host;
        this.template = templates;
        this.height = height;
        this.data = data;
        this.bindings = bindings;
        this.actions = actions;
        this.listener = listener;

        this.prerendering = true;
    }

    private NListItem[] items = new NListItem[20];
    private int index = 0;

    private FrameLayout frameLayout;


    void stopPrerendering() {
        prerendering = false;
        frameLayout.removeAllViews();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return prerendering ? 0 : 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        NListItem view = new NListItem(context, height);
//        view.startReactApplication(host.getReactInstanceManager(), template);

        View view;
        if (viewType == 0) {
            frameLayout = new FrameLayout(context);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1, 1000);
            frameLayout.setLayoutParams(lp);
            for (int i = 0; i < items.length; i++) {
                NListItem listItem = new NListItem(context, height, listener);
                listItem.startReactApplication(host.getReactInstanceManager(), template);
                items[i] = listItem;
                listItem.setVisibility(View.INVISIBLE);
                frameLayout.addView(listItem);
            }

            view = frameLayout;
        } else {
            view = items[index++];
            view.setVisibility(View.VISIBLE);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!prerendering) {
            holder.root.setData(position, data.getMap(position), bindings, actions);
        }
    }

    @Override
    public int getItemCount() {
        return prerendering ? 1 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        NListItem root;

        ViewHolder(View itemView) {
            super(itemView);

            root = itemView instanceof NListItem ? (NListItem) itemView : null;
        }
    }

}
