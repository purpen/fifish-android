package com.qiyuan.fifish.bean;

/**
 * @author lilin
 * created at 2016/9/23 14:04
 */
public class UploadImgVideoBean {

    /**
     * large : http://obbrr76ua.bkt.clouddn.com/photo/160926/075677f8244e5165dfb3f2204d2da93c!cvxlg
     * small : http://obbrr76ua.bkt.clouddn.com/photo/160926/075677f8244e5165dfb3f2204d2da93c!cvxsm
     */

    public FileBean file;
    /**
     * file : {"large":"http://obbrr76ua.bkt.clouddn.com/photo/160926/075677f8244e5165dfb3f2204d2da93c!cvxlg","small":"http://obbrr76ua.bkt.clouddn.com/photo/160926/075677f8244e5165dfb3f2204d2da93c!cvxsm"}
     * id : 38
     * ret : success
     */

    public String id;
    public String ret;

    public static class FileBean {
        public String large;
        public String small;
    }
}
