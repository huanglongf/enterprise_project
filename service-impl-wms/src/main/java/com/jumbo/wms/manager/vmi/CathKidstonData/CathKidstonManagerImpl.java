package com.jumbo.wms.manager.vmi.CathKidstonData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.vmi.cathKidstonData.CKReceiveConfrimDao;
import com.jumbo.dao.vmi.cathKidstonData.CKReceiveDao;
import com.jumbo.dao.vmi.cathKidstonData.CKTransferOutDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.vmi.VmiStaCreateManagerProxy;
import com.jumbo.wms.model.vmi.cathKidstonData.CKReceive;
import com.jumbo.wms.model.vmi.cathKidstonData.CKReceiveConfrim;
import com.jumbo.wms.model.vmi.cathKidstonData.CKReceiveConfrimCommand;
import com.jumbo.wms.model.vmi.cathKidstonData.CKTransferOut;
import com.jumbo.wms.model.vmi.cathKidstonData.CKTransferOutCommand;
import com.jumbo.wms.model.vmi.cathKidstonData.CathKidstonStatus;

@Transactional
@Service("cathKidstonManager")
public class CathKidstonManagerImpl extends BaseManagerImpl implements CathKidstonManager {

    private static final long serialVersionUID = -1031236401461150647L;

    @Autowired
    private CKReceiveDao ckReceiveDao;
    @Autowired
    private VmiStaCreateManagerProxy vmiStaCreateManagerProxy;
    @Autowired
    private CKReceiveConfrimDao ckReceiveConfrimDao;
    @Autowired
    private CKTransferOutDao ckTransferOutDao;

