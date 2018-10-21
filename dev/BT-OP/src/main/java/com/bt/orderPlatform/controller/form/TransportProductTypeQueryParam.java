package com.bt.orderPlatform.controller.form;
import com.bt.orderPlatform.page.QueryParameter;

public class TransportProductTypeQueryParam extends QueryParameter {
	
		private Integer id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private String express_code;			//承运商编码	public String getExpress_code() {
		return express_code;
	}
	public void setExpress_code(String express_code) {
		this.express_code = express_code;
	}
	private String product_type_code;			//产品类型代码	private String product_type_name;			//产品类型名称	private String status;			//	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getProduct_type_code() {	    return this.product_type_code;	}	public void setProduct_type_code(String product_type_code) {	    this.product_type_code=product_type_code;	}	public String getProduct_type_name() {	    return this.product_type_name;	}	public void setProduct_type_name(String product_type_name) {	    this.product_type_name=product_type_name;	}	public String getStatus() {	    return this.status;	}	public void setStatus(String status) {	    this.status=status;	}
	
}
