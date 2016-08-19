package com.cnjabsco.android.jni;

import android.graphics.Bitmap;

/**
 * 视频解码器
 * 
 * @author fbpang
 * @version 1.0
 * @date 2013-04-12
 */
public class DecoderJni {
	public int recordVedioJ(String outPath, int stop) {
		recordVedio(outPath,stop);
		return 0;
	}
	private static native int recordVedio(String outPath, int stop);

	private static native int openSource(String path);

	private static native int decodeOneFrame(Bitmap bitmap);

	private static native int getFrameWidth();

	private static native int getFrameHeight();

	private static native void closeSource();

	private static native int getNetStatus();

	static {
		System.loadLibrary("ffmpeg");
    	System.loadLibrary("sffdecoder");
	}

	/**
	 * 打开视频资源
	 * 
	 * @return 连接状态结果：<br/>
	 *         0 normal; <br/>
	 *         1 Couldn't open file;<br/>
	 *         2 Unable to get stream info;<br/>
	 *         3 Unable to find video stream;<br/>
	 *         4 Unsupported codec;<br/>
	 *         5 Unable to open codec.<br/>
	 */
	public int openSourceJ(String path) {
		return openSource(path);
	}

	/**
	 * 解码一帧视频图像；<br/>
	 * 
	 * @param bitmap
	 *            视频图像
	 * @return 解码结果：<br/>
	 *         0 normal<br/>
	 *         1 AndroidBitmap_getInfo() failed<br/>
	 *         2 AndroidBitmap_lockPixels() failed<br/>
	 *         3 could not initialize conversion context<br/>
	 */
	public int decodeOneFrameJ(Bitmap bitmap) {
		return decodeOneFrame(bitmap);
	}

	/**
	 * 获取帧视频图像宽度
	 * 
	 * @return 帧视频图像宽度
	 */
	public int getFrameWidthJ() {
		return getFrameWidth();
	}

	/**
	 * 获取帧视频图像高度
	 * 
	 * @return 帧视频图像高度
	 */
	public int getFrameHeightJ() {
		return getFrameHeight();
	}

	/**
	 * 关闭视频资源
	 */
	public void closeSourceJ() {
		closeSource();
	}

	/**
	 * 检测网络状态
	 * 
	 * @return 1 正常<br/>
	 *         0 异常（可能无视频数据）<br/>
	 */
	public int getNetStatusJ() {
		return getNetStatus();
	}
}
