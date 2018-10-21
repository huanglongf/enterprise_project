package com.bt.interf.sf;

import org.apache.poi.ss.formula.functions.T;

import com.bt.common.util.CommonUtil;
import com.bt.orderPlatform.model.sfIntraCity.IntraCityCancelorder;
import com.bt.orderPlatform.model.sfIntraCity.IntraCityCommonElement;
import com.bt.orderPlatform.model.sfIntraCity.IntraCityCreateorder;
import com.bt.orderPlatform.model.sfIntraCity.IntraCityListorderfeed;
import com.bt.orderPlatform.model.sfIntraCity.IntraCityResultMsg;
import com.bt.orderPlatform.model.sfIntraCity.ResultListorderfeed;


public interface SfIntraCityService{

    /**serverHost**/
    public final String TC_SERVER_HOST = CommonUtil.getAllMessage("config", "sf_tc_server_host");
    
    /**appId**/
    public final Integer TC_APP_ID = Integer.parseInt((CommonUtil.getAllMessage("config", "sf_tc_dev_id")));

    /**app秘钥**/
    public final String TC_APP_KEY = CommonUtil.getAllMessage("config", "sf_tc_dev_key");

    /**对接文档版本号**/
    public final Integer TC_SERVER_VERSION = Integer.parseInt(CommonUtil.getAllMessage("config", "sf_tc_version"));
    
    /**订单来源**/
    public final String TC_ORDER_SOURCE = "宝尊电商";
    


    
    /***
     * 
     * <b>方法名：</b>：Createorder<br>
     * <b>功能说明：</b>：创建同城订单<br>
     * @author <font color='blue'>chenkun</font> 
     * @date  2018年5月2日 下午3:07:41
     * @param intraCityCreateorder
     * @return
     */
    public IntraCityResultMsg<T> Createorder(IntraCityCreateorder intraCityCreateorder);
    
    /***
     * 
     * <b>方法名：</b>：Cancelorder<br>
     * <b>功能说明：</b>：取消同城订单<br>
     * @author <font color='blue'>chenkun</font> 
     * @date  2018年5月3日 下午1:59:34
     * @param intraCityCreateorder
     * @return
     */
    public IntraCityResultMsg<T> Cancelorder(IntraCityCancelorder intraCityCancelorder);
    
    
    /***
     * 
     * <b>方法名：</b>：Listorderfeed<br>
     * <b>功能说明：</b>：订单状态查询<br>
     * @author <font color='blue'>chenkun</font> 
     * @date  2018年5月3日 下午3:31:48
     * @param intraCityCancelorder
     * @return
     */
    public IntraCityResultMsg<ResultListorderfeed> Listorderfeed(IntraCityListorderfeed intraCityListorderfeed);
    
    /***
     * 
     * <b>方法名：</b>：SfIntraCityRequest<br>
     * <b>功能说明：</b>：底层调用的通讯方法<br>
     * @author <font color='blue'>chenkun</font> 
     * @date  2018年5月3日 下午2:09:18
     * @param data
     * @param url
     * @return
     */
    public IntraCityResultMsg<T> SfIntraCityRequest(IntraCityCommonElement data,String url);
    
    

}
