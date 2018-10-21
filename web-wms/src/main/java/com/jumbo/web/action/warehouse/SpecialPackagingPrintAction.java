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

import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.model.warehouse.StaSpecialExecuteType;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;

/**
 * 
 * @author jinlong.ke
 * 
 */
public class SpecialPackagingPrintAction extends BaseJQGridProfileAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = 2280702260698706655L;


    @Autowired
    private PrintManager printManager;
    private Long staId;
    private Integer seType;

    /**
     * 根据 类型打印
     * 
     * @return
     */
    public String bySPTypePrint() {
        JasperPrint jp;
        try {
            if (seType == null) {
                return null;
            }
            jp = printManager.bySPTypePrint(staId, StaSpecialExecuteType.valueOf(seType));
            return printObject(jp);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Integer getSeType() {
        return seType;
    }

    public void setSeType(Integer seType) {
        this.seType = seType;
    }
}
