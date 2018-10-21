package com.jumbo.wms.manager.vmi.espData;

import java.io.File;
import java.io.IOException;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.json.JSONObject;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.vmi.esprit.xml.order.EspritOrderRootXml;
import com.jumbo.wms.model.vmi.espData.ESPInvoiceBDPo;
import com.jumbo.wms.model.vmi.espData.ESPInvoicePercentage;
import com.jumbo.wms.model.vmi.espData.ESPInvoicePercentageCommand;
import com.jumbo.wms.model.vmi.espData.ESPOrder;
import com.jumbo.wms.model.vmi.espData.ESPOrderCommand;
import com.jumbo.wms.model.vmi.espData.ESPPoTypeCommand;
import com.jumbo.wms.model.vmi.espData.ESPTransferOrder;

public interface ESPOrderManager extends BaseManager {
    List<String> getNotOperaterSeqNum();

    /**
     * 保存ESPRIT收货指令
     * 
     * @param root
     */
    void saveEspritOrderData(EspritOrderRootXml root, File sourceFile, String backupPath) throws IOException;

    /**
     * 生成收货反馈文件
     * 
     * @param locationUploadPath
     * @throws Exception
     */
    void writeReceivingDataToFile(String locationUploadPath) throws Exception;


    void saveToTable(File file);


    /**
     * 生成转店反馈文件
     * 
     * @param locationUploadPath
     * @throws Exception
     */
    void writeDeliveryDataToFile(String locationUploadPath) throws Exception;

    List<ESPOrderCommand> getOrdersGroupBySeqNumAndPO(String status);

    List<ESPOrder> findOrdersBySeqNumAndPO(String seqNum, String status, String po);

    void generateSTAFromESPOrder(ESPOrderCommand orderCommand, String toLocation);

    void generateTransferWHSTAFromESPOrder(List<ESPTransferOrder> espOrderList, String toWHVMICode, String sourceVMICode);

    void updateOrderStatus(String seqNum, Integer staStatus, String po, String ordStatus);

    List<Long> findStaIdNotGenerateTransfer();

    List<Long> findInvIdNotGenerateTransfer();

    String getTONodeByStaID(String staId);

    String getTONodeByInvID(String invId);

    List<ESPTransferOrder> findTransferOrdersByStaId(String staId);

    List<ESPTransferOrder> findTransferOrdersByInvId(String invId);

    List<ESPTransferOrder> findOrdersByStaIdEndWithO(Long staId);

    List<ESPTransferOrder> findOrdersByInvIdEndWithO(Long invId);

    List<ESPTransferOrder> findOrdersByStaIdNotEndWithO(Long staId);

    List<ESPTransferOrder> findOrdersByInvIdNotEndWithO(Long invId);

    void espPoAndInvoiceBD(String po, String invoiceNum);

    Pagination<ESPInvoiceBDPo> findESPPoInvoiceBD(int start, int size, String po, String invoiceNum, Sort[] sorts);

    void generateChangeInboundSta(ESPOrderCommand espComd, String toLocation);

    void generateNewInboungSta(ESPOrderCommand espComd, String toLocation);

    void generetaCancelInboundSta(ESPOrderCommand espComd);

    Pagination<ESPPoTypeCommand> findESPPoTypeList(int start, int pageSize, ESPPoTypeCommand ptCommand, Sort[] sorts);

    JSONObject findPoTypeByPo(JSONObject json, String po) throws Exception;

    Pagination<ESPInvoicePercentage> findInvoicePertentage(int start, int pageSize, ESPInvoicePercentageCommand ipCommand, Sort[] sorts);

    ESPInvoicePercentage findIP(String invoiceNum);

    /**
     * 大仓收货未反馈邮件通知 void
     * 
     * @throws
     */
    void receivedNotUploadEmailInform();

}
