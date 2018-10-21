package com.bt.lmis.service;

import java.util.ArrayList;

import com.bt.lmis.model.CustomerPageBean;
import com.bt.lmis.model.TableColumnBean;

public interface CustomPageService {
  /**
   * 1.获取查询组件	
   */
	public ArrayList<CustomerPageBean> obtain_search_assembly( CustomerPageBean param);
	/**
	 * 2.获取显示列组件
	 */
	public ArrayList<CustomerPageBean> obtain_display_assembly( CustomerPageBean param);
	/**
	 * 3.获取表的属性集合
	 */
	public ArrayList<TableColumnBean> obtain_table_assembly( TableColumnBean param);
}
