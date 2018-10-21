package com.jumbo.wms.model.warehouse.baoShui;

public class CustomsDeclarationLineCommand extends CustomsDeclarationLine{

    private static final long serialVersionUID = 7781621067680926109L;
    
    
    private String isManualAdds;//是否手动

    public String getIsManualAdds() {
        return isManualAdds;
    }

    public void setIsManualAdds(String isManualAdds) {
        this.isManualAdds = isManualAdds;
    }
    
}
