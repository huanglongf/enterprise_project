package com.jumbo.webservice.sku.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.webservice.base.AuthenticationFailedException;
import com.jumbo.webservice.pda.getScanSku.GetScanSkuRequestBody;
import com.jumbo.webservice.pda.getScanSku.ScanSkuRequest;
import com.jumbo.wms.manager.operationcenter.ScanSkuRecordManager;
import com.jumbo.wms.model.command.ScanSkuRecordCommand;

@javax.jws.WebService(serviceName = "SkuServiceService", portName = "SkuServicePort", targetNamespace = "http://webservice.jumbo.com/sku/", endpointInterface = "com.jumbo.webservice.sku.service.ScanSkuService")
public class ScanSkuServiceImpl implements ScanSkuService {
	 protected static final Logger logger = LoggerFactory.getLogger(ScanSkuServiceImpl.class);
	 @Autowired
	 private ScanSkuRecordManager scanSkuRecordManager;
	 
	public void saveScanRecords(ScanSkuRequest getSkuRequest) throws AuthenticationFailedException {
		 List<ScanSkuRecordCommand> rs = new ArrayList<ScanSkuRecordCommand>();
		 for (GetScanSkuRequestBody body: getSkuRequest.getGetScanSkuRequestBody()) {
			 ScanSkuRecordCommand scan=new ScanSkuRecordCommand();
			 scan.setQty(body.getQty());
			 scan.setSkuBarcode(body.getSkuBarcode());
			 scan.setLocationCode(body.getLocationCode());
			 scan.setBatchCode(body.getBatchCode());
			 rs.add(scan);
		 }
		 scanSkuRecordManager.saveScanRecords(rs, null);
	}

}
