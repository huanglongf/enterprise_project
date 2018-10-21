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

package loxia.dao.support;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.util.ClassUtils;

/**
 * @author wanghua
 * 
 */
public class EnumPropertyEditor<E extends Enum<E>> extends PropertyEditorSupport {
    protected static final Logger log = LoggerFactory.getLogger(EnumPropertyEditor.class);
    private static final String DEFAULT_IDENTIFIER_METHOD_NAME = "getValue";
    private static final String DEFAULT_VALUE_OF_METHOD_NAME = "valueOf";
    private Class<E> delegate;

    public void setAsText(String text) {
        setValue(text);
    }

    public void setValue(Object value) {
        if (log.isDebugEnabled()) {
            log.debug("The original value is:{}", value == null ? null : value.toString());
        }
        if (delegate == null) {
            throw new IllegalArgumentException("EnumPropertyEditor with public static delegate.valueOf(SimpleType) must not be null");
        }
        try {
            @SuppressWarnings("rawtypes")
            Class returnType = delegate.getMethod(DEFAULT_IDENTIFIER_METHOD_NAME, new Class[0]).getReturnType();
            Method valueOfMethod = delegate.getMethod(DEFAULT_VALUE_OF_METHOD_NAME, returnType);
            if (ClassUtils.isAssignableValue(returnType, value)) {
                super.setValue(valueOfMethod.invoke(delegate, value));
            } else {
                PropertyEditor pe = new SimpleTypeConverter().getDefaultEditor(returnType);
                if (pe == null) {
                    throw new IllegalArgumentException("EnumPropertyEditor with public static " + delegate.getName() + " valueOf(param),param must be simpleType");
                }
                pe.setValue(value);
                super.setValue(valueOfMethod.invoke(delegate, pe.getValue()));
            }
        } catch (Exception e) {
            log.debug("Try to invoke {}.{} failure", new Object[] {delegate.getName(), DEFAULT_VALUE_OF_METHOD_NAME});
        }


        if (log.isDebugEnabled()) {
            log.debug("The new value is:{}", super.getValue() == null ? null : super.getValue().toString());
        }
    }

    public void setDelegate(Class<E> delegate) {
        this.delegate = delegate;
    }
}
