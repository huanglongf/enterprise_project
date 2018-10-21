package com.lmis.common.dynamicSql.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: DynamicSqlParam
 * @Description: TODO(动态SQL入参)
 * @author Ian.Huang
 * @date 2017年12月19日 下午7:55:54 
 * 
 * @param <T>
 */
public class DynamicSqlParam<T> extends PersistentObject {

	/** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1278810856787385131L;
	
	@ApiModelProperty(value = "布局ID")
	private String layoutId;
	
	@ApiModelProperty(value = "元素集合")
	private List<Element> elements;

	@ApiModelProperty(value = "排序字符串")
	private String orderByStr;
	
	@ApiModelProperty(value = "表对象实体")
	private T tableModel;
	
	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}
	
	public List<Element> getElements() {
		return elements;
	}

	/*public void setElements(List<Element> elements) {
		this.elements = elements;
	}*/
	
	public void setElements(String elementsStr) throws Exception {
		List<Element> elements = null;
		JSONArray tmpElements = (JSONArray) JSONArray.parse(elementsStr);
		if(!ObjectUtils.isNull(tmpElements)) {
			elements = new ArrayList<Element>();
			for(int i = 0; i < tmpElements.size(); i++) {
				JSONObject tmpElement = (JSONObject)tmpElements.get(i);
				
				// 元素ID必须存在
				if(ObjectUtils.isNull(tmpElement.get("elementId"))) throw new Exception("元素ID不存在");
				Element element = new Element(tmpElement.get("elementId").toString());
				if(null != tmpElement.get("value")) element.setValue(tmpElement.get("value").toString());
				elements.add(element); 
			}
		}
		this.elements = elements;
	}

	public String getOrderByStr() {
		return orderByStr;
	}

	public void setOrderByStr(String orderByStr) {
		this.orderByStr = orderByStr;
	}
	
	public T getTableModel() {
		return tableModel;
	}
	
	public void setTableModel(T tableModel) {
		this.tableModel = tableModel;
	}
	
	/**
	 * @Title: getElementValue
	 * @Description: TODO(获取指定元素ID的值)
	 * @param elementId
	 * @throws Exception
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 下午1:53:00
	 */
	public String getElementValue(String elementId) throws Exception {
		if(ObjectUtils.isNull(elementId)) {
			throw new Exception("元素ID为空，无法取值");
		}
		for(int i = 0; i < this.elements.size(); i++) {
			if(elementId.equals(this.elements.get(i).getElementId())) {
				return this.elements.get(i).getValue();
			}
		}
		return null;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
