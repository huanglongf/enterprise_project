package com.lmis.common.getData.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.getData.dao.GetConstantDataMapper;
import com.lmis.common.getData.model.ConstantData;
import com.lmis.common.getData.service.GetConstantDataServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.constantSql.dao.SetupConstantSqlMapper;
import com.lmis.setup.constantSql.model.SetupConstantSql;

import javax.annotation.Resource;

/**
 * @author daikaihua
 * @date 2017年11月28日
 * @todo TODO
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class GetConstantDataServiceImpl<T extends ConstantData> implements GetConstantDataServiceInterface<T>{

	@Autowired
	private GetConstantDataMapper<T> getConstantDataMapper;
	
	@Autowired
	private SetupConstantSqlMapper<SetupConstantSql> setupConstantSqlMapper;

	@Resource(name = "dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<?> dynamicSqlService;

	@Autowired
    private RedisTemplate<?, ?> redisTemplate;
	
	//redis命名规则，常量部分的"setup:constant:"+表setup_constant_sql中字段sql_code
	@Value("${redis.key.constant}")
    String redisKeyConstant;
	//下拉框读取redis数据开关0-关1-开
	@Value("${dropdown.redis.switch}")
    String dropdownRedisSwitch;
	//下拉框读取数据页长配置
	@Value("${dropdown.page.pageSize}")
    String dropdownRedisPageSize;
	
	
	@Override
	public LmisResult<SetupConstantSql> getConstantDate(String jsonStr) {
		JSONArray jsonArray = JSON.parseArray(jsonStr);
		List<SetupConstantSql> rstList = new ArrayList<SetupConstantSql>();
		LmisResult<SetupConstantSql> lmisResult = new LmisResult<SetupConstantSql>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			
			//通过sqlCode获取对应的sql语句
			String sqlCode = jsonObject.get("sqlCode").toString();
			//redis开关打开
			if(dropdownRedisSwitch.equals("1")) {
				//从redis中获取对应的数据
				@SuppressWarnings("unchecked")
				ValueOperations<String, SetupConstantSql> operations = (ValueOperations<String, SetupConstantSql>)redisTemplate.opsForValue();
				String redisKey = redisKeyConstant + sqlCode;
				SetupConstantSql scs = operations.get(redisKey);
				if(!ObjectUtils.isNull(scs) && CollectionUtils.isNotEmpty(scs.getList())) {
					rstList.add(scs);
				} else {
					// redis中没有值的情况，需要从数据库中获取
					List<SetupConstantSql> list = setupConstantSqlMapper.retrieve(new SetupConstantSql(null, false, sqlCode));
					if(!ObjectUtils.isNull(list)) {
						
						String sql = list.get(0).getSqlRemark();
						sql=sql+" LIMIT "+dropdownRedisPageSize;
						// 通过sql语句获取对应的参数列表
						list.get(0).setList(getConstantDataMapper.getConstantSqlData(sql));
						
						// 把值插入redis中
						operations.set(redisKey, list.get(0));
						rstList.add(list.get(0));
					}
				}
				//redis开关关闭
			}else {
				List<SetupConstantSql> list = setupConstantSqlMapper.retrieve(new SetupConstantSql(null, false, sqlCode));
				if(!ObjectUtils.isNull(list)) {
					String sql = list.get(0).getSqlRemark();
					sql=sql+" LIMIT "+dropdownRedisPageSize;
					// 通过sql语句获取对应的参数列表
					list.get(0).setList(getConstantDataMapper.getConstantSqlData(sql));
					rstList.add(list.get(0));
				}
			}
		}
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		lmisResult.setData(rstList);
		return lmisResult;
	}	

	@Override
	public LmisResult<SetupConstantSql> redisForConstantSql() {
		LmisResult<SetupConstantSql> lmisResult = new LmisResult<SetupConstantSql>();
		@SuppressWarnings("unchecked")
		ValueOperations<String, SetupConstantSql> operations = (ValueOperations<String, SetupConstantSql>)redisTemplate.opsForValue();
		SetupConstantSql scs = new SetupConstantSql();
		scs.setIsDeleted(false);
		List<SetupConstantSql> list = setupConstantSqlMapper.retrieve(scs);
		if(!ObjectUtils.isNull(list)) {
			for(int i = 0; i < list.size(); i++) {
				SetupConstantSql tmp = list.get(i);
				
				// 判断sql语句中是否带"?",包含的情况下则为需要参数才能 执行的sql，redis的key中需要加上sql
				if(tmp.getSqlRemark().indexOf("?")!=-1) {
					
					// 把值插入redis中
					operations.set(redisKeyConstant + "sql:" + tmp.getSqlCode(), tmp);
				} else if(tmp.getSqlRemark().toLowerCase().indexOf("select")!=-1) {
					
					// 不带"?"的情况，一般为页面下拉框，可以通过sql直接把key/value的list加载到redis中去
					tmp.setList(getConstantDataMapper.getConstantSqlData(tmp.getSqlRemark()));
					
					//把值插入redis中
					operations.set(redisKeyConstant + tmp.getSqlCode(), tmp);
				}
			}
		}
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	public LmisResult<ConstantData> getElementValue(ConstantData constantData) {
		LmisResult<ConstantData> lmisResult = new LmisResult<ConstantData>();
		String sqlCode = constantData.getCode();
		
		String sqlStr = null;
		if(dropdownRedisSwitch.equals("1")) {
			// 从redis中获取对应的数据
			@SuppressWarnings("unchecked")
			ValueOperations<String, SetupConstantSql> operations = (ValueOperations<String, SetupConstantSql>)redisTemplate.opsForValue();
			String redisKey = redisKeyConstant + "sql:" + sqlCode;
			SetupConstantSql scs = operations.get(redisKey);
			if(scs != null) {
				sqlStr = scs.getSqlRemark();
			} else {
				// redis中没有值的情况，需要从数据库中获取
				List<SetupConstantSql> list = setupConstantSqlMapper.retrieve(new SetupConstantSql(null, false, sqlCode));
				if(!ObjectUtils.isNull(list)){
					SetupConstantSql rstScs = list.get(0);
					
					// 通过sql语句获取对应的参数列表
					sqlStr = rstScs.getSqlRemark();
					
					// 把值插入redis中
					operations.set(redisKey, rstScs);
				}
			}
		}else {
			// redis中没有值的情况，需要从数据库中获取
			List<SetupConstantSql> list = setupConstantSqlMapper.retrieve(new SetupConstantSql(null, false, sqlCode));
			if(!ObjectUtils.isNull(list)){
				SetupConstantSql rstScs = list.get(0);
				
				// 通过sql语句获取对应的参数列表
				sqlStr = rstScs.getSqlRemark();
			}
		}
		if(!"".equals(sqlStr)) {
			
			//给sql语句拼装参数值，最多支持5个，从value1开始
			if(sqlStr.indexOf("?")!=-1)
				sqlStr = sqlStr.replaceFirst("\\?", "'" + constantData.getValue1() + "'");
			if(sqlStr.indexOf("?")!=-1)
				sqlStr = sqlStr.replaceFirst("\\?", "'" + constantData.getValue2() + "'");
			if(sqlStr.indexOf("?")!=-1)
				sqlStr = sqlStr.replaceFirst("\\?", "'" + constantData.getValue3() + "'");
			if(sqlStr.indexOf("?")!=-1)
				sqlStr = sqlStr.replaceFirst("\\?", "'" + constantData.getValue4() + "'");
			if(sqlStr.indexOf("?")!=-1)
				sqlStr = sqlStr.replaceFirst("\\?", "'" + constantData.getValue5() + "'");
			List<ConstantData> valueList = getConstantDataMapper.getConstantSqlData(sqlStr);

			lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
			lmisResult.setData(valueList);
			return lmisResult;
		}
		lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
		return lmisResult;
	}
	
}
