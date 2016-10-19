package com.qiyuan.fifish.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;

/**
 * Created by android on 2016/9/27.
 */
public class MediaAdapter extends CursorAdapter implements AbsListView.RecyclerListener{
    public MediaAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public MediaAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public MediaAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public void onMovedToScrapHeap(View view) {

    }
}
