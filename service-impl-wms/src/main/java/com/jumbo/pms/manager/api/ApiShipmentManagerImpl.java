package com.jumbo.pms.manager.api;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.connector.sf.model.order.request.OrderOption;
import cn.baozun.bh.connector.sf.rmi.RmiService;

import com.jumbo.dao.pms.parcel.BranchLibraryDao;
import com.jumbo.dao.pms.parcel.LogisticsProviderBranchRelationDao;
import com.jumbo.dao.pms.parcel.ParcelInfoDao;
import com.jumbo.dao.pms.parcel.ParcelInfoLineDao;
import com.jumbo.dao.pms.parcel.ParcelTransDetailDao;
import com.jumbo.dao.pms.parcel.ShipmentDao;
import com.jumbo.dao.pms.parcel.SysInterfaceQueueDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.pms.model.BranchLibrary;
import com.jumbo.pms.model.LogisticsProviderBranchRelation;
import com.jumbo.pms.model.Parcel;
import com.jumbo.pms.model.ParcelStatus;
import com.jumbo.pms.model.ParcelTransDetail;
import com.jumbo.pms.model.SysInterfaceQueue;
import com.jumbo.pms.model.SysInterfaceQueueStatus;
import com.jumbo.pms.model.SysInterfaceQueueSysType;
import com.jumbo.pms.model.SysInterfaceQueueType;
import com.jumbo.pms.model.command.ParcelInfoCommand;
import com.jumbo.pms.model.command.ParcelInfoLineCommand;
import com.jumbo.pms.model.command.ParcelInfoQueryCommand;
import com.jumbo.pms.model.command.ParcelResult;
import com.jumbo.pms.model.command.ShipmentCommand;
import com.jumbo.pms.model.command.cond.ParcelSFResponse;
import com.jumbo.pms.model.command.cond.PgPackageCreateCommand;
import com.jumbo.pms.model.command.cond.PgPackageCreateCond;
import com.jumbo.pms.model.command.vo.CreateLogisticsOrderVo;
import com.jumbo.pms.model.command.vo.GetParcelInfoVo;
import com.jumbo.pms.model.command.vo.ParcelUpdateMailNoVo;
import com.jumbo.pms.model.command.vo.ParcelUpdateStatusVo;
import com.jumbo.pms.model.command.vo.ShipmentInTransitVo;
import com.jumbo.pms.model.parcel.ParcelInfo;
import com.jumbo.pms.model.parcel.ParcelInfoLine;
import com.jumbo.pms.model.parcel.Shipment;
import com.jumbo.rmi.warehouse.PmsBaseResult;
import com.jumbo.webservice.sfNew.SfOrderWebserviceClientInter;
import com.jumbo.webservice.sfNew.model.SfOrder;
import com.jumbo.webservice.sfNew.model.SfOrderOption;
import com.jumbo.webservice.sfNew.model.SfOrderResponse;
import com.jumbo.webservice.sfNew.model.SfResponse;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.model.system.ChooseOption;

@Transactional
@Service("apiShipmentManager")
public class ApiShipmentManagerImpl extends BaseManagerImpl implements ApiShipmentManager {

    /**
     * 
     */
    private static final long serialVersionUID = 5380881358880899553L;
    /**
	 * 
	 */
    @Autowired
    private ParcelInfoDao parcelInfoDao;
    @Autowired
    private ParcelTransDetailDao parcelTransDetailDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private SysInterfaceQueueDao sysInterfaceQueueDao;
    @Autowired
    private ParcelInfoLineDao parcelInfoLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private BranchLibraryDao branchLibraryDao;
    @Autowired
    private LogisticsProviderBranchRelationDao logisticsProviderBranchRelationDao;
    @Autowired
    private RmiService sfRmiService;
    @Autowired
    private ShipmentDao shipmentDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private SfOrderWebserviceClientInter sfOrderWebserviceClient;



    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String getParcelCode(ParcelInfo parcelInfo) {
        return sequenceManager.getCode(Parcel.class.getName(), parcelInfo);
    }

    @Override
    public List<ParcelResult> updateParcelStatus(String channelCode, List<ParcelUpdateStatusVo> parcelUpdateStatusVos) {
        /**
         * PMS更新订单包裹状态(门店已签收/顾客已签收) 校验当前包裹+门店 确认当前门店是否可以收货 如果通过校验,记录包裹状态 顾客已签收 需要通知PAC修改订单状态
         * 
         */
        log.debug("---------ApiShipmentManagerImpl.updateParcelStatus() start--------------------------------------------------");
        List<ParcelResult> baseResultList = new ArrayList<ParcelResult>();
        if (parcelUpdateStatusVos == null || parcelUpdateStatusVos.size() == 0) {
            baseResultList.add(constructRequest(null, ParcelResult.STATUS_ERROR, "请求数据为空", PmsBaseResult.ERROR_CODE_60001));
            return baseResultList;
        }

        for (ParcelUpdateStatusVo pc : parcelUpdateStatusVos) {
            baseResultList.add(updateParcelSingel(pc));
        }
        log.debug("---------ApiShipmentManagerImpl.updateParcelStatus() end--------------------------------------------------");
        return baseResultList;
    }

