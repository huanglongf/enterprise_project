package com.jumbo.dao.vmi.cch;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.cch.CacheMarkdownDetailData;

@Transactional
public interface CacheMarkdownDetailDataDao extends GenericEntityDao<CacheMarkdownDetailData, Long> {

}
