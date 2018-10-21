package com.jumbo.wms.manager.vmi.warehouse;

import com.jumbo.wms.manager.BaseManagerImpl;

public abstract class AbstractVmiWarehouse extends BaseManagerImpl implements VmiWarehouseInterface {
    private static final long serialVersionUID = -2699013472546942080L;
    private String sourceCode;

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

}
