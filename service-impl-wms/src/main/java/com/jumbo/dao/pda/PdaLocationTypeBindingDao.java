package com.jumbo.dao.pda;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.PdaLocationTypeBinding;


/**
 * 
 * @author lizaibiao
 * 
 */
@Transactional
public interface PdaLocationTypeBindingDao extends GenericEntityDao<PdaLocationTypeBinding, Long> {}
