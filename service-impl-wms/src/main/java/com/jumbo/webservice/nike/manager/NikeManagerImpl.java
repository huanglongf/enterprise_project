/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
package com.jumbo.webservice.nike.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.vmi.nikeDate.NikeCheckReceiveDao;
import com.jumbo.dao.vmi.nikeDate.NikeReturnReceiveDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockInDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockReceiveDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockReceiveDataDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.vmi.nikeData.NikeCheckReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeReturnReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeStockReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeStockReceiveData;
import com.jumbo.wms.model.vmi.nikeData.NikeStockReceiveDto;
import com.jumbo.wms.model.vmi.nikeData.NikeVmiStockInCommand;
import com.jumbo.wms.model.vmi.nikeData.NikeVmiStockInHub;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("nikeManager")
public class NikeManagerImpl extends BaseManagerImpl implements NikeManager {

    private static final long serialVersionUID = 8085693229996688963L;

    protected static final Logger log = LoggerFactory.getLogger(NikeManagerImpl.class);

    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    NikeStockInDao nikeStockInDao;
    @Autowired
    private NikeStockReceiveDao nikeStockReceiveDao;
    @Autowired
    private NikeStockReceiveDataDao nikeStockReceiveDataDao;
    @Autowired
    private NikeCheckReceiveDao nikeCheckReceiveDao;
    @Autowired
    private NikeReturnReceiveDao nikeReturnReceiveDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    
    @Autowired
    private RocketMQProducerServer producerServer;
    
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    private static String blank = "	";

    private static String NIKE_FILE_POSTFIX = ".dat";
    // 入库反馈、转店入库反馈 写文件 文件名前缀
    // private static String NIKE_STOCK_RECEIVE_FILE_PREFIX = "WEB_tfxrec_";
    private static String NIKE_STOCK_RECEIVE_SFRP_FILE_PREFIX = "WEB_rec_";
    // 转店退仓反馈 - WEB_tfx_ type = 2
    private static String NIKE_TRANSFER_OUT_RECEIVE_FILE_PREFIX = "WEB_tfx_";
    // 退大仓反馈 - WEB_rts_ type = 3
    private static String NIKE_RETURN_RECEIVE_FILE_PREFIX = "WEB_rts_";
    // 退大仓反馈 - WEB_rts_ type = 4
    private static String NIKE_INV_CHECK_RECEIVE_FILE_PREFIX = "WEB_adj_";

