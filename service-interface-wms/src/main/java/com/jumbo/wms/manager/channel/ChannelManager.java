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
package com.jumbo.wms.manager.channel;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.command.WarehouseCommand;
import com.jumbo.wms.model.jasperReport.PackingObj;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfect;
import com.jumbo.wms.model.warehouse.BiChannelImperfectCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLine;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLineCommand;
import com.jumbo.wms.model.warehouse.BiChannelRef;
import com.jumbo.wms.model.warehouse.BiChannelSpecialActionCommand;
import com.jumbo.wms.model.warehouse.ChannelWhRefCommand;
import com.jumbo.wms.model.warehouse.ImperfectStv;
import com.jumbo.wms.model.warehouse.ImperfectStvCommand;
import com.jumbo.wms.model.warehouse.SfCloudWarehouseConfigCommand;
import com.jumbo.wms.model.warehouse.SkuImperfect;
import com.jumbo.wms.model.warehouse.StaInvoiceCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;

public interface ChannelManager extends BaseManager {

    /**
     * find all channel in the customer by wh ou id
     * 
     * @param whouid
     * @return
     */
    List<BiChannel> findtAllChannelByWhouid(Long whouid);

    Boolean checkIsSpecialByStaAndSku(String staCode, String barCode);

    List<BiChannel> findAllChannelByWhouids(Long startWarehouseId, Long endWarehouseId);

    /**
     * 通过公司组织ID获取所有公司下关联渠道信息
     * 
     * @param comouid
     * @return
     */
    List<BiChannel> getAllWhRefChannelByCmpouId(Long comouid);

    /**
     * 根据不同的组织类型查询渠道信息
     * 
     * @param start
     * @param pagesize
     * @param type
     * @param shopName
     * @param ouid
     * @param sorts
     * @return
     */
    Pagination<BiChannel> findBiChannelByOuid(int start, int pagesize, String ouTypeName, String shopName, Long ouid, Sort[] sorts);

    /**
     * 根据查询全部渠道分页信息
     * 
     * @param start
     * @param pagesize
     * @param type
     * @param shopName
     * @param ouid
     * @param sorts
     * @return
     */
    Pagination<BiChannel> findBiChannelByPage(int start, int pageSize, String shopName, Sort[] sorts);

    /**
     * 增加打印次数，反馈是否是后置打印装箱清单
     * 
     * @param plid
     * @param staid
     * @param isPostPrint 强制后置参数
     * @return
     */
    boolean isPostpositionPackingPage(Long plid, Long staid, boolean isPostPrint);

    /**
     * 获取后置装箱清单打印数据
     * 
     * @param plid
     * @param staid
     * @return
     */
    Map<Long, PackingObj> findPackingPageNoLocation(Long plid, Long staid);

    /**
     * 获取后置装箱清单打印数据(O2OQS定制)
     * 
     * @param plid
     * @param staid
     * @return Map<Long,PackingObj>
     * @throws
     */
    Map<Long, PackingObj> findPackagedPageNoLocation(Long plid, Long ppId, Long staid);

    Map<Long, PackingObj> findPackingPageShowLocation(Long plid, Long staid);

    Map<Long, PackingObj> findPackingPageShowLocationNike(Long plid, Long staid);



    /**
     * O2SQS定制
     * 
     * @param plid
     * @param ppId
     * @param staid
     * @return Map<Long,PackingObj>
     * @throws
     */
    Map<Long, PackingObj> findPackagedPageShowLocation(Long plid, Long ppId, Long staid);

    /**
     * 记录装箱清单打印日志
     * 
     * @param plid
     * @param userId
     */
    void logPackingPagePrint(Long plid, Long userId, WhInfoTimeRefNodeType nodeType);

    void logPackingPagePrint(String plCode, Long userId, WhInfoTimeRefNodeType nodeType);

    /**
     * 获取配货清单打印模板类型
     * 
     * @param plid
     * @param staid
     * @return
     */
    String findPickingListTemplateType(Long plid, Long staid);

    /**
     * 获取配货清单打印模板类型
     * 
     * @param plid
     * @param staid
     * @return
     */
    List<StockTransApplicationCommand> findPickingList(Long plid, Long staId);

