package com.jumbo.wms.manager.pickZone;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.jumbo.dao.automaticEquipment.ZoonDao;
import com.jumbo.dao.pickZone.WhPickZoneDao;
import com.jumbo.dao.warehouse.WarehouseDistrictDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.command.pickZone.WhPickZoneInfoCommand;
import com.jumbo.wms.model.command.pickZone.WhPickZoonCommand;
import com.jumbo.wms.model.pickZone.WhPickZoon;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;

/**
 * @author gianni.zhang
 * 
 *         2015年7月8日 下午3:58:05
 */

@Transactional
@Service("pickZoneEditManager")
public class PickZoneEditManagerImpl extends BaseManagerImpl implements PickZoneEditManager {

    /**
     * 
     */
    private static final long serialVersionUID = 6446115391080035648L;

    @Autowired
    private WhPickZoneDao whPickZoneDao;
    @Autowired
    private ZoonDao zoonDao;

    @Autowired
    private WarehouseDistrictDao warehouseDistrictDao;

    public Pagination<WhPickZoonCommand> findPickZoneList(int start, int pageSize, Sort[] sorts, String code, String name, Long ouId) {
        if ("".equals(code)) {
            code = null;
        }
        if ("".equals(name)) {
            name = null;
        }
        return whPickZoneDao.findPickZoneList(start, pageSize, sorts, code, name, ouId, new BeanPropertyRowMapper<WhPickZoonCommand>(WhPickZoonCommand.class));

    }

    @Override
    public String addPickZone(String code, String name, User user, OperationUnit ou, Long whZoonId) {
        Long ouId = null;
        if (ou != null) ouId = ou.getId();
        WhPickZoon pickZone = whPickZoneDao.findPickZoneByCode(code, ouId, new BeanPropertyRowMapper<WhPickZoon>(WhPickZoon.class));
        if (null != pickZone) {
            if (1 == pickZone.getStatus()) {
                log.info("pick zone is existed and status is 1");
                return "EXIST";
            } else {
                log.info("pick zone is existed but status is 0");
            }
        }
        Zoon zoon = null;
        if (whZoonId != null && whZoonId != 0) {
            zoon = zoonDao.getByPrimaryKey(whZoonId);

        }

        try {
            WhPickZoon newPickZone = new WhPickZoon();
            newPickZone.setCode(code);
            newPickZone.setName(name);
            newPickZone.setCreateTime(new Date());
            newPickZone.setLastModifyTime(new Date());
            newPickZone.setStatus(1);
            newPickZone.setCreatorUser(user);
            newPickZone.setLastModifyUser(user);
            newPickZone.setOu(ou);
            newPickZone.setZoon(zoon);
            whPickZoneDao.save(newPickZone);
            return "SUCCESS";
        } catch (Exception e) {
            return "FAILED";
        }
    }

