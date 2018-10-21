package com.jumbo.wms.manager.warehouse.api;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.BiChannelSpecialActionDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.OperationUnitManager;
import com.jumbo.wms.model.BaseResponse;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.cond.WarehouseQueryCond;

@Transactional
@Service("apiWarehouseManager")
public class ApiWarehouseManagerImpl extends BaseManagerImpl implements ApiWarehouseManager {

    /**
     * 
     */
    private static final long serialVersionUID = -1098543767293246039L;

    @Autowired
    private OperationUnitManager operationUnitManager;

    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private BiChannelSpecialActionDao biChannelSpecialActionDao;

    @Override
    public WarehouseQueryCond findWhByOuCode(String channelCode, String ouCode) {
        // 根据ouCode查询组织节点
        OperationUnit whOu = operationUnitManager.getByCode(ouCode);
        if (null == whOu) {
            return constructionBaseResponse(BaseResponse.RESULT_ERROR, BaseResponse.ERROR_CODE_80005, "查无此组织");
        }

        // 根据whOu查询仓库信息
        WarehouseQueryCond whCond = warehouseDao.findWarehouseByOuId(whOu.getId());

        if (null == whCond) {
            return constructionBaseResponse(BaseResponse.RESULT_ERROR, BaseResponse.ERROR_CODE_80004, "查询反馈数据异常");
        }

        // WMS有存店铺对外打印面单时的字段
        if (StringUtils.hasText(channelCode)) {
            BiChannelSpecialAction sa = biChannelSpecialActionDao.getChannelActionByCodeAndType(channelCode, 30, new BeanPropertyRowMapperExt<BiChannelSpecialAction>(BiChannelSpecialAction.class));
            if (null != sa) {
                if (StringUtils.hasText(sa.getShopName())) whCond.setPic(sa.getShopName()); // 联系人
                if (StringUtils.hasText(sa.getContactsPhone())) whCond.setPicContact(sa.getContactsPhone()); // 联系人方式
                if (StringUtils.hasText(sa.getSendWarehouseName())) whCond.setAddress(sa.getSendWarehouseName()); // 发货仓名称
            }
        }

        return whCond;
    }

    /**
     * 构造返回对象
     * 
     * @param result
     * @param errorCode
     * @param message
     */
    private WarehouseQueryCond constructionBaseResponse(String result, String errorCode, String message) {
        WarehouseQueryCond cond = new WarehouseQueryCond();
        cond.setResult(result);
        cond.setErrorCode(errorCode);
        cond.setMessage(message);
        return cond;
    }
}
