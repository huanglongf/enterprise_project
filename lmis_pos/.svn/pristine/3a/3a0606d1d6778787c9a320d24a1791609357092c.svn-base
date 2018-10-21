package com.lmis.pos.whsAllocate.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.DateUtils;
import com.lmis.common.dataFormat.JsonUtils;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.util.RedisUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.dao.PosWhsAllocateMapper;
import com.lmis.pos.common.model.PosPurchaseOrderDetail;
import com.lmis.pos.common.model.PosWhsAllocate;
import com.lmis.pos.common.service.PosWhsAllocateService;
import com.lmis.pos.common.util.ConstantCommon;
import com.lmis.pos.crdOut.dao.PosCrdOutMapper;
import com.lmis.pos.crdOut.model.PosCrdOut;
import com.lmis.pos.skuMaster.model.PosSkuMaster;
import com.lmis.pos.soldtoRule.dao.PosSoldtoRuleMapper;
import com.lmis.pos.soldtoRule.model.PosSoldtoRule;
import com.lmis.pos.whs.dao.PosWhsMapper;
import com.lmis.pos.whs.model.PosWhs;
import com.lmis.pos.whsAllocate.enums.PoWhsCodeEnum;
import com.lmis.pos.whsAllocate.model.PosWhsAllocateRate;
import com.lmis.pos.whsAllocate.service.PosWhsCompartmentService;
import com.lmis.pos.whsRateFillin.dao.PosWhsRateFillinMapper;
import com.lmis.pos.whsRateFillin.model.PosWhsRateFillin;
import com.lmis.pos.whsRateLoadin.dao.PosWhsRateLoadinMapper;
import com.lmis.pos.whsRateLoadin.model.PosWhsRateLoadin;
import com.lmis.pos.whsSurplusSc.dao.PosWhsSurplusScMapper;
import com.lmis.pos.whsSurplusSc.model.PosWhsSurplusSc;
import com.lmis.pos.whsSurplusSc.service.PosWhsSurplusScServiceInterface;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

@Transactional(rollbackFor = Exception.class)
@Service("posWhsCompartmentServiceImpl")
public class PosWhsCompartmentServiceImpl<T extends PosWhsAllocate> implements PosWhsCompartmentService<T>{
	//  private static Log logger = LogFactory.getLog(PosWhsCompartmentServiceImpl.class);

    //商品品类 30(配饰)
    private static final String ITEMTYPE_30 = "30";

    //商品po编码最后一位
    private static final String LASTCHAR = "A";

    //分别是类型L/F
    private static final String PO_TYPE_F = "F";
    private static final String PO_TYPE_L = "L";
    
    //
    private static final String DEFAULT_PPK = "1";
    
    //
    private static final String TRUE = "1";
    //private static final String FALSE = "0";

    @Autowired
    private HttpSession session;
	
	@Autowired
    private RedisUtils redisUtils;
    
    @Autowired
    private PosWhsMapper<PosWhs> posWhsMapper;

    @Autowired
    private PosWhsSurplusScMapper<PosWhsSurplusSc> posWhsSurplusScMapper;

    @Autowired
    private PosWhsRateFillinMapper<PosWhsRateFillin> posWhsRateFillinMapper;

    @Autowired
    private PosWhsAllocateService<PosWhsAllocate> posWhsAllocateService;

    @Autowired
    private PosWhsRateLoadinMapper<PosWhsRateLoadin> posWhsRateLoadinMapper;

    @Autowired
    private PosWhsSurplusScServiceInterface<PosWhsSurplusSc> posWhsSurplusScServiceInterface;

    @Autowired
    private PosCrdOutMapper<PosCrdOut> posCrdOutMapper;

    @Autowired
    private PosWhsAllocateMapper<PosWhsAllocate> posWhsAllocateMapper;
    
    @Autowired
    private PosSoldtoRuleMapper<PosSoldtoRule> posSoldtoRuleMapper;
    
    @Value("${redis.key.pos.whsallocate}")
    String redisWhsAllocate;

