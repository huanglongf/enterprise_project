package com.jumbo.web.action.warehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.SkuTagManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.trans.SkuTag;
import com.jumbo.wms.model.trans.SkuTagCommand;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
/**
 * 商品标签
 * @author xiaolong.fei
 *
 */
public class SkuTagAction extends BaseJQGridProfileAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SkuTag tag;
	private Integer status;
	private Integer type;
	private SkuCommand skuCmd;
	private File skuFile;
    @Autowired
    private ExcelReadManager excelReadManager;
	
	@Autowired
	private SkuTagManager skuTagManager;
	
	public String manager() {
        return SUCCESS;
    }
	
	public String findSkuTag(){
		setTableConfig();
		Pagination<SkuTagCommand> tagList = skuTagManager.findSkuTag(tableConfig.getStart(), tableConfig.getPageSize(), tag, tableConfig.getSorts());
		request.put(JSON, toJson(tagList));
		return JSON;
	}
	
    public String findSkuTagByPagination() {
        setTableConfig();
        try {
            Pagination<SkuTagCommand> tagList = skuTagManager.findSkuTagByPagination(tag, status, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
            if(null != tagList)
                request.put(JSON, toJson(tagList));
            else
            {
                JSONObject result = new JSONObject();
                result.put("result", NONE);
                request.put(JSON, result);   
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }
    
    public String findSkuTagExistByCode(){
        try {
            boolean result = skuTagManager.findSkuTagExistByCode(tag);
            JSONObject ret = new JSONObject();
            ret.put("result", result);
            request.put(JSON, ret);
        } catch (JSONException e) {
            log.error("", e);
        }   
        return JSON;
    }
    
    public String saveSkuTag() throws JSONException {
        JSONObject ret = new JSONObject();
        try {
            skuTagManager.saveSkuTag(tag, status, type);
            ret.put("msg", "success");
        } catch (Exception e) {
            ret.put("msg", "error");
            log.error("", e);
        } 
        request.put(JSON, ret);
        return JSON;
    }
    
    public String findAllSkuByTagId() {
        setTableConfig();
        try {
            Pagination<SkuCommand> tagList = skuTagManager.findAllSkuByTagId(tag, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
            if(null != tagList)
                request.put(JSON, toJson(tagList));
            else
            {
                JSONObject result = new JSONObject();
                result.put("result", NONE);
                request.put(JSON, result);   
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }
    
    public String findAllSkuRef() {
        setTableConfig();
        try {
            Pagination<SkuCommand> tagList = skuTagManager.findAllSkuRef(tag, skuCmd, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
            if(null != tagList)
                request.put(JSON, toJson(tagList));
            else
            {
                JSONObject result = new JSONObject();
                result.put("result", NONE);
                request.put(JSON, result);   
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }
    
    public String importRefSku() throws Exception{
        String msg = SUCCESS;
        if (skuFile == null) {
            msg = (skuFile == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                if(null == tag || null == tag.getId()){
                    request.put("msg", "error");
                    return SUCCESS;
                }
                Long tagId = tag.getId();
                ReadStatus rs = excelReadManager.importRefSku(skuFile,tagId);
                if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = new ArrayList<String>();
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException be = (BusinessException) ex;
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                        }
                    }
                    request.put("msg", list);
                } else {
                    request.put("msg", msg);
                }
                return SUCCESS;
            } catch (BusinessException e) {
                request.put("msg", "error");
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            }catch (Exception e) {
                request.put("msg", "error");
                log.error("",e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public String findSkuTagByCode() {
        String tagCode = tag.getCode();
        SkuTag tag = skuTagManager.findSkuTagByCode(tagCode);
        request.put(JSON, JsonUtil.obj2json(tag));
        return JSON;
    }

	public SkuTag getTag() {
		return tag;
	}
	public void setTag(SkuTag tag) {
		this.tag = tag;
	}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public SkuCommand getSkuCmd() {
        return skuCmd;
    }

    public void setSkuCmd(SkuCommand skuCmd) {
        this.skuCmd = skuCmd;
    }

    public File getSkuFile() {
        return skuFile;
    }

    public void setSkuFile(File skuFile) {
        this.skuFile = skuFile;
    }
    
}
