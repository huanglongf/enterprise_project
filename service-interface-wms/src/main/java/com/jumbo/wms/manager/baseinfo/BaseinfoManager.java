/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not license
 * agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.manager.baseinfo;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jumbo.rmi.warehouse.RmiChannelInfo;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.AdPackage;
import com.jumbo.wms.model.LitreSingle;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Company;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.District;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import com.jumbo.wms.model.baseinfo.LicensePlate;
import com.jumbo.wms.model.baseinfo.OperationCenter;
import com.jumbo.wms.model.baseinfo.SkuDeclarationCommand;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.VehicleStandard;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.command.WarehouseCoverageAreaRefCommand;
import com.jumbo.wms.model.command.automaticEquipment.ZoonCommand;
import com.jumbo.wms.model.pda.PdaLocationType;
import com.jumbo.wms.model.pda.PdaLocationTypeCommand;
import com.jumbo.wms.model.pda.PdaSkuLocTypeCapCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleCommand;
import com.jumbo.wms.model.warehouse.AutoPlConfigCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLineCommand;
import com.jumbo.wms.model.warehouse.SfNextMorningConfig;
import com.jumbo.wms.model.warehouse.WhAddStatusSource;
import com.jumbo.wms.model.warehouse.WhAddStatusSourceCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;


public interface BaseinfoManager extends BaseManager {



    PdaLocationType findbyId(Long id);

    InterfaceSecurityInfo findInterfaceSecurityInfo(String organizationCode);

    /**
     * 仓库设置客户
     * 
     * @param whouid
     * @param customerId
     */
    void warehouseSetCustomer(Long whouid, Long customerId);

    /**
     * 根据主键查找公司信息
     * 
     * @param id
     * @return
     */
    Company findCompanyByPK(Long id);

    /**
     * 根据组织id查找公司信息
     * 
     * @param ouid
     * @return
     */
    Company findCompanyByOuId(Long ouid);

    /**
     * 根据公司id查找所有公司
     * 
     * @param ouid
     * @return
     */
    List<OperationUnitCommand> findWarehouseByCompany(Long companyId, Long shopId);

    /**
     * 根据店铺id查找所有公司
     * 
     * @param ouid
     * @return
     */
    List<OperationUnitCommand> findWarehouseByShop(Long shopId);

    /**
     * 查寻店铺id物流供应商
     * 
     * @param ouid
     * @return
     */
    List<TransportatorCommand> findTransportatorByShop(Long shopId);

    /**
     * update Company
     * 
     * @param company
     * @param ou
     * @return
     */
    Company updateCompany(Company company, OperationUnit ou);

    /**
     * save Company
     * 
     * @param company
     * @return
     */
    Company save(Company company);

    // /**
    // * 根据公司id查找，本公司下的所有店铺CompanyShop列表
    // *
    // * @param companyId
    // * @return
    // */
    // List<CompanyShopCommand> findShopListByCompany(Long companyId);

    /**
     * 根据仓库id查找本仓库所属的公司组织
     * 
     * @param warehouseOuId
     * @return
     */
    OperationUnit findCompanyByWarehouse(Long warehouseOuId);

    // OperationCenter
    /**
     * 根据主键查找运营中心信息
     * 
     * @param id
     * @return
     */
    OperationCenter findOperationCenterByPK(Long id);

    /**
     * update OperationCenter
     * 
     * @param operationCenter
     * @param ou
     * @return
     */
    OperationCenter updateOperationCenter(OperationCenter operationCenter, OperationUnit ou);

    /**
     * save OperationCenter
     * 
     * @param operationCenter
     * @return
     */
    OperationCenter save(OperationCenter operationCenter);

    /**
     * 根据组织id查找运营中心信息
     * 
     * @param id
     * @return
     */
    OperationCenter findOperationCenterByOuId(Long id);

    // Warehouse
    /**
     * 根据主键查找仓库信息
     * 
     * @param id
     * @return
     */
    Warehouse findWarehouseByPK(Long id);

    /**
     * update Warehouse
     * 
     * @param warehouse
     * @return
     */
    String updateWarehouse(Warehouse warehouse, OperationUnit ou, WhAddStatusSource wss, String transIds, AutoPlConfigCommand apc);

    /**
     * 
     * @param warehouse
     * @return
     */
    Warehouse save(Warehouse warehouse);

    /**
     * 根据组织id查找仓库信息
     * 
     * @param id
     * @return
     */
    Warehouse findWarehouseByOuId(Long id);

    /**
     * 查找当前仓库所属的运营中心
     * 
     * @param ouid
     * @return
     */
    OperationUnit getByPrimaryKey(Long ouid);


