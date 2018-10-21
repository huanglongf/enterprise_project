package com.lmis.pos.whsOutPlan.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dataFormat.DateUtils;
import com.lmis.common.dynamicSql.dao.DynamicSqlMapper;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.util.StringUtil;
import com.lmis.pos.common.util.ZipUtil;
import com.lmis.pos.whsOutPlan.dao.PosWhsOutPlanMapper;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlan;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanLog;
import com.lmis.pos.whsOutPlan.service.PosWhsOutPlanServiceInterface;

/** 
 * @ClassName: PosWhsOutPlanServiceImpl
 * @Description: TODO(仓库出库计划业务层实现类)
 * @author codeGenerator
 * @date 2018-05-29 15:13:58
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosWhsOutPlanServiceImpl<T extends PosWhsOutPlan> implements PosWhsOutPlanServiceInterface<T> {

    @Resource(name="dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<PosWhsOutPlan> dynamicSqlService;
    @Value("${lmis_pos.podata.excel_import_file_upload_path}")
    private String excelImportFileUploadPath;
    @Autowired
    private PosWhsOutPlanMapper<T> posWhsOutPlanMapper;
    @Autowired
    private HttpSession session;
    @Autowired
    private DynamicSqlMapper<T> dynamicSqlMapper;
    @Override
    public LmisResult<?> executeSelect(T t, LmisPageObject pageObject) throws Exception {
        Page page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
        posWhsOutPlanMapper.retrieve(t);
        LmisResult lmisResult = new LmisResult();
        lmisResult.setMetaAndData(page.toPageInfo());
        lmisResult.setCode("S1001");
        return lmisResult;
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeSelect(T t) throws Exception {
        List<?> result = posWhsOutPlanMapper.retrieve(t);
        if(result.size()<1) throw new Exception("查无记录，数据异常");
        if(result.size() > 1) throw new Exception("记录存在多条，数据异常");
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result);
    }

    @Override
    public LmisResult<?> executeInsert(T t) throws Exception {

        // TODO(业务校验)
        t.setCreateBy(session.getAttribute("lmisUserId").toString());
        //更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());
        //所属机构
        t.setPwrOrg(session.getAttribute("lmisUserOrg").toString());

        // 插入操作
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS,posWhsOutPlanMapper.create(t));
    }

    @Override
    public LmisResult<?> executeUpdate(T t) throws Exception {

        // TODO(业务校验)
        
        List<Map<String,Object>> list = posWhsOutPlanMapper.findSkutypeOutrate(t.getWhsCode());
        if(list.size()<3){
            throw new Exception("未生成鞋服配占比");
        }
        
        for (int i = 0; i < list.size(); i++){
            if("20".equals(list.get(i).get("type"))){
                t.setPlannedOutShoe(new BigDecimal(t.getPlannedOut()).multiply(new BigDecimal(list.get(i).get("outrate")+"")).setScale( 0, BigDecimal.ROUND_DOWN ).intValue());
            }
            if("10".equals(list.get(i).get("type"))){
                t.setPlannedOutAddress(new BigDecimal(t.getPlannedOut()).multiply(new BigDecimal(list.get(i).get("outrate")+"")).setScale( 0, BigDecimal.ROUND_DOWN ).intValue());
            }
        }
            t.setPlannedOutAccessory(t.getPlannedOut()-t.getPlannedOutAddress()-t.getPlannedOutShoe());
            t.setUpdateBy(session.getAttribute("lmisUserId").toString());
            // 更新操作
        return new LmisResult(LmisConstant.RESULT_CODE_SUCCESS, posWhsOutPlanMapper.update(t));
    }

    @Override
    public LmisResult<?> deletePosWhsOutPlan(T t) throws Exception {

        // TODO(业务校验)
        if("".equals(t.getId())||"".equals(t.getScheduleDate())||"".equals(t.getWhsCode())){
            throw new Exception("字段不全");
        }
        List list = posWhsOutPlanMapper.selectAllocate(t);
        if(list.size()>0){
            throw new Exception("已分配入库计划，不允许删除");
        }
        // 删除操作
        return new LmisResult(LmisConstant.RESULT_CODE_SUCCESS, posWhsOutPlanMapper.delete(t));
    }
public static void main(String[] args) throws FileNotFoundException, IOException{
    File file = new File("D:\\22.xls");
    System.out.println(file.getName().endsWith(".xlsx"));
    Workbook workbook = new HSSFWorkbook(new FileInputStream(file)); 
    Sheet sheet = null;
    sheet = workbook.getSheetAt(0);
    System.out.println( sheet.getRow(1).getCell(0));
}
    @Override
    public LmisResult<?> PosWhsOutPlanUpload(MultipartFile file) throws Exception{
        // TODO Auto-generated method stub
        String path = "";
        boolean error = false;
        List<PosWhsOutPlanLog> planLogList = null;
        List<PosWhsOutPlan> planList = null;
        List<String> datelist = new ArrayList();
        try{
            String uuid = posWhsOutPlanMapper.uuid();
//            path = "D:\\"+uuid+"\\";
            path = excelImportFileUploadPath+uuid+File.separator;
            upload(file.getInputStream(),path+file.getName()+".zip");
            ZipUtil.unzip(path+file.getName()+".zip",path);
            Workbook  workbook = null;

            File file2 = new File(path);
            File file3 = null;
            File[] listFiles = file2.listFiles();
            String fileName="";
            for (int i = 0; i < listFiles.length; i++){
                if(listFiles[i].getName().endsWith(".xlsx")||listFiles[i].getName().endsWith(".xls")){
                    file3 = listFiles[i];
                    fileName=file3.getName();
                }
            }
            if(file3==null){
                throw new Exception("文件格式不正确");
            }else{
                if(fileName.endsWith(".xlsx")){
                    workbook = new XSSFWorkbook(new FileInputStream(file3)); 
                    
                }else if(fileName.endsWith(".xls")){
                    workbook = new HSSFWorkbook(new FileInputStream(file3)); 
                }
            }
            
            Sheet sheet = null;
            sheet = workbook.getSheetAt(0);

            String dateToString = DateUtils.dateToString(DateUtils.getDate(),DateUtils.patternB);
            String taskNo = dateToString+new Random().nextInt(100);
            String userId = session.getAttribute("lmisUserId").toString();
            String org = session.getAttribute("lmisUserOrg").toString();
            planLogList = new LinkedList<PosWhsOutPlanLog>();
             planList = new LinkedList<PosWhsOutPlan>();
            StringBuilder sb = new StringBuilder(); 
                
            List<Map<String,Object>> list = posWhsOutPlanMapper.findWhsOutrate();
            if(sheet.getLastRowNum()>1001){
                throw new Exception("单次导入最大支持1000条数据");
            }
            if(sheet.getLastRowNum()<1){
                throw new Exception("数据为空");
            }
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {//从第二行数据开始读取
                PosWhsOutPlanLog planLog = new PosWhsOutPlanLog();
                PosWhsOutPlan plan = new PosWhsOutPlan();
                planLog.setCreateBy(userId);
                planLog.setUpdateBy(userId);
                planLog.setPwrOrg(org);
                planLog.setTaskNo(taskNo);
                planLog.setFileName(fileName);
                Row row = sheet.getRow(j);
                if (row != null) {
                    String formateDate = getCellVal(row.getCell(0));
                    String number = getCellVal(row.getCell(1));
                    
                    
                   
                    planLog.setScheduleDate(formateDate);
                    if("".equals(formateDate)||"".equals(number)){
                        planLog.setRemark("字段不能为空");
                        error=true;
                    }else if(DateUtils.stringToDate(formateDate)==null){
                       
                        planLog.setRemark("时间格式错误");
                        error=true;
                    }else if(sb.indexOf(formateDate)!=-1&&!"".equals(formateDate)){
                        planLog.setRemark("时间重复");
                        error=true;
                    }else{
                        sb.append(formateDate);   
                        List li = posWhsOutPlanMapper.selectPlanBatch(formateDate);//
                        if(li.size()>0){//记录数据库已存在的数据
                            datelist.add(formateDate);
                        }
                        planLog.setScheduleDate(formateDate);
                    }
                    
                    
                    
                    
                    
                    BigDecimal num=null;
                    try{
                        num = new BigDecimal(number);
                        if(num.intValue()<1){
                            planLog.setRemark("格式不正确，请输入大于0的正整数");
                            error=true;
                        }
                        planLog.setAmount(number);
                    }catch(Exception e){
                        planLog.setAmount(number);
                        planLog.setRemark("计划出库数格式错误");
                        error=true;
                    }
                    
                    if(!error){//校验如果没有错就存入plan表
                        
                        String whsCode = "";
                        int planOutSum=0;
                        for (int i = 0; i < list.size(); i++){
                            if(!error){
                            if(!StringUtil.isNotEmpty(list.get(i).get("outrate"))){
                                planLog.setRemark("未生成仓库比例");
                                error=true;
                                break;
                            }
                            if(!StringUtil.isNotEmpty(list.get(i).get("skuOutrateas"))){
                                planLog.setRemark("未生成鞋服配比例");
                                error=true;
                                break;
                            }
                            if(!whsCode.equals(list.get(i).get("code"))){
                                whsCode = list.get(i).get("code")+"";
                                plan = new PosWhsOutPlan();
                                //一个仓库第一条 
                                plan.setWhsCode(list.get(i).get("code")+"");
                                plan.setCreateBy(userId);
                                plan.setUpdateBy(userId);
                                plan.setPwrOrg(org);
                                plan.setScheduleDate(formateDate);
                                if(whsCode.equals(list.get(list.size()-1).get("code")+"")){
                                    plan.setPlannedOut(num.intValue()-planOutSum);
                                }else{
                                    plan.setPlannedOut(num.multiply(new BigDecimal(list.get(i).get("outrate")+"")).setScale( 0, BigDecimal.ROUND_DOWN ).intValue());
                                    planOutSum+=plan.getPlannedOut();
                                }
                                if("20".equals(list.get(i).get("sku_type"))){
                                    plan.setPlannedOutShoe(new BigDecimal(plan.getPlannedOut()).multiply(new BigDecimal(list.get(i).get("skuOutrateas")+"")).setScale( 0, BigDecimal.ROUND_DOWN ).intValue());
                                }
                                if("10".equals(list.get(i).get("sku_type"))){
                                    plan.setPlannedOutAddress(new BigDecimal(plan.getPlannedOut()).multiply(new BigDecimal(list.get(i).get("skuOutrateas")+"")).setScale( 0, BigDecimal.ROUND_DOWN ).intValue());
                                }
                                if("30".equals(list.get(i).get("sku_type"))){
                                    plan.setPlannedOutAccessory(new BigDecimal(plan.getPlannedOut()).multiply(new BigDecimal(list.get(i).get("skuOutrateas")+"")).setScale( 0, BigDecimal.ROUND_DOWN ).intValue());
                                }
                                plan.setRelatedTaskNo(taskNo);
                            }else{
                                if(i+1==list.size()||!list.get(i).get("code").equals(list.get(i+1).get("code"))){
                                    if(plan.getPlannedOut()==null){
                                        plan.setPlannedOut(0);
                                    }
                                    if(plan.getPlannedOutAccessory()==null){
                                        plan.setPlannedOutAccessory(0);
                                    }
                                    if(plan.getPlannedOutAddress()==null){
                                        plan.setPlannedOutAddress(0);
                                    }
                                    if(plan.getPlannedOutShoe()==null){
                                        plan.setPlannedOutShoe(0);
                                    }
                                    
                                    if("20".equals(list.get(i).get("sku_type"))){
                                        plan.setPlannedOutShoe(plan.getPlannedOut()-plan.getPlannedOutAccessory()-plan.getPlannedOutAddress());
                                    }
                                    if("10".equals(list.get(i).get("sku_type"))){
                                        plan.setPlannedOutAddress(plan.getPlannedOut()-plan.getPlannedOutAccessory()-plan.getPlannedOutShoe());
                                    }
                                    if("30".equals(list.get(i).get("sku_type"))){
                                        plan.setPlannedOutAccessory(plan.getPlannedOut()-plan.getPlannedOutAddress()-plan.getPlannedOutShoe());
                                    }
                                    if(plan.getPlannedOut()<0||plan.getPlannedOutAccessory()<0||plan.getPlannedOutAddress()<0||plan.getPlannedOutShoe()<0){
                                        planLog.setRemark("鞋服配比例异常");
                                        error=true;
                                    }
                                    planList.add(plan);
                                }
                                else if(whsCode.equals(list.get(i).get("code"))){
                                    if("20".equals(list.get(i).get("sku_type"))){
                                        plan.setPlannedOutShoe(new BigDecimal(plan.getPlannedOut()).multiply(new BigDecimal(list.get(i).get("skuOutrateas")+"")).setScale( 0, BigDecimal.ROUND_DOWN ).intValue());
                                    }
                                    if("10".equals(list.get(i).get("sku_type"))){
                                        plan.setPlannedOutAddress(new BigDecimal(plan.getPlannedOut()).multiply(new BigDecimal(list.get(i).get("skuOutrateas")+"")).setScale( 0, BigDecimal.ROUND_DOWN ).intValue());
                                    }
                                    if("30".equals(list.get(i).get("sku_type"))){
                                        plan.setPlannedOutAccessory(new BigDecimal(plan.getPlannedOut()).multiply(new BigDecimal(list.get(i).get("skuOutrateas")+"")).setScale( 0, BigDecimal.ROUND_DOWN ).intValue());
                                    } 
                                }
                            }
                        }
                        }
                       
                    }
                    planLogList.add(planLog);
                }
            }
            for (int i = 0; i < planLogList.size(); i=i+30){
                if(i+30>planLogList.size()){
                    posWhsOutPlanMapper.createBatchLog(planLogList.subList(i, planLogList.size()),error);
                }else{
                    posWhsOutPlanMapper.createBatchLog(planLogList.subList(i, i+30),error);
                }
            }
            if(!error){
                
                
                for (int i = 0; i < datelist.size(); i=i+30){
                    //批量删除重复数据
                    if(i+30>datelist.size()){
                        posWhsOutPlanMapper.deletePlanBatch(datelist.subList(i, datelist.size()));
                    }else{
                        posWhsOutPlanMapper.deletePlanBatch(datelist.subList(i, i+30));
                    }

                }
                for (int i = 0; i < planList.size(); i=i+30){
                    //计划表批量添加
                    if(i+30>planList.size()){
                        posWhsOutPlanMapper.createBatch((List<T>) planList.subList(i, planList.size()));
                    }else{
                        posWhsOutPlanMapper.createBatch((List<T>) planList.subList(i, i+30));
                    }
                    
                }
            }
            
        }finally{
            deleteDirectory(path);
        }
        LmisResult lr = new LmisResult<>();
        lr.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        if(error){
            lr.setData(planLogList.get(0).getTaskNo()); 
        }
        return lr;
    }
    
    private String getCellVal(Cell cell){
        if(cell==null){
            return "";
        }
        String value="";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                //如果为时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(cell)) {      
                   //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                   value=sdf.format(HSSFDateUtil.getJavaDate(cell.
                   getNumericCellValue())).toString();                                 
                     break;
                 } else {
                     value = new DecimalFormat("0").format(cell.getNumericCellValue());
                 }
                break;
            case HSSFCell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                value = cell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                value = cell.getCellFormula() + "";
                break;
            default:
                value = "";
                break;
       }
        return value;
    }

    public boolean deleteDirectory(String sPath) {  
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = true;  
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            //删除子文件  
            if (files[i].isFile()) {  
                flag = (files[i].delete());  
                if (!flag) break;  
            } //删除子目录  
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;  
        //删除当前目录  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    } 

    public static void upload(InputStream input, String destFilePath) throws Exception {

        BufferedInputStream bis = new BufferedInputStream(input);
        File file = new File(destFilePath);
        File parent = file.getParentFile();
        if (parent != null && (!parent.exists())) parent.mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = bis.read(buf, 0, 1024)) != -1) fos.write(buf, 0, len);
        bos.flush();
        bos.close();
        bis.close();
    }

    @Override
    public LmisResult<?> selectPosWhsOutPlanLog(PosWhsOutPlanLog posWhsOutPlanLog,LmisPageObject pageObject) throws Exception{
        // TODO Auto-generated method stub
        Page page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
        posWhsOutPlanMapper.retrieveLog(posWhsOutPlanLog);
        LmisResult lmisResult = new LmisResult();
        lmisResult.setMetaAndData(page.toPageInfo());
        lmisResult.setCode("S1001");
        return lmisResult;
    }

    @Override
    public LmisResult<?> deletePosWhsOutPlanLog(PosWhsOutPlanLog posWhsOutPlanLog) throws Exception{
        // TODO Auto-generated method stub
        return new LmisResult(LmisConstant.RESULT_CODE_SUCCESS, posWhsOutPlanMapper.logicalDeleteLog(posWhsOutPlanLog));
    }

    @Override
    public LmisResult<?> checkPosWhsList() throws Exception{
        // TODO Auto-generated method stub
        return new LmisResult(LmisConstant.RESULT_CODE_SUCCESS, posWhsOutPlanMapper.checkPosWhsList());
    }
}
