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
		<link type= "text/css" href= "<%=basePath %>daterangepicker/font-awesome.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/moment.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
        <%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
        <style type="text/css">
         label{
          margin-right: 15px;
          font-size: 14px;
         }
		.select {
		    padding: 3px 4px;
		    height: 30px;
		    width: 160px;
		   text-align: enter;
		}
		.table_head_line td {
		font-weight: bold;
		font-size: 16px
		}
        </style>
	</head>
<script>
	$(function() {
		$("#table_content tbody tr").dblclick(
			function() {
			   penDiv('/BT-LMIS/controller/groupMessageController/listGroup.do?id='+ $(this).children(":first").children(":first").val()+ "&group_name="+ $(this).children().eq(1).text());
		});
	});

	$(document).ready(function() {
		var group_name=$("#group_name").val();
	});
</script>
<body>
	<div class="page-header" align="left">
		<h1>店铺组别信息查询</h1>
	</div>
		<div class="page-header" style="margin-left : 20px;">
		<input type="hidden" name="groupCodeJsp" id="groupCodeJsp" value="${groupCode}">
		<input type="hidden" name="groupNamejsp" id="groupNamejsp" value="${groupName}">
		<input type="hidden" name="statusJsp" id="statusJsp" value="${status}">
		<input type="hidden" name="superiorJsp" id="superiorJsp" value="${superior}">
		<input type="hidden" name="if_allotJsp" id="if_allotJsp" value="${if_allot}">
		<form id= "main_search">
			<table>
		  		<tr>
		  			<td width="10%" align="right"><label>组别编码:</label></td>
		  			<td width="20%" align="left">
		  			    <select id="group_code" name="group_code" data-edit-select="1">
			  				<option value= "">---请选择---</option>
			  				 <c:forEach items= "${seniorQuery}" var= "street" >
			  					    <option value="${street.group_code}" <c:if test="${groupPar.group_code==street.group_code}">selected</c:if> >${street.group_code}</option>
							 </c:forEach>
						</select>
		  			</td>
		  			<td width="10%" align="right"><label>组别名称:</label></td>
		  			<td width="20%" align="left">
		  			    <select id="group_name" name="group_name" style="width: 50%;" data-edit-select="1">
			  					<option value= "">---请选择---</option>
			  					<c:forEach items= "${seniorQuery}" var= "street" >
			  					    <option value="${street.group_name}" <c:if test="${groupPar.group_name==street.group_name}">selected</c:if> >${street.group_name}</option>
								</c:forEach>
						</select>
		  			</td>
		  			
		  			<td width="10%" align="right"><label>组别启用状态:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="status"  name="status" style="width: 60%;" data-edit-select="1">
		  			       <option value="">---请选择---</option>
		  			       <option value="1" <c:if test="${groupPar.status=='1'}">selected='selected'</c:if>>启用</option>
		  			       <option value="0" <c:if test="${groupPar.status=='0'}">selected='selected'</c:if>>禁用</option>
		  			  </select>
		  			</td>
		  		</tr>
			</table>
		</form>
		</div>
		<div class="div_margin" style="margin-left : 20px;">
			<button class="btn btn-xs btn-pink" onclick="findGroup();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			<button class= "btn btn-xs btn-yellow" onclick= "openDiv('${root}control/shopGroupController/newlyGroupWord.do');" >
				<i class= "icon-hdd" ></i>新增
			</button>
			<button id="btn_export" class="btn btn-xs btn-inverse" onclick="deleteGroup();">
				<i class="icon-trash"></i>删除
			</button>
			<button class="btn btn-xs btn-success" onclick="updateStatus('0');">
				<i class="ace-icon fa fa-lock orange bigger-120" >
					禁用
				</i>
			</button>
			<button class="btn btn-xs btn-success" onclick="updateStatus('1');">
				<i class="ace-icon fa fa-unlock green bigger-120" >
					启用
				</i>
			</button>
		</div>
		
<div  class="title_div" id="sc_title">		
	<table class="table table-striped" style="table-layout: fixed;">
			   		<thead>
				  		<tr>
				  		<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
                        <td class="td_cs">序号</td> 
			  			<td class="td_cs">组别编码</td>
			  			<td class="td_cs">组别名称</td>
			  			<td class="td_cs">说明</td>
			  			<td class="td_cs">状态</td>
			  			<td class="td_cs">操作</td>
				  		</tr>  	
			  		</thead>
	
	</table>
</div>
<div class="tree_div"></div>
		
		
		
		
		
		
		
		
		
