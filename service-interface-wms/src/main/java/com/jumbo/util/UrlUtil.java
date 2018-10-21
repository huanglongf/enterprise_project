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
package com.jumbo.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author lichuan
 * 
 */
public class UrlUtil implements Serializable {


    private static final long serialVersionUID = -4224216447382559952L;
    private static String defaultCharset = "UTF-8";

    public static String encode(String uriComponent) throws UnsupportedEncodingException {
        if (null != uriComponent && "" != uriComponent) {
            return URLEncoder.encode(uriComponent, defaultCharset);
        }
        return "";
    }

    public static String encode(String uriComponent, String charset) throws UnsupportedEncodingException {
        if (null == charset) {
            charset = defaultCharset;
        }
        if (null != uriComponent && "" != uriComponent) {
            return URLEncoder.encode(uriComponent, charset);
        }
        return "";
    }

    public static String decode(String uriComponent) throws UnsupportedEncodingException {
        if (null != uriComponent && "" != uriComponent) {
            return URLDecoder.decode(uriComponent, defaultCharset);
        }
        return "";
    }

    public static String decode(String uriComponent, String charset) throws UnsupportedEncodingException {
        if (null == charset) {
            charset = defaultCharset;
        }
        if (null != uriComponent && "" != uriComponent) {
            return URLDecoder.decode(uriComponent, charset);
        }
        return "";
    }

}
