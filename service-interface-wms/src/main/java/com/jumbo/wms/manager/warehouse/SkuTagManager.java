package com.jumbo.wms.manager.warehouse;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.trans.SkuTag;
import com.jumbo.wms.model.trans.SkuTagCommand;

/**
 * 商品标签
 * @author fxl
 *
 */
public interface SkuTagManager extends BaseManager{
	/**
	 * 查询商品标签列表
	 * @param start
	 * @param pageSize
	 * @param tag
	 * @param sorts
	 * @return
	 */
	Pagination<SkuTagCommand> findSkuTag(int start,int pageSize,SkuTag tag,Sort [] sorts);
	/**
	 * 分页查询商品标签列表
	 *@param tag
	 *@param start
	 *@param pageSize
	 *@param sorts
	 *@return Pagination<SkuTagCommand> 
	 *@throws
	 */
	Pagination<SkuTagCommand> findSkuTagByPagination(SkuTag tag,Integer status,int start,int pageSize,Sort [] sorts);
	/**
	 * 根据商品标签编码查询是否存在
	 *@param tag
	 *@return boolean 
	 *@throws
	 */
	boolean findSkuTagExistByCode(SkuTag tag);
	/**
	 * 保存商品标签
	 *@param tag
	 *@param status
	 *@param type void 
	 *@throws
	 */
	void saveSkuTag(SkuTag tag, Integer status, Integer type);
	/**
	 * 根据商品标签id查询商品
	 *@param tag
	 *@param skuCmd
	 *@param start
	 *@param pageSize
	 *@param sorts
	 *@return Pagination<SkuCommand> 
	 *@throws
	 */
	Pagination<SkuCommand> findAllSkuByTagId(SkuTag tag, int start,int pageSize,Sort [] sorts);
	/**
     * 根据条件查询商品
     *@param tag
     *@param skuCmd
     *@param start
     *@param pageSize
     *@param sorts
     *@return Pagination<SkuCommand> 
     *@throws
     */
    Pagination<SkuCommand> findAllSkuRef(SkuTag tag, SkuCommand skuCmd, int start,int pageSize,Sort [] sorts);
    /**
     * 根据标签编码查询标签
     *@param tagCode
     *@return SkuTag 
     *@throws
     */
    SkuTag findSkuTagByCode(String tagCode);
}
