/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.dao.pingan;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pingan.WhPingAnCover;
import com.jumbo.wms.model.pingan.WhPingAnCoverCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

@Transactional
public interface WhPingAnCoverDao extends GenericEntityDao<WhPingAnCover, Long> {

	/**
	 * 获取待同步平安投保数据
	 * 
	 * @return
	 */
	@NamedQuery
	List<WhPingAnCover> findPingAnCoverToHub4();

	@NativeQuery
	List<WhPingAnCoverCommand> findPingAnCoverToHub4(RowMapper<WhPingAnCoverCommand> rowMapper);
}
