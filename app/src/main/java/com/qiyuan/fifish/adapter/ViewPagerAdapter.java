package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.qiyuan.fifish.ui.activity.MainActivity;
import com.qiyuan.fifish.ui.activity.UserGuideActivity;
import com.qiyuan.fifish.util.ImageLoader;
import com.qiyuan.fifish.util.ToastUtils;

import java.util.List;

public class ViewPagerAdapter<T> extends RecyclingPagerAdapter {
    private final String TAG = getClass().getSimpleName();
    private Activity activity;
    private List<T> list;
    private int size;
    private boolean isInfiniteLoop;
    private String code;

    public int getSize() {
        return size;
    }

    public ViewPagerAdapter(final Activity activity, List<T> list) {
        this.activity = activity;
        this.list = list;
        this.size = list.size();
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : size;
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(activity);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final T content = list.get(getPosition(position));

//        if (content instanceof Banner) {
//            ImageLoader.getInstance().displayImage(((Banner) content).cover_url, holder.imageView, options);
//        }

        if (content instanceof Integer) {
            holder.imageView.setImageResource((Integer) content);
//            ImageLoader.getInstance().displayImage("drawable://"+(Integer) content,holder.imageView,options);
        }

        if (content instanceof String) {
            if (TextUtils.isEmpty((String) content)) {
                ToastUtils.showError("图片链接为空");
            } else {
                ImageLoader.loadImage((String) content, holder.imageView);
            }
        }


        if (activity instanceof UserGuideActivity) {
            if (position == size - 1) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(UserGuideActivity.fromPage)) {
//                            activity.startActivity(new Intent(activity, MainActivity.class));
//                            activity.finish();
                            isNeedCode();
                        } else {
                            UserGuideActivity.fromPage = null;
                            activity.finish();
                        }
                    }
                });
            }
        }

        if (activity instanceof MainActivity) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Banner banner = (Banner) content;
//                    Intent intent;
//                    switch (banner.type) {
//                        case 1:      //url地址
//                            Uri uri = Uri.parse(banner.web_url);
//                            intent = new Intent(Intent.ACTION_VIEW, uri);
//                            activity.startActivity(intent);
//                            break;
//                        case 8:     //场景详情
//                            intent = new Intent(activity, SceneDetailActivity.class);
//                            intent.putExtra("id", banner.web_url);
//                            activity.startActivity(intent);
//                            break;
//                        case 9:     //产品
//                            intent = new Intent(activity, GoodsDetailActivity.class);
//                            intent.putExtra("id", banner.web_url);
//                            activity.startActivity(intent);
//                            break;
//                        case 10:    //情景
//                            intent = new Intent(activity, QingjingDetailActivity.class);
//                            intent.putExtra("id", banner.web_url);
//                            activity.startActivity(intent);
//                        case 11:    //专题
//                            intent = new Intent(activity, SubjectActivity.class);
//                            intent.putExtra(SubjectActivity.class.getSimpleName(), banner.web_url);
//                            intent.putExtra(SubjectActivity.class.getName(), banner.title);
//                            activity.startActivity(intent);
//                            break;
//                    }

                }
            });
        }

        return view;
    }


    /**
     * 提交邀请码
     *
     * @param et
     */
    private void submitCode(final EditText et) {
        code = et.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showError("请输入邀请码");
            return;
        }
//        ClientDiscoverAPI.submitInviteCode(code, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                if (TextUtils.isEmpty(responseInfo.result)) {
//                    return;
//                }
//                HttpResponse response = JsonUtil.fromJson(responseInfo.result, HttpResponse.class);
//                if (response.isSuccess()) {
//                    updateInviteCodeStatus();
//                    return;
//                }
//                ToastUtils.showError(response.getMessage());
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                ToastUtils.showError("网络异常，请确保网络畅通");
//            }
//        });
    }

    private void updateInviteCodeStatus() {
//        ClientDiscoverAPI.updateInviteCodeStatus(code, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                if (TextUtils.isEmpty(responseInfo.result)) {
//                    return;
//                }
//                HttpResponse response = JsonUtil.fromJson(responseInfo.result, HttpResponse.class);
//                if (response.isSuccess()) {
//                    SPUtil.write(DataConstants.INVITE_CODE_TAG, false);
//                    activity.startActivity(new Intent(activity, MainActivity.class));
//                    activity.finish();
//                    return;
//                }
//                ToastUtils.showError(response.getMessage());
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                ToastUtils.showError("网络异常，请确保网络畅通");
//            }
//        });
    }

    /**
     * 判断是否需要输入邀请码
     */
    private void isNeedCode() {
//        ClientDiscoverAPI.isInvited(new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                if (TextUtils.isEmpty(responseInfo.result)) {
//                    return;
//                }
//                HttpResponse<IsInviteData> response = JsonUtil.json2Bean(responseInfo.result, new TypeToken<HttpResponse<IsInviteData>>() {
//                });
//                if (response.isSuccess()) {
//                    if (response.getData().status == 1) {
//                        SPUtil.write(DataConstants.INVITE_CODE_TAG, true);
//                        InputCodeDialog dialog = new InputCodeDialog();
//                        dialog.setCancelable(false);
//                        dialog.setOnCommitClickListener(new InputCodeDialog.OnCommitClickListener() {
//                            @Override
//                            public void execute(View v, EditText et) {
//                                submitCode(et);
//                            }
//                        });
//                        dialog.show(activity.getFragmentManager(), "InputCodeDialog");
//                    } else {
//                        activity.startActivity(new Intent(activity, MainActivity.class));
//                        activity.finish();
//                    }
//                    return;
//                }
//                ToastUtils.showError(response.getMessage());
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                ToastUtils.showError("网络异常，请确保网络畅通");
//            }
//        });
    }

    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ViewPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}