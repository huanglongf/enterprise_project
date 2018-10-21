package com.jumbo.web.action.expressRadar;

import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.expressRadar.RadarAreaManager;
import com.jumbo.wms.model.expressRadar.SysRadarArea;

public class RadarAreaAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -1201876445648776871L;
    
    @Autowired
    private RadarAreaManager radarAreaManager;
    private String province;
    private String city;
    private List<Long> areaIds;


    public String initRadarAreaPage(){
        return SUCCESS;
    }
    
    public String findAreaList() {
        setTableConfig();
        if("".equals(province)){
            province = null;
        }
        if("".equals(city)){
            city = null;
        }
        Pagination<SysRadarArea> sysRadarAreaList= radarAreaManager.findArea(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), province, city);
        request.put(JSON, toJson(sysRadarAreaList));
        return JSON;
    }
    
    public String addRadarArea(){
        String flag = null;
        JSONObject result = new JSONObject();
        try{
            flag = radarAreaManager.addRadarArea(province, city);
            if("SUCCESS".equals(flag)){
                result.put("flag", "success");
                request.put(JSON, result); 
            }
            else if("EXIST".equals(flag)){
                result.put("flag", "exist");
                request.put(JSON, result);
            }else{
                result.put("flag", "error");
                request.put(JSON, result);
            }
        } catch(Exception e){
        }
        return JSON;
    }
    
    public String delRadarArea(){
//        radarAreaManager.removeArea(areaIds);
        String flag = null;
        JSONObject result = new JSONObject();
        try{
            flag = radarAreaManager.removeArea(areaIds);
            if("SUCCESS".equals(flag)){
                result.put("flag", "success");
                request.put(JSON, result); 
            }
            else{
                result.put("flag", "exist");
                request.put(JSON, result);
            }
        } catch(Exception e){
        }
        return JSON;
    }
    
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Long> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Long> areaIds) {
        this.areaIds = areaIds;
    }
    
   
    
}

