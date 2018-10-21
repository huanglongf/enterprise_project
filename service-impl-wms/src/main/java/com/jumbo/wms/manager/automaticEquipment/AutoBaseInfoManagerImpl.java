package com.jumbo.wms.manager.automaticEquipment;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.PhysicalWarehouseDao;
import com.jumbo.dao.automaticEquipment.CheckingSpaceRoleDao;
import com.jumbo.dao.automaticEquipment.ContainerPopDao;
import com.jumbo.dao.automaticEquipment.GoodsCollectionDao;
import com.jumbo.dao.automaticEquipment.InboundRoleDao;
import com.jumbo.dao.automaticEquipment.MsgToWcsDao;
import com.jumbo.dao.automaticEquipment.PopUpAreaDao;
import com.jumbo.dao.automaticEquipment.ShippingPointDao;
import com.jumbo.dao.automaticEquipment.ShippingPointRoleLineDao;
import com.jumbo.dao.automaticEquipment.SkuTypeDao;
import com.jumbo.dao.automaticEquipment.ZoonDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.odo.OdoDao;
import com.jumbo.dao.odo.OdoLineDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.WhThreePlAreaInfoDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.HandOverListDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.WarehouseDistrictDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.CheckingSpaceRole;
import com.jumbo.wms.model.automaticEquipment.ContainerPop;
import com.jumbo.wms.model.automaticEquipment.GoodsCollection;
import com.jumbo.wms.model.automaticEquipment.InboundRole;
import com.jumbo.wms.model.automaticEquipment.MsgToWcs;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsRequest.SShouRongQi;
import com.jumbo.wms.model.automaticEquipment.PopUpArea;
import com.jumbo.wms.model.automaticEquipment.ShippingPoint;
import com.jumbo.wms.model.automaticEquipment.ShippingPointRoleLine;
import com.jumbo.wms.model.automaticEquipment.SkuType;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSpType;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.command.automaticEquipment.CheckingSpaceRoleCommand;
import com.jumbo.wms.model.command.automaticEquipment.CountPackageCommand;
import com.jumbo.wms.model.command.automaticEquipment.InboundRoleCommand;
import com.jumbo.wms.model.command.automaticEquipment.PopUpAreaCommand;
import com.jumbo.wms.model.command.automaticEquipment.ShippingPointCommand;
import com.jumbo.wms.model.command.automaticEquipment.ShippingPointRoleLineCommand;
import com.jumbo.wms.model.command.automaticEquipment.ZoonCommand;
import com.jumbo.wms.model.odo.Odo;
import com.jumbo.wms.model.odo.OdoCommand;
import com.jumbo.wms.model.odo.OdoLineCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.WhThreePlAreaInfo;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.web.commond.GoodsCollectionCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONObject;


/**
 * @author lihui
 * 
 * @createDate 2016年1月20日 下午2:58:33
 */
@Transactional
@Service("autoBaseInfoManager")
public class AutoBaseInfoManagerImpl extends BaseManagerImpl implements AutoBaseInfoManager {

    private static final long serialVersionUID = -5807973670097576197L;
    @Autowired
    private SkuTypeDao skuTypeDao;
    @Autowired
    private ZoonDao zoonDao;
    @Autowired
    private PopUpAreaDao popUpAreaDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private WhThreePlAreaInfoDao whThreePlAreaInfoDao;
    @Autowired
    private WarehouseDistrictDao warehouseDistrictDao;
    @Autowired
    private ShippingPointRoleLineDao shippingPointRoleLineDao;
    @Resource(name = "shippingRoleReader")
    private ExcelReader shippingRoleReader;

    @Resource(name = "shippingCollectionReaderImprot")
    private ExcelReader shippingCollectionReaderImprot;

    @Resource(name = "locationAndPopUpReader")
    private ExcelReader locationAndPopUpReader;

    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private ShippingPointDao shippingPointDao;
    @Autowired
    private InboundRoleDao inboundRoleDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private ContainerPopDao containerPopDao;
    @Autowired
    private TransportatorDao TransportatorDao;
    @Autowired
    private CheckingSpaceRoleDao checkingSpaceRoleDao;
    @Autowired
    private MsgToWcsDao msgToWcsDao;
    @Autowired
    private HandOverListDao handOverListDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private GoodsCollectionDao goodsCollectionDao;
    @Autowired
    private PhysicalWarehouseDao physicalWarehouseDao;
    @Autowired
    private OdoDao odoDao;
    @Autowired
    private OdoLineDao odoLineDao;



    /**
     * 根据参数查询商品类型
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<SkuType> findSkuTypeByParams(int start, int pageSize, Map<String, Object> params) {
        return skuTypeDao.findSkuTypeByParams(start, pageSize, params, new BeanPropertyRowMapper<SkuType>(SkuType.class));
    }

    /**
     * 添加商品类型
     * 
     * @param st
     */
    public String saveSkuType(SkuType skuType) {
        String msg = "";
        Map<String, Object> params = new HashMap<String, Object>();
        if (skuType.getName() != null && !"".equals(skuType.getName())) {
            params.put("name", skuType.getName());
            Pagination<SkuType> skuTypeList = findSkuTypeByParams(-1, -1, params);
            if (skuTypeList.getCount() == 0) {
                skuTypeDao.save(skuType);
            } else {
                msg += "名称不可以重复！";
            }
        } else {
            msg += "名称不可以为空！";
        }

        return msg;
    }


    /**
     * 修改商品编码
     * 
     * @param st
     */
    @Override
    public void updateSkuType(Map<String, Object> params, List<Long> idList) {
        for (Long id : idList) {
            SkuType skuType = skuTypeDao.getByPrimaryKey(id);
            Boolean status = (Boolean) params.get("status");
            if (status != null) {
                skuType.setStatus(status);
            }
            String name = (String) params.get("name");
            if (name != null && !"".equals(name)) {
                skuType.setName(name);
            }
        }
    }