    @Value("${redis.key.pos.whsallocate.timeout}")
    long redisWhsAllocateTimeout;

    private static String paramStr = null;
    
    @Override
    public void allocatePurchaseOrder(PosPurchaseOrderDetail poDetail) throws Exception{
        if(ObjectUtils.isNull(poDetail))
        	throw new Exception("PO明细为空");
        if(ObjectUtils.isNull(poDetail.getSoldTo()))
        	throw new Exception("PO明细SOLD_TO为空");
        if(ObjectUtils.isNull(poDetail.getPoNumber()))
        	throw new Exception("[SOLD_TO=" + poDetail.getSoldTo() + "]PO_NO为空");
        if(ObjectUtils.isNull(poDetail.getOrderType()))
        	throw new Exception("[SOLD_TO=" + poDetail.getSoldTo() + ",PO_NO=" + poDetail.getPoNumber() + "]ORDER_TYPE为空");
        if(ObjectUtils.isNull(poDetail.getSkuCode()))
        	throw new Exception("[SOLD_TO=" + poDetail.getSoldTo() + ",PO_NO=" + poDetail.getPoNumber() + ",ORDER_TYPE=" + poDetail.getOrderType() + "]SKU_CODE为空");
        
        // 标准参数信息信息拼接
        paramStr = "[SOLD_TO=" + poDetail.getSoldTo()
        + ",PO_NO=" + poDetail.getPoNumber()
        + ",ORDER_TYPE=" + poDetail.getOrderType()
        + ",SKU_CODE=" + poDetail.getSkuCode() + "]";
        
        // 非空业务字段校验
        if(ObjectUtils.isNull(poDetail.getCrdTime()))
        	throw new Exception(paramStr + "CRD为空");
        if(ObjectUtils.isNull(poDetail.getItemType()))
        	throw new Exception(paramStr + "BU为空");
        if(ObjectUtils.isNull(poDetail.getPoType()))
        	throw new Exception(paramStr + "PO_TYPE为空");
        PosSoldtoRule _posSoldtoRule = new PosSoldtoRule();
        _posSoldtoRule.setIsDeleted(false);
        _posSoldtoRule.setIsDisabled(false);
        _posSoldtoRule.setSoldto(poDetail.getSoldTo());
        List<PosSoldtoRule> posSoldtoRule = posSoldtoRuleMapper.retrieve(_posSoldtoRule);
        if(ObjectUtils.isNull(posSoldtoRule))
        	throw new Exception(paramStr + "SOLD_TO拆单规则不存在");
        if (!posSoldtoRule.get(0).getIsAllocated()
        		//|| ITEMTYPE_30.equals(poDetail.getItemType())
        		|| (posCrdOutMapper.getPosCrdOutByDate(poDetail.getCrdTime()) > 0)
        		|| (!PO_TYPE_L.equals(poDetail.getPoType()) && !PO_TYPE_F.equals(poDetail.getPoType()))
        		|| ((PO_TYPE_L.equals(poDetail.getPoType()) || PO_TYPE_F.equals(poDetail.getPoType()))
        				&& LASTCHAR.equals(poDetail.getPoNumber().substring(poDetail.getPoNumber().length() - 1, poDetail.getPoNumber().length())))) {
        	withoutAllocated(poDetail, posSoldtoRule.get(0));
        } else if((PO_TYPE_L.equals(poDetail.getPoType()) || PO_TYPE_F.equals(poDetail.getPoType()))
				&& !LASTCHAR.equals(poDetail.getPoNumber().substring(poDetail.getPoNumber().length() - 1, poDetail.getPoNumber().length()))) {
        	whsDistribution(poDetail, posSoldtoRule.get(0));
		}
        paramStr = null;
    	return;
    }
    
