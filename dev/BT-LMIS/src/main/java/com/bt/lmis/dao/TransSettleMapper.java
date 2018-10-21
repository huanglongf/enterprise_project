package com.bt.lmis.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.bt.lmis.code.BaseMapper;

public interface TransSettleMapper<T> extends BaseMapper<T>  {
    //获取需要结算的承运商合同信息(视图)
	public ArrayList<Object>carriageContract(HashMap<String,String>param) throws RuntimeException;
    //获取需要结算的店铺合同信息(视图)
	public ArrayList<Object>storeContract(HashMap<String, String> param) throws RuntimeException;
	//获取需要结算的客户合同信息(视图)
	public ArrayList<Object>customerContract(HashMap<String, String> param) throws RuntimeException;
	//获取计费公式(视图)
	public HashMap<Object,Object>getFormula(HashMap<String, String> param) throws RuntimeException;
		
	
   //获取对应合同下需要结算的承运商的原始结算数据(视图)
	public ArrayList<Object>carriageSettleEveryDay(HashMap<String, String> param) throws RuntimeException;
   //获取对应合同下需要结算的店铺的原始结算数据(视图)
	public ArrayList<Object>storeSettleEveryDay(HashMap<String, String> param) throws RuntimeException;	
	//获取对应合同下需要结算的客户的原始结算数据(视图)
	public ArrayList<Object>customerSettleEveryDay(HashMap<String, String> param) throws RuntimeException;	
	//获取对应合同下需要结算的商品类型的合同下的原始结算数据
	public ArrayList<Object>getSettleDataForGt(HashMap<String, String> param) throws RuntimeException;
	
	
	//抛重(过程)
	public HashMap<Object,Object>carriageSettlePz(HashMap<Object,Object>param) throws RuntimeException;
	//续重(过程)
	public HashMap<Object,Object>carriageSettleXz(HashMap<Object,Object>param) throws RuntimeException;
	//公斤(过程)
	public HashMap<Object,Object>carriageSettleGj(HashMap<Object,Object>param) throws RuntimeException;
	//商品类型(过程)
	public HashMap<Object,Object>carriageSettleGt(HashMap<Object, Object> set_detail) throws RuntimeException;
	
//=============================================整车，零担==================================================================
	//保价费(过程)
	public HashMap<String,String>carriageSettleBj(HashMap<Object, Object> bj_param) throws RuntimeException;
	//管理费(过程)
	public HashMap<String,String>carriageSettleGl(HashMap<Object, Object> gl_param) throws RuntimeException;
	//特殊服务费(过程)
	public HashMap<String,String>carriageSettleTf(HashMap<String,String>param) throws RuntimeException;
//====================================================================================================================
	
//=============================================商品类型===================================================================
	//保价费(过程)
	public HashMap<String,String>carriageSettleBjGt(HashMap<Object, Object> bj_param) throws RuntimeException;
	//管理费(过程)
	public HashMap<String,String>carriageSettleGlGt(HashMap<Object, Object> gl_param) throws RuntimeException;
	//特殊服务费(过程)
	public HashMap<String,String>carriageSettleTfGt(HashMap<String,String>param) throws RuntimeException;
//======================================================================================================================	
	
	
	//更新结算标记 
    //1: 结算明细是否可以进行汇总 
	//2: 原始数据是否明细结算成功
	//3: 错误信息记录
	public void updateSettleInfo(HashMap<Object,Object>pram)throws RuntimeException;
	public void updateLogisInfo(HashMap<Object,Object>pram)throws RuntimeException;
	public void insertResultInfo(HashMap<Object,Object>param)throws RuntimeException;

	
	//承运商费用汇总(过程)
	public HashMap<String,String>transPool(HashMap<String,String>param) throws RuntimeException;
	//客户费用汇总(过程)
	public HashMap<String,String>customerPool(HashMap<String,String>param) throws RuntimeException;
	//店铺费用汇总(过程)
	public HashMap<String,String>storePool(HashMap<String,String>param) throws RuntimeException;

}
