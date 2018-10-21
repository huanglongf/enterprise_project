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

import loxia.support.json.JSONObject;

public interface TableModel<T> {

    /**
     * 获得当前Table每列的显示名
     * 
     * @return
     */
    String[] getColumnNames();

    /**
     * 获得当前Table每列的数据属性名，如 user.name
     * 
     * @return
     */
    String[] getColumns();

    /**
     * 是否分页
     * 
     * @return
     */
    boolean isPagable();

    /**
     * 分页时每页记录数
     * 
     * @return
     */
    int getPageSize();

    /**
     * Table总记录数
     * 
     * @return
     */
    int getCount();

    /**
     * Table总页数
     * 
     * @return
     */
    int getPageNum();

    /**
     * Table当前显示页
     * 
     * @return
     */
    int getCurrentPage();

    /**
     * 获取Table的结果集
     * 
     * @return
     */
    List<T> getItems();

    /**
     * Table的JSON表示
     * 
     * @param filterString
     * @return
     */
    JSONObject toJson(String filterString);
}
