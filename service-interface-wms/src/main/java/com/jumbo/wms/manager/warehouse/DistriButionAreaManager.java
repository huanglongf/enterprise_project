package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.util.List;


import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.AreaType;
import com.jumbo.wms.model.warehouse.BingdingDetai;
import com.jumbo.wms.model.warehouse.DistriButionArea;
import com.jumbo.wms.model.warehouse.DistriButionAreaLoc;
import com.jumbo.wms.model.warehouse.DwhDistriButionAreaLocCommand;
import com.jumbo.wms.model.warehouse.TransactionType;

import loxia.dao.Page;
import loxia.dao.Pagination;
import loxia.support.excel.ReadStatus;
/***
 * 
 * @author lijinggong+2018年7月26日
 *
 *
 */
public interface  DistriButionAreaManager extends BaseManager {
	
	public Pagination<DistriButionArea> getDistriButionArea(Long mainWhid,Page page);
	
	/**
	 * 根据分配区域编码和分配区域名称进行模糊查询
	 * 
	 */
    public Pagination<DistriButionArea> findDistriButionArea(Long mainWhid,Page page,String distriButionAreaCode,String distriButionAreaName);
    //增加数据
    public Integer insertDistriButionArea(String distriButionAreaCode,String distriButionAreaName,Long mainWhid, Long createId, String createUser);
    
    
    
    //修改
    public Integer updateDistriButionArea(Long mainWhid,Long id,String distriButionAreaCode,String distriButionAreaName);
    
    //删除
    public Integer deleteDistriButionArea(Long id);
    
    
    //取得判断条件
    public String judgeDistriButionArea(String distriButionAreaCode);
    
    
    /* 区域绑定库位
     */
    //分页查询区域绑定库位
    public Pagination<DistriButionAreaLoc> getDistriButionAreaLoc(Long mainWhid,Page page);
    
    public Pagination<DistriButionAreaLoc> findDistriButionAreaLoc(Long mainWhid,Page page,String locCodeName,String locCode,String locDistriButionAreaCode,String locDistriButionAreaName);
    
    //删除
    public Integer deleteDistriButionAreaLoc(Long id);
    //查询库区
    public  List<DistriButionAreaLoc> getName();
    //查询分配区域名称
    public List<DistriButionAreaLoc> getDistriButionNameList(Long mainWhid);
    //查询分配区域编码
    public List<DistriButionAreaLoc> getDistriButionCodeList(Long mainWhid);
    
    /* 区域绑定作业类型
	*/
    public Pagination<AreaType> getDistriButionAreaType(Long mainWhid,Page page);
    
    public Pagination<AreaType> findDistriButionAreaType(Long mainWhid,Page page,String typeDistriButionAreaCode,String typeDistriButionAreaName);
    
    
   //作业类型
    public Pagination<TransactionType> getTransActionType(Long mainWhid,Page page);
    
    //区域绑定作用类型
    public Integer bindingTransAction(String distriButionAreaIds,String transActionTypeIds);
    
    //区域绑定作用类型
    public Integer cancelBinding(String distriButionAreaIds,String transActionTypeIds);
    
    //根据id查询已绑定的详细信息
    public Pagination<BingdingDetai> findBingdingDetai(Long mainWhid,Page page,Long bingdingDetaiId);
    
    
    
    //导入区域类型绑定库位
    public  ReadStatus saveDwhDistriButionAreaLocImport(Long mainWhid,File file, OperationUnit op) throws Exception;
    
    //导出库位
    public List<DwhDistriButionAreaLocCommand> exportDistriButionArea(Long mainWhid,String locCodeName,String locCode,String locDistriButionAreaCode,String locDistriButionAreaName);
}
