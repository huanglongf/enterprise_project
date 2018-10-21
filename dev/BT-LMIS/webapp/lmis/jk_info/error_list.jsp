<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link type= "text/css" href= "<%=basePath %>My97DatePicker/skin/WdatePicker.css" rel= "stylesheet" />
		<link type= "text/css" href="<%=basePath %>css/common.css" rel="stylesheet" />
		<link type= "text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>My97DatePicker/WdatePicker.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/validateForm.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>lmis/jk_info/error.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/moment.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/daterangepicker.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>My97DatePicker/WdatePicker.js" ></script>
	 <style type="text/css">
	.lmis-panel {
		overflow-x: hidden;
		overflow-y: auto;
		}
	</style>

	
	</head>
	<body>
	
			<div class= "page-header no-margin-bottom" align= "left" >
			<h1>运费异常转换处理</h1>
		</div>
		
		<div class= "lmis-panel" >
			<div class= "widget-box collapsed no-margin" >
				<div class= "widget-header header-color-blue" >
			        <h4>查询列表</h4>
			        <div class= "widget-toolbar" >
			            <a href= "#" data-action= "reload" onclick= "refreshCondition();" >
			                <i class= "icon-refresh bigger-125" ></i>
			            </a>
			            <a href= "#" data-action= "collapse" >
			                <i class= "icon-chevron-down bigger-125" ></i>
			            </a>
			        </div>
			        <div class="widget-toolbar no-border">
			            <button class="btn btn-xs btn-white bigger" onclick = "pageJump('load','1');" >
			                <i class="icon-search"></i>查询
			            </button>
			        </div>

			    </div>
			    <div class= "widget-body" >
			    	<div class= "widget-main" style= "background: #F0F8FF;" >
	
	<form id="search_form">
	<table>
	   <tr>
	      <td>
	       <div style="width:300px;">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right">运&nbsp;单&nbsp;号: </label>
						<span class="input-icon input-icon-right">
							<input type="text"  name="express_number" />
							<i class="icon-leaf green"></i>
						</span>
					</div>
               </div>
	      </td>
	      
	      <td>
				 <div style="width:300px;">
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">店铺名称:</label>
								<span class="input-icon input-icon-right">
									<input type="text"   name="store_name" />
									<i class="icon-leaf green"></i>
								</span>
							</div>
				</div>	      
	      </td>
	      
	      <td>
				 <div style="width:300px;">
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">仓库名称:</label>
								<span class="input-icon input-icon-right">
									<input type="text"  name="warehouse_name" />
									<i class="icon-leaf green"></i>
								</span>
							</div>
				</div>	      
	      </td>	      
	   </tr>
	   
	   
	   <tr>
	      <td>
	       <div style="width:300px;">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right">目的地省: </label>
						<span class="input-icon input-icon-right">
							<input type="text"   name="end_province" />
							<i class="icon-leaf green"></i>
						</span>
					</div>
               </div>
	      </td>
	      
	      <td>
				 <div style="width:300px;">
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">目的地市:</label>
								<span class="input-icon input-icon-right">
									<input type="text"   name="end_city" />
									<i class="icon-leaf green"></i>
								</span>
							</div>
				</div>	      
	      </td>
	      
	      <td>
				 <div style="width:300px;">
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">目的地区:</label>
								<span class="input-icon input-icon-right">
									<input type="text"   name="end_state" />
									<i class="icon-leaf green"></i>
								</span>
							</div>
				</div>	      
	      </td>	      
	   </tr>	  
	   
	   
	   <tr>
	      <td>
      				 <div style="width:300px;">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right">错误原因:</label>
							<span class="input-icon input-icon-right">
								<input type="text"   name="error_remark" />
								<i class="icon-leaf red"></i>
							</span>
						</div>
				</div>	 
	      </td>
	      
	      <td colspan="4"  >
	          <div style="width:300px;float: left;">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right">出库时间:</label>
							<span class="input-icon input-icon-left" >
								<input type="text"   name="s_time"  onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" value=""/>
								<i class="icon-leaf green"><span style="font-size: 10px;"><span></span></span></i>
							</span>
				        </div>      
			 </div>
	          <div style="width:300px;float: left;">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right">出库时间:</label>
							<span class="input-icon input-icon-right" >
							 <input type="text"   name="e_time"  onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" value=""/>
								<i class="icon-leaf green"><span style="font-size: 10px;"><span></span></span></i>
							</span>
				        </div>      
			 </div>			 
	      </td>	      
	   </tr> 
	</table>
	
