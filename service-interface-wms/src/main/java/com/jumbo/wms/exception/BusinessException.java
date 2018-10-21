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

package com.jumbo.wms.exception;

import java.io.Serializable;

public class BusinessException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 5847868283758650823L;

    private int errorCode;
    private Object[] args;
    private BusinessException linkedException;



    public BusinessException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BusinessException(int errorCode, Object[] args) {
        super();
        this.errorCode = errorCode;
        this.args = args;
    }

    public BusinessException() {
        super();
        errorCode = ErrorCode.ERROR_NOT_SPECIFIED;
    }

    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message) {
        super(message);
        errorCode = ErrorCode.ERROR_NOT_SPECIFIED;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        errorCode = ErrorCode.ERROR_NOT_SPECIFIED;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public BusinessException getLinkedException() {
        return linkedException;
    }

    public void setLinkedException(BusinessException linkedException) {
        this.linkedException = linkedException;
    }
}
