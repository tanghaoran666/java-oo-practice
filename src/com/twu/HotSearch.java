package com.twu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HotSearch {
    private String keyword;
    private int hotValue;

    public static List<HotSearch> mapToList(Map<String, Integer> map){
        List<HotSearch> list=new ArrayList();
        Iterator it=map.keySet().iterator();
        while(it.hasNext()){
            String key=it.next().toString();
            HotSearch hotSearch=new HotSearch();
            hotSearch.setKeyword(key);
            hotSearch.setHotValue(map.get(key));
            list.add(hotSearch);
        }
        return list;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getHotValue() {
        return hotValue;
    }

    public void setHotValue(int hotValue) {
        this.hotValue = hotValue;
    }
}
