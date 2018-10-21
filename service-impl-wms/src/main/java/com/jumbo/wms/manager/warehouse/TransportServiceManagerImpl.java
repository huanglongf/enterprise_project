package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.TransEmsInfoDao;
import com.jumbo.dao.warehouse.TransportServiceAreaDao;
import com.jumbo.dao.warehouse.TransportServiceDao;
import com.jumbo.webservice.ems.EmsServiceClient2;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.TransEmsInfo;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.trans.TransportServiceArea;
import com.jumbo.wms.model.trans.TransportServiceCommand;

/**
 * 物流服务
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
@Service("transportServiceManager")
public class TransportServiceManagerImpl extends BaseManagerImpl implements TransportServiceManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6392911622227439276L;

    @Autowired
    TransportServiceDao transportServiceDao;

    @Autowired
    private TransEmsInfoDao transEmsInfoDao;

    @Autowired
    private ChooseOptionDao chooseOptionDao;

    @Autowired
    private TransportServiceAreaDao transportServiceAreaDao;

    @Override
    public List<TransportServiceCommand> findTransServiceList(Sort[] sorts) {
        return transportServiceDao.getTransportService(new BeanPropertyRowMapper<TransportServiceCommand>(TransportServiceCommand.class), sorts);
    }

    @Override
    public List<TransportServiceCommand> findTransServiceListById(String transId, Sort[] sorts) {
        return transportServiceDao.getTransportServiceById(transId, new BeanPropertyRowMapper<TransportServiceCommand>(TransportServiceCommand.class), sorts);
    }

    @Override
    public List<TransportServiceArea> findTransServiceAreaListById(String transId, Sort[] sorts) {
        return transportServiceAreaDao.findTransServiceAreaById(transId, new BeanPropertyRowMapper<TransportServiceArea>(TransportServiceArea.class), sorts);
    }

    public TransEmsInfo findByCmp(Boolean isCod) {// run
        Boolean bEms = true;// 用老的
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
        if (op != null && op.getOptionValue() != null) {
            bEms = false;// 用新的
        }
        if (bEms) {
            return transEmsInfoDao.findByCmp(isCod, 0);
        } else {
            return transEmsInfoDao.findByCmp(isCod, 1);
        }
    }

    @Override
    public List<TransportServiceCommand> getTransportServiceByCode(String expcode) {
        List<TransportServiceCommand> list = transportServiceDao.getTransportServiceByCode(expcode, new BeanPropertyRowMapper<TransportServiceCommand>(TransportServiceCommand.class));
        return list;
    }
}
