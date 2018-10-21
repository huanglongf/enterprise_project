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

package com.jumbo.wms.model.warehouse;



/**
 * 物流省份编码
 * 
 * @author xiaolong.fei
 * 
 */
public class WhTransAreaNoCommand extends WhTransAreaNo {

    /**
     * 
     */
    private static final long serialVersionUID = -819201695469408515L;

    /**
     * 创建用户名
     */
    private String crateUserName;
    
    /**
     * 更新方式： 0：部分更新；1：全部更新。
     */
    private Long updateModel;

    public String getCrateUserName() {
        return crateUserName;
    }

    public void setCrateUserName(String crateUserName) {
        this.crateUserName = crateUserName;
    }

    public Long getUpdateModel() {
        return updateModel;
    }

    public void setUpdateModel(Long updateModel) {
        this.updateModel = updateModel;
    }
    
}
