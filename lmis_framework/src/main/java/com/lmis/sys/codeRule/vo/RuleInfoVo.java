package com.lmis.sys.codeRule.vo;

import java.io.Serializable;

public class RuleInfoVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 配置编码
     */
     String configCode;
    /**
     * 增长值（用于自增长）
     */
    int increValue;
  
    /**
     * 数据起始值（自增长数据，后台存一份到dataValue中）
     */
    String startValue;
    /**
     * 数据值长度
     */
     int  dataValuelg;
    /**
     * 数据类型
     */
    String dataType;
    /**
     * 几位（用于为随机数和自增长提供参考的位数）
     */
     int number;
    /**
     * 更新周期
     */
    String updateCycle;

    public String getUpdateCycle() {
        return updateCycle;
    }

    public void setUpdateCycle(String updateCycle) {
        this.updateCycle = updateCycle;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }


    public int getIncreValue() {
        return increValue;
    }

    public void setIncreValue(int increValue) {
        this.increValue = increValue;
    }


    public int getDataValuelg() {
        return dataValuelg;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataValuelg(int dataValuelg) {
        this.dataValuelg = dataValuelg;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }
}
