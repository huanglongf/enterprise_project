package com.jumbo.wms.manager.task.edw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;

@Service("edwTaskManagerProxy")
public class EdwTaskManagerProxyImpl extends BaseManagerImpl implements EdwTaskManagerProxy {

    private static final long serialVersionUID = 9146001652792212309L;

    private static final String sc = "|";

    @Autowired
    private EdwTaskManager emsTaskManagerImpl;

    /**
     * 整合各表数据
     */
    @Override
    public String uploadEdwFile(String locationUploadPathReceiving, String locationUploadPathReceivingOk, String dateTimeString) {
        String result = "SUCCESS";
        FileWriter fileWriter = null;
        try {
            // String dateTimeString = getFormateDate("yyyyMMdd");
            // t_bi_channel
            emsTaskManagerImpl.findEdwTbiChannel(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_bi_channel
            // t_au_user
            emsTaskManagerImpl.findEdwTauUser(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_au_user
            // t_au_operation_unit
            emsTaskManagerImpl.findEdwTauOperationUnit(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_au_operation_unit
            // t_bi_brand
            emsTaskManagerImpl.findEdwTbiBrand(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_bi_brand
            // t_sf_confirm_order_queue_log
            emsTaskManagerImpl.findEdwTsfConfirmOrderOueueLog(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_sf_confirm_order_queue_log
            // t_bi_inv_sku
            emsTaskManagerImpl.findEdwSku(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_bi_inv_sku
            // t_bi_warehouse
            emsTaskManagerImpl.findEdwTbiWarehouse(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_bi_warehouse
            // t_wh_sta
            emsTaskManagerImpl.findEdwTwhSta(locationUploadPathReceiving, dateTimeString);
            // t_wh_sta
            // t_wh_location
            emsTaskManagerImpl.findEdwTwhLocation(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_wh_location
            // t_wh_package_info
            emsTaskManagerImpl.findEdwTwhPackageInfo(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_wh_package_info
            // t_wh_sta_ho_list & t_wh_sta_ho_list_line
            emsTaskManagerImpl.findEdwTwhStaHoList(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_wh_sta_ho_list & t_wh_sta_ho_list_line
            // t_wh_info_time_ref
            emsTaskManagerImpl.findEdwTwhInfoTimeRef(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_wh_info_time_ref
            // t_wh_st_log
            emsTaskManagerImpl.findEdwTwhStLog(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_wh_st_log
            // t_wh_sku_inventory
            emsTaskManagerImpl.findEdwTwhInventory(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_wh_sku_inventory
            // t_bi_inv_sku_barcode
            emsTaskManagerImpl.findEdwTbiInvSkuBarcode(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_bi_inv_sku_barcode
            // t_wh_sta_add_line
            emsTaskManagerImpl.findEdwTwhStaAddLine(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_wh_sta_add_line
            // t_wh_sta_picking_list
            emsTaskManagerImpl.findEdwTwhStaPickingList(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_wh_sta_picking_list
            // ok文件
            uploadEdwOkFile(locationUploadPathReceiving, locationUploadPathReceivingOk, dateTimeString, fileWriter, "^t_.*.txt$");
            // ok文件
        } catch (Exception e) {
            result = "ERROR";
            log.error("", e);
            return result;
        }
        return result;
    }


    @Override
    public String uploadEdwFileHk(String locationUploadPathReceiving, String locationUploadPathReceivingOk, String dateTimeString) {
        String result = "SUCCESS";
        FileWriter fileWriter = null;
        try {
            // String dateTimeString = getFormateDate("yyyyMMdd");
            // t_wh_sku_inventory
            emsTaskManagerImpl.findEdwTwhInventory(locationUploadPathReceiving, fileWriter, dateTimeString);
            // t_wh_sku_inventory
            // ok文件
            uploadEdwOkFile(locationUploadPathReceiving, locationUploadPathReceivingOk, dateTimeString, fileWriter, "^t_.*.txt$");
            // ok文件
        } catch (Exception e) {
            result = "ERROR";
            log.error("", e);
            return result;
        }
        return result;
    }


    /**
     * 写入OK文件
     * 
     * @throws Exception
     */
    private void uploadEdwOkFile(String locationUploadPathReceiving, String locationUploadPathReceivingOk, String dateTimeString, FileWriter fileWriter, String fileName) throws Exception {
        File f = new File(locationUploadPathReceivingOk + "/" + dateTimeString + "001" + Constants.FILE_EXTENSION_OK);
        File file = new File(locationUploadPathReceiving);
        File[] array = file.listFiles();// 获取文件夹下所有的文件
        // 判断ok文件是否存在
        try {
            if (f.exists()) {
                // 存在
                fileWriter = new FileWriter(locationUploadPathReceivingOk + "/" + dateTimeString + "001" + Constants.FILE_EXTENSION_OK, true);
                for (int i = 0; i < array.length; i++) {
                    // 获取文件夹下所有文件比对文件名
                    String fileN = array[i].getName();
                    // 判断是否是对应表文件名
                    boolean result = Pattern.compile(fileName).matcher(fileN).find();
                    if (result) {
                        File file1 = new File(locationUploadPathReceiving + "/" + fileN);
                        int count = getTotalLines(file1);// 文件行数
                        int byteInt = getFileByte(file1);// 文件大小
                        fileWriter.write(fileN.substring(0, fileN.length() - 16) + sc + byteInt + sc + count + sc + dateTimeString + "001" + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 不存在
                fileWriter = new FileWriter(locationUploadPathReceivingOk + "/" + dateTimeString + "001" + Constants.FILE_EXTENSION_OK);
                for (int i = 0; i < array.length; i++) {
                    // 获取文件夹下所有文件比对文件名
                    String fileN = array[i].getName();
                    // 判断是否是对应表文件名
                    boolean result = Pattern.compile(fileName).matcher(fileN).find();
                    if (result) {
                        File file1 = new File(locationUploadPathReceiving + "/" + fileN);
                        int count = getTotalLines(file1);// 文件行数
                        int byteInt = getFileByte(file1);// 文件大小
                        fileWriter.write(fileN.substring(0, fileN.length() - 16) + sc + byteInt + sc + count + sc + dateTimeString + "001" + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            fileWriter.close();
            log.error("", e);
            throw new BusinessException();
        }
    }

    /**
     * 获取文件大小
     * 
     * @throws Exception
     */
    private int getFileByte(File file) throws Exception {
        int size = 0;
        FileInputStream fis = new FileInputStream(file);
        try {
            size = fis.available();
            fis.close();
        } catch (Exception e) {
            fis.close();
            log.error("", e);
            throw new BusinessException();
        }
        return size;
    }

    /**
     * 文件内容的总行数。
     */
    private int getTotalLines(File file) throws Exception {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        int lines = 0;
        try {
            String s = reader.readLine();
            while (s != null) {
                lines++;
                s = reader.readLine();
            }
            reader.close();
            in.close();
        } catch (Exception e) {
            log.error("", e);
            reader.close();
            in.close();
            throw new BusinessException();
        }
        return lines;
    }

    public String getFormateDate(String s) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date dateTime = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(dateTime);
        return date;
    }

}
