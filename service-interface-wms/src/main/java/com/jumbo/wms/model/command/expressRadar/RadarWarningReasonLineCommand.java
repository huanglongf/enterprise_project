package com.jumbo.wms.model.command.expressRadar;

import com.jumbo.wms.model.expressRadar.RadarWarningReasonLine;


/**
 * @author lihui
 * 
 *         2015年5月25日 下午4:44:25
 */
public class RadarWarningReasonLineCommand extends RadarWarningReasonLine {


    private static final long serialVersionUID = 7743702446599258363L;

    /**
     * 预警原因ID
     */
    private Long wrId;

    /**
     * 预警等级编码
     */
    private String lvCode;

    /**
     * 预警等级ID
     */
    private Long lvId;



    public Long getWrId() {
        return wrId;
    }

    public void setWrId(Long wrId) {
        this.wrId = wrId;
    }

    public Long getLvId() {
        return lvId;
    }

    public void setLvId(Long lvId) {
        this.lvId = lvId;
    }

    public String getLvCode() {
        return lvCode;
    }

    public void setLvCode(String lvCode) {
        this.lvCode = lvCode;
    }


}
