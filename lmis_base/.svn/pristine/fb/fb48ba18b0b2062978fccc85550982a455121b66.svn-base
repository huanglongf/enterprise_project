package com.lmis.basis.fixedAssetsAllot.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.basis.fixedAssetsAllot.dao.BasisFixedAssetsAllotMapper;
import com.lmis.basis.fixedAssetsAllot.model.BasisFixedAssetsAllot;
import com.lmis.basis.fixedAssetsAllot.model.ViewBasisFixedAssetsAllot;
import com.lmis.basis.fixedAssetsAllot.service.BasisFixedAssetsAllotServiceInterface;
import com.lmis.basis.fixedAssetsAmt.dao.BasisFixedAssetsAmtMapper;
import com.lmis.basis.fixedAssetsAmt.model.BasisFixedAssetsAmt;
import com.lmis.basis.fixedAssetsDetail.dao.BasisFixedAssetsDetailMapper;
import com.lmis.basis.fixedAssetsDetail.model.BasisFixedAssetsDetail;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.constant.LBASEConstant;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.constant.dao.SetupConstantMapper;
import com.lmis.setup.constant.model.SetupConstant;

/** 
 * @ClassName: BasisFixedAssetsAllotServiceImpl
 * @Description: TODO(固定资产调拨日志业务层实现类)
 * @author codeGenerator
 * @date 2018-04-02 10:47:58
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class BasisFixedAssetsAllotServiceImpl<T extends BasisFixedAssetsAllot> implements BasisFixedAssetsAllotServiceInterface<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<BasisFixedAssetsAllot> dynamicSqlService;
	@Autowired
	private BasisFixedAssetsAllotMapper<T> basisFixedAssetsAllotMapper;
	@Autowired
	private BasisFixedAssetsDetailMapper<BasisFixedAssetsDetail> basisFixedAssetsDetailMapper;
	
	@Autowired
	private BasisFixedAssetsAmtMapper< BasisFixedAssetsAmt> basisFixedAssetsAmtMapper;
	
	
	@Autowired
	private SetupConstantMapper<SetupConstant> setupConstantMapper;
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

	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			
		// TODO(业务校验)
		
		// 插入操作
		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			
		// TODO(业务校验)
		
		// 更新操作
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@Override
	public LmisResult<?> deleteBasisFixedAssetsAllot(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, basisFixedAssetsAllotMapper.logicalDelete(t));
	}

	@Override
	public LmisResult<?> selectBasisFixedAssetsAllot(ViewBasisFixedAssetsAllot allot, LmisPageObject pageObject)
			throws Exception {
		if(ObjectUtils.isNull(allot.getAssetsCode())) throw new Exception(LBASEConstant.EBASE000048);
		Page<Map<String, Object>> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
		basisFixedAssetsAllotMapper.selectBasisFixedAssetsAllot(allot);
		LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
		lmisResult.setMetaAndData(page.toPageInfo());
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> transferFixedAssets(BasisFixedAssetsAllot allot) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthsdf = new SimpleDateFormat("M");
		
		//校验传参非空
		if(ObjectUtils.isNull(allot.getAssetsCode())||ObjectUtils.isNull(allot.getAllotDate())
				||ObjectUtils.isNull(allot.getOrgIdTo()))throw new Exception(LBASEConstant.EBASE000066);
		
		//添加调拨日志
		BasisFixedAssetsDetail detail = new BasisFixedAssetsDetail();
		detail.setAssetsCode(allot.getAssetsCode());
		detail.setIsDeleted(false);
		detail.setIsDisabled(false);
		List<BasisFixedAssetsDetail> detailList = basisFixedAssetsDetailMapper.retrieve(detail);
		if(detailList.size()!=1) throw new Exception(LBASEConstant.EBASE000067);
		BasisFixedAssetsDetail updateDetail = detailList.get(0);
		allot.setOrgIdFrom(updateDetail.getOrgId());
		allot.setCreateBy(session.getAttribute("lmisUserId").toString());//创建人
		allot.setUpdateBy(session.getAttribute("lmisUserId").toString());//更新人
		allot.setPwrOrg(session.getAttribute("lmisUserOrg").toString());//所属机构
		basisFixedAssetsAllotMapper.create((T) allot);
		
		//修改固定资产明细表中所属组织机构
		updateDetail.setOrgId(allot.getOrgIdTo());
		updateDetail.setUpdateBy(session.getAttribute("lmisUserId").toString());//更新人
		basisFixedAssetsDetailMapper.update(updateDetail);
		//修改摊销明细所属组织机构
		
		SetupConstant constant=new SetupConstant();
		constant.setIsDeleted(false);
		constant.setIsDisabled(false);
		constant.setGroupCode("year");
		
		SetupConstant constant1=new SetupConstant();
		constant1.setIsDeleted(false);
		constant1.setIsDisabled(false);
		constant1.setGroupCode("month");
		
		BasisFixedAssetsAmt amt=new BasisFixedAssetsAmt(); 
		amt.setIsDeleted(false);
		amt.setIsDisabled(false);
		amt.setAssetsCode(allot.getAssetsCode());
		
		
		String tempYear="";
		String tempYearCode="";
		List<SetupConstant> constantList = null;
		Date dt=sdf.parse(allot.getAllotDate());
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		int i = 1;
		while (true) {
			
			Date dt1=rightNow.getTime();
			String year = yearsdf.format(dt1);
			if(!tempYear.equals(year)) {
				constant.setConstantName(year);
				constantList = setupConstantMapper.retrieve(constant);
				if(constantList.size()!=1) throw new Exception(LBASEConstant.EBASE000068);
				tempYearCode=constantList.get(0).getConstantCode();
				tempYear=year;
			}
			String month=monthsdf.format(dt1);
			constant1.setConstantName(month);
			List<SetupConstant> monthList = setupConstantMapper.retrieve(constant1);
			if(monthList.size()!=1) throw new Exception(LBASEConstant.EBASE000069);
			amt.setYearCode(tempYearCode);
			amt.setTimeCode(monthList.get(0).getConstantCode());
			
			List<BasisFixedAssetsAmt> amtList = basisFixedAssetsAmtMapper.retrieve(amt);
			if(amtList.size()==1) {
				BasisFixedAssetsAmt updateAmt = amtList.get(0);
				updateAmt.setUpdateBy(session.getAttribute("lmisUserId").toString());
				updateAmt.setOrgId(allot.getOrgIdTo());
				basisFixedAssetsAmtMapper.update(updateAmt);
			}else if(amtList.size()==0) {
				if(i==1) {
					throw new Exception(LBASEConstant.EBASE000070);
				}
				break;
			}else {
				throw new Exception(LBASEConstant.EBASE000071);
			}
			++i;
			rightNow.add(Calendar.MONTH,1);//日期加寿命个月
		}
		return new LmisResult<String>(LmisConstant.RESULT_CODE_SUCCESS, "");
	}

}
