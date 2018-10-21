package com.bt.lmis.dao;
import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.NvitationRealuseanmountData;

/**
* @ClassName: NvitationRealuseanmountDataMapper
* @Description: TODO(NvitationRealuseanmountDataMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface NvitationRealuseanmountDataMapper<T> extends BaseMapper<T> {

	public List<NvitationRealuseanmountData> list(NvitationRealuseanmountData entity);
   
	public  void  update_settleflag(NvitationRealuseanmountData entity);

	public List<NvitationRealuseanmountData> listSE(NvitationRealuseanmountData entity);

	public int countSE(NvitationRealuseanmountData entity);

}
