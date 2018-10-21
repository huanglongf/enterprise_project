package com.jumbo.wms.manager.priorityChannelOms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.priorityChannelOms.PriorityChannelOmsConfigDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.wms.model.config.ThreadConfig;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.PriorityChannelOms;

import loxia.dao.Pagination;
import loxia.dao.Sort;

@Transactional
@Service("priorityChannelOmsUtilManager")
public class PriorityChannelOmsUtilManagerImpl implements PriorityChannelOmsUtilManager {

    /**
     * 
     */
    private static final long serialVersionUID = 372781102095097261L;

    @Autowired
    private PriorityChannelOmsConfigDao priorityChannelOmsConfigDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Override
    public Pagination<PriorityChannelOms> getAllPriorityChannelOms(int start, int pageSize, Sort[] sorts) {
        return priorityChannelOmsConfigDao.getAllPriorityChannelOms(start, pageSize, sorts,new BeanPropertyRowMapper<PriorityChannelOms>(PriorityChannelOms.class));
    }
	@Override
	public String addPriorityChannelOms(PriorityChannelOms priorityChannelOms) {
		String flag="";
			if ("".equals(priorityChannelOms.getId())||priorityChannelOms.getId()==null) {
				if (stringUnitl(priorityChannelOms.getCode())&&priorityChannelOms.getQty()!=null) {
					//为空就执行新增操作
					//验证编码是否存在
					PriorityChannelOms priorityChannelOms2=priorityChannelOmsConfigDao.getPriorityChannelOmsByCode(priorityChannelOms.getCode());
					if (priorityChannelOms2==null) {
						priorityChannelOmsConfigDao.save(priorityChannelOms);
					}else{
						flag="店铺已经存在";
					}
				}else{
					flag="信息不完整";
				}
			}else{
				if (stringUnitl(priorityChannelOms.getCode())&&priorityChannelOms.getQty()!=null) {
					PriorityChannelOms priorityChannelOms2=priorityChannelOmsConfigDao.getPriorityChannelOmsById(priorityChannelOms.getId());
					if (priorityChannelOms2!=null) {
						//验证编码
						if (priorityChannelOms.getCode().equals(priorityChannelOms2.getCode())) {
							priorityChannelOmsConfigDao.save(priorityChannelOms);
						}else {
							PriorityChannelOms priorityChannelOms3=priorityChannelOmsConfigDao.getPriorityChannelOmsByCode(priorityChannelOms.getCode());
							if (priorityChannelOms3==null) {
								priorityChannelOmsConfigDao.save(priorityChannelOms);
							}else{
								flag="店铺已经存在";
							}
						}
						
					}else{
						flag="信息有误";
					}
				}else{
					flag="信息不完整";
				}
			}
		
		
		
		return flag;
	}
	private Boolean stringUnitl(String string) {
		if (!"".equals(string)&&string!=null) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public String updateChooseOption(String optionValue) {
		String flag="";
	    //验证 optionValue格式和值
		if (optionValue.contains("/")) {
			String[] strings=optionValue.split("/");
			if (strings.length==2) {
				if (Integer.parseInt(strings[0])+Integer.parseInt(strings[1])==10) {
					ChooseOption chooseOption=chooseOptionDao.findByCategoryCodeAndKey("system_thread","priorityOrderOms");
					if (chooseOption!=null) {
						chooseOption.setOptionValue(optionValue);
						chooseOptionDao.save(chooseOption);
					}
				}else {
					flag="格式不正确，必须为 */*形式,*为0-10的整数，并且两个*的值相加和为10";
				}
			}else {
				flag="格式不正确，必须为 */*形式,*为0-10的整数，并且两个*的值相加和为10";
			}
		}else {
			flag="格式不正确，必须为 */*形式,*为0-10的整数，并且两个*的值相加和为10";
		}
		//严重格式
		//验证值
	return flag;
	}
	@Override
	public ChooseOption buildChooseOption() {
	return chooseOptionDao.findByCategoryCodeAndKey("system_thread","priorityOrderOms");
	}
}
