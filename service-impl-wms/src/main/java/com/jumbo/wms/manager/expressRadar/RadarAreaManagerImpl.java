package com.jumbo.wms.manager.expressRadar;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.expressRadar.RadarAreaDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.expressRadar.SysRadarArea;

@Transactional
@Service("radarAreaManager")
public class RadarAreaManagerImpl extends BaseManagerImpl implements RadarAreaManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 285371875290196523L;
    Logger log = LoggerFactory.getLogger(RadarAreaManagerImpl.class);

    @Autowired
    RadarAreaDao radarAreaDao;

    @Override
    public List<SysRadarArea> radarAreaList(String province, String city) {


        return null;
    }


    /**
     * 移除快递省市
     * 
     * @param province
     * @param city
     * @return void
     */
    @Override
    public String removeArea(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return "ERROR";
        }
        try {
            log.debug("删除省市:" + ids);
            radarAreaDao.updateRadarAreaStatus(ids, 0L);
            return "SUCCESS";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * 创建快递省市
     * 
     * @param province
     * @param city
     * @return void
     */
    @Override
    public String addRadarArea(String province, String city) {
        Integer flag = 1;
        Map<String, String> m = new HashMap<String, String>();
        if (city != null && city.length() > 0) {
            m.put("city", city);
        }
        if (province != null && province.length() > 0) {
            m.put("province", province);
        }
        SysRadarArea sra = radarAreaDao.findStatusByArea(m, new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class));
        if (null != sra) {
            if (1L == sra.getStatus()) {
                flag = 0;
                return "EXIST";
            } else {
                flag = 1;
            }
        }
        if (1 == flag) {
            try {
                SysRadarArea sraNew = new SysRadarArea();
                sraNew.setCity(city);
                sraNew.setProvince(province);
                sraNew.setCreateTime(new Date());
                sraNew.setLastModifyTime(new Date());
                sraNew.setStatus(1L);
                radarAreaDao.save(sraNew);
                return "SUCCESS";
            } catch (Exception e) {
                return "ERROR";
            }
        }
        return "ERROR";
    }

    /**
     * 查询快递省市列表
     * 
     * @param province
     * @param city
     * @return Pagination<SysRadarArea>
     */
    @Override
    public Pagination<SysRadarArea> findArea(int start, int pageSize, Sort[] sorts, String province, String city) {
        return radarAreaDao.findRadarAreaByParams(start, pageSize, sorts, province, city, new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class));
    }

}
