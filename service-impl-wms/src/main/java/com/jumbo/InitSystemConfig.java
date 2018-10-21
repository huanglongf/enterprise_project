package com.jumbo;

public class InitSystemConfig {
    
    private static Integer EXCEL_ROWS_LIMIT = 20000;

	public InitSystemConfig(String connectTimeout,String responseTimeout,Integer excelRowsLimit) {
		System.setProperty("sun.rmi.transport.connectionTimeout", connectTimeout);
	    System.setProperty("sun.rmi.transport.tcp.responseTimeout", responseTimeout);
	    if(excelRowsLimit != null){
	        EXCEL_ROWS_LIMIT = excelRowsLimit;
	    }
	}
	
	public static Integer getExcelRowsLimit(){
	    return EXCEL_ROWS_LIMIT; 
	}
}