    /**
     * @Title: withoutAllocated
     * @Description: TODO(不分仓流程)
     * @param poDetail
     * @param posSoldtoRule
     * @throws Exception
     * @return: void
     * @author: Ian.Huang
     * @date: 2018年6月6日 下午4:00:39
     */
    private void withoutAllocated(PosPurchaseOrderDetail poDetail, PosSoldtoRule posSoldtoRule) throws Exception{
    	PosWhs ___posWhs = null;
    	if(posSoldtoRule.getIsScOccupy()) {
    		String _posWhsCode = poDetail.getPoNumber().substring(9, 11).toUpperCase();
            if(StrUtil.isBlank(_posWhsCode))
            	throw new Exception(paramStr + "不分仓流程，需占用入库能力，PO单两位下单对象不存在");
            String posWhsCode = PoWhsCodeEnum.getName(_posWhsCode);
            if(ObjectUtils.isNull(posWhsCode))
            	throw new Exception(paramStr + "不分仓流程，需占用入库能力，仓库PO对照码转换映射不存在");
        	PosWhs __posWhs = new PosWhs();
        	__posWhs.setIsDeleted(false);
        	__posWhs.setIsDisabled(false);
        	__posWhs.setPoWhsCode(posWhsCode);
        	List<PosWhs> posWhs = posWhsMapper.retrieve(__posWhs);
            if (ObjectUtils.isNull(posWhs))
            	throw new Exception(paramStr + "不分仓流程，需占用入库能力，归属仓库不存在");
            ___posWhs = posWhs.get(0);
    	}
        purchaseOrderToAllocat(poDetail, ___posWhs, null, null, false, false);
    }
    
    /**
     * @Title: getWhsAllocateRate
     * @Description: TODO(获取仓库对应分仓比例)
     * @param posWhsAllocateRate
     * @param posWhs
     * @return: Double
     * @author: Ian.Huang
     * @date: 2018年6月20日 上午12:04:06
     */
    private Double getWhsAllocateRate(List<PosWhsAllocateRate> posWhsAllocateRate, PosWhs posWhs) {
    	for(PosWhsAllocateRate _posWhsAllocateRate : posWhsAllocateRate) {
    		if(_posWhsAllocateRate.getWhsCode().equals(posWhs.getWhsCode()))
    			return _posWhsAllocateRate.getRate().doubleValue();
    	}
    	return null;
    }
    
