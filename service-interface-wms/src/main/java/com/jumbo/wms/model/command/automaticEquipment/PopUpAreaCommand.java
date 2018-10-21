package com.jumbo.wms.model.command.automaticEquipment;

import com.jumbo.wms.model.automaticEquipment.PopUpArea;

/**
 * @author lihui
 *
 * @createDate 2016年1月19日 下午7:21:46
 */
public class PopUpAreaCommand extends PopUpArea {



    /**
     * 
     */
    private static final long serialVersionUID = 2789930393830329943L;


    private String statusStr;




    public String getStatusStr() {
        return statusStr;
    }


    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }



}
