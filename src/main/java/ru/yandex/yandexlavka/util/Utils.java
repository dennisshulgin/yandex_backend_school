package ru.yandex.yandexlavka.util;

import java.util.StringJoiner;

public class Utils {

    public static String pathToPattern(String path) {
        String[] parts = path.split("/");

        for(int i = 1; i < parts.length; i++) {
            int j = 0;
            int countNums = 0;

            while(j < parts[i].length()) {

                if (parts[i].charAt(j) >= '0' && parts[i].charAt(j) <= '9') {
                    countNums++;
                }
                j++;
            }

            if(countNums == parts[i].length()) {
                parts[i] = "#";
            }
        }

        StringJoiner sj = new StringJoiner("/");
        for(String part : parts) {
            sj.add(part);
        }

        return sj.toString();
    }
}
