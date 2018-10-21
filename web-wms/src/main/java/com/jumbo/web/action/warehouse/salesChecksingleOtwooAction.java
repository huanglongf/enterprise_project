package com.jumbo.web.action.warehouse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.model.system.ChooseOption;

public class salesChecksingleOtwooAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 9150225033516810616L;
    @Autowired
    private DropDownListManager dropDownListManager;

    public List<ChooseOption> getPlStatus() {
        return plStatus;
    }

    public void setPlStatus(List<ChooseOption> plStatus) {
        this.plStatus = plStatus;
    }

    private List<ChooseOption> plStatus;

    /**
     * O2O单件核对
     * 
     * @return
     */
    public String entPlCheck() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

}
