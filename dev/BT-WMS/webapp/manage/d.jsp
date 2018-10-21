<%@ page language= "java" import= "java.util.*" pageEncoding= "utf-8" %>
<!DOCTYPE html>
<html>
	<head lang= "en">
		<meta charset= "UTF-8">
		<%@ include file= "../yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<script type= "text/javascript">
			function res(){
				$("#form1").reset();
			}

			function pageJump() {
				find();
			}
			function find(){
				var stime = $("#stime").val();
				var etime = $("#etime").val();
				if(stime!=''){
					stime = str = stime.replace(/-/g,'/');
				}
				if(etime!=''){
					etime = str = etime.replace(/-/g,'/');
				}
				var sdate = new Date(stime).getTime(); 
				var edate = new Date(etime).getTime();
				openDiv("${root }control/mainController/d.do?container_code="+$("#container_code").val()+"&stime="+sdate+"&etime="+edate
						+"&create_user="+$("#create_user").val()
						+"&sku="+$("#sku").val()
						+ "&startRow="
						+ $("#startRow").val()
						+ "&endRow="
						+ $("#startRow").val()
						+ "&page="
						+ $("#pageIndex").val()
						+ "&pageSize="
						+ $("#pageSize").val(),"异常记录查询");
			}

			function export_excel(){
				var stime = $("#stime").val();
				var etime = $("#etime").val();
				if(stime!=''){
					stime = str = stime.replace(/-/g,'/');
				}
				if(etime!=''){
					etime = str = etime.replace(/-/g,'/');
				}
				var sdate = new Date(stime).getTime(); 
				var edate = new Date(etime).getTime();
				 $.ajax({
					type : "POST",
					url: "${root}control/mainController/export_e.do",  
					data:"container_code="+$("#container_code").val()+"&stime="+sdate+"&etime="+edate
					+"&create_user="+$("#create_user").val()
					+"&sku="+$("#sku").val(),
					dataType: "text",  
					success : function(jsonStr) {
						window.open(root+jsonStr);
					}
				});
			}
		</script>
	</head>
	<body>
		<form id="form1" name="form1" action="">
			<div class= "page-header" style= "margin-left: 20px; margin-bottom: 20px;" >
				<table>
			  		<tr>
			  			<td align= "right" width= "10%"><label class= "blue" for= "client_code">开始时间&nbsp;:</label></td>
			  			<td width= "20%">
							<input type="text" id="stime" name="stime" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" value="${queryParam.create_time_s}"/>
			  			</td>
			  			<td align= "right" width= "10%"><label class= "blue" for= "client_code">结束时间&nbsp;:</label></td>
			  			<td width= "20%">
							<input type="text" id="etime" name="etime" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" value="${queryParam.create_time_e}"/>
			  			</td>
			  			<td align= "right" width= "10%"><label class= "blue" for= "client_code">操作人&nbsp;:</label></td>
			  			<td width= "20%"><input id= "create_user" name= "create_user" type= "text" value= "${queryParam.create_user }" /></td>
			  		</tr>
			  		<tr>
			  			<td align= "right" width= "10%"><label class= "blue" for= "client_code">容器号&nbsp;:</label></td>
			  			<td width= "20%"><input id= "container_code" name= "container_code" type= "text" value= "${queryParam.container_code }" /></td>
			  			<td align= "right" width= "10%"><label class= "blue" for= "client_code">SKU&nbsp;:</label></td>
			  			<td width= "20%"><input id= "sku" name= "sku" type= "text" value= "${queryParam.sku }" /></td>
			  		</tr>
				</table>
			</div>
			<div style= "margin-top: 10px; margin-bottom: 10px; margin-left: 20px;" >
				<button class= "btn btn-xs btn-pink" type="button" onclick= "find();" >
					<i class= "icon-search icon-on-right bigger-110" ></i>查询
				</button>&nbsp;
				<button class= "btn btn-xs btn-yellow" type="reset" >
					<i class= "icon-hdd" ></i>重置
				</button>&nbsp;
				<button class= "btn btn-xs btn-inverse" type="button" onclick= "export_excel();" >
					<i class= "icon-hdd" ></i>导出
				</button>&nbsp;
			</div>
		</form>
		<div class= "title_div" id= "sc_title" >
			<table class= "table table-striped" style= "table-layout: fixed;" >
		   		<thead>
			  		<tr class= "table_head_line" >
			  			<td class= "td_cs" >操作时间</td>
			  			<td class= "td_cs" >操作人</td>
			  			<td class= "td_cs" >容器号</td>
			  			<td class= "td_cs" >SKU条码</td>
			  			<td class= "td_cs" >数量</td>
			  		</tr>  	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center">
			  		<c:forEach items= "${pageView.records }" var= "res" >
				  		<tr>
				  			<td class= "td_cs" >${res.create_time }</td>
				  			<td class= "td_cs" >${res.create_user }</td>
				  			<td class= "td_cs" >${res.container_code }</td>
				  			<td class= "td_cs" >${res.sku }</td>
				  			<td class= "td_cs" >${res.num }</td>
				  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
      	<div style= "margin-right: 1%; margin-top: 20px;" >${pageView.pageView }</div>
	</body>
</html>
