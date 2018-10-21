package com.bt.common.sequence.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceMapper {

	Long currval(@Param("seqId") String seqId);
	Long nextval(@Param("seqId") String seqId);
	int createKey(@Param("seqId") String seqId);

}
