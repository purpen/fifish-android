package com.album;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bean.Folder;
import com.bean.Image;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.FolderAdapter;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.util.ActivityUtil;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class OptionAlbumActivity extends AppCompatActivity {
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 100;
    public static final String TAG = "OptionAlbumActivity";
    private CustomHeadView mHeadView;
    // loaders
    private int LOADER_ALL_PHOTO = 0;
    private int LOADER_CATEGORY_PHOTO = 1;
    private int LOADER_ALL_VIDEO = 2;
    private ListView mListView;
    private FolderAdapter mFolderAdapter;
    private boolean hasFolderGened = false;
    private List<Image> imagesLists = new ArrayList<>();
    private boolean isVideo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_album);

        ActivityUtil.getInstance().addActivity(this);
        mHeadView = (CustomHeadView) findViewById(R.id.head_album_all);
        mHeadView.setHeadCenterTxtShow(true, getString(R.string.option_album));
        mHeadView.setHeadGoBackShow(true);
        if (mayRedExternalStorage()) {
            loadMediaResource();
        }
        mListView = (ListView) findViewById(R.id.list_album);
        mFolderAdapter = new FolderAdapter(this, isVideo);
        mListView.setAdapter(mFolderAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;
                final AdapterView v = adapterView;
                // 第一条item是所有照片，所以得专门传一个所有照片的集合
                if (index == 0) {
//                    OptionAlbumActivity.this.getSupportLoaderManager().restartLoader(LOADER_ALL_PHOTO, null, mLoaderCallback);
                    Intent intent = new Intent();
                    intent.setClass(OptionAlbumActivity.this, OptionPhotoActivity.class);
                    intent.putExtra("list", (Serializable) imagesLists);
                    intent.putExtra("isVideo", isVideo);
                    startActivity(intent);
                }
                Folder folder = (Folder) v.getAdapter().getItem(index);
                if (null != folder) {
                    Intent intent = new Intent();
                    intent.setClass(OptionAlbumActivity.this, OptionPhotoActivity.class);
                    intent.putExtra("list", (Serializable) folder.images);
                    intent.putExtra("isVideo", isVideo);
                    startActivity(intent);
                }
            }
        });

    }


    private void loadMediaResource() {
        switch ((int) getIntent().getExtras().get("album")) {
            case 0:
                isVideo = false;
                this.getSupportLoaderManager().initLoader(LOADER_ALL_PHOTO, null, mLoaderCallback);
                break;
            case 2:
                isVideo = true;
                this.getSupportLoaderManager().initLoader(LOADER_ALL_VIDEO, null, mLoaderCallback);
                break;
        }
    }

    private boolean mayRedExternalStorage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        return false;
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID,};

        private final String[] VIDEO_PROJECTION = {
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DURATION};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            /**
             * new CursorLoader(this, uri, null, null, null, null);
             【备注：】new CursorLoader()的参数：
             1、 uri —要获取的内容的URI；
             2、projection —要返回的列组成的数组。传入null 将会返回所有的列，但这样会导致低效；
             3、selection —表明哪些行将被返回，相当于SQL语句中的WHERE条件 (不包括WHERE关键词)。传入null 将返回所有的行；
             4、selectionArgs —Where语句中的'?’组成的数组。
             5、sortOrder —如何排序，相当于SQL语句中的 ORDER BY 语句(不包括ORDER BY关键词)。传入null将使用默认顺序。
             */
            CursorLoader cursorLoader = null;
            if (id == LOADER_ALL_PHOTO) {
                cursorLoader = new CursorLoader(OptionAlbumActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR " + IMAGE_PROJECTION[3] + "=? ",
                        new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
            } else if (id == LOADER_CATEGORY_PHOTO) {
                cursorLoader = new CursorLoader(OptionAlbumActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'",
                        null, IMAGE_PROJECTION[2] + " DESC");
            } else if (id == LOADER_ALL_VIDEO) {
                cursorLoader = new CursorLoader(OptionAlbumActivity.this,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECTION,
                        null,
                        null, VIDEO_PROJECTION[2] + " DESC");
            }
            return cursorLoader;
        }

        private boolean fileExist(String path) {
            if (!TextUtils.isEmpty(path)) {
                return new File(path).exists();
            }
            return false;
        }

        List<Image> images = null;

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                if (data.getCount() > 0) {
                    images = new ArrayList<>();
                    data.moveToFirst();
                    do {
                        String path, name;
                        long dateTime;//生成文件的日期
                        int id;

                        //视频的时长
                        long videoDuration = 0;
                        if (isVideo) {
                            videoDuration = data.getLong(data.getColumnIndexOrThrow(VIDEO_PROJECTION[6])) / 1000;//毫秒转为秒
                            path = data.getString(data.getColumnIndexOrThrow(VIDEO_PROJECTION[0]));
                            name = data.getString(data.getColumnIndexOrThrow(VIDEO_PROJECTION[1]));
                            dateTime = data.getLong(data.getColumnIndexOrThrow(VIDEO_PROJECTION[2]));//生成文件的日期
                            id = data.getInt(data.getColumnIndexOrThrow(VIDEO_PROJECTION[5]));
                            Log.e(">>", ">>PP>>" + formatData("yyyy年MM月dd日 HH:mm", dateTime));
                        } else {
                            path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                            name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                            dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));//生成文件的日期
                            id = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[5]));
                        }

                        if (!fileExist(path)) {
                            continue;
                        }
                        Image image = null;
                        if (!TextUtils.isEmpty(name)) {
                            if (isVideo) {
                                image = new Image(id, path, name, dateTime, true, videoDuration);
                            } else {
                                image = new Image(id, path, name, dateTime, false, videoDuration);
                            }
                            images.add(image);
                        }
                        if (!hasFolderGened) {
                            // get all folder data
                            File folderFile = new File(path).getParentFile();
                            if (folderFile != null && folderFile.exists()) {
                                String fp = folderFile.getAbsolutePath();
                                Folder f = getFolderByPath(fp);
                                if (f == null) {
                                    Folder folder = new Folder();
                                    folder.name = folderFile.getName();
                                    folder.path = fp;
                                    folder.cover = image;
                                    List<Image> imageList = new ArrayList<>();
                                    imageList.add(image);
                                    folder.images = imageList;
                                    mResultFolder.add(folder);
                                } else {
                                    f.images.add(image);
                                }
                            }
                        }
                    } while (data.moveToNext());
                    imagesLists = images;
//                    mImageAdapter.setData(images);
//                    if(resultList != null && resultList.size()>0){
//                        mImageAdapter.setDefaultSelected(resultList);
//                    }
                    if (!hasFolderGened) {
                        mFolderAdapter.setData(mResultFolder);
                        hasFolderGened = true;
                    }
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
    // folder result data set
    private ArrayList<Folder> mResultFolder = new ArrayList<>();

    private Folder getFolderByPath(String path) {
        if (mResultFolder != null) {
            for (Folder folder : mResultFolder) {
                if (TextUtils.equals(folder.path, path)) {
                    return folder;
                }
            }
        }
        return null;
    }

    public String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted , Access contacts here or do whatever you need.
                loadMediaResource();
            }
        }
    }
}