    public void generateRsnData() {
        // 更新中间表状态为待处理
        int rs = nikeStockReceiveDataDao.updateToDoing();
        if (rs > 0) {
            // 将待处理状态数据插入反馈表，如果计划量=执行量 PO CLOSE
            // 查找所有反馈的PO的相关STA
            List<StockTransApplicationCommand> stas = staDao.findNikeAsnStaByRsnPo(new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class));
            Map<String, Boolean> pos = new HashMap<String, Boolean>(); // 未完成的PO单号
            if (stas != null) {
                for (StockTransApplicationCommand statmp : stas) {
                    // 作业单没有创建成功
                    if (statmp.getId() == null) {
                        pos.put(statmp.getMemo(), false);
                        continue;
                    }
                    // 计算是否均完成
                    StockTransApplication sta = staDao.getByPrimaryKey(statmp.getId().longValue());
                    Boolean isFinish = pos.get(sta.getMemo());
                    if (isFinish == null) {
                        if (sta.getVmiRCStatus() != null && sta.getVmiRCStatus()) {
                            pos.put(sta.getMemo(), true);
                        } else {
                            pos.put(sta.getMemo(), false);
                        }
                    } else {
                        if (sta.getVmiRCStatus() != null && sta.getVmiRCStatus() && isFinish) {
                            pos.put(sta.getMemo(), true);
                        } else {
                            pos.put(sta.getMemo(), false);
                        }
                    }
                }
                // 生成反馈数据
                for (StockTransApplicationCommand tmpsta : stas) {
                    if (tmpsta.getId() == null) {
                        continue;
                    }
                    StockTransApplication sta = staDao.getByPrimaryKey(tmpsta.getId().longValue());
                    // 未收货作业单不反馈，如果作业单已经反馈当整单未完成时每次均要反馈
                    if (sta.getVmiRCStatus() == null || !sta.getVmiRCStatus()) {
                        continue;
                    }
                    List<StaLine> staline = staLineDao.findByStaId(sta.getId());
                    for (StaLine line : staline) {
                        NikeStockReceive rec = new NikeStockReceive();
                        rec.setReferenceNo(sta.getRefSlipCode());
                        rec.setItemEanUpcCode(line.getSku().getExtensionCode2());
                        rec.setReceiveDate(new Date());

                        rec.setFromLocation(sta.getFromLocation());
                        rec.setToLocation(sta.getToLocation());
                        rec.setTransferPrefix(sta.getFromLocation());
                        rec.setSapCarton(sta.getMemo());
                        rec.setStatus(NikeStockReceive.CREATE_STATUS);
                        // if (sta.getVmiRCStatus() != null && sta.getVmiRCStatus()) {
                        rec.setQuantity(line.getQuantity());
                        rec.setCartonStatus(NikeStockReceiveData.CLOSED);

                        if (pos.get(sta.getMemo()) != null && pos.get(sta.getMemo())) {
                            rec.setPoStatus(NikeStockReceiveData.CLOSED);
                        } else {
                            rec.setPoStatus(NikeStockReceiveData.OPEN);
                        }
                        // } else {
                        // rec.setQuantity(0L);
                        // rec.setCartonStatus(NikeStockReceiveData.OPEN);
                        // rec.setPoStatus(NikeStockReceiveData.OPEN);
                        // }
                        // 入库反馈 type 1
                        rec.setType(NikeStockReceive.INBOUND_RECEIVE_TYPE);
                        rec.setCreateDate(new Date());
                        nikeStockReceiveDao.save(rec);
                    }
                }
                nikeStockReceiveDataDao.updateFinished();
            }
        }
    }

    // 读文件 =============================================================
    /***
     * 读文件 1. 文件 - 数据库 vmi入库写中间表 - ftp下载文件 - 插入中间表 - ok 从文件读 seq - 出库反馈中间表 (转店 退仓中间表) -
     * t_wh_receive_confirmation 1.1 如果 出库反馈中间表 有，入库中间表 插入数据 直接完成状态 10 -- t_NIKE_VMI_STOCKIN 1.2 如果
     * 出库反馈中间表 没有，入库中间表 插入数据 新建状态 1 ---t_NIKE_VMI_STOCKIN
     * 
     * @param localFileDir 文件目录
     * @param bakFileDir 文件备份目录
     * @return
     */
    @Transactional
    public boolean inBoundreadFileIntoDB(String localFileDir, String bakFileDir, String fileStart) {
        String line = null;
        boolean flag = false;

        BufferedReader buffRead = null;
        File[] files = null;
        try {
            File fileDir = new File(localFileDir);
            files = fileDir.listFiles();
            if (files == null || files.length == 0) {
                log.debug("{} is null, has no file ============================", localFileDir);
                flag = false;
                return flag;
            }
            // 从本地读取文件写入到数据库中

            // String [] fileNameStart = fileStart.split(",");
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                if (fileStart != null) {
                    Pattern pattern = Pattern.compile(fileStart);
                    Matcher matcher = pattern.matcher(file.getName());
                    if (!matcher.matches()) {
                        continue;
                    }
                }
                log.debug("file  name ===================={} ", file.getName());
                buffRead = new BufferedReader(new FileReader(file));
                List<String> results = new ArrayList<String>();
                while ((line = buffRead.readLine()) != null) {
                    // line = replaceBlank(line);
                    results.add(line);
                }
                log.debug("results: **************** {}", results.size());
                flag = inBoundInsertIntoDB(results); // T_NIKE_VMI_STOCKIN
                if (buffRead != null) buffRead.close();
                if (flag) {
                    FileUtils.copyFileToDirectory(file, new File(bakFileDir), true);
                    file.delete();
                }
            }
        } catch (FileNotFoundException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                if (buffRead != null) buffRead.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return flag;
    }

    // 入库中间表数据库插入数据
    @Transactional
    public Boolean inBoundInsertIntoDB(List<String> lines) {
        Boolean flag = false, canMove = false;
        if (lines == null || lines.isEmpty()) {
            return flag;
        }
        try {
            String transferPrefix;
            String referenceNo;
            String receiveDate;
            String fromLocation;
            String toLocation;
            String cs2000ItemCode;
            String colorCode;
            String sizeCode;
            String itemEanUpcCode;
            Long quantity;
            String lineSequenceNo;
            String totalLineSequenceNo;
            String sapCarton;
            String sapDnNo = null;
            String result[] = null;
            for (String s : lines) {
                if (s == null || "".equals(s)) continue;
                result = s.split("	");
                if (result == null || result.length == 0) continue;
                transferPrefix = result[0];
                referenceNo = result[1];
                receiveDate = result[2];
                fromLocation = result[3];
                toLocation = result[4];
                cs2000ItemCode = result[5];
                colorCode = result[6];
                sizeCode = result[7];
                itemEanUpcCode = result[9];
                quantity = Long.parseLong(result[10]);
                lineSequenceNo = result[11];
                totalLineSequenceNo = result[12];
                sapCarton = result[13];
                try {
                    sapDnNo = result[14];
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                nikeStockInDao.saveNikeVMIStockInBoundSql(transferPrefix, referenceNo, receiveDate, fromLocation, toLocation, cs2000ItemCode, colorCode, sizeCode, itemEanUpcCode, quantity, lineSequenceNo, totalLineSequenceNo, sapCarton, sapDnNo,
                        NikeVmiStockInCommand.TODO_STATUS);
            }
            canMove = true;
        } catch (Exception e) {
            // delete
            try {
                nikeStockInDao.deleteTodoStatus(NikeVmiStockInCommand.TODO_STATUS);
            } catch (Exception e1) {
                if (log.isErrorEnabled()) {
                    log.error("nikeStockInDao.deleteTodoStatus Exception:", e1);
                }
            }
            flag = false;
            canMove = false;
            log.error("", e);
        } finally {
            // update
            String[] result = null;
            String referenceNo = null;
            if (canMove) {
                flag = true;
                try {
                    for (String s : lines) {
                        result = s.split("	");
                        referenceNo = result[1];
                        List<String> seqs = findSequence(referenceNo);
                        if (seqs != null && !seqs.isEmpty()) { // FINISH_STATUS
                            log.debug("******************************** seq  is exists ");
                            nikeStockInDao.updateNikeVmiStockInCommandStatus(NikeVmiStockInCommand.FINISH_STATUS, NikeVmiStockInCommand.TODO_STATUS);
                        } else {// CREATE_STATUS
                            nikeStockInDao.updateNikeVmiStockInCommandStatus(NikeVmiStockInCommand.CREATE_STATUS, NikeVmiStockInCommand.TODO_STATUS);
                        }
                    }
                } catch (Exception e) {
                    flag = false;
                    log.error("", e);
                }
            }
        }
        return flag;
    }

    /**
     * 查询序列号在 转店出库反馈 中间表 - t_wh_receive_confirmation是否存在
     * 
     * @param seq
     * @return
     */
    @Transactional(readOnly = true)
    private List<String> findSequence(String seq) {
        List<String> sequences = nikeReturnReceiveDao.findIsExistsSequence(seq, new SingleColumnRowMapper<String>(String.class));
        return sequences;
    }

    /**
     * 写文件 ok 数据库 - 文件 入库反馈/转店入库反馈 同一个中间表 入库反馈 /转店入库反馈 - 文件 文件写完之后 状态改成10， 状态是不是10的才写入文件 3.1 入库反馈
     * 无条件 写文件 3.2 转店入库反馈 type 为2 无条件 写文件， 如果转店入库中间表中的 seq 在 入库中间表有seq才写到文件 localDir : 写文件的目录
     * fileName : 写文件的文件名 *
     **/

    @Transactional
    public void inBoundTransferInBoundWriteToFile(String localDir) {
        // =============================================
        // 1.查询状态为1的 update为2
        // 2.查询为2的数据，写入到文件
        // 3.更新状态为2的数据更新为10。
        // 1.T_NIKE_STOCK_RECEIVE
        // nikeStockReceiveDao.updateToWriteStatus(NikeStockReceive.TO_WRITE_STATUS);
        nikeStockReceiveDao.updateNikeInBoundRevToWriteStatus(NikeStockReceive.INBOUND_RECEIVE_TYPE, NikeStockReceive.TO_WRITE_STATUS);
        nikeStockReceiveDao.updateNikeTransferRevToWriteStatus(NikeStockReceive.TRANSFER_INBOUND_RECEIVE_TYPE, NikeStockReceive.TO_WRITE_STATUS);
        List<NikeStockReceive> nikeStockReceives = nikeStockReceiveDao.findToWriteFile(NikeStockReceive.TO_WRITE_STATUS, new BeanPropertyRowMapperExt<NikeStockReceive>(NikeStockReceive.class));
        if (nikeStockReceives == null || nikeStockReceives.isEmpty()) {
            log.debug("nikeStockReceives is null.................app exit ");
            return;
        } else {
            BufferedWriter br = null;
            String fileName = NIKE_STOCK_RECEIVE_SFRP_FILE_PREFIX + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMdd_HHmmss") + NIKE_FILE_POSTFIX);
            try {
                String lastChar = String.valueOf(localDir.charAt(localDir.length() - 1));
                /*
                 * if (!lastChar.equals("/") && !lastChar.equals("\\")) { fileName = localDir +
                 * File.separator + fileName; }else{ fileName = localDir + fileName; } br = new
                 * BufferedWriter(new FileWriter(new File(fileName)));
                 */
                if (lastChar.equals("/") || lastChar.equals("\\")) {
                    br = new BufferedWriter(new FileWriter(new File(localDir + fileName)));
                } else {
                    br = new BufferedWriter(new FileWriter(new File(localDir + File.separator + fileName)));
                }
                batchWriteDataFileForInboundReceive(nikeStockReceives, br);
                // 3.
                nikeStockReceiveDao.updateFileName(NikeStockReceive.TO_WRITE_STATUS, fileName);
                nikeStockReceiveDao.updateToFinishStatus(NikeStockReceive.TO_WRITE_STATUS, NikeStockReceive.FINISH_STATUS);
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    /**
     * 入库反馈写文件 - 批量将数据写入到本地文件
     */
    private void batchWriteDataFileForInboundReceive(List<NikeStockReceive> nikeStockReceives, BufferedWriter br) {
        for (NikeStockReceive nikeStockReceive : nikeStockReceives) {
            try {
                br.write(buildDataForInboundReceive(nikeStockReceive));
                br.newLine();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        try {
            br.flush();
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                br.close();
                br = null;
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    private String buildDataForInboundReceive(NikeStockReceive nikeReceive) {
        // 数据写入dat文件
        StringBuilder line = new StringBuilder();
        line.append(StringUtils.hasLength(nikeReceive.getTransferPrefix()) ? nikeReceive.getTransferPrefix() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getReferenceNo()) ? nikeReceive.getReferenceNo() : "");
        line.append(blank);
        line.append("CLOSED");
        line.append(blank);
        // FIXME NIKE收货调整
        // start--------------------------
        // line.append(StringUtils.hasLength(nikeReceive.getCartonStatus()) ?
        // nikeReceive.getCartonStatus() : "");
        // line.append(blank);
        // end---------------------------
        line.append(FormatUtil.formatDate(nikeReceive.getReceiveDate(), "yyyyMMdd"));
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getFromLocation()) ? nikeReceive.getFromLocation() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getToLocation()) ? nikeReceive.getToLocation() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getCs2000ItemCode()) ? nikeReceive.getCs2000ItemCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getColorCode()) ? nikeReceive.getColorCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getSizeCode()) ? nikeReceive.getSizeCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getInseamCode()) ? nikeReceive.getInseamCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getItemEanUpcCode()) ? nikeReceive.getItemEanUpcCode() : "");
        line.append(blank);
        line.append(nikeReceive.getQuantity() == null ? "" : nikeReceive.getQuantity());
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getLineSequenceNo()) ? nikeReceive.getLineSequenceNo() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getTotalLineSequenceNo()) ? nikeReceive.getTotalLineSequenceNo() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getSapCarton()) ? nikeReceive.getSapCarton() : "");
        line.append(blank);
        // FIXME NIKE收货调整
        // start--------------------------
        // line.append(StringUtils.hasLength(nikeReceive.getPoStatus()) ? nikeReceive.getPoStatus()
        // : "");
        // line.append(blank);
        // end---------------------------
        line.append("CLOSED");
        line.append(blank);

        return line.toString();
    }

    /**
     * 转店退仓反馈 - WEB_tfx_ type = 2 NIKE_TRANSFER_OUT_RECEIVE_FILE_PREFIX = "WEB_tfx_";
     */
    @Transactional
    public void transferOutReceiveWriteToFile(String localDir) {
        // 1
        nikeReturnReceiveDao.updateToWriteStatus(NikeReturnReceive.RETURN_TRANSFER_OUT_TYPE, NikeReturnReceive.TO_WRITE_STATUS, NikeReturnReceive.CREATE_STATUS);
        // update t_wh_receive_confirmation t set t.status = 2 where t.type = 2
        // and t.status = 1;
        // 2.
        List<NikeReturnReceive> list = nikeReturnReceiveDao.findToWriteFile(NikeReturnReceive.RETURN_TRANSFER_OUT_TYPE, NikeReturnReceive.TO_WRITE_STATUS, new BeanPropertyRowMapperExt<NikeReturnReceive>(NikeReturnReceive.class));
        if (list == null || list.isEmpty()) {
            log.debug("nikeReturnReceives is null.................app exit ");
            return;
        } else {
            Map<String, List<NikeReturnReceive>> map = new HashMap<String, List<NikeReturnReceive>>();
            for (NikeReturnReceive nike : list) {
                String key = nike.getReferenceNo();
                if (map.containsKey(key)) {
                    map.get(key).add(nike);
                } else {
                    List<NikeReturnReceive> tempList = new ArrayList<NikeReturnReceive>();
                    tempList.add(nike);
                    map.put(key, tempList);
                }
            }
            for (String key : map.keySet()) {
                List<NikeReturnReceive> nikeReturnReceives = map.get(key);
                BufferedWriter br = null;
                String fileName = NIKE_TRANSFER_OUT_RECEIVE_FILE_PREFIX + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMdd_HHmmss") + NIKE_FILE_POSTFIX);
                try {
                    String lastChar = String.valueOf(localDir.charAt(localDir.length() - 1));
                    /*
                     * if (!lastChar.equals("/") && !lastChar.equals("\\")) { fileName = localDir +
                     * File.separator + fileName; }else{ fileName = localDir + fileName; } br = new
                     * BufferedWriter(new FileWriter(new File(fileName)));
                     */
                    if (lastChar.equals("/") || lastChar.equals("\\")) {
                        br = new BufferedWriter(new FileWriter(new File(localDir + fileName)));
                    } else {
                        br = new BufferedWriter(new FileWriter(new File(localDir + File.separator + fileName)));
                    }
                    batchWriteDataFileForTransferOutReceive(nikeReturnReceives, br);
                    // 3.
                    nikeReturnReceiveDao.updateFileName(NikeReturnReceive.RETURN_TRANSFER_OUT_TYPE, key, NikeReturnReceive.TO_WRITE_STATUS, fileName);
                    nikeReturnReceiveDao.updateToFinishStatus(NikeReturnReceive.RETURN_TRANSFER_OUT_TYPE, key, NikeReturnReceive.TO_WRITE_STATUS, NikeReturnReceive.FINISH_STATUS);
                } catch (IOException e) {
                    log.error("", e);
                }
                // 让当前线程暂停1秒
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 转店退仓反馈写文件 - 批量将数据写入到本地文件
     */
    private void batchWriteDataFileForTransferOutReceive(List<NikeReturnReceive> nikeReturnReceives, BufferedWriter br) {
        for (NikeReturnReceive nikeReturnReceive : nikeReturnReceives) {
            try {
                br.write(buildDataForTransferOutReceive(nikeReturnReceive));
                br.newLine();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        try {
            br.flush();
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                br.close();
                br = null;
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    private String buildDataForTransferOutReceive(NikeReturnReceive nikeReceive) {
        // 数据写入dat文件
        StringBuilder line = new StringBuilder();
        line.append(StringUtils.hasLength(nikeReceive.getTransferPrefix()) ? nikeReceive.getTransferPrefix() : ""); // nikeReceive.getTransferPrefix()
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getReferenceNo()) ? nikeReceive.getReferenceNo() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getReceiveDate()) ? nikeReceive.getReceiveDate() : ""); // 20120316
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getFromLocation()) ? nikeReceive.getFromLocation() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getToLocation()) ? nikeReceive.getToLocation() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getCs2000ItemCode()) ? nikeReceive.getCs2000ItemCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getColorCode()) ? nikeReceive.getColorCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getSizeCode()) ? nikeReceive.getSizeCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getInseamCode()) ? nikeReceive.getInseamCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getUpcCode()) ? nikeReceive.getUpcCode() : "");
        line.append(blank);
        line.append(nikeReceive.getQuantity() == null ? "" : nikeReceive.getQuantity());
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getLineSequenceNo()) ? nikeReceive.getLineSequenceNo() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getTotalLineSequenceNo()) ? nikeReceive.getTotalLineSequenceNo() : "");
        line.append(blank);
        // line.append(StringUtils.hasLength(nikeReceive.getWmsCarton()) ?
        // nikeReceive.getWmsCarton() : "");
        line.append(StringUtils.hasLength(nikeReceive.getReferenceNo()) ? nikeReceive.getReferenceNo() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getWmsDNNo()) ? nikeReceive.getWmsDNNo() : "");
        return line.toString();
    }

    // ==============================================================
    /**
     * 退大仓反馈 - WEB_rts_ type = 3 NIKE_RETURN_RECEIVE_FILE_PREFIX = "WEB_rts_";
     */
    @Transactional
    public void vmiReturnReceiveWriteToFile(String localDir) {
        // 1
        nikeReturnReceiveDao.updateToWriteStatus(NikeReturnReceive.RETURN_VMI_RETURN_TYPE, NikeReturnReceive.TO_WRITE_STATUS, NikeReturnReceive.CREATE_STATUS);
        // update t_wh_receive_confirmation t set t.status = 2 where t.type = 3
        // and t.status = 1;
        // 2.
        List<NikeReturnReceive> list = nikeReturnReceiveDao.findToWriteFile(NikeReturnReceive.RETURN_VMI_RETURN_TYPE, NikeReturnReceive.TO_WRITE_STATUS, new BeanPropertyRowMapperExt<NikeReturnReceive>(NikeReturnReceive.class));
        if (list == null || list.isEmpty()) {
            log.debug("nikeReturnReceives is null.................app exit ");
            return;
        } else {
            Map<String, List<NikeReturnReceive>> map = new HashMap<String, List<NikeReturnReceive>>();
            for (NikeReturnReceive nike : list) {
                String key = nike.getReferenceNo();
                if (map.containsKey(key)) {
                    map.get(key).add(nike);
                } else {
                    List<NikeReturnReceive> tempList = new ArrayList<NikeReturnReceive>();
                    tempList.add(nike);
                    map.put(key, tempList);
                }
            }
            for (String key : map.keySet()) {
                List<NikeReturnReceive> nikeReturnReceives = map.get(key);
                BufferedWriter br = null;
                FileWriter fw = null;
                String fileName = NIKE_RETURN_RECEIVE_FILE_PREFIX + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMdd_HHmmss") + NIKE_FILE_POSTFIX);
                try {
                    String lastChar = String.valueOf(localDir.charAt(localDir.length() - 1));
                    /*
                     * if (!lastChar.equals("/") && !lastChar.equals("\\")) { fileName = localDir +
                     * File.separator + fileName; }else{ fileName = localDir + fileName; } br = new
                     * BufferedWriter(new FileWriter(new File(fileName)));
                     */
                    if (lastChar.equals("/") || lastChar.equals("\\")) {
                        fw = new FileWriter(new File(localDir + fileName));
                        br = new BufferedWriter(fw);
                    } else {
                        fw = new FileWriter(new File(localDir + File.separator + fileName));
                        br = new BufferedWriter(fw);
                    }
                    batchWriteDataFileForVMIReturnReceive(nikeReturnReceives, br);
                    // 3.
                    nikeReturnReceiveDao.updateFileName(NikeReturnReceive.RETURN_VMI_RETURN_TYPE, key, NikeReturnReceive.TO_WRITE_STATUS, fileName);
                    nikeReturnReceiveDao.updateToFinishStatus(NikeReturnReceive.RETURN_VMI_RETURN_TYPE, key, NikeReturnReceive.TO_WRITE_STATUS, NikeReturnReceive.FINISH_STATUS);
                } catch (IOException e) {
                    log.error("", e);
                } finally {
                    try {
                        if (fw != null) {
                            fw.close();
                        }
                    } catch (IOException e) {
                        log.error("", e);
                    }
                }
                // 让当前线程暂停1秒
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 库存调整 反馈
     * 
     * @param localDir
     */
    @Transactional
    public void invCheckReceiveWriteToFile(String localDir) {
        // 1
        nikeCheckReceiveDao.updateToWriteStatus(NikeCheckReceive.TO_WRITE_STATUS, NikeCheckReceive.CREATE_STATUS);
        // 2.
        List<NikeCheckReceive> nikeCheckReceives = nikeCheckReceiveDao.findToWriteFile(NikeCheckReceive.TO_WRITE_STATUS);
        if (nikeCheckReceives == null || nikeCheckReceives.isEmpty()) {
            log.debug("nikeCheckReceive is null.................app exit ");
            return;
        } else {
            // 保存 map 的 key值 为了到后面在finally 中关闭流 方便查询
            List<String> mapKeys = new ArrayList<String>();
            Date date = new Date();
            // 将数据按照规则分为不同的数据组,每一个数据组里面的数据是同一文件里面的数据 （同类型、同店铺、同事物方向）
            Map<String, Object> map = new HashMap<String, Object>();
            try {
                BufferedWriter br = null;
                FileWriter fw = null;
                // 获取上传路径
                String lastChar = String.valueOf(localDir.charAt(localDir.length() - 1));
                int count = 0;
                for (NikeCheckReceive check : nikeCheckReceives) {
                    // StringBuilder mapKey = new StringBuilder();
                    // //判断调整单类型（系统调整、手动调整）
                    // if(NikeCheckReceive.SYS_CHECK_TYPE.equals(check.getType())){
                    // mapKey.append(NikeCheckReceive.SYS_CHECK_TYPE);
                    // } else {
                    // mapKey.append(NikeCheckReceive.MANUAL_CHECK_TYPE);
                    // }
                    // //不同店铺写到不同的文件里面
                    // mapKey.append("_");
                    // mapKey.append(StringUtils.hasLength(check.getOwnerCode())
                    // ?
                    // check.getOwnerCode() : "");
                    // mapKey.append("_");
                    // // 不同事物方向数据 保存在不同的文件
                    // if (check.getQuantity() > 0) {
                    // mapKey.append(Constants.NIKE_IN_KEY);
                    // } else {
                    // mapKey.append(Constants.NIKE_OUT_KEY);
                    // }
                    // 保存到对应的数据组里面
                    // String key = mapKey.toString();
                    String key = check.getCheckCode();
                    File file = null;
                    if (map.containsKey(key)) {
                        file = (File) map.get(key);
                        br = (BufferedWriter) map.get(key + Constants.NIKE_BUFFERED_WRITER_KEY);
                        br.newLine();
                    } else {
                        String fileName = getFileName(date, count++);
                        file = null;
                        if (lastChar.equals("/") || lastChar.equals("\\")) {
                            file = new File(localDir + fileName);
                        } else {
                            file = new File(localDir + File.separator + fileName);
                        }
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        fw = new FileWriter(file);
                        br = new BufferedWriter(fw);
                        mapKeys.add(key);
                        map.put(key, file);
                        map.put(key + Constants.NIKE_FILE_WRITER_KEY, fw);
                        map.put(key + Constants.NIKE_BUFFERED_WRITER_KEY, br);
                    }
                    // 写数据
                    br.write(batchWriteDataFileForINVCheckReceive(check));
                    check.setFileName(file.getName());
                    check.setStatus(NikeCheckReceive.FINISH_STATUS);
                    check.setFinishDate(date);
                    nikeCheckReceiveDao.save(check);
                }
            } catch (IOException e) {
                log.error("", e);
                log.debug(e.getMessage());
            } finally {
                for (String key : mapKeys) {
                    FileWriter fw = (FileWriter) map.get(key + Constants.NIKE_FILE_WRITER_KEY);
                    BufferedWriter br = (BufferedWriter) map.get(key + Constants.NIKE_BUFFERED_WRITER_KEY);
                    try {
                        if (br != null) {
                            br.flush();
                            br.close();
                        }
                        if (fw != null) {
                            fw.close();
                        }
                    } catch (IOException e) {
                        log.error("", e);
                    }
                }
            }
        }
    }

    /**
     * 库存调整 反馈文件 - 批量将数据写入到本地文件
     */
    private String batchWriteDataFileForINVCheckReceive(NikeCheckReceive bean) {
        StringBuilder line = new StringBuilder();
        line.append(StringUtils.hasLength(bean.getOwnerCode()) ? bean.getOwnerCode() : "");
        line.append(blank);
        line.append(bean.getFileCode());
        line.append(blank);
        line.append(bean.getCreateDate() != null ? FormatUtil.formatDate(bean.getCreateDate(), "yyyyMMdd") : ""); // 20120316
        line.append(blank);
        line.append(blank);
        line.append(blank);
        line.append(blank);
        line.append(blank);
        line.append(StringUtils.hasLength(bean.getUpc()) ? bean.getUpc() : "");
        line.append(blank);
        line.append(bean.getQuantity() == null ? "" : bean.getQuantity());
        line.append(blank);
        line.append(blank);
        line.append(blank);
        return line.toString();
    }

    /**
     * 获取文件编码
     * 
     * @return
     */
    public String getFileCode() {
        StringBuilder result = new StringBuilder();
        result.append("A");
        Long no = nikeCheckReceiveDao.findNikeCheckReceiveFileNO(new SingleColumnRowMapper<Long>(Long.class));
        int length = no.toString().length();
        for (; length < 7; length++) {
            result.append("0");
        }
        result.append(no.toString());
        return result.toString();
    }

    /**
     * 获取文件名称
     * 
     * @param date
     * @param l
     * @return
     */
    private String getFileName(Date date, int l) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, l);
        return NIKE_INV_CHECK_RECEIVE_FILE_PREFIX + String.valueOf(FormatUtil.formatDate(calendar.getTime(), "yyyyMMdd_HHmmss") + NIKE_FILE_POSTFIX);
    }

    /**
     * 退大仓反馈写文件 - 批量将数据写入到本地文件
     */
    private void batchWriteDataFileForVMIReturnReceive(List<NikeReturnReceive> nikeReturnReceives, BufferedWriter br) {
        for (NikeReturnReceive nikeReturnReceive : nikeReturnReceives) {
            try {
                br.write(buildDataForVMIReturnReceive(nikeReturnReceive));
                br.newLine();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        try {
            br.flush();
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    private String buildDataForVMIReturnReceive(NikeReturnReceive nikeReceive) {
        // 数据写入dat文件
        StringBuilder line = new StringBuilder();
        line.append(StringUtils.hasLength(nikeReceive.getFromLocation()) ? nikeReceive.getFromLocation() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getReferenceNo()) ? nikeReceive.getReferenceNo() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getReceiveDate()) ? nikeReceive.getReceiveDate() : ""); // 20120316
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getCs2000ItemCode()) ? nikeReceive.getCs2000ItemCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getColorCode()) ? nikeReceive.getColorCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getSizeCode()) ? nikeReceive.getSizeCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getInseamCode()) ? nikeReceive.getInseamCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getUpcCode()) ? nikeReceive.getUpcCode() : "");
        line.append(blank);
        line.append(nikeReceive.getQuantity() == null ? "" : nikeReceive.getQuantity());
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getReasonCode()) ? nikeReceive.getReasonCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getLineSequenceNo()) ? nikeReceive.getLineSequenceNo() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(nikeReceive.getTotalLineSequenceNo()) ? nikeReceive.getTotalLineSequenceNo() : "");
        return line.toString();
    }

    @Transactional
    public void generateStaByRefNo(String ref, String brand) {
        log.debug("create sta start *****************************");
        List<StockTransApplication> list = staDao.findBySlipCodeByType(ref, StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        for (StockTransApplication staTmp : list) {
            if (staTmp.getStatus().equals(StockTransApplicationStatus.CREATED) || staTmp.getStatus().equals(StockTransApplicationStatus.PARTLY_RETURNED) || staTmp.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                continue;
            }
        }
        List<StockTransApplication> tList = staDao.findBySlipCodeByType(ref, StockTransApplicationType.VMI_OWNER_TRANSFER);
        for (StockTransApplication staTmp : tList) {
            if (staTmp.getStatus().equals(StockTransApplicationStatus.CREATED) || staTmp.getStatus().equals(StockTransApplicationStatus.OCCUPIED) || staTmp.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                continue;
            }
        }
        String refno = ref;
        // staLine
        List<NikeVmiStockInCommand> nikeStockins = nikeStockInDao.findNikeVmiStockInByRefNo(refno, brand, new BeanPropertyRowMapperExt<NikeVmiStockInCommand>(NikeVmiStockInCommand.class));
        BiChannel shop = null;
        String toLocation = null;
        String fromLocation = null;
        // String brand = null;
        for (NikeVmiStockInCommand ns : nikeStockins) {
            // brand = ns.getBrand();
            toLocation = ns.getToLocation();
            fromLocation = ns.getFromLocation();
            if (shop == null) {
                shop = shopDao.getByVmiCode(toLocation);
            } else {
                break;
            }
        }
        if (shop == null) {
            /******** KJL edit *********************/
            nikeStockInDao.updateStatusToFinishByRefNo(ref);
            return;
        }
        StockTransApplication sta = new StockTransApplication();
        sta.setRefSlipCode(refno);
        OperationUnit ou = null;
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setCreateTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        sta.setLastModifyTime(new Date());
        sta = staDao.save(sta);
        sta.setSlipCode1(nikeStockins.get(0).getSapCarton());
        sta.setSlipCode2(nikeStockins.get(0).getSapDNNo());
        sta.setMemo(nikeStockins.get(0).getSapCarton());
        if ("1".equals(brand)) {
            sta.setNikeVmiStockinSource("1");
        }

        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
        if (ou == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {toLocation});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());

        // NIKE收货单创单明细行合并
        Long skuQty = 0l;
        Map<String, NikeVmiStockInCommand> nvsicMap = new HashMap<String, NikeVmiStockInCommand>();
        Map<String, Long> qMap = new HashMap<String, Long>();
        InventoryStatus invStatus = inventoryStatusDao.findInvStatusForSale(ou.getParentUnit().getParentUnit().getId());
        boolean isError = false;

        for (NikeVmiStockInCommand ns : nikeStockins) {
            Long quantity = 0l;
            String itemEanUpcCode = ns.getItemEanUpcCode();
            if (ns.getQuantity() != null) {
                if (nvsicMap.containsKey(itemEanUpcCode)) {
                    quantity = qMap.get(itemEanUpcCode) + ns.getQuantity();
                } else {
                    quantity = ns.getQuantity();
                }
            } else {
                throw new BusinessException("Notice:The quantity field must not be empty(T_NIKE_VMI_STOCKIN)");
            }
            qMap.put(itemEanUpcCode, quantity);
            nvsicMap.put(itemEanUpcCode, ns);
        }

        for (Map.Entry<String, NikeVmiStockInCommand> m : nvsicMap.entrySet()) {
            String itemEanUpcCode = m.getKey();
            NikeVmiStockInCommand ns = m.getValue();
            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(itemEanUpcCode, shop.getCustomer().getId(), shop.getId());
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(ns.getItemEanUpcCode(), shop.getVmiCode());
                isError = true;
                continue;
            }
            StaLine staLine = new StaLine();
            staLine.setSku(sku);
            staLine.setQuantity(qMap.get(itemEanUpcCode));
            staLine.setId(ns.getStaLineId());
            staLine.setCompleteQuantity(0L);
            staLine.setInvStatus(invStatus);
            sta.setRefSlipCode(ns.getReferenceNo());
            staLine.setSta(sta);
            staLine = staLineDao.save(staLine);
            skuQty += staLine.getQuantity();
            staLine.setOwner(shop.getCode());
            nikeStockInDao.updateStaIdByRefNo(staLine.getSta().getId(), staLine.getId(), ns.getItemEanUpcCode(), ns.getReferenceNo(), brand);
        }
        if (isError) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        sta.setSkuQty(skuQty);
        sta.setOwner(shop.getCode());
        sta.setFromLocation(fromLocation);
        sta.setToLocation(toLocation);
        sta.setMainWarehouse(ou);
        staDao.save(sta);
        staDao.flush();
        staDao.updateSkuQtyById(sta.getId());
        /*
         * if ("1".equals(brand)) {// 迁移数据到T_NIKE_VMI_STOCKIN_RECODE
         * nikeStockInDao.insertNikeVmiStockInRecode(sta.getId(), refno); }
         */
        log.debug("create sta end *****************************");
    }

    @Override
    public boolean nikeInboundHub(List<NikeVmiStockInHub> list) {
        Date date = new Date();
        Boolean flag = false, canMove = false;
        if (list == null || list.size() == 0) {
            log.error("nikeInboundHub list =0 ");
            return flag;
        }
        try {
            for (NikeVmiStockInHub nikeVmiStockInHub : list) {
                nikeStockInDao.saveNikeVMIStockInBoundSqlBrand(nikeVmiStockInHub.getTransferPrefix(), nikeVmiStockInHub.getReferenceNo(), nikeVmiStockInHub.getReceiveDate(), nikeVmiStockInHub.getFromLocation(), nikeVmiStockInHub.getToLocation(),
                        nikeVmiStockInHub.getCs2000ItemCode(), nikeVmiStockInHub.getColorCode(), nikeVmiStockInHub.getSizeCode(), nikeVmiStockInHub.getItemEanUpcCode(), nikeVmiStockInHub.getQuantity(), nikeVmiStockInHub.getLineSequenceNo(),
                        nikeVmiStockInHub.getTotalLineSequenceNo(), nikeVmiStockInHub.getSapCarton(), nikeVmiStockInHub.getSapDNNo(), NikeVmiStockInCommand.TODO_STATUS, "1", nikeVmiStockInHub.getQualifier(), nikeVmiStockInHub.getLotnumber(), date);
            }
            canMove = true;
        } catch (Exception e) {
            // delete
            try {
                // nikeStockInDao.deleteTodoStatusBrand(NikeVmiStockInCommand.TODO_STATUS);
            } catch (Exception e1) {
                log.error("nikeInboundHub_Exception1:", e1);
            }
            flag = false;
            canMove = false;
            log.error("nikeInboundHub_Exception2:", e);

        }
 finally {
            // update
            String referenceNo = null;
            if (canMove) {
                flag = true;
                try {
                    for (NikeVmiStockInHub nikeVmiStockInHub : list) {
                        referenceNo = nikeVmiStockInHub.getReferenceNo();
                        List<String> seqs = findSequence(referenceNo);
                        if (seqs != null && !seqs.isEmpty()) { // FINISH_STATUS
                            log.error("seq  is exists:" + referenceNo);
                            nikeStockInDao.updateNikeVmiStockInCommandStatusBrand(NikeVmiStockInCommand.FINISH_STATUS, NikeVmiStockInCommand.TODO_STATUS);
                        } else {// CREATE_STATUS
                            nikeStockInDao.updateNikeVmiStockInCommandStatusBrand(NikeVmiStockInCommand.CREATE_STATUS, NikeVmiStockInCommand.TODO_STATUS);
                        }
                    }
                } catch (Exception e) {
                    flag = false;
                    log.error("nikeInboundHub_Exception3", e);
                }
            }
        }
        return false;
    }

    @Override
    public void uploadInboundNikeDataToHub() {
        String fileName = "nike_inbound" + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMdd_HHmmss"));
        // 1.查询入库反馈状态为1的 update为2
        nikeStockReceiveDao.updateNikeInBoundRevToWriteStatusBrand(NikeStockReceive.INBOUND_RECEIVE_TYPE, NikeStockReceive.TO_WRITE_STATUS);
        // 2.查询所有入库反馈状态为2brand为1的 list
        List<NikeStockReceive> nikeStockReceives = nikeStockReceiveDao.findToWriteFileBrand(NikeStockReceive.TO_WRITE_STATUS, new BeanPropertyRowMapperExt<NikeStockReceive>(NikeStockReceive.class));
        if (nikeStockReceives == null || nikeStockReceives.isEmpty()) {
            log.debug("uploadInboundNikeDataToHub is null.................app exit ");
            return;
        } else {
            String reqJson = JsonUtil.writeValue(nikeStockReceives);
            try {
                MessageCommond mes = new MessageCommond();
                    mes.setMsgId("nike_inbound"+UUIDUtil.getUUID());
                mes.setIsMsgBodySend(true);
                    mes.setMsgType("uploadInboundNikeDataToHub");
                    mes.setMsgBody(reqJson);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    mes.setSendTime(sdf.format(date));
                    // producerServer.sendDataMsgConcurrently(MQ_WMS3_SALES_ORDER_SERVICE, mes);
                    producerServer.sendDataMsgConcurrently(MQ_WMS32HUB_NIKE_INBOUNT_RETURN,null, mes);
                    // 保存进mongodb
                    try {
                        MongoDBMessage mdbm = new MongoDBMessage();
                        BeanUtils.copyProperties(mes, mdbm);
                        mdbm.setStaCode(fileName);
                        mdbm.setOtherUniqueKey(fileName);
                        mdbm.setMsgBody(reqJson);
                        mdbm.setMemo(MQ_WMS3_SALES_ORDER_SERVICE);
                        mongoOperation.save(mdbm);
                    } catch (Exception e) {
                        log.error("uploadInboundNikeDataToHub_savemongodb", e);
                    }

            } catch (Exception e) {
                log.error("uploadInboundNikeDataToHub_faMQ", e);
                throw new BusinessException();
            }
        }
        nikeStockReceiveDao.updateFileNameBrand(NikeStockReceive.TO_WRITE_STATUS, fileName);
        nikeStockReceiveDao.updateToFinishStatusBrand(NikeStockReceive.TO_WRITE_STATUS, NikeStockReceive.FINISH_STATUS);

    }

    @Override
    public void uploadInboundNikeDataToHub2() {// nike收货反馈
        String fileName = "nike_inbound" + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMdd_HHmmss"));
        // 1.查询入库反馈状态为1的 update为2
        nikeStockReceiveDao.updateNikeInBoundRevToWriteStatusBrand(NikeStockReceive.INBOUND_RECEIVE_TYPE, NikeStockReceive.TO_WRITE_STATUS);
        // 根据staID 来封装nike收货反馈数据
        List<NikeStockReceiveDto> nikeStockReceives = nikeStockReceiveDao.findToWriteFileByStaId(NikeStockReceive.TO_WRITE_STATUS, new BeanPropertyRowMapperExt<NikeStockReceiveDto>(NikeStockReceiveDto.class));
        if (nikeStockReceives == null || nikeStockReceives.isEmpty()) {
            log.debug("uploadInboundNikeDataToHub is null.................app exit ");
            return;
        } else {
            String reqJson = JsonUtil.writeValue(nikeStockReceives);
            try {
                MessageCommond mes = new MessageCommond();
                mes.setMsgId("nike_inbound" + UUIDUtil.getUUID());
                mes.setIsMsgBodySend(true);
                mes.setMsgType("uploadInboundNikeDataToHub");
                mes.setMsgBody(reqJson);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                mes.setSendTime(sdf.format(date));
                // producerServer.sendDataMsgConcurrently(MQ_WMS3_SALES_ORDER_SERVICE, mes);
                producerServer.sendDataMsgConcurrently(MQ_WMS32HUB_NIKE_INBOUNT_RETURN, null, mes);
                // 保存进mongodb
                try {
                    MongoDBMessage mdbm = new MongoDBMessage();
                    BeanUtils.copyProperties(mes, mdbm);
                    mdbm.setStaCode(fileName);
                    mdbm.setOtherUniqueKey(fileName);
                    mdbm.setMsgBody(reqJson);
                    mdbm.setMemo(MQ_WMS32HUB_NIKE_INBOUNT_RETURN);
                    mongoOperation.save(mdbm);
                } catch (Exception e) {
                    log.error("uploadInboundNikeDataToHub_savemongodb", e);
                }

            } catch (Exception e) {
                log.error("uploadInboundNikeDataToHub_faMQ", e);
                throw new BusinessException();
            }
        }
        nikeStockReceiveDao.updateFileNameBrand(NikeStockReceive.TO_WRITE_STATUS, fileName);
        nikeStockReceiveDao.updateToFinishStatusBrand(NikeStockReceive.TO_WRITE_STATUS, NikeStockReceive.FINISH_STATUS);



    }

}
