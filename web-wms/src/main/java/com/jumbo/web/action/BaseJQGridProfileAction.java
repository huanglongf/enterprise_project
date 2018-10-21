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

package com.jumbo.web.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.util.StringUtils;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.web.security.UserDetails;

/**
 * 
 * @author wanghua
 * 
 * @param <T>
 */
public class BaseJQGridProfileAction extends BaseJQGridAction implements UserDetailsAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2897095350151928198L;

    protected UserDetails userDetails;

    public static final String RESULT = "result";
    public static final String MESSAGE = "msg";

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public OperationUnit getCurrentOu() {
        return userDetails == null ? null : userDetails.getCurrentOu();
    }

    /**
     * 通用的异步调用返回的成功的结果
     * 
     * @throws JSONException
     */
    public void asynReturnTrueValue() {
        try {
            request.put(JSON, new JSONObject().put("rs", Constants.RS_TRUE));
        } catch (JSONException e) {
            log.error("",e);
        }
    }

    /**
     * 通用的异步调用返回的失败的结果
     * 
     * @throws JSONException
     */
    public void asynReturnFalseValue() {
        try {
            request.put(JSON, new JSONObject().put("rs", Constants.RS_FALSE));
        } catch (JSONException e) {
            log.error("",e);
        }
    }

    /**
     * 日期增加1天
     * 
     * @param date
     * @return
     */
    protected Date addOneDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return calendar.getTime();
    }

    protected List<String> getExcelImportErrorMsg(ReadStatus rs) {
        List<String> errorMsg = new ArrayList<String>();
        if (rs.getStatus() > 0) {
            List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
            for (String s : list) {
                errorMsg.add(s);
            }
        } else if (rs.getStatus() < 0) {
            for (Exception ex : rs.getExceptions()) {
                if (ex instanceof BusinessException) {
                    BusinessException bex = (BusinessException) ex;
                    String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                    errorMsg.add(msg);
                }
            }
        }
        return errorMsg;
    }

    /**
     * 获取所有BusinessException异常信息
     * @param e 异常信息
     * @param isEndWithBr 是否已<br/>结尾
     * @return 异常文字
     */
    protected String getErrorMessage(Exception e, boolean isEndWithBr) {
        if (e instanceof BusinessException) {
            String msg = "";
            BusinessException current = (BusinessException) e;
            msg += addMsgEndWihtBr(getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()), isEndWithBr);
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                msg += addMsgEndWihtBr(getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()), isEndWithBr);
            }
            log.debug("error msg : {}", msg);
            return msg;
        } else {
            return addMsgEndWihtBr(e.getMessage(), isEndWithBr);
        }
    }

    protected String getErrorMessage(List<Exception> list, boolean isEndWithBr) {
        StringBuffer bf = new StringBuffer();
        for (Exception e : list) {
            if (e instanceof BusinessException) {
                BusinessException current = (BusinessException) e;
                bf.append(addMsgEndWihtBr(getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()), isEndWithBr));
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    bf.append(addMsgEndWihtBr(getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()), isEndWithBr));
                }
            } else {
                bf.append(addMsgEndWihtBr(e.getMessage(), isEndWithBr));
            }
        }
        log.debug("error msg : {}", bf.toString());
        return bf.toString();
    }

    protected String getErrorMessageByString(List<String> list, boolean isEndWithBr) {
        StringBuffer bf = new StringBuffer();
        for (String s : list) {
            bf.append(addMsgEndWihtBr(s, isEndWithBr));
        }
        return bf.toString();
    }

    private String addMsgEndWihtBr(String str, boolean isEndWithBr) {
        if (StringUtils.hasText(str)) {
            str = str.trim();
            if (str.endsWith(Constants.HTML_LINE_BREAK)) {
                return str;
            } else {
                if (isEndWithBr) {
                    return str + Constants.HTML_LINE_BREAK;
                } else {
                    return str;
                }
            }
        }
        return "";
    }

}
