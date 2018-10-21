package com.lmis.framework.i18;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * @author daikaihua
 * @date 2017年11月17日
 * @todo 国际化实现
 */
public class LmisLocaleResolver extends AcceptHeaderLocaleResolver {
	private Locale myLocal;
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return myLocal==null?request.getLocale():myLocal;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        myLocal = locale;
    }
}
