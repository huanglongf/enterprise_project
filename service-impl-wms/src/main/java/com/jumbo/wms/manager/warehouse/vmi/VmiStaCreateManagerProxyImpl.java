package com.jumbo.wms.manager.warehouse.vmi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.BiChannelCommand;


/**
 * VMI作业单创建
 * 
 * @author sjk
 * 
 */
@Service("vmiStaCreateManagerProxy")
public class VmiStaCreateManagerProxyImpl extends BaseManagerImpl implements VmiStaCreateManagerProxy {

    private static final long serialVersionUID = -7480647802020329234L;

    @Autowired
    private VmiStaCreateManager vmiStaCreateManager;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private VmiFactory vmiFactory;

    /**
     * VMI 品牌 创建入库作业单
     * 
     * @param vmiCode
     */
    @Override
    public void generateVmiInboundStaByVmiCode(String vmiCode) {
        BiChannel shop = companyShopDao.getByVmiCode(vmiCode);
        if (shop == null) {
            log.error("shop not found by vmiCode [{}]", vmiCode);
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        VmiInterface vmiBrand = vmiFactory.getBrandVmi(shop.getVmiCode());
        if (vmiBrand != null) {
            List<String> orders = vmiBrand.generateInboundStaGetAllOrderCode();
            for (String orderCode : orders) {
                try {
                    vmiStaCreateManager.generateVmiInboundStaBySlipCode(vmiCode, orderCode);
                } catch (BusinessException e) {
                    log.warn("generate vmi invound sta error : error code is [{}],vmi code : {},order code : {}", e.getErrorCode(),vmiCode,orderCode);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    @Override
    public void generateVmiInboundStaByVmiCodeDefault(String vmiCode, String vmiSource) {
        BiChannelCommand shop = companyShopDao.findVmiDefaultTbiChannel(vmiCode, vmiSource, null, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        if (shop == null) {
            log.error("shop not found by vmiCode [{}]", vmiCode);
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        VmiInterface vmiBrand = vmiFactory.getBrandVmi(shop.getVmiCode());
        if (vmiBrand != null) {
            List<String> orders = vmiBrand.generateInboundStaGetAllOrderCodeDefault(vmiCode, vmiSource, shop.getAsnTypeString());
            for (String orderCode : orders) {
                try {
                    vmiStaCreateManager.generateVmiInboundStaBySlipCodeDefault(vmiCode, vmiSource, orderCode, shop.getAsnTypeString());
                } catch (BusinessException e) {
                    log.error("generate vmi invound sta error : error code is [{}]", e.getErrorCode());
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }

    }
}
