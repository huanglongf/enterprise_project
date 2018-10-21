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
		<script type= "text/javascript" src= "<%=basePath %>lmis/transport_vendor/js/transport_vendor.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>lmis/transport_vendor/js/product_type.js" ></script>
	</head>
	<body>
		<div class= "page-header" align= "left" >
			<h1>物流商编辑</h1>
		</div>
		<input id= "transportVendor_id" type= "hidden" value= "${transportVendor.id }" />
		<table style= "width: 100%;" class= "form_table" >
			<tr>
				<td class= "right" nowrap= "nowrap" width= "25%" >
					<label class= "no-padding-right blue" for= "transport_code" >
						物流商编码&nbsp;: 
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input ${not empty transportVendor.id? "readonly= 'readonly'": "" } id= "transport_code" name= "transport_code" type= "text" placeholder= "必填，最大长度20，字母数字组合" value= "${transportVendor.transport_code }" />
					<span></span>
				</td>
				<td class= "right" nowrap= "nowrap" width= "15%" >
					<label class="no-padding-right blue" for="transport_name">
						物流商名称&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "transport_name" name= "transport_name" type= "text" placeholder= "必填，最大长度25" value= "${transportVendor.transport_name }" />
				</td>
			</tr>
			<tr>
				<td class= "right" nowrap= "nowrap" width= "25%" >
					<label class= "no-padding-right blue" for= "contact" >
						联系人&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "contact" name= "contact" type= "text" placeholder= "必填，最大长度10" value= "${transportVendor.contact }" />
				</td>
				<td class= "right" nowrap= "nowrap" width= "15%" >
					<label class="no-padding-right blue" for="phone">
						联系电话&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "phone" name= "phone" type= "text" placeholder= "必填，手机号或固定电话" value= "${transportVendor.phone }" />
				</td>
			</tr>
			<tr>
				<td class= "right" nowrap= "nowrap" width= "25%" >
					<label class= "no-padding-right blue" for= "transport_type" >
						物流商类型&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<select id= "transport_type" name= "transport_type" style="width: 100%;" >
						<option value= "-1" >---请选择---</option>
						<option value= "0" ${transportVendor.transport_type == "0"? "selected= 'selected'": "" } >物流</option>
						<option value= "1" ${transportVendor.transport_type == "1"? "selected= 'selected'": "" } >快递</option>
					</select>
				</td>
				<td class= "right" nowrap= "nowrap" width= "15%" >
					<label class="no-padding-right blue" for="validity">
						是否有效&nbsp;:
					</label>
				</td>
				<td class= "left" nowrap= "nowrap" width= "15%" >
					<input id= "validity" name= "validity" type= "checkbox" class= "ace ace-switch ace-switch-5" ${transportVendor.validity == "true"? "checked= 'checked'": "" } />
					<span class= "lbl" ></span>
				</td>
			</tr>
			<tr>
				<td width= "25%" />
				<td class= "left" width= "45%" colspan= "3">
					<div style= "width: 100%" class= "form-actions no-margin-bottom no-padding-bottom no-border-bottom" align= "center" >
						<button class= "btn btn-info" type= "button" onclick= "saveTransportVendor();" >
							<i class= "icon-ok bigger-110" ></i>提交
						</button>
						&nbsp;&nbsp;&nbsp;
						<button class= "btn" type= "reset" onclick= "openDiv('${root }/control/transportVendorController/query.do');" >
							<i class= "icon-undo bigger-110" ></i>返回
						</button>
					</div>		
				</td>
				<td class="right" nowrap= "nowrap" width= "30%" />
			</tr>
		</table>
		<div ${empty transportVendor.id? "style= 'display: none;'": "" } >
			<ul class= "nav nav-pills" role="tablist" style= "height: 39px;" >
				<li role= "presentation" class= "active" ><a href= "#product_type" data-target= "#product_type" data-toggle= "pill" >产品类型</a></li>
			</ul>
			<div class= "tab-content" >
				<!-- 产品类型 -->
			  	<div id= "product_type" class= "tab-pane fade in active" >
			 		<jsp:include page="/lmis/transport_vendor/product_type_list.jsp" flush= "true" />
	           	</div>
			</div>
		</div>
		<!-- 店铺编辑页面弹窗 -->
		<div id= "product_type_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "product_type_formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "product_type_formLabel" class= "modal-title" ></h4>
					</div>
					<div class= "modal-body" >
						<input id= "product_type_id" name= "product_type_id" type= "hidden" />
						<div class= "form-group" >
				    		<label class= "blue" for= "transport_vendor_form" >物流商&nbsp;:</label>
				    		<input id= "transport_vendor_form" name= "transport_vendor_form" class= "form-control" type= "text" value= "${transportVendor.transport_name }" readonly= "readonly" />
				    	</div>
						<div class= "form-group" >
				    		<label class= "blue" for= "product_type_code_form" >产品类型编码&nbsp;:</label>
				    		<input id= "product_type_code_form" name= "product_type_code_form" class= "form-control" type= "text" placeholder= "必填，最大长度25" />
				    	</div>
						<div class= "form-group" >
				    		<label class= "blue" for= "product_type_name_form" >产品类型名称&nbsp;:</label>
				    		<input id= "product_type_name_form" name= "product_type_name_form" class= "form-control" type= "text" placeholder= "必填，最大长度25" />
				    	</div>
				    	<div class= "form-group" >
				    		<label class= "blue" for= "status_form" >是否启用&nbsp;:</label>
				    		<input id= "status_form" name= "status_form" type= "checkbox" class= "ace ace-switch ace-switch-5" />
							<span class= "lbl" ></span>
				    	</div>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick= "saveProductType();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>保存
		     			</button>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
