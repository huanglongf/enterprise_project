package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnMsgConfirmReply;

@Transactional
public interface CnMsgConfirmReplyDao extends GenericEntityDao<CnMsgConfirmReply, Long> {

    @NamedQuery
    List<CnMsgConfirmReply> getCnMsgConfirmReplyByNew();
}
