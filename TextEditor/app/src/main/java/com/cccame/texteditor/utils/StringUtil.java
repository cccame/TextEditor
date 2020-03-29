package com.cccame.texteditor.utils;

public class StringUtil {
    private static StringBuffer sb = new StringBuffer();

    // 将Object转换为字符串
    public static String str(Object obj) {
        if (obj != null) {
            return obj.toString();
        } else
            return "";
    }

    // 返回一个由参数拼接而成的字符串
    public static String cat(Object... objArr) {
        sb.delete(0, sb.length());
        for (int i = 0; i < objArr.length; i++) {
            if (objArr[i] != null) {
                sb.append(objArr[i].toString());
            }
        }
        return sb.toString();
    }


}
