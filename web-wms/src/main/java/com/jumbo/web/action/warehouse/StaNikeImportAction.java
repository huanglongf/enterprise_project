package com.jumbo.web.action.warehouse;

import java.io.File;
import java.util.List;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.warehouse.RelationNike;

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;

public class StaNikeImportAction extends BaseJQGridProfileAction implements ServletResponseAware {
    

    /**
     * 
     */
    private static final long serialVersionUID = -484042729104871231L;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private WareHouseManager wareHouseManager;

    /**
     * NIKE收货-导入箱号关系
     */
    private RelationNike relationNike;

    private File file;

    public String staNikeImport() {
        return SUCCESS;
    }

    /**
     * 方法说明：NIKE收货-导入箱号关系
     * 
     * @return
     */
    public String importNikeRelation() throws Exception {
        try {
            ReadStatus rs = excelReadManager.nikeRelationImport(file, userDetails.getCurrentOu().getId(), userDetails.getUsername());
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                request.put("result", SUCCESS);
                request.put("msg", "操作成功");
            } else if (rs.getStatus() > 0) {
                String msg = "";
                List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                for (String s : list) {
                    msg += s;
                }
                request.put("msg", msg);
            }
        } catch (BusinessException e) {
            request.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("", e);
            request.put("msg", "error");
        }
        return SUCCESS;
    }



    /**
     * NIKE收货-导入箱号关系查询
     * 
     * @return
     * @throws JSONException
     */
    public String queryNikeRelation() throws JSONException {
        setTableConfig();
        if (relationNike == null) {
            relationNike = new RelationNike();
        }
        request.put(JSON, toJson(wareHouseManager.findRelationNikeByOuid(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId(), relationNike)));
        return JSON;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public RelationNike getRelationNike() {
        return relationNike;
    }

    public void setRelationNike(RelationNike relationNike) {
        this.relationNike = relationNike;
    }

}
