package com.jumbo.wms.manager.archive;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.archive.ConfirmOrderArchiveDao;
import com.jumbo.dao.archive.ShippingArchiveDao;
import com.jumbo.dao.archive.ShippingLineArchiveDao;
import com.jumbo.dao.archive.WmsInfoLogOmsArchiveDao;
import com.jumbo.dao.archive.WmsOrderInvoiceOmsArchiveDao;
import com.jumbo.dao.archive.WmsOrderStatusOmsArchiveDao;
import com.jumbo.dao.archive.wmsOrderInvoiceLineOmsArchiveDao;
import com.jumbo.dao.archive.wmsTransInfoOmsArchiveDao;
import com.jumbo.dao.hub2wms.WmsConfirmOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsInfoLogOmsDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceLineOmsDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceOmsDao;
import com.jumbo.dao.hub2wms.WmsOrderStatusOmsDao;
import com.jumbo.dao.hub2wms.WmsShippingLineQueueDao;
import com.jumbo.dao.hub2wms.WmsShippingQueueDao;
import com.jumbo.dao.hub2wms.WmsTransInfoOmsDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.archive.ConfirmOrderArchive;
import com.jumbo.wms.model.archive.ShippingArchive;
import com.jumbo.wms.model.archive.ShippingLineArchive;
import com.jumbo.wms.model.archive.WmsOrderInvoiceOmsArchive;
import com.jumbo.wms.model.archive.WmsOrderStatusOmsArchive;
import com.jumbo.wms.model.archive.WmsTransInfoOmsArchive;
import com.jumbo.wms.model.archive.wmsInfoLogOmsArchive;
import com.jumbo.wms.model.archive.wmsOrderInvoiceLineOmsArchive;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsInfoLogOms;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLineOms;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceOms;
import com.jumbo.wms.model.hub2wms.WmsOrderStatusOms;
import com.jumbo.wms.model.hub2wms.WmsShippingLineQueue;
import com.jumbo.wms.model.hub2wms.WmsShippingQueue;
import com.jumbo.wms.model.hub2wms.WmsTransInfoOms;

@Transactional
@Service("archiveManager")
public class ArchiveManagerImpl extends BaseManagerImpl implements ArchiveManager {
    /**
     * 
     */
    private static final long serialVersionUID = -2971114726337986481L;
    @Autowired
    WmsConfirmOrderQueueDao confirmOrderQueueDao;
    @Autowired
    WmsShippingQueueDao shippingQueueDao;
    @Autowired
    WmsShippingLineQueueDao lineQueueDao;
    @Autowired
    ShippingArchiveDao shippingArchiveDao;
    @Autowired
    ShippingLineArchiveDao shippingLineArchiveDao;
    @Autowired
    ConfirmOrderArchiveDao confirmOrderArchiveDao;
    @Autowired
    WmsOrderStatusOmsDao wmsOrderStatusOmsDao;
    @Autowired
    WmsTransInfoOmsDao wmsTransInfoOmsDao;
    @Autowired
    wmsTransInfoOmsArchiveDao wmsTransInfoOmsArchiveDao;
    @Autowired
    WmsInfoLogOmsDao infoLogOmsDao;
    @Autowired
    WmsInfoLogOmsArchiveDao wmsInfoLogOmsArchiveDao;
    @Autowired
    WmsOrderInvoiceOmsDao wmsOrderInvoiceOmsDao;
    @Autowired
    WmsOrderInvoiceOmsArchiveDao wmsOrderInvoiceOmsArchiveDao;
    @Autowired
    WmsOrderInvoiceLineOmsDao wmsOrderInvoiceLineOmsDao;
    @Autowired
    wmsOrderInvoiceLineOmsArchiveDao wmsOrderInvoiceLineOmsArchiveDao;
    @Autowired
    WmsOrderStatusOmsArchiveDao statusOmsArchiveDao;


    @Override
    public void createConfirmOrder(int sum) {
        List<WmsConfirmOrderQueue> confirmOrderQueue = confirmOrderQueueDao.getlistWmsConfirmOrderQueue(sum, new BeanPropertyRowMapper<WmsConfirmOrderQueue>(WmsConfirmOrderQueue.class));
        for (WmsConfirmOrderQueue wmsConfirmOrderQueue : confirmOrderQueue) {
            List<WmsShippingQueue> sl = shippingQueueDao.findAllShippingQueueById(wmsConfirmOrderQueue.getId(), new BeanPropertyRowMapper<WmsShippingQueue>(WmsShippingQueue.class));
            for (WmsShippingQueue wmsShipping : sl) {
                List<WmsShippingLineQueue> sll = lineQueueDao.findAllShippingLineQueueById(wmsShipping.getId(), new BeanPropertyRowMapper<WmsShippingLineQueue>(WmsShippingLineQueue.class));
                for (WmsShippingLineQueue wmsShippingLine : sll) {
                    ShippingLineArchive lineArchive = new ShippingLineArchive();
                    lineArchive.setId(wmsShippingLine.getId());
                    lineArchive.setLineNo(wmsShippingLine.getLineNo());
                    lineArchive.setQty(wmsShippingLine.getQty());
                    lineArchive.setShippingQueue(wmsShipping.getId());
                    lineArchive.setSku(wmsShippingLine.getSku());
                    shippingLineArchiveDao.save(lineArchive);
                }
                lineQueueDao.deleteAll(sll);
                ShippingArchive shippingArchive = new ShippingArchive();
                shippingArchive.setId(wmsShipping.getId());
                shippingArchive.setQueue(wmsConfirmOrderQueue.getId());
                shippingArchive.setShippingCode(wmsShipping.getTransCode());
                shippingArchive.setTransCode(wmsShipping.getTransCode());
                shippingArchive.setWhCode(wmsShipping.getWhCode());
                shippingArchiveDao.save(shippingArchive);
            }
            shippingQueueDao.deleteAll(sl);
            ConfirmOrderArchive confirmOrderArchive = new ConfirmOrderArchive();
            BeanUtils.copyProperties(wmsConfirmOrderQueue, confirmOrderArchive);
            confirmOrderArchiveDao.save(confirmOrderArchive);
        }
        confirmOrderQueueDao.deleteAll(confirmOrderQueue);

    }

