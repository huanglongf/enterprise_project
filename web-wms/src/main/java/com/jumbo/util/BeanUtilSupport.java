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

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Map;

import loxia.utils.DateUtil;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.jumbo.Constants;
import com.jumbo.wms.model.BaseModel;

/**
 * 
 * @author wanghua
 * 
 */
public abstract class BeanUtilSupport {
    protected static final Logger log = LoggerFactory.getLogger(BeanUtilSupport.class);

    /**
     * 是否是BaseModel的子类
     * 
     * @param c
     * @return
     */
    public static boolean isBaseModel(Class<?> c) {
        return BaseModel.class.isAssignableFrom(c);
    }

    /**
     * 把bean按一级结构放入map e.g:bean=User,key=""结果id,loginName...,ou.id,ou.code,ou.name.....
     * e.g:bean=User,key="user"结果user.id,user.loginName...,user.ou.id,user.ou.code,user.ou.name.....
     * 
     * @param map
     * @param key
     * @param bean
     * @throws Exception
     */
    public static void describe(Map<String, Object> map, String key, Object bean, String[] columns) throws Exception {
        if (columns == null || columns.length == 0) {
            describe(map, key, bean);
        } else {
            if (bean != null) {
                BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
                BeanWrapper bw = new BeanWrapperImpl(bean);
                for (String col : columns) {
                    if (bw.getPropertyType(col) == null) {
                        log.debug("{} does not has property:{}", bean.getClass().getName(), col);
                        continue;
                    }
                    if (col.indexOf(".") == -1) {
                        String code = (key == null ? "" : key + ".") + col;
                        if (Date.class.isAssignableFrom(bw.getPropertyType(col))) {
                            Date date = (Date) bw.getPropertyDescriptor(col).getReadMethod().invoke(bean);
                            map.put(code, date == null ? "" : DateUtil.format(date, Constants.DF_YMDHMS));
                        } else {
                            map.put(code, beanUtilsBean.getProperty(bean, col));
                        }
                    } else {
                        String superName = col.substring(0, col.indexOf(".")), childName = col.substring(col.indexOf(".") + 1);
                        describe(map, superName, bw.getPropertyDescriptor(superName).getReadMethod().invoke(bean), new String[] {childName});
                    }
                }
            }
        }
    }

    public static void describe(Map<String, Object> map, String key, Object bean) throws Exception {
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        if (bean != null) {
            PropertyDescriptor descriptors[] = beanUtilsBean.getPropertyUtils().getPropertyDescriptors(bean);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName(), code = (key == null ? "" : key + ".") + name;
                if (descriptors[i].getReadMethod() != null) {
                    if (isBaseModel(descriptors[i].getPropertyType())) {
                        // describe(map, code, descriptors[i].getReadMethod().invoke(bean));
                        log.debug("No describe {}.{}(class={})", new Object[] {bean.getClass().getSimpleName(), name, descriptors[i].getPropertyType().getSimpleName()});
                    } else {
                        if (Date.class.isAssignableFrom(descriptors[i].getPropertyType())) {
                            Date date = (Date) descriptors[i].getReadMethod().invoke(bean);
                            map.put(code, date == null ? "" : DateUtil.format(date, Constants.DF_YMDHMS));
                        } else {
                            map.put(code, beanUtilsBean.getProperty(bean, name));
                        }
                    }
                }
            }
        }
    }
}
