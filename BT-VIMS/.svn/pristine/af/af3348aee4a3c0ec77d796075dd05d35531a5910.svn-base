package com.bt.vims.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
public class SignProcess {
    /**
     * 验证md5签名
     * @param sign
     * @param objects
     * @param secretKey
     * @return
     * @Description:
     */
    public synchronized static boolean checkMd5Sign(String sign, Map<String, String> objects, String secretKey){
        return sign.equals(makeMd5Sign(objects, secretKey));
    }
    
    /**
     * 
     * @param objects
     * @param secretKey
     * @return
     * @Description:
     */
    public synchronized static String makeMd5Sign(Map<String, String> objects, String secretKey){
        String sign = null;
        StringBuffer content = new StringBuffer();
        // 对resultmap中的参数进行排序
        List<String> keyList = new ArrayList<String>();
        Iterator<Entry<String, String>> ite = objects.entrySet().iterator();
        while(ite.hasNext()){
            keyList.add(ite.next().getKey());
        }
        Collections.sort(keyList);
        // 拼接secretKey
        for(String key : keyList){
            content.append(key).append("=").append(objects.get(key)).append("&");
        }
        content.delete(content.length() -1, content.length());
        // 生成md5签名
        sign = MD5UtilStrong.getMD5String(content + secretKey);
        return sign;
    }
}
