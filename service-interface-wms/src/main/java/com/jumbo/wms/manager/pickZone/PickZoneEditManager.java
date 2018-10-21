package com.jumbo.wms.manager.pickZone;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.command.pickZone.WhPickZoneInfoCommand;
import com.jumbo.wms.model.command.pickZone.WhPickZoonCommand;
import com.jumbo.wms.model.pickZone.WhPickZoon;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;


/**
 * @author gianni.zhang
 * 
 *         2015年7月8日 下午3:58:38
 */
public interface PickZoneEditManager extends BaseManager {
    
    /**
     * 拣货区列表
     * 
     * @param start
     * @param pageSize
     * @param sorts
     * @param code
     * @param name
     * @return
     */
    public Pagination<WhPickZoonCommand> findPickZoneList(int start, int pageSize, Sort[] sorts, String code, String name, Long ouId);
    
    /**
     * 添加拣货区
     * 
     * @param code
     * @param name
     * @param user
     * @param whZoonId
     * @return
     */
    public String addPickZone(String code, String name, User user, OperationUnit ou, Long whZoonId);

    /**
     * 删除拣货区
     * 
     * @param id
     * @param user
     * @return
     */
    public String deletePickZone(Long id, User user);
    
    /**
     * 标记拣货区名称
     * 
     * @param id
     * @param name
     * @param user
     * @param whZoonId
     * @return
     */
    public String editPickZoneName(Long id, String name, User user, Long whZoonId);

    /**
     * 库区下拉列表
     * 
     * @return
     */
    public List<WarehouseDistrict> findDistrictList(Long ouId);
    
    /**
     * 拣货区域下拉列表
     * 
     * @return
     */
    public List<WhPickZoon> findPickZoneInfo(Long ouId);
    
    /**
     * 查找库位
     * 
     * @param start
     * @param pageSize
     * @param sorts
     * @param district
     * @param location
     * @param pickZoneName
     * @param pickZoneCode
     * @return
     */
    public Pagination<WhPickZoneInfoCommand> findPickZoneInfoList(int start, int pageSize, Sort[] sorts, String district, String location, String pickZoneName, String pickZoneCode, Long ouId);
    
    /**
     * 导出至excel
     * 
     * @param district
     * @param location
     * @param pickZoneName
     * @param pickZoneCode
     * @return
     */
    public List<WhPickZoneInfoCommand> findPickZoneInfoList2Excel(String district, String location, String pickZoneName, String pickZoneCode, Long ouId);
    
    /**
     * 计算导出库位的个数
     * 
     * @param district
     * @param location
     * @param pickZoneName
     * @param pickZoneCode
     * @return
     */
    Integer countLocationByParam(String district, String location, String pickZoneName, String pickZoneCode, Long ouId);
   
    List<WhPickZoon> findPickZoneByOuId(Long ouId, Sort[] sorts);

}
