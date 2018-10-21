package com.lmis.sys.sequence.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.sys.sequence.dao.SysSequenceMapper;
import com.lmis.sys.sequence.model.SysSequence;
import com.lmis.sys.sequence.service.SysSequenceServiceInterface;

@Transactional(rollbackFor=Exception.class)
@Service
public class SysSequenceServiceImpl implements SysSequenceServiceInterface {

	@Autowired
	private SysSequenceMapper<SysSequence> sysSequenceMapper;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Long getSeqNum(String seqStr){
		return sysSequenceMapper.nextval(seqStr);
	}
}
