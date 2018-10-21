package com.bt.lmis.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.dao.DifferenceMatchingMapper;
import com.bt.lmis.model.DifferenceObj;
import com.bt.lmis.model.SimpleTable;
import com.bt.utils.BigExcelExport;

public class RobortC extends Thread{
	//退货结算机器人
		private DifferenceObj obj;
		public DifferenceObj getObj() {
			return obj;
		}
		public RobortC(DifferenceObj obj) {
			super();
			this.obj = obj;
		}
		public void setObj(DifferenceObj obj) {
			this.obj = obj;
		}
		public void run(){
			List<SimpleTable> sts= new ArrayList<SimpleTable>();
			DifferenceMatchingMapper<T> differenceMatchingMapper= (DifferenceMatchingMapper<T>)SpringUtils.getBean("differenceMatchingMapper");
		
			LinkedHashMap<String, String> tableHeader=this.obj.getTableHeader();
			List<Map<String, Object>> tableContent=differenceMatchingMapper.warehouseTableContent(obj.getBat_id());
			try {
				File f=BigExcelExport.excelDownLoadDatab_Z(tableContent, tableHeader, obj.getFileName(), "原始数据.xlsx");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("C done");
		}

}
