
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>预警类型信息查询及新建</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type='text/javascript'>
		var params='&warningtype_code=${queryParam.warningtype_code}&warning_category=${queryParam.warning_category}&initial_level=${queryParam.initial_level}';
	function pageJump() {
      openDiv('${root}/controller/express_alarm/query.do?startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val()+params);
	}
	
	/**
	 * 查询
	 */
	function find() {
	   var warningtype_code=$('#warningtype_code').val();
	   if(warningtype_code==-1)warningtype_code='';
	   var warning_category=$('#warning_category').val();
	   if(warning_category==-1)warning_category='';
	   var  initial_level=$('#initial_level').val();
	   if(initial_level==-1)initial_level='';
	openDiv('${root}/controller/express_alarm/query.do?warningtype_code=' + warningtype_code + '&warning_category='+warning_category + '&initial_level='+initial_level);
	}

	function del(){
		if($("input[name='ckb']:checked").length>=1){
			if($("input[name='ckb']:checked").length>1){
				alert("只能选择一行!");
		  	}else{
		  		 var result=  	confirm('是否删除！'); 
					if(result){
		  		$.post('${root}//controller/express_alarm/del.do?id='+$("input[name='ckb']:checked").val(),
		        		function(data){
		  			/*  if(typeof(reValue) != "undefined"&&data.documentURI.indexOf('BT-LMIS')>0){
						window.location='/BT-LMIS';
						return;
					};  */
		        	            if(data.code==1){
		        	            	alert("操作成功！");
		        	            }else if(data.code==0){
		        	            	alert("操作失败！");
		        	            	alert(data.msg);
		        	             }
		        	            openDiv('${root}/controller/express_alarm/query.do');
  
		                        }
		  		      );	
		  		
					}
		  	}
		}else{
			alert("请选择一行!");
		}
	}
	function toUpdate(uuid){
		
		openDiv('${root}/controller/express_alarm/toUpdate.do?id='+uuid);

	}
	$(document).ready(function() {
		var  w_code="${queryParam.warningtype_code}";
		var  w_cate="${queryParam.warning_category}";
		var  init_levle="${queryParam.initial_level}";
	    $("#warningtype_code").val(w_code);
		$("#warning_category").val(w_cate);
		$("#initial_level").val(init_levle);
	
	});
	
	var Select = {
			del : function(obj,e){
			if((e.keyCode||e.which||e.charCode) == 8){
			var opt = obj.options[0];
			opt.text = opt.value = opt.value.substring(0, opt.value.length>0?opt.value.length-1:0);
			}
			},
			write : function(obj,e){
			if((e.keyCode||e.which||e.charCode) == 8)return ;
			var opt = obj.options[0];
			opt.selected = "selected";
			opt.text = opt.value += String.fromCharCode(e.charCode||e.which||e.keyCode);
			}
			}
	
    </script>
	</head>
	
	<body>
		<div class="page-header"><h1 style='margin-left:20px'>预警类型信息查询及新建</h1></div>	
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<table>
		  	 <tr>
		  	 <td align="left" width="10%"><label class="blue" >预警类型&nbsp;:</label></td> 
					<td width="20%"><select id="warningtype_code" name=""  class='select' onkeypress="Select.write(this,event)" onkeydown="Select.del(this,event)">
						<option value= -1>---请选择---</option>
						<c:forEach items="${warningtype}" var = "warningtype" >
							<option  value="${warningtype.warningtype_code}">${warningtype.warningtype_name}</option>
						</c:forEach>	
					</select></td>
					<td align="left" width="10%"><label class="blue" >初始级别&nbsp;:</label></td> 
					<td width="20%"><select id="initial_level" name="initial_level"  class='select'>
						<option value= -1>---请选择---</option>
						<option value= 1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1级</option>
						<option value= 2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2级</option>
						<option value= 3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3级</option>
						<option value= 4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4级</option>
						<option value= 5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5级</option>
						<option value= 6>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6级</option>
						<option value= 7>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7级</option>
						<option value= 8>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8级</option>
						<option value= 9>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;9级</option>
						<option value= 10>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;10级</option>
					</select></td>
		  		</tr>
		  		
		  		
		  		<tr>
		  		<td align="left" width="10%"><label class="blue" >预警类别&nbsp;:</label></td> 
					<td width="20%"><select id="warning_category" name="warning_category"  class='select'>
						<option value= -1>---请选择---</option>
						<option value='0'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;事件</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;超时</option>
					</select></td>
				</tr>
			</table>
		</div>
		<div style="margin-top: 10px;margin-left: 20px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;&nbsp;
			<button class="btn btn-xs btn-yellow" onclick="openDiv('${root}/controller/express_alarm/toadd.do');">
				<i class="icon-hdd"></i>新增
			</button>
<!-- 			&nbsp; -->
<!-- 			<button class="btn btn-xs btn-primary" onclick="up();"> -->
<!-- 				<i class="icon-edit"></i>修改 -->
<!-- 			</button> -->
			&nbsp;
				<!-- <button class="btn btn-xs btn-primary" onclick="toUpdate();">
				<i class="icon-edit"></i>修改
			</button>&nbsp; -->
			<button class="btn btn-xs btn-inverse" onclick="del();">
				<i class="icon-trash"></i>删除
			</button
			>&nbsp;
		</div>
		  <div class= "title_div" id= "sc_title">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   		<thead>
			  		<tr class= "table_head_line"  >
			  			<td class='td_cs'><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class='td_cs'>序号</td>
			  			<td class='td_cs'>预警类型代码</td>
			  			<td class='td_cs'>预警类型</td>
			  			<td class='td_cs'>预警类别</td>
			  			<td class='td_cs'>初始级别</td>
			  		</tr>  	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center">
			  			<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr>
			  			<td class= "td_cs" nowrap="nowrap"><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td class= "td_cs" nowrap="nowrap">${status.count}</td>
			  			<td class= "td_cs" nowrap="nowrap"><a href='javascript:toUpdate("${power.id}")'>${power.warningtype_code }</a></td>
			  			<td class= "td_cs" nowrap="nowrap"><a href='javascript:toUpdate("${power.id}")'>${power.warningtype_name }</a></td>
			  			<td class= "td_cs" nowrap="nowrap">${power.warning_category_name }</td>
			  			<td class= "td_cs" nowrap="nowrap"><c:if test="${power.initial_level =='0'}">---</c:if> 
			  			    <c:if test="${power.initial_level =='1'}">1 级</c:if>
			  			    <c:if test="${power.initial_level =='2'}">2 级</c:if>
			  			    <c:if test="${power.initial_level =='3'}">3 级</c:if>
			  			    <c:if test="${power.initial_level =='4'}">4 级</c:if>
			  			    <c:if test="${power.initial_level =='5'}">5 级</c:if>
			  			    <c:if test="${power.initial_level =='6'}">6 级</c:if>
			  			    <c:if test="${power.initial_level =='7'}">7 级</c:if>
			  			    <c:if test="${power.initial_level =='8'}">8 级</c:if>
			  			    <c:if test="${power.initial_level =='9'}">9 级</c:if>
			  			    <c:if test="${power.initial_level =='10'}">10级</c:if>
			  			</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
      	<div style="margin-right: 30px;margin-top:20px;">${pageView.pageView}</div>		
	</body>
</html>
<style>

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
.table {
    width: 100%;
    margin-bottom: 0px;
}
.td_cs{
    text-align: center;
} 

</style>