    /**
     * 获取渠道列表
     * 
     * @param start
     * @param pageSize
     * @param biChannel
     * @param id
     * @param sorts
     * @return
     */
    Pagination<BiChannelCommand> getBiChannelList(int start, int pageSize, BiChannel biChannel, Long id, Sort[] sorts);

    /**
     * 获取渠道列表
     * 
     * @param start
     * @param pageSize
     * @param biChannel
     * @param id
     * @param sorts
     * @return
     */
    Pagination<BiChannelCommand> getBiChannelListByType(int start, int pageSize, BiChannel biChannel, Long whId, Sort[] sorts);

    /**
     * 获取渠道列表
     * 
     * @param start
     * @param pageSize
     * @param biChannel
     * @param id
     * @param sorts
     * @return
     */
    Pagination<BiChannelCommand> findBiChannelListByTypeAndExpT(int start, int pageSize, BiChannel biChannel, Long id, Sort[] sorts);

    /**
     * 根据调整单查寻渠道
     * 
     * @param icId
     * @return
     */
    BiChannel findBichannelByInvCheck(Long icId);

    /**
     * 通过ID获取渠道信息
     * 
     * @param id
     * @return
     */
    BiChannelCommand getBiChannelById(Long id);

    /**
     * 通过渠道编码获取渠道信息
     * 
     * @param code
     * @return BiChannelCommand
     * @throws
     */
    BiChannelCommand getBiChannelByCode(String code);

    BiChannelSpecialActionCommand getBiChannelSpecialActionByCidType(Long id, int type);

    BiChannel checkBiChannelByCodeOrName(String code, String name);

    void updateBiChannel(BiChannel biChannel, BiChannelSpecialAction bcsap, BiChannelSpecialAction bcsae, int deleteBCWR, Long userId);

    void addBiChannel(BiChannel biChannel, BiChannelSpecialAction bcsap, BiChannelSpecialAction bcsae, Long userId);

    List<OperationUnitCommand> findBiChannelWhRefList(Long customerId);

    String findChannelWhRefListByChannelId(Long channelId);

    void updateChannelWhRef(List<ChannelWhRef> c, Long channelId, Long userId, List<ChannelWhRefCommand> channelList);

    String verifySfJcustid(List<ChannelWhRef> list);


    BiChannelRef checkBiChannelRefByBiChannel(String code);

    /**
     * 根据ID获取BiChannel
     * 
     * @param channelId
     * @return
     */
    public BiChannel getBiChannel(Long channelId);

    String findInvoiceType(Long plId, Long staId);

    String exportSoInvoiceByBatchNo(String batchNo, List<Long> wioIdList);

    void addInvoiceExecuteCountByPlId(Long plId, Long staId);

    List<StaInvoiceCommand> findCoachInvoice(Long plId, Long staId);

    List<StaInvoiceCommand> findBurberryInvoice(Long plId, Long staId);

    List<StaInvoiceCommand> findDefaultInvoice(Long plId, Long staId);

    /**
     * 根据作业单编码和商品条码确认是否NIKE特殊处理逻辑
     * 
     * @param staCode
     * @param barCode
     * @return
     */
    String getIsSpecialByStaAndSku(String staCode, String barCode, Long ouId);

    /**
     * 获取公司信息
     * 
     * @return
     */
    List<BiChannelCommand> findInvoiceCompany();

    /**
     * 根据组织查询对应按箱收货渠道信息
     * 
     * @param start
     * @param pagesize
     * @param ouTypeName
     * @param shopName
     * @param ouid
     * @param sorts
     * @return
     */
    List<BiChannel> findBiChannelByOuidAndCartonStaShop(Long ouid, Boolean isCartonStaShop);

    List<StaInvoiceCommand> findReplenishBurberryInvoice(String batchNo, List<Long> wioIdList);

    List<StaInvoiceCommand> findReplenishCoachInvoice(String batchNo, List<Long> wioIdList);

    List<StaInvoiceCommand> findDefaultInvoice(String batchNo, List<Long> wioIdList);

    void addInvoiceExecuteCountByBatchNo(String batchNo, List<Long> wioIdList);

    /**
     * 查询所有的渠道列表
     * 
     * @return
     */
    List<BiChannel> selectAllBiChannel();