<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
		  		<tbody>
		  		<c:forEach items="${pageView.records}" var="pageView" varStatus="status">
		  		   <input type="hidden" id="${pageView.id}_group_code" value="${pageView.group_code}"/>
		  		   <input type="hidden" id="${pageView.id}_group_name" value="${pageView.group_name}"/>
		  		   <input type="hidden" id="${pageView.id}_superior" value="${pageView.superior}"/>
		  		   <input type="hidden" id="${pageView.id}_if_allot" value="${pageView.if_allot}"/>
		  		   <input type="hidden" id="${pageView.id}_instruction" value="${pageView.instruction}"/>
		  		   <input type="hidden" id="${pageView.id}_status" value="${pageView.status}"/>
			  		<tr id="tr_${pageView.id }" ondblclick="updateSgroup('${pageView.id }','${pageView.group_code}','${pageView.group_name}','${pageView.superior}','${pageView.if_allot}','${pageView.instruction}','${pageView.status}')"  onclick="upBgbyId('${pageView.id }')">
			  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${pageView.id}_${pageView.status}"></td>
			  			<td class="td_cs">${status.index + 1}</td>
			  			<td class="td_cs">${pageView.group_code}</td>
			  			<td class="td_cs">${pageView.group_name}</td>
						<c:if test="${fns:length(pageView.instruction)>30}">
							<td class="td_cs" title="${pageView.instruction}">${fns:substring(pageView.instruction,0,30)}...</td>
						</c:if>
						<c:if test="${fns:length(pageView.instruction)<=30}">
							<td class="td_cs" title="${pageView.instruction}">${pageView.instruction}</td>
						</c:if>
						<td class="td_cs">
			  				<c:if test= "${pageView.status == 'false' }">禁用</c:if>
				  			<c:if test= "${pageView.status == 'true' }">启用</c:if>
			  			</td>
			  			<td class="td_cs">
							<button class= "btn btn-xs btn-yellow" onclick= "openDiv('${root}control/groupMessageController/getWKEmployee.do?groupid=${pageView.id}');" >
								<i class= "icon-refresh" ></i>添加用户
							</button>
			  			</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right : 1%; margin-top : 20px;">${pageView.pageView}</div>
	</body>
	<script type="text/javascript">
	  	function findGroup() {
		  	var data=$("#main_search").serialize();
		  	openDivs('${root}control/groupMessageController/listGroup.do?',data); 
		}
		function pageJump() {
			  var data=$("#main_search").serialize();
		      openDiv('${root}control/groupMessageController/listGroup.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		} 
		function inverseCkb(items){
			$('[name='+items+']:checkbox').each(function(){
				this.checked=!this.checked;
			});
		}
		/**
	   	* 修改菜单状态
	   	*/
		function updateStatus(status) {
			if ($("input[name='ckb']:checked").length == 1) {
				var priv_ids = [];
				$("input[name='ckb']:checked").each(function() {
					id = $(this).val();
				});
				
				var message = '确定修改吗?';

				if (status == '0') {
					message = '确定禁用吗？';
				} else {
					message = '确定启用吗？';
				}
				
				if (!confirm(message)) {
					return;
				}
				
				var url = root
						+ "/control/groupMessageController/updateStatus.do?id="
						+ id + "&status=" + status;
				$.ajax({
					type : "POST",
					url : url,
					dataType : "text",
					data : "",
					success : function(data) {
						if (data == 'true') {
							pageJump();
						} else if (data == 'false') {
							alert("成功!");
						} else {
							alert("失败!");
						}
					}
				});
			} else {
				alert("请选择一行!");
			}
		}
		function upBg(id){
			$("*[id*=tr_]").css("background","#ffffff");
			$("#tr_"+id).css("background","#C6E2FF");
		}
		
		function deleteGroup() {
			if ($("input[name='ckb']:checked").length >= 1) {
				var priv_ids = [];
				$("input[name='ckb']:checked").each(function() {
					priv_ids.push($.trim($(this).val()));
				});
				if (!confirm("确定删除吗?")) {
					return;
				}
				$.ajax({
							type : "POST",
							url : root
									+ "/control/groupMessageController/deleteGroup.do?",
							dataType : "text",
							data : "privIds=" + priv_ids,
							success : function(data) {
								if (data == 'true') {
									pageJump();
								} else if (data == 'false') {
									alert("已启用状态不能删除!");
								} else {
									alert("包含已启用状态不能删除!");
								}
							}
						});
			} else {
				alert("请选择一行!");
			}
		}
        //修改主表信息
		function updateSgroup(id,group_code,group_name,superior,if_allot,instruction,status){
			var data = "&id="+id+"&group_code="+group_code+"&group_name="+group_name+"&superior="+superior+"&if_allot="+if_allot+"&instruction="+instruction+"&status="+status;
			openDivs('${root}control/shopGroupController/updateSgroup.do?',data);
		}
		function upBgbyId(id){
			$("*[id*=tr_]").css("background","#ffffff");
			$("#tr_"+id).css("background","#C6E2FF");
		}	
	</script>
</html>

