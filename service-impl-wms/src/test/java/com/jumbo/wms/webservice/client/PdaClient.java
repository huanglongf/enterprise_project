package com.jumbo.wms.webservice.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.jumbo.wms.webservice.pda.AuthRequestHeader;
import com.jumbo.wms.webservice.pda.AuthenticationFailedException;
import com.jumbo.wms.webservice.pda.GetInboundOnShelvesRequest;
import com.jumbo.wms.webservice.pda.GetInboundOnShelvesRequestBody;
import com.jumbo.wms.webservice.pda.GetInboundOnShelvesResponse;
import com.jumbo.wms.webservice.pda.GetInventoryRequest;
import com.jumbo.wms.webservice.pda.GetInventoryRequestBody;
import com.jumbo.wms.webservice.pda.GetInventoryResponse;
import com.jumbo.wms.webservice.pda.GetSkuRequest;
import com.jumbo.wms.webservice.pda.GetSkuRequestBody;
import com.jumbo.wms.webservice.pda.GetSkuResponse;
import com.jumbo.wms.webservice.pda.LoginRequest;
import com.jumbo.wms.webservice.pda.LoginRequestBody;
import com.jumbo.wms.webservice.pda.LoginResponse;
import com.jumbo.wms.webservice.pda.PdaService;
import com.jumbo.wms.webservice.pda.PdaServiceService;

public class PdaClient {

    private static final QName SERVICE_NAME = new QName("http://webservice.jumbo.com/pda/", "PdaServiceService");

    public static void main(String[] args) throws AuthenticationFailedException {
        String  pwd = "TODO";
        System.out.println("pwd is : " +pwd );
        LoginResponse  rs = login("jingkai.sun", pwd);
        System.out.println(rs.getBaseResponseBody().getStatus());
    }

    private static AuthRequestHeader getAuth() {
        AuthRequestHeader auth = new AuthRequestHeader();
        auth.setOrganizationCode("aaa");
//        auth.setCallTime(FormatUtil.formatDate(new Date(), FormatUtil.DATE_FORMATE_YYYYMMDDHHMMSS));
        auth.setCallTime("23232");
        auth.setSignSalt("bbb");
        auth.setSequenceCode("111111");
        String sign = sign(auth.getOrganizationCode(), auth.getSequenceCode(), auth.getSignSalt(), auth.getCallTime());
        auth.setSign(sign);
        return auth;
    }

    private static String sign(String organizationCode, String key, String signSalt, String callTime) {
        return com.jumbo.util.Md5Util.getMD5Str(organizationCode + callTime + signSalt + key);
    }


    public static GetSkuResponse getSku(String barcode) {
        URL wsdlURL = PdaServiceService.WSDL_LOCATION;
        PdaServiceService ss = new PdaServiceService(wsdlURL, SERVICE_NAME);
        PdaService port = ss.getPdaServicePort();
        GetSkuRequest skur = new GetSkuRequest();
        skur.setAuthRequestHeader(getAuth());
        GetSkuRequestBody sb = new GetSkuRequestBody();
        sb.setBarcode(barcode);
        skur.setGetSkuRequestBody(sb);
        try {
            GetSkuResponse rs = port.getSku(skur);
            return rs;
        } catch (AuthenticationFailedException e) {
            return null;
        }
    }

    public static GetInventoryResponse getInventory(String uniqCode, String skuBarCode, String location) {
        URL wsdlURL = PdaServiceService.WSDL_LOCATION;
        PdaServiceService ss = new PdaServiceService(wsdlURL, SERVICE_NAME);
        PdaService port = ss.getPdaServicePort();
        try {
            GetInventoryRequest getInventoryRequest = new GetInventoryRequest();
            getInventoryRequest.setAuthRequestHeader(getAuth());
            GetInventoryRequestBody rbody = new GetInventoryRequestBody();
            rbody.setLocation(location);
            rbody.setSkuBarCode(skuBarCode);
            rbody.setUniqCode(location);
            GetInventoryResponse _getInventory__return = port.getInventory(getInventoryRequest);
            return _getInventory__return;
        } catch (AuthenticationFailedException e) {
            return null;
        }
    }
    
    public static void getinbound() throws AuthenticationFailedException{
        URL wsdlURL = PdaServiceService.WSDL_LOCATION;
        PdaServiceService ss = new PdaServiceService(wsdlURL, SERVICE_NAME);
        PdaService port = ss.getPdaServicePort();
        GetInboundOnShelvesRequest rq = new GetInboundOnShelvesRequest();
        rq.setAuthRequestHeader(getAuth());
        GetInboundOnShelvesRequestBody rb = new GetInboundOnShelvesRequestBody();
        rb.setCode("C3423136000073004274");
        rb.setUniqCode("1061");
        rq.setGetInboundOnShelvesRequestBody(rb);
        GetInboundOnShelvesResponse x = port.getInboundOnShelves(rq);
        System.out.println(x);
    }

    public static LoginResponse login(String username, String password) {
        URL wsdlURL = PdaServiceService.WSDL_LOCATION;
        PdaServiceService ss = new PdaServiceService(wsdlURL, SERVICE_NAME);
        PdaService port = ss.getPdaServicePort();
        LoginRequest lgRequest = new LoginRequest();
        lgRequest.setAuthRequestHeader(getAuth());
        LoginRequestBody lrb = new LoginRequestBody();
        lrb.setPassword(password);
        lrb.setUser(username);
        lgRequest.setLoginRequestBody(lrb);
        try {
            System.out.println("login invoking");
            LoginResponse lgReturn = port.login(lgRequest);
            return lgReturn;
        } catch (AuthenticationFailedException e) {
            return null;
        }
    }
}
