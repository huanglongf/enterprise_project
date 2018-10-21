<%@ page language= "java" import= "java.util.*" pageEncoding= "utf-8" %>
<!DOCTYPE html>
<html>
	<head lang= "en" >
		<meta charset= "UTF-8" >
		<%@ include file= "/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>css/table.css" rel= "stylesheet" />
		
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>lmis/client_with_store_management/js/client.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>lmis/client_with_store_management/js/store.js" ></script>
	</head>
	<body>
		<div class= "page-header" align= "left" >
			<h1>客户编辑</h1>
		</div>
		<input id= "id" type= "hidden" value= "${client.id }" />
		<table style= "width: 100%;" class= "clearfix form-group form_table no-margin-bottom no-border-bottom no-padding-bottom" >
			<tr>
				<td class= "right" nowrap= "nowrap" width= "25%" >
					<label class= "no-padding-right blue" for= "client_code" >
						客户编码&nbsp;: 
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input ${not empty client.id? "readonly= 'readonly'": "" } id= "client_code" name= "client_code" type= "text" placeholder= "必填，最大长度20，字母数字组合" value= "${client.client_code }" />
					<span></span>
				</td>
				<td class= "right" nowrap= "nowrap" width= "15%" >
					<label class="no-padding-right blue" for="client_name">
						客户名称&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "client_name" name= "client_name" type= "text" placeholder= "必填，最大长度25" value= "${client.client_name }" />
				</td>
			</tr>		
			<tr>
				<td class= "right" nowrap= "nowrap" width= "25%" >
					<label class= "no-padding-right blue" for= "client_type" >
						客户类型&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<select id= "client_type" name= "client_type" style="width: 100%;" >
						<option value= "-1" >---请选择---</option>
						<option value= "0" ${client.client_type == "0"? "selected= 'selected'": "" } >一般客户</option>
						<option value= "1" ${client.client_type == "1"? "selected= 'selected'": "" } >普通客户</option>
						<option value= "2" ${client.client_type == "2"? "selected= 'selected'": "" } >重要客户</option>
						<option value= "3" ${client.client_type == "3"? "selected= 'selected'": "" } >其它</option>
					</select>
				</td>
				<td class= "right" nowrap= "nowrap" width= "15%" >
					<label class="no-padding-right blue" for="settlement_method">
						结算方式&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<select id= "settlement_method" name= "settlement_method" style="width: 100%;" >
						<option value= "-1" >---请选择---</option>
						<option value= "0" ${client.settlement_method == 0? "selected= 'selected'": "" } >代销</option>
						<option value= "1" ${client.settlement_method == 1? "selected= 'selected'": "" } >结算经销</option>
						<option value= "2" ${client.settlement_method == 2? "selected= 'selected'": "" } >付款经销</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class= "right" nowrap= "nowrap" width= "25%" >
					<label class= "no-padding-right blue" for= "contact" >
						联系人&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "contact" name= "contact" type= "text" placeholder= "必填，最大长度10" value= "${client.contact }" />
				</td>
				<td class= "right" nowrap= "nowrap" width= "15%" >
					<label class="no-padding-right blue" for="phone">
						联系电话&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "phone" name= "phone" type= "text" placeholder= "必填，手机号或固定电话" value= "${client.phone }" />
				</td>
			</tr>
			<tr>
				<td class= "right" nowrap= "nowrap" width= "25%" >
					<label class= "no-padding-right blue" for= "address" >
						联系地址&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "address" name= "address" type= "text" placeholder= "必填，最大长度100" value= "${client.address }" />
				</td>
				<td class= "right" nowrap= "nowrap" width= "15%" >
					<label class="no-padding-right blue" for="company">
						开票公司&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "company" name= "company" type= "text" placeholder= "必填，最大长度100" value= "${client.company }" />
				</td>
			</tr>
			<tr>
				<td class= "right" nowrap= "nowrap" width= "25%" >
					<label class= "no-padding-right blue" for= "invoice_type" >
						发票类型&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<select id= "invoice_type" name= "invoice_type" style="width: 100%;" >
						<option value= "-1" >---请选择---</option>
						<option value= "0" ${client.invoice_type == "0"? "selected= 'selected'": "" } >手写发票</option>
						<option value= "1" ${client.invoice_type == "1"? "selected= 'selected'": "" } >机打发票</option>
						<option value= "2" ${client.invoice_type == "2"? "selected= 'selected'": "" } >普通发票</option>
						<option value= "3" ${client.invoice_type == "3"? "selected= 'selected'": "" } >增值税发票</option>
						<option value= "4" ${client.invoice_type == "4"? "selected= 'selected'": "" } >其它发票</option>
					</select>
				</td>
				<td class= "right" nowrap= "nowrap" width= "15%" >
					<label class= "no-padding-right blue" for= "invoice_info">
						发票信息&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<textarea cols= "30" style= "width: 100%; height: 150px;" id= "invoice_info" name= "invoice_info" placeholder= "必填，最大长度200" >${client.invoice_info }</textarea>
				</td>
			</tr>
			<tr>
				<td class= "right" nowrap= "nowrap" width= "25%" >
					<label class= "no-padding-right blue" for= "remark" >
						备注&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "remark" name= "remark" type= "text" placeholder= "最大长度100" value= "${client.remark }" />
				</td>
				<td class= "right" nowrap= "nowrap" width= "15%" >
					<label class="no-padding-right blue" for="validity">
						是否有效&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "validity" name= "validity" type= "checkbox" class= "ace ace-switch ace-switch-5" ${client.validity == "true"? "checked= 'checked'": "" } />
					<span class= "lbl" ></span>
				</td>
			</tr>
			<tr>
				<td width= "25%" />
				<td class= "left" width= "45%" colspan= "3">
					<div style= "width: 100%" class= "form-actions no-margin-bottom no-padding-bottom no-border-bottom" align= "center" >
						<button class= "btn btn-info" type= "button" onclick= "save();" >
							<i class= "icon-ok bigger-110" ></i>提交
						</button>
						&nbsp;&nbsp;&nbsp;
						<button class= "btn" type= "reset" onclick= "openDiv('${root }/control/clientController/queryList.do');" >
							<i class= "icon-undo bigger-110" ></i>返回
						</button>
					</div>		
				</td>
				<td class="right" nowrap= "nowrap" width= "30%" />
			</tr>
		</table>
 		<div id= "store_list" class= "form-group" style= "width: 100%; ${empty client.id? 'display: none;': '' }" >
			<jsp:include page= "/lmis/client_with_store_management/store_list.jsp" flush= "true" />
		</div>
 		<!-- 店铺编辑页面弹窗 -->
		<div id= "store_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" ></h4>
					</div>
					<div class= "modal-body" >
						<input id= "store_id" name= "store_id" type= "hidden" />
						<div class= "form-group" >
				    		<label class= "blue" for= "client_form" >所属客户&nbsp;:</label>
				    		<input id= "client_form" name= "client_form" class= "form-control" type= "text" value= "${client.client_name }" readonly= "readonly" />
				    	</div>
						<div class= "form-group" >
				    		<label class= "blue" for= "cost_center_form" >成本中心&nbsp;:</label>
				    		<input id= "cost_center_form" name= "cost_center_form" class= "form-control" type= "text" placeholder= "必填，最大长度100" />
				    	</div>
						<div class= "form-group" >
				    		<label class= "blue" for= "store_code_form" >店铺编码&nbsp;:</label>
				    		<input id= "store_code_form" name= "store_code_form" class= "form-control" type= "text" placeholder= "必填，最大长度25" />
				    	</div>
						<div class= "form-group" >
				    		<label class= "blue" for= "store_name_form" >店铺名称&nbsp;:</label>
				    		<input id= "store_name_form" name= "store_name_form" class= "form-control" type= "text" placeholder= "必填，最大长度25" />
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "store_type_form" >店铺类型&nbsp;:</label>
				    		<select id= "store_type_form" name= "store_type_form" class= "form-control" >
								<option value= "-1" >---请选择---</option>
								<option value= "0" >A类</option>
								<option value= "1" >B类</option>
								<option value= "2" >其它</option>
							</select>
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "settlement_method_form" >结算方式&nbsp;:</label>
				    		<select id= "settlement_method_form" name= "settlement_method_form" class= "form-control" >
								<option value= "-1" >---请选择---</option>
								<option value= "0" >代销</option>
								<option value= "1" >结算经销</option>
								<option value= "2" >付款经销</option>
							</select>
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "contact_form" >联系人&nbsp;:</label>
				    		<input id= "contact_form" name= "contact_form" class= "form-control" type= "text" placeholder= "必填，最大长度10" />
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "phone_form" >联系电话&nbsp;:</label>
				    		<input id= "phone_form" name= "phone_form" class= "form-control" type= "text" placeholder= "必填，手机号或固定电话" />
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "address_form" >联系地址&nbsp;:</label>
				    		<input id= "address_form" name= "address_form" class= "form-control" type= "text" placeholder= "必填，最大长度100" />
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "company_form" >开票公司&nbsp;:</label>
				    		<input id= "company_form" name= "company_form" class= "form-control" type= "text" placeholder= "必填，最大长度100" />
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "invoice_type_form" >发票类型&nbsp;:</label>
				    		<select id= "invoice_type_form" name= "invoice_type_form" class= "form-control" >
								<option value= "-1" >---请选择---</option>
								<option value= "0" >手写发票</option>
								<option value= "1" >机打发票</option>
								<option value= "2" >普通发票</option>
								<option value= "3" >增值税发票</option>
								<option value= "4" >其它发票</option>
							</select>
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "invoice_info_form" >发票信息&nbsp;:</label>
				    		<textarea id= "invoice_info_form" name= "invoice_info_form" class= "form-control" style= "height: 150px;" cols= "30" placeholder= "必填，最大长度200" ></textarea>
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "remark_form">备注&nbsp;:</label>
				    		<input id= "remark_form" name= "remark_form" class= "form-control" type= "text" placeholder= "最大长度100" />
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "validity_form" >是否有效&nbsp;:</label>
				    		<input id= "validity_form" name= "validity_form" type= "checkbox" class= "ace ace-switch ace-switch-5" />
							<span class= "lbl" ></span>
				    	</div>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="saveStore();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>保存
		     			</button>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
