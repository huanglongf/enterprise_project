<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
<%@ include file="/templet/common.jsp"%>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<script type= "text/javascript" src= "<%=basePath %>work_order/wo_manhours/js/workorder_rule.js"></script>
	</head>
<script>
	$(function() {
	});
</script>
<body>
		<div>
		<input type="hidden" name="groupCodeJsp" id="groupCodeJsp" value="${groupCode}">
		<input type="hidden" name="groupNamejsp" id="groupNamejsp" value="${groupName}">
		<input type="hidden" name="statusJsp" id="statusJsp" value="${status}">
		<input type="hidden" name="superiorJsp" id="superiorJsp" value="${superior}">
		<input type="hidden" name="if_allotJsp" id="if_allotJsp" value="${if_allot}">
		</div>
		<div class="row">
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-12">
					<div class="widget-box">
						<div class="widget-header widget-header-small">
							<h5 class="widget-title lighter">查询栏</h5>
							<a class="pointer" title="初始化" onclick="refresh();"><i
								class="fa fa-refresh"></i></a>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<form id="wor_form" name="wor_form" class="container search_form">
									<div class="row form-group">
		  			<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue">预警类型:</label></div>
		  			<div class="col-md-3 no-padding">
		  			   <select id="warningtype_code" name="warningtype_code"   data-edit-select="1" onchange='warningtypeCode()'>
						<option value= ''>---请选择---</option>
						<c:forEach items="${warningtype}" var = "warningtype" >
							<option  value="${warningtype.warningtype_code}">${warningtype.warningtype_name}</option>
						</c:forEach>	
					 </select> 
		  			</div>
		  			<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue">预警级别:</label></div>
		  			<div class="col-md-3 no-padding">
		  			   <select id="initial_level" name="initial_level"  class='select' data-edit-select="1" >
						<option value= ''>---请选择---</option>
						<c:forEach items="${WarninglevelList}" var = "warningtype" >
							<option  value="${warningtype.levelup_level}">${warningtype.levelup_level}</option>
						</c:forEach>	
					</select>
		  			</div>
		  			<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue">启用状态:</label></div>
		  			<div class="col-md-3 no-padding">
		  			  <select id="ew_flag" name="ew_flag"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<option value=1>是</option>
						<option value=0>否</option>
					</select>
		  			</div>
		  		</div>
									<div class="senior-search">
										<div class="row form-group">
		  			<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue">工单类型:</label></div>
		  			<div class="col-md-3 no-padding">
		  			  <select id="wk_type_query" name="wk_type_query"  class='select'  data-edit-select="1" onchange='wkTypeQuery()' >
						<option value= ''>---请选择---</option>
						<c:forEach items="${wtType}" var = "wtType" >
							<option  value="${wtType.code}">${wtType.name}</option>
						</c:forEach>
						<!-- <option value='0'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;揽件超时</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户原因</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;配送超时</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;货差</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;货损</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送货上门变自提</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拒收</option>	 -->
					</select>
		  			</div>
		  		<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue">工单级别:</label></div>
		  			<div class="col-md-3 no-padding">
		  			  <select id="wk_level_query"  name="wk_level_query" style="width: 60%;" class='select' data-edit-select="1">
		  			     <option value=''>---请选择---</option>
		  			    <!--  <option value="">---请选择---</option> -->
		  			     <c:forEach items="${wk_level_list}" var = "wk_level" >
							<option  value="${wk_level.level_code}">${wk_level.level_name}</option>
						</c:forEach>
		  			  </select>
		  		</div>
		  		</div>
									</div>
									<div class="row text-center">
										<a class="senior-search-shift pointer" title="高级查询"><i
											class="fa fa-angle-double-down fa-2x" aria-hidden="true"></i></a>
									</div>
								</form>
							</div>
						</div>
					</div>
		<div
						style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;">
			<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="tablesearch('','');">
				<i class="ace-icon fa fa-search grey bigger-120">
					查询
				</i>
			</button>
			<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width" onclick= "addRecord();">
				<i class="ace-icon fa fa-plus-circle blue bigger-120">
					新增
				</i>
			</button>
			<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width" onclick= "del();">
				<i class="ace-icon fa fa-trash-o red bigger-120">
					删除
				</i>
			</button>
			<button class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width" onclick="upStatus('false');">
				<i class="ace-icon fa fa-lock orange bigger-120" >
					禁用
				</i>
			</button>
			<button class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width" onclick="upStatus('true');">
				<i class="ace-icon fa fa-unlock green bigger-120" >
					启用
				</i>
			</button>
		</div>
 	<div class="search-table">
			<jsp:include page= "/templet/table.jsp" flush= "true" />
	</div>
