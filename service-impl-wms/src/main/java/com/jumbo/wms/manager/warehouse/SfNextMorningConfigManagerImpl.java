package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.SfCloudWarehouseConfigDao;
import com.jumbo.dao.warehouse.SfNextMorningConfigDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.SfCloudWarehouseConfig;
import com.jumbo.wms.model.warehouse.SfCloudWarehouseConfigCommand;
import com.jumbo.wms.model.warehouse.SfNextMorningConfig;

/**
 * SF次晨达业务逻辑
 * 
 * @author jinlong.ke
 * @date 2016年4月7日下午2:56:10
 */
@Service("sfNextMorningConfigManager")
public class SfNextMorningConfigManagerImpl extends BaseManagerImpl implements SfNextMorningConfigManager {

    /**
     * 
     */
    private static final long serialVersionUID = -4500569118402740557L;
    @Autowired
    private SfNextMorningConfigDao sfNextMorningConfigDao;
    @Autowired
    private SfCloudWarehouseConfigDao sfCloudWarehouseConfigDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private UserDao userDao;
    @Resource(name = "sfNextMorningConfigReader")
    private ExcelReader sfNextMorningConfigReader;
    @Resource(name = "sfConfigReader")
    private ExcelReader sfConfigReader;
    @Autowired
    private BiChannelDao biChannelDao;

