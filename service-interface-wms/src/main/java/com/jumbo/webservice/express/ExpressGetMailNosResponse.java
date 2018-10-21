package com.jumbo.webservice.express;

import java.io.Serializable;
import java.util.List;

import com.jumbo.webservice.express.ExpressResponse;

/**
 * 标准快递获取运单号实体
 * @author kai.zhu
 *
 */
public class ExpressGetMailNosResponse extends ExpressResponse implements Serializable {
	
	private static final long serialVersionUID = 6481715980871548097L;
	
	private String regionCode;	// 区域编码
	private String batchId;		// 批次号，系统唯一用于标识此次请求ID
	private List<TransNo> mailnos;	// 运单号集合
	
	public static class TransNo implements Serializable {
		
		private static final long serialVersionUID = -8871170622530699660L;
		
		private String mailno; // 运单号

		public String getMailno() {
			return mailno;
		}
		public void setMailno(String mailno) {
			this.mailno = mailno;
		}
	}
	
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public List<TransNo> getMailnos() {
		return mailnos;
	}

	public void setMailnos(List<TransNo> mailnos) {
		this.mailnos = mailnos;
	}

}
