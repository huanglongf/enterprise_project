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

import java.util.List;
import java.util.Map;

/**
 * 
 * @author wanghua
 * @see com.jumbo.web.action.BaseJQGridAction
 */
public interface TableConfigAware {
    /**
     * 前台传的列名参数,根据列名,把需要的数据放入json中
     * 
     * @param columns
     */
    void setColumns(String columns);

    /**
     * 当前是第几页
     * 
     * @param page
     */
    void setPage(int page);

    /**
     * 排序的字段
     * 
     * @param sidx
     */
    void setSidx(String sidx);

    /**
     * 排序的asc,desc
     * 
     * @param sord
     */
    void setSord(String sord);

    /**
     * 分页的每页多少条
     * 
     * @param rows
     */
    void setRows(int rows);

    /**
     * 设置当前Table每列的显示名
     * 
     * @param colNames
     */
    void setColNames(List<String> colNames);


    /**
     * 设置当前Table每列的数据属性名
     * 
     * @param colModel
     */
    void setColModel(List<String> colModel);

    /**
     * 表头的名字,可作为excel导出的参数
     * 
     * @param caption
     */
    void setCaption(String caption);

    /**
     * 普通的Jqgrid/Excel导出
     * 
     * @param isExcel
     */
    void setIsExcel(Boolean isExcel);

    void setColumnOption(Map<String, Object> columnOption);

    /**
     * 根据其他参数构造TableConfig
     */
    void setTableConfig();
}
