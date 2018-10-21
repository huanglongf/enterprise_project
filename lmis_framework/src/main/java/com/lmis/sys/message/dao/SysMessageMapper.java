package com.lmis.sys.message.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.sys.message.model.SysMessage;
import com.lmis.sys.message.model.ViewSysMessage;

/** 
 * @ClassName: SysMessageMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-22 20:04:53
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysMessageMapper<T extends SysMessage> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewSysMessage
	 * @Description: TODO(查询view_sys_message)
	 * @param viewSysMessage
	 * @return: List<ViewSysMessage>
	 * @author: codeGenerator
	 * @date: 2018-05-22 20:04:53
	 */
	List<ViewSysMessage> retrieveViewSysMessage(ViewSysMessage viewSysMessage);
	
	/**
	 * 根据id获取消息详情
	 * @author xuyisu
	 * @date  2018/05/25
	 * @param id
	 * @return
	 */
	SysMessage getSysMessageById(@Param("id") String id);
	
	/**
     * 根据code获取消息详情
     * @author xuyisu
     * @date  2018/05/25
     * @param id
     * @return
     */
    SysMessage getSysMessageByCode(@Param("code") String code);
    /**
     * 根据传入的code获取多个消息
     * @author xuyisu
     * @date  2018年6月12日
     * 
     * @param list
     * @return
     */
    public List<Map<String,String>> getSysMessages(List<String> list);
}
