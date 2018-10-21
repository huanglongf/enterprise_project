package com.lmis.pos.whsAllocate.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.util.RedisUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.job.InventoryScheduled;
import com.lmis.pos.common.model.PosPurchaseOrderDetail;
import com.lmis.pos.common.model.PosPurchaseOrderMain;
import com.lmis.pos.common.model.PosWhsAllocate;
import com.lmis.pos.common.service.PosPurchaseOrderDetailService;
import com.lmis.pos.common.service.PosPurchaseOrderMainService;
import com.lmis.pos.common.util.ConstantCommon;
import com.lmis.pos.whsAllocate.model.PosWhsAllocateDate;
import com.lmis.pos.whsAllocate.service.PosWhsAllocateService;
import com.lmis.pos.whsAllocate.service.PosWhsCompartmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosWhsAllocateController
 * @Description: TODO(PO分仓结果控制层类)
 * @author codeGenerator
 * @date 2018-05-30 14:49:24
 * 
 */
@Api(value = "PO分仓结果")
@RestController("posWhsAllocateController2")
@RequestMapping(value="/pos")
public class PosWhsAllocateController {
    
	@Autowired
    private HttpSession session;
	
	private static final String SUCCESS = "1";
	private static final String FAILURE = "2";
	
	@Autowired
	private RedisUtils redisUtils;
	
	@Value("${redis.key.pos.whsallocate}")
    String redisWhsAllocate;
	
	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	private static Lock lock;
	 
	static {
		lock = new ReentrantLock(true);
	}
	
	@Resource(name="posWhsAllocateServiceImpl2")
	private PosWhsAllocateService<PosWhsAllocate> posWhsAllocateService;
	
	@Resource(name="posPurchaseOrderMainServiceImpl")
	private PosPurchaseOrderMainService<PosPurchaseOrderMain> posPurchaseOrderMainService;
	
	@Resource(name="posPurchaseOrderDetailServiceImpl")
	private PosPurchaseOrderDetailService<PosPurchaseOrderDetail> posPurchaseOrderDetailService;
	
	@Resource(name="posWhsCompartmentServiceImpl")
	private PosWhsCompartmentService<PosWhsAllocate> PosWhsCompartmentService;
	
	@Resource(name = "inventoryScheduled")
	private InventoryScheduled inventoryScheduled;
	