    /**
     * 查询所有物流
     * 
     * @return
     */
    List<Transportator> findAllTransportator();

    /**
     * 
     * @param shopidList
     * @param id
     */
    void addOrRemoveFromCompany(List<Long> shopidList, Long ouid, String type);

    /**
     * 创建商品
     * 
     * @param skuCommand
     * @param skuTemplateId
     * @param shopId
     * @param extPropsMap
     * @return
     */
    void sendMsgToOmsCreateSku(String extCode2, String vmiCode);

    /**
     * 创建商品-不捕获异常，抛给调用处由调用处捕获处理
     * 
     * @param skuCommand
     * @param skuTemplateId
     * @param shopId
     * @param extPropsMap
     * @return
     */
    void sendMsgToOmsCreateSkuTE(String extCode2, String vmiCode);

    List<BiChannel> queryShopListMultiCheck(Long id);

    List<BiChannel> queryAllShopList();

    /**
     * 根据绑定的默认收货仓库ID查询所有关联店铺
     * 
     * @param id
     * @return List<BiChannel>
     * @throws
     */
    List<BiChannel> queryShopListByDefaultOuId(Long ouId);


    String getBiWarehouseStatus(Long ouId);

    /**
     * RMI接口通过渠道编码获取相关信息
     * 
     * @param channelCode
     * @return
     */
    RmiChannelInfo findRmiChannelRefWarehouse(String channelCode);

    /**
     * Rmi接口所有渠道信息
     * 
     * @param customerCode
     * @return
     */
    List<RmiChannelInfo> findAllRmiChannelInofByCustomer(String customerCode);

    /**
     * 查询所有关联的快递
     */
    List<Transportator> findAllTransRef(OperationUnit ou);

    /**
     * 获取配货清单自动创建配置bin.hu
     */
    AutoPlConfigCommand getAotoPlConfig(Long ouid);

    List<AutoPickingListRoleCommand> findAutoPickingListRoleList();

    /**
     * 查询所有客户列表
     * 
     * @return
     */
    public List<Customer> findAllCustomer();

    /**
     * 查询客户
     * 
     * @param whouid
     * @return
     */
    public Customer getByWhouid(Long whouid);

    public List<District> findAllProvince();

    public List<District> findCity(String province);

    public List<District> findDistrictsByCity(String province, String city);

    public List<WhAddStatusSourceCommand> findWhStatusSourceListSql(int type);

    public InterfaceSecurityInfo findUseringByName(String username, String source, Date date);

    public List<TransportatorCommand> getTransByPlId(Long pickingListId);

    /**
     * 仓库覆盖区域导入
     * 
     * @param file
     * @return ReadStatus
     * @throws
     */
    public ReadStatus importCoverageArea(File file, Long ouId, Long creatorId);

    /**
     * 仓库覆盖区域覆盖导入
     * 
     * @param file
     * @return ReadStatus
     * @throws
     */
    public ReadStatus coverImportCoverageArea(File file, Long ouId, Long creatorId);

    /**
     * 根据ouId查询所有的覆盖区域
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @return Pagination<WarehouseCoverageAreaRef>
     * @throws
     */
    Pagination<WarehouseCoverageAreaRefCommand> findCoverageAreaByOuId(int start, int pageSize, Long ouId, Sort[] sorts);

    /**
     * 删除覆盖区域
     * 
     * @param areaId
     * @param ouId void
     * @throws
     */
    public void deleteCoverageArea(Long areaId, Long ouId);

    /**
     * 查询渠道残次信息
     * 
     * @return
     */
    Pagination<BiChannelImperfectCommand> findWarehouseImperfectlList(int start, int pageSize, Long ouId, Sort[] sorts);

    /**
     * 查询渠道残次信息
     * 
     * @return
     */
    List<BiChannelImperfectCommand> findWarehouseImperfectl(Long ouId);

    /**
     * 查询渠道残次明细信息
     * 
     * @return
     */
    Pagination<BiChannelImperfectLineCommand> findWarehouseImperfectlLineList(int start, int pageSize, Long imperfectId, Long id, Sort[] sorts);

    /**
     * 导入类型
     * 
     * @param file
     * @param cid
     * @return
     */
    ReadStatus addSkuImperfect(File file, Long ouId);

    /**
     * 根据仓库查询配置的SF次晨达业务
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @return
     */
    Pagination<SfNextMorningConfig> findSfNextMorningConfigByOuId(int start, int pageSize, Long id, Sort[] sorts);

