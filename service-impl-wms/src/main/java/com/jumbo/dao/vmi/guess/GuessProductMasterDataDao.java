package com.jumbo.dao.vmi.guess;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.guess.GuessProductMasterData;
@Transactional
public interface GuessProductMasterDataDao extends GenericEntityDao<GuessProductMasterData, Long>{
    @NativeQuery
    SkuCommand findGuessProductByUpc(@QueryParam("shortsku") String upc,RowMapper<SkuCommand> rowMapper);
    @NamedQuery
     List<GuessProductMasterData> guessProductMasterDataList();
}
