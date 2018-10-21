package com.jumbo.pms.model.command.vo;

import java.util.List;

import com.jumbo.pms.model.command.ParcelInfoCommand;
import com.jumbo.pms.model.command.ShipmentCommand;

/**
 * 包裹出库
 */
public class ShipmentInTransitVo extends ShipmentCommand {

	private static final long serialVersionUID = 1L;
	public static final int ENTRANCE_SO = 1;
	public static final int ENTRANCE_RO = 2;

	public static final String OPTYPE_HAS_MAILNO = "001";
	public static final String OPTYPE_HASNOT_MAILNO = "002";

	
	
    /**
     * 包裹数量
     */
    private Integer parcelCount;
    
    private List<ParcelInfoCommand> parcelInfoCommands;
    
	public Integer getParcelCount() {
        return parcelCount;
    }

    public void setParcelCount(Integer parcelCount) {
        this.parcelCount = parcelCount;
    }

    public List<ParcelInfoCommand> getParcelInfoCommands() {
		return parcelInfoCommands;
	}

	public void setParcelInfoCommands(List<ParcelInfoCommand> parcelInfoCommands) {
		this.parcelInfoCommands = parcelInfoCommands;
	}
}
