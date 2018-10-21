package com.jumbo.wms.model.warehouse;

public class PdaHandOverCommand extends PdaHandOver {

    /**
     * 
     */
    private static final long serialVersionUID = 8522987611280092441L;

    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

}
