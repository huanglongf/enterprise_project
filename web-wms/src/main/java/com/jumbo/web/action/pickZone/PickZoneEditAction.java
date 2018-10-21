package com.jumbo.web.action.pickZone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.pickZone.PickZoneEditManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.command.pickZone.WhPickZoneInfoCommand;
import com.jumbo.wms.model.command.pickZone.WhPickZoonCommand;
import com.jumbo.wms.model.pickZone.WhPickZoon;


/**
 * @author gianni.zhang
 * 
 *         2015年7月8日 下午3:58:57
 */
public class PickZoneEditAction extends BaseJQGridProfileAction {
    /**
     * 
     */
    private static final long serialVersionUID = -504486542376335574L;

    @Autowired
    private PickZoneEditManager pickZoneEditManager;
    
    @Autowired
    private ExcelExportManager excelExportManager;
    
    @Autowired
    private ExcelReadManager excelReadManager;

    private String code;
    private String name;
    private Long id;
    private String district;
    private String location;
    private String pickZoneCode;
    private String pickZoneName;
    private String fileName;
    private List<String> postData;
    private File uploadFile;
    /*private HttpServletResponse response;
    private InputStream inputStream;*/
    private Integer flag;
    private Long whZoonId;



    public String initPickZoneEditPage() {
        return SUCCESS;
    }

    /**
     * 分配失败-手动设置 加载
     * 
     * @return
     */
    public String pickfailedManualpick() {
        return SUCCESS;
    }
    
    public String pickShouManualPick() {
        return SUCCESS;
    }

    public String findPickZoneList() {
        setTableConfig();
        Long ouId = this.userDetails.getCurrentOu().getId();
        Pagination<WhPickZoonCommand> whPickZoneList = pickZoneEditManager.findPickZoneList(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), code, name, ouId);
        request.put(JSON, toJson(whPickZoneList));
        return JSON;
    }
    
    public String addPickZone(){
        String flag = null;
        JSONObject result = new JSONObject();
        User user = this.userDetails.getUser();
        OperationUnit ou = this.userDetails.getCurrentOu();
        try{
            flag = pickZoneEditManager.addPickZone(code, name, user, ou, whZoonId);
            if("SUCCESS".equals(flag)){
                result.put("flag", "success");
                request.put(JSON, result); 
            }
            else if("EXIST".equals(flag)){
                result.put("flag", "exist");
                request.put(JSON, result); 
            }
            else{
                result.put("flag", "failed");
                request.put(JSON, result); 
            }
        } catch(Exception e){
        }
        return JSON;
    }
    
    public String deletePickZone(){
        String flag = null;
        JSONObject result = new JSONObject();
        User user = this.userDetails.getUser();
        try{
            flag = pickZoneEditManager.deletePickZone(id, user);
            if("SUCCESS".equals(flag)){
                result.put("flag", "success");
                request.put(JSON, result);
            } else if ("USEING".equals(flag)) {
                result.put("flag", "useing");
                request.put(JSON, result);
            } else {
                result.put("flag", "failed");
                request.put(JSON, result); 
            }
        } catch(Exception e){
        }
        return JSON;
    }
    
    public String editPickZoneName(){
        String flag = null;
        JSONObject result = new JSONObject();
        User user = this.userDetails.getUser();
        try{
            flag = pickZoneEditManager.editPickZoneName(id, name, user, whZoonId);
            if("SUCCESS".equals(flag)){
                result.put("flag", "success");
                request.put(JSON, result);
            }
            else{
                result.put("flag", "failed");
                request.put(JSON, result);
            }
        } catch(Exception e){
            
        }
        return JSON;
    }
    
    public String findDistrictList(){
        Long ouId = this.userDetails.getCurrentOu().getId();
        request.put(JSON, JsonUtil.collection2json(pickZoneEditManager.findDistrictList(ouId)));
        return JSON;
    }
    
    public String findPickZoneInfo(){
        Long ouId = this.userDetails.getCurrentOu().getId();
        request.put(JSON, JsonUtil.collection2json(pickZoneEditManager.findPickZoneInfo(ouId)));
        return JSON;
    }
    
    public String findPickZoneInfoList(){
        setTableConfig();
        Long ouId = this.userDetails.getCurrentOu().getId();
        Pagination<WhPickZoneInfoCommand> pickZoneInfoList = pickZoneEditManager.findPickZoneInfoList(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), district, location, pickZoneName, pickZoneCode, ouId);
        request.put(JSON, toJson(pickZoneInfoList));
        return JSON;
    }
    
    public String exportLocation() throws Exception{
        String fileName = this.getCurrentOu().getCode();
        Long ouId = this.getCurrentOu().getId();
        setFileName(fileName + Constants.EXCEL_XLS);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Integer count = pickZoneEditManager.countLocationByParam(district, location, pickZoneName, pickZoneCode, ouId);
        if(count == 1){
            request.put("result", "exceeded");
            return ERROR;
        }
        excelExportManager.exportLocationByParam(out, district, location, pickZoneName, pickZoneCode, ouId);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        setInputStream(in);
        return SUCCESS;
        
        
        /*String fileName = this.getCurrentOu().getCode();
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        try {
            excelExportManager.exportLocationByParam(response.getOutputStream(), district, location, pickZoneName, pickZoneCode);
        } catch (IOException e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
        return SUCCESS;*/
    }
    
    public String importPickZone(){
        Long userId = this.userDetails.getUser().getId();
        Long ouId = this.userDetails.getCurrentOu().getId();
        try {
            ReadStatus rs = excelReadManager.importPickZoneInfo(uploadFile, userId, ouId, flag);
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                request.put("result", SUCCESS);
            } else if (rs.getStatus() > 0) {
                String msg = "";
                List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                for (String s : list) {
                    msg += s;
                }
                request.put("result", ERROR);
                request.put("message", msg);
            } else if(rs.getStatus() == -1){
                request.put("message", "");
                List<String> list = new ArrayList<String>();
                for (Exception ex : rs.getExceptions()) {
                    if (ex instanceof BusinessException) {
                        BusinessException be = (BusinessException) ex;
                        list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + "exist: " + be.getArgs()[0].toString() + " 已存在拣货区域: " + be.getArgs()[1].toString() + " 和拣货顺序: " + be.getArgs()[2].toString()) + Constants.HTML_LINE_BREAK);
                    }
                }
                request.put("result", "override");
                request.put("message", JsonUtil.collection2json(list));
