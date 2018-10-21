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
package com.jumbo.wms.manager.warehouse.card;

import java.io.Serializable;

/**
 * @author lichuan
 * 
 */
public class CardResult implements Serializable {

    private static final long serialVersionUID = -3766319982174011852L;
    /**
     * 成功
     */
    public static final int STATUS_SUCCESS = 1;

    /**
     * 系统异常
     */
    public static final int STATUS_ERROR = -1;

    /**
     * 失败
     */
    public static final int STATUS_FAIL = 0;

    /**
     * 状态 ：1成功 0失败-1系统异常
     */
    private int status;
    /**
     * 原因
     */
    private String msg;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 错误编码
     */
    private String errorCode;

    public final int getStatus() {
        return status;
    }

    public final void setStatus(int status) {
        this.status = status;
    }

    public final String getMsg() {
        return msg;
    }

    public final void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    

}
