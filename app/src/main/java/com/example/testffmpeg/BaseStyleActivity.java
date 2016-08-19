package com.example.testffmpeg;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import org.xutils.x;


public abstract class BaseStyleActivity extends FragmentActivity {
	protected static final String TAG = "BaseStyleActivity";
	protected ActionBar actionBar;
	private Class mActivtyClass = null;
	private Runnable mBackAction = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		actionBar = getActionBar();
//		if (actionBar != null) {
//			actionBar.setCustomView(R.layout.custom_action_bar);
//			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//			actionBar.setDisplayShowCustomEnabled(true);
//		}		
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		setTitle(getTitle());
//		showBackButton(false);
	}
	public void setBackActivtyClass(Class c){
		mActivtyClass = c;
//		showBackButton(c==null?false:true);
	}
	public void setBackAction(Runnable r){
		mBackAction = r;
//		showBackButton(r==null?false:true);		
	}
	protected boolean clickBackButton(){
//		if (actionBar != null) {
//			View back = actionBar.getCustomView()
//					.findViewById(R.id.back);
//			if (back != null && back.getVisibility() == View.VISIBLE){
//				back.performClick();
//				return true;
//			}
//		}
		return false;
	}
	/*public void showBackButton(boolean show){
		if (actionBar != null) {
			View back = actionBar.getCustomView()
					.findViewById(R.id.back);
			if (back != null){
				back.setVisibility(show?View.VISIBLE:View.INVISIBLE);
				if(show){
					back.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if(mBackAction != null)
								mBackAction.run();
							else{
								BaseStyleActivity.this.finish();
								if(mActivtyClass != null){
									Intent intent = new Intent();
									intent.setClass(BaseStyleActivity.this, mActivtyClass);
									BaseStyleActivity.this.startActivity(intent);
								}
							}
						}
					});
				}
			}
		}		
	}*/
	
	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
//		if (actionBar != null) {
//			TextView textView = (TextView) actionBar.getCustomView()
//					.findViewById(R.id.title);
//			if (textView != null)
//				textView.setText(title);
//		}
	}
	@Override
	public void setTitle(int titleId) {
		super.setTitle(titleId);
//		if (actionBar != null) {
//			TextView textView = (TextView) actionBar.getCustomView()
//					.findViewById(R.id.title);
//			if (textView != null)
//				textView.setText(titleId);
//		}
	}
}