    /**
     * @Title: whsDistribution
     * @Description: TODO(订单分仓)
     * @param poDetail
     * @param posSoldtoRule
     * @throws Exception
     * @return: void
     * @author: Ian.Huang
     * @date: 2018年6月6日 下午4:42:27
     */
    private void whsDistribution(PosPurchaseOrderDetail poDetail, PosSoldtoRule posSoldtoRule) throws Exception {
    	List<PosWhs> whs = null;
    	//分仓比例赋值
        List<PosWhsAllocateRate> posWhsAllocateRate = null;
        switch(poDetail.getPoType()) {
        case PO_TYPE_L:
        	//获取po类型对应拆分的仓库
        	whs = posWhsMapper.getWhsDepotByTypeL(poDetail.getPoType(), poDetail.getVNumber(), poDetail.getProdSize());
        	if (!ObjectUtils.isNull(whs))
        		posWhsAllocateRate = posWhsRateLoadinMapper.listPosWhsRateLoadin(poDetail.getVNumber(), poDetail.getProdSize(), whs);
    		//throw new Exception(paramStr + "可分仓的仓库不存在，无法分仓");
        	break;
        case PO_TYPE_F:
        	//获取po类型对应拆分的仓库
        	whs = posWhsMapper.getWhsDepotByTypeF(poDetail.getPoType(), poDetail.getSkuCode());
        	if (!ObjectUtils.isNull(whs))
        		posWhsAllocateRate = posWhsRateFillinMapper.listPosWhsRateFillin(poDetail.getSkuCode(), whs);
    		//throw new Exception(paramStr + "可分仓的仓库不存在，无法分仓");
        	break;
        }
        //存在解析计算所得占比
        boolean isWhsRateFL = false;
        //校验比例之和是否为1或百分之百
        BigDecimal sumRate = new BigDecimal(0);
        if(ObjectUtils.isNull(posWhsAllocateRate)) {
        	//获取需要分仓并且存在区域销售比例的有效仓库
        	whs = posWhsMapper.getWhsDepot(poDetail.getPoType(), TRUE);
        	//仓库区域销售占比之和
        	for(PosWhs _whs : whs)
        		sumRate = sumRate.add(_whs.getSaleRate() == null ? new BigDecimal(0) : _whs.getSaleRate());
        } else {
        	isWhsRateFL = true;
        	//解析计算所得占比之和
        	for(PosWhsAllocateRate _posWhsAllocateRate : posWhsAllocateRate)
        		sumRate = sumRate.add(_posWhsAllocateRate.getRate() == null ? new BigDecimal(0) : _posWhsAllocateRate.getRate());
        }
        sumRate = sumRate.setScale(0, BigDecimal.ROUND_HALF_DOWN);
        if(sumRate.intValue() != 1 && sumRate.intValue() != 100)
    		throw new Exception(paramStr + "分仓比例为" + (ObjectUtils.isNull(posWhsAllocateRate) ? "仓库区域销售占比" : "解析计算所得占比") +"，总和等于" + sumRate + "，不等于1或100%，数据异常");
        //获取最小单位约数，分仓数，尾数
        String ppk = "";
        PosSkuMaster skuMaster = posWhsAllocateMapper.getSkuMaster(poDetail.getProdCode());
        if (skuMaster == null || skuMaster.getPpk() == null){
            ppk = DEFAULT_PPK;
        }else{
            ppk = skuMaster.getPpk();
        }
        int allocated = poDetail.getProdQty().intValue() / Integer.parseInt(ppk);
        int tailGoods = poDetail.getProdQty().intValue() % (Integer.parseInt(ppk));
        //已经分仓数量
        int sum = 0;
        //存在能够用于分仓的比例
        boolean isWhsAllocateRate = false;
        //尾货是否已被分仓
        boolean isTailGoods = false;
        for (int i = 0; i < whs.size(); i++) {
            PosWhs _whs = whs.get(i);
            Double whsAllocateRate = null;
            //分仓比例是否存在
            if(ObjectUtils.isNull(posWhsAllocateRate)) {
            	//不存在
            	//直接取仓库区域销售占比
            	whsAllocateRate = _whs.getSaleRate().doubleValue();
            } else {
            	//存在
            	//获取当前仓库对应的分仓比例
            	whsAllocateRate = getWhsAllocateRate(posWhsAllocateRate, _whs);
            }
            //分仓比例存在且不为0
            if(whsAllocateRate != null && whsAllocateRate != 0) {
            	if(isWhsAllocateRate == false)
            		isWhsAllocateRate = true;
                int _allocated = 0;
                if (i != whs.size() - 1) {
                    if (whsAllocateRate <= 1) {
                    	_allocated = (int) (allocated * whsAllocateRate);
                    } else {
                    	_allocated = (int) (allocated * whsAllocateRate / 100);
                    }
                    sum += _allocated;
                } else {
                	_allocated = allocated - sum;
                }
                _allocated *= Integer.parseInt(ppk);
                if(_whs.getIsTailGoods() && isTailGoods == false) {
            		isTailGoods = true;
            		_allocated += tailGoods;
                }
                if(_allocated != 0)
                	whsDistributionByDay(poDetail, _whs, _allocated, isWhsRateFL);
            }
        }
        //正常流程补缺尾货仓场景
        if(isWhsAllocateRate) {
        	//查出的分仓仓库未包含尾货仓且尾货不为0
        	if(!isTailGoods && tailGoods != 0) {
        		//尾货仓
        		PosWhs whsDepotTailGoods = posWhsMapper.getWhsDepotTailGoods();
        		if (ObjectUtils.isNull(whsDepotTailGoods))
        			throw new Exception(paramStr + "尾货走尾货仓流程，尾货仓不存在");
        		whsDistributionByDay(poDetail, whsDepotTailGoods, tailGoods, isWhsRateFL);
        	}
        } else {
        	throw new Exception(paramStr + "不存在能够分仓的比例");
        }
    }
    
