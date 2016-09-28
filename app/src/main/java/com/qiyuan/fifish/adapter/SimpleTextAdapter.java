package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;

/**
 * @author lilin
 *         created at 2016/9/27 9:37
 */

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<String> list;
    public SimpleTextAdapter(Activity activity, ArrayList<String> list) {
        this.activity = activity;
        this.list = list;
    }
    @Override
    public SimpleTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = Util.inflateView(R.layout.view_bottom_item, null);
        return new SimpleTextAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleTextAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item_text_view)
        TextView mTextView;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
