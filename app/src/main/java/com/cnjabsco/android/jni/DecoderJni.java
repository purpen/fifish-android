package com.cnjabsco.android.jni;

import android.graphics.Bitmap;

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

	public int openSourceJ(String path) {
		return openSource(path);
	}


	public int decodeOneFrameJ(Bitmap bitmap) {
		return decodeOneFrame(bitmap);
	}

	public int getFrameWidthJ() {
		return getFrameWidth();
	}


	public int getFrameHeightJ() {
		return getFrameHeight();
	}


	public void closeSourceJ() {
		closeSource();
	}


	public int getNetStatusJ() {
		return getNetStatus();
	}
}
