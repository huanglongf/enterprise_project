package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.vmi.warehouse.CompanyShopShare;


@Transactional
public interface CompanyShopShareDao extends GenericEntityDao<CompanyShopShare, Long> {

    @NativeQuery
    List<CompanyShopShare> findShopShares(@QueryParam("groupCode") String groupCode, RowMapper<CompanyShopShare> r);

    @NamedQuery
    List<CompanyShopShare> findShopSharesHql(@QueryParam("groupCode") String groupCode);
}
