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

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.SimpleTypeConverter;

import com.jumbo.wms.model.BaseModel;

/**
 * 主要是解决sql查询,把外键转换成@Id被setValue的对象
 * 
 * @author wanghua
 * 
 */
public class BaseModelPropertyEditor<T extends BaseModel> extends PropertyEditorSupport {
    protected static final Logger log = LoggerFactory.getLogger(BaseModelPropertyEditor.class);
    private Class<T> delegate;

    public void setAsText(String text) {
        setValue(text);
    }

    public void setValue(Object value) {
        if (value != null) {
            if (log.isDebugEnabled()) {
                log.debug("The original {}.value is:{}", value.getClass().getName(), value.toString());
            }
            if (delegate == null) {
                throw new IllegalArgumentException("BaseModelPropertyEditor must not be null");
            }
            Object obj = BeanUtils.instantiate(delegate);
            if (obj != null) {
                PropertyDescriptor pd = null;
                try {
                    pd = getTargetPd(obj);
                    if (pd != null && pd.getWriteMethod() != null) {
                        @SuppressWarnings("rawtypes")
                        Class paramType = pd.getWriteMethod().getParameterTypes()[0];
                        if (String.class.equals(paramType)) {
                            pd.getWriteMethod().invoke(obj, value.toString());
                        } else {
                            PropertyEditor pe = new SimpleTypeConverter().getDefaultEditor(paramType);
                            if (pe == null) {
                                throw new IllegalArgumentException(delegate.getName() + "." + pd.getName() + "@Id" + " is not common, you must register yours' CustomPropertyEditor");
                            }
                            pe.setValue(value);
                            pd.getWriteMethod().invoke(obj, pe.getValue());
                        }
                    }
                } catch (Exception e) {
                    log.debug("Try to invoke {}.setValue for {} failure", new Object[] {delegate.getName(), pd == null ? null : pd.getName()});
                }
            }
            super.setValue(obj);
            if (log.isDebugEnabled()) {
                log.debug("The new value is:{}", super.getValue() == null ? null : super.getValue().toString());
            }
        }
    }

    public void setDelegate(Class<T> delegate) {
        this.delegate = delegate;
    }

    private PropertyDescriptor getTargetPd(Object obj) {
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            if (pd.getReadMethod().getAnnotation(Id.class) != null) {
                return pd;
            }
        }
        return null;
    }
}
