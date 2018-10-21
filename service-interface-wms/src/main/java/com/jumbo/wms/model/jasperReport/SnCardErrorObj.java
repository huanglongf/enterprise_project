package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.util.List;

/**
 * 激活失败卡信息bin.hu
 * 
 * @author jumbo
 * 
 */
public class SnCardErrorObj implements Serializable {

    private static final long serialVersionUID = 8597132666112785096L;

    private String code;

    private String code1;

    private String code2;

    private String slipCode;

    private String plCode;

    private String printTime;

    private String ouName;

    private List<SnCardErrorLinesObj> lines;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public List<SnCardErrorLinesObj> getLines() {
        return lines;
    }

    public void setLines(List<SnCardErrorLinesObj> lines) {
        this.lines = lines;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }



}