    private ParcelResult updateParcelSingel(ParcelUpdateStatusVo pc) {
        String code = pc.getCode();
        try {
            // 校验数据
            ParcelInfoQueryCommand parcelInfo = parcelInfoDao.findByCode(code);
            if (parcelInfo == null) {
                return constructRequest(code, pc.getExtend(), ParcelResult.STATUS_ERROR, "查无此件", ParcelResult.ERROR_CODE_70001, null);
            }

            boolean checkParcelStatus = Boolean.FALSE;
            String opType = pc.getOpType();

            // 当前状态是否允许签收
            if ("002".equals(opType)) {// 门店已签收
                checkParcelStatus = checkParcelStatus(parcelInfo.getStatus(), ParcelStatus.parcel_delivered.getValue());
            } else if ("003".equals(opType)) {// 顾客已签收
                checkParcelStatus = checkParcelStatus(parcelInfo.getStatus(), ParcelStatus.parcel_Received_by_Store.getValue());
            }

            // 校验包裹是否已签收
            if (!checkParcelStatus) {
                // 先校验包裹是否已经被签收,如果是,直接反馈牵手成功,不做任何操作 ~
                if ("002".equals(opType)) {// 门店已签收
                    checkParcelStatus = checkParcelStatus(parcelInfo.getStatus(), ParcelStatus.parcel_Received_by_Store.getValue());
                } else if ("003".equals(opType)) {// 顾客已签收
                    checkParcelStatus = checkParcelStatus(parcelInfo.getStatus(), ParcelStatus.parcel_Picked_by_Customer.getValue());
                }

                if (checkParcelStatus) {
                    return constructRequest(code, pc.getExtend(), PmsBaseResult.STATUS_SUCCESS, MessageFormat.format("包裹[{0}]重复签收", code), null, null);
                }
                String statusValue = findByCategoryCodeAndKey("parcelStatus", parcelInfo.getStatus().toString());
                return constructRequest(code, pc.getExtend(), PmsBaseResult.STATUS_ERROR, MessageFormat.format("包裹[{0}]当前状态是[{1}]，签收失败", parcelInfo.getMailNo(), statusValue), ParcelResult.ERROR_CODE_70006, null);
            }

            Integer changeStatus = "002".equals(opType) ? ParcelStatus.parcel_Received_by_Store.getValue() : ParcelStatus.parcel_Picked_by_Customer.getValue();
            // 5. 校验通过,包裹更新状态, 若是顾客已签收,需求通知PAC更新订单状态到完成
            parcelInfoDao.updateByCode(code, changeStatus == ParcelStatus.parcel_Received_by_Store.getValue() ? new Date() : null, changeStatus == ParcelStatus.parcel_Picked_by_Customer.getValue() ? new Date() : null, changeStatus);
            saveDetailAndQueue(code, changeStatus);
            return constructRequest(code, pc.getExtend(), PmsBaseResult.STATUS_SUCCESS, null, null, null);
        } catch (Exception e) {
            return constructRequest(code, pc.getExtend(), PmsBaseResult.STATUS_ERROR, "签收异常", ParcelResult.ERROR_CODE_70003, null);
        }
    }

