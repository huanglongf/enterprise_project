package com.lmis.common.getData.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author daikaihua
 * @date 2017年11月28日
 * @todo TODO
 */
public class ConstantData implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = -2861269747763676633L;
	
	@ApiModelProperty(value = "常量编号")
	private String code;

	@ApiModelProperty(value = "常量名称")
	private String name;

	@ApiModelProperty(value = "其他值1")
	private String value1;

	@ApiModelProperty(value = "其他值2")
	private String value2;

	@ApiModelProperty(value = "其他值3")
	private String value3;

	@ApiModelProperty(value = "其他值4")
	private String value4;

	@ApiModelProperty(value = "其他值5")
	private String value5;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public String getValue5() {
		return value5;
	}

	public void setValue5(String value5) {
		this.value5 = value5;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String toString() {
		return "{code:" + code
		+ ",name:" + name
		+ ",value1:" + value1
		+ ",value2:" + value2
		+ ",value3:" + value3
		+ ",value4:" + value4
		+ ",value5:" + value5
		+"}";
	}

}
