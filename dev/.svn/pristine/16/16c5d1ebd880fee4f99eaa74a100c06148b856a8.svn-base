package com.bt.lmis.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.bt.lmis.dao.CustomPageMapper;
import com.bt.lmis.model.CustomerPageBean;
import com.bt.lmis.model.TableColumnBean;
import com.bt.lmis.service.CustomPageService;

public class CustomPageServiceImpl implements CustomPageService {

	@Autowired
	private CustomPageMapper mapper;
	
	@Override
	public ArrayList<CustomerPageBean> obtain_search_assembly(CustomerPageBean param) {
		// TODO Auto-generated method stub
		return mapper.obtain_assembly(param);
	}

	@Override
	public ArrayList<CustomerPageBean> obtain_display_assembly(CustomerPageBean param) {
		// TODO Auto-generated method stub
		return mapper.obtain_assembly(param);
	}

	@Override
	public ArrayList<TableColumnBean> obtain_table_assembly(TableColumnBean param) {
		// TODO Auto-generated method stub
		return mapper.obtain_customer_column(param);
	}

}
