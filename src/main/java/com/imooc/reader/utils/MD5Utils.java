package com.imooc.reader.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5Digest(String source, Integer salt) {
        char[] ca = source.toCharArray();
        // 若某一个字符串在混淆后，如果超出了ASCII码的范围会自动扩展到Unicode码
        for (int i=0; i<ca.length; i++) {
            ca[i] = (char)(ca[i] + salt);
        }
        String target = new String(ca);
        String md5 = DigestUtils.md5Hex(target);
        return md5;
    }
}
