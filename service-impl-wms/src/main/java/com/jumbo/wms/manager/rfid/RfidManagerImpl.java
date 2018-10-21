package com.jumbo.wms.manager.rfid;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.rfid.SkuRfidDao;
import com.jumbo.dao.rfid.SkuRfidLogDao;
import com.jumbo.dao.system.SequenceCounterDao;
import com.jumbo.util.RfidFormat;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuRfid;
import com.jumbo.wms.model.baseinfo.SkuRfidLog;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import org.springframework.jdbc.core.SingleColumnRowMapper;

@Service("rfidManager")
public class RfidManagerImpl extends BaseManagerImpl implements RfidManager{
    /**
     *
     */
    private static final long serialVersionUID = -8526473672667261619L;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SkuRfidDao skuRfidDao;
    @Autowired
    private SkuRfidLogDao skuRfidLogDao;
    @Autowired
    private SequenceCounterDao sequenceCounterDao;


    @Override
    public Map<String,Object> saveRfidAndLog(String barCode, List<String> rfids) {
        Map<String,Object> result = new HashMap<>();
        for(String rfid:rfids){
            try{
                if(!RfidFormat.checkRfid(rfid,barCode)){
                    result.put("code","error");
                    result.put("msg","rfid："+rfid+" 非法");
                    return result;
                }
            }catch(Exception e){
                result.put("code","error");
                result.put("msg","barCode："+barCode+" 非法");
                return result;
            }

        }
        if(!RfidFormat.checkRfidSequence(rfids.get(0),rfids.get(1))){
            result.put("code","error");
            result.put("msg","左右脚序列号不一致");
            return result;
        }
        Sku sku= skuDao.getSkuByBarcode(barCode);
        if(sku==null){
            result.put("code","error");
            result.put("msg","无法查到sku");
        }else{
            Customer customer = sku.getCustomer();
            Long customerId = customer.getId();
            if(customerId==null){
                result.put("code","error");
                result.put("msg","无法查到仓库"); 
            }else{
                Sku skuRestult = skuDao.findSkuByBarCodeAndCustomerId(barCode, customerId, new BeanPropertyRowMapper<Sku>(Sku.class));
                if(skuRestult==null){
                    result.put("code","error");
                    result.put("msg","无法查到sku");
                }else{
                    if(!skuRestult.isRfid()){
                        result.put("code","error");
                        result.put("msg","非RFID商品");
                    }else {
                        UUID uuid=UUID.randomUUID();
                        String str = uuid.toString();
                        String rfidBatch = str.replace("-", "");
                        Date date = new Date();
                        for(String rfid:rfids){
                            //RFID记录
                            SkuRfid skuRfid = new SkuRfid();
                            skuRfid.setRfidCode(rfid);
                            skuRfid.setLastModifyTime(date);
                            skuRfid.setOuId(customer.getId());
                            skuRfid.setSkuId(sku.getId());
                            skuRfid.setStaId(null);
                            skuRfid.setVersion(0);
                            skuRfid.setRfidBatch(rfidBatch);
                            skuRfidDao.save(skuRfid);
                            // 日志记录
                            SkuRfidLog skuRfidLog = new SkuRfidLog();
                            skuRfidLog.setDirection(TransactionDirection.valueOf(1));
                            skuRfidLog.setSkuId(sku.getId());
                            skuRfidLog.setOuId(customer.getId());
                            skuRfidLog.setRfidCode(rfid);
                            skuRfidLog.setStaId(null);
                            skuRfidLog.setTransactionTime(date);
                            skuRfidLogDao.save(skuRfidLog);
                        }
                        result.put("code","success");
                        result.put("msg","保存成功");
                    }       
                }
                
            }
             
        }
        return result;
    }

    @Override
    public String createRfid(String barcode) {
        Long nextBySequence = sequenceCounterDao.getNextBySequence(" S_SKU_RFID_SEQUENCE", new SingleColumnRowMapper<Long>(Long.class));
        return RfidFormat.createRfid(barcode,nextBySequence.toString());
    }
}
