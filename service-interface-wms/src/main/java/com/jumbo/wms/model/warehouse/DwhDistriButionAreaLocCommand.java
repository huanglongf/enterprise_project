/**
 * 
 */
package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

/**
 * @author lijinggong+2018年8月6日
 *
 * 
 */

public class DwhDistriButionAreaLocCommand implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 5112749629621192064L;
    //库位
    private String code;
    //库区
    private String codeName;
    //分配区域编码
    private String distriButionCode;
    
    //分配区域名称
    private String distriButionName;
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCodeName() {
        return codeName;
    }
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    public String getDistriButionCode() {
        return distriButionCode;
    }
    public void setDistriButionCode(String distriButionCode) {
        this.distriButionCode = distriButionCode;
    }
    public String getDistriButionName() {
        return distriButionName;
    }
    public void setDistriButionName(String distriButionName) {
        this.distriButionName = distriButionName;
    }
    
    
}
