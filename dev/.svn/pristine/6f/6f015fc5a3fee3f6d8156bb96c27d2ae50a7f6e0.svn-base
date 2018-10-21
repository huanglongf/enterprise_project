<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>css/table.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>work_order/wo_group/css/group.css" rel= "stylesheet" />
		
	    <script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
	    <script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>work_order/wo_group/js/group.js" ></script>
	</head>
	<body>
		<div class= "page-header no-margin-bottom" >
			<h1>组别编辑</h1>
		</div>
		<div class= "container row-height" style= "background: #F0F8FF; height: 400px;" >
			<input type="hidden" id="id" name="id" value="${group.id}" />
			<div class= "form-group row" >
				<div class= "col-md-2" style= "text-align: right;" >
		            <label class= "no-padding-right blue" style= "white-space: nowrap;" for= "group_code" >组别编码&nbsp;:</label>
		        </div>
		        <div class= "col-md-4" >
		        	<input id="group_code" name="group_code" class= "form-control" type="text" value="${group.group_code }" ${not empty group.id? "disabled= 'disabled'": "" } />
		        </div>
		        <div class= "col-md-2" style= "text-align: right;" >
		            <label class= "no-padding-right blue" style= "white-space: nowrap;" for= "group_name" >组别名称&nbsp;:</label>
		        </div>
		        <div class= "col-md-4" >
		        	<input id="group_name" name="group_name" class= "form-control" type="text" value="${group.group_name }" ${not empty group.id? "disabled= 'disabled'": "" } />
		        </div>
			</div>
			<div class= "form-group row" >
				<div class= "col-md-2" style= "text-align: right;" >
		            <label class= "no-padding-right blue" style= "white-space: nowrap;" for= "status" >启用状态&nbsp;:</label>
		        </div>
		        <div class= "col-md-4" >
		        	<input id="status" name="status" type="checkbox" class="ace ace-switch ace-switch-5" ${group.status== true? "checked= 'checked'": "" } />
					<span class="lbl"></span>
		        </div>
				<div class= "col-md-2" style= "text-align: right;" >
		            <label class= "no-padding-right blue" style= "white-space: nowrap;" for= "autoAllocFlag" >自动分配工单&nbsp;:</label>
		        </div>
		        <div class= "col-md-4" >
		        	<input id="autoAllocFlag" name="autoAllocFlag" type="checkbox" class="ace ace-switch ace-switch-5" ${group.autoAllocFlag== true? "checked= 'checked'": "" } />
					<span class="lbl"></span>
		        </div>
			</div>
			<div class= "form-group row" >
				<div class= "col-md-2" style= "text-align: right;" >
		            <label class= "no-padding-right blue" style= "white-space: nowrap;" for= "superior" >上级组别&nbsp;:</label>
		        </div>
		        <div class= "col-md-4" >
		        	<select id= "superior" name= "superior" data-edit-select= "1" >
		        		<option selected= "selected" value="" >---请选择---</option>
	  					<c:forEach items= "${superior }" var= "superior" >
                        	<option value= "${superior.id }" ${superior.id == group.superior? "selected= 'selected'": "" } >${superior.group_name }</option>
                        </c:forEach>
		        	</select>
		        </div>
			</div>
			<div class= "form-group row" style= "height: 150px;" >
				<div class= "col-md-2" style= "text-align: right;" >
                    <label class= "no-padding-right blue" style= "white-space: nowrap;" for= "remark" >说明&nbsp;:</label>
                </div>
                <div class= "col-md-9" >
                    <textarea id= "remark" name= "remark" class= "form-control" style= "height: 150px;" cols= "30" placeholder= "最大长度200" >${group.remark }</textarea>
                </div>
			</div>
			<div class= "form-group row" >
				<div class= "clearfix form-actions" style= "height: 60px;">
					<div class="col-md-offset-5">
						<button id="save" name="save" class="btn btn-info" type="button" onclick= "save();" >
							<i class="icon-ok bigger-110"></i>
							提交
						</button>
						<button class="btn" type="reset" onclick="openDiv('${root}control/groupController/list.do?queryType=init');">
			 				<i class="icon-undo bigger-110"></i>
							返回
						</button>
					</div>
				</div>
			</div>
		</div>
		<div ${empty group.id? "style= 'display: none;'": "" } >
			<ul class= "nav nav-pills" role="tablist" style= "height: 39px;" >
			    <li role= "presentation" class= "active" ><a href= "#work_power" data-target= "#work_power" data-toggle= "pill" >工作权限</a></li>
			    <li role= "presentation" ><a href= "#store_power" data-target= "#store_power" data-toggle= "pill" >店铺权限</a></li>
			</ul>
			<div class= "tab-content no-padding" style= "height: 275px;" >
			    <div id= "work_power" class= "tab-pane fade in active" >
			        <jsp:include page= "/work_order/wo_group/work_power.jsp" flush= "true" />
			    </div>
			    <div id= "store_power" class= "tab-pane fade" >
			    	<jsp:include page= "/work_order/wo_group/store_power.jsp" flush= "true" />
			    </div>
			</div>
		</div>
		<jsp:include page= "/work_order/wo_group/storePower_form.jsp" flush= "true" />
		<jsp:include page= "/work_order/wo_group/workPower_form.jsp" flush= "true" />
	</body>
</html>
