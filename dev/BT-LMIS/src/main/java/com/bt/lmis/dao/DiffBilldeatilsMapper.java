package com.bt.lmis.dao;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.ExpressbillMaster;

/**
* @ClassName: DiffBilldeatilsMapper
* @Description: TODO(DiffBilldeatilsMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface DiffBilldeatilsMapper<T> extends BaseMapper<T> {

	List<DiffBilldeatils> selectMasterId(DiffBilldeatilsQueryParam queryParam);

	int Count(DiffBilldeatilsQueryParam queryParam);
	
	void deleteDiff(List<String[]> sflist);

	void insertDiff(List<String[]> sflist);

	void verification(DiffBilldeatilsQueryParam param);

	void verificationIds(@Param("ids")List<String> ids);
	
	void verificationIdsSf(@Param("ids")List<String> ids);
	
	DiffBilldeatils selectById(int id);

	List<DiffBilldeatils> selectAll();
	
	void Cancelverification(DiffBilldeatilsQueryParam param);

	void CancelverificationIds(@Param("ids")List<String> ids);

	void gerAccount(DiffBilldeatilsQueryParam param);

	int checkAccountById(@Param("ids")List<String> ids);

	void upaccount(@Param("master_id")String ids,@Param("user")String user,@Param("discount")String discount,@Param("is_verification")String is_verification);
	
	void deleteaccount(@Param("ids")String ids,@Param("user")String user,@Param("is_verification")String is_verification);
	
	void genAccountIds(@Param("ids")String ids,@Param("account_id")String account_id,@Param("user")String user,@Param("master_id")String master_id);

	void insertReDiffParam(DiffBilldeatilsQueryParam param);

	void deleteExpressbillDetail(DiffBilldeatilsQueryParam param);

	void insertDiffBilldeatilsTemp(@Param("account_id")String uuid, @Param("id")String id);

	void insertDiffBilldeatilsTempByQueryParam(@Param("account_id")String account_id,@Param("pp") DiffBilldeatilsQueryParam param);

	void updateDiffBilldeatilsTemp(@Param("account_id")String uuid, @Param("discount")String str);

	void updateDiffBilldeatilsTempSF(@Param("account_id")String uuid, @Param("producttype_code")String procude_code, @Param("discount")BigDecimal discount1);

	void deleteDiffBilldeatilsTempByAccountId(@Param("account_id")String uuid);

	void updateDiffBilldeatils(@Param("account_id")String account_id,@Param("master_id")String master_id, @Param("discount")String str);

	void updateDiffBilldeatilsSF(@Param("account_id")String account_id,@Param("master_id")String master_id, @Param("producttype_code")String procude_code,  @Param("discount")BigDecimal discount1);

	List<Map<String, Object>> selectExpressbillMasterhxByAccountId(@Param("account_id")String account_id);

	List<Map<String, Object>> checkToDiff(DiffBilldeatilsQueryParam param);

	void updateDiff(List<String[]> sflist);
	
	List<Map<String,Object>>uploadDetails(DiffBilldeatilsQueryParam param);

	List<Map<String, Object>> uploadDetailsIds(@Param("ids")String ids1);

	void deleteByQuery(DiffBilldeatilsQueryParam dq);

	List<Map<String, Object>> getMonthAccount();

	void sFverification(DiffBilldeatilsQueryParam param);

	void verification1(DiffBilldeatilsQueryParam param);

	void updateDiffBackups(ExpressbillMaster master);

	void bf_settle(DiffBilldeatils detail);

	void bf_return(DiffBilldeatils detail);
	Map<String,Object>getMinMaxId(DiffBilldeatilsQueryParam param);
}
