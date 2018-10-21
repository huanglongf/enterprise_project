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
 * 
 */

package com.jumbo.wms.model.warehouse.test;

import java.util.List;

import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

/**
 * 压力测试中间表 
 * @author LSH5645
 *
 */
public class PressureTestDto {
    
    private Long staId;//作业单id
    
    private String staCode;//作业单code
    
    private Long pId;//批次ID
    
    private String pCode;//批次号
    
    private String  type;//1：拣货 2：播种 3：复核
    
    public PickingListCommand getPick() {
        return pick;
    }


    public void setPick(PickingListCommand pick) {
        this.pick = pick;
    }


    private StockTransApplicationCommand sta;//作业单实体
    
    private List<StaLineCommand> staLines;//作业单实体明细
    
    private PickingListCommand pick;//批次实体


    public Long getStaId() {
        return staId;
    }


    public void setStaId(Long staId) {
        this.staId = staId;
    }


    public String getStaCode() {
        return staCode;
    }


    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }


    public Long getpId() {
        return pId;
    }


    public void setpId(Long pId) {
        this.pId = pId;
    }


    public String getpCode() {
        return pCode;
    }


    public void setpCode(String pCode) {
        this.pCode = pCode;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public List<StaLineCommand> getStaLines() {
        return staLines;
    }


    public void setStaLines(List<StaLineCommand> staLines) {
        this.staLines = staLines;
    }

    public StockTransApplicationCommand getSta() {
        return sta;
    }


    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }


    
}
