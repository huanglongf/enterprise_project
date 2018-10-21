package com.jumbo.wms.manager.check;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.daemon.CheckStaRepetitiveTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

@Transactional
@Service("checkStaRepetitiveTask")
public class CheckStaRepetitiveTaskImpl extends BaseManagerImpl implements CheckStaRepetitiveTask {

    /**
     * 
     */
    private static final long serialVersionUID = 3062625403859304319L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    ChooseOptionDao chooseOptionDao;
    @Autowired
    MailTemplateDao mailTemplateDao;
    @Autowired
    InventoryDao inventoryDao;

    @Override
    public void checkStaRepetitive() {
        log.info("checkStaRepetitive>>>>>>>>>>");
        List<String> slipCodes = staDao.checkStaRepetitive(new SingleColumnRowMapper<String>());
        String code = "";
        for (String slipCode : slipCodes) {
            List<StockTransApplication> stas = staDao.findBySlipCodeAndStatus(slipCode);
            if (stas.size() > 1) {
                for (StockTransApplication sta : stas) {
                    if (!sta.getStatus().equals(StockTransApplicationStatus.FINISHED) && !sta.getStatus().equals(StockTransApplicationStatus.INTRANSIT)) {
                        sta.setStatus(StockTransApplicationStatus.CANCELED);
                        sta.setCancelTime(new Date());
                        staDao.save(sta);
                        if (code.equals("")) {
                            code = sta.getCode();
                        } else {
                            code += "," + sta.getCode();
                        }
                        break;
                    }
                }
            }
        }
        if (!code.equals("")) {
            String[] codes = code.split(",");
            for (String code1 : codes) {
                inventoryDao.releaseInventoryByOpcOcde(code1);
            }
        }
        if (slipCodes != null && slipCodes.size() > 0) {
            // 查询系统常量表 收件人
            ChooseOption option = chooseOptionDao.findByCategoryCode("ERROR_NOTICE");
            if (!StringUtil.isEmpty(option.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("STA_EMAIL");
                if (template != null) {

                    String mailBody = template.getMailBody().substring(0, template.getMailBody().indexOf("{")) + slipCodes.toString() + template.getMailBody().substring(template.getMailBody().indexOf("}") + 1, template.getMailBody().length());// 邮件内容
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
