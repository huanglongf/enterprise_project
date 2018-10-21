
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="../../lmis/yuriy.jsp" %>
		<title>快递信息查询</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link href="<%=basePath %>My97DatePicker/skin/WdatePicker.css" rel="stylesheet">
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<%-- <script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script> --%>
		<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type='text/javascript'>
			function add(){
				openDiv('/BT-LMIS//control/radar/ageingMasterController/addAgeingMaster.do');
			}
			function toDetail(id){
				openDiv('/BT-LMIS/control/radar/ageingdetailcontroller/query.do?ageingId='+id);
			}
			function pageJump() {
				query();
			}
			function query(){
				  openDiv("/BT-LMIS//control/radar/ageingMasterController/query.do?startRow="
					  + $("#startRow").val()
					  + "&endRow="
					  + $("#startRow").val()
					  + "&page="
					  + $("#pageIndex").val()
					  + "&pageSize="
					  + $("#pageSize").val());
			}
			
			function inverseCkb(items) {
				$('[name=' + items + ']:checkbox').each(function() {
					this.checked = !this.checked;
				});
			}
			
			function toupdate(){
				 if($("input[name='ckb']:checked").length==1){
				  		 var result=  	confirm('是否修改！'); 
							if(result){
								var ids=$("input[name='ckb']:checked");
								var idsStr='';
								$.each(ids,function(index,item){
									idsStr=idsStr+this.value+';';
								});
								openDiv('/BT-LMIS//control/radar/ageingMasterController/toupdateAgeingMaster.do?ids='+idsStr); 
							}
				}else{
					alert("请选择一行!");
				} 
			}
            //删除
            function  todel() {
                var priv_ids =[];
                $("input[name='ckb']:checked").each(function(){
                    priv_ids.push($.trim($(this).val()));
                });
                if($("input[name='ckb']:checked").length >=1 ){
                    if(!confirm("确定删除吗?")){
                        return;
                    }
                    $.ajax({
                        type:"post",
                        url:"<%=basePath%>control/radar/ageingMasterController/delAgeingMaster.do",
                        data:"id="+priv_ids,
                        dataType:"json",
                        success:function(result){
                            if(result.flag){
                                alert(result.msg);
                                refresh();
                            }else{
                                alert(result.msg);
                            }
                        }
                    })
                }else{
                    alert("请选择要删除的行!")
                }
            }

            /**
             * 刷新
             * */
            function refresh(){
                openDiv('<%=basePath%>control/radar/ageingMasterController/query.do');
            }
            
            
            /**
             * 修改状态
             * */
            function upStatus(id,status){
    			$.ajax({
    	   			type: "POST",  
    	           	url: root+"/control/radar/ageingMasterController/upStatus.do?",
    	           	//我们用text格式接收
    	           	dataType: "json",
    	           	data:  "id="+id+"&status="+status,
    	           	success: function (result) {
    	           		if(result.flag){
      						openDiv('${root}/control/radar/ageingMasterController/query.do');
    	           		}else{
    	           			alert(result.msg);
    	           			openDiv('${root}/control/radar/ageingMasterController/query.do');
    	           		}
    	         	}  
    			}); 
    	  	}
            
            function find(){
                var param=$('#searchForm').serialize();
                openDiv('<%=basePath%>control/radar/ageingMasterController/query.do?'+param);
            }
    	</script>
	</head>
	<body>
	
		<div class="page-header"><h1 style='margin-left:20px'>店铺时效信息查询</h1></div>
		<div style= "width: 100%;" align= "center">
			<form id="searchForm">
				<table  style="width: auto;padding: 10px;">
					<tr>
						<td align="left" width="80px"><label class="blue" >时效名称&nbsp;:</label></td>
						<td width="200px">
							<input id='ageingName' name='ageingName' value="${queryParam.ageingName}">
						</td>
						<td align="left" width="80px"><label class="blue" >店铺名称&nbsp;:</label></td>
						<td width="200px">
							<select id="storeCode"  name="storeCode"  class='select'  data-edit-select="1" style='width:160px;height:30px'>
			  					<option value= "" >---请选择---</option>
								<c:forEach items= "${stores }" var= "store" >
									<c:if test='${store.store_code eq queryParam.storeCode }'>
										<option selected='selected' value="${store.store_code}">${store.store_name}</option>
									</c:if>
									<c:if test='${store.store_code ne queryParam.storeCode }'>
										<option  value="${store.store_code}">${store.store_name}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<td align="left" width="80px"><label class="blue" >备注&nbsp;:</label></td>
						<td colspan="7">
							<input  id='remark' name='remark' value="${queryParam.remark}"/>
						</td>
					</tr>
				</table>
			</form>
		</div>	
		<div class="page-header"><h1 style='margin-left:20px'>店铺时效信息查询</h1></div>
		<div style="margin-top: 10px;margin-left: 20px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
 			&nbsp; 
			<button class="btn btn-xs btn-pink" onclick="add();">
				<i class="icon-search icon-on-right bigger-110"></i>新增
			</button>
 			&nbsp; 
			<!-- <button class="btn btn-xs btn-pink" onclick="toupdate();">
				<i class="icon-search icon-on-right bigger-110"></i>修改
			</button>
 			&nbsp; -->
			<button class="btn btn-xs btn-pink" onclick="todel();">
				<i class="icon-search icon-on-right bigger-110"></i>删除
			</button>
			&nbsp;
				
		</div>
		
		<div id="page_view">
		<div class='title_div' style="height : 520px; overflow : auto; overflow : scroll; border : solid #F2F2F2 1px;"  >
			<table class="table table-striped" overflow-x：show>
		   		<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td><input style="width: 50px" type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class='td_cs' style="width: 50px">序号</td>
			  			<td class='td_cs' style="width: 50px">单据号</td>
			  			<td class='td_cs' style="width: 200px">时效名称</td>
			  			<td class='td_cs' style="width: 200px">店铺名称名称</td>
			  			<td class='td_cs' style="width: 200px">创建时间</td>
			  			<td class='td_cs' style="width: 200px">创建人</td>
			  			<td class='td_cs' style="width: 200px">备注</td>
			  			<td class='td_cs' style="width: 200px">状态</td>
			  		</tr>  	
		  		</thead>
		  		<tbody id='tbody' align="center">
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr ondblclick='toDetail("${power.id}");' value="${power.id}" >
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td nowrap="nowrap">${status.count }</td>
			  			<td nowrap="nowrap">${power.id}</td>
			  			<td nowrap="nowrap">${power.ageingName}</td>
			  			<td nowrap="nowrap">${power.storeName}</td>
			  			<td nowrap="nowrap"><fmt:formatDate value="${power.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			  			<td nowrap="nowrap">${power.createUser}</td>
			  			<td nowrap="nowrap">${power.remark}</td>
			  			<td nowrap="nowrap">
			  				<c:if test="${power.status==0}">已停用</c:if>
			  				<c:if test="${power.status==1}">已启用</c:if>
			  				|
			  				<c:if test="${power.status==1}"><button class="btn btn-xs btn-info" onclick="upStatus('${power.id}','0');">停用</button></c:if>
			  				<c:if test="${power.status==0}"><button class="btn btn-xs btn-pink" onclick="upStatus('${power.id}','1');">启用</button></c:if>
			  			</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
     <div id='botton_page' style="margin-right: 30px;margin-top:20px;">
<div style="margin-right: 30px;margin-top:20px;">${pageView.pageView}</div>	
      	 </div>
      	 </div> 
	</body>
</html>
<style>
.select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: center;
}
.title_div td{
	font-size: 15px;
}

.m-input-select {
    display: inline-block;
    position: relative;
    -webkit-user-select: none;
    width: 160px;
    }
</style>

