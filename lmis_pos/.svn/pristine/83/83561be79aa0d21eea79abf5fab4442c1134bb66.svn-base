package com.lmis.pos.whs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.whs.model.PosWhs;

/** 
 * @ClassName: PosWhsMapper
 * @Description: TODO(仓库主表DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-29 13:23:45
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsMapper<T extends PosWhs> extends BaseMapper<T> {
    
    /**
     * @Title: getWhsDepot
     * @Description: TODO(获取需要分仓的有效仓库)
     * @param type
     * @param isSaleRate
     * @return: List<PosWhs>
     * @author: Ian.Huang
     * @date: 2018年6月22日 下午7:03:52
     */
    List<PosWhs> getWhsDepot(@Param("type") String type, @Param("isSaleRate") String isSaleRate);
    
    List<PosWhs> getWhsDepotByTypeL(@Param("type")String poType, @Param("vnumber")String vNumber, @Param("size")String prodSize);
	List<PosWhs> getWhsDepotByTypeF(@Param("type")String poType, @Param("skuCode")String skuCode);
    
    /**
     * 获取尾仓
     * @author xuyisu
     * @date  2018年5月31日
     * 
     * @return
     */
    PosWhs getWhsDepotTailGoods();
    
    int countCodeNameUpd(T pw);
    
    /**
     * 
     * <b>方法名：</b>：queryWhsToSetSaleRate<br>
     * <b>功能说明：</b>：查询需要设置销售占比的区域仓库<br>
     * @author <font color='blue'>chenkun</font> 
     * @date  2018年6月13日 上午10:21:46
     * @return
     */
    List<PosWhs> queryWhsToSetSaleRate();
    
    /***
     * 
     * <b>方法名：</b>：queryCountWhsToSetSaleRate<br>
     * <b>功能说明：</b>：查询需要设置销售占比的区域仓库数量<br>
     * @author <font color='blue'>chenkun</font> 
     * @date  2018年6月13日 上午10:21:52
     * @return
     */
    Integer  queryCountWhsToSetSaleRate();
    
}
