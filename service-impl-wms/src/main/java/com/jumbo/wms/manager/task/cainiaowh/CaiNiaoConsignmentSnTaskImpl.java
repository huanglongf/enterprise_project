package com.jumbo.wms.manager.task.cainiaowh;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.baozun.model.top.BzWlbWmsSnInfoQueryRequest;
import cn.baozun.model.top.BzWlbWmsSnInfoQueryResponse;
import cn.baozun.model.top.BzWlbWmsSnInfoQueryResponse.Result.Sninfolist;
import cn.baozun.model.top.BzWlbWmsSnInfoQueryResponse.Result.Sninfolist.SnInfo;
import cn.baozun.rmi.top.TopRmiService;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.CaiNiaoConsignmentSnTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.SkuSnLog;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.TransactionDirection;

/**
 * 接口/类说明：菜鸟仓SN商品管理 Impl
 * 
 * @ClassName: CaiNiaoConsignmentSnTaskImpl
 * @author LuYingMing
 * @date 2016年9月19日 下午6:39:09
 */
@Transactional
@Service("caiNiaoConsignmentSnTask")
public class CaiNiaoConsignmentSnTaskImpl extends BaseManagerImpl implements CaiNiaoConsignmentSnTask {


    private static final long serialVersionUID = 4957606018944314637L;

    // private static Integer ErrorCount = 0;

    @Autowired
    private TopRmiService topRmiService;

    @Autowired
    private StockTransApplicationDao staDao;

    @Autowired
    private BiChannelDao biChannelDao;

    @Autowired
    private SkuSnDao skuSnDao;

    @Autowired
    private SkuSnLogDao skuSnLogDao;

    @Autowired
    private ChooseOptionDao chooseOptionDao;

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;

    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private SkuDao skuDao;


