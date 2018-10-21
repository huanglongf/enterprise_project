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

public class RobortB extends Thread {

	
	//退货结算机器人
		private DifferenceObj obj;
		public DifferenceObj getObj() {
			return obj;
		}
		public RobortB(DifferenceObj obj) {
			super();
			this.obj = obj;
		}
		public void setObj(DifferenceObj obj) {
			this.obj = obj;
		}
		public void run(){
			DifferenceMatchingMapper<T> differenceMatchingMapper= (DifferenceMatchingMapper<T>)SpringUtils.getBean("differenceMatchingMapper");
			//建退货入库表
			differenceMatchingMapper.deleteReturnTabCYPP(obj.getBat_id());
			differenceMatchingMapper.createReturnTab(obj.getBat_id());
		
			LinkedHashMap<String, String> tableHeader=this.obj.getTableHeader();
			List<Map<String, Object>> tableContent=differenceMatchingMapper.returnTableContentCYPP(obj.getBat_id());
			
			//建表
			differenceMatchingMapper.deleteWarehouseTabCYPP(obj.getBat_id());
			for(Map<String, Object> pp:tableContent){
				differenceMatchingMapper.InsertWarehouseTabCYPP(pp);
			}
			
			differenceMatchingMapper.createWarehouseTabCYPP(obj.getBat_id());
			
			RobortC c=new RobortC(obj);
			c.start();
			try {
				File f=BigExcelExport.excelDownLoadDatab_Z(tableContent, tableHeader, obj.getFileName(), "退货入库的.xlsx");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				c.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("B done");
		}
}
