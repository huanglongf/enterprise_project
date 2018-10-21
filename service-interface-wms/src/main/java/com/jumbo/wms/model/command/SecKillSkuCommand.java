package com.jumbo.wms.model.command;

import com.jumbo.wms.model.warehouse.SecKillSku;
/**
 * 
 * @author jinlong.ke
 *
 */
public class SecKillSkuCommand extends SecKillSku {

    /**
     * 
     */
    private static final long serialVersionUID = 2589006543325290959L;
    /**
     * 秒杀明细
     */
    private String skusString;
    /**
     * 秒杀汇总，配货时显示
     */
    private String secKillString;
    /**
     * 秒杀数量
     */
    private Integer kind;

    public String getSkusString() {
        return skusString;
    }

    public void setSkusString(String skusString) {
        this.skusString = skusString;
    }

    public Integer getKind() {
        kind = Integer.parseInt(this.getSkus().split(":")[0]);
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getSecKillString() {
        return secKillString;
    }

    public void setSecKillString(String secKillString) {
        this.secKillString = secKillString;
    }
}
