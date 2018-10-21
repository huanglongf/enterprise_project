package com.lmis.pos.common.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.DateUtils;
import com.lmis.pos.common.dao.PosWhsInventoryMapper;
import com.lmis.pos.whsSurplusSc.model.PosWhsSurplusSc;
@Transactional(rollbackFor=Exception.class)
@Service
public class InventoryScheduled{

    @Autowired
    private PosWhsInventoryMapper posWhsInventoryMapper; 

    @Autowired
    private HttpSession session;
        
    @SuppressWarnings({ "unchecked", "rawtypes" })
//    @Scheduled(cron = "0/1 * * * * ?")  
    @Scheduled(cron = "0 0/20 * * * ?")  
    public void timer() throws Exception{ 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        Date someDaysBeforeAfter = DateUtils.getSomeDaysBeforeAfter(new Date(), -1);
        Date date1 = new Date();
        String format = sdf.format(date1);
        Map downloadmap = new HashMap();
        downloadmap.put("remark", format);
        downloadmap.put("remark1", sdf1.format(someDaysBeforeAfter));
        downloadmap.put("flag", "1");
        List selectFtpDownLoad = posWhsInventoryMapper.selectFtpDownLoad(downloadmap);//查询是否拉取数据成功
        List selectWhsSurplusSign = posWhsInventoryMapper.selectWhsSurplusSign(downloadmap);//查询是否已经初始化库存
        if(selectFtpDownLoad.size()<1||selectWhsSurplusSign.size()>0){
            return;
        }
        
       
        posWhsInventoryMapper.deleteDateTemp();
        posWhsInventoryMapper.deleteScTemp();

        String date = posWhsInventoryMapper.selectDateMin();
       
        Date parse = sdf.parse(format);
        Date stringToDate = DateUtils.stringToDate(date);
        int days = DateUtils.getDays(parse, stringToDate);
        List<Map<String,String>> l = new ArrayList();

        for (int i = 0; i < days+1; i++) {
            Map map = new HashMap();
            map.put("dates", DateUtils.dateToString(parse, DateUtils.patternA));
            l.add(map);
            Date d = DateUtils.nextDate(parse);
            parse=d;
        }
        posWhsInventoryMapper.insertDateToTemp(l);//添加需要初始化的日期

        posWhsInventoryMapper.deleteTodaysTemp();
        List<Map<String, String>> findWhs = posWhsInventoryMapper.findWhs();//获取可分仓仓库

        String[] array = {"10","20","30"};

        Map m = new HashMap();
        for (int i = 0; i < array.length; i++){
            m.put("bu", array[i]);
            posWhsInventoryMapper.insertSurplus(m);//
        }

        posWhsInventoryMapper.updateSc(sdf.format(new Date()));
        downloadmap.put("flag", "0");
        List checkSign = posWhsInventoryMapper.selectWhsSurplusSign(downloadmap);//查询是否已经初始化库存
        if(checkSign.size()<1){
            posWhsInventoryMapper.updatestorageBuType(sdf.format(someDaysBeforeAfter));//刷bu  把今天  jk_settle_storage bu_type为null的刷成10 20 30
        }
        List<Map<String, String>> inventoryCount = posWhsInventoryMapper.inventoryCount(findWhs,sdf.format(someDaysBeforeAfter));//去lmis_pe  count  统计昨天库存
        for (int i = 0; i < inventoryCount.size(); i++){
            posWhsInventoryMapper.updateScInit(inventoryCount.get(i));//修改pos_whs_surplus_sc  sc_init  第一天初始库存
        }

        List<PosWhsSurplusSc> selectScGroupCodeAndBu = posWhsInventoryMapper.selectScGroupCodeAndBu();//获取生成了的pos_whs_surplus_sc 第一天数据

        for (int i = 0; i < selectScGroupCodeAndBu.size(); i++){

            //每个仓库类型绑定下的第一天的值
            PosWhsSurplusSc sc = selectScGroupCodeAndBu.get(i);
            if(sc.getScMax()==null) sc.setScMax(0);
            if(sc.getScInit()==null)sc.setScInit(0);
            if(sc.getPlannedOut()==null)sc.setPlannedOut(0);
            if(sc.getPlannedInPo()==null)sc.setPlannedInPo(0);
            if(sc.getInJobsMax()==null)sc.setInJobsMax(0);
            if(sc.getSurplus()==null)sc.setSurplus(0);
            if(sc.getInJobsEnable()==null)sc.setInJobsEnable(0);
            if(sc.getInEnable()==null)sc.setInEnable(0);
            if(sc.getFinalInventory()==null)sc.setFinalInventory(0);
            if(sc.getSurplusPlus()==null)sc.setSurplusPlus(0);
            if(sc.getInEnablePlus()==null)sc.setInEnablePlus(0);
                
            sc.setSurplus(sc.getScMax()-sc.getScInit()+sc.getPlannedOut()-sc.getPlannedInPo());
            sc.setInJobsEnable(sc.getInJobsMax()-sc.getPlannedInPo());
            sc.setInEnable((sc.getSurplus()>sc.getInJobsEnable()?sc.getInJobsEnable():sc.getSurplus()));
            sc.setFinalInventory(sc.getScInit()-sc.getPlannedOut()+sc.getPlannedInPo());
            sc.setSurplusPlus(sc.getScMax()-sc.getScInit()+sc.getPlannedOut()-sc.getPlannedInPo());
            sc.setInEnablePlus((sc.getSurplus()>sc.getInJobsEnable()?sc.getInJobsEnable():sc.getSurplus()));

            List<PosWhsSurplusSc> scList = posWhsInventoryMapper.selectScByCodeOrderSchedule(sc);//根据仓库 skutype  获取所有时间的数据 根据Schedule_date 排序
            scList.set(0, sc);
            //            List<PosWhsSurplusSc> updateSc = new ArrayList<PosWhsSurplusSc>();
            for(int j=0;j<scList.size()-1;j++){
                //修改第二天初始库存及期末库存
                PosWhsSurplusSc nextSc = scList.get(j+1);
                if(nextSc.getScMax()==null) nextSc.setScMax(0);
                if(nextSc.getScInit()==null)nextSc.setScInit(0);
                if(nextSc.getPlannedOut()==null)nextSc.setPlannedOut(0);
                if(nextSc.getPlannedInPo()==null)nextSc.setPlannedInPo(0);
                if(nextSc.getInJobsMax()==null)nextSc.setInJobsMax(0);
                if(nextSc.getSurplus()==null)nextSc.setSurplus(0);
                if(nextSc.getInJobsEnable()==null)nextSc.setInJobsEnable(0);
                if(nextSc.getInEnable()==null)nextSc.setInEnable(0);
                if(nextSc.getFinalInventory()==null)nextSc.setFinalInventory(0);
                if(nextSc.getSurplusPlus()==null)nextSc.setSurplusPlus(0);
                if(nextSc.getInEnablePlus()==null)nextSc.setInEnablePlus(0);
                //修改第二天的条件
                nextSc.setWhsCode(scList.get(j).getWhsCode());
                nextSc.setSkuType(scList.get(j).getSkuType());
                nextSc.setScheduleDate(DateUtils.formateDate(DateUtils.getSomeDaysBeforeAfter(new Date(), j+1)));
                nextSc.setDateId(scList.get(j).getDateId());

                //需要修改的值
                nextSc.setScInit(scList.get(j).getFinalInventory());
                nextSc.setInJobsEnable(nextSc.getInJobsMax()-nextSc.getPlannedInPo());
                nextSc.setSurplus(nextSc.getPlannedOut()-nextSc.getPlannedInPo());
                nextSc.setFinalInventory(scList.get(j).getFinalInventory()-nextSc.getPlannedOut()+nextSc.getPlannedInPo());
                
                nextSc.setSurplusPlus(nextSc.getScMax()-nextSc.getScInit()+nextSc.getPlannedOut()-nextSc.getPlannedInPo());
                nextSc.setInEnablePlus((nextSc.getSurplusPlus()>nextSc.getInJobsEnable()?nextSc.getInJobsEnable():nextSc.getSurplusPlus()));
                nextSc.setInEnable((nextSc.getSurplus()>nextSc.getInJobsEnable()?nextSc.getInJobsEnable():nextSc.getSurplus()));

            }
            for (int j = 0; j < scList.size(); j=j+50){
                if(j+50>scList.size()){
                    posWhsInventoryMapper.InsertScIntVal(scList.subList(j, scList.size()));
                }else{
                    posWhsInventoryMapper.InsertScIntVal(scList.subList(j, j+50)); 
                }
            }
            
        }
        posWhsInventoryMapper.updateScIntVal();//临时表导入正式表
        posWhsInventoryMapper.updatePlanInPo();//临时表导入正式表
        posWhsInventoryMapper.insertWhsSurplusSign();

    }


}
