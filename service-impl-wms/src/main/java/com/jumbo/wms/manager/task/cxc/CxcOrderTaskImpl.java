package com.jumbo.wms.manager.task.cxc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.CxcConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.CxcConfirmOrderQueueLogDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.HttpClientUtil;
import com.jumbo.util.JsonDateValueProcessor;
import com.jumbo.util.cxc.CxcBase64;
import com.jumbo.util.cxc.CxcMd5;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.wms.daemon.CxcOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.CxcConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.CxcConfirmOrderQueueLog;

@Transactional
@Service("cxcOrderTask")
public class CxcOrderTaskImpl extends BaseManagerImpl implements CxcOrderTask {


    private static final long serialVersionUID = 8394733598939066464L;

    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private CxcConfirmOrderQueueDao cxcConfirmOrderQueueDao;
    @Autowired
    private CxcConfirmOrderQueueLogDao cxcConfirmOrderQueueLogDao;

    /**
     * 设置CXC快递单号
     * 
     * @author LuYingMing
     * @date 2016年5月30日 下午12:30:37
     * @see com.jumbo.wms.daemon.CxcOrderTask#setCxcWarehouseTranckingNo()
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void setCxcWarehouseTranckingNo() {

        List<Long> idList = warehouseDao.getAllWarehouseByExcludeVmi(new SingleColumnRowMapper<Long>(Long.class));
        for (Long id : idList) {
            try {
                Warehouse wa = warehouseDao.getByOuId(id);
                if (null != wa) {
                    if (!wa.getIsCxcOlOrder()) {
                        continue;
                    }
                }
                List<String> lpList = new ArrayList<String>();
                lpList.add(Transportator.CXC);
                // 查询需要设置运单号的作业单
                List<Long> staList = staDao.findStaByOuIdAndStatus(id, lpList, new SingleColumnRowMapper<Long>(Long.class));
                for (Long staId : staList) {
                    try {
                        // 设置CXC单据号
                        transOlManager.matchingTransNo(staId);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 调用CXC物流接口
     * 
     * @author LuYingMing
     * @date 2016年6月2日 下午4:46:20
     * @see com.jumbo.wms.daemon.CxcOrderTask#callCxcLogisticsProvidersInterface()
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void callCxcLogisticsProvidersInterface() {
        try {
            List<CxcConfirmOrderQueue> cxcList = cxcConfirmOrderQueueDao.getAllCxcOrderQueue();
            if (null != cxcList && cxcList.size() > 0) {
                for (CxcConfirmOrderQueue cxcConfirmOrderQueue : cxcList) {
                    JsonConfig jsonConfig = new JsonConfig();
                    // 注册date转换器
                    jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
                    jsonConfig.setExcludes(new String[] {"id", "status"});
                    // 将JsonConfig放入JSONObject对象中，针对不同的数据类型有多种方式放入JsonConfig
                    String cxcJson = JSONObject.fromObject(cxcConfirmOrderQueue, jsonConfig).toString();
                    // 加密工具由CXC提供
                    String encodeStr = CxcBase64.encode(CxcMd5.MD5Encode(cxcJson + "98161006").toLowerCase());
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("data", cxcJson);
                    params.put("data_digest", encodeStr);
                    // params.put("clientId", ytoClientID);
                    // 调接口
                    String resultXml = HttpClientUtil.httpPost(CXC_GET_MAILNO_URL, params);
                    log.warn(resultXml);
                    JSONObject jsonObject = JSONObject.fromObject(resultXml);
                    // CXC:接口应答字段"success"
                    String result = jsonObject.get("success").toString();
                    // CXC:接口应答字段"message"
                    String msg = jsonObject.getString("message").toString();
                    if ("true".equals(result)) {
                        // TODO 删队列表数据,保存到日志表
                        CxcConfirmOrderQueueLog model = new CxcConfirmOrderQueueLog();
                        model.setBdBoxcode(cxcConfirmOrderQueue.getBdBoxcode());
                        model.setBdCaddress(cxcConfirmOrderQueue.getBdCaddress());
                        model.setBdCode(cxcConfirmOrderQueue.getBdCode());
                        model.setBdCodpay(cxcConfirmOrderQueue.getBdCodpay());
                        model.setBdConsigneename(cxcConfirmOrderQueue.getBdConsigneename());
                        model.setBdConsigneephone(cxcConfirmOrderQueue.getBdConsigneephone());
                        model.setBdGoodsnum(cxcConfirmOrderQueue.getBdGoodsnum());
                        model.setBdHeight(cxcConfirmOrderQueue.getBdHeight());
                        model.setBdLength(cxcConfirmOrderQueue.getBdLength());
                        model.setBdPackageno(cxcConfirmOrderQueue.getBdPackageno());
                        model.setBdPremium(cxcConfirmOrderQueue.getBdPremium());
                        model.setBdProcedurefee(cxcConfirmOrderQueue.getBdProcedurefee());
                        model.setBdProductprice(cxcConfirmOrderQueue.getBdProductprice());
                        model.setBdPurprice(cxcConfirmOrderQueue.getBdPurprice());
                        model.setBdRemark(cxcConfirmOrderQueue.getBdRemark());
                        model.setBdShipmentno(cxcConfirmOrderQueue.getBdShipmentno());
                        model.setBdWeidght(cxcConfirmOrderQueue.getBdWeidght());
                        model.setBdWidth(cxcConfirmOrderQueue.getBdWidth());
                        model.setCmCode(cxcConfirmOrderQueue.getCmCode());
                        model.setCreatedate(cxcConfirmOrderQueue.getCreatedate());
                        // 保存到日志表
                        cxcConfirmOrderQueueLogDao.save(model);
                        // 删除cxc队列表
                        cxcConfirmOrderQueueDao.deleteByPrimaryKey(cxcConfirmOrderQueue.getId());
                    } else {
                        log.error(new Date().toString() + "调用结果为:" + result + ",原因是:" + msg);
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }

    }



    public static String sendMsgtoIds(String data, String url) {
        // String formatData = data.replace("\\&", " ");
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            // SimpleDateFormat sf = new SimpleDateFormat("yyyyMMDDHHMMss");
            // String requestTime = sf.format(new Date());
            String param = null;
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // HttpURLConnection conn1 = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            // conn.setRequestProperty("accept", "*/*");
            // conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            // conn.setRequestProperty("user-agent",
            // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());


            // TODO 在POST时用“data_digest”字段进行签名验证。签名使用MD5方式，对
            // data的内容进行签名。原理为：通知内容JSON+partnerID(联调时分配)，然后进行 MD5，转换为Base64 字符串。
            String secretKey = AppSecretUtil.generateSecret(data);
            param = "data=" + data + "&data_digest=" + secretKey;
            log.debug("DebugIDS->:" + param);
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            realUrl = null;
        } catch (Exception e) {
            log.error("LF sendMsgtoIds:发送 POST 请求出现异常！:" + e.toString());
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("LF sendMsgtoIds:流关闭异常：" + ex.toString());
            }
        }
        log.debug("LF sendMsgtoIds:" + result);
        return result;
    }

}
