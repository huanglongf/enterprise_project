package com.bt.lmis.core.business;

public class test {
	public static void main(String[] args) {
		try {
			IExcelParse ie = new ExcelParse2007();
			ie.loadExcel("/usr/local/log/申通模板.xlsx");
			System.out.println(ie.getSheetCount());
			System.out.println(ie.getSheetName(1));
			System.out.println(ie.getRealRowCount(1));
			for (int i = 2; i < ie.getRealRowCount(1); i++) {
				String[] row = ie.readExcelByRow(1, i);
			}
		} catch (Exception e) {
			if(e.toString().indexOf("NullPointerException")>0){
				System.out.println("空指针!");
			}else{
				e.printStackTrace();
			}
		}
	}
}
