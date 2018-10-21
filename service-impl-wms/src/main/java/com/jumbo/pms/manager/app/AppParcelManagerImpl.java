package com.jumbo.pms.manager.app;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.utils.PropListCopyable;
import loxia.utils.PropertyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.connector.sf.model.order.request.OrderOption;
import cn.baozun.bh.connector.sf.rmi.RmiService;

import com.jumbo.dao.pms.parcel.BranchLibraryDao;
import com.jumbo.dao.pms.parcel.ParcelDao;
import com.jumbo.dao.pms.parcel.ParcelLineDao;
import com.jumbo.dao.pms.parcel.ParcelTransDetailDao;
import com.jumbo.dao.pms.parcel.SysInterfaceQueueDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.pms.model.AppParcelCommand;
import com.jumbo.pms.model.BranchLibrary;
import com.jumbo.pms.model.Parcel;
import com.jumbo.pms.model.ParcelBill;
import com.jumbo.pms.model.ParcelLine;
import com.jumbo.pms.model.ParcelLineBill;
import com.jumbo.pms.model.ParcelLineCommand;
import com.jumbo.pms.model.ParcelMailNoGettingCommand;
import com.jumbo.pms.model.ParcelStatus;
import com.jumbo.pms.model.ParcelTransDetail;
import com.jumbo.pms.model.SysInterfaceQueue;
import com.jumbo.pms.model.SysInterfaceQueueStatus;
import com.jumbo.pms.model.SysInterfaceQueueSysType;
import com.jumbo.pms.model.SysInterfaceQueueType;
import com.jumbo.rmi.warehouse.PmsBaseResult;
import com.jumbo.webservice.sfNew.SfOrderWebserviceClientInter;
import com.jumbo.webservice.sfNew.model.SfOrder;
import com.jumbo.webservice.sfNew.model.SfOrderOption;
import com.jumbo.webservice.sfNew.model.SfOrderResponse;
import com.jumbo.webservice.sfNew.model.SfResponse;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.model.system.ChooseOption;

