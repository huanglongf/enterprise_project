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
 * 
 */

package com.jumbo.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.AutoPickingListRoleManager;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.system.ChooseOption;

import loxia.dao.Pagination;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

public class ChooseOptionAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8371921989994716591L;

    private String categoryCode;
    private String optionKey;
    private Long staId;
    private Boolean isAvailable;
    private String optionValue;
    private String wmsOrderType;

    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private AutoPickingListRoleManager autoPickingListRoleManager;

    @Override
    public String execute() throws Exception {
        if (categoryCode == null || categoryCode.trim().length() == 0)
            request.put(JSON, new JSONArray());
        else
            request.put(JSON, new JSONArray(chooseOptionManager.findOptionListByCategoryCode(categoryCode)));
        return JSON;
    }

    public String value() throws Exception {
        if (categoryCode == null || categoryCode.trim().length() == 0 || optionKey == null || optionKey.trim().length() == 0)
            request.put(JSON, new JSONObject());
        else {
            request.put(JSON, new JSONObject(chooseOptionManager.findChooseOptionByCategoryCodeAndKey(categoryCode, optionKey)));
        }
        return JSON;
    }

    public String valueList() throws Exception {
        if (categoryCode == null || categoryCode.trim().length() == 0 || optionKey == null || optionKey.trim().length() == 0)
            request.put(JSON, new JSONObject());
        else {
            request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findChooseListByCategoryCodeAndKey(categoryCode, optionKey)));
        }
        return JSON;
    }

    public String optionrulelist() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.optionrulelist()));
        return JSON;
    }

    /**
     * Status的fromatter.editoptions
     * 
     * @return
     * @throws Exception
     */
    public String formaterOptions() throws Exception {
        if (categoryCode != null && categoryCode.trim().length() > 0) {
            request.put(JSON, selectFormaterOptionsForByCode(categoryCode));
        }
        return JSON;
    }

    public String initPumaToOrderCode() {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.initPumaToOrderCode()));
        return JSON;
    }

    /**
     * 获取快递商名称
     * 
     * @return
     * @throws Exception
     */
    public String getTrasportName() throws Exception {
        JSONObject json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        List<TransportatorCommand> list = chooseOptionManager.findTransportator(optionKey);
        if (list != null && !list.isEmpty()) {
            for (TransportatorCommand tc : list) {
                sb.append((sb.length() > 0 ? ";" : "") + tc.getCode() + ":" + tc.getName());
            }
        }
        json.put("value", sb.toString());
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 获取快递商编号、名称、平台编码 fanht
     * 
     * @return
     * @throws Exception
     */
    public String getTrasport() throws Exception {
        JSONObject json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        List<TransportatorCommand> list = chooseOptionManager.findTransportator(optionKey);
        if (list != null && !list.isEmpty()) {
            for (TransportatorCommand tc : list) {
                sb.append((sb.length() > 0 ? ";" : "") + tc.getCode() + ":" + tc.getName() + ":" + tc.getPlatformCode());
            }
        }
        json.put("value", sb.toString());
        request.put(JSON, json);
        return JSON;
    }

    /**
     * categoryCode的fromatter.editoptions
     * 
     * @return
     * @throws Exception
     */
    private JSONObject selectFormaterOptionsForByCode(String code) throws Exception {
        JSONObject json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        List<ChooseOption> statusList = chooseOptionManager.findOptionListByCategoryCode(code);
        if (statusList != null && !statusList.isEmpty()) {
            for (ChooseOption o : statusList) {
                sb.append(o.getOptionKey() + ":" + o.getOptionValue() + ";");
            }
        }
        json.put("value", sb.toString());
        return json;
    }

    /**
     * 获取所有物流供应商信息
     * 
     * @return
     * @throws Exception
     */
    public String getTransportator() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findTransportator(optionKey)));
        return JSON;
    }

    public String getTransportatorAfter() throws Exception {
        request.put(JSON, JsonUtil.collection2json(autoPickingListRoleManager.findTransportatorList()));
        return JSON;
    }

    /**
     * 获取所有OTO目的地
     * 
     * @return
     * @throws Exception
     */
    public String getOtoLocation() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findOtoLocation(categoryCode)));
        return JSON;
    }

    /**
     * 获取集团下所有可用的外包仓
     * 
     * @return
     * @throws Exception
     */
    public String getVMIWarehouse() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.getVMIWarehouse()));
        return JSON;
    }

    public String getChooseOptionByCode() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findOptionListByCategoryCode(categoryCode)));
        return JSON;
    }
 /**
  * WMS工单类型查询
  * @return
  * @throws Exception
  */
    public String findAdPackageByOuIdByAdName() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findAdPackageByOuIdByAdName(userDetails.getCurrentOu().getId())));
        return JSON;
    }

    public String getChooseOptionByCodeEsprit() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.getChooseOptionByCodeEsprit(categoryCode)));
        return JSON;
    }

    /**
     * 获取所在公司库存商品状态
     * 
     * @return
     * @throws Exception
     */
    public String findInventoryStatus() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findInventoryStatus(isAvailable, userDetails.getCurrentOu().getId())));
        return JSON;
    }

    /**
     * 获取快递供应商平台编码、名称
     * 
     * @return
     * @throws Exception
     */
    public String findPlatformList() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findPlatformList()));
        return JSON;
    }

    public String findAdOrderType() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findAdOrderType()));
        return JSON;
    }


    public String findWmsResult() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findWmsResult(wmsOrderType)));
        return JSON;
    }
    /**
     * staId 获取快递单列表 fanht
     * 
     * @return
     * @throws Exception
     */
    public String getTransportNos() throws Exception {
        JSONObject json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        List<TransportatorCommand> list = chooseOptionManager.getTransportNos(staId);
        for (TransportatorCommand bean : list) {
            sb.append(bean.getCode()).append(",");
        }
        json.put("value", sb.toString());
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 获取配货商品分类下拉菜单 KJL
     * 
     * @return
     */
    public String findCategories() {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findCategories()));
        return JSON;
    }


    /**
     * 分页查询行政部门
     * 
     * @return
     * @throws Exception
     */
    public String getChooseOptionByCodePage() {
        setTableConfig();
        Pagination<ChooseOption> list = chooseOptionManager.getChooseOptionByCodePage(tableConfig.getStart(), tableConfig.getPageSize(), categoryCode, optionValue, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }
    /**
     * 过仓直连维护
     * 
     * @return
     * @throws JSONException 
     */
    public String omsChooseOptionUpdate() throws JSONException {
        JSONObject result=new JSONObject();
        if (optionValue!=null&&!"".equals(optionValue)) {
			try {
				String flag = chooseOptionManager.omsChooseOptionUpdate(optionValue);
				if (flag == null || "".equals(flag)) {
					result.put("flag", SUCCESS);
				} else {
					result.put("flag",flag);
				}
			} catch (Exception e) {
				result.put("flag", ERROR);
                log.error(e.getMessage());
			}
		}else {
			result.put("flag", "信息不完整!");
		}
       request.put(JSON, result);
        return JSON;
    }


    /**
     * 过仓非直连维护
     * 
     * @return
     * @throws JSONException 
     * @throws Exception
     */
    public String pacChooseOptionUpdate() throws JSONException {
    	JSONObject result=new JSONObject();
        if (optionValue!=null&&!"".equals(optionValue)) {
			try {
				String flag = chooseOptionManager.pacChooseOptionUpdate(optionValue);
				if (flag == null || "".equals(flag)) {
					result.put("flag", SUCCESS);
				} else {
					result.put("flag",flag);
				}
			} catch (Exception e) {
				result.put("flag", ERROR);
                log.error(e.getMessage());
			}
		}else {
			result.put("flag", "信息不完整!");
		}
       request.put(JSON, result);
        return JSON;
    }
    /**
     * 查询直连
     * 
     * @return
     * @throws Exception
     */
    public String buildOmsChooseOptionUpdate() throws Exception {
        request.put(JSON, JsonUtil.obj2json(chooseOptionManager.buildOmsChooseOptionUpdate()));
        return JSON;
    }
    /**
     * 查询非直连
     * 
     * @return
     * @throws Exception
     */
    public String buildPacChooseOptionUpdate() throws Exception {
        request.put(JSON, JsonUtil.obj2json(chooseOptionManager.buildPacChooseOptionUpdate()));
        return JSON;
    }
    public String getAllChooseOption() throws Exception {
    	setTableConfig();
    	List<ChooseOption> list=chooseOptionManager.getAllChooseOption();
        request.put(JSON, toJson(list));
        return JSON;
    }
    public String updateChooseOptionValue() throws JSONException {
        JSONObject result=new JSONObject();
        if (optionValue!=null&&!"".equals(optionValue)) {
			try {
				String flag = chooseOptionManager.updateChooseOptionValue(staId,optionValue);
				if (flag == null || "".equals(flag)) {
					result.put("flag", SUCCESS);
				} else {
					result.put("flag",flag);
				}
			} catch (Exception e) {
				result.put("flag", ERROR);
                log.error(e.getMessage());
			}
		}else {
			result.put("flag", "信息不完整!");
		}
       request.put(JSON, result);
        return JSON;
    }
    public String getAllChooseOptionOcp() throws Exception {
    	setTableConfig();
    	Pagination<ChooseOption> list=chooseOptionManager.getAllChooseOptionOcp(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }
    public String updateChooseOptionValueOcp() throws JSONException {
        JSONObject result=new JSONObject();
        if (optionValue!=null&&!"".equals(optionValue)) {
			try {
				String flag = chooseOptionManager.updateChooseOptionValueOcp(staId,optionValue);
				if (flag == null || "".equals(flag)) {
					result.put("flag", SUCCESS);
				} else {
					result.put("flag",flag);
				}
			} catch (Exception e) {
				result.put("flag", ERROR);
                log.error(e.getMessage());
			}
		}else {
			result.put("flag", "信息不完整!");
		}
       request.put(JSON, result);
        return JSON;
    }
    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getOptionKey() {
        return optionKey;
    }

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getWmsOrderType() {
        return wmsOrderType;
    }

    public void setWmsOrderType(String wmsOrderType) {
        this.wmsOrderType = wmsOrderType;
    }


}
