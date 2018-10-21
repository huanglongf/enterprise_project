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
			function submit(){
				var remark = $("#remark").val();
				var ageingName = $("#ageingName").val();
				var storeCode = $("#store").val();
				if(storeCode=="" ||storeCode==null ||storeCode==undefined){
					alert("店铺不能为空");
					return false;
				}
				if(ageingName=="" ||ageingName==null ||ageingName==undefined){
					alert("时效名称不能为空");
					return false;
				}
				$.ajax({
					url : root + "control/radar/ageingMasterController/submitAgeingMaster.do",	
					type : "post",
			       	data : {
				   		"remark" :remark,
				   		"storeCode" :storeCode,
				   		"ageingName" :ageingName
			       	},
			       	dataType : "json",
			       	success: function (data) {
						if(data.result_code == 'SUCCESS'){
							openDiv('/BT-LMIS//control/radar/ageingMasterController/query.do');
			    	   	}else if(data.result_code == 'FAILURE'){
			    			alert(data.result_content);
			    			//openDiv('/BT-LMIS//control/radar/ageingMasterController/query.do');
			    	   	}else{
			    			alert("保存异常!");
			    			openDiv('/BT-LMIS//control/radar/ageingMasterController/query.do');
			    	   	}
			       	}  
				});
			}
			function back(){
			    openDiv('/BT-LMIS//control/radar/ageingMasterController/query.do');
			}
		
		
    </script>
	
</head>
<body>
	<div class= "page-header" align= "left" >
		<h1>店铺时效信息创建/编辑</h1>
	</div>
	<div class= "col-xs-12" >
		<div class= "clearfix form-group no-margin-bottom no-padding-bottom no-border-bottom" >
			<div class= "no-margin-bottom no-padding-bottom no-border-bottom" style= "width: 100%;" align= "center" >
				<table style= "width: 100%;" class= "form_table" >
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="express">
								时效名称&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<input id='ageingName' name='ageingName'>
						</td>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="express">
								店铺名称&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<select id="store" name="store" style="width: 100%;" data-edit-select="1">
			  					<option value= "" >---请选择---</option>
								<c:forEach items= "${store }" var= "store" >
									<option value= "${store.storeCode }" ${receiveDeadline.storeCode == store.storeCode? "selected= 'selected'": "" } >
										${store.storeName }
									</option>
								</c:forEach>
							</select>
						</td>
			  			<td class="right" nowrap="nowrap" width="30%"/> 
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="no-padding-right blue" for="product_type">
								备注&nbsp;:
							</label>
						</td>
			  			<td class="left" nowrap="nowrap" width="15%">
			  				<div class="input-group bootstrap-timepicker">
								<input  id='remark' name='remark'/>
							</div>
			  			</td>
					</tr>
					<tr>
						<td width= "25%" />
						<td class= "left" width= "45%" colspan= "3" >
							<div style= "width: 100%" class= "form-actions no-margin-bottom no-padding-bottom no-border-bottom" align= "center" >
								<button class= "btn btn-info" type= "button" onclick= "submit();" >
									<i class= "icon-ok bigger-110" ></i>提交
								</button>
								&nbsp;&nbsp;&nbsp;
								<button class= "btn" type= "reset" onclick= "back();" >
									<i class= "icon-undo bigger-110" ></i>返回
								</button>
							</div>		
						</td>
						<td class="right" nowrap="nowrap" width="30%"/>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
