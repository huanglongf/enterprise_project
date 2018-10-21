package com.jumbo.manager.channel.packingPage;

import java.util.Map;

import com.jumbo.wms.model.jasperReport.PackingObj;
import com.jumbo.wms.model.jasperReport.PrintPackingPageParam;


public interface PackingPageInterface {

    String PRINT_TEMPLATE_BASE_PATH = "jasperprint/";
    
    /**
     * 获取后置主模板
     * @return
     */
    String getPostpositionalMainTemplate();
    /**
     * 获取后置子模板
     * @return
     */
    String getPostpositionalSubTemplate();
    
    /**
     * 获取主模板
     * 
     * @return
     */
    String getMainTemplate();

    /**
     * 获取子模板
     * 
     * @return
     */
    String getSubTemplate();

    /**
     * 设置自定义参数
     * 
     * @param map
     */
    void setUserDefined(Map<String, Object> map);

    /**
     * 明细行是否显示库位
     * 
     * @return
     */
    boolean isDetialShowLocation();
    
    /**
     * 明细行是否显示SN
     * 
     * @return
     */
    boolean isDetialShowSN();
    
    /**
     * 装箱清单取值逻辑是否特殊定制
     * 
     * @return
     */
    boolean isSpecialCustom();
    
    Map<Long, PackingObj> getSpecialCustomValue(PrintPackingPageParam param);

}
