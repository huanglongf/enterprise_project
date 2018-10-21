package com.jumbo.wms.model.pda;




/**
 * @author hui.li
 *
 */
public class PdaStaShelvesPlanCommand extends PdaStaShelvesPlan {

 
    /**
     * 
     */
    private static final long serialVersionUID = -1857163392281638068L;

    private Long locId;// 库位

    private Integer occupy;

    private Integer sort;

    private Integer lv;

    private String locCode;

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Integer getOccupy() {
        return occupy;
    }

    public void setOccupy(Integer occupy) {
        this.occupy = occupy;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }


   }
