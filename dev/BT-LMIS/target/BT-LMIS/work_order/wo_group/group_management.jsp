<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>work_order/wo_group/css/group.css" rel= "stylesheet" />
		
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>work_order/wo_group/js/group.js" ></script>
	</head>
	<body>
		<div class= "page-header no-margin-bottom" align= "left" >
			<h1>组别管理</h1>
		</div>
		<div class= "widget-box collapsed" >
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
		            <button class="btn btn-xs btn-white bigger" onclick = "find();" >
		                <i class="icon-search"></i>查询
		            </button>
		        </div>
		    </div>
		    <div class= "widget-body" >
		    	<div class= "widget-main" style= "background: #F0F8FF;" >
		    		<form class= "container-fluid row-height" >
		    			<div class= "row" >
		    				<div class= "col-md-1" style= "text-align: right;" >
		                        <label class= "no-padding-right blue" for= "group_code" style= "white-space: nowrap;" >
                         			组别代码&nbsp;: 
		                        </label>
		                    </div>
		                    <div class= "col-md-2" >
		                        <input id= "group_code" name= "group_code" type= "text" class= "form-control" />
		                    </div>
		                    <div class= "col-md-1" style= "text-align: right;" >
		                        <label class= "no-padding-right blue" for= "group_name" style= "white-space: nowrap;" >
                         			组别名称&nbsp;: 
		                        </label>
		                    </div>
		                    <div class= "col-md-2" >
		                        <input id= "group_name" name= "group_name" type= "text" class= "form-control" />
		                    </div>
		                    <div class= "col-md-1" style= "text-align: right;" >
		                        <label class= "no-padding-right blue" for= "status" style= "white-space: nowrap;" >
                         			启用状态&nbsp;: 
		                        </label>
		                    </div>
		                    <div class= "col-md-2" >
		                        <select id= "status" name= "status" data-edit-select= "1" >
				  					<option selected= "selected" value="" >---请选择---</option>
				  					<option value= "true" >启用</option>
				  					<option value= "false" >停用</option>
				  				</select>
		                    </div>
		    			</div>
		    			<div class= "row" >
		    				<div class= "col-md-1" style= "text-align: right;" >
		                        <label class= "no-padding-right blue" for= "superior" style= "white-space: nowrap;" >
                         			上级组别&nbsp;:
		                        </label>
		                    </div>
		                    <div class= "col-md-2" >
		                        <select id= "superior" name= "superior" data-edit-select= "1" >
				  					<option selected= "selected" value="" >---请选择---</option>
				  					<c:forEach items= "${superior }" var= "superior" >
		                                <option value= "${superior.id }" >${superior.group_name }</option>
		                            </c:forEach>
				  				</select>
		                    </div>
		                    <div class= "col-md-1" style= "text-align: right;" >
		                        <label class= "no-padding-right blue" for= "autoAllocFlag" style= "white-space: nowrap;" >
                         			自动分配工单&nbsp;: 
		                        </label>
		                    </div>
		                    <div class= "col-md-2" >
		                        <select id= "autoAllocFlag" name= "autoAllocFlag" data-edit-select= "1" >
				  					<option selected= "selected" value="" >---请选择---</option>
				  					<option value= "true" >是</option>
				  					<option value= "false" >否</option>
				  				</select>
		                    </div>
		    			</div>
		    		</form>
		    	</div>
		    </div>
		</div>
		<p class= "alert alert-info no-margin-bottom" >
			<button class= "btn btn-white" onclick= "find();" ><i class= "icon-refresh" ></i>刷新</button>
			<button class= "btn btn-white" onclick="openDiv('${root}/control/groupController/toForm.do');"><i class="icon-plus"></i>新增</button>
			<button class= "btn btn-sm btn-inverse" onclick="del();"><i class="icon-trash"></i>删除</button>
		</p>
		<jsp:include page= "/work_order/wo_group/group_list.jsp" flush= "true" />
	</body>
</html>
