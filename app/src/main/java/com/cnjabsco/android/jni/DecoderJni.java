package com.cnjabsco.android.jni;

import android.graphics.Bitmap;

/**
 * ��Ƶ������
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
	 * ����Ƶ��Դ
	 * 
	 * @return ����״̬�����<br/>
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
	 * ����һ֡��Ƶͼ��<br/>
	 * 
	 * @param bitmap
	 *            ��Ƶͼ��
	 * @return ��������<br/>
	 *         0 normal<br/>
	 *         1 AndroidBitmap_getInfo() failed<br/>
	 *         2 AndroidBitmap_lockPixels() failed<br/>
	 *         3 could not initialize conversion context<br/>
	 */
	public int decodeOneFrameJ(Bitmap bitmap) {
		return decodeOneFrame(bitmap);
	}

	/**
	 * ��ȡ֡��Ƶͼ����
	 * 
	 * @return ֡��Ƶͼ����
	 */
	public int getFrameWidthJ() {
		return getFrameWidth();
	}

	/**
	 * ��ȡ֡��Ƶͼ��߶�
	 * 
	 * @return ֡��Ƶͼ��߶�
	 */
	public int getFrameHeightJ() {
		return getFrameHeight();
	}

	/**
	 * �ر���Ƶ��Դ
	 */
	public void closeSourceJ() {
		closeSource();
	}

	/**
	 * �������״̬
	 * 
	 * @return 1 ����<br/>
	 *         0 �쳣����������Ƶ���ݣ�<br/>
	 */
	public int getNetStatusJ() {
		return getNetStatus();
	}
}
