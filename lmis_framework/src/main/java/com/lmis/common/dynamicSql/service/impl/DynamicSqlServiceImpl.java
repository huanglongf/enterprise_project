package com.lmis.common.dynamicSql.service.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dataFormat.StringUtils;
import com.lmis.common.dynamicSql.dao.DynamicSqlMapper;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.model.Element;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.util.SqlCombineHelper;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.pageElement.model.ViewSetupPageElement;
import com.lmis.setup.pageTable.model.ViewSetupPageTable;

/**
 * @ClassName: DynamicSqlServiceImpl
 * @Description: TODO(动态SQL业务层实现类)
 * @author Ian.Huang
 * @date 2017年12月19日 下午3:15:53
 * 
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class DynamicSqlServiceImpl<T> extends RedisHelperServiceImpl<T> implements DynamicSqlServiceInterface<T>{

    // 关键字静态变量
    private static final String WHERE = " WHERE ";

    private static final String AND = " AND ";

    private static final String EQUALS = " = ";

    private static final String SQM = "'";

    private static final String SELECT = "SELECT ";

    private static final String DEFAULT_COLUMN = "id, version, pwr_org as sql_combine_pwr_org, ";

    private static final String AS = " AS ";

    private static final String FROM = " FROM ";

    private static final String ORDER_BY = " ORDER BY ";

    private static final String DESC = "DESC";

    private static final String COMMA = ", ";

    private static final String SPACE = " ";

    private static final String INSERT_INTO = "INSERT INTO ";

    private static final String VALUES = ") VALUES (";

    private static final String LEFT_BRACKET = " (";

    private static final String RIGHT_BRACKET = ")";

    private static final String ID = "id";

    private static final String CREATE_TIME = "create_time";

    private static final String CREATE_BY = "create_by";

    private static final String UPDATE_TIME = "update_time";

    private static final String UPDATE_BY = "update_by";

    private static final String IS_DELETED = "is_deleted";

    private static final String IS_DISABLED = "is_disabled";

    private static final String VERSION = "version";

    private static final String PWR_ORG = "pwr_org";

    private static final String UUID = "UUID()";

    private static final String NOW = "NOW()";

    private static final String FALSE = "false";

    private static final String INITAIL_VALUE = "1";

    private static final String UPDATE = "UPDATE ";

    private static final String SET = " SET ";

    private static final String NULL = "NULL";

    private static final String PLUS = " + ";

    private static final String DATE_FORMAT = "DATE_FORMAT";

    private static final String DEFAULT_DATE_FORMAT = "\'%Y-%m-%d\'";

    @Autowired
    private HttpSession session;

    @Autowired
    private RedisTemplate<?, ?> redisTemplate;

    @Autowired
    private DynamicSqlMapper<T> dynamicSqlMapper;

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> getWhereSql(DynamicSqlParam<?> dynamicSqlParam) throws Exception{

        // 如删除标志为空，则默认为未删除
        StringBuffer whereSql = new StringBuffer(WHERE);

        // 判断元素集合是否存在
        if (!ObjectUtils.isNull(dynamicSqlParam.getElements())){
            StringBuffer _whereSql = new StringBuffer("");

            // 元素集合存在
            for (int i = 0; i < dynamicSqlParam.getElements().size(); i++){
                ViewSetupPageElement setupPageElement = getSetupPageElement((ValueOperations<String, ViewSetupPageElement>) redisTemplate.opsForValue(), dynamicSqlParam.getElements().get(i).getElementId());
                if (!ObjectUtils.isNull(dynamicSqlParam.getElements().get(i).getValue()) && !ObjectUtils.isNull(setupPageElement)){

                    // 查询条件值存在且元素对象存在，则拼接
                    if (!ObjectUtils.isNull(_whereSql.toString())){

                        // 当where语句不为空串时，拼接查询连接符
                        if (ObjectUtils.isNull(setupPageElement.getWhereType())){

                            // 查询连接符不存在，则默认拼接and
                            _whereSql.append(AND);
                        }else{
                            _whereSql.append(SPACE).append(setupPageElement.getWhereType()).append(SPACE);
                        }
                    }

                    // 当where语句仍为空串时，不拼接查询连接符
                    if (ObjectUtils.isNull(setupPageElement.getColumnId()))
                        throw new Exception("页面元素【" + dynamicSqlParam.getElements().get(i).getElementId() + "】对应字段名不存在，数据异常");
                    _whereSql.append(setupPageElement.getColumnId());
                    if (ObjectUtils.isNull(setupPageElement.getWhereOperator())){

                        // 查询运算符不存在，则默认拼接=
                        _whereSql.append(EQUALS);
                    }else{
                        _whereSql.append(SPACE).append(setupPageElement.getWhereOperator()).append(SPACE);
                    }
                    if (ObjectUtils.isNull(setupPageElement.getColumnType()))
                        throw new Exception("页面元素【" + dynamicSqlParam.getElements().get(i).getElementId() + "】对应字段类型不存在，数据异常");
                    switch (setupPageElement.getColumnType()) {
                        case "varchar":
                            if (!ObjectUtils.isNull(setupPageElement.getWhereOperator()) && ("like".equals(setupPageElement.getWhereOperator().trim()) || "like1".equals(setupPageElement.getWhereOperator().trim()))){
                                _whereSql.append("like".equals(setupPageElement.getWhereOperator()) ? SQM + "%" : SQM);
                                _whereSql.append(dynamicSqlParam.getElements().get(i).getValue()).append("%" + SQM);
                            }else{
                                _whereSql.append(SQM).append(dynamicSqlParam.getElements().get(i).getValue()).append(SQM);
                            }
                            break;
                        case "date":
                            ;
                        case "datetime":
                            _whereSql.append(SQM).append(dynamicSqlParam.getElements().get(i).getValue()).append(SQM);
                            break;
                        default:
                            _whereSql.append(dynamicSqlParam.getElements().get(i).getValue());
                            break;
                    }
                }
                // 对应数据仍不存在，则无需进行下一步

                // 当循环结束，并且存在条件内容时，拼接where关键字
                if (((i + 1) == dynamicSqlParam.getElements().size()) && !ObjectUtils.isNull(_whereSql.toString()))
                    whereSql.append(_whereSql);
            }
        }
        if (whereSql.length() > WHERE.length()){
            if (whereSql.indexOf(IS_DELETED) < 0){
                whereSql.append(AND).append(IS_DELETED).append(EQUALS).append(FALSE);
            }
        }else{
            whereSql.append(IS_DELETED).append(EQUALS).append(FALSE);
        }
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, whereSql);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> getColumnsAndOrderby(DynamicSqlParam<?> dynamicSqlParam) throws Exception{
        if (ObjectUtils.isNull(dynamicSqlParam.getLayoutId()))
            throw new Exception("布局ID不存在");
        StringBuffer selectSql = new StringBuffer("");
        StringBuffer orderbySql = new StringBuffer("");

        // 是否存在排序语句
        if (!ObjectUtils.isNull(dynamicSqlParam.getOrderByStr()))
            orderbySql.append(ORDER_BY).append(dynamicSqlParam.getOrderByStr());
        List<ViewSetupPageTable> setupPageTables = getSetupPageTables((ValueOperations<String, List<ViewSetupPageTable>>) redisTemplate.opsForValue(), dynamicSqlParam.getLayoutId());
        if (!ObjectUtils.isNull(setupPageTables)){
            List<ViewSetupPageTable> orderbys = new ArrayList<ViewSetupPageTable>();
            for (int i = 0; i < setupPageTables.size(); i++){

                // 查询结果字段对应字段名不存在
                if (ObjectUtils.isNull(setupPageTables.get(i).getTableColumnId()))
                    throw new Exception("查询结果字段【" + setupPageTables.get(i).getColumnId() + "】对应字段名不存在，数据异常");

                // 拼接表字段及别名
                StringBuffer columnStr = new StringBuffer(setupPageTables.get(i).getTableColumnId());
                if (!ObjectUtils.isNull(setupPageTables.get(i).getColumnType())){
                    switch (setupPageTables.get(i).getColumnType()) {
                        case "date":
                            ;
                        case "datetime":
                            columnStr = new StringBuffer(DATE_FORMAT).append(LEFT_BRACKET).append(columnStr).append(COMMA);
                            if (ObjectUtils.isNull(setupPageTables.get(i).getColumnFormat())){
                                columnStr.append(DEFAULT_DATE_FORMAT);
                            }else{
                                columnStr.append(SQM).append(setupPageTables.get(i).getColumnFormat()).append(SQM);
                            }
                            columnStr.append(RIGHT_BRACKET);
                            break;
                        default:
                            break;
                    }
                }
                selectSql.append(columnStr).append(AS).append(setupPageTables.get(i).getTableColumnId());

                // 将排序字段按顺序放入集合
                if (ObjectUtils.isNull(orderbySql) && !ObjectUtils.isNull(setupPageTables.get(i).getOrderbyType())){

                    // 排序类型存在
                    if (ObjectUtils.isNull(setupPageTables.get(i).getOrderbySeq())){

                        // 当排序字段排序顺序不存在则默认为0
                        setupPageTables.get(i).setOrderbySeq(0);
                    }
                    sortOrdrby(orderbys, setupPageTables.get(i));
                }

                // 拼接分隔符及关键字
                if (!ObjectUtils.isNull(selectSql.toString())){

                    // 存在select内容时
                    if (((i + 1) == setupPageTables.size())){

                        // 当循环结束，开头拼接select与from关键字
                        selectSql = new StringBuffer(SELECT).append(DEFAULT_COLUMN).append(selectSql).append(FROM);
                        if (ObjectUtils.isNull(setupPageTables.get(i).getTableId())){
                            throw new Exception("查询结果字段【" + setupPageTables.get(i).getColumnId() + "】对应表名不存在，数据异常");
                        }
                        selectSql = selectSql.append(setupPageTables.get(i).getTableId());
                    }else{
                        selectSql.append(COMMA);
                    }
                }
            }

            // 按排序字段集合拼接排序语句
            if (!ObjectUtils.isNull(orderbys)){

                // 排序字段集合不为空
                for (int i = 0; i < orderbys.size(); i++){
                    orderbySql.append(orderbys.get(i).getTableColumnId()).append(SPACE).append(orderbys.get(i).getOrderbyType());

                    // 拼接分隔符及关键字
                    if (!ObjectUtils.isNull(selectSql.toString())){

                        // 存在order by内容时
                        if (((i + 1) == orderbys.size())){

                            // 当循环结束，开头拼接select与from关键字
                            orderbySql = new StringBuffer(ORDER_BY).append(orderbySql);
                        }else{
                            orderbySql.append(COMMA);
                        }
                    }
                }
            }
        }
        JSONObject data = new JSONObject();
        data.put("selectSql", selectSql);
        data.put("orderbySql", ObjectUtils.isNull(orderbySql) ? orderbySql.append(ORDER_BY).append(UPDATE_TIME).append(SPACE).append(DESC) : orderbySql);
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, data);
    }

    /**
     * @Title: sortOrdrby
     * @Description: TODO(前后两进排序，升序排序)
     * @param orderbys
     * @param orderby
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年12月25日 上午11:38:05
     */
    private void sortOrdrby(List<ViewSetupPageTable> orderbys,ViewSetupPageTable orderby){
        if (ObjectUtils.isNull(orderbys)){

            // 还不存在一个排序字段
            orderbys.add(orderby);
        }else{

            // 已存在排序字段
            int count = 0;
            int end = orderbys.size();
            while (true){

                // 比较字段排序小于当前字段排序则替代其位置，顺序后推
                if (orderby.getOrderbySeq().compareTo(orderbys.get(count).getOrderbySeq()) <= 0){
                    orderbys.add(count, orderby);
                    break;
                }

                // 比较字段排序大于最后一个排序字段的排序顺序，则插入它之后
                if (orderbys.get(end - 1).getOrderbySeq().compareTo(orderby.getOrderbySeq()) <= 0){
                    orderbys.add(end, orderby);
                    break;
                }
                count++;
                end--;
            }
        }
    }

    @Override
    public LmisResult<?> getDynamicSearchSql(DynamicSqlParam<?> dynamicSqlParam) throws Exception{
        LmisResult<?> result1 = getColumnsAndOrderby(dynamicSqlParam);
        if (LmisConstant.RESULT_CODE_ERROR.equals(result1.getCode()))
            return result1;
        LmisResult<?> result2 = getWhereSql(dynamicSqlParam);
        if (LmisConstant.RESULT_CODE_ERROR.equals(result2.getCode()))
            return result2;
        JSONObject data1 = (JSONObject) result1.getData();

        // 当select from语句为空，则布局下字段不存在
        if (ObjectUtils.isNull(data1.get("selectSql")))
            throw new Exception("布局下字段不存在，select...from...语句为空");
        StringBuffer sql = new StringBuffer(data1.get("selectSql").toString());
        if (!ObjectUtils.isNull(result2.getData()))
            sql.append(result2.getData().toString());
        if (!ObjectUtils.isNull(data1.get("orderbySql")))
            sql.append(data1.get("orderbySql").toString());
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, sql);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> getDynamicInsertSql(DynamicSqlParam<?> dynamicSqlParam) throws Exception{
        String lmisUserId = "";
        String lmisUserOrg = "";
        try {
            lmisUserId = (String) session.getAttribute("lmisUserId");
            lmisUserOrg = (String) session.getAttribute("lmisUserOrg");
        } catch (Exception e) {
            lmisUserId = dynamicSqlParam.getCreateBy();
            lmisUserOrg = dynamicSqlParam.getPwrOrg();
        }
        StringBuffer sql = new StringBuffer("");
        StringBuffer preSql = new StringBuffer("");
        StringBuffer sufSql = new StringBuffer("");
        List<ViewSetupPageElement> setupPageElements = getSetupPageElements((ValueOperations<String, List<ViewSetupPageElement>>) redisTemplate.opsForValue(), dynamicSqlParam.getLayoutId());
        if (!ObjectUtils.isNull(setupPageElements)) {
        	
        	// 创建上传参数集合副本
        	List<Element> _dynamicSqlParam = new ArrayList<Element>();
        	_dynamicSqlParam.addAll(dynamicSqlParam.getElements());
        	for (int a = 0; a < setupPageElements.size(); a++) {
        		if (ObjectUtils.isNull(setupPageElements.get(a).getColumnId())) throw new Exception("页面元素【" + setupPageElements.get(a).getElementId() + "】对应字段名不存在，数据异常");
        		if(IS_DISABLED.equals(setupPageElements.get(a).getColumnId())) continue;
        		boolean checkNull = false;
        		if(setupPageElements.get(a).getIsNullAble().equals("NO")) checkNull = true;
        		if (!ObjectUtils.isNull(_dynamicSqlParam)) {
        			for (int i = 0; i < _dynamicSqlParam.size(); i++) {
        				if (setupPageElements.get(a).getElementId().equals(_dynamicSqlParam.get(i).getElementId())) {
		     
        					//校验是否为空
        					if (checkNull && ObjectUtils.isNull(_dynamicSqlParam.get(i).getValue()))
        						throw new Exception("页面元素【" + _dynamicSqlParam.get(i).getElementId() + "|" + setupPageElements.get(a).getElementName() + "】对应字段数据不能为空");
    		 
        					//校验长度
        					if (!setupPageElements.get(a).getDataType().equals("tinyint") && !setupPageElements.get(a).getDataType().equals("datetime")){
        						if (!ObjectUtils.isNull(setupPageElements.get(a).getCharacterMaximumLength())) {
        							if (_dynamicSqlParam.get(i).getValue().length() > Long.parseLong(setupPageElements.get(a).getCharacterMaximumLength()))
        								throw new Exception("页面元素【" + _dynamicSqlParam.get(i).getElementId() + "|" + setupPageElements.get(a).getElementName() + "】对应字段数据超出长度限制");
        						} else if (!ObjectUtils.isNull(setupPageElements.get(a).getNumbericPrecision())){
        							String[] value = _dynamicSqlParam.get(i).getValue().split(".");
        							Long dLength = Long.parseLong(setupPageElements.get(a).getNumbericScale());
        							if (value.length > 0 && value[0].length() > Long.parseLong(setupPageElements.get(a).getNumbericPrecision()) - dLength)
        								throw new Exception("页面元素【" + _dynamicSqlParam.get(i).getElementId() + "|" + setupPageElements.get(a).getElementName() + "】对应整数字段数据超出长度限制");
        							if (value.length > 1 && value[1].length() > dLength)
        								throw new Exception("页面元素【" + _dynamicSqlParam.get(i).getElementId() + "|" + setupPageElements.get(a).getElementName() + "】对应小数字段数据超出长度限制");
        						}
        					}
    		 
        					//校验类型
        					if (!setupPageElements.get(a).getDataType().toString().equals(setupPageElements.get(a).getColumnType()))
        						throw new Exception("页面元素【" + _dynamicSqlParam.get(i).getElementId() + "|" + setupPageElements.get(a).getElementName() + "】对应字段数据类型不同，数据异常");
			
        					//获取redis中element对象
        					if (!ObjectUtils.isNull(_dynamicSqlParam.get(i).getValue())) {
        						
        						//拼接分隔符
        						if (!ObjectUtils.isNull(preSql) && !ObjectUtils.isNull(sufSql)) {
        		        			preSql.append(COMMA);
        		        			sufSql.append(COMMA);
        		        		}
        						
        						//插入元素值存在且元素对象存在，则拼接
        						preSql.append(setupPageElements.get(a).getColumnId());
        						if (ObjectUtils.isNull(setupPageElements.get(a).getColumnType()))
        							throw new Exception("页面元素【" + _dynamicSqlParam.get(i).getElementId() + "|" + setupPageElements.get(a).getElementName() + "】对应字段类型不存在，数据异常");
        						switch (setupPageElements.get(a).getColumnType()) {
        						case "varchar":
        						case "date":;
        						case "datetime":
        							String elementValue = dealWithSingleQuote(_dynamicSqlParam.get(i).getValue());
        							sufSql.append(SQM).append(elementValue).append(SQM);
        							break;
        						default:
        							sufSql.append(_dynamicSqlParam.get(i).getValue());
        							break;
        						}
        					}
        					
        					//当前上传参数值已拼接过以后，即剔除下一次匹配队列
        					_dynamicSqlParam.remove(i);
        					break;
        				}
        				if ((i + 1) == _dynamicSqlParam.size()) {
        					if(checkNull) throw new Exception("页面元素【" + setupPageElements.get(a).getElementId() + "|" + setupPageElements.get(a).getElementName() + "】对应字段数据不能为空");
        				} 
        			}
        		} else {
        			break;
        		}
        	}
        	if(!ObjectUtils.isNull(preSql) && !ObjectUtils.isNull(sufSql)) {
        		//循环结束，拼接sql
    			sql.append(INSERT_INTO);
    			if (ObjectUtils.isNull(setupPageElements.get(0).getTableId())) throw new Exception("页面元素【" + setupPageElements.get(0).getElementId() + "】对应表名不存在，数据异常");
    			sql.append(setupPageElements.get(0).getTableId())
    			.append(LEFT_BRACKET)
    			.append(ID)
    			.append(COMMA)
    			.append(CREATE_TIME)
    			.append(COMMA)
    			.append(CREATE_BY)
    			.append(COMMA)
    			.append(UPDATE_TIME)
    			.append(COMMA)
    			.append(UPDATE_BY)
    			.append(COMMA)
    			.append(IS_DELETED)
    			.append(COMMA)
    			.append(IS_DISABLED)
    			.append(COMMA)
    			.append(VERSION)
    			.append(COMMA)
    			.append(PWR_ORG)
    			.append(COMMA)
    			.append(preSql)
    			.append(VALUES)
    			.append(UUID)
    			.append(COMMA)
    			.append(NOW)
    			.append(COMMA)
    			.append(SQM)
    			.append(lmisUserId)
    			.append(SQM)
    			.append(COMMA)
    			.append(NOW)
    			.append(COMMA)
    			.append(SQM)
    			.append(lmisUserId)
    			.append(SQM)
    			.append(COMMA)
    			.append(FALSE)
    			.append(COMMA);
    			if (ObjectUtils.isNull(dynamicSqlParam.getIsDisabled())){
    				sql.append(FALSE);
    			}else{
    				sql.append(dynamicSqlParam.getIsDisabled());
    			}
    			sql.append(COMMA)
    			.append(INITAIL_VALUE)
    			.append(COMMA)
    			.append(SQM)
    			.append(lmisUserOrg)
    			.append(SQM)
    			.append(COMMA)
    			.append(sufSql)
    			.append(RIGHT_BRACKET);
        	}
        }

        // 当insert语句为空
        if (ObjectUtils.isNull(sql)) throw new Exception("insert语句为空");
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, sql);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> getDynamicUpdateSql(DynamicSqlParam<?> dynamicSqlParam) throws Exception{
        String lmisUserId = "";
        try {
            lmisUserId = (String) session.getAttribute("lmisUserId");
        } catch (Exception e) {
            lmisUserId = dynamicSqlParam.getUpdateBy();
        }
        if (ObjectUtils.isNull(dynamicSqlParam.getLayoutId())) throw new Exception("布局ID不存在");
        if (ObjectUtils.isNull(dynamicSqlParam.getId())) throw new Exception("update语句id不存在");
        StringBuffer sql = new StringBuffer("");
        List<ViewSetupPageElement> setupPageElements = getSetupPageElements((ValueOperations<String, List<ViewSetupPageElement>>) redisTemplate.opsForValue(), dynamicSqlParam.getLayoutId());
        if (!ObjectUtils.isNull(setupPageElements)) {
        	
        	// 创建上传参数集合副本
        	List<Element> _dynamicSqlParam = new ArrayList<Element>();
        	_dynamicSqlParam.addAll(dynamicSqlParam.getElements());
            for (int i = 0; i < setupPageElements.size(); i++) {
                if (ObjectUtils.isNull(setupPageElements.get(i).getColumnId())) throw new Exception("页面元素【" + setupPageElements.get(i).getElementId() + "】对应字段名不存在，数据异常");
                if(IS_DISABLED.equals(setupPageElements.get(i).getColumnId())) continue;
                boolean isExist = false;
                boolean checkNull = false;
                if(setupPageElements.get(i).getIsNullAble().equals("NO")) checkNull = true;
                // 拼接分隔符
                if(!ObjectUtils.isNull(sql)) sql.append(COMMA);
                sql.append(setupPageElements.get(i).getColumnId()).append(EQUALS);
                if (!ObjectUtils.isNull(_dynamicSqlParam)) {
                	
                	//查找element_id对应的值有没有上传
                    for (int j = 0; j < _dynamicSqlParam.size(); j++) {
                        if (setupPageElements.get(i).getElementId().equals(_dynamicSqlParam.get(j).getElementId())) {
                        	isExist = true;
                        	
                            //校验是否为空
                            if (checkNull && ObjectUtils.isNull(_dynamicSqlParam.get(j).getValue()))
                            	throw new Exception("页面元素【" + _dynamicSqlParam.get(j).getElementId() + "|" + setupPageElements.get(i).getElementName() + "】对应字段数据不能为空");
                            
                            //校验长度
                            if (!setupPageElements.get(i).getDataType().equals("tinyint") && !setupPageElements.get(i).getDataType().equals("datetime")) {
                                if (!ObjectUtils.isNull(setupPageElements.get(i).getCharacterMaximumLength())) {
                                    if (_dynamicSqlParam.get(j).getValue().length() > Long.parseLong(setupPageElements.get(i).getCharacterMaximumLength()))
                                    	throw new Exception("页面元素【" + _dynamicSqlParam.get(j).getElementId() + "|" + setupPageElements.get(i).getElementName() + "】对应字段数据超出长度限制");
                                } else if (!ObjectUtils.isNull(setupPageElements.get(i).getNumbericPrecision())) {
                                    String[] value = _dynamicSqlParam.get(j).getValue().split("\\.");
                                    Long dLength = Long.parseLong(setupPageElements.get(i).getNumbericScale());
                                    if (value.length > 0
                                    		&& value[0].length() > Long.parseLong(setupPageElements.get(i).getNumbericPrecision()) - dLength)
                                    	throw new Exception("页面元素【" + _dynamicSqlParam.get(j).getElementId() + "|" + setupPageElements.get(i).getElementName() + "】对应整数字段数据超出长度限制");
                                    if (value.length > 1 && value[1].length() > dLength)
                                    	throw new Exception("页面元素【" + _dynamicSqlParam.get(j).getElementId() + "|" + setupPageElements.get(i).getElementName() + "】对应小数字段数据超出长度限制");
                                }
                            }
                            
    						//element_id传了值上来
    						if (ObjectUtils.isNull(setupPageElements.get(i).getColumnType()))
    							throw new Exception("页面元素【" + _dynamicSqlParam.get(i).getElementId() + "|" + setupPageElements.get(i).getElementName() + "】对应字段类型不存在，数据异常");
    						switch (setupPageElements.get(i).getColumnType()) {
    						case "varchar":;
    						case "date":;
    						case "datetime":
    							// 处理字段如值果出现单引号导致动态 SQL 出现语法问题
    							String elementValue = dealWithSingleQuote(_dynamicSqlParam.get(j).getValue());
    							sql.append(SQM).append(elementValue).append(SQM);
    							break;
    						default:
    					        sql.append(_dynamicSqlParam.get(j).getValue());
    					        break;
    						}
    						
    						//当前上传参数值已拼接过以后，即剔除下一次匹配队列
        					_dynamicSqlParam.remove(j);
    						break;
                        }
                    }
                }
            	if(!isExist) {
                	if(checkNull)
                		throw new Exception("页面元素【" + setupPageElements.get(i).getElementId() + "|" + setupPageElements.get(i).getElementName() + "】对应字段数据不能为空");
                    sql.append(NULL);
                }
            }
            //当循环结束，开头拼接update关键字
            if (ObjectUtils.isNull(setupPageElements.get(0).getTableId())) throw new Exception("页面元素【" + setupPageElements.get(0).getTableId() + "】对应表名不存在，数据异常");
            sql = new StringBuffer(UPDATE)
            		.append(setupPageElements.get(0).getTableId())
            		.append(SET)
            		.append(UPDATE_TIME)
            		.append(EQUALS)
            		.append(NOW)
            		.append(COMMA)
            		.append(UPDATE_BY)
            		.append(EQUALS)
            		.append(SQM)
            		.append(lmisUserId)
            		.append(SQM)
            		.append(COMMA)
            		.append(sql);
        }
        StringBuffer whereSql = new StringBuffer(WHERE)
        		.append(ID)
        		.append(EQUALS)
        		.append(SQM)
        		.append(dynamicSqlParam.getId())
        		.append(SQM);
        if (!ObjectUtils.isNull(dynamicSqlParam.getVersion())){
            sql.append(COMMA)
            .append(VERSION)
            .append(EQUALS)
            .append(VERSION)
            .append(PLUS)
            .append(INITAIL_VALUE);
            whereSql.append(AND)
            .append(VERSION)
            .append(EQUALS)
            .append(SQM)
            .append(dynamicSqlParam.getVersion())
            .append(SQM);
        }
        sql.append(whereSql);
        //当update语句为空
        if (ObjectUtils.isNull(sql)) throw new Exception("update语句为空");
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, sql);
    }

    /**
     * 此方法用于处理传入的 SQL 字段值中包含单引号的情况处理
     * @param elementValue
     * @return
     */
	private String dealWithSingleQuote(String elementValue) {
		if (elementValue.contains("'")) {
			elementValue = elementValue.replaceAll("'", "''");
		}
		return elementValue;
	}

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> getDynamicCheckSql(DynamicSqlParam<?> dynamicSqlParam) throws Exception{
        if (ObjectUtils.isNull(dynamicSqlParam.getLayoutId()))
            throw new Exception("布局ID不存在");
        if (ObjectUtils.isNull(dynamicSqlParam.getId()))
            throw new Exception("select语句id不存在");
        List<ViewSetupPageElement> setupPageElements = getSetupPageElements((ValueOperations<String, List<ViewSetupPageElement>>) redisTemplate.opsForValue(), dynamicSqlParam.getLayoutId());
        if (ObjectUtils.isNull(setupPageElements))
            throw new Exception("查看页面元素字段不存在");
        StringBuffer sql = new StringBuffer("");
        for (int i = 0; i < setupPageElements.size(); i++){

            // 页面元素对应字段名不存在
            if (ObjectUtils.isNull(setupPageElements.get(i).getColumnId()))
                throw new Exception("页面元素【" + setupPageElements.get(i).getElementId() + "】对应字段名不存在，数据异常");

            // 拼接表字段及别名
            String columnStr = setupPageElements.get(i).getColumnId();
            if (!ObjectUtils.isNull(setupPageElements.get(i).getColumnType())){
                switch (setupPageElements.get(i).getColumnType()) {
                    case "date":
                        ;
                    case "datetime":
                        if (ObjectUtils.isNull(setupPageElements.get(i).getElementFormat())){
                            sql.append(columnStr).append(AS).append(columnStr);
                        }else{
                            sql.append(DATE_FORMAT).append(LEFT_BRACKET).append(columnStr).append(COMMA).append(SQM).append(setupPageElements.get(i).getElementFormat()).append(SQM).append(RIGHT_BRACKET).append(AS).append(columnStr);
                        }
                        break;
                    default:
                        sql.append(columnStr).append(AS).append(columnStr);
                        break;
                }
            }

            // 拼接分隔符及关键字
            if (!ObjectUtils.isNull(sql.toString())){
                if ((i + 1) == setupPageElements.size()){
                    sql = new StringBuffer(SELECT).append(DEFAULT_COLUMN).append(sql).append(FROM);
                    if (ObjectUtils.isNull(setupPageElements.get(i).getTableId()))
                        throw new Exception("查询结果字段【" + setupPageElements.get(i).getElementId() + "】对应表名不存在，数据异常");
                    sql.append(setupPageElements.get(i).getTableId());
                }else{
                    sql.append(COMMA);
                }
            }
        }
        sql.append(WHERE).append(ID).append(EQUALS).append(SQM).append(dynamicSqlParam.getId()).append(SQM).append(AND).append(IS_DELETED).append(EQUALS).append(FALSE);
        if (ObjectUtils.isNull(sql))
            throw new Exception("select语句为空");
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, sql);
    }

    @Override
    public LmisResult<?> executeSelect(DynamicSqlParam<?> dynamicSqlParam,LmisPageObject pageObject) throws Exception{
        boolean isTrue=SqlCombineHelper.getLocalSqlCombine()==null?false:SqlCombineHelper.getLocalSqlCombine();
        //先执行清空
        SqlCombineHelper.clearLocalPage();
        dynamicSqlParam.setOrderByStr(pageObject.getLmisOrderByStr());
        LmisResult<?> _lmisResult = getDynamicSearchSql(dynamicSqlParam);
        if (LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode()))
            return _lmisResult;
        //根据前面是否已经设置为true 否 直接清
        if (isTrue){
            SqlCombineHelper.setLocalSqlCombine(true);
        }
        Page<Map<String, Object>> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
        dynamicSqlMapper.executeSelect(_lmisResult.getData().toString());
        LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
        lmisResult.setMetaAndData(page.toPageInfo());
        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return lmisResult;
    }

    @Override
    public LmisResult<?> executeSelect(DynamicSqlParam<?> dynamicSqlParam) throws Exception{
        boolean isTrue=SqlCombineHelper.getLocalSqlCombine()==null?false:SqlCombineHelper.getLocalSqlCombine();
        //先执行清空
        SqlCombineHelper.clearLocalPage();
        
        LmisResult<?> _lmisResult = getDynamicCheckSql(dynamicSqlParam);
        if (LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode()))
            return _lmisResult;
        
      //根据前面是否已经设置为true 否 直接清
        if (isTrue){
            SqlCombineHelper.setLocalSqlCombine(true);
        }
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, dynamicSqlMapper.executeSelect(_lmisResult.getData().toString()));
    }

    @Override
    public LmisResult<?> executeInsert(DynamicSqlParam<?> dynamicSqlParam) throws Exception{
        LmisResult<?> _lmisResult = getDynamicInsertSql(dynamicSqlParam);
        if (LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode()))
            return _lmisResult;
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, dynamicSqlMapper.executeInsert(_lmisResult.getData().toString()));
    }

    @Override
    public LmisResult<?> executeUpdate(DynamicSqlParam<?> dynamicSqlParam) throws Exception{
        LmisResult<?> _lmisResult = getDynamicUpdateSql(dynamicSqlParam);
        if (LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode()))
            return _lmisResult;
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, dynamicSqlMapper.executeUpdate(_lmisResult.getData().toString()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public DynamicSqlParam<T> generateTableModel(DynamicSqlParam<T> dynamicSqlParam,T obj) throws Exception{
        if (ObjectUtils.isNull(obj))
            throw new Exception("传入实体为空");
        if (ObjectUtils.isNull(dynamicSqlParam.getTableModel()) && !ObjectUtils.isNull(dynamicSqlParam.getElements())){
            for (int i = 0; i < dynamicSqlParam.getElements().size(); i++){
                ViewSetupPageElement setupPageElement = getSetupPageElement((ValueOperations<String, ViewSetupPageElement>) redisTemplate.opsForValue(), dynamicSqlParam.getElements().get(i).getElementId());
                if (!ObjectUtils.isNull(setupPageElement)){
                    if (!"is_disabled".equals(setupPageElement.getColumnId())){
                        Method method = obj.getClass().getMethod("set" + StringUtils.underline2Hump(setupPageElement.getColumnId(), false), obj.getClass().getDeclaredField(StringUtils.underline2Hump(setupPageElement.getColumnId(), true)).getType());
                        String value = dynamicSqlParam.getElements().get(i).getValue();
                        switch (obj.getClass().getDeclaredField(StringUtils.underline2Hump(setupPageElement.getColumnId(), true)).getType().getSimpleName()) {
                            case "Boolean":
                                method.invoke(obj, Boolean.parseBoolean(value));
                                break;
                            case "Integer":
                                method.invoke(obj, Integer.parseInt(value));
                                break;
                            case "Date":
                                method.invoke(obj, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
                                break;
                            case "BigDecimal":
                                method.invoke(obj, new BigDecimal(value));
                                break;
                            default:
                                method.invoke(obj, value);
                                break;
                        }
                    }
                }
            }

            // 封装ID
            if (!ObjectUtils.isNull(dynamicSqlParam.getId()))
                obj.getClass().getMethod("set" + StringUtils.underline2Hump(ID, false), String.class).invoke(obj, dynamicSqlParam.getId());

            // 封装VERSION
            if (!ObjectUtils.isNull(dynamicSqlParam.getVersion()))
                obj.getClass().getMethod("set" + StringUtils.underline2Hump(VERSION, false), Integer.class).invoke(obj, dynamicSqlParam.getVersion());

            // 表对象实体赋值
            dynamicSqlParam.setTableModel(obj);
        }
        return dynamicSqlParam;
    }

}
