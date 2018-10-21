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

package com.jumbo.wms.model.odo;

/**
 * 库间移动
 * 
 * @author hui.li
 * 
 */
public class OdoCommand extends Odo {

    /**
     * 
     */
    private static final long serialVersionUID = 7020259554804867013L;


    private String ouName;

    private String ownerName;

    private String statusName;

    private String invName;

    private String outOuName;

    private String inOuName;

    private String diffOuName;

    private String userName;


    public String getOwnerName() {
        return ownerName;
    }


    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public String getStatusName() {
        return statusName;
    }


    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    public String getInvName() {
        return invName;
    }


    public void setInvName(String invName) {
        this.invName = invName;
    }


    public String getOutOuName() {
        return outOuName;
    }


    public void setOutOuName(String outOuName) {
        this.outOuName = outOuName;
    }


    public String getInOuName() {
        return inOuName;
    }


    public void setInOuName(String inOuName) {
        this.inOuName = inOuName;
    }


    public String getDiffOuName() {
        return diffOuName;
    }


    public void setDiffOuName(String diffOuName) {
        this.diffOuName = diffOuName;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getOuName() {
        return ouName;
    }


    public void setOuName(String ouName) {
        this.ouName = ouName;
    }


}
