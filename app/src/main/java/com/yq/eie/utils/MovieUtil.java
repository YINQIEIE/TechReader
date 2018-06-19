package com.yq.eie.utils;

import com.yq.eie.http.response.PersonBean;

import java.util.List;

public class MovieUtil {

    public static String getFormatName(List<PersonBean> list) {
        if (list.isEmpty()) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i).getName())
                    .append("/");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public static String getFormatString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i))
                    .append("/");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
