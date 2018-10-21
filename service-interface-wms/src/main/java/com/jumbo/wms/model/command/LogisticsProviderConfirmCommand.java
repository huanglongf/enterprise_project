package com.jumbo.wms.model.command;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import loxia.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.util.StringUtils;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.command.vmi.esprit.xml.receiving.EspRecv;

/**
 * @author jianghailiang
 */
public class LogisticsProviderConfirmCommand extends BaseModel {
    protected static final Logger log = LoggerFactory.getLogger(EspRecv.class);

    private static final long serialVersionUID = -2149836512636986461L;

    /**
     * 订单编码
     */
    private String soCode;

    /**
     * 订单创建开始时间
     */
    private String soCreateDateBegin;

    /**
     * 订单创建结束时间
     */
    private String soCreateDateEnd;

    /**
     * 物流商编码
     */
    private String lpCode;

    /**
     * 内部平台物流商对接编码
     */
    private String transExpCode;

    public Map<String, Object> getLogisticsMap() {
        Map<String, Object> result = new HashMap<String, Object>();

        if (StringUtils.hasText(soCode)) {
            result.put("soCode", soCode);
        }

        if (StringUtils.hasText(soCreateDateBegin)) {
            try {
                result.put("soCreateDateBegin", DateUtil.parse(this.soCreateDateBegin, "yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                // e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("getLogisticsMap ParseException！", e);
                }
            }
        }

        if (StringUtils.hasText(soCreateDateEnd)) {
            try {
                result.put("soCreateDateEnd", DateUtil.parse(this.soCreateDateEnd, "yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                // e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("getLogisticsMap ParseException！", e);
                }
            }
        }

        if (StringUtils.hasText(lpCode)) {
            result.put("lpCode", lpCode);
        }

        if (StringUtils.hasText(transExpCode)) {
            result.put("transExpCode", transExpCode);
        }

        return result;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getSoCreateDateBegin() {
        return soCreateDateBegin;
    }

    public void setSoCreateDateBegin(String soCreateDateBegin) {
        this.soCreateDateBegin = soCreateDateBegin;
    }

    public String getSoCreateDateEnd() {
        return soCreateDateEnd;
    }

    public void setSoCreateDateEnd(String soCreateDateEnd) {
        this.soCreateDateEnd = soCreateDateEnd;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getTransExpCode() {
        return transExpCode;
    }

    public void setTransExpCode(String transExpCode) {
        this.transExpCode = transExpCode;
    }

}
