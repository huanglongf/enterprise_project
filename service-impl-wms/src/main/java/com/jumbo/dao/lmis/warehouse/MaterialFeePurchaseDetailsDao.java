package com.jumbo.dao.lmis.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lmis.MaterialFeePurchaseDetails;

@Transactional
public interface MaterialFeePurchaseDetailsDao extends GenericEntityDao<MaterialFeePurchaseDetails, Long> {
    @NativeQuery(model = MaterialFeePurchaseDetails.class)
    List<MaterialFeePurchaseDetails> findMaterialFeePurchaseDetailsByTime(@QueryParam(value = "startTime") String startTime, @QueryParam(value = "endTime") String endTime);

    @NativeQuery(pagable = true)
    Pagination<MaterialFeePurchaseDetails> findMaterialFeePurchaseDetailsByTime(int start, int pageSize, @QueryParam(value = "startTime") String startTime, @QueryParam(value = "endTime") String endTime,
            BeanPropertyRowMapper<MaterialFeePurchaseDetails> r, Sort[] sorts);
}
