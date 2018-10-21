package com.jumbo.pms.model.command.cond;

import com.jumbo.pms.model.command.ParcelResult;

/**
 * 
 * @author Double S
 *
 */
public class PgPackageCreateCond  extends ParcelResult {
    

	/**
     * 
     */
    private static final long serialVersionUID = 3573993376424356508L;
    /** 处理成功 */
    public static final int STATUS_SUCCESS = 1;
    /** 处理失败 */
    public static final int STATUS_ERROR = 0;
    
    private PgPackageCreateCommand pgPackageCreateCommand;

    public PgPackageCreateCommand getPgPackageCreateCommand() {
        return pgPackageCreateCommand;
    }

    public void setPgPackageCreateCommand(PgPackageCreateCommand pgPackageCreateCommand) {
        this.pgPackageCreateCommand = pgPackageCreateCommand;
    }
    
}
