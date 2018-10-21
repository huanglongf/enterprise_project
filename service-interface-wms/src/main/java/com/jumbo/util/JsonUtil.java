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
package com.jumbo.util;

import java.io.Serializable;
import java.util.Collection;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Marshaller;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONObject;

/**
 * 
 * @author wanghua
 * 
 */
public abstract class JsonUtil implements Serializable{
    private static final long serialVersionUID = -7783951896294929264L;

    /**
     * object -----> json
     * 
     * @see loxia.support.json.JSONObject#JSONObject(Object)
     * @see #obj2jsonIncludeSimple(Object)
     * @param obj
     * @return
     */
    public static String obj2jsonStr(Object obj) {
        return new JSONObject(obj).toString();
    }

    public static JSONObject obj2json(Object obj) {
        return new JSONObject(obj);
    }

    /**
     * object -----> json
     * 
     * @see loxia.support.json.JSONObject#JSONObject(Object, String)
     * @see loxia.support.json.JSONPropFilter#JSONPropFilter(String, java.util.Set)
     * @param obj
     * @param propFilterStr
     * @return
     */
    public static String obj2jsonStr(Object obj, String propFilterStr) {
        return new JSONObject(obj, propFilterStr).toString();
    }

    public static JSONObject obj2json(Object obj, String propFilterStr) {
        return new JSONObject(obj, propFilterStr);
    }

    /**
     * @see #obj2json(Object, String)
     * @param obj
     * @return
     */
    public static String obj2jsonStrIncludeAll(Object obj) {
        return obj2jsonStr(obj, "**,-class");
    }

    public static JSONObject obj2jsonIncludeAll(Object obj) {
        return obj2json(obj, "**,-class");
    }

    /**
     * String.class Boolean.class BigDecimal.class BigInteger.class Byte.class Double.class
     * Float.class Integer.class Long.class Short.class JSONString.class Date.class
     * 
     * @see #obj2json(Object, String)
     * @param obj
     * @return
     */
    public static String obj2jsonStrIncludeSimple(Object obj) {
        return obj2jsonStr(obj);
    }

    public static JSONObject obj2jsonIncludeSimple(Object obj) {
        return obj2json(obj);
    }

    /**
     * @see #obj2json(Object, String)
     * @param obj
     * @return
     */
    public static String obj2jsonStrExcludeAll(Object obj) {
        return obj2jsonStr(obj, "-*");
    }

    public static JSONObject obj2jsonExcludeAll(Object obj) {
        return obj2json(obj, "-*");
    }

    /**
     * 
     * @param col
     * @return
     */
    public static String collection2jsonStr(Collection<?> col) {
        return new JSONArray().put(col).toString();
    }

    public static JSONArray collection2json(Collection<?> col) {
        return new JSONArray().put(col);
    }

    public static String collection2jsonStr(Collection<?> col, String propFilterStr) {
        return new JSONArray().put(col, propFilterStr).toString();
    }

    public static JSONArray collection2json(Collection<?> col, String propFilterStr) {
        return new JSONArray().put(col, propFilterStr);
    }

}
