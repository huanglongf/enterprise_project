package com.jumbo.wms.manager.automaticEquipment;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.CheckingSpaceRole;
import com.jumbo.wms.model.automaticEquipment.GoodsCollection;
import com.jumbo.wms.model.automaticEquipment.PopUpArea;
import com.jumbo.wms.model.automaticEquipment.ShippingPoint;
import com.jumbo.wms.model.automaticEquipment.SkuType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.command.automaticEquipment.CheckingSpaceRoleCommand;
import com.jumbo.wms.model.command.automaticEquipment.CountPackageCommand;
import com.jumbo.wms.model.command.automaticEquipment.InboundRoleCommand;
import com.jumbo.wms.model.command.automaticEquipment.PopUpAreaCommand;
import com.jumbo.wms.model.command.automaticEquipment.ShippingPointCommand;
import com.jumbo.wms.model.command.automaticEquipment.ShippingPointRoleLineCommand;
import com.jumbo.wms.model.command.automaticEquipment.ZoonCommand;
import com.jumbo.wms.model.odo.OdoCommand;
import com.jumbo.wms.model.odo.OdoLineCommand;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.web.commond.GoodsCollectionCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONArray;

/**
 * 
 * @author jinlong.ke
 * @date 2016年6月22日下午1:06:10
 * 
 */
public interface AutoBaseInfoManager extends BaseManager {


    String verifyChannelSkuSpAndSkuType(InboundRoleCommand inboundRoleCommand);
    
    
    Boolean odoOutBoundDetailList(String id, int status);


    List<OperationUnit> odoOuIdList(String id);

    Boolean findOdoLineByOdoId(String id, int ouId);

    List<WarehouseDistrict> findAllDistrictByOuId(Long ouId);
    
    Pagination<OdoLineCommand> odoOutBoundDetail(int start, int pageSize, Sort[] sorts, String id, int status);


    Pagination<OdoCommand> findOdOAllQuery(int start, int pageSize, OdoCommand odoCommand, Sort[] sorts);


    /**
     * 根据参数查询商品类型
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<SkuType> findSkuTypeByParams(int start, int pageSize, Map<String, Object> params);

    /**
     * 添加商品类型
     * 
     * @param st
     */
    public String saveSkuType(SkuType st);


    GoodsCollection findshippingpointStatus(String id);


    List<PhysicalWarehouse> findPhysicalWarehouse();


    void deleteShippingPointCollection(String[] ids);


    boolean checkPopupArea(String code);

    /**
     * 修改商品编码
     * 
     * @param st
     */
    public void updateSkuType(Map<String, Object> params, List<Long> idList);


    /**
     * 根据参数查询区域
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<ZoonCommand> findZoonByParams(int start, int pageSize, Map<String, Object> params);


    public List<ZoonCommand> findZoonByParams2(Long ouId, Sort[] sorts);



    /**
     * 创建zoon
     * 
     * @param zoonCommand
     */
    public String saveZoon(ZoonCommand zoonCommand);

    /**
     * 获取自动化仓库
     * 
     * @return
     */
    public List<OperationUnit> findAutoWh();

    /**
     * 修改Zoon
     */
    public void updateZoonByIds(Map<String, Object> params, List<Long> idList);

    Pagination<GoodsCollectionCommand> findShippingPointCollectionList(int start, int pageSize, Sort[] sorts, Long ouId, String roleCode, Integer roleName, String wscCode, String plCode, String container, String PassWay, String pickModel);


    /**
     * 根据参数查询弹出口区域
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<PopUpAreaCommand> findPopUpAreaByParams(int start, int pageSize, Map<String, Object> params);

    /**
     * 修改弹出口
     */
    public void updatePopUpAreaByIds(Map<String, Object> params, List<Long> idList);

    /**
     * 修改弹出口
     * 
     * @param popUpAreaCommand
     * @return
     */
    public String updatePopUpArea(PopUpAreaCommand popUpAreaCommand);


    public String savePopUpArea(PopUpAreaCommand popUpAreaCommand);


    /**
     * 查询集货点
     * 
     * @param start
     * @param pageSize
     * @param sorts
     * @param cstmId
     * @return
     */
    Pagination<ShippingPointCommand> findShippingPointList(int start, int pageSize, Sort[] sorts, Long ouId, String code, String name, String wscCode);



    public ReadStatus importShippingCollect(File file, String id, User user) throws Exception;



    /**
     * 查询负载均衡集货口(组)
     * 
     * @param sorts
     * @param j
     * @param i
     * 
     * @param refShippingPointId
     * @param refShippingPointId2
     * @return
     */
    Pagination<ShippingPoint> findAssumeShippingPointList(int start, int pageSize, Sort[] sorts, Long ouId, Long refShippingPointId);

    /**
     * 查询地区的省
     * 
     * @return
     */
    JSONArray findAreaByPaream(Long type, Long parentId);

    /**
     * 查询弹出扣
     * 
     * @return
     */
    JSONArray findpopAreaList();

    /**
     * 查询集货口
     * 
     * @return
     */
    JSONArray findPointList(Long ouId);

    /**
     * 保存集货规则明细
     * 
     * @param line
     * @param roleId
     */
    String saveShippingRoleLine(ShippingPointRoleLineCommand line, Long pointId, Long ouId);

    /**
     * 根据父ID查找规则明细
     * 
     * @param roleId
     * @param sorts
     * @return
     */
    Pagination<ShippingPointRoleLineCommand> findRoleLineByRoleId(int start, int pageSize, Sort[] sorts, Long ouId, String code);

    Pagination<CheckingSpaceRoleCommand> findlAllCheckSpace(int start, int pageSize, Sort[] sorts, Long ouId, String code, Integer type);


