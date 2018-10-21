package com.jumbo.web.action.warehouse;


import java.util.List;
import loxia.support.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.TransportServiceManager;
import com.jumbo.wms.model.trans.TransportServiceArea;
import com.jumbo.wms.model.trans.TransportServiceCommand;

/**
 * 物流服务
 * 
 * @author xiaolong.fei
 * 
 */
public class TransportServiceAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3277769175547150094L;

    @Autowired
    private TransportServiceManager transportServiceManager;

    private String transId;
    private String expCode;// 物流code

    public String getExpCode() {
        return expCode;
    }

    public void setExpCode(String expCode) {
        this.expCode = expCode;
    }


    /**
     * 查询物流服务
     * 
     * @return
     */
    public String findTransService() {
        setTableConfig();
        List<TransportServiceCommand> serv = transportServiceManager.findTransServiceList(tableConfig.getSorts());
        request.put(JSON, toJson(serv));
        return JSON;
    }

    /**
     * 查询物流服务
     * 
     * @return
     */
    public String findTransServiceById() {
        setTableConfig();
        List<TransportServiceCommand> serv = transportServiceManager.findTransServiceListById(transId, tableConfig.getSorts());
        request.put(JSON, toJson(serv));
        return JSON;
    }

    /**
     * 查询物流服务配送范围
     * 
     * @return
     */
    public String findTransServiceAreaById() {
        setTableConfig();
        List<TransportServiceArea> serv = transportServiceManager.findTransServiceAreaListById(transId, tableConfig.getSorts());
        request.put(JSON, toJson(serv));
        return JSON;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
    /**
     * 根据物流商code查询时效类型
     */
    public String findTransServiceByCode() throws Exception {
        JSONObject result = new JSONObject();
        List<TransportServiceCommand> list = transportServiceManager.getTransportServiceByCode(expCode);
        if (list.size() != 0) {// 物流商是否设置了时效类型
            result.put("brand", "yes");
        } else {
            result.put("brand", "no");
        }
        result.put("list", JsonUtil.collection2json(list));
        request.put(JSON, result);
        return JSON;
    }

}