    List<StaInvoiceCommand> findDefaultInvoices(List<Long> plIds);

    void addInvoiceExecuteCountByPlIds(List<Long> plIds);

    /**
     * 查询渠道残次信息
     * 
     * @return
     */
    Pagination<BiChannelImperfectCommand> findBiChanneImperfectlList(int start, int pageSize, Long channelId, Long id, Sort[] sorts);

    /**
     * 查询渠道残次明细信息
     * 
     * @return
     */
    Pagination<BiChannelImperfectLineCommand> findBiChanneImperfectlLineList(int start, int pageSize, Long imperfectId, Long id, Sort[] sorts);

    /**
     * 保存渠道残次原因
     * 
     * @param biChannelImperfect
     */
    void addImperfect(BiChannelImperfect biChannelImperfect);

    /**
     * 保存渠道残次类型
     * 
     * @param biChannelImperfect
     */
    void addImperfectLine(BiChannelImperfectLine biChannelImperfectLine);

    /**
     * 根据渠道ID查询原因
     * 
     * @return
     */
    List<BiChannelImperfectCommand> findImperfect(Long channelId);

    /**
     * 根据渠道原因ID查询明细信息
     * 
     * @param imperfectId
     * @return
     */
    List<BiChannelImperfectLineCommand> findImperfectLine(Long imperfectId);

    /**
     * 保存收货信息
     */
    ImperfectStv addImperfectStv(ImperfectStv imperfectStv);

    /**
     * 根据staid查询收货确认明细
     * 
     * @return
     */
    List<ImperfectStvCommand> findImperfectStvLine(Long staId);

    /**
     * 保存日志信息
     * 
     * @param staId
     */
    void saveImperfectStvLineLog(Long staId, OperationUnit operationUnit);

    /**
     * 残次品条码保存
     * 
     * @param imperfect
     */
    SkuImperfect addSkuImperfect(SkuImperfect imperfect, OperationUnit ouId);

    /**
     * 修改残次类型
     * 
     * @param imperfect
     */

    void updateSkuImperfect(SkuImperfect imperfect);

    public List<ChannelWhRefCommand> findChannelListByChannelId(Long channelId);

    /**
     * 导入类型
     * 
     * @param file
     * @param cid
     * @return
     */
    ReadStatus addSkuImperfect(File file, Long cid);

    /**
     * 导入原因
     * 
     * @param file
     * @param cid
     * @return
     */
    ReadStatus addSkuImperfectWhy(File file, Long channelId);

    ChannelWhRef findChannelRefByWhIdAndShopId(Long whId, Long shopId);

    /**
     * sf时效类型
     */
    Pagination<SfCloudWarehouseConfigCommand> findSfTimeList(int start, int pageSize, Long ouId, Long cId, Sort[] sorts);

    /**
     * 获取所有仓库
     */
    List<WarehouseCommand> findAllWarehouse();

    /**
     * 获取Adidas装箱清单模板取值特殊定制
     * 
     * @return
     */
    Map<Long, PackingObj> getAdidasSpecialCustomValue(Long plId, Long staId);

    /**
     * 获取mk装箱清单模板取值特殊定制
     * 
     * @param plId
     * @param staId
     * @return
     */
    Map<Long, PackingObj> getMkSpecialCustomValue(Long plId, Long staId);

    /**
     * 
     * 方法说明：获取Reebok装箱清单模板取值特殊定制
     * 
     * @author LuYingMing
     * @param plId
     * @param staId
     * @return
     */
    Map<Long, PackingObj> getReebokSpecialCustomValue(Long plId, Long staId);

    /**
     * 
     * 方法说明：获取GoPro装箱清单模板取值特殊定制
     * 
     * @author LuYingMing
     * @param plId
     * @param staId
     * @return
     */
    Map<Long, PackingObj> getGoProSpecialCustomValue(Long plId, Long staId);

    /**
     * 方法说明：Nike HK 装箱清单文案图片特殊定制
     * 
     * @param plId
     * @param staId
     * @return
     */
    Map<Long, PackingObj> getNikeHKSpecialCustomValue(Long plId, Long staId);
    
    /**
     * 获取自定义打印模板
     */
    public String findPickingListCustomPrintCode(Long plid, Long staid);

}
