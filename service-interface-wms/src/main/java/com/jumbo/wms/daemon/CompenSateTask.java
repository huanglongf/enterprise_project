package com.jumbo.wms.daemon;

public interface CompenSateTask {

    /**
     * 索赔单据状态是‘已审核’的, 并且预警天数为0,系统自动刷新索赔单据状态为‘索赔成功’
     * 
     */
    public void changeCompenSateState();
}