@Transactional
@Service("appParcelManager")
public class AppParcelManagerImpl extends BaseManagerImpl implements AppParcelManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -478400999989349853L;

    @Autowired
    private ParcelDao parcelDao;
    @Autowired
    private ParcelTransDetailDao parcelTransDetailDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private SysInterfaceQueueDao sysInterfaceQueueDao;
    @Autowired
    private ParcelLineDao parcelLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private BranchLibraryDao branchLibraryDao;
    @Autowired
    private RmiService sfRmiService;
    @Autowired
    private SfOrderWebserviceClientInter sfOrderWebserviceClient;



    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String getParcelCode(Parcel parcel) {
        return sequenceManager.getCode(Parcel.class.getName(), parcel);
    }

    @Override
    public List<PmsBaseResult> updateSoParcel(List<AppParcelCommand> parcelCommands) {
        /**
         * PMS更新订单包裹状态(门店已签收/顾客已签收) 校验当前包裹+门店 确认当前门店是否可以收货 如果通过校验,记录包裹状态 顾客已签收 需要通知PAC修改订单状态
         * 
         */
        List<PmsBaseResult> baseResultList = new ArrayList<PmsBaseResult>();
        PmsBaseResult baseResult = null;
        if (parcelCommands == null || parcelCommands.size() == 0) {
            baseResultList.add(constructResponse(null, PmsBaseResult.STATUS_ERROR, "請求數據為空", PmsBaseResult.ERROR_CODE_60001));
            return baseResultList;
        }

        for (AppParcelCommand pc : parcelCommands) {
            try {
                // 校验数据,
                baseResult = validateParcel(pc, baseResult);
                if (baseResult != null) {
                    baseResultList.add(baseResult);
                    continue;
                }
                String mailNo = pc.getMailNo();
                String lpcode = pc.getLpcode();
                String outerOrderCode = !StringUtils.hasText(pc.getOuterOrderCode()) ? null : pc.getOuterOrderCode();
                Parcel parcel = findByLpcodeAndMailNo(mailNo, lpcode, outerOrderCode);
                if (null == parcel) {
                    baseResultList.add(constructResponse(pc, PmsBaseResult.STATUS_ERROR, "查無此件", PmsBaseResult.ERROR_CODE_70001));
                    continue;
                }
                boolean checkParcelStatus = Boolean.FALSE;
                String opType = pc.getOpType();

                // 如果门店还没有签收,校验当前状态是否允许签收
                if ("002".equals(opType)) {// 门店已签收
                    checkParcelStatus = checkParcelStatus(parcel.getStatusInteger(), ParcelStatus.parcel_delivered);
                } else if ("003".equals(opType)) {// 顾客已签收
                    checkParcelStatus = checkParcelStatus(parcel.getStatusInteger(), ParcelStatus.parcel_Received_by_Store);
                }

                // 4. 校验包裹当前状态是否允许签收
                if (!checkParcelStatus) {
                    // 先校验包裹是否已经被签收,如果是,直接反馈牵手成功,不做任何操作 ~
                    if ("002".equals(opType)) {// 门店已签收
                        checkParcelStatus = checkParcelStatus(parcel.getStatusInteger(), ParcelStatus.parcel_Received_by_Store);
                    } else if ("003".equals(opType)) {// 顾客已签收
                        checkParcelStatus = checkParcelStatus(parcel.getStatusInteger(), ParcelStatus.parcel_Picked_by_Customer);
                    }

                    if (checkParcelStatus) {
                        baseResultList.add(constructResponse(pc, PmsBaseResult.STATUS_SUCCESS, MessageFormat.format("根據運單號[{0}]和物流商[{1}]查詢到包裹重复签收", mailNo, lpcode), null));
                        continue;
                    }
                    String statusValue = findByCategoryCodeAndKey("parcelStatus", parcel.getStatusInteger().toString());
                    baseResultList.add(constructResponse(pc, PmsBaseResult.STATUS_ERROR, MessageFormat.format("根據運單號[{0}]和物流商[{1}]查詢到包裹當前狀態是[{2}]，簽收失敗", mailNo, lpcode, statusValue), PmsBaseResult.ERROR_CODE_70006));
                    continue;
                }

                // 5. 校验通过,包裹更新状态, 若是顾客已签收,需求通知PAC更新订单状态到完成
                updateParcelStatus(parcel, "002".equals(opType) ? ParcelStatus.parcel_Received_by_Store : ParcelStatus.parcel_Picked_by_Customer);
                baseResultList.add(constructResponse(pc, PmsBaseResult.STATUS_SUCCESS, MessageFormat.format("運單號[{0}]，物流商[{1}]包裹簽收成功", mailNo, lpcode), null));
            } catch (Exception e) {
                baseResultList.add(constructResponse(pc, PmsBaseResult.STATUS_ERROR, "簽收異常", PmsBaseResult.ERROR_CODE_70003));
            }
        }
        return baseResultList;
    }

    public Parcel copyParcel(ParcelBill parcelBill) {
        Parcel parcel = new Parcel();
        try {
            PropertyUtil.copyProperties(parcelBill, parcel, new PropListCopyable("originCode", "sender", "senderMobile", "receiver", "receiverMobile", "destinationCode", "omsOrderCode", "outerOrderCode", "address", "mailNo", "lpcode", "parcelQuantity",
                    "Courier", "shipmentContents", "isCod", "charges", "remark", "extTransOrderId"));
        } catch (IllegalAccessException e) {
            if (log.isErrorEnabled()) {
                log.error("copyParcel-IllegalAccessException", e);
            }
        } catch (InvocationTargetException e) {
            if (log.isErrorEnabled()) {
                log.error("copyParcel-InvocationTargetException", e);
            }
        } catch (NoSuchMethodException e) {
            if (log.isErrorEnabled()) {
                log.error("copyParcel-NoSuchMethodException", e);
            }
        }
        return parcel;
    }

    public ParcelLine copyParcelLine(ParcelLineBill parcelLineBill) {
        ParcelLine pl = new ParcelLine();
        try {
            PropertyUtil.copyProperties(parcelLineBill, pl, new PropListCopyable("skuCode", "barCode", "skuName", "quantity"));
        } catch (IllegalAccessException e) {
            if (log.isErrorEnabled()) {
                log.error("copyParcel-IllegalAccessException", e);
            }
        } catch (InvocationTargetException e) {
            if (log.isErrorEnabled()) {
                log.error("copyParcel-InvocationTargetException", e);
            }
        } catch (NoSuchMethodException e) {
            if (log.isErrorEnabled()) {
                log.error("copyParcel-NoSuchMethodException", e);
            }
        }
        return pl;
    }

    /**
     * STA finish notify PMS parcel delivery
     * 
     * @param sta
     */
    @Override
    public void staFinishNotifyPMS(ParcelBill parcelBill) {
        /**
         * 1. 校验数据是否已存在,如存在不做任何处理 2. 封装数据,然后保存 3. 记录日志
         * 
         * TODO *********SG 要改成先用中间表保存数据,然后再定义一个定时任务来创建包裹信息
         */
        // 包裹主档 运单号+物流商 为唯一键
        Parcel parcel = findByLpcodeAndMailNo(parcelBill.getMailNo(), parcelBill.getLpcode(), parcelBill.getOuterOrderCode());
        if (parcel == null) {
            parcel = new Parcel();
        } else {
            log.debug("AppParcelManagerImpl.staFinishNotifyPMS mailNo[{}],lpcode[{}] has created~ [{}]", new Object[] {parcelBill.getMailNo(), parcelBill.getLpcode()});
            return;
        }

        try {
            parcel = copyParcel(parcelBill);
            parcel.setCode(getParcelCode(parcel));
            ParcelStatus changeStatus = ParcelStatus.parcel_delivered;
            parcel.setStatus(changeStatus);
            ParcelLine parcelLine = null;
            parcelDao.save(parcel);
            for (ParcelLineBill lineBill : parcelBill.getLineBills()) {
                parcelLine = copyParcelLine(lineBill);
                parcelLine.setParcel(parcel);
                parcelLineDao.save(parcelLine);
            }
            // 记录日志
            saveParcelTransDetail(parcel.getCode(), changeStatus);
        } catch (Exception e) {
            log.error("AppParcelManagerImpl.staFinishNotifyPMS Execution error , orderCode [" + parcelBill.getOmsOrderCode() + "], please check~");
        }
    }

    private void saveSysInterfaceQueue(Parcel parcel, SysInterfaceQueueType type, SysInterfaceQueueSysType targetType, SysInterfaceQueueStatus status) {
        SysInterfaceQueue queue = new SysInterfaceQueue();
        queue.setMailNo(parcel.getMailNo());
        queue.setLpcode(parcel.getLpcode());
        queue.setOuterOrderCode(parcel.getOuterOrderCode());
        queue.setOmsOrderCode(parcel.getOmsOrderCode());
        queue.setTargetSys(targetType);
        queue.setType(type);
        queue.setStatus(status);
        sysInterfaceQueueDao.save(queue);
    }

    public String findByCategoryCodeAndKey(String categoryCode, String key) {
        ChooseOption option = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, key);
        return option.getOptionValue();
    }

    private void updateParcelStatus(Parcel parcel, ParcelStatus changeStatus) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mailNo", parcel.getMailNo());
        params.put("lpcode", parcel.getLpcode());
        params.put("status", changeStatus.getValue());
        params.put("outerOrderCode", parcel.getOuterOrderCode());
        parcelDao.updateByParams(params);

        // 记录日志
        saveParcelTransDetail(parcel.getCode(), changeStatus);

        if (ParcelStatus.parcel_Picked_by_Customer.equals(changeStatus)) {
            // 顾客已签收需要通知PAC更新订单状态,PAC订单一定允许SF刷完成
            saveSysInterfaceQueue(parcel, SysInterfaceQueueType.PARCEL_CHANGE_STATUS_NOTIFY_PAC, SysInterfaceQueueSysType.PACS, SysInterfaceQueueStatus.STATUS_NEW);
        }
    }

    private void saveParcelTransDetail(String slipCode, ParcelStatus changeStatus) {
        // 记录包裹路由信息
        ParcelTransDetail detail = new ParcelTransDetail();
        detail.setAcceptTime(new Date());
        detail.setParcelCode(slipCode);
        detail.setRemark(findByCategoryCodeAndKey("parcelStatus", changeStatus.getValue() + ""));
        parcelTransDetailDao.save(detail);
    }

    private boolean checkParcelStatus(Integer currentParcelStatus, ParcelStatus correctParcelStatus) {
        if (currentParcelStatus.equals(correctParcelStatus.getValue())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 封装反馈数据
     * 
     * @param ac
     * @param status
     * @param msg
     * @return
     */
    private PmsBaseResult constructResponse(AppParcelCommand ac, int status, String msg, Integer errorCode) {
        PmsBaseResult baseResult = new PmsBaseResult();
        if (ac != null) {
            String mailNo = ac.getMailNo();
            String lpcode = ac.getLpcode();
            baseResult.setMailNo(mailNo);
            baseResult.setLpCode(lpcode);
            Parcel parcel = findByLpcodeAndMailNo(mailNo, lpcode, null);
            if (parcel != null) {
                baseResult.setOuterOrderCode(parcel.getOuterOrderCode());
            }
        }
        baseResult.setStatus(status);
        baseResult.setMsg(msg);
        // 异常信息
        baseResult.setErrorCode(errorCode);
        return baseResult;
    }

    @Override
    public PmsBaseResult getMailNoForStore(ParcelMailNoGettingCommand command) {
        /**
         * 1. 为空校验 2. 校验orderCode是否已经申请过运单号，如果申请过直接反馈 3. 根据门店编码查询是否存在 4. 根据仓库编码查询是否存在 5.
         * 封装数据调用物流服务商接口 6. 超期件包裹需要更新状态(不影响获取运单号) 7. 创建包裹数据 8. 封装反馈数据
         */
        // 1
        PmsBaseResult baseResult = new PmsBaseResult();
        baseResult.setStatus(PmsBaseResult.STATUS_ERROR);
        if (command == null) {
            baseResult.setMsg("請求數據為空");
            baseResult.setErrorCode(PmsBaseResult.ERROR_CODE_60001);
            return baseResult;
        }

        if (!StringUtils.hasText(command.getOrderCode()) || !StringUtils.hasText(command.getO2oShopCode()) || !StringUtils.hasText(command.getSender()) || !StringUtils.hasText(command.getSenderMobile())) {
            baseResult.setMsg("數據非法, 請求失敗");
            baseResult.setErrorCode(PmsBaseResult.ERROR_CODE_60002);
            return baseResult;
        }

        // 2
        String orderCode = command.getOrderCode();
        Parcel parcel = parcelDao.findByOuterOrderCode(orderCode);
        if (parcel != null) {
            baseResult = generateParcelInfo(parcel);
            return baseResult;
        }

        // 3
        String o2oShopCode = command.getO2oShopCode();
        BranchLibrary originLibrary = branchLibraryDao.findBySlipCode(o2oShopCode);
        if (originLibrary == null) {
            baseResult.setMsg(MessageFormat.format("門店編碼[{0}]不存在", o2oShopCode));
            baseResult.setErrorCode(PmsBaseResult.ERROR_CODE_70005);
            return baseResult;
        }

        // 4
        /**
         * 收货信息
         */
        String destinationCode = command.getDestinationCode();
        BranchLibrary destinationLibrary = branchLibraryDao.findBySlipCode(destinationCode);
        if (destinationLibrary == null) {
            baseResult.setMsg(MessageFormat.format("倉庫[{0}]不存在", destinationCode));
            baseResult.setErrorCode(PmsBaseResult.ERROR_CODE_70007);
            return baseResult;
        }

        // 5
        parcel = matchingTransNo(command);
        if (parcel == null) {
            baseResult.setMsg("調用物流商接口失敗");
            baseResult.setErrorCode(PmsBaseResult.ERROR_CODE_70004);
            return baseResult;
        }

        baseResult = generateParcelInfo(parcel);

        // 6
        if (command.getIsParcelTimeOut() != null && command.getIsParcelTimeOut()) {
            try {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("mailNo", command.getSlipMailNo());
                params.put("lpcode", command.getSlipLpcode());
                params.put("status", ParcelStatus.parcel_Time_Out.getValue());
                params.put("outerOrderCode", null);
                parcelDao.updateByParams(params);
            } catch (Exception e) {
                log.error("ERROR ----- AppParcelManagerImpl.getMailNoForStore.updateByLpcodeAndMailNo");
            }
        }
        return baseResult;
    }

    /**
     * 封装数据,调用SF接口,通知SF上门取件
     * 
     * @param command
     * @param relation
     * @return
     */
    public Parcel matchingTransNo(ParcelMailNoGettingCommand command) {
        SfOrder order = new SfOrder();
        String orderId = sequenceManager.getCode(SfOrder.class.getName(), order);
        order.setOrderId(orderId);
        order.setExpressType("1");
        order.setPayMethod(SfOrder.SF_PAYMETHOD_TYPE_SEND_PARTY);
        BranchLibrary destinationLibrary = branchLibraryDao.findBySlipCode(command.getDestinationCode());
        // 到件方
        order.setdContact(destinationLibrary.getContact());
        order.setdTel(destinationLibrary.getTelephone());
        order.setdProvince(destinationLibrary.getProvince());
        order.setdCity(destinationLibrary.getCity());
        order.setdAddress(destinationLibrary.getAddress());
        // 寄件方
        BranchLibrary originLibrary = branchLibraryDao.findBySlipCode(command.getO2oShopCode());
        order.setjAddress(originLibrary.getAddress());
        order.setjProvince(originLibrary.getProvince());
        order.setjCity(originLibrary.getCity());
        order.setjContact(originLibrary.getContact());
        order.setjTel(originLibrary.getTelephone());
        order.setjCompany(originLibrary.getBranchName());

        SfOrderOption option = new SfOrderOption();
        option.setdCounty(destinationLibrary.getCounty());

        option.setCustid(originLibrary.getCustid());
        option.setCargoCount(command.getSkuCount());
        // 获取 SF顾客编码
        String sfClientCode = findByCategoryCodeAndKey(Constants.SF_WEB_SERVICE_INFO, Constants.SF_WEB_SERVICE_INFO_CODE);
        // 获取 SF checkWord
        String checkword = findByCategoryCodeAndKey(Constants.SF_WEB_SERVICE_INFO, Constants.SF_WEB_SERVICE_INFO_CHECKWORD);
        option.setjMobile(originLibrary.getMobile());
        option.setdMobile(destinationLibrary.getMobile());
        order.setOrderOption(option);
        SfResponse rs = null;
        Parcel parcel = null;
        try {
            rs = sfOrderWebserviceClient.creSfOrder(order, sfClientCode, checkword, 0);
        } catch (Exception e) {
            return parcel;
        }

        if (rs == null || rs.getHead() == null || !rs.getHead().equals(SfResponse.STATUS_OK)) {
            return parcel;
        } else if (rs.getHead().equals(SfResponse.STATUS_OK)) {
            SfOrderResponse response = (SfOrderResponse) rs.getBodyObj();
            String mailNo = response.getMailno();
            if (!StringUtils.hasText(mailNo)) {
                return parcel;
            }
            // create parcel
            parcel = new Parcel();
            parcel.setExtTransOrderId(orderId);
            // String transCityCode = response.getDestCode();
            parcel.setMailNo(mailNo);
            // parcel.setOmsOrderCode(omsOrderCode);
            parcel.setOuterOrderCode(command.getOrderCode());
            parcel.setCode(getParcelCode(parcel));
            parcel.setAddress(destinationLibrary.getAddress());
            parcel.setDestinationCode(destinationLibrary.getSlipCode());
            parcel.setOriginCode(originLibrary.getSlipCode());
            parcel.setLpcode("SF");
            parcel.setStatus(ParcelStatus.apply_parcel_code);
            parcel.setShipmentContents(command.getShipmentContents());
            parcel.setSenderMobile(command.getSenderMobile());
            parcel.setSender(command.getSender());
            parcel.setRemark(command.getRemark());
            parcel.setReceiver(destinationLibrary.getBranchName());
            parcel.setReceiverMobile(destinationLibrary.getMobile());
            parcelDao.save(parcel);
            List<ParcelLineCommand> lineCommands = command.getLineCommands();
            ParcelLine line = null;
            for (ParcelLineCommand pl : lineCommands) {
                line = new ParcelLine();
                line.setSkuCode(pl.getSkuCode());
                line.setSkuName(pl.getSkuName());
                line.setBarCode(pl.getBarCode());
                line.setQuantity(pl.getQuantity());
                line.setParcel(parcel);
                parcelLineDao.save(line);
            }
            // 记录日志
            saveParcelTransDetail(parcel.getCode(), ParcelStatus.apply_parcel_code);
            // 通知SF上门取件
            saveSysInterfaceQueue(parcel, SysInterfaceQueueType.PARCEL_PICK_UP_NOTIFY, SysInterfaceQueueSysType.LP, SysInterfaceQueueStatus.STATUS_NEW);
        }
        return parcel;
    }

    /**
     * 通知SF上门取件
     * 
     * @param sourceOrder
     * @param response
     */
    public void notifySFPickUp(SfOrder sourceOrder, SfOrderResponse response) {
        log.warn("*********************notifySFPickUp orderId: [{}] **********************", new Object[] {sourceOrder.getOrderId()});
        cn.baozun.bh.connector.sf.model.order.request.Order order = new cn.baozun.bh.connector.sf.model.order.request.Order();

        // 订单主体信息
        order.setDAddress(sourceOrder.getdAddress());
        order.setDCity(sourceOrder.getdCity());
        order.setDCompany(sourceOrder.getdCompany());
        order.setDContact(sourceOrder.getdContact());
        order.setDProvince(sourceOrder.getdProvince());
        order.setDTel(sourceOrder.getdTel());
        order.setExpressType(sourceOrder.getExpressType());
        order.setJAddress(sourceOrder.getjAddress());
        order.setJCity(sourceOrder.getjCity());
        order.setJCompany(sourceOrder.getjCompany());
        order.setJContact(sourceOrder.getjContact());
        order.setJProvince(sourceOrder.getjProvince());
        order.setJTel(sourceOrder.getjTel());

        order.setOrderid(sourceOrder.getOrderId());
        order.setPayMethod(sourceOrder.getPayMethod() + "");// 必须传值 默认：1
        order.setParcelQuantity("1");

        // 可选字段
        OrderOption orderOption = new OrderOption();
        SfOrderOption sourceOption = sourceOrder.getOrderOption();
        orderOption.setCustid(sourceOption.getCustid());// 月结卡号 如果不传这个字段订单自动变成寄付现结 update by lwb
                                                        // 2015/2/3
        orderOption.setTemplate(sourceOption.getTemplate());// 模板
        orderOption.setDDeliverycode(response.getDestCode());// 到件方代码
        orderOption.setRemark(sourceOption.getRemark());
        order.setOrderOption(orderOption);

        try {
            cn.baozun.bh.connector.sf.model.order.response.Response sfResponse = sfRmiService.orderService(order);

            if (sfResponse.getHead() != null && sfResponse.getHead().equals("ERR")) {
                log.warn("*****notifySFPickUp****** orderId:[{}],errorCode:[{}],errorValue:[{}]", new Object[] {sourceOrder.getOrderId(), sfResponse.getERROR().getCode(), sfResponse.getERROR().getValue()});
            } else {
                log.warn("*****notifySFPickUp****** orderId:[{}],successRemark:[{}]", new Object[] {sfResponse.getBody().getOrderResponse().getRemark()});
            }
        } catch (Exception e) {
            log.warn("*****notifySFPickUp Exception****** orderId:[{}],error:[{}]", new Object[] {sourceOrder.getOrderId(), e.getMessage()});
        }
    }

    public PmsBaseResult generateParcelInfo(Parcel parcel) {
        PmsBaseResult baseResult = new PmsBaseResult();
        BranchLibrary originLibrary = branchLibraryDao.findBySlipCode(parcel.getOriginCode());
        BranchLibrary destinationLibrary = branchLibraryDao.findBySlipCode(parcel.getDestinationCode());
        baseResult.setAccountNo(originLibrary.getAccountNo());
        baseResult.setChargeTo(originLibrary.getPayMethod() == null ? "1" : originLibrary.getPayMethod().toString()); // 1代表寄方付
        baseResult.setOriginRegionCode(originLibrary.getRegionCode());
        baseResult.setDestinationRegionCode(destinationLibrary.getRegionCode());
        baseResult.setLpCode(parcel.getLpcode());
        baseResult.setMailNo(parcel.getMailNo());
        baseResult.setReceiver(parcel.getReceiver());
        baseResult.setReceiverAddress(parcel.getAddress());
        baseResult.setReceiverMobile(parcel.getReceiverMobile());
        baseResult.setO2oShopAddress(originLibrary.getAddress());
        baseResult.setShipperSignature(originLibrary.getBranchNameEn());
        baseResult.setStatus(PmsBaseResult.STATUS_SUCCESS);
        return baseResult;
    }

    public Parcel findByLpcodeAndMailNo(String mailNo, String lpcode, String outerOrderCode) {
        return parcelDao.findByLpcodeAndMailNo(mailNo, lpcode, outerOrderCode);
    }

    @Override
    public PmsBaseResult queryParcelExists(AppParcelCommand pc) {
        /**
         * APP扫描包裹 需要调用PMS接口查询包裹是否属于当前门店
         * 
         * 新增需求 识别平台单号来签收包裹 opType == 004
         */
        PmsBaseResult baseResult = null;
        if (pc == null) {
            return constructResponse(null, PmsBaseResult.STATUS_ERROR, "請求數據為空", PmsBaseResult.ERROR_CODE_60001);
        }

        try {
            // 校验数据

            /** 平台单号签收包裹 平台单号必填 */
            if ("004".equals(pc.getOpType())) {
                String outerOrderCode = pc.getOuterOrderCode();
                if (!StringUtils.hasText(outerOrderCode)) {
                    return constructResponse(null, PmsBaseResult.STATUS_ERROR, "數據非法, 請求失敗", PmsBaseResult.ERROR_CODE_60002);
                }
                Parcel parcel = parcelDao.findByOuterOrderCode(outerOrderCode);
                if (parcel != null) {
                    baseResult = constructResponse(pc, PmsBaseResult.STATUS_SUCCESS, "門店允許簽收", null);
                    baseResult.setOuterOrderCode(outerOrderCode);
                } else {
                    return constructResponse(null, PmsBaseResult.STATUS_ERROR, "數據非法, 請求失敗", PmsBaseResult.ERROR_CODE_60002);
                }
            } else {
                baseResult = validateParcelInfo(pc, baseResult);
                if (baseResult == null) {
                    baseResult = constructResponse(pc, PmsBaseResult.STATUS_SUCCESS, "門店允許簽收", null);
                }
            }

        } catch (Exception e) {
            baseResult = constructResponse(pc, PmsBaseResult.STATUS_ERROR, "簽收異常", PmsBaseResult.ERROR_CODE_70003);
        }
        return baseResult;
    }

    public PmsBaseResult validateParcel(AppParcelCommand pc, PmsBaseResult baseResult) {
        String mailNo = pc.getMailNo();
        String lpcode = pc.getLpcode();
        String o2oShopCode = pc.getO2oShopCode();
        // String outerOrderCode = pc.getOuterOrderCode();
        // 1. 不处理运单号+门店+物流商+平台单号为空的数据
        if (!StringUtils.hasText(mailNo) || !StringUtils.hasText(lpcode) || !StringUtils.hasText(o2oShopCode)) {
            return constructResponse(pc, PmsBaseResult.STATUS_ERROR, "請求數據為空", PmsBaseResult.ERROR_CODE_60001);
        }
        return null;
    }

    public PmsBaseResult validateParcelInfo(AppParcelCommand pc, PmsBaseResult baseResult) {
        String mailNo = pc.getMailNo();
        String lpcode = pc.getLpcode();
        String o2oShopCode = pc.getO2oShopCode();
        // 1. 不处理运单号+门店+物流商为空的数据
        if (!StringUtils.hasText(mailNo) || !StringUtils.hasText(lpcode) || !StringUtils.hasText(o2oShopCode)) {
            return constructResponse(pc, PmsBaseResult.STATUS_ERROR, "請求數據為空", PmsBaseResult.ERROR_CODE_60001);
        }

        Parcel parcel = findByLpcodeAndMailNo(mailNo, lpcode, null);
        // 2. 根據運單號[{}]和物流商[{}]查詢不到包裹
        if (parcel == null) {
            return constructResponse(pc, PmsBaseResult.STATUS_ERROR, MessageFormat.format("根據運單號[{0}]和物流商[{1}]查詢不到包裹", mailNo, lpcode), PmsBaseResult.ERROR_CODE_70001);
        }

        // 3. 匹配门店编码,确认当前门店是否可以收货
        /**
         * 是否需要校验门店编码 0 or null :需要校验 1：不需要校验
         */
        if ((StringUtils.hasText(parcel.getDestinationCode()) && !parcel.getDestinationCode().equals(o2oShopCode)) && (!StringUtils.hasText(pc.getExtend()) || "0".equals(pc.getExtend()))) {
            return constructResponse(pc, PmsBaseResult.STATUS_ERROR, MessageFormat.format("根據運單號[{0}]和物流商[{1}]查詢到包裹不屬於當前自提點,簽收失敗", mailNo, lpcode), PmsBaseResult.ERROR_CODE_70002);
        }
        return baseResult;
    }

    @Override
    public PmsBaseResult updateParcel(AppParcelCommand pc) {
        /**
         * 异常包裹更新接口 後續需補充 1.PAC退單節點是待倉庫入庫，或前節點 2.WMS未入庫
         */
        PmsBaseResult baseResult = null;
        if (pc == null) {
            return constructResponse(null, PmsBaseResult.STATUS_ERROR, "請求數據為空", PmsBaseResult.ERROR_CODE_60001);
        }

        if ("004".equals(pc.getOpType())) {
            try {
                // 校验数据
                // 原包裹號存在
                Parcel parcel = parcelDao.findByLpcodeAndMailNo(pc.getMailNo(), pc.getLpcode(), null);
                if (parcel == null) {
                    return constructResponse(pc, PmsBaseResult.STATUS_ERROR, "根據運單號[{}]和物流商[{}]查詢不到包裹", PmsBaseResult.ERROR_CODE_70001);
                }

                // 新包裹號不存在
                Parcel parcel2 = parcelDao.findByLpcodeAndMailNo(pc.getSlipMailNo(), pc.getSlipLpcode(), null);
                if (parcel2 != null) {
                    return constructResponse(pc, PmsBaseResult.STATUS_ERROR, "当前運單號[{0}]已存在，更新失敗", PmsBaseResult.ERROR_CODE_70008);
                }

                // 当前運單號[{0}]已存在，更新失敗
                int index =
                        parcelDao.updateMailByLpcodeAndMailNo(pc.getSlipMailNo(), pc.getSlipLpcode(), pc.getMailNo(), pc.getLpcode(),
                                MessageFormat.format("【old[{0}]，[{1}]，new[{2}]，[{3}]】", pc.getMailNo(), pc.getLpcode(), pc.getSlipMailNo(), pc.getSlipLpcode()));
                if (index > 0) {
                    baseResult = constructResponse(pc, PmsBaseResult.STATUS_SUCCESS, "更新成功", null);
                } else {
                    baseResult = constructResponse(pc, PmsBaseResult.STATUS_ERROR, "數據更新失敗,請聯繫客服", PmsBaseResult.ERROR_CODE_70009);
                }
            } catch (Exception e) {
                baseResult = constructResponse(pc, PmsBaseResult.STATUS_ERROR, "PMS系統異常", PmsBaseResult.ERROR_CODE_60003);
            }
        }
        return baseResult;
    }

}
