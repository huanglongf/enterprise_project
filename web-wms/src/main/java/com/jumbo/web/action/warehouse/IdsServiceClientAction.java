package com.jumbo.web.action.warehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.webservice.ids.manager.IdsManagerProxy;
import com.jumbo.webservice.ids.service.ServiceType;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;
import com.jumbo.wms.model.vmi.ids.OrderConfirm;
import com.jumbo.wms.model.vmi.ids.OrderConfirm.ConfirmResult;

public class IdsServiceClientAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 921555652885511550L;
    protected static final Logger log = LoggerFactory.getLogger(IdsServiceClientAction.class);
    private static Map<String, IdsServerInformation> IDS_SERVER_INFO;
    private String key;
    private String sign;
    private String serviceType;
    private String requestTime;
    private String version;
    private String data = "";
    @Autowired
    private IdsManagerProxy idsManagerProxy;
    @Autowired
    private IdsManager idsManager;

    public void idsSendMegToWms() {
        log.error("idsSendMegToWms statrt");
        String param1 = "", param2 = "";
        String jsonStr = null;
        OrderConfirm orderConfirm = new OrderConfirm();
        boolean isNotError = true;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            InputStream is = request.getInputStream();
            StringBuffer buffer = new StringBuffer();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            while ((jsonStr = br.readLine()) != null) {

                buffer.append(jsonStr);
                buffer.append("\n");
            }
            jsonStr = buffer.toString();
            if (StringUtils.hasText(buffer)) {
                String strs[] = jsonStr.split("&");
                for (int i = 0; i < strs.length; i++) {
                    if (i == 0) {
                        String strs2[] = strs[i].split("=");
                        key = strs2[1];
                    }
                    if (i == 1) {
                        String strs2[] = strs[i].split("=");
                        requestTime = strs2[1];
                    }
                    if (i == 2) {
                        String strs2[] = strs[i].split("=");
                        sign = strs2[1];
                    }
                    if (i == 3) {
                        String strs2[] = strs[i].split("=");
                        version = strs2[1];
                    }
                    if (i == 4) {
                        String strs2[] = strs[i].split("=");
                        serviceType = strs2[1];
                    }
                    if (i == 5) {
                        data = strs[i].substring(5);

                    }
                }
            }
        } catch (IOException e) {
            orderConfirm.setResult(createErrorResult("", ServiceType.ERROR_S02, "签名错误:签名参数解析异常"));
            idsManager.idsInvSynInfoSave("签名错误 1:" + jsonStr);
            isNotError = false;
        } catch (Exception e) {
            log.error("IdsServiceClientAction Exception");
            log.error("", e);
            orderConfirm.setResult(createErrorResult("", ServiceType.ERROR_S02, "签名错误:签名参数EXCEPTION异常"));
            idsManager.idsInvSynInfoSave("签名错误 1:" + jsonStr);
            isNotError = false;
        }
        try {
            if (StringUtils.hasText(key) && isNotError) {
                log.error("Debug-请求品牌:" + key);
                log.error("Debug-请求接口:" + serviceType);
                log.error("Debug-请求接口:sign" + sign);
                log.error("Debug-请求data:" + data);
                getIdsServerInfo();
                if (IDS_SERVER_INFO.containsKey(key)) {
                    IdsServerInformation serverInfo = IDS_SERVER_INFO.get(key);

                    param1 = "Key=" + key + "&RequestTime=" + requestTime + "&Sign=" + serverInfo.getBzSign() + "&Version=" + version + "&ServiceType=" + serviceType;
                    // 对应AEO
                    param2 = "key=" + key + "&requestTime=" + requestTime + "&sign=" + serverInfo.getBzSign() + "&version=" + version + "&serviceType=" + serviceType;

                    log.error("Debug-签名:" + param1);

                    if (AppSecretUtil.generateSecret(param1).equals(sign) || AppSecretUtil.generateSecret(param2).equals(sign)) {
                        // String source = serverInfo.getSource();
                        String vmiSource = serverInfo.getVmiSource();
                        log.error("Debug-SourceVmi:" + vmiSource);
                        boolean isNotSupportInterface = false;
                        if (vmiSource == null) {
                            orderConfirm.setResult(createErrorResult("", ServiceType.ERROR_S05, "source 未找到！:vmiSource：" + vmiSource));
                            idsManager.idsInvSynInfoSave("签名错误3:" + param1);
                        } else if (ServiceType.LOGISTIC_ORDER_STATUS.endsWith(serviceType)) {
                            // 订单状态更新接口
                            // 所支持的品牌
                            if (vmiSource.equals(Constants.VIM_WH_SOURCE_UA_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_AEO_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_AF_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_NIKE_IDS)
                                    || Constants.VIM_WH_SOURCE_NEWLOOK_IDS.equals(vmiSource) || vmiSource.equals(Constants.VIM_WH_SOURCE_NBAUA_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_NBJ01UA_IDS)
                                    || vmiSource.equals(Constants.VIM_WH_SOURCE_GODIVA_HAVI) || Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS.equals(vmiSource) || vmiSource.equals(Constants.VIM_WH_SOURCE_NIKE_IDS_TM)
                                    || vmiSource.equals(Constants.VIM_WH_SOURCE_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_IDS_VS) || vmiSource.equals(Constants.VIM_WH_SOURCE_CONVERSE_IDS)) {
                                orderConfirm = idsManager.orderConfirmResponse(data);
                                if (orderConfirm != null) {
                                    String batchId = orderConfirm.getBatchID();
                                    idsManager.updateAfreshSendOrder(batchId, vmiSource);
                                }
                            } else {
                                isNotSupportInterface = true;
                            }
                        } else if (ServiceType.LOGISTIC_ORDER_DELIVERY.endsWith(serviceType)) {
                            // 发货单确认接口
                            // 所支持的品牌
                            if (vmiSource.equals(Constants.VIM_WH_SOURCE_UA_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_AEO_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_AF_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_NIKE_IDS)
                                    || vmiSource.equals(Constants.VIM_WH_SOURCE_GODIVA_HAVI) || Constants.VIM_WH_SOURCE_NEWLOOK_IDS.equals(vmiSource) || vmiSource.equals(Constants.VIM_WH_SOURCE_NBAUA_IDS)
                                    || vmiSource.equals(Constants.VIM_WH_SOURCE_NBJ01UA_IDS) || Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS.equals(vmiSource) || vmiSource.equals(Constants.VIM_WH_SOURCE_NIKE_IDS_TM)
                                    || vmiSource.equals(Constants.VIM_WH_SOURCE_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_IDS_VS) || vmiSource.equals(Constants.VIM_WH_SOURCE_CONVERSE_IDS)) {
                                orderConfirm = idsManager.orderDeliveryRequest(data, vmiSource);
                            } else {
                                isNotSupportInterface = true;
                            }
                        } else if (ServiceType.LOGISTIC_RETURN_ARRIVAL.endsWith(serviceType)) {
                            // 退货单确认接口
                            // 所支持的品牌
                            // if (vmiSource.equals(Constants.VIM_WH_SOURCE_UA_IDS) ||
                            // vmiSource.equals(Constants.VIM_WH_SOURCE_NBAUA_IDS) ||
                            // vmiSource.equals(Constants.VIM_WH_SOURCE_AEO_IDS) ||
                            // vmiSource.equals(Constants.VIM_WH_SOURCE_AF_IDS)
                            // || vmiSource.equals(Constants.VIM_WH_SOURCE_GODIVA_HAVI) ||
                            // Constants.VIM_WH_SOURCE_NEWLOOK_IDS.equals(vmiSource) ||
                            // vmiSource.equals(Constants.VIM_WH_SOURCE_NBJ01UA_IDS)
                            // || Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS.equals(vmiSource)) {
                            orderConfirm = idsManager.returnArrivalRequest(data, vmiSource);
                            // } else {
                            // isNotSupportInterface = true;
                            // }
                        } else if (ServiceType.LOGISTIC_INVENTORY_SYNCHRONOUS.endsWith(serviceType)) {
                            // 出入库同步接口
                            // 所支持的品牌
                            if (vmiSource.equals(Constants.VIM_WH_SOURCE_UA_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_NBAUA_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_AEO_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_IDS)
                                    || vmiSource.equals(Constants.VIM_WH_SOURCE_GODIVA_HAVI) || vmiSource.equals(Constants.VIM_WH_SOURCE_AF_IDS) || Constants.VIM_WH_SOURCE_NEWLOOK_IDS.equals(vmiSource)
                                    || vmiSource.equals(Constants.VIM_WH_SOURCE_NBJ01UA_IDS) || Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS.equals(vmiSource) || vmiSource.equals(Constants.VIM_WH_SOURCE_IDS) || vmiSource.equals(Constants.VIM_WH_SOURCE_IDS_VS)
                                    || vmiSource.equals(Constants.VIM_WH_SOURCE_CONVERSE_IDS)) {
                                orderConfirm = idsManager.vmiInventory(data, vmiSource);
                            } else {
                                isNotSupportInterface = true;
                            }
                        } else {
                            orderConfirm.setResult(createErrorResult("", ServiceType.ERROR_S02, "签名错误:接口为找到！:serviceType：" + serviceType));
                            idsManager.idsInvSynInfoSave("签名错误 4:" + param1);
                        }
                        // 是否不支持此接口
                        if (isNotSupportInterface) {
                            log.error(vmiSource + ":vmiSource");
                            orderConfirm.setResult(createErrorResult("", ServiceType.ERROR_S06, "当前业务，不支持此接口:serviceType：" + serviceType));
                            idsManager.idsInvSynInfoSave("当前业务，不支持此接口:" + param1);
                        }
                    } else {
                        orderConfirm.setResult(createErrorResult("", ServiceType.ERROR_S02, "签名错误:key 未找到对应流程" + key));
                        idsManager.idsInvSynInfoSave("签名错误 5:" + param1);
                    }
                } else {
                    orderConfirm.setResult(createErrorResult("", ServiceType.ERROR_S02, "签名错误:key不正确：" + key));
                    idsManager.idsInvSynInfoSave("签名错误 6:" + param1);
                }
            } else if (isNotError) {
                orderConfirm.setResult(createErrorResult("", ServiceType.ERROR_S02, "签名错误:Key为空."));
                idsManager.idsInvSynInfoSave("签名错误 7:" + param1);
            }
        } catch (Exception e) {
            log.error("", e);
            orderConfirm.setResult(createErrorResult("", ServiceType.ERROR_S05, "系统错误."));
            idsManager.idsInvSynInfoSave("系统错误:" + param1);
        }
        try {
            String respXml = (String) MarshallerUtil.buildJaxb(orderConfirm);
            log.error("Debug-IDSServiceRetunXML:" + respXml);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/xml; charset=UTF-8");
            response.getWriter().print(respXml.toString());
        } catch (IOException eio) {
            log.error("", eio);
        }
    }

    private List<ConfirmResult> createErrorResult(String externDocKey, String reason, String description) {
        ConfirmResult result = new ConfirmResult();
        result.setExternDocKey(externDocKey);
        result.setSuccess("false");
        result.setReason(reason);
        result.setDescription(description);
        List<ConfirmResult> resultList = new ArrayList<ConfirmResult>();
        resultList.add(result);
        return resultList;
    }

    private void getIdsServerInfo() {
        if (IDS_SERVER_INFO == null) {
            IDS_SERVER_INFO = idsManagerProxy.findIdsServiceInfo();
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
