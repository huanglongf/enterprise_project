package com.jumbo.wms.model.command.automaticEquipment;

import com.jumbo.wms.model.automaticEquipment.WhPickingBatch;

/**
 * @author lihui
 *
 * @createDate 2016年1月19日 下午7:21:46
 */
public class WhPickingBatchCommand extends WhPickingBatch {

	private static final long serialVersionUID = 3543711598672198181L;

	private String pickingListCode;

    private String whZoneCode;

    private Long pickingListId;

    public String getPickingListCode() {
        return pickingListCode;
    }

    public void setPickingListCode(String pickingListCode) {
        this.pickingListCode = pickingListCode;
    }

    public String getWhZoneCode() {
        return whZoneCode;
    }

    public void setWhZoneCode(String whZoneCode) {
        this.whZoneCode = whZoneCode;
    }

    public Long getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(Long pickingListId) {
        this.pickingListId = pickingListId;
    }

}
