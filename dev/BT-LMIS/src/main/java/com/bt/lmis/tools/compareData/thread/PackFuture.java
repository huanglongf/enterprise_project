package com.bt.lmis.tools.compareData.thread;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.bt.common.CommonUtils;
import com.bt.lmis.tools.compareData.model.WhsCollectData;
import com.bt.lmis.tools.compareData.model.WhsPackCollectData;
import com.bt.lmis.tools.compareData.model.WhsPackData;

public class PackFuture <T> implements Callable<List<WhsPackCollectData>>{

	
	
	private List<WhsPackData> packList;
	private List<WhsCollectData> collList;
	private String empName;
	private String taskCode;

    public PackFuture( List<WhsPackData> packList,List<WhsCollectData> collList,String empName,String taskCode){
    	this.packList=packList;
    	this.collList=collList;
    	this.empName=empName;
    	this.taskCode=taskCode;
    }
    


	@Override
    public List<WhsPackCollectData> call()  {
    	List<WhsPackCollectData> insertList=new ArrayList<>();
    	//装箱数据未在采集数据中找到（CartonNo、Article、act_size三个条件联合查询
		for (WhsPackData p : packList) {
			insertList.add(checkPackCollect(p,collList,empName));
		}
		return insertList;

    }
	
    
    private WhsPackCollectData checkPackCollect(WhsPackData p, List<WhsCollectData> collectList, String empName) {
		
		WhsPackCollectData data = new WhsPackCollectData();
		//装箱数据
		data.setCreateBy(empName);
		data.setUpdateBy(empName);
		data.setTaskCode(taskCode);
		
		data.setPackActSize(p.getActSize());
		data.setPackArticle(p.getArticle());
		data.setPackQty(p.getQty());
		data.setPackSize(p.getSize());
		data.setPackCartonNo(p.getCartonNo());
		for (WhsCollectData c : collectList) {
			
			if(CommonUtils.checkExistOrNot(c.getCartonNo())&&CommonUtils.checkExistOrNot(p.getCartonNo())) {
				if(p.getActSize().equals(c.getActSize())&&p.getArticle().equals(c.getArticle())&&p.getCartonNo().equals(c.getCartonNo())) {
					//采集数据
					data.setCollectActSize(c.getActSize());
					data.setCollectArticle(c.getArticle());
					data.setCollectCartonNo(c.getCartonNo());
					data.setCollectEancode(c.getEancode());
					data.setCollectQty(c.getQty());
					data.setCollectSize(c.getSize());
					data.setCollectStatus(c.getStatus());
					
					if(!p.getSize().equals(c.getSize())) {
						data.setDifferenceReason("亚欧码");
					}
					if(!p.getQty().equals(c.getQty())) {
						data.setCompareResult("失败");
						data.setDifferencesNum(new BigDecimal(p.getQty()).subtract(new BigDecimal(c.getQty())).setScale(0,BigDecimal.ROUND_DOWN).toString());
					}else {
						data.setDifferencesNum("0");
						data.setCompareResult("成功");
					}
					return data;
				}
			}else if (!CommonUtils.checkExistOrNot(c.getCartonNo())&&!CommonUtils.checkExistOrNot(p.getCartonNo())) {
				
				if(p.getActSize().equals(c.getActSize())&&p.getArticle().equals(c.getArticle())) {
					//采集数据
					data.setCollectActSize(c.getActSize());
					data.setCollectArticle(c.getArticle());
					data.setCollectCartonNo(c.getCartonNo());
					data.setCollectEancode(c.getEancode());
					data.setCollectQty(c.getQty());
					data.setCollectSize(c.getSize());
					data.setCollectStatus(c.getStatus());
					
					if(!p.getSize().equals(c.getSize())) {
						data.setDifferenceReason("亚欧码");
					}
					if(!p.getQty().equals(c.getQty())) {
						data.setCompareResult("失败");
						data.setDifferencesNum(new BigDecimal(p.getQty()).subtract(new BigDecimal(c.getQty())).setScale(0,BigDecimal.ROUND_DOWN).toString());
					}else {
						data.setDifferencesNum("0");
						data.setCompareResult("成功");
					}
					return data;
				}
			}
			
			
		}
		data.setCompareResult("失败");
		data.setDifferenceReason("未匹配到对应的采集数据");
		
		return data;
	}
    
}
