package com.jumbo.web.action.warehouse;



import java.io.File;
import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.SpecialSkuManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;

/**
 * 特殊处理商品维护
 * 
 * @author jinlong.ke
 * 
 */
public class SpecialSkuMainTainAction extends BaseJQGridProfileAction {

	private static final long serialVersionUID = -7711848417364599923L;
	/**
	 * 供应商编码
	 */
	private String supplierCode;
	/**
	 * 特殊处理商品ID
	 */
	private Long ssId;
	@Autowired
	private SpecialSkuManager specialSkuManager;
	@Autowired
	private ExcelReadManager read;
	
	private File file;//导入供应商编码
	
	private List<Long>idList;
	/**
	 * 特殊处理商品维护页面
	 * 
	 * @return
	 */
	public String specialSkuCommon() {
		return SUCCESS;
	}

	/**
	 * 获取当前仓库下维护的特殊处理商品
	 * 
	 * @return
	 */
	public String selectAllSpecialSku() {
		setTableConfig();
		if("".equals(supplierCode)){
		    supplierCode=null; 
		}
		request.put(JSON, toJson(specialSkuManager.selectAllSpecialSkuByOu(tableConfig.getStart(),tableConfig.getPageSize(),userDetails.getCurrentOu().getId(), tableConfig.getSorts(),supplierCode)));
		return JSON;
	}

	/**
	 * 添加新的特殊处理商品
	 * 
	 * @return
	 * @throws JSONException
	 */
	public String addSpecialSkuBySupplierCode() throws JSONException {
	    setTableConfig();
		JSONObject json = new JSONObject();
		try {
			specialSkuManager.addSpecialSkuBySupplierCode(tableConfig.getStart(),tableConfig.getPageSize(),userDetails.getCurrentOu().getId(), supplierCode);
			json.put("rs", SUCCESS);
		} catch (BusinessException e) {
			log.error("", e);
			json.put("rs", ERROR);
			String msg = getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), new Object[] { "" });
			json.put("msg", msg);
		} catch (Exception e) {
			log.error("", e);
			json.put("rs", SUCCESS);
			String msg = getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_SYS_ERROR, new Object[] { "" });
			json.put("msg", msg);
		}
		request.put(JSON, json);
		return JSON;
	}

	/**
	 * 根据id删除
	 * 
	 * @return
	 * @throws JSONException
	 */
	public String deleteSpecialSkuById() throws JSONException {
		JSONObject json = new JSONObject();
		try {
			specialSkuManager.deleteSpecialSkuById(idList);
			json.put("rs", SUCCESS);
		} catch (Exception e) {
			log.error("", e);
			json.put("rs", ERROR);
		}
		request.put(JSON, json);
		return JSON;
	}
	
    public String importSkuSupplierCode() throws JSONException {
        List<String> sList = read.importsupplierCode(file);
        for (String s : sList) {
            int i = 0;
            if(s==null||s.equals("")){
                request.put("msg","不能有空行");
                return SUCCESS;
            }
            for (String s1 : sList) {
                if (s.equals(s1)) {
                    i++;
                }
            }
            if (i >= 2) {
                request.put("msg","供应商编码 :"+ s + "存在重复");
                return SUCCESS;
            }
        }
        try {
            specialSkuManager.addSpecialSkuBySupplierCodeList(0, 20, userDetails.getCurrentOu().getId(), sList);
            request.put("msg", "导入成功");
        } catch (BusinessException e) {
            log.error("", e);
            String msg = getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), new Object[] {""});
            request.put("msg", msg);
        } catch (Exception e) {
            log.error("", e);
            String msg = getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_SYS_ERROR, new Object[] {""});
            request.put("msg", msg);
        }
        return SUCCESS;
    }
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public Long getSsId() {
		return ssId;
	}

	public void setSsId(Long ssId) {
		this.ssId = ssId;
	}

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    
}
