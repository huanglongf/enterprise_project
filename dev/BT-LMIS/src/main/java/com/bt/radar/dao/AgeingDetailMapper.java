package com.bt.radar.dao;

import java.util.List;
import java.util.Map;

import com.bt.radar.controller.form.AgeingDetailQueryParam;
import com.bt.radar.model.AgeingDetail;
import org.apache.ibatis.annotations.Param;
import com.bt.radar.model.AgeingDetailBackups;

public interface AgeingDetailMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(AgeingDetailQueryParam record);

    int insertSelective(AgeingDetailQueryParam record);

    AgeingDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgeingDetailQueryParam record);

    int updateByPrimaryKey(AgeingDetailQueryParam record);

    List<AgeingDetail> queryAgeingDetail(AgeingDetailQueryParam queryParam);

	int countAllAgeingDetail(AgeingDetailQueryParam queryParam);

	/**
     * 根据id批量删除
     * */
	int delBatchAgeingDetailById(@Param("idList") List<Integer> idList);

    /**
     * 根据时效id批量删除
     * */
    int delBatchAgeingDetailByAgeingId(@Param("ageingIdList") List<String> ageingIdList);

    /**
     * 根据时效id查询明细
     * */
    List<AgeingDetail> queryByAgeingIds(@Param("ageingIdList") List<String> ageingIdList);

	void insertAgeingDetailBackups(AgeingDetailBackups ageingDetailBackups);

	AgeingDetail selectByAgeingDetailBackups(AgeingDetailBackups ageingDetailBackups2);

	int insertAgeingDetailQueryParam(AgeingDetailQueryParam queryParam);

	/**
	 * 根据id查询非当前id下明细信息相符的明细信息
	 */
	AgeingDetail selectByAgeingDetailQueryParam(AgeingDetailQueryParam queryParam);

	int updateAgeingDetailQueryParam(AgeingDetailQueryParam queryParam);

	List<Map<String, Object>> selectAgeingDetailQueryParam(AgeingDetailQueryParam queryParam);

	void insertTemp(@Param("dataList")List<String[]> list, @Param("uid")String uid,@Param("username")String username,@Param("ageId")String ageId,@Param("store")String store);

	void checkWare(@Param("batid")String batId);

	void checkAddress(@Param("batid")String batId);

	void checkExpress(@Param("batid")String batId);

	void checkProductType(@Param("batid")String batId);

	void checkUnique(@Param("batid")String batId);

	List<?> checkUniqueOther(@Param("batid")String batId);

	void UpdateUniqueOther(List<?> listId);

	void insertDetail(@Param("batid")String batId);
}