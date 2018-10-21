package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.util.JSONUtil;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.RfdConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.webservice.rfd.RfdCreateOrderDetails;
import com.jumbo.webservice.rfd.RfdCreateOrderRequest;
import com.jumbo.webservice.rfd.RfdCreateOrderResDetail;
import com.jumbo.webservice.rfd.RfdCreateOrderResponse;
import com.jumbo.webservice.rfd.RfdOrderClient;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.RfdConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;
@Service("transOlRfd")
@Transactional
public class TransOlRfd extends BaseManagerImpl implements TransOlInterface {

	private static final long serialVersionUID = -7278982323594576191L;
	
	@Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private RfdConfirmOrderQueueDao rfdConfirmOrderQueueDao;
	
    /**
     * 线下包裹匹配运单号
     */
	@Override
	public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
		return null;
	}
	
    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) throws Exception {
        return null;
    }

	/**
     * 匹配运单号
     */
	@Override
	public StaDeliveryInfo matchingTransNo(Long staId) throws Exception {
		StockTransApplication sta = staDao.getByPrimaryKey(staId);
		List<StaLine> line = staLineDao.findByStaId(staId);
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        // 如果之前调用成功，记录快递单号则直接返回
        if (StringUtils.hasText(sd.getTrackingNo()) && StringUtils.hasText(sd.getExtTransOrderId()) && !sta.getIsMerge()) {
            return sd;
        }
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        // 推送订单传输实体
        List<RfdCreateOrderRequest> orders = new ArrayList<RfdCreateOrderRequest>();
        RfdCreateOrderRequest order = getRequest(sta, sd, wh, shop, line, null);
        orders.add(order);
        // 获取运单号
        String returnJson = RfdOrderClient.sendOrderToRfd(orders, RFD_CREATEORDER_URL, RFD_IDENTITY, RFD_SECRET);
        RfdCreateOrderResponse response = JSONUtil.jsonToBean(returnJson, RfdCreateOrderResponse.class);
        if (response == null || response.getIsSuccess() == null) {
            throw new BusinessException(ErrorCode.RFD_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
        } else if (response.getIsSuccess()) {
        	List<RfdCreateOrderResDetail> res = response.getResultData();
            if (res != null) {
            	RfdCreateOrderResDetail d = res.get(0);
            	if (d != null) {
            		sd.setTrackingNo(d.getWaybillNo().toString());
					sd.setExtTransOrderId(d.getFormCode());
                    sd.setTransCityCode(d.getStationId() == null ? null : d.getStationId().toString()); // 站点编号
                	sd.setTransBigWord(d.getStationName());
				} else {
	                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getMessage()});
	            }
            } else {
                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getMessage()});
            }
        } else {
            throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getMessage()});
        }
        return sd;
	}
	
	    /**
     * 拆包裹
     */
	@Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        // 匹配时基于订单是否COD判断获取不同类型的运单号(EMS COD与非COD 帐号不同)
        PackageInfo info = packageInfoDao.getByPrimaryKey(packId);
        StaDeliveryInfo sd = null;
        StockTransApplication sta = null;
        if (info != null) {
            sd = info.getStaDeliveryInfo();// 根据包裹获取物流信息
            if (sd != null) {
                sta = staDao.getByPrimaryKey(sd.getId());// 根据物流信息获取作业单
            } else {
            	throw new BusinessException(ErrorCode.ERROR_DELIINFO_ISNULL);
            }
        }
        sd.setExtTransOrderId(sta.getCode());
        String transNo = matchingTransNoByStaId(sd.getId());
        if (StringUtils.hasText(transNo)) {
            info.setTrackingNo(transNo);// 运单号更新到包裹信息
        }
        return sd;
	}
	
	private String matchingTransNoByStaId(Long staId) {
		StockTransApplication sta = staDao.getByPrimaryKey(staId);
		List<StaLine> line = staLineDao.findByStaId(staId);
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        Long packCount = packageInfoDao.findUnCheckedPackageBySta(staId, new SingleColumnRowMapper<Long>(Long.class));
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        // 创建传输实体
        List<RfdCreateOrderRequest> orders = new ArrayList<RfdCreateOrderRequest>();
        RfdCreateOrderRequest order = getRequest(sta, sd, wh, shop, line, packCount);
        orders.add(order);
        String transNo = null;
        // 获得运单号
        String returnJson = RfdOrderClient.sendOrderToRfd(orders, RFD_CREATEORDER_URL, RFD_IDENTITY, RFD_SECRET);
        RfdCreateOrderResponse response = JSONUtil.jsonToBean(returnJson, RfdCreateOrderResponse.class);
        if (response == null || response.getIsSuccess() == null) {
            throw new BusinessException(ErrorCode.RFD_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
        } else if (response.getIsSuccess()) {
        	List<RfdCreateOrderResDetail> res = response.getResultData();
            if (res != null) {
            	RfdCreateOrderResDetail d = res.get(0);
            	if (d != null && !"03".equals(d.getResultCode())) {
            		transNo = d.getWaybillNo().toString();
				} else {
	                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), d.getResultMessage()});
	            }
            } else {
                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getMessage()});
            }
        } else {
            throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getMessage()});
        }
		return transNo;
	}
	
	private RfdCreateOrderRequest getRequest(StockTransApplication sta, StaDeliveryInfo sd, Warehouse wh, BiChannel shop, List<StaLine> line, Long packInfoCount) {
		RfdCreateOrderRequest order = new RfdCreateOrderRequest();
        order.setMerchantCode("rfd"); // 商家编号 如风达提供固定值
        order.setFormCode(sta.getCode());
        if (packInfoCount != null) {
        	order.setDeliverCode(String.valueOf((packInfoCount + 1L)));
		}
        order.setToName(sd.getReceiver());
        order.setToProvince(sd.getProvince());
        order.setToCity(sd.getCity());
        order.setToArea(sd.getDistrict());
        order.setToAddress(sd.getAddress());
        order.setToPostCode(sd.getZipcode());
        if (StringUtils.hasText(sd.getMobile())) {
        	order.setToMobile(sd.getMobile());
        } else if (StringUtils.hasText(sd.getTelephone())) {
        	order.setToMobile(sd.getTelephone());
        }
        order.setToPhone(sd.getTelephone());
        order.setOrderType(0);
        order.setTotalAmount(sta.getOrderTotalActual());
        order.setPaidAmount(sta.getTotalActual());
        order.setReceiveAmount(sta.getTotalActual());
        order.setRefundAmount(new BigDecimal(0));
        order.setInsureAmount(sd.getInsuranceAmount());
        order.setWeight(sd.getWeight());
        order.setFromName(wh.getPic());
        order.setFromAddress(wh.getAddress());
        order.setFromProvince(wh.getProvince());
        order.setFromCity(wh.getCity());
        order.setFromArea(wh.getDistrict());
        order.setFromPostCode(wh.getZipcode());
        order.setFromMobile(wh.getPhone());
        order.setFromPhone(shop.getTelephone());
        order.setGoodsProperty(0);
        order.setPackageCount(1);
        order.setDistributionCode("rfd");
        order.setCashType((sd.getIsCodPos() != null && sd.getIsCodPos()) ? 2 : 1);
        List<RfdCreateOrderDetails> orderDetails = new ArrayList<RfdCreateOrderDetails>();
        if (line != null && line.size() > 0) {
			for (StaLine staLine : line) {
				Sku sku = staLine.getSku();
				RfdCreateOrderDetails detail = new RfdCreateOrderDetails();
		        detail.setProductName(sku.getName());
		        detail.setCount(staLine.getQuantity().intValue());
                detail.setUnit("件");
		        detail.setSellPrice(staLine.getTotalActual());
		        detail.setProductCode(sku.getBarCode());
		        orderDetails.add(detail);
			}
		}
        order.setOrderDetails(orderDetails);
		return order;
	}
	
	@Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        // 合并订单传主订单
        if (null != sta.getGroupSta() && (null == sta.getIsMerge() || !sta.getIsMerge())) {
            sta = staDao.getByPrimaryKey(sta.getGroupSta().getId());
        }
        List<PackageInfo> piList = packageInfoDao.findByStaId(sta.getId());
        if (piList == null || piList.size() == 0) {
            return;
        }
        List<StaAdditionalLine> list = staAdditionalLineDao.findByStaId(sta.getId());
        if (list == null) {
            list = new ArrayList<StaAdditionalLine>();
        }
        for (PackageInfo pi : piList) {
        	RfdConfirmOrderQueue queue = rfdConfirmOrderQueueDao.findRfdQueueByStaCodeAndTransNo(sta.getCode(), pi.getTrackingNo(), new BeanPropertyRowMapper<RfdConfirmOrderQueue>(RfdConfirmOrderQueue.class));
        	if (queue == null) {
        		RfdConfirmOrderQueue q = new RfdConfirmOrderQueue();
                q.setCreateTime(new Date());
                q.setExeCount(0L);
                q.setStaCode(sta.getCode());
                q.setTransNo(pi.getTrackingNo());
                BigDecimal weight = sta.getStaDeliveryInfo().getWeight();
                DecimalFormat df = new DecimalFormat("0.00");
                q.setWeight(df.format(weight));
                boolean isNotInfo = true;
                if (list.size() > 0) {
                    for (StaAdditionalLine l : list) {
                        if (l.getSku() != null && l.getSku().getLength() != null) {
                            q.setHeight(df.format(l.getSku().getHeight().divide(new BigDecimal(10))));
                            q.setLength(df.format(l.getSku().getLength().divide(new BigDecimal(10))));
                            q.setWhight(df.format(l.getSku().getWidth().divide(new BigDecimal(10))));
                            isNotInfo = false;
                            break;
                        }
                    }
                }
                if (isNotInfo) {
                    q.setHeight("0");
                    q.setLength("0");
                    q.setWhight("0");
                }
                rfdConfirmOrderQueueDao.save(q);
			}
        }
	}

	@Override
	public void setRegistConfirmInvoiceOrder(WmsInvoiceOrder wio) {

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

}
