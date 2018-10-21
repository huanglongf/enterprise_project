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

package com.jumbo.wms.model.command;

import com.jumbo.wms.model.authorization.OperationUnit;

public class OperationUnitCommand extends OperationUnit {

    /**
	 * 
	 */
    private static final long serialVersionUID = 884857277634462689L;

//    private Long id;

    private Integer isRef;

    private Integer isDefault;

    private Integer isBZInv;

    /**
     * Edw 字段
     * @return
     */
    private String ouTypeid;
    private String parentUnitid;
    private String ouComment;
//    private String code;
//
//    private String name;

    public Integer getIsBZInv() {
        return isBZInv;
    }


    public void setIsBZInv(Integer isBZInv) {
        this.isBZInv = isBZInv;
    }


    public Integer getIsRef() {
        return isRef;
    }


    public void setIsRef(Integer isRef) {
        this.isRef = isRef;
    }


    public Integer getIsDefault() {
        return isDefault;
    }


    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }


	public String getOuTypeid() {
		return ouTypeid;
	}


	public void setOuTypeid(String ouTypeid) {
		this.ouTypeid = ouTypeid;
	}


	public String getParentUnitid() {
		return parentUnitid;
	}


	public void setParentUnitid(String parentUnitid) {
		this.parentUnitid = parentUnitid;
	}


	public String getOuComment() {
		return ouComment;
	}


	public void setOuComment(String ouComment) {
		this.ouComment = ouComment;
	}


    // public Long getId() {
    // return id;
    // }
    //
    //
    // public void setId(Long id) {
    // this.id = id;
    // }
    //
    //
    // public String getCode() {
    // return code;
    // }
    //
    //
    // public void setCode(String code) {
    // this.code = code;
    // }
    //
    //
    // public String getName() {
    // return name;
    // }
    //
    //
    // public void setName(String name) {
    // this.name = name;
    // }


}
