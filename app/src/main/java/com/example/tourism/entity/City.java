package com.example.tourism.entity;

import java.io.Serializable;
import java.util.Comparator;

public class City implements Serializable {
    public String cityName;         //地区名称
    public String namePy;      //地区名称拼音
    public String fisrtSpell;   //地区名称首字母

    public static class ComparatorPY implements Comparator<City> {
        @Override
        public int compare(City lhs, City rhs) {
            String str1 = lhs.namePy;
            String str2 = rhs.namePy;
            return str1.compareToIgnoreCase(str2);
        }
    }
}
