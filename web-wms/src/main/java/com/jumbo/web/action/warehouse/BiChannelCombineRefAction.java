/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 */
package com.jumbo.web.action.warehouse;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.channel.ChannelCombineRefManager;
import com.jumbo.wms.model.warehouse.BiChannelCombineRefCommand;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class BiChannelCombineRefAction extends BaseJQGridProfileAction {

	private static final long serialVersionUID = 5379294127020151127L;
	@Autowired
	private ChannelCombineRefManager channelRefManager;

	private BiChannelCombineRefCommand list;

	public String maintain() {
		return SUCCESS;
	}

	/**
	 * 查询合并发货渠道信息
	 * 
	 * @return
	 */
	public String findChannelRef() {
		setTableConfig();
		Pagination<BiChannelCombineRefCommand> list = channelRefManager
				.findBiChannelCombineRef(tableConfig.getStart(),
						tableConfig.getPageSize(), tableConfig.getSorts(),userDetails.getCurrentOu().getId());
		request.put(JSON, toJson(list));
		return JSON;
	}
	
	/**
	 * 查询合并发货子渠道信息
	 * 
	 * @return
	 */
	public String findChildChannelRef() {
		setTableConfig();
		request.put(JSON, toJson(channelRefManager.findBiChannelCombineChildrenRef(list.getHbChId(),list.getWhId(),tableConfig.getSorts(),userDetails.getCurrentOu().getId())));
		return JSON;
	}
	
	/**
	 * 保存单渠道合并
	 * @return
	 */
	public String saveSingelChannelsMaintain() throws JSONException {
		 List<String> errorMsg = new ArrayList<String>();
		JSONObject result = new JSONObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(list.getExpDate());
			channelRefManager.addSingelChannels(list.getWhId(), date, list.getChildChannelIdList());
			result.put("msg", SUCCESS);
		}catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("",e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
		request.put(JSON, result);
		return JSON;
	}

	/**
	 * 保存多渠道合并
	 * @return
	 */
	public String saveMoreChannelsMaintain() throws JSONException {
		  List<String> errorMsg = new ArrayList<String>();
	        JSONObject result = new JSONObject();
	        try {
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse(list.getExpDate());
	        	channelRefManager.addMoreChannels(list.getHbChId(),list.getWhId(), date,list.getChannCode(),list.getChannName(),list.getZxShopName(),list.getYdShopName(), list.getChildChannelIdList());
	            result.put("msg", SUCCESS);
	        } catch (BusinessException e) {
	            BusinessException bex = e;
	            do {
	                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
	                log.error(msg);
	                errorMsg.add(msg);
	                bex = bex.getLinkedException();
	            } while (bex != null);
	            result.put("msg", ERROR);
	            result.put("errMsg", JsonUtil.collection2json(errorMsg));
	        } catch (Exception e) {
	            log.error("",e);
	            result.put("msg", e.getCause() + " " + e.getMessage());
	        }
	        request.put(JSON, result);
	        return JSON;
	}
	
	/**
	 * 修改单渠道合并
	 * @return
	 */
	public String updateSingelChannelsMaintain() {
		JSONObject result = new JSONObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(list.getExpDate());
			channelRefManager.updateSingelChannels(list.getId(),list.getHbChId(), list.getWhId(),date, list.getIsCombine());
			result.put("msg", SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		request.put(JSON, result);
		return JSON;
	}
	
	
	/**
	 * 修改多渠道合并
	 * @return
	 */
	public String updateMoreChannelsMaintain() {
		JSONObject result = new JSONObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(list.getExpDate());
			channelRefManager.updateMoreChannels(list.getId(),list.getHbChId(), list.getWhId(),list.getChildChannelIdList(),date, list.getIsCombine());
			result.put("msg", SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		request.put(JSON, result);
		return JSON;
	}
	
	public BiChannelCombineRefCommand getList() {
		return list;
	}

	public void setList(BiChannelCombineRefCommand list) {
		this.list = list;
	}

}
