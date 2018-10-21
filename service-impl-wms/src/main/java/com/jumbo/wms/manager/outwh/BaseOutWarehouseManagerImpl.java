package com.jumbo.wms.manager.outwh;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.MsgInvoiceDao;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgInvoice;

@Transactional
@Service("baseOutWarehouseManager")
public class BaseOutWarehouseManagerImpl implements BaseOutWarehouseManager {

    @Autowired
    private MsgInvoiceDao msgInvoiceDao;
    @Autowired
    private WarehouseDao warehouseDao;

    /**
	 * 
	 */
    private static final long serialVersionUID = 253270674313046944L;

    /**
     * 查询待发送发票
     * 
     * @param source
     * @param sourceWh
     * @return
     */
    public List<MsgInvoice> findInvoicesBySource(String source, String sourceWh) {
        List<MsgInvoice> list = msgInvoiceDao.findBySource(source, sourceWh);
        if (list == null) {
            list = new ArrayList<MsgInvoice>();
        }
        return list;
    }

    /**
     * 根据Sta 归类发票
     * 
     * @param source
     * @param sourceWh
     * @return
     */
    public Map<String, List<MsgInvoice>> findInvoicesBySourceGroup(String source, String sourceWh) {
        List<MsgInvoice> list = findInvoicesBySource(source, sourceWh);
        Map<String, List<MsgInvoice>> map = new HashMap<String, List<MsgInvoice>>();
        for (MsgInvoice inc : list) {
            String staCode = inc.getStaCode();
            List<MsgInvoice> tmpList = map.get(staCode);
            if (tmpList == null) {
                tmpList = new ArrayList<MsgInvoice>();
                tmpList.add(inc);
                map.put(staCode, tmpList);
            } else {
                tmpList.add(inc);
            }
        }
        return map;
    }

    public void updateMsgInvoiceStatus(Long msgId, DefaultStatus status) {
        MsgInvoice msg = msgInvoiceDao.getByPrimaryKey(msgId);
        if (msg != null) {
            msg.setUpdateTime(new Date());
            msg.setStatus(status);
        }
    }

    public List<Warehouse> findOutWarehousesBySource(String source) {
        List<Warehouse> whs = warehouseDao.getAllBySource(source);
        return whs;
    }


}
