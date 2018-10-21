package com.jumbo.wms.manager.task.edw;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.HandOverListDao;
import com.jumbo.dao.warehouse.HandOverListLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.SfConfirmOrderQueueLogDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Brand;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.command.SkuBarcodeCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.UserCommand;
import com.jumbo.wms.model.command.WarehouseCommand;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.HandOverListCommand;
import com.jumbo.wms.model.warehouse.HandOverListLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.SfConfirmOrderQueueLog;
import com.jumbo.wms.model.warehouse.StaAdditionalLineCommand;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucherCommand;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.WarehouseLocationCommand;
import com.jumbo.wms.model.warehouse.WhInfoTimeRef;

@Transactional
@Service("edwTaskManager")
public class EdwTaskManagerImpl extends BaseManagerImpl implements EdwTaskManager {

    private static final long serialVersionUID = 4339615262026671179L;

    private static final String sc = "^|^";
    private static final String sourSys = "APP02";

    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private SfConfirmOrderQueueLogDao sfConfirmOrderQueueLogDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StockTransVoucherDao stockTransVoucherDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private HandOverListDao handOverListDao;
    @Autowired
    private HandOverListLineDao handOverListLineDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private PickingListDao pickingListDao;



    /**
     * t_bi_channel表数据
     */
    @Override
    public void findEdwTbiChannel(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        List<BiChannelCommand> biList = biChannelDao.findEdwTbiChannel(new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class));
        // 文件名
        String dateTimeString = dateTime;
        String fileName = "t_bi_channel_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
        fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
        try {
            if (biList.size() > 0) {
                for (int i = 0; i < biList.size(); i++) {
                    if ((biList.size() - 1) == i) {
                        fileWriter.write(biList.get(i).getId() + sc + biList.get(i).getCustomerId() + sc + biList.get(i).getCode() + sc + biList.get(i).getName() + sc + (biList.get(i).getTelephone() == null ? "" : biList.get(i).getTelephone()) + sc
                                + (biList.get(i).getAddress() == null ? "" : biList.get(i).getAddress()) + sc + (biList.get(i).getZipcode() == null ? "" : biList.get(i).getZipcode()) + sc
                                + (biList.get(i).getRtnWarehouseAddress() == null ? "" : biList.get(i).getRtnWarehouseAddress()) + sc + (biList.get(i).getIsNds() == null ? "0" : biList.get(i).getIsNds()) + sc
                                + (biList.get(i).getSmsTemplate() == null ? "" : biList.get(i).getSmsTemplate()) + sc + (biList.get(i).getIsM() == null ? "" : biList.get(i).getIsM()) + sc
                                + (biList.get(i).getVmiCode() == null ? "" : biList.get(i).getVmiCode()) + sc + (biList.get(i).getVmiWHSource() == null ? "" : biList.get(i).getVmiWHSource()) + sc
                                + (biList.get(i).getShopCode() == null ? "" : biList.get(i).getShopCode()) + sc + (biList.get(i).getIsMa() == null ? "0" : biList.get(i).getIsMa()) + sc + (biList.get(i).getIsRnp() == null ? "0" : biList.get(i).getIsRnp())
                                + sc + (biList.get(i).getIsJdoo() == null ? "0" : biList.get(i).getIsJdoo()) + sc + (biList.get(i).getStatusString() == null ? "1" : biList.get(i).getStatusString()) + sc
                                + (biList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", biList.get(i).getCreateTime())) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(biList.get(i).getId() + sc + biList.get(i).getCustomerId() + sc + biList.get(i).getCode() + sc + biList.get(i).getName() + sc + (biList.get(i).getTelephone() == null ? "" : biList.get(i).getTelephone()) + sc
                                + (biList.get(i).getAddress() == null ? "" : biList.get(i).getAddress()) + sc + (biList.get(i).getZipcode() == null ? "" : biList.get(i).getZipcode()) + sc
                                + (biList.get(i).getRtnWarehouseAddress() == null ? "" : biList.get(i).getRtnWarehouseAddress()) + sc + (biList.get(i).getIsNds() == null ? "0" : biList.get(i).getIsNds()) + sc
                                + (biList.get(i).getSmsTemplate() == null ? "" : biList.get(i).getSmsTemplate()) + sc + (biList.get(i).getIsM() == null ? "" : biList.get(i).getIsM()) + sc
                                + (biList.get(i).getVmiCode() == null ? "" : biList.get(i).getVmiCode()) + sc + (biList.get(i).getVmiWHSource() == null ? "" : biList.get(i).getVmiWHSource()) + sc
                                + (biList.get(i).getShopCode() == null ? "" : biList.get(i).getShopCode()) + sc + (biList.get(i).getIsMa() == null ? "0" : biList.get(i).getIsMa()) + sc + (biList.get(i).getIsRnp() == null ? "0" : biList.get(i).getIsRnp())
                                + sc + (biList.get(i).getIsJdoo() == null ? "0" : biList.get(i).getIsJdoo()) + sc + (biList.get(i).getStatusString() == null ? "1" : biList.get(i).getStatusString()) + sc
                                + (biList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", biList.get(i).getCreateTime())) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_au_user
     */
    @Override
    public void findEdwTauUser(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            List<UserCommand> userList = userDao.findEdwTauUser(new BeanPropertyRowMapper<UserCommand>(UserCommand.class));
            // 文件名
            String dateTimeString = dateTime;
            String fileName = "t_au_user_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            if (userList.size() > 0) {
                // yyyy-MM-dd HH:mm:ss
                for (int i = 0; i < userList.size(); i++) {
                    if ((userList.size() - 1) == i) {
                        fileWriter.write(userList.get(i).getId() + sc + (userList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", userList.get(i).getCreateTime())) + sc
                                + (userList.get(i).getEmail() == null ? "" : userList.get(i).getEmail()) + sc + (userList.get(i).getIsAne() == null ? "" : userList.get(i).getIsAne()) + sc
                                + (userList.get(i).getIsAnl() == null ? "" : userList.get(i).getIsAnl()) + sc + (userList.get(i).getIsEn() == null ? "" : userList.get(i).getIsEn()) + sc
                                + (userList.get(i).getIsSys() == null ? "" : userList.get(i).getIsSys()) + sc + (userList.get(i).getJobNumber() == null ? "" : userList.get(i).getJobNumber()) + sc
                                + (userList.get(i).getLatestAccessTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", userList.get(i).getLatestAccessTime())) + sc
                                + (userList.get(i).getLatestUpdateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", userList.get(i).getLatestUpdateTime())) + sc
                                + (userList.get(i).getLoginName() == null ? "" : userList.get(i).getLoginName()) + sc + (userList.get(i).getPhone() == null ? "" : userList.get(i).getPhone()) + sc
                                + (userList.get(i).getUserName() == null ? "" : userList.get(i).getUserName()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(userList.get(i).getId() + sc + (userList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", userList.get(i).getCreateTime())) + sc
                                + (userList.get(i).getEmail() == null ? "" : userList.get(i).getEmail()) + sc + (userList.get(i).getIsAne() == null ? "" : userList.get(i).getIsAne()) + sc
                                + (userList.get(i).getIsAnl() == null ? "" : userList.get(i).getIsAnl()) + sc + (userList.get(i).getIsEn() == null ? "" : userList.get(i).getIsEn()) + sc
                                + (userList.get(i).getIsSys() == null ? "" : userList.get(i).getIsSys()) + sc + (userList.get(i).getJobNumber() == null ? "" : userList.get(i).getJobNumber()) + sc
                                + (userList.get(i).getLatestAccessTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", userList.get(i).getLatestAccessTime())) + sc
                                + (userList.get(i).getLatestUpdateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", userList.get(i).getLatestUpdateTime())) + sc
                                + (userList.get(i).getLoginName() == null ? "" : userList.get(i).getLoginName()) + sc + (userList.get(i).getPhone() == null ? "" : userList.get(i).getPhone()) + sc
                                + (userList.get(i).getUserName() == null ? "" : userList.get(i).getUserName()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_au_operation_unit
     */
    @Override
    public void findEdwTauOperationUnit(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            List<OperationUnitCommand> unitList = operationUnitDao.findEdwTauOperationUnit(new BeanPropertyRowMapper<OperationUnitCommand>(OperationUnitCommand.class));
            // 文件名
            String dateTimeString = dateTime;
            String fileName = "t_au_operation_unit_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            if (unitList.size() > 0) {
                // yyyy-MM-dd HH:mm:ss
                for (int i = 0; i < unitList.size(); i++) {
                    if ((unitList.size() - 1) == i) {
                        fileWriter.write(unitList.get(i).getId() + sc + (unitList.get(i).getOuTypeid() == null ? "" : unitList.get(i).getOuTypeid()) + sc + (unitList.get(i).getCode() == null ? "" : unitList.get(i).getCode()) + sc
                                + (unitList.get(i).getName() == null ? "" : unitList.get(i).getName()) + sc + (unitList.get(i).getFullName() == null ? "" : unitList.get(i).getFullName()) + sc
                                + (unitList.get(i).getIsAvailableInt() == null ? "" : unitList.get(i).getIsAvailableInt()) + sc + (unitList.get(i).getParentUnitid() == null ? "" : unitList.get(i).getParentUnitid()) + sc + dateTimeString + "001" + sc
                                + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(unitList.get(i).getId() + sc + (unitList.get(i).getOuTypeid() == null ? "" : unitList.get(i).getOuTypeid()) + sc + (unitList.get(i).getCode() == null ? "" : unitList.get(i).getCode()) + sc
                                + (unitList.get(i).getName() == null ? "" : unitList.get(i).getName()) + sc + (unitList.get(i).getFullName() == null ? "" : unitList.get(i).getFullName()) + sc
                                + (unitList.get(i).getIsAvailableInt() == null ? "" : unitList.get(i).getIsAvailableInt()) + sc + (unitList.get(i).getParentUnitid() == null ? "" : unitList.get(i).getParentUnitid()) + sc + dateTimeString + "001" + sc
                                + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }

    }

    /**
     * t_bi_brand
     */
    @Override
    public void findEdwTbiBrand(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            List<Brand> brandList = brandDao.findEdwTbiBrand(new BeanPropertyRowMapper<Brand>(Brand.class));
            // 文件名
            String dateTimeString = dateTime;
            String fileName = "t_bi_brand_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            if (brandList.size() > 0) {
                // yyyy-MM-dd HH:mm:ss
                for (int i = 0; i < brandList.size(); i++) {
                    if ((brandList.size() - 1) == i) {
                        fileWriter.write(brandList.get(i).getId() + sc + (brandList.get(i).getCode() == null ? "" : brandList.get(i).getCode()) + sc + (brandList.get(i).getName() == null ? "" : brandList.get(i).getName()) + sc
                                + (brandList.get(i).getNameEng() == null ? "" : brandList.get(i).getNameEng()) + sc + (brandList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", brandList.get(i).getCreateTime())) + sc
                                + (brandList.get(i).getIsAvailableInt() == null ? "" : brandList.get(i).getIsAvailableInt()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(brandList.get(i).getId() + sc + (brandList.get(i).getCode() == null ? "" : brandList.get(i).getCode()) + sc + (brandList.get(i).getName() == null ? "" : brandList.get(i).getName()) + sc
                                + (brandList.get(i).getNameEng() == null ? "" : brandList.get(i).getNameEng()) + sc + (brandList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", brandList.get(i).getCreateTime())) + sc
                                + (brandList.get(i).getIsAvailableInt() == null ? "" : brandList.get(i).getIsAvailableInt()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_sf_confirm_order_queue_log
     */
    @Override
    public void findEdwTsfConfirmOrderOueueLog(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            List<SfConfirmOrderQueueLog> sfList = sfConfirmOrderQueueLogDao.findEdwTsfConfirmOrderOueueLog(new BeanPropertyRowMapper<SfConfirmOrderQueueLog>(SfConfirmOrderQueueLog.class));
            // 文件名
            String dateTimeString = dateTime;
            String fileName = "t_sf_confirm_order_queue_log_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            if (sfList.size() > 0) {
                // yyyy-MM-dd HH:mm:ss
                for (int i = 0; i < sfList.size(); i++) {
                    if ((sfList.size() - 1) == i) {
                        fileWriter.write(sfList.get(i).getId() + sc + (sfList.get(i).getCheckword() == null ? "" : sfList.get(i).getCheckword()) + sc
                                + (sfList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", sfList.get(i).getCreateTime())) + sc + (sfList.get(i).getMailno() == null ? "" : sfList.get(i).getMailno()) + sc
                                + (sfList.get(i).getOrderId() == null ? "" : sfList.get(i).getOrderId()) + sc + (sfList.get(i).getStaCode() == null ? "" : sfList.get(i).getStaCode()) + sc
                                + (sfList.get(i).getWeight() == null ? "" : sfList.get(i).getWeight()) + sc + (sfList.get(i).getFilter1() == null ? "" : sfList.get(i).getFilter1()) + sc
                                + (sfList.get(i).getFilter2() == null ? "" : sfList.get(i).getFilter2()) + sc + (sfList.get(i).getFilter3() == null ? "" : sfList.get(i).getFilter3()) + sc
                                + (sfList.get(i).getFinishTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", sfList.get(i).getFinishTime())) + sc + (sfList.get(i).getFilter4() == null ? "" : sfList.get(i).getFilter4()) + sc
                                + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(sfList.get(i).getId() + sc + (sfList.get(i).getCheckword() == null ? "" : sfList.get(i).getCheckword()) + sc
                                + (sfList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", sfList.get(i).getCreateTime())) + sc + (sfList.get(i).getMailno() == null ? "" : sfList.get(i).getMailno()) + sc
                                + (sfList.get(i).getOrderId() == null ? "" : sfList.get(i).getOrderId()) + sc + (sfList.get(i).getStaCode() == null ? "" : sfList.get(i).getStaCode()) + sc
                                + (sfList.get(i).getWeight() == null ? "" : sfList.get(i).getWeight()) + sc + (sfList.get(i).getFilter1() == null ? "" : sfList.get(i).getFilter1()) + sc
                                + (sfList.get(i).getFilter2() == null ? "" : sfList.get(i).getFilter2()) + sc + (sfList.get(i).getFilter3() == null ? "" : sfList.get(i).getFilter3()) + sc
                                + (sfList.get(i).getFinishTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", sfList.get(i).getFinishTime())) + sc + (sfList.get(i).getFilter4() == null ? "" : sfList.get(i).getFilter4()) + sc
                                + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_bi_inv_sku数据表
     */
    @Override
    public void findEdwSku(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            List<SkuCommand> skuList = skuDao.findEdwSku(new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
            // 文件名
            String dateTimeString = dateTime;
            String fileName = "t_bi_inv_sku_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            if (skuList.size() > 0) {
                // yyyy-MM-dd HH:mm:ss
                for (int i = 0; i < skuList.size(); i++) {
                    if ((skuList.size() - 1) == i) {
                        fileWriter.write(skuList.get(i).getId() + sc + skuList.get(i).getBarCode() + sc + skuList.get(i).getCode() + sc + (skuList.get(i).getExtensionCode1() == null ? "" : skuList.get(i).getExtensionCode1()) + sc
                                + (skuList.get(i).getExtensionCode2() == null ? "" : skuList.get(i).getExtensionCode2()) + sc + (skuList.get(i).getExtensionCode3() == null ? "" : skuList.get(i).getExtensionCode3()) + sc
                                + (skuList.get(i).getJmCode() == null ? "" : skuList.get(i).getJmCode()) + sc + (skuList.get(i).getKeyProperties() == null ? "" : skuList.get(i).getKeyProperties()) + sc
                                + (skuList.get(i).getName() == null ? "" : skuList.get(i).getName()) + sc + (skuList.get(i).getSupplierCode() == null ? "" : skuList.get(i).getSupplierCode()) + sc
                                + (skuList.get(i).getBrandId() == null ? "" : skuList.get(i).getBrandId()) + sc + (skuList.get(i).getProductId() == null ? "" : skuList.get(i).getProductId()) + sc
                                + (skuList.get(i).getEnName() == null ? "" : skuList.get(i).getEnName()) + sc + (skuList.get(i).getUpdateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", skuList.get(i).getUpdateTime())) + sc
                                + (skuList.get(i).getColor() == null ? "" : skuList.get(i).getColor()) + sc + (skuList.get(i).getSkuSize() == null ? "" : skuList.get(i).getSkuSize()) + sc
                                + (skuList.get(i).getExtensionCode1() == null ? "" : skuList.get(i).getExtensionCode1()) + sc + (skuList.get(i).getExtensionCode2() == null ? "" : skuList.get(i).getExtensionCode2()) + sc
                                + (skuList.get(i).getCustomerId() == null ? "" : skuList.get(i).getCustomerId()) + sc + (skuList.get(i).getWarrantyCardTypeInt() == null ? "" : skuList.get(i).getWarrantyCardTypeInt()) + sc
                                + (skuList.get(i).getWidth() == null ? "" : skuList.get(i).getWidth()) + sc + (skuList.get(i).getHeight() == null ? "" : skuList.get(i).getHeight()) + sc
                                + (skuList.get(i).getLength() == null ? "" : skuList.get(i).getLength()) + sc + (skuList.get(i).getIsSnSkuInt() == null ? "" : skuList.get(i).getIsSnSkuInt()) + sc
                                + (skuList.get(i).getSalesModel() == null ? "" : skuList.get(i).getSalesModel()) + sc + (skuList.get(i).getGrossWeight() == null ? "" : skuList.get(i).getGrossWeight()) + sc
                                + (skuList.get(i).getStoremodeInt() == null ? "" : skuList.get(i).getStoremodeInt()) + sc + (skuList.get(i).getIsGiftInt() == null ? "" : skuList.get(i).getIsGiftInt()) + sc
                                + (skuList.get(i).getDeliveryTypeInt() == null ? "" : skuList.get(i).getDeliveryTypeInt()) + sc + (skuList.get(i).getSkuCategoriesId() == null ? "" : skuList.get(i).getSkuCategoriesId()) + sc
                                + (skuList.get(i).getCustomerSkuCode() == null ? "" : skuList.get(i).getCustomerSkuCode()) + sc + (skuList.get(i).getListPrice() == null ? "" : skuList.get(i).getListPrice()) + sc + dateTimeString + "001" + sc + "3" + sc
                                + sourSys);
                    } else {
                        fileWriter.write(skuList.get(i).getId() + sc + skuList.get(i).getBarCode() + sc + skuList.get(i).getCode() + sc + (skuList.get(i).getExtensionCode1() == null ? "" : skuList.get(i).getExtensionCode1()) + sc
                                + (skuList.get(i).getExtensionCode2() == null ? "" : skuList.get(i).getExtensionCode2()) + sc + (skuList.get(i).getExtensionCode3() == null ? "" : skuList.get(i).getExtensionCode3()) + sc
                                + (skuList.get(i).getJmCode() == null ? "" : skuList.get(i).getJmCode()) + sc + (skuList.get(i).getKeyProperties() == null ? "" : skuList.get(i).getKeyProperties()) + sc
                                + (skuList.get(i).getName() == null ? "" : skuList.get(i).getName()) + sc + (skuList.get(i).getSupplierCode() == null ? "" : skuList.get(i).getSupplierCode()) + sc
                                + (skuList.get(i).getBrandId() == null ? "" : skuList.get(i).getBrandId()) + sc + (skuList.get(i).getProductId() == null ? "" : skuList.get(i).getProductId()) + sc
                                + (skuList.get(i).getEnName() == null ? "" : skuList.get(i).getEnName()) + sc + (skuList.get(i).getUpdateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", skuList.get(i).getUpdateTime())) + sc
                                + (skuList.get(i).getColor() == null ? "" : skuList.get(i).getColor()) + sc + (skuList.get(i).getSkuSize() == null ? "" : skuList.get(i).getSkuSize()) + sc
                                + (skuList.get(i).getExtensionCode1() == null ? "" : skuList.get(i).getExtensionCode1()) + sc + (skuList.get(i).getExtensionCode2() == null ? "" : skuList.get(i).getExtensionCode2()) + sc
                                + (skuList.get(i).getCustomerId() == null ? "" : skuList.get(i).getCustomerId()) + sc + (skuList.get(i).getWarrantyCardTypeInt() == null ? "" : skuList.get(i).getWarrantyCardTypeInt()) + sc
                                + (skuList.get(i).getWidth() == null ? "" : skuList.get(i).getWidth()) + sc + (skuList.get(i).getHeight() == null ? "" : skuList.get(i).getHeight()) + sc
                                + (skuList.get(i).getLength() == null ? "" : skuList.get(i).getLength()) + sc + (skuList.get(i).getIsSnSkuInt() == null ? "" : skuList.get(i).getIsSnSkuInt()) + sc
                                + (skuList.get(i).getSalesModel() == null ? "" : skuList.get(i).getSalesModel()) + sc + (skuList.get(i).getGrossWeight() == null ? "" : skuList.get(i).getGrossWeight()) + sc
                                + (skuList.get(i).getStoremodeInt() == null ? "" : skuList.get(i).getStoremodeInt()) + sc + (skuList.get(i).getIsGiftInt() == null ? "" : skuList.get(i).getIsGiftInt()) + sc
                                + (skuList.get(i).getDeliveryTypeInt() == null ? "" : skuList.get(i).getDeliveryTypeInt()) + sc + (skuList.get(i).getSkuCategoriesId() == null ? "" : skuList.get(i).getSkuCategoriesId()) + sc
                                + (skuList.get(i).getCustomerSkuCode() == null ? "" : skuList.get(i).getCustomerSkuCode()) + sc + (skuList.get(i).getListPrice() == null ? "" : skuList.get(i).getListPrice()) + sc + dateTimeString + "001" + sc + "3" + sc
                                + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_bi_warehouse
     */
    @Override
    public void findEdwTbiWarehouse(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            List<WarehouseCommand> biList = warehouseDao.findEdwTbiWarehouse(new BeanPropertyRowMapper<WarehouseCommand>(WarehouseCommand.class));
            // 文件名
            String dateTimeString = dateTime;
            String fileName = "t_bi_warehouse_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            if (biList.size() > 0) {
                // yyyy-MM-dd HH:mm:ss
                for (int i = 0; i < biList.size(); i++) {
                    if ((biList.size() - 1) == i) {
                        fileWriter.write(biList.get(i).getId() + sc + (biList.get(i).getAvailSize() == null ? "" : biList.get(i).getAvailSize()) + sc + (biList.get(i).getFax() == null ? "" : biList.get(i).getFax()) + sc
                                + (biList.get(i).getIsShareInt() == null ? "" : biList.get(i).getIsShareInt()) + sc + (biList.get(i).getManageModeInt() == null ? "" : biList.get(i).getManageModeInt()) + sc
                                + (biList.get(i).getOpModeInt() == null ? "" : biList.get(i).getOpModeInt()) + sc + (biList.get(i).getOtherContact1() == null ? "" : biList.get(i).getOtherContact1()) + sc
                                + (biList.get(i).getPhone() == null ? "" : biList.get(i).getPhone()) + sc + (biList.get(i).getPic() == null ? "" : biList.get(i).getPic()) + sc + (biList.get(i).getPicContact() == null ? "" : biList.get(i).getPicContact())
                                + sc + (biList.get(i).getSize() == null ? "" : biList.get(i).getSize()) + sc + (biList.get(i).getWorkerNum() == null ? "" : biList.get(i).getWorkerNum()) + sc + biList.get(i).getOuId() + sc
                                + (biList.get(i).getDeparture() == null ? "" : biList.get(i).getDeparture()) + sc + (biList.get(i).getIsNeedWrapStuffInt() == null ? "" : biList.get(i).getIsNeedWrapStuffInt()) + sc
                                + (biList.get(i).getSoCountModelId() == null ? "" : biList.get(i).getSoCountModelId()) + sc + (biList.get(i).getVmiSource() == null ? "" : biList.get(i).getVmiSource()) + sc
                                + (biList.get(i).getVmiSourceWh() == null ? "" : biList.get(i).getVmiSourceWh()) + sc + (biList.get(i).getIsManualWeighingInt() == null ? "" : biList.get(i).getIsManualWeighingInt()) + sc
                                + (biList.get(i).getAddress() == null ? "" : biList.get(i).getAddress()) + sc + (biList.get(i).getIsCheckedBarcodeInt() == null ? "" : biList.get(i).getIsCheckedBarcodeInt()) + sc
                                + (biList.get(i).getIsAutoCommit() == null ? "" : biList.get(i).getIsAutoCommit()) + sc + (biList.get(i).getCity() == null ? "" : biList.get(i).getCity()) + sc
                                + (biList.get(i).getIsSfOlOrderInt() == null ? "" : biList.get(i).getIsSfOlOrderInt()) + sc + (biList.get(i).getProvince() == null ? "" : biList.get(i).getProvince()) + sc
                                + (biList.get(i).getZipcode() == null ? "" : biList.get(i).getZipcode()) + sc + (biList.get(i).getSfWhCode() == null ? "" : biList.get(i).getSfWhCode()) + sc
                                + (biList.get(i).getCityCode() == null ? "" : biList.get(i).getCityCode()) + sc + (biList.get(i).getIsMqInvoiceInt() == null ? "" : biList.get(i).getIsMqInvoiceInt()) + sc
                                + (biList.get(i).getIsOutinvoiceInt() == null ? "" : biList.get(i).getIsOutinvoiceInt()) + sc + (biList.get(i).getIsEmsOlOrderInt() == null ? "" : biList.get(i).getIsEmsOlOrderInt()) + sc
                                + (biList.get(i).getSaleOcpTypeInt() == null ? "" : biList.get(i).getSaleOcpTypeInt()) + sc + (biList.get(i).getIsSupportPackageSku() == null ? "" : biList.get(i).getIsSupportPackageSku()) + sc
                                + (biList.get(i).getIsSupportSeckillInt() == null ? "" : biList.get(i).getIsSupportSeckillInt()) + sc + (biList.get(i).getIsCloseRollover() == null ? "" : biList.get(i).getIsCloseRollover()) + sc
                                + (biList.get(i).getIsOlStoInt() == null ? "" : biList.get(i).getIsOlStoInt()) + sc + (biList.get(i).getIsZtoOlOrderInt() == null ? "" : biList.get(i).getIsZtoOlOrderInt()) + sc
                                + (biList.get(i).getCustomerId() == null ? "" : biList.get(i).getCustomerId()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(biList.get(i).getId() + sc + (biList.get(i).getAvailSize() == null ? "" : biList.get(i).getAvailSize()) + sc + (biList.get(i).getFax() == null ? "" : biList.get(i).getFax()) + sc
                                + (biList.get(i).getIsShareInt() == null ? "" : biList.get(i).getIsShareInt()) + sc + (biList.get(i).getManageModeInt() == null ? "" : biList.get(i).getManageModeInt()) + sc
                                + (biList.get(i).getOpModeInt() == null ? "" : biList.get(i).getOpModeInt()) + sc + (biList.get(i).getOtherContact1() == null ? "" : biList.get(i).getOtherContact1()) + sc
                                + (biList.get(i).getPhone() == null ? "" : biList.get(i).getPhone()) + sc + (biList.get(i).getPic() == null ? "" : biList.get(i).getPic()) + sc + (biList.get(i).getPicContact() == null ? "" : biList.get(i).getPicContact())
                                + sc + (biList.get(i).getSize() == null ? "" : biList.get(i).getSize()) + sc + (biList.get(i).getWorkerNum() == null ? "" : biList.get(i).getWorkerNum()) + sc + biList.get(i).getOuId() + sc
                                + (biList.get(i).getDeparture() == null ? "" : biList.get(i).getDeparture()) + sc + (biList.get(i).getIsNeedWrapStuffInt() == null ? "" : biList.get(i).getIsNeedWrapStuffInt()) + sc
                                + (biList.get(i).getSoCountModelId() == null ? "" : biList.get(i).getSoCountModelId()) + sc + (biList.get(i).getVmiSource() == null ? "" : biList.get(i).getVmiSource()) + sc
                                + (biList.get(i).getVmiSourceWh() == null ? "" : biList.get(i).getVmiSourceWh()) + sc + (biList.get(i).getIsManualWeighingInt() == null ? "" : biList.get(i).getIsManualWeighingInt()) + sc
                                + (biList.get(i).getAddress() == null ? "" : biList.get(i).getAddress()) + sc + (biList.get(i).getIsCheckedBarcodeInt() == null ? "" : biList.get(i).getIsCheckedBarcodeInt()) + sc
                                + (biList.get(i).getIsAutoCommit() == null ? "" : biList.get(i).getIsAutoCommit()) + sc + (biList.get(i).getCity() == null ? "" : biList.get(i).getCity()) + sc
                                + (biList.get(i).getIsSfOlOrderInt() == null ? "" : biList.get(i).getIsSfOlOrderInt()) + sc + (biList.get(i).getProvince() == null ? "" : biList.get(i).getProvince()) + sc
                                + (biList.get(i).getZipcode() == null ? "" : biList.get(i).getZipcode()) + sc + (biList.get(i).getSfWhCode() == null ? "" : biList.get(i).getSfWhCode()) + sc
                                + (biList.get(i).getCityCode() == null ? "" : biList.get(i).getCityCode()) + sc + (biList.get(i).getIsMqInvoiceInt() == null ? "" : biList.get(i).getIsMqInvoiceInt()) + sc
                                + (biList.get(i).getIsOutinvoiceInt() == null ? "" : biList.get(i).getIsOutinvoiceInt()) + sc + (biList.get(i).getIsEmsOlOrderInt() == null ? "" : biList.get(i).getIsEmsOlOrderInt()) + sc
                                + (biList.get(i).getSaleOcpTypeInt() == null ? "" : biList.get(i).getSaleOcpTypeInt()) + sc + (biList.get(i).getIsSupportPackageSku() == null ? "" : biList.get(i).getIsSupportPackageSku()) + sc
                                + (biList.get(i).getIsSupportSeckillInt() == null ? "" : biList.get(i).getIsSupportSeckillInt()) + sc + (biList.get(i).getIsCloseRollover() == null ? "" : biList.get(i).getIsCloseRollover()) + sc
                                + (biList.get(i).getIsOlStoInt() == null ? "" : biList.get(i).getIsOlStoInt()) + sc + (biList.get(i).getIsZtoOlOrderInt() == null ? "" : biList.get(i).getIsZtoOlOrderInt()) + sc
                                + (biList.get(i).getCustomerId() == null ? "" : biList.get(i).getCustomerId()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }


    /**
     * t_wh_sta t_wh_sta_delivery_info t_wh_sta_line t_wh_stv t_wh_stv_line
     */
    @Override
    public void findEdwTwhSta(String locationUploadPath, String dateTime) throws Exception {
        // sta文件名
        String sta_dateTimeString = dateTime;
        String sta_fileName = "t_wh_sta_" + sta_dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
        FileWriter sta_fileWriter = new FileWriter(locationUploadPath + "/" + sta_fileName);
        // delivery_info文件名
        String delivery_info_sta_fileName = "t_wh_sta_delivery_info_" + sta_dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
        FileWriter delivery_info_fileWriter = new FileWriter(locationUploadPath + "/" + delivery_info_sta_fileName);
        // sta_line文件名
        String sta_line_fileName = "t_wh_sta_line_" + sta_dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
        FileWriter sta_line_fileWriter = new FileWriter(locationUploadPath + "/" + sta_line_fileName);
        // stv文件
        String stv_fileName = "t_wh_stv_" + sta_dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
        FileWriter stv_fileWriter = new FileWriter(locationUploadPath + "/" + stv_fileName);
        // stv_line 文件
        String stvline_fileName = "t_wh_stv_line_" + sta_dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
        FileWriter stvline_fileWriter = new FileWriter(locationUploadPath + "/" + stvline_fileName);
        boolean stvCheck = true;
        boolean deliveryCheck = true;
        try {
            List<StockTransApplicationCommand> staList = stockTransApplicationDao.findEdwTwhSta(new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
            if (staList.size() > 0) {
                for (StockTransApplicationCommand sta : staList) {
                    // 写sta.TXT
                    sta_fileWriter.write(sta.getId() + sc + (sta.getBusinessSeqNo() == null ? "" : sta.getBusinessSeqNo()) + sc + sta.getCode() + sc + (sta.getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", sta.getCreateTime()))
                            + sc + (sta.getFinishTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", sta.getFinishTime())) + sc + (sta.getInboundTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", sta.getInboundTime())) + sc
                            + (sta.getIsNeedOccupiedInt() == null ? "" : sta.getIsNeedOccupiedInt()) + sc + (sta.getMemo() == null ? "" : sta.getMemo().replaceAll("\n", "")) + sc
                            + (sta.getOutboundTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", sta.getOutboundTime())) + sc + (sta.getOwner() == null ? "" : sta.getOwner()) + sc + (sta.getSlipCode() == null ? "" : sta.getSlipCode()) + sc
                            + (sta.getRefSlipTypeInt() == null ? "" : sta.getRefSlipTypeInt()) + sc + (sta.getIntStaStatus() == null ? "" : sta.getIntStaStatus()) + sc + (sta.getTotalActual() == null ? "" : sta.getTotalActual()) + sc
                            + (sta.getIntStaType() == null ? "" : sta.getIntStaType()) + sc + (sta.getCreatorId() == null ? "" : sta.getCreatorId()) + sc + (sta.getHoListId() == null ? "" : sta.getHoListId()) + sc
                            + (sta.getInboundOperatorId() == null ? "" : sta.getInboundOperatorId()) + sc + (sta.getMainWhId() == null ? "" : sta.getMainWhId()) + sc + (sta.getOutboundOperator() == null ? "" : sta.getOutboundOperator()) + sc
                            + (sta.getPickingListId() == null ? "" : sta.getPickingListId()) + sc + (sta.getSkus() == null ? "" : sta.getSkus()) + sc + (sta.getIndex() == null ? "" : sta.getIndex()) + sc
                            + (sta.getVmiRCStatusInt() == null ? "" : sta.getVmiRCStatusInt()) + sc + (sta.getCurrency() == null ? "" : sta.getCurrency()) + sc + (sta.getTotalGTP() == null ? "" : sta.getTotalGTP()) + sc
                            + (sta.getCheckTime() == null ? "" : sta.getCheckTime()) + sc + (sta.getSkuQty() == null ? "" : sta.getSkuQty()) + sc + (sta.getIsSnInt() == null ? "" : sta.getIsSnInt()) + sc
                            + (sta.getIsSpecialPackagingInt() == null ? "" : sta.getIsSpecialPackagingInt()) + sc + (sta.getSlipCode1() == null ? "" : sta.getSlipCode1()) + sc + (sta.getSlipCode2() == null ? "" : sta.getSlipCode2()) + sc
                            + (sta.getSysUpdateTime() == null ? "" : sta.getSysUpdateTime()) + sc + (sta.getActivitySource() == null ? "" : sta.getActivitySource()) + sc + (sta.getSkuMaxLength() == null ? "" : sta.getSkuMaxLength()) + sc
                            + (sta.getDeliveryType() == null ? "" : sta.getDeliveryType()) + sc + (sta.getIsSedKillInt() == null ? "" : sta.getIsSedKillInt()) + sc + (sta.getSkuCategoriesInt() == null ? "" : sta.getSkuCategoriesInt()) + sc
                            + (sta.getIsMergeInt() == null ? "" : sta.getIsMergeInt()) + sc + (sta.getTrackingAndSku() == null ? "" : sta.getTrackingAndSku()) + sc + (sta.getPickingTypeInt() == null ? "" : sta.getPickingTypeInt()) + sc
                            + (sta.getOrderCreateTime() == null ? "" : sta.getOrderCreateTime()) + sc + (sta.getOrderTotalActual() == null ? "" : sta.getOrderTotalActual()) + sc
                            + (sta.getOrderTransferFreeInt() == null ? "" : sta.getOrderTransferFreeInt()) + sc + (sta.getChannelList() == null ? "" : sta.getChannelList()) + sc + sta_dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    // STA对应物流表
                    StaDeliveryInfoCommand dList = staDeliveryInfoDao.findEdwTwhStaDeliveryInfo(sta.getId(), new BeanPropertyRowMapper<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
                    if (dList != null) {
                        // 写t_wh_sta_delivery_inf.txt
                        delivery_info_fileWriter.write(dList.getId() + sc + (dList.getAddress() == null ? "" : dList.getAddress().replaceAll("\n", "")) + sc + (dList.getCity() == null ? "" : dList.getCity()) + sc
                                + (dList.getCountry() == null ? "" : dList.getCountry()) + sc + (dList.getDistrict() == null ? "" : dList.getDistrict()) + sc + (dList.getLpCode() == null ? "" : dList.getLpCode()) + sc
                                + (dList.getMobile() == null ? "" : dList.getMobile()) + sc + (dList.getProvince() == null ? "" : dList.getProvince()) + sc + (dList.getReceiver() == null ? "" : dList.getReceiver()) + sc
                                + (dList.getStoreComIsNeedInvoiceInt() == null ? "" : dList.getStoreComIsNeedInvoiceInt()) + sc + (dList.getTelephone() == null ? "" : dList.getTelephone()) + sc
                                + (dList.getTrackingNo() == null ? "" : dList.getTrackingNo()) + sc + (dList.getTransferCost() == null ? "" : dList.getTransferCost()) + sc + (dList.getTransferFee() == null ? "" : dList.getTransferFee()) + sc
                                + (dList.getZipcode() == null ? "" : dList.getZipcode()) + sc + (dList.getWeight() == null ? "" : dList.getWeight()) + sc + (dList.getIsCodInt() == null ? "" : dList.getIsCodInt()) + sc
                                + (dList.getExtTransOrderId() == null ? "" : dList.getExtTransOrderId()) + sc + (dList.getTransCityCode() == null ? "" : dList.getTransCityCode()) + sc
                                + (dList.getIsMorePackageInt() == null ? "" : dList.getIsMorePackageInt()) + sc + (dList.getTransTimeTypeInt() == null ? "" : dList.getTransTimeTypeInt()) + sc
                                + (dList.getTransTypeInt() == null ? "" : dList.getTransTypeInt()) + sc + (dList.getOrderUserCode() == null ? "" : dList.getOrderUserCode()) + sc + (dList.getInsuranceAmount() == null ? "" : dList.getInsuranceAmount())
                                + sc + (dList.getIsCodPosInt() == null ? "" : dList.getIsCodPosInt()) + sc + sta_dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                        deliveryCheck = false;
                    }
                    // STA对应的sta明细
                    List<StaLineCommand> lineList = staLineDao.findEdwTwhStaLine(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                    // 写t_wh_sta_line
                    for (StaLineCommand staline : lineList) {
                        sta_line_fileWriter.write(staline.getId() + sc + (staline.getCompleteQuantity() == null ? "" : staline.getCompleteQuantity()) + sc + (staline.getOwner() == null ? "" : staline.getOwner()) + sc
                                + (staline.getQuantity() == null ? "" : staline.getQuantity()) + sc + (staline.getSkuCost() == null ? "" : staline.getSkuCost()) + sc + (staline.getTotalActual() == null ? "" : staline.getTotalActual()) + sc
                                + (staline.getInvInvstatusId() == null ? "" : staline.getInvInvstatusId()) + sc + (staline.getSkuId() == null ? "" : staline.getSkuId()) + sc + (staline.getStaId() == null ? "" : staline.getStaId()) + sc
                                + (staline.getUnitPrice() == null ? "" : staline.getUnitPrice()) + sc + (staline.getOrderTotalActual() == null ? "" : staline.getOrderTotalActual()) + sc
                                + (staline.getOrderTotalBfDiscount() == null ? "" : staline.getOrderTotalBfDiscount()) + sc + (staline.getListPrice() == null ? "" : staline.getListPrice()) + sc + (staline.getSkuName() == null ? "" : staline.getSkuName())
                                + sc + sta_dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                    // STA对应的STV
                    List<StockTransVoucherCommand> stvlist = stockTransVoucherDao.findEdwTwhStv(sta.getId(), new BeanPropertyRowMapper<StockTransVoucherCommand>(StockTransVoucherCommand.class));
                    // 写stv
                    for (StockTransVoucherCommand stv : stvlist) {
                        stv_fileWriter.write(stv.getId() + sc + (stv.getBusinessSeqNo() == null ? "" : stv.getBusinessSeqNo()) + sc + stv.getCode() + sc
                                + (stv.getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", stv.getCreateTime())) + sc + (stv.getDirectionInt() == null ? "" : stv.getDirectionInt()) + sc
                                + (stv.getFinishTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", stv.getFinishTime())) + sc + (stv.getOwner() == null ? "" : stv.getOwner()) + sc + (stv.getStatusInt() == null ? "" : stv.getStatusInt())
                                + sc + (stv.getCreatorId() == null ? "" : stv.getCreatorId()) + sc + (stv.getOperatorId() == null ? "" : stv.getOperatorId()) + sc + (stv.getStaId() == null ? "" : stv.getStaId()) + sc
                                + (stv.getTransactionTypeId() == null ? "" : stv.getTransactionTypeId()) + sc + (stv.getWhId() == null ? "" : stv.getWhId()) + sc + sta_dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                        // sta对应的stv_line
                        List<StvLineCommand> stvList = stvLineDao.findEdwTwhStvLine(stv.getId(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
                        // 写stv_line
                        for (StvLineCommand stvline : stvList) {
                            stvline_fileWriter.write(stvline.getId() + sc + (stvline.getBatchCode() == null ? "" : stvline.getBatchCode()) + sc + (stvline.getDirectionInt() == null ? "" : stvline.getDirectionInt()) + sc
                                    + (stvline.getOwner() == null ? "" : stvline.getOwner()) + sc + (stvline.getQuantity() == null ? "" : stvline.getQuantity()) + sc + (stvline.getSkuCost() == null ? "" : stvline.getSkuCost()) + sc
                                    + (stvline.getDistrictId() == null ? "" : stvline.getDistrictId()) + sc + (stvline.getInvStatusId() == null ? "" : stvline.getInvStatusId()) + sc + (stvline.getLocationId() == null ? "" : stvline.getLocationId()) + sc
                                    + (stvline.getSkuId() == null ? "" : stvline.getSkuId()) + sc + (stvline.getStalineId() == null ? "" : stvline.getStalineId()) + sc + stvline.getStvId() + sc
                                    + (stvline.getTranstypeId() == null ? "" : stvline.getTranstypeId()) + sc + (stvline.getWhId() == null ? "" : stvline.getWhId()) + sc
                                    + (stvline.getInBoundTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", stvline.getInBoundTime())) + sc + sta_dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                        }
                        stvCheck = false;
                    }
                }
                if (stvCheck) {
                    stv_fileWriter.write("");
                    stvline_fileWriter.write("");
                }
                if (deliveryCheck) {
                    delivery_info_fileWriter.write("");
                }
                sta_fileWriter.flush();
                delivery_info_fileWriter.flush();
                sta_line_fileWriter.flush();
                stv_fileWriter.flush();
                stvline_fileWriter.flush();
                sta_fileWriter.close();
                delivery_info_fileWriter.close();
                sta_line_fileWriter.close();
                stv_fileWriter.close();
                stvline_fileWriter.close();
            } else {
                // 没数据写个空文件
                sta_fileWriter.write("");
                delivery_info_fileWriter.write("");
                sta_line_fileWriter.write("");
                stv_fileWriter.write("");
                stvline_fileWriter.write("");
                sta_fileWriter.flush();
                delivery_info_fileWriter.flush();
                sta_line_fileWriter.flush();
                stv_fileWriter.flush();
                stvline_fileWriter.flush();
                sta_fileWriter.close();
                delivery_info_fileWriter.close();
                sta_line_fileWriter.close();
                stv_fileWriter.close();
                stvline_fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            sta_fileWriter.close();
            delivery_info_fileWriter.close();
            sta_line_fileWriter.close();
            stv_fileWriter.close();
            stvline_fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_wh_location
     */
    @Override
    public void findEdwTwhLocation(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            String dateTimeString = dateTime;
            String fileName = "t_wh_location_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            List<WarehouseLocationCommand> wList = warehouseLocationDao.findEdwTwhLocation(new BeanPropertyRowMapper<WarehouseLocationCommand>(WarehouseLocationCommand.class));
            if (wList.size() > 0) {
                for (int i = 0; i < wList.size(); i++) {
                    if ((wList.size() - 1) == i) {
                        fileWriter.write(wList.get(i).getId() + sc + wList.get(i).getBarCode() + sc + wList.get(i).getCapaRatio() + sc + wList.get(i).getCapacity() + sc + wList.get(i).getCode() + sc
                                + (wList.get(i).getDimC() == null ? "" : wList.get(i).getDimC()) + sc + (wList.get(i).getDimX() == null ? "" : wList.get(i).getDimX()) + sc + (wList.get(i).getDimY() == null ? "" : wList.get(i).getDimY()) + sc
                                + (wList.get(i).getDimZ() == null ? "" : wList.get(i).getDimZ()) + sc + (wList.get(i).getIsAvailableInt() == null ? "" : wList.get(i).getIsAvailableInt()) + sc
                                + (wList.get(i).getIsDisputableInt() == null ? "" : wList.get(i).getIsDisputableInt()) + sc + (wList.get(i).getIsLockedInt() == null ? "" : wList.get(i).getIsLockedInt()) + sc
                                + (wList.get(i).getManualCode() == null ? "" : wList.get(i).getManualCode()) + sc + (wList.get(i).getSortingModeInt() == null ? "" : wList.get(i).getSortingModeInt()) + sc
                                + (wList.get(i).getSysCompileCode() == null ? "" : wList.get(i).getSysCompileCode()) + sc + (wList.get(i).getDistrictId() == null ? "" : wList.get(i).getDistrictId()) + sc
                                + (wList.get(i).getOuid() == null ? "" : wList.get(i).getOuid()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(wList.get(i).getId() + sc + wList.get(i).getBarCode() + sc + wList.get(i).getCapaRatio() + sc + wList.get(i).getCapacity() + sc + wList.get(i).getCode() + sc
                                + (wList.get(i).getDimC() == null ? "" : wList.get(i).getDimC()) + sc + (wList.get(i).getDimX() == null ? "" : wList.get(i).getDimX()) + sc + (wList.get(i).getDimY() == null ? "" : wList.get(i).getDimY()) + sc
                                + (wList.get(i).getDimZ() == null ? "" : wList.get(i).getDimZ()) + sc + (wList.get(i).getIsAvailableInt() == null ? "" : wList.get(i).getIsAvailableInt()) + sc
                                + (wList.get(i).getIsDisputableInt() == null ? "" : wList.get(i).getIsDisputableInt()) + sc + (wList.get(i).getIsLockedInt() == null ? "" : wList.get(i).getIsLockedInt()) + sc
                                + (wList.get(i).getManualCode() == null ? "" : wList.get(i).getManualCode()) + sc + (wList.get(i).getSortingModeInt() == null ? "" : wList.get(i).getSortingModeInt()) + sc
                                + (wList.get(i).getSysCompileCode() == null ? "" : wList.get(i).getSysCompileCode()) + sc + (wList.get(i).getDistrictId() == null ? "" : wList.get(i).getDistrictId()) + sc
                                + (wList.get(i).getOuid() == null ? "" : wList.get(i).getOuid()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_wh_package_info
     */
    @Override
    public void findEdwTwhPackageInfo(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            String dateTimeString = dateTime;
            String fileName = "t_wh_package_info_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            List<PackageInfoCommand> pList = packageInfoDao.findEdwTwhPackageInfo(new BeanPropertyRowMapper<PackageInfoCommand>(PackageInfoCommand.class));
            if (pList.size() > 0) {
                for (int i = 0; i < pList.size(); i++) {
                    if ((pList.size() - 1) == i) {
                        fileWriter.write(pList.get(i).getId() + sc + (pList.get(i).getIsCheckedInt() == null ? "" : pList.get(i).getIsCheckedInt()) + sc + (pList.get(i).getLpCode() == null ? "" : pList.get(i).getLpCode()) + sc
                                + (pList.get(i).getTrackingNo() == null ? "" : pList.get(i).getTrackingNo()) + sc + (pList.get(i).getTransferCose() == null ? "" : pList.get(i).getTransferCose()) + sc
                                + (pList.get(i).getWeight() == null ? "" : pList.get(i).getWeight()) + sc + (pList.get(i).getStaDeliveryInfoId() == null ? "" : pList.get(i).getStaDeliveryInfoId()) + sc
                                + (pList.get(i).getPgStatus() == null ? "" : pList.get(i).getPgStatus()) + sc + (pList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", pList.get(i).getCreateTime())) + sc + dateTimeString
                                + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(pList.get(i).getId() + sc + (pList.get(i).getIsCheckedInt() == null ? "" : pList.get(i).getIsCheckedInt()) + sc + (pList.get(i).getLpCode() == null ? "" : pList.get(i).getLpCode()) + sc
                                + (pList.get(i).getTrackingNo() == null ? "" : pList.get(i).getTrackingNo()) + sc + (pList.get(i).getTransferCose() == null ? "" : pList.get(i).getTransferCose()) + sc
                                + (pList.get(i).getWeight() == null ? "" : pList.get(i).getWeight()) + sc + (pList.get(i).getStaDeliveryInfoId() == null ? "" : pList.get(i).getStaDeliveryInfoId()) + sc
                                + (pList.get(i).getPgStatus() == null ? "" : pList.get(i).getPgStatus()) + sc + (pList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", pList.get(i).getCreateTime())) + sc + dateTimeString
                                + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_wh_sta_ho_list & t_wh_sta_ho_list_line
     */
    @Override
    public void findEdwTwhStaHoList(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        String dateTimeString = dateTime;
        String sta_ho_list_fileName = "t_wh_sta_ho_list_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
        FileWriter shl_fileWriter = new FileWriter(locationUploadPath + "/" + sta_ho_list_fileName);
        // delivery_info文件名
        String sta_ho_list_line_fileName = "t_wh_sta_ho_list_line_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
        FileWriter shll_fileWriter = new FileWriter(locationUploadPath + "/" + sta_ho_list_line_fileName);
        try {
            List<HandOverListCommand> hList = handOverListDao.findEdwTwhStaHoList(new BeanPropertyRowMapperExt<HandOverListCommand>(HandOverListCommand.class));
            if (hList.size() > 0) {
                for (HandOverListCommand h : hList) {
                    shl_fileWriter.write(h.getId() + sc + (h.getBillCount() == null ? "" : h.getBillCount()) + sc + (h.getCode() == null ? "" : h.getCode()) + sc
                            + (h.getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", h.getCreateTime())) + sc + (h.getHandOverTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", h.getHandOverTime())) + sc
                            + (h.getLpcode() == null ? "" : h.getLpcode()) + sc + (h.getPackageCount() == null ? "" : h.getPackageCount()) + sc + (h.getPartyAOperator() == null ? "" : h.getPartyAOperator()) + sc
                            + (h.getPartyBOperator() == null ? "" : h.getPartyBOperator()) + sc + (h.getPaytyAMobile() == null ? "" : h.getPaytyAMobile()) + sc + (h.getPaytyBMobile() == null ? "" : h.getPaytyBMobile()) + sc
                            + (h.getPaytyBPassPort() == null ? "" : h.getPaytyBPassPort()) + sc + (h.getHoIntStatus() == null ? "" : h.getHoIntStatus()) + sc + (h.getTotalWeight() == null ? "" : h.getTotalWeight()) + sc
                            + (h.getModifierId() == null ? "" : h.getModifierId()) + sc + (h.getOperatorId() == null ? "" : h.getOperatorId()) + sc + (h.getOuId() == null ? "" : h.getOuId()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    List<HandOverListLineCommand> hlList = handOverListLineDao.findEdwTwhStaHoListLine(h.getId(), new BeanPropertyRowMapper<HandOverListLineCommand>(HandOverListLineCommand.class));
                    for (HandOverListLineCommand ho : hlList) {
                        shll_fileWriter.write(ho.getId() + sc + (ho.getLineIntStatus() == null ? "" : ho.getLineIntStatus()) + sc + (ho.getTrackingNo() == null ? "" : ho.getTrackingNo()) + sc + (ho.getWeight() == null ? "" : ho.getWeight()) + sc
                                + (ho.getHoListId() == null ? "" : ho.getHoListId()) + sc + (ho.getStaId() == null ? "" : ho.getStaId()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                shl_fileWriter.flush();
                shll_fileWriter.flush();
                shl_fileWriter.close();
                shll_fileWriter.close();
            } else {
                shl_fileWriter.write("");
                shll_fileWriter.write("");
                shl_fileWriter.flush();
                shll_fileWriter.flush();
                shl_fileWriter.close();
                shll_fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            shl_fileWriter.close();
            shll_fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_wh_info_time_ref
     */
    @Override
    public void findEdwTwhInfoTimeRef(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            String dateTimeString = dateTime;
            String fileName = "t_wh_info_time_ref_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            List<WhInfoTimeRef> wList = whInfoTimeRefDao.findEdwTwhInfoTimeRef(new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
            if (wList.size() > 0) {
                for (int i = 0; i < wList.size(); i++) {
                    if ((wList.size() - 1) == i) {
                        fileWriter.write(wList.get(i).getId() + sc + (wList.get(i).getBillTypeInt() == null ? "" : wList.get(i).getBillTypeInt()) + sc + (wList.get(i).getNodeTypeInt() == null ? "" : wList.get(i).getNodeTypeInt()) + sc
                                + (wList.get(i).getSlipCode() == null ? "" : wList.get(i).getSlipCode()) + sc + (wList.get(i).getExecutionTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getExecutionTime())) + sc
                                + (wList.get(i).getCreateId() == null ? "" : wList.get(i).getCreateId()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(wList.get(i).getId() + sc + (wList.get(i).getBillTypeInt() == null ? "" : wList.get(i).getBillTypeInt()) + sc + (wList.get(i).getNodeTypeInt() == null ? "" : wList.get(i).getNodeTypeInt()) + sc
                                + (wList.get(i).getSlipCode() == null ? "" : wList.get(i).getSlipCode()) + sc + (wList.get(i).getExecutionTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getExecutionTime())) + sc
                                + (wList.get(i).getCreateId() == null ? "" : wList.get(i).getCreateId()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_wh_st_log
     */
    @Override
    public void findEdwTwhStLog(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            String dateTimeString = dateTime;
            String fileName = "t_wh_st_log_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            List<StockTransTxLogCommand> wList = stockTransTxLogDao.findEdwTwhStLog(new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
            if (wList.size() > 0) {
                for (int i = 0; i < wList.size(); i++) {
                    if ((wList.size() - 1) == i) {
                        fileWriter.write(wList.get(i).getId() + sc + (wList.get(i).getIntDirection() == null ? "" : wList.get(i).getIntDirection()) + sc + (wList.get(i).getDistrictId() == null ? "" : wList.get(i).getDistrictId()) + sc
                                + (wList.get(i).getInvStatusId() == null ? "" : wList.get(i).getInvStatusId()) + sc + (wList.get(i).getLocationId() == null ? "" : wList.get(i).getLocationId()) + sc
                                + (wList.get(i).getOwner() == null ? "" : wList.get(i).getOwner()) + sc + (wList.get(i).getQuantity() == null ? "" : wList.get(i).getQuantity()) + sc + (wList.get(i).getSkuId() == null ? "" : wList.get(i).getSkuId()) + sc
                                + (wList.get(i).getStvId() == null ? "" : wList.get(i).getStvId()) + sc + (wList.get(i).getTransactionTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getTransactionTime())) + sc
                                + (wList.get(i).getWarehouseOuId() == null ? "" : wList.get(i).getWarehouseOuId()) + sc + (wList.get(i).getTransactionTypeid() == null ? "" : wList.get(i).getTransactionTypeid()) + sc
                                + (wList.get(i).getInboundTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getInboundTime())) + sc + (wList.get(i).getBatchCode() == null ? "" : wList.get(i).getBatchCode()) + sc
                                + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(wList.get(i).getId() + sc + (wList.get(i).getIntDirection() == null ? "" : wList.get(i).getIntDirection()) + sc + (wList.get(i).getDistrictId() == null ? "" : wList.get(i).getDistrictId()) + sc
                                + (wList.get(i).getInvStatusId() == null ? "" : wList.get(i).getInvStatusId()) + sc + (wList.get(i).getLocationId() == null ? "" : wList.get(i).getLocationId()) + sc
                                + (wList.get(i).getOwner() == null ? "" : wList.get(i).getOwner()) + sc + (wList.get(i).getQuantity() == null ? "" : wList.get(i).getQuantity()) + sc + (wList.get(i).getSkuId() == null ? "" : wList.get(i).getSkuId()) + sc
                                + (wList.get(i).getStvId() == null ? "" : wList.get(i).getStvId()) + sc + (wList.get(i).getTransactionTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getTransactionTime())) + sc
                                + (wList.get(i).getWarehouseOuId() == null ? "" : wList.get(i).getWarehouseOuId()) + sc + (wList.get(i).getTransactionTypeid() == null ? "" : wList.get(i).getTransactionTypeid()) + sc
                                + (wList.get(i).getInboundTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getInboundTime())) + sc + (wList.get(i).getBatchCode() == null ? "" : wList.get(i).getBatchCode()) + sc
                                + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_wh_sta_add_line
     */
    @Override
    public void findEdwTwhStaAddLine(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            String dateTimeString = dateTime;
            String fileName = "t_wh_sta_add_line_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            List<StaAdditionalLineCommand> wList = staAdditionalLineDao.findEdwTwhStaAddLine(new BeanPropertyRowMapper<StaAdditionalLineCommand>(StaAdditionalLineCommand.class));
            if (wList.size() > 0) {
                for (int i = 0; i < wList.size(); i++) {
                    if ((wList.size() - 1) == i) {
                        fileWriter.write(wList.get(i).getId() + sc + (wList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getCreateTime())) + sc
                                + (wList.get(i).getLpcode() == null ? "" : wList.get(i).getLpcode()) + sc + (wList.get(i).getOwner() == null ? "" : wList.get(i).getOwner()) + sc + (wList.get(i).getQuantity() == null ? "" : wList.get(i).getQuantity())
                                + sc + (wList.get(i).getSkuCost() == null ? "" : wList.get(i).getSkuCost()) + sc + (wList.get(i).getTrackingNo() == null ? "" : wList.get(i).getTrackingNo()) + sc
                                + (wList.get(i).getSkuId() == null ? "" : wList.get(i).getSkuId()) + sc + (wList.get(i).getStaId() == null ? "" : wList.get(i).getStaId()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(wList.get(i).getId() + sc + (wList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getCreateTime())) + sc
                                + (wList.get(i).getLpcode() == null ? "" : wList.get(i).getLpcode()) + sc + (wList.get(i).getOwner() == null ? "" : wList.get(i).getOwner()) + sc + (wList.get(i).getQuantity() == null ? "" : wList.get(i).getQuantity())
                                + sc + (wList.get(i).getSkuCost() == null ? "" : wList.get(i).getSkuCost()) + sc + (wList.get(i).getTrackingNo() == null ? "" : wList.get(i).getTrackingNo()) + sc
                                + (wList.get(i).getSkuId() == null ? "" : wList.get(i).getSkuId()) + sc + (wList.get(i).getStaId() == null ? "" : wList.get(i).getStaId()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_wh_sku_inventory
     */
    @Override
    public void findEdwTwhInventory(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            String dateTimeString = dateTime;
            String fileName = "t_wh_sku_inventory_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            List<StockTransTxLogCommand> stList = stockTransTxLogDao.findEdwTwhStLogToSkuId(new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
            if (stList.size() > 0) {
                // 通过ST_LOG表的SKUID和OWNER查询对应库存表信息
                for (StockTransTxLogCommand sl : stList) {
                    List<InventoryCommand> wList = inventoryDao.findEdwTwhInventory(sl.getOwner(), sl.getSkuId(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                    if (wList.size() > 0) {
                        for (int i = 0; i < wList.size(); i++) {
                            fileWriter.write(wList.get(i).getId() + sc + (wList.get(i).getBatchCode() == null ? "" : wList.get(i).getBatchCode()) + sc
                                    + (wList.get(i).getInboundTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getInboundTime())) + sc + (wList.get(i).getIsOccupiedInt() == null ? "" : wList.get(i).getIsOccupiedInt()) + sc
                                    + (wList.get(i).getOccupationCode() == null ? "" : wList.get(i).getOccupationCode()) + sc + (wList.get(i).getOwner() == null ? "" : wList.get(i).getOwner()) + sc
                                    + (wList.get(i).getQuantity() == null ? "" : wList.get(i).getQuantity()) + sc + (wList.get(i).getSkuCost() == null ? "" : wList.get(i).getSkuCost()) + sc
                                    + (wList.get(i).getDistrictId() == null ? "" : wList.get(i).getDistrictId()) + sc + (wList.get(i).getLocationId() == null ? "" : wList.get(i).getLocationId()) + sc
                                    + (wList.get(i).getWhOuId() == null ? "" : wList.get(i).getWhOuId()) + sc + (wList.get(i).getSkuId() == null ? "" : wList.get(i).getSkuId()) + sc + (wList.get(i).getStatusId() == null ? "" : wList.get(i).getStatusId())
                                    + sc + (wList.get(i).getVersionDate() == null ? "" : wList.get(i).getVersionDate()) + sc + (wList.get(i).getProductionDate() == null ? "" : wList.get(i).getProductionDate()) + sc
                                    + (wList.get(i).getValidDate() == null ? "" : wList.get(i).getValidDate()) + sc + (wList.get(i).getExpireDate() == null ? "" : wList.get(i).getExpireDate()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys
                                    + "\n");
                        }
                    } else {
                        // 没数据写个对应商品渠道删除信息
                        fileWriter.write(sc + sc + sc + sc + sc + sl.getOwner() + sc + 0 + sc + sc + sc + sc + sc + sl.getSkuId() + sc + sc + sc + sc + sc + sc + dateTimeString + "001" + sc + "2" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_bi_inv_sku_barcode
     */
    @Override
    public void findEdwTbiInvSkuBarcode(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            String dateTimeString = dateTime;
            String fileName = "t_bi_inv_sku_barcode_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            List<SkuBarcodeCommand> wList = skuBarcodeDao.findEdwTbiInvSkuBarcode(new BeanPropertyRowMapper<SkuBarcodeCommand>(SkuBarcodeCommand.class));
            if (wList.size() > 0) {
                for (int i = 0; i < wList.size(); i++) {
                    if ((wList.size() - 1) == i) {
                        fileWriter.write(wList.get(i).getId() + sc + (wList.get(i).getBarcode() == null ? "" : wList.get(i).getBarcode()) + sc + (wList.get(i).getSkuId() == null ? "" : wList.get(i).getSkuId()) + sc
                                + (wList.get(i).getCustomerId() == null ? "" : wList.get(i).getCustomerId()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(wList.get(i).getId() + sc + (wList.get(i).getBarcode() == null ? "" : wList.get(i).getBarcode()) + sc + (wList.get(i).getSkuId() == null ? "" : wList.get(i).getSkuId()) + sc
                                + (wList.get(i).getCustomerId() == null ? "" : wList.get(i).getCustomerId()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }
    }

    /**
     * t_wh_sta_picking_list
     */
    @Override
    public void findEdwTwhStaPickingList(String locationUploadPath, FileWriter fileWriter, String dateTime) throws Exception {
        try {
            String dateTimeString = dateTime;
            String fileName = "t_wh_sta_picking_list_" + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT;
            fileWriter = new FileWriter(locationUploadPath + "/" + fileName);
            List<PickingListCommand> wList = pickingListDao.findEdwTwhStaPickingList(new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
            if (wList.size() > 0) {
                for (int i = 0; i < wList.size(); i++) {
                    if ((wList.size() - 1) == i) {
                        fileWriter
                                .write(wList.get(i).getId() + sc + (wList.get(i).getCheckedBillCount() == null ? "" : wList.get(i).getCheckedBillCount()) + sc + (wList.get(i).getCheckedSkuQty() == null ? "" : wList.get(i).getCheckedSkuQty()) + sc
                                        + (wList.get(i).getCheckedTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getCheckedTime())) + sc + (wList.get(i).getCode() == null ? "" : wList.get(i).getCode()) + sc
                                        + (wList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getCreateTime())) + sc
                                        + (wList.get(i).getExecutedTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getExecutedTime())) + sc
                                        + (wList.get(i).getPickingTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getPickingTime())) + sc + (wList.get(i).getPlanBillCount() == null ? "" : wList.get(i).getPlanBillCount())
                                        + sc + (wList.get(i).getPlanSkuQty() == null ? "" : wList.get(i).getPlanSkuQty()) + sc + (wList.get(i).getSortingModeInt() == null ? "" : wList.get(i).getSortingModeInt()) + sc
                                        + (wList.get(i).getStatusInt() == null ? "" : wList.get(i).getStatusInt()) + sc + (wList.get(i).getCrtUserName() == null ? "" : wList.get(i).getCrtUserName()) + sc
                                        + (wList.get(i).getOperUserName() == null ? "" : wList.get(i).getOperUserName()) + sc + (wList.get(i).getWhid() == null ? "" : wList.get(i).getWhid()) + sc
                                        + (wList.get(i).getOutputCount() == null ? "" : wList.get(i).getOutputCount()) + sc + (wList.get(i).getIntCheckMode() == null ? "" : wList.get(i).getIntCheckMode()) + sc
                                        + (wList.get(i).getLpcode() == null ? "" : wList.get(i).getLpcode()) + sc + (wList.get(i).getFlagName() == null ? "" : wList.get(i).getFlagName()) + sc
                                        + (wList.get(i).getTransTypeInt() == null ? "" : wList.get(i).getTransTypeInt()) + sc + (wList.get(i).getTransTimeTypeInt() == null ? "" : wList.get(i).getTransTimeTypeInt()) + sc
                                        + (wList.get(i).getWhStatusInt() == null ? "" : wList.get(i).getWhStatusInt()) + sc + (wList.get(i).getWhTypeInt() == null ? "" : wList.get(i).getWhTypeInt()) + sc
                                        + (wList.get(i).getInvoiceNum() == null ? "" : wList.get(i).getInvoiceNum()) + sc + (wList.get(i).getIsPPP() == null ? "" : wList.get(i).getIsPPP()) + sc
                                        + (wList.get(i).getIsPEB() == null ? "" : wList.get(i).getIsPEB()) + sc + (wList.get(i).getIsInvoiceInt() == null ? "" : wList.get(i).getIsInvoiceInt()) + sc
                                        + (wList.get(i).getCity() == null ? "" : wList.get(i).getCity()) + sc + (wList.get(i).getToLocation() == null ? "" : wList.get(i).getToLocation()) + sc
                                        + (wList.get(i).getSpecialTypeInt() == null ? "" : wList.get(i).getSpecialTypeInt()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys);
                    } else {
                        fileWriter.write(wList.get(i).getId() + sc + (wList.get(i).getCheckedBillCount() == null ? "" : wList.get(i).getCheckedBillCount()) + sc + (wList.get(i).getCheckedSkuQty() == null ? "" : wList.get(i).getCheckedSkuQty()) + sc
                                + (wList.get(i).getCheckedTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getCheckedTime())) + sc + (wList.get(i).getCode() == null ? "" : wList.get(i).getCode()) + sc
                                + (wList.get(i).getCreateTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getCreateTime())) + sc
                                + (wList.get(i).getExecutedTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getExecutedTime())) + sc
                                + (wList.get(i).getPickingTime() == null ? "" : getFormateDateToData("yyyy-MM-dd HH:mm:ss", wList.get(i).getPickingTime())) + sc + (wList.get(i).getPlanBillCount() == null ? "" : wList.get(i).getPlanBillCount()) + sc
                                + (wList.get(i).getPlanSkuQty() == null ? "" : wList.get(i).getPlanSkuQty()) + sc + (wList.get(i).getSortingModeInt() == null ? "" : wList.get(i).getSortingModeInt()) + sc
                                + (wList.get(i).getStatusInt() == null ? "" : wList.get(i).getStatusInt()) + sc + (wList.get(i).getCrtUserName() == null ? "" : wList.get(i).getCrtUserName()) + sc
                                + (wList.get(i).getOperUserName() == null ? "" : wList.get(i).getOperUserName()) + sc + (wList.get(i).getWhid() == null ? "" : wList.get(i).getWhid()) + sc
                                + (wList.get(i).getOutputCount() == null ? "" : wList.get(i).getOutputCount()) + sc + (wList.get(i).getIntCheckMode() == null ? "" : wList.get(i).getIntCheckMode()) + sc
                                + (wList.get(i).getLpcode() == null ? "" : wList.get(i).getLpcode()) + sc + (wList.get(i).getFlagName() == null ? "" : wList.get(i).getFlagName()) + sc
                                + (wList.get(i).getTransTypeInt() == null ? "" : wList.get(i).getTransTypeInt()) + sc + (wList.get(i).getTransTimeTypeInt() == null ? "" : wList.get(i).getTransTimeTypeInt()) + sc
                                + (wList.get(i).getWhStatusInt() == null ? "" : wList.get(i).getWhStatusInt()) + sc + (wList.get(i).getWhTypeInt() == null ? "" : wList.get(i).getWhTypeInt()) + sc
                                + (wList.get(i).getInvoiceNum() == null ? "" : wList.get(i).getInvoiceNum()) + sc + (wList.get(i).getIsPPP() == null ? "" : wList.get(i).getIsPPP()) + sc + (wList.get(i).getIsPEB() == null ? "" : wList.get(i).getIsPEB())
                                + sc + (wList.get(i).getIsInvoiceInt() == null ? "" : wList.get(i).getIsInvoiceInt()) + sc + (wList.get(i).getCity() == null ? "" : wList.get(i).getCity()) + sc
                                + (wList.get(i).getToLocation() == null ? "" : wList.get(i).getToLocation()) + sc + (wList.get(i).getSpecialTypeInt() == null ? "" : wList.get(i).getSpecialTypeInt()) + sc + dateTimeString + "001" + sc + "3" + sc + sourSys
                                + "\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            } else {
                // 没数据写个空文件
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            log.error("", e);
            fileWriter.close();
            throw new BusinessException();
        }

    }


    public String getFormateDateToData(String s, Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(s);
        String date = sdf.format(d);
        return date;
    }


}
