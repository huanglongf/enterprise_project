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

public class DiffC extends Thread{
	//退货结算机器人
		private DifferenceObj obj;
		public DifferenceObj getObj() {
			return obj;
		}
		public DiffC(DifferenceObj obj) {
			super();
			this.obj = obj;
		}
		public void setObj(DifferenceObj obj) {
			this.obj = obj;
		}
		public void run(){
			System.out.println("C done");
		}

}
