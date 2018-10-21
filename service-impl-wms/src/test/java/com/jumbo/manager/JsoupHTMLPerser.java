package com.jumbo.manager;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jumbo.entity.ClassEntity;

public class JsoupHTMLPerser {

	public static String OUTPUT_FILE = "e:\\workbook.xls";
	public static String ZOOKEEPER_URL = "http://dubbo-monitor.scm.baozun.cn";

	public static void main(String[] args) {
		try {
			loadclassByMethod1();
		} catch (Exception e) {
		}
	}

	public static void exportExcel(List<ClassEntity> ListEn) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("dubbo数据解析");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 7000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);
		sheet.setColumnWidth(6, 5000);
		// 创建字体样式
		HSSFFont font = wb.createFont();
		font.setFontName("Verdana");
		font.setBoldweight((short) 100);
		font.setFontHeight((short) 300);

		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 500);// 设定行的高度
		// 创建一个Excel的单元格
		HSSFCell cell1 = row.createCell(0);
		HSSFCell cell2 = row.createCell(1);
		HSSFCell cell3 = row.createCell(2);
		HSSFCell cell4 = row.createCell(3);
		HSSFCell cell5 = row.createCell(4);
		HSSFCell cell6 = row.createCell(5);
		HSSFCell cell7 = row.createCell(6);
		HSSFCell cell8 = row.createCell(7);
		HSSFCell cell9 = row.createCell(8);
		HSSFCell cell10 = row.createCell(9);
		HSSFCell cell11 = row.createCell(10);
		HSSFCell cell12 = row.createCell(11);
		HSSFCell cell13 = row.createCell(12);
		// 给Excel的单元格设置样式和赋值
		// cell.setCellStyle(style);
		cell1.setCellValue("类名");
		cell2.setCellValue("APP");
		cell3.setCellValue("Method");
		cell4.setCellValue("F_Success");
		cell5.setCellValue("T_Success");
		cell6.setCellValue("F_Failure");
		cell7.setCellValue("T_Failure");
		cell8.setCellValue("F_Avg Elapsed (ms)");
		cell9.setCellValue("T_Avg Elapsed (ms)");

		cell10.setCellValue("F_Max Elapsed (ms)");
		cell11.setCellValue("T_Max Elapsed (ms)");

		cell12.setCellValue("F_Max Concurrent");
		cell13.setCellValue("T_Max Concurrent");
		int index = 0;
		for (ClassEntity en : ListEn) {
			// 写类名
			row = sheet.createRow(sheet.getLastRowNum() + 1);
			cell1 = row.createCell(0);
			cell1.setCellValue(en.getEntityClass());
			// APP
			cell2 = row.createCell(1);
			cell2.setCellValue(en.getApp());

			index = sheet.getLastRowNum() + 1;
			// 普通字符
			if (en.getMothed() != null) {
				// 写方法
				for (int j = 0; j < en.getMothed().size(); j++) {
					if (en.getMothed().size() == 1) {
						cell1 = row.createCell(2);
						cell1.setCellValue(en.getMothed().get(j));
					} else {
						if (j == 0) {
							cell1 = row.createCell(2);
							cell1.setCellValue(en.getMothed().get(j));
						} else {
							row = sheet.createRow(index + j - 1);
							cell1 = row.createCell(2);
							cell1.setCellValue(en.getMothed().get(j));
						}
					}
					String[] sc = en.getSuccess().get(j).split(" --> ");
					cell1 = row.createCell(3);
					cell1.setCellValue(sc[0]);
					cell1 = row.createCell(4);
					cell1.setCellValue(sc[1]);

					String[] fail = en.getFailure().get(j).split(" --> ");
					cell1 = row.createCell(5);
					cell1.setCellValue(fail[0]);
					cell1 = row.createCell(6);
					cell1.setCellValue(fail[1]);

					String[] aed = en.getAvgElapsed().get(j).split(" --> ");
					cell1 = row.createCell(7);
					cell1.setCellValue(aed[0]);
					cell1 = row.createCell(8);
					cell1.setCellValue(aed[1]);

					String[] me = en.getMaxElapsed().get(j).split(" --> ");
					cell1 = row.createCell(9);
					cell1.setCellValue(me[0]);
					cell1 = row.createCell(10);
					cell1.setCellValue(me[1]);

					String[] mc = en.getMaxConcurrent().get(j).split(" --> ");
					cell1 = row.createCell(11);
					cell1.setCellValue(mc[0]);
					cell1 = row.createCell(12);
					cell1.setCellValue(mc[1]);

					// CLASS
					cell1 = row.createCell(0);
					cell1.setCellValue(en.getEntityClass());
					// APP
					cell2 = row.createCell(1);
					cell2.setCellValue(en.getApp());
				}
			}

		}

		FileOutputStream os = new FileOutputStream(OUTPUT_FILE);
		wb.write(os);
		os.close();
	}

	/**
	 * 解析后，格式化数据
	 * 
	 * @param data
	 * @return
	 */
	static ClassEntity getData(List<String> data) {
		ClassEntity en = new ClassEntity();
		try {
			List<String> mothed = new ArrayList<String>(); // 方法
			List<String> success = new ArrayList<String>(); // 成功
			List<String> failure = new ArrayList<String>(); // 失败
			List<String> avgElapsed = new ArrayList<String>();
			List<String> maxElapsed = new ArrayList<String>();
			List<String> maxConcurrent = new ArrayList<String>();
			int a = 7; // 存储方法名列
			int b = 8; // 存储成功列
			int c = 9; // 存储失败列
			int d = 10; //
			int f = 11;
			int g = 12;
			for (int i = 0; i < data.size(); i++) {
				if (i == 0) {
					// 存储类名
					en.setEntityClass(data.get(0).substring(data.get(0).indexOf(">", 10) + 2, data.get(0).lastIndexOf("Providers") - 3));
				}
				if (i > 6) {
					if (i == a) {
						a = i + 6;
						mothed.add(data.get(i));
						en.setMothed(mothed);
					}
					if (i == b) {
						b = i + 6;
						success.add(data.get(i));
						en.setSuccess(success);
					}
					if (i == c) {
						c = i + 6;
						failure.add(data.get(i));
						en.setFailure(failure);
					}
					if (i == d) {
						d = i + 6;
						avgElapsed.add(data.get(i));
						en.setAvgElapsed(avgElapsed);
					}
					if (i == f) {
						f = i + 6;
						maxElapsed.add(data.get(i));
						en.setMaxElapsed(maxElapsed);
					}
					if (i == g) {
						g = i + 6;
						maxConcurrent.add(data.get(i));
						en.setMaxConcurrent(maxConcurrent);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return en;
	}

	/**
	 * 加载数据并写入excel
	 */
	static void loadclassByMethod1() {

		List<ClassEntity> listEn = new ArrayList<ClassEntity>();
		try {
			Map<String, Map<String, String>> rs = loadDocByUrl();

			for (Entry<String, Map<String, String>> ent : rs.entrySet()) {
				Map<String, String> val = ent.getValue();
				String url = ZOOKEEPER_URL + "/" + val.get("URL");
				// 获取单独类 所有数据
				List<String> retlist = loadclassByMethod(url);
				// 格式化类数据
				ClassEntity ce = getData(retlist);
				ce.setApp(val.get("APP"));
				ce.setEntityClass(ent.getKey());
				listEn.add(ce);
			}
			System.out.println("类总数量：" + listEn.size());
			exportExcel(listEn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据拼接后的地址 获得类数据
	 * 
	 * @param url
	 * @return
	 */
	static List<String> loadclassByMethod(String url) {
		List<String> list = new ArrayList<String>();
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("searcharg", "java");
			params.put("searchtype", "t");
			params.put("SORT", "DZ");
			params.put("extended", "0");
			Document doc = null;
			int error = 1;
			while (true) {
				try {
					doc = Jsoup.connect(url).userAgent("Mozilla") // 声明了浏览器用于
							// HTTP
							// 请求的用户代理头的值
							.timeout(3 * 1000) // 超时时间
							.data(params) // 请求参数
							.get(); // 使用get方法，对应还有post()
				} catch (Exception e) {
					System.out.println(url + " error :" + error++);
				}
				if (doc != null) {
					break;
				}
			}

			// 获取所有 td标签的值
			Elements links = doc.getElementsByTag("td");
			for (Element ele : links) {
				list.add(ele.text());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 取得所有类路径
	 */
	static Map<String, Map<String, String>> loadDocByUrl() throws Exception {
		// 要请求的网址
		String url = ZOOKEEPER_URL + "/services.html";
		// List<String> list = new ArrayList<String>();
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("searcharg", "java");
		params.put("searchtype", "t");
		params.put("SORT", "DZ");
		params.put("extended", "0");
		Document doc = Jsoup.connect(url).userAgent("Mozilla") // 声明了浏览器用于 HTTP
																// 请求的用户代理头的值
				.timeout(10 * 1000) // 超时时间
				.data(params) // 请求参数
				.get(); // 使用get方法，对应还有post()

		Elements tables = doc.getElementsByTag("table");
		Element table = tables.get(1);
		Element tbody = table.getElementsByTag("tbody").get(0);
		Elements trs = tbody.getElementsByTag("tr");

		Map<String, Map<String, String>> rs = new HashMap<String, Map<String, String>>();
		for (Element tr : trs) {
			Element tdclass = tr.getElementsByTag("td").get(0);
			Element tdapp = tr.getElementsByTag("td").get(1);

			Element tdaurl = tr.getElementsByTag("td").get(5).getElementsByTag("a").get(0);
			String childUrl = tdaurl.attr("href");

			Map<String, String> val = new HashMap<String, String>();
			val.put("URL", childUrl);
			val.put("APP", tdapp.html());
			rs.put(tdclass.html(), val);

		}
		return rs;
		// Elements links = doc.getElementsByTag("a");
		//
		// for (Element ele : links) {
		// if (ele.select(":contains(Statistics)").outerHtml().indexOf("=") !=
		// -1) {
		// list.add(ele.select(":contains(Statistics)").outerHtml().substring(ele.select(":contains(Statistics)").outerHtml().lastIndexOf("=")
		// + 1,
		// ele.select(":contains(Statistics)").outerHtml().lastIndexOf('"')));
		// }
		//
		// }
		// return list;
	}
}
