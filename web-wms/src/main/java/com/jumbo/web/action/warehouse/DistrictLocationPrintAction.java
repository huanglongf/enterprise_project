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
package com.jumbo.web.action.warehouse;

import loxia.dao.Pagination;
import net.sf.jasperreports.engine.JasperPrint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;

/**
 * 
 * @author jumbo
 * 
 */
public class DistrictLocationPrintAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6853772412464737791L;

    private static Logger log = LoggerFactory.getLogger(DistrictLocationPrintAction.class);

    private Long id;

    private String locCode;
    private String dirstictCode;


    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private PrintManager printManager;

    public String districtLocationPrintEntry() {
        return SUCCESS;
    }

    /**
     * location
     * 
     * @return
     */
    public String findValidLocations() {
        setTableConfig();
        Pagination<WarehouseLocation> allLocations = wareHouseManager.findValidLocationsByouid(tableConfig.getStart(), tableConfig.getPageSize(), locCode, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(allLocations));
        return JSON;
    }

    /**
     * district
     * 
     * @return
     */
    public String findValidDistricts() {
        setTableConfig();
        Pagination<WarehouseDistrict> allDistricts = wareHouseManager.findValidDistrictByouid(tableConfig.getStart(), tableConfig.getPageSize(), dirstictCode, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(allDistricts));
        return JSON;
    }

    /**
     * 库位打印
     * 
     * @return
     * @throws Exception
     */
    public String printLocationBarCode() throws Exception {
        log.debug("Begin print...");
        JasperPrint jpJasperPrint = new JasperPrint();
        try {
            jpJasperPrint = printManager.printLocationBarCode(id);
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }

    }

    /**
     * 库区打印
     * 
     * @return
     * @throws Exception
     */
    public String printDistrictCode() throws Exception {
        log.debug("Begin print...");
        JasperPrint jpJasperPrint = new JasperPrint();
        try {
            jpJasperPrint = printManager.printDistrictCode(id);
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 库区下所有库位打印
     * 
     * @return
     * @throws Exception
     */
    public String printDistrictRelativeLocations() throws Exception {
        log.debug("Begin print...");
        JasperPrint jpJasperPrint = new JasperPrint();
        try {
            jpJasperPrint = printManager.printDistrictRelativeLocations(id);
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getDirstictCode() {
        return dirstictCode;
    }

    public void setDirstictCode(String dirstictCode) {
        this.dirstictCode = dirstictCode;
    }
}
