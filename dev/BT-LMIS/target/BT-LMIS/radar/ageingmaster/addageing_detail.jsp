<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
	<meta charset="UTF-8">
	<%@ include file="/lmis/yuriy.jsp"%>
	<title>LMIS</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="overview &amp; stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>assets/css/bootstrap-timepicker.css" />
	<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />
	<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/dialog.css" />
	
    <script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
	<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
	<script type="text/javascript" src="<%=basePath %>assets/js/date-time/bootstrap-timepicker.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
	<script type='text/javascript'>
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
    function submit() {
    	var param=$('#searchForm').serialize();
    	var ageingId = $('#ageingId').val();
    	var warehouseCode = $('#warehouseCode').val();
    	if(warehouseCode=="" ||warehouseCode==null ||warehouseCode==undefined){
			alert("仓库名称不能为空");
			return false;
		}
    	var expressCode = $('#expressCode').val();
    	if(expressCode=="" ||expressCode==null ||expressCode==undefined){
			alert("物流商不能为空");
			return false;
		}
    	var productTypeCode = $('#productTypeCode').val();
    	if(productTypeCode=="" ||productTypeCode==null ||productTypeCode==undefined){
			alert("产品类型不能为空");
			return false;
		}
    	var pCode = $('#pCode').val();
    	if(pCode=="" ||pCode==null ||pCode==undefined){
			alert("省不能为空");
			return false;
		}
    	var cCode = $('#cCode').val();
    	if(cCode=="" ||cCode==null ||cCode==undefined){
			alert("市不能为空");
			return false;
		}
    	var ageingValue = $('#ageingValue').val();
    	if(ageingValue=="" ||ageingValue==null ||ageingValue==undefined){
			alert("时效值不能为空");
			return false;
		}
    	var embranceCalTime = $('#embranceCalTime').val();
    	if(embranceCalTime=="" ||embranceCalTime==null ||embranceCalTime==undefined){
			alert("揽件截至时间不能为空");
			return false;
		}
        $.post('${root}control/radar/ageingdetailcontroller/addAgeingDetail.do?'+param,
  	 			function(result){
		        	if(result.flag){
		                alert(result.msg);
		                openDiv('/BT-LMIS/control/radar/ageingdetailcontroller/query.do?ageingId='+ageingId);
		            }else{
		                alert(result.msg);
		                //openDiv('/BT-LMIS/control/radar/ageingdetailcontroller/query.do?ageingId='+ageingId);
		            }
          	}); 
	}
    function back() {
    	var ageingId = $('#ageingId').val();
		openDiv('/BT-LMIS/control/radar/ageingdetailcontroller/query.do?ageingId='+ageingId);
	}
		
		
    </script>
	
</head>
<body>
	<div class= "page-header" align= "left" >
		<h1>店铺时效明细信息创建/编辑</h1>
	</div>
	<div class= "col-xs-12" >
		<div class= "clearfix form-group no-margin-bottom no-padding-bottom no-border-bottom" >
			<div class= "no-margin-bottom no-padding-bottom no-border-bottom" style= "width: 100%;" align= "center" >
				<form id="searchForm">
				<table  style="width: auto;padding: 10px;">
					<tr>
					<input type="hidden" id="ageingId" name="ageingId" value="${queryParam.ageingId}">
						<td align="left" width="100px"><label class="blue" >仓库名称&nbsp;:</label></td>
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
						<td align="left" width="100px"><label class="blue" >物流商&nbsp;:</label></td>
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
						<td align="left" width="100px"><label class="blue" >产品类型&nbsp;:</label></td>
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
						<td align="left" width="100px"><label class="blue" >省&nbsp;:</label></td>
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
						<td align="left" width="100px"><label class="blue" >市&nbsp;:</label></td>
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
						<td align="left" width="100px"><label class="blue" >区&nbsp;:</label></td>
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
					<tr>
						<td align="left" width="100px"><label class="blue" >时效值&nbsp;:</label></td>
						<td width="200px">
							<input id="ageingValue" name="ageingValue" style="width: 200px;height: 30px;"  oninput='this.value=this.value.replace(/^[0]+[0-9]*$/gi,"")' >
						</td>
						<td align="left" width="100px"><label class="blue" >揽件截至时间&nbsp;:</label></td>
						<td width="200px">
							<input id="embranceCalTime" style="width: 200px;height: 30px;" name="embranceCalTime"  onkeyup="this.value=this.value.replace(/[^\d\:]/g,'')" >
						</td>
						<td align="left" width="100px"></td>
						<td width="200px">
						</td>
					</tr>
				</table>
			</form>
			<div style= "width: 100%" class= "form-actions no-margin-bottom no-padding-bottom no-border-bottom" align= "center" >
				<button class= "btn btn-info" type= "button" onclick= "submit();" >
					<i class= "icon-ok bigger-110" ></i>提交
				</button>
				&nbsp;&nbsp;&nbsp;
				<button class= "btn" type= "reset" onclick= "back();" >
					<i class= "icon-undo bigger-110" ></i>返回
				</button>
			</div>	
			
			</div>
		</div>
	</div>
</body>
</html>
