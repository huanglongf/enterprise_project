package com.jumbo.wms.model.vmi.warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WlbOrderItemList implements Serializable {


    private static final long serialVersionUID = 1335496665263473650L;
    private List<WlnItem> ItemList = new ArrayList<WlnItem>();

    public List<WlnItem> getItemList() {
        return ItemList;
    }

    public void setItemList(List<WlnItem> itemList) {
        ItemList = itemList;
    }


}