    /**
     * 删除规则明细
     * 
     * @param id
     */
    void deleteShippingRoleLine(Long id);

    /**
     * 删除规则明细
     * 
     * @param id
     */
    void deleteCheckRoleLine(Long id);

    /**
     * 集货规则明细导入
     * 
     * @param file
     * @return
     */
    ReadStatus importShippingRole(File file, Long ouId) throws Exception;

    /**
     * 保存集货弹出扣
     * 
     * @param line
     * @param roleId
     */
    String saveContainPop(String containCode, String popCode, Long ouId);

    /**
     * 保存集货点
     * 
     * @param code
     * @param name
     * @param maxAssumeNumber
     * @param pointType
     * @param refShippingPointId
     * @param roleId
     * @return
     */
    String saveShippingPoint(String id, String code, String name, String wscCode, String pointType, Long maxAssumeNumber, Long refShippingPointId, String oper, Long pointId, Long ouId, Long userId);


    /**
     * 保存负载均衡集货点
     * 
     * @param list
     * @return
     */
    String saveAssumeShippingPoint(List<ShippingPoint> list);

    /**
     * 根据id删除集货点
     * 
     * @param id
     */
    public void deleteShippingById(String id);

    /**
     * 保存核对工作台
     * 
     * @param owner
     * @param isQs
     * @param isSpaice
     * @param sort
     * @param ouId
     * @param checkCode
     * @return
     */
    String saveCheckSpace(Integer transTimeType, String skuCodes, String city, String lpcode, Long roleId, String owner, String toLocation, Long isQs, Long isSpaice, Integer sort, Long ouId, String checkCode, Integer menuType, String isPreSale);

    /**
     * 根据本次收货内容推荐库位给出弹出口<br/>
     * 
     * @param stvId
     * @return
     */
    Map<String, Object> recommandLocationByStv(Long stvId);

    /**
     * 根据参数查询上架规则
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<InboundRoleCommand> findInboundRoleByParams(int start, int pageSize, Map<String, Object> params);

    /**
     * 添加上架规则
     * 
     * @param inboundRoleCommand
     * @return
     */
    public String saveInboundRole(InboundRoleCommand inboundRoleCommand);

    /**
     * 验证规则是否正确
     * 
     * @param inboundRoleCommand
     * @return
     */
    public String verifySaveData(InboundRoleCommand inboundRoleCommand);

    /**
     * 根据自动化仓库获取店铺
     * 
     * @param ouId
     * @return
     */
    public List<BiChannel> findChannelByAutoWh(Long ouId);


    /**
     * 根据仓库及弹出口获取库位
     * 
     * @param ouId
     * @param popId
     * @return
     */
    public List<WarehouseLocation> findLocationByZoon(Long ouId, Long popId);

    /**
     * 修改上架规则
     * 
     * @param inboundRoleCommand
     * @return
     */
    public String updateInboundRole(InboundRoleCommand inboundRoleCommand);

    /**
     * 根据ID删除上架规则
     * 
     * @param idList
     */
    public void deleteInboundRoleByIds(List<Long> idList);


    /**
     * 校验商品与商品上架类型的关联
     * 
     * @param inboundRoleCommand
     * @return
     */
    public String verifySkuAndSkuType(InboundRoleCommand inboundRoleCommand);


    public String verifySkuSpAndSkuType(InboundRoleCommand inboundRoleCommand);

    /**
     * 修改商品的商品上架类型
     * 
     * @param skuCode
     * @param skuTypeName
     */
    public void updateSkuBySkuType(Long skuId, Long skuTypeId);

    /**
     * 库位与弹出口导入
     * 
     * @param file
     * @param ouId
     * @return
     * @throws Exception
     */
    public ReadStatus importLocationAndPopUp(File file, Long ouId) throws Exception;

    /**
     * 查看弹出口是否被库位使用
     * 
     * @return
     */
    public String checkLocationUsePopUpArea(List<Long> idList);

    /**
     * 查看弹出口是否被入库规则使用
     * 
     * @param idList
     * @return
     */
    public String checkInBoundRoleUsePopUpArea(List<Long> idList);

    /**
     * 统计创建交接的包裹数量
     * 
     * @param lpCode
     * @return
     */
    public Pagination<CountPackageCommand> findCountPackageByOutbound(int start, int pageSize, String lpCode);

    /**
     * 取消入库货箱流向
     * 
     * @param code
     * @return
     */
    public Long cancelAutoBox(String code);

    /**
     * 根据配货小批次或者作业单推荐复核工作台
     * 
     * @param pbId 配货小批次ID
     * @param staId 作业单ID
     */
    public CheckingSpaceRole getCheckSpaceByCondition(Long pbId, Integer type, Long staId);

    /**
     * 根据商品和店铺获取弹出口列表
     * 
     * @param skuId
     * @param skuTypeId
     * @param owner
     * @return
     */
    public List<PopUpArea> recommandLocationListBySku(Long skuId, Long skuTypeId, String owner);


    String saveCollection(String code, Long ouId, Integer sort, User userId, String popUpCode, String passWay, String pickModel);

    String updateShippingPointCollection(String code, Integer sort, User userId, String popUpCode, String id, String passWay, String pickModel);

    /**
     * 
     * 方法说明：查询级联关系的集货口(通过负载均衡的关联字段)
     * 
     * @author LuYingMing
     * @param refShippingPointId
     * @return
     */
    List<Long> findShippingPointForCascadeByRefId(Long refShippingPointId);

    /**
     * 
     * 方法说明：批量删除集货口
     * 
     * @author LuYingMing
     * @param arrStr
     */
    void batchRemove(String[] arrStr);

    /**
     * 集货库位看板
     * 
     * @param physicalId 物理仓ouid
     */
    public List<GoodsCollection> findShippingPointCollection(Long ouId);
}
