package com.jumbo.wms.model.command.automaticEquipment;

import com.jumbo.wms.model.automaticEquipment.Zoon;

/**
 * @author lihui
 *
 * @createDate 2016年1月19日 下午7:21:46
 */
public class ZoonCommand extends Zoon {
    private static final long serialVersionUID = -1907560327541348478L;


    private Long ouId;

    private String ouName;

    private String statusStr;


    public Long getOuId() {
        return ouId;
    }


    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }


    public String getOuName() {
        return ouName;
    }


    public void setOuName(String ouName) {
        this.ouName = ouName;
    }


    public String getStatusStr() {
        return statusStr;
    }


    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }



}
