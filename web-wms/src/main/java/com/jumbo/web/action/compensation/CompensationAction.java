package com.jumbo.web.action.compensation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.DateUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.comm.HttpClientDownUtil;
import com.jumbo.util.comm.ZipTool;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.compensation.CompensationManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.compensation.CompensateStatus;
import com.jumbo.wms.model.compensation.WhCompensationCommand;
import com.jumbo.wms.model.compensation.WhCompensationDetailsCommand;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;



/**
 * @author lihui
 *
 * @createDate 2015年9月22日 下午4:37:15
 */
public class CompensationAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 1413096739416493825L;


    @Autowired
    private CompensationManager compensationManager;

    @Autowired
    private ExcelExportManager Excexport;

    private WhCompensationCommand whCompensationCommand;


    private Long compensationId;

    private Long claimTypeId;

    private Double logisticsepartmentAmt;

    private Double expressDeliveryAmt;

    private String disposeRemark;

    private String startDate;

    private String endDate;

    private String name;

    private String finishStartDate;
    private String finishEndDate;

    /**
     * 初始化页面
     * 
     * @return
     */
    public String initDamageQueryPage() {
        return SUCCESS;
    }

    public String initCheckQueryPage() {
        return SUCCESS;
    }

    /**
     * 索赔列表
     * 
     * @return
     */
    public String findCompensationList() {
        setTableConfig();
        if (whCompensationCommand != null) {
            whCompensationCommand.setStartDate(dateFormat(startDate));
            whCompensationCommand.setEndDate(dateFormat(endDate));
            whCompensationCommand.setStaFinishDateStart(dateFormat(finishStartDate));
            whCompensationCommand.setStaFinishDateEnd(dateFormat(finishEndDate));

        }

        Pagination<WhCompensationCommand> compensationList = compensationManager.findCompensationByParams(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), whCompensationCommand);
        request.put(JSON, toJson(compensationList));
        return JSON;
    }

    public void exportClaimantInfo() throws Exception {
        if (whCompensationCommand != null) {
            whCompensationCommand.setStartDate(dateFormat(startDate));
            whCompensationCommand.setEndDate(dateFormat(endDate));
        }
        List<WhCompensationCommand> compensationList = compensationManager.findCompensationByParamsNoPage(whCompensationCommand);
        List<WhCompensationCommand> compensationList1 = new ArrayList<WhCompensationCommand>();
        for (WhCompensationCommand whCompensationCommand : compensationList) {
            List<WhCompensationDetailsCommand> WhCDList = compensationManager.findCompensationDetailsByParamsNoPage(whCompensationCommand.getId());
            String barCode="";
           try {
           List<SkuCommand>  skuCommandList= compensationManager.findskuBarCodeByStaId(whCompensationCommand.getStaId());
           barCode=skuCommandList.get(0).getBarCode();
           } catch (Exception e) {
               log.error("",e);
           }
            for (WhCompensationDetailsCommand whCompensationDetailsCommand : WhCDList) {
                if (whCompensationCommand.getCommodityCode() != null && whCompensationCommand.getCommodityName() != null) {
                    whCompensationCommand.setCommodityCode(whCompensationCommand.getCommodityCode() + whCompensationDetailsCommand.getSkuCode() + ",");
                    whCompensationCommand.setCommodityName(whCompensationCommand.getCommodityName() + whCompensationDetailsCommand.getSkuName() + ",");
                    whCompensationCommand.setMaterial(barCode);
                } else {
                    whCompensationCommand.setCommodityCode(whCompensationDetailsCommand.getSkuCode() + ",");
                    whCompensationCommand.setCommodityName(whCompensationDetailsCommand.getSkuName() + ",");
                    whCompensationCommand.setMaterial(barCode);
                }
            }
            compensationList1.add(whCompensationCommand);
        }
        //
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = userDetails.getCurrentOu().getName() + "索赔信息";
        fileName = fileName.replace(" ", "");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            Excexport.exportClaimantInfo(os, compensationList1);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    /**
     * check索赔列表
     * 
     * @return
     */
    public String findCompensationCheckList() {
        setTableConfig();
        if (whCompensationCommand != null) {
            whCompensationCommand.setStartDate(dateFormat(startDate));
            whCompensationCommand.setEndDate(dateFormat(endDate));
            whCompensationCommand.setStaFinishDateStart(dateFormat(finishStartDate));
            whCompensationCommand.setStaFinishDateEnd(dateFormat(finishEndDate));

        }
        Pagination<WhCompensationCommand> compensationList = compensationManager.findCompensationByParams(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), whCompensationCommand);
        request.put(JSON, toJson(compensationList));
        return JSON;
    }

    /**
     * 仓库列表
     * 
     * @return
     */
    public String getSendWarehouseforPage() {
        setTableConfig();
        Pagination<OperationUnit> compensationList = compensationManager.getSendWarehouseforPage(tableConfig.getStart(), tableConfig.getPageSize(), name);
        request.put(JSON, toJson(compensationList));
        return JSON;
    }

    /**
     * 索赔明细列表
     * 
     * @return
     */
    public String findCompensationDetailsList() {
        setTableConfig();
        Pagination<WhCompensationDetailsCommand> compensationDetailsList = compensationManager.findCompensationDetailsByParams(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), compensationId);
        request.put(JSON, toJson(compensationDetailsList));
        return JSON;
    }

    /**
     * 获取索赔类型
     * 
     * @return
     */
    public String findCompensateType() {
        request.put(JSON, JsonUtil.collection2json(compensationManager.findCompensateType()));
        return JSON;
    }

    /**
     * 获取发货仓
     * 
     * @return
     */
    public String getSendWarehouse() {
        request.put(JSON, JsonUtil.collection2json(compensationManager.getSendWarehouse()));
        return JSON;
    }

    /**
     * 获取索赔类型
     * 
     * @return
     */
    public String findCompensateCause() {
        request.put(JSON, JsonUtil.collection2json(compensationManager.findCompensateCause(claimTypeId)));
        return JSON;
    }

    /**
     * 处理索赔
     * 
     * @return
     * @throws JSONException
     */
    public String disposeCompensate() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            compensationManager.updateCompensationByParams(CompensateStatus.DISPOSE.getValue(), logisticsepartmentAmt, expressDeliveryAmt, disposeRemark, compensationId, userDetails.getUser());
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 审核索赔
     * 
     * @return
     * @throws JSONException
     */
    public String checkCompensate() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            compensationManager.updateCompensation(CompensateStatus.CHECK.getValue(), logisticsepartmentAmt, expressDeliveryAmt, disposeRemark, compensationId, userDetails.getUser());
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 索赔成功
     * 
     * @return
     * @throws JSONException
     */
    public String compensateSuccess() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            compensationManager.updateCompensationByParams(CompensateStatus.SUCCEED.getValue(), null, null, null, compensationId, userDetails.getUser());
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 索赔失败
     * 
     * @return
     * @throws JSONException
     */
    public String compensateFail() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            compensationManager.updateCompensationByParams(CompensateStatus.FAIL.getValue(), null, null, null, compensationId, userDetails.getUser());
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 批量下载附件
     * 
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public String downFile() throws JSONException, IOException {
        if (whCompensationCommand != null) {
            whCompensationCommand.setStartDate(dateFormat(startDate));
            whCompensationCommand.setEndDate(dateFormat(endDate));
        }
        Pagination<WhCompensationCommand> compensationList = compensationManager.findCompensationByParams(-1, -1, null, whCompensationCommand);
        if (compensationList.getItems() != null) {
            Map<String, String> cfg = compensationManager.getCOMPENSATIONConfig(); // 查询配置信息
            String localDir = cfg.get("localDownPath");

            if (localDir == null || "".equals(localDir)) {
                return null;
            }

            long pathName = new Date().getTime();
            String rootPath = localDir + pathName;
            File dir = new File(rootPath);
            if (!dir.exists() && !dir.isDirectory()) {
                dir.mkdirs(); // 创建目录
            }
            for (WhCompensationCommand wcc : compensationList.getItems()) {
                if (wcc.getFileUrl() != null && !"".equals(wcc.getFileUrl())) {
                    try {
                        // 下载文件
                        HttpClientDownUtil.download(wcc.getFileUrl(), rootPath, wcc.getClaimCode());
                    } catch (IOException e) {
                        log.error("", e);

                    }
                }
            }
            try {
                // 压缩
                ZipTool.zip(rootPath);

                File file = new File(rootPath + ".zip");// path是根据日志路径和文件名拼接出来的
                String filename = file.getName();// 获取日志文件名称
                InputStream fis = new BufferedInputStream(new FileInputStream(rootPath + ".zip"));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                response.reset();
                // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
                response.addHeader("Content-Length", "" + file.length());
                OutputStream os = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                os.write(buffer);// 输出文件
                os.flush();
                os.close();
                // 删除文件及文件夹
                FileUtils.deleteDirectory(dir);
                FileUtils.deleteQuietly(file);
            } catch (Exception e) {
                log.error("批量下载异常", e);
            }
        }



        return null;

    }

    /**
     * 查询日期format
     * 
     * @return
     */
    private Date dateFormat(String dateStr) {
        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }
        Date sDat;
        try {
            sDat = FormatUtil.stringToDate(dateStr);
        } catch (ParseException e) {
            return null;
        }
        boolean flag = DateUtil.isYearDate(dateStr);
        if (flag) {
            // 日期为"yyy-mm-dd"默认补加一天
            sDat = DateUtil.addDays(sDat, 1);
        }
        return sDat;
    }

    /**
     * 批量下载之前判断文件大小
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public String  confirmFileSize() throws JSONException, IOException {
        JSONObject ob=new JSONObject();
        if (whCompensationCommand != null) {
            whCompensationCommand.setStartDate(dateFormat(startDate));
            whCompensationCommand.setEndDate(dateFormat(endDate));
        }
        Pagination<WhCompensationCommand> compensationList = compensationManager.findCompensationByParams(-1, -1, null, whCompensationCommand);
        long fileSize=0;
        if (compensationList.getItems() != null) {
            Map<String, String> cfg = compensationManager.getCOMPENSATIONConfig(); // 查询配置信息
            String localDir = cfg.get("localDownPath");

            if (localDir == null || "".equals(localDir)) {
                return null;
            }

            long pathName = new Date().getTime();
            String rootPath = localDir + pathName;
            File dir = new File(rootPath);
            if (!dir.exists() && !dir.isDirectory()) {
                dir.mkdirs(); // 创建目录
            }
            for (WhCompensationCommand wcc : compensationList.getItems()) {
                if (wcc.getFileUrl() != null && !"".equals(wcc.getFileUrl())) {
                    try {
                        // 下载文件
                        HttpClientDownUtil.download(wcc.getFileUrl(), rootPath, wcc.getClaimCode());
                        //根据文件路径获取文件
                        File[] fileList = dir.listFiles();
                        for (File file : fileList) {
                     //     System.out.println(file.length());
                          fileSize=fileSize+file.length();
                          //如果已下载文件总和大于15M，删除所有已经下载的文件
                          if(fileSize>15728645){
                              // 删除文件及文件夹
                              for (File file1 : fileList) {
                                  FileUtils.deleteQuietly(file1);
                            }
                          }
                        }
                    } catch (IOException e) {
                        log.error("批量下载异常", e);
                    }
                }
            }
            FileUtils.deleteDirectory(dir);
            if(fileSize>15728645){
                ob.put("message", "error");
            }else{
                ob.put("message", "ok");
            }
            request.put(JSON, ob);
        }
        return JSON;
    }
    
    public WhCompensationCommand getWhCompensationCommand() {
        return whCompensationCommand;
    }

    public void setWhCompensationCommand(WhCompensationCommand whCompensationCommand) {
        this.whCompensationCommand = whCompensationCommand;
    }

    public Long getCompensationId() {
        return compensationId;
    }

    public void setCompensationId(Long compensationId) {
        this.compensationId = compensationId;
    }

    public Long getClaimTypeId() {
        return claimTypeId;
    }

    public void setClaimTypeId(Long claimTypeId) {
        this.claimTypeId = claimTypeId;
    }

    public Double getLogisticsepartmentAmt() {
        return logisticsepartmentAmt;
    }

    public void setLogisticsepartmentAmt(Double logisticsepartmentAmt) {
        this.logisticsepartmentAmt = logisticsepartmentAmt;
    }

    public Double getExpressDeliveryAmt() {
        return expressDeliveryAmt;
    }

    public void setExpressDeliveryAmt(Double expressDeliveryAmt) {
        this.expressDeliveryAmt = expressDeliveryAmt;
    }

    public String getDisposeRemark() {
        return disposeRemark;
    }

    public void setDisposeRemark(String disposeRemark) {
        this.disposeRemark = disposeRemark;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinishStartDate() {
        return finishStartDate;
    }

    public void setFinishStartDate(String finishStartDate) {
        this.finishStartDate = finishStartDate;
    }

    public String getFinishEndDate() {
        return finishEndDate;
    }

    public void setFinishEndDate(String finishEndDate) {
        this.finishEndDate = finishEndDate;
    }


}
