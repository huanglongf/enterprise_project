package com.bt.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author Will.Wang
 * @date 2018年1月26日
 */

public class BalanceSelectMain {
	/**
	 * 写入text文件并发送邮件
	 * @author Mingpeng.Gao
	 * @param texts
	 */
	public static void writeToTxtSendEmail(List<String> texts){
		FileWriter fileWriter =null;
		BufferedWriter bufferedWriter =null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			File file = new File("D:/bt_lmis_log/"+"bt_lmis_log_"+simpleDateFormat.format(new Date())+".txt");

			if(file.exists()){
               file.delete();//存在删除
			}
			file.createNewFile();//创建新的文件
			fileWriter = new FileWriter(file);
			bufferedWriter =new BufferedWriter(fileWriter);
			Iterator<String> iterator =texts.iterator();
			while (iterator.hasNext()){
				bufferedWriter.write(iterator.next());//写入
				bufferedWriter.newLine();//换行
			}
			bufferedWriter.flush();
			//发送邮件
			SendMail sendMail = new SendMail("337578303@qq.com","etkigfnqwyuobggi");
			Map<String,String> map= new HashMap<String,String>();
			map.put("mail.smtp.host", "smtp.qq.com");

			//暂时未成功，需要调试
			map.put("mail.smtp.auth", "true");
			map.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			map.put("mail.smtp.port", "465");
			map.put("mail.smtp.socketFactory.port", "465");
			sendMail.setPros(map);
			sendMail.initMessage();
			/*
			 * 添加收件人有三种方法：
			 * 1,单人添加(单人发送)调用setRecipient(str);发送String类型
			 * 2,多人添加(群发)调用setRecipients(list);发送list集合类型
			 * 3,多人添加(群发)调用setRecipients(sb);发送StringBuffer类型
			 */
			List<String> list = new ArrayList<String>();//接收人列表
			list.add("mingpeng.gao@baozun.com");
			list.add("hongyi.huang@baozun.com");

			sendMail.setRecipients(list);
			sendMail.setSubject(simpleDateFormat.format(new Date())+"_每日结算信息");//主题
			sendMail.setDate(new Date());//发送日期
			sendMail.setFrom("高明朋");//发件人姓名

			String filename="D:/bt_lmis_log/"+"bt_lmis_log_"+simpleDateFormat.format(new Date())+".txt";//附件绝对路径
			sendMail.setMultipart(filename);//发送附件
			sendMail.setContent("具体内容请查看附件.txt文件", "text/html; charset=UTF-8");
			sendMail.sendMessage();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				fileWriter.close();
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	public static void main(String[] args) throws Exception {
		Connection conn = null;
		String sql;
		String dateFromSet = "2018-03-25";
		String dateToSet = "2018-04-24";
		String dateFrom = "2018-04-01";
		String dateTo = "2018-04-30";
		String url = "jdbc:mysql://10.7.46.48:3306/lmis_pe?"
				+ "user=root&password=baozun2017&useUnicode=true&characterEncoding=UTF8";
		List<String> stringList = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			System.out.println("成功加载MySQL驱动程序");
			stringList.add("成功加载MySQL驱动程序");
			stringList.add("dateFromSet="+dateFromSet);
			stringList.add("dateToSet="+dateToSet);
			stringList.add("dateFrom="+dateFrom);
			stringList.add("dateTo="+dateTo);
			System.out.println("dateFromSet="+dateFromSet);
			System.out.println("dateToSet="+dateToSet);
			System.out.println("dateFrom="+dateFrom);
			System.out.println("dateTo="+dateTo);
			conn = DriverManager.getConnection(url);
			stringList.add("1-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("1-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			sql = "SELECT count(id), settle_flag FROM tb_warehouse_express_data GROUP BY settle_flag;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet result =  pstmt.executeQuery();
			while (result.next()) {
				int num = result.getInt("count(id)");
				String settle_flag = result.getString("settle_flag");
				System.out.println(num+"     "+settle_flag);
				stringList.add(num+"     "+settle_flag);
			}
			System.out.println(sql);
			stringList.add(sql);
			stringList.add("2-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			System.out.println("2-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			sql="SELECT count(id) FROM tb_warehouse_express_data_settlement;";
			pstmt = conn.prepareStatement(sql);
			result =  pstmt.executeQuery();
			while (result.next()) {
				int num = result.getInt("count(id)");
				System.out.println(num);
				stringList.add(num+"");
			}
			System.out.println(sql);
			stringList.add(sql);
			System.out.println("3-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			stringList.add("3-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			sql = "SELECT count(id), settle_flag FROM tb_warehouse_express_data WHERE TO_DAYS(transport_time) >= TO_DAYS(?) AND TO_DAYS(transport_time) <= TO_DAYS(?) GROUP BY settle_flag;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dateFromSet);
			pstmt.setString(2, dateToSet);
			result =  pstmt.executeQuery();
			while (result.next()) {
				int num = result.getInt("count(id)");
				String settle_flag = result.getString("settle_flag");
				System.out.println(num+"     "+settle_flag);
				stringList.add(num+"     "+settle_flag);
			}
			System.out.println(sql);
			stringList.add(sql);
			System.out.println("4-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			stringList.add("4-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			sql = "SELECT count(id) FROM tb_warehouse_express_data_settlement WHERE TO_DAYS(transport_time) >= TO_DAYS(?) AND TO_DAYS(transport_time) <= TO_DAYS(?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dateFromSet);
			pstmt.setString(2, dateToSet);
			result =  pstmt.executeQuery();
			while (result.next()) {
				int num = result.getInt("count(id)");
				System.out.println(num);
				stringList.add(num+"");
			}
			System.out.println(sql);
			stringList.add(sql);
			System.out.println("5-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			stringList.add("5-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			sql = "SELECT count(id), settle_client_flag FROM tb_warehouse_express_data GROUP BY settle_client_flag;";
			pstmt = conn.prepareStatement(sql);
			result =  pstmt.executeQuery();
			while (result.next()) {
				int num = result.getInt("count(id)");
				String settle_client_flag = result.getString("settle_client_flag");
				System.out.println(num+"     "+settle_client_flag);
				stringList.add(num+"     "+settle_client_flag);
			}
			System.out.println(sql);
			stringList.add(sql);
			System.out.println("6-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			stringList.add("6-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			sql = "SELECT count(id) FROM tb_warehouse_express_data_store_settlement WHERE package_price IS NULL;";
			pstmt = conn.prepareStatement(sql);
			result =  pstmt.executeQuery();
			while (result.next()) {
				int num = result.getInt("count(id)");
				System.out.println(num);
				stringList.add(num+"");
			}
			System.out.println(sql);
			stringList.add(sql);
			System.out.println("7-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			stringList.add("7-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			sql = "SELECT count(id), settle_client_flag FROM tb_warehouse_express_data WHERE TO_DAYS(transport_time) >= TO_DAYS(?) AND TO_DAYS(transport_time) <= TO_DAYS(?) GROUP BY settle_client_flag;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dateFrom);
			pstmt.setString(2, dateTo);
			result =  pstmt.executeQuery();
			while (result.next()) {
				int num = result.getInt("count(id)");
				String settle_client_flag = result.getString("settle_client_flag");
				System.out.println(num+"     "+settle_client_flag);
				stringList.add(num+"     "+settle_client_flag);
			}
			System.out.println(sql);
			stringList.add(sql);
			System.out.println("8-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			stringList.add("8-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			sql = "SELECT count(id) FROM tb_warehouse_express_data_store_settlement WHERE TO_DAYS(transport_time) >= TO_DAYS(?) AND TO_DAYS(transport_time) <= TO_DAYS(?) AND package_price IS NULL;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dateFrom);
			pstmt.setString(2, dateTo);
			result =  pstmt.executeQuery();
			while (result.next()) {
				int num = result.getInt("count(id)");
				System.out.println(num);
				stringList.add(num+"");
			}
			System.out.println(sql);
			stringList.add(sql);
			System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			stringList.add("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			writeToTxtSendEmail(stringList);//写入D盘txt文件
		} catch (SQLException e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}
}