<%-- 	<div  class="title_div" id="sc_title">		
		<table class="table table-striped" style="table-layout: fixed;">
				   		<thead>
					  		<tr>
					  		<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
	                        <td class="td_cs">序号</td> 
				  			<td class="td_cs">预警类型</td>
				  			<td class="td_cs">预警级别</td>
				  			<td class="td_cs">工单类型</td>
				  			<td class="td_cs">工单级别</td>
				  			<td class="td_cs">启用状态</td>
					  		</tr>  	
				  		</thead>
		
		</table>
	</div>
	<div class="tree_div"></div>
	<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title','sc_content')">
	<table  id='table_content' class="table table-striped" style="table-layout: fixed;">
			  		<tbody>
			  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
				  		<tr>
				  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
				  			<td class="td_cs">${status.count}</td>
				  			<td class="td_cs" code='${power.ew_type_code}'>${power.ew_type_name }</td>
				  			<td class="td_cs" code='${power.ew_level}'>${power.ew_level }</td>	
				  			<td class="td_cs" code='${power.wk_type_code}'>${power.wk_type_name}</td>
				  			<td class="td_cs" code='${power.wk_level_code}'>${power.wk_level}
				  			</td>
				  			<td class= "td_cs" >
					  				<c:if test="${power.ew_flag==0}">禁用</c:if><c:if test="${power.ew_flag==1}">启用</c:if>
					  				|
					  				<c:if test= "${power.ew_flag ==1}" ><button class="btn btn-sm btn-white btn-warning btn-bold btn-round" onclick="upStatus('${power.id}','false');"><i class="ace-icon fa fa-lock orange bigger-120" ></i>禁用</button></c:if>
					  				<c:if test= "${power.ew_flag ==0}" ><button class="btn btn-sm btn-white btn-success btn-bold btn-round" onclick="upStatus('${power.id}','true');"><i class="ace-icon fa fa-unlock green bigger-120" ></i>启用</button></c:if>
					  			</td>
				  			
				  		</tr>
			  		</c:forEach>
			  		</tbody>
				</table>
				<div style="margin-right : 1%; margin-top : 20px;">${pageView.pageView}</div>
			</div> --%>
		
			<!-- 规则页面弹窗 -->
		<div id= "rule_new_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" ></h4>
					</div>
					<div class= "modal-body" >
		 <form id="addForm">			
			<table>
		  		<tr>
		  			<td width="10%" align="right"><label>预警类型:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="warningtype_code_new" name="warningtype_code"  class='select' data-edit-select="1" onchange='warningtypeCodeNew()'>
						<option value= ''>---请选择---</option>
						<c:forEach items="${warningtype}" var = "warningtype" >
							<option  value="${warningtype.warningtype_code}">${warningtype.warningtype_name}</option>
						</c:forEach>	
					</select>
		  			</td>
		  		<tr>	
		  			<td width="10%" align="right"><label>预警级别:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="initial_level_new" name="initial_level"  class='select' data-edit-select="1" >
						<option value= ''>---请选择---</option>
						<c:forEach items="${WarninglevelList}" var = "warningtype" >
							<option  value="${warningtype.levelup_level}">${warningtype.levelup_level}</option>
						</c:forEach>	
					</select>
		  			</td>
		  			</tr>
		  			<tr>
		  			<td width="10%" align="right"><label>启用状态:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="ew_flag_new" name="ew_flag"  class='select' data-edit-select="1" >
						<option value=-1>---请选择---</option>
						<option value=1>是</option>
						<option value=0>否</option>
					</select>
		  			</td>
		  		</tr>
		  		<tr align="center">
		  			<td width="10%" align="right"><label>工单类型:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="wk_type_new" name="wk_type_query"  class='select'  data-edit-select="1" onchange='wkTypeNew()'>
						<option value= ''>---请选择---</option>
						<c:forEach items="${wtType}" var = "wtType" >
							<option  value="${wtType.code}">${wtType.name}</option>
						</c:forEach>
						<!-- <option value='0'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;揽件超时</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户原因</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;配送超时</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;货差</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;货损</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送货上门变自提</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拒收</option>	 -->
					</select>
		  			</td></tr>
		  			<tr>
		  		<td width="5%" align="right" ><label>工单级别:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="wk_level_new"  name="wk_level_query" style="width: 60%;" class='select' data-edit-select="1">
		  			     <option value="">---请选择---</option>
		  			     <c:forEach items="${wk_level_list}" var = "wk_level" >
							<option  value="${wk_level.level_code}">${wk_level.level_name}</option>
						</c:forEach>
		  			  </select>
		  		</td>
		  		</tr>
			</table>
			</form>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="toAdd();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>保存
		     			</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 修改页面 -->
		<div id= "rule_update" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" ></h4>
					</div>
					<div class= "modal-body" >
		 <form id="updateForm">			
			<table>  
		  		<tr>
		  			<td width="10%" align="right"><label>预警类型:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="warningtype_code_update" name="warningtype_code"  class='select' data-edit-select="1" onchange='warningtypeCodeUpdate()'>
						<option value= ''>---请选择---</option>
						<c:forEach items="${warningtype}" var = "warningtype" >
							<option  value="${warningtype.warningtype_code}">${warningtype.warningtype_name}</option>
						</c:forEach>	
					</select>
					<input type='text' id='id_update' style='display:none'>
		  			</td>
		  		<tr>	
		  			<td width="10%" align="right"><label>预警级别:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="initial_level_update" name="initial_level"  class='select' data-edit-select="1" >
						<option value= ''>---请选择---</option>
						<c:forEach items="${WarninglevelList}" var = "warningtype" >
							<option  value="${warningtype.levelup_level}">${warningtype.levelup_level}</option>
						</c:forEach>	
					</select>
		  			</td>
		  			</tr>
		  			<tr>
		  			<td width="10%" align="right"><label>启用状态:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="ew_flag_update" name="ew_flag"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<option value=1>是</option>
						<option value=0>否</option>
					</select>
		  			</td>
		  		</tr>
		  		<tr align="center">
		  			<td width="10%" align="right"><label>工单类型:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="wk_type_update" name="wk_type_query"  class='select'  data-edit-select="1" onchange='wkTypeUpdate()'>
						<option value= ''>---请选择---</option>
						<c:forEach items="${wtType}" var = "wtType" >
							<option  value="${wtType.code}">${wtType.name}</option>
						</c:forEach>
						<!-- <option value='0'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;揽件超时</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户原因</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;配送超时</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;货差</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;货损</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送货上门变自提</option>
						<option value='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拒收</option>	 -->
					</select>
		  			</td></tr>
		  			<tr>
		  		<td width="5%" align="right" ><label>工单级别:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="wk_level_update"  name="wk_level_query" style="width: 60%;" class='select' data-edit-select="1">
		  			     <option value="">---请选择---</option>
		  			     <c:forEach items="${wk_level_list}" var = "wk_level" >
							<option  value="${wk_level.level_code}">${wk_level.level_name}</option>
						</c:forEach>
		  			  </select>
		  		</td>
		  		</tr>
			</table>
			</form>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="updateRule();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>保存
		     			</button>
					</div>
				</div>
			</div>
		</div>
		
		</div>
			</div>
		</div>
	</div>
	</body>
	<script type="text/javascript">

		
		function upStatus(flag){
			//alert(id+"---"+flag);
			if($("input[name='ckb']:checked").length>1){alert("只能选择一行操作！！");return;}
			if(flag=='true'){
				if(!confirm('确定启用吗？')){
					return;
				}
				
			var id=$("input[name='ckb']:checked").eq(0).val();
				 $.post(root+'/controller/workOder/updateEwflag.do?id='
			    			+id+'&ew_flag=1',
					function(data){
					 if(data.code==1){
							pageJump();	
						}else{
							alert('操作失败！！');
						}
					}); 
		
			}else{

				if(!confirm('确定禁用吗？')){
					return;
				}
				var id=$("input[name='ckb']:checked").eq(0).val();
				
				$.post(root+'/controller/workOder/updateEwflag.do?id='
		    			+id+'&ew_flag=0',
				function(data){
					if(data.code==1){
						pageJump();	
					}else{
						alert('操作失败！！');
					}
					
				  }); 
			}
			
			
		}
		
		
		function warningtypeCode(){
			warningLevelCodeChange("warningtype_code","initial_level",this.value);
		}

		function warningtypeCodeNew(){
			warningLevelCodeChange("warningtype_code_new","initial_level_new",this.value);

		}
		function warningtypeCodeUpdate(){

			warningLevelCodeChange("warningtype_code_update","initial_level_update",this.value);

		}	
		
		function wkTypeUpdate(){

			workorderLevelCodeChange("wk_type_update","wk_level_update",this.value);

		}
		

		function wkTypeNew(){

			workorderLevelCodeChange("wk_type_new","wk_level_new",this.value);

		}
		
		function wkTypeQuery(){

			workorderLevelCodeChange("wk_type_query","wk_level_query",this.value);

		}

		
	</script>	
</html>
