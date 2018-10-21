package com.bt.common.sequence.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bt.common.sequence.Service.SequenceService;
import com.bt.common.sequence.dao.SequenceMapper;
@Transactional(rollbackFor=Exception.class)
public class SequenceServiceImpl implements SequenceService {
	
	@Autowired
	private SequenceMapper sequenceMapper;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Long getSeqNum(String seqStr){
		return sequenceMapper.nextval(seqStr);
	}
	
}