//                request.put("msg", list);
            }
            else if((rs.getStatus() == -2)){
                request.put("message", "");
                List<String> list = new ArrayList<String>();
                for (Exception ex : rs.getExceptions()) {
                    if (ex instanceof BusinessException) {
                        BusinessException be = (BusinessException) ex;
                        if(1500000002 == be.getErrorCode())
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getArgs()[1].toString() + "_required: " + be.getArgs()[2].toString() + "的拣货区域和拣货顺序必须同时填写或者同时为空") + Constants.HTML_LINE_BREAK);
                        else if(1500000003 == be.getErrorCode())
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getArgs()[1].toString() + "_error: 不存在" + be.getArgs()[2].toString()) + Constants.HTML_LINE_BREAK);
                        else if(1500000004 == be.getErrorCode())
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + "exist: " + be.getArgs()[0].toString() + " 已存在拣货区域: " + be.getArgs()[1].toString() + " 和拣货顺序: " + be.getArgs()[2].toString()) + Constants.HTML_LINE_BREAK);
                        else if(1500000005 == be.getErrorCode())
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getArgs()[0].toString() + "_missing: 库区或者库位缺失") + Constants.HTML_LINE_BREAK);
                        else if(1500000006 == be.getErrorCode())
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getArgs()[0].toString() + "_error: 不存在拣货区域: " + be.getArgs()[1].toString()) + Constants.HTML_LINE_BREAK);
                    }
                }
                request.put("result", ERROR);
                request.put("message", JsonUtil.collection2json(list));
            }
        } catch (BusinessException e) {
//            request.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            request.put("result", ERROR);
            request.put("message", "error");
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
            request.put("message", sb.toString());
        } catch (Exception e) {
            log.error("", e);
            request.put("result", ERROR);
            request.put("message", "error");
        }
        return SUCCESS;
    }
    
    public String findPickZoneInPickingList(){
        return null;
    }
    
    /**
     * 根据仓库查询拣货区域
     * 
     * @return
     */
    public String findPickZoneByOuId() {
        setTableConfig();
        List<WhPickZoon> list = pickZoneEditManager.findPickZoneByOuId(getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPickZoneCode() {
        return pickZoneCode;
    }

    public void setPickZoneCode(String pickZoneCode) {
        this.pickZoneCode = pickZoneCode;
    }

    public String getPickZoneName() {
        return pickZoneName;
    }

    public void setPickZoneName(String pickZoneName) {
        this.pickZoneName = pickZoneName;
    }

    public List<String> getPostData() {
        return postData;
    }

    public void setPostData(List<String> postData) {
        this.postData = postData;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Long getWhZoonId() {
        return whZoonId;
    }

    public void setWhZoonId(Long whZoonId) {
        this.whZoonId = whZoonId;
    }
    
    /*public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public InputStream getInputStream(){  
        return inputStream;   
    }  */


}
