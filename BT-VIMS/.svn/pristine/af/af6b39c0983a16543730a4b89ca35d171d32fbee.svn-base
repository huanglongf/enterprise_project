package com.bt.vims.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bt.vims.model.VimsData;

/**
 * 读取访客信息仓库
 * @author liuqingqiang
 * @date 2018-05-11
 * @version V1.0
 *
 */
@Repository
public interface VimsDataMapper{
    
    /**
     *将客户端的访客数据插入数据表中 
     * @param vimsDatas 访客数据信息集合
     * @return 插入数据id
     */
    public int create(List<VimsData> vimsDatas);
    
    
    /**
     * 
     * @param map（id参数）
     * @return 访客数据信息
     */
    public VimsData queryById(Map<String, Object> map);

}
