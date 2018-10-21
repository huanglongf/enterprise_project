<%@ page language= "java" import= "java.util.*" pageEncoding= "utf-8" %>
<!DOCTYPE html>
<html>
	<head lang= "en">
		<meta charset= "UTF-8">
		<%@ include file= "../yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<script src="<%=basePath %>jquery-1.8.3.js"></script>
		<script type= "text/javascript">
			function res(){
				$("#form1").reset();
			}

			function pageJump() {
				find();
			}
			$(function(){
				$("#container_code").keydown(function(e){
				    if(e.keyCode==13) {
				    	find();
				    }
				});
			});
			function find(){
				openDiv("${root}control/mainController/a.do?container_code="+$("#container_code").val()+"&type="+$("#type").val()+ 
					"&startRow="+ $("#startRow").val()+ "&endRow="+ $("#startRow").val()+ "&page="+ $("#pageIndex").val()+ "&pageSize="+ $("#pageSize").val(),"容器管理");
			}
			function del(id){
				openDiv("${root}control/mainController/a_dtl.do?id="+id,"容器管理");
			}
			function export_excel(){
				 $.ajax({
					type : "POST",
					url: "${root}control/mainController/export_a.do",  
					data:"container_code="+$("#container_code").val()+"&type="+$("#type").val(),
					dataType: "text",  
					success : function(jsonStr) {
						window.open(root+jsonStr);
					}
				});
			}
			function close_c(container_code){
				var a = confirm("还有未操作完的数据!");
				if(a){
					$.ajax({ 
				           type: "POST",  
				           url: root+"control/mainController/close_c.do",  
				           //我们用text格式接收  
				           //json格式接收数据  
				           dataType: "json",  
				           data:  "container_code="+container_code,
				           success: function (jsonStr) {
				        	   	if(jsonStr.code==200){
				        	   		alert("成功!");
			        	   		}else if(jsonStr.code==500){
			        	   			alert("系统错误!");
			        	   		}
				           }  
				    }); 
				}
			}
		</script>
	</head>
	<body>
		<form id="form1" name="form1" action="">
			<div class= "page-header" style= "margin-left: 20px; margin-bottom: 20px;" >
				<table>
			  		<tr>
			  			<td align= "right" width= "10%"><label class= "blue" for= "client_code">容器编码&nbsp;:</label></td>
			  			<td width= "20%"><input id= "container_code" name= "container_code" type= "text" value= "${queryParam.container_code }" /></td>
			  			<td align= "right" width= "10%"><label class= "blue" for= "client_name">容器状态&nbsp;:</label></td>
			  			<td width= "20%">
							<select id="type" name="type">
								<option value="0" selected="selected">请选择</option>
								<option value="1" <c:if test="${queryParam.type==1 }">selected="selected"</c:if>>释放</option>
								<option value="2" <c:if test="${queryParam.type==2 }">selected="selected"</c:if>>下架中</option>
								<option value="3" <c:if test="${queryParam.type==3 }">selected="selected"</c:if>>下架完成</option>
								<option value="4" <c:if test="${queryParam.type==4 }">selected="selected"</c:if>>上架中</option>
							</select>
						</td>
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
				</button>
			</div>
		</form>
		<div class= "title_div" id= "sc_title" >
			<table class= "table table-striped" style= "table-layout: fixed;" >
		   		<thead>
			  		<tr class= "table_head_line" >
			  			<td class= "td_cs" >容器编码</td>
			  			<td class= "td_cs" >使用者</td>
			  			<td class= "td_cs" >状态</td>
			  			<td class= "td_cs" >剩余数量</td>
			  			<td class= "td_cs" >操作</td>
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
				  			<td class= "td_cs"  >${res.container_code }</td>
				  			<td class= "td_cs"  >${res.username }</td>
				  			<td class= "td_cs" >
				  				<c:if test="${res.type==1}">释放</c:if>
				  				<c:if test="${res.type==2}">下架中</c:if>
				  				<c:if test="${res.type==3}">下架完成</c:if>
				  				<c:if test="${res.type==4}">上架中</c:if>
				  			</td>
				  			<td class= "td_cs">${res.num }</td>
				  			<td class= "td_cs">
				  				<c:if test="${res.type!=1}"><button onclick="del('${res.id}')">详细</button></c:if>
				  				<c:if test="${res.type==1}">无</c:if>
<%-- 			  					<c:if test="${res.type==4}"> --%>
<%-- 			  						<button data-theme="a" onclick="close_c('${res.container_code}');">&nbsp;&nbsp;结&nbsp;&nbsp;束&nbsp;&nbsp;</button> --%>
<%-- 		  						</c:if> --%>
			  				</td>
				  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
      	<div style= "margin-right: 1%; margin-top: 20px;" >${pageView.pageView }</div>
	</body>
</html>