    /**
     * @author LuYingMing
     * @date 2016年9月19日 下午6:38:53
     * @see com.jumbo.wms.daemon.CaiNiaoConsignmentSnTask#callTaoBaoWlbWmsSnInfoQueryInterface()
     */
    @Override
    public void callTaoBaoWlbWmsSnInfoQueryInterface() {
        try {
            List<MsgRtnOutbound> list = msgRtnOutboundDao.findRtnOutboundByStatusAndSn(new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
            if (null != list && list.size() > 0) {
                for (MsgRtnOutbound msgRtnOutbound : list) {
                    if (!StringUtil.isEmpty(msgRtnOutbound.getStaCode()) && null != msgRtnOutbound.getId()) {
                        StockTransApplication sta = staDao.getByCode(msgRtnOutbound.getStaCode());
                        if (null != sta && !StringUtil.isEmpty(sta.getSlipCode1()) && !StringUtil.isEmpty(sta.getOwner())) {
                            String owner = sta.getOwner();
                            BiChannel entity = biChannelDao.checkBiChannelByCodeOrName(owner, null, new BeanPropertyRowMapperExt<BiChannel>(BiChannel.class));
                            if (null != entity && null != entity.getId()) {
                                try {
                                    BzWlbWmsSnInfoQueryResponse responseBean = callCaiNiaoWareHouse(sta, entity); // 调取菜鸟仓查询接口
                                    if (null != responseBean && responseBean.getResult().getSuccess() && responseBean.getResult().getSnInfoList().size() > 0) {
                                        operationData(responseBean, sta); // 操作数据
                                        msgRtnOutboundDao.updateIsSendById(msgRtnOutbound.getId()); // 成功后更新邮件发送状态
                                    } else {
                                        Long errorCount = msgRtnOutbound.getErrorCount();
                                        long eCount = 0L;
                                        if (null == errorCount) {
                                            eCount = 1L;
                                        } else {
                                            int i = errorCount.intValue();
                                            i += 1;
                                            eCount = (long) i;
                                        }
                                        msgRtnOutboundDao.updateErrorCodeByID(msgRtnOutbound.getId(), eCount);
                                    }
                                } catch (Exception e) {
                                    log.error("The reason for the exception of BzWlbWmsSnInfoQueryResponse is:" + sta.getCode(), e);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("The reason for the exception of callTaoBaoWlbWmsSnInfoQueryInterface is:", e);
        }


        try {
            List<MsgRtnOutbound> lisOutbounds = msgRtnOutboundDao.findRtnOutboundByErrorCountAndNoSend(new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
            if (null != lisOutbounds && lisOutbounds.size() > 0) {
                List<StockTransApplication> staList = new ArrayList<StockTransApplication>();
                for (MsgRtnOutbound entity : lisOutbounds) {
                    if (!StringUtil.isEmpty(entity.getStaCode())) {
                        StockTransApplication sta = staDao.getByCode(entity.getStaCode());
                        if (null != sta) {
                            staList.add(sta);
                        } else {
                            log.error("StockTransApplication is null!~");
                            continue;
                        }
                    } else {
                        log.error("staCode is null!~");
                        continue;
                    }
                }
                Boolean res = sendEmail(staList); // 发送邮件
                if (res) {
                    for (MsgRtnOutbound msg : lisOutbounds) {
                        Long msgId = msg.getId();
                        msgRtnOutboundDao.updateIsSendById(msgId);
                    }
                }
            }
        } catch (Exception e) {
            log.error("The reason for the exception of callTaoBaoWlbWmsSnInfoQueryInterface is:", e);
        }
    }



    /**
     * 方法说明：call 菜鸟仓SN接口
     * 
     * @author LuYingMing
     * @date 2016年9月21日 下午7:44:57
     * @param sta
     * @param entity
     * @return
     */
    BzWlbWmsSnInfoQueryResponse callCaiNiaoWareHouse(StockTransApplication sta, BiChannel entity) {
        BzWlbWmsSnInfoQueryRequest requestBean = new BzWlbWmsSnInfoQueryRequest();
        requestBean.setOrderCode(sta.getSlipCode1());
        requestBean.setOrderCodeType(50L);
        requestBean.setPageIndex(1L);
        BzWlbWmsSnInfoQueryResponse responseBean = topRmiService.wlbWmsSnInfoQuery(requestBean, entity.getId());
        return responseBean;
    }



    /**
     * 方法说明：菜鸟仓发货SN号查询次数超过3次仍没成功的邮件通知
     * 
     * @author LuYingMing
     * @date 2016年9月21日 下午5:48:27
     * @param list
     */
    Boolean sendEmail(List<StockTransApplication> list) {
        Boolean result = false;
        // 查询系统常量表 收件人
        ChooseOption option = chooseOptionDao.findByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_CAINIAO_SN);
        if (!StringUtil.isEmpty(option.getOptionValue())) {
            // 传人邮件模板的CODE -- 查询String类型可用的模板
            MailTemplate template = mailTemplateDao.findTemplateByCode(Constants.MAIL_TEMPLATE_CODE_CAINIAO_SN);
            if (template != null) {
                String mailBody = template.getMailBody() + getMsgByList(list);// 邮件内容
                String subject = template.getSubject();// 标题
                String addressee = option.getOptionValue(); // 查询收件人
                boolean bool = false;
                bool = MailUtil.sendMail(subject, addressee, "", mailBody, false, null);
                if (bool) {
                    log.debug("邮件通知成功！");
                    result = bool;
                } else {
                    log.debug("邮件通知失败,请联系系统管理员！");
                }
            } else {
                log.debug("邮件模板不存在或被禁用");
            }
        } else {
            log.debug("邮件通知失败,收件人为空！");
        }

        return result;
    }

    /**
     * 方法说明：将消息内容分组显示并返回字符串
     * 
     * @author LuYingMing
     * @date 2016年9月21日 下午6:54:44
     * @param list
     * @return
     */
    String getMsgByList(List<StockTransApplication> list) {
        // 商品分组
        Map<String, StringBuffer> moslMap = new HashMap<String, StringBuffer>();
        for (StockTransApplication sta : list) {
            if (sta.getCode() != null) {
                StringBuffer s = moslMap.get(sta.getOwner());
                // BiChannel bi = biChannelDao.getByVmiCode(mosl.getVmiCode());
                if (s != null) {
                    s.append(", '" + sta.getCode() + "'");
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append("\r\n----------------------------------------------------------------\r\n");
                    // sb.append(mosl.getVmiCode());
                    if (sta.getOwner() != null) {
                        sb.append(sta.getOwner());
                    }
                    sb.append(": '" + sta.getCode() + "'");
                    moslMap.put(sta.getOwner(), sb);
                }
            } else {
                StringBuffer s = moslMap.get("slipCode1");
                if (s != null) {
                    s.append(", '" + sta.getSlipCode1() + "'");
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append("\r\n----------------------------------------------------------------\r\n");
                    sb.append("平台交易订单号(slipCode1): ");
                    sb.append("'" + sta.getSlipCode1() + "'");
                    moslMap.put("slipCode1", sb);
                }
            }
        }
        // 将消息合成一条字符串
        StringBuffer msgSb = new StringBuffer();
        Set<String> set = moslMap.keySet();
        for (String key : set) {
            msgSb.append(moslMap.get(key));
        }

        return msgSb.toString();
    }

    /**
     * 方法说明：操作数据(删除SkuSn,保存SkuSnLog)
     * 
     * @author LuYingMing
     * @date 2016年9月21日 下午7:49:32
     * @param responseBean
     */
    void operationData(BzWlbWmsSnInfoQueryResponse responseBean, StockTransApplication sta) {
        List<Sninfolist> list = responseBean.getResult().getSnInfoList();
        if (null != list && list.size() > 0) {
            for (Sninfolist sninfoList : list) {
                SnInfo snInfo = sninfoList.getSnInfo();
                if (null != snInfo) {
                    String snCode = snInfo.getSnCode();
                    String skuCode = snInfo.getItemCode();
                    if (!StringUtil.isEmpty(skuCode) && !StringUtil.isEmpty(snCode)) {
                        Sku sku = skuDao.getByCode(skuCode);
                        try {
                            // Ps:考虑到历史数据的SN管理瑕疵,目前暂定:
                            // 1) 如果找到,删SkuSn 存SkuSnLog;
                            // 2)没有找到,SkuSn不做任何操作,存SkuSnLog;
                            SkuSnCommand skuSnCommand = skuSnDao.findBySkuIdAndSn(sku.getId(), snCode, new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
                            if (null != skuSnCommand) {
                                Integer result = skuSnCommand.getSkuSnStatus();
                                if (result == SkuSnStatus.USING.getValue()) {
                                    Long skuSnId = skuSnCommand.getId();
                                    skuSnDao.deleteSkuSnById(skuSnId);
                                } else {
                                    log.error("POSSIBLE SkuSnStatus STATUS EXCEPTION !~~~~");
                                }
                            }

                            addSkuSnLog(sta, snCode, sku.getId()); // 保存日志

                        } catch (Exception e) {
                            if (log.isErrorEnabled()) {
                                log.error("The reason for the exception of SkuSnLog is:" + snCode, e);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * 方法说明：插入sku sn号操作日志
     * 
     * @author LuYingMing
     * @param sta
     */
    void addSkuSnLog(StockTransApplication sta, String snCode, Long skuId) {
        StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
        OperationUnit ou = sta.getMainWarehouse();
        SkuSnLog skuSnLog = new SkuSnLog();
        skuSnLog.setDirection(TransactionDirection.OUTBOUND);
        skuSnLog.setOuId(ou.getId());
        skuSnLog.setSkuId(skuId);
        skuSnLog.setSn(snCode);
        skuSnLog.setStvId(stv.getId());
        skuSnLog.setTransactionTime(new Date());
        skuSnLog.setLastModifyTime(new Date());
        skuSnLogDao.save(skuSnLog);
    }


}
