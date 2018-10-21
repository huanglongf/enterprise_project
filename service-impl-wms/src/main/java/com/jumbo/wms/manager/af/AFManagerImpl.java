package com.jumbo.wms.manager.af;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.baozun.bh.connector.af.constants.ConstantsAf;
import cn.baozun.bh.connector.af.model.AnfBoh;
import cn.baozun.bh.connector.af.model.AnfBoh.Anf;
import cn.baozun.bh.connector.af.model.InventoryDatas;
import cn.baozun.bh.connector.af.model.InventoryDatas.InventoryData;
import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.util.JSONUtil;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.af.AFInventoryCompareDao;
import com.jumbo.dao.vmi.af.AFLFInventoryInfoDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InvLockDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.util.mq.MqMsgUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.af.AFLFInventoryInfo;
import com.jumbo.wms.model.vmi.af.AFinventoryCommand;
import com.jumbo.wms.model.vmi.af.InvLock;
import com.jumbo.wms.model.warehouse.InventoryStatus;

@Transactional
@Service("aFManager")
public class AFManagerImpl extends BaseManagerImpl implements AFManager {

    /**
     * 
     */
    private static final long serialVersionUID = -9026886131625511370L;

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Autowired
    private ChooseOptionDao chooseOptionDao;

    @Autowired
    private ExcelWriterManager excelWriterManager;

    @Autowired
    private AFLFInventoryInfoDao aFLFInventoryInfoDao;

    @Autowired
    private WareHouseManager whManager;

    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private OperationUnitDao ouDao;

    @Autowired
    private AFInventoryCompareDao aFInventoryCompareDao;

    @Autowired
    private BiChannelDao companyShopDao;

    @Autowired
    private WarehouseDao whDao;

    @Autowired
    private InvLockDao invLockDao;



    /**
     * MQ消息模板
     */
    private JmsTemplate bhMqJmsTemplate;

    @Resource
    private ApplicationContext applicationContext;

    @PostConstruct
    protected void init() {
        try {
            bhMqJmsTemplate = (JmsTemplate) applicationContext.getBean("bhJmsTemplate");
        } catch (Exception e) {
            log.error("no bean named bhMqJmsTemplate");
        }
    }


    public JmsTemplate getBhMqJmsTemplate() {
        return bhMqJmsTemplate;
    }

