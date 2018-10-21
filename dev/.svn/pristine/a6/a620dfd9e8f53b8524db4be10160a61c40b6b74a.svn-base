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
			            <button class="btn btn-xs btn-white bigger" onclick = "pageJump('load','2');" >
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
	   </tr> 
	</table>
	
</form>
			    	</div>
			    </div>
			</div>
		<div class="page-header">
		</div>
		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="pageJump('load','2');">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			<button class= "btn btn-xs btn-primary" onclick= "exportData();" >
				<i class= "icon-edit" ></i>导出
			</button>
			<button class="btn btn-xs btn-yellow" onclick="back_to_import()">
				<i class="icon-download"></i>返回
			</button>
		</div>
<input type="hidden"  value="2" id="page_type">
<jsp:include page= "/lmis/jk_info/error_page.jsp" flush= "true" />
</div>
</body>
</html>
