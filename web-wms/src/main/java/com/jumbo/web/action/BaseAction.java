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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.sf.jasperreports.engine.JasperPrint;
import org.apache.struts2.interceptor.RequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

public class BaseAction extends ActionSupport implements RequestAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6703783447190273443L;

    protected static final Logger log = LoggerFactory.getLogger(BaseAction.class);

    public static final String JSON = "json";
    public static final String PRINT = "print";
    public static final String REQUEST = "request";
    protected Map<String, Object> request;

    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setRequest(Map<String, Object> request) {
        this.request = request;
    }

    protected String printObject(Object obj) throws Exception {
        List<JasperPrint> result = new ArrayList<JasperPrint>();
        if (obj instanceof JasperPrint) {
            result.add((JasperPrint) obj);
        } else if (obj instanceof Collection) {
            @SuppressWarnings("rawtypes")
            Collection c = (Collection) obj;
            for (Object o : c) {
                if (!(o instanceof JasperPrint)) throw new IllegalArgumentException();
                JasperPrint jp = (JasperPrint) o;
                result.add(jp);
            }
        } else
            throw new IllegalArgumentException();
        this.inputStream = new ByteArrayInputStream(printObjectToZip(result));
        return PRINT;
    }

    protected String printObject(List<Object> objs) throws Exception {
        List<JasperPrint> result = new ArrayList<JasperPrint>();
        for (Object obj : objs) {
            if (obj instanceof JasperPrint) {
                result.add((JasperPrint) obj);
            } else if (obj instanceof Collection) {
                @SuppressWarnings("rawtypes")
                Collection c = (Collection) obj;
                for (Object o : c) {
                    if (!(o instanceof JasperPrint)) throw new IllegalArgumentException();
                    JasperPrint jp = (JasperPrint) o;
                    result.add(jp);
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
        this.inputStream = new ByteArrayInputStream(printObjectToZip(result));
        return PRINT;
    }



    private byte[] printObjectToZip(List<JasperPrint> result) throws IOException {
        log.debug("printObject to Stream===========================================");
        ByteArrayOutputStream zipos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(zipos);
        zos.putNextEntry(new ZipEntry("print.json"));
        ByteArrayOutputStream objos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(objos);
        oos.writeObject(result);
        zos.write(objos.toByteArray());
        zos.closeEntry();
        zos.close();
        log.debug("printObject write object===========================================");
        log.debug("printObject to input stream,os size {}===========================================", zipos.toByteArray().length);
        return zipos.toByteArray();
    }

    protected String getMessage(String key, Object... args) {
        Locale locale = ActionContext.getContext().getLocale();
        return LocalizedTextUtil.findText(this.getClass(), key, locale, key, args);
    }

    public void addActionError(String errKey, Object... args) {
        addActionError(getMessage(errKey, args));
    }

    public void addFieldError(String fieldName, String errKey, Object... args) {
        addFieldError(fieldName, getMessage(errKey, args));
    }

    public void addActionMessage(String msgKey, Object... args) {
        addActionMessage(getMessage(msgKey, args));
    }

    public String CharacterReplace(String s) {
        if (s == null || "".equals(s)) {
            return s;
        }
        s = s.replace("<", "&lt");
        s = s.replace(">", "&gt");
        return s;
    }
}
