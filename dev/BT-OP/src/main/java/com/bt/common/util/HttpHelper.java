package com.bt.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;

public final class HttpHelper {

	private static final Logger logger = Logger.getLogger(HttpHelper.class);
	
    private static MultiThreadedHttpConnectionManager manager = null;

    private static int DEFAULT_CONNECTION_TIMEOUT = 60000;
    private static int DEFAULT_SOCKET_TIMEOUT = 60000;
    private static int DEFAULT_MAX_CONNECTION_PER_HOST = 5;
    private static int DEFAULT_MAX_TOTAL_CONNECTIONS = 60;

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static boolean initialed = false;
    
    private static JaxWsDynamicClientFactory jwdcf;
    private static Client client;

    private HttpHelper() {
    }

    private static void init() {
        if (manager == null) {
            manager = new MultiThreadedHttpConnectionManager();
            manager.getParams().setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
            manager.getParams().setSoTimeout(DEFAULT_SOCKET_TIMEOUT);
            manager.getParams().setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTION_PER_HOST);
            manager.getParams().setMaxTotalConnections(DEFAULT_MAX_TOTAL_CONNECTIONS);
            initialed = true;
//            logger.debug( "manager is initialed");
        }
    }

    public static String fetchStringByPost(String url, Map<String, Object> params) {
        String response = null;
        PostMethod method = null;
        BufferedReader br = null;
        try {
            if (!initialed) {
                init();
            }

            HttpClient client = new HttpClient(manager);
            method = new PostMethod(url);

            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, DEFAULT_CHARSET);

            // params
            if (params != null) {
                NameValuePair[] requestBody = new NameValuePair[params.size()];
                int i = 0;
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    requestBody[i++] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                }
                method.setRequestBody(requestBody);
            }

            // executeMethod
            int statusCode = client.executeMethod(method);

            if (statusCode == HttpStatus.SC_OK) {
                // body
                br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), DEFAULT_CHARSET));
                StringBuilder sb = new StringBuilder();
                String inputLine = br.readLine();
                if (inputLine != null) {
                    sb.append(inputLine);
                    while ((inputLine = br.readLine()) != null) {
                        sb.append("\n");
                        sb.append(inputLine);
                    }
                }
                response = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                } finally {
                    br = null;
                }
            }
            if (method != null) {
                method.releaseConnection();
            }
        }
        return response;
    }

    public static String fetchStringByPost(String url, String paramStr) {
        String response = null;
        PostMethod method = null;
        BufferedReader br = null;
        try {
            if (!initialed) {
                init();
            }

            HttpClient client = new HttpClient(manager);
            method = new PostMethod(url);

            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, DEFAULT_CHARSET);

            // params
            if (paramStr != null) {
            	String[] params = paramStr.split("&");
                NameValuePair[] requestBody = new NameValuePair[params.length];
                for (int j = 0; j < params.length; j++) {
                	requestBody[j] = new NameValuePair(params[j].split("=")[0], params[j].split("=")[1]);
				}
                method.setRequestBody(requestBody);
            }

            // executeMethod
            int statusCode = client.executeMethod(method);

            if (statusCode == HttpStatus.SC_OK) {
                // body
                br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), DEFAULT_CHARSET));
                StringBuilder sb = new StringBuilder();
                String inputLine = br.readLine();
                if (inputLine != null) {
                    sb.append(inputLine);
                    while ((inputLine = br.readLine()) != null) {
                        sb.append("\n");
                        sb.append(inputLine);
                    }
                }
                response = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                } finally {
                    br = null;
                }
            }
            if (method != null) {
                method.releaseConnection();
            }
        }
        return response;
    }

    public static <T> T fetchObjectByPost(Class<T> responseClass, String url, Map<String, Object> params) throws Exception {
        PostMethod method = null;
        BufferedReader br = null;
        try {
            if (!initialed) {
                init();
            }

            HttpClient client = new HttpClient(manager);
            method = new PostMethod(url);

            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, DEFAULT_CHARSET);

            // params
            if (params != null) {
                NameValuePair[] requestBody = new NameValuePair[params.size()];
                int i = 0;
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    requestBody[i++] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                }
                method.setRequestBody(requestBody);
            }

            // executeMethod
            int statusCode = client.executeMethod(method);
            

            if (statusCode == HttpStatus.SC_OK) {
            	// body
            	br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), DEFAULT_CHARSET));
                StringBuilder sb = new StringBuilder();
                String inputLine = br.readLine();
                if (inputLine != null) {
                    sb.append(inputLine);
                    while ((inputLine = br.readLine()) != null) {
                        sb.append("\n");
                        sb.append(inputLine);
                    }
                }
                
               //System.out.println(sb.toString());
               return XmlUtil.readConfigFromStream(responseClass, new ByteArrayInputStream(sb.toString().getBytes()));
                
              /*return XmlUtil.readConfigFromStream(responseClass, method.getResponseBodyAsStream());*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                } finally {
                    br = null;
                }
            }
            if (method != null) {
                method.releaseConnection();
            }
        }
        return null;
    }
    

/**
 *  因为SF子母单接口有问题 专门针对做了个处理 SFOrderZD
 * <b>方法名：</b>：fetchObjectByPostForSFOrderZD<br>
 * <b>功能说明：</b>：TODO<br>
 * @author <font color='blue'>chenkun</font> 
 * @date  2018年3月22日 下午4:59:02
 * @param responseClass
 * @param url
 * @param params
 * @return
 * @throws Exception
 */
