package com.bt.orderPlatform.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.common.util.CommonUtil;
import com.bt.common.util.EPlatform;
import com.bt.common.util.FileUtil;
import com.bt.common.util.OSinfo;
import com.bt.orderPlatform.controller.form.SFExpressPrint;
import com.bt.orderPlatform.controller.form.SFZMDExpressPrint;
import com.bt.orderPlatform.controller.form.WaybillMasterQueryParam;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.model.WaybillZd;
import com.bt.orderPlatform.service.WaybilDetailService;
import com.bt.orderPlatform.service.WaybillMasterService;
import com.bt.orderPlatform.service.WaybillZdService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.json.JSONObject;

/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2017年11月23日 下午1:39:02  
*/
@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/control/orderPlatform/SFPrintController")
public class SFPrintController{
    
   // private static final Logger logger = Logger.getLogger(SFPrintController.class);
	
	@Resource(name = "waybilDetailServiceImpl")
	private WaybilDetailService<WaybilDetail> waybilDetailService;
	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterServiceImpl;
    @Resource(name = "waybillZdServiceImpl")
    private WaybillZdService<WaybillZd> waybillZdService;
	
	@ResponseBody
	@RequestMapping(value = "/print_rebook",produces = "application/json;charset=UTF-8")
	public String printPDF(WaybillMasterQueryParam queryParam,Model model,HttpServletRequest request,HttpServletResponse response) {
		Map<String,String> result=new HashMap<String,String>();
		try {
			String outPutPath = "";
			String outireoprtPutPath = "";
			if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
				outPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_Linux");
				outireoprtPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_Linux_REPORT");
			}else{
				outPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname());
				outireoprtPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname());
			}
			File file1 = new File(outPutPath);
			if (!file1.exists()) {
				file1.mkdir();
			}
			String file_name=String.valueOf(System.currentTimeMillis()).concat("_运单.pdf");
			File file=new File(outPutPath+file_name);
			
			String id = request.getParameter("ids");
			String[] split = id.split(";");
			List<SFExpressPrint> SFlist = new LinkedList<SFExpressPrint>();
			List<SFExpressPrint> ZTOlist = new LinkedList<SFExpressPrint>();
			List<JasperPrint> expressPrintTotalList = new LinkedList<JasperPrint>();
			WaybillMaster waybillmaster = null;
			SFExpressPrint sfExpressPrint =null;
			
			for (String string : split) {
				List<WaybillMaster> selectIdByBatIdAndStatus = waybillMasterServiceImpl.selectIdByBatIdAndStatus(string);
				for (WaybillMaster waybillMaster2 : selectIdByBatIdAndStatus) {
					waybillmaster = waybillMasterServiceImpl.selectById(waybillMaster2.getId());
					sfExpressPrint = waybillMasterServiceImpl.selectSFPrint(waybillMaster2.getId());
					if (sfExpressPrint!=null) {
					    
					    /**标记订单被打印**/
					    setWaybillmasterHasBeenPrinted(waybillmaster);
					    
						if(sfExpressPrint.getExpress_code().equals("SF")||sfExpressPrint.getExpress_code().equals("SFSM")){
						    if(waybillmaster.getTotal_qty()>1){
						        /**打印子母单**/
						       // printSFZMD(waybillmaster, sfExpressPrint, outireoprtPutPath,file);
						        addSFZMDExpressPrint(waybillmaster, sfExpressPrint, outireoprtPutPath, expressPrintTotalList);
						        continue;
						    }
							SFlist.add(sfExpressPrint);
						}else if(sfExpressPrint.getExpress_code().equals("ZTO")){
							ZTOlist.add(sfExpressPrint);
						}
						
					}
				}
			}
		
			generalPrint(SFlist, ZTOlist, outireoprtPutPath, file, expressPrintTotalList);
			
	        /**返回打印成功信息**/
            resultSucc(result, response, file_name);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result","2");
			result.put("out_result_reason","成功");
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/printBaozunWaybilMaster",produces = "application/json;charset=UTF-8")
	public String printBaozunWaybilMaster(WaybillMasterQueryParam queryParam,Model model,HttpServletRequest request,HttpServletResponse response) {
		Map<String,String> result=new HashMap<String,String>();
		try {  
			String outPutPath = "";
			String outireoprtPutPath = "";
			if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
				outPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_Linux");
				outireoprtPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_Linux_REPORT");
			}else{
				outPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname());
				outireoprtPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname());
			}
			File file1 = new File(outPutPath);
			if (!file1.exists()) {
				file1.mkdir();
			}
			String file_name=String.valueOf(System.currentTimeMillis()).concat("_运单.pdf");
			File file=new File(outPutPath+file_name);
			String id = request.getParameter("ids");
			String[] split = id.split(";");
			List<SFExpressPrint> SFlist = new ArrayList<SFExpressPrint>();
			List<SFExpressPrint> ZTOlist = new ArrayList<SFExpressPrint>();
			List<JasperPrint> expressPrintTotalList = new LinkedList<JasperPrint>();
			WaybillMaster waybillmaster = null;
			SFExpressPrint sfExpressPrint =null;
			for (String string : split) {
				waybillmaster = waybillMasterServiceImpl.selectById(string);
				sfExpressPrint = waybillMasterServiceImpl.selectSFPrint(string);
                if (sfExpressPrint!=null) {
                    
                    /**标记订单被打印**/
                    setWaybillmasterHasBeenPrinted(waybillmaster);
                    
                    if(sfExpressPrint.getExpress_code().equals("SF")||sfExpressPrint.getExpress_code().equals("SFSM")){
                        if(waybillmaster.getTotal_qty()>1){
                            /**打印子母单**/
                            addSFZMDExpressPrint(waybillmaster, sfExpressPrint, outireoprtPutPath, expressPrintTotalList);
                            continue;
                        }
                        SFlist.add(sfExpressPrint);
                    }else if(sfExpressPrint.getExpress_code().equals("ZTO")){
                        ZTOlist.add(sfExpressPrint);
                    }
                }
            }
           
            generalPrint(SFlist, ZTOlist, outireoprtPutPath, file, expressPrintTotalList);
            
            /**返回打印成功信息**/
            resultSucc(result, response, file_name);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result","2");
			result.put("out_result_reason","失败");
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/print_rebook1",produces = "application/json;charset=UTF-8")
	public String printPDF1(WaybillMasterQueryParam queryParam,Model model,HttpServletRequest request,HttpServletResponse response) {
		Map<String,String> result=new HashMap<String,String>();
		try {
			String outPutPath = "";
			String outireoprtPutPath = "";
			if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
				outPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_Linux_REPORT");
				outireoprtPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_Linux_REPORT_FILE");
			}else{
				outPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname());
				outireoprtPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname());
			}
			 File file1 = new File(outPutPath);
            if (!file1.exists()) {
                file1.mkdir();
            }
            String file_name=String.valueOf(System.currentTimeMillis()).concat("_运单.pdf");
            File file=new File(outPutPath+file_name);
			String id = request.getParameter("ids");
			WaybillMaster waybillmaster = waybillMasterServiceImpl.selectById(id);
			SFExpressPrint sfExpressPrint =waybillMasterServiceImpl.selectSFPrint(id);
			if(sfExpressPrint!=null){
			    
		        /**标记订单被打印**/
                setWaybillmasterHasBeenPrinted(waybillmaster);
			    
			    if(waybillmaster.getTotal_qty()>1){
			        /**打印子母单**/
	                printSFZMD(waybillmaster, sfExpressPrint, outireoprtPutPath,file);
	             }else{
                    List<SFExpressPrint> SFlist = new ArrayList<SFExpressPrint>();
                    List<SFExpressPrint> ZTOlist = new ArrayList<SFExpressPrint>();
                 
                    SFlist.add(sfExpressPrint);
                    
                    List<JasperPrint> expressPrintTotalList = new LinkedList<JasperPrint>();
                    generalPrint(SFlist, ZTOlist, outireoprtPutPath, file, expressPrintTotalList);
	             }
			}
		     
            /**返回打印成功信息**/
            resultSucc(result, response, file_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.fromObject(result).toString();
	}

    @ResponseBody
	@RequestMapping(value = "/print_rebook2",produces = "application/json;charset=UTF-8")
	public String printPDF2(WaybillMasterQueryParam queryParam,Model model,HttpServletRequest request,HttpServletResponse response) {
		Map<String,String> result=new HashMap<String,String>();
		try {
			String outPutPath = "";
			String outireoprtPutPath = "";
			if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
				outPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_Linux");
				outireoprtPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_Linux_REPORT");
			}else{
				outPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname());
				outireoprtPutPath = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname());
			}
			File file1 = new File(outPutPath);
			if (!file1.exists()) {
				file1.mkdir();
			}
			String file_name=String.valueOf(System.currentTimeMillis()).concat("_运单.pdf");
			File file=new File(outPutPath+file_name);
			String id = request.getParameter("ids");
			String[] split = id.split(";");
			List<SFExpressPrint> SFlist = new LinkedList<SFExpressPrint>();
			List<SFExpressPrint> ZTOlist = new LinkedList<SFExpressPrint>();
	        List<JasperPrint> expressPrintTotalList = new LinkedList<JasperPrint>();
			WaybillMaster waybillmaster = null;
			SFExpressPrint sfExpressPrint =null;
		    for (String string : split) {
                waybillmaster = waybillMasterServiceImpl.selectById(string);
                sfExpressPrint = waybillMasterServiceImpl.selectSFPrint(string);
                if (sfExpressPrint!=null) {
                    
                    /**标记订单被打印**/
                    setWaybillmasterHasBeenPrinted(waybillmaster);
                    
                    if(sfExpressPrint.getExpress_code().equals("SF")||sfExpressPrint.getExpress_code().equals("SFSM")){
                        if(waybillmaster.getTotal_qty()>1){
                            /**打印子母单**/
                            printSFZMD(waybillmaster, sfExpressPrint, outireoprtPutPath, file);
                            continue;
                        }
                        SFlist.add(sfExpressPrint);
                    }else if(sfExpressPrint.getExpress_code().equals("ZTO")){
                        ZTOlist.add(sfExpressPrint);
                    }
                }
            }
		    
            generalPrint(SFlist, ZTOlist, outireoprtPutPath, file, expressPrintTotalList);
            
			
            /**返回打印成功信息**/
            resultSucc(result, response, file_name);
		
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result","2");
			result.put("out_result_reason","成功");
		}
		return JSONObject.fromObject(result).toString();
	}
	
	
	public static void main(String[] args) {
		String[] t={"1","2","3"};
		System.out.println(t[0]);
	}
	
	
	

	/***
	 * 
	 * <b>方法名：</b>：addSFZMDExpressPrint<br>
	 * <b>功能说明：</b>：添加子母单打印实例<br>
	 * @author <font color='blue'>chenkun</font> 
	 * @date  2018年4月24日 下午6:20:27
	 * @param waybillmaster
	 * @param sfExpressPrint
	 * @param outireoprtPutPath
	 * @param listPrint
	 * @throws JRException
	 */
    private void addSFZMDExpressPrint(WaybillMaster waybillmaster,SFExpressPrint sfExpressPrint,String outireoprtPutPath, List<JasperPrint> listPrint ) throws JRException {
        List<WaybillZd> zds = waybillZdService.selectByOrderId(waybillmaster.getOrder_id());
        if (zds==null||zds.isEmpty()){
            throw new RuntimeException("订单orderid:"+waybillmaster.getOrder_id()+"子单查询错误");
        }
        SFZMDExpressPrint sfMain = new SFZMDExpressPrint(); 
        BeanUtils.copyProperties(sfExpressPrint, sfMain);
        
        sfMain.setTotal(waybillmaster.getTotal_qty()+"");
        sfMain.setOrdinal("1");
        sfMain.setMainNo(sfMain.getTrackingNo());
        
        List<SFZMDExpressPrint> sfzmdlist = new LinkedList<SFZMDExpressPrint>();
        sfzmdlist.add(sfMain);
        
        for (WaybillZd zd : zds){
            SFZMDExpressPrint sfzdPrint = new SFZMDExpressPrint();
            BeanUtils.copyProperties(sfMain, sfzdPrint);
            sfzdPrint.setOrdinal(zd.getOrdinal()+"");
            sfzdPrint.setTrackingNo(zd.getMailZdno());
            sfzmdlist.add(sfzdPrint);
        }
    
        JRDataSource jrDataSource_common = new JRBeanCollectionDataSource(sfzmdlist);
        String jasperSource_common=outireoprtPutPath+"/SFNKD_ZDS.jasper";
       // String jasperSource_common=outireoprtPutPath+"/SFNKD_C.jasper";
        Map<String, Object> param_common=new HashMap<String, Object>();
        JasperReport report_common = (JasperReport) JRLoader.loadObject(new File(jasperSource_common));
        JasperPrint print_common = JasperFillManager.fillReport(report_common,param_common,jrDataSource_common);
        listPrint.add(print_common);
    
    }
    /***
     * 
     * <b>方法名：</b>：printSFZMD<br>
     * <b>功能说明：</b>：打印SF字母单<br>
     * @author <font color='blue'>chenkun</font> 
     * @date  2018年3月26日 上午9:35:01
     * @param waybillmaster
     * @param sfExpressPrint
     * @param outireoprtPutPath
     * @param file
     * @throws JRException
     * @throws IOException
     */
    private void printSFZMD(WaybillMaster waybillmaster,SFExpressPrint sfExpressPrint,String outireoprtPutPath ,File file) throws JRException, IOException{
	    List<WaybillZd> zds = waybillZdService.selectByOrderId(waybillmaster.getOrder_id());
	    if (zds==null||zds.isEmpty()){
            throw new RuntimeException("订单orderid:"+waybillmaster.getOrder_id()+"子单查询错误");
        }
	    SFZMDExpressPrint sfMain = new SFZMDExpressPrint(); 
	    BeanUtils.copyProperties(sfExpressPrint, sfMain);
	    
	    sfMain.setTotal(waybillmaster.getTotal_qty()+"");
	    sfMain.setOrdinal("1");
	    sfMain.setMainNo(sfMain.getTrackingNo());
	    
	    List<SFZMDExpressPrint> sfzmdlist = new LinkedList<SFZMDExpressPrint>();
	    sfzmdlist.add(sfMain);
	    
	    for (WaybillZd zd : zds){
	        SFZMDExpressPrint sfzdPrint = new SFZMDExpressPrint();
	        BeanUtils.copyProperties(sfMain, sfzdPrint);
	        sfzdPrint.setOrdinal(zd.getOrdinal()+"");
	        sfzdPrint.setTrackingNo(zd.getMailZdno());
	        sfzmdlist.add(sfzdPrint);
        }
	    
        List<JasperPrint> listPrint=new LinkedList<JasperPrint>();
    
        JRDataSource jrDataSource_common = new JRBeanCollectionDataSource(sfzmdlist);
        String jasperSource_common=outireoprtPutPath+"/SFNKD_ZDS.jasper";
       // String jasperSource_common=outireoprtPutPath+"/SFNKD_C.jasper";
        Map<String, Object> param_common=new HashMap<String, Object>();
        JasperReport report_common = (JasperReport) JRLoader.loadObject(new File(jasperSource_common));
        JasperPrint print_common = JasperFillManager.fillReport(report_common,param_common,jrDataSource_common);
        listPrint.add(print_common);
    
        JRPdfExporter exporter = new JRPdfExporter();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, listPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.exportReport();
        byte[] bytes = baos.toByteArray();
        FileUtil.writeByteArrayToFile(file,bytes);

    }

	/***
	 * 
	 * <b>方法名：</b>：generalPrint<br>
	 * <b>功能说明：</b>：打印方法<br>
	 * @author <font color='blue'>chenkun</font> 
	 * @date  2018年3月26日 上午9:33:40
	 * @param SFlist
	 * @param ZTOlist
	 * @param outireoprtPutPath
	 * @param file
	 * @throws JRException
	 * @throws IOException
	 */
    private void generalPrint(List<SFExpressPrint> SFlist,List<SFExpressPrint> ZTOlist,String outireoprtPutPath ,File file,List<JasperPrint> listPrint) throws JRException, IOException{
        if(SFlist.size()>0){
            JRDataSource jrDataSource_common = new JRBeanCollectionDataSource(SFlist);
            String jasperSource_common=outireoprtPutPath+"/SFNKD_C.jasper";
            Map<String, Object> param_common=new HashMap<String, Object>();
            JasperReport report_common = (JasperReport) JRLoader.loadObject(new File(jasperSource_common));
            JasperPrint print_common = JasperFillManager.fillReport(report_common,param_common,jrDataSource_common);
            listPrint.add(print_common);
        }
        if(ZTOlist.size()>0){
            JRDataSource jrDataSource_common = new JRBeanCollectionDataSource(ZTOlist);
            String jasperSource_common=outireoprtPutPath+"/ZTONKD_C.jasper";
            Map<String, Object> param_common=new HashMap<String, Object>();
            JasperReport report_common = (JasperReport) JRLoader.loadObject(new File(jasperSource_common));
            JasperPrint print_common = JasperFillManager.fillReport(report_common,param_common,jrDataSource_common);
            listPrint.add(print_common);
        }
         
        if(listPrint.size()>0){
            JRPdfExporter exporter = new JRPdfExporter();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, listPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            FileUtil.writeByteArrayToFile(file,bytes);
        }    
	}
	
	/**
	 * 
	 * <b>方法名：</b>：setWaybillmasterHasBeenPrinted<br>
	 * <b>功能说明：</b>：标记订单被打印了<br>
	 * @author <font color='blue'>chenkun</font> 
	 * @date  2018年3月26日 上午9:46:30
	 * @param waybillmaster
	 */
	private void setWaybillmasterHasBeenPrinted(WaybillMaster waybillmaster){
	    waybillmaster.setPrint_code("1");
        Date date = new Date();
        waybillmaster.setPrint_time(date);
        waybillMasterServiceImpl.setPrint_code(waybillmaster);
	}
	
	/***
	 * 
	 * <b>方法名：</b>：resultSucc<br>
	 * <b>功能说明：</b>：打印成功返回信息<br>
	 * @author <font color='blue'>chenkun</font> 
	 * @date  2018年3月26日 上午9:58:15
	 * @param result
	 * @param response
	 */
	private void resultSucc(Map<String,String> result,HttpServletResponse response,String file_name){
	    response.setContentType("application/octet-stream");              
        result.put("path", CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+file_name);
        result.put("out_result","1");
        result.put("out_result_reason","成功");
	}
}
