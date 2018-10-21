package com.jumbo.wms.manager.warehouse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.dao.warehouse.WmsInvoiceOrderDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

/**
 * 补寄发票业务逻辑实现
 * 
 * @author jinlong.ke
 * 
 */
@Service("fillInInvoiceManagerProxy")
public class FillInInvoiceManagerProxyImpl extends BaseManagerImpl implements FillInInvoiceManagerProxy {

    /**
     * 
     */
    private static final long serialVersionUID = 6627784040632218770L;
    @Autowired
    private WmsInvoiceOrderDao wmsInvoiceOrderDao;
    @Autowired
    private FillInInvoiceManager fillInInvoiceManager;

    /**
     * 1、判断是否统一物流商<br/>
     * 2、判断是否统一店铺<br/>
     * 3、没有物流商设置物流商，生成单号，有单号跳过<br/>
     * 4、有批次校验批次，没有批次，生成批次
     */
    @Override
    public String preExportInvoice(List<Long> wioIdlist) {
        isSameLpCodeAndOwner(wioIdlist);
        setLpCodeAndTransNo(wioIdlist);
        String s = createBatchCode(wioIdlist);
        return s;

    }

    private String createBatchCode(List<Long> wioIdlist) {
        String batchCode = null;
        if (wioIdlist.size() == 0) {
            // 本次选择订单系统暂无批次生成，可能由于快递单号无法匹配都已转物流，请重新选择!
            throw new BusinessException(ErrorCode.FILL_IN_INVOICE_NO_ORDER);
        } else {
            batchCode = fillInInvoiceManager.setBatchCodeForList(wioIdlist);
        }
        return batchCode;
    }

    /**
     * 判断是否是同一物流商和渠道
     * 
     * @param wioIdlist
     */
    private void isSameLpCodeAndOwner(List<Long> wioIdlist) {
        String lpCode = null;
        String owner = null;
        for (Long id : wioIdlist) {
            WmsInvoiceOrder wo = wmsInvoiceOrderDao.getByPrimaryKey(id);
            if (null == lpCode) {
                lpCode = wo.getLpCode();
            } else {
                if (!lpCode.equals(wo.getLpCode())) {
                    // 所选单据必须属于同一物流商
                    throw new BusinessException(ErrorCode.FILL_IN_INVOICE_ORDER_MUST_SAME_LPCODE);
                }
            }
            if (null == owner) {
                owner = wo.getOwner();
            } else {
                if (!owner.equals(wo.getOwner())) {
                    // 所选单据必须属于同一渠道
                    throw new BusinessException(ErrorCode.FILL_IN_INVOICE_ORDER_MUST_SAME_CHANNEL);
                }
            }
        }

    }

    /**
     * 设置运单号，如果出现物流不可达，非EMS转EMS
     * 
     * @param wioIdlist
     */
    private void setLpCodeAndTransNo(List<Long> wioIdlist) {
        BusinessException root = new BusinessException();
        for (int i = 0; i < wioIdlist.size(); i++) {
            try {
                fillInInvoiceManager.setLpCodeAndTransNo(wioIdlist.get(i));
            } catch (Exception e) {
                WmsInvoiceOrder order = wmsInvoiceOrderDao.getByPrimaryKey(wioIdlist.get(i));
                if (e instanceof BusinessException) {
                    BusinessException be = (BusinessException) e;
                    if (be.getErrorCode() == ErrorCode.SF_INTERFACE_ERROR || be.getErrorCode() == ErrorCode.YTO_INTERFACE_ERROR || ErrorCode.PROVIDER_NO_TRANS_OR_ERROR == be.getErrorCode()) {
                        // 单据{0}匹配运单号时，接口无法调用或者可用单号不足！
                        BusinessException linkException = new BusinessException(ErrorCode.FILL_IN_INVOICE_INTERFACE_ERROR, new Object[] {order.getOrderCode()});
                        root.setLinkedException(linkException);
                        root = linkException;
                    } else if (be.getErrorCode() == ErrorCode.TRANS_CAN_NOT_SEND) {
                        // 顺丰无法送达，发票快递转EMS;
                        order.setLpCode(Transportator.EMS);
                        wioIdlist.remove(i);
                        i--;
                    } else {
                        // 单据{0}匹配运单号时，系统发生异常
                        BusinessException linkException = new BusinessException(ErrorCode.FILL_IN_INVOICE_SYSTEM_ERROR, new Object[] {order.getOrderCode()});
                        root.setLinkedException(linkException);
                        root = linkException;
                    }
                } else {
                    BusinessException linkException = new BusinessException(ErrorCode.FILL_IN_INVOICE_SYSTEM_ERROR, new Object[] {order.getOrderCode()});
                    root.setLinkedException(linkException);
                    root = linkException;
                }
            }
        }
        if (root.getLinkedException() != null) {
            throw root;
        }
    }
}