    /**
     * 根据参数查询区域
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<ZoonCommand> findZoonByParams(int start, int pageSize, Map<String, Object> params) {
        return zoonDao.findZoonByParams(start, pageSize, params, new BeanPropertyRowMapper<ZoonCommand>(ZoonCommand.class));
    }
    
   

    @Override
    public String saveZoon(ZoonCommand zoonCommand) {
        String flag = "";
        Zoon zoon = new Zoon();
        if (zoonCommand.getName() != null && !"".equals(zoonCommand.getName()) && zoonCommand.getCode() != null && !"".equals(zoonCommand.getCode())) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("code", zoonCommand.getCode());
            params.put("ouId", zoonCommand.getOuId());
            Pagination<ZoonCommand> pzc = zoonDao.findZoonByParams(-1, -1, params, new BeanPropertyRowMapper<ZoonCommand>(ZoonCommand.class));
            if (pzc == null || pzc.getCount() == 0) {

                zoon.setName(zoonCommand.getName());
                zoon.setCode(zoonCommand.getCode());
                OperationUnit ou = operationUnitDao.getByPrimaryKey(zoonCommand.getOuId());
                zoon.setOperationUnit(ou);
                zoonDao.save(zoon);
            } else {
                flag = "编码重复！";
            }
        } else {
            flag = "信息不完整！";
        }
        return flag;
    }


    /**
     * 获取自动化仓库
     * 
     * @return
     */
    public List<OperationUnit> findAutoWh() {
        List<OperationUnit> ouList = zoonDao.findAutoWh(new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
        return ouList;
    }

    /**
     * 修改Zoon
     */
    public void updateZoonByIds(Map<String, Object> params, List<Long> idList) {
        for (Long id : idList) {
            Zoon zoon = zoonDao.getByPrimaryKey(id);
            Boolean status = (Boolean) params.get("status");
            if (status != null) {
                zoon.setStatus(status);
            }
            String name = (String) params.get("name");
            if (name != null && !"".equals(name)) {
                zoon.setName(name);
            }
        }
    }

    /**
     * 根据参数查询弹出口区域
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<PopUpAreaCommand> findPopUpAreaByParams(int start, int pageSize, Map<String, Object> params) {
        return popUpAreaDao.findPopUpAreaByParams(start, pageSize, params, new BeanPropertyRowMapper<PopUpAreaCommand>(PopUpAreaCommand.class));
    }

    public String savePopUpArea(PopUpAreaCommand popUpAreaCommand) {
        String flag = null;
        PopUpArea zoon = new PopUpArea();
        if (popUpAreaCommand.getName() != null && !"".equals(popUpAreaCommand.getName()) && popUpAreaCommand.getCode() != null && !"".equals(popUpAreaCommand.getCode()) && popUpAreaCommand.getWscPopCode() != null
                && !"".equals(popUpAreaCommand.getWscPopCode()) && popUpAreaCommand.getSort() != null && !"".equals(popUpAreaCommand.getSort())) {
            // 验证是否重复
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("code", popUpAreaCommand.getCode());
            Pagination<PopUpAreaCommand> pucPagination = findPopUpAreaByParams(-1, -1, params);
            if (pucPagination != null && pucPagination.getCount() > 0) {
                flag = "编码重复！";
                return flag;
            }
            params.clear();
            pucPagination = null;
            params.put("barcode", popUpAreaCommand.getBarcode());
            pucPagination = findPopUpAreaByParams(-1, -1, params);
            if (pucPagination != null && pucPagination.getCount() > 0) {
                flag = "条码重复！";
                return flag;
            }
            params.clear();
            pucPagination = null;
            params.put("sort", popUpAreaCommand.getSort());
            pucPagination = findPopUpAreaByParams(-1, -1, params);
            if (pucPagination != null && pucPagination.getCount() > 0) {
                flag = "排序不可重复！";
                return flag;
            }
            zoon.setName(popUpAreaCommand.getName());
            zoon.setCode(popUpAreaCommand.getCode());
            zoon.setBarcode(popUpAreaCommand.getBarcode());
            zoon.setSort(popUpAreaCommand.getSort());
            zoon.setWscPopCode(popUpAreaCommand.getWscPopCode());

            popUpAreaDao.save(zoon);
        } else {
            flag = "信息不完整！";
        }
        return flag;
    }

    /**
     * 修改弹出口
     * 
     * @param popUpAreaCommand
     * @return
     */
    public String updatePopUpArea(PopUpAreaCommand popUpAreaCommand) {
        String flag = null;
        PopUpArea zoon = popUpAreaDao.getByPrimaryKey(popUpAreaCommand.getId());
        if (popUpAreaCommand.getName() != null && !"".equals(popUpAreaCommand.getName()) && popUpAreaCommand.getSort() != null && !"".equals(popUpAreaCommand.getSort())) {
            // 验证是否重复
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("sort", popUpAreaCommand.getSort());
            Pagination<PopUpAreaCommand> pucPagination = findPopUpAreaByParams(-1, -1, params);
            if (pucPagination != null && pucPagination.getCount() > 0) {
                List<PopUpAreaCommand> puac = pucPagination.getItems();
                boolean b = true;
                for (PopUpAreaCommand p : puac) {
                    if (popUpAreaCommand.getId().equals(p.getId())) {
                        b = false;
                    }
                }
                if (b) {
                    flag = "优先级重复！";
                    return flag;
                }
            }
            zoon.setName(popUpAreaCommand.getName());
            zoon.setSort(popUpAreaCommand.getSort());
            zoon.setWscPopCode(popUpAreaCommand.getWscPopCode());
            popUpAreaDao.save(zoon);
        } else {
            flag = "信息不完整！";
        }
        return flag;
    }

    /**
     * 修改弹出口
     */
    public void updatePopUpAreaByIds(Map<String, Object> params, List<Long> idList) {
        for (Long id : idList) {
            PopUpArea zoon = popUpAreaDao.getByPrimaryKey(id);
            Boolean status = (Boolean) params.get("status");
            if (status != null) {
                zoon.setStatus(status);
            }
            String name = (String) params.get("name");
            if (name != null && !"".equals(name)) {
                zoon.setName(name);
            }
        }
    }

    /**
     * 查询集货点
     */
    public Pagination<ShippingPointCommand> findShippingPointList(int start, int pageSize, Sort[] sorts, Long ouId, String roleCode, String roleName, String wscCode) {
        String code = null;
        String name = null;
        String wscCodes = null;
        if (StringUtils.hasText(roleCode)) {
            code = roleCode + "%";
        }
        if (StringUtils.hasText(roleName)) {
            name = roleName + "%";
        }
        if (StringUtils.hasText(wscCode)) {
            wscCodes = wscCode + "%";
        }
        return shippingPointDao.findShippingPointList(start, pageSize, ouId, name, code, wscCodes, new BeanPropertyRowMapper<ShippingPointCommand>(ShippingPointCommand.class), sorts);
    }

    public Pagination<OdoCommand> findOdOAllQuery(int start, int pageSize, OdoCommand odoCommand, Sort[] sorts) {
        String code = null;
        String ouName = null;
        String inOuName = null;
        String ownerName = null;
        Integer status = null;
        if (odoCommand != null) {
            code = StringUtil.isEmpty(odoCommand.getCode()) ? null : odoCommand.getCode();
            ouName = StringUtil.isEmpty(odoCommand.getOuName()) ? null : odoCommand.getOuName();
            inOuName = StringUtil.isEmpty(odoCommand.getInOuName()) ? null : odoCommand.getInOuName();
            ownerName = StringUtil.isEmpty(odoCommand.getOwnerName()) ? null : odoCommand.getOwnerName();
            status = odoCommand.getStatus();

        }
        return odoDao.findOdOAllQuery(start, pageSize, code, ouName, inOuName, ownerName, status, sorts, new BeanPropertyRowMapper<OdoCommand>(OdoCommand.class));
    }

    public Pagination<OdoLineCommand>odoOutBoundDetail(int start, int pageSize, Sort[] sorts,String id,int status){
        return odoLineDao.odoOutBoundDetail(start, pageSize, sorts, status, id, new BeanPropertyRowMapper<OdoLineCommand>(OdoLineCommand.class));
    }

    public Boolean odoOutBoundDetailList(String id, int status) {
        return odoLineDao.odoOutBoundDetailList(status, id, new SingleColumnRowMapper<Boolean>(Boolean.class));
    }

    public Boolean findOdoLineByOdoId(String id, int ouId) {
        Boolean flag = odoDao.findOdoLineByOdoId(id, new SingleColumnRowMapper<Boolean>(Boolean.class));
        if (flag != null && flag) {
            Odo odo = odoDao.getByPrimaryKey(Long.valueOf(id));
            odo.setDiffOuid(Long.valueOf(ouId));
            odo.setStatus(6);
            return true;
        }
        return flag;
    }

    public List<OperationUnit> odoOuIdList(String id) {
        return operationUnitDao.odoOuIdList(id, new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
    }

    public Pagination<GoodsCollectionCommand> findShippingPointCollectionList(int start, int pageSize, Sort[] sorts, Long ouId, String roleCode, Integer roleName, String wscCode, String plCode, String container, String passWay, String pickModel) {
        String code = null;
        String wscCodes = null;
        String sort1 = null;
        if (StringUtils.hasText(roleCode)) {
            code = roleCode + "%";
        }

        if (StringUtils.hasText(wscCode)) {
            wscCodes = wscCode + "%";
        }
        if (null == roleName) {
            sort1 = null;
        } else {
            sort1 = roleName.toString();
        }
        if (!StringUtils.hasText(plCode)) {
            plCode = null;
        }
        if (!StringUtils.hasText(container)) {
            container = null;
        }
        if (!StringUtils.hasText(passWay)) {
            passWay = null;
        }
        if (!StringUtils.hasText(pickModel)) {
            pickModel = null;
        }
        return goodsCollectionDao.findShippingPointCollectionList(start, pageSize, ouId, code, sort1, wscCodes, plCode, container, passWay, pickModel, new BeanPropertyRowMapper<GoodsCollectionCommand>(GoodsCollectionCommand.class), sorts);
    }


    public List<PhysicalWarehouse> findPhysicalWarehouse() {
        return physicalWarehouseDao.selectAllPhyWarehouse(new BeanPropertyRowMapper<PhysicalWarehouse>(PhysicalWarehouse.class));
    }


    public boolean checkPopupArea(String code) {
        PopUpArea popUpArea = popUpAreaDao.getPopUpAreaByCodeAndOuid(code);
        if (null != popUpArea && !"".equals(popUpArea)) {
            return true;
        } else {
            return false;
        }
    }

    public String saveCollection(String code, Long ouId, Integer sort, User userId, String popUpCode, String passWay, String pickModel) {
        GoodsCollection goodsCollection = goodsCollectionDao.findGoodsCollectionByCode(code, ouId);
        if (null != goodsCollection && !"".equals(goodsCollection)) {
            return "false";
        } else {
            List<GoodsCollection> sortList = goodsCollectionDao.querySortByOuId(ouId);
            for (GoodsCollection list : sortList) {
                if (list.getSort().intValue() == sort.intValue()) {
                    return "error";
                }
            }
            goodsCollection = new GoodsCollection();
            goodsCollection.setCollectionCode(code);
            goodsCollection.setPhysicalId(physicalWarehouseDao.getByPrimaryKey(ouId));
            goodsCollection.setModifyDate(new Date());
            goodsCollection.setStatus(1);
            goodsCollection.setSort(sort);
            goodsCollection.setModifier(userId);
            goodsCollection.setPopUpCode(popUpCode);
            if (passWay != null || !"".equals(passWay)) {
                goodsCollection.setPassWay(passWay);
            }
            if (pickModel != null || !"".equals(pickModel)) {
                goodsCollection.setPickModel(pickModel);
            }
            goodsCollectionDao.save(goodsCollection);
            return "success";
        }

    }

    public String updateShippingPointCollection(String code, Integer sort, User userId, String popUpCode, String id, String passWay, String pickModel) {
        GoodsCollection goodsCollection = goodsCollectionDao.getByPrimaryKey(Long.parseLong(id));
        if (null != goodsCollection && !"".equals(goodsCollection)) {
            if (null != goodsCollection.getPickinglist() && !"".equals(goodsCollection.getPickinglist())) {
                return "false";
            } else {
                if (goodsCollection.getSort().intValue() == sort.intValue()) {

                } else {
                    List<GoodsCollection> sortList = goodsCollectionDao.querySortByOuId(goodsCollection.getPhysicalId().getId());
                    for (GoodsCollection list : sortList) {
                        if (list.getSort().intValue() == sort.intValue()) {
                            return "error";
                        }
                    }
                }

                goodsCollection.setCollectionCode(code);
                goodsCollection.setModifyDate(new Date());
                goodsCollection.setStatus(1);
                goodsCollection.setSort(sort);
                goodsCollection.setModifier(userId);
                goodsCollection.setPopUpCode(popUpCode);
                if (passWay != null || !"".equals(passWay)) {
                    goodsCollection.setPassWay(passWay);
                }
                if (pickModel != null || !"".equals(pickModel)) {
                    goodsCollection.setPickModel(pickModel);
                }
                // goodsCollectionDao.save(goodsCollection);
                return "success";
            }
        }
        return "no";

    }

    public GoodsCollection findshippingpointStatus(String id) {
        return goodsCollectionDao.getByPrimaryKey(Long.parseLong(id));
    }

    /**
     * 查询负载均衡集货点(组)
     */
    public Pagination<ShippingPoint> findAssumeShippingPointList(int start, int pageSize, Sort[] sorts, Long ouId, Long refShippingPointId) {
        String refShippingPoint = null;
        if (StringUtils.hasText(refShippingPointId + "")) {
            refShippingPoint = refShippingPointId + "";
        }
        return shippingPointDao.findAssumeShippingPointList(start, pageSize, ouId, refShippingPoint, new BeanPropertyRowMapper<ShippingPoint>(ShippingPoint.class), sorts);
    }

    public JSONArray findAreaByPaream(Long type, Long parentId) {
        JSONArray ja = new JSONArray();
        try {
            List<WhThreePlAreaInfo> cate = whThreePlAreaInfoDao.findAreaByPaream(type, parentId, new BeanPropertyRowMapper<WhThreePlAreaInfo>(WhThreePlAreaInfo.class));
            for (int i = 0; i < cate.size(); i++) {
                WhThreePlAreaInfo s = cate.get(i);
                JSONObject jo = new JSONObject();
                jo.put("areaId", s.getAreaId());
                jo.put("areaName", s.getAreaName());
                ja.put(jo);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return ja;
    }

    public JSONArray findpopAreaList() {
        JSONArray ja = new JSONArray();
        try {
            List<PopUpArea> cate = popUpAreaDao.findPopByOuId(new BeanPropertyRowMapper<PopUpArea>(PopUpArea.class));
            for (int i = 0; i < cate.size(); i++) {
                PopUpArea s = cate.get(i);
                JSONObject jo = new JSONObject();
                jo.put("areaCode", s.getCode());
                jo.put("areaName", s.getName());
                ja.put(jo);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return ja;
    }

    public JSONArray findPointList(Long ouId) {
        JSONArray ja = new JSONArray();
        try {
            List<ShippingPoint> cate = shippingPointDao.findPointByOuId(ouId, new BeanPropertyRowMapper<ShippingPoint>(ShippingPoint.class));
            for (int i = 0; i < cate.size(); i++) {
                ShippingPoint s = cate.get(i);
                JSONObject jo = new JSONObject();
                jo.put("pointId", s.getId());
                jo.put("pointName", s.getName());
                ja.put(jo);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return ja;
    }


    /**
     * 保存集货点
     */
    public String saveShippingPoint(String id, String code, String name, String wscCode, String pointType, Long maxAssumeNumber, Long refShippingPointId, String oper, Long pointId, Long ouId, Long userId) {
        ShippingPoint point = null;
        if (pointId != null && !pointId.equals("")) {
            // 修改(从编码点进去修改)
            point = shippingPointDao.getPointByName(name);
            ShippingPoint po = shippingPointDao.getByPrimaryKey(pointId);
            if (point != null && !po.getName().equals(name)) {
                return "1";
            }
            po.setName(name);
            po.setLastModifyId(userId);
            po.setWcsCode(wscCode);
            po.setOuId(ouId);
            if (pointType == null) {
                po.setPointType("0");
            } else {
                po.setPointType("1");
                po.setMaxAssumeNumber(maxAssumeNumber);
            }
            shippingPointDao.save(po);
        } else if (oper != null && oper.equals("edit")) {
            // 修改（通过jggrid导航条上修改按钮）
            point = shippingPointDao.getPointByCode(code);
            ShippingPoint po = shippingPointDao.getByPrimaryKey(Long.parseLong(id));
            if (point != null && !po.getCode().equals(code)) {
                return "2";
            } else {
                point = shippingPointDao.getPointByName(name);
                if (point != null && !po.getName().equals(name)) {
                    return "1";
                }
            }
            po.setCode(code);
            po.setName(name);
            po.setLastModifyId(userId);
            po.setWcsCode(wscCode);
            po.setOuId(ouId);
            po.setMaxAssumeNumber(maxAssumeNumber);
            shippingPointDao.save(po);
        } else {
            // 新增
            point = shippingPointDao.getPointByCode(code);
            if (point != null) {
                return "2";
            } else {
                point = shippingPointDao.getPointByName(name);
                if (point != null) {
                    return "1";
                }
            }
            point = new ShippingPoint();
            point.setCode(code);
            point.setName(name);
            point.setWcsCode(wscCode);
            point.setCreateId(userId);
            point.setCreateTime(new Date());
            point.setOuId(ouId);
            if (pointType == null) {
                // 普通集货点
                point.setPointType("0");
            } else {
                // 负载均衡集货点 (从属)
                if (pointType.equals("2")) {
                    point.setPointType("2");
                    point.setRefShippingPoint(refShippingPointId + "");
                } else {
                    // 负载均衡集货点 (主导)
                    point.setPointType("1");
                }
                point.setMaxAssumeNumber(maxAssumeNumber);
            }
            shippingPointDao.save(point);
        }
        return "";
    }

    /**
     * 保存负载均衡集货点
     */
    public String saveAssumeShippingPoint(List<ShippingPoint> list) {
        if (list != null && !list.isEmpty()) {
            String code = list.get(0).getRefShippingPoint();
            ShippingPoint pointLeader = shippingPointDao.getPointByCode(code);
            // 主导负载均衡集货口不存在
            if (pointLeader == null) {
                return "3";
            }
            // 校验是否已存在
            for (ShippingPoint p2 : list) {
                ShippingPoint point2 = shippingPointDao.getPointByCode(p2.getCode());
                if (point2 != null) {
                    return "2";
                } else {
                    point2 = shippingPointDao.getPointByName(p2.getName());
                    if (point2 != null) {
                        return "1";
                    }
                }
            }
            // 新增
            for (ShippingPoint p : list) {
                p.setPointType("2");
                p.setRefShippingPoint(pointLeader.getId() + "");
                p.setOuId(pointLeader.getOuId());
                p.setCreateId(pointLeader.getCreateId());
                p.setCreateTime(new Date());
                shippingPointDao.save(p);
            }
        }
        return "";
    }

    /**
     * 删除集货点
     */
    @Override
    public void deleteShippingById(String id) {
        shippingPointDao.deleteByPrimaryKey(Long.parseLong(id));
    }



    @Override
    public void deleteShippingPointCollection(String[] ids) {
        for (String id : ids) {
            goodsCollectionDao.deleteByPrimaryKey(Long.parseLong(id));
        }
    }

    /**
     * 保存工作台
     */
    public String saveCheckSpace(Integer transTimeType, String skuCodes, String city, String lpcode, Long roleId, String owner, String toLocation, Long isQs, Long isSpaice, Integer sort, Long ouId, String checkCode, Integer menuType, String isPreSale) {
        CheckingSpaceRole roleLine = null;
        CheckingSpaceRole lines = checkingSpaceRoleDao.getCheckRoleLineBySort(sort, menuType, ouId);
        if (roleId != null && !roleId.equals("")) {
            // 修改
            roleLine = checkingSpaceRoleDao.getByPrimaryKey(roleId);
            // 优先级已存在
            if (lines != null && !roleLine.getId().equals(lines.getId())) {
                return "1";
            }
            roleLine.setLpcode(lpcode);
            roleLine.setCity(city);
            roleLine.setToLocation(toLocation);
            roleLine.setOwner(owner);
            roleLine.setWhOuId(ouId);
            roleLine.setCheckingAreaCode(checkCode);
            roleLine.setLv(sort);
            roleLine.setTransType(transTimeType);// 时效
            roleLine.setSkuCodes(skuCodes);// 指定商品

            if (!StringUtil.isEmpty(isPreSale)) {
                roleLine.setIsPreSale(isPreSale);
            } else {
                roleLine.setIsPreSale(null);
            }

            if (isQs != null && isQs == 1) {
                roleLine.setIsQs(true);
            } else if (isQs != null && isQs == 0) {
                roleLine.setIsQs(false);
            } else if (isQs == null) {
                roleLine.setIsQs(null);
            }
            if (isSpaice != null && isSpaice == 1) {
                roleLine.setSpecialType(1l);
            } else if (isSpaice != null && isSpaice == 0) {
                roleLine.setSpecialType(0l);
            } else if (isSpaice == null) {
                roleLine.setSpecialType(null);
            }
            checkingSpaceRoleDao.save(roleLine);
        } else {
            // 保存
            // 优先级已存在
            if (lines != null) {
                return "1";
            }
            roleLine = new CheckingSpaceRole();
            roleLine.setLpcode(lpcode);
            roleLine.setCity(city);
            roleLine.setCreateTime(new Date());
            roleLine.setToLocation(toLocation);
            roleLine.setOwner(owner);
            roleLine.setWhOuId(ouId);
            roleLine.setType(menuType);
            roleLine.setCheckingAreaCode(checkCode);
            roleLine.setLv(sort);
            roleLine.setTransType(transTimeType);// 时效
            roleLine.setSkuCodes(skuCodes);// 指定商品

            if (!StringUtil.isEmpty(isPreSale)) {
                roleLine.setIsPreSale(isPreSale);
            } else {
                roleLine.setIsPreSale(null);
            }

            if (isQs != null && isQs == 1) {
                roleLine.setIsQs(true);
            } else if (isQs != null && isQs == 0) {
                roleLine.setIsQs(false);
            }
            if (isSpaice != null && isSpaice == 1) {
                roleLine.setSpecialType(1l);
            } else if (isQs != null && isQs == 0) {
                roleLine.setSpecialType(0l);
            }
            checkingSpaceRoleDao.save(roleLine);
            CheckingSpaceRole cLine = checkingSpaceRoleDao.getByPrimaryKey(roleLine.getId());
            if (menuType == 1) {
                cLine.setCode("CS100" + roleLine.getId()); // CODE生成规则，固定格式+ID
            } else {
                cLine.setCode("BZ100" + roleLine.getId()); // CODE生成规则，固定格式+ID
            }
        }
        return "";

    }

    /**
     * 保存集货规则明细
     */
    public String saveShippingRoleLine(ShippingPointRoleLineCommand line, Long pointId, Long ouId) {
        ShippingPointRoleLine roleLine = null;
        ShippingPoint point = shippingPointDao.getByPrimaryKey(pointId);
        ShippingPointRoleLine lines = shippingPointRoleLineDao.getPointRoleLineBySort(line.getSort(), ouId);
        if (line.getId() != null && !line.getId().equals("")) {
            // 修改
            roleLine = shippingPointRoleLineDao.getByPrimaryKey(line.getId());
            // 优先级已存在
            if (lines != null && !roleLine.getId().equals(lines.getId())) {
                return "1";
            }
            roleLine.setLpcode(line.getLpcode());
            roleLine.setProvince(line.getProvince());
            roleLine.setCity(line.getCity());
            roleLine.setDistrict(line.getDistrict());
            roleLine.setOwner(line.getOwner());
            roleLine.setOuId(ouId);
            roleLine.setAddress(line.getAddress());
            roleLine.setStaCode(line.getStaCode());
            roleLine.setSort(line.getSort());
            roleLine.setPoint(point);
            if (line.getTimeTypes() != null) {
                roleLine.setTimeType(TransTimeType.valueOf(line.getTimeTypes()));
            }
            if (line.getTypes() != null) {
                roleLine.setType(StockTransApplicationType.valueOf(line.getTypes()));
            }
            if (line.getIsCods() != null && line.getIsCods() == 1) {
                roleLine.setIsCod(true);
            } else if (line.getIsCods() != null && line.getIsCods() == 0) {
                roleLine.setIsCod(false);
            }
            shippingPointRoleLineDao.save(roleLine);
        } else {
            // 保存
            // 优先级已存在
            if (lines != null) {
                return "1";
            }
            roleLine = new ShippingPointRoleLine();
            roleLine.setLpcode(line.getLpcode());
            roleLine.setProvince(line.getProvince());
            roleLine.setCity(line.getCity());
            roleLine.setDistrict(line.getDistrict());
            roleLine.setOwner(line.getOwner());
            roleLine.setStaCode(line.getStaCode());
            roleLine.setAddress(line.getAddress());
            roleLine.setSort(line.getSort());
            roleLine.setOuId(ouId);
            roleLine.setPoint(point);
            if (line.getTimeTypes() != null && !line.getTimeTypes().equals("")) {
                roleLine.setTimeType(TransTimeType.valueOf(line.getTimeTypes()));
            }
            if (line.getTypes() != null && !line.getTypes().equals("")) {
                roleLine.setType(StockTransApplicationType.valueOf(line.getTypes()));
            }
            if (line.getIsCods() != null && line.getIsCods() == 1) {
                roleLine.setIsCod(true);
            } else if (line.getIsCods() != null && line.getIsCods() == 0) {
                roleLine.setIsCod(false);
            }
            shippingPointRoleLineDao.save(roleLine);
        }
        return "";
    }

    /**
     * 根据父ID查找规则明细
     */
    public Pagination<ShippingPointRoleLineCommand> findRoleLineByRoleId(int start, int pageSize, Sort[] sorts, Long ouId, String pointCode) {
        String code = null;
        if (StringUtils.hasText(pointCode)) {
            code = pointCode + "%";
        }
        return shippingPointRoleLineDao.findRoleLineByRoleId(start, pageSize, ouId, code, new BeanPropertyRowMapper<ShippingPointRoleLineCommand>(ShippingPointRoleLineCommand.class), sorts);

    }

    /**
     * 查询工作台
     */
    public Pagination<CheckingSpaceRoleCommand> findlAllCheckSpace(int start, int pageSize, Sort[] sorts, Long ouId, String owner, Integer type) {
        String owners = null;
        if (StringUtils.hasText(owner)) {
            owners = owner;
        }
        return checkingSpaceRoleDao.findCheckingSpaceRole(start, pageSize, ouId, type, owners, new BeanPropertyRowMapper<CheckingSpaceRoleCommand>(CheckingSpaceRoleCommand.class), sorts);

    }



    /**
     * 删除规则明细
     */
    public void deleteShippingRoleLine(Long id) {
        shippingPointRoleLineDao.deleteByPrimaryKey(id);
    }


    /**
     * 删除工作台规则明细
     */
    public void deleteCheckRoleLine(Long id) {
        checkingSpaceRoleDao.deleteByPrimaryKey(id);
    }


    /**
     * 集货规则明细
     */
    @SuppressWarnings("unchecked")
    public ReadStatus importShippingRole(File file, Long ouId) throws Exception {
        log.debug("===========userWarehouseRefImport start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<ShippingPointRoleLineCommand> roleList = null;
        ReadStatus rs = null;
        try {
            rs = shippingRoleReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SORT_ERROR));
                return rs;
            }
            roleList = (List<ShippingPointRoleLineCommand>) beans.get("data");
            saveShiipingRole(roleList, rs, ouId);
            return rs;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }

    /**
     * 保存集货规则明细
     * 
     * @param userList
     */
    private void saveShiipingRole(List<ShippingPointRoleLineCommand> roleList, ReadStatus rs, Long ouId) {
        int sheet0OffsetRow = 4; // sheet页数据行数
        Map<Long, Long> sortMap = new HashMap<Long, Long>();
        for (ShippingPointRoleLineCommand list : roleList) {
            // 校验集货口，不能为空且必须存在
            if (!"".equals(list.getPointName()) && list.getPointName() != null) {
                ShippingPoint point = shippingPointDao.getPointByName(list.getPointName());
                if (point == null) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.POINTNAME_IS_NULL, new Object[] {sheet0OffsetRow}));
                }
            } else {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.POINTNAME_IS_NULL, new Object[] {sheet0OffsetRow}));
            }
            // 判断优先级
            if (!"".equals(list.getSort()) && list.getSort() != null) {
                // 判断exl优先级是否重复
                if (sortMap.containsKey(list.getSort())) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.SORT_IS_NULL2, new Object[] {sheet0OffsetRow, list.getSort()}));
                }
                if (list.getSort() < 1 || list.getSort() > 9999) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.PRIORITY_ERROR, new Object[] {sheet0OffsetRow, list.getSort()}));
                }
                sortMap.put(list.getSort(), list.getSort());
                // 判断数据库是否重复
                ShippingPointRoleLine point = shippingPointRoleLineDao.getPointRoleLineBySort(list.getSort(), ouId);
                if (point != null) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.SORT_IS_NULL2, new Object[] {sheet0OffsetRow, point.getSort()}));
                }
            } else {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.SORT_IS_NULL3, new Object[] {sheet0OffsetRow}));
            }
            // 物流商转换
            if (!"".equals(list.getLpcode()) && list.getLpcode() != null) {
                Transportator ts = TransportatorDao.findByCode(list.getLpcode());
                if (ts == null) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.LPCODE_IS_NULL, new Object[] {sheet0OffsetRow}));
                }
            }
            // 是否COD
            if (!"".equals(list.getIsCodStr()) && list.getIsCodStr() != null) {
                if (!"是".equals(list.getIsCodStr()) && !"否".equals(list.getIsCodStr())) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.COD_IS_ERROR, new Object[] {sheet0OffsetRow}));
                }
            }
            // 时效类型转换
            if (!"".equals(list.getTimeTypeStr()) && list.getTimeTypeStr() != null) {
                if (!"次晨".equals(list.getTimeTypeStr()) && !"当日".equals(list.getTimeTypeStr()) && !"次日".equals(list.getTimeTypeStr()) && !"普通".equals(list.getTimeTypeStr()) && !"及时达".equals(list.getTimeTypeStr())) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.TIME_TYPE_IS_ERROR, new Object[] {sheet0OffsetRow}));
                }
            }
            // 店铺转换
            if (!"".equals(list.getOwner()) && list.getOwner() != null) {
                BiChannel channel = biChannelDao.getByName(list.getOwner());
                if (channel == null) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.OWNER_IS_ERROR, new Object[] {sheet0OffsetRow}));
                }
            }
            // 作业类型转换
            if (!"".equals(list.getStaType()) && list.getStaType() != null) {
                ChooseOption choose = chooseOptionDao.findByCategoryCodeAndValue("whSTAType", list.getStaType());
                if (choose == null) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.STA_TYPE_IS_ERROR, new Object[] {sheet0OffsetRow}));
                }
            }
            sheet0OffsetRow++;
        }
        if (rs.getStatus() != -1) {
            for (ShippingPointRoleLineCommand list : roleList) {
                ShippingPointRoleLine line = new ShippingPointRoleLine();
                line.setLpcode(list.getLpcode());
                line.setProvince(list.getProvince());
                line.setCity(list.getCity());
                line.setDistrict(list.getDistrict());
                line.setAddress(list.getAddress());
                line.setSort(list.getSort());
                line.setStaCode(list.getStaCode());
                line.setOuId(ouId);
                // 是否COD
                if (!"".equals(list.getIsCodStr()) && list.getIsCodStr() != null) {
                    if ("是".equals(list.getIsCodStr())) {
                        line.setIsCod(true);
                    } else {
                        line.setIsCod(false);
                    }
                }
                // 时效类型转换
                if (!"".equals(list.getTimeTypeStr()) && list.getTimeTypeStr() != null) {
                    if ("当日".equals(list.getTimeTypeStr())) {
                        line.setTimeType(TransTimeType.SAME_DAY);
                    } else if ("次日".equals(list.getTimeTypeStr())) {
                        line.setTimeType(TransTimeType.THE_NEXT_DAY);
                    } else if ("普通".equals(list.getTimeTypeStr())) {
                        line.setTimeType(TransTimeType.ORDINARY);
                    } else if ("及时达".equals(list.getTimeTypeStr())) {
                        line.setTimeType(TransTimeType.TIMELY);
                    } else if ("次晨".equals(list.getTimeTypeStr())) {
                        line.setTimeType(TransTimeType.THE_NEXT_MORNING);
                    }
                }
                // 店铺转换
                if (!"".equals(list.getOwner()) && list.getOwner() != null) {
                    BiChannel channel = biChannelDao.getByName(list.getOwner());
                    line.setOwner(channel.getCode());
                }
                // 作业类型转换
                if (!"".equals(list.getStaType()) && list.getStaType() != null) {
                    ChooseOption choose = chooseOptionDao.findByCategoryCodeAndValue("whSTAType", list.getStaType());
                    line.setType(StockTransApplicationType.valueOf(Integer.parseInt(choose.getOptionKey())));
                }
                ShippingPoint point = shippingPointDao.getPointByName(list.getPointName());
                line.setPoint(point);
                shippingPointRoleLineDao.save(line);
            }
        }

    }

    /**
     * 根据参数查询上架规则
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<InboundRoleCommand> findInboundRoleByParams(int start, int pageSize, Map<String, Object> params) {
        return inboundRoleDao.findInboundRoleByParams(start, pageSize, params, new BeanPropertyRowMapper<InboundRoleCommand>(InboundRoleCommand.class));
    }

    @Override
    public String saveInboundRole(InboundRoleCommand inboundRoleCommand) {
        String msg = "";
        if (!StringUtils.hasText(inboundRoleCommand.getOwner())) {
            msg = verifySaveData(inboundRoleCommand);
        }
        if (!"".equals(msg)) {
            return msg;
        }

        Long cId = inboundRoleCommand.getChannelId();
        BiChannel biChannel = null;
        if (cId != null) {
            biChannel = biChannelDao.getByPrimaryKey(cId);
        } else {
            biChannel = biChannelDao.getByName(inboundRoleCommand.getOwner());
        }

        String skuCode = inboundRoleCommand.getSkuCode();
        String locationCode = inboundRoleCommand.getLocationCode();
        Long skuTypeId = inboundRoleCommand.getSkuTypeId();
        WarehouseLocation w = warehouseLocationDao.findLocationByCode(locationCode, inboundRoleCommand.getOuId());
        PopUpArea pa = null;
        if (w == null || w.getPopUpArea() == null) {

            pa = popUpAreaDao.getPopUpAreaByCode(inboundRoleCommand.getPopUpAraeCode());
        } else {
            pa = w.getPopUpArea();
        }

        OperationUnit ou = operationUnitDao.getByPrimaryKey(inboundRoleCommand.getOuId());
        SkuType st = null;
        if (skuTypeId != null) {
            st = skuTypeDao.getByPrimaryKey(skuTypeId);
        } else if (inboundRoleCommand.getSkuTypeName() != null) {
            st = skuTypeDao.getSkuTypeByName(inboundRoleCommand.getSkuTypeName());
        }
        Sku sku = null;
        if (StringUtils.hasText(skuCode)) {
            sku = skuDao.getByCode(skuCode);
        }
        InboundRole ir = new InboundRole();
        ir.setLv(inboundRoleCommand.getLv());
        ir.setOperationUnit(ou);
        if (biChannel != null) {
            ir.setOwner(biChannel.getCode());
        }
        ir.setLocation(w);
        ir.setTargetLocation(pa);
        ir.setSkuType(st);
        ir.setSku(sku);

        inboundRoleDao.save(ir);

        return msg;
    }

    /**
     * 验证规则是否正确
     * 
     * @param inboundRoleCommand
     * @return
     */
    public String verifySaveData(InboundRoleCommand inboundRoleCommand) {
        String msg = "";
        Long cId = inboundRoleCommand.getChannelId();
        /*
         * if (cId == null && !StringUtils.hasText(inboundRoleCommand.getOwner())) { msg +=
         * "店铺不可为空！\r\n"; }
         */
        if (cId != null || StringUtils.hasText(inboundRoleCommand.getOwner()) || StringUtils.hasText(inboundRoleCommand.getSkuCode()) || inboundRoleCommand.getSkuTypeId() != null || inboundRoleCommand.getSkuTypeName() != null) {
            if (StringUtils.hasText(inboundRoleCommand.getOwner())) {
                BiChannel bi = biChannelDao.getByName(inboundRoleCommand.getOwner());
                if (bi == null) {
                    msg += "店铺不正确！\r\n";
                }
            }
            String lvStr = inboundRoleCommand.getLvStr();
            if (lvStr != null && !"".equals(lvStr)) {
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = pattern.matcher(lvStr);
                if (!isNum.matches()) {
                    msg += "优先级格式不正确！\r\n";
                } else {
                    inboundRoleCommand.setLv(Integer.parseInt(lvStr));
                }
            }

            if (inboundRoleCommand.getLv() != null) {

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("ouId", inboundRoleCommand.getOuId());
                params.put("lv", inboundRoleCommand.getLv());
                Pagination<InboundRoleCommand> pIc = findInboundRoleByParams(-1, -1, params);
                if (pIc != null && pIc.getCount() > 0) {
                    if (inboundRoleCommand.getId() == null) {
                        msg += "优先级必须唯一！\r\n";
                    } else {
                        boolean b = false;
                        for (InboundRoleCommand ic : pIc.getItems()) {
                            if (ic.getId().equals(inboundRoleCommand.getId())) {
                                b = true;
                            }
                        }
                        if (!b) {
                            msg += "优先级必须唯一！\r\n";
                        }
                    }
                }

            } else if (lvStr == null || "".equals(lvStr)) {
                msg += "优先级不可为空！\r\n";
            }

            String locationCode = inboundRoleCommand.getLocationCode();
            String popUpAreaCode = inboundRoleCommand.getPopUpAraeCode();
            Long popUpAreaIdL = null;
            if (StringUtils.hasText(locationCode) || StringUtils.hasText(popUpAreaCode)) {
                if (StringUtils.hasText(locationCode)) {
                    WarehouseLocation w = warehouseLocationDao.findLocationByCode(locationCode, inboundRoleCommand.getOuId());
                    if (w == null) {
                        msg += "库位不存在！\r\n";
                    } else if (w.getPopUpArea() == null) {
                        msg += "该库位的弹出口区域没有维护！\r\n";
                    } else {
                        popUpAreaIdL = w.getPopUpArea().getId();
                    }
                }
                if (StringUtils.hasText(popUpAreaCode)) {
                    PopUpArea pa = popUpAreaDao.getPopUpAreaByCode(inboundRoleCommand.getPopUpAraeCode());
                    if (pa == null) {
                        msg += "弹出口不存在！\r\n";
                    } else if (popUpAreaIdL != null && !pa.getId().equals(popUpAreaIdL)) {
                        msg += "库位与弹出口不匹配！\r\n";
                    }

                }
            } else {
                msg += "库位条码和弹出口编码不可都为空！\r\n";
            }
            /*
             * if (inboundRoleCommand.getLv() == null) { msg += "优先级不可为空！\r\n"; }
             */
            String skuCode = inboundRoleCommand.getSkuCode();
            Long skuTypeId = inboundRoleCommand.getSkuTypeId();
            if (skuTypeId == null && inboundRoleCommand.getSkuTypeName() != null) {
                SkuType skuTypes = skuTypeDao.getSkuTypeByName(inboundRoleCommand.getSkuTypeName());
                if (skuTypes == null) {
                    msg += "【" + inboundRoleCommand.getSkuTypeName() + "】此商品上架类型未维护或已被禁用！\r\n";
                } else {
                    skuTypeId = skuTypes.getId();
                }
            }
            Sku sku = null;
            sku = skuDao.getByCode(skuCode);
            if (StringUtils.hasText(skuCode) && sku == null) {
                msg += "商品编码不存在！\r\n";
            }
            // 判断商品上架类型和规则的上架类型是否匹配
            if (skuTypeId != null && sku != null) {
                if (sku.getSkuType() != null) {
                    if (!sku.getSkuType().getId().equals(skuTypeId)) {
                        msg += "商品上架类型跟【" + skuCode + "】此商品的上架类型不匹配！\r\n";
                    }
                } else {
                    msg += "【" + skuCode + "】此商品的上架类型未维护！\r\n";
                }
            }

        } else {
            msg += "店铺、商品上架类型和商品条码不可都为空！\r\n";
        }

        return msg;
    }

    /**
     * 根据自动化仓库获取店铺
     * 
     * @param ouId
     * @return
     */
    public List<BiChannel> findChannelByAutoWh(Long ouId) {
        return inboundRoleDao.findChannelByAutoWh(ouId, new BeanPropertyRowMapper<BiChannel>(BiChannel.class));
    }


    /**
     * 根据仓库及弹出口获取库位
     * 
     * @param ouId
     * @param popId
     * @return
     */
    public List<WarehouseLocation> findLocationByZoon(Long ouId, Long popId) {
        return inboundRoleDao.findLocationByZoon(ouId, popId, new BeanPropertyRowMapper<WarehouseLocation>(WarehouseLocation.class));
    }

    /**
     * 修改上架规则
     * 
     * @param inboundRoleCommand
     * @return
     */
    @Override
    public String updateInboundRole(InboundRoleCommand inboundRoleCommand) {
        String msg = verifySaveData(inboundRoleCommand);
        if (!"".equals(msg)) {
            return msg;
        }
        String skuCode = inboundRoleCommand.getSkuCode();
        String locationCode = inboundRoleCommand.getLocationCode();
        Long cId = inboundRoleCommand.getChannelId();
        Long skuTypeId = inboundRoleCommand.getSkuTypeId();
        WarehouseLocation w = warehouseLocationDao.findLocationByCode(locationCode, inboundRoleCommand.getOuId());
        PopUpArea pa = null;
        if (w == null || w.getPopUpArea() == null) {

            pa = popUpAreaDao.getPopUpAreaByCode(inboundRoleCommand.getPopUpAraeCode());
        } else {
            pa = w.getPopUpArea();
        }
        BiChannel biChannel = null;
        if (cId != null) {
            biChannel = biChannelDao.getByPrimaryKey(cId);
        } else {
            biChannel = biChannelDao.getByName(inboundRoleCommand.getOwner());
        }

        SkuType st = null;
        if (skuTypeId != null) {
            st = skuTypeDao.getByPrimaryKey(skuTypeId);
        }
        Sku sku = null;
        if (StringUtils.hasText(skuCode)) {
            sku = skuDao.getByCode(skuCode);
        }
        InboundRole ir = inboundRoleDao.getByPrimaryKey(inboundRoleCommand.getId());
        ir.setLv(inboundRoleCommand.getLv());
        if (biChannel != null) {
            ir.setOwner(biChannel.getCode());
        } else {
            ir.setOwner(null);
        }
        ir.setLocation(w);
        ir.setTargetLocation(pa);
        ir.setSkuType(st);
        ir.setSku(sku);

        return msg;
    }

    @Override
    public void deleteInboundRoleByIds(List<Long> idList) {
        for (Long id : idList) {
            inboundRoleDao.deleteByPrimaryKey(id);
        }
    }

    /**
     * 校验商品与商品上架类型的关联
     * 
     * @param inboundRoleCommand
     * @return
     */
    public String verifySkuAndSkuType(InboundRoleCommand inboundRoleCommand) {
        String msg = "";
        if (!StringUtils.hasText(inboundRoleCommand.getSkuCode()) || inboundRoleCommand.getSkuTypeName() == null) {

            msg += "商品上架类型和商品条码不可为空！";
        } else {
            String skuCode = inboundRoleCommand.getSkuCode();
            SkuType skuTypes = skuTypeDao.getSkuTypeByName(inboundRoleCommand.getSkuTypeName());
            if (skuTypes == null) {
                msg += "【" + inboundRoleCommand.getSkuTypeName() + "】此商品上架类型未维护或已被禁用！";
            }
            inboundRoleCommand.setSkuType(skuTypes);
            Sku sku = null;
            sku = skuDao.getByCode(skuCode);
            if (StringUtils.hasText(skuCode) && sku == null) {
                msg += "【" + skuCode + "】此商品编码不存在！";
            } else if (sku.getSkuType() != null) {
                msg += "此商品的商品上架类型已存在，如需修改，请到页面进行维护！";
            }
            inboundRoleCommand.setSku(sku);
        }
        return msg;
    }


    public String verifySkuSpAndSkuType(InboundRoleCommand inboundRoleCommand) {
        String msg = "";
        if (!StringUtils.hasText(inboundRoleCommand.getSkuCode()) || !StringUtils.hasText(inboundRoleCommand.getSkuSpCode())) {

            msg += "商品条码和耗材条码不可为空!";
        } else {

            String skuCode = inboundRoleCommand.getSkuCode();
            String skuSpCode = inboundRoleCommand.getSkuSpCode();
            Sku sku = skuDao.getByCode(skuCode);
            if (sku == null) {
                msg += "【" + inboundRoleCommand.getSkuTypeName() + "不存在】";
            }
            sku = skuDao.getByCode(skuSpCode);
            if (sku == null) {
                msg += "【" + skuSpCode + "耗材商品编码不存在!】";
            }

        }
        return msg;
    }

    public String verifyChannelSkuSpAndSkuType(InboundRoleCommand inboundRoleCommand) {
        String msg = "";
        if (!StringUtils.hasText(inboundRoleCommand.getSkuCode()) || inboundRoleCommand.getSkuSpCode() == null || !StringUtils.hasText(inboundRoleCommand.getOwner())) {

            msg += "店铺商品条码和耗材条码不可为空!";
        } else {
            String owner = inboundRoleCommand.getOwner();
            String skuCode = inboundRoleCommand.getSkuCode();
            String skuSpCode = inboundRoleCommand.getSkuSpCode();
            Sku sku = skuDao.getByCode(skuCode);
            if (sku == null) {
                msg += "【 商品" + skuCode + "不存在】";
            }

            sku = skuDao.getByCode(skuSpCode);
            if (sku == null) {
                msg += "【" + skuSpCode + "耗材商品编码不存在!】";
            } else {
                if (null != sku.getSpType() && !"".equals(sku.getSpType()) && sku.getSpType().equals(SkuSpType.CONSUMPTIVE_MATERIAL)) {} else {
                    msg += "【" + skuSpCode + "不是耗材商品!】";
                }
            }

            BiChannel biChannel = biChannelDao.getByName(owner);
            if (biChannel == null) {
                msg += "【" + owner + "不存在!】";
            }
        }
        return msg;
    }

    public String saveContainPop(String containCode, String popCode, Long ouId) {
        String result = ""; // 1: 货箱条码不存在，但保存成功。 2：保存失败，货箱条码已绑定。 3:保存成功
        // ContainerPop pop = containerPopDao.getContainerPopByCode(containCode);
        // if (pop != null) {
        // result = "2" + pop.getPopCode();
        // return result;
        // }
        ContainerPop pops = new ContainerPop();
        pops.setContainerCode(containCode);
        pops.setPopCode(popCode);
        pops.setCreateTime(new Date());
        pops.setOuId(ouId);
        containerPopDao.save(pops);
        result = "3";
        // 根据条码去查作业单
        // StockTransApplication sta = staDao.getByContainCode(containCode, ouId);
        // if (sta == null) {
        // result = "1";
        // }
        // 封装数据成json格式
        SShouRongQi ss = new SShouRongQi();
        ss.setContainerNO(containCode); // 容器号
        ss.setDestinationNO(popCode); // 目的地位置
        String context = net.sf.json.JSONObject.fromObject(ss).toString();
        // 保存数据到中间表
        MsgToWcs msg = new MsgToWcs();
        msg.setContext(context);
        msg.setContainerCode(containCode);
        msg.setCreateTime(new Date());
        msg.setErrorCount(0);
        msg.setStatus(true);
        msg.setType(WcsInterfaceType.SShouRongQi);
        msgToWcsDao.save(msg);
        msgToWcsDao.flush();
        return result + "_" + msg.getId();
    }

    @Override
    public Map<String, Object> recommandLocationByStv(Long stvId) {
        Map<String, Object> m = new HashMap<String, Object>();
        StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
        List<PopUpArea> list = new ArrayList<PopUpArea>();
        Map<String, PopUpArea> map = new HashMap<String, PopUpArea>();
        /**
         * 算法：<br/>
         * 1、根据收货内容确认本次收货商品范围<br/>
         * 2、根据商品范围同步确认商品上架类型范围<br/>
         * 3、根据商品、商品上架类型、店铺、仓库确认上架库位<br/>
         * 4、根据上架库位和弹出口绑定关系确认弹出口,并根据弹出口顺序给出最终弹出口推荐结果
         */
        StringBuffer sf = new StringBuffer("");
        // 注意该查询语句，其中getBoxQty（）映射的是sku_type_id
        List<Sku> sList = skuDao.findSkuListByStvId(stvId, new BeanPropertyRowMapper<Sku>(Sku.class));
        for (Sku sku : sList) {

            PopUpArea p = recommandLocationBySku(sku.getId(), sku.getBoxQty(), stv.getOwner());
            if (p != null) {
                map.put(p.getCode(), p);
            } else {
                sf.append(sku.getCode() + "（条码" + sku.getBarCode() + "),");
            }
        }
        if (!sf.toString().equals("")) {
            // throw new BusinessException(ErrorCode.RECOMMAND_LOCATION_BYSKU_ERROR, new Object[]
            // {sf});
            String error = "存在商品[" + sf.toString() + "]没有推荐到合适库位/弹出口,请检查基础配置！";
            m.put("ERROR", error);
            return m;
        }
        list.addAll(map.values());
        Collections.sort(list, new Comparator<PopUpArea>() {
            @Override
            public int compare(PopUpArea o1, PopUpArea o2) {
                return o1.getSort().compareTo(o2.getSort());
            }

        });
        m.put("DATA", list);
        return m;
    }

    /**
     * @param skuId
     * @param skuTypeId
     * @param owner
     * @return
     */
    private PopUpArea recommandLocationBySku(Long skuId, Long skuTypeId, String owner) {
        return popUpAreaDao.recommandLocationBySku(skuId, skuTypeId, owner, new BeanPropertyRowMapper<PopUpArea>(PopUpArea.class));
    }

    /**
     * 修改商品的商品上架类型
     * 
     * @param skuCode
     * @param skuTypeName
     */
    public void updateSkuBySkuType(Long skuId, Long skuTypeId) {
        skuTypeDao.updateSkuBySkuType(skuId, skuTypeId);
    }

    /**
     * 库位与弹出口导入
     * 
     * @param file
     * @param ouId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ReadStatus importLocationAndPopUp(File file, Long ouId) throws Exception {
        log.debug("===========userWarehouseRefImport start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<WarehouseLocation> roleList = null;
        ReadStatus rs = null;
        try {
            rs = locationAndPopUpReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SORT_ERROR));
                return rs;
            }
            roleList = (List<WarehouseLocation>) beans.get("data");
            String msg = updateLocationAndPopUp(roleList, ouId);
            if (msg != null && !"".equals(msg)) {
                rs.setMessage(msg);
                rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
            } else {
                rs.setStatus(ReadStatus.STATUS_SUCCESS);
            }
            return rs;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }

    /**
     * 更新库位与弹出口区域的关系
     * 
     * @param wllList
     * @param ouId
     * @return
     */
    public String updateLocationAndPopUp(List<WarehouseLocation> wllList, Long ouId) {
        String msg = "";
        // for (WarehouseLocation wl : wllList) {// 校验
        // if (wl.getCode() == null || "".equals(wl.getCode())) {
        // msg = msg + "库位不可以为空！\r\n";
        // } else {
        // WarehouseLocation w = warehouseLocationDao.findByLocationCode(wl.getCode(), ouId);
        // if (w == null) {
        // msg = msg + "库位编码[" + wl.getCode() + "]不存在！\r\n";
        // }
        // }
        // if (wl.getPopUpAreaCode() != null && !"".equals(wl.getPopUpAreaCode())) {
        // PopUpArea p = popUpAreaDao.getPopUpAreaByCodeAndOuid(wl.getPopUpAreaCode());
        // if (p == null) {
        // msg = msg + "弹出口区域编码[" + wl.getPopUpAreaCode() + "]不存在！\r\n";
        // }
        // }
        //
        // }
        if (msg == null || "".equals(msg)) {
            for (WarehouseLocation wl : wllList) {// 修改
                /*
                 * WarehouseLocation w = warehouseLocationDao.findByLocationCode(wl.getCode(),
                 * ouId); if (w == null) { msg = msg + "库位编码[" + wl.getCode() + "]不存在！\r\n"; return
                 * msg; } if (wl.getPopUpAreaCode() != null && !"".equals(wl.getPopUpAreaCode())) {
                 * PopUpArea p = popUpAreaDao.getPopUpAreaByCodeAndOuid(wl.getPopUpAreaCode()); if
                 * (p == null) { msg = msg + "弹出口区域编码[" + wl.getPopUpAreaCode() + "]不存在！\r\n";
                 * return msg; } w.setPopUpArea(p);
                 * 
                 * } else { w.setPopUpArea(null); }
                 */
                if (!StringUtil.isEmpty(wl.getCode())) {
                    warehouseLocationDao.updateLocationPopByCode(wl.getCode(), ouId, wl.getPopUpAreaCode());
                }
            }
        }
        return msg;
    }

    /**
     * 查看弹出口是否被库位使用
     * 
     * @param idList
     * @return
     */
    public String checkLocationUsePopUpArea(List<Long> idList) {
        String msg = "";
        for (Long id : idList) {

            List<WarehouseLocation> wl = warehouseLocationDao.getWarehouseLocationByPopUpAreaId(id);
            if (wl != null && wl.size() > 0) {
                PopUpArea p = popUpAreaDao.getByPrimaryKey(id);
                msg = msg + "弹出口" + p.getName() + "有库位正在使用！\r\n";
            }
        }
        return msg;
    }

    /**
     * 查看弹出口是否被入库规则使用
     * 
     * @param idList
     * @return
     */
    public String checkInBoundRoleUsePopUpArea(List<Long> idList) {
        String msg = "";
        for (Long id : idList) {
            List<InboundRole> ir = inboundRoleDao.getInboundRoleByPopUpAreaId(id);

            if (ir != null && ir.size() > 0) {
                PopUpArea p = popUpAreaDao.getByPrimaryKey(id);
                msg = msg + "弹出口" + p.getName() + "有入库规则正在使用！\r\n";
            }
        }
        return msg;
    }

    /**
     * 统计创建交接的包裹数量
     * 
     * @param lpCode
     * @return
     */
    public Pagination<CountPackageCommand> findCountPackageByOutbound(int start, int pageSize, String lpCode) {
        Pagination<CountPackageCommand> p = handOverListDao.countPackageByLpcode(start, pageSize, lpCode, new BeanPropertyRowMapper<CountPackageCommand>(CountPackageCommand.class));
        return p;
    }

    /**
     * 取消入库货箱流向
     * 
     * @param code
     * @return
     */
    public Long cancelAutoBox(String code) {
        // 封装数据成json格式
        SShouRongQi ss = new SShouRongQi();
        ss.setContainerNO(code); // 容器号
        String context = net.sf.json.JSONObject.fromObject(ss).toString();
        // 保存数据到中间表
        MsgToWcs msg = new MsgToWcs();
        msg.setContext(context);
        msg.setContainerCode(code);
        msg.setCreateTime(new Date());
        msg.setErrorCount(0);
        msg.setStatus(true);
        msg.setType(WcsInterfaceType.SQuxiaoRongQi);
        msgToWcsDao.save(msg);
        msgToWcsDao.flush();

        return msg.getId();
    }

    @Override
    public CheckingSpaceRole getCheckSpaceByCondition(Long pbId, Integer type, Long staId) {
        return checkingSpaceRoleDao.findCheckSpaceByCondition(pbId, type, staId, new BeanPropertyRowMapper<CheckingSpaceRole>(CheckingSpaceRole.class));
    }

    /**
     * 根据商品和店铺获取弹出口列表
     * 
     * @param skuId
     * @param skuTypeId
     * @param owner
     * @return
     */
    public List<PopUpArea> recommandLocationListBySku(Long skuId, Long skuTypeId, String owner) {
        return popUpAreaDao.recommandLocationListBySku(skuId, skuTypeId, owner, new BeanPropertyRowMapper<PopUpArea>(PopUpArea.class));
    }

    /**
     * 
     * @author LuYingMing
     * @see com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager#findShippingPointForCascadeByRefId(java.lang.Long)
     */
    @Override
    public List<Long> findShippingPointForCascadeByRefId(Long refShippingPointId) {
        List<Long> idList = new ArrayList<Long>();
        if (null != refShippingPointId) {
            String refId = refShippingPointId.toString();
            List<ShippingPoint> list = shippingPointDao.getPointByRefShippingPoint(refId, new BeanPropertyRowMapper<ShippingPoint>(ShippingPoint.class));
            if (null != list && list.size() > 0) {
                for (ShippingPoint shippingPoint : list) {
                    Long shippingPointId = shippingPoint.getId();
                    idList.add(shippingPointId);
                }

            }
        }
        return idList;


    }

    /**
     * 集货规则明细
     */
    @SuppressWarnings("unchecked")
    public ReadStatus importShippingCollect(File file, String id, User user) throws Exception {
        log.debug("===========userWarehouseRefImport start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<GoodsCollection> roleList = null;
        ReadStatus rs = null;
        try {
            rs = shippingCollectionReaderImprot.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SORT_ERROR));
                return rs;
            }
            roleList = (List<GoodsCollection>) beans.get("data");
            rs = checkShippingCollect(roleList, rs, Long.valueOf(id));
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            for (GoodsCollection list : roleList) {
                GoodsCollection goodsCollection = new GoodsCollection();
                goodsCollection.setCollectionCode(list.getCollectionCode());
                goodsCollection.setModifier(user);
                goodsCollection.setModifyDate(new Date());
                goodsCollection.setPhysicalId(physicalWarehouseDao.getByPrimaryKey(Long.valueOf(id)));
                goodsCollection.setPopUpCode(list.getPopUpCode());
                goodsCollection.setSort(list.getSort());
                goodsCollection.setStatus(1);
                goodsCollectionDao.save(goodsCollection);
            }
            return rs;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }


    public ReadStatus checkShippingCollect(List<GoodsCollection> goodsCollectionList, ReadStatus rs, Long ouId) {
        Map<String, GoodsCollection> map = new HashMap<String, GoodsCollection>();
        List<GoodsCollection> sortList = goodsCollectionDao.querySortByOuId(ouId);
        for (int i = 0; i < goodsCollectionList.size(); i++) {
            if (null != map && map.containsKey(goodsCollectionList.get(i).getCollectionCode())) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.CODE_IS_REPEAT_ERROR, new Object[] {goodsCollectionList.get(i).getCollectionCode()}));
            } else {
                map.put(goodsCollectionList.get(i).getCollectionCode(), goodsCollectionList.get(i));
            }
        }
        Set<Integer> sortSet = new HashSet<Integer>();
        for (GoodsCollection list : goodsCollectionList) {
            GoodsCollection goodsCollection = goodsCollectionDao.findGoodsCollectionByCode(list.getCollectionCode(), ouId);
            if (null != goodsCollection && !"".equals(goodsCollection)) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.CODE_IS_EXIT_ERROR, new Object[] {list.getCollectionCode()}));
            }
            if (null != list.getSort() && list.getSort() % 1 != 0) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.SORT_IS_NOT_POSITIVE_INTEGER, new Object[] {list.getSort()}));
            }
            if (null != list.getSort() && list.getSort() % 1 == 0) {
                if (null != sortSet && sortSet.size() > 0) {
                    if (!sortSet.contains(list.getSort())) {
                        sortSet.add(list.getSort());
                    } else {
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.SORT_IS_REPEAT, new Object[] {list.getSort()}));
                    }
                } else {
                    sortSet.add(list.getSort());
                }
            }
            if (null != list.getPopUpCode() && !"".equals(list.getPopUpCode())) {
                PopUpArea popUpArea = popUpAreaDao.getPopUpAreaByCodeAndOuid(list.getPopUpCode());
                if (null == popUpArea || "".equals(popUpArea)) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.POPUPAREA_IS_NOT_EXIT, new Object[] {list.getPopUpCode()}));
                }
            }
        }

        for (Integer set : sortSet) {
            for (GoodsCollection list : sortList) {
                if (list.getSort().intValue() == set) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.SORT_IS_REPEAT_EXIT, new Object[] {set}));
                }
            }
        }

        return rs;
    }

    /**
     * 
     * @author LuYingMing
     * @see com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager#batchRemove(java.lang.String[])
     */
    @Override
    public void batchRemove(String[] arrStr) {
        if (null != arrStr && arrStr.length > 0) {
            for (int i = 0; i < arrStr.length; i++) {
                deleteShippingById(arrStr[i]);
            }
        }

    }

    @Override
    public List<GoodsCollection> findShippingPointCollection(Long ouId) {
        return goodsCollectionDao.findGoodsCollection(ouId, new BeanPropertyRowMapper<GoodsCollection>(GoodsCollection.class));
    }

    @Override
    public List<ZoonCommand> findZoonByParams2(Long ouId, Sort[] sorts) {
        return zoonDao.findZoonByParams2(ouId, new BeanPropertyRowMapperExt<ZoonCommand>(ZoonCommand.class));
    }

    public List<WarehouseDistrict> findAllDistrictByOuId(Long ouId){
        return warehouseDistrictDao.findDistrictList(ouId, new BeanPropertyRowMapper<WarehouseDistrict>(WarehouseDistrict.class));
    }

}
