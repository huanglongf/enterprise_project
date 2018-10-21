<%@ page language= "java" import= "java.util.*" pageEncoding= "utf-8" %>
<%@ page import= "com.bt.lmis.page.PageView"  %>
<%@ page import= "com.bt.utils.CommonUtils" %>
<!DOCTYPE html>
<html>
	<head lang= "en" >
		<meta charset= "UTF-8" >
		<%@ include file= "/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>css/table.css" rel= "stylesheet" />	
		<link type= "text/css" href= "<%=basePath %>daterangepicker/font-awesome.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>font-awesome-4.7.0/css/font-awesome.css" rel="stylesheet" media="all" />
		<link type="text/css" href="<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link type="text/css" href="<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		
		<script type="text/javascript" src="<%=basePath %>jquery/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/validateForm.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>" + "<"+"/script>");
			/* $(document).ready(function() {
				$(".form-control").daterangepicker(null, function(start, end, label) {
	               	console.log(start.toISOString(), end.toISOString(), label);
	               	
				});
				
			}); */
			$(document).ready(function() {
				$("#reservation2").daterangepicker(null, function(start, end, label) {
	               	console.log(start.toISOString(), end.toISOString(), label);
	               	
				});
				
			});
		</script>
	</head>
	<body>
		<div class= "page-header" align= "left" >
			<h1>合同管理</h1>
		</div>
		<form id= "main_search" >
			<table>
				<%
					Map param=new HashMap();
					if(request.getAttribute("queryParam") != null){
						param=CommonUtils.convertBean(request.getAttribute("queryParam"));
						
					}
					ArrayList sec= (ArrayList)request.getAttribute("search");
					System.out.print(sec.size());
					for(int sc= 0; sc< sec.size(); sc+= 2){
				%>
				<tr>
					<td <%if(((HashMap)sec.get(sc)).get("field_name").equals("")){ %>style="display: none;"<%} %>><%=((HashMap)sec.get(sc)).get("field_value")==null?"":((HashMap)sec.get(sc)).get("field_value")%>:</td>
					<td class= "col-xs-5" <%if(((HashMap)sec.get(sc)).get("field_value").equals("")){ %>style= "display: none;"<%} %> >
						<%if(((HashMap)sec.get(sc)).get("data_type").equals("date")||((HashMap)sec.get(sc)).get("data_type").equals("datetime") ){ %>
							<div class= "input-prepend input-group" style= "width: 240px;" >
								<span class= "add-on input-group-addon" >
								 <i class= "glyphicon glyphicon-calendar fa fa-calendar" ></i></span>
								<input type= "text" readonly  style= "width:100%;" name= "<%=((HashMap)sec.get(sc)).get("field_name")%>" id= "reservation" class= "form-control" value= "<%=param.get(((HashMap)sec.get(sc)).get("field_name")) == null? "": param.get(((HashMap)sec.get(sc)).get("field_name")) %>" />
							</div>
					
						<%} else { %>	
							<input id="contract_no" name="<%=((HashMap)sec.get(sc)).get("field_name")%>" value="<%=param.get(((HashMap)sec.get(sc)).get("field_name"))==null?"":param.get(((HashMap)sec.get(sc)).get("field_name")) %>" style= "width:90%" type= "text" />
						<%} %>	
					</td>
					<td <%if(((HashMap)sec.get(sc+1)).get("field_name").equals("")){ %>style="display: none;"<%} %>><%=((HashMap)sec.get(sc+1)).get("field_value")%>:</td>
                    <td class= "col-xs-5" <%if(((HashMap)sec.get(sc+1)).get("field_value").equals("")){ %>style="display: none;"<%} %>>
						<%if(((HashMap)sec.get(sc+1)).get("data_type").equals("date")||((HashMap)sec.get(sc+1)).get("data_type").equals("datetime")){ %>
						<div class= "input-prepend input-group" style= "width: 240px;" >
							<span class= "add-on input-group-addon" >
							<i class= "glyphicon glyphicon-calendar fa fa-calendar" ></i></span>
							<input type= "text" readonly style= "width:100%" name= "<%=((HashMap)sec.get(sc+1)).get("field_name")%>" id="reservation" class="form-control" value="<%=param.get(((HashMap)sec.get(sc+1)).get("field_name")) == null? "": param.get(((HashMap)sec.get(sc+1)).get("field_name")) %>" />
						</div>
						<%} else { %>					  
							<input id= "contract_no" name= "<%=((HashMap)sec.get(sc+1)).get("field_name") %>" value= "<%=param.get(((HashMap)sec.get(sc+1)).get("field_name"))==null?"":param.get(((HashMap)sec.get(sc+1)).get("field_name")) %>" style= "width:90%" type= "text" />
						<%} %>
					</td>				
               </tr>
               <%}%>
			</table>
		</form>
		<div style= "margin-top: 10px; margin-left: 10px; margin-bottom: 10px;" >
			<button class= "btn btn-xs btn-pink" onclick= "find();" >
				<i class= "icon-search icon-on-right bigger-110" ></i>查询
			</button>&nbsp;
			<button class= "btn btn-xs btn-primary" onclick= "toUpdate();" >
				<i class= "icon-edit" ></i>修改
			</button>&nbsp;
			<button class= "btn btn-xs btn-inverse" onclick= "del();" >
				<i class= "icon-trash" ></i>删除
			</button>&nbsp;
			<button class= "btn btn-xs btn-yellow" onclick= "openDiv('${root}control/sospController/toForm.do');" >
				<i class= "icon-hdd" ></i>新增[品牌&店铺]合同
			</button>&nbsp;
			<button class= "btn btn-xs btn-yellow" onclick= "openDiv('${root}control/expressContractController/form.do');" >
				<i class= "icon-hdd" ></i>新增[物流&快递]合同
			</button>&nbsp;
			<button class= "btn btn-xs btn-inverse" onclick= "clean();" >
				<i class= "icon-trash" ></i>清除查询条件
			</button>&nbsp;
			<button class= "btn btn-xs btn-primary" onclick= "toSetPage();" >
				<i class= "icon-edit" ></i>设置页面参数
			</button>&nbsp;
			<!-- <button class= "btn btn-xs btn-primary" onclick= "goReport();" >
				<i class= "icon-edit" ></i>查看报表
			</button>&nbsp; -->
			<button class="btn btn-xs btn-light" onclick="recoverSettlement();">
				<i class="icon-undo"></i>重新结算
			</button>&nbsp;
		</div>
		<form id= "serarch_td" >
			<div  class= "title_div" id= "sc_title" >		
				<table class= "table table-striped" style= "table-layout: fixed;" >
					<thead align= "center" >
						<tr>
							<td class= "td_ch" ><input type= "checkbox" id= "checkAll" onclick= "inverseCkb('ckb')" /></td>
	                            <%
	                            	ArrayList<?> list= (ArrayList<?>)request.getAttribute("field");
	                            	for(int k= 0; k< list.size(); k++) {%>
	                   	    			<td class="td_cs">
	                   	    				<%=((HashMap)list.get(k)).get("field_value") %>
	                   	    			</td>
                   	      		<%} %>
						</tr>
					</thead>
				</table>
			</div>
			<div class= "tree_div" ></div>
			<div class= "content_div" id= "sc_content" onscroll= "init_td('sc_title','sc_content')" >
				<table class= "table table-striped" style= "table-layout: fixed;" >		
					<tbody align= "center" >
				    	<tr>
				    		<td class= "td_ch" ></td>
                    	 	<%for(int k= 0; k< list.size(); k++) {%>
                    	 		<td class= "td_cs" >
	                            	<span class= "input-icon input-icon-right" >
		                            	<%if(((HashMap)list.get(k)).get("data_type").equals("date")||((HashMap)list.get(k)).get("data_type").equals("datetime")) { %>
											<div class= "input-prepend input-group" style= "width: 200px;" >
												<span class= "add-on input-group-addon" >
													<i class= "glyphicon glyphicon-calendar fa fa-calendar" ></i>
												</span>
												<input type= "text" style= "width: 100%" name= "<%=((HashMap)list.get(k)).get("field_name")%>" id= "reservation" class= "form-control" value= "<%=param.get(((HashMap)list.get(k)).get("field_name")) == null? "": param.get(((HashMap)list.get(k)).get("field_name")) %>" onchange= "find_td()" />
											</div>
											<i class="icon-search green" onclick="find_td()"></i>
										<%} else { %>			
											<select style= "text-align:center;" id= "mark_<%=k %>" >
											    <option value= ""></option>
											    <option value= "0">≈</option>
											    <option value= "1">=</option>
											    <option value= "2">></option>
											    <option value= "3">>=</option>
											    <option value= "4"><</option>
											    <option value= "5"><=</option>
										    </select>						
											<input type= "text" id= "" style= "width: 100px;" name= "<%=((HashMap)list.get(k)).get("field_name")%>"  value= "<%=param.get(((HashMap)list.get(k)).get("field_name")) == null? "": param.get(((HashMap)list.get(k)).get("field_name")) %>" onchange= "find_td('<%=k %>')" />
											<%-- <i class="icon-search green" onclick="find_td('<%=k%>')"></i> --%>
										<%}%>
									</span>
							    </td>
                    	 	<%}%>
                    	</tr>
                    	<%
                    		PageView p= (PageView)request.getAttribute("pageView");
                       		for(int i= 0; i< p.getRecords().size(); i++) { %>
	                       		<tr>
	                       			<% if(p.getRecords().get(i)!=null){%>
	                    	   			<td><input id= "ckb" name= "ckb" type= "checkbox" value= "<%=(CommonUtils.convertBean(p.getRecords().get(i))).get("id") %>" /></td>
	                   	    			<%for(int k= 0; k< list.size(); k++) { %>
	                   	    				<td class= "td_cs" >
	                   	    					<%=(CommonUtils.convertBean(p.getRecords().get(i))).get(((HashMap)list.get(k)).get("field_name")) %>
	                   	    				</td>
	                   	        		<%} %>
	                    	      	<%} %>
	                    	   </tr>
                    	<%}%>
					</tbody>
				</table>
			</div>
		</form>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
		<%@include file="/lmis/balance/recover_settlement_form.jsp" %>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#reservation').daterangepicker(null, function(start, end, label) {
				console.log(start.toISOString(), end.toISOString(), label);
				
			});
			
		});
		/**
		 * checkbox(全选&反选)
		 * items 复选框的name
		 */
		function inverseCkb(items) {
			$('[name=' + items + ']:checkbox').each(function() {
				this.checked = !this.checked;
				
			});
			
		}
		/**
		 * 查询
		 */
		function find() {
			var data=$("#main_search").serialize();
			openDivs('${root}control/contractController/list.do',data);
			
		}
		
		function find_td(id) {
			if($("#mark_"+id).val()!=""){
				var data=$("#serarch_td").serialize()+"&mark="+$("#mark_"+id).val();
			
			}else{
				var data=$("#serarch_td").serialize();
				
			}
			openDiv('${root}control/contractController/list.do?'+data);
			
		}

		function pageJump() {
		  var data=$("#serarch_td").serialize();
	      openDiv('${root}control/contractController/list.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	      
		}
		
		function toUpdate() {
			if($("input[name='ckb']:checked").length>=1){
				if($("input[name='ckb']:checked").length>1){
					alert("只能选择一行!");
					
				}else{
			  		openDiv('${root}control/contractController/toUpdate.do?id='+$("input[name='ckb']:checked").val());
			  		
				}
				
			}else{
				alert("请选择一行!");
				
			}
			
		}

	  	/**
	   	* 批量删除
	   	*/
	  	function del(){
	  		if($("input[name='ckb']:checked").length>=1){
				var priv_ids =[];
				// 遍历每一个name为priv_id的复选框，其中选中的执行函数
			  	$("input[name='ckb']:checked").each(function(){
			  		// 将选中的值添加到数组priv_ids中
					priv_ids.push($.trim($(this).val()));
			  		
			  	});
			  	if(!confirm("确定删除吗?")){
				  	return;
				  	
			  	}			  	
			  	$.ajax({
					type: "POST",
		           	url: root+"/control/contractController/del.do?",
		           	dataType: "text",
		           	data:  "privIds="+priv_ids,
		           	success: function (data) {
						if(data=='true'){
							find();
							
						}else if(data=='false'){
		        			alert("删除错误!");
		        			
						}else{
		        			alert("删除异常!");
		        			
						}
						
		           	}  
		           	
			  	});
			  	
	  		}else{
				alert("请选择一行!");
				
	  		}
	  		
	  	}
	  	
	  	function clean(){
	  		$("input").each(function(){
	  		     $(this).val('');
	  		     
	  		});
	  		
	  	}
	  	
	  	function toSetPage(){
	  		openDiv('${root}control/contractController/toSetPage.do?page_id=1');
	  		
	  	}

	    function goReport(){
	    	window.open("${root}control/contractController/goReport.do");
	    	
	    }
	    
	    function recoverSettlement() {
	    	var now = new Date();
	    	var month = (now.getMonth() + 1);
	    	if(month < 10) {
	    		month = "0" + month;
	    		
	    	}
	    	var day = now.getDate();
	    	if(day < 10) {
	    		day = "0" + day;
	    		
	    	}
	    	var date = now.getFullYear() + "-" + month + "-" + day;
	    	$("#recoverDate").val(date + " - " + date);
	    	loadRecoverProcess();
	    	$("#recoverSettlement_form").modal({backdrop: "static", keyboard: false});
	    	
	    }
	    
	    function loadRecoverProcess() {
	    	openIdDiv("recoverSettlementLog", "/BT-LMIS/control/recoverSettlementDataController/toRecoverSettlementLog.do");
	    	
	    }
	</script>
</html>
