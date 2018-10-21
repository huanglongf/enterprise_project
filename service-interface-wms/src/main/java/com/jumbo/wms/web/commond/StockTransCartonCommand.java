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

package com.jumbo.wms.web.commond;

import com.jumbo.wms.model.BaseModel;

/**
 * 纸箱数量导入command
 *
 * @author weiwei.wu@baozun.com
 * @version 2018年8月20日 下午7:22:43
 */
public class StockTransCartonCommand extends BaseModel {

    private static final long serialVersionUID = 5274475642869758025L;


    /**
     * 作业单号
     */
    private String code;

    /**
     * 纸箱数量
     */
    private Integer cartonNum;

    
    public String getCode(){
        return code;
    }

    
    public void setCode(String code){
        this.code = code;
    }

    
    public Integer getCartonNum(){
        return cartonNum;
    }

    
    public void setCartonNum(Integer cartonNum){
        this.cartonNum = cartonNum;
    }
    
    

    
}
