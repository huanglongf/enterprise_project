package com.bt.lmis.controller.lucene;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.crypto.Data;

public class JdbcUtils {
	
	// 数据库用户名
	private static final String USERNAME = "root";
	// 数据库密码
	private static final String PASSWORD = "root";
	// 驱动信息
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	// 数据库地址
	private static final String URL = "jdbc:mysql://10.88.98.130:3306/lmis_pe?useUnicode=true&characterEncoding=utf8";
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet resultSet;

	public JdbcUtils() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName(DRIVER);
			System.out.println("数据库连接成功！");

		} catch (Exception e) {

		}
	}

	/**
	 * 获得数据库的连接
	 * 
	 * @return
	 */
	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 增加、删除、改
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException {
		boolean flag = false;
		int result = -1;
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		return flag;
	}

	/**
	 * 查询单条记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();// 返回查询结果
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		return map;
	}

	/**
	 * 查询多条记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}

		return list;
	}

	/**
	 * 通过反射机制查询单条记录
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> T findSimpleRefResult(String sql, List<Object> params, Class<T> cls) throws Exception {
		T resultObject = null;
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			// 通过反射机制创建一个实例
			resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true); // 打开javabean的访问权限
				field.set(resultObject, cols_value);
			}
		}
		return resultObject;

	}

	/**
	 * 通过反射机制查询多条记录
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findMoreRefResult(String sql, List<Object> params, Class<T> cls) throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			// 通过反射机制创建一个实例
			T resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true); // 打开javabean的访问权限
				field.set(resultObject, cols_value);
			}
			list.add(resultObject);
		}
		return list;
	}

	/**
	 * 释放数据库连接
	 */
	public void releaseConn() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		

		/******************* 增 *********************/
		/*
		 * String sql =
		 * "insert into userinfo (username, pswd) values (?, ?), (?, ?), (?, ?)"
		 * ; List<Object> params = new ArrayList<Object>(); params.add("小明");
		 * params.add("123xiaoming"); params.add("张三"); params.add("zhangsan");
		 * params.add("李四"); params.add("lisi000"); try { boolean flag =
		 * jdbcUtils.updateByPreparedStatement(sql, params);
		 * System.out.println(flag); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		/******************* 删 *********************/
		// 删除名字为张三的记录
		/*
		 * String sql = "delete from userinfo where username = ?"; List<Object>
		 * params = new ArrayList<Object>(); params.add("小明"); boolean flag =
		 * jdbcUtils.updateByPreparedStatement(sql, params);
		 */

		/******************* 改 *********************/
		// 将名字为李四的密码改了
		/*
		 * String sql = "update userinfo set pswd = ? where username = ? ";
		 * List<Object> params = new ArrayList<Object>();
		 * params.add("lisi88888"); params.add("李四"); boolean flag =
		 * jdbcUtils.updateByPreparedStatement(sql, params);
		 * System.out.println(flag);
		 */

		/******************* 查 *********************/
		for (int l = 0; l < 100; l++) {
			JdbcUtils jdbcUtils = new JdbcUtils();
			jdbcUtils.getConnection();
			
			String sql2 = "select * from tb_warehouse_express_data_settlement limit "+(l*10000)+",10000";
			Date a = new Date();
			List<Map<String, Object>> list = jdbcUtils.findModeResult(sql2, null);
			Date b = new  Date();
			System.out.println("SQL:"+sql2);
			System.out.println("耗时:"+(b.getTime()-a.getTime())+"ms");
	//		Map<String, Object> map = list.get(0);
	//		Set<String> keys = map.keySet();
	//		Iterator<String> it = keys.iterator();
	//		while (it.hasNext()) {
	//			String key = it.next();
	//			System.out.println("String "+key+" = null!=map.get('"+key+"') && !map.get('"+key+"').equals('') ? map.get('"+key+"').toString():'';");
	//			
	//		}
			Date c = new Date();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String remark = null!=map.get("remark") && !map.get("remark").equals("") ? map.get("remark").toString():"";
				String street = null!=map.get("street") && !map.get("street").equals("") ? map.get("street").toString():"";
				String epistatic_order = null!=map.get("epistatic_order") && !map.get("epistatic_order").equals("") ? map.get("epistatic_order").toString():"";
				String transport_name = null!=map.get("transport_name") && !map.get("transport_name").equals("") ? map.get("transport_name").toString():"";
				String warehouse = null!=map.get("warehouse") && !map.get("warehouse").equals("") ? map.get("warehouse").toString():"";
				String delivery_order = null!=map.get("delivery_order") && !map.get("delivery_order").equals("") ? map.get("delivery_order").toString():"";
				String city = null!=map.get("city") && !map.get("city").equals("") ? map.get("city").toString():"";
				String total_fee = null!=map.get("total_fee") && !map.get("total_fee").equals("") ? map.get("total_fee").toString():"";
				String transport_time = null!=map.get("transport_time") && !map.get("transport_time").equals("") ? map.get("transport_time").toString():"";
				String province = null!=map.get("province") && !map.get("province").equals("") ? map.get("province").toString():"";
				String store_name = null!=map.get("store_name") && !map.get("store_name").equals("") ? map.get("store_name").toString():"";
				String cod_flag = null!=map.get("cod_flag") && !map.get("cod_flag").equals("") ? map.get("cod_flag").toString():"";
				String added_weight = null!=map.get("added_weight") && !map.get("added_weight").equals("") ? map.get("added_weight").toString():"";
				String first_weight = null!=map.get("first_weight") && !map.get("first_weight").equals("") ? map.get("first_weight").toString():"";
				String delivery_address = null!=map.get("delivery_address") && !map.get("delivery_address").equals("") ? map.get("delivery_address").toString():"";
				String width = null!=map.get("width") && !map.get("width").equals("") ? map.get("width").toString():"";
				String added_weight_price = null!=map.get("added_weight_price") && !map.get("added_weight_price").equals("") ? map.get("added_weight_price").toString():"";
				String discount = null!=map.get("discount") && !map.get("discount").equals("") ? map.get("discount").toString():"";
				String data_id = null!=map.get("data_id") && !map.get("data_id").equals("") ? map.get("data_id").toString():"";
				String transport_direction = null!=map.get("transport_direction") && !map.get("transport_direction").equals("") ? map.get("transport_direction").toString():"";
				String itemtype_name = null!=map.get("itemtype_name") && !map.get("itemtype_name").equals("") ? map.get("itemtype_name").toString():"";
				String charged_weight = null!=map.get("charged_weight") && !map.get("charged_weight").equals("") ? map.get("charged_weight").toString():"";
				String update_time = null!=map.get("update_time") && !map.get("update_time").equals("") ? map.get("update_time").toString():"";
				String create_time = null!=map.get("create_time") && !map.get("create_time").equals("") ? map.get("create_time").toString():"";
				String first_weight_price = null!=map.get("first_weight_price") && !map.get("first_weight_price").equals("") ? map.get("first_weight_price").toString():"";
				String collection_on_delivery = null!=map.get("collection_on_delivery") && !map.get("collection_on_delivery").equals("") ? map.get("collection_on_delivery").toString():"0.00";
				String jp_num = null!=map.get("jp_num") && !map.get("jp_num").equals("") ? map.get("jp_num").toString():"";
				String insurance_flag = null!=map.get("insurance_flag") && !map.get("insurance_flag").equals("") ? map.get("insurance_flag").toString():"";
				String sku_number = null!=map.get("sku_number") && !map.get("sku_number").equals("") ? map.get("sku_number").toString():"";
				String update_user = null!=map.get("update_user") && !map.get("update_user").equals("") ? map.get("update_user").toString():"";
				String volumn_weight = null!=map.get("volumn_weight") && !map.get("volumn_weight").equals("") ? map.get("volumn_weight").toString():"0.00";
				String volumn = null!=map.get("volumn") && !map.get("volumn").equals("") ? map.get("volumn").toString():"";
				String weight = null!=map.get("weight") && !map.get("weight").equals("") ? map.get("weight").toString():"";
				String extend_prop2 = null!=map.get("extend_prop2") && !map.get("extend_prop2").equals("") ? map.get("extend_prop2").toString():"0.00";
				String extend_prop1 = null!=map.get("extend_prop1") && !map.get("extend_prop1").equals("") ? map.get("extend_prop1").toString():"0.00";
				String state = null!=map.get("state") && !map.get("state").equals("") ? map.get("state").toString():"";
				String standard_freight = null!=map.get("standard_freight") && !map.get("standard_freight").equals("") ? map.get("standard_freight").toString():"";
				String itemtype_code = null!=map.get("itemtype_code") && !map.get("itemtype_code").equals("") ? map.get("itemtype_code").toString():"";
				String express_number = null!=map.get("express_number") && !map.get("express_number").equals("") ? map.get("express_number").toString():"";
				String balance_subject = null!=map.get("balance_subject") && !map.get("balance_subject").equals("") ? map.get("balance_subject").toString():"";
				String dFlag = null!=map.get("dFlag") && !map.get("dFlag").equals("") ? map.get("dFlag").toString():"";
				String id = null!=map.get("id") && !map.get("id").equals("") ? map.get("id").toString():"";
				String order_amount = null!=map.get("order_amount") && !map.get("order_amount").equals("") ? map.get("order_amount").toString():"";
				String cost_center = null!=map.get("cost_center") && !map.get("cost_center").equals("") ? map.get("cost_center").toString():"";
				String store_code = null!=map.get("store_code") && !map.get("store_code").equals("") ? map.get("store_code").toString():"";
				String length = null!=map.get("length") && !map.get("length").equals("") ? map.get("length").toString():"";
				String delegated_pickup = null!=map.get("delegated_pickup") && !map.get("delegated_pickup").equals("") ? map.get("delegated_pickup").toString():"";
				String contract_id = null!=map.get("contract_id") && !map.get("contract_id").equals("") ? map.get("contract_id").toString():"";
				String higth = null!=map.get("higth") && !map.get("higth").equals("") ? map.get("higth").toString():"";
				String insurance_fee = null!=map.get("insurance_fee") && !map.get("insurance_fee").equals("") ? map.get("insurance_fee").toString():"";
				String cod = null!=map.get("cod") && !map.get("cod").equals("") ? map.get("cod").toString():"";
				String jf_weight = null!=map.get("jf_weight") && !map.get("jf_weight").equals("") ? map.get("jf_weight").toString():"";
				String afterdiscount_freight = null!=map.get("afterdiscount_freight") && !map.get("afterdiscount_freight").equals("") ? map.get("afterdiscount_freight").toString():"";
				String transport_code = null!=map.get("transport_code") && !map.get("transport_code").equals("") ? map.get("transport_code").toString():"";
				String create_user = null!=map.get("create_user") && !map.get("create_user").equals("") ? map.get("create_user").toString():"";
				String order_type = null!=map.get("order_type") && !map.get("order_type").equals("") ? map.get("order_type").toString():"";
				StringBuffer ins_sql = new StringBuffer();
				ins_sql.append("insert into tb_warehouse_express_data_settlement_info(remark,street,epistatic_order,transport_name,warehouse,delivery_order,city,total_fee,transport_time,province,store_name,cod_flag,added_weight,first_weight,delivery_address,width,added_weight_price,discount,data_id,transport_direction,itemtype_name,charged_weight,update_time,create_time,first_weight_price,collection_on_delivery,jp_num,insurance_flag,sku_number,update_user,volumn_weight,volumn,weight,extend_prop2,extend_prop1,state,standard_freight,itemtype_code,express_number,balance_subject,dFlag,id,order_amount,cost_center,store_code,length,delegated_pickup,contract_id,higth,insurance_fee,cod,jf_weight,afterdiscount_freight,transport_code,create_user,order_type) ");
				ins_sql.append("values ('"+remark+"','"+street+"','"+epistatic_order+"','"+transport_name+"','"+warehouse+"','"+delivery_order+"','"+city+"','"+total_fee+"','"+transport_time+"','"+province+"','"+store_name+"','"+cod_flag+"','"+added_weight+"','"+first_weight+"','"+delivery_address+"','"+width+"','"+added_weight_price+"','"+discount+"','"+data_id+"','"+transport_direction+"','"+itemtype_name+"','"+charged_weight+"','"+update_time+"','"+create_time+"','"+first_weight_price+"','"+collection_on_delivery+"','"+jp_num+"','"+insurance_flag+"','"+sku_number+"','"+update_user+"','"+volumn_weight+"','"+volumn+"','"+weight+"','"+extend_prop2+"','"+extend_prop1+"','"+state+"','"+standard_freight+"','"+itemtype_code+"','"+express_number+"','"+balance_subject+"','"+dFlag+"','"+id+"','"+order_amount+"','"+cost_center+"','"+store_code+"','"+length+"','"+delegated_pickup+"','"+contract_id+"','"+higth+"','"+insurance_fee+"','"+cod+"','"+jf_weight+"','"+afterdiscount_freight+"','"+transport_code+"','"+create_user+"','"+order_type+"')");
				jdbcUtils.updateByPreparedStatement(ins_sql.toString(), null);
			}
			Date d = new Date();
			System.out.println("插入耗时:"+(d.getTime()-c.getTime())+"ms");
			System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
			jdbcUtils.releaseConn();
		}
		
		// 利用反射查询 单条记录
//		String sql = "select * from userinfo where username = ? ";
//		List<Object> params = new ArrayList<Object>();
//		params.add("李四");
//		UserInfo userInfo;
//		try {
//			userInfo = jdbcUtils.findSimpleRefResult(sql, params, UserInfo.class);
//			System.out.print(userInfo);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}