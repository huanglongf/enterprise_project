package com.bt.orderPlatform.model.sfIntraCity;

public class IntraCityResultMsg<T>{
    
    private Integer error_code;
    
    private String error_msg;
    
    private T result;

    
    public int getError_code(){
        return error_code;
    }

    
    public void setError_code(Integer error_code){
        this.error_code = error_code;
    }

    
    public String getError_msg(){
        return error_msg;
    }

    
    public void setError_msg(String error_msg){
        this.error_msg = error_msg;
    }

    
    public T getResult(){
        return result;
    }


    
    public void setResult(T result){
        this.result = result;
    }


    public boolean isSuccess(){
        if(error_code!=null&&error_code.equals(0)){
            return true;  
        }
        return false;  
    }


    @Override
    public String toString(){
        return "IntraCityResultMsg [error_code=" + error_code + ", error_msg=" + error_msg + ", result=" + result+ "]";
    } 
 
}