    /**
     * create Shipment API
     * 
     * @param sta
     */
    @Override
    public List<ParcelResult> createShipment(String opType, ShipmentInTransitVo shipmentInTransitVo) {
        /**
         * 场景1 ： OMS拆单 WMS拆包裹
         * 
         * 1. 校验数据是否已存在,如存在不做任何处理 2. 封装数据,然后保存 3. 记录日志
         * 
         */
        if (shipmentInTransitVo == null) {
            return null;
        }
        List<ParcelResult> parcelResults = new ArrayList<ParcelResult>();
        try {
            // create Shipment
            Shipment shipment = checkAndSaveShipment(shipmentInTransitVo);
            List<ParcelInfoCommand> parcelInfoCommands = shipmentInTransitVo.getParcelInfoCommands();
            for (ParcelInfoCommand parcelInfoCommand : parcelInfoCommands) {
                // create parcelInfo
                String code = createParcelInfo(opType, shipment, parcelInfoCommand);
                if (!StringUtils.hasText(code)) {
                    log.error("ApiShipmentManagerImpl.createShipment Parcel exists Execution error , omsOrderCode [" + shipmentInTransitVo.getOmsOrderCode() + "], please check~");
                    parcelResults.add(constructRequest(code, null, ParcelResult.STATUS_ERROR, "快递单号已存在", ParcelResult.ERROR_CODE_70011, null));
                    return parcelResults;
                }
                // 记录日志
                saveParcelTransDetail(code, ParcelStatus.parcel_delivered.getValue());
                /**
                 * 门店包裹推送SD 门店配货 & 门店自提 不需要通知门店 门店自动流转至门店已签收 所以包裹服务需要自行扭转
                 * */
                if (StringUtils.hasText(shipment.getDestinationCode()) && ShipmentInTransitVo.OPTYPE_HAS_MAILNO.equals(opType)) {
                    saveSysInterfaceQueue(code, SysInterfaceQueueType.PARCEL_DELIVERED_NOTIFY_SHOPDOG, SysInterfaceQueueSysType.G_SHOPDOG, SysInterfaceQueueStatus.STATUS_NEW);
                }

                if (ShipmentInTransitVo.OPTYPE_HASNOT_MAILNO.equals(opType)) {
                    ParcelUpdateStatusVo parcelUpdateStatusVo = new ParcelUpdateStatusVo();
                    parcelUpdateStatusVo.setCode(code);
                    parcelUpdateStatusVo.setOpType(ParcelUpdateStatusVo.OPTYPE_STORE);
                    updateParcelSingel(parcelUpdateStatusVo);
                }
                parcelResults.add(constructRequest(code, null, ParcelResult.STATUS_SUCCESS, null, null, null));
            }
        } catch (Exception e) {
            log.error("AppParcelManagerImpl.staInTransitNotifyPMS Execution error , orderCode [" + shipmentInTransitVo.getOmsOrderCode() + "], please check~");
            parcelResults.add(constructRequest(shipmentInTransitVo.getOmsOrderCode(), null, ParcelResult.STATUS_ERROR, "Running error...", ParcelResult.ERROR_CODE_60003, null));
        }
        return parcelResults;
    }

    /**
     * 
     * @param channelCode
     * @param shipmentCommand
     * @return
     */
    private Shipment checkAndSaveShipment(ShipmentCommand shipmentCommand) {
        Integer type = shipmentCommand.getType();
        Shipment shipment = shipmentDao.findByOmsOrderCode(null == type || ShipmentInTransitVo.ENTRANCE_SO == type ? shipmentCommand.getOmsOrderCode() : shipmentCommand.getOmsRaCode(), type);
        if (shipment != null) {
            return shipment;
        }
        shipment = new Shipment();
        org.springframework.beans.BeanUtils.copyProperties(shipmentCommand, shipment);
        shipment.setChannelCode(shipmentCommand.getChannelCode());
        shipment.setType(type);
        shipment = shipmentDao.save(shipment);
        return shipment;
    }

    private String createParcelInfo(String opType, Shipment shipment, ParcelInfoCommand parcelInfoCommand) {
        String mailNo = parcelInfoCommand.getMailNo();
        String lpcode = parcelInfoCommand.getLpcode();
        ParcelInfo parcelInfo = null;
        /**
         * opType == 002 门店配货 && 门店自提 门店自提&&门店配货的包裹没有运单号，无需查询
         */
        if (ShipmentInTransitVo.OPTYPE_HAS_MAILNO.equals(opType)) {
            parcelInfo = findByLpcodeAndMailNoAndPFCode(mailNo, lpcode, !StringUtils.hasText(shipment.getPlatformOrderCode()) ? null : shipment.getPlatformOrderCode());
        }
        /** 包裹主档 运单号+物流商 为唯一键 */
        if (parcelInfo == null) {
            parcelInfo = new ParcelInfo();
        } else {
            log.debug("AppParcelManagerImpl.staInTransitNotifyPMS mailNo[{}],lpcode[{}] has created~ [{}]", new Object[] {mailNo, lpcode});
            return null;
        }
        org.springframework.beans.BeanUtils.copyProperties(parcelInfoCommand, parcelInfo);
        String code = getParcelCode(parcelInfo);
        parcelInfo.setCode(code);
        parcelInfo.setStatus(ParcelStatus.parcel_delivered.getValue());
        parcelInfo.setShipmentId(shipment.getId());
        parcelInfo.setCreateTime(new Date());
        parcelInfo.setDeliveryTime(null == parcelInfoCommand.getDeliveryTime() ? new Date() : parcelInfoCommand.getDeliveryTime());
        parcelInfoDao.save(parcelInfo);
        ParcelInfoLine parcelInfoLine = null;
        List<ParcelInfoLineCommand> infoLineCommands = parcelInfoCommand.getParcelInfoLineCommands();
        if (infoLineCommands != null && infoLineCommands.size() > 0) {
            for (ParcelInfoLineCommand lineCommand : infoLineCommands) {
                parcelInfoLine = new ParcelInfoLine();
                org.springframework.beans.BeanUtils.copyProperties(lineCommand, parcelInfoLine);
                parcelInfoLine.setParcelId(parcelInfo.getId());
                parcelInfoLineDao.save(parcelInfoLine);
            }
        }
        parcelInfoDao.flush();
        return code;
    }

