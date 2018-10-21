package com.jumbo.wms.manager.vmi.etamData;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.vmi.etamData.EtamSkuDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.vmi.etamData.EtamSku;

/**
 * ETAM
 * 
 * @author Administrator
 * 
 */

@Transactional
@Service("etamManager")
public class EtamManagerImpl implements EtamManager {

    @Autowired
    private EtamSkuDao etamSkuDao;
    /**
	 * 
	 */
    private static final long serialVersionUID = 959714255077233892L;
    protected static final Logger log = LoggerFactory.getLogger(BaseManagerImpl.class);
    private String delimiter = "";

    @Transactional
    public boolean skuInsertIntoDB(List<String> lines) {
        Boolean flag = true;
        String result[] = null;
        String result2[] = new String[16];
        if (lines == null || lines.isEmpty()) {
            return flag;
        }
        try {

            for (String s : lines) {
                if (s == null || "".equals(s)) {
                    continue;
                }
                result = s.split(delimiter);
                if (result == null || result.length == 0) continue;
                if (result.length < 16) {
                    for (int i = 0; i < result.length; i++) {
                        result2[i] = result[i];
                    }
                    for (int j = result.length; j < 16; j++) {
                        result2[j] = "";
                    }
                    result = result2;
                }

                // 如果价格不能转换则跳过当前行
                try {
                    new BigDecimal(result[12]);
                } catch (NumberFormatException e) {
                    log.error(" --read--etam sku=" + result[0] + "exception-------------" + result[12]);
                    continue;
                }

                // 重复sku
                EtamSku etamSku0 = etamSkuDao.findSkuBySku(result[0]);
                if (etamSku0 != null) {
                    etamSku0.setYear(result[4]);
                    etamSku0.setSeason(result[6]);
                    etamSku0.setTagPrice(new BigDecimal(result[12]));
                    etamSkuDao.save(etamSku0);
                    continue;
                }

                EtamSku etamSku = new EtamSku();
                etamSku.setSku(result[0]);
                etamSku.setBar9(result[1]);
                etamSku.setColor(result[2]);
                etamSku.setSize(result[3]);
                etamSku.setYear(result[4]);
                etamSku.setBrand(result[5]);
                etamSku.setSeason(result[6]);
                etamSku.setStyle(result[7]);
                etamSku.setStory(result[8]);
                etamSku.setFamily(result[9]);
                etamSku.setComponent(result[10]);
                etamSku.setWash(result[11]);
                etamSku.setTagPrice(new BigDecimal(result[12]));
                etamSku.setUserDef1(result[13]);
                etamSku.setUserDef2(result[14]);
                etamSku.setUserDef3(result[15]);
                etamSku = etamSkuDao.save(etamSku);
                // etamSkuDao.flush();
            }
        } catch (Exception e) {
            flag = false;
            log.error(e.getMessage());
            log.error("", e);
            return flag;
        }
        return flag;
    }

}
