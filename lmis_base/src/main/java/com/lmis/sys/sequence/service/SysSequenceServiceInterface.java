package com.lmis.sys.sequence.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor=Exception.class)
@Service
public interface SysSequenceServiceInterface {

	public Long getSeqNum(String seqStr);
}
