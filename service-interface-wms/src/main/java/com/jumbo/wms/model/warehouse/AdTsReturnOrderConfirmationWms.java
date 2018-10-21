package com.jumbo.wms.model.warehouse;

import java.io.Serializable;
import java.util.List;

public class AdTsReturnOrderConfirmationWms implements Serializable{
    private static final long serialVersionUID = -9026434693529494866L;
    
    public List<return_info> return_info;
    
  
    public List<return_info> getReturn_info() {
        return return_info;
    }


    public void setReturn_info(List<return_info> return_info) {
        this.return_info = return_info;
    }


    public static class return_info implements Serializable{
        /**
         * 
         */
        private static final long serialVersionUID = -794020207359278615L;
        public String   ts_order_id;
        public String   status;
        public String   wms_status;
        public String   wms_order_type;
        public String   remark;
        
        public String getTs_order_id(){
            return ts_order_id;
        }
        
        public void setTs_order_id(String ts_order_id){
            this.ts_order_id = ts_order_id;
        }
        
        public String getStatus(){
            return status;
        }
        
        public void setStatus(String status){
            this.status = status;
        }
        
        public String getWms_status(){
            return wms_status;
        }
        
        public void setWms_status(String wms_status){
            this.wms_status = wms_status;
        }
        
        public String getWms_order_type(){
            return wms_order_type;
        }
        
        public void setWms_order_type(String wms_order_type){
            this.wms_order_type = wms_order_type;
        }
        
        public String getRemark(){
            return remark;
        }
        
        public void setRemark(String remark){
            this.remark = remark;
        }
    }
       
}
