package com.jumbo.dao.baseinfo;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.Brand;

@Transactional
public interface BrandDao extends GenericEntityDao<Brand, Long> {

    @NamedQuery
    Brand findBrandByName(@QueryParam("brandName") String name);

    @NamedQuery
    Brand findBrandByNameAndId(@QueryParam("brandName") String name, @QueryParam("id") Long id);

    @NamedQuery
    Brand getByCode(@QueryParam("brandCode") String brandCode);

    @NativeQuery
    List<Brand> findAllBrand(BeanPropertyRowMapper<Brand> beanPropertyRowMapper);
    /**
     * 弹出框查询品牌
     * 
     * @param start
     * @param beanPropertyRowMapper 
     * @param sorts 
     * @param pageSize
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<Brand> findBrandByPage(int start, int pagesize, BeanPropertyRowMapper<Brand> beanPropertyRowMapper, Sort[] sorts);
    
    /**
     * Edw
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<Brand> findEdwTbiBrand(RowMapper<Brand> rowMapper);
}
