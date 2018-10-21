/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhTransProvideNo;

@Transactional
public interface WhTransProvideNoDao extends GenericEntityDao<WhTransProvideNo, Long> {
    /**
     * 根据物流商获取随机一条数据
     */
    @NativeQuery
    String getTranNoByLpcode(@QueryParam("lpcode") String lpcode, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    String getTranNoByLpcodeSf(@QueryParam("lpcode") String lpcode, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询母单号
     */
    @NativeQuery
    String getTranNoByLpcodeSfMu(@QueryParam("lpcode") String lpcode, SingleColumnRowMapper<String> singleColumnRowMapper);


    @NativeQuery
    String getTranNoByLpcodeJcustidSf(@QueryParam("lpcode") String lpcode, @QueryParam("jcustId") String jcustId, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询母单号
     * 
     * @param lpcode
     * @param jcustId
     * @param singleColumnRowMapper
     * @return
     */

    @NativeQuery
    String getTranNoByLpcodeJcustidSfMu(@QueryParam("lpcode") String lpcode, @QueryParam("jcustId") String jcustId, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据物流商获取随机一条数据
     */
    @NativeQuery
    String getJdTranNoByLpcode(@QueryParam("lpcode") String lpcode, @QueryParam("owner") String owner, @QueryParam("isCod") Integer isCod, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据物流商获取随机一条数据
     */
    @NativeQuery
    Long getTranNoByLpcodeList(SingleColumnRowMapper<Long> r);

    /**
     * 获取可用的物流单号数量
     */
    @NativeQuery
    Long getTranNoNumberByLpCode(@QueryParam("lpcode") String lpcode, @QueryParam("isCod") Boolean isCod, SingleColumnRowMapper<Long> r);

    @NativeQuery
    Long getTranNoNumberByLpCodeAndAccount(@QueryParam("lpcode") String lpcode, @QueryParam("isCod") Boolean isCod, @QueryParam("account") String account, SingleColumnRowMapper<Long> r);

    @NativeQuery
    Long getJdTranNoByLpcodeList(@QueryParam("jd") String jd, @QueryParam("owner") String owner, SingleColumnRowMapper<Long> r);

    @NativeQuery
    List<Long> getJdStaIdByLpcodeList(@QueryParam("jd") String jd, @QueryParam("owner") String owner, SingleColumnRowMapper<Long> r);

    @NativeQuery
    List<String> getJdOwnerByLpcodeList(SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    Long getStoTranNoNum(SingleColumnRowMapper<Long> r);

    /**
     * 根据TranNo获取WhTransProvideNo实体
     * 
     * @param ouid
     * @return
     */
    @NamedQuery
    WhTransProvideNo getByTranNo(@QueryParam("transno") String tranno);

    /**
     * 根据TranNo获取WhTransProvideNo实体
     * 
     * @param ouid
     * @return
     */
    @NamedQuery
    WhTransProvideNo getJdByTranNo(@QueryParam("transno") String tranno);

    /**
     * 查询最后申请的trans_no
     * 
     * @param
     * @return String
     */
    @NativeQuery
    String getLastTransNo(SingleColumnRowMapper<String> singleColumnRowMapper);

    // 查询取消的订单占用的WhTransProvideNo

    @NativeQuery
    List<Long> getCancelStaTransNo(SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NamedQuery
    WhTransProvideNo getByTranNoAndStaIdAndLpcode(@QueryParam("transno") String transno, @QueryParam("staid") Long staid, @QueryParam("lpcode") String lpcode);

    /**
     * 根据物流商获取随机一条数据
     */
    @NativeQuery
    String getEmsTranNoByLpcode(@QueryParam("lpcode") String lpcode, @QueryParam("owner") String owner, @QueryParam("isCod") Integer isCod, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据物流商获取随机一条数据andEMS账号
     */
    @NativeQuery
    String getEmsTranNoByLpcodeAndAccount(@QueryParam("lpcode") String lpcode, @QueryParam("owner") String owner, @QueryParam("isCod") Integer isCod, @QueryParam("account") String account, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询没有被匹配的运单号的第一个运单号
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    String getTopOneTransNo(SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 占用运单号
     */
    @NativeUpdate
    void occupationWhTransProwideNoTransNo(@QueryParam("transNo") String transNo, @QueryParam("staId") Long staId);

    /**
     * 根据物流商和区域编码获得运单号数量
     * 
     * @param lpcode
     * @param regionCode
     * @param r
     * @return
     */
    @NativeQuery
    Long getTranNoNumberByLpCodeAndRegionCode(@QueryParam("lpcode") String lpcode, @QueryParam("regionCode") String regionCode, SingleColumnRowMapper<Long> r);

    /**
     * 根据物流商获取随机一条数据
     * 
     * @return
     */
    @NativeQuery
    String getTranNoByLpcodeAndRegionCode(@QueryParam("lpcode") String lpcode, @QueryParam("regionCode") String regionCode, SingleColumnRowMapper<String> singleColumnRowMapper);
    
    /**
     * 根据物流商获取随机一条数据yamato单号
     */
    @NativeQuery
    WhTransProvideNo getYamatoTranNoByLpcodeAndAccount(@QueryParam("lpcode") String lpcode, @QueryParam("owner") String owner, @QueryParam("isCod") Integer isCod,RowMapper<WhTransProvideNo> rowMapper);

}
