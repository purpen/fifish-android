package com.qiyuan.fifish.ui.view;

import android.app.Activity;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.qiyuan.fifish.R;

/**
 * @author lilin
 *         created at 2016/9/26 21:42
 */
public class BottomSheetView {
    public static final int LINEAR_LAYOUT = 0;
    public static final int GRID_LAYOUT = 1;
    public BottomSheetView(Activity context,RecyclerView.Adapter adapter,int layout,int column,String title) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.view_bottom_list, null);
        TextView tvTitle = (TextView)view.findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(title)){
            tvTitle.setVisibility(View.GONE);
        }else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bottom_sheet_recycler_view);
        switch (layout){
            case LINEAR_LAYOUT:
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                break;
            case GRID_LAYOUT:
                recyclerView.setLayoutManager(new GridLayoutManager(context,column));
                break;
            default:
                break;
        }
        recyclerView.setAdapter(adapter);
        dialog.setContentView(view);
        dialog.show();
    }

    public static void dismiss(){

    }

    public static void show(Activity context,RecyclerView.Adapter adapter,int layout) {
        new BottomSheetView(context,adapter,layout,0,null);
    }
    public static void show(Activity context,RecyclerView.Adapter adapter,int layout,String title) {
        new BottomSheetView(context,adapter,layout,0,title);
    }

    public static void show(Activity context,RecyclerView.Adapter adapter,int layout,int column) {
        new BottomSheetView(context,adapter,layout,column,null);
    }
    public static void show(Activity context,RecyclerView.Adapter adapter,int layout,int column,String title) {
        new BottomSheetView(context,adapter,layout,column,title);
    }
}
