package com.bt.lmis.reflected.utils;

import java.util.Map;
import java.util.ResourceBundle;

import org.apache.velocity.VelocityContext;

/** 
* @ClassName: CreateJava 
* @Description: TODO(创建JAVA) 
* @author Yuriy.Jiang
* @date 2016年6月22日 上午11:17:20 
*  
*/
public class CreateJava {

	private static ResourceBundle res = ResourceBundle.getBundle("config");
	private static String url = res.getString("write_url");
	private static String username = res.getString("write_username");
	private static String passWord = res.getString("write_password");

	/**
	 * 
	 * @param tableName			表名
	 * @param codeName			表名对应的中文注释
	 * @param modulePakPath		模块包路径：com\\bt\\lmis
	 * @param modulePackage		模块包：com.bt.lmis
	 */
	public static void create(String tableName, String codeName, String modulePakPath, String modulePackage, String dbName, String prefix) {
		if (null == tableName || "".equals(tableName)) {
			return;
		}
		if (null == codeName || "".equals(codeName)) {
			return;
		}
		if (null == modulePakPath || "".equals(modulePakPath)) {
			return;
		}
		if (null == modulePackage || "".equals(modulePackage)) {
			return;
		}
		CreateBean createBean = new CreateBean();
		createBean.setMysqlInfo(url, username, passWord, dbName);
		/** 此处修改成你的 表名 和 中文注释 ***/
		String className = createBean.getTablesNameToClassName(tableName, prefix);
		String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());

		// 项目跟路径路径，此处修改为你的项目路径
		String rootPath = CommonPageParser.getRootPath();
		// 资源路径
		String resourcePath = rootPath + "\\src\\main\\resources\\";
		// java路径
		String javaPath = rootPath + "\\src\\main\\java\\";
		String moduleSimplePackage = modulePackage.substring(modulePackage.lastIndexOf(".") + 1, modulePackage.length());

		// //根路径
		// String srcPath = rootPath + "src\\main\\java";
		// //包路径
		// String pckPath = rootPath + "com\\bt\\lmis";
		//
		// File file=new File(pckPath);
		// java,xml文件名称
		String modelPath = "\\model\\" + className + ".java";
		String searchFormPath = "\\controller\\form\\" + className + "QueryParam.java";
		String mapperPath = "\\dao\\" + className + "Mapper.java";
		String servicePath = "\\service\\" + className + "Service.java";
		String serviceImplPath = "\\service\\impl\\" + className + "ServiceImpl.java";
		String controllerPath = "\\controller\\" + className + "Controller.java";
		String sqlMapperPath = "\\dao\\" + className + "Mapper.xml";
		// String springPath="conf\\spring\\";
		// String sqlMapPath="conf\\mybatis\\";

		VelocityContext context = new VelocityContext();
		context.put("className", className); //
		context.put("lowerName", lowerName);
		context.put("codeName", codeName);
		context.put("tableName", tableName);
		context.put("modulePackage", modulePackage);
		context.put("moduleSimplePackage", moduleSimplePackage);
		/****************************** 生成bean字段 *********************************/
		try {
			context.put("feilds", createBean.getBeanFeilds(tableName)); // 生成bean
		} catch (Exception e) {
			e.printStackTrace();
		}

		/******************************* 生成sql语句 **********************************/
		try {
			Map<String, Object> sqlMap = createBean.getAutoCreateSql(tableName);
			context.put("columnDatas", createBean.getColumnDatas(tableName)); // 生成bean
			context.put("SQL", sqlMap);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// -------------------生成文件代码---------------------/

		CommonPageParser.WriterPage(context, "TempBean.java", (javaPath + modulePakPath).replace("\\", "/"), modelPath.replace("\\", "/")); // 生成实体Bean
		CommonPageParser.WriterPage(context, "TempQueryParam.java", (javaPath + modulePakPath).replace("\\", "/"), searchFormPath.replace("\\", "/")); // 生成Model
		CommonPageParser.WriterPage(context, "TempMapper.java", (javaPath + modulePakPath).replace("\\", "/"), mapperPath.replace("\\", "/")); // 生成MybatisMapper接口
		CommonPageParser.WriterPage(context, "TempService.java", (javaPath + modulePakPath).replace("\\", "/"), servicePath.replace("\\", "/"));// 生成Service
		CommonPageParser.WriterPage(context, "TempServiceImpl.java", (javaPath + modulePakPath).replace("\\", "/"), serviceImplPath.replace("\\", "/"));// 生成Service
		CommonPageParser.WriterPage(context, "TempMapper.xml", (resourcePath + modulePakPath).replace("\\", "/"), sqlMapperPath.replace("\\", "/"));// 生成XMLfor Mapper
		CommonPageParser.WriterPage(context, "TempController.java", (javaPath + modulePakPath).replace("\\", "/"), controllerPath.replace("\\", "/"));// 生成Controller
	}
}
