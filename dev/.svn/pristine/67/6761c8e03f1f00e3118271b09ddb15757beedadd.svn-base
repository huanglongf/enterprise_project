<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	$(function(){
		$("#conditionTr").children("td").each(function(){
			$(this).children(":first").on('blur keydown', function(event){
				if(event.type == "blur" || event.keyCode == 13){
					var batId = $("#batId_param").val();
					if(batId != ""){
						// 当批次号参数不为空
						if(isNaN(batId) || batId < 0){
							alert("批次号输入不合法！");
							return false;
						}
					}
					openIdDiv("rawDataList", "/BT-LMIS/control/rawDataController/tolist.do?batId=" 
						+ batId
						+ "&type="
						+ $("#type_param").val()
						);
				}
			});
		})
	});
</script>
<div align="left">
	<h4>
		已上传接口原始数据
	</h4>
</div>
<div class="title_div" id="sc_title" style="height: 85px;">		
	<table class="table table-striped" style="table-layout: fixed;">
   		<thead>
	  		<tr class="table_head_line">
	  			<td class="td_cs" style="width: 200px">原始数据类型</td>
	  			<td class="td_cs" style="width: 200px">批次号</td>
	  			<td class="td_cs" style="width: 200px">数据状态</td>
	  			<td class="td_cs" style="width: 200px">操作</td>
	  		</tr>
	  		<tr id="conditionTr">
	  			<td class="td_cs" ><input id="type_param" value="${type }" /></td>
	  			<td class="td_cs" ><input id="batId_param" value="${batId }" /></td>
	  			<td class="td_cs" ></td>
	  			<td class="td_cs" ></td>
	  		</tr>
		</thead>
	</table>
</div>
<div class="tree_div" style="height: 85px; margin-top: -85px;"></div>
<div style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title', 'sc_content')">
	<table id="table_content" class="table table-hover table-striped" style="table-layout: fixed;">
  		<tbody align="center">
	  		<c:forEach items="${result }" var="res">
		  		<c:forEach items="${res.batIdData }" var="batIdData">
		  			<tr>
			  			<td class="td_cs" style="width: 200px" title="${res.type }">${res.type }</td>
			  			<td class="td_cs" style="width: 200px" title="${batIdData.batId }">${batIdData.batId }</td>
			  			<td class="td_cs" style="width: 200px" title="${batIdData.msg }">${batIdData.msg }</td>
			  			<td class="td_cs" style="width: 200px">
		  					<button class="btn btn-xs btn-success" onclick="load('${res.type }', '${batIdData.batId }');">
								<i class="icon-upload"></i>加载
							</button>&nbsp;
			  				<button class="btn btn-xs btn-danger" onclick="del('${res.type }', '${batIdData.batId }');">
								<i class="icon-trash"></i>删除
							</button>&nbsp;
			  			</td>
			  		</tr>
		  		</c:forEach>
	  		</c:forEach>
  		</tbody>
	</table>
</div>