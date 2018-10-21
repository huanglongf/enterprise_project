/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.wms.webservice.ttk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jumbo.util.HttpClientUtil;

/**
 * @author lichuan
 *
 */
public class HttpgetTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        StringBuilder requestUrl =  new StringBuilder();
        StringBuilder params = new StringBuilder();
        String site = "总部";
        String username = "测试客户";
        String password = "1234";
        int getCount = 0;
        String url = "http://api.ttk.cn/serverProxy/ProxyActionEx.php";
        //String arg3 = "arg3={\"site\":\""+ URLEncoder.encode(new String(site.getBytes(),Charset.forName("UTF-8")), "UTF-8") +"\",\"cus\":\""+ URLEncoder.encode(new String(username.getBytes(),Charset.forName("UTF-8")), "UTF-8") +"\",\"password\":\""+ URLEncoder.encode(new String(password.getBytes(),Charset.forName("UTF-8")), "UTF-8") +"\",\"getCount\":\"" + URLEncoder.encode(new String("1".getBytes(),Charset.forName("UTF-8")), "UTF-8") + "\"}";
        //requestUrl.append(url).append("?arg1="+ URLEncoder.encode(new String("手机接口".getBytes(),Charset.forName("UTF-8")), "UTF-8") +"&arg2="+URLEncoder.encode(new String("通用_提取空白单号".getBytes(),Charset.forName("UTF-8")), "UTF-8")+"").append("&").append(arg3);
        String arg3 = "arg3={\"site\":\""+ site +"\",\"cus\":\""+ username +"\",\"password\":\""+ password +"\",\"getCount\":\"" + 1 + "\"}";
        params.append("?arg1=手机接口&arg2=通用_提取空白单号").append("&").append(arg3);
        String p = params.toString().replace("\"", "%22").replace("{", "%7b").replace("}", "%7d");
        requestUrl.append(url).append(p);
        System.out.println(requestUrl.toString());
        System.out.println(URLEncoder.encode("手机接口", "UTF-8"));
        System.out.println(URLEncoder.encode("通用_提取空白单号", "UTF-8"));
        System.out.println(URLEncoder.encode(site, "UTF-8"));
        System.out.println(URLEncoder.encode(username, "UTF-8"));
        System.out.println(URLEncoder.encode(password, "UTF-8"));
        System.out.println(URLEncoder.encode("{\"site\":\""+ site +"\",\"cus\":\""+ username +"\",\"password\":\""+ password +"\",\"getCount\":\"" + 1 + "\"}", "UTF-8"));
        params = new StringBuilder();
        requestUrl = new StringBuilder();
        String arg1 = URLEncoder.encode("手机接口", "UTF-8");
        String arg2 = URLEncoder.encode("通用_提取空白单号", "UTF-8");
        site = URLEncoder.encode(site, "UTF-8");
        username = URLEncoder.encode(username, "UTF-8");
        password = URLEncoder.encode(password, "UTF-8");
        String count = URLEncoder.encode(getCount+"", "UTF-8");
        arg3 = URLEncoder.encode("{\"site\":\""+ site +"\",\"cus\":\""+ username +"\",\"password\":\""+ password +"\",\"getCount\":\"" + 1 + "\"}", "UTF-8");
        params.append("?arg1=").append(arg1).append("&arg2=").append(arg2).append("&arg3=").append(("{\"site\":\""+ site +"\",\"cus\":\""+ username +"\",\"password\":\""+ password +"\",\"getcount\":\"" + count + "\"}").replace("\"", "%22").replace("{", "%7b").replace("}", "%7d"));
        System.out.println(params.toString());
        params = new StringBuilder();
        requestUrl = new StringBuilder();
        params.append("?arg1=").append(arg1).append("&arg2=").append(arg2).append("&arg3=").append("{site:").append(site).append(",")
        .append("cus:").append(username).append(",").append("password:").append(password).append(",").append("getcount:").append(count).append("}");
        System.out.println(params.toString().replace("\"", "%22").replace("{", "%7b").replace("}", "%7d"));
        requestUrl.append(url).append(params.toString().replace("\"", "%22").replace("{", "%7b").replace("}", "%7d"));
        System.out.println(requestUrl.toString());
        String response = HttpClientUtil.httpGet(requestUrl.toString());
        System.out.println(response);
    }

}
