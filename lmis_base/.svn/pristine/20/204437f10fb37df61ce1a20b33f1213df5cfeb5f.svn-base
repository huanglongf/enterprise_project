package com.lmis.basis.warehouse.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.lmis.basis.costCenter.dao.BasisCostCenterMapper;
import com.lmis.basis.costCenter.model.BasisCostCenter;
import com.lmis.setup.constant.dao.SetupConstantMapper;
import com.lmis.setup.constant.model.SetupConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.basis.warehouse.dao.BasisWarehouseMapper;
import com.lmis.basis.warehouse.model.BasisWarehouse;
import com.lmis.basis.warehouse.service.BasisWarehouseServiceInterface;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.constant.LBASEConstant;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;

/** 
 * @ClassName: BasisWarehouseServiceImpl
 * @Description: TODO(仓库业务层实现类)
 * @author codeGenerator
 * @date 2018-01-19 15:05:00
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class BasisWarehouseServiceImpl<T extends BasisWarehouse> implements BasisWarehouseServiceInterface<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<BasisWarehouse> dynamicSqlService;
	@Autowired
	private BasisWarehouseMapper<T> basisWarehouseMapper;

	@Autowired
	private SetupConstantMapper<SetupConstant> setupConstantMapper;

	@Autowired
	private BasisCostCenterMapper<BasisCostCenter> basisCostCenterMapper;

	@Autowired
	private HttpSession session;
	
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception {
		return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		LmisResult<?> _lmisResult = dynamicSqlService.executeSelect(dynamicSqlParam);
		if(LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode())) return _lmisResult;
		List<Map<String, Object>> result =  (List<Map<String, Object>>) _lmisResult.getData();
		if(ObjectUtils.isNull(result)) throw new Exception(LBASEConstant.EBASE000007);
		if(result.size() != 1) throw new Exception(LBASEConstant.EBASE000008);
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			
		// TODO(业务校验)
		
		BasisWarehouse warehouse = dynamicSqlService.generateTableModel((DynamicSqlParam<BasisWarehouse>) dynamicSqlParam, new BasisWarehouse()).getTableModel();
		//导入验证
		SetupConstant warehouseType = new SetupConstant();
		warehouseType.setIsDeleted(false);
		warehouseType.setIsDisabled(false);
		if(ObjectUtils.isNull(warehouse.getWarehouseType())) throw new Exception(LBASEConstant.EBASE000096);
		warehouseType.setConstantCode(warehouse.getWarehouseType());
		if(setupConstantMapper.retrieve(warehouseType).size()!=1) throw new Exception(LBASEConstant.EBASE000097);
		if(!ObjectUtils.isNull(warehouse.getCostCenter())) {
			BasisCostCenter basisCostCenter = new BasisCostCenter();
			basisCostCenter.setIsDeleted(false);
			basisCostCenter.setIsDisabled(false);
			basisCostCenter.setCostCenterId(warehouse.getCostCenter());
			if(basisCostCenterMapper.retrieve(basisCostCenter).size()!=1) throw new Exception(LBASEConstant.EBASE000098);
		}

		 //非空校验
	    if(ObjectUtils.isNull(warehouse.getWarehouseCode())) throw new Exception(LBASEConstant.EBASE000099);
	    //唯一校验
		BasisWarehouse checkWarehouse=new BasisWarehouse();
		checkWarehouse.setIsDeleted(false);
		checkWarehouse.setWarehouseCode(warehouse.getWarehouseCode());
		if(basisWarehouseMapper.retrieve((T) checkWarehouse).size()>0) throw new Exception(LBASEConstant.EBASE000100);
        if (!ObjectUtils.isNull(warehouse.getUnitPrice())&&!isNumeric(warehouse.getUnitPrice())) throw new Exception(LBASEConstant.EBASE000101);
		if (!ObjectUtils.isNull(warehouse.getArea())&&!isNumeric(warehouse.getArea())) throw new Exception(LBASEConstant.EBASE000102);
		//创建人
		dynamicSqlParam.setCreateBy(session.getAttribute("lmisUserId").toString());
		//更新人
		dynamicSqlParam.setUpdateBy(session.getAttribute("lmisUserId").toString());
		//所属机构
		dynamicSqlParam.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
		
		// 插入操作
		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}
	public static boolean isNumeric(String str) {

		try {
			BigDecimal temp = new BigDecimal(str);
			if(temp.compareTo(new BigDecimal("0"))==-1){
				return false;
			}
		} catch (Exception e) {
			return false;//异常 说明包含非数字。
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			
		// TODO(业务校验)
		
		//存在校验
		BasisWarehouse warehouse = dynamicSqlService.generateTableModel((DynamicSqlParam<BasisWarehouse>) dynamicSqlParam, new BasisWarehouse()).getTableModel();
		BasisWarehouse checkWarehouse=new BasisWarehouse();
		checkWarehouse.setIsDeleted(false);
		checkWarehouse.setWarehouseCode(warehouse.getWarehouseCode());
		if(basisWarehouseMapper.retrieve((T) checkWarehouse).size()==0) throw new Exception(LBASEConstant.EBASE000103);
		
		//更新人
		dynamicSqlParam.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		// 更新操作
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> deleteBasisWarehouse(T t) throws Exception {
			
		// TODO(业务校验)
		
		//存在校验
		BasisWarehouse checkWarehouse=new BasisWarehouse();
		checkWarehouse.setIsDeleted(false);
		checkWarehouse.setWarehouseCode(t.getWarehouseCode());
		if(basisWarehouseMapper.retrieve((T) checkWarehouse).size()==0) throw new Exception(LBASEConstant.EBASE000103);
		
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, basisWarehouseMapper.logicalDelete(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> switchBasisWarehouse(T t) throws Exception {
		// TODO Auto-generated method stub
		
		//存在校验
		BasisWarehouse checkWarehouse=new BasisWarehouse();
		checkWarehouse.setIsDeleted(false);
		checkWarehouse.setWarehouseCode(t.getWarehouseCode());
		if(basisWarehouseMapper.retrieve((T) checkWarehouse).size()==0) throw new Exception(LBASEConstant.EBASE000103);
		
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, basisWarehouseMapper.shiftValidity(t));
	}

}
