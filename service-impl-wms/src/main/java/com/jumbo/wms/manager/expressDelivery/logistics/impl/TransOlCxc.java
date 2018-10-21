package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.CxcConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.ChannelInsuranceManager;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.warehouse.CxcConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.CxcConfirmOrderQueueCommand;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlCxc")
@Transactional
public class TransOlCxc extends BaseManagerImpl implements TransOlInterface {

    
	private static final long serialVersionUID = 2663557382993037214L;
	
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private CxcConfirmOrderQueueDao cxcConfirmOrderQueueDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private ChannelInsuranceManager channelInsuranceManager;
    

    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        return null;
    }

    @Override
    public StaDeliveryInfo matchingTransNo(Long staId) {
    	StockTransApplication stab = staDao.getByPrimaryKey(staId);
		StaDeliveryInfo sd = stab.getStaDeliveryInfo();
		try {
            // 运单编号生成规则:YYMMDD+随机6位=12位
			Date date = new Date();
			SimpleDateFormat sFormat = new SimpleDateFormat("yyMMdd");
			String transNo_start = sFormat.format(date);
			String tempString = Long.toString(date.getTime());
			String transNo_end = tempString.substring(tempString.length() - 6,
					tempString.length());
			if (transNo_start.length() == 6 && transNo_end.length() == 6) {
				String transNo = transNo_start.concat(transNo_end);
				sd.setTrackingNo(transNo);
                sd.setExtTransOrderId(stab.getCode()); // 设置物流商平台订单号,以便作为其他业务使用
			}
		} catch (Exception e) {
			log.error("The error reason is:", e.getMessage());
		}

		return sd;
    }

    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) {
        StockTransApplication stab = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = stab.getStaDeliveryInfo();
        try {
            // 运单编号生成规则:YYMMDD+随机6位=12位
            Date date = new Date();
            SimpleDateFormat sFormat = new SimpleDateFormat("yyMMdd");
            String transNo_start = sFormat.format(date);
            String tempString = Long.toString(date.getTime());
            String transNo_end = tempString.substring(tempString.length() - 6, tempString.length());
            if (transNo_start.length() == 6 && transNo_end.length() == 6) {
                String transNo = transNo_start.concat(transNo_end);
                sd.setTrackingNo(transNo);
                sd.setExtTransOrderId(stab.getCode()); // 设置物流商平台订单号,以便作为其他业务使用
            }
        } catch (Exception e) {
            log.error("The error reason is:", e.getMessage());
        }

        return sd;
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        // 合并订单传主订单 ,getGroupSta存的是父订单的ID
        if (null != sta.getGroupSta() && (null == sta.getIsMerge() || !sta.getIsMerge())) {
            sta = staDao.getByPrimaryKey(sta.getGroupSta().getId()); // 父ID
        }
        List<CxcConfirmOrderQueueCommand> cList = cxcConfirmOrderQueueDao.findCxcConfirmOrderQueueByStaId(sta.getId(), new BeanPropertyRowMapper<CxcConfirmOrderQueueCommand>(CxcConfirmOrderQueueCommand.class));
		if (null != cList && cList.size() > 0) {
			try {
				for (CxcConfirmOrderQueueCommand entity : cList) {
					String trackingNo = entity.getTrackingNo();
					if (null != trackingNo) {
                        // 查询是否存在
						CxcConfirmOrderQueue cEntity = cxcConfirmOrderQueueDao.getCxcConfirmOrderQueueByTransCode(trackingNo);
                        // 否就保存
						if (null == cEntity) {
					        CxcConfirmOrderQueue cxcConfirmOrderQueue = new CxcConfirmOrderQueue();
                            // 常量表里拿到 cxc指定的客户编号,批号(客户代码)
					        String cmCode = chooseOptionDao.findByCategoryCodeAndKey("cmCode","CXC").getOptionValue();
					        String bdShipmentno = chooseOptionDao.findByCategoryCodeAndKey("bdShipmentno","CXC").getOptionValue();
					        if (!StringUtil.isEmpty(cmCode) && !StringUtil.isEmpty(bdShipmentno)) {
					        	cxcConfirmOrderQueue.setCmCode(cmCode);
					        	cxcConfirmOrderQueue.setBdShipmentno(bdShipmentno);
					        	cxcConfirmOrderQueue.setBdBoxcode("1");
					        	cxcConfirmOrderQueue.setBdPackageno(1L);
					        	cxcConfirmOrderQueue.setCreatedate(new Date());
					        	cxcConfirmOrderQueue.setBdCodpay(null);
					        	cxcConfirmOrderQueue.setBdCode(entity.getTrackingNo());
					        	cxcConfirmOrderQueue.setBdCaddress(entity.getAddress());
					        	cxcConfirmOrderQueue.setBdConsigneename(entity.getReceiver());
					        	cxcConfirmOrderQueue.setBdConsigneephone(entity.getMobile());
					        	if (null != entity.getWeight()) {
					        		cxcConfirmOrderQueue.setBdWeidght(entity.getWeight().doubleValue());
								}else {
									cxcConfirmOrderQueue.setBdWeidght(0.0);
								}
                                // 设置代收货款
                                // 如果是货到付款,就设置代收货款的具体金额
					        	if (entity.getIsCod()) {
					        		BigDecimal purprice = (entity.getOrderTotalActual() == null ? new BigDecimal(0) : entity.getOrderTotalActual()).add((entity.getOrderTransferFree() == null ? new BigDecimal(0) : entity.getOrderTransferFree()));
						        	cxcConfirmOrderQueue.setBdPurprice(purprice);
								}else {
									cxcConfirmOrderQueue.setBdPurprice(new BigDecimal(0));
								}
                                // 设置保价
					        	if (null != entity.getInsuranceAmount() && null != entity.getOwner()) {
					        		channelInsuranceManager.getInsurance(entity.getOwner(), entity.getInsuranceAmount());
								}else {
									cxcConfirmOrderQueue.setBdProductprice(null);
									
								}
                                // 执行保存
					        	cxcConfirmOrderQueueDao.save(cxcConfirmOrderQueue);
							}else {
								log.error("----------------cmCode and bdShipmentno data anomalies----------------------");
							}
						}else {
							log.error("---------------- CxcConfirmOrderQueue already exists----------------------");
						}
					}else {
						log.error("---------------- trackingNo does not exist----------------------");
					}
				}
			} catch (Exception e) {
                log.error("----------系统异常----------------");
			}
		}	
    }

    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        // 匹配时基于订单是否COD判断获取不同类型的运单号(EMS COD与非COD 帐号不同)
        PackageInfo info = packageInfoDao.getByPrimaryKey(packId);
        StaDeliveryInfo sd = null;
        if (info != null) {
            sd = info.getStaDeliveryInfo();// 根据包裹获取物流信息
            if (sd != null) {
            	
            } else {
                throw new BusinessException(ErrorCode.ERROR_DELIINFO_ISNULL);
            }
        }
        Date date = new Date();
		SimpleDateFormat sFormat = new SimpleDateFormat("yyMMdd");
		String transNo_start = sFormat.format(date);
		String tempString = Long.toString(date.getTime());
		String transNo_end = tempString.substring(tempString.length() - 6,
				tempString.length());
		if (transNo_start.length() == 6 && transNo_end.length() == 6) {
			String transNo = transNo_start.concat(transNo_end);
            info.setTrackingNo(transNo);// 运到号更新到包裹信息
		}
        return sd;
    }

    @Override
    public PickingListPackage matchingTransNoByPackage(Long plId) {
        return null;
    }

    @Override
    public WmsInvoiceOrder invoiceOrderMatchingTransNo(Long wioId) {
        return null;
    }

    @Override
    public WmsInvoiceOrder matchingTransNoForInvoiceOrder(Long id) {
        return null;
    }
    
    @Override
    public void setRegistConfirmInvoiceOrder(WmsInvoiceOrder wio) {
        
    }
}
