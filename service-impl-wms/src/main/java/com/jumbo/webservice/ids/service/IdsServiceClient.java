package com.jumbo.webservice.ids.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
// import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.util.zip.JaxbUtil;
import com.jumbo.webservice.ids.BhSyncResponse;

// import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.wms.model.vmi.ids.HostRequest;

public class IdsServiceClient {
    private String idsAddress = "http://wcs.lfuat.net:20185/wms-aeo";
    protected static final Logger logger = LoggerFactory.getLogger(IdsServiceClient.class);

    // @Autowired
    // private IdsManager idsManager;

    private String path = "";


    public void connectIdsService(String constantsType, String date) {
        try {

            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");

            WebClient client = WebClient.create(idsAddress);
            HostRequest request = new HostRequest();
            request.setKey("AEO_ERP_WMS_API");
            request.setData(sf.format(new Date()));
            request.setSign("f132050c38301dff2a299d0a1f554c50");
            request.setVersion("1.0");
            request.setData(date);
            request.setServiceType(constantsType);
            String requestXML = JaxbUtil.marshal(request);
            System.out.println(requestXML);
            BhSyncResponse response = client.path(path).accept(MediaType.APPLICATION_XML).post(request, BhSyncResponse.class);
            System.out.println(JaxbUtil.marshal(response));
            System.out.println(response.getOpCode());
            System.out.println(response.getResult());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("connectIdsService:" + constantsType + ":" + date, e);
            }
        }
    }
}
