package com.qiyuan.fifish.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/4/27 13:29
 */
public class ProvinceCityData implements Serializable {
    public int total_rows;
    public ArrayList<Province> rows;
    public class Province implements Serializable {
        public int _id;
        public String city;
        public String parent_id;
        public ArrayList<City> cities;
    }
    public class City implements Serializable {
        public int _id;
        public String city;
        public int parent_id;
    }
}