    /**
     * 根据仓库查询配置的NIKE当日达业务
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @return
     */
    Pagination<LitreSingle> findNIKETodaySendConfigByOuId(int start, int pageSize, Long id, Sort[] sorts);

    Pagination<AdPackage> findAdPackageByOuIdPage(int start, int pageSize, Long id, Sort[] sorts);

    void saveNikeConfig(LitreSingle ls, Long ouId);

    List<LitreSingle> findLitreSingleByOuId(Long ouId);

    List<AdPackage> findAdPackageByOuId(Long ouId);

    ReadStatus importNikeTodaySendConfig(File fileSFC, Long id);

    ReadStatus importAdPackage(File fileSFC, Long id, String userName);

    /**
     * 验证与保存库位类型
     */
    String saveLocationType(PdaLocationType pdaLocationType, User user, OperationUnit op);

    /**
     * 验证与保存库位类型绑定中间表
     */
    String saveLocationTypeBinding(PdaLocationTypeCommand pdaLocationTypeCommand, User user, OperationUnit op);

    /**
     * 分页查询库位类型
     * 
     * @param whId
     * @param pickingListId
     * @param sorts
     * @param rowMapper
     * @return
     */
    Pagination<PdaLocationTypeCommand> getPdaLocationType(int start, int pagesize, long ouId, PdaLocationTypeCommand locationTypeCommand, Sort[] sorts);

    /**
     * 分页查询库位类型绑定
     */
    Pagination<PdaLocationTypeCommand> getPdaLocationTypeBinding(int start, int pagesize, long ouId, PdaLocationTypeCommand locationTypeCommand, Sort[] sorts);


    /**
     * 验证与修改库位类型
     */
    String updateLocationType(PdaLocationType pdaLocationType, User user, OperationUnit op);

    /**
     * 验证与批量删除库位类型
     */
    String delLocationType(List<String> ids, User user);

    /**
     * 查询库位类型LIST
     */
    List<PdaLocationType> getPdaLocationTypeList(OperationUnit op);

    /**
     * 查询库位类型（name,code,ouId)
     */
    PdaLocationType queryLocationTypeByNameCodeOuId(PdaLocationType pdaLocationType, OperationUnit op);

    /**
     * 批量删除库位类型
     */
    String delLocationTypeBinding(List<String> ids, User user);

    /**
     * 读取文件批量插入库位类型绑定
     * 
     * @throws Exception
     */
    ReadStatus saveLocationTypeBindingImport(File coachFile, OperationUnit op) throws Exception;

    Pagination<PdaSkuLocTypeCapCommand> getPdaSkuLocTypeCapBinding(int start, int pagesize, long ouId, PdaSkuLocTypeCapCommand pdaSkuLocTypeCapCommand, Sort[] sorts);

    void delSkuLocTypeCap(List<String> ids);

    ReadStatus savePdaSkuLocTypeCapeBindingImport(File file, OperationUnit op) throws Exception;

    ReadStatus saveQsSkuBindingImport(File file, OperationUnit op, User user) throws Exception;

    ReadStatus saveSkuCountryOfOriginImport(File file, OperationUnit op, User user) throws Exception;

    Boolean savePdaSkuLocTypeCapBinding(PdaSkuLocTypeCapCommand pdaSkuLocTypeCapCommand, OperationUnit op);

    String zdhReStoreData(String plCode);

    String zdhReStoreStatus(String plCode);

    Pagination<ZoonCommand> queryZoonSort(int start, int pageSize, Long id, ZoonCommand zoonCommand, Sort[] sorts);

    String commitZoon(ZoonCommand zoonCommand, Long id);

    Integer skuWeightCount(Long id);

    Pagination<VehicleStandard> findVehicleStandardList(int start, int pageSize, String standardCode, BigDecimal vehicleVolume1, BigDecimal vehicleVolume2, Sort[] sorts);

    Pagination<LicensePlate> findLicensePlateList(int start, int pageSize, String vehicleCode, String licensePlateNumber, String vehicleStandard, BigDecimal vehicleVolume1, BigDecimal vehicleVolume2, Date useTime, Sort[] sorts);

    public String updateVehicleStandard(VehicleStandard vehicleStandard);

    public String updateLicensePlate(LicensePlate licensePlate, Long ouid);

    public void deleteVehicleStandard(Long id);

    public void deleteLicensePlateById(Long id);

    Pagination<SkuDeclarationCommand> findGoodsList(int start, int pageSize, String owner, String upc, String hsCode, String skuCode, String style, String color, String skuSize, int isDiscount, int status, String skuName, int isService, Sort[] sorts);

    public void deletefindGoodsListById(Long id);

    public void pushGoods(Long id);

}