	@ApiOperation(value="执行PO分仓拆分")
	@RequestMapping(value = "/executeAllocate", method = RequestMethod.POST)
    public String executeAllocate(String id) {
		LmisResult<?> lmisResult = new LmisResult<String> ();
		try {
			lock.lock();
			PosPurchaseOrderMain _posPurchaseOrderMain = new PosPurchaseOrderMain();
			_posPurchaseOrderMain.setIsDeleted(false);
			_posPurchaseOrderMain.setIsDisabled(false);
			_posPurchaseOrderMain.setId(id);
			List<PosPurchaseOrderMain> posPurchaseOrderMain = posPurchaseOrderMainService.retrieve(_posPurchaseOrderMain);
			if(ObjectUtils.isNull(posPurchaseOrderMain))
				throw new Exception("PO单[ID=" + id + "]主键不存在");
			if(posPurchaseOrderMain.size() != 1)
				throw new Exception("PO单[ID=" + id + "]主键存在多条");
			if(SUCCESS.equals(posPurchaseOrderMain.get(0).getPoFlag()))
				throw new Exception("PO单[PO_NO=" + posPurchaseOrderMain.get(0).getPoNumber() + "]已分仓");
			PosPurchaseOrderDetail _posPurchaseOrderDetail = new PosPurchaseOrderDetail();
			_posPurchaseOrderDetail.setIsDeleted(false);
			_posPurchaseOrderDetail.setIsDisabled(false);
			_posPurchaseOrderDetail.setPoNumber(posPurchaseOrderMain.get(0).getPoNumber());
			List<PosPurchaseOrderDetail> posPurchaseOrderDetail = posPurchaseOrderDetailService.retrieve(_posPurchaseOrderDetail);
			if(ObjectUtils.isNull(posPurchaseOrderDetail))
				throw new Exception("PO单[PO_NO=" + posPurchaseOrderMain.get(0).getPoNumber() + "]明细不存在");
			for(PosPurchaseOrderDetail __posPurchaseOrderDetail : posPurchaseOrderDetail)
				PosWhsCompartmentService.allocatePurchaseOrder(__posPurchaseOrderDetail);
			List<String> ids = JSONArray.parseArray(redisUtils.get(redisWhsAllocate + ConstantCommon.COMPENSATE_IDS), String.class);
			PosPurchaseOrderMain __posPurchaseOrderMain = new PosPurchaseOrderMain();
			__posPurchaseOrderMain.setId(posPurchaseOrderMain.get(0).getId());
			__posPurchaseOrderMain.setVersion(posPurchaseOrderMain.get(0).getVersion());
			__posPurchaseOrderMain.setPoFlag(SUCCESS);
			__posPurchaseOrderMain.setSplitTime(new Date());
			__posPurchaseOrderMain.setIsSplitTotleQty(ids.size() == 0 ? 0 : posWhsAllocateService.getQtySum(ids));
			__posPurchaseOrderMain.setIsSplitPoQty(ids.size());
			int n = posPurchaseOrderMainService.update(__posPurchaseOrderMain);
			if(n == 0)
				throw new Exception("PO单[PO_NO=" + posPurchaseOrderMain.get(0).getPoNumber() + "]分仓操作无效");
			if(n != 1)
				throw new Exception("PO单[PO_NO=" + posPurchaseOrderMain.get(0).getPoNumber() + "]分仓操作影响多条记录，数据异常");
			lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		} catch (Exception e) {
			StringBuffer _e = new StringBuffer("");
			try {
				//异常信息不为空并且并非已分仓异常校验
				if(e.getMessage() != null && !e.getMessage().contains("已分仓")) {
					//更新PO主表信息，处理状态以及错误信息
					PosPurchaseOrderMain _posPurchaseOrderMain = new PosPurchaseOrderMain();
					_posPurchaseOrderMain.setId(id);
					_posPurchaseOrderMain.setUpdateBy(session.getAttribute("lmisUserId").toString());
					_posPurchaseOrderMain.setPoFlag(FAILURE);
					_posPurchaseOrderMain.setLog(e.getMessage());
					posPurchaseOrderMainService.updatePosPurchaseOrderMain(_posPurchaseOrderMain);
					//当已存在生效分仓记录时启动补偿机制
					List<String> ids = JSONArray.parseArray(redisUtils.get(redisWhsAllocate + ConstantCommon.COMPENSATE_IDS), String.class);
					if(!ObjectUtils.isNull(ids)) {
						//回滚删除补偿所有已插入的分仓结果记录
						int result = posWhsAllocateService.rollbackBatch(session.getAttribute("lmisUserId").toString(), ids);
						//因为会存在id放入redis但没有插入数据库的时间差，所以可能5个id只删除4条数据的情况，但redis中id的数量一定不会小于删除数量
						if(result > ids.size())
							throw new Exception("补偿已插入分仓结果删除数量大于记录的ID数量，ID为"
									+ redisUtils.get(redisWhsAllocate + ConstantCommon.COMPENSATE_IDS));
						//重新计算剩余入库能力
						inventoryScheduled.timer();
					}
				}
			} catch (Exception e1) {
				_e.append("；异常处理失败：失败原因：").append(e1);
			}
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage(e.getMessage() + _e.toString());
		} finally {
			redisUtils.delete(redisWhsAllocate + ConstantCommon.COMPENSATE_IDS);
			lock.unlock();
		}
		return JSON.toJSONString(lmisResult);
    }
	
