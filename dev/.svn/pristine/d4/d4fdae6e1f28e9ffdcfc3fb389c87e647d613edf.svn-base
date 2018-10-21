package com.bt.lmis.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.NvitationUseanmountDataGroupdetail;

/**
* @ClassName: NvitationUseanmountDataGroupdetailMapper
* @Description: TODO(NvitationUseanmountDataGroupdetailMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface NvitationUseanmountDataGroupdetailMapper<T> extends BaseMapper<T> {

	public List<NvitationUseanmountDataGroupdetail> findByCBID(@Param("cbid")String cbid);
	
	
	public List<NvitationUseanmountDataGroupdetail> findAll(NvitationUseanmountDataGroupdetail param);


	public void delete_by_condition(NvitationUseanmountDataGroupdetail detail);
}
