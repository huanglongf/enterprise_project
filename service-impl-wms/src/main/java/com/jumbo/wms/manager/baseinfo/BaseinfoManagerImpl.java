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

package com.jumbo.wms.manager.baseinfo;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.automaticEquipment.ZoonDao;
import com.jumbo.dao.baseinfo.CompanyDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.DistrictDao;
import com.jumbo.dao.baseinfo.InterfaceSecurityInfoDao;
import com.jumbo.dao.baseinfo.LicensePlateDao;
import com.jumbo.dao.baseinfo.MsgOmsSkuLogDao;
import com.jumbo.dao.baseinfo.OperationCenterDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.VehicleStandardDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.lf.ZdhPiciDao;
import com.jumbo.dao.lf.ZdhPiciLineDao;
import com.jumbo.dao.pda.PdaLocationTypeBindingDao;
import com.jumbo.dao.pda.PdaLocationTypeDao;
import com.jumbo.dao.pda.PdaSkuLocTypeCapDao;
import com.jumbo.dao.system.SysSchedulerTaskDao;
import com.jumbo.dao.warehouse.AdPackageDao;
import com.jumbo.dao.warehouse.AutoPickingListRoleDao;
import com.jumbo.dao.warehouse.AutoPlConfigDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.BiChannelImperfectDao;
import com.jumbo.dao.warehouse.BiChannelImperfectLineDao;
import com.jumbo.dao.warehouse.BiWarehouseAddStatusDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.LitreSingleDao;
import com.jumbo.dao.warehouse.QsSkuDao;
import com.jumbo.dao.warehouse.SfNextMorningConfigDao;
import com.jumbo.dao.warehouse.SkuDeclarationDao;
import com.jumbo.dao.warehouse.SkuOriginDeclarationDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WarehouseCoverageAreaRefDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhAddStatusSourceDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.rmi.warehouse.RmiChannelInfo;
import com.jumbo.rmi.warehouse.RmiWarehouseInfo;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.AdPackage;
import com.jumbo.wms.model.LitreSingle;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Company;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.District;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import com.jumbo.wms.model.baseinfo.LicensePlate;
import com.jumbo.wms.model.baseinfo.MongoCarService;
import com.jumbo.wms.model.baseinfo.MsgOmsSkuLog;
import com.jumbo.wms.model.baseinfo.OperationCenter;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuDeclarationCommand;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.VehicleStandard;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.SkuCountryOfOriginCommand;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.command.WarehouseCoverageAreaRefCommand;
import com.jumbo.wms.model.command.automaticEquipment.ZoonCommand;
import com.jumbo.wms.model.lf.ZdhPici;
import com.jumbo.wms.model.lf.ZdhPiciLineCommand;
import com.jumbo.wms.model.pda.PdaLocationType;
import com.jumbo.wms.model.pda.PdaLocationTypeBinding;
import com.jumbo.wms.model.pda.PdaLocationTypeCommand;
import com.jumbo.wms.model.pda.PdaSkuLocTypeCap;
import com.jumbo.wms.model.pda.PdaSkuLocTypeCapCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleCommand;
import com.jumbo.wms.model.warehouse.AutoPlConfig;
import com.jumbo.wms.model.warehouse.AutoPlConfigCommand;
import com.jumbo.wms.model.warehouse.AutoPlStatus;
import com.jumbo.wms.model.warehouse.BiChannelImperfect;
import com.jumbo.wms.model.warehouse.BiChannelImperfectCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLineCommand;
import com.jumbo.wms.model.warehouse.BiWarehouseAddStatus;
import com.jumbo.wms.model.warehouse.QsSku;
import com.jumbo.wms.model.warehouse.QsSkuCommand;
import com.jumbo.wms.model.warehouse.SfNextMorningConfig;
import com.jumbo.wms.model.warehouse.WarehouseCoverageAreaRef;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WhAddStatusMode;
import com.jumbo.wms.model.warehouse.WhAddStatusSource;
import com.jumbo.wms.model.warehouse.WhAddStatusSourceCommand;
import com.jumbo.wms.model.warehouse.WhAddTypeMode;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;
import loxia.utils.PropListCopyable;
import loxia.utils.PropertyUtil;

@Transactional
@Service("baseinfoManager")
public class BaseinfoManagerImpl extends BaseManagerImpl implements BaseinfoManager {

    /**
     * 
     */
    private static final long serialVersionUID = 3203997905298638333L;

    @Resource(name = "locationTypeBindingInstructionReader")
    private ExcelReader locationTypeBindingInstructionReader;
    @Resource(name = "skuLocTypeCapBindingInstructionReader")
    private ExcelReader skuLocTypeCapBindingInstructionReader;
    @Resource(name = "qsSkuBindingInstructionReader")
    private ExcelReader qsSkuBindingInstructionReader;
    @Resource(name = "skuCountryOfOriginInstructionReader")
    private ExcelReader skuCountryOfOriginInstructionReader;
    @Autowired
    private DistrictDao districtDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private PdaLocationTypeDao pdaLocationTypeDao;
    @Autowired
    private PdaLocationTypeBindingDao pdaLocationTypeBindingDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private MsgOmsSkuLogDao msgOmsSkuLogDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private OperationCenterDao operationCenterDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private BiWarehouseAddStatusDao basDao;
    @Autowired
    private WhAddStatusSourceDao wassDao;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private AutoPlConfigDao apDao;
    @Autowired
    private AutoPickingListRoleDao aplrDao;
    @Autowired
    protected InterfaceSecurityInfoDao interfaceSecurityInfoDao;
    @Autowired
    private WarehouseCoverageAreaRefDao coverageAreaRefDao;
    @Resource(name = "whCoverageAreaReader")
    private ExcelReader coverageAreaReader;
    @Autowired
    private SfNextMorningConfigDao sfNextMorningConfigDao;
    @Autowired
    private BiChannelImperfectLineDao biChannelImperfectLineDao;
    @Autowired
    private BiChannelImperfectDao biChannelImperfectDao;
    @Autowired
    private LitreSingleDao litreSingleDao;
    @Resource(name = "addSkuImperfect")
    private ExcelReader addSkuImperfect;
    @Resource(name = "adPackageImport")
    private ExcelReader adPackageImport;
    @Resource(name = "nikeTodaySendConfigReader")
    private ExcelReader nikeTodaySendConfigReader;
    @Autowired
    private PdaSkuLocTypeCapDao pdaSkuLocTypeCapDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private AdPackageDao adPackageDao;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private QsSkuDao qsSkuDao;
    @Autowired
    private SysSchedulerTaskDao sysTask;
    @Value("${zk.notice.task.wms}")
    private String znode;
    @Value("${zk.task.node}")
    private int taskNode;
    @Autowired
    private ZkClient zkClient;
    @Autowired
    private ZdhPiciDao zdhPiciDao;
    @Autowired
    private ZdhPiciLineDao zdhPiciLineDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private ZoonDao zoonDao;
    @Autowired
    private VehicleStandardDao vehicleStandardDao;
    @Autowired
    private LicensePlateDao licensePlateDao;
    @Autowired
    private SkuDeclarationDao skuDeclarationDao;
    @Autowired
    private SkuOriginDeclarationDao skuOriginDeclarationDao;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    public InterfaceSecurityInfo findInterfaceSecurityInfo(String organizationCode) {
        // TEST
        return interfaceSecurityInfoDao.findByName(organizationCode, new Date());
    }

