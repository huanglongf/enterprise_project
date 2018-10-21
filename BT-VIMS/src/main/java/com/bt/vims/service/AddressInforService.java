package com.bt.vims.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.bt.vims.model.AddressInfor;
import com.bt.vims.page.QueryParameter;
import com.bt.vims.page.QueryResult;

/**
 * 地址信息业务类
 * @author liuqingqiang
 * @date 2018-05-09
 * @version V1.0
 */
@Service
public interface AddressInforService {
	
	/**
	 * 查询库中所有的地址信息
	 * @param queryParam (分页参数)
	 * @return （所有地址信息实例集合）
	 */
	public <T> QueryResult<T> findAllAddressInfor(QueryParameter qr);
	
	/**
	 * 根据具体的地址信息ID查找一条信息
	 * @param id （查找具体某条信息id）
	 * @return （地址信息实体对象）
	 */
	public AddressInfor findSingleAddressInfor(Integer id); 
	
	/**
	 * 登录页面上的所有地址信息查询
	 * @return （地址信息集合）
	 */
	public List<AddressInfor> getAllAddressInfor(); 
	
	/**
	 * 修改地址信息
	 * @param id （修改的数据id）
	 * @param newInfor （新信息实例）
	 * @param request
	 */
	public void updateAddressInfor(String id, AddressInfor newInfor, HttpServletRequest request);
	
	/**
	 * 新增地址信息
	 * @param addressInfor （地址信息实例）
	 * @param request （http参数）
	 */
	public void insertAddressInfor(AddressInfor addressInfor,  HttpServletRequest request);
	
	/**
	 * 删除地址信息（逻辑删除）
	 * @param idArray （所勾选删除id数组）
	 */
	public void logicDeleteAddressInfor(String[] idArray, HttpServletRequest request);
	
	/**
	 * 删除地址信息（逻辑删除）
	 * @param idArray （所勾选删除id数组）
	 */
	public void phyDeleteAddressInfor(String[] idArray);
	
	/**
	 * 验证地址编码不重复
	 * @param addressCode （新增地址编码）
	 * @return
	 */
	public boolean validateCode(String addressCode);
	
	/**
	 * 根据地址名称查询地址编码
	 * @param addressName
	 * @return
	 */
	public AddressInfor findDataByName(String addressName);

}
