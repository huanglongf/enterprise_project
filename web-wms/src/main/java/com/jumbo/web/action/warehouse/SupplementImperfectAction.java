package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.SkuImperfect;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public class SupplementImperfectAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1706711161171892142L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    ChannelManager channelManager;
    @Autowired
    private PrintManager printManager;
    private Long imperfectId;
    private String imperfectIds;
    private SkuImperfect skuImperfect;
    private Sku sku;
    private StockTransApplication sta;



    public String getImperfectIds() {
        return imperfectIds;
    }

    public void setImperfectIds(String imperfectIds) {
        this.imperfectIds = imperfectIds;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public Long getImperfectId() {
        return imperfectId;
    }

    public void setImperfectId(Long imperfectId) {
        this.imperfectId = imperfectId;
    }

    public SkuImperfect getSkuImperfect() {
        return skuImperfect;
    }

    public void setSkuImperfect(SkuImperfect skuImperfect) {
        this.skuImperfect = skuImperfect;
    }

    public String purchaseEntry() {
        return SUCCESS;
    }

    public String imperfectlist() {
        return SUCCESS;
    }

    public String inventoryImperfect() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.inventoryImperfect(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu(), sta, sku, tableConfig.getSorts())));
        return JSON;
    }

    public String addSkuImperfect() {
        JSONObject result = new JSONObject();
        try {
            SkuImperfect imperfect = channelManager.addSkuImperfect(skuImperfect, userDetails.getCurrentOu());
            result.put("msg", SUCCESS);
            result.put("id", imperfect.getId());
        } catch (Exception e) {
            // e.printStackTrace();
            // log.error("", e);
            if (log.isErrorEnabled()) {
                log.error("addSkuImperfect Exception:", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String updateSkuImperfect() {
        JSONObject result = new JSONObject();
        try {
            channelManager.updateSkuImperfect(skuImperfect);
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            // e.printStackTrace();
            // log.error("", e);
            if (log.isErrorEnabled()) {
                log.error("updateSkuImperfect Exception:", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String printSkuImperfect() {
        JasperPrint jp;
        try {
            jp = printManager.skuImperfect(imperfectId);
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

    public String printSkuImperfects() {
        List<JasperPrint> jp;
        try {
            jp = printManager.skuImperfects(imperfectIds);
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
}
