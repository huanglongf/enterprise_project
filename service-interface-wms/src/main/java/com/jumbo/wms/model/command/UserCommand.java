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

import com.jumbo.wms.model.authorization.User;


public class UserCommand extends User {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3660431154588810169L;

    /**
     * 再次输入密码
     */
    private String newPassword;

    private String confirmPassword;

    private String isAne;// 用户帐号是否未过期，过期帐号无法登录系统

    private String isAnl;// 用户帐号是否未被锁定，被锁定的用户无法使用系统

    private String isEn;// 用户帐号是否可用

    private String isSys;// 是否系统用户，系统用户只能初始化，不能通过界面功能维护和删除

    private String warehouseName; // 用户绑定的仓库

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getIsAne() {
        return isAne;
    }

    public void setIsAne(String isAne) {
        this.isAne = isAne;
    }

    public String getIsAnl() {
        return isAnl;
    }

    public void setIsAnl(String isAnl) {
        this.isAnl = isAnl;
    }

    public String getIsEn() {
        return isEn;
    }

    public void setIsEn(String isEn) {
        this.isEn = isEn;
    }

    public String getIsSys() {
        return isSys;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

}
