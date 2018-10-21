package loxia.web.struts2;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.opensymphony.xwork2.conversion.TypeConversionException;

public class BigDecimalTypeConvertor extends StrutsTypeConverter {
    protected static final Logger log = LoggerFactory.getLogger(BigDecimalTypeConvertor.class);
    @SuppressWarnings("rawtypes")
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values == null || values.length == 0) return null;
        if (!StringUtils.hasText(values[0])) return null;

        try {
            return new BigDecimal(values[0]);
        } catch (Exception e) {
            log.error("",e);
            throw new TypeConversionException(values[0] + " is not one valid BigDecimal.");
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String convertToString(Map context, Object o) {
        if (!(o instanceof BigDecimal)) throw new TypeConversionException();
        return o.toString();
    }

}
