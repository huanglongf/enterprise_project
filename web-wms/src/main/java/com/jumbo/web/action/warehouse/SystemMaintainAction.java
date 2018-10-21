package com.jumbo.web.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.hub2wms.HubWmsManager;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderStatusOms;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundCommand;

/**
 * 系统维护
 * 
 * @author kai.zhu
 *
 */
public class SystemMaintainAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 8674177967084677150L;

    private String startTime;
    private String endTime;
    private String shoppingCode;
    private List<Long> idList;

    private String codeList;

    @Autowired
    private HubWmsManager hubWmsManager;

    /**
     * 页面跳转
     * 
     * @throws JSONException
     */
    public String outboundDataMaintain() {
        return SUCCESS;
    }

    public String outboundReset() {
        return SUCCESS;
    }

    public String openTestClearjsp() {
        return SUCCESS;
    }

    public String openWmsConfirmOrderQueue() {
        return SUCCESS;
    }


    public String findStaByStaCode() {
        setTableConfig();
        Pagination<MsgRtnOutboundCommand> list = hubWmsManager.findStaByStaCode(tableConfig.getStart(), tableConfig.getPageSize(), codeList, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 分页查询数据
     * 
     * @return
     */
    public String queryWmsOrder() {
        setTableConfig();
        Pagination<WmsOrderStatusOms> list = hubWmsManager.queryWmsOrder(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), shoppingCode, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 推送选中数据添加到t_wh_Order_Status_OMS表并更新相关联的表
     * 
     * @return
     * @throws JSONException
     */
    public String sendNewDateAndModity() throws JSONException {
        JSONObject result = new JSONObject();
        if (idList == null || idList.isEmpty()) {
            result.put("result", ERROR);
            result.put("message", "推送数据不能为空!");
        } else {
            // 分割list为每200size
            List<List<Long>> listArray = new ArrayList<List<Long>>();
            int listSize = idList.size();
            int pageSize = 200;
            int page = (listSize + (pageSize - 1)) / pageSize;
            for (int i = 0; i < page; i++) {
                List<Long> subList = new ArrayList<Long>();
                for (int j = 0; j < listSize; j++) {
                    int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
                    if (pageIndex == (i + 1)) {
                        subList.add(idList.get(j));
                    }
                    if ((j + 1) == ((j + 1) * pageSize)) {
                        break;
                    }
                }
                listArray.add(subList);
            }
            // 做添加与更新
            Boolean flag = true;
            for (List<Long> list : listArray) {
                try {
                    hubWmsManager.saveAndUpdateOrderStatus(list, userDetails.getUser().getUserName());
                } catch (Exception e) {
                    flag = false;
                    log.error("saveAndUpdateOrderStatus error ids:" + list.toString());
                }
            }
            if (flag) {
                result.put("result", SUCCESS);
                result.put("message", "推送成功!");
            } else {
                result.put("result", ERROR);
                result.put("message", "推送数据部分批次发生异常!");
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /***
     * 直连创单反馈
     * 
     * shuailiang.wen 分页查询数据
     * 
     * @return
     */
    public String queryWmsConfirmOrderQueue() {
        setTableConfig();
        Pagination<WmsConfirmOrderQueue> list = hubWmsManager.queryWmsConfirmOrderQueue(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), shoppingCode, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /***
     * 直连创单反馈
     * 
     * shuailiang.wen 推送选中数据T_WH_CONFIRM_ORDER
     * 
     * @return
     * @throws JSONException
     */
    public String updateWmsConfirmOrderQueue() throws JSONException {
        JSONObject result = new JSONObject();
        if (idList == null || idList.isEmpty()) {
            result.put("result", ERROR);
            result.put("message", "推送数据不能为空!");
        } else {
            // 分割list为每200size
            List<List<Long>> listArray = new ArrayList<List<Long>>();
            int listSize = idList.size();
            int pageSize = 200;
            int page = (listSize + (pageSize - 1)) / pageSize;
            for (int i = 0; i < page; i++) {
                List<Long> subList = new ArrayList<Long>();
                for (int j = 0; j < listSize; j++) {
                    int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
                    if (pageIndex == (i + 1)) {
                        subList.add(idList.get(j));
                    }
                    if ((j + 1) == ((i + 1) * pageSize)) {
                        break;
                    }
                }
                listArray.add(subList);
            }
            // 做添加与更新
            Boolean flag = true;
            for (List<Long> list : listArray) {
                try {
                    hubWmsManager.updateWmsConfirmOrderQueue(list, userDetails.getUser().getUserName());
                } catch (Exception e) {
                    flag = false;
                    log.error("updateWmsConfirmOrderQueue error ids:" + list.toString());
                }
            }
            if (flag) {
                result.put("result", SUCCESS);
                result.put("message", "推送成功!");
            } else {
                result.put("result", ERROR);
                result.put("message", "推送数据部分批次发生异常!");
            }
        }
        request.put(JSON, result);
        return JSON;
    }



    public String updateWmsOutBound() throws JSONException {
        JSONObject result = new JSONObject();
        if (idList == null || idList.isEmpty()) {
            result.put("result", ERROR);
            result.put("message", "推送数据不能为空!");
        } else {
            hubWmsManager.updateWmsOutBound(idList);
            result.put("result", SUCCESS);
            result.put("message", "推送成功!");
        }
        request.put(JSON, result);
        return JSON;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getShoppingCode() {
        return shoppingCode;
    }

    public void setShoppingCode(String shoppingCode) {
        this.shoppingCode = shoppingCode;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String getCodeList() {
        return codeList;
    }

    public void setCodeList(String codeList) {
        this.codeList = codeList;
    }


}
