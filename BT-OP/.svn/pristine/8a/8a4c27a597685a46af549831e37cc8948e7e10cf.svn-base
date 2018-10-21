package com.bt.orderPlatform.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.WaybillZdMapper;
import com.bt.orderPlatform.service.WaybillZdService;
/**
 * 
 * <b>类名：</b>WaybillZdServiceImpl.java<br>
 * @author <font color='blue'>chenkun</font> 
 * @date  2018年3月23日 上午11:13:57
 * @Description
 * @param <WaybillZd>
 */
@Service
public class WaybillZdServiceImpl<WaybillZd> implements WaybillZdService<WaybillZd> {

	@Autowired
    private WaybillZdMapper<WaybillZd> zdMapper;

    @Override
    public int insert(List<WaybillZd> zds){
      if(zds!=null&&!zds.isEmpty()){
          int i = 0;
          for (WaybillZd waybillZd : zds){
              i =i + insert(waybillZd);
          }
          return i;
      }
      return 0;  
    }

    @Override
    public int insert(WaybillZd zd){
        return zdMapper.insert(zd);
    }

    @Override
    public List<WaybillZd> selectByOrderId(String orderId){
        return zdMapper.selectByOrderId(orderId);
    }

}
