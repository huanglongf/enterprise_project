package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiAsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnDefault;

@Transactional
public interface VmiAsnDao extends GenericEntityDao<VmiAsnDefault, Long> {

    @NativeQuery
    VmiAsnCommand findVmiAsnByuuid(@QueryParam("uuid") String uuid, RowMapper<VmiAsnCommand> rowMapper);


    @NativeQuery
    List<VmiAsnCommand> findVmiAsnByOrder(@QueryParam("vmicode") String vmicode, @QueryParam("orderCode") String orderCode, RowMapper<VmiAsnCommand> rowMapper);

    @NativeQuery
    List<String> findVmiAsnByType1(@QueryParam("vmicode") String vmicode, @QueryParam("vmisource") String vmisource, SingleColumnRowMapper<String> r);

    @NativeQuery
    List<String> findVmiAsnByType2(@QueryParam("vmicode") String vmicode, @QueryParam("vmisource") String vmisource, SingleColumnRowMapper<String> r);

    @NativeQuery
    List<VmiAsnCommand> findVmiAsnList(@QueryParam("vmicode") String vmicode, @QueryParam("vmisource") String vmisource, RowMapper<VmiAsnCommand> rowMapper);

    @NativeQuery
    List<VmiAsnCommand> findVmiAsnAll(@QueryParam("vmicode") String vmicode, @QueryParam("vmisource") String vmisource, RowMapper<VmiAsnCommand> rowMapper);

    @NativeQuery
    List<VmiAsnCommand> findVmiAsnStoreCodeVmiSource(RowMapper<VmiAsnCommand> rowMapper);

    @NativeUpdate
    void updateVmiAsnStatus(@QueryParam("id") Long id, @QueryParam("status") Integer status);

    @NativeQuery
    List<VmiAsnCommand> findVmiAsnErrorAll(RowMapper<VmiAsnCommand> rowMapper);

    @NativeQuery
    Integer findVmiAsnHasFinishedCount(@QueryParam("vmicode") String vmicode, @QueryParam("orderCode") String orderCode, SingleColumnRowMapper<Integer> r);

    @NativeQuery
    List<String> findGucciVmiAsnNoSku(@QueryParam("vmicode") String vmicode, SingleColumnRowMapper<String> singleColumnRowMapper);
}