</form>




								                    			                    		                    		                    	                    
			    	</div>
			    </div>
			</div>
		<div class="page-header">
		</div>
		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="pageJump('load','1');">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			<button class="btn btn-xs btn-pink" onclick="anysis_data();">
				<i class="icon-search icon-on-right bigger-110"></i>根据百度api解析地址
			</button>			
			<button class= "btn btn-xs btn-primary" onclick= "export_data();" >
				<i class= "icon-edit" ></i>导出
			</button>		
			<button class= "btn btn-xs btn-primary" onclick= "to_import_data();" >
				<i class= "icon-edit" ></i>添加数据
			</button>		
			 <button class="btn btn-xs btn-primary" onclick = "add_error_address();" >
			     <i class="icon-edit"></i>添加解析记录
			 </button>
			<button class="btn btn-xs btn-pink" onclick="refresh_tranaction();">
				<i class="icon-search icon-on-right bigger-110"></i>重新转换
			</button>			 
		</div>
<input type="hidden"  value="1" id="page_type">
<jsp:include page= "/lmis/jk_info/order_page.jsp" flush= "true" />
</div>


<!-- 店铺编辑页面弹窗 -->
		<div id= "address_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >添加地址解析记录</h4>
					</div>
					<div class= "modal-body" >
						<input id= "id_param" name= "id_param" type= "hidden" />
               
						<div class= "form-group" >
				    		<label class= "blue" for= "client_form" >选择解析层级&nbsp;:<span id="province_lable"  style="color: red;"></span></label>
				    		<select data-edit-select= "1"  id="add_level"  onchange="change_level()">
				    		   <option value="">---请选择解析的层级---</option>
				    		   <option value="1">省</option>
				    		   <option value="2">市</option>
				    		   <option value="3">区</option>
				    		</select>
				    	</div>
	
						<div class= "form-group" >
				    		<label class= "blue" for= "standard_time_form" >异常地址&nbsp;:<span id="city_lable" style="color: red;"></span></label>
	                        <input id= "error_address" name= "standard_time_form" class= "form-control" type= "text"  placeholder="请填写异常地址"/> 
				    	</div>
						<div class= "form-group"  id="real_province"  style="display: none">
				    		<label class= "blue" for= "standard_time_form" >实际目的地省&nbsp;:<span id="city_lable" style="color: red;"></span></label>
                             <select id="provice_code" name="provice_code"  data-edit-select="1" onchange='shiftArea(1,"provice_code","city_code","state_code","")'>
								<option value= ''>---请选择---</option>
								<c:forEach items="${list_province}" var = "area" >
								    <c:if test='${queryParam.provice_code eq area.area_code }'> 
									<option selected='selected' value="${area.area_code}">${area.area_name}</option>
									 </c:if>
									 <c:if test='${queryParam.provice_code ne area.area_code }'> 
									<option  value="${area.area_code}">${area.area_name}</option>
									 </c:if>
								</c:forEach>
							</select>
				    	</div>
				    	
						<div class= "form-group"   id="real_city" style="display: none">
				    	<label class= "blue" for= "plan_time_form" >实际目的地市&nbsp;:<span id="state_lable" style="color: red;"></span></label>
			                  <select id="city_code" name="city_code"   data-edit-select="1"  onchange='shiftArea(2,"provice_code","city_code","state_code","")'>
									<option value=''>---请选择---</option>
									<c:forEach items="${list_city}" var = "cityVar" >
									    <c:if test='${queryParam.city_code eq cityVar.area_code}'>
										<option selected='selected'  value="${cityVar.area_code}">${cityVar.area_name}</option>
										</c:if>
										<c:if test='${queryParam.city_code ne cityVar.area_code}'>
										<option  value="${cityVar.area_code}">${cityVar.area_name}</option>
										</c:if>
									</c:forEach>
								</select>
				    	</div>
				    	
		             <div class= "form-group"   id="real_state" style="display: none">
				    		<label class= "blue" for= "client_form" >实际目的地区&nbsp;:<span id="detail_lable" style="color: red;"></span></label>
				                     <select id="state_code" name="state_code"   data-edit-select="1"  >
									<option value=''>---请选择---</option>
									<c:forEach items="${list_state}" var = "stateVar" >
									      <c:if test='${stateVar.area_code eq queryParam.state_code }'> 
											<option selected='selected' value="${stateVar.area_code}">${stateVar.area_name}</option>
										  </c:if>
										  <c:if test='${stateVar.area_code ne queryParam.state_code }'> 
											<option  value="${stateVar.area_code}">${stateVar.area_name}</option>
										  </c:if>	
										</c:forEach>
									</select>
				    	</div>
				    					    	
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="save_address();" >
		     				<i class="icon-save" aria-hidden= "true" ></i><span id="btn_type_text">保存</span>
		     			</button>
					</div>
				</div>
			</div>
		</div>

</body>
</html>
