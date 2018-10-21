package com.jumbo.webservice.pda;

import java.io.Serializable;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.jumbo.webservice.base.AuthRequestHeader;
import com.jumbo.webservice.base.AuthResponseHeader;
import com.jumbo.webservice.base.AuthenticationFailed;
import com.jumbo.webservice.pda.executeOrder.ExecuteOrderRequest;
import com.jumbo.webservice.pda.executeOrder.ExecuteOrderRequestBody;
import com.jumbo.webservice.pda.executeOrder.ExecuteOrderResponse;
import com.jumbo.webservice.pda.getInboundData.GetInboundDataRequest;
import com.jumbo.webservice.pda.getInboundData.GetInboundDataRequestBody;
import com.jumbo.webservice.pda.getInboundData.GetInboundDataResponse;
import com.jumbo.webservice.pda.getInboundData.GetInboundDataResponseBody;
import com.jumbo.webservice.pda.getInboundOnShelves.GetInboundOnShelvesRequest;
import com.jumbo.webservice.pda.getInboundOnShelves.GetInboundOnShelvesRequestBody;
import com.jumbo.webservice.pda.getInboundOnShelves.GetInboundOnShelvesResponse;
import com.jumbo.webservice.pda.getInboundOnShelves.GetInboundOnShelvesResponseBody;
import com.jumbo.webservice.pda.getInventory.GetInventoryRequest;
import com.jumbo.webservice.pda.getInventory.GetInventoryRequestBody;
import com.jumbo.webservice.pda.getInventory.GetInventoryResponse;
import com.jumbo.webservice.pda.getInventory.GetInventoryResponseBody;
import com.jumbo.webservice.pda.getInventoryCheck.GetInventoryCheckRequest;
import com.jumbo.webservice.pda.getInventoryCheck.GetInventoryCheckRequestBody;
import com.jumbo.webservice.pda.getInventoryCheck.GetInventoryCheckResponse;
import com.jumbo.webservice.pda.getInventoryCheck.GetInventoryCheckResponseBody;
import com.jumbo.webservice.pda.getLibraryMovement.GetLibraryMovementRequest;
import com.jumbo.webservice.pda.getLibraryMovement.GetLibraryMovementRequestBody;
import com.jumbo.webservice.pda.getLibraryMovement.GetLibraryMovementResponse;
import com.jumbo.webservice.pda.getLibraryMovement.GetLibraryMovementResponseBody;
import com.jumbo.webservice.pda.getPickingData.GetPickingDataRequest;
import com.jumbo.webservice.pda.getPickingData.GetPickingDataRequestBody;
import com.jumbo.webservice.pda.getPickingData.GetPickingDataResponse;
import com.jumbo.webservice.pda.getPickingData.GetPickingDataResponseBody;
import com.jumbo.webservice.pda.getReturnOrder.GetReturnOrderRequest;
import com.jumbo.webservice.pda.getReturnOrder.GetReturnOrderRequestBody;
import com.jumbo.webservice.pda.getReturnOrder.GetReturnOrderResponse;
import com.jumbo.webservice.pda.getReturnOrder.GetReturnOrderResponseBody;
import com.jumbo.webservice.pda.getTransNo.GetTransNoRequest;
import com.jumbo.webservice.pda.getTransNo.GetTransNoRequestBody;
import com.jumbo.webservice.pda.getTransNo.GetTransNoResponse;
import com.jumbo.webservice.pda.getTransNo.GetTransNoResponseBody;
import com.jumbo.webservice.pda.getWarehouses.GetWarehousesRequest;
import com.jumbo.webservice.pda.getWarehouses.GetWarehousesResponse;
import com.jumbo.webservice.pda.getWarehouses.GetWarehousesResponseBody;
import com.jumbo.webservice.pda.login.LoginRequest;
import com.jumbo.webservice.pda.login.LoginRequestBody;
import com.jumbo.webservice.pda.login.LoginResponse;
import com.jumbo.webservice.pda.uploadHandOverList.UploadHandOverListRequest;
import com.jumbo.webservice.pda.uploadHandOverList.UploadHandOverListRequestBody;
import com.jumbo.webservice.pda.uploadHandOverList.UploadHandOverListResponse;
import com.jumbo.webservice.pda.uploadInboundOnShelves.UploadInboundOnShelvesRequest;
import com.jumbo.webservice.pda.uploadInboundOnShelves.UploadInboundOnShelvesRequestBody;
import com.jumbo.webservice.pda.uploadInboundOnShelves.UploadInboundOnShelvesResponse;
import com.jumbo.webservice.pda.uploadInboundReceive.UploadInboundReceiveRequest;
import com.jumbo.webservice.pda.uploadInboundReceive.UploadInboundReceiveRequestBody;
import com.jumbo.webservice.pda.uploadInboundReceive.UploadInboundReceiveResponse;
import com.jumbo.webservice.pda.uploadInitiativeMoveInbound.UploadInitiativeMoveInboundRequest;
import com.jumbo.webservice.pda.uploadInitiativeMoveInbound.UploadInitiativeMoveInboundRequestBody;
import com.jumbo.webservice.pda.uploadInitiativeMoveInbound.UploadInitiativeMoveInboundResponse;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundRequest;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundRequestBody;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundResponse;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundResponseBody;
import com.jumbo.webservice.pda.uploadInventoryCheck.UploadInventoryCheckRequest;
import com.jumbo.webservice.pda.uploadInventoryCheck.UploadInventoryCheckRequestBody;
import com.jumbo.webservice.pda.uploadInventoryCheck.UploadInventoryCheckResponse;
import com.jumbo.webservice.pda.uploadLibraryMovement.UploadLibraryMovementRequest;
import com.jumbo.webservice.pda.uploadLibraryMovement.UploadLibraryMovementRequestBody;
import com.jumbo.webservice.pda.uploadLibraryMovement.UploadLibraryMovementResponse;
import com.jumbo.webservice.pda.uploadPickingOutbound.UploadPickingOutboundRequest;
import com.jumbo.webservice.pda.uploadPickingOutbound.UploadPickingOutboundRequestBody;
import com.jumbo.webservice.pda.uploadPickingOutbound.UploadPickingOutboundResponse;
import com.jumbo.webservice.pda.uploadReturnOrder.UploadReturnOrderRequest;
import com.jumbo.webservice.pda.uploadReturnOrder.UploadReturnOrderRequestBody;
import com.jumbo.webservice.pda.uploadReturnOrder.UploadReturnOrderResponse;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the com.jumbo.webservice.pda package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4612205088566781964L;
    private final static QName _GetReturnOrderResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "getReturnOrderResponse");
    private final static QName _UploadInboundOnShelvesRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadInboundOnShelvesRequest");
    private final static QName _UploadInventoryCheckResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadInventoryCheckResponse");
    private final static QName _UploadPickingOutboundRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadPickingOutboundRequest");
    private final static QName _GetInboundDataResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "getInboundDataResponse");
    private final static QName _GetInboundOnShelvesResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "getInboundOnShelvesResponse");
    private final static QName _GetInventoryCheckRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "getInventoryCheckRequest");
    private final static QName _UploadInitiativeMoveOutboundResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadInitiativeMoveOutboundResponse");
    private final static QName _ExecuteOrderRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "executeOrderRequest");
    private final static QName _LoginRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "loginRequest");
    private final static QName _GetLibraryMovementRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "getLibraryMovementRequest");
    private final static QName _GetPickingDataResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "getPickingDataResponse");
    private final static QName _UploadInitiativeMoveOutboundRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadInitiativeMoveOutboundRequest");
    private final static QName _UploadInventoryCheckRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadInventoryCheckRequest");
    private final static QName _UploadPickingOutboundResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadPickingOutboundResponse");
    private final static QName _ExecuteOrderResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "executeOrderResponse");
    private final static QName _GetPickingDataRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "getPickingDataRequest");
    private final static QName _GetInventoryRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "getInventoryRequest");
    private final static QName _GetWarehousesResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "getWarehousesResponse");
    private final static QName _UploadInboundReceiveRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadInboundReceiveRequest");
    private final static QName _UploadInitiativeMoveInboundRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadInitiativeMoveInboundRequest");
    private final static QName _UploadLibraryMovementRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadLibraryMovementRequest");
    private final static QName _UploadInboundOnShelvesResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadInboundOnShelvesResponse");
    private final static QName _LoginResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "loginResponse");
    private final static QName _GetInboundOnShelvesRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "getInboundOnShelvesRequest");
    private final static QName _UploadLibraryMovementResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadLibraryMovementResponse");
    private final static QName _GetReturnOrderRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "getReturnOrderRequest");
    private final static QName _GetInboundDataRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "getInboundDataRequest");
    private final static QName _GetTransNoResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "getTransNoResponse");
    private final static QName _GetWarehousesRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "getWarehousesRequest");
    private final static QName _GetTransNoRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "getTransNoRequest");
    private final static QName _UploadInitiativeMoveInboundResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadInitiativeMoveInboundResponse");
    private final static QName _UploadInboundReceiveResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadInboundReceiveResponse");
    private final static QName _GetLibraryMovementResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "getLibraryMovementResponse");
    private final static QName _GetInventoryResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "getInventoryResponse");
    private final static QName _UploadHandOverListResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadHandOverListResponse");
    private final static QName _GetInventoryCheckResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "getInventoryCheckResponse");
    private final static QName _UploadReturnOrderRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadReturnOrderRequest");
    private final static QName _UploadReturnOrderResponse_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadReturnOrderResponse");
    private final static QName _UploadHandOverListRequest_QNAME = new QName("http://webservice.jumbo.com/pda/", "uploadHandOverListRequest");
    private final static QName _AuthenticationFailed_QNAME = new QName("http://webservice.jumbo.com/pda/", "authenticationFailed");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     * for package: com.jumbo.webservice.pda
     * 
     */
    public ObjectFactory() {}

    /**
     * Create an instance of {@link UploadInboundReceiveResponse }
     * 
     */
    public UploadInboundReceiveResponse createUploadInboundReceiveResponse() {
        return new UploadInboundReceiveResponse();
    }

    /**
     * Create an instance of {@link GetInboundDataRequestBody }
     * 
     */
    public GetInboundDataRequestBody createGetInboundDataRequestBody() {
        return new GetInboundDataRequestBody();
    }

    /**
     * Create an instance of {@link UploadInitiativeMoveOutboundResponse }
     * 
     */
    public UploadInitiativeMoveOutboundResponse createUploadInitiativeMoveOutboundResponse() {
        return new UploadInitiativeMoveOutboundResponse();
    }

    /**
     * Create an instance of {@link AuthRequestHeader }
     * 
     */
    public AuthRequestHeader createAuthRequestHeader() {
        return new AuthRequestHeader();
    }

    /**
     * Create an instance of {@link PickingDatadetial }
     * 
     */
    public PickingDatadetial createPickingDatadetial() {
        return new PickingDatadetial();
    }

    /**
     * Create an instance of {@link LogicalWarehouse }
     * 
     */
    public LogicalWarehouse createLogicalWarehouse() {
        return new LogicalWarehouse();
    }

    /**
     * Create an instance of {@link GetLibraryMovementResponse }
     * 
     */
    public GetLibraryMovementResponse createGetLibraryMovementResponse() {
        return new GetLibraryMovementResponse();
    }

    /**
     * Create an instance of {@link GetInboundOnShelvesRequestBody }
     * 
     */
    public GetInboundOnShelvesRequestBody createGetInboundOnShelvesRequestBody() {
        return new GetInboundOnShelvesRequestBody();
    }

    /**
     * Create an instance of {@link GetTransNoResponse }
     * 
     */
    public GetTransNoResponse createGetTransNoResponse() {
        return new GetTransNoResponse();
    }

    /**
     * Create an instance of {@link UploadInboundOnShelvesResponse }
     * 
     */
    public UploadInboundOnShelvesResponse createUploadInboundOnShelvesResponse() {
        return new UploadInboundOnShelvesResponse();
    }

    /**
     * Create an instance of {@link UploadPickingOutboundResponse }
     * 
     */
    public UploadPickingOutboundResponse createUploadPickingOutboundResponse() {
        return new UploadPickingOutboundResponse();
    }

    /**
     * Create an instance of {@link GetPickingDataResponseBody }
     * 
     */
    public GetPickingDataResponseBody createGetPickingDataResponseBody() {
        return new GetPickingDataResponseBody();
    }

    /**
     * Create an instance of {@link UploadReturnOrderRequest }
     * 
     */
    public UploadReturnOrderRequest createUploadReturnOrderRequest() {
        return new UploadReturnOrderRequest();
    }

    /**
     * Create an instance of {@link ExecuteOrderRequestBody }
     * 
     */
    public ExecuteOrderRequestBody createExecuteOrderRequestBody() {
        return new ExecuteOrderRequestBody();
    }

    /**
     * Create an instance of {@link UploadInboundReceiveRequestBody }
     * 
     */
    public UploadInboundReceiveRequestBody createUploadInboundReceiveRequestBody() {
        return new UploadInboundReceiveRequestBody();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link UploadInboundOnShelvesRequestBody }
     * 
     */
    public UploadInboundOnShelvesRequestBody createUploadInboundOnShelvesRequestBody() {
        return new UploadInboundOnShelvesRequestBody();
    }

    /**
     * Create an instance of {@link GetInboundOnShelvesRequest }
     * 
     */
    public GetInboundOnShelvesRequest createGetInboundOnShelvesRequest() {
        return new GetInboundOnShelvesRequest();
    }

    /**
     * Create an instance of {@link GetReturnOrderResponseBody }
     * 
     */
    public GetReturnOrderResponseBody createGetReturnOrderResponseBody() {
        return new GetReturnOrderResponseBody();
    }

    /**
     * Create an instance of {@link Warehouse }
     * 
     */
    public Warehouse createWarehouse() {
        return new Warehouse();
    }

    /**
     * Create an instance of {@link TransNoDetailLine }
     * 
     */
    public TransNoDetailLine createTransNoDetailLine() {
        return new TransNoDetailLine();
    }

    /**
     * Create an instance of {@link Inventory }
     * 
     */
    public Inventory createInventory() {
        return new Inventory();
    }

    /**
     * Create an instance of {@link LoginRequestBody }
     * 
     */
    public LoginRequestBody createLoginRequestBody() {
        return new LoginRequestBody();
    }

    /**
     * Create an instance of {@link Sku }
     * 
     */
    public Sku createSku() {
        return new Sku();
    }

    /**
     * Create an instance of {@link UploadPickingOutboundRequestBody }
     * 
     */
    public UploadPickingOutboundRequestBody createUploadPickingOutboundRequestBody() {
        return new UploadPickingOutboundRequestBody();
    }

    /**
     * Create an instance of {@link GetPickingDataRequest }
     * 
     */
    public GetPickingDataRequest createGetPickingDataRequest() {
        return new GetPickingDataRequest();
    }

    /**
     * Create an instance of {@link GetWarehousesRequest }
     * 
     */
    public GetWarehousesRequest createGetWarehousesRequest() {
        return new GetWarehousesRequest();
    }

    /**
     * Create an instance of {@link GetInventoryCheckResponse }
     * 
     */
    public GetInventoryCheckResponse createGetInventoryCheckResponse() {
        return new GetInventoryCheckResponse();
    }

    /**
     * Create an instance of {@link GetPickingDataRequestBody }
     * 
     */
    public GetPickingDataRequestBody createGetPickingDataRequestBody() {
        return new GetPickingDataRequestBody();
    }

    /**
     * Create an instance of {@link GetInboundDataRequest }
     * 
     */
    public GetInboundDataRequest createGetInboundDataRequest() {
        return new GetInboundDataRequest();
    }

    /**
     * Create an instance of {@link UploadInitiativeMoveInboundResponse }
     * 
     */
    public UploadInitiativeMoveInboundResponse createUploadInitiativeMoveInboundResponse() {
        return new UploadInitiativeMoveInboundResponse();
    }

    /**
     * Create an instance of {@link UploadInventoryCheckRequestBody }
     * 
     */
    public UploadInventoryCheckRequestBody createUploadInventoryCheckRequestBody() {
        return new UploadInventoryCheckRequestBody();
    }

    /**
     * Create an instance of {@link UploadLibraryMovementResponse }
     * 
     */
    public UploadLibraryMovementResponse createUploadLibraryMovementResponse() {
        return new UploadLibraryMovementResponse();
    }

    /**
     * Create an instance of {@link UploadInboundReceiveRequest }
     * 
     */
    public UploadInboundReceiveRequest createUploadInboundReceiveRequest() {
        return new UploadInboundReceiveRequest();
    }

    /**
     * Create an instance of {@link InboundReceiveDetail }
     * 
     */
    public InboundReceiveDetail createInboundReceiveDetail() {
        return new InboundReceiveDetail();
    }

    /**
     * Create an instance of {@link BaseResponseBody }
     * 
     */
    public BaseResponseBody createBaseResponseBody() {
        return new BaseResponseBody();
    }

    /**
     * Create an instance of {@link UploadInitiativeMoveInboundRequest }
     * 
     */
    public UploadInitiativeMoveInboundRequest createUploadInitiativeMoveInboundRequest() {
        return new UploadInitiativeMoveInboundRequest();
    }

    /**
     * Create an instance of {@link UploadLibraryMovementRequest }
     * 
     */
    public UploadLibraryMovementRequest createUploadLibraryMovementRequest() {
        return new UploadLibraryMovementRequest();
    }

    /**
     * Create an instance of {@link GetPickingDataResponse }
     * 
     */
    public GetPickingDataResponse createGetPickingDataResponse() {
        return new GetPickingDataResponse();
    }

    /**
     * Create an instance of {@link UploadInitiativeMoveInboundRequestBody }
     * 
     */
    public UploadInitiativeMoveInboundRequestBody createUploadInitiativeMoveInboundRequestBody() {
        return new UploadInitiativeMoveInboundRequestBody();
    }

    /**
     * Create an instance of {@link UploadInboundOnShelvesRequest }
     * 
     */
    public UploadInboundOnShelvesRequest createUploadInboundOnShelvesRequest() {
        return new UploadInboundOnShelvesRequest();
    }

    /**
     * Create an instance of {@link UploadInventoryCheckResponse }
     * 
     */
    public UploadInventoryCheckResponse createUploadInventoryCheckResponse() {
        return new UploadInventoryCheckResponse();
    }

    /**
     * Create an instance of {@link GetInventoryResponse }
     * 
     */
    public GetInventoryResponse createGetInventoryResponse() {
        return new GetInventoryResponse();
    }

    /**
     * Create an instance of {@link GetTransNoRequest }
     * 
     */
    public GetTransNoRequest createGetTransNoRequest() {
        return new GetTransNoRequest();
    }

    /**
     * Create an instance of {@link UploadLibraryMovementRequestBody }
     * 
     */
    public UploadLibraryMovementRequestBody createUploadLibraryMovementRequestBody() {
        return new UploadLibraryMovementRequestBody();
    }

    /**
     * Create an instance of {@link UploadHandOverListRequestBody }
     * 
     */
    public UploadHandOverListRequestBody createUploadHandOverListRequestBody() {
        return new UploadHandOverListRequestBody();
    }

    /**
     * Create an instance of {@link GetReturnOrderRequest }
     * 
     */
    public GetReturnOrderRequest createGetReturnOrderRequest() {
        return new GetReturnOrderRequest();
    }

    /**
     * Create an instance of {@link GetLibraryMovementRequestBody }
     * 
     */
    public GetLibraryMovementRequestBody createGetLibraryMovementRequestBody() {
        return new GetLibraryMovementRequestBody();
    }

    /**
     * Create an instance of {@link GetInboundDataResponse }
     * 
     */
    public GetInboundDataResponse createGetInboundDataResponse() {
        return new GetInboundDataResponse();
    }

    /**
     * Create an instance of {@link GetInventoryCheckRequest }
     * 
     */
    public GetInventoryCheckRequest createGetInventoryCheckRequest() {
        return new GetInventoryCheckRequest();
    }

    /**
     * Create an instance of {@link UploadInitiativeMoveOutboundRequest }
     * 
     */
    public UploadInitiativeMoveOutboundRequest createUploadInitiativeMoveOutboundRequest() {
        return new UploadInitiativeMoveOutboundRequest();
    }

    /**
     * Create an instance of {@link BaseRequestBody }
     * 
     */
    public BaseRequestBody createBaseRequestBody() {
        return new BaseRequestBody();
    }

    /**
     * Create an instance of {@link GetInventoryRequestBody }
     * 
     */
    public GetInventoryRequestBody createGetInventoryRequestBody() {
        return new GetInventoryRequestBody();
    }

    /**
     * Create an instance of {@link PickingOutboundDetail }
     * 
     */
    public PickingOutboundDetail createPickingOutboundDetail() {
        return new PickingOutboundDetail();
    }

    /**
     * Create an instance of {@link TransNoDetail }
     * 
     */
    public TransNoDetail createTransNoDetail() {
        return new TransNoDetail();
    }

    /**
     * Create an instance of {@link GetLibraryMovementRequest }
     * 
     */
    public GetLibraryMovementRequest createGetLibraryMovementRequest() {
        return new GetLibraryMovementRequest();
    }

    /**
     * Create an instance of {@link GetInventoryCheckRequestBody }
     * 
     */
    public GetInventoryCheckRequestBody createGetInventoryCheckRequestBody() {
        return new GetInventoryCheckRequestBody();
    }

    /**
     * Create an instance of {@link UploadReturnOrderRequestBody }
     * 
     */
    public UploadReturnOrderRequestBody createUploadReturnOrderRequestBody() {
        return new UploadReturnOrderRequestBody();
    }

    /**
     * Create an instance of {@link GetTransNoResponseBody }
     * 
     */
    public GetTransNoResponseBody createGetTransNoResponseBody() {
        return new GetTransNoResponseBody();
    }

    /**
     * Create an instance of {@link UploadInitiativeMoveOutboundRequestBody }
     * 
     */
    public UploadInitiativeMoveOutboundRequestBody createUploadInitiativeMoveOutboundRequestBody() {
        return new UploadInitiativeMoveOutboundRequestBody();
    }

    /**
     * Create an instance of {@link AuthenticationFailed }
     * 
     */
    public AuthenticationFailed createAuthenticationFailed() {
        return new AuthenticationFailed();
    }

    /**
     * Create an instance of {@link GetReturnOrderRequestBody }
     * 
     */
    public GetReturnOrderRequestBody createGetReturnOrderRequestBody() {
        return new GetReturnOrderRequestBody();
    }

    /**
     * Create an instance of {@link UploadPickingOutboundRequest }
     * 
     */
    public UploadPickingOutboundRequest createUploadPickingOutboundRequest() {
        return new UploadPickingOutboundRequest();
    }

    /**
     * Create an instance of {@link GetInventoryResponseBody }
     * 
     */
    public GetInventoryResponseBody createGetInventoryResponseBody() {
        return new GetInventoryResponseBody();
    }

    /**
     * Create an instance of {@link GetTransNoRequestBody }
     * 
     */
    public GetTransNoRequestBody createGetTransNoRequestBody() {
        return new GetTransNoRequestBody();
    }

    /**
     * Create an instance of {@link LibraryMovementInOut }
     * 
     */
    public LibraryMovementInOut createLibraryMovementInOut() {
        return new LibraryMovementInOut();
    }

    /**
     * Create an instance of {@link GetWarehousesResponse }
     * 
     */
    public GetWarehousesResponse createGetWarehousesResponse() {
        return new GetWarehousesResponse();
    }

    /**
     * Create an instance of {@link GetInboundDataResponseBody }
     * 
     */
    public GetInboundDataResponseBody createGetInboundDataResponseBody() {
        return new GetInboundDataResponseBody();
    }

    /**
     * Create an instance of {@link GetInboundOnShelvesResponseBody }
     * 
     */
    public GetInboundOnShelvesResponseBody createGetInboundOnShelvesResponseBody() {
        return new GetInboundOnShelvesResponseBody();
    }

    /**
     * Create an instance of {@link GetReturnOrderResponse }
     * 
     */
    public GetReturnOrderResponse createGetReturnOrderResponse() {
        return new GetReturnOrderResponse();
    }

    /**
     * Create an instance of {@link ExecuteOrderRequest }
     * 
     */
    public ExecuteOrderRequest createExecuteOrderRequest() {
        return new ExecuteOrderRequest();
    }

    /**
     * Create an instance of {@link LoginRequest }
     * 
     */
    public LoginRequest createLoginRequest() {
        return new LoginRequest();
    }

    /**
     * Create an instance of {@link UploadInitiativeMoveOutboundResponseBody }
     * 
     */
    public UploadInitiativeMoveOutboundResponseBody createUploadInitiativeMoveOutboundResponseBody() {
        return new UploadInitiativeMoveOutboundResponseBody();
    }

    /**
     * Create an instance of {@link GetInboundOnShelvesResponse }
     * 
     */
    public GetInboundOnShelvesResponse createGetInboundOnShelvesResponse() {
        return new GetInboundOnShelvesResponse();
    }

    /**
     * Create an instance of {@link GetInventoryCheckResponseBody }
     * 
     */
    public GetInventoryCheckResponseBody createGetInventoryCheckResponseBody() {
        return new GetInventoryCheckResponseBody();
    }

    /**
     * Create an instance of {@link GetWarehousesResponseBody }
     * 
     */
    public GetWarehousesResponseBody createGetWarehousesResponseBody() {
        return new GetWarehousesResponseBody();
    }

    /**
     * Create an instance of {@link ShelvesSkuDetial }
     * 
     */
    public ShelvesSkuDetial createShelvesSkuDetial() {
        return new ShelvesSkuDetial();
    }

    /**
     * Create an instance of {@link UploadInventoryCheckRequest }
     * 
     */
    public UploadInventoryCheckRequest createUploadInventoryCheckRequest() {
        return new UploadInventoryCheckRequest();
    }

    /**
     * Create an instance of {@link AuthResponseHeader }
     * 
     */
    public AuthResponseHeader createAuthResponseHeader() {
        return new AuthResponseHeader();
    }

    /**
     * Create an instance of {@link UploadHandOverListResponse }
     * 
     */
    public UploadHandOverListResponse createUploadHandOverListResponse() {
        return new UploadHandOverListResponse();
    }

    /**
     * Create an instance of {@link ExecuteOrderResponse }
     * 
     */
    public ExecuteOrderResponse createExecuteOrderResponse() {
        return new ExecuteOrderResponse();
    }

    /**
     * Create an instance of {@link UploadHandOverListRequest }
     * 
     */
    public UploadHandOverListRequest createUploadHandOverListRequest() {
        return new UploadHandOverListRequest();
    }

    /**
     * Create an instance of {@link GetInventoryRequest }
     * 
     */
    public GetInventoryRequest createGetInventoryRequest() {
        return new GetInventoryRequest();
    }

    /**
     * Create an instance of {@link GetLibraryMovementResponseBody }
     * 
     */
    public GetLibraryMovementResponseBody createGetLibraryMovementResponseBody() {
        return new GetLibraryMovementResponseBody();
    }

    /**
     * Create an instance of {@link UploadReturnOrderResponse }
     * 
     */
    public UploadReturnOrderResponse createUploadReturnOrderResponse() {
        return new UploadReturnOrderResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReturnOrderResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getReturnOrderResponse")
    public JAXBElement<GetReturnOrderResponse> createGetReturnOrderResponse(GetReturnOrderResponse value) {
        return new JAXBElement<GetReturnOrderResponse>(_GetReturnOrderResponse_QNAME, GetReturnOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadInboundOnShelvesRequest }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadInboundOnShelvesRequest")
    public JAXBElement<UploadInboundOnShelvesRequest> createUploadInboundOnShelvesRequest(UploadInboundOnShelvesRequest value) {
        return new JAXBElement<UploadInboundOnShelvesRequest>(_UploadInboundOnShelvesRequest_QNAME, UploadInboundOnShelvesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadInventoryCheckResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadInventoryCheckResponse")
    public JAXBElement<UploadInventoryCheckResponse> createUploadInventoryCheckResponse(UploadInventoryCheckResponse value) {
        return new JAXBElement<UploadInventoryCheckResponse>(_UploadInventoryCheckResponse_QNAME, UploadInventoryCheckResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadPickingOutboundRequest }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadPickingOutboundRequest")
    public JAXBElement<UploadPickingOutboundRequest> createUploadPickingOutboundRequest(UploadPickingOutboundRequest value) {
        return new JAXBElement<UploadPickingOutboundRequest>(_UploadPickingOutboundRequest_QNAME, UploadPickingOutboundRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInboundDataResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getInboundDataResponse")
    public JAXBElement<GetInboundDataResponse> createGetInboundDataResponse(GetInboundDataResponse value) {
        return new JAXBElement<GetInboundDataResponse>(_GetInboundDataResponse_QNAME, GetInboundDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInboundOnShelvesResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getInboundOnShelvesResponse")
    public JAXBElement<GetInboundOnShelvesResponse> createGetInboundOnShelvesResponse(GetInboundOnShelvesResponse value) {
        return new JAXBElement<GetInboundOnShelvesResponse>(_GetInboundOnShelvesResponse_QNAME, GetInboundOnShelvesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInventoryCheckRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getInventoryCheckRequest")
    public JAXBElement<GetInventoryCheckRequest> createGetInventoryCheckRequest(GetInventoryCheckRequest value) {
        return new JAXBElement<GetInventoryCheckRequest>(_GetInventoryCheckRequest_QNAME, GetInventoryCheckRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}
     * {@link UploadInitiativeMoveOutboundResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadInitiativeMoveOutboundResponse")
    public JAXBElement<UploadInitiativeMoveOutboundResponse> createUploadInitiativeMoveOutboundResponse(UploadInitiativeMoveOutboundResponse value) {
        return new JAXBElement<UploadInitiativeMoveOutboundResponse>(_UploadInitiativeMoveOutboundResponse_QNAME, UploadInitiativeMoveOutboundResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteOrderRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "executeOrderRequest")
    public JAXBElement<ExecuteOrderRequest> createExecuteOrderRequest(ExecuteOrderRequest value) {
        return new JAXBElement<ExecuteOrderRequest>(_ExecuteOrderRequest_QNAME, ExecuteOrderRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "loginRequest")
    public JAXBElement<LoginRequest> createLoginRequest(LoginRequest value) {
        return new JAXBElement<LoginRequest>(_LoginRequest_QNAME, LoginRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLibraryMovementRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getLibraryMovementRequest")
    public JAXBElement<GetLibraryMovementRequest> createGetLibraryMovementRequest(GetLibraryMovementRequest value) {
        return new JAXBElement<GetLibraryMovementRequest>(_GetLibraryMovementRequest_QNAME, GetLibraryMovementRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPickingDataResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getPickingDataResponse")
    public JAXBElement<GetPickingDataResponse> createGetPickingDataResponse(GetPickingDataResponse value) {
        return new JAXBElement<GetPickingDataResponse>(_GetPickingDataResponse_QNAME, GetPickingDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadInitiativeMoveOutboundRequest }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadInitiativeMoveOutboundRequest")
    public JAXBElement<UploadInitiativeMoveOutboundRequest> createUploadInitiativeMoveOutboundRequest(UploadInitiativeMoveOutboundRequest value) {
        return new JAXBElement<UploadInitiativeMoveOutboundRequest>(_UploadInitiativeMoveOutboundRequest_QNAME, UploadInitiativeMoveOutboundRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadInventoryCheckRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadInventoryCheckRequest")
    public JAXBElement<UploadInventoryCheckRequest> createUploadInventoryCheckRequest(UploadInventoryCheckRequest value) {
        return new JAXBElement<UploadInventoryCheckRequest>(_UploadInventoryCheckRequest_QNAME, UploadInventoryCheckRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadPickingOutboundResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadPickingOutboundResponse")
    public JAXBElement<UploadPickingOutboundResponse> createUploadPickingOutboundResponse(UploadPickingOutboundResponse value) {
        return new JAXBElement<UploadPickingOutboundResponse>(_UploadPickingOutboundResponse_QNAME, UploadPickingOutboundResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteOrderResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "executeOrderResponse")
    public JAXBElement<ExecuteOrderResponse> createExecuteOrderResponse(ExecuteOrderResponse value) {
        return new JAXBElement<ExecuteOrderResponse>(_ExecuteOrderResponse_QNAME, ExecuteOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPickingDataRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getPickingDataRequest")
    public JAXBElement<GetPickingDataRequest> createGetPickingDataRequest(GetPickingDataRequest value) {
        return new JAXBElement<GetPickingDataRequest>(_GetPickingDataRequest_QNAME, GetPickingDataRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInventoryRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getInventoryRequest")
    public JAXBElement<GetInventoryRequest> createGetInventoryRequest(GetInventoryRequest value) {
        return new JAXBElement<GetInventoryRequest>(_GetInventoryRequest_QNAME, GetInventoryRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWarehousesResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getWarehousesResponse")
    public JAXBElement<GetWarehousesResponse> createGetWarehousesResponse(GetWarehousesResponse value) {
        return new JAXBElement<GetWarehousesResponse>(_GetWarehousesResponse_QNAME, GetWarehousesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadInboundReceiveRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadInboundReceiveRequest")
    public JAXBElement<UploadInboundReceiveRequest> createUploadInboundReceiveRequest(UploadInboundReceiveRequest value) {
        return new JAXBElement<UploadInboundReceiveRequest>(_UploadInboundReceiveRequest_QNAME, UploadInboundReceiveRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadInitiativeMoveInboundRequest }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadInitiativeMoveInboundRequest")
    public JAXBElement<UploadInitiativeMoveInboundRequest> createUploadInitiativeMoveInboundRequest(UploadInitiativeMoveInboundRequest value) {
        return new JAXBElement<UploadInitiativeMoveInboundRequest>(_UploadInitiativeMoveInboundRequest_QNAME, UploadInitiativeMoveInboundRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadLibraryMovementRequest }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadLibraryMovementRequest")
    public JAXBElement<UploadLibraryMovementRequest> createUploadLibraryMovementRequest(UploadLibraryMovementRequest value) {
        return new JAXBElement<UploadLibraryMovementRequest>(_UploadLibraryMovementRequest_QNAME, UploadLibraryMovementRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadInboundOnShelvesResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadInboundOnShelvesResponse")
    public JAXBElement<UploadInboundOnShelvesResponse> createUploadInboundOnShelvesResponse(UploadInboundOnShelvesResponse value) {
        return new JAXBElement<UploadInboundOnShelvesResponse>(_UploadInboundOnShelvesResponse_QNAME, UploadInboundOnShelvesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInboundOnShelvesRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getInboundOnShelvesRequest")
    public JAXBElement<GetInboundOnShelvesRequest> createGetInboundOnShelvesRequest(GetInboundOnShelvesRequest value) {
        return new JAXBElement<GetInboundOnShelvesRequest>(_GetInboundOnShelvesRequest_QNAME, GetInboundOnShelvesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadLibraryMovementResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadLibraryMovementResponse")
    public JAXBElement<UploadLibraryMovementResponse> createUploadLibraryMovementResponse(UploadLibraryMovementResponse value) {
        return new JAXBElement<UploadLibraryMovementResponse>(_UploadLibraryMovementResponse_QNAME, UploadLibraryMovementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReturnOrderRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getReturnOrderRequest")
    public JAXBElement<GetReturnOrderRequest> createGetReturnOrderRequest(GetReturnOrderRequest value) {
        return new JAXBElement<GetReturnOrderRequest>(_GetReturnOrderRequest_QNAME, GetReturnOrderRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInboundDataRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getInboundDataRequest")
    public JAXBElement<GetInboundDataRequest> createGetInboundDataRequest(GetInboundDataRequest value) {
        return new JAXBElement<GetInboundDataRequest>(_GetInboundDataRequest_QNAME, GetInboundDataRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransNoResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getTransNoResponse")
    public JAXBElement<GetTransNoResponse> createGetTransNoResponse(GetTransNoResponse value) {
        return new JAXBElement<GetTransNoResponse>(_GetTransNoResponse_QNAME, GetTransNoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWarehousesRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getWarehousesRequest")
    public JAXBElement<GetWarehousesRequest> createGetWarehousesRequest(GetWarehousesRequest value) {
        return new JAXBElement<GetWarehousesRequest>(_GetWarehousesRequest_QNAME, GetWarehousesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransNoRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getTransNoRequest")
    public JAXBElement<GetTransNoRequest> createGetTransNoRequest(GetTransNoRequest value) {
        return new JAXBElement<GetTransNoRequest>(_GetTransNoRequest_QNAME, GetTransNoRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadInitiativeMoveInboundResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadInitiativeMoveInboundResponse")
    public JAXBElement<UploadInitiativeMoveInboundResponse> createUploadInitiativeMoveInboundResponse(UploadInitiativeMoveInboundResponse value) {
        return new JAXBElement<UploadInitiativeMoveInboundResponse>(_UploadInitiativeMoveInboundResponse_QNAME, UploadInitiativeMoveInboundResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadInboundReceiveResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadInboundReceiveResponse")
    public JAXBElement<UploadInboundReceiveResponse> createUploadInboundReceiveResponse(UploadInboundReceiveResponse value) {
        return new JAXBElement<UploadInboundReceiveResponse>(_UploadInboundReceiveResponse_QNAME, UploadInboundReceiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLibraryMovementResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getLibraryMovementResponse")
    public JAXBElement<GetLibraryMovementResponse> createGetLibraryMovementResponse(GetLibraryMovementResponse value) {
        return new JAXBElement<GetLibraryMovementResponse>(_GetLibraryMovementResponse_QNAME, GetLibraryMovementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInventoryResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getInventoryResponse")
    public JAXBElement<GetInventoryResponse> createGetInventoryResponse(GetInventoryResponse value) {
        return new JAXBElement<GetInventoryResponse>(_GetInventoryResponse_QNAME, GetInventoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadHandOverListResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadHandOverListResponse")
    public JAXBElement<UploadHandOverListResponse> createUploadHandOverListResponse(UploadHandOverListResponse value) {
        return new JAXBElement<UploadHandOverListResponse>(_UploadHandOverListResponse_QNAME, UploadHandOverListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInventoryCheckResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "getInventoryCheckResponse")
    public JAXBElement<GetInventoryCheckResponse> createGetInventoryCheckResponse(GetInventoryCheckResponse value) {
        return new JAXBElement<GetInventoryCheckResponse>(_GetInventoryCheckResponse_QNAME, GetInventoryCheckResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadReturnOrderRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadReturnOrderRequest")
    public JAXBElement<UploadReturnOrderRequest> createUploadReturnOrderRequest(UploadReturnOrderRequest value) {
        return new JAXBElement<UploadReturnOrderRequest>(_UploadReturnOrderRequest_QNAME, UploadReturnOrderRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadReturnOrderResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadReturnOrderResponse")
    public JAXBElement<UploadReturnOrderResponse> createUploadReturnOrderResponse(UploadReturnOrderResponse value) {
        return new JAXBElement<UploadReturnOrderResponse>(_UploadReturnOrderResponse_QNAME, UploadReturnOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadHandOverListRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "uploadHandOverListRequest")
    public JAXBElement<UploadHandOverListRequest> createUploadHandOverListRequest(UploadHandOverListRequest value) {
        return new JAXBElement<UploadHandOverListRequest>(_UploadHandOverListRequest_QNAME, UploadHandOverListRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticationFailed }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jumbo.com/pda/", name = "authenticationFailed")
    public JAXBElement<AuthenticationFailed> createAuthenticationFailed(AuthenticationFailed value) {
        return new JAXBElement<AuthenticationFailed>(_AuthenticationFailed_QNAME, AuthenticationFailed.class, null, value);
    }

}
