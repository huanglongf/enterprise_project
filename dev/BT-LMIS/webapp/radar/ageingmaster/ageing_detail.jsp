
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
		<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type='text/javascript'>
		function pageJump() {
			query();
		}
		function query(){
			  openDiv("/BT-LMIS//control/radar/ageingdetailcontroller/query.do?startRow="
				  + $("#startRow").val()
				  + "&endRow="
				  + $("#startRow").val()
				  + "&page="
				  + $("#pageIndex").val()
				  + "&pageSize="
				  + $("#pageSize").val()
				  + "&ageingId="
				  + $("#ageingId").val());
		}
		
			function toUpload(){
                var param=$('#searchForm').serialize();
//				var ageingId = $('#ageingId').val();
				openDiv('/BT-LMIS/control/radar/ageingdetailcontroller/ageingDetailUpload.do?'+param);
			}
			function add(){
				//openDiv('/BT-LMIS/control/radar/ageingdetailcontroller/ageingDetailUpload.do');
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
								openDiv('/BT-LMIS//control/radar/ageingdetailcontroller/update.do?ids='+idsStr); 
							}
				}else{
					alert("请选择一行!");
				} 
			}
            //删除
            function  todel() {
			    debugger;
                var priv_ids =[];
                $("input[name='ckb']:checked").each(function(){
                    priv_ids.push($.trim($(this).val()));
                });
                var param=$('#searchForm').serialize();
				param=param+"&detailIdStr="+priv_ids;
                if($("input[name='ckb']:checked").length >=1 ){
                    if(!confirm("确定删除吗?")){
                        return;
                    }
                    $.ajax({
                        type:"post",
                        url:"<%=basePath%>control/radar/ageingdetailcontroller/delAgeingDetail.do",
                        data:param,
                        dataType:"json",
                        success:function(result){
                            if(result.flag){
                                alert(result.msg);
                                find();
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
                openDiv('<%=basePath%>control/radar/ageingdetailcontroller/query.do?ageingId='+$("#ageingId").val());
            }
            //查询
            function find(){
                var param=$('#searchForm').serialize();
                openDiv('<%=basePath%>control/radar/ageingdetailcontroller/query.do?'+param);
            }
            function black(){
                var param=$('#searchForm').serialize();
                openDiv('<%=basePath%>control/radar/ageingMasterController/query.do');
            }

            $(function () {
                $('#pCode').bind('change',function(){
                    if(this.value==''){
                        $('#cCode').html('<option value="">---请选择---</option>');
                        $('#sCode').html('<option value="">---请选择---</option>');
                    }
                    $.post('${root}/control/radar/expressinfoMasterController/getArea.do?area_code='+this.value,function(data){
                        if(data.toString()=='[object XMLDocument]'){
                            window.location='/BT-LMIS';
                            return;
                        };
                        if(data.nodeName=='#document')toMain();
                        var  htmlStr='<option value="">---请选择---</option>';
                        $.each(data.area,function(index,item){
                            htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';
                        });
                        $('#cCode').html(htmlStr);
                        $('#sCode').html('<option value="">---请选择---</option>');
                    });
                });
                $('#cCode').bind('change',function(){
                    if(this.value==''){
                        $('#sCode').html('<option value="">---请选择---</option>');
                    }
                    $.post('${root}/control/radar/expressinfoMasterController/getArea.do?area_code='+this.value,function(data){
                        if(data.toString()=='[object XMLDocument]'){
                            window.location='/BT-LMIS';
                            return;
                        };
                        if(data.nodeName=='#document')toMain();
                        var  htmlStr='<option value="">---请选择---</option>';
                        $.each(data.area,function(index,item){
                            htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';
                        });
                        $('#sCode').html(htmlStr);
                    });
                });
                $('#expressCode').bind('change',function(data){
                    ExpressCodeChange("expressCode","productTypeCode");
                });
            });
            function ExpressCodeChange(upper,third){
                upper = "#" + upper;
                third ="#"+third;
                var transport_code = $(upper).val();
                if(transport_code == ""){
                    $(third).val("");
                    $(third).next().val("");
                    $(third).next().attr("placeholder", "---请选择---");
                    $(third).next().attr("disabled", "disabled");
                } else {
                    $.ajax({
                        url : root + "/control/radar/expressinfoMasterController/getRouteStatus.do",
                        type : "post",
                        data : {
                            "transport_code" : transport_code
                        },
                        dataType : "json",
                        async : false,
                        success : function(result) {
                            $(third).next().attr("disabled", false);
                            $(third).children(":first").siblings().remove();
                            $(third).siblings("ul").children(":first").siblings().remove();
                            var content3 = '';
                            var content4 = '';
                            for(var i =0; i < result.products.length; i++){
                                content3 +=
                                    '<option value="'
                                    + result.products[i].product_type_code
                                    + '">'
                                    +result.products[i].product_type_name
                                    +'</option>'
                                content4 +=
                                    '<li class="m-list-item" data-value="'
                                    + result.products[i].product_type_code
                                    + '">'
                                    + result.products[i].product_type_name
                                    + '</li>'
                            }
                            $(third).next().val("");
                            $(third).next().attr("placeholder", "---请选择---");

                            $(third + " option:eq(0)").after(content3);
                            $(third + " option:eq(0)").attr("selected", true);
                            $(third).siblings("ul").children(":first").after(content4);
                            $(third).siblings("ul").children(":first").addClass("m-list-item-active");
                        },
                        error : function(result) {
                            alert(result);
                        }
                    });
                }
            }
            
            function add() {
            	 openDiv('<%=basePath%>control/radar/ageingdetailcontroller/add.do?ageingId='+$("#ageingId").val());
			}
            function downLoad() {
            	var result=  	confirm('是否导出时效明细！'); 
				if(result){
					loadingStyle();
					$.ajax({
						type: "POST",  
						url: '${root}control/radar/ageingdetailcontroller/downLoad.do?ageingId='+$("#ageingId").val(),
						//json格式接收数据  
						dataType: "json",  
						success: function (jsonStr) {
							cancelLoadingStyle();
						    if(jsonStr.out_result==1){
							    window.open(root + jsonStr.path);
						    }
						} 
					}); 
	  		
			}
			}
    	</script>
	</head>
	<body>
	
		<div class="page-header"><h1 style='margin-left:20px'>店铺时效信息查询</h1></div>
		<div style= "width: 100%;" align= "center">
			<form id="searchForm">
				<input type="hidden" id="ageingId" name="ageingId" value="${queryParam.ageingId}">
				<table  style="width: auto;padding: 10px;">
					<tr>
						<td align="left" width="80px"><label class="blue" >仓库名称&nbsp;:</label></td>
						<td width="200px">
							<select id='warehouseCode'  name="warehouseCode"  data-edit-select="1">
								<option value= ''>---请选择---</option>
								<c:forEach items="${warehouses}" var = "warehouse" >
									<c:if test="${ queryParam.warehouseCode eq warehouse.warehouse_code}">
										<option  value="${warehouse.warehouse_code}" selected="selected">${warehouse.warehouse_name}</option>
									</c:if>
									<c:if test="${warehouse.warehouse_code ne queryParam.warehouseCode}">
										<option  value="${warehouse.warehouse_code}">${warehouse.warehouse_name}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<td align="left" width="80px"><label class="blue" >物流商&nbsp;:</label></td>
						<td width="200px">
							<select id="expressCode"  name="expressCode"  class='select'  data-edit-select="1" style='width:160px;height:30px'>
								<option value= ''>---请选择---</option>
								<c:forEach items="${trans_names}" var = "trans_names" >
									<c:if test='${trans_names.transport_code eq queryParam.expressCode}'>
										<option selected='selected' value="${trans_names.transport_code}">${trans_names.transport_name}</option></c:if>
									<c:if test='${trans_names.transport_code ne queryParam.expressCode}'>
										<option   value="${trans_names.transport_code}">${trans_names.transport_name}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<td align="left" width="80px"><label class="blue" >产品类型&nbsp;:</label></td>
						<td colspan="7">
							<select id="productTypeCode" name="productTypeCode"  data-edit-select="1">
								<option value= ''>---请选择---</option>
								<c:forEach items='${prodeuct_type}' var='prodeuct_type'>
									<c:if test='${queryParam.productTypeCode eq prodeuct_type.product_type_code}'>
										<option selected='selected' value="${prodeuct_type.product_type_code}">${prodeuct_type.product_type_name}</option>
									</c:if>
									<c:if test='${queryParam.productTypeCode ne prodeuct_type.product_type_code}'>
										<option  value="${prodeuct_type.product_type_code}">${prodeuct_type.product_type_name}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" width="20px"><label class="blue" >省&nbsp;:</label></td>
						<td width="200px">
							<select id="pCode" name="pCode"  data-edit-select="1" onchange='shiftArea(1,"pCode","cCode","sCode","")'>
								<option value= ''>---请选择---</option>
								<c:forEach items="${areas}" var = "area" >
									<c:if test='${queryParam.pCode eq area.area_code }'>
										<option selected='selected' value="${area.area_code}">${area.area_name}</option>
									</c:if>
									<c:if test='${queryParam.pCode ne area.area_code }'>
										<option  value="${area.area_code}">${area.area_name}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<td align="left" width="20px"><label class="blue" >市&nbsp;:</label></td>
						<td width="200px">
							<select id="cCode" name="cCode"   data-edit-select="1"  onchange='shiftArea(2,"pCode","cCode","sCode","")'>
								<option value=''>---请选择---</option>
								<c:forEach items="${city}" var = "cityVar" >
									<c:if test='${queryParam.cCode eq cityVar.area_code}'>
										<option selected='selected'  value="${cityVar.area_code}">${cityVar.area_name}</option>
									</c:if>
									<c:if test='${queryParam.cCode ne cityVar.area_code}'>
										<option  value="${cityVar.area_code}">${cityVar.area_name}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<td align="left" width="20px"><label class="blue" >区&nbsp;:</label></td>
						<td width="200px">
							<select id="sCode" name="sCode"   data-edit-select="1"  >
								<option value=''>---请选择---</option>
								<c:forEach items="${state}" var = "stateVar" >
									<c:if test='${stateVar.area_code eq queryParam.sCode }'>
										<option selected='selected' value="${stateVar.area_code}">${stateVar.area_name}</option>
									</c:if>
									<c:if test='${stateVar.area_code ne queryParam.sCode }'>
										<option  value="${stateVar.area_code}">${stateVar.area_name}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div style="margin-top: 10px;margin-left: 20px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
 			&nbsp; 
			<button class="btn btn-xs btn-pink" onclick="add();">
				<i class="icon-search icon-on-right bigger-110"></i>新增
			</button>
 			&nbsp; 
			<button class="btn btn-xs btn-pink" onclick="toupdate();">
				<i class="icon-search icon-on-right bigger-110"></i>修改
			</button>
 			&nbsp;
			<button class="btn btn-xs btn-pink" onclick="todel();">
				<i class="icon-search icon-on-right bigger-110"></i>删除
			</button>
			&nbsp;
			<button class="btn btn-xs btn-pink" onclick="toUpload();">
				<i class="icon-search icon-on-right bigger-110"></i>导入
			</button>
 			&nbsp; 
			<button class="btn btn-xs btn-primary" onclick="downLoad();"> 
				<i class="icon-edit"></i>导出
			</button>
			&nbsp;
			<button class="btn btn-xs btn-pink" onclick="black();">
				<i class="icon-edit"></i>返回
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
			  			<td class='td_cs' style="width: 200px">仓库名称</td>
			  			<td class='td_cs' style="width: 200px">物流商名称</td>
			  			<td class='td_cs' style="width: 200px">产品类型名称</td>
			  			<td class='td_cs' style="width: 200px">省</td>
			  			<td class='td_cs' style="width: 200px">市</td>
			  			<td class='td_cs' style="width: 200px">区</td>
			  			<td class='td_cs' style="width: 200px">揽件截止时间</td>
			  			<td class='td_cs' style="width: 200px">时效值/天</td>
			  		</tr>  	
		  		</thead>
		  		<tbody id='tbody' align="center">
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td nowrap="nowrap">${status.count }</td>
			  			<td nowrap="nowrap">${power.warehouseName}</td>
			  			<td nowrap="nowrap">${power.expressName}</td>
			  			<td nowrap="nowrap">${power.productTypeName}</td>
			  			<td nowrap="nowrap">${power.pName}</td>
			  			<td nowrap="nowrap">${power.cName}</td>
			  			<td nowrap="nowrap">${power.sName}</td>
			  			<td nowrap="nowrap">${power.embranceCalTime}</td>
			  			<td nowrap="nowrap">${power.ageingValue}</td>
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

