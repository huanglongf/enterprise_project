package com.jumbo.web.action.automaticEquipment;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.lmis.LmisManager;

/**
 * 
 * @author jinggang.chen
 * 
 */
public class OrderOutBoundAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 7615493481976398868L;

    @Autowired
    private LmisManager lmisManager;

    private File file;

    public String orderOutBoundImport() {

        return SUCCESS;
    }

    public String orderOutBoundDataToLmis() {
        String result = null;
        try {
            result = lmisManager.sendOrderOutBoundData(file,userDetails.getCurrentOu().getId());
            if ("success".equals(result)) {
                request.put("msg", "导入成功并同步LMIS");
            } else {
                request.put("msg", result);
            }
        } catch (Exception e) {
            request.put("msg", e);
        }
        if ("success".equals(result)) {
            request.put("msg", "导入成功并同步LMIS");
        } else {
            request.put("msg", result);
        }
        return SUCCESS;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


}