	@ApiOperation(value="分仓结果核查")
	@RequestMapping(value = "/poAllocatedCheck", method = RequestMethod.POST)
    public String poAllocatedCheck(String ids) {
		return JSON.toJSONString(posWhsAllocateService.poAllocatedCheck(ids));
	}
	
	@ApiOperation(value="PO分仓回滚")
	@RequestMapping(value = "/poAllocatedRollBack", method = RequestMethod.POST)
    public String poAllocatedRollBack(String id) {
		LmisResult<?> lmisResult = new LmisResult<String> ();
		try {
			lock.lock();
			PosPurchaseOrderMain _posPurchaseOrderMain = new PosPurchaseOrderMain();
			_posPurchaseOrderMain.setIsDeleted(false);
			_posPurchaseOrderMain.setIsDisabled(false);
			_posPurchaseOrderMain.setId(id);
			List<PosPurchaseOrderMain> posPurchaseOrderMain = posPurchaseOrderMainService.retrieve(_posPurchaseOrderMain);
			if(ObjectUtils.isNull(posPurchaseOrderMain))
				throw new Exception("PO单[ID=" + id + "]主键不存在");
			if(posPurchaseOrderMain.size() != 1)
				throw new Exception("PO单[ID=" + id + "]主键存在多条");
			if(!SUCCESS.equals(posPurchaseOrderMain.get(0).getPoFlag()))
				throw new Exception("PO[PO_NO=" + posPurchaseOrderMain.get(0).getPoNumber() + "]仅已分仓的PO允许取消");
			//今天不能大于CRD
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(sdf.parse(sdf.format(Calendar.getInstance().getTime())).getTime()
					> sdf.parse(posPurchaseOrderMain.get(0).getCrdTime()).getTime())
				throw new Exception("PO[PO_NO=" + posPurchaseOrderMain.get(0).getPoNumber() + "]CRD已过期，PO不允许取消");
			posWhsAllocateService.poAllocatedRollback(posPurchaseOrderMain.get(0));
			lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		} catch (Exception e) {
			StringBuffer _e = new StringBuffer("");
			try {
				//当已存在生效PO取消分仓记录时启动补偿机制
				List<String> ids = JSONArray.parseArray(redisUtils.get(redisWhsAllocate + ConstantCommon.ROLLBACK_IDS), String.class);
				if(!ObjectUtils.isNull(ids)) {
					//回滚复原补偿所有已删除的分仓结果记录
					int result = posWhsAllocateService.logicalDeleteRollback(session.getAttribute("lmisUserId").toString(), ids);
					if(result != ids.size());
					//重新计算剩余入库能力
					inventoryScheduled.timer();
				}
			} catch (Exception e1) {
				_e.append("；异常处理失败：失败原因：").append(e1);
			}
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage(e.getMessage() + _e.toString());
		} finally {
			redisUtils.delete(redisWhsAllocate + ConstantCommon.ROLLBACK_IDS);
			lock.unlock();
		}
		return JSON.toJSONString(lmisResult);
	}
	
	@ApiOperation(value="仓库分配比例分析")
    @RequestMapping(value = "/selectPosWhsAllocateList", method = RequestMethod.POST)
    public String selectPosWhsAllocate(@ModelAttribute PosWhsAllocateDate posWhsAllocate, @ModelAttribute LmisPageObject pageObject) throws Exception {
        pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posWhsAllocateService.selectPosWhsAllocate(posWhsAllocate, pageObject));
    }
	
	@ApiOperation(value="PO分仓结果查询")
    @RequestMapping(value = "/findPosWhsAllocate", method = RequestMethod.POST)
    public String findPosWhsAllocate(@ModelAttribute PosWhsAllocate allocate,@ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
		return JSON.toJSONString(posWhsAllocateService.findPosWhsAllocate(allocate,pageObject));
    }

}
