package com.cnjabsco.android.jni;

public class RecordVideoJni {
	public int recordVedioJ(String outPath, String inPath,int stop) {
		recordVedio(outPath,inPath,stop);
		return 0;
	}
	private static native int recordVedio(String outPath, String inPath,int stop);
	static {
		System.loadLibrary("ffmpeg");
    	System.loadLibrary("avrecord");
	}
}
