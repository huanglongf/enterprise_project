package com.jumbo.dao.invflow;

import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.invflow.WmsIMOccupiedAndRelease;
import com.jumbo.wms.model.invflow.WmsOccupiedAndRelease;

@Transactional
public interface WmsIMOccupiedAndReleaseDao extends GenericEntityDao<WmsIMOccupiedAndRelease, Long> {
    /**
     * 根据时间段查询占用或释放记录
     * 
     * @param systemKey
     * @param customerCode
     * @param owner
     * @param type
     * @param startTime
     * @return endTime
     */
    @NativeQuery(pagable = true)
    Pagination<WmsOccupiedAndRelease> getWmsIMOccupiedAndRelease(int start, int pagesize, @QueryParam("systemKey") String systemKey, @QueryParam("customerCode") String customerCode, @QueryParam("owner") String owner, @QueryParam("type") Integer type,
            @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, BeanPropertyRowMapper<WmsOccupiedAndRelease> beanPropertyRowMapper);

    /**
     * 转移过期数据
     * 
     * @param date
     * @param status
     */
    @NativeUpdate
    void transferExpireData(@QueryParam("date") String date, @QueryParam("status") Integer status);

    /**
     * 删除过期数据
     * 
     * @param date
     * @param status
     */
    @NativeUpdate
    void removeExpireData(@QueryParam("date") String date, @QueryParam("status") Integer status);

    @NamedQuery
    List<WmsIMOccupiedAndRelease> getWmsIMOccupiedAndReleaseByStaCodeAndType(@QueryParam("staCode") String staCode, @QueryParam("occType") Integer occType);



    /**
     * 查询未推送数量
     * 
     * @param status
     */
    @NativeQuery
    Long getUnpushedOccupiedAndRelease(@QueryParam("status") Integer status, SingleColumnRowMapper<Long> r);

    /**
     * 根据状态查询占用或释放记录
     * 
     * @param start
     * @param pagesize
     * @param status
     */
    @NativeQuery(pagable = true)
    Pagination<WmsOccupiedAndRelease> getOccupiedAndReleaseByStatus(int start, int pagesize, @QueryParam("status") Integer status, BeanPropertyRowMapper<WmsOccupiedAndRelease> beanPropertyRowMapper);

    /**
     * 根据状态和类型查询占用或释放记录
     * 
     * @param start
     * @param pagesize
     * @param status
     */
    @NativeQuery(pagable = true)
    Pagination<WmsOccupiedAndRelease> getOccupiedAndReleaseByStatusAndType(int start, int pagesize, @QueryParam("status") Integer status, @QueryParam("type") Integer type, BeanPropertyRowMapper<WmsOccupiedAndRelease> beanPropertyRowMapper);

    /**
     * 更新推送数据状态
     * 
     * @param date
     */
    @NativeUpdate
    void updateOccupiedAndReleaseStatus(@QueryParam("ids") List<Long> ids);

    /**
     * 更新推送数据错误次数
     * 
     * @param date
     */
    @NativeUpdate
    void updateIMOccupiedAndReleaseErrorCount(@QueryParam("ids") List<Long> ids);

    @NativeUpdate
    void deleteIMByOwnerAndouId(@QueryParam("owners") String owner, @QueryParam("ouNames") List<String> ouNames);

    @NativeQuery
    Boolean findWmsOccupiedAndRelease(@QueryParam("owners") String owner, @QueryParam("ouNames") List<String> ouName, SingleColumnRowMapper<Boolean> r);

    @NativeUpdate
    void insertWmsOccupiedAndRelease(@QueryParam("owners") String owner, @QueryParam("ouNames") List<String> ouNames);

    /**
     * 查询推送失败数量
     * 
     * @param status
     */
    @NativeQuery
    Long getUnpushedOccupiedAndReleaseError(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 插入盘点亏损数据 WMS经理确认
     * 
     * @param invCheckId
     * @param invCheckCode
     */
    @NativeUpdate
    void insertWmsOccupiedAndReleaseByInvCheckLoss(@QueryParam("invCheckId") Long invCheckId, @QueryParam("invCheckCode") String invCheckCode);

    /**
     * PACS取消盘点 插入盘点亏损方向数据
     * 
     * @param invCheckId
     * @param invCheckCode
     */
    @NativeUpdate
    void insertWmsOccupiedAndReleaseByInvCheckLossCancel(@QueryParam("invCheckId") Long invCheckId, @QueryParam("invCheckCode") String invCheckCode);

}