    public void setBhMqJmsTemplate(JmsTemplate bhMqJmsTemplate) {
        this.bhMqJmsTemplate = bhMqJmsTemplate;
    }


    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendAfInventoryMessage() {
        log.error("sendAfInventoryMessage");
        // 4085L 4104AF2
        Date date1 = new Date();
        InvLock in = new InvLock();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(date1);
        InvLock invLock = invLockDao.getOneInvLock(time, "ANF", new BeanPropertyRowMapperExt<InvLock>(InvLock.class));
        if (invLock == null) {
            in.setBrand(0);
            in.setSource("ANF");
            in.setTime(time);
            in.setCreateTime(new Date());


        List<AFinventoryCommand> list = inventoryDao.findAFInventoryReport(4085L, new BeanPropertyRowMapperExt<AFinventoryCommand>(AFinventoryCommand.class));

        InventoryDatas inventorys = new InventoryDatas();
        List<InventoryData> dataList = new ArrayList<InventoryData>();
        for (AFinventoryCommand command : list) {
            InventoryData data = new InventoryData();
            data.setStoreCode(command.getStoreCode());
            data.setCurrentDate(command.getCurrentDate());
            data.setUpc(command.getUpc());
            data.setOnHandUnits(command.getOnHandUnits());
            data.setDamaged(command.getDamaged());
            dataList.add(data);
        }
        inventorys.setInventorydatas(dataList);

        String msg = JSONUtil.beanToJson(inventorys);
        System.out.println(msg);
        ConnectorMessage connectorMessage = new ConnectorMessage();

        connectorMessage.setPlatformCode(ConstantsAf.BH_CONNECTOR_PLATFORM_CODE_AF);
        connectorMessage.setInterfaceCode(ConstantsAf.BH_CONNECTOR_INTERFACE_CODE_AF_INVENTORYDATA);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setMessageContent(msg);

        msg = JSONUtil.beanToJson(connectorMessage);
            log.error("sendAfInventoryMessage_1");
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "oms2hb_af.inventorydata", msg);
            log.error("sendAfInventoryMessage_2");
        in.setBrand(1);
        invLockDao.save(in);
            log.error("sendAfInventoryMessage_3");
        }
    }



    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendAfInventoryMessage2() {
        log.error("sendAfInventoryMessage2");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date2 = cal.getTime();
        // ////
        Date date1 = new Date();
        InvLock in = new InvLock();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(date1);
        InvLock invLock = invLockDao.getOneInvLock(time, "ANF", new BeanPropertyRowMapperExt<InvLock>(InvLock.class));
        if (invLock == null) {
            in.setBrand(0);
            in.setSource("ANF");
            in.setTime(time);
            in.setCreateTime(new Date());

            List<AFinventoryCommand> list = inventoryDao.findAFInventoryReport2(4085L, date2, new BeanPropertyRowMapperExt<AFinventoryCommand>(AFinventoryCommand.class));

        InventoryDatas inventorys = new InventoryDatas();
        List<InventoryData> dataList = new ArrayList<InventoryData>();
        for (AFinventoryCommand command : list) {
            InventoryData data = new InventoryData();
            data.setStoreCode(command.getStoreCode());
            data.setCurrentDate(command.getCurrentDate());
            data.setUpc(command.getUpc());
            data.setOnHandUnits(command.getOnHandUnits());
            data.setDamaged(command.getDamaged());
            dataList.add(data);
        }
        inventorys.setInventorydatas(dataList);

        String msg = JSONUtil.beanToJson(inventorys);
        System.out.println(msg);
        ConnectorMessage connectorMessage = new ConnectorMessage();

        connectorMessage.setPlatformCode(ConstantsAf.BH_CONNECTOR_PLATFORM_CODE_AF);
        connectorMessage.setInterfaceCode(ConstantsAf.BH_CONNECTOR_INTERFACE_CODE_AF_INVENTORYDATA);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setMessageContent(msg);

        msg = JSONUtil.beanToJson(connectorMessage);
            log.error("sendAfInventoryMessage2_1");
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "oms2hb_af.inventorydata", msg);
            log.error("sendAfInventoryMessage2_2");
        in.setBrand(1);
        invLockDao.save(in);
            log.error("sendAfInventoryMessage2_3");

        }
    }

    /**
     * 保存AF商品在利丰仓库的库存信息
     * 
     * @param message mq消息
     */
    public void saveAfLfInvInfo(String message) {
        log.info("=========SAVEAFLFINVINFO START===========");

        try {
            // 解析mq消息
            AnfBoh anfBoh = JSONUtil.jsonToBean(message, AnfBoh.class);
            if (anfBoh == null) {
                log.info("**AfLfInvInfo is null ");
                return;
            }
            // 从mq消息中,得到af商品库存信息
            List<Anf> anfList = anfBoh.getAnfs();
            for (Anf anf : anfList) {

                OperationUnit ou = null;
                Warehouse wh = null;

                InventoryStatus isForSaleTrue = null;
                InventoryStatus isForSaleFlase = null;


                if (wh == null) {
                    wh = whManager.getWareHouseByVmiSource(anf.getSrouce());
                }
                if (wh == null) {
                    log.debug("=========WAREHOUSE UNIT {} NOT FOUNT===========", new Object[] {anf.getSrouce()});
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
                Long ouId = wh.getOu().getId();
                if (ouId == null) {
                    log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {anf.getStorerKey()});
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
                ou = ouDao.getByPrimaryKey(ouId);
                if (ou == null) {
                    log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {anf.getStorerKey()});
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }

                if (isForSaleFlase == null) {
                    Long companyId = null;
                    if (ou.getParentUnit() != null) {
                        OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                        if (ou1 != null) {
                            companyId = ou1.getParentUnit().getId();
                        }
                    }
                    isForSaleTrue = inventoryStatusDao.findInvStatusisForSale(companyId, true);
                    isForSaleFlase = inventoryStatusDao.findInvStatusisForSale(companyId, false);
                }

                AFLFInventoryInfo aFLFInventoryInfo = new AFLFInventoryInfo();
                // Sku
                aFLFInventoryInfo.setSku(anf.getSku());
                // 店铺key
                aFLFInventoryInfo.setStorerKey(anf.getStorerKey());
                // 总库存量
                aFLFInventoryInfo.setTotalQty(Long.valueOf(anf.getTotalQty()));
                // 可用库存量
                aFLFInventoryInfo.setAvaiableQty(Long.valueOf(anf.getAvaiableQTY()));
                if (("Damage").equals(anf.getStatus())) {
                    // 状态
                    aFLFInventoryInfo.setStatus(isForSaleFlase.getId());
                } else if (("Normal").equals(anf.getStatus())) {
                    // 状态
                    aFLFInventoryInfo.setStatus(isForSaleTrue.getId());
                } else {
                    aFLFInventoryInfo.setStatus(null);
                }
                // 对比状态
                aFLFInventoryInfo.setCompareStatus("1");
                // 创建时间
                aFLFInventoryInfo.setCreateDate(new Date());

                // af商品在利丰仓库信息保存
                aFLFInventoryInfoDao.save(aFLFInventoryInfo);

                log.info("=========SAVEAFLFINVINFO SUCCESS===========");
            }

        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 发送AF库存对比Email
     */
    public void sendAfInvComReportMail(String vimCode, String vimSource, Long shopId) {

        log.debug("af sendAfInvComReportMail START TEST");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();// 取系统日期
        // 获取时间
        String date = sdf.format(c.getTime());

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date nowTime = calendar.getTime();
        insertAFLFInventoryInfoReportInfo(vimCode, vimSource, shopId);

        String localPath = "/home/appuser/wms/inventorycompare/";
        File dir = new File(localPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File invComFile = new File(localPath + vimCode + "_" + "库存对比_" + date + ".xls");
        try {
            // 判断是否存在
            if (!invComFile.exists()) {
                invComFile.createNewFile();
            }
            excelWriterManager.afInvComReport(new FileOutputStream(invComFile), vimSource);
        } catch (Exception e) {
            log.error("af inventorycompare excel create error", e.getMessage());
        }

        // 查询系统常量表 收件人
        ChooseOption option = chooseOptionDao.findByCategoryCode("AFINVCOM_EMAIL");
        if (!StringUtil.isEmpty(option.getOptionValue())) {
            // 传人邮件的CODE
            MailTemplate template = mailTemplateDao.findTemplateByCode("AFINVCOM_EMAIL");
            if (template != null) {
                String mailBody = template.getMailBody();// 邮件内容
                String subject = template.getSubject();// 标题

                String addressee = option.getOptionValue(); // 查询收件人

                try {
                    List<File> list = new ArrayList<File>();
                    list.add(invComFile);
                    if (MailUtil.sendMail(subject, addressee, "", mailBody, true, list)) {
                        aFInventoryCompareDao.updateAFInvComNorStatus("10", nowTime);
                    } else {
                        aFInventoryCompareDao.updateAFInvComNorStatus("-1", nowTime);
                    }

                } catch (Exception e) {
                    log.error("", e);
                }

            }
        }
    }



    public void insertAFLFInventoryInfoReportInfo(String vimCode, String vimSource, Long shopId) {

        log.info("=========AFLFInventoryInfoReport START===========");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date today = calendar.getTime();
        String starDate = FormatUtil.formatDate(today, "yyyy-MM-dd");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            today = df.parse(starDate);

        } catch (ParseException e) {
            log.error("", e);
        }
        try {
            String innerShopid = "";
            Long ouid = null;

            BiChannel companyShop = companyShopDao.getByPrimaryKey(shopId);
            if (companyShop != null) {
                innerShopid = companyShop.getCode();
            } else {
                log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {shopId});
                throw new Exception("没有找到店铺信息!shopId:" + shopId);
            }

            if (StringUtils.hasText(vimSource)) {
                Warehouse warehouse = whDao.findWarehouseByVmiSource(vimSource);
                if (warehouse != null) {
                    ouid = warehouse.getOu().getId();
                } else {
                    log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {vimSource});
                    throw new Exception("没有找到仓库信息!VMISOURCE:" + vimSource);
                }
            }

            log.info("=========AFLFInventoryInfoReport INSERT===========");
            aFLFInventoryInfoDao.insertAFLFInventoryInfoReport(today, vimCode, ouid, innerShopid);
            log.info("=========AFLFInventoryInfoReport END===========");

        } catch (Exception e) {
            log.error("", e);
        }


    }

}
