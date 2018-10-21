package com.jumbo.web.action.warehouse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.DistriButionAreaManager;
import com.jumbo.wms.model.warehouse.AreaType;
import com.jumbo.wms.model.warehouse.BingdingDetai;
import com.jumbo.wms.model.warehouse.DistriButionArea;
import com.jumbo.wms.model.warehouse.DistriButionAreaLoc;
import com.jumbo.wms.model.warehouse.TransactionType;
import loxia.dao.Page;
import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.excel.WriteStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
/**
 * 
 * @author lijinggong+2018年7月26日
 *
 *
 */

public class DistriButionAreaAction extends BaseJQGridProfileAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9187747012302014031L;
    
	@Autowired
	private DistriButionAreaManager distriButionAreaMagger;
    @Autowired
    private ExcelExportManager excelExportManager;
    private File file;
	/**
	 * 打开页面
	 * @return
	 */
	public String openDistriButionArea() {
	    Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
  		List<DistriButionAreaLoc>  name = distriButionAreaMagger.getName();
  		List<DistriButionAreaLoc>  distriButionName = distriButionAreaMagger.getDistriButionNameList(mainWhid);
  		List<DistriButionAreaLoc>  distriButionCode = distriButionAreaMagger.getDistriButionCodeList(mainWhid);
		ServletActionContext.getRequest().setAttribute("nameList",name);
		ServletActionContext.getRequest().setAttribute("distriButionNameList",distriButionName);
		ServletActionContext.getRequest().setAttribute("distriButionCodeList",distriButionCode);
		return SUCCESS;	
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getDistriButionAreaList() {
	    Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
		setTableConfig();
		Page page = new Page();
		page.setStart(tableConfig.getStart());
		page.setSize(tableConfig.getPageSize());
		try {
			Pagination<DistriButionArea> zoonList =  distriButionAreaMagger.getDistriButionArea(mainWhid,page);
	        request.put(JSON, toJson(zoonList));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
        return JSON;
	}
    
	/**
	 * 根据分配区域编码和分配区域名称进行模糊查询
	 * @return
	 */
    public String findDistriButionAreaList() {
        Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
		setTableConfig();
		Page page = new Page();
		page.setStart(tableConfig.getStart());
		page.setSize(tableConfig.getPageSize());
		try {
			Pagination<DistriButionArea> zoonList = distriButionAreaMagger.findDistriButionArea(mainWhid,page,distriButionAreaCode,distriButionAreaName);
	        request.put(JSON, toJson(zoonList));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
        return JSON;
	}
    
    //增加
    public String insertDistriButionArea() {
    	JSONObject result = new JSONObject();
        Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
		Long  createId = userDetails.getUser().getId();//登入用户ID
		String createUser = userDetails.getUsername();//登录用户的ID
  		try {
  	  		Integer success = distriButionAreaMagger.insertDistriButionArea(distriButionAreaCode, distriButionAreaName,mainWhid,createId,createUser);
			result.put("msg",success.toString());
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
  		request.put(JSON, result);
        return JSON;
    }
    
   //修改
  	public String updateDistriButionArea() {
  	    Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
  		JSONObject result = new JSONObject();
  		try {
  	  		Integer success = distriButionAreaMagger.updateDistriButionArea(mainWhid,id, distriButionAreaCode, distriButionAreaName);
			result.put("msg",success.toString());
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
  		request.put(JSON, result);
        return JSON;	
  	}
  	
  	 //删除文件
  	public String deleteDistriButionArea() {
  		JSONObject result = new JSONObject();
  	    try {
  	    	 Integer success = distriButionAreaMagger.deleteDistriButionArea(id);
			 result.put("msg",success.toString());
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
        request.put(JSON, result);
  		return JSON;	
  	}
  	
  	//取得判断条件
  	public String judgeDistriButionArea() {
  		JSONObject result = new JSONObject();
  	    try {
  	    	String success = distriButionAreaMagger.judgeDistriButionArea(distriButionAreaCode);
			result.put("data",success.toString());
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
        request.put(JSON, result);
  		return JSON;
  	}
  	
  	
  	
  	
    public String distriButionAreaCode;
    public String distriButionAreaName;
    public Long id;
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDistriButionAreaCode() {
		return distriButionAreaCode;
	}


	public void setDistriButionAreaCode(String distriButionAreaCode) {
		this.distriButionAreaCode = distriButionAreaCode;
	}


	public String getDistriButionAreaName() {
		return distriButionAreaName;
	}


	public void setDistriButionAreaName(String distriButionAreaName) {
		this.distriButionAreaName = distriButionAreaName;
	}
    
	 /* 区域绑定库位
     */
    //分页查询区域绑定库位
	/**
	 * 
	 * @return
	 */
	public String getDistriButionAreaLoc() {
	    Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
		setTableConfig();
		Page page = new Page();
		page.setStart(tableConfig.getStart());
		page.setSize(tableConfig.getPageSize());
		try {
			Pagination<DistriButionAreaLoc> zoonList =  distriButionAreaMagger.getDistriButionAreaLoc(mainWhid,page);
			request.put(JSON, toJson(zoonList));
		} catch (Exception e) {
			log.error(e.getMessage());
		}    
        return JSON;
	} 
	//根据条件进行查询进行查询
	public String findDistriButionAreaLoc() {
	    Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
		setTableConfig();
		Page page = new Page();
		page.setStart(tableConfig.getStart());
		page.setSize(tableConfig.getPageSize());
		try {
			Pagination<DistriButionAreaLoc> zoonList =  distriButionAreaMagger.findDistriButionAreaLoc(mainWhid,page,locCodeName, locCode, locDistriButionAreaCode, locDistriButionAreaName);
	        request.put(JSON, toJson(zoonList));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
        return JSON;		
	}
	//删除
  	public String deleteDistriButionAreaLoc() {
  		JSONObject result = new JSONObject();
  		try {
  	    Integer success = distriButionAreaMagger.deleteDistriButionAreaLoc(locId);
  	     result.put("msg",success.toString());
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
        request.put(JSON, result);
  		return JSON;	
  	}
  	
  	
  	
	private Long locId;
	private String locCodeName;
	private String locCode;
	private String locDistriButionAreaCode;
	private String locDistriButionAreaName;
	
   public Long getLocId() {
		return locId;
	}


	public void setLocId(Long locId) {
		this.locId = locId;
	}


	public String getLocCodeName() {
		return locCodeName;
	}


	public void setLocCodeName(String locCodeName) {
		this.locCodeName = locCodeName;
	}


	public String getLocCode() {
		return locCode;
	}


	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}


	public String getLocDistriButionAreaCode() {
		return locDistriButionAreaCode;
	}


	public void setLocDistriButionAreaCode(String locDistriButionAreaCode) {
		this.locDistriButionAreaCode = locDistriButionAreaCode;
	}


	public String getLocDistriButionAreaName() {
		return locDistriButionAreaName;
	}


	public void setLocDistriButionAreaName(String locDistriButionAreaName) {
		this.locDistriButionAreaName = locDistriButionAreaName;
	}


	/*区域绑定作业类型
     */
    //区域绑定作业类型
	public String getDistriButionAreaType() {
	    Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
		setTableConfig();
		Page page = new Page();
		page.setStart(tableConfig.getStart());
		page.setSize(tableConfig.getPageSize());
		try {
			Pagination<AreaType> zoonList =  distriButionAreaMagger.getDistriButionAreaType(mainWhid,page);
	        request.put(JSON, toJson(zoonList));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
        return JSON;
	} 
	
	//根据条件进行查询
	public String findDistriButionAreaType() {
	    Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
		setTableConfig();
		Page page = new Page();
		page.setStart(tableConfig.getStart());
		page.setSize(tableConfig.getPageSize());
        try {
            Pagination<AreaType> zoonList =  distriButionAreaMagger.findDistriButionAreaType(mainWhid,page, typeDistriButionAreaCode, typeDistriButionAreaName);
            request.put(JSON, toJson(zoonList));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
        return JSON;
	}
	
    private String typeDistriButionAreaCode;
    private String typeDistriButionAreaName; 
    
    public String getTypeDistriButionAreaCode() {
        return typeDistriButionAreaCode;
    }


    public void setTypeDistriButionAreaCode(String typeDistriButionAreaCode) {
        this.typeDistriButionAreaCode = typeDistriButionAreaCode;
    }


    public String getTypeDistriButionAreaName() {
        return typeDistriButionAreaName;
    }


    public void setTypeDistriButionAreaName(String typeDistriButionAreaName) {
        this.typeDistriButionAreaName = typeDistriButionAreaName;
    }


    /**
      * 作用类型加载
     */
    
    public String getTransActionType() {
        Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
    	setTableConfig();
		Page page = new Page();
		page.setStart(tableConfig.getStart());
		page.setSize(tableConfig.getPageSize());
      try {
    	  Pagination<TransactionType> zoonList =  distriButionAreaMagger.getTransActionType(mainWhid,page);
          request.put(JSON, toJson(zoonList));	
		} catch (Exception e) {
			log.error(e.getMessage());
		}
        return JSON;
    }
    
    //区域绑定作用类型
    public String bindingTransAction() {
    	JSONObject result = new JSONObject();
  		try {
  	  		Integer success = distriButionAreaMagger.bindingTransAction(distriButionAreaIds,transActionTypeIds);
			result.put("msg",success.toString());
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
  		request.put(JSON, result);
        return JSON;
     }
    
    //取消绑定
    public String cancelBinding() {
    	JSONObject result = new JSONObject();
  		try {
  	  		Integer success = distriButionAreaMagger.cancelBinding(distriButionAreaIds, transActionTypeIds);
			result.put("msg",success.toString());
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
  		request.put(JSON, result);
        return JSON;
    }
    //加载已绑定的作业类型
    public String findBingdingDetai() {
        Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
    	setTableConfig();
		Page page = new Page();
		page.setStart(tableConfig.getStart());
		page.setSize(tableConfig.getPageSize());
		try {
			Pagination<BingdingDetai> zoonList =  distriButionAreaMagger.findBingdingDetai(mainWhid,page, bingdingDetaiId);
	        request.put(JSON, toJson(zoonList));		
		 } catch (Exception e) {
					log.error(e.getMessage());
		   }
        return JSON;
    }
    
    
    private String distriButionAreaIds;
    private String transActionTypeIds;
    private Long bingdingDetaiId;
    
	public Long getBingdingDetaiId() {
		return bingdingDetaiId;
	}


	public void setBingdingDetaiId(Long bingdingDetaiId) {
		this.bingdingDetaiId = bingdingDetaiId;
	}


	public String getDistriButionAreaIds() {
		return distriButionAreaIds;
	}


	public void setDistriButionAreaIds(String distriButionAreaIds) {
		this.distriButionAreaIds = distriButionAreaIds;
	}


	public String getTransActionTypeIds() {
		return transActionTypeIds;
	}


	public void setTransActionTypeIds(String transActionTypeIds) {
		this.transActionTypeIds = transActionTypeIds;
	}
    
	
	/**
	 * 导入区域类型绑定库位
	 * @return
	 */
    public String saveDwhDistriButionAreaLocImport() {
        Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
        String msg = SUCCESS; 
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if(file == null ) {
            msg = "上传文件不能为空!!!";
            log.error(msg);
            request.put("msg", msg);
        }else {
            try {
                ReadStatus rs =  distriButionAreaMagger.saveDwhDistriButionAreaLocImport(mainWhid,file, userDetails.getCurrentOu());
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) request.put("msg", rs.getExceptions().get(0).getMessage());
                }
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
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
                }
            } catch (BusinessException e) {
                request.put("msg", ERROR);
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
            } catch (Exception e) {
                request.put("msg", ERROR);
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }
    
    /**
     * 导出库位
     * 
     * @throws IOException
     */
    public String  exportDistriBution() throws IOException{
        JSONObject result = new JSONObject();
        Long  mainWhid = userDetails.getCurrentOu().getId();//仓库ID
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss");
        String dateTime = sdf.format(new Date());
        response.setHeader("Content-Disposition", "attachment;filename=distributionarealoc"+Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            WriteStatus ws = excelExportManager.exportDistriBution(mainWhid,outs,locCodeName,locCode,locDistriButionAreaCode,locDistriButionAreaName);
            result.put("msg","1");
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("exportDistriBution Exception:", e);
            };
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
        request.put(JSON, result);
        return JSON;
    }
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
   
}