    /**
     * 解析收货指令文件插入数据库
     */
    public void saveCKreceive(File file, String backupPath) {
        BufferedReader bufferedReader = null;
        InputStreamReader read;
        try {
            read = new InputStreamReader(new FileInputStream(file), "GBK");
            bufferedReader = new BufferedReader(read);
            String ckLine = null;
            // 获取每一行数据
            while ((ckLine = bufferedReader.readLine()) != null) {
                String[] ck = ckLine.split(",");
                // 不同文件内出现DeliveryNo需要过滤
                CKReceive k = ckReceiveDao.findCkReceiveByDeliveryNo(ck[0], new BeanPropertyRowMapper<CKReceive>(CKReceive.class));
                if (k != null) {
                    continue;
                }
                CKReceive c = new CKReceive();
                // 234567,,20141220,5276,5678,4726,9995340417784,6
                if (StringUtil.isEmpty(ck[0])) {
                    // 相关单据号为空整个文件作废
                    throw new BusinessException();
                }
                c.setDeliveryNo(ck[0]);// 相关单据号
                c.setCreateTime(new Date());// 创建时间
                c.setLastModifyTime(new Date());// 最后操作时间
                if (StringUtil.isEmpty(ck[2])) {
                    throw new BusinessException();
                }
                c.setDateTime(ck[2]);
                if (StringUtil.isEmpty(ck[3])) {
                    throw new BusinessException();
                }
                c.setFromLocation(ck[3]);// 来源仓库
                if (StringUtil.isEmpty(ck[4])) {
                    throw new BusinessException();
                }
                c.setToLocation(ck[4]);// 目标仓库
                if (StringUtil.isEmpty(ck[5])) {
                    throw new BusinessException();
                }
                c.setStore(ck[5]);// 店铺号
                if (StringUtil.isEmpty(ck[6])) {
                    throw new BusinessException();
                }
                c.setUpc(ck[6]);// 商品UPC = sku.ext_code2
                if (StringUtil.isEmpty(ck[7])) {
                    throw new BusinessException();
                }
                c.setQuantity(Long.valueOf(ck[7]));
                ckReceiveDao.save(c);
            }
            ckReceiveDao.flush();
            read.close();
            FileUtils.moveToDirectory(file, new File(backupPath), true);
        } catch (Exception ex) {
            log.error("", ex);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 生成收货反馈文件上传FTP
     */
    @Override
    public List<CKReceiveConfrimCommand> writeReceivingDataToFile(String locationUploadPath) throws Exception {
        List<CKReceiveConfrimCommand> ckrcList = ckReceiveConfrimDao.findCKReceiveConfrim(new BeanPropertyRowMapper<CKReceiveConfrimCommand>(CKReceiveConfrimCommand.class));
        if (ckrcList.size() > 0) {
            // 文件名
            String fileName = "delivery-note-receive-confirmation_" + getFormateDate() + Constants.FILE_EXTENSION_TXT;
            FileWriter fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            for (int i = 0; i < ckrcList.size(); i++) {
                if ((ckrcList.size() - 1) == i) {
                    fileWriter.write(ckrcList.get(i).getDeliveryNo() + "," + (ckrcList.get(i).getCartonId() == null ? "" : ckrcList.get(i).getCartonId()) + "," + ckrcList.get(i).getDateTime() + "," + ckrcList.get(i).getFromLocation() + ","
                            + ckrcList.get(i).getToLoaction() + "," + CKReceive.vmiCode + "," + ckrcList.get(i).getUpc() + "," + ckrcList.get(i).getQuantity());
                } else {
                    fileWriter.write(ckrcList.get(i).getDeliveryNo() + "," + (ckrcList.get(i).getCartonId() == null ? "" : ckrcList.get(i).getCartonId()) + "," + ckrcList.get(i).getDateTime() + "," + ckrcList.get(i).getFromLocation() + ","
                            + ckrcList.get(i).getToLoaction() + "," + CKReceive.vmiCode + "," + ckrcList.get(i).getUpc() + "," + ckrcList.get(i).getQuantity() + "\n");
                }
            }
            fileWriter.flush();
            fileWriter.close();
            return ckrcList;
        }
        return null;
    }

    /**
     * 生成退仓反馈文件上传FTP
     */
    @Override
    public List<CKTransferOutCommand> writeTransferOutDataToFile(String locationUploadPath) throws Exception {
        List<CKTransferOutCommand> cktoList = ckTransferOutDao.findCKTransferOut(new BeanPropertyRowMapper<CKTransferOutCommand>(CKTransferOutCommand.class));
        if (cktoList.size() > 0) {
            // 文件名
            String fileName = "transfer-out_" + getFormateDate() + Constants.FILE_EXTENSION_TXT;
            FileWriter fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            for (int i = 0; i < cktoList.size(); i++) {
                if ((cktoList.size() - 1) == i) {
                    fileWriter.write(cktoList.get(i).getDeliveryNo() + "," + cktoList.get(i).getCartonId() + "," + cktoList.get(i).getDateTime() + "," + cktoList.get(i).getFromLocation() + "," + cktoList.get(i).getToLoaction() + "," + CKReceive.vmiCode
                            + "," + cktoList.get(i).getUpc() + "," + cktoList.get(i).getQuantity() + "," + cktoList.get(i).getInvStatus() + "," + (cktoList.get(i).getRemark() == null ? " " : cktoList.get(i).getRemark()));
                } else {
                    fileWriter.write(cktoList.get(i).getDeliveryNo() + "," + cktoList.get(i).getCartonId() + "," + cktoList.get(i).getDateTime() + "," + cktoList.get(i).getFromLocation() + "," + cktoList.get(i).getToLoaction() + "," + CKReceive.vmiCode
                            + "," + cktoList.get(i).getUpc() + "," + cktoList.get(i).getQuantity() + "," + cktoList.get(i).getInvStatus() + "," + (cktoList.get(i).getRemark() == null ? " " : cktoList.get(i).getRemark()) + "\n");
                }
            }
            fileWriter.flush();
            fileWriter.close();
            return cktoList;
        }
        return null;
    }

    public void updateCKReceiveConfrimStatus(List<CKReceiveConfrimCommand> ckrcList, CathKidstonStatus cs) {
        for (CKReceiveConfrimCommand ck : ckrcList) {
            CKReceiveConfrim c = ckReceiveConfrimDao.getByPrimaryKey(ck.getId());
            c.setLastModifyTime(new Date());
            c.setStatus(cs);
            ckReceiveConfrimDao.save(c);
        }
    }

    public void updateTransferOutStatus(List<CKTransferOutCommand> cktoList, CathKidstonStatus cs) {
        for (CKTransferOutCommand ck : cktoList) {
            CKTransferOut c = ckTransferOutDao.getByPrimaryKey(ck.getId());
            c.setStatus(cs);
            c.setLastModifyTime(new Date());
            ckTransferOutDao.save(c);
        }
    }

    public String getFormateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 获取收货执行明细行
     */
    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        return null;
    }

    @Override
    public void createStaForCathKidston(String vmiCode) {
        vmiStaCreateManagerProxy.generateVmiInboundStaByVmiCode(vmiCode);
    }
}
