package com.jumbo.webservice.pda.manager;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.authorization.PhysicalWarehouseDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.commandMapper.PhysicalWarehouseRowMapper;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PdaOrderDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.rmiservice.RmiServiceImpl;
import com.jumbo.util.FormatUtil;
import com.jumbo.webservice.base.AuthResponseHeader;
import com.jumbo.webservice.pda.BaseResponseBody;
import com.jumbo.webservice.pda.Inventory;
import com.jumbo.webservice.pda.LibraryMovementInOut;
import com.jumbo.webservice.pda.LogicalWarehouse;
import com.jumbo.webservice.pda.PickingDatadetial;
import com.jumbo.webservice.pda.ShelvesSkuDetial;
import com.jumbo.webservice.pda.Sku;
import com.jumbo.webservice.pda.TransNoDetail;
import com.jumbo.webservice.pda.TransNoDetailLine;
import com.jumbo.webservice.pda.Warehouse;
import com.jumbo.webservice.pda.executeOrder.ExecuteOrderRequest;
import com.jumbo.webservice.pda.executeOrder.ExecuteOrderResponse;
import com.jumbo.webservice.pda.getInboundData.GetInboundDataRequest;
import com.jumbo.webservice.pda.getInboundData.GetInboundDataResponse;
import com.jumbo.webservice.pda.getInboundData.GetInboundDataResponseBody;
import com.jumbo.webservice.pda.getInboundOnShelves.GetInboundOnShelvesRequest;
import com.jumbo.webservice.pda.getInboundOnShelves.GetInboundOnShelvesResponse;
import com.jumbo.webservice.pda.getInboundOnShelves.GetInboundOnShelvesResponseBody;
import com.jumbo.webservice.pda.getInventory.GetInventoryRequest;
import com.jumbo.webservice.pda.getInventory.GetInventoryResponse;
import com.jumbo.webservice.pda.getInventory.GetInventoryResponseBody;
import com.jumbo.webservice.pda.getInventoryCheck.GetInventoryCheckRequest;
import com.jumbo.webservice.pda.getInventoryCheck.GetInventoryCheckResponse;
import com.jumbo.webservice.pda.getInventoryCheck.GetInventoryCheckResponseBody;
import com.jumbo.webservice.pda.getLibraryMovement.GetLibraryMovementRequest;
import com.jumbo.webservice.pda.getLibraryMovement.GetLibraryMovementResponse;
import com.jumbo.webservice.pda.getLibraryMovement.GetLibraryMovementResponseBody;
import com.jumbo.webservice.pda.getPickingData.GetPickingDataRequest;
import com.jumbo.webservice.pda.getPickingData.GetPickingDataResponse;
import com.jumbo.webservice.pda.getPickingData.GetPickingDataResponseBody;
import com.jumbo.webservice.pda.getReturnOrder.GetReturnOrderRequest;
import com.jumbo.webservice.pda.getReturnOrder.GetReturnOrderResponse;
import com.jumbo.webservice.pda.getReturnOrder.GetReturnOrderResponseBody;
import com.jumbo.webservice.pda.getSku.GetSkuRequest;
import com.jumbo.webservice.pda.getSku.GetSkuResponse;
import com.jumbo.webservice.pda.getSku.GetSkuResponseBody;
import com.jumbo.webservice.pda.getTransNo.GetTransNoRequest;
import com.jumbo.webservice.pda.getTransNo.GetTransNoResponse;
import com.jumbo.webservice.pda.getTransNo.GetTransNoResponseBody;
import com.jumbo.webservice.pda.getWarehouses.GetWarehousesRequest;
import com.jumbo.webservice.pda.getWarehouses.GetWarehousesResponse;
import com.jumbo.webservice.pda.getWarehouses.GetWarehousesResponseBody;
import com.jumbo.webservice.pda.login.LoginRequest;
import com.jumbo.webservice.pda.login.LoginResponse;
import com.jumbo.webservice.pda.uploadHandOverList.UploadHandOverListRequest;
import com.jumbo.webservice.pda.uploadHandOverList.UploadHandOverListResponse;
import com.jumbo.webservice.pda.uploadInboundOnShelves.UploadInboundOnShelvesRequest;
import com.jumbo.webservice.pda.uploadInboundOnShelves.UploadInboundOnShelvesResponse;
import com.jumbo.webservice.pda.uploadInboundReceive.UploadInboundReceiveRequest;
import com.jumbo.webservice.pda.uploadInboundReceive.UploadInboundReceiveResponse;
import com.jumbo.webservice.pda.uploadInitiativeMoveInbound.UploadInitiativeMoveInboundRequest;
import com.jumbo.webservice.pda.uploadInitiativeMoveInbound.UploadInitiativeMoveInboundResponse;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundRequest;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundResponse;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundResponseBody;
import com.jumbo.webservice.pda.uploadInventoryCheck.UploadInventoryCheckRequest;
import com.jumbo.webservice.pda.uploadInventoryCheck.UploadInventoryCheckResponse;
import com.jumbo.webservice.pda.uploadLibraryMovement.UploadLibraryMovementRequest;
import com.jumbo.webservice.pda.uploadLibraryMovement.UploadLibraryMovementResponse;
import com.jumbo.webservice.pda.uploadPickingOutbound.UploadPickingOutboundRequest;
import com.jumbo.webservice.pda.uploadPickingOutbound.UploadPickingOutboundResponse;
import com.jumbo.webservice.pda.uploadReturnOrder.UploadReturnOrderRequest;
import com.jumbo.webservice.pda.uploadReturnOrder.UploadReturnOrderResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.pda.GetTransNoCommand;
import com.jumbo.wms.model.pda.InboundOnShelvesDetailCommand;
import com.jumbo.wms.model.pda.InboundOnShelvesSkuCommand;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.pda.PdaStockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