    /**
     * @Title: whsDistributionByDay
     * @Description: TODO(仓库分配到天)
     * @param poDetail
     * @param whs
     * @param allocated
     * @param isWhsRateFL
     * @throws Exception
     * @return: void
     * @author: Ian.Huang
     * @date: 2018年6月5日 上午1:05:59
     */
    private void whsDistributionByDay(PosPurchaseOrderDetail poDetail, PosWhs whs, int allocated, boolean isWhsRateFL) throws Exception{
    	//当前日期id yy-MM-dd
        String dateId = DateUtils.dateToString1(new Date(), DateUtils.patternA);
        //3.根据SKU dataId码和bu 和得到的仓库 从 pos_whs_surplus_sc  查询仓库剩余入库能力分析
        List<PosWhsSurplusSc> posWhsSurplusScInfo = posWhsSurplusScMapper.getPosWhsSurplusScInfo(dateId, poDetail.getItemType(), whs.getWhsCode());
        if (posWhsSurplusScInfo == null || posWhsSurplusScInfo.size() == 0)
            throw new Exception(paramStr + dateId + whs.getWhsName() + poDetail.getItemType() + "入库能力分析不存在");
        //计算最早到货日期
        String crd = DateUtils.formateDate(DateUtils.getSomeDaysBeforeAfter(DateUtils.stringToDate(poDetail.getCrdTime()), whs.getArrivalLeadDays().intValue()));
        //定义crd是否合法
        boolean isCrdValid=false;
        double maxAbility = 0;
        for (PosWhsSurplusSc posWhsSurplusSc : posWhsSurplusScInfo){
            //如果时间大于最早日期加起来
            if (!isCrdValid
            		&& ((DateUtils.stringToDate(posWhsSurplusSc.getScheduleDate()).getTime()
            				>= (DateUtils.stringToDate(crd)).getTime()))) {
            	//存在大于crd的可入库能力时间
            	isCrdValid=true;
            }
            maxAbility += posWhsSurplusSc.getInEnable().doubleValue();
        }
        //如果当前最赞到货时间大于所有入库能力分析的时间 不能执行
        if(!isCrdValid)
        	throw new Exception(paramStr + "拆分至" + whs.getWhsName() +"的crd为" + crd + "，超出最大入库分析能力");
        //3.1判断入库能力总量是否够
        if (allocated > maxAbility)
        	throw new Exception(paramStr + whs.getWhsName() + poDetail.getItemType() + "最大入库能力为" + maxAbility + "，不满足拆分所需" + allocated);
        while (allocated > 0){
            //4.分仓，循环执行分仓，同时更新内存中剩余入库能力的数据
            for (int i = 0; i < posWhsSurplusScInfo.size(); i++) {
                PosWhsSurplusSc posWhsSurplusSc = posWhsSurplusScInfo.get(i);
                //如果时间时间一样，处理分仓
                if (crd.equals(posWhsSurplusSc.getScheduleDate())){
                    int scheduleResult = 0;
                    int currentMax = posWhsSurplusSc.getInEnablePlus().intValue() <=0 ? 0 : posWhsSurplusSc.getInEnablePlus().intValue();
                    if (allocated > currentMax){
                    	scheduleResult = currentMax;
                    	allocated -= currentMax;
                    }else{
                    	scheduleResult = allocated;
                    	allocated = 0;
                    }
                    if(scheduleResult != 0)
                    	purchaseOrderToAllocat(poDetail, whs, posWhsSurplusSc.getScheduleDate(), scheduleResult, true, isWhsRateFL);
                    if(allocated == 0)
                    	break;
                }
            }
            if (allocated != 0)
            	crd = DateUtils.formateDate(DateUtils.getSomeDaysBeforeAfter(DateUtils.stringToDate(crd), 1));
        }
    }
    
