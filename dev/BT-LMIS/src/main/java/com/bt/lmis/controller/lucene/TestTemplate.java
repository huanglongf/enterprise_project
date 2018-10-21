package com.bt.lmis.controller.lucene;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: TestTemplate 
* @Description: TODO(测试导出模版) 
* @author Yuriy.Jiang
* @date 2017年1月3日 上午10:41:44 
*  
*/
public class TestTemplate {
	
	public static void main(String[] args) {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		try {
			String sql1 = "select * from df_sf_template";
			Date a = new Date();
			List<Map<String, Object>> list = jdbcUtils.findModeResult(sql1, null);
			for (int i = 0; i < list.size(); i++) {
				String express_number = list.get(0).get("express_number").toString();
			}
			Date b = new Date();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
