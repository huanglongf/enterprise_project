package com.lmis.pos.whsAllocate.service.impl;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.dao.PosPoImportLogMapper;
import com.lmis.pos.common.dao.PosPurchaseOrderDetailMapper;
import com.lmis.pos.common.dao.PosPurchaseOrderMainMapper;
import com.lmis.pos.common.dao.PosWhsAllocateMapper;
import com.lmis.pos.common.dao.PosWhsInventoryMapper;
import com.lmis.pos.common.job.InventoryScheduled;
import com.lmis.pos.common.model.PosPoImportLog;
import com.lmis.pos.common.model.PosPurchaseOrderDetail;
import com.lmis.pos.common.model.PosPurchaseOrderMain;
import com.lmis.pos.common.model.PosWhsAllocate;
import com.lmis.pos.soldtoRule.dao.PosSoldtoRuleMapper;
import com.lmis.pos.soldtoRule.model.PosSoldtoRule;
import com.lmis.pos.whsAllocate.model.PosWhsAllocateDate;
import com.lmis.pos.whsAllocate.service.PosWhsAllocateService;
import com.lmis.pos.whsAllocate.service.PosWhsCompartmentService;

/** 
 * @ClassName: PosWhsAllocateServiceImpl
 * @Description: TODO(PO分仓结果业务层实现类)
 * @author codeGenerator
 * @date 2018-05-30 14:49:24
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service("posWhsAllocateServiceImpl2")
public class PosWhsAllocateServiceImpl<T extends PosWhsAllocate> implements PosWhsAllocateService<T> {

	private static final String INIT = "0";
	
	@Autowired
    private HttpSession session;
	
	private static final String NEWLINE = "<br/>";
	
	@Autowired
	private PosWhsAllocateMapper<T> posWhsAllocateMapper;
	
	@Autowired
	private PosPoImportLogMapper<PosPoImportLog> posPoImportLogMapper;
	
	@Autowired
	private PosPurchaseOrderMainMapper<PosPurchaseOrderMain> posPurchaseOrderMainMapper;
	
	@Autowired
	private PosPurchaseOrderDetailMapper<PosPurchaseOrderDetail> posPurchaseOrderDetailMapper;
	
	@Autowired
	private PosSoldtoRuleMapper<PosSoldtoRule> posSoldtoRuleMapper;
	
	@Autowired
    private PosWhsInventoryMapper posWhsInventoryMapper; 
	
	@Resource(name = "inventoryScheduled")
	private InventoryScheduled inventoryScheduled;
	
	@Resource(name = "posWhsCompartmentServiceImpl")
    private PosWhsCompartmentService<PosWhsAllocate> posWhsCompartmentService;
	
	@Override
	public int getQtySum(List<String> ids) {
		return posWhsAllocateMapper.getQtySum(ids);
	}
	
	@Override
	public int rollbackBatch(String lmisUserId, List<String> ids) {
		int result = 0;
		int batch = 100;
		int size = ids.size();
		int cycle = size / batch;
		if(size % batch != 0) cycle++;
		int fromIndex = 0;
		int toIndex = 0;
		for(int i = 0; i < cycle; i++) {
			fromIndex = i * batch;
			toIndex = fromIndex + batch;
			if(toIndex > size) toIndex = size;
			// 批量删除
			result += posWhsAllocateMapper.deleteBatch(ids.subList(fromIndex, toIndex));
		}
		return result;
	}
	
	@Override
	public LmisResult<?> poAllocatedCheck(String _ids) {
		LmisResult<?> lmisResult = new LmisResult<T>();
		try {
			List<String> ids = JSONObject.parseArray(_ids, String.class);
			List<PosPoImportLog> posPoImportLog = posPoImportLogMapper.retrieveByIds(ids);
			if(posPoImportLog.size() != ids.size())
				throw new Exception("上传主键ID数量" + ids.size() + "，查到对应文件数量" + posPoImportLog.size() + "，数据异常");
			StringBuffer errorMsg = new StringBuffer("");
			for(PosPoImportLog _posPoImportLog : posPoImportLog) {
				if(_posPoImportLog.getIsSplitPoMainQty().intValue() != _posPoImportLog.getPoQty().intValue()) {
					errorMsg.append("文件编号：")
					.append(_posPoImportLog.getFileNo())
					.append("；异常描述：未拆单或剩余未拆PO单")
					.append(NEWLINE);
					continue;
				}
				//1.导入的各PO商品数与相应的拆分结果商品数一致
				StringBuffer _errorMsg = new StringBuffer("");
				PosPurchaseOrderMain _po = new PosPurchaseOrderMain();
				_po.setIsDeleted(false);
				_po.setIsDisabled(false);
				_po.setFileNo(_posPoImportLog.getFileNo());
				List<PosPurchaseOrderMain> po=  posPurchaseOrderMainMapper.retrieve(_po);
				List<String> poNo = new ArrayList<String>();
				List<String> _poNo = new ArrayList<String>();
				for(PosPurchaseOrderMain __po : po) {
					poNo.add(__po.getPoNumber());
					if(ObjectUtils.isNull(_poNo)) {
						_poNo.add(__po.getPoNumber());
					} else {
						_poNo.set(0, __po.getPoNumber());
					}
					List<Map<String, Object>> qtySumDetailByPo = posPurchaseOrderDetailMapper.getQtySumByPo("0", _poNo);
					if(Integer.parseInt(qtySumDetailByPo.get(0).get("qty").toString())
							!= posWhsAllocateMapper.getQtySumByPo(null, _poNo).intValue()) {
						if(ObjectUtils.isNull(_errorMsg)) {
							_errorMsg.append("文件编号：")
									.append(_posPoImportLog.getFileNo())
									.append("；异常描述：PO导入商品数与拆分商品数不一致，PO单号=")
									.append(__po.getPoNumber());
						} else {
							_errorMsg.append("，").append(__po.getPoNumber());
						}
					}
				}
				if(!ObjectUtils.isNull(_errorMsg))
					errorMsg.append(_errorMsg).append(NEWLINE);
				_errorMsg = null;
				_errorMsg = new StringBuffer("");
				//2.导入的商品总数与拆分结果的商品总数一致
				List<Map<String, Object>> qtySumDetail = posPurchaseOrderDetailMapper.getQtySumByPo("0", poNo);
				Integer qtySumAllocated = posWhsAllocateMapper.getQtySumByPo(null, poNo);
				if(Integer.parseInt(qtySumDetail.get(0).get("qty").toString()) != qtySumAllocated.intValue()) {
					errorMsg.append("文件编号：")
							.append(_posPoImportLog.getFileNo())
							.append("；异常描述：EXCEL关联所有PO，导入商品总数与拆分商品总数不一致，导入总数为")
							.append(Integer.parseInt(qtySumDetail.get(0).get("qty").toString()))
							.append("，拆分总数为")
							.append(qtySumAllocated.intValue())
							.append(NEWLINE);
				}
				//3.导入的尺码商品数与拆分结果的尺码商品数一致
				List<Map<String, Object>> _qtySumDetail = posPurchaseOrderDetailMapper.getQtySumByPo("1", poNo);
				for(Map<String, Object> sizeQty : _qtySumDetail) {
					Integer qtySumByPo = posWhsAllocateMapper.getQtySumByPo(sizeQty.get("size").toString(), poNo);
					if(Integer.parseInt(sizeQty.get("qty").toString()) != qtySumByPo.intValue()) {
						errorMsg.append("文件编号：")
								.append(_posPoImportLog.getFileNo())
								.append("；异常描述：EXCEL关联所有PO，尺码及对应数量与拆分后的尺码及对应数量不一致，尺码")
								.append(sizeQty.get("size").toString())
								.append("导入总数为")
								.append(sizeQty.get("qty"))
								.append("，拆分总数为")
								.append(qtySumByPo)
								.append(NEWLINE);
					}
				}
				//4.导入的各PO拆分得到的新PO号规则效验
				//1)sold to:8008021/8008010,不会出现LBZ、FBZ两个关键字
				PosSoldtoRule _psr = new PosSoldtoRule();
				_psr.setIsDeleted(false);
				_psr.setIsDisabled(false);
				_psr.setIsScOccupy(true);
				List<PosSoldtoRule> psr = posSoldtoRuleMapper.retrieve(_psr);
				if(!ObjectUtils.isNull(psr)) {
					List<String> invalidPo = posWhsAllocateMapper.selectInvalidPo("0", psr, _posPoImportLog.getFileNo());
					if(!ObjectUtils.isNull(invalidPo)) {
						for(String _invalidPo : invalidPo) {
							if(ObjectUtils.isNull(_errorMsg)) {
								_errorMsg.append("文件编号：")
								.append(_posPoImportLog.getFileNo())
								.append("；异常描述： 拆分的PO单号匹配到错误关键字，涉及的原PO为：")
								.append(_invalidPo);
							} else {
								_errorMsg.append("，").append(_invalidPo);
							}
						}
					}
				}
				psr = null;
				//2)sold to:8008190/8008193，不会出现新PO号与原PO号不一样
				_psr.setIsScOccupy(false);
				psr = posSoldtoRuleMapper.retrieve(_psr);
				if(!ObjectUtils.isNull(psr)) {
					List<String> invalidPo = posWhsAllocateMapper.selectInvalidPo("1", psr, _posPoImportLog.getFileNo());
					if(!ObjectUtils.isNull(invalidPo)) {
						for(String _invalidPo : invalidPo) {
							if(ObjectUtils.isNull(_errorMsg)) {
								_errorMsg.append("文件编号：")
								.append(_posPoImportLog.getFileNo())
								.append("；异常描述： 拆分的PO单号匹配到错误关键字，涉及的原PO为：")
								.append(_invalidPo);
							} else {
								_errorMsg.append("，").append(_invalidPo);
							}
						}
					}
				}
				if(!ObjectUtils.isNull(_errorMsg))
					errorMsg.append(_errorMsg).append(NEWLINE);
				_errorMsg = null;
			}
			if(!ObjectUtils.isNull(errorMsg))
				throw new Exception(errorMsg.toString());
			lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
			lmisResult.setMessage("无异常");
		} catch (Exception e) {
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage(e.getMessage());
		}
		return lmisResult;
	}
	
	@Override
	public void poAllocatedRollback(PosPurchaseOrderMain posPurchaseOrderMain) throws Exception {
		//删除分仓明细
		PosWhsAllocate _posWhsAllocate = new PosWhsAllocate();
		_posWhsAllocate.setUpdateBy(session.getAttribute("lmisUserId").toString());
		_posWhsAllocate.setPoSource(posPurchaseOrderMain.getPoNumber());
		posWhsCompartmentService.logicalDelete(_posWhsAllocate);
		
		//初始库存生成标识修改
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		posWhsInventoryMapper.updateWhsSurplusSign(date+"%");
		
		//剩余入库能力重新计算
		inventoryScheduled.timer();
		//更新PO主表信息，分仓状态/分仓/已拆分商品数/已拆分明细数
		posPurchaseOrderMain.setUpdateBy(session.getAttribute("lmisUserId").toString());
		posPurchaseOrderMain.setPoFlag(INIT);
		posPurchaseOrderMain.setSplitTime(null);
		posPurchaseOrderMain.setIsSplitTotleQty(0);
		posPurchaseOrderMain.setIsSplitPoQty(0);
		posPurchaseOrderMainMapper.updateRecord(posPurchaseOrderMain);
	}

	@Override
	public int logicalDeleteRollback(String updateBy, List<String> ids) {
		int result = 0;
		int batch = 100;
		int size = ids.size();
		int cycle = size / batch;
		if(size % batch != 0) cycle++;
		int fromIndex = 0;
		int toIndex = 0;
		for(int i = 0; i < cycle; i++) {
			fromIndex = i * batch;
			toIndex = fromIndex + batch;
			if(toIndex > size) toIndex = size;
			// 批量删除
			result += posWhsAllocateMapper.logicalDeleteRollback(updateBy, ids.subList(fromIndex, toIndex));
		}
		return result;
	}
	
    @SuppressWarnings({"unchecked","rawtypes"})
    @Override
    public LmisResult<?> selectPosWhsAllocate(PosWhsAllocateDate t,LmisPageObject pageObject) throws Exception{
        // TODO Auto-generated method stub
        if(t.getProdCode()!=null){
            if(t.getProdCode().length()>50)throw new Exception("字符长度不超过50");
        }
        if(t.getSkuCode()!=null){
            if(t.getSkuCode().length()>50)throw new Exception("字符长度不超过50");
        }
        String i = posWhsAllocateMapper.selectWhsPlanInPoSum(t);
        
        t.setSumPo(i+"");
        Page page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
        posWhsAllocateMapper.selectWhsOutrate(t);
        LmisResult lmisResult = new LmisResult();
        lmisResult.setMetaAndData(page.toPageInfo());
        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        List<Map<String,String>> list = (List<Map<String, String>>) lmisResult.getData();
        double d = 0.00;
        if(list.size()>0){
            for (int j = 0; j < list.size()-1; j++){
                d+=Double.parseDouble(list.get(j).get("ratio"));
                list.get(j).put("ratio", list.get(j).get("ratio")+"%");
            }
            DecimalFormat   fnum  =   new  DecimalFormat("##0.00"); 
            list.get(list.size()-1).put("ratio", fnum.format(100.00-d)+"%");   
        }
        return lmisResult;
    }

	@Override
	public LmisResult<?> findPosWhsAllocate(T t, LmisPageObject pageObject) {
		Page<Map<String, Object>> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
		posWhsAllocateMapper.retrieve(t);
		LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
		lmisResult.setMetaAndData(page.toPageInfo());
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

}
