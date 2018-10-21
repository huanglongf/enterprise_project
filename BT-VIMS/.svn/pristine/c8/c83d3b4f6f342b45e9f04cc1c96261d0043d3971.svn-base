package com.bt.vims.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.bt.vims.model.User;

public class Result<T>{
	private Integer code;//-1:失败，0:成功 ，其他数据
	private String msg;//提示信息
	private T data;//具体的内容
	
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setId("112121321");
		List<User> al = new ArrayList<User>();
		al.add(user);
	    Result<List> result = new Result<List>();
	    result.setCode(-1);
	    result.setMsg("失败");
	    result.setData(al);
	    System.out.println();
	}
	
}
