/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.manager.vmi.Default;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.vmi.defaultData.VmiAsnLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;

/**
 * Markwins品牌定制逻辑
 * 
 * @author jumbo
 * 
 */
@Service("vmiDefaultMarkwins")
public class VmiDefaultMARKWINS extends BaseVmiDefault {

    @Autowired
    private VmiAsnLineDao vmiAsnLineDao;
    @Autowired
    private StaLineDao staLineDao;

    @Override
    public void generateInboundStaSetHeadDefault(String slipCode, StockTransApplication head) {}

    @Override
    public void generateInboundStaSetSlipCodeOwner(BiChannelCommand shop, StockTransApplication sta) {}

    @Override
    public void generateReceivingWhenShelv(ExtParam ext, VmiRsnLineDefault v) {}

    // @Override
    // public void generateReceivingWhenClose(Long staid) {}

    @Override
    public void generateSaveSkuBatch(String batchCode, StockTransApplication sta, List<StvLine> stvlineList) {}

    @Override
    public void transferOmsOutBound(OperationBillLine line, StvLineCommand sc) {}

    /**
     * 添加过期时间信息
     */
    @Override
    public void generateVmiInboundStaLine(StockTransApplication sta, StaLine line, String upc) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        VmiAsnLineCommand v = vmiAsnLineDao.findVmiAsnLineByOrdercodeCartonnoUpc(sta.getSlipCode1(), sta.getRefSlipCode(), upc, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
        if (v != null) {
            if (!StringUtil.isEmpty(v.getExtMemo())) {
                line.setExpireDate(sdf.parse(v.getExtMemo()));
            }
        }
    }

    @Override
    public void generateInsertVmiInventory(ExtParam ext) {}

    @Override
    public boolean importForBatchReceiving() {
        return true;
    }

    /**
     * 模板批量收货品牌定制 设置过期时间到stvline importForBatchReceiving()方法返回为true时需要配置
     */
    @Override
    public void importForBatchReceivingSaveStvLine(StvLine stvLine, StockTransApplication sta, Sku sku) {
        StaLine staLine = staLineDao.findStaLineBySkuId(sku.getId(), sta.getId());
        stvLine.setValidDate(sku.getValidDate());
        stvLine.setExpireDate(staLine.getExpireDate());
    }

    @Override
    public void generateVmiOutBound(ExtParam ext) {}

    @Override
    public void generateVmiInBound(ExtParam ext) {}

    @Override
    public List<InventoryCommand> findVmiInventoryByOwner(List<String> ownerList) {
        return null;
    }

    /**
     * 格式化时间
     */
    @SuppressWarnings("unused")
    private String getFormateDateToData(String s, Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(s);
        String date = sdf.format(d);
        return date;
    }
}
