package com.bt.orderPlatform.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.ViewAreaMapper;
import com.bt.orderPlatform.model.ViewArea;
import com.bt.orderPlatform.service.ViewAreaService;
@Service
public class ViewAreaServiceImpl<T>  implements ViewAreaService<T> {

	@Autowired
    private ViewAreaMapper<T> mapper;

	public ViewAreaMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public ViewArea selectByViewAreaAddress(String view_area_address) {
		// TODO Auto-generated method stub
		return mapper.selectByViewAreaAddress(view_area_address);
	}

	@Override
	public List<ViewArea> selectByViewArea(ViewArea viewArea) {
		// TODO Auto-generated method stub
		return mapper.selectByViewArea(viewArea);
	}

	
    @Override
    public List<ViewArea> selectByProcessedViewArea(ViewArea viewArea){
        // TODO Auto-generated method stub
        return mapper.selectByProcessedViewArea(viewArea);
    }

		
	
}
