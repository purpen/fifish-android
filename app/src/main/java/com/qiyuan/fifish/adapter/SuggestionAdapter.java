package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiyuan.fifish.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.VH> {
    private List<String> list;
    private Activity activity;

    public SuggestionAdapter(Activity activity, List<String> list) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_add_label, null);
        VH holder = new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(SuggestionAdapter.class.getSimpleName(),list.get(position));
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });
        holder.name.setText("#" + list.get(position) + " ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
