package com.jumbo.wms.manager.task.edw;

import java.io.FileWriter;

import com.jumbo.wms.manager.BaseManager;

public interface EdwTaskManager extends BaseManager {

    /**
     * t_bi_channel表数据
     */
    void findEdwTbiChannel(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_au_user数据表
     */
    void findEdwTauUser(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_au_operation_unit数据表
     */
    void findEdwTauOperationUnit(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_bi_brand数据表
     */
    void findEdwTbiBrand(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_sf_confirm_order_queue_log数据表
     */
    void findEdwTsfConfirmOrderOueueLog(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_bi_inv_sku数据表
     */
    void findEdwSku(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_bi_warehouse数据表
     */
    void findEdwTbiWarehouse(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_wh_sta数据表
     */
    void findEdwTwhSta(String locationUploadPathReceiving, String dateTime) throws Exception;


    /**
     * t_wh_location
     */
    void findEdwTwhLocation(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_wh_package_info
     */
    void findEdwTwhPackageInfo(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_wh_sta_ho_list & t_wh_sta_ho_list_line
     */
    void findEdwTwhStaHoList(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_wh_info_time_ref
     */
    void findEdwTwhInfoTimeRef(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_wh_st_log
     */
    void findEdwTwhStLog(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_wh_sta_add_line
     */
    void findEdwTwhStaAddLine(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_wh_sku_inventory
     */
    void findEdwTwhInventory(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_bi_inv_sku_barcode
     */
    void findEdwTbiInvSkuBarcode(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

    /**
     * t_wh_sta_picking_list
     */
    void findEdwTwhStaPickingList(String locationUploadPathReceiving, FileWriter fileWriter, String dateTime) throws Exception;

}