    /**
     * @Title: purchaseOrderToAllocat
     * @Description: TODO(分仓结果生成并保存)
     * @param poDetail
     * @param posWhs
     * @param crd
     * @param prodQty
     * @param isWhsAllocate
     * @param isWhsAllocateRate
     * @throws Exception
     * @return: void
     * @author: Ian.Huang
     * @date: 2018年6月6日 下午4:35:39
     */
    private void purchaseOrderToAllocat(PosPurchaseOrderDetail poDetail, PosWhs posWhs, String crd, Integer prodQty, boolean isWhsAllocate, boolean isWhsAllocateRate) throws Exception{
        PosWhsAllocate t = new PosWhsAllocate();
        BeanUtils.copyProperties(poDetail, t);
        t.setPoSource(poDetail.getPoNumber());
        t.setPoSource1(poDetail.getPoNumber1());
        t.setCrdSource(poDetail.getCrdTime());
        t.setQtySource(poDetail.getProdQty());
        if(!ObjectUtils.isNull(posWhs))
        	t.setWhsCode(posWhs.getWhsCode());
        if (isWhsAllocate) {
        	t.setPoAllocated(
            		(DateUtils.dateToString1(DateUtils.stringToDate(crd), DateUtils.patternB) + poDetail.getPoType() + posWhs.getPoWhsCode() + poDetail.getCategory() + poDetail.getItemType())
            		.toUpperCase()
            		);
        	t.setCrd(crd);
        	t.setQty(prodQty);
        } else {
        	t.setPoAllocated(
        			ObjectUtils.isNull(posWhs) ? poDetail.getPoNumber1() :
        			poDetail.getPoNumber1().replace(poDetail.getPoNumber().substring(9, 11), posWhs.getPoWhsCode()));
        	t.setCrd(poDetail.getCrdTime());
        	t.setQty(poDetail.getProdQty());
        }
        t.setBu(poDetail.getItemType());
        t.setSize(poDetail.getProdSize());
        t.setCancelDate(poDetail.getCancelTime());
        t.setIsWhsAllocate(isWhsAllocate);
        t.setIsWhsAllocateRate(isWhsAllocateRate);
        t.setId(RandomUtil.randomUUID());
        String strRedis = redisUtils.get(redisWhsAllocate + ConstantCommon.COMPENSATE_IDS);
        if (StrUtil.isBlank(strRedis)) {
            List<String> list = new ArrayList<>();
            list.add(t.getId());
            redisUtils.set(redisWhsAllocate + ConstantCommon.COMPENSATE_IDS, list, redisWhsAllocateTimeout);
        } else {
            List<String> list = JsonUtils.json2List(strRedis, String.class);
            list.add(t.getId());
            redisUtils.set(redisWhsAllocate + ConstantCommon.COMPENSATE_IDS, list, redisWhsAllocateTimeout);
        }
        t.setVersion(1);
        t.setCreateBy(session.getAttribute("lmisUserId").toString());
        t.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
        //保存
        posWhsAllocateService.savePosWhsAllocate(t);
        //调用重新计算方法
        LmisResult<?> updatePlannedInAbility = posWhsSurplusScServiceInterface.updatePlannedInAbility(t.getWhsCode(), t.getBu(), t.getCrd(), t.getQty());
        if (updatePlannedInAbility == null || (!LmisConstant.RESULT_CODE_SUCCESS.equals(updatePlannedInAbility.getCode())))
            throw new Exception("[PO_NO=" + poDetail.getPoNumber()
            + ",SKU_CODE=" + poDetail.getSkuCode()
            + ",WHS_CODE=" + t.getWhsCode()
            + ",BU=" + t.getBu()
            + ",CRD=" + t.getCrd()
            + ",QTY=" + t.getQty()
            + "]调用重新计算方法失败,失败原因:"
            + updatePlannedInAbility.getMessage());
    }
    
    @Transactional(propagation=Propagation.REQUIRES_NEW,isolation=Isolation.READ_COMMITTED,noRollbackFor={Exception.class})
	@Override
	public void logicalDelete(T t) {
		//记录即将逻辑删除的ID
		redisUtils.set(redisWhsAllocate + ConstantCommon.ROLLBACK_IDS, posWhsAllocateMapper.listPosWhsAllocateIds(t.getPoSource()), redisWhsAllocateTimeout);
		posWhsAllocateMapper.logicalDelete(t);
	}
    
}
