package com.bt.lmis.tools.compareData.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.bt.common.CommonUtils;
import com.bt.lmis.tools.compareData.model.WhsCollectData;
import com.bt.lmis.tools.compareData.model.WhsPackCollectData;
import com.bt.lmis.tools.compareData.model.WhsPackData;

public class CollectFuture <T> implements Callable<List<WhsPackCollectData>>{
	
	private List<WhsPackData> packList;
	private List<WhsCollectData> collList;
	private String empName;
	private String taskCode;

    public CollectFuture( List<WhsCollectData> collList,List<WhsPackData> packList,String empName,String taskCode){
    	this.packList=packList;
    	this.collList=collList;
    	this.empName=empName;
    	this.taskCode=taskCode;
    }

	@Override
	public List<WhsPackCollectData> call() throws Exception {
		List<WhsPackCollectData> insertList=new ArrayList<>();
		//采集数据未在装箱数据中找到（CartonNo、Article、act_size三个条件联合查询）
		for (WhsCollectData c : collList) {
			WhsPackCollectData checkCollectPack = checkCollectPack(c, packList,empName);
			if(CommonUtils.checkExistOrNot(checkCollectPack)) {
				insertList.add(checkCollectPack);
			}
		}
		return insertList;
	}
	
	
	private WhsPackCollectData checkCollectPack(WhsCollectData c, List<WhsPackData> packList, String empName) {
		
			WhsPackCollectData data = new WhsPackCollectData();
			
			//采集数据
			data.setTaskCode(taskCode);
			data.setCreateBy(empName);
			data.setUpdateBy(empName);
			data.setCollectActSize(c.getActSize());
			data.setCollectArticle(c.getArticle());
			data.setCollectEancode(c.getEancode());
			data.setCollectQty(c.getQty());
			data.setCollectSize(c.getSize());
			data.setCollectStatus(c.getStatus());
			
			data.setCollectCartonNo(c.getCartonNo());
			
			for (WhsPackData p : packList) {
				if(CommonUtils.checkExistOrNot(c.getCartonNo())&&CommonUtils.checkExistOrNot(p.getCartonNo())) {
					if(p.getActSize().equals(c.getActSize())&&p.getArticle().equals(c.getArticle())&&p.getCartonNo().equals(c.getCartonNo())) return null;
				}else if(!CommonUtils.checkExistOrNot(c.getCartonNo())&&!CommonUtils.checkExistOrNot(p.getCartonNo())) {
					if(p.getActSize().equals(c.getActSize())&&p.getArticle().equals(c.getArticle())) return null;
				}
			}
			data.setCompareResult("失败");
			data.setDifferenceReason("未匹配到对应的装箱数据");
			
			return data;
		
	}
	
	
}
