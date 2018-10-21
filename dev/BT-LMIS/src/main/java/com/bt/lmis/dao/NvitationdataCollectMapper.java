package com.bt.lmis.dao;
import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.NvitationdataCollect;

/**
* @ClassName: NvitationdataCollectMapper
* @Description: TODO(NvitationdataCollectMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface NvitationdataCollectMapper<T> extends BaseMapper<T> {

	public List<NvitationdataCollect> findByCBIDACYCLE(NvitationdataCollect data);

	public void delete_by_cond(NvitationdataCollect entityB);
	
}
