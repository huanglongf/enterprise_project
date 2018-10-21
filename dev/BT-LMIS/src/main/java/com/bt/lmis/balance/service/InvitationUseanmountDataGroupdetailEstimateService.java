package com.bt.lmis.balance.service;

import java.util.List;

import com.bt.lmis.balance.model.InvitationUseanmountDataGroupdetailEstimate;

public interface InvitationUseanmountDataGroupdetailEstimateService {
	int deleteByPrimaryKey(String id);

    int insert(InvitationUseanmountDataGroupdetailEstimate record);

    int insertSelective(InvitationUseanmountDataGroupdetailEstimate record);

    InvitationUseanmountDataGroupdetailEstimate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InvitationUseanmountDataGroupdetailEstimate record);

    int updateByPrimaryKey(InvitationUseanmountDataGroupdetailEstimate record);

	int save(InvitationUseanmountDataGroupdetailEstimate detail);

	List<InvitationUseanmountDataGroupdetailEstimate> findByCBIDBN(InvitationUseanmountDataGroupdetailEstimate invitationUseanmountDataGroupdetailEstimate);

	int countByCBIDBN(InvitationUseanmountDataGroupdetailEstimate invitationUseanmountDataGroupdetailEstimate);
}
