package com.bt.lmis.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.WhFeeRDQueryParam;
import com.bt.lmis.model.WarehouseFeeRD;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WarehouseFeeRDService;
import com.bt.utils.BaseConst;
import com.bt.utils.DateUtil;

@Controller
@RequestMapping("/control/warehouseFeeRawDataController")
public class WarehouseFeeRawDataController extends BaseController {

	private static final Logger logger = Logger.getLogger(WarehouseFeeRawDataController.class);
	
	@Resource(name="warehouseFeeRDServiceImpl")
	private WarehouseFeeRDService<WarehouseFeeRD> warehouseFeeRDServiceImpl;
	
	@RequestMapping("/list")
	public String getTestList(WhFeeRDQueryParam queryParam, ModelMap map, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<WarehouseFeeRD> pageView = new PageView<WarehouseFeeRD>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<WarehouseFeeRD> qr = warehouseFeeRDServiceImpl.findAll(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
		}catch(Exception e){
			logger.error(e);
		}
		return "/lmis/warehouseFeeRawData_list";
	}
	
	/*** 
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile 
     *  
     * @param file 
     * @return 
     */ 
	@RequestMapping("fileUpload")  
    public String fileUpload(@RequestParam("file") MultipartFile file, ModelMap map, HttpServletRequest req, HttpServletResponse res){
        // 判断文件是否为空  
        if (!file.isEmpty()){
            try {
                // 文件保存路径
                String filePath = req.getSession().getServletContext().getRealPath("/") + "upload/" + file.getOriginalFilename();  
                // 转存文件
                file.transferTo(new File(filePath));
            	Map<Integer, String> maps = readExcels(filePath);
    			List<WarehouseFeeRD> wList = new ArrayList<WarehouseFeeRD>();
    			for (int i = 1; i <= maps.size(); i++){
    				WarehouseFeeRD w = map2bean(maps.get(i));
    				if(null!=w)wList.add(w);
    			}
    			warehouseFeeRDServiceImpl.insertBatch(wList);
            } catch(Exception e) {  
                e.printStackTrace();  
            }
        }
        return "redirect:/control/warehouseFeeRawDataController/list.do";
    }  
	
    public boolean handleXLS(String url,String fileName) {  
        String filePath = url;  
        File uploadDest = new File(filePath);  
        String[] fileNames = uploadDest.list();  
        for (int i = 0; i < fileNames.length; i++) {
        	if(fileNames[i].equals(fileName)){
        		//存在重名
        		return true;
        	}
        } 
        return false;
    }
    
    public WarehouseFeeRD map2bean(String str){
    	if(null!=str && !str.equals("")){
    		WarehouseFeeRD  whfrd = new WarehouseFeeRD();
        	String[] map = str.split("    ");
        	if(map.length>0){
        		whfrd.setCreate_time(new Date());
        		whfrd.setCreate_user("system");
        		whfrd.setSettle_flag(0);//结算标识(0:未结算；1：已结算)
            	if(null!=map[0] && !map[0].equals(""))
            		whfrd.setWarehouse_type(Integer.valueOf(map[0]));
            	if(null!=map[1] && !map[1].equals(""))
            		whfrd.setStart_time(DateUtil.StrToDate(map[1]));
            	if(null!=map[2] && !map[2].equals(""))
            		whfrd.setStore_id(Integer.valueOf(map[2]));
            	if(null!=map[3] && !map[3].equals(""))
            		whfrd.setStore_name(map[3]);
            	if(null!=map[4] && !map[4].equals(""))
            		whfrd.setIs_closed(Integer.valueOf(map[4]));
            	if(null!=map[5] && !map[5].equals(""))
            		whfrd.setTrade(map[5]);
            	if(null!=map[6] && !map[6].equals(""))
            		whfrd.setWarehouse_code(map[6]);
            	if(null!=map[7] && !map[7].equals(""))
            		whfrd.setWarehouse_name(map[7]);
            	if(null!=map[8] && !map[8].equals(""))
            		whfrd.setSystem_warehouse(map[8]);
            	if(null!=map[9] && !map[9].equals(""))
            		whfrd.setWhcost_center(map[9]);
            	if(null!=map[10] && !map[10].equals(""))
            		whfrd.setCost_code(map[10]);
            	if(null!=map[11] && !map[11].equals(""))
            		whfrd.setCost_name(map[11]);
            	if(null!=map[12] && !map[12].equals(""))
            		whfrd.setArea_code(map[12]);
            	if(null!=map[13] && !map[13].equals(""))
            		whfrd.setArea_name(map[13]);
            	if(null!=map[14] && !map[14].equals(""))
            		whfrd.setItem_type(map[14]);
            	if(null!=map[15] && !map[15].equals(""))
            		whfrd.setStorage_type(Integer.valueOf(map[15]));
            	if(null!=map[16] && !map[16].equals(""))
            		whfrd.setStorage_acreage(new BigDecimal(map[16]));
            	if(null!=map[17] && !map[17].equals(""))
            		whfrd.setAcreage_unit(map[17]);
            	return whfrd;
        	}
    	}
    	return null;
    }
}