@Service("pdaManager")
public class PdaManagerImpl extends BaseManagerImpl implements PdaManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7305824984251674355L;
    protected static final Logger logger = LoggerFactory.getLogger(RmiServiceImpl.class);
    @Autowired
    private PhysicalWarehouseDao physicalWarehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private PdaOperationManager pdaOperationManager;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private PdaOrderDao pdaOrderDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private AuthorizationManager authorizationManager;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse response = new LoginResponse();
        BaseResponseBody brb = new BaseResponseBody();
        try {
            String r = authorizationManager.toCasPwdLogin(loginRequest.getLoginRequestBody().getUser(), loginRequest.getLoginRequestBody().getPassword(), "PDA");

            JSONObject jsonObject = new JSONObject(r);
            // r = new JSONObject(r).get("success");
            if ((Boolean) jsonObject.get("success")) {
                brb.setStatus("1");
                // brb.setResult("认证成功");
                brb.setResult((String) jsonObject.get("description"));
            } else {
                brb.setStatus("0");
                // brb.setResult("认证失败!");
                brb.setResult((String) jsonObject.get("description"));
            }
            AuthResponseHeader rh = getAuthResponseHeader();
            response.setAuthResponseHeader(rh);
            response.setBaseResponseBody(brb);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("login exception:" + loginRequest.getLoginRequestBody().getUser(), e);
            }
        }
        return response;
    }

    @Override
    public GetWarehousesResponse getWareHouse(GetWarehousesRequest getWarehousesRequest) {
        GetWarehousesResponse response = new GetWarehousesResponse();
        List<PhysicalWarehouse> plist = new ArrayList<PhysicalWarehouse>();
        Map<String, PhysicalWarehouse> mapdata = physicalWarehouseDao.selectAllPhyAndVirtualWarehouse(new PhysicalWarehouseRowMapper());
        if (mapdata != null && !mapdata.isEmpty()) {
            plist.addAll(mapdata.values());
        }
        if (plist.isEmpty()) {
            plist.add(new PhysicalWarehouse());
        }
        GetWarehousesResponseBody gb = new GetWarehousesResponseBody();
        if (plist != null && plist.size() != 0) {
            for (int i = 0; i < plist.size(); i++) {
                Warehouse wh = new Warehouse();
                PhysicalWarehouse ph = plist.get(i);
                wh.setName(ph.getName());
                for (int j = 0; j < plist.get(i).getWhou().size(); j++) {
                    String code = plist.get(i).getWhou().get(j).getCode();
                    if (code != null) {
                        LogicalWarehouse lw = new LogicalWarehouse();
                        lw.setUniqCode(code);
                        if (plist.get(i).getWhou().get(j).getName() != null) {
                            lw.setName(plist.get(i).getWhou().get(j).getName());
                        }
                        wh.getLogicalWarehouses().add(lw);
                    }

                }
                gb.getWarehouses().add(wh);
            }
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setGetWarehousesResponseBody(gb);
        return response;
    }

    public AuthResponseHeader getAuthResponseHeader() {
        AuthResponseHeader rh = new AuthResponseHeader();
        rh.setSequenceCode("");
        rh.setServerSequenceCode("");
        rh.setSign("");
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        rh.setCallbackTime(sd.format(new Date()));
        rh.setSignSalt("");
        return rh;
    }

    @Override
    public GetInboundDataResponse getInboundData(GetInboundDataRequest getInboundDataRequest) {
        GetInboundDataResponse response = new GetInboundDataResponse();
        Long orderId = pdaOrderDao.findPdaOrderByCodeAndTypeStatus(getInboundDataRequest.getGetInboundDataRequestBody().getCode(), new SingleColumnRowMapper<Long>(Long.class));
        GetInboundDataResponseBody gr = new GetInboundDataResponseBody();
        if (orderId != null) {
            gr.setStatus("0");// 失败状态
            gr.setCode(getInboundDataRequest.getGetInboundDataRequestBody().getCode());
            gr.setSlipCode("");
            gr.setUniqCode(getInboundDataRequest.getGetInboundDataRequestBody().getUniqCode());
        } else {
            List<PdaStockTransApplicationCommand> plist =
                    staDao.selectPdaSta(getInboundDataRequest.getGetInboundDataRequestBody().getCode(), getInboundDataRequest.getGetInboundDataRequestBody().getUniqCode(), new BeanPropertyRowMapper<PdaStockTransApplicationCommand>(
                            PdaStockTransApplicationCommand.class));
            if (plist == null || plist.size() == 0 || plist.isEmpty()) {
                gr.setStatus("0");// 失败状态
                gr.setCode(getInboundDataRequest.getGetInboundDataRequestBody().getCode());
                gr.setSlipCode("");
                gr.setUniqCode(getInboundDataRequest.getGetInboundDataRequestBody().getUniqCode());
            } else {
                gr.setStatus("1");
                /*** 成功数据封装 ************************************/
                // 封装头信息
                if (plist.get(0).getCode() != null) {
                    gr.setCode(plist.get(0).getCode());
                } else {
                    gr.setCode("");
                }
                if (plist.get(0).getSlipCode() != null) {
                    gr.setSlipCode(plist.get(0).getSlipCode());
                } else {
                    gr.setSlipCode("");
                }
                if (plist.get(0).getUniqCode() != null) {
                    gr.setUniqCode(plist.get(0).getUniqCode());
                } else {
                    gr.setUniqCode("");
                }
                // 封装SKU列表信息
                Map<String, Sku> mapSku = new HashMap<String, Sku>();
                for (int i = 0; i < plist.size(); i++) {
                    Long lineId = plist.get(i).getLineId();
                    if (lineId != null) {
                        String skuCode = plist.get(i).getSkuCode();
                        if (skuCode != null) {
                            String key = lineId + skuCode;
                            if (mapSku.get(key) != null) {// 条码的不同封装,已存在更新barCode列表
                                Sku sku = mapSku.get(key);
                                if (plist.get(i).getBarCode() != null) {
                                    sku.getBarCodes().add(plist.get(i).getBarCode());
                                }
                                mapSku.put(key, sku);
                            } else {// 不存在新建封装
                                Sku sku = new Sku();
                                sku.setCode(skuCode);
                                if (plist.get(i).getSkuName() != null) {
                                    sku.setSkuName(plist.get(i).getSkuName());
                                } else {
                                    sku.setSkuName("");
                                }
                                if (plist.get(i).getIsSn() != null) {
                                    sku.setIsSnSku(plist.get(i).getIsSn().toString());
                                } else {
                                    sku.setIsSnSku("");
                                }
                                if (plist.get(i).getIsDate() != null) {
                                    sku.setIsExpDateSku(plist.get(i).getIsDate());
                                } else {
                                    sku.setIsExpDateSku("");
                                }
                                if (plist.get(i).getKeyProperty() != null) {
                                    sku.setKeyProp(plist.get(i).getKeyProperty());
                                } else {
                                    sku.setKeyProp("");
                                }
                                if (plist.get(i).getColor() != null) {
                                    sku.setColor(plist.get(i).getColor());
                                } else {
                                    sku.setColor("");
                                }
                                if (plist.get(i).getDsize() != null) {
                                    sku.setSkuSize(plist.get(i).getDsize());
                                } else {
                                    sku.setSkuSize("");
                                }
                                if (plist.get(i).getSupplierCode() != null) {
                                    sku.setSupplierCode(plist.get(i).getSupplierCode());
                                } else {
                                    sku.setSupplierCode("");
                                }
                                if (plist.get(i).getBarCode() != null) {
                                    sku.getBarCodes().add(plist.get(i).getBarCode());
                                }
                                mapSku.put(key, sku);
                            }
                        }
                    }
                }
                if (mapSku != null && !mapSku.isEmpty()) {
                    gr.getSkus().addAll(mapSku.values());
                }
            }
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setGetInboundDataResponseBody(gr);
        return response;
    }

    @Override
    public GetInboundOnShelvesResponse getInboundOnShelves(GetInboundOnShelvesRequest getInboundOnShelvesRequest) {
        GetInboundOnShelvesResponse response = new GetInboundOnShelvesResponse();
        List<InboundOnShelvesDetailCommand> detail =
                stvLineDao.findInboundOnShelvesDetail(getInboundOnShelvesRequest.getGetInboundOnShelvesRequestBody().getCode(), getInboundOnShelvesRequest.getGetInboundOnShelvesRequestBody().getUniqCode(),
                        new BeanPropertyRowMapper<InboundOnShelvesDetailCommand>(InboundOnShelvesDetailCommand.class));
        List<InboundOnShelvesSkuCommand> skuList =
                stvLineDao.findInboundOnShelvesSku(getInboundOnShelvesRequest.getGetInboundOnShelvesRequestBody().getCode(), getInboundOnShelvesRequest.getGetInboundOnShelvesRequestBody().getUniqCode(),
                        new BeanPropertyRowMapper<InboundOnShelvesSkuCommand>(InboundOnShelvesSkuCommand.class));
        GetInboundOnShelvesResponseBody gb = new GetInboundOnShelvesResponseBody();
        if (detail == null || detail.size() == 0 || detail.isEmpty()) {
            gb.setStatus("0");// 查询失败处理
            gb.setCode("");
            gb.setSlipCode("");
            gb.setUniqCode("");
        } else {
            gb.setStatus("1");
            /*-----------------------查询成功，添加detail部分-------------------*/
            if (detail.get(0).getCode() != null) {
                gb.setCode(detail.get(0).getCode());
            } else {
                gb.setCode("");
            }
            if (detail.get(0).getSlipCode() != null) {
                gb.setSlipCode(detail.get(0).getSlipCode());
            } else {
                gb.setSlipCode("");
            }
            if (detail.get(0).getUniqueCode() != null) {
                gb.setUniqCode(detail.get(0).getUniqueCode());
            } else {
                gb.setUniqCode("");
            }
            Map<Long, ShelvesSkuDetial> map = new HashMap<Long, ShelvesSkuDetial>();
            for (int i = 0; i < detail.size(); i++) {
                if (detail.get(i).getDetailId() != null) {
                    Long detailId = detail.get(i).getDetailId();
                    ShelvesSkuDetial ss = map.get(detailId);
                    if (ss == null) {
                        ss = new ShelvesSkuDetial();
                        if (detail.get(i).getSkuCode() != null) {
                            ss.setSkuCode(detail.get(i).getSkuCode());
                        } else {
                            ss.setSkuCode("");
                        }
                        if (detail.get(i).getQty() != null) {
                            ss.setQty(detail.get(i).getQty());
                        } else {
                            ss.setQty("");
                        }
                        if (detail.get(i).getLocation() != null) {
                            ss.getLocations().add(detail.get(i).getLocation());
                        }
                        map.put(detailId, ss);
                    } else {
                        if (detail.get(i).getLocation() != null) {
                            ss.getLocations().add(detail.get(i).getLocation());
                        }
                    }
                    map.put(detailId, ss);
                }
            }
            if (map != null && !map.isEmpty()) {
                gb.getInboundOnShelvesDetials().addAll(map.values());
            }
            /*-----------------------查询成功，添加sku部分-------------------*/
            if (!(skuList == null || skuList.size() == 0 || skuList.isEmpty())) {
                Map<Long, Sku> maps = new HashMap<Long, Sku>();
                for (int i = 0; i < skuList.size(); i++) {
                    Long detailId = skuList.get(i).getStvLineId();
                    if (detailId != null) {
                        Sku sku = maps.get(detailId);
                        if (sku == null) {
                            sku = new Sku();
                            if (skuList.get(i).getSkuCode() != null) {
                                sku.setCode(skuList.get(i).getSkuCode());
                            } else {
                                sku.setCode("");
                            }
                            if (skuList.get(i).getSkuName() != null) {
                                sku.setSkuName(skuList.get(i).getSkuName());
                            } else {
                                sku.setSkuName("");
                            }
                            if (skuList.get(i).getIsSn() != null) {
                                sku.setIsSnSku(skuList.get(i).getIsSn().toString());
                            } else {
                                sku.setIsSnSku("");
                            }
                            if (skuList.get(i).getIsDateSku() != null) {
                                sku.setIsExpDateSku(skuList.get(i).getIsDateSku());
                            } else {
                                sku.setIsExpDateSku("");
                            }
                            if (skuList.get(i).getColor() != null) {
                                sku.setColor(skuList.get(i).getColor());
                            } else {
                                sku.setColor("");
                            }
                            if (skuList.get(i).getKeyProp() != null) {
                                sku.setKeyProp(skuList.get(i).getKeyProp());
                            } else {
                                sku.setKeyProp("");
                            }
                            if (skuList.get(i).getSupplierCode() != null) {
                                sku.setSupplierCode(skuList.get(i).getSupplierCode());
                            } else {
                                sku.setSupplierCode("");
                            }
                            if (skuList.get(i).getDsize() != null) {
                                sku.setSkuSize(skuList.get(i).getDsize());
                            } else {
                                sku.setSkuSize("");
                            }
                            if (skuList.get(i).getBarCode() != null) {
                                sku.getBarCodes().add(skuList.get(i).getBarCode());
                            }
                        } else {
                            if (skuList.get(i).getBarCode() != null) {
                                sku.getBarCodes().add(skuList.get(i).getBarCode());
                            }
                        }
                        maps.put(detailId, sku);
                    }
                }
                if (maps != null && !maps.isEmpty()) {
                    gb.getSkus().addAll(maps.values());
                }
            }
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setGetInboundOnShelvesResponseBody(gb);
        return response;
    }

    @Override
    public GetInventoryResponse getInventory(GetInventoryRequest getInventoryRequest) {
        GetInventoryResponse response = new GetInventoryResponse();
        List<InventoryCommand> detailList =
                inventoryDao.getInventoryForPda(getInventoryRequest.getGetInventoryRequestBody().getLocation(), getInventoryRequest.getGetInventoryRequestBody().getSkuBarCode(), getInventoryRequest.getGetInventoryRequestBody().getUniqCode(),
                        new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
        GetInventoryResponseBody gb = new GetInventoryResponseBody();
        if (detailList == null || detailList.size() == 0 || detailList.isEmpty()) {} else {
            for (int i = 0; i < detailList.size(); i++) {
                Inventory iv = new Inventory();
                if (getInventoryRequest.getGetInventoryRequestBody().getSkuBarCode() != null) {
                    iv.setSkuBarCode(getInventoryRequest.getGetInventoryRequestBody().getSkuBarCode());
                } else {
                    if (detailList.get(i).getBarCode() != null) {
                        iv.setSkuBarCode(detailList.get(i).getBarCode());
                    } else {
                        iv.setSkuBarCode("");
                    }
                }
                if (detailList.get(i).getLocationCode() != null) {
                    iv.setLocation(detailList.get(i).getLocationCode());
                } else {
                    iv.setLocation("");
                }
                if (detailList.get(i).getQuantity() != null) {
                    iv.setQty(detailList.get(i).getQuantity().toString());
                } else {
                    iv.setQty("");
                }
                if (detailList.get(i).getLockQty() != null) {
                    iv.setOcpQty(detailList.get(i).getLockQty().toString());
                } else {
                    iv.setOcpQty("");
                }
                if (detailList.get(i).getInventoryStatusName() != null) {
                    iv.setInvStatus(detailList.get(i).getInventoryStatusName());
                } else {
                    iv.setInvStatus("");
                }
                gb.getInventorys().add(iv);
            }
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setGetInventoryResponseBody(gb);
        return response;
    }

    @Override
    public UploadInboundReceiveResponse uploadInboundReceive(UploadInboundReceiveRequest uploadInboundReceiveRequest) {
        UploadInboundReceiveResponse response = new UploadInboundReceiveResponse();
        BaseResponseBody rb = new BaseResponseBody();
        // 查询给定的作业单是否存在
        StockTransApplication sta = staDao.findStaByCodeAndWhId(uploadInboundReceiveRequest.getUploadInboundReceiveRequestBody().getCode(), Long.parseLong(uploadInboundReceiveRequest.getUploadInboundReceiveRequestBody().getUniqCode()));
        if (sta != null) {
            String order = pdaOrderDao.getPdaOrderByCode(sta.getCode(), new SingleColumnRowMapper<String>(String.class));
            if (order == null) {
                try {
                    pdaOperationManager.saveUploadInbound(uploadInboundReceiveRequest);
                    rb.setStatus("1");
                    rb.setResult("作业单已完成");
                } catch (Exception e) {
                    rb.setStatus("0");
                    rb.setResult("上传失败");
                }
            } else {
                rb.setStatus("0");
                rb.setResult("存在未审核的单据!");
            }
        } else {
            rb.setStatus("0");
            rb.setResult("作业单不存在");
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setBaseResponseBody(rb);
        return response;
    }

    @Override
    public UploadInboundOnShelvesResponse uploadInboundOnShelves(UploadInboundOnShelvesRequest uploadInboundOnShelvesRequest) {
        UploadInboundOnShelvesResponse response = new UploadInboundOnShelvesResponse();
        StockTransApplication sta = staDao.findStaByCodeAndWhId(uploadInboundOnShelvesRequest.getUploadInboundOnShelvesRequestBody().getCode(), Long.parseLong(uploadInboundOnShelvesRequest.getUploadInboundOnShelvesRequestBody().getUniqCode()));
        BaseResponseBody rb = new BaseResponseBody();
        if (sta != null) {
            try {
                pdaOperationManager.saveOnShelvesUpload(uploadInboundOnShelvesRequest);
                rb.setStatus("1");
                rb.setResult("作业单已完成");
            } catch (Exception e) {
                log.error("", e);
                rb.setStatus("0");
                rb.setResult("上传失败");
            }
        } else {
            rb.setStatus("0");
            rb.setResult("作业单不存在");
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setBaseResponseBody(rb);
        return response;
    }

    @Override
    public ExecuteOrderResponse executeOrder(ExecuteOrderRequest executeOrderRequest) {
        ExecuteOrderResponse response = new ExecuteOrderResponse();
        BaseResponseBody brb = new BaseResponseBody();
        brb.setResult("1");
        brb.setStatus("作业单执行成功");
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setBaseResponseBody(brb);
        return response;
    }

    @Override
    public GetSkuResponse getSku(GetSkuRequest getSkuRequest) {
        GetSkuResponse rs = new GetSkuResponse();

        com.jumbo.wms.model.baseinfo.Sku sku = skuDao.getByBarcode1(getSkuRequest.getGetSkuRequestBody().getBarcode());
        GetSkuResponseBody rss = new GetSkuResponseBody();
        rss.setBarcode(sku.getBarCode());
        rss.setSkuId(sku.getId());
        rss.setSkuname(sku.getName());
        AuthResponseHeader rh = getAuthResponseHeader();
        rs.setAuthResponseHeader(rh);
        rs.setGetSkuResponseBody(rss);
        return rs;
    }

    @Override
    public GetInventoryCheckResponse getInventoryCheck(GetInventoryCheckRequest getInventoryCheckRequest) {
        GetInventoryCheckResponse response = new GetInventoryCheckResponse();
        GetInventoryCheckResponseBody rb = new GetInventoryCheckResponseBody();
        List<InboundOnShelvesDetailCommand> checkList =
                inventoryCheckDao.getInventoryCheck(getInventoryCheckRequest.getGetInventoryCheckRequestBody().getCode(), getInventoryCheckRequest.getGetInventoryCheckRequestBody().getUniqCode(), new BeanPropertyRowMapper<InboundOnShelvesDetailCommand>(
                        InboundOnShelvesDetailCommand.class));
        if (checkList == null || checkList.isEmpty() || checkList.size() == 0) {

        } else {
            rb.setCode(getInventoryCheckRequest.getGetInventoryCheckRequestBody().getCode());
            for (int i = 0; i < checkList.size(); i++) {
                rb.getLocations().add(checkList.get(i).getLocation());
            }
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setGetInventoryCheckResponseBody(rb);
        return response;
    }

    @Override
    public GetLibraryMovementResponse getLibraryMovement(GetLibraryMovementRequest getLibraryMovementRequest) {
        GetLibraryMovementResponse response = new GetLibraryMovementResponse();
        GetLibraryMovementResponseBody rb = new GetLibraryMovementResponseBody();
        // 判断给定作业库内移动作业单是否存在
        StockTransApplication sta = staDao.findLMStaByCodeAndWhId(getLibraryMovementRequest.getGetLibraryMovementRequestBody().getCode(), Long.parseLong(getLibraryMovementRequest.getGetLibraryMovementRequestBody().getUniqCode()));
        if (sta == null) {
            rb.setCode(getLibraryMovementRequest.getGetLibraryMovementRequestBody().getCode());
            rb.setOutbound(new LibraryMovementInOut());
            rb.setInbound(new LibraryMovementInOut());
        } else {
            List<InboundOnShelvesDetailCommand> inboundList =
                    stvLineDao.findLineByDirection(getLibraryMovementRequest.getGetLibraryMovementRequestBody().getCode(), TransactionDirection.INBOUND.getValue(), new BeanPropertyRowMapper<InboundOnShelvesDetailCommand>(
                            InboundOnShelvesDetailCommand.class));
            List<InboundOnShelvesDetailCommand> outboundList =
                    stvLineDao.findLineByDirection(getLibraryMovementRequest.getGetLibraryMovementRequestBody().getCode(), TransactionDirection.OUTBOUND.getValue(), new BeanPropertyRowMapper<InboundOnShelvesDetailCommand>(
                            InboundOnShelvesDetailCommand.class));
            LibraryMovementInOut li = new LibraryMovementInOut();
            if (inboundList != null) {
                for (int i = 0; i < inboundList.size(); i++) {
                    Inventory inv = new Inventory();
                    inv.setLocation(inboundList.get(i).getLocation() == null ? "" : inboundList.get(i).getLocation());
                    inv.setQty(inboundList.get(i).getQty() == null ? "" : inboundList.get(i).getQty());
                    inv.setSkuBarCode(inboundList.get(i).getSkuCode() == null ? "" : inboundList.get(i).getSkuCode());
                    inv.setInvStatus(inboundList.get(i).getInvStatus() == null ? "" : inboundList.get(i).getInvStatus());
                    li.getLibraryMovementInOutDetails().add(inv);
                }
            }
            LibraryMovementInOut lo = new LibraryMovementInOut();
            if (outboundList != null) {
                for (int j = 0; j < outboundList.size(); j++) {
                    Inventory inv = new Inventory();
                    inv.setLocation(outboundList.get(j).getLocation() == null ? "" : outboundList.get(j).getLocation());
                    inv.setQty(outboundList.get(j).getQty() == null ? "" : outboundList.get(j).getQty());
                    inv.setSkuBarCode(outboundList.get(j).getSkuCode() == null ? "" : outboundList.get(j).getSkuCode());
                    inv.setInvStatus(outboundList.get(j).getInvStatus() == null ? "" : outboundList.get(j).getInvStatus());
                    lo.getLibraryMovementInOutDetails().add(inv);
                }
            }
            rb.setCode(getLibraryMovementRequest.getGetLibraryMovementRequestBody().getCode());
            rb.setInbound(li);
            rb.setOutbound(lo);
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setGetLibraryMovementResponseBody(rb);
        return response;
    }

    @Override
    public GetPickingDataResponse getPickingData(GetPickingDataRequest getPickingDataRequest) {
        GetPickingDataResponse response = new GetPickingDataResponse();
        GetPickingDataResponseBody rb = new GetPickingDataResponseBody();
        // 根据给定的code查找拣货单据
        PickingList pl = pickingListDao.getByCode(getPickingDataRequest.getGetPickingDataRequestBody().getCode());
        // 判断单据是否存在，决定是否进行下一操作和返回结果数据
        if (pl != null) {
            rb.setCode(getPickingDataRequest.getGetPickingDataRequestBody().getCode());
            rb.setType("1");// 默认设置都为1 **************************
            List<InboundOnShelvesDetailCommand> pickDetail = stvLineDao.findPickDetail(getPickingDataRequest.getGetPickingDataRequestBody().getCode(), new BeanPropertyRowMapper<InboundOnShelvesDetailCommand>(InboundOnShelvesDetailCommand.class));
            if (pickDetail != null && pickDetail.size() > 0) {
                for (int i = 0; i < pickDetail.size(); i++) {
                    PickingDatadetial pd = new PickingDatadetial();
                    pd.setLocation(pickDetail.get(i).getLocation() == null ? "" : pickDetail.get(i).getLocation());
                    pd.setPgindex(pickDetail.get(i).getPgIndex() == null ? "" : pickDetail.get(i).getPgIndex().toString());
                    pd.setSkuCode(pickDetail.get(i).getSkuCode() == null ? "" : pickDetail.get(i).getSkuCode());
                    pd.setQty(pickDetail.get(i).getQty() == null ? "" : pickDetail.get(i).getQty());
                    rb.getPickingDatadetials().add(pd);
                }
            }
            List<InboundOnShelvesSkuCommand> pickSku = skuDao.findPickSku(getPickingDataRequest.getGetPickingDataRequestBody().getCode(), new BeanPropertyRowMapper<InboundOnShelvesSkuCommand>(InboundOnShelvesSkuCommand.class));
            if (pickSku != null && pickSku.size() > 0) {
                Map<String, Sku> map = new HashMap<String, Sku>();
                for (int i = 0; i < pickSku.size(); i++) {
                    Sku sku = null;
                    if (map.get(pickSku.get(i).getSkuCode()) == null) {
                        sku = new Sku();
                        sku.setCode(pickSku.get(i).getSkuCode());
                        sku.setSkuName(pickSku.get(i).getSkuName() == null ? "" : pickSku.get(i).getSkuName());
                        sku.setIsSnSku(pickSku.get(i).getIsSn() == null ? "" : pickSku.get(i).getIsSn().toString());
                        sku.setSupplierCode(pickSku.get(i).getSupplierCode() == null ? "" : pickSku.get(i).getSupplierCode());
                        sku.setColor(pickSku.get(i).getColor() == null ? "" : pickSku.get(i).getColor());
                        sku.setKeyProp(pickSku.get(i).getKeyProp() == null ? "" : pickSku.get(i).getKeyProp());
                        sku.getBarCodes().add(pickSku.get(i).getBarCode() == null ? "" : pickSku.get(i).getBarCode());
                        sku.setSkuSize(pickSku.get(i).getDsize() == null ? "" : pickSku.get(i).getDsize());
                    } else {
                        sku = map.get(pickSku.get(i).getSkuCode());
                        sku.getBarCodes().add(pickSku.get(i).getBarCode() == null ? "" : pickSku.get(i).getBarCode());
                    }
                    map.put(pickSku.get(i).getSkuCode(), sku);
                }
                if (!map.isEmpty()) {
                    rb.getSkus().addAll(map.values());
                }
            }
        } else {
            // 没有对应的拣货单
            rb.setCode(getPickingDataRequest.getGetPickingDataRequestBody().getCode());
            rb.setType("");
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setGetPickingDataResponseBody(rb);
        return response;
    }

    @Override
    public GetReturnOrderResponse getReturnOrder(GetReturnOrderRequest getReturnOrderRequest) {
        // 退仓单类型确认为 61 采购出库101 VMI退大仓 102 VMI转点退仓
        GetReturnOrderResponse response = new GetReturnOrderResponse();
        GetReturnOrderResponseBody grb = new GetReturnOrderResponseBody();
        StockTransApplication sta = staDao.findIfExistReturnOrder(getReturnOrderRequest.getGetReturnOrderRequestBody().getCode(), Long.parseLong(getReturnOrderRequest.getGetReturnOrderRequestBody().getUniqCode()));
        if (sta != null) {
            grb.setCode(getReturnOrderRequest.getGetReturnOrderRequestBody().getCode());
            grb.setUniqCode(getReturnOrderRequest.getGetReturnOrderRequestBody().getUniqCode());
            grb.setSlipCode(sta.getRefSlipCode() == null ? "" : sta.getRefSlipCode());
            List<InboundOnShelvesDetailCommand> detail = inventoryDao.findReturnOrderDetail(sta.getCode(), true, new BeanPropertyRowMapper<InboundOnShelvesDetailCommand>(InboundOnShelvesDetailCommand.class));
            if (detail != null && detail.size() > 0) {
                for (int i = 0; i < detail.size(); i++) {
                    ShelvesSkuDetial de = new ShelvesSkuDetial();
                    de.setSkuCode(detail.get(i).getSkuCode() == null ? "" : detail.get(i).getSkuCode());
                    de.setQty(detail.get(i).getQty() == null ? "" : detail.get(i).getQty());
                    de.getLocations().add(detail.get(i).getLocation() == null ? "" : detail.get(i).getLocation());
                    de.setInvStatus(detail.get(i).getInvStatus() == null ? "" : detail.get(i).getInvStatus());
                    grb.getReturnOrderDetails().add(de);
                }
            }
            List<InboundOnShelvesSkuCommand> skus = inventoryDao.findReturnOrderSkus(sta.getCode(), new BeanPropertyRowMapper<InboundOnShelvesSkuCommand>(InboundOnShelvesSkuCommand.class));
            if (skus != null && skus.size() > 0) {
                Map<String, Sku> map = new HashMap<String, Sku>();
                for (int i = 0; i < skus.size(); i++) {
                    Sku sku = null;
                    if (map.get(skus.get(i).getSkuCode()) == null) {
                        sku = new Sku();
                        sku.setCode(skus.get(i).getSkuCode());
                        sku.setSkuName(skus.get(i).getSkuName() == null ? "" : skus.get(i).getSkuName());
                        sku.setIsSnSku(skus.get(i).getIsSn() == null ? "" : skus.get(i).getIsSn().toString());
                        sku.setSupplierCode(skus.get(i).getSupplierCode() == null ? "" : skus.get(i).getSupplierCode());
                        sku.setColor(skus.get(i).getColor() == null ? "" : skus.get(i).getColor());
                        sku.setKeyProp(skus.get(i).getKeyProp() == null ? "" : skus.get(i).getKeyProp());
                        sku.getBarCodes().add(skus.get(i).getBarCode() == null ? "" : skus.get(i).getBarCode());
                        sku.setSkuSize(skus.get(i).getDsize() == null ? "" : skus.get(i).getDsize());
                    } else {
                        sku = map.get(skus.get(i).getSkuCode());
                        sku.getBarCodes().add(skus.get(i).getBarCode() == null ? "" : skus.get(i).getBarCode());
                    }
                    map.put(skus.get(i).getSkuCode(), sku);
                }
                if (!map.isEmpty()) {
                    grb.getSkus().addAll(map.values());
                }
            }
        } else {
            // 退仓单不存在
            grb.setCode(getReturnOrderRequest.getGetReturnOrderRequestBody().getCode());
            grb.setUniqCode(getReturnOrderRequest.getGetReturnOrderRequestBody().getUniqCode());
            grb.setSlipCode("");
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setGetReturnOrderResponseBody(grb);
        return response;
    }

    @Override
    public UploadHandOverListResponse uploadHandOverList(UploadHandOverListRequest uploadHandOverListRequest) {
        UploadHandOverListResponse response = new UploadHandOverListResponse();
        BaseResponseBody brb = new BaseResponseBody();
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        try {
            pdaOperationManager.saveHandOverListUpload(uploadHandOverListRequest);
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_UPLOAD_SUCCESS), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
            brb.setResult(errorMsg);
            brb.setStatus("1");
        } catch (BusinessException e) {
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            brb.setStatus("0");
            brb.setResult(errorMsg);
        } catch (Exception e) {
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_UPLOAD_FAILED), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
            brb.setStatus("0");
            brb.setResult(errorMsg);
        }
        response.setBaseResponseBody(brb);
        return response;
    }

    @Override
    public UploadInventoryCheckResponse uploadInventoryCheck(UploadInventoryCheckRequest uploadInventoryCheckRequest) {
        UploadInventoryCheckResponse response = new UploadInventoryCheckResponse();
        BaseResponseBody brb = new BaseResponseBody();
        try {
            pdaOperationManager.saveCheckUpload(uploadInventoryCheckRequest);
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_UPLOAD_SUCCESS), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
            brb.setResult(errorMsg);
            brb.setStatus("1");
        } catch (BusinessException e) {
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            brb.setStatus("0");
            brb.setResult(errorMsg);
        } catch (Exception e) {
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_UPLOAD_FAILED), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
            brb.setStatus("0");
            brb.setResult(errorMsg);
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setBaseResponseBody(brb);
        return response;
    }

    @Override
    public UploadLibraryMovementResponse uploadLibraryMovement(UploadLibraryMovementRequest uploadLibraryMovementRequest) {
        UploadLibraryMovementResponse response = new UploadLibraryMovementResponse();
        BaseResponseBody brb = new BaseResponseBody();
        if (uploadLibraryMovementRequest.getUploadLibraryMovementRequestBody().getCode() == null) {
            brb.setStatus("0");
            brb.setResult("执行失败,必须指定code");
        } else {
            try {
                pdaOperationManager.saveMovementUpload(uploadLibraryMovementRequest);
                brb.setStatus("1");
                brb.setResult("执行成功");
            } catch (Exception e) {
                brb.setStatus("0");
                brb.setResult("执行失败!");
            }
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setBaseResponseBody(brb);
        return response;
    }

    @Override
    public UploadPickingOutboundResponse uploadPickingOutbound(UploadPickingOutboundRequest uploadPickingOutboundRequest) {
        return null;
    }

    @Override
    public UploadReturnOrderResponse uploadReturnOrder(UploadReturnOrderRequest uploadReturnOrderRequest) {
        UploadReturnOrderResponse response = new UploadReturnOrderResponse();
        BaseResponseBody brb = new BaseResponseBody();
        try {
            pdaOperationManager.saveReturnOrder(uploadReturnOrderRequest);
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_UPLOAD_SUCCESS), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
            brb.setResult(errorMsg);
            brb.setStatus("1");
        } catch (BusinessException e) {
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            brb.setStatus("0");
            brb.setResult(errorMsg);
        } catch (Exception e) {
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_UPLOAD_FAILED), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
            brb.setStatus("0");
            brb.setResult(errorMsg);
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setBaseResponseBody(brb);
        return response;
    }

    @Override
    public GetTransNoResponse getTransNo(GetTransNoRequest getTransNoRequest) {
        GetTransNoResponse response = new GetTransNoResponse();
        GetTransNoResponseBody gtb = new GetTransNoResponseBody();
        Date startTime = FormatUtil.getDate(getTransNoRequest.getGetTransNoRequestBody().getStartTime());
        Date endTime = FormatUtil.getDate(getTransNoRequest.getGetTransNoRequestBody().getEndTime());
        if (startTime != null) {
            if (startTime.getTime() / 1000 < new Date().getTime() / 1000 - 60 * 240) {
                startTime.setTime(new Date().getTime() - 60 * 240 * 1000);
            }
        }
        List<GetTransNoCommand> gList = staDeliveryInfoDao.getTransNoForPda(startTime, endTime, new BeanPropertyRowMapper<GetTransNoCommand>(GetTransNoCommand.class));
        if (gList != null && !gList.isEmpty()) {
            Map<String, TransNoDetail> keyMap = new HashMap<String, TransNoDetail>();
            for (int i = 0; i < gList.size(); i++) {
                if (keyMap.get(gList.get(i).getMapKey()) == null) {
                    TransNoDetail td = new TransNoDetail();
                    td.setLpcode(gList.get(i).getLpCode());
                    td.setUniqCode(gList.get(i).getUniqueCode());
                    TransNoDetailLine tdl = new TransNoDetailLine();
                    tdl.setTransNos(gList.get(i).getTransNo() == null ? "" : gList.get(i).getTransNo());
                    tdl.setReceiver(gList.get(i).getReceiver() == null ? "" : gList.get(i).getReceiver());
                    tdl.setWeight(gList.get(i).getWeight() == null ? "" : gList.get(i).getWeight().toString());
                    td.getTransNoDetailLines().add(tdl);
                    keyMap.put(gList.get(i).getMapKey(), td);
                } else {
                    TransNoDetailLine tdl = new TransNoDetailLine();
                    tdl.setTransNos(gList.get(i).getTransNo() == null ? "" : gList.get(i).getTransNo());
                    tdl.setReceiver(gList.get(i).getReceiver() == null ? "" : gList.get(i).getReceiver());
                    tdl.setWeight(gList.get(i).getWeight() == null ? "" : gList.get(i).getWeight().toString());
                    keyMap.get(gList.get(i).getMapKey()).getTransNoDetailLines().add(tdl);
                }
            }
            if (!keyMap.isEmpty()) {
                gtb.getTransNoDetails().addAll(keyMap.values());
            }
        }
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        response.setGetTransNoResponseBody(gtb);
        return response;
    }

    @Override
    public UploadInitiativeMoveInboundResponse uploadInitiativeMoveInbound(UploadInitiativeMoveInboundRequest uploadInitiativeMoveInboundRequest) {
        UploadInitiativeMoveInboundResponse response = new UploadInitiativeMoveInboundResponse();
        BaseResponseBody brb = new BaseResponseBody();
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        StockTransApplication sta = staDao.findStaByCode(uploadInitiativeMoveInboundRequest.getUploadInitiativeMoveInboundRequestBody().getCode());
        if (sta == null || sta.getMainWarehouse() == null || !(sta.getMainWarehouse().getId().equals(Long.parseLong(uploadInitiativeMoveInboundRequest.getUploadInitiativeMoveInboundRequestBody().getUniqCode()))) || sta.getType() == null
                || !(sta.getType().getValue() == 31) || (sta.getStatus() != null && sta.getStatus().getValue() != 2)) {
            brb.setStatus("0");
            brb.setResult("库内移动作业单不存在或者已完成，PDA上传数据未保存!");
            response.setBaseResponseBody(brb);
            return response;
        } else {
            pdaOperationManager.saveUploadInitativeMoveInBound(uploadInitiativeMoveInboundRequest);
            brb.setStatus("1");
            brb.setResult("PDA数据上传成功!");
            response.setBaseResponseBody(brb);
            return response;
        }
    }

    @Override
    public UploadInitiativeMoveOutboundResponse uploadInitiativeMoveOutbound(UploadInitiativeMoveOutboundRequest uploadInitiativeMoveOutboundRequest) {
        UploadInitiativeMoveOutboundResponse response = new UploadInitiativeMoveOutboundResponse();
        UploadInitiativeMoveOutboundResponseBody uob = new UploadInitiativeMoveOutboundResponseBody();
        AuthResponseHeader rh = getAuthResponseHeader();
        response.setAuthResponseHeader(rh);
        PdaOrder order = null;
        try {
            order = pdaOperationManager.saveUploadInitativeMoveOutBound(uploadInitiativeMoveOutboundRequest);
        } catch (BusinessException e) {
            BaseResponseBody brb = new BaseResponseBody();
            brb.setStatus("0");
            brb.setResult(e.getMessage());
            uob.setBaseResponseBody(brb);
            uob.setCode("");
            uob.setSlipCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getSlipCode());
            uob.setUniqCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getUniqCode());
            response.setUploadInitiativeMoveOutboundResponseBody(uob);
            return response;
        } catch (Exception e) {
            BaseResponseBody brb = new BaseResponseBody();
            brb.setStatus("0");
            brb.setResult("保存失败，PDA上传数据未保存!");
            uob.setBaseResponseBody(brb);
            uob.setCode("");
            uob.setSlipCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getSlipCode());
            uob.setUniqCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getUniqCode());
            response.setUploadInitiativeMoveOutboundResponseBody(uob);
            return response;
        }
        if (order != null) {
            try {
                String code = executeInitiativeMoveOutbound(uploadInitiativeMoveOutboundRequest);
                order.setMemo("完成");
                order.setStatus(DefaultStatus.FINISHED);
                order.setFinishTime(new Date());
                order.setOrderCode(code);
                pdaOrderDao.save(order);
                BaseResponseBody brb = new BaseResponseBody();
                brb.setStatus("1");
                brb.setResult("执行成功");
                uob.setBaseResponseBody(brb);
                uob.setCode(code);
                uob.setSlipCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getSlipCode());
                uob.setUniqCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getUniqCode());
                response.setUploadInitiativeMoveOutboundResponseBody(uob);
                return response;
            } catch (BusinessException e) {
                pdaOperationManager.updatePdaOrderStatus(order.getId(), DefaultStatus.ERROR, e.getMessage() == null ? "执行失败" : e.getMessage());
                BaseResponseBody brb = new BaseResponseBody();
                brb.setStatus("0");
                brb.setResult("执行失败,PDA上传数据已保存!" + e.getMessage() == null ? "" : ",错误信息：" + e.getMessage());
                uob.setBaseResponseBody(brb);
                uob.setCode("");
                uob.setSlipCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getSlipCode());
                uob.setUniqCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getUniqCode());
                response.setUploadInitiativeMoveOutboundResponseBody(uob);
                return response;
            } catch (Exception e) {
                log.error("", e);
                pdaOperationManager.updatePdaOrderStatus(order.getId(), DefaultStatus.ERROR, "执行失败，系统异常");
                BaseResponseBody brb = new BaseResponseBody();
                brb.setStatus("0");
                brb.setResult("执行失败,PDA上传数据已保存!系统异常");
                uob.setBaseResponseBody(brb);
                uob.setCode("");
                uob.setSlipCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getSlipCode());
                uob.setUniqCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getUniqCode());
                response.setUploadInitiativeMoveOutboundResponseBody(uob);
                return response;
            }
        }
        return response;
    }

    private String executeInitiativeMoveOutbound(UploadInitiativeMoveOutboundRequest uploadInitiativeMoveOutboundRequest) {
        Long whouid = Long.parseLong(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getUniqCode());
        com.jumbo.wms.model.baseinfo.Warehouse wh = warehouseDao.getByOuId(whouid);
        if (wh == null) {
            throw new BusinessException("PDA 上传的数据中仓库id为" + uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getUniqCode() + "的仓库不存在");
        }
        User u = userDao.findByLoginName(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getUserName());
        Map<String, StvLine> outMap = new HashMap<String, StvLine>();
        List<StvLine> list = new ArrayList<StvLine>();
        List<Inventory> listIn = uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getOutbound().getLibraryMovementInOutDetails();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(whouid);
        if (listIn != null && listIn.size() > 0) {
            for (int i = 0; i < listIn.size(); i++) {
                if (listIn.get(i).getQty() == null || Long.parseLong(listIn.get(i).getQty()) == 0) {
                    continue;
                }
                StvLine sl = new StvLine();
                com.jumbo.wms.model.baseinfo.Sku sku = skuDao.getByBarcode(listIn.get(i).getSkuBarCode(), customerId);
                if (sku == null) {
                    SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(listIn.get(i).getSkuBarCode(), customerId);
                    if (addSkuCode == null) {
                        throw new BusinessException("PDA 上传的数据中条形码为" + listIn.get(i).getSkuBarCode() + "的商品不存在");
                    } else {
                        sku = addSkuCode.getSku();
                    }
                }
                sl.setSku(sku);
                WarehouseLocation loc = warehouseLocationDao.findLocationByCode(listIn.get(i).getLocation(), wh.getOu().getId());
                if (loc == null) {
                    throw new BusinessException("PDA 上传数据中编码为" + listIn.get(i).getLocation() + "的库位不存在");
                }
                sl.setLocation(loc);
                InventoryStatus status = inventoryStatusDao.findByNameUnionSystem(listIn.get(i).getInvStatus(), wh.getOu().getParentUnit().getParentUnit().getId());
                if (status == null) {
                    throw new BusinessException("PDA 上传数据中库存状态" + listIn.get(i).getInvStatus() + "不存在");
                }
                sl.setInvStatus(status);
                sl.setQuantity(Long.parseLong(listIn.get(i).getQty()));
                String key = sku.getId() + "_" + status.getId() + loc.getId();
                if (outMap.get(key) == null) {
                    outMap.put(key, sl);
                } else {
                    StvLine line = outMap.get(key);
                    line.setQuantity(line.getQuantity() + Long.parseLong(listIn.get(i).getQty()));
                }
            }
        } else {
            throw new BusinessException("PDA 上传数据中出库明细行为空");
        }
        for (StvLine out : outMap.values()) {
            Long qty = out.getQuantity();
            List<com.jumbo.wms.model.warehouse.Inventory> invList =
                    inventoryDao.findBySkuLocAndStatus(out.getSku().getId(), out.getLocation().getId(), out.getInvStatus().getId(), wh.getOu().getId(), new BeanPropertyRowMapperExt<com.jumbo.wms.model.warehouse.Inventory>(
                            com.jumbo.wms.model.warehouse.Inventory.class));
            for (com.jumbo.wms.model.warehouse.Inventory inv : invList) {
                StvLine temp = new StvLine();
                if (inv.getQuantity() >= qty) {
                    temp.setQuantity(qty);
                    qty = 0L;
                } else if (inv.getQuantity() > 0) {
                    temp.setQuantity(inv.getQuantity());
                    qty -= inv.getQuantity();
                }
                temp.setSku(out.getSku());
                temp.setLocation(out.getLocation());
                temp.setInvStatus(out.getInvStatus());
                temp.setOwner(inv.getOwner());
                list.add(temp);
                if (qty < 1) break;
            }
            if (qty > 0) {
                throw new BusinessException("[SKU:" + out.getSku().getBarCode() + " The lack of inventory! number:]" + qty);
            }
        }
        try {
            StockTransApplication sta = wareHouseManager.createTransitInnerSta(false, uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getSlipCode(), "", u.getId(), wh.getOu().getId(), list);
            return sta.getCode();
        } catch (BusinessException e) {
            throw e;
        }

    }
}
