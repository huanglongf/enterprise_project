package com.bt.orderPlatform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.FtpFileNameMapper;
import com.bt.orderPlatform.model.FtpFileName;
import com.bt.orderPlatform.service.FtpFileNameService;
@Service
public class FtpFileNameServiceImpl<T>  implements FtpFileNameService<T> {

	@Autowired
    private FtpFileNameMapper<T> mapper;

	public FtpFileNameMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public List<FtpFileName> selectByfile_Name(FtpFileName f) {
		// TODO Auto-generated method stub
		return mapper.selectByfile_Name(f);
	}

	@Override
	public void insert(FtpFileName f) {
		// TODO Auto-generated method stub
		mapper.insert(f);
	}

    @Override
    public List<Map> checkSign(){
        // TODO Auto-generated method stub
        return mapper.checkSign();
    }

    @Override
    public void deleteSign(){
        // TODO Auto-generated method stub
        mapper.deleteSign();
    }

    @Override
    public void insertSign(){
        // TODO Auto-generated method stub
        mapper.insertSign();
    }

	
}
