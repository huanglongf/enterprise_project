package com.bt.lmis.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.dao.ManagementOtherLadderMapper;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.ManagementOtherLadder;
import com.bt.lmis.service.ManagementOtherLadderService;
import com.bt.utils.DictUtil;
import com.bt.utils.SessionUtils;

/**
* Title: ManagementOtherLadderServiceImpl
* Description: 
* Company: baozun
* @author jindong.lin
* @date 2017年9月5日
*/
@Service
public class ManagementOtherLadderServiceImpl implements ManagementOtherLadderService {
	
	@Autowired
	private ManagementOtherLadderMapper managementOtherLadderMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return managementOtherLadderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ManagementOtherLadder record) {
		// TODO Auto-generated method stub
		return managementOtherLadderMapper.insert(record);
	}

	@Override
	public int insertSelective(ManagementOtherLadder record) {
		// TODO Auto-generated method stub
		return managementOtherLadderMapper.insertSelective(record);
	}

	@Override
	public ManagementOtherLadder selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return managementOtherLadderMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ManagementOtherLadder record) {
		// TODO Auto-generated method stub
		return managementOtherLadderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ManagementOtherLadder record) {
		// TODO Auto-generated method stub
		return managementOtherLadderMapper.updateByPrimaryKey(record);
	}

	@Override
	public JSONObject loadManECOther(HttpServletRequest request, JSONObject result) throws RuntimeException{
		result = new JSONObject();
		
		ManagementOtherLadder managementOtherLadder = new ManagementOtherLadder();
		managementOtherLadder.setContractId(Integer.valueOf(request.getParameter("cb_id").toString()));
		managementOtherLadder.setType(request.getParameter("type").toString());
		
		List<ManagementOtherLadder> managementOtherLadderList = managementOtherLadderMapper.findList(managementOtherLadder);
		//tb_management_other_ladder_type_name
		if (managementOtherLadderList != null && managementOtherLadderList.size() > 0) {
			result.put("result_null_manEC", "false");
			result.put("manECListShow", managementOtherLadderList);
		} else {
			result.put("result_null_manEC", "true");
		}
		return result;
	}

	@Override
	public List<ManagementOtherLadder> findList(ManagementOtherLadder managementOtherLadder) {
		return managementOtherLadderMapper.findList(managementOtherLadder);
	}

	@Transactional(readOnly=false)
	@Override
	public JSONObject deleteManECOther(HttpServletRequest request, JSONObject result) throws RuntimeException {
		result = new JSONObject();
		Integer id = Integer.parseInt(request.getParameter("id").toString());
		Employee employee = SessionUtils.getEMP(request);
		//删除记录
		if (managementOtherLadderMapper.deleteByPrimaryKey(id) > 0 ) {
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
			//查询现存记录
			ManagementOtherLadder managementOtherLadder = new ManagementOtherLadder();
			managementOtherLadder.setContractId(Integer.valueOf(request.getParameter("cb_id").toString()));
			managementOtherLadder.setType(request.getParameter("type").toString());
			
			List<ManagementOtherLadder> managementOtherLadderList = managementOtherLadderMapper.findList(managementOtherLadder);
			//tb_management_other_ladder_type_name
			if (managementOtherLadderList != null && managementOtherLadderList.size() > 0) {
				//更新排序
				for (int i = 0; i < managementOtherLadderList.size(); i++) {
					managementOtherLadder.setId(managementOtherLadderList.get(i).getId());
					managementOtherLadder.setUpdateTime(new Date());
					managementOtherLadder.setUpdateUser(employee.getId().toString());
					managementOtherLadder.setSort(i);
					managementOtherLadderMapper.updateByPrimaryKeySelective(managementOtherLadder);
				}
				result.put("result_null_manEC", "false");
				result.put("manECListShow", managementOtherLadderList);
			} else {
				result.put("result_null_manEC", "true");
			}
			
			
			
		}
		
		
		
		
		return result;
	}

	@Transactional(readOnly=false)
	@Override
	public JSONObject saveManECOther(HttpServletRequest request, JSONObject result) throws RuntimeException{
		result = new JSONObject();
		
		ManagementOtherLadder managementOtherLadder = new ManagementOtherLadder();
		
		Integer cbId = Integer.valueOf(request.getParameter("cb_id").toString());
		
		String managementFeeType = request.getParameter("management_fee_type").toString();
		
		String ladderMethod = request.getParameter("ladder_method").toString();
		
		String unit =  "元";// request.getParameter("uom_1").toString();
		
		String rate = request.getParameter("charge_percent").toString();
		
		//拼接区间表达式
		StringBuffer section = new StringBuffer(); 
		
		List<ManagementOtherLadder> managementOtherLadderList = null;
		
		BigDecimal minNumber = null;
		
		BigDecimal maxNumber = null;
		
		if (!ladderMethod.equals("0")) {

			minNumber = new BigDecimal(request.getParameter("num_1").toString());
			
			maxNumber = new BigDecimal(request.getParameter("num_2").toString());
			
			if (minNumber.compareTo(maxNumber) < 0) {
				//0 : > , 1 : >=
				if ("0".equals(request.getParameter("compare_1"))) {
					section.append("(");
				} else if ("1".equals(request.getParameter("compare_1"))) {
					section.append("[");
				} else {
					throw new RuntimeException("compare_1不合法");
				}
				section.append(minNumber);
				
				section.append(",");

				section.append(maxNumber);
				//2 : < , 3 : <=
				if ("2".equals(request.getParameter("compare_2"))) {
					section.append(")");
				} else if ("3".equals(request.getParameter("compare_2"))) {
					section.append("]");
				} else {
					throw new RuntimeException("compare_2不合法");
				}

				//校验表达式是否已存在
				managementOtherLadder.setContractId(cbId);
				managementOtherLadder.setType(managementFeeType);
				managementOtherLadder.setBntInterval(section.toString());
				managementOtherLadderList = managementOtherLadderMapper.findList(managementOtherLadder);
				
				if(managementOtherLadderList != null && managementOtherLadderList.size() > 0){
					throw new RuntimeException("输入区间已存在，请删除原纪录重新添加");
				}

				managementOtherLadder.setBntInterval(null);
				managementOtherLadderList = managementOtherLadderMapper.findList(managementOtherLadder);

				if(managementOtherLadderList != null && managementOtherLadderList.size() > 0
						&& (!managementOtherLadderList.get(managementOtherLadderList.size()-1).getLadderMethod().equals(ladderMethod))){
					throw new RuntimeException("输入阶梯方式不一致，请删除原纪录重新添加");
				}
				
			} else {
				throw new RuntimeException("num_1不能大于num_2");
			}
		}
		
		//保存记录
		
		Employee employee = SessionUtils.getEMP(request);
		
		managementOtherLadder.setCreateTime(new Date());
		managementOtherLadder.setCreateUser(employee.getId().toString());
		managementOtherLadder.setUpdateTime(new Date());
		managementOtherLadder.setUpdateUser(employee.getId().toString());
		managementOtherLadder.setContractId(cbId);
		managementOtherLadder.setType(managementFeeType);
		managementOtherLadder.setLadderMethod(ladderMethod);
		managementOtherLadder.setLadderMethodName(DictUtil.getDictLabel("tb_management_other_ladder_ladder_method_name", ladderMethod));
		managementOtherLadder.setTypeName(DictUtil.getDictLabel("tb_management_other_ladder_type_name", managementFeeType));
		managementOtherLadder.setBntIntervalUnit(unit);
		managementOtherLadder.setRate(new BigDecimal(rate));
		
		//重新排序
		managementOtherLadderList = null;
		managementOtherLadder.setBntInterval(null);
		managementOtherLadder.setLadderMethod(null);
		managementOtherLadderList = managementOtherLadderMapper.findList(managementOtherLadder);
		
		//先查再插
		managementOtherLadder.setLadderMethod(ladderMethod);
		managementOtherLadder.setBntInterval(section.toString());
		int r = managementOtherLadderMapper.insertSelective(managementOtherLadder);
		
		if (r > 0) {

			result.put("result_code", "SUCCESS");
			result.put("result_content", "新增成功！");

			if (managementOtherLadderList != null && managementOtherLadderList.size() > 0) {
				//重排序
				String tempMaxNumber = null;
				int times = 0;
				for (int i = 0; i < managementOtherLadderList.size(); i++) {
					tempMaxNumber = managementOtherLadderList.get(i).getBntInterval().split(",")[1];
					tempMaxNumber = tempMaxNumber.substring(0, tempMaxNumber.length()-1);
					if (maxNumber != null && maxNumber.compareTo(new BigDecimal(tempMaxNumber)) < 0) {
						break;
					} else if (maxNumber == null) {
						times = managementOtherLadderList.size() ;
						break;
					}
					times ++ ;
				}
				
				//指定位置插入新增结果
				if (times == managementOtherLadderList.size()) {
					managementOtherLadderList.add(managementOtherLadder);
				} else {
					managementOtherLadderList.add(times, managementOtherLadder);
				}
				
				//更新排序
				managementOtherLadder = new ManagementOtherLadder();
				for (int i = 0; i < managementOtherLadderList.size(); i++) {
					managementOtherLadder.setId(managementOtherLadderList.get(i).getId());
					managementOtherLadder.setUpdateTime(new Date());
					managementOtherLadder.setUpdateUser(employee.getId().toString());
					managementOtherLadder.setSort(i);
					managementOtherLadderMapper.updateByPrimaryKeySelective(managementOtherLadder);
				}
				result.put("result_null_manEC", "false");
				result.put("manECListShow", managementOtherLadderList);
			} else {
				managementOtherLadderList = null;
				managementOtherLadder.setBntInterval(null);
				managementOtherLadder.setLadderMethod(null);
				managementOtherLadderList = managementOtherLadderMapper.findList(managementOtherLadder);
				result.put("result_null_manEC", "false");
				result.put("manECListShow", managementOtherLadderList);
			}
		} else {
			result.put("result_content", "新增失败！");
		}
		
		
		return result;
	}

}
