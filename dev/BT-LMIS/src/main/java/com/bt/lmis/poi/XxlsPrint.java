package com.bt.lmis.poi;

import java.sql.SQLException;
import java.util.List;

public class XxlsPrint extends XxlsAbstract {

	@Override
	public void optRows(int sheetIndex,int curRow, List<String> rowlist) throws SQLException {
		for (int i = 0; i < rowlist.size(); i++) {
			System.out.print("'" + rowlist.get(i) + "',");
		}
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		XxlsPrint howto = new XxlsPrint();
		howto.processOneSheet("C:\\lmis\\rawData\\upload\\jk_settle_order_10001_20160929112048.xlsx",1);
//		howto.processAllSheets("F:/new.xlsx");
	}
}
