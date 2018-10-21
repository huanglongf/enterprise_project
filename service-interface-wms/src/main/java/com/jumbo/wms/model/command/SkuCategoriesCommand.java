package com.jumbo.wms.model.command;

import com.jumbo.wms.model.baseinfo.SkuCategories;

public class SkuCategoriesCommand extends SkuCategories {

    /**
     * 
     */
    private static final long serialVersionUID = -295222910688162498L;
    
    /**
     * 父亲节点Id
     */
    private Long parentId;


    public Long getParentId() {
        return parentId;
    }


    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