public static <T> T fetchObjectByPostForSFOrderZD(Class<T> responseClass, String url, Map<String, Object> params) throws Exception {
    PostMethod method = null;
    BufferedReader br = null;
    try {
        if (!initialed) {
            init();
        }
            HttpClient client = new HttpClient(manager);
            method = new PostMethod(url);

            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, DEFAULT_CHARSET);

            // params
            if (params != null) {
                NameValuePair[] requestBody = new NameValuePair[params.size()];
                int i = 0;
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    requestBody[i++] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                }
                method.setRequestBody(requestBody);
            }

            // executeMethod
            int statusCode = client.executeMethod(method);
            

//            System.out.println("===============================");
//            System.out.println(statusCode);
//            System.out.println("===============================");

            if (statusCode == HttpStatus.SC_OK) {
                // body
                br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), DEFAULT_CHARSET));
                StringBuilder sb = new StringBuilder();
                String inputLine = br.readLine();
                if (inputLine != null) {
                    sb.append(inputLine);
                    while ((inputLine = br.readLine()) != null) {
                        sb.append("\n");
                        sb.append(inputLine);
                    }
                }
                
               StringBuilder sb2 = new StringBuilder();
               if(sb.toString().indexOf("<OrderZDResponse>")!=-1){
                   sb2.append(sb.toString().replaceAll("<OrderZDResponse>", "").replaceAll("</OrderZDResponse>", ""));
                   sb = sb2;
               }
              // System.out.println(sb.toString());
               return XmlUtil.readConfigFromStream(responseClass, new ByteArrayInputStream(sb.toString().getBytes()));
                
              /*return XmlUtil.readConfigFromStream(responseClass, method.getResponseBodyAsStream());*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                } finally {
                    br = null;
                }
            }
            if (method != null) {
                method.releaseConnection();
            }
        }
        return null;
    }


public static String fetchObjectByPostTest( String url, Map<String, Object> params) throws Exception {
    PostMethod method = null;
    BufferedReader br = null;
    try {
        if (!initialed) {
            init();
        }
            HttpClient client = new HttpClient(manager);
            method = new PostMethod(url);

            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, DEFAULT_CHARSET);

            // params
            if (params != null) {
                NameValuePair[] requestBody = new NameValuePair[params.size()];
                int i = 0;
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    requestBody[i++] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                }
                method.setRequestBody(requestBody);
            }

            // executeMethod
            int statusCode = client.executeMethod(method);
            

            if (statusCode == HttpStatus.SC_OK) {
            	// body
            	br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), DEFAULT_CHARSET));
                StringBuilder sb = new StringBuilder();
                String inputLine = br.readLine();
                if (inputLine != null) {
                    sb.append(inputLine);
                    while ((inputLine = br.readLine()) != null) {
                        sb.append("\n");
                        sb.append(inputLine);
                    }
                }
                
               return sb.toString();
            	
                    }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                } finally {
                    br = null;
                }
            }
            if (method != null) {
                method.releaseConnection();
            }
        }
        return null;
    }
    

    /**
     * 发送http请求
     * @param url
     * @param xml
     * @return xml数据
     */
    public static String fetchXmlByPost(String url, String xml) {
        String response = null;
        PostMethod method = null;
        BufferedReader br = null;
        try {
            if (!initialed) {
                init();
            }

            HttpClient client = new HttpClient(manager);
            method = new PostMethod(url);

            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, DEFAULT_CHARSET);

            // params
            if (StringUtils.isNotBlank(xml)) {
                RequestEntity requestEntity = new StringRequestEntity(xml, "text/xml", DEFAULT_CHARSET);
                method.setRequestEntity(requestEntity);
            }

            // executeMethod
            int statusCode = client.executeMethod(method);

            if (statusCode == HttpStatus.SC_OK) {
                // body
                br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), DEFAULT_CHARSET));
                StringBuilder sb = new StringBuilder();
                String inputLine = br.readLine();
                if (inputLine != null) {
                    sb.append(inputLine);
                    while ((inputLine = br.readLine()) != null) {
                        sb.append("\n");
                        sb.append(inputLine);
                    }
                }
                response = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                } finally {
                    br = null;
                }
            }
            if (method != null) {
                method.releaseConnection();
            }
        }
        return response;
    }
    
    public static Client getClient(){
		if(jwdcf == null){
			logger.error("生成JAX-WS动态客户端工厂类实体");
			jwdcf = JaxWsDynamicClientFactory.newInstance();
		}
		if(client == null){
			logger.error("生成客户端");
			client = jwdcf.createClient(CommonUtil.getConfig("wsdl_url_sf"));
		}
	    
	    return client;
    }
    
    public static HTTPClientPolicy setHttpClientPolicy(){
    	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
    	// 连接超时
	    httpClientPolicy.setConnectionTimeout(30 * 1000);
	    // 取消块编码
	    httpClientPolicy.setAllowChunking(false); 
	    httpClientPolicy.setReceiveTimeout(30 * 1000);
	    return httpClientPolicy;
    }
    
    
}