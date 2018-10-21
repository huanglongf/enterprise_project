package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.baseinfo.StvCheckImg;

@Transactional
public interface StvCheckImgDao extends GenericEntityDao<StvCheckImg, Long>{

}