    @Override
    public String deletePickZone(Long id, User user) {
        try {
            WhPickZoon whPickZone = whPickZoneDao.getByPrimaryKey(id);
            if (null != whPickZone) {
                List<StockTransApplication> staList = whPickZoneDao.findStasByZoonId(id, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
                if (staList == null || staList.isEmpty()) {
                    whPickZone.setStatus(0);
                    whPickZone.setLastModifyUser(user);
                    whPickZone.setLastModifyTime(new Date());

                    // 清除库位上的拣货区域
                    whPickZoneDao.clearLocationZoonByZoonId(id);

                    return "SUCCESS";

                } else {
                    return "USEING";
                }


            } else {
                log.info("data not exist");
                return "FAILED";
            }
        } catch (Exception e) {

        }
        return "FAILED";
    }

    @Override
    public String editPickZoneName(Long id, String name, User user, Long whZoonId) {
        try {
            WhPickZoon whPickZone = whPickZoneDao.getByPrimaryKey(id);
            Zoon zoon = null;
            if (whZoonId != null && whZoonId != 0) {
                zoon = zoonDao.getByPrimaryKey(whZoonId);

            }
            if (null != whPickZone) {
                if (!StringUtils.isEmpty(name)) {

                    whPickZone.setName(name);
                }
                whPickZone.setZoon(zoon);
                whPickZone.setLastModifyUser(user);
                whPickZone.setLastModifyTime(new Date());
                return "SUCCESS";
            } else {
                log.info("data not exist");
                return "FAILED";
            }
        } catch (Exception e) {

        }
        return "FAILED";
    }

    @Override
    public List<WarehouseDistrict> findDistrictList(Long ouId) {
        return warehouseDistrictDao.findDistrictList(ouId, new BeanPropertyRowMapper<WarehouseDistrict>(WarehouseDistrict.class));
    }

    @Override
    public List<WhPickZoon> findPickZoneInfo(Long ouId) {
        return whPickZoneDao.findPickZoneInfo(ouId, new BeanPropertyRowMapper<WhPickZoon>(WhPickZoon.class));
    }

    @Override
    public Pagination<WhPickZoneInfoCommand> findPickZoneInfoList(int start, int pageSize, Sort[] sorts, String district, String location, String pickZoneName, String pickZoneCode, Long ouId) {
        if ("请选择".equals(district) || null == district) {
            district = null;
        }
        if ("null".equals(location) || null == location || "".equals(location)) {
            location = null;
        }
        if ("请选择".equals(pickZoneName) || null == pickZoneName) {
            pickZoneName = null;
        }
        if ("请选择".equals(pickZoneCode) || null == pickZoneCode) {
            pickZoneCode = null;
        }
        // Pagination<WhPickZoneInfoCommand> list = whPickZoneDao.findPickZoneInfoList(start,
        // pageSize, sorts, district, location, pickZoneName, pickZoneCode, new
        // BeanPropertyRowMapper<WhPickZoneInfoCommand>(WhPickZoneInfoCommand.class));
        return whPickZoneDao.findPickZoneInfoList(start, pageSize, sorts, district, location, pickZoneName, pickZoneCode, ouId, new BeanPropertyRowMapper<WhPickZoneInfoCommand>(WhPickZoneInfoCommand.class));
    }

    @Override
    public List<WhPickZoneInfoCommand> findPickZoneInfoList2Excel(String district, String location, String pickZoneName, String pickZoneCode, Long ouId) {
        if ("请选择".equals(district) || null == district) {
            district = null;
        }
        if ("null".equals(location) || null == location || "".equals(location)) {
            location = null;
        }
        if ("请选择".equals(pickZoneName) || null == pickZoneName) {
            pickZoneName = null;
        }
        if ("请选择".equals(pickZoneCode) || null == pickZoneCode) {
            pickZoneCode = null;
        }

        List<WhPickZoneInfoCommand> list = whPickZoneDao.findPickZoneInfoList2Excel(district, location, pickZoneName, pickZoneCode, ouId, new BeanPropertyRowMapper<WhPickZoneInfoCommand>(WhPickZoneInfoCommand.class));
        // return whPickZoneDao.findPickZoneInfoList2Excel(district, location, pickZoneName,
        // pickZoneCode, new
        // BeanPropertyRowMapper<WhPickZoneInfoCommand>(WhPickZoneInfoCommand.class));
        return list;
    }

    @Override
    public Integer countLocationByParam(String district, String location, String pickZoneName, String pickZoneCode, Long ouId) {
        Integer exceeded = 0;
        if ("请选择".equals(district) || null == district) {
            district = null;
        }
        if ("null".equals(location) || null == location || "".equals(location)) {
            location = null;
        }
        if ("请选择".equals(pickZoneName) || null == pickZoneName) {
            pickZoneName = null;
        }
        if ("请选择".equals(pickZoneCode) || null == pickZoneCode) {
            pickZoneCode = null;
        }
        BigDecimal count = whPickZoneDao.countLocation(district, location, pickZoneName, pickZoneCode, ouId, new SingleColumnRowMapper<BigDecimal>());
        if (count.compareTo(BigDecimal.valueOf(40000)) == 1) exceeded = 1;
        return exceeded;
    }

    public List<WhPickZoon> findPickZoneByOuId(Long ouId, Sort[] sorts) {
        return whPickZoneDao.findAllPickZone(ouId, new BeanPropertyRowMapperExt<WhPickZoon>(WhPickZoon.class));
    }
}
