package com.bt.lmis.test;

/** 
* @ClassName: Watched 
* @Description: TODO(抽象主题角色，watched：被观察) 
* @author Yuriy.Jiang
* @date 2016年8月17日 下午2:56:34 
*  
*/
public interface Watched {
	public void addWatcher(Watcher watcher);

    public void removeWatcher(Watcher watcher);

    public void notifyWatchers(String str);
}
