package com.jumbo.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;

public class HttpClientUtil {
    protected static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    private static String defaultCharset = "UTF-8";
    private static int connectSTimeOut = 5000; // 读取zk里面的配置,默认为5s
    private static int readSTimeOut = 5000; // 读取zk里面的配置,默认为5s

    public int connectTime;
    public int readTime;

    public void init() {
        connectSTimeOut = connectTime;
        readSTimeOut = readTime;
    }

    /**
     * HTTP get 请求
     * 
     * @param getUrl
     * @return
     */
    public static String httpGet(String getUrl) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            // 创建Get方法实例
            HttpGet httpgets = new HttpGet(getUrl);
            HttpResponse response = httpclient.execute(httpgets);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                String str = convertStreamToString(instreams);
                httpgets.abort();
                return str;
            } else {
                return null;
            }
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (ClientProtocolException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (IOException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return sb.toString();
    }


    public static String httpPost(String url, Map<String, String> params) {
        return httpPost(url, params, 3000, 3000);
    }


    @SuppressWarnings("deprecation")
    public static String httpPost(String url, Map<String, String> params, int connectTimeout, int readTimeout) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), connectTimeout);
        HttpConnectionParams.setSoTimeout(httpclient.getParams(), readTimeout);

        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            for (Entry<String, String> ent : params.entrySet()) {
                postParams.add(new BasicNameValuePair(ent.getKey(), ent.getValue()));
            }
            post.setEntity(new UrlEncodedFormEntity(postParams, HTTP.UTF_8));
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte b[] = new byte[1024];
            int i = 0;
            do {
                i = in.read(b);
                if (i > 0) {
                    out.write(b, 0, i);
                }
            } while (i > 0);
            return new String(out.toByteArray());
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (IllegalStateException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("httpPost", e);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * HTTP post请求,自定义请求头编码
     * 
     * @param url
     * @param params
     * @param charset
     * @return String
     * @throws
     */
    public static String httpPost(String url, Map<String, String> params, String charset) {
        if (null == charset || "".equals(charset)) {
            charset = defaultCharset;
        } else {
            Charset.forName(charset);
        }
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            for (Entry<String, String> ent : params.entrySet()) {
                postParams.add(new BasicNameValuePair(ent.getKey(), ent.getValue()));
            }
            post.setEntity(new UrlEncodedFormEntity(postParams, charset));
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte b[] = new byte[1024];
            int i = 0;
            do {
                i = in.read(b);
                if (i > 0) {
                    out.write(b, 0, i);
                }
            } while (i > 0);
            return new String(out.toByteArray());
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (IllegalStateException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (IOException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public static String doPostWithJson(String url, String json, Map<String, String> params) {
        return doPostWithJson(url, json, params, connectSTimeOut, readSTimeOut);
    }

    public static String doPostWithJson(String url, String json, Map<String, String> params, int connectTimeout, int readTimeout) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), connectTimeout);
        HttpConnectionParams.setSoTimeout(client.getParams(), readTimeout);
        HttpPost post = new HttpPost(url);
        try {
            StringEntity s = new StringEntity(json, "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");// 发送json数据需要设置contentType
            post.setEntity(s);
            // 在header中放置信息
            if (params != null) {
                for (Entry<String, String> ent : params.entrySet()) {
                    post.addHeader(ent.getKey(), ent.getValue());
                }
            }
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte b[] = new byte[1024];
            int i = 0;
            do {
                i = in.read(b);
                if (i > 0) {
                    out.write(b, 0, i);
                }
            } while (i > 0);
            return new String(out.toByteArray());
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public static String doPostWithText(String url, String content, Map<String, String> params) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), connectSTimeOut);
        HttpConnectionParams.setSoTimeout(client.getParams(), readSTimeOut);
        HttpPost post = new HttpPost(url);
        try {
            StringEntity s = new StringEntity(content, "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("text/plain");
            post.setEntity(s);
            // 在header中放置信息
            if (params != null) {
                for (Entry<String, String> ent : params.entrySet()) {
                    post.addHeader(ent.getKey(), ent.getValue());
                }
            }
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte b[] = new byte[1024];
            int i = 0;
            do {
                i = in.read(b);
                if (i > 0) {
                    out.write(b, 0, i);
                }
            } while (i > 0);
            return new String(out.toByteArray());
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public void setConnectTime(int connectTime) {
        this.connectTime = connectTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

}
