package com.jumbo.dao.baseinfo;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.invflow.WmsCustomer;

@Transactional
public interface CustomerDao extends GenericEntityDao<Customer, Long> {
    /**
     * 查询客户
     * 
     * @param whouid
     * @return
     */
    @NamedQuery
    Customer getByWhouid(@QueryParam("whouid") Long whouid);

    /**
     * 查询客户
     * 
     * @param code
     * @return
     */
    @NamedQuery
    Customer getByCode(@QueryParam("code") String code);

    /**
     * 查询所有客户列表
     * 
     * @return
     */
    @NativeQuery(model = Customer.class)
    List<Customer> findAllCustomer();

    /**
     * 查询所有客户
     * 
     * @param whouid
     * @return
     */
    @NativeQuery(model = WmsCustomer.class)
    List<WmsCustomer> getwmsAllAvailableCustomerInfo(BeanPropertyRowMapper<WmsCustomer> beanPropertyRowMapper);
}
