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

package com.jumbo.web;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

@SuppressWarnings("rawtypes")
public class WebAuthenticationDetailsSource implements AuthenticationDetailsSource {

    private Class<?> clazz = WebBzAuthenticationDetails.class;

    // ~ Methods
    // ========================================================================================================

    /**
     * @param context the <tt>HttpServletRequest</tt> object.
     */
    public Object buildDetails(Object context) {
        Assert.isInstanceOf(HttpServletRequest.class, context);
        try {
            Constructor<?> constructor = clazz.getConstructor(HttpServletRequest.class);

            return constructor.newInstance(context);
        } catch (NoSuchMethodException ex) {
            ReflectionUtils.handleReflectionException(ex);
        } catch (InvocationTargetException ex) {
            ReflectionUtils.handleReflectionException(ex);
        } catch (InstantiationException ex) {
            ReflectionUtils.handleReflectionException(ex);
        } catch (IllegalAccessException ex) {
            ReflectionUtils.handleReflectionException(ex);
        }

        return null;
    }

    public void setClazz(Class<?> clazz) {
        Assert.notNull(clazz, "Class required");
        this.clazz = clazz;
    }
}
