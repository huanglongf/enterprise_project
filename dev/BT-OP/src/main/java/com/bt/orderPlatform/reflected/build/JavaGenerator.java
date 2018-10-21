package com.bt.orderPlatform.reflected.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bt.orderPlatform.reflected.utils.CreateJava;

/**
 * ******************************
 * java生成器，  
 * 这个代码不需要提交
 * *******************************
 * 
 */
public class JavaGenerator {
	
	public static void main(String[] args) {
		/**
		 * 
		 * @param tableName 	表名
		 * @param codeName 		表名对应的中文注释
		 * @param modulePakPath	模块包路径：com\\bt\\lmis
		 * @param modulePackage	模块包：com.bt.lmis
		 * @param dbName		数据库名称
		 * @param prefix 		数据库表前缀
		 */
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 =  new HashMap<String, Object>();
		map1.put("tableName", "sys_function_button");
		map1.put("packageName", "");
		map1.put("codeName", "功能菜单表");
		list.add(map1);
		String dbName = "blop_empolder";
		String prefix = "sys_";
		//windows
		//String modulePakPath = "com\\bt\\lmis";
		//Mac
		String modulePakPath = "com/bt/login";
		String modulePackage = "com.bt.login";
		for(int i=0; i<list.size(); i++){
			Map<String, Object> m = list.get(i);
			CreateJava.create((String)m.get("tableName"), (String)m.get("codeName"), modulePakPath + (String)m.get("packageName") , modulePackage + (String)m.get("packageName"), dbName, prefix);
		}
	}
}
