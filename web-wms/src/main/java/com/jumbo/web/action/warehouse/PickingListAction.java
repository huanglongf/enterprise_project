/**
 * F * Copyright (c) 2010 Jumbomart All Rights Reserved.
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.AllocateCargoOrderCommand;
import com.jumbo.wms.model.warehouse.PickingListCommand;

import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 
 * @author dly
 * 
 */
public class PickingListAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4012186321121611460L;

    @Autowired
    private PrintManager printManager;
    @Autowired
    private WareHouseManager wareHouseManager;

    private AllocateCargoOrderCommand comd;

    private PickingListCommand plCmd;

    private Long pickingListId;
    private String fileName;

    private String code;
    /**
     * 是否需要使用后置打印模板
     */
    private Boolean isPostPrint = false;

    private String paperType;

    public String pickingListQuery() {
        return SUCCESS;
    }

    public String pickingListQueryReturn() {
        return SUCCESS;
    }

    /**
     * 查寻所有的配货清单
     * 
     * @return
     */
    public String queryPickingList() {
        setTableConfig();
        if (comd != null) {
            comd.setFormCrtime(FormatUtil.getDate(comd.getFormCrtime1()));
            comd.setToCrtime(FormatUtil.getDate(comd.getToCrtime1()));
            comd.setFormPickingTime(FormatUtil.getDate(comd.getFormPickingTime1()));
            comd.setToPickingTime(FormatUtil.getDate(comd.getToPickingTime1()));
        }

        request.put(JSON, toJson(wareHouseManager.queryPickingList(tableConfig.getStart(), tableConfig.getPageSize(), comd, userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 查询配货清单明细
     * 
     * @return
     */
    public String queryPickingListDetail() {
        JSONObject result = new JSONObject();
        try {
            PickingListCommand pl = wareHouseManager.getPickingListById(pickingListId);
            if (pl != null) {
                result.put("pickingList", JsonUtil.obj2json(pl));
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 打印配货清单
     */
    public String printPickinglist() {
        JasperPrint jp;
        try {
            jp = printManager.printPackingPage(plCmd.getId(), plCmd.getStaId(), userDetails.getUser().getId(), isPostPrint, paperType);
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

    /**
     * 打印配货清单NIKE定制化
     */
    public String printPickinglistReturn() {
        JasperPrint jp;
        try {
            jp = printManager.printPackingPageReturn(plCmd.getId(), plCmd.getStaId(), userDetails.getUser().getId(), isPostPrint);
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

    public String queryStaStatusByCode() {
        List<PickingListCommand> list = wareHouseManager.queryStaStatusByCode(code);
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    public AllocateCargoOrderCommand getComd() {
        return comd;
    }

    public void setComd(AllocateCargoOrderCommand comd) {
        this.comd = comd;
    }

    public Long getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(Long pickingListId) {
        this.pickingListId = pickingListId;
    }

    public PickingListCommand getPlCmd() {
        return plCmd;
    }

    public void setPlCmd(PickingListCommand plCmd) {
        this.plCmd = plCmd;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getIsPostPrint() {
        return isPostPrint;
    }

    public void setIsPostPrint(Boolean isPostPrint) {
        this.isPostPrint = isPostPrint;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }


}
