package com.qiyuan.fifish.bean;

import java.io.Serializable;

/**
 * @author lilin
 *         created at 2016/4/22 1746
 */
public class FocusFans implements Serializable {
    public int _id;
    public int user_id;
    public int follow_id;
    public int group_id;
    public int type;
    public boolean focus_flag;
    public int is_read;
    public Follow follows;
}