    @Override
    public void createOutOrder(int sum) {
        List<WmsOrderStatusOms> orderStatusOms = wmsOrderStatusOmsDao.wmsOutOrderConfirmPac(sum, new BeanPropertyRowMapper<WmsOrderStatusOms>(WmsOrderStatusOms.class));
        for (WmsOrderStatusOms wmsOrderStatusOms : orderStatusOms) {
            List<WmsTransInfoOms> wmsTransInfoOms = wmsTransInfoOmsDao.findOrderId(wmsOrderStatusOms.getId(), new BeanPropertyRowMapper<WmsTransInfoOms>(WmsTransInfoOms.class));
            for (WmsTransInfoOms wmsTransInfoOms2 : wmsTransInfoOms) {
                WmsTransInfoOmsArchive wmsTransInfoOmsArchive = new WmsTransInfoOmsArchive();
                BeanUtils.copyProperties(wmsTransInfoOms2, wmsTransInfoOmsArchive);
                wmsTransInfoOmsArchive.setOrderStatusOms(wmsOrderStatusOms.getId());
                wmsTransInfoOmsArchiveDao.save(wmsTransInfoOmsArchive);
            }
            wmsTransInfoOmsDao.deleteAll(wmsTransInfoOms);
            List<WmsInfoLogOms> infoLogOms = infoLogOmsDao.queryInfoLog(wmsOrderStatusOms.getId(), new BeanPropertyRowMapper<WmsInfoLogOms>(WmsInfoLogOms.class));
            for (WmsInfoLogOms wmsInfoLogOms : infoLogOms) {
                wmsInfoLogOmsArchive wmsInfoLogOmsArchive = new wmsInfoLogOmsArchive();
                BeanUtils.copyProperties(wmsInfoLogOms, wmsInfoLogOmsArchive);
                wmsInfoLogOmsArchive.setWmsOrderStatusOms(wmsOrderStatusOms.getId());
                wmsInfoLogOmsArchiveDao.save(wmsInfoLogOmsArchive);
            }
            infoLogOmsDao.deleteAll(infoLogOms);
            List<WmsOrderInvoiceOms> invoiceOms = wmsOrderInvoiceOmsDao.findOrderId(wmsOrderStatusOms.getId(), new BeanPropertyRowMapper<WmsOrderInvoiceOms>(WmsOrderInvoiceOms.class));
            for (WmsOrderInvoiceOms wmsOrderInvoiceOms : invoiceOms) {
                WmsOrderInvoiceOmsArchive wmsOrderInvoiceOmsArchive = new WmsOrderInvoiceOmsArchive();
                BeanUtils.copyProperties(wmsOrderInvoiceOms, wmsOrderInvoiceOmsArchive);
                wmsOrderInvoiceOmsArchive.setOrderStatusOms(wmsOrderStatusOms.getId());
                wmsOrderInvoiceOmsArchiveDao.save(wmsOrderInvoiceOmsArchive);
                List<WmsOrderInvoiceLineOms> invoiceLineOms = wmsOrderInvoiceLineOmsDao.findInvoiceId(wmsOrderInvoiceOms.getId(), new BeanPropertyRowMapper<WmsOrderInvoiceLineOms>(WmsOrderInvoiceLineOms.class));
                for (WmsOrderInvoiceLineOms wmsOrderInvoiceLineOms : invoiceLineOms) {
                    wmsOrderInvoiceLineOmsArchive wmsOrderInvoiceLineOmsArchive = new wmsOrderInvoiceLineOmsArchive();
                    BeanUtils.copyProperties(wmsOrderInvoiceLineOms, wmsOrderInvoiceLineOmsArchive);
                    wmsOrderInvoiceLineOmsArchive.setInvoiceId(wmsOrderInvoiceOms.getId());
                    wmsOrderInvoiceLineOmsArchiveDao.save(wmsOrderInvoiceLineOmsArchive);
                }
                wmsOrderInvoiceLineOmsDao.deleteAll(invoiceLineOms);
            }
            wmsOrderInvoiceOmsDao.deleteAll(invoiceOms);
            WmsOrderStatusOmsArchive archive = new WmsOrderStatusOmsArchive();
            BeanUtils.copyProperties(wmsOrderStatusOms, archive);
            statusOmsArchiveDao.save(archive);
        }
        wmsOrderStatusOmsDao.deleteAll(orderStatusOms);
    }

}
