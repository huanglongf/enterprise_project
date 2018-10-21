package com.jumbo.wms.model.warehouse;


/**
 * 仓库全量库存同步表
 * 
 * @author xiaolong.fei
 * 
 */
public class WholeInventorySynchroCommand extends WholeInventorySynchro {

    /**
     * 
     */
    private static final long serialVersionUID = 4013668694626106920L;
	
    /**
     * 总行数
     */
	private Long lineCount; 
	
	/**
	 * 商品总数
	 */
	private Long qtyCount;

    public Long getLineCount() {
        return lineCount;
    }

    public void setLineCount(Long lineCount) {
        this.lineCount = lineCount;
    }

    public Long getQtyCount() {
        return qtyCount;
    }

    public void setQtyCount(Long qtyCount) {
        this.qtyCount = qtyCount;
    }
	
}
