/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.manager;

import java.util.List;

import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

public abstract class BaseManagerImpl implements BaseManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 13056181500802085L;

    protected static final Logger log = LoggerFactory.getLogger(BaseManagerImpl.class);
    public static final long TASK_LOCK_TIMEOUT = Constants.TASK_LOCK_TIMEOUT;
    public static final String TASK_LOCK_VALUE = Constants.TASK_LOCK_VALUE;
    @Value("${oms.so.outbound}")
    public String OMS_SO_OUTBOUND;
    @Value("${oms.return.inbound}")
    public String OMS_RETURN_INBOUND;
    @Value("${oms.exchange.outbound}")
    public String OMS_EXCHANGE_OUTBOUND;
    @Value("${oms.sf.outbound}")
    public String OMS_SF_OUTBOUND;
    @Value("${sf.checkword}")
    public String SF_CHECKWORD;
    @Value("${sf.company}")
    public String SF_COMPANY;
    @Value("${sf.monthaccount}")
    public String SF_MONTHLY_ACCOUNT;
    @Value("${sf.skuunit}")
    public String SF_SKU_UNIT;
    @Value("${sf.tw.accesscode}")
    public String SF_TW_ACCESS_CODE;
    @Value("${sf.tw.checkword}")
    public String SF_TW_CHECKWORD;
    @Value("${sf.tw.companycode}")
    public String SF_TW_COMPANY_CODE;
    @Value("${sf.tw.warehousecode}")
    public String SF_TW_WAREHOUSE_CODE;
    @Value("${sf.tw.monthaccount}")
    public String SF_TW_MONTHLY_ACCOUNT;
    @Value("${sf.tw.vendorcode}")
    public String SF_TW_VENDOR_CODE;
    @Value("${zto.username}")
    public String ZTO_USERNAME;
    @Value("${zto.password}")
    public String ZTO_PASSWORD;
    @Value("${yto.url}")
    public String YTO_URL;
    @Value("${yto.upload.orders.url}")
    public String YTO_UPLOAD_ORDERS_URL;
    @Value("${godiva.url}")
    public String GOD_URL;
    @Value("${cas.user.register}")
    public String CAS_USER_REGISTER;
    @Value("${cas.user.pwd.update}")
    public String CAS_USER_PWD_UPDATE;
    @Value("${cas.user.pwd.login.su}")
    public String CAS_USER_PWD_LOGIN_SU;
    @Value("${cas.user.pwd.update.su}")
    public String CAS_USER_PWD_UPDATE_SU;
    @Value("${cas.user.client.key}")
    public String USER_CLIENT_KEY;
    @Value("${sales.url}")
    public String SALES_URL;
    @Value("${sales.username}")
    public String SALES_NAME;
    @Value("${sales.password}")
    public String SALES_SOURCE;
    @Value("${sta.count}")
    public String STA_COUNT;
    @Value("${ttk.site}")
    public String TTK_SITE;
    @Value("${ttk.cus}")
    public String TTK_CUS;
    @Value("${ttk.password}")
    public String TTK_PASSWORD;
    @Value("${ttk.get.url}")
    public String TTK_GET_URL;
    @Value("${ttk.post.url}")
    public String TTK_POST_URL;
    @Value("${ttk.order.url}")
    public String TTK_ORDER_URL;
    @Value("${ttk.digest.parternID}")
    public String TTK_DIGEST_PARTERNID;
    @Value("${oms.so.cancl.result}")
    public String OMS_CANCEL_URL;
    @Value("${nike.inbound_price_limit}")
    public String NIKE_INBOUND_PRICE_LIMIT;
    @Value("${oauth.user.client.login.su}")
    public String OAUTH_USER_CLIENT_LOGIN_SU;
    @Value("${kerry.clientId}")
    public String KERRY_CLIENTID;
    @Value("${kerry.secret}")
    public String KERRY_SECRET;
    @Value("${kerry.createorder.url}")
    public String KERRY_CREATEORDER_URL;
    @Value("${kerry.cancelorder.url}")
    public String KERRY_CANCELORDER_URL;
    @Value("${ems.new.url}")
    public String EMS_NEW_URL;
    @Value("${kerry.hawbcreate.url}")
    public String KERRY_HAWBCREATE_URL;
    @Value("${cxc.get.mailno.url}")
    public String CXC_GET_MAILNO_URL;
    @Value("${rfd.identity}")
    public String RFD_IDENTITY;
    @Value("${rfd.secret}")
    public String RFD_SECRET;
    @Value("${rfd.createorder.url}")
    public String RFD_CREATEORDER_URL;
    @Value("${rfd.confirmorder.url}")
    public String RFD_CONFIRMORDER_URL;
    @Value("${sys.seq.inc.inv.pre}")
    public String SYS_SEQ_INV_INV_PRE;

    // MQ 主题与 标签 开始
    @Value("${mq.mq.wms3.sale.outbount}")
    public String MQ_WMS3_SALE_OUTBOUNT;// 出库反馈

    @Value("${mq.wms3.sale.outbount.oms}")
    public String MQ_WMS3_SALE_OUTBOUNT_OMS;// 出库 反馈标签 01

    @Value("${mq.mq.wms3.mq.rtn.outbount.yh}")
    public String MQ_WMS3_MQ_RTN_OUTBOUNT_YH;// 出库反馈YH WLB 主题

    @Value("${mq.mq.wms3.mq.rtn.outbount.lf}")
    public String MQ_WMS3_MQ_RTN_OUTBOUNT_LF;// // 出库反馈LF 主题

    @Value("${mq.mq.wms3.sales.order.service}")
    public String MQ_WMS3_SALES_ORDER_SERVICE;// 订单接受 直连 主题

    @Value("${mq.mq.wms3.sales.order.create}")
    public String MQ_WMS3_SALES_ORDER_CREATE;// 订单接受 非直连 主题

    @Value("${mq.mq.wms3.sales.order.service.return}")
    public String MQ_WMS3_SALES_ORDER_SERVICE_RETURN;// 直连创单反馈 主题

    @Value("${mq.mq.wms3.sales.order.create.return}")
    public String MQ_WMS3_SALES_ORDER_CREATE_RETURN;// 非直连退货入库反馈 主题
    // /其他系统
    @Value("${mq.hub2wms3.salesorder.notify}")
    public String MQ_HUB_SALESORDER_NOTIFY;// hub销售数据过仓 主题
    @Value("${mq.pac.wms.queue}")
    public String MQ_PAC_WMS_QUEUE;// pac销售数据过仓 主题

    @Value("${mq.mq.wms3.im.inv.sku.flow}")
    public String MQ_WMS3_IM_INV_SKU_FLOW;// 推送IM库存流水 主题 发

    @Value("${mq.mq.wms3.im.occupied}")
    public String MQ_WMS3_IM_OCCUPIED;// 发送IM内部库存占用 主题 发

    @Value("${mq.mq.wms3.im.release}")
    public String MQ_WMS3_IM_RELEASE;// 发送IM内部库存取消释放 主题

    @Value("${mq.mq.wms3.sale.outbount.feedback}")
    public String MQ_WMS3_SALE_OUTBOUNT_FEEDBACK;// 销售出、退换货出 反馈OMS 非直连 主题

    /**
     * 增量库存同步 主题
     */
    @Value("${mq.mq.wms3.incremental.inventory}")
    public String MQ_WMS3_INCREMENTAL_INVENTORY;

    /**
     * 创单成功请求pac反馈 主题
     */
    @Value("${mq.mq.wms32pac.rtn.order.create}")
    public String MQ_WMS32PAC_RTN_ORDER_CREATE;

    /**
     * wms出库单同步lmis 主题
     */
    @Value("${mq.wms2lmis.order.outbound}")
    public String MQ_WMS2LMIS_ORDER_OUTBOUND;

    /**
     * 作业废纸箱信息推送 to lmis, mq主题
     */
    @Value("${mq.wms2lmis.soc.rmq}")
    public String MQ_WMS2LMIS_SOC_RMQ;

    // /结束
    @Value("${toms.42.cancel}")
    public String TOMS_42_CANCEL;// toms 换货出取消 1：开 2：关

    @Value("${msg.outbount.createtimelog}")
    public String MSG_OUTBOUNT_CREATETIMELOG;// 外包仓出库createtimelog 1：写入log 2：关

    @Value("${mq.wms3.occupy.inventory}")
    public String MQ_WMS3_OCCUPY_INVENTORY;// 占用库存MQ列表

    @Value("${is_push_im_transit_inner}")
    public String IS_PUSH_IM_TRANSIT_INNER;// 库间移动的占用和取消是否推送IM

    @Value("${is_toms_inventory}")
    public String IS_TOMS_INVENTORY;// 直连过仓初始化库存开关 1：开 0：关

    @Value("${mq.wms32im.inventory.snapshot}")
    public String MQ_WMS32IM_INVENTORY_SNAPSHOT;// 同步全量库存给IM系统

    @Value("${mq.wms32im.inventory.snapshot.success}")
    public String MQ_WMS32IM_INVENTORY_SNAPSHOT_SUCCESS;// 同步批次汇总信息给IM系统


    @Value("${is_pacs_inventory}")
    public String IS_PACS_INVENTORY;// 非直连过仓初始化库存开关 1：开 0：关


    // MQ_wms32hub_nike_inbount_return
    @Value("${mq.mq.wms32hub.nike.inbount.return}")
    public String MQ_WMS32HUB_NIKE_INBOUNT_RETURN;// nike收货 反馈hub

    //压测S
    @Value("${mq.wms3.picking}")
    public String MQ_WMS3_PICKING;// 自动化压测脚本数据准备-拣货
    @Value("${mq.wms3.seeding}")
    public String MQ_WMS3_SEEDING;// 自动化压测脚本数据准备-播种
    @Value("${mq.wms3.checking}")
    public String MQ_WMS3_CHECKING;// 自动化压测脚本数据准备-复核
    //压测E
    

    /**
     * 主要用于校验时返回的List<BusinessException>的特殊处理
     * 
     * @param errors
     */
    protected void businessExceptionPost(List<BusinessException> errors) {
        if (errors == null || errors.isEmpty()) return;
        BusinessException[] errorsArray = new BusinessException[errors.size()];
        throw new BusinessException(ErrorCode.ERROR_NOT_SPECIFIED, errors.toArray(errorsArray));
    }

    /**
     * 为主异常信息添加关联异常信息
     * 
     * @param root 主异常信息
     * @param newError 新增异常
     */
    protected void setLinkedBusinessException(BusinessException root, BusinessException newError) {
        BusinessException b = root;
        while (b.getLinkedException() != null) {
            b = b.getLinkedException();
        }
        b.setLinkedException(newError);
    }

    protected String formatIntToChinaBigNumStr(int num) {
        if (num < 0) num = 0;
        String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String si = num + "";
        char cc[] = new char[5];
        if (si.length() < 5) {
            StringBuilder t = new StringBuilder(si);
            for (int n = 0; n < 5 - si.length(); n++) {
                t = new StringBuilder("0").append(t);
            }
            cc = t.toString().toCharArray();
        } else if (si.length() == 5) {
            cc = si.toCharArray();
        } else {
            log.debug("amount is too big");
            return "";
        }
        StringBuilder tt = new StringBuilder();
        for (int j = 0; j < cc.length; j++) {
            tt.append(bigLetter[cc[j] - 48] + "   ");
        }
        return tt.toString();
    }

    protected String _hasLength(String entity) {
        return StringUtils.hasLength(entity) ? entity : "";
    }

    public void isHaveException(StockTransApplication sta, StockTransVoucher stv, User user, Long userId) throws Exception {
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (StockTransApplicationType.INBOUND_OTHERS.getValue() == sta.getType().getValue()) {
            if (sta.getStatus().getValue() != StockTransApplicationStatus.CREATED.getValue()) {
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            }
        } else if (sta.getStatus().getValue() != StockTransApplicationStatus.OCCUPIED.getValue()) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        if (stv == null) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
    }
}
