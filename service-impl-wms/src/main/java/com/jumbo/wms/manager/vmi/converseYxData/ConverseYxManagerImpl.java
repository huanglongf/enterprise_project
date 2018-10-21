package com.jumbo.wms.manager.vmi.converseYxData;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.vmi.converseYxData.ConverseYxReceiveDao;
import com.jumbo.dao.vmi.converseYxData.ConverseYxTransferOutDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.vmi.converseData.ConverseYxReceive;
import com.jumbo.wms.model.vmi.converseData.ConverseYxTransferOut;


/**
 * Converse永兴业务实现
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("converseYxManager")
public class ConverseYxManagerImpl extends BaseManagerImpl implements ConverseYxManager {

    /**
     * 
     */
    private static final long serialVersionUID = -1734627521249618591L;
    private static final String CONVERSE_YX_RECEIVE_PREFIX = "Stkreccon_";
    private static final String CONVERSE_YX_FILE_SUFFIX = ".txt";
    private static final String CONVERSE_YC_TRANSFER_OUT_PREFIX = "tfx_";
    private static final String CONVERSE_YX_FILE_SPLIT = ",";
    @Autowired
    private ConverseYxReceiveDao converseYxReceiveDao;
    @Autowired
    private ConverseYxTransferOutDao converseYxTransferOutDao;

    /**
     * 当前批次一个文件（即本次查询时所有新建的入库写一个文件）<br/>
     * 1、更改中间表数据状态为待写文件状态<br/>
     * 2、写文件<br/>
     * 3、更新本批数据为完成状态
     */
    public void inBoundTransferInBoundWriteToFile(String localDir) {
        // 将中间表数据状态更新为待写文件状态 DefaultStatus 2;
        converseYxReceiveDao.updateDataToWriteStatus();
        List<ConverseYxReceive> list = converseYxReceiveDao.findAllToWriteStatusData(new BeanPropertyRowMapper<ConverseYxReceive>(ConverseYxReceive.class));
        if (list == null || list.isEmpty()) {
            log.debug("Converse Yx receive data to write is null.........");
            return;
        } else {
            BufferedWriter br = null;
            String fileName = CONVERSE_YX_RECEIVE_PREFIX + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + CONVERSE_YX_FILE_SUFFIX);
            try {
                String lastChar = String.valueOf(localDir.charAt(localDir.length() - 1));
                if (lastChar.equals("/") || lastChar.equals("\\")) {
                    br = new BufferedWriter(new FileWriter(new File(localDir + fileName)));
                } else {
                    br = new BufferedWriter(new FileWriter(new File(localDir + File.separator + fileName)));
                }
                batchWriteDataFileForInboundReceive(list, br);
                // 3.
                converseYxReceiveDao.updateToFinishStatus();
            } catch (IOException e) {
                log.error("", e);
            }
        }

    }

    private void batchWriteDataFileForInboundReceive(List<ConverseYxReceive> list, BufferedWriter br) {
        for (ConverseYxReceive cr : list) {
            try {
                br.write(buildDataForInboundReceive(cr));
                br.newLine();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        closeBufferedWriter(br);
    }

    private String buildDataForInboundReceive(ConverseYxReceive cr) {
        StringBuilder line = new StringBuilder();
        line.append(StringUtils.hasLength(cr.getTransferPrefix()) ? cr.getTransferPrefix() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getCartonNumber()) ? cr.getCartonNumber() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getReceiveDate()) ? cr.getReceiveDate() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getFromLocation()) ? cr.getFromLocation() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getToLocation()) ? cr.getToLocation() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getCs2000ItemCode()) ? cr.getCs2000ItemCode() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getColorCode()) ? cr.getColorCode() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getSizeCode()) ? cr.getSizeCode() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getInseamCode()) ? cr.getInseamCode() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getUpc()) ? cr.getUpc() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(cr.getQuantity() == null ? "" : cr.getQuantity());
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(cr.getLineSequenceNo() == null ? "" : cr.getLineSequenceNo());
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(cr.getTotalLineSequenceNo() == null ? "" : cr.getTotalLineSequenceNo());
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(cr.getTransferNo() == null ? "" : cr.getTransferNo());
        line.append(CONVERSE_YX_FILE_SPLIT);
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

    /**
     * 
     */
    public void vmiReturnReceiveWriteToFile(String localDir) {
        converseYxTransferOutDao.updateDataToWriteStatus();
        List<ConverseYxTransferOut> list = converseYxTransferOutDao.findAllToWriteStatusData(new BeanPropertyRowMapper<ConverseYxTransferOut>(ConverseYxTransferOut.class));
        if (list == null || list.isEmpty()) {
            log.debug("nikeReturnReceives is null.................app exit ");
            return;
        } else {
            Map<String, List<ConverseYxTransferOut>> map = new HashMap<String, List<ConverseYxTransferOut>>();
            for (ConverseYxTransferOut cr : list) {
                String key = cr.getCartonNumber();
                if (map.containsKey(key)) {
                    map.get(key).add(cr);
                } else {
                    List<ConverseYxTransferOut> tempList = new ArrayList<ConverseYxTransferOut>();
                    tempList.add(cr);
                    map.put(key, tempList);
                }
            }
            for (String key : map.keySet()) {
                List<ConverseYxTransferOut> crs = map.get(key);
                BufferedWriter br = null;
                FileWriter fw = null;
                String fileName = CONVERSE_YC_TRANSFER_OUT_PREFIX + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + CONVERSE_YX_FILE_SUFFIX);
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
                    converseYxTransferOutDao.updateToFinishStatus();
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

    private void batchWriteDataFileForVMIReturnReceive(List<ConverseYxTransferOut> crs, BufferedWriter br) {
        for (ConverseYxTransferOut cr : crs) {
            try {
                br.write(buildDataForVMIReturnReceive(cr));
                br.newLine();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        closeBufferedWriter(br);
    }

    private String buildDataForVMIReturnReceive(ConverseYxTransferOut cr) {
        StringBuilder line = new StringBuilder();
        line.append(StringUtils.hasLength(cr.getTransferPrefix()) ? cr.getTransferPrefix() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getCartonNumber()) ? cr.getCartonNumber() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getReceiveDate()) ? cr.getReceiveDate() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getFromLocation()) ? cr.getFromLocation() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getToLocation()) ? cr.getToLocation() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getCs2000ItemCode()) ? cr.getCs2000ItemCode() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getColorCode()) ? cr.getColorCode() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getSizeCode()) ? cr.getSizeCode() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getInseamCode()) ? cr.getInseamCode() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getUpc()) ? cr.getUpc() : "");
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(cr.getQuantity() == null ? "" : cr.getQuantity());
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(cr.getLineSequenceNo() == null ? "" : cr.getLineSequenceNo());
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(cr.getTotalLineSequenceNo() == null ? "" : cr.getTotalLineSequenceNo());
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(cr.getTransferNo() == null ? "" : cr.getTransferNo());
        line.append(CONVERSE_YX_FILE_SPLIT);
        line.append(StringUtils.hasLength(cr.getSapCarton()) ? cr.getSapCarton() : "");
        return line.toString();
    }


}
