package com.jumbo.wms.manager.task.arrive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.daemon.ArriveTimeTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Service("arriveTimeTask")
public class ArriveTimeTaskImpl extends BaseManagerImpl implements ArriveTimeTask {
    /**
     * 
     */
    private static final long serialVersionUID = -1529438782863919875L;
    private final String ERROR_EMAIL = "ERROR_EMAIL";
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;

    @Override
    public void deblocking() {
        List<StockTransApplication> stas = staDao.getdeblocking();
        Date date = new Date();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = dateFormater.format(date);
        for (StockTransApplication sta : stas) {
            if (datetime.equals(dateFormater.format(sta.getHkArriveTime())) && sta.getIsLocked() != false) {
                sta.setIsLocked(false);
                staDao.save(sta);
            }
        }

    }

    @Override
    public void errorEmail() {
        List<StockTransApplication> stas = staDao.getdeblocking();
        Date date = new Date();
        // SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        // String datetime = dateFormater.format(date);
        for (StockTransApplication sta : stas) {
            if (date.getTime() >= sta.getHkArriveTime().getTime() && sta.getIsLocked() == true) {
                // 查询系统常量表 收件人
                ChooseOption option = chooseOptionDao.findByCategoryCode(ERROR_EMAIL);
                if (!StringUtil.isEmpty(option.getOptionValue())) {
                    // 传人邮件模板的CODE -- 查询String类型可用的模板
                    MailTemplate template = mailTemplateDao.findTemplateByCode("ERROR_EMAIL");
                    if (template != null) {
                        String mailBody = template.getMailBody().substring(0, template.getMailBody().indexOf("{")) + sta.getCode() + template.getMailBody().substring(template.getMailBody().indexOf("}") + 1, template.getMailBody().length());// 邮件内容
                        String subject = template.getSubject();// 标题
                        String addressee = option.getOptionValue(); // 查询收件人
                        MailUtil.sendMail(subject, addressee, "", mailBody, false, null);
                    } else {
                        log.debug("邮件模板不存在或被禁用");
                    }
                } else {
                    log.debug("邮件通知失败,收件人为空！");
                }
            }
        }

    }

}
