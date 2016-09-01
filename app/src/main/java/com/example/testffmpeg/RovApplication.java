package com.example.testffmpeg;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.widget.ImageView;

import org.xutils.x;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;


public class RovApplication extends Application {
	private static final int WIDTH = 320;
	private static final int HEIGHT = 320;
    private static RovApplication instance;
    public RovApplication() {
    	instance = this;
    }

    public static RovApplication getInstance() {
    	return instance;
    }	
    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 鏄惁杈撳嚭debug鏃ュ織, 寮�惎debug浼氬奖鍝嶆�鑳�
    }
    public static void viewFile(Context context,File file){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),getMIMEType(file));
		context.startActivity(intent);    	
    }
	
	public static String getMIMEType(File file){
		String name = file.getName();
		String type = "*/*";
		int index = name.lastIndexOf('.');
		if(index > 0){
			String ext = name.substring(index).toLowerCase();
			if(ext.equalsIgnoreCase(".jpg") || ext.equalsIgnoreCase(".jpeg")){
				type = "image/jpeg";
			}else if(ext.equalsIgnoreCase(".png") || ext.equalsIgnoreCase(".bmp")){
				type = "image/*";
			}else if(ext.equalsIgnoreCase(".mp4")){
				type = "video/mp4";
			}else if(ext.equalsIgnoreCase(".3gp") || ext.equalsIgnoreCase(".avi") || ext.equalsIgnoreCase(".mkv")){
				type = "video/*";
			}
		}
		return type;
	}    
    
	private static Map<String, SoftReference<Bitmap>> caches =  new HashMap<String, SoftReference<Bitmap>>();
	
	public static void setImageViewThumbnail(ImageView image,File item){
		SoftReference<Bitmap> rf = null;
    	synchronized (caches) {
        	if(caches.containsKey(item.getAbsolutePath())){
                rf = caches.get(item.getAbsolutePath());                
        	}   	   	
    	}
    	if(rf != null && rf.get() != null){
            image.setImageBitmap(rf.get());
            return;
    	}
//    	image.setImageResource(R.mipmap.video);
    	Bitmap bitmap = null;
		String name = item.getName().toLowerCase();
		if(name.endsWith("mp4")){
			bitmap = getVideoThumbnail(item.getAbsolutePath(),WIDTH,HEIGHT,Images.Thumbnails.MICRO_KIND);			
			if(bitmap != null)
				image.setImageBitmap(bitmap);
//			else
//				image.setImageResource(R.mipmap.video);
		}else if(name.endsWith("png")){					
			bitmap = getImageThumbnail(item.getAbsolutePath(),WIDTH,HEIGHT);
			if(bitmap != null)
				image.setImageBitmap(bitmap);
//			else
//				image.setImageResource(R.mipmap.picture);
		}
		else if(name.endsWith("jpg")||name.endsWith("jpeg")){
			bitmap = getImageThumbnail(item.getAbsolutePath(),WIDTH,HEIGHT);
			if(bitmap != null)
				image.setImageBitmap(bitmap);
//			else
//				image.setImageResource(R.mipmap.picture2);
		}
		if(bitmap != null){
			rf = new SoftReference(bitmap);
			synchronized (caches){
				caches.put(item.getAbsolutePath(), rf);
			}
		}
	}
    /** 
     * 鏍规嵁鎸囧畾鐨勫浘鍍忚矾寰勫拰澶у皬鏉ヨ幏鍙栫缉鐣ュ浘 
     * 姝ゆ柟娉曟湁涓ょ偣濂藉锛�
     *     1. 浣跨敤杈冨皬鐨勫唴瀛樼┖闂达紝绗竴娆¤幏鍙栫殑bitmap瀹為檯涓婁负null锛屽彧鏄负浜嗚鍙栧搴﹀拰楂樺害锛�
     *        绗簩娆¤鍙栫殑bitmap鏄牴鎹瘮渚嬪帇缂╄繃鐨勫浘鍍忥紝绗笁娆¤鍙栫殑bitmap鏄墍瑕佺殑缂╃暐鍥俱� 
     *     2. 缂╃暐鍥惧浜庡師鍥惧儚鏉ヨ娌℃湁鎷変几锛岃繖閲屼娇鐢ㄤ簡2.2鐗堟湰鐨勬柊宸ュ叿ThumbnailUtils锛屼娇 
     *        鐢ㄨ繖涓伐鍏风敓鎴愮殑鍥惧儚涓嶄細琚媺浼搞� 
     * @param imagePath 鍥惧儚鐨勮矾寰�
     * @param width 鎸囧畾杈撳嚭鍥惧儚鐨勫搴�
     * @param height 鎸囧畾杈撳嚭鍥惧儚鐨勯珮搴�
     * @return 鐢熸垚鐨勭缉鐣ュ浘 
     */    
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {  
        Bitmap bitmap = null;  
        BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;  
        // 鑾峰彇杩欎釜鍥剧墖鐨勫鍜岄珮锛屾敞鎰忔澶勭殑bitmap涓簄ull  
        bitmap = BitmapFactory.decodeFile(imagePath, options);  
        options.inJustDecodeBounds = false; // 璁句负 false  
        // 璁＄畻缂╂斁姣� 
        int h = options.outHeight;  
        int w = options.outWidth;  
        int beWidth = w / width;  
        int beHeight = h / height;  
        int be = 1;  
        if (beWidth < beHeight) {  
            be = beWidth;  
        } else {  
            be = beHeight;  
        }  
        if (be <= 0) {  
            be = 1;  
        }  
        options.inSampleSize = be;  
        // 閲嶆柊璇诲叆鍥剧墖锛岃鍙栫缉鏀惧悗鐨刡itmap锛屾敞鎰忚繖娆¤鎶妎ptions.inJustDecodeBounds 璁句负 false  
        bitmap = BitmapFactory.decodeFile(imagePath, options);  
        // 鍒╃敤ThumbnailUtils鏉ュ垱寤虹缉鐣ュ浘锛岃繖閲岃鎸囧畾瑕佺缉鏀惧摢涓狟itmap瀵硅薄  
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,  
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);  
        return bitmap;  
    }  
  
    /** 
     * 鑾峰彇瑙嗛鐨勭缉鐣ュ浘 
     * 鍏堥�杩嘥humbnailUtils鏉ュ垱寤轰竴涓棰戠殑缂╃暐鍥撅紝鐒跺悗鍐嶅埄鐢═humbnailUtils鏉ョ敓鎴愭寚瀹氬ぇ灏忕殑缂╃暐鍥俱� 
     * 濡傛灉鎯宠鐨勭缉鐣ュ浘鐨勫鍜岄珮閮藉皬浜嶮ICRO_KIND锛屽垯绫诲瀷瑕佷娇鐢∕ICRO_KIND浣滀负kind鐨勫�锛岃繖鏍蜂細鑺傜渷鍐呭瓨銆�
     * @param videoPath 瑙嗛鐨勮矾寰�
     * @param width 鎸囧畾杈撳嚭瑙嗛缂╃暐鍥剧殑瀹藉害 
     * @param height 鎸囧畾杈撳嚭瑙嗛缂╃暐鍥剧殑楂樺害搴�
     * @param kind 鍙傜収MediaStore.Images.Thumbnails绫讳腑鐨勫父閲廙INI_KIND鍜孧ICRO_KIND銆�
     *            鍏朵腑锛孧INI_KIND: 512 x 384锛孧ICRO_KIND: 96 x 96 
     * @return 鎸囧畾澶у皬鐨勮棰戠缉鐣ュ浘 
     */  
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,  
            int kind) {  
        Bitmap bitmap = null;  
        // 鑾峰彇瑙嗛鐨勭缉鐣ュ浘  
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,  
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);  
        return bitmap;  
    }  
    
    public void addImageToGallery(final String filePath,String title) {
    	final Context context = this;
    	String mime_type = "image/*";
    	if(filePath.toLowerCase().endsWith("mp4"))
    		mime_type = "video/*";
        ContentValues values = new ContentValues();
        values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(Images.Media.MIME_TYPE, mime_type);//"image/jpeg"
        values.put(MediaStore.MediaColumns.DATA, filePath);
        values.put(Images.Media.TITLE, title);
        context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    
}
