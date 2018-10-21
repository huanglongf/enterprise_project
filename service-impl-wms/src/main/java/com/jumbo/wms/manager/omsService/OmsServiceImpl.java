package com.jumbo.wms.manager.omsService;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.InterfaceSecurityInfoDao;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.opensymphony.xwork2.Action;



/**
 * OMS接口方法
 */
@Transactional
@Service("omsService")
public class OmsServiceImpl implements OmsService {
    protected static final Logger log = LoggerFactory.getLogger(OmsServiceImpl.class);
    /**
     * 
     */
    private static final long serialVersionUID = 5329954997950257007L;
    @Autowired
    private InterfaceSecurityInfoDao interfaceSecurityInfoDao;

    /**
     * 取消反馈通知到OMS
     * 
     * @param sta 单据
     * @param msg 操作信息
     * @param result 是否成功
     */
    public int cancelSoReturn(StockTransApplication sta, String msg, boolean result, String omsCancelUrl) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("soCode", sta.getRefSlipCode()));
        params.add(new BasicNameValuePair("message", msg));
        params.add(new BasicNameValuePair("result", result ? Action.SUCCESS : Action.ERROR));
        log.debug("cancelSoReturn :" + sta.getRefSlipCode() + ";return:" + (result ? Action.SUCCESS : Action.ERROR));
        String str = execute(params, omsCancelUrl);
        log.debug("cancelSoReturn :" + sta.getRefSlipCode() + ";omsReturn:" + str);
        // 判断返回的数据是否成功
        if (str != null && str.contains(Action.SUCCESS)) {
            return OmsServiceConstants.SUCCESS;
        } else if (str != null && str.contains(Action.ERROR)) {
            return OmsServiceConstants.ERROR;
        } else {
            return OmsServiceConstants.UNKNOWN;
        }
    }

    /**
     * 调用接口
     * 
     * @param params 所需要的参数
     */
    private String execute(List<NameValuePair> params, String omsCancelUrl) {
        // 创建HTTP客服端
        HttpClient httpclient = new DefaultHttpClient();
        try {
            // 获取请求连接
            HttpPost post = new HttpPost(omsCancelUrl);
            // 证书信息
            InterfaceSecurityInfo securityInfo = interfaceSecurityInfoDao.findUseringUserBySource(Constants.OMS3_SOURCE, new Date());
            // 获取时间
            String callDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            if (params == null) {
                params = new ArrayList<NameValuePair>();
            }
            // 用户名
            params.add(new BasicNameValuePair("username", securityInfo.getUsername()));
            // 密码
            params.add(new BasicNameValuePair("source", securityInfo.getSource()));
            // 时间
            params.add(new BasicNameValuePair("calldate", callDate));
            // 拼接 证书 key 值
            StringBuffer sb = new StringBuffer();
            sb.append(securityInfo.getUsername());
            sb.append(securityInfo.getPassword());
            sb.append(callDate);
            params.add(new BasicNameValuePair("secretkey", AppSecretUtil.generateSecret(sb.toString())));
            // 设置请求编码 已经请求数据
            post.setEntity(new UrlEncodedFormEntity(params, Constants.UTF_8));
            // 发送请请求 获取请求返回数据
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
            throw new BusinessException(ErrorCode.OMS_OUT_BOUND_ERROR);
        } catch (IllegalStateException e) {
            throw new BusinessException(ErrorCode.OMS_OUT_BOUND_ERROR);
        } catch (IOException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.OMS_OUT_BOUND_ERROR);
        }
    }
}
