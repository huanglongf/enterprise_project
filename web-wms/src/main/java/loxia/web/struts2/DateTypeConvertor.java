package loxia.web.struts2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import loxia.support.LoxiaSupportConstants;
import loxia.support.LoxiaSupportSettings;

import com.opensymphony.xwork2.conversion.TypeConversionException;

public class DateTypeConvertor extends StrutsTypeConverter {
    protected static final Logger log = LoggerFactory.getLogger(StrutsTypeConverter.class);
    @SuppressWarnings("rawtypes")
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values == null || values.length == 0) return null;
        if (!StringUtils.hasText(values[0])) return null;
        String dateFormat = LoxiaSupportSettings.getInstance().get(LoxiaSupportConstants.DATE_PATTERN);
        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            return df.parse(values[0]);
        } catch (ParseException e) {
            log.error("",e);
            throw new TypeConversionException(values[0] + " is not one valid date.");
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String convertToString(Map context, Object o) {
        if (!(o instanceof Date)) throw new TypeConversionException();
        String dateFormat = LoxiaSupportSettings.getInstance().get(LoxiaSupportConstants.DATE_PATTERN);
        DateFormat df = new SimpleDateFormat(dateFormat);
        return df.format((Date) o);
    }

}
