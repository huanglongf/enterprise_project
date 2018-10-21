package com.lmis.pos.whs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: PosWhsServiceInterface
 * @Description: TODO(仓库主表业务层接口类)
 * @author codeGenerator
 * @date 2018-05-29 10:13:18
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosWhsServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param t
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:13:18
	 */
	LmisResult<?> executeSelect(T t, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:13:18
	 */
	LmisResult<?> executeSelect(T t) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:13:18
	 */
	LmisResult<?> executeInsert(T t) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:13:18
	 */
	LmisResult<?> executeUpdate(T t) throws Exception;
	
	/**
	 * @Title: deletePosWhs
	 * @Description: TODO(删除仓库主表)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:13:18
	 */
	LmisResult<?> deletePosWhs(T t) throws Exception;

    /**
     * @param t
     * @return
     * @throws Exception
     */
    LmisResult<T> switchWhs(T t) throws Exception;

    /**
     * @param poWhs
     * @return
     */
    LmisResult<T> checkCodeName(T t) throws Exception;
    
    /**
     * 
     * <b>方法名：</b>：queryWhsToSetSaleRate<br>
     * <b>功能说明：</b>：TODO<br>
     * @author <font color='blue'>chenkun</font> 
     * @date  2018年6月13日 上午10:29:35
     * @return
     * @throws Exception
     */
    LmisResult<T> queryWhsToSetSaleRate() throws Exception;
    
     /***
      * 
      * <b>方法名：</b>：updateWhsSaleRate<br>
      * <b>功能说明：</b>：TODO<br>
      * @author <font color='blue'>chenkun</font> 
      * @date  2018年6月13日 上午10:36:22
      * @param t
      * @return
      * @throws Exception
      */
    LmisResult<T> updateWhsSaleRate(List<T> t) throws Exception;
}
