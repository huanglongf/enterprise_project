package com.bt.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CmdUtil{ 
	
	public static Runtime rn=Runtime.getRuntime(); 
	
	public static Process p=null;
	
public static void main(String args[]){ 
		 

		String tt="mysqldump -uroot -padmin -d lmis_test er_table_index >F:\\db.sql;";
		String cc="cmd /c mysqldump -uroot -padmin -h127.0.0.1 lim_test  er_expressinfo_master>F:/dbname_db.sql";
		String ss="cmd /c mysql  -uroot -pbaozun2017 -h10.88.97.136 --database lmis_test -N -e \"SELECT   * FROM er_expressinfo_master limit 0,100000\"  | sed \"s/\\t/\",\"/g;s/^/\"/g;s/$/\"/g\"> F:\\test1234.csv";
		//String commond="SELECT  \"运单号\",\"作业单号\",\"平台订单号\",\"收件人\", \"电话\",\"地址\",\"复核时间\", \"称重时间\",\"物流商\", \"产品类型\",\"店铺名\", \"仓库名\",\"目的地省\", \"目的地市\",\"目的地区\", \"预警类型\",\"预警类别\",\"预警级别\",\"时效时间\"";
		/*StringBuffer commond=new StringBuffer("cmd /c mysql  -uroot -padmin -h127.0.0.1 --database lmis_test -N -e \"SELECT  \"1运单号\",\"作业单号\",\"平台订单号\",\"收件人\", \"电话\",\"地址\",\"复核时间\", \"称重时间\",\"物流商\", \"产品类型\",\"店铺名\", \"仓库名\",\"目的地省\", \"目的地市\",\"目的地区\", \"预警类型\",\"预警类别\",\"预警级别\",\"时效时间\" ");
		commond.append("UNION SELECT a.waybill AS \"1运单号\",a.work_no AS \"作业单号\",a.platform_no AS \"平台订单号\",a.shiptoname AS \"收件人\", a.phone AS \"电话\",  a.address AS \"地址\",a.check_time AS \"复核时间\",a.weight_time AS \"称重时间\",");
		commond.append("a.express_name AS \"物流商\", a.producttype_name AS \"产品类型\",a.store_name AS \"店铺名\",a.warehouse_name AS \"仓库名\", a.provice_name AS \"目的地省\",a.city_name AS \"目的地市\",a.state_name AS \"目的地区\",c.warningtype_name as \"预警类型\",");
		commond.append("case  b.warning_category  when 1 then \"超时预警\" when 0 then \"事件预警\" end as \"预警类别\",b.warning_level as \"预警级别\",b.efficient_time as \"时效时间\"");
		commond.append(" FROM er_expressinfo_master  a left join ");
		commond.append(" er_waybill_warninginfo_detail b ");
		commond.append(" on a.waybill=b.waybill  left join ");
		commond.append(" er_warninginfo_maintain_master c on b.warningtype_code=c.warningtype_code and c.dl_flag=1 LIMIT 0 , 10;\"   | sed \"s/\\t/\",\"/g;s/^/\"/g;s/$/\"/g\"");
		commond.append(">F:\\tttt123455.csv");*/
		StringBuffer commond=new StringBuffer("cmd /c mysql  -uroot -pbaozun2017 -h10.88.97.136 --database lmis_test -N -e \"SELECT  \\\"运单号\\\",\\\"作业单号\\\",\\\"平台订单号\\\",\\\"收件人\\\", \\\"电话\\\",\\\"地址\\\",\\\"复核时间\\\", \\\"称重时间\\\",\\\"物流商\\\", \\\"产品类型\\\",\\\"店铺名\\\", \\\"仓库名\\\",\\\"目的地省\\\", \\\"目的地市\\\",\\\"目的地区\\\", \\\"预警类型\\\",\\\"预警类别\\\",\\\"预警级别\\\",\\\"时效时间\\\" ");
		commond.append("UNION SELECT a.waybill ,a.work_no ,a.platform_no,a.shiptoname, a.phone,  a.address,a.check_time,a.weight_time,");
		commond.append("a.express_name , a.producttype_name ,a.store_name,a.warehouse_name , a.provice_name ,a.city_name,a.state_name ,c.warningtype_name,");
		commond.append("case  b.warning_category  when 1 then \\\"超时预警\\\" when 0 then \\\"事件预警\\\" end ,b.warning_level ,b.efficient_time");
		commond.append(" FROM er_expressinfo_master  a left join ");
		commond.append(" er_waybill_warninginfo_detail b ");
		commond.append(" on a.waybill=b.waybill  left join ");
		commond.append(" er_warninginfo_maintain_master c on b.warningtype_code=c.warningtype_code and c.dl_flag=1 LIMIT 0 , 200000;\"   | sed \"s/\\t/\",\"/g;s/^/\"/g;s/$/\"/g\"");

			try{ 
				Runtime runtime = Runtime.getRuntime();  
				for(int i=0;i<5;i++){
					commond.append(">F:\\");
					commond.append(i+"tttt123455.csv");
				//	runtime.exec(commond.toString());
				}
			    }catch(Exception e){ 
			System.out.println("Error exec notepad"); 
			} 
	} 
  
public static void execCommond(String commond){
	try {
		rn.exec("cmd /c "+commond);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
};

private static String getExportCommand() {  
    StringBuffer command = new StringBuffer();  
    String username = "root";//用户名  
    String password = "admin";//用户密码  
    String exportDatabaseName = "lmis_test";//需要导出的数据库名  
    String host = "127.0.0.1";;//从哪个主机导出数据库，如果没有指定这个值，则默认取localhost  
    String port = "3306";//使用的端口号  
    String exportPath = "F:/test1245.csv";//导出路径  
      
    //注意哪些地方要空格，哪些不要空格  
    command.append("mysqldump -u").append(username).append(" -p").append(password)//密码是用的小p，而端口是用的大P。  
    .append(" -h").append(host).append(" -P").append(port).append(" ").append(exportDatabaseName).append(" -r ").append(exportPath);  
    return command.toString();  
}  



private static String[] getImportCommand() {  
    String username = "root";//用户名  
    String password = "admin";//密码  
    String host = "127.0.0.1";//导入的目标数据库所在的主机  
    String port = "3306";//使用的端口号  
    String importDatabaseName = "lmis_test";//导入的目标数据库的名称  
    String importPath = "F:/import.csv";//导入的目标文件所在的位置  
    //第一步，获取登录命令语句  
    String loginCommand = new StringBuffer().append("mysql -u").append(username).append(" -p").append(password).append(" -h").append(host)  
    .append(" -P").append(port).toString();  
    //第二步，获取切换数据库到目标数据库的命令语句  
    String switchCommand = new StringBuffer("use ").append(importDatabaseName).toString();  
    //第三步，获取导入的命令语句  
    String importCommand = new StringBuffer("source ").append(importPath).toString();  
    //需要返回的命令语句数组  
    String[] commands = new String[] {loginCommand, switchCommand, importCommand};  
    return commands;  
}  

}
