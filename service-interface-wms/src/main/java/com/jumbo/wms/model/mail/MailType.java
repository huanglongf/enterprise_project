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

package com.jumbo.wms.model.mail;

/**
 * 邮件类型
 */
public enum MailType {
    PLAIN(1), // 纯文本
    HTML(2), // HTML格式
    STRING(3), // 字符串
    TEMPLATE(4); //模板
    private int value;

    private MailType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MailType valueOf(int value) {
        switch (value) {
            case 1:
                return PLAIN;
            case 2:
                return HTML;
            case 3:
            	return STRING;
            case 4:
            	return TEMPLATE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
