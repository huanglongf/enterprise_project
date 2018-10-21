/**
 * Copyright (c) 2015 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.model;
import java.io.Serializable;

public class BaseResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8984620536205048294L;
    /** 结果状态：1.成功 */
    public static String RESULT_SUCCESS = "1"; // 成功
    /** 结果状态：0.失败 */
    public static String RESULT_ERROR = "0"; // 失败
    /** 参数数据异常 */
    public static String ERROR_CODE_80001 = "80001"; // 参数数据异常
    /** 查无此渠道 */
    public static String ERROR_CODE_80002 = "80002"; // 查无此渠道
    /** 此渠道没有设置默认退回仓 */
    public static String ERROR_CODE_80003 = "80003"; // 此渠道没有设置默认退回仓
    /** 查询反馈数据异常 */
    public static String ERROR_CODE_80004 = "80004"; // 查询反馈数据异常
    /** 查无此仓库组织 */
    public static String ERROR_CODE_80005 = "80005"; // 查无此仓库组织
     
    /** 结果状态：1.成功；0.失败 */
    private String result = RESULT_SUCCESS;
    
    /** 错误码 */
    private String errorCode;
    
    /** 结果信息 */
    private String message;

    public BaseResponse() { }

    public BaseResponse(String result) {
        this.result = result;
    }

    public BaseResponse(String result, String errorCode) {
        this.result = result;
        this.errorCode = errorCode;
    }

    public BaseResponse(String result, String errorCode, String message) {
        this.result = result;
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