    public void warehouseSetCustomer(Long whouid, Long customerId) {
        Customer c = customerDao.getByPrimaryKey(customerId);
        if (c == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Warehouse wh = warehouseDao.getByOuId(whouid);
        if (wh.getCustomer() == null) {
            wh.setCustomer(c);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 根据主键查找公司信息
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Company findCompanyByPK(Long companyid) {
        return companyDao.getByPrimaryKey(companyid);
    }

    /**
     * 根据组织id查找公司信息
     * 
     * @param ouid
     * @return
     */
    @Transactional(readOnly = true)
    public Company findCompanyByOuId(Long ouid) {
        return companyDao.getByOuId(ouid);
    }

    /**
     * 
     */
    @Transactional(readOnly = true)
    public List<OperationUnitCommand> findWarehouseByCompany(Long companyId, Long shopId) {
        return operationUnitDao.findWarehouseByCompany(companyId, shopId, new BeanPropertyRowMapperExt<OperationUnitCommand>(OperationUnitCommand.class));
    }

    /**
     * 查找仓库
     */
    @Transactional(readOnly = true)
    public List<OperationUnitCommand> findWarehouseByShop(Long shopId) {
        return operationUnitDao.findWarehouseByShop(shopId, new BeanPropertyRowMapperExt<OperationUnitCommand>(OperationUnitCommand.class));
    }

    /**
     * 查找物流
     */
    @Transactional(readOnly = true)
    public List<TransportatorCommand> findTransportatorByShop(Long shopId) {
        return transportatorDao.findTransportatorListByShop(shopId, new BeanPropertyRowMapperExt<TransportatorCommand>(TransportatorCommand.class));
    }

    /**
     * 更新公司基本信息
     * 
     * @param company
     * @param ou
     * @return
     */
    @Transactional
    public Company updateCompany(Company company, OperationUnit ou) {
        if (ou == null) {
            throw new RuntimeException("Current OperationUnit is null...");
        }
        OperationUnit o = operationUnitDao.getByPrimaryKey(ou.getId());
        Company entity = findCompanyByOuId(ou.getId());
        if (entity == null) {
            company.setOu(ou);
            company = companyDao.save(company);
        } else {
            try {
                PropertyUtil.copyProperties(company, entity, new PropListCopyable("taxNo", "bankName", "bankAccount", "legalRep", "scopeOfBusiness", "k3Code"));
                entity.setOu(o);
            } catch (Exception e) {
                log.error("", e);
                throw new RuntimeException("Failed to construct company...");
            }
            company = companyDao.save(entity);
        }
        try {
            PropertyUtil.copyProperties(ou, o, new PropListCopyable("name", "comment"));
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("Failed to construct Operation unit...");
        }
        o = operationUnitDao.save(o);
        company.setOu(o);
        return company;
    }

    /**
     * save company
     * 
     * @param company
     * @return
     */
    public Company save(Company company) {
        return companyDao.save(company);
    }

    public List<Transportator> findAllTransportator() {
        return transportatorDao.findAll();
    }

    /**
     * 根据仓库id查找本仓库所属的公司组织
     * 
     * @param warehouseOuId
     * @return
     */
    @Transactional(readOnly = true)
    public OperationUnit findCompanyByWarehouse(Long warehouseOuId) {
        // 仓库->运营中心->公司
        return operationUnitDao.getByPrimaryKey(warehouseOuId).getParentUnit().getParentUnit();
    }

    /**
     * 
     * 根据主键查找运营中心信息
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public OperationCenter findOperationCenterByPK(Long id) {
        return operationCenterDao.getByPrimaryKey(id);
    }

    /**
     * 根据组织id查找运营中心信息
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public OperationCenter findOperationCenterByOuId(Long ouid) {
        return operationCenterDao.getByOuId(ouid);
    }

    /**
     * 运营中心基本信息修改
     * 
     * @param operationCenter
     * @param ou
     * @return
     */
    @Transactional
    public OperationCenter updateOperationCenter(OperationCenter operationCenter, OperationUnit ou) {
        if (ou == null) {
            throw new RuntimeException("Current OperationUnit is null...");
        }
        OperationUnit o = operationUnitDao.getByPrimaryKey(ou.getId());
        OperationCenter entity = findOperationCenterByOuId(ou.getId());
        if (entity == null) {
            operationCenter.setOu(ou);
            operationCenter = operationCenterDao.save(operationCenter);
        } else {
            try {
                PropertyUtil.copyProperties(operationCenter, entity, new PropListCopyable("pic", "picContact"));
                entity.setOu(o);
            } catch (Exception e) {
                log.error("", e);
                throw new RuntimeException("Failed to construct Operation Center...");
            }
            operationCenter = operationCenterDao.save(entity);
        }
        try {
            PropertyUtil.copyProperties(ou, o, new PropListCopyable("name", "comment"));
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("Failed to construct Operationunit...");
        }
        o = operationUnitDao.save(o);
        operationCenter.setOu(o);
        return operationCenter;
    }

    /**
     * save OperationCenter
     * 
     * @param operationCenter
     * @return
     */
    public OperationCenter save(OperationCenter operationCenter) {
        return operationCenterDao.save(operationCenter);
    }

    // Warehouse baeInfo
    /**
     * 根据主键查找仓库信息
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Warehouse findWarehouseByPK(Long id) {
        return warehouseDao.getByPrimaryKey(id);
    }

    /**
     * update Warehouse
     * 
     * @param warehouse
     * @return
     */
    @Transactional
    public String updateWarehouse(Warehouse warehouse, OperationUnit ou, WhAddStatusSource wss, String transIds, AutoPlConfigCommand apc) {
        BiWarehouseAddStatus bi;
        // Warehouse warehouse1 = warehouse;
        // 保存定时节点ID
        String taskValue = null;
        if (ou == null) {
            throw new RuntimeException("Current OperationUnit is null...");
        }
        OperationUnit o = operationUnitDao.getByPrimaryKey(ou.getId());
        Warehouse entity = findWarehouseByOuId(ou.getId());

        if (entity == null) {
            warehouse.setOu(ou);
            warehouse.setLastModifyTime(new Date());
            warehouse = warehouseDao.save(warehouse);
        } else {
            // Warehouse w = warehouseDao.getByPrimaryKey(entity.getId());
            try {
                PropertyUtil.copyProperties(warehouse, entity, new PropListCopyable("pic", "picContact", "phone", "fax", "otherContact1", "otherContact2", "otherContact3", "size", "availSize", "workerNum", "departure", "opMode", "manageMode",
                        "isNeedWrapStuff", "address", "isManualWeighing", "isCheckedBarcode", "isSfOlOrder", "province", "city", "district", "zipcode", "sfWhCode", "cityCode", "isMqInvoice", "invoiceTaxMqCode", "isEmsOlOrder", "isOlSto",
                        "isSupportSecKill", "isSupportPackageSku", "isNotExpireDate", "isNotSn", "isDhlOlOrder", "isCheckPickingStatus", "isCheckConsumptiveMaterial", "isAutoOcp", "ocpErrorLimit", "skuQty", "isThirdPartyPaymentSF", "isTransMust",
                        "orderCountLimit", "seedingAreaCode", "checkingAreaCode", "idx2MaxLimit", "autoPickinglistLimit", "skuNum", "skuTotal", "handLimit", "isSuggest", "sfWhCodeCod", "manpowerPickinglistLimit", "totalPickinglistLimit",
                        "groupWorkbenchCode", "specialWorkbenchCode", "sameDayWorkbenchCode", "nextDayWorkbenchCode", "nextMorningWorkbenchCode", "isSkipWeight","isCartonManager"));
                entity.setOu(o);
            } catch (Exception e) {
                log.error("", e);
                throw new RuntimeException("Failed to construct...");
            }
            entity.setOutboundOrderNum(warehouse.getOutboundOrderNum());
            entity.setIsManpowerConsolidation(warehouse.getIsManpowerConsolidation());
            entity.setIsZtoOlOrder(warehouse.getIsZtoOlOrder());
            entity.setIsTtkOlOrder(warehouse.getIsTtkOlOrder());
            entity.setIsYtoOlOrder(warehouse.getIsYtoOlOrder());
            entity.setIsWxOlOrder(warehouse.getIsWxOlOrder());
            entity.setIsCxcOlOrder(warehouse.getIsCxcOlOrder());
            entity.setIsRfdOlOrder(warehouse.getIsRfdOlOrder());
            entity.setIsImperfect(warehouse.getIsImperfect());
            entity.setIsBigLuxuryWeigh(warehouse.getIsBigLuxuryWeigh());
            entity.setIsAutoWh(warehouse.getIsAutoWh());
            entity.setAutoSeedGroup(warehouse.getAutoSeedGroup());
            entity.setLastModifyTime(new Date());
            entity.setIsAreaOcpInv(warehouse.getIsAreaOcpInv());
            entity.setIsAgv(warehouse.getIsAgv());
            entity.setIsSkipWeight(warehouse.getIsSkipWeight());

            entity.setIsBondedWarehouse(warehouse.getIsBondedWarehouse());
            entity.setIsCartonManager(warehouse.getIsCartonManager());;

            entity.setIsCartonManager(warehouse.getIsCartonManager());;
            entity.setIsBondedWarehouse(warehouse.getIsBondedWarehouse());
            warehouse = warehouseDao.save(entity);
        }
        // // 判断是否启用订单占用区域优先级是否修改 如果修改配置定时&ZK否则不配置
        // if (warehouse.getIsAreaOcpInv() == entity.getIsAreaOcpInv()) {
        // // 是否启用订单占用区域优先级的校验逻辑
        // try {
        // List<Long> idList = sysTask.findSysSchedulerTaskByMethodNameAndWhId(ou.getId(), new
        // SingleColumnRowMapper<Long>(Long.class));
        // if (idList.size() == 0 && warehouse1.getIsAreaOcpInv()) {
        // SysSchedulerTask task = new SysSchedulerTask();
        // task.setArgs(entity.getOu().getId() + "");
        // task.setBeanName("staInventoryOcpThreadPool");
        // task.setCode("queryStaToOcpAeraInvTirgger" + entity.getOu().getId());
        // task.setDescription("作业单占用库存计算区域库存");
        // task.setLifecycle(1);
        // task.setMethodName("queryStaToOcpAeraInv");
        // task.setNeedCompensate(warehouse1.getIsAreaOcpInv() == null ? true :
        // !warehouse1.getIsAreaOcpInv());
        // task.setNode(taskNode);
        // task.setTimeExp("0 0/2 * * * ?");
        // sysTask.save(task);
        // // 触发zk节点
        // // Object object = task.getId();
        // // zkClient.writeData(znode, object);
        // taskValue = task.getId().toString();
        // } else {
        // if (warehouse1.getIsAreaOcpInv() == null) {
        // sysTask.updateSysSchedulerTaskByArgs(entity.getOu().getId() + "", 0);
        // } else {
        // sysTask.updateSysSchedulerTaskByArgs(entity.getOu().getId() + "",
        // warehouse1.getIsAreaOcpInv() ? 0 : 1);
        // }
        // // zk触发
        // for (Long id : idList) {
        // // Object object = id;
        // // zkClient.writeData(znode, object);
        // taskValue = id.toString();
        // }
        // }
        // } catch (Exception e1) {
        // log.error("", e1);
        // }
        // }
        // 先删除该仓库下所有的状态
        basDao.deleteBiWarehouseAddStatus(o.getId());
        bi = new BiWarehouseAddStatus();
        WhAddStatusSourceCommand wssBean = wassDao.getWhStatusSourceByTS(1, WhAddStatusMode.CHECK.getValue(), new BeanPropertyRowMapper<WhAddStatusSourceCommand>(WhAddStatusSourceCommand.class));
        // 如果没有选择角色，默认为type = 1(配货清单) status = 29(待核对)
        // 29为比插入项
        bi.setType(WhAddTypeMode.PICKINGLIST);
        bi.setStatus(WhAddStatusMode.CHECK);// 29待核对
        bi.setSort(wssBean.getSort());
        bi.setOperationUnit(o);
        basDao.save(bi);
        if (wss.getStatusName() != null && !wss.getStatusName().equals("")) {
            // 获取type & status值
            String[] statusValues = wss.getStatusName().subSequence(0, wss.getStatusName().length() - 1).toString().split(",");
            for (int i = 0; i < statusValues.length; i++) {
                bi = new BiWarehouseAddStatus();
                String[] statusValus = statusValues[i].split(" ");
                bi.setStatus(WhAddStatusMode.valueOf(Integer.parseInt(statusValus[0])));
                bi.setType(WhAddTypeMode.valueOf(Integer.parseInt(statusValus[1])));
                bi.setSort(Integer.parseInt(statusValus[2]));
                bi.setOperationUnit(o);
                basDao.save(bi);
            }
        }

        try {
            // 插入自动配货配置数据
            AutoPlConfig apcc = apDao.getAotoPlConfig(ou.getId());
            if (apcc == null) {
                // 无数据 -添加
                if (apc.getStatus() == 1) {
                    // 如果选中自动创建配货清单才去新建配置数据
                    AutoPlConfig ap = new AutoPlConfig();
                    ap.setIntervalMinute(apc.getIntervalMinute());
                    if (apc.getNextExecuteTime() == null || apc.getNextExecuteTime().equals("")) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        calendar.add(Calendar.MINUTE, apc.getIntervalMinute());
                        ap.setNextExecuteTime(calendar.getTime());
                    } else {
                        ap.setNextExecuteTime(FormatUtil.getDate(apc.getNextExecuteTime()));
                    }
                    ap.setAutoPlr(aplrDao.getByPrimaryKey(apc.getAutoPlr()));
                    ap.setCreateTime(new Date());
                    ap.setVersion(new Date());
                    ap.setStatus(AutoPlStatus.NORMAL);
                    ap.setOuId(ou);
                    apDao.save(ap);
                }
            } else {
                AutoPlConfig ap = apDao.getByPrimaryKey(apcc.getId());
                // 有数据 -修改
                if (apc.getStatus() == 1) {
                    // 修改数据
                    ap.setAutoPlr(aplrDao.getByPrimaryKey(apc.getAutoPlr()));
                    ap.setIntervalMinute(apc.getIntervalMinute());
                    if (apc.getNextExecuteTime() == null || apc.getNextExecuteTime().equals("")) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        calendar.add(Calendar.MINUTE, apc.getIntervalMinute());
                        ap.setNextExecuteTime(calendar.getTime());
                    } else {
                        ap.setNextExecuteTime(FormatUtil.getDate(apc.getNextExecuteTime()));
                    }
                    ap.setStatus(AutoPlStatus.NORMAL);
                    apDao.save(ap);
                } else {
                    // 修改状态
                    ap.setStatus(AutoPlStatus.CANCEL);
                    apDao.save(ap);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("autoplconfig error...");
        }

        // 查询是否存在GI区，存在跳过，不存在创建1个CODE=GI的GI区

        try {
            PropertyUtil.copyProperties(ou, o, new PropListCopyable("name", "comment"));
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("Failed to construct...");
        }
        o = operationUnitDao.save(o);
        warehouse.setOu(o);
        // 更新关联快递
        warehouseDao.deleteTransRef(ou.getId());
        if (null != transIds && !"".equals(transIds)) {
            String[] ids = transIds.split(",");
            for (String id : ids) {
                if (null != id && !"".equals(id)) {
                    Long transId = Long.valueOf(id);
                    warehouseDao.insertTransRef(ou.getId(), transId);
                }
            }
        }
        return taskValue;
    }

    /**
     * save
     */
    public Warehouse save(Warehouse warehouse) {
        return warehouseDao.save(warehouse);
    }

    /**
     * 根据组织id查找仓库信息
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Warehouse findWarehouseByOuId(Long ouid) {
        return warehouseDao.getByOuId(ouid);
    }

    @Transactional(readOnly = true)
    public OperationUnit getByPrimaryKey(Long ouid) {
        return operationUnitDao.getByPrimaryKey(ouid);
    }

    @Transactional
    public void addOrRemoveFromCompany(List<Long> shopidList, Long ouid, String type) {
        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMsgToOmsCreateSku(String extCode2, String vmiCode) {
        // 调用OMS创建SKU接口
        try {
            rmi4Wms.skuRequest(extCode2, vmiCode);
            MsgOmsSkuLog skuLog = new MsgOmsSkuLog();
            // 调整逻辑，所有已记录过的SKU不记录日志
            MsgOmsSkuLog obj = msgOmsSkuLogDao.findMsgOmsSkuLogByCode(extCode2);
            if (obj == null) {
                skuLog.setCreateTime(new Date());
                skuLog.setExtCode2(extCode2);
                skuLog.setVmiCode(vmiCode);
                skuLog.setIsMail(0l);
                msgOmsSkuLogDao.save(skuLog);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 创建商品，不捕获异常，抛给调用处由调用处捕获处理
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMsgToOmsCreateSkuTE(String extCode2, String vmiCode) {
        // 调用OMS创建SKU接口
        rmi4Wms.skuRequest(extCode2, vmiCode);
        MsgOmsSkuLog skuLog = new MsgOmsSkuLog();
        // 调整逻辑，所有已记录过的SKU不记录日志
        MsgOmsSkuLog obj = msgOmsSkuLogDao.findMsgOmsSkuLogByCode(extCode2);
        if (obj == null) {
            skuLog.setCreateTime(new Date());
            skuLog.setExtCode2(extCode2);
            skuLog.setVmiCode(vmiCode);
            skuLog.setIsMail(0l);
            msgOmsSkuLogDao.save(skuLog);
        }
    }

    public List<BiChannel> queryShopListMultiCheck(Long ouId) {
        return biChannelDao.getAllRefShopByWhOuId(ouId);
    }

    public List<BiChannel> queryAllShopList() {
        return biChannelDao.getAllBiChannel();
    }

    /** 
     *
     */
    @Override
    public List<BiChannel> queryShopListByDefaultOuId(Long ouId) {
        return biChannelDao.getAllRefShopByDefaultWhOuId(ouId);
    }

    /**
     * 通过ou_id找到对应的仓库状态 bin.hu
     */
    public String getBiWarehouseStatus(Long ouId) {
        String statusValue = "";
        List<BiWarehouseAddStatus> biList = basDao.getBiWarehouseStatus(ouId);
        for (BiWarehouseAddStatus bi : biList) {
            statusValue += bi.getStatus().getValue() + ",";
        }
        return statusValue;
    }

    public RmiChannelInfo findRmiChannelRefWarehouse(String channelCode) {
        BiChannel ch = biChannelDao.getByCode(channelCode);
        if (ch == null) {
            return null;
        }
        List<OperationUnit> whous = operationUnitDao.getAllWhByShopId(ch.getId());
        RmiChannelInfo rmiCh = new RmiChannelInfo();
        rmiCh.setCode(ch.getCode());
        rmiCh.setName(ch.getName());
        for (OperationUnit whou : whous) {
            RmiWarehouseInfo whinfo = new RmiWarehouseInfo();
            whinfo.setCode(whou.getCode());
            whinfo.setName(whou.getName());
            rmiCh.getWhList().add(whinfo);
        }
        return rmiCh;
    }

    public List<RmiChannelInfo> findAllRmiChannelInofByCustomer(String customerCode) {
        Customer customer = customerDao.getByCode(customerCode);
        if (customer == null) {
            return null;
        }
        List<BiChannel> list = biChannelDao.findAllByCustomer(customer.getId());
        List<RmiChannelInfo> rmiList = new ArrayList<RmiChannelInfo>();
        for (BiChannel ch : list) {
            rmiList.add(findRmiChannelRefWarehouse(ch.getCode()));
        }
        return rmiList;
    }

    @Override
    public List<Transportator> findAllTransRef(OperationUnit ou) {
        if (null == ou.getId()) return null;
        return warehouseDao.findAllTransRef(ou.getId(), new BeanPropertyRowMapper<Transportator>(Transportator.class));
    }

    @Override
    public AutoPlConfigCommand getAotoPlConfig(Long ouid) {
        return apDao.getAotoPlConfigCommand(ouid, new BeanPropertyRowMapperExt<AutoPlConfigCommand>(AutoPlConfigCommand.class));
    }

    @Override
    public List<AutoPickingListRoleCommand> findAutoPickingListRoleList() {
        return aplrDao.findAutoPickingListRoleList(new BeanPropertyRowMapperExt<AutoPickingListRoleCommand>(AutoPickingListRoleCommand.class));
    }

    /**
     * 查询所有客户列表
     * 
     * @return
     */
    public List<Customer> findAllCustomer() {
        return customerDao.findAllCustomer();
    }

    /**
     * 查询客户
     * 
     * @param whouid
     * @return
     */
    public Customer getByWhouid(Long whouid) {
        return customerDao.getByWhouid(whouid);
    }

    public List<District> findAllProvince() {
        return districtDao.findAllProvince(new BeanPropertyRowMapperExt<District>(District.class));
    }

    public List<District> findCity(String province) {
        return districtDao.findCity(province, new BeanPropertyRowMapperExt<District>(District.class));
    }

    public List<WhAddStatusSourceCommand> findWhStatusSourceListSql(int type) {
        return wassDao.findWhStatusSourceListSql(type, new BeanPropertyRowMapper<WhAddStatusSourceCommand>(WhAddStatusSourceCommand.class));
    }

    public InterfaceSecurityInfo findUseringByName(String username, String source, Date date) {
        return interfaceSecurityInfoDao.findUseringByName(username, source, date);
    }

    public List<TransportatorCommand> getTransByPlId(Long pickingListId) {
        return transportatorDao.getTransByPlId(pickingListId, new BeanPropertyRowMapperExt<TransportatorCommand>(TransportatorCommand.class));
    }

    @Override
    public List<District> findDistrictsByCity(String province, String city) {
        return districtDao.findDistrict(province, city, new BeanPropertyRowMapperExt<District>(District.class));
    }

    /**
     * 仓库覆盖区域导入
     */
    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus importCoverageArea(File file, Long ouId, Long userId) {
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
        if (null == ou) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        List<WarehouseCoverageAreaRef> noList = null;
        List<WarehouseCoverageAreaRef> areaList = null;
        areaList = new ArrayList<WarehouseCoverageAreaRef>();
        ReadStatus rs = null;
        try {
            rs = coverageAreaReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_DATA_PROVINCE_CITY_NOT_NONE));
                return rs;
            }
            noList = (List<WarehouseCoverageAreaRef>) beans.get("data");
            for (WarehouseCoverageAreaRef ca : noList) {
                String province = ca.getProvince();
                String city = ca.getCity();
                String district = ca.getDistrict();
                int priority = ca.getPriority();
                if (1 > priority) {
                    throw new BusinessException(ErrorCode.WAREHOUSE_COVERAGE_AREA_PRIORITY_ERROR);
                }
                district = (StringUtil.isEmpty(district) ? "" : district);
                if (StringUtil.isEmpty(province) || StringUtil.isEmpty(city)) {
                    throw new BusinessException(ErrorCode.EXCEL_DATA_PROVINCE_CITY_NOT_NONE);
                }
                if (0 < areaList.size()) {
                    boolean isExist = false;
                    for (WarehouseCoverageAreaRef a : areaList) {
                        String p = a.getProvince();
                        String c = a.getCity();
                        String d = a.getDistrict();
                        if (province.equals(p) && city.equals(c) && district.equals(d)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (false == isExist) {
                        WarehouseCoverageAreaRef wca = new WarehouseCoverageAreaRef();
                        wca.setProvince(ca.getProvince());
                        wca.setCity(ca.getCity());
                        wca.setDistrict(ca.getDistrict());
                        wca.setCreatorId(userId);
                        wca.setCreateTime(new Date());
                        wca.setOu(ou);
                        wca.setPriority(ca.getPriority());
                        coverageAreaRefDao.save(wca);
                        areaList.add(wca);
                    }
                } else {
                    WarehouseCoverageAreaRef wca = new WarehouseCoverageAreaRef();
                    wca.setProvince(ca.getProvince());
                    wca.setCity(ca.getCity());
                    wca.setDistrict(ca.getDistrict());
                    wca.setCreatorId(userId);
                    wca.setCreateTime(new Date());
                    wca.setOu(ou);
                    wca.setPriority(ca.getPriority());
                    coverageAreaRefDao.save(wca);
                    areaList.add(wca);
                }
            }
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("ReadStatus Exception:", ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 仓库覆盖区域覆盖导入
     */
    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus coverImportCoverageArea(File file, Long ouId, Long userId) {
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
        if (null == ou) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        List<WarehouseCoverageAreaRef> noList = null;
        List<WarehouseCoverageAreaRef> areaList = null;
        areaList = new ArrayList<WarehouseCoverageAreaRef>();
        ReadStatus rs = null;
        try {
            rs = coverageAreaReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_DATA_PROVINCE_CITY_NOT_NONE));
                return rs;
            }
            noList = (List<WarehouseCoverageAreaRef>) beans.get("data");
            // 删除所有关联覆盖区
            coverageAreaRefDao.deleteCoverageAreaByOuId(ouId);
            for (WarehouseCoverageAreaRef ca : noList) {
                String province = ca.getProvince();
                String city = ca.getCity();
                String district = ca.getDistrict();
                int priority = ca.getPriority();
                if (1 > priority) {
                    throw new BusinessException(ErrorCode.WAREHOUSE_COVERAGE_AREA_PRIORITY_ERROR);
                }
                district = (StringUtil.isEmpty(district) ? "" : district);
                if (StringUtil.isEmpty(province) || StringUtil.isEmpty(city)) {
                    throw new BusinessException(ErrorCode.EXCEL_DATA_PROVINCE_CITY_NOT_NONE);
                }
                if (0 < areaList.size()) {
                    boolean isExist = false;
                    for (WarehouseCoverageAreaRef a : areaList) {
                        String p = a.getProvince();
                        String c = a.getCity();
                        String d = a.getDistrict();
                        if (province.equals(p) && city.equals(c) && district.equals(d)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (false == isExist) {
                        WarehouseCoverageAreaRef wca = new WarehouseCoverageAreaRef();
                        wca.setProvince(ca.getProvince());
                        wca.setCity(ca.getCity());
                        wca.setDistrict(ca.getDistrict());
                        wca.setCreatorId(userId);
                        wca.setCreateTime(new Date());
                        wca.setOu(ou);
                        wca.setPriority(ca.getPriority());
                        coverageAreaRefDao.save(wca);
                        areaList.add(wca);
                    }
                } else {
                    WarehouseCoverageAreaRef wca = new WarehouseCoverageAreaRef();
                    wca.setProvince(ca.getProvince());
                    wca.setCity(ca.getCity());
                    wca.setDistrict(ca.getDistrict());
                    wca.setCreatorId(userId);
                    wca.setCreateTime(new Date());
                    wca.setOu(ou);
                    wca.setPriority(ca.getPriority());
                    coverageAreaRefDao.save(wca);
                    areaList.add(wca);
                }
            }
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("coverImportCoverageArea Exception:" + ouId, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 根据ouId查询所有的覆盖区域
     */
    @Override
    public Pagination<WarehouseCoverageAreaRefCommand> findCoverageAreaByOuId(int start, int pageSize, Long ouId, Sort[] sorts) {
        return coverageAreaRefDao.findCoverageAreaByOuId(start, pageSize, ouId, sorts, new BeanPropertyRowMapper<WarehouseCoverageAreaRefCommand>(WarehouseCoverageAreaRefCommand.class));
    }

    /**
     * 删除覆盖区域
     */
    @Override
    public void deleteCoverageArea(Long areaId, Long ouId) {
        WarehouseCoverageAreaRef wca = coverageAreaRefDao.getByPrimaryKey(areaId);
        if (null == wca) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (null != ouId && ouId.equals((null != wca.getOu() ? wca.getOu().getId() : null))) {
            coverageAreaRefDao.deleteByPrimaryKey(areaId);
        }
    }

    @Override
    public Pagination<BiChannelImperfectLineCommand> findWarehouseImperfectlLineList(int start, int pageSize, Long imperfectId, Long id, Sort[] sorts) {

        return biChannelImperfectLineDao.findBiChanneImperfectlLineList(start, pageSize, imperfectId, new BeanPropertyRowMapperExt<BiChannelImperfectLineCommand>(BiChannelImperfectLineCommand.class), sorts);
    }

    @Override
    public Pagination<BiChannelImperfectCommand> findWarehouseImperfectlList(int start, int pageSize, Long ouId, Sort[] sorts) {
        return biChannelImperfectDao.getBiChannelImperfectlListOuId(start, pageSize, ouId, new BeanPropertyRowMapperExt<BiChannelImperfectCommand>(BiChannelImperfectCommand.class), sorts);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus addSkuImperfect(File file, Long ouId) {
        log.debug("===========addSkuImperfect start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<BiChannelImperfectCommand> list = null;
        ReadStatus rs = null;
        try {
            rs = addSkuImperfect.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            list = (List<BiChannelImperfectCommand>) beans.get("data");
            OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(ouId);
            saveSkuImperfect(list, operationUnit);
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("addSkuImperfect Exception:" + ouId, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public void saveSkuImperfect(List<BiChannelImperfectCommand> list, OperationUnit operationUnit) {
        for (BiChannelImperfectCommand biChannelImperfectCommand : list) {
            BiChannelImperfect biChannelImperfect = biChannelImperfectDao.findImperfectCodeOuId(biChannelImperfectCommand.getCode(), operationUnit.getId());
            if (biChannelImperfect == null) {
                biChannelImperfect = new BiChannelImperfect();
                biChannelImperfect.setOuId(operationUnit);
                biChannelImperfect.setCode(biChannelImperfectCommand.getCode());
                biChannelImperfect.setName(biChannelImperfectCommand.getName());
                biChannelImperfect.setIsLocked(false);
            } else {
                biChannelImperfect.setName(biChannelImperfectCommand.getName());
            }


            biChannelImperfectDao.save(biChannelImperfect);
        }
    }

    @Override
    public List<BiChannelImperfectCommand> findWarehouseImperfectl(Long ouId) {
        return biChannelImperfectDao.getBiChannelImperfectlOuId(ouId, new BeanPropertyRowMapperExt<BiChannelImperfectCommand>(BiChannelImperfectCommand.class));
    }

    @Override
    public Pagination<SfNextMorningConfig> findSfNextMorningConfigByOuId(int start, int pageSize, Long id, Sort[] sorts) {
        return sfNextMorningConfigDao.findSfNextMorningConfigByOuId(start, pageSize, id, sorts, new BeanPropertyRowMapper<SfNextMorningConfig>(SfNextMorningConfig.class));
    }

    @Override
    public Pagination<LitreSingle> findNIKETodaySendConfigByOuId(int start, int pageSize, Long id, Sort[] sorts) {
        return litreSingleDao.findNIKETodaySendConfigByOuId(start, pageSize, id, sorts, new BeanPropertyRowMapper<LitreSingle>(LitreSingle.class));
    }

    @Override
    public void saveNikeConfig(LitreSingle ls, Long ouId) {
        List<LitreSingle> list = litreSingleDao.findmainWarehouseOwner(ouId, null, null, null, null, new BeanPropertyRowMapper<LitreSingle>(LitreSingle.class));
        for (LitreSingle litreSingle : list) {
            if (ls.getOwner().equals(litreSingle.getOwner()) && ls.getProvince().equals(litreSingle.getProvince()) && ls.getCity().equals(litreSingle.getCity()) && ls.getDistrict().equals(litreSingle.getDistrict())) {
                throw new BusinessException();
            }
        }
        ls.setMainWarehouse(new OperationUnit(ouId));
        ls.setCreateTime(new Date());
        litreSingleDao.save(ls);
    }

    @Override
    public List<LitreSingle> findLitreSingleByOuId(Long ouId) {
        return litreSingleDao.findmainWarehouseOwner(ouId, null, null, null, null, new BeanPropertyRowMapper<LitreSingle>(LitreSingle.class));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus importNikeTodaySendConfig(File fileSFC, Long id) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<LitreSingle> noList = null;
        List<LitreSingle> areaList = new ArrayList<LitreSingle>();
        ReadStatus rs = null;
        try {
            rs = nikeTodaySendConfigReader.readSheet(new FileInputStream(fileSFC), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_DATA_PROVINCE_CITY_NOT_NONE));
                return rs;
            }
            noList = (List<LitreSingle>) beans.get("configList");
            // 删除所有关联区域
            litreSingleDao.deleteNikeTodaySendConfigByOuId(id);
            String reg = "(([0-1]\\d)|(2[0-3])):[0-5]\\d-(([0-1]\\d)|(2[0-3])):[0-5]\\d";
            for (LitreSingle ca : noList) {
                String owner = ca.getOwner();
                String province = ca.getProvince();
                BigDecimal totalActual = ca.getTotalActual();
                String city = ca.getCity();
                String district = ca.getDistrict();
                String time1 = ca.getStartTime();
                String time2 = ca.getEndTime();
                district = (StringUtil.isEmpty(district) ? "" : district);
                if (StringUtil.isEmpty(owner) || StringUtil.isEmpty(province) || StringUtil.isEmpty(city) || StringUtil.isEmpty(district)) {
                    throw new BusinessException(ErrorCode.EXCEL_DATA_PROVINCE_CITY_NOT_NONE);
                }
                if (!time1.matches(reg) || !time2.matches(reg)) {
                    throw new BusinessException(ErrorCode.EXCEL_DATA_TIME_ERROR);
                }
                Integer time1StartHour = Integer.parseInt(time1.split("-")[0].split(":")[0]);
                Integer time1EndHour = Integer.parseInt(time1.split("-")[1].split(":")[0]);
                Integer time2StartHour = Integer.parseInt(time2.split("-")[0].split(":")[0]);
                Integer time2EndHour = Integer.parseInt(time2.split("-")[1].split(":")[0]);
                if (time1StartHour > time1EndHour || time2StartHour > time2EndHour || time1EndHour > time2StartHour) {
                    throw new BusinessException(ErrorCode.EXCEL_DATA_TIME_ERROR);
                }
                if (0 < areaList.size()) {
                    boolean isExist = false;
                    for (LitreSingle a : areaList) {
                        String o = a.getOwner();
                        String p = a.getProvince();
                        String c = a.getCity();
                        String d = a.getDistrict();
                        if (owner.equals(o) && province.equals(p) && city.equals(c) && district.equals(d)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (false == isExist) {
                        LitreSingle ls = new LitreSingle();
                        ls.setOwner(owner);
                        ls.setProvince(province);
                        ls.setCity(city);
                        ls.setTotalActual(totalActual);
                        ls.setDistrict(district);
                        ls.setStartTime(time1);
                        ls.setEndTime(time2);
                        ls.setCreateTime(new Date());
                        ls.setMainWarehouse(new OperationUnit(id));
                        litreSingleDao.save(ls);
                        areaList.add(ls);
                    }
                } else {
                    LitreSingle ls = new LitreSingle();
                    ls.setOwner(owner);
                    ls.setProvince(province);
                    ls.setCity(city);
                    ls.setDistrict(district);
                    ls.setTotalActual(totalActual);
                    ls.setStartTime(time1);
                    ls.setEndTime(time2);
                    ls.setCreateTime(new Date());
                    ls.setMainWarehouse(new OperationUnit(id));
                    litreSingleDao.save(ls);
                    areaList.add(ls);
                }
            }
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public String saveLocationType(PdaLocationType pdaLocationType, User user, OperationUnit op) {
        String brand = null;
        PdaLocationType type = pdaLocationTypeDao.getPdaLocationType(op.getId(), pdaLocationType.getCode(), null, new BeanPropertyRowMapperExt<PdaLocationType>(PdaLocationType.class));
        PdaLocationType type2 = pdaLocationTypeDao.getPdaLocationType(op.getId(), null, pdaLocationType.getName(), new BeanPropertyRowMapperExt<PdaLocationType>(PdaLocationType.class));

        if (type == null && type2 == null) {
            pdaLocationType.setCreateTime(new Date());
            pdaLocationType.setLastModifyTime(new Date());
            pdaLocationType.setCreatorUser(user);
            pdaLocationType.setModifyUser(user);
            pdaLocationType.setOp(op);
            pdaLocationTypeDao.save(pdaLocationType);
            brand = "1";// ok
        } else if (type != null) {
            brand = "2";// code已存在
        } else {
            brand = "3";// name已存在
        }
        return brand;
    }



    @Override
    public Pagination<PdaLocationTypeCommand> getPdaLocationType(int start, int pagesize, long ouId, PdaLocationTypeCommand locationTypeCommand, Sort[] sorts) {
        return pdaLocationTypeDao.getPdaLocationTypeByPage(start, pagesize, ouId, new BeanPropertyRowMapperExt<PdaLocationTypeCommand>(PdaLocationTypeCommand.class), sorts);
    }

    @Override
    public String updateLocationType(PdaLocationType pdaLocationType, User user, OperationUnit op) {
        String brand = null;
        PdaLocationType type = pdaLocationTypeDao.getPdaLocationTypeRemoveId(op.getId(), pdaLocationType.getId(), pdaLocationType.getCode(), null, new BeanPropertyRowMapperExt<PdaLocationType>(PdaLocationType.class));
        PdaLocationType type2 = pdaLocationTypeDao.getPdaLocationTypeRemoveId(op.getId(), pdaLocationType.getId(), null, pdaLocationType.getName(), new BeanPropertyRowMapperExt<PdaLocationType>(PdaLocationType.class));

        if (type == null && type2 == null) {
            PdaLocationType locType = pdaLocationTypeDao.getByPrimaryKey(pdaLocationType.getId());
            locType.setCode(pdaLocationType.getCode());
            locType.setName(pdaLocationType.getName());
            locType.setLastModifyTime(new Date());
            locType.setModifyUser(user);
            pdaLocationTypeDao.save(locType);
            brand = "1";
        } else if (type != null) {
            brand = "2";// code已存在
        } else {
            brand = "3";// name已存在
        }
        return brand;
    }

    @Override
    public String delLocationType(List<String> ids, User user) {
        for (String str : ids) {// 验证是否绑定库位与是否绑定商品容器
            Long id = Long.valueOf(str);
            PdaLocationType type = pdaLocationTypeDao.getByPrimaryKey(id);
            PdaLocationType locationType = pdaLocationTypeDao.verifyLocation(id, new BeanPropertyRowMapper<PdaLocationType>(PdaLocationType.class));
            if (locationType != null) {// 验证是否绑定库位
                return "CODE:" + type.getCode() + "已绑定库位";
            }
            PdaLocationType locationType2 = pdaLocationTypeDao.verifySkuCap(id, new BeanPropertyRowMapper<PdaLocationType>(PdaLocationType.class));
            if (locationType2 != null) {// 是否绑定商品容器
                return "CODE:" + type.getCode() + "已绑定商品容器";
            }
        }
        for (String str : ids) {// 删除库位
            Long id = Long.valueOf(str);
            pdaLocationTypeDao.deleteByPrimaryKey(id);
        }
        return "1";
    }

    @Override
    public List<PdaLocationType> getPdaLocationTypeList(OperationUnit op) {
        List<PdaLocationType> list = pdaLocationTypeDao.getPdaLocationTypeList(op.getId());
        return list;
    }

    @Override
    public Pagination<PdaLocationTypeCommand> getPdaLocationTypeBinding(int start, int pagesize, long ouId, PdaLocationTypeCommand locationTypeCommand, Sort[] sorts) {
        String locationCode = null;// 库位编码
        String code = null;// 库位类型编码
        String name = null;// 库位类型名称

        if (locationTypeCommand != null) {
            if (!"".equals(locationTypeCommand.getLocationCode())) {
                locationCode = locationTypeCommand.getLocationCode();
            }
            if (!"".equals(locationTypeCommand.getCode())) {
                code = locationTypeCommand.getCode();
            }
            if (!"".equals(locationTypeCommand.getName())) {
                name = locationTypeCommand.getName();
            }
        }
        return pdaLocationTypeDao.getPdaLocationTypeBindingByPage(start, pagesize, ouId, locationCode, name, code, new BeanPropertyRowMapperExt<PdaLocationTypeCommand>(PdaLocationTypeCommand.class), sorts);
    }



    @Override
    public Pagination<PdaSkuLocTypeCapCommand> getPdaSkuLocTypeCapBinding(int start, int pagesize, long ouId, PdaSkuLocTypeCapCommand pdaSkuLocTypeCapCommand, Sort[] sorts) {
        String typeName = null;
        String skuName = null;
        String supplierCode = null;
        String skuCode = null;
        String typeCode = null;
        if (null != pdaSkuLocTypeCapCommand) {
            if (null != pdaSkuLocTypeCapCommand.getSupplierCode() && !"".equals(pdaSkuLocTypeCapCommand.getSupplierCode())) {
                supplierCode = pdaSkuLocTypeCapCommand.getSupplierCode().trim();;
            }
            if (null != pdaSkuLocTypeCapCommand.getSkuCode() && !"".equals(pdaSkuLocTypeCapCommand.getSkuCode())) {
                skuCode = pdaSkuLocTypeCapCommand.getSkuCode().trim();;
            }
            if (null != pdaSkuLocTypeCapCommand.getSkuName() && !"".equals(pdaSkuLocTypeCapCommand.getSkuName())) {
                skuName = pdaSkuLocTypeCapCommand.getSkuName().trim();
            }
            if (null != pdaSkuLocTypeCapCommand.getTypeName() && !"".equals(pdaSkuLocTypeCapCommand.getTypeName())) {
                typeName = pdaSkuLocTypeCapCommand.getTypeName();
            }
            if (null != pdaSkuLocTypeCapCommand.getTypeCode() && !"".equals(pdaSkuLocTypeCapCommand.getTypeCode())) {
                typeCode = pdaSkuLocTypeCapCommand.getTypeCode();
            }
        }
        return pdaSkuLocTypeCapDao.getPdaSkuLocTypeCapBindingByPage(start, pagesize, ouId, supplierCode, skuCode, skuName, typeName, typeCode, new BeanPropertyRowMapperExt<PdaSkuLocTypeCapCommand>(PdaSkuLocTypeCapCommand.class), sorts);
    }

    @Override
    public PdaLocationType queryLocationTypeByNameCodeOuId(PdaLocationType pdaLocationType, OperationUnit op) {
        return pdaLocationTypeDao.queryLocationTypeByNameCodeOuId(pdaLocationType.getCode(), pdaLocationType.getName(), op.getId(), null, new BeanPropertyRowMapper<PdaLocationType>(PdaLocationType.class));
    }

    @Override
    public String delLocationTypeBinding(List<String> ids, User user) {
        for (String str : ids) {// 删除库位类型绑定
            Long id = Long.valueOf(str);
            pdaLocationTypeBindingDao.deleteByPrimaryKey(id);
        }
        return "1";
    }


    @Override
    public void delSkuLocTypeCap(List<String> ids) {
        for (String id : ids) {
            pdaSkuLocTypeCapDao.deleteByPrimaryKey(Long.parseLong(id));
        }

    }

    @Override
    public String saveLocationTypeBinding(PdaLocationTypeCommand pdaLocationTypeCommand, User user, OperationUnit op) {
        String brand = null;
        WarehouseLocation location = warehouseLocationDao.findLocationByCode2(pdaLocationTypeCommand.getLocationCode(), op.getId());
        PdaLocationTypeBinding binding = pdaLocationTypeDao.getPdaLocationTypeBinding(pdaLocationTypeCommand.getLocationCode(), null, op.getId(), new BeanPropertyRowMapperExt<PdaLocationTypeBinding>(PdaLocationTypeBinding.class));
        PdaLocationType type = pdaLocationTypeDao.queryLocationTypeByNameCodeOuId(pdaLocationTypeCommand.getCode(), pdaLocationTypeCommand.getName(), op.getId(), null, new BeanPropertyRowMapperExt<PdaLocationType>(PdaLocationType.class));
        if (location == null) {
            brand = "3";// locationCode不存在
        } else if (binding == null) {
            PdaLocationTypeBinding b = new PdaLocationTypeBinding();
            b.setLocationId(location.getId());
            b.setLocTypeId(type.getId());
            pdaLocationTypeBindingDao.save(b);
            brand = "1";// ok
        } else {
            brand = "2";// 该仓库已经绑定
        }
        return brand;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus saveLocationTypeBindingImport(File file, OperationUnit op) throws Exception {
        Set<String> set = new HashSet<String>();
        StringBuilder sb = new StringBuilder();
        Map<String, Object> beans = new HashMap<String, Object>();
        List<PdaLocationTypeCommand> typeCommandList = null;
        ReadStatus rs = null;
        rs = locationTypeBindingInstructionReader.readSheet(new FileInputStream(file), 0, beans);
        typeCommandList = (List<PdaLocationTypeCommand>) beans.get("data");
        int i = 4;
        for (PdaLocationTypeCommand pdaLocationTypeCommand : typeCommandList) {
            String msg = verifyLocationTypeBinding(pdaLocationTypeCommand, op);// 验证导入库存类型绑定
            if (!"".equals(msg)) {
                rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                sb.append("{第" + i + "行数据不正确：" + msg + "};\r\n");
            }
            if (set.add(pdaLocationTypeCommand.getCode() + "," + pdaLocationTypeCommand.getLocationCode())) {} else {
                rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                sb.append("{第" + i + "行数据重复" + pdaLocationTypeCommand.getCode() + "," + pdaLocationTypeCommand.getLocationCode() + "};\r\n");
            }
            i++;
        }
        if (rs.getStatus() == 10) {
            log.error("saveLocationTypeBindingImport 数据不正确");
            // rs.setMessage(sb.toString());
            throw new BusinessException(sb.toString());
        }
        return rs;
    }

    /**
     * QS导入
     */

    @Override
    public ReadStatus saveQsSkuBindingImport(File file, OperationUnit op, User user) throws Exception {
        log.debug("===========saveQsSkuBindingImport start============");
        Set<String> setHash = new HashSet<String>();
        Map<String, Object> beans = new HashMap<String, Object>();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        List<QsSkuCommand> stalList = null;
        ReadStatus rs = null;
        try {
            rs = qsSkuBindingInstructionReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            String type = (String) beans.get("type");
            stalList = (List<QsSkuCommand>) beans.get("outStaLine");
            for (QsSkuCommand qsSkuCommand : stalList) {
                setHash.add(qsSkuCommand.getBarCode());
            }
            if (setHash.size() == 0) {
                throw new BusinessException("SKU条码/编码不可以为空");
            }
            if (setHash.size() > 10000) {
                throw new BusinessException("最多导入1万条数据");
            }
            int intType = 0;
            if ("SKU条码".equals(type)) { // 判断type
                intType = 2;
            } else if ("商品编码".equals(type)) {
                intType = 1;
            }
            Sku sku = null;
            QsSkuCommand qs = null;
            QsSku qsEn = null;
            Long customerId = wareHouseManagerQuery.getCustomerByWhouid(op.getId());
            for (String skuCode : setHash) {
                if (intType == 1) {
                    sku = skuDao.getByBarcode(skuCode, customerId);
                } else {
                    sku = skuDao.getByCode(skuCode);
                }
                if (sku == null) {
                    sb.append(skuCode + ";");
                } else {
                    if (intType == 1) {
                        qs = qsSkuDao.getQsSku(op.getId(), sku.getId(), new BeanPropertyRowMapperExt<QsSkuCommand>(QsSkuCommand.class));
                        if (qs != null) {
                            sb2.append(skuCode + ";");
                        } else {
                            qsEn = new QsSku();
                            qsEn.setCreateTime(new Date());
                            qsEn.setOpUserId(user.getId());
                            qsEn.setOuId(op.getId());
                            qsEn.setSkuId(sku.getId());
                            qsSkuDao.save(qsEn);
                        }
                    } else {
                        qs = qsSkuDao.getQsSku(op.getId(), sku.getId(), new BeanPropertyRowMapperExt<QsSkuCommand>(QsSkuCommand.class));
                        if (qs != null) {
                            sb2.append(skuCode + ";");
                        } else {
                            qsEn = new QsSku();
                            qsEn.setCreateTime(new Date());
                            qsEn.setOpUserId(user.getId());
                            qsEn.setOuId(op.getId());
                            qsEn.setSkuId(sku.getId());
                            qsSkuDao.save(qsEn);
                        }
                    }
                }
            }
            if (!sb.toString().equals("")) {
                sb.append("商品不存在。");
                throw new BusinessException(sb.toString());
            }
            if (!sb2.toString().equals("")) {
                sb2.append("商品已维护。");
                throw new BusinessException(sb2.toString());
            }
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("saveQsSkuBindingImport Exception", ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }


    /**
     * 商品产地导入
     */

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus saveSkuCountryOfOriginImport(File file, OperationUnit op, User user) throws Exception {
        log.debug("===========saveSkuCountryOfOriginImport start============");
        Map<String, String> data = new HashMap<String, String>();
        Map<String, Object> beans = new HashMap<String, Object>();
        Map<String, Sku> saveSkus = new HashMap<String, Sku>();
        StringBuilder sb = new StringBuilder();
        List<SkuCountryOfOriginCommand> skus = null;
        ReadStatus rs = null;
        try {
            rs = skuCountryOfOriginInstructionReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            String type = (String) beans.get("type");
            skus = (List<SkuCountryOfOriginCommand>) beans.get("skus");
            for (SkuCountryOfOriginCommand skuCommand : skus) {
                data.put(skuCommand.getCode(), skuCommand.getCountryOfOrigin());
            }
            if (data.size() == 0) {
                throw new BusinessException("SKU条码/编码/excode1不可以为空");
            }
            if (data.size() > 10000) {
                throw new BusinessException("最多导入1万条数据");
            }
            int intType = 0;
            if ("SKU条码".equals(type)) { // 判断type
                intType = 1;
            } else if ("编码".equals(type)) {
                intType = 2;
            } else if ("excode1".equals(type)) {
                intType = 3;
            }
            Sku sku = null;
            Long customerId = wareHouseManagerQuery.getCustomerByWhouid(op.getId());
            for (String skuCode : data.keySet()) {
                if (intType == 1) {
                    sku = skuDao.getByBarcode(skuCode, customerId);
                } else if (intType == 2) {
                    sku = skuDao.getByCode(skuCode);
                } else if (intType == 3) {
                    sku = skuDao.getByExtCode1AndCustomerId(skuCode, customerId);
                }
                if (sku == null) {
                    sb.append(skuCode + ";");
                } else {
                    sku.setCountryOfOrigin(data.get(skuCode));
                    sku.setLastModifyTime(new Date());
                    saveSkus.put(sku.getCode(), sku);
                }
            }
            if (!sb.toString().equals("")) {
                sb.append("商品不存在!");
                throw new BusinessException(sb.toString());
            }
            for (Map.Entry<String, Sku> entry : saveSkus.entrySet()) {
                skuDao.save(entry.getValue());
            }
            log.debug("===========saveSkuCountryOfOriginImport end============");
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("商品产地维护异常！", ex);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus savePdaSkuLocTypeCapeBindingImport(File file, OperationUnit op) throws Exception {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> beans = new HashMap<String, Object>();
        List<PdaSkuLocTypeCapCommand> typeCommandList = null;
        ReadStatus rs = null;

        rs = skuLocTypeCapBindingInstructionReader.readSheet(new FileInputStream(file), 0, beans);
        // System.out.println(rs.getStatus());
        typeCommandList = (List<PdaSkuLocTypeCapCommand>) beans.get("data");
        int i = 4;
        for (PdaSkuLocTypeCapCommand pdaSkuLocTypeCapCommand : typeCommandList) {
            String msg = verifyPdaSkuLocTypeCapBinding(pdaSkuLocTypeCapCommand, op);// 验证导入库存类型绑定
            if (!"".equals(msg)) {
                rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                sb.append("{第" + i + "行数据不正确：" + msg + "};\r\n");
            }
            i++;
        }
        if (null != typeCommandList && typeCommandList.size() > 1) {
            for (int j = 0; j < typeCommandList.size() - 1; j++) {
                for (int k = j + 1; k < typeCommandList.size(); k++) {
                    if (typeCommandList.get(k).getSupplierCode().equals(typeCommandList.get(j).getSupplierCode()) && typeCommandList.get(k).getSkuCode().equals(typeCommandList.get(j).getSkuCode())
                            && typeCommandList.get(k).getTypeCode().equals(typeCommandList.get(j).getTypeCode())) {
                        rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                        int m = k + 4;
                        int n = j + 4;
                        sb.append("{第" + m + "行数据与第" + n + "行数据有重复" + "};\r\n");
                    }
                }

            }
        }

        if (null != rs && rs.getStatus() == 10) {
            log.error("savePdaSkuLocTypeCapeBindingImport 数据不正确");
            // rs.setMessage(sb.toString());
            throw new BusinessException(sb.toString());
        }
        for (PdaSkuLocTypeCapCommand pdaSkuLocTypeCapCommand : typeCommandList) {
            PdaSkuLocTypeCap pdaSkuLocTypeCap =
                    pdaSkuLocTypeCapDao.getBySkuCode(pdaSkuLocTypeCapCommand.getSkuCode(), null, pdaSkuLocTypeCapCommand.getTypeCode(), pdaSkuLocTypeCapCommand.getSupplierCode(), new BeanPropertyRowMapper<PdaSkuLocTypeCap>(PdaSkuLocTypeCap.class));
            if (null == pdaSkuLocTypeCap) {
                pdaSkuLocTypeCap = new PdaSkuLocTypeCap();
            }
            pdaSkuLocTypeCap.setSkuCode(pdaSkuLocTypeCapCommand.getSkuCode());
            pdaSkuLocTypeCap.setSupplierCode(pdaSkuLocTypeCapCommand.getSupplierCode());
            PdaLocationType type = pdaLocationTypeDao.queryLocationTypeByNameCodeOuId(pdaSkuLocTypeCapCommand.getTypeCode(), null, op.getId(), null, new BeanPropertyRowMapperExt<PdaLocationType>(PdaLocationType.class));
            pdaSkuLocTypeCap.setPdaLocationType(type);
            pdaSkuLocTypeCap.setQty(pdaSkuLocTypeCapCommand.getQty());
            pdaSkuLocTypeCapDao.save(pdaSkuLocTypeCap);
        }
        return rs;
    }

    private String verifyLocationTypeBinding(PdaLocationTypeCommand pdaLocationTypeCommand, OperationUnit op) {// 验证导入库存类型绑定
        String msg = "";
        if (!StringUtils.hasText(pdaLocationTypeCommand.getCode()) || !StringUtils.hasText(pdaLocationTypeCommand.getLocationCode())) {
            msg += "库位类型编码和库位编码不可为空！";
        } else {
            WarehouseLocation location = warehouseLocationDao.findLocationByCode2(pdaLocationTypeCommand.getLocationCode(), op.getId());
            PdaLocationTypeBinding binding = pdaLocationTypeDao.getPdaLocationTypeBinding(pdaLocationTypeCommand.getLocationCode(), null, op.getId(), new BeanPropertyRowMapperExt<PdaLocationTypeBinding>(PdaLocationTypeBinding.class));
            PdaLocationType type = pdaLocationTypeDao.queryLocationTypeByNameCodeOuId(pdaLocationTypeCommand.getCode(), null, op.getId(), null, new BeanPropertyRowMapperExt<PdaLocationType>(PdaLocationType.class));
            if (location == null) {
                msg += "【" + pdaLocationTypeCommand.getLocationCode() + "】此仓库下库存编码不存在！";
            }
            if (type == null) {
                msg += "【" + pdaLocationTypeCommand.getCode() + "】此仓库下库存类型编码不存在！";
            }
            if (msg == "") {
                if (binding == null) {
                    PdaLocationTypeBinding b = new PdaLocationTypeBinding();
                    b.setLocationId(location.getId());
                    b.setLocTypeId(type.getId());
                    pdaLocationTypeBindingDao.save(b);
                } else {
                    binding.setLocationId(location.getId());
                    binding.setLocTypeId(type.getId());
                    pdaLocationTypeBindingDao.save(binding);
                }
            }
        }
        return msg;
    }

    private String verifyPdaSkuLocTypeCapBinding(PdaSkuLocTypeCapCommand pdaSkuLocTypeCapCommand, OperationUnit op) {// 验证导入库存类型绑定
        String msg = "";
        if (!StringUtils.hasText(pdaSkuLocTypeCapCommand.getTypeCode()) || !StringUtils.hasText(pdaSkuLocTypeCapCommand.getSupplierCode()) || !StringUtils.hasText(pdaSkuLocTypeCapCommand.getSkuCode())) {
            msg += "库位类型编码或货号或SKU编码不可为空!";
        } else {
            List<SkuCommand> list = skuDao.getAllSkuBySupplierCode(pdaSkuLocTypeCapCommand.getSupplierCode(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
            Sku sku = skuDao.getByCode(pdaSkuLocTypeCapCommand.getSkuCode());
            PdaLocationType type = pdaLocationTypeDao.queryLocationTypeByNameCodeOuId(pdaSkuLocTypeCapCommand.getTypeCode(), null, op.getId(), null, new BeanPropertyRowMapperExt<PdaLocationType>(PdaLocationType.class));
            if (null == list || list.size() == 0) {
                msg += "【" + pdaSkuLocTypeCapCommand.getSupplierCode() + "】此货号不存在!";
            }
            if (null == sku) {
                msg += "【" + pdaSkuLocTypeCapCommand.getSkuCode() + "】SKU编码不存在!";
            }
            if (null == type) {
                msg += "【" + pdaSkuLocTypeCapCommand.getTypeCode() + "】库位类型编码不存在!";
            }
            if (null == pdaSkuLocTypeCapCommand.getQty() || 0 >= pdaSkuLocTypeCapCommand.getQty()) {
                msg += "【" + pdaSkuLocTypeCapCommand.getQty() + "】容量不存在或者为0!";
            }

        }
        return msg;
    }

    @Override
    public Boolean savePdaSkuLocTypeCapBinding(PdaSkuLocTypeCapCommand pdaSkuLocTypeCapCommand, OperationUnit op) {
        Boolean msg = true;
        String skuCode = null;
        String supplierCode = null;
        Sku sku = null;
        List<Sku> sku1 = new ArrayList<Sku>();
        if (null != pdaSkuLocTypeCapCommand.getSkuCode() && !"".equals(pdaSkuLocTypeCapCommand.getSkuCode()) && null != pdaSkuLocTypeCapCommand.getSupplierCode() && !"".equals(pdaSkuLocTypeCapCommand.getSupplierCode())) {
            sku = skuDao.getByCode(pdaSkuLocTypeCapCommand.getSkuCode());
            if (null != sku && null != sku.getSupplierCode() && sku.getSupplierCode().equals(pdaSkuLocTypeCapCommand.getSkuCode())) {

            } else {
                msg = false;
                return msg;
            }
        }

        if (null != pdaSkuLocTypeCapCommand.getSkuCode() && !"".equals(pdaSkuLocTypeCapCommand.getSkuCode())) {
            sku = skuDao.getByCode(pdaSkuLocTypeCapCommand.getSkuCode());
            if (null != sku) {
                skuCode = pdaSkuLocTypeCapCommand.getSkuCode();
            } else {
                msg = false;
                return msg;
            }
        } else {
            skuCode = null;
        }

        if (null != pdaSkuLocTypeCapCommand.getSupplierCode() && !"".equals(pdaSkuLocTypeCapCommand.getSupplierCode())) {
            sku1 = skuDao.getBysupplierCode(pdaSkuLocTypeCapCommand.getSupplierCode());
            if (null != sku1 && sku1.size() > 0) {
                supplierCode = pdaSkuLocTypeCapCommand.getSupplierCode();
            } else {
                msg = false;
                return msg;
            }
        } else {
            supplierCode = null;
        }

        PdaSkuLocTypeCap pdaSkuLocTypeCap = pdaSkuLocTypeCapDao.getBySkuCode(skuCode, pdaSkuLocTypeCapCommand.getId(), null, supplierCode, new BeanPropertyRowMapper<PdaSkuLocTypeCap>(PdaSkuLocTypeCap.class));
        if (null == pdaSkuLocTypeCap) {
            pdaSkuLocTypeCap = new PdaSkuLocTypeCap();
        }
        pdaSkuLocTypeCap.setQty(pdaSkuLocTypeCapCommand.getQty());
        pdaSkuLocTypeCap.setSupplierCode(pdaSkuLocTypeCapCommand.getSupplierCode());
        pdaSkuLocTypeCap.setSkuCode(pdaSkuLocTypeCapCommand.getSkuCode());
        PdaLocationType type = pdaLocationTypeDao.queryLocationTypeByNameCodeOuId(null, null, op.getId(), pdaSkuLocTypeCapCommand.getId(), new BeanPropertyRowMapperExt<PdaLocationType>(PdaLocationType.class));
        pdaSkuLocTypeCap.setPdaLocationType(type);
        pdaSkuLocTypeCapDao.save(pdaSkuLocTypeCap);
        return msg;
    }

    @Override
    public PdaLocationType findbyId(Long id) {
        return pdaLocationTypeDao.getByPrimaryKey(id);
    }

    @Override
    public String zdhReStoreData(String plCode) {
        ZdhPici zdhPici = zdhPiciDao.getZdhPiciByCode(plCode, new BeanPropertyRowMapper<ZdhPici>(ZdhPici.class));
        if (zdhPici == null) {
            throw new BusinessException("没有该批次号");
        }
        if ("1".equals(zdhPici.getRestoreStatus()) || "2".equals(zdhPici.getRestoreStatus())) {
            throw new BusinessException("库存数据已经恢复，不可重复操作");
        }
        zdhPici.setRestoreStatus("1");
        zdhPiciDao.save(zdhPici);
        zdhPiciDao.flush();
        // 执行库存数据恢复
        inventoryDao.reStoreSkuInventory(plCode);
        return "1";
    }

    @Override
    public String zdhReStoreStatus(String plCode) {
        ZdhPici zdhPici = zdhPiciDao.getZdhPiciByCode(plCode, new BeanPropertyRowMapper<ZdhPici>(ZdhPici.class));
        if (zdhPici == null) {
            throw new BusinessException("没有该批次号");
        }
        if ("1".equals(zdhPici.getRestoreStatus())) {
            List<ZdhPiciLineCommand> list = zdhPiciLineDao.getZdhPiciLineByCode(plCode, new BeanPropertyRowMapper<ZdhPiciLineCommand>(ZdhPiciLineCommand.class));
            for (ZdhPiciLineCommand zdhPiciLine : list) {
                staDao.reStoreStaStatus(zdhPiciLine.getShopName(), zdhPiciLine.getWhId());
            }
        } else if ("2".equals(zdhPici.getRestoreStatus())) {
            throw new BusinessException("单据状态已经恢复，不可重复操作");
        } else {
            throw new BusinessException("请先执行库存数据恢复");
        }
        zdhPici.setRestoreStatus("2");
        zdhPici.setStatus(0);
        zdhPiciDao.save(zdhPici);
        zdhPiciDao.flush();
        return "1";
    }

    @Override
    public Pagination<ZoonCommand> queryZoonSort(int start, int pageSize, Long id, ZoonCommand zoonCommand, Sort[] sorts) {
        String code = null;// 仓库区域编码
        String name = null;// 仓库区域名称

        if (zoonCommand != null) {
            if (!"".equals(zoonCommand.getName())) {
                name = zoonCommand.getName();
            }
            if (!"".equals(zoonCommand.getCode())) {
                code = zoonCommand.getCode();
            }
        }
        // return pdaLocationTypeDao.getPdaLocationTypeBindingByPage(start, pagesize, ouId,
        // locationCode, name, code, new
        // BeanPropertyRowMapperExt<PdaLocationTypeCommand>(PdaLocationTypeCommand.class), sorts);
        return zoonDao.findZoonSortByParams(start, pageSize, id, name, code, new BeanPropertyRowMapperExt<ZoonCommand>(ZoonCommand.class));
    }

    @Override
    public String commitZoon(ZoonCommand zoonCommand, Long id) {
        String seq = zoonCommand.getSeq();
        Zoon zoon = zoonDao.getByPrimaryKey(zoonCommand.getId());
        zoon.setSeq(seq);
        return "1";
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus importAdPackage(File fileSFC, Long id, String userName) {
        log.debug("===========addAdPackage start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<AdPackage> list = null;

        ReadStatus rs = null;
        try {
            rs = adPackageImport.readSheet(new FileInputStream(fileSFC), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            list = (List<AdPackage>) beans.get("adPackage");
            OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(id);
            adPackageDao.deleteAdPackage(operationUnit.getId());
            for (AdPackage e : list) {
                AdPackage entity = new AdPackage();
                entity.setAdName(e.getAdName());
                entity.setAdResult(e.getAdResult());
                entity.setCreatePerson(userName);
                entity.setCreateTime(new Date());
                entity.setMainWarehouse(operationUnit);
                adPackageDao.save(entity);
            }

            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("addAdPackage Exception:" + id, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public List<AdPackage> findAdPackageByOuId(Long ouId) {
        return adPackageDao.findAdPackageByOuId(ouId, new BeanPropertyRowMapper<AdPackage>(AdPackage.class));
    }

    @Override
    public Pagination<AdPackage> findAdPackageByOuIdPage(int start, int pageSize, Long id, Sort[] sorts) {
        // TODO Auto-generated method stub
        return adPackageDao.findAdPackageByOuIdPage(start, pageSize, id, new BeanPropertyRowMapperExt<AdPackage>(AdPackage.class));
    }
    
    @Override
    public Integer skuWeightCount(Long id) {
        return warehouseDao.skuWeightCount(id, new SingleColumnRowMapper<Integer>(Integer.class));
    }

    @Override
    public Pagination<VehicleStandard> findVehicleStandardList(int start, int pageSize, String standardCode, BigDecimal vehicleVolume1, BigDecimal vehicleVolume2, Sort[] sorts) {
        if (!StringUtils.hasText(standardCode)) {
            standardCode = null;
        }
        if (vehicleVolume1 == null) {
            vehicleVolume1 = new BigDecimal(0);
        }
        if (vehicleVolume2 == null) {
            vehicleVolume2 = new BigDecimal(99999);
        }

        return vehicleStandardDao.findVehicleStandardList(start, pageSize, standardCode, vehicleVolume1, vehicleVolume2, new BeanPropertyRowMapperExt<VehicleStandard>(VehicleStandard.class));
    }

    @Override
    public String updateVehicleStandard(VehicleStandard vehicleStandard) {
        VehicleStandard vehicleStandard1 = null;
        if (vehicleStandard.getId() != null) {
            vehicleStandard1 = vehicleStandardDao.getByPrimaryKey(vehicleStandard.getId());
        } else {
            vehicleStandard1 = new VehicleStandard();
        }
        vehicleStandard1.setStandardCode(vehicleStandard.getStandardCode());
        vehicleStandard1.setVehicleVolume(vehicleStandard.getVehicleVolume());
        vehicleStandardDao.save(vehicleStandard1);
        return "";
    }

    @Override
    public void deleteVehicleStandard(Long id) {
        vehicleStandardDao.deleteByPrimaryKey(id);
    }

    @Override
    public Pagination<LicensePlate> findLicensePlateList(int start, int pageSize, String vehicleCode, String licensePlateNumber, String vehicleStandard, BigDecimal vehicleVolume1, BigDecimal vehicleVolume2, Date useTime, Sort[] sorts) {
        if (!StringUtils.hasText(vehicleCode)) {
            vehicleCode = null;
        }
        if (!StringUtils.hasText(licensePlateNumber)) {
            licensePlateNumber = null;
        }
        if (!StringUtils.hasText(vehicleStandard)) {
            vehicleStandard = null;
        }
        if (vehicleVolume1 == null) {
            vehicleVolume1 = new BigDecimal(0);
        }
        if (vehicleVolume2 == null) {
            vehicleVolume2 = new BigDecimal(99999);
        }
        return licensePlateDao.findLicensePlateList(start, pageSize, vehicleCode, licensePlateNumber, vehicleStandard, vehicleVolume1, vehicleVolume2, useTime, new BeanPropertyRowMapperExt<LicensePlate>(LicensePlate.class));
    }

    @Override
    public String updateLicensePlate(LicensePlate licensePlate, Long ouid) {
        LicensePlate licensePlate1 = null;
        OperationUnit ouEntity = operationUnitDao.getByPrimaryKey(ouid);
        if (licensePlate.getId() != null) {
            licensePlate1 = licensePlateDao.getByPrimaryKey(licensePlate.getId());
            if (licensePlate1.getStatus() == 0 && !licensePlate1.getVehicleStandard().equals(licensePlate.getVehicleStandard())) {
                return "车辆已装车 不能修改车辆规格";
            }
        } else {
            licensePlate1 = new LicensePlate();
            Integer sort = 1;
            if (licensePlateDao.maxSort(licensePlate.getUseTime(), new SingleColumnRowMapper<Integer>(Integer.class)) != null) {
                sort = licensePlateDao.maxSort(licensePlate.getUseTime(), new SingleColumnRowMapper<Integer>(Integer.class)) + 1;
            }
            licensePlate1.setSort(sort);
            licensePlate1.setStatus(1);
        }
        licensePlate1.setVehicleCode(licensePlate.getVehicleCode());
        licensePlate1.setLicensePlateNumber(licensePlate.getLicensePlateNumber());
        licensePlate1.setVehicleStandard(licensePlate.getVehicleStandard());
        licensePlate1.setUseTime(licensePlate.getUseTime());
        licensePlate1.setOu(ouEntity);
        licensePlateDao.save(licensePlate1);
        MongoCarService mongoCarService = new MongoCarService();
        mongoCarService.setId(licensePlate1.getId());
        mongoCarService.setUseTime(licensePlate1.getUseTime());
        mongoCarService.setLicensePlateNumber(licensePlate1.getLicensePlateNumber());
        mongoCarService.setVehicleVolume(vehicleStandardDao.getVehicleVolume(licensePlate1.getVehicleStandard(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
        mongoOperation.save(mongoCarService);



        return "";
    }

    @Override
    public void deleteLicensePlateById(Long id) {
        licensePlateDao.deleteByPrimaryKey(id);
        MongoCarService mongoCarService = new MongoCarService();
        mongoCarService.setId(id);
        mongoOperation.remove(mongoCarService);
    }

    @Override
    public Pagination<SkuDeclarationCommand> findGoodsList(int start, int pageSize, String owner, String upc, String hsCode, String skuCode, String style, String color, String skuSize, int isDiscount, int status, String skuName, int isService,
            Sort[] sorts) {
        if (!StringUtils.hasText(owner)) {
            owner = null;
        } else {
            owner = "%" + owner + "%";
        }
        if (!StringUtils.hasText(upc)) {
            upc = null;
        } else {
            upc = "%" + upc + "%";
        }
        if (!StringUtils.hasText(hsCode)) {
            hsCode = null;
        } else {
            hsCode = "%" + hsCode + "%";
        }
        if (!StringUtils.hasText(skuCode)) {
            skuCode = null;
        } else {
            skuCode = "%" + skuCode + "%";
        }
        if (!StringUtils.hasText(style)) {
            style = null;
        } else {
            style = "%" + style + "%";
        }
        if (!StringUtils.hasText(color)) {
            color = null;
        } else {
            color = "%" + color + "%";
        }
        if (!StringUtils.hasText(skuSize)) {
            skuSize = null;
        } else {
            skuSize = "%" + skuSize + "%";
        }
        if (!StringUtils.hasText(skuName)) {
            skuName = null;
        } else {
            skuName = "%" + skuName + "%";
        }

        return skuDeclarationDao.findGoodsList(start, pageSize, owner, upc, hsCode, skuCode, style, color, skuSize, isDiscount, status, skuName, new BeanPropertyRowMapperExt<SkuDeclarationCommand>(SkuDeclarationCommand.class));
    }

    @Override
    public void deletefindGoodsListById(Long id) {
        skuOriginDeclarationDao.deleteByPrimaryKey(id);
    }

    @Override
    public void pushGoods(Long id) {
        SkuDeclarationCommand skuDeclarationCommand = skuDeclarationDao.pushGoods(id, new BeanPropertyRowMapperExt<SkuDeclarationCommand>(SkuDeclarationCommand.class));
        System.out.println(skuDeclarationCommand);
    }


    
}
