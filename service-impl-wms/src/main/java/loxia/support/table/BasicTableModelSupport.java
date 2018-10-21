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

import java.io.Serializable;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasicTableModelSupport<T> implements TableModel<T>, Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6188108657048596459L;
    protected static final Logger logger = LoggerFactory.getLogger(BasicTableModelSupport.class);
    protected TableConfig tableConfig;
    /**
     * Table的List数据
     */
    protected List<T> items;
    /**
     * 总共多少条
     */
    protected int count;

    /**
     * 
     * @param tableConfig 参数
     * @param items 不分页的数据
     */
    public BasicTableModelSupport(TableConfig tableConfig, List<T> items) {
        this.tableConfig = tableConfig;
        this.items = items;
        this.count = this.items.size();
        if (tableConfig.getPageSize() < 0) {
            tableConfig.setPageSize(this.count > 0 ? this.count : 1);
        }
    }

    /**
     * 
     * @param tableConfig 参数
     * @param items 分页的数据
     */
    public BasicTableModelSupport(TableConfig tableConfig, Pagination<T> items) {
        this.tableConfig = tableConfig;
        this.items = items == null ? null : items.getItems();
        this.count = Long.valueOf(items == null ? 0 : items.getCount()).intValue();
    }

    public String[] getColumnNames() {
        return tableConfig.getColumnNames();
    }

    public String[] getColumns() {
        return tableConfig.getColumns();
    }

    public int getCount() {
        return count;
    }

    public int getCurrentPage() {
        return tableConfig.getCurrentPage();
    }

    public List<T> getItems() {
        return items;
    }

    public int getPageNum() {
        return tableConfig.getPageSize() <= 0 ? 1 : (count + tableConfig.getPageSize() - 1) / tableConfig.getPageSize();
    }

    public int getPageSize() {
        return tableConfig.getPageSize();
    }

    public boolean isPagable() {
        return tableConfig.isPagable();
    }

    public abstract JSONObject toJson(String filterString);

    public JSONObject toJson() {
        try {
            return toJson("**,-class");
        } catch (IllegalStateException e) {
            logger.error("ToJson exception:{}", e.getMessage());
        }
        return null;
    }
}
