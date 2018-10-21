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
				openDiv("${root }control/mainController/c.do?container_code="+$("#container_code").val()
						+"&type="+$("#type").val()
						+"&create_user="+$("#create_user").val()
						+"&location="+$("#location").val()
						+"&sku="+$("#sku").val()
						+ "&startRow="
						+ $("#startRow").val()
						+ "&endRow="
						+ $("#startRow").val()
						+ "&page="
						+ $("#pageIndex").val()
						+ "&pageSize="
						+ $("#pageSize").val(),"上架下架记录查询");
			}
		</script>
	</head>
	<body>
		<button class= "btn btn-xs btn-yellow" type="reset" onclick= "openDiv('${root}control/mainController/a.do','容器查询');">
			<i class= "icon-hdd" ></i>返回
		</button>
		<div class= "title_div" id= "sc_title" >
			<table class= "table table-striped" style= "table-layout: fixed;" >
		   		<thead>
			  		<tr class= "table_head_line" >
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
			  		<c:forEach items= "${containerList }" var= "res" >
				  		<tr>
				  			<td class= "td_cs" >${res.container_code }</td>
				  			<td class= "td_cs" >${res.sku }</td>
				  			<td class= "td_cs" >${res.cut }</td>
				  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
	</body>
</html>
