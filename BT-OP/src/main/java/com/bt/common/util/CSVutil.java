package com.bt.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2017年7月26日 下午2:28:27  
*/
public class CSVutil {

	
	/**
     * 生成为CVS文件
     * 
     * @param exportData 源数据List
     * @param map csv文件的列表头map
     * @param outPutPath 文件路径
     * @param fileName 文件名称
     * @return
     */
	public static File createCSVFile(List<Map<String, Object>> exportData, LinkedHashMap map, 
            String fileName) {
		 SimpleDateFormat formater = new SimpleDateFormat("yyMMddHHmmss");
     	String format = formater.format(new Date());
		String outPutPath = "";
		if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
			outPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_Linux");
		}else{
			outPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname());
		}
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdir();
            }
            // 定义文件名格式并创建(fileName, ".csv", new File(outPutPath));
            
            csvFile = new File(outPutPath+fileName+ ".csv");
            System.out.println("csvFile：" + csvFile);
            // UTF-8使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"), 1024);
            System.out.println("csvFileOutputStream：" + csvFileOutputStream);
            // 写入文件头部
            csvFileOutputStream.write("\""+"vf_report_3_4"+"\"");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write(",");
            //csvFileOutputStream.newLine();
            csvFileOutputStream.write("\r\n");
            for (Iterator propertyIterator = map.entrySet().iterator(); 
                    propertyIterator.hasNext();) {
                java.util.Map.Entry propertyEntry = 
                        (java.util.Map.Entry) propertyIterator.next();
                csvFileOutputStream.write(
                        "" + (String) propertyEntry.getValue() != null ?
                        		"\""+(String) propertyEntry.getValue()+"\"" : "" + "");
               // if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                  
                //}
            }
            csvFileOutputStream.write("\r\n");
            //csvFileOutputStream.newLine();
            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
                Object row = (Object) iterator.next();
                for (Iterator propertyIterator = map.entrySet().iterator(); 
                        propertyIterator.hasNext();) {
                    java.util.Map.Entry propertyEntry =(java.util.Map.Entry) propertyIterator.next();
                    if(propertyEntry.getKey()=="producttype_name"){
                    	csvFileOutputStream.write("\""+format+"\"");
                    	//if (propertyIterator.hasNext()) {
                            csvFileOutputStream.write(",");
                       // }
                    }else{
                    	String property = BeanUtils.getProperty(row, (String) propertyEntry.getKey());
                    	if(property!=null&&property!=""){
                    		if(property!=null&&!property.equals("")){
	                    		property="\""+property+"\"";
	                    		csvFileOutputStream.write(property);
	                    		 csvFileOutputStream.write(",");
                    		}else{
                        		csvFileOutputStream.write(property);
                        		 csvFileOutputStream.write(",");
                        	}
                    	}
	                    /*if (propertyIterator.hasNext()) {
	                        csvFileOutputStream.write(",");
	                    }*/
	                }
                }
                if (iterator.hasNext()) {
                    //csvFileOutputStream.newLine();
                	  csvFileOutputStream.write("\r\n");
                }
            }
            Calendar calendar = Calendar.getInstance ();
            calendar.add (Calendar.SECOND, 1);
            String format1 = formater.format(calendar.getTime());
            //csvFileOutputStream.newLine();
            csvFileOutputStream.write("\r\n");
            csvFileOutputStream.write("\""+format1+"\"");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write("\""+"IPD Docs - "+format+"\"");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write("");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write("\""+"0.5"+"\"");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write("\""+format1+"\"");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write("\""+exportData.get(0).get("count").toString()+"\"");
            csvFileOutputStream.write(",");
            csvFileOutputStream.write("\r\n");
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }
	
	 public static void main(String[] args) {
	        List<Map<String, Object>> exportData = new ArrayList<Map<String, Object>>();
	        Map<String, Object> row1 = new LinkedHashMap<String, Object>();
	        row1.put("id", "");
	        row1.put("2", "12");
	        row1.put("3", "13");
	        row1.put("4", "14");
	        row1.put("5", "");
	        exportData.add(row1);
	        row1 = new LinkedHashMap<String, Object>();
	        row1.put("id", "21");
	        row1.put("2", "22");
	        row1.put("3", "23");
	        row1.put("4", "24");
	        exportData.add(row1);
	        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
	        map.put("id", "第一列");
	        map.put("2", "第二列");
	        map.put("3", "第三列");
	        map.put("4", "第四列");
	       // String path = "c:/export/";
	        String fileName = "vans_report_4000326048";
	        File file = createCSVFile(exportData, map, fileName);
	        String fileName2 = file.getName();
	        System.out.println("文件名称：" + CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+fileName2);
	    }
}
