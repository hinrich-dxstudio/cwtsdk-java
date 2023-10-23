package org.dxstudio.cwtsdk;

import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SignUtils {

    private static String getListString(List<Map<String, Object>> list) {
        List<String> l = new ArrayList<>();
        list.forEach(item -> {
            l.add(getString(item));
        });
        String arrStr = String.join(",", l);
        return "[" + arrStr + "]";
    }

    private static String getString(Map<String, Object> p) {
        List<String> query = new ArrayList<>();
        p.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((v) -> {
            String s;
            if (v.getValue() instanceof List<?>) {
                s = getListString((List<Map<String, Object>>) v.getValue());
            } else {
                s = v.getValue().toString();
            }
            query.add(v.getKey() + "=" + s);
        });
        return String.join("&", query);
    }


    public static String getSign(Map<String, Object> p, String secret) {
        if (p.isEmpty()) return "";
        String originalSign = getString(p) + "&secret=" + secret;
//        System.out.println(originalSign);
        return DigestUtils.md5DigestAsHex(originalSign.getBytes());
    }

}
