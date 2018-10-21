/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package loxia.support.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.util.BeanUtilSupport;

/**
 * 
 * @author wanghua
 * 
 * @param <T>
 */
public class TableModelJSGridImpl<T> extends BasicTableModelSupport<T> {
    private static final long serialVersionUID = -6188108657048596459L;
    private static final Logger log = LoggerFactory.getLogger(TableModelJSGridImpl.class);

    /**
     * 
     * @param tableConfig 参数
     * @param items 不分页的数据
     */
    public TableModelJSGridImpl(TableConfig tableConfig, List<T> items) {
        super(tableConfig, items);
    }

    /**
     * 
     * @param tableConfig 参数
     * @param items 分页的数据
     */
    public TableModelJSGridImpl(TableConfig tableConfig, Pagination<T> items) {
        super(tableConfig, items);
    }

    public JSONObject toJson(String filterString) {
        JSONObject json = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("page", getCurrentPage());
            map.put("total", getPageNum());
            map.put("records", getCount());
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (T item : getItems()) {
                Map<String, Object> cell = new HashMap<String, Object>();
                cell.putAll(addColumnValues(item));
                list.add(cell);
            }
            JSONArray rows = new JSONArray(list, filterString);
            map.put("rows", rows);
        } catch (Exception e) {
            log.error("",e);
            log.error("TableModelJSGridImpl.toJson({}),对象转换Map<Key,Value>失败", filterString, e);
        }
        json = new JSONObject(map, filterString);
        return json;
    }

    /**
     * 对象转为map,并根据getColumns()的列名过滤,只取指定key
     * 
     * @param item
     * @return
     */
    protected Map<String, Object> addColumnValues(T item) {
        return addColumnValues(getModelMap(item));
    }

    /**
     * 根据getColumns()的列名过滤,只取指定key
     */
    protected Map<String, Object> addColumnValues(Map<String, Object> item) {
        if (getColumns() == null) {
            return item;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        for (String column : getColumns()) {
            result.put(column, item.get(column));
        }
        return result;
    }

    /**
     * 按一级结构把对象转为map
     * 
     * @see com.jumbo.util.BeanUtilSupport#describe(Map, String, Object)
     * @param item
     * @return
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> getModelMap(T item) {
        if (Map.class.isAssignableFrom(item.getClass())) return (Map<String, Object>) item;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanUtilSupport.describe(map, null, item, getColumns());
        } catch (Exception e) {
            log.error("",e);
            log.error("BeanUtilSupport.describe(map, null, {}),对象转换Map<Key,Value>失败", item, e);
        }
        return map;
    }
}
