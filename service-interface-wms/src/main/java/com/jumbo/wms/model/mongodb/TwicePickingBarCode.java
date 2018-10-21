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
package com.jumbo.wms.model.mongodb;

import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * @author lihui
 * 
 */
public class TwicePickingBarCode extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2807160415014060058L;

    private Long id;

    /**
     * 配货批ID
     */
    private Long pickingId;

    private String pickingListCode;

    private List<StaCheckRecord> staCheckRecordList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPickingId() {
        return pickingId;
    }

    public void setPickingId(Long pickingId) {
        this.pickingId = pickingId;
    }

    public String getPickingListCode() {
        return pickingListCode;
    }

    public void setPickingListCode(String pickingListCode) {
        this.pickingListCode = pickingListCode;
    }

    public List<StaCheckRecord> getStaCheckRecordList() {
        return staCheckRecordList;
    }

    public void setStaCheckRecordList(List<StaCheckRecord> staCheckRecordList) {
        this.staCheckRecordList = staCheckRecordList;
    }



}
