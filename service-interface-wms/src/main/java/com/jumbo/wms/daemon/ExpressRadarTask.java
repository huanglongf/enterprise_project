package com.jumbo.wms.daemon;

public interface ExpressRadarTask {
    // 特殊店铺
	public void specialStore(String args);
	
	//普通店铺
    public void commonStore();

    /**
     * 更新快递的预警信息
     */
    public void updateExpressWarnningInfo();
}
