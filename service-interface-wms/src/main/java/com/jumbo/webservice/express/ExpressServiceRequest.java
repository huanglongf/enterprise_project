package com.jumbo.webservice.express;

import java.io.Serializable;
import java.util.List;

import com.jumbo.webservice.express.ExpressRoute;

/**
 * 路由信息回传实体
 * @author kai.zhu
 *
 */
public class ExpressServiceRequest implements Serializable {
	
	private static final long serialVersionUID = 3304030252340414603L;
	
	private String mailno; 				// 运单号
	private List<ExpressRoute> route;	// 路由信息对象
	
	public String getMailno() {
		return mailno;
	}
	public void setMailno(String mailno) {
		this.mailno = mailno;
	}
	public List<ExpressRoute> getRoute() {
		return route;
	}
	public void setRoute(List<ExpressRoute> route) {
		this.route = route;
	}
}
