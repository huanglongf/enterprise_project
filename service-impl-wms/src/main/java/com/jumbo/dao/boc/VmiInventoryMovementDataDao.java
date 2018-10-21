package com.jumbo.dao.boc;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.boc.VmiInventoryMovementData;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

/**
 * 
 * @author jieqing.wang
 *
 */
@Transactional
public interface VmiInventoryMovementDataDao extends GenericEntityDao<VmiInventoryMovementData, Long> {

	/**
	 * 查询未创单的BillNo
	 * @return List
	 */
	@NativeQuery
	List<String> findBillNoListByExecuteStatus(RowMapper<String> rowMapper);
	
	/**
	 * 根据批次查询品牌interface过来的数据
	 * @return List
	 */
	@NativeQuery
	List<VmiInventoryMovementData> findInventoryMovementListByBillNoExecuteStatus(@QueryParam("billno") String billno , BeanPropertyRowMapperExt<VmiInventoryMovementData> beanPropertyRowMapperExt);
	
	/**
	 * 根据upc（相关单号）查找
	 * @param upc
	 * @return list
	 */
	@NativeQuery
	List<VmiInventoryMovementData> findInvMovementListByUPC(@QueryParam("upc") String upc , BeanPropertyRowMapperExt<VmiInventoryMovementData> beanPropertyRowMapperExt);
	
	/**
	 * 更新执行状态
	 * @return void
	 */
	@NativeUpdate
	void updateInvMovementListStatusByBillNo(@QueryParam("billno") String billno);
	
	/**
	 * 放入对应的盘点单号
	 * @return void
	 */
	@NativeUpdate
	void updateInvMovementListICCodeByBillNo(@QueryParam("billno") String billno,@QueryParam("iccode") String iccode);
}