    public String findByCategoryCodeAndKey(String categoryCode, String key) {
        ChooseOption option = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, key);
        return option.getOptionValue();
    }


    /**
     * 封装反馈数据
     * 
     * @param parcelCode
     * @param extend
     * @param status
     * @param msg
     * @param errorCode
     * @return
     */

    private ParcelResult constructRequest(String parcelCode, String extend, int status, String msg, Integer errorCode, String slipCode) {
        ParcelResult parcelResult = constructRequest(slipCode, status, msg, errorCode);
        parcelResult.setParcelCode(parcelCode);
        parcelResult.setExtend(extend);
        return parcelResult;
    }

    /**
     * 封装反馈数据
     * 
     * @param status
     * @param msg
     * @param errorCode
     * @return
     */

    private ParcelResult constructRequest(String slipCode, int status, String msg, Integer errorCode) {
        ParcelResult parcelResult = new ParcelResult();
        parcelResult.setSlipCode(slipCode);
        parcelResult.setStatus(status);
        parcelResult.setMsg(msg);
        // 异常信息
        parcelResult.setErrorCode(errorCode);
        return parcelResult;
    }

    @Override
    public Map<String, Object> createLogisticsOrder(String channelCode, String opType, CreateLogisticsOrderVo createLogisticsOrderVo) {
        log.debug("------------------------ApiShipmentManagerImpl.createLogisticsOrder Start -------------------------------");
        Map<String, Object> requestMap = new HashMap<String, Object>();
        /**
         * 1. 校验为空 2. 校验是否重复申请运单号，如果存在，直接反馈 3. 多个物流商的校验，优先级，如果物流不能送达，尝试下一个物流，如果还是不OK，直接反馈失败 4.
         * 调用物流商，封装物流面单数据 5. 记录包裹信息
         */
        // 1
        if (createLogisticsOrderVo == null) {
            requestMap.put("parcelResult", constructRequest(null, ParcelResult.STATUS_ERROR, "请求数据为空", ParcelResult.ERROR_CODE_60001));
            return requestMap;
        }

        // 2
        String lpCode = createLogisticsOrderVo.getLpCode();
        String mailNo = createLogisticsOrderVo.getMailNo();
        if (StringUtils.hasText(mailNo) && StringUtils.hasText(lpCode)) {
            if ("SF".equals(lpCode)) {
                ParcelSFResponse parcelInfo = null;
                if (StringUtils.hasText(createLogisticsOrderVo.getOmsRaCode())) {
                    parcelInfo = shipmentDao.findByMultiCodeForResponse(null, null, null, createLogisticsOrderVo.getOmsRaCode(), mailNo);
                } else {
                    parcelInfo = shipmentDao.findByMultiCodeForResponse(null, createLogisticsOrderVo.getOmsOrderCode(), null, null, mailNo);
                }
                if (parcelInfo != null) {
                    requestMap.put(lpCode, parcelInfo);
                    requestMap.put("parcelResult", constructRequest(lpCode, ParcelResult.STATUS_SUCCESS, null, null));
                    return requestMap;
                }
            }
        }

        // 1.1
        String originCode = createLogisticsOrderVo.getOriginCode();
        String destinationCode = createLogisticsOrderVo.getDestinationCode();
        if (!StringUtils.hasText(originCode)) {
            requestMap.put("parcelResult", constructRequest(null, ParcelResult.STATUS_ERROR, "数据非法, 请求失败", ParcelResult.ERROR_CODE_60002));
            return requestMap;
        }

        // 获取发货点
        BranchLibrary originLibrary = branchLibraryDao.findBySlipCode(originCode);
        if (originLibrary == null) {
            requestMap.put("parcelResult", constructRequest(null, ParcelResult.STATUS_ERROR, "门店编码[{}]不存在", ParcelResult.ERROR_CODE_70005));
            return requestMap;
        }

        // 3
        // 获取默认物流商，以后需要转换成获取全部物流商，再去匹配优先级
        LogisticsProviderBranchRelation relation = logisticsProviderBranchRelationDao.findByBlIdAndPriority(originLibrary.getId(), 1);
        if (relation == null) {
            requestMap.put("parcelResult", constructRequest(null, ParcelResult.STATUS_ERROR, "门店没有配置物流商", ParcelResult.ERROR_CODE_70010));
            return requestMap;
        }

        /**
         * 如果是退货 需要校验目的仓
         */
        BranchLibrary destinationLibrary = null;
        // opType == 002 表示退货，如果是为空或者其他，表示门店配货
        if ("002".equals(opType)) {
            if (!StringUtils.hasText(destinationCode)) {
                requestMap.put("parcelResult", constructRequest(null, ParcelResult.STATUS_ERROR, "数据非法, 请求失败", ParcelResult.ERROR_CODE_60002));
                return requestMap;
            }

            /**
             * 收货信息
             */
            destinationLibrary = branchLibraryDao.findBySlipCode(destinationCode);
            if (destinationLibrary == null) {
                requestMap.put("parcelResult", constructRequest(null, ParcelResult.STATUS_ERROR, "仓库[{0}]不存在", ParcelResult.ERROR_CODE_70007));
                return requestMap;
            }
            if (!StringUtils.hasText(createLogisticsOrderVo.getReceiver())) createLogisticsOrderVo.setReceiver(destinationLibrary.getContact());
            if (!StringUtils.hasText(createLogisticsOrderVo.getReceiverMobile())) createLogisticsOrderVo.setReceiverMobile(destinationLibrary.getMobile());
        }

        lpCode = relation.getLogisticsProvider().getCode();
        createLogisticsOrderVo.setType("002".equals(opType) ? ShipmentInTransitVo.ENTRANCE_RO : ShipmentInTransitVo.ENTRANCE_SO);
        if (!StringUtils.hasText(createLogisticsOrderVo.getSender())) createLogisticsOrderVo.setSender(originLibrary.getContact());
        if (!StringUtils.hasText(createLogisticsOrderVo.getSenderMobile())) createLogisticsOrderVo.setSenderMobile(originLibrary.getMobile());
        if (!StringUtils.hasText(createLogisticsOrderVo.getAddress())) createLogisticsOrderVo.setAddress(destinationLibrary.getAddress());
        Shipment shipment = checkAndSaveShipment(createLogisticsOrderVo);
        int parcelCount = shipment.getParcelCount() == null ? 1 : shipment.getParcelCount().intValue() + 1;
        // 4
        if ("SF".equals(relation.getLogisticsProvider().getCode())) {
            mailNo = createSfOrder(channelCode, destinationLibrary, originLibrary, createLogisticsOrderVo);
            if (!StringUtils.hasText(mailNo)) {
                requestMap.put("parcelResult", constructRequest(null, ParcelResult.STATUS_ERROR, "调用物流商接口失败", ParcelResult.ERROR_CODE_70004));
                return requestMap;
            }

        }

        // 5
        List<ParcelInfoLineCommand> parcelInfoLineCommands = createLogisticsOrderVo.getParcelInfoLineCommands();
        ParcelInfoCommand pc = new ParcelInfoCommand();
        pc.setMailNo(mailNo);
        pc.setLpcode(lpCode);
        pc.setShipmentContents(createLogisticsOrderVo.getShipmentContents());
        pc.setTotalQty(null == createLogisticsOrderVo.getCargoCount() ? 0L : new Long(createLogisticsOrderVo.getCargoCount()));
        pc.setParcelInfoLineCommands(parcelInfoLineCommands);
        String code = createParcelInfo(null, shipment, pc);
        // 修改包裹数量
        shipmentDao.updateParcelCountById(shipment.getId());
        // 记录日志
        saveParcelTransDetail(code, ParcelStatus.apply_parcel_code.getValue());
        // 通知SF上门取件
        saveSysInterfaceQueue(code, SysInterfaceQueueType.PARCEL_PICK_UP_NOTIFY, SysInterfaceQueueSysType.LP, SysInterfaceQueueStatus.STATUS_NEW);
        // 通知大陆SD包裹到店
        saveSysInterfaceQueue(code, SysInterfaceQueueType.PARCEL_DELIVERED_NOTIFY_SHOPDOG, SysInterfaceQueueSysType.G_SHOPDOG, SysInterfaceQueueStatus.STATUS_NEW);
        // 封装数据返回
        ParcelSFResponse parcelSFResponse = structureParcelSFResponse(originLibrary, destinationLibrary, mailNo, createLogisticsOrderVo, parcelCount, shipment.getRemark(), relation);
        requestMap.put("parcelResult", constructRequest(code, null, ParcelResult.STATUS_SUCCESS, null, null, lpCode));
        requestMap.put(lpCode, parcelSFResponse);
        log.debug("------------------------ApiShipmentManagerImpl.createLogisticsOrder End -------------------------------");
        return requestMap;
    }

    /**
     * ParcelSFResponse
     */
    public ParcelSFResponse structureParcelSFResponse(BranchLibrary originLibrary, BranchLibrary destinationLibrary, String mailNo, CreateLogisticsOrderVo createLogisticsOrderVo, int parcelCount, String remark, LogisticsProviderBranchRelation relation) {
        ParcelSFResponse parcelSFResponse = new ParcelSFResponse();
        parcelSFResponse.setAccountNo(originLibrary.getAccountNo());
        parcelSFResponse.setMailNo(mailNo);
        parcelSFResponse.setLpCode(relation.getLogisticsProvider().getCode());
        parcelSFResponse.setLpName(relation.getLogisticsProvider().getName());
        parcelSFResponse.setOriginRegionCode(originLibrary.getRegionCode());
        parcelSFResponse.setChargeTo(originLibrary.getPayMethod() == null ? "1" : originLibrary.getPayMethod().toString()); // 1代表寄方付
        if (null == destinationLibrary) {
            parcelSFResponse.setDestinationRegionCode(null);
            parcelSFResponse.setReceiver(createLogisticsOrderVo.getReceiver());
            parcelSFResponse.setReceiverMobile(createLogisticsOrderVo.getReceiverMobile());
            parcelSFResponse.setReceiverAddress(createLogisticsOrderVo.getAddress());
        } else {
            parcelSFResponse.setDestinationRegionCode(destinationLibrary.getRegionCode());
            parcelSFResponse.setReceiver(destinationLibrary.getContact());
            parcelSFResponse.setReceiverMobile(destinationLibrary.getMobile());
            parcelSFResponse.setReceiverAddress(destinationLibrary.getAddress());
        }
        parcelSFResponse.setSender(originLibrary.getContact());
        parcelSFResponse.setSenderMobile(originLibrary.getMobile());
        parcelSFResponse.setShipperAddress(originLibrary.getAddress());
        parcelSFResponse.setSfMobile(ParcelSFResponse.SF_MOBILE);
        parcelSFResponse.setShippingDate(new Date());
        parcelSFResponse.setShippingSignature(originLibrary.getBranchName());
        parcelSFResponse.setQuantity(parcelCount);
        parcelSFResponse.setShipmentContents("Clothing");
        parcelSFResponse.setRemarks(remark);
        parcelSFResponse.setSlipCode(createLogisticsOrderVo.getSlipCode());
        return parcelSFResponse;
    }

    /**
     * 封装数据,调用SF接口,通知SF上门取件
     * 
     * @param command
     * @param relation
     * @return
     */
    public String createSfOrder(String channelCode, BranchLibrary destinationLibrary, BranchLibrary originLibrary, CreateLogisticsOrderVo createLogisticsOrderVo) {
        SfOrder order = new SfOrder();
        String orderId = sequenceManager.getCode(SfOrder.class.getName(), order);
        order.setOrderId(orderId);
        order.setExpressType("1");
        order.setPayMethod(SfOrder.SF_PAYMETHOD_TYPE_SEND_PARTY);
        SfOrderOption option = new SfOrderOption();
        // 到件方
        if (destinationLibrary == null) {
            order.setdContact(createLogisticsOrderVo.getContact());
            order.setdTel(createLogisticsOrderVo.getTelephone());
            order.setdProvince(createLogisticsOrderVo.getProvince());
            order.setdCity(createLogisticsOrderVo.getCity());
            order.setdAddress(createLogisticsOrderVo.getAddress());
            option.setdCounty(createLogisticsOrderVo.getCountry());
            option.setdMobile(createLogisticsOrderVo.getReceiverMobile());
        } else {
            order.setdContact(destinationLibrary.getContact());
            order.setdTel(destinationLibrary.getTelephone());
            order.setdProvince(destinationLibrary.getProvince());
            order.setdCity(destinationLibrary.getCity());
            order.setdAddress(destinationLibrary.getAddress());
            option.setdCounty(destinationLibrary.getCounty());
            option.setdMobile(destinationLibrary.getMobile());
        }

        // 寄件方
        order.setjAddress(originLibrary.getAddress());
        order.setjProvince(originLibrary.getProvince());
        order.setjCity(originLibrary.getCity());
        order.setjContact(originLibrary.getContact());
        order.setjTel(originLibrary.getTelephone());
        order.setjCompany(originLibrary.getBranchName());



        option.setCustid(originLibrary.getCustid());
        option.setCargoCount(createLogisticsOrderVo.getCargoCount() == null ? "1" : createLogisticsOrderVo.getCargoCount().toString());
        // 获取 SF顾客编码
        String sfClientCode = findByCategoryCodeAndKey(Constants.SF_WEB_SERVICE_INFO, Constants.SF_WEB_SERVICE_INFO_CODE);
        // 获取 SF checkWord
        String checkword = findByCategoryCodeAndKey(Constants.SF_WEB_SERVICE_INFO, Constants.SF_WEB_SERVICE_INFO_CHECKWORD);
        option.setjMobile(originLibrary.getMobile());
        order.setOrderOption(option);
        SfResponse rs = null;
        String mailNo = null;
        try {
            rs = sfOrderWebserviceClient.creSfOrder(order, sfClientCode, checkword, 0);
        } catch (Exception e) {
            return mailNo;
        }

        if (rs == null || rs.getHead() == null || !rs.getHead().equals(SfResponse.STATUS_OK)) {
            return mailNo;
        } else if (rs.getHead().equals(SfResponse.STATUS_OK)) {
            SfOrderResponse response = (SfOrderResponse) rs.getBodyObj();
            mailNo = response.getMailno();
        }
        return mailNo;
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

    public PmsBaseResult generateParcelInfo(ParcelSFResponse parcel) {
        PmsBaseResult baseResult = new PmsBaseResult();
        Shipment shipment = shipmentDao.getByPrimaryKey(parcel.getShipmentId());
        if (shipment != null) {
            BranchLibrary originLibrary = branchLibraryDao.findBySlipCode(shipment.getOriginCode());
            BranchLibrary destinationLibrary = branchLibraryDao.findBySlipCode(shipment.getDestinationCode());
            baseResult.setAccountNo(originLibrary.getAccountNo());
            baseResult.setChargeTo(originLibrary.getPayMethod() == null ? "1" : originLibrary.getPayMethod().toString()); // 1代表寄方付
            baseResult.setOriginRegionCode(originLibrary.getRegionCode());
            baseResult.setDestinationRegionCode(destinationLibrary.getRegionCode());
            baseResult.setLpCode(parcel.getLpCode());
            baseResult.setMailNo(parcel.getMailNo());
            baseResult.setReceiver(shipment.getReceiver());
            baseResult.setReceiverAddress(shipment.getAddress());
            baseResult.setReceiverMobile(shipment.getReceiverMobile());
            baseResult.setO2oShopAddress(originLibrary.getAddress());
            baseResult.setShipperSignature(originLibrary.getBranchNameEn());
            baseResult.setStatus(PmsBaseResult.STATUS_SUCCESS);
        }
        return baseResult;
    }

    public ParcelInfo findByLpcodeAndMailNoAndPFCode(String mailNo, String lpcode, String platformOrderCode) {
        return parcelInfoDao.findByLpcodeAndMailNoAndPFCode(mailNo, lpcode, platformOrderCode);
    }

    private boolean checkParcelStatus(Integer currentParcelStatus, Integer correctParcelStatus) {
        if (currentParcelStatus.equals(correctParcelStatus)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private void saveDetailAndQueue(String code, Integer changeStatus) {
        // 记录日志
        saveParcelTransDetail(code, changeStatus);

        if (ParcelStatus.parcel_Picked_by_Customer.getValue() == changeStatus) {
            // 顾客已签收需要通知PAC更新订单状态,PAC订单一定允许SF刷完成
            saveSysInterfaceQueue(code, SysInterfaceQueueType.PARCEL_CHANGE_STATUS_NOTIFY_PAC, SysInterfaceQueueSysType.PACS, SysInterfaceQueueStatus.STATUS_NEW);
        }
    }

    private void saveParcelTransDetail(String slipCode, Integer changeStatus) {
        // 记录包裹路由信息
        ParcelTransDetail detail = new ParcelTransDetail();
        detail.setAcceptTime(new Date());
        detail.setParcelCode(slipCode);
        detail.setRemark(findByCategoryCodeAndKey("parcelStatus", changeStatus + ""));
        parcelTransDetailDao.save(detail);
    }

    private void saveSysInterfaceQueue(String parcelCode, SysInterfaceQueueType type, SysInterfaceQueueSysType targetType, SysInterfaceQueueStatus status) {
        SysInterfaceQueue queue = new SysInterfaceQueue();
        queue.setCode(parcelCode);
        queue.setTargetSys(targetType);
        queue.setType(type);
        queue.setStatus(status);
        sysInterfaceQueueDao.save(queue);
    }

    @Override
    public ParcelResult updateParcelMailNo(ParcelUpdateMailNoVo parcelUpdateMailVo) {
        String code = parcelUpdateMailVo.getCode();
        // 校验数据
        ParcelInfoQueryCommand parcelInfo = parcelInfoDao.findByCode(code);
        if (null == parcelInfo) {
            return constructRequest(code, parcelUpdateMailVo.getExtend(), ParcelResult.STATUS_ERROR, "查无此件", ParcelResult.ERROR_CODE_70001, null);
        }
        String mailNo = parcelUpdateMailVo.getNewMailNo();
        String lpcode = parcelUpdateMailVo.getNewLpcode();

        // 新包裹號不存在
        ParcelInfo newParcelInfo = findByLpcodeAndMailNoAndPFCode(mailNo, lpcode, null);
        if (null != newParcelInfo) {
            return constructRequest(code, parcelUpdateMailVo.getExtend(), ParcelResult.STATUS_ERROR, "当前运单号已存在，更新失败", ParcelResult.ERROR_CODE_70008, null);
        }

        // 更新操作
        int update = parcelInfoDao.updateMailNoByCode(code, mailNo, lpcode);
        if (update < 1) {
            return constructRequest(code, parcelUpdateMailVo.getExtend(), ParcelResult.STATUS_ERROR, "系统更新失败", ParcelResult.ERROR_CODE_60003, null);
        }
        return constructRequest(code, parcelUpdateMailVo.getExtend(), ParcelResult.STATUS_SUCCESS, "更新成功", null, null);
    }

    @Override
    public PgPackageCreateCond getParcelInfo(GetParcelInfoVo vo) {
        /**
         * 1.校验运单号+物流商是否存在包裹服务 1.1 存在就封装数据 1.2 不存在 status = 0 errorCode = 70001
         */
        if (null == vo) {
            return constructRequest(ParcelResult.STATUS_ERROR, "请求数据为空", ParcelResult.ERROR_CODE_60001);
        }

        if (!StringUtils.hasText(vo.getMailNo()) && (null == vo.getChannelCodes() || vo.getChannelCodes().size() == 0)) {
            return constructRequest(ParcelResult.STATUS_ERROR, "数据非法, 请求失败", ParcelResult.ERROR_CODE_60002);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mailNo", vo.getMailNo());
        params.put("lpCode", vo.getLpCode());
        params.put("channelCodes", vo.getChannelCodes());

        ParcelInfoQueryCommand parcelInfo = parcelInfoDao.findByParams(params);
        if (null == parcelInfo) {
            return constructRequest(ParcelResult.STATUS_ERROR, "查无此件", ParcelResult.ERROR_CODE_70001);
        }

        PgPackageCreateCommand pgPackageCommand = null;
        Date deliveryTime = (null == parcelInfo.getDeliveryTime() ? new Date() : parcelInfo.getDeliveryTime());
        pgPackageCommand = new PgPackageCreateCommand();
        pgPackageCommand.setPlatCreateTime(deliveryTime);
        pgPackageCommand.setCreateTime(deliveryTime);
        pgPackageCommand.setIsCod(parcelInfo.getIsCod());
        pgPackageCommand.setTrackNo(parcelInfo.getMailNo());
        String lpCode = parcelInfo.getLpcode();
        pgPackageCommand.setLpCode(lpCode);
        ChooseOption option = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("lpName", lpCode);
        pgPackageCommand.setLpName(option != null ? option.getOptionValue() : null);// 使用chooseOption
        pgPackageCommand.setTranstimeType(parcelInfo.getTransTimeType());
        pgPackageCommand.setTotalCharges(parcelInfo.getTotalCharges());
        pgPackageCommand.setStoreCode(parcelInfo.getDestinationCode());
        pgPackageCommand.setOmsCode(parcelInfo.getOmsOrderCode());
        pgPackageCommand.setOrderCode(parcelInfo.getPlatformOrderCode());
        pgPackageCommand.setReceiver(parcelInfo.getReceiver());
        pgPackageCommand.setReceiverPhone(parcelInfo.getReceiverMobile());
        pgPackageCommand.setRoCode(parcelInfo.getOmsRaCode());
        pgPackageCommand.setShopCode(parcelInfo.getChannelCode());
        pgPackageCommand.setSourceCode(parcelInfo.getOriginCode());
        pgPackageCommand.setExtCode(parcelInfo.getCode());
        pgPackageCommand.setSourceSys(parcelInfo.getSourceSys());
        pgPackageCommand.setShipTime(deliveryTime);
        pgPackageCommand.setTotalQty(null == parcelInfo.getTotalQty() ? 0 : parcelInfo.getTotalQty().intValue());
        PgPackageCreateCond cond = new PgPackageCreateCond();
        cond.setStatus(PgPackageCreateCond.STATUS_SUCCESS);
        cond.setPgPackageCreateCommand(pgPackageCommand);
        return cond;
    }

    private PgPackageCreateCond constructRequest(int status, String msg, Integer errorCode) {
        PgPackageCreateCond parcelResult = new PgPackageCreateCond();
        parcelResult.setStatus(status);
        parcelResult.setMsg(msg);
        // 异常信息
        parcelResult.setErrorCode(errorCode);
        return parcelResult;
    }

}
