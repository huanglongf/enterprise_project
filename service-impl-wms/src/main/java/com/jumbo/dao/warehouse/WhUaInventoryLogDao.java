package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.WhUaInventoryLog;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.dao.GenericEntityDao;

@Transactional
public interface WhUaInventoryLogDao extends GenericEntityDao<WhUaInventoryLog, Long> {

    @NativeUpdate
    void insertUaInventoryLog();

    @NativeUpdate
    void insertAfInventoryLog();

    @NativeUpdate
    void insertNikeNewInventoryLog();
    @NativeUpdate
    void insertNikeNewInventoryLog2();

    @NativeUpdate
    void insertNikeCrwInventoryLog();

    @NativeUpdate
    void insertConverseInventoryLog();

    @NativeUpdate
    void insertAeoInventoryLog();

    @NativeUpdate
    void insertAeoJDInventoryLog();

    @NativeUpdate
    void insertNewLookInventoryLog();

    @NativeUpdate
    void insertGuessInventoryLog();

    @NativeUpdate
    void insertGuessInventoryRetailLog();

    @NativeUpdate
    void insertUaNbaInventoryLog();

    @NativeUpdate
    void insertNikeInventoryLog();

    @NativeUpdate
    void insertNikeInventoryLogGZ();

    @NativeUpdate
    void insertNikeInventoryLogTM();

    @NativeUpdate
    void insertNikeInventoryLogGZTM();

    @NativeUpdate
    void insertNewLookJDInventoryLog();

    @NativeUpdate
    void insertIDSVSInventoryLog();

    @NativeQuery
    List<WhUaInventoryLog> findbyUaInventoryLog(RowMapper<WhUaInventoryLog> row);

    @NativeQuery
    List<WhUaInventoryLog> findbyUaInventoryLogByAll(RowMapper<WhUaInventoryLog> row);


    @NativeQuery
    List<WhUaInventoryLog> findUaInventoryLogByAll(RowMapper<WhUaInventoryLog> row);

    @NativeQuery
    List<WhUaInventoryLog> findUaInventoryLogByRe(RowMapper<WhUaInventoryLog> row);

}
