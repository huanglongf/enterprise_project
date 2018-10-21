/**
 * Create by LuYingMing Since 2016年8月25日 下午1:04:23
 */
package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.List;

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.warehouse.HightProvinceConfigDao;
import com.jumbo.dao.warehouse.PriorityShippedCityConfigDao;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.HightProvinceConfig;
import com.jumbo.wms.model.warehouse.PriorityShippedCityConfig;
import com.jumbo.wms.model.warehouse.PriorityShippedCityConfigCommand;


/**
 * 接口/类说明：优先发货城市配置 Impl
 * 
 * @ClassName: PriorityShippedCityConfigManagerImpl
 * @author LuYingMing
 * @date 2016年8月25日 下午1:04:23
 */
@Service("priorityShippedCityConfigManager")
public class PriorityShippedCityConfigManagerImpl implements PriorityShippedCityConfigManager {

    private static final long serialVersionUID = 6258993135642371207L;

    @Autowired
    private PriorityShippedCityConfigDao psccDao;

    @Autowired
    private OperationUnitDao operationUnitDao;

    @Autowired
    private HightProvinceConfigDao hightProvinceConfigDao;

    /**
     * @author LuYingMing
     * @throws Exception
     * @date 2016年8月25日 下午1:04:23
     * @see com.jumbo.wms.manager.warehouse.PriorityShippedCityConfigManager#save(com.jumbo.wms.model.warehouse.PriorityShippedCityConfig)
     */
    @Override
    public void saveEntity(PriorityShippedCityConfig entity) {

        PriorityShippedCityConfig model = new PriorityShippedCityConfig();
        model.setOuId(entity.getOuId());
        model.setOuTypeId(entity.getOuTypeId());
        model.setCityName(entity.getCityName());
        psccDao.save(model);
    }


    public void saveHightProvinceConfig(HightProvinceConfig hightProvinceConfig) {
        hightProvinceConfigDao.save(hightProvinceConfig);
    }

    /**
     * @author LuYingMing
     * @date 2016年8月25日 下午1:04:24
     * @see com.jumbo.wms.manager.warehouse.PriorityShippedCityConfigManager#delete(java.lang.Long)
     */
    @Override
    public void deleteEntity(String ids) {
        String[] idStrs = ids.split(",");
        for (int i = 0; i < idStrs.length; i++) {
            Long id = Long.parseLong(idStrs[i]);
            psccDao.deleteByPrimaryKey(id);
        }
    }

    public void deleteHightProvinceConfig(String ids) {
        String[] idStrs = ids.split(",");
        for (int i = 0; i < idStrs.length; i++) {
            Long id = Long.parseLong(idStrs[i]);
            hightProvinceConfigDao.deleteByPrimaryKey(id);
        }
    }

    /**
     * @author LuYingMing
     * @date 2016年8月25日 下午7:39:04
     * @see com.jumbo.wms.manager.warehouse.PriorityShippedCityConfigManager#queryPriorityCityConfig(int,
     *      int, java.lang.Long, java.lang.Long, loxia.dao.Sort[])
     */
    @Override
    public Pagination<PriorityShippedCityConfigCommand> queryPriorityCityConfig(int start, int pageSize, Long ouId, Long ouTypeId) {
        Pagination<PriorityShippedCityConfigCommand> aPagination = psccDao.queryPriorityShippedCityConfig(start, pageSize, ouId, ouTypeId, new BeanPropertyRowMapper<PriorityShippedCityConfigCommand>(PriorityShippedCityConfigCommand.class));
        return aPagination;
    }

    @Override
    public Pagination<HightProvinceConfig> queryHightProvinceConfig(int start, int pageSize, Long ouId, Long ouTypeId, HightProvinceConfig hightProvinceConfig) {
        String name = null;
        if (null != hightProvinceConfig) {
            if (StringUtils.hasText(hightProvinceConfig.getPriorityName())) {
                name = hightProvinceConfig.getPriorityName();
            }
        }
        Pagination<HightProvinceConfig> hightProvinceConfigPagination = hightProvinceConfigDao.queryHightProvinceConfig(start, pageSize, ouId, ouTypeId, name, new BeanPropertyRowMapper<HightProvinceConfig>(HightProvinceConfig.class));
        return hightProvinceConfigPagination;
    }

    @Override
    public List<PriorityShippedCityConfigCommand> findPriorityCityList(Long ouId, Long ouTypeId) {
        List<PriorityShippedCityConfigCommand> pList = new ArrayList<PriorityShippedCityConfigCommand>();
        Pagination<PriorityShippedCityConfigCommand> aPagination = psccDao.queryPriorityShippedCityConfig(-1, -1, ouId, ouTypeId, new BeanPropertyRowMapper<PriorityShippedCityConfigCommand>(PriorityShippedCityConfigCommand.class));
        pList = aPagination.getItems();
        if (null == pList || pList.isEmpty()) {
            // 先查仓库→无数据→查集团
            OperationUnit entity = operationUnitDao.getByCode("Root");
            Pagination<PriorityShippedCityConfigCommand> pagination =
                    psccDao.queryPriorityShippedCityConfig(-1, -1, entity.getId(), entity.getOuType().getId(), new BeanPropertyRowMapper<PriorityShippedCityConfigCommand>(PriorityShippedCityConfigCommand.class));
            pList = pagination.getItems();
        }
        return pList;
    }

    @Override
    public List<HightProvinceConfig> findPriorityList(Long ouId, Long ouTypeId) {
        List<HightProvinceConfig> pList = new ArrayList<HightProvinceConfig>();
        Pagination<HightProvinceConfig> aPagination = hightProvinceConfigDao.queryHightProvinceConfig(-1, -1, ouId, ouTypeId, null, new BeanPropertyRowMapper<HightProvinceConfig>(HightProvinceConfig.class));
        pList = aPagination.getItems();
        return pList;
    }



}
