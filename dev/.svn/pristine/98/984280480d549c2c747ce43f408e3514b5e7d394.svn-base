package com.bt.lmis.tools.whsTempData.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bt.common.controller.param.Parameter;
import com.bt.common.sequence.dao.SequenceMapper;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.tools.whsTempData.dao.WhsTempDataMapper;
import com.bt.lmis.tools.whsTempData.model.WhsTempData;
import com.bt.lmis.tools.whsTempData.service.WhsTempDataService;

@Transactional(rollbackFor=Exception.class)
@Service
public class WhsTempDataServiceImpl<T> extends ServiceSupport<T> implements WhsTempDataService<T> {
	@Autowired
	private WhsTempDataMapper<T> whsTempDataMapper;
	
	@Autowired
	private SequenceMapper sequenceMapper;

	@Override
	public QueryResult<Map<String, Object>> queryWhsTempData(Parameter parameter) {
		return new QueryResult<Map<String, Object>>(whsTempDataMapper.queryWhsTempData(parameter),
				whsTempDataMapper.countWhsTempData(parameter));
	}

	@Override
	public int deleteBybatchId(String batchId) {
		return whsTempDataMapper.deleteBybatchId(batchId);
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return whsTempDataMapper.selectCount(param);
	}

	@Override
	public List<Map<String, Object>> importWhsTempData(Workbook wk,Employee user,String batchId) {
		
		boolean isSuccess=true;
		
		List<WhsTempData> insertWTDList = new ArrayList<>();
		List<Map<String, Object>> errorListMap = new ArrayList<>();
		Pattern _pattern = Pattern.compile("[0-9]+");// 数字
		Pattern pat = Pattern.compile("[\u2E80-\u9FFF]");//汉字
//		Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");// 数字和字母
		for (Sheet sheet : wk) {
			for (Row r : sheet) {
				if (r.getRowNum() != 0) {
					StringBuffer sb = new StringBuffer();
					Map<String, Object> map = new HashMap<>();
					WhsTempData whsTempData = new WhsTempData();
					Cell cartonNo = null;// 箱号
					Cell location = null;// 库位
					Cell sku = null;// sku
					Cell qty = null;// 数量
					Cell status = null;// 状态

					cartonNo = r.getCell(0);
					location = r.getCell(1);
					sku = r.getCell(2);
					qty = r.getCell(3);
					status = r.getCell(4);
					
					if((cartonNo == null||cartonNo.getStringCellValue().trim().equals(""))
							&&(location == null||location.getStringCellValue().trim().equals(""))
							&&(sku == null||sku.getStringCellValue().trim().equals(""))
							&&(qty == null||qty.getStringCellValue().trim().equals(""))
							&&(status == null||status.getStringCellValue().trim().equals(""))
							){
						break;
					}

					// 箱号校验
					if (cartonNo == null||cartonNo.getStringCellValue().trim().equals("")) {
						map.put("箱号", "");
						sb.append("请输入箱号，");
						isSuccess=false;
					} else {
						String _cartonNo = cartonNo.getStringCellValue().trim();
						map.put("箱号", _cartonNo);
						// 校验汉字
						Matcher mat = pat.matcher(_cartonNo);
						if (mat.find()) {
							sb.append("箱号不可填写汉字，");
							isSuccess=false;
						}
						// 校验长度
						if (_cartonNo.length() > 50) {
							sb.append("箱号数据过长，");
							isSuccess=false;
						}
						whsTempData.setCartonNo(_cartonNo);
					}

					// 库位校验
					if (location == null||location.getStringCellValue().trim().equals("")) {
						map.put("库位", "");
						sb.append("请输入库位，");
						isSuccess=false;
					} else {
						String _location = location.getStringCellValue().trim();
						map.put("库位", _location);
						// 校验汉字
                        Matcher mat = pat.matcher(_location);
                        if (mat.find()) {
                            sb.append("库位不可填写汉字，");
                            isSuccess=false;
                        }
						// 校验长度
						if (_location.length() > 50) {
							sb.append("库位数据过长，");
							isSuccess=false;
						}
						whsTempData.setLocation(_location);
					}

					// sku校验
					if (sku == null) {
						map.put("SKU", "");
					} else {
						String _sku = sku.getStringCellValue().trim();
						map.put("SKU", _sku);
						// 校验汉字
                        Matcher mat = pat.matcher(_sku);
                        if (mat.find()) {
                            sb.append("SKU不可填写汉字，");
                            isSuccess=false;
                        }
						// 校验长度
						if (_sku.length() > 50) {
							sb.append("SKU数据过长，");
							isSuccess=false;
						}
						whsTempData.setSku(_sku);
					}

					// 数量校验
					if (qty == null) {
						map.put("数量", "");
					} else {
						String _qty = qty.getStringCellValue().trim();
						map.put("数量", _qty);
						// 校验特殊字符
						Matcher isNum = _pattern.matcher(_qty);
						if (!isNum.find()) {
							sb.append("数量请输入整型数字，");
							isSuccess=false;
						}
						// 校验长度
						if (_qty.length() > 10) {
							sb.append("数量数据过长，");
							isSuccess=false;
						}
						whsTempData.setQty(_qty);
					}

					// 状态校验
					if (status == null||status.getStringCellValue().trim().equals("")) {
						map.put("状态", "");
						sb.append("请输入状态，");
						isSuccess=false;
					} else {
						String _status = status.getStringCellValue().trim();
						map.put("状态", _status);
						// 校验长度
						if (_status.length() > 50) {
							sb.append("状态数据过长，");
							isSuccess=false;
						}
						whsTempData.setStatus(_status);
					}
					
					
					
					
					whsTempData.setCreateBy(user.getUsername());
					whsTempData.setUpdateBy(user.getUsername());
					whsTempData.setBatchId(batchId);
					String error = sb.toString();
					if(error.endsWith("，")) {
						error=error.substring(0,error.length()-1);
					}
					map.put("错误原因",error);
					errorListMap.add(map);
					insertWTDList.add(whsTempData);
				}
			}
		}
		
		if(isSuccess) {
			
			// 分批添加
			int batch= 100;
			int size= insertWTDList.size();
			int cycle= size / batch;
			if(size% batch != 0){
				cycle++;
			}
			int fromIndex= 0;
			int toIndex= 0;
			for(int i= 0; i< cycle; i++){
				fromIndex= i* batch;
				toIndex= fromIndex+ batch;
				if(toIndex> size){
					toIndex= size;
				}
				// 批量插入仓库暂存数据 
				whsTempDataMapper.createBatch(insertWTDList.subList(fromIndex, toIndex));
			}
			return new ArrayList<Map<String,Object>>();
		}else {
			return errorListMap;
		}
		
	}

	@Override
	public void addSequence(String string) {
		if(sequenceMapper.currval(string)==0) {
			sequenceMapper.createKey(string);
		}
	}
	
	@Override
	public String getSequence(String date) {
		Long l = sequenceMapper.nextval(date);
		return date+String.format("%03d", l);
	}

	@Override
	public int selectBybatchId(String batchId) {
		// TODO Auto-generated method stub
		return whsTempDataMapper.selectBybatchId(batchId);
	}


	@Override
	public ArrayList<Map<String, Object>> exportWhsTempData(Parameter parameter) {
		// TODO Auto-generated method stub
		return whsTempDataMapper.exportWhsTempData(parameter);
	}
	
}
