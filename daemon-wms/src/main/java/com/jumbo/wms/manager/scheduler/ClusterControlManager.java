package com.jumbo.wms.manager.scheduler;

import java.util.List;

/**
 * 集群控制
 * 这里会控制当前集群中只有一个结点可用
 * @author Justin Hu
 *
 */
public interface ClusterControlManager {

	/**
	 * 状态发生改变
	 * @param parentPath
	 * @param currentChilds
	 */
	public void changeData(String parentPath, List<String> currentChilds);
	

	
	/**
	 * 初始连接
	 */
	public void init();
}
