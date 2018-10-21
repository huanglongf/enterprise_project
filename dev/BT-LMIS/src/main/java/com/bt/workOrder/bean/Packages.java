package com.bt.workOrder.bean;

public class Packages {
    private String id;

    private String batId;

    private String skuNumber;

    private String barcode;

    private String itemName;

    private String extendPro;
    
    private int qty;

    
    public Packages(){
        super();
    }


    public String getId(){
        return id;
    }

    
    public void setId(String id){
        this.id = id;
    }

    
    public String getBatId(){
        return batId;
    }

    
    public void setBatId(String batId){
        this.batId = batId;
    }

    
    public String getSkuNumber(){
        return skuNumber;
    }

    
    public void setSkuNumber(String skuNumber){
        this.skuNumber = skuNumber;
    }

    
    public String getBarcode(){
        return barcode;
    }

    
    public void setBarcode(String barcode){
        this.barcode = barcode;
    }

    
    public String getItemName(){
        return itemName;
    }

    
    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    
    public String getExtendPro(){
        return extendPro;
    }

    
    public void setExtendPro(String extendPro){
        this.extendPro = extendPro;
    }

    
    public int getQty(){
        return qty;
    }

    
    public void setQty(int qty){
        this.qty = qty;
    }


    public Packages(String id, String batId, String skuNumber, String barcode, String itemName, String extendPro, int qty){
        super();
        this.id = id;
        this.batId = batId;
        this.skuNumber = skuNumber;
        this.barcode = barcode;
        this.itemName = itemName;
        this.extendPro = extendPro;
        this.qty = qty;
    }


    @Override
    public String toString(){
        return "Packages [id=" + id + ", batId=" + batId + ", skuNumber=" + skuNumber + ", barcode=" + barcode + ", itemName=" + itemName + ", extendPro=" + extendPro + ", qty=" + qty + "]";
    }

   
	
    
}