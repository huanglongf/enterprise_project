package com.jumbo.wms.model.dataimport;

import com.jumbo.wms.model.BaseModel;

public class ShopDataSource extends BaseModel {

    private static final long serialVersionUID = 3100622120155176953L;
    /**
     * 店铺名称
     */
    private String dataSourceShopName;
    /**
     * 数据来源
     */
    private String datasource;
    /**
     * 店铺编码
     */
    private String dataSourceShopCode;

    public ShopDataSource(String dataSourceShopName, String datasource, String dataSourceShopCode) {
        super();
        this.dataSourceShopName = dataSourceShopName;
        this.datasource = datasource;
        this.dataSourceShopCode = dataSourceShopCode;
    }

    public ShopDataSource() {
        super();
    }

    public String getDataSourceShopName() {
        return dataSourceShopName;
    }

    public void setDataSourceShopName(String dataSourceShopName) {
        this.dataSourceShopName = dataSourceShopName;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getDataSourceShopCode() {
        return dataSourceShopCode;
    }

    public void setDataSourceShopCode(String dataSourceShopCode) {
        this.dataSourceShopCode = dataSourceShopCode;
    }
}
