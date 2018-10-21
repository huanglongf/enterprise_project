/**
 * DimensionImpl.java com.erry.model.it.impl
 * 
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
 * 
 */

package com.jumbo.dao.vmi.nikeDate;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.nikeData.NikeReturnReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeStockReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeStockReceiveDto;

@Transactional
public interface NikeStockReceiveDao extends GenericEntityDao<NikeStockReceive, Long> {

    @NativeUpdate
    void updateStatusByStaId(@QueryParam("staId") long staId, @QueryParam("vmiRcStatus") int vmiRcStatus);

    @NativeQuery
    List<NikeStockReceive> findNikeStockReceivesUnWrite(BeanPropertyRowMapperExt<NikeStockReceive> beanPropertyRowMapperExt);

    @NativeUpdate
    void updateStatusHasWriteFile(@QueryParam("id") Long id, @QueryParam("finishStatus") Integer finishStatus);

    /**
     * @param returnVmiReturnType
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<NikeReturnReceive> findNikeReturnReceivesUnWrite(@QueryParam("type") Integer type, BeanPropertyRowMapperExt<NikeReturnReceive> beanPropertyRowMapperExt);

    @NativeUpdate
    void updateStatusNikeReturnReceiveHasWriteFile(@QueryParam("id") Long id, @QueryParam("finishStatus") Integer finishStatus);

    @NativeUpdate
    void updateToWriteStatus(@QueryParam("toWriteStatus") Integer toWriteStatus);

    @NativeQuery
    List<NikeStockReceive> findToWriteFile(@QueryParam("toWriteStatus") Integer toWriteStatus, BeanPropertyRowMapperExt<NikeStockReceive> beanPropertyRowMapperExt);

    @NativeQuery
    List<NikeStockReceive> findToWriteFileBrand(@QueryParam("toWriteStatus") Integer toWriteStatus, BeanPropertyRowMapperExt<NikeStockReceive> beanPropertyRowMapperExt);


    @NativeQuery
    List<NikeStockReceiveDto> findToWriteFileByStaId(@QueryParam("toWriteStatus") Integer toWriteStatus, BeanPropertyRowMapperExt<NikeStockReceiveDto> beanPropertyRowMapperExt);



    @NativeUpdate
    void updateToFinishStatus(@QueryParam("toWriteStatus") Integer toWriteStatus, @QueryParam("finishStatus") Integer finishStatus);

    @NativeUpdate
    void updateToFinishStatusBrand(@QueryParam("toWriteStatus") Integer toWriteStatus, @QueryParam("finishStatus") Integer finishStatus);

    @NativeUpdate
    void updateNikeInBoundRevToWriteStatus(@QueryParam("type") Integer type, @QueryParam("toWriteStatus") Integer toWriteStatus);


    @NativeUpdate
    void updateNikeInBoundRevToWriteStatusBrand(@QueryParam("type") Integer type, @QueryParam("toWriteStatus") Integer toWriteStatus);



    @NativeUpdate
    void updateNikeTransferRevToWriteStatus(@QueryParam("type") Integer type, @QueryParam("toWriteStatus") Integer toWriteStatus);

    @NativeUpdate
    void updateFileName(@QueryParam("toWriteStatus") Integer toWriteStatus, @QueryParam("fileName") String fileName);

    @NativeUpdate
    void updateFileNameBrand(@QueryParam("toWriteStatus") Integer toWriteStatus, @QueryParam("fileName") String fileName);
}
