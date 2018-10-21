package com.lmis.pos.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.pos.whsSurplusSc.model.PosWhsSurplusSc;

@Mapper
@Repository
public interface PosWhsInventoryMapper{
        List<Map<String,String>> inventoryCount(@Param("list")List<Map<String, String>> list,@Param("startTime")String time);

        void insertWhsInventory(@Param("list")List<Map<String, String>> inventoryCount);

        boolean updateScInit(Map<String, String> map);

        List<Map<String, String>> selectInventory();

        List<Map<String, String>> findWhs();
        
        void insertSurplus(Map<String, String> map);

        List<Map> findSkuType();
        
        void updateSurplus(Map<String, String> map);
        
        void updateEnable(Map<String, String> map);
        
        List<Map<String,String>> selectEnablePlus();

        void updateEnablePlus(Map<String, String> map);

        void updateinJobsEnable();

        Map<String,String> selectByCode(Map<String, String> map);

        Map<String, String> updateFinalInventoryByDate(Map<String, String> map);

        Map<String, String> selectFinalInventoryByDate(Map<String, String> map);

        void updateScInitByCodeAndBu(Map<String, String> map);

        void deleteTodaysTemp();

        List<String> selectDateArray();

        String selectDateMin();

        void insertDateToTemp(@Param("list")List<Map<String,String>> l);

        void deleteDateTemp();

        void updateSc(String date);

        void updateScInEnable(Map<String, String> selectFinalInventoryByDate);

        List<PosWhsSurplusSc> selectScGroupCodeAndBu();

        void updateScIntVal();

        List<PosWhsSurplusSc> selectScByCodeOrderSchedule(PosWhsSurplusSc sc);

        void InsertScIntVal(List<PosWhsSurplusSc> subList);

        void deleteScTemp();

        void updatestorageBuType(String startTime);

        List selectFtpDownLoad(Map downloadmap);

        List selectWhsSurplusSign(Map downloadmap);

        void insertWhsSurplusSign();

        void updatePlanInPo();
        
        int updateWhsSurplusSign(@Param("date")String date);


}