    @Override
    public List<SfNextMorningConfig> findConfigByOuId(Long id) {
        return sfNextMorningConfigDao.findSfNextMorningConfigListByOuId(id, new BeanPropertyRowMapper<SfNextMorningConfig>(SfNextMorningConfig.class));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus importSfNextMorningConfig(File fileSFC, Long ouId, Long userId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<SfNextMorningConfig> noList = null;
        List<SfNextMorningConfig> areaList = new ArrayList<SfNextMorningConfig>();
        ReadStatus rs = null;
        try {
            rs = sfNextMorningConfigReader.readSheet(new FileInputStream(fileSFC), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_DATA_PROVINCE_CITY_NOT_NONE));
                return rs;
            }
            noList = (List<SfNextMorningConfig>) beans.get("configList");
            // 删除所有关联次晨达区域
            sfNextMorningConfigDao.deleteSfNextMorningConfigByOuId(ouId);
            for (SfNextMorningConfig ca : noList) {
                String province = ca.getProvince();
                String city = ca.getCity();
                String district = ca.getDistrict();

                district = (StringUtil.isEmpty(district) ? "" : district);
                if (StringUtil.isEmpty(province) || StringUtil.isEmpty(city)) {
                    throw new BusinessException(ErrorCode.EXCEL_DATA_PROVINCE_CITY_NOT_NONE);
                }
                if (0 < areaList.size()) {
                    boolean isExist = false;
                    for (SfNextMorningConfig a : areaList) {
                        String p = a.getProvince();
                        String c = a.getCity();
                        String d = a.getDistrict();
                        if (province.equals(p) && city.equals(c) && district.equals(d)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (false == isExist) {
                        SfNextMorningConfig sfc = new SfNextMorningConfig();
                        sfc.setProvince(ca.getProvince());
                        sfc.setCity(ca.getCity());
                        sfc.setDistrict(district);
                        sfc.setCreatorId(userDao.getByPrimaryKey(userId));
                        sfc.setCreateTime(new Date());
                        sfc.setOu(operationUnitDao.getByPrimaryKey(ouId));
                        sfNextMorningConfigDao.save(sfc);
                        areaList.add(sfc);
                    }
                } else {
                    SfNextMorningConfig sfc = new SfNextMorningConfig();
                    sfc.setProvince(ca.getProvince());
                    sfc.setCity(ca.getCity());
                    sfc.setDistrict(district);
                    sfc.setCreatorId(userDao.getByPrimaryKey(userId));
                    sfc.setCreateTime(new Date());
                    sfc.setOu(operationUnitDao.getByPrimaryKey(ouId));
                    sfNextMorningConfigDao.save(sfc);
                    areaList.add(sfc);
                }
            }
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("importSfNextMorningConfig exception:" + ouId, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public List<SfCloudWarehouseConfigCommand> findSfConfigByOuId(Long ouId, Long cId) {
        return sfCloudWarehouseConfigDao.findCloudConfigByOuCid(ouId, cId, new BeanPropertyRowMapper<SfCloudWarehouseConfigCommand>(SfCloudWarehouseConfigCommand.class));
    }

    @Override
    public OperationUnit getById(Long id) {
        OperationUnit ou = operationUnitDao.getById(id, new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
        return ou;
    }

    /**
     * 直连sf时效导入
     */

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus importSfConfig(File fileSFT, Long ouId, Long userId, Long cId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<SfCloudWarehouseConfigCommand> noList = null;
        List<SfCloudWarehouseConfig> areaList = new ArrayList<SfCloudWarehouseConfig>();
        Map<String, String> map = new HashMap<String, String>();
        ReadStatus rs = null;
        try {
            rs = sfConfigReader.readSheet(new FileInputStream(fileSFT), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_DATA_PROVINCE_CITY_NOT_NONE));
                return rs;
            }
            noList = (List<SfCloudWarehouseConfigCommand>) beans.get("configList");

            for (SfCloudWarehouseConfigCommand c : noList) {
                if (StringUtil.isEmpty(c.getProvince()) || StringUtil.isEmpty(c.getCity()) || StringUtil.isEmpty(c.getTimeTypeString())) {
                    throw new BusinessException(ErrorCode.EXCEL_DATA_PROVINCE_CITY_NOT_NONE_TIME);
                }
                String timeTypeString = c.getTimeTypeString();
                Integer timeType = getTimeType(timeTypeString);
                if (timeType == null) {
                    throw new BusinessException("sf002");// 时效不正确
                }
                String str = map.get(c.getProvince() + c.getCity());
                if (str == null) {} else {
                    if (!timeTypeString.equals(str)) {
                        throw new BusinessException(ErrorCode.SF_TIME_TYPE_ERROR, new Object[] {c.getProvince(), c.getCity()});
                    }
                }
                map.put(c.getProvince() + c.getCity(), c.getTimeTypeString());
            }

            // 删除所有关联SF时效区域
            sfCloudWarehouseConfigDao.deleteSfConfigByOuIdCid(ouId, cId);
            for (SfCloudWarehouseConfigCommand ca : noList) {
                String province = ca.getProvince();
                String city = ca.getCity();
                String timeTypeString = ca.getTimeTypeString();
                Integer timeType = getTimeType(timeTypeString);
                if (timeType == null) {
                    // throw new BusinessException("sf002");// 时效不正确
                } else {
                    ca.setTimeType(timeType);
                }
                if (0 < areaList.size()) {
                    boolean isExist = false;
                    for (SfCloudWarehouseConfig a : areaList) {
                        String p = a.getProvince();
                        String c = a.getCity();
                        Integer t = a.getTimeType();
                        if (province.equals(p) && city.equals(c) && t == timeType) {
                            isExist = true;
                            break;
                        }
                    }
                    if (false == isExist) {
                        SfCloudWarehouseConfig sfc = new SfCloudWarehouseConfig();
                        sfc.setProvince(ca.getProvince());
                        sfc.setCity(ca.getCity());
                        sfc.setTimeType(ca.getTimeType());
                        sfc.setBiChannel(biChannelDao.getByPrimaryKey(cId));
                        sfc.setUser(userDao.getByPrimaryKey(userId));
                        sfc.setCreateTime(new Date());
                        sfc.setUpdateTime(new Date());
                        sfc.setOu(operationUnitDao.getByPrimaryKey(ouId));
                        sfCloudWarehouseConfigDao.save(sfc);
                        areaList.add(sfc);
                    }
                } else {
                    SfCloudWarehouseConfig sfc = new SfCloudWarehouseConfig();
                    sfc.setProvince(ca.getProvince());
                    sfc.setCity(ca.getCity());
                    sfc.setTimeType(ca.getTimeType());
                    sfc.setBiChannel(biChannelDao.getByPrimaryKey(cId));
                    sfc.setUser(userDao.getByPrimaryKey(userId));
                    sfc.setCreateTime(new Date());
                    sfc.setUpdateTime(new Date());
                    sfc.setOu(operationUnitDao.getByPrimaryKey(ouId));
                    sfCloudWarehouseConfigDao.save(sfc);
                    areaList.add(sfc);
                }
            }
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("importSfConfig exception:" + ouId, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public Integer getTimeType(String timeTypeString) {// 时效名称转换
        if ("普通".equals(timeTypeString)) {
            return 1;
        } else if ("及时达".equals(timeTypeString)) {
            return 3;
        } else if ("当日".equals(timeTypeString)) {
            return 5;
        } else if ("次日".equals(timeTypeString)) {
            return 6;
        } else if ("次晨".equals(timeTypeString)) {
            return 7;
        } else if ("云仓次日".equals(timeTypeString)) {
            return 8;
        } else if ("云仓隔日".equals(timeTypeString)) {
            return 9;
        }
        return null;
    }



}
