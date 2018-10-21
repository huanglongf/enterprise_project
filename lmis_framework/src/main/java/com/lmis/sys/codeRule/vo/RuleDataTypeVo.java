package com.lmis.sys.codeRule.vo;

import java.io.Serializable;

/**
 * 用于给前端返回规则类型的实体
 * @author xsh11040
 *
 */
public class RuleDataTypeVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7213864592014207288L;
    /**
     * 规则编号
     */
    private String code;
    /**
     * 规则名称
     */
    private String name;
    /**
     * 规则类型
     */
    private String type;
    
    public String getCode(){
        return code;
    }
    
    public void setCode(String code){
        this.code = code;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getType(){
        return type;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    
    
}
