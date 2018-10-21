package com.jumbo.wms.manager.vmi.levisData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.vmi.levis.LevisShoesReceiveDao;
import com.jumbo.dao.vmi.levis.LevisYxTransferOutDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.vmi.converseData.ConverseYxTransferOut;
import com.jumbo.wms.model.vmi.levisData.LevisShoesReceive;
import com.jumbo.wms.model.vmi.levisData.LevisYxTransferOut;

/**
 * levis童装主体切换
 * 
 * @author jinggang.chen
 * 
 */
@Transactional
public class LevisChildrenManagerImpl extends BaseManagerImpl implements LevisChildrenManager {

    private static final long serialVersionUID = -1578095437331650169L;

    private static final String LEVIS_CHILDREN_RECEIVE_PREFIX = "Stkreccon_";
    private static final String LEVIS_CHILDREN_FILE_SUFFIX = ".txt";
    private static final String LEVIS_CHILDREN_FILE_SPLIT = ",";
    private static final String LEVIS_YX_TRANSFER_OUT_PREFIX = "tfx_";

    
    
    
    @Autowired
    private LevisShoesReceiveDao  levisShoesReceiveDao;
    @Autowired
    private LevisYxTransferOutDao levisYxTransferOutDao;

    /**
     * 当前批次一个文件（即本次查询时所有新建的入库写一个文件）<br/>
     * 1、更改中间表数据状态为待写文件状态<br/>
     * 2、写文件<br/>
     * 3、更新本批数据为完成状态
     */
    public void inBoundTransferInBoundWriteToFile(String localDir) {
        // 将中间表数据状态更新为待写文件状态 DefaultStatus 2;
         levisShoesReceiveDao.updateDataToWriteStatus();
        List<LevisShoesReceive> list = levisShoesReceiveDao.findAllToWriteStatusData(new BeanPropertyRowMapper<LevisShoesReceive>(LevisShoesReceive.class));
        if (list == null || list.isEmpty()) {
            log.debug("LeivsChild Yx receive data to write is null.........");
            return;
        } else {
            BufferedWriter br = null;
            String fileName = LEVIS_CHILDREN_RECEIVE_PREFIX + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + LEVIS_CHILDREN_FILE_SUFFIX);
            try {
                String lastChar = String.valueOf(localDir.charAt(localDir.length() - 1));
                if (lastChar.equals("/") || lastChar.equals("\\")) {
                    br = new BufferedWriter(new FileWriter(new File(localDir + fileName)));
                } else {
                    br = new BufferedWriter(new FileWriter(new File(localDir + File.separator + fileName)));
                }
                batchWriteDataFileForInboundReceive(list, br);
                // 3.
                levisShoesReceiveDao.updateToFinishStatus();
            } catch (IOException e) {
                log.error("", e);
            }
        }

    }


    private void batchWriteDataFileForInboundReceive(List<LevisShoesReceive> list, BufferedWriter br) {
        for (LevisShoesReceive cr : list) {
            try {
                br.write(buildDataForInboundReceive(cr));
                br.newLine();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        closeBufferedWriter(br);
    }

    private String buildDataForInboundReceive(LevisShoesReceive cr) {
        StringBuilder line = new StringBuilder();
        line.append(StringUtils.hasLength(cr.getTransferPrefix()) ? cr.getTransferPrefix() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getCartonNumber()) ? cr.getCartonNumber() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getReceiveDate()) ? cr.getReceiveDate() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getFromLocation()) ? cr.getFromLocation() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getToLocation()) ? cr.getToLocation() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getCs2000ItemCode()) ? cr.getCs2000ItemCode() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getColorCode()) ? cr.getColorCode() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getSizeCode()) ? cr.getSizeCode() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getInseamCode()) ? cr.getInseamCode() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getUpc()) ? cr.getUpc() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(cr.getQuantity() == null ? "" : cr.getQuantity());
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(cr.getLineSequenceNo() == null ? "" : cr.getLineSequenceNo());
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(cr.getTotalLineSequenceNo() == null ? "" : cr.getTotalLineSequenceNo());
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(cr.getTransferNo() == null ? "" : cr.getTransferNo());
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getSapCarton()) ? cr.getSapCarton() : "");
        return line.toString();
    }

    private void closeBufferedWriter(BufferedWriter br) {
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
    
    //levis永兴退大仓
    public void vmiReturnReceiveWriteToFile(String localDir) {
        levisYxTransferOutDao.updateDataToWriteStatus();
        List<LevisYxTransferOut> list = levisYxTransferOutDao.findAllToWriteStatusData(new BeanPropertyRowMapper<LevisYxTransferOut>(LevisYxTransferOut.class));
        if (list == null || list.isEmpty()) {
            log.debug("levisChildernReturn is null.................app exit ");
            return;
        } else {
            Map<String, List<LevisYxTransferOut>> map = new HashMap<String, List<LevisYxTransferOut>>();
            for (LevisYxTransferOut cr : list) {
                String key = cr.getCartonNumber();
                if (map.containsKey(key)) {
                    map.get(key).add(cr);
                } else {
                    List<LevisYxTransferOut> tempList = new ArrayList<LevisYxTransferOut>();
                    tempList.add(cr);
                    map.put(key, tempList);
                }
            }
            for (String key : map.keySet()) {
                List<LevisYxTransferOut> crs = map.get(key);
                BufferedWriter br = null;
                FileWriter fw = null;
                String fileName = LEVIS_YX_TRANSFER_OUT_PREFIX + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + LEVIS_CHILDREN_FILE_SUFFIX);
                try {
                    String lastChar = String.valueOf(localDir.charAt(localDir.length() - 1));
                    if (lastChar.equals("/") || lastChar.equals("\\")) {
                        fw = new FileWriter(new File(localDir + fileName));
                        br = new BufferedWriter(fw);
                    } else {
                        fw = new FileWriter(new File(localDir + File.separator + fileName));
                        br = new BufferedWriter(fw);
                    }
                    batchWriteDataFileForVMIReturnReceive(crs, br);
                    levisYxTransferOutDao.updateToFinishStatus();
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
    
    private void batchWriteDataFileForVMIReturnReceive(List<LevisYxTransferOut> crs, BufferedWriter br) {
        for (LevisYxTransferOut cr : crs) {
            try {
                br.write(buildDataForVMIReturnReceive(cr));
                br.newLine();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        closeBufferedWriter(br);
    }
    
    
    private String buildDataForVMIReturnReceive(LevisYxTransferOut cr) {
        StringBuilder line = new StringBuilder();
        line.append(StringUtils.hasLength(cr.getTransferPrefix()) ? cr.getTransferPrefix() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getCartonNumber()) ? cr.getCartonNumber() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getReceiveDate()) ? cr.getReceiveDate() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getFromLocation()) ? cr.getFromLocation() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getToLocation()) ? cr.getToLocation() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getCs2000ItemCode()) ? cr.getCs2000ItemCode() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getColorCode()) ? cr.getColorCode() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getSizeCode()) ? cr.getSizeCode() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getInseamCode()) ? cr.getInseamCode() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getUpc()) ? cr.getUpc() : "");
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(cr.getQuantity() == null ? "" : cr.getQuantity());
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(cr.getLineSequenceNo() == null ? "" : cr.getLineSequenceNo());
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(cr.getTotalLineSequenceNo() == null ? "" : cr.getTotalLineSequenceNo());
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(cr.getTransferNo() == null ? "" : cr.getTransferNo());
        line.append(LEVIS_CHILDREN_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getSapCarton()) ? cr.getSapCarton() : "");
        return line.toString();
    }
}
