<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<%@ include file="yuriy.jsp" %>
	<!-- css -->
		<link rel="stylesheet" href="<%=basePath %>css/ui-dialog.css">
		<link rel="stylesheet" href="<%=basePath %>assets/css/font-awesome.min.css" />
	    <link rel="stylesheet" href="<%=basePath %>assets/css/ace-fonts.css" />
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace-skins.min.css" />	
		<link rel="stylesheet" href="<%=basePath %>/css/button.css" />	
        <link rel="stylesheet" href="<%=basePath %>/css/jquery.mobile-1.3.2.min.css">
        <link rel="stylesheet" href="<%=basePath %>/css/bootstrap.min.css">
        <link type= "text/css" href= "<%=basePath %>css/table.css" rel= "stylesheet" />	
        <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="<%=basePath %>css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
        <link rel="stylesheet" href="<%=basePath %>css/new_css.css">
        
		<!-- js -->
		<script src="<%=basePath %>js/jquery-1.8.3.min.js"></script>
		<script src="<%=basePath %>js/jquery.mobile-1.3.2.min.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=basePath %>js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
		<script src="<%=basePath %>js/dialog.js"></script>
		<script src="<%=basePath %>js/dialog-min.js"></script>
		<script type= "text/javascript" src="<%=basePath %>js/roof.js" ></script>
	<style>
	 .fixed_div{
            position:fixed;
            left:0px;
            bottom:0px;
            width:100%;
            height:40px;
        }
	</style>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>宝尊电商-登记</title>

		<script>
		$(document).ready(function(){ 
			pageJump("deault");
			$("#end_time").val(getNowFormatDate());
			$("#start_time").val(getNowFormatDate("yyyy-mm-dd") +" 00:00");
		});

		function getNowFormatDate(type) {
			var currentdate="";
		    var date = new Date();
		    var seperator1 = "-";
		    var seperator2 = ":";
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    var min=date.getMinutes();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		    if (min >= 0 && min <= 9) {
		    	min = "0" + min;
		    }
		    if(type=="yyyy-mm-dd"){
                 currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
			}else{
		         currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+ " " + date.getHours() + seperator2 + min;
			}
		    return currentdate;
		}

		
			function out(data){
		    	$.ajax({
					type: "POST",  
					url: root+"/control/mainController/updateSign.do?data="+data,  
					//我们用text格式接收  
					//dataType: "text",   
					//json格式接收数据  
					dataType: "json",  
					data: null,
					success: function (data) { 
						alert(data.msg);
						window.location.href="${root}control/mainController/detailed_list.do";
					}  
				}); 
			}
			function address() {
				window.location.href="${root}control/addressControl/addressList.do";
			}
			function sign() {
				window.location.href="${root}control/mainController/sign.do";
			}
			function puch() {
				window.location.href="${root}control/mainController/sign_out.do?data="+$("#fkid").val();
			}
			function sign_out() {
				window.location.href="${root}control/mainController/sign_out.do";
			}
			function detailed_list() {
				window.location.href="${root}control/mainController/detailed_list.do";
			}
			function main_list() {
				window.location.href="${root}control/mainController/main_list.do";
			}
			function out_login() {
				window.location.href="${root}control/userController/outLogin.do";
			}
		</script>
	</head>

	<body onclick="">

			  
		<div data-role="page" id="pageone" style="vertical-align:middle;" align="center">
			<div data-role="header" data-theme="b" >
				<div style="height: 60px;">
					<div style="display: inline;float: left;"><h3>&nbsp;&nbsp;[Visitor information management system]访客信息管理系统</h3></div>
					<div style="display: inline;float: right;margin-right: 10px;"><img alt="log" src="${root}img/baozun.png" style="height: 60px;width: 160px;"></div>
					<div style="display: inline;float: right;margin-right: 10px;margin-top: 25px;">欢迎,${session_user.name}｜<a href="javascript:void();" onclick="out_login();">[退出]</a></div>
				</div>
			</div>
<div style="float:left; width:98%;">	
 <div style="float: left;width:100%;">		
	<form class="form-search" id="searchForm">
	  <table style="width:100%;">
	     <tr>
	        <td width="8%" align="center">访客名称:</td>
	        <td width="12%" align="left"><input type="text" autofocus="autofocus" name="visitor_name" id="visitor_name" value="${queryParam.visitor_name}" class="span2 search-query"></td>
	        <td width="8%" align="center">访客电话:</td>
	        <td width="12%" align="left"><input type="text" name="visitor_phone" id="visitor_phone" value="${queryParam.visitor_phone}" class="span2 search-query"></td>
	        <td width="8%" align="center">到访时间:</td>
	        <td width="40%" align="left">
				<div class="input-append date form_datetime" data-date="" style="margin-top:20px;float: left;">
					 <input type="text" value="" readonly id="start_time">
					   <div style="position:relative;top:-33px;left:80%;">
						    <span class="add-on"><i class="icon-remove"></i></span>
						    <span class="add-on"><i class="icon-calendar"></i></span>					    
					   </div>
				</div>  
				<div style="margin-top:35px;float: left;">--</div>
				<div class="input-append date form_datetime" data-date="" style="margin-top:20px;float: left;">
					 <input type="text" value="" readonly id="end_time">
					   <div style="position:relative;top:-33px;left:80%;">
						    <span class="add-on"><i class="icon-remove"></i></span>
						    <span class="add-on"><i class="icon-calendar"></i></span>					    
					   </div>
				</div> 
			</td> 				  
	     </tr>
	     <tr>
	        <td width="10%" align="center">拜访对象:</td>
	        <td width="20%" align="left"><input type="text" name="host" id="host" value="${queryParam.host}" class="span2 search-query"></td>
	        <td width="10%" align="center">访客车牌:</td>
	        <td width="20%" align="left"><input type="text" name="license_plate_number" id="license_plate_number" value="${queryParam.license_plate_number}" class="span2 search-query"></td>
	        <td width="10%"><input type="hidden" id="addressName" value="${addressName}" /> <!-- 根据登陆地址名称进行分流老模板新模板 --></td>
	        <td width="20%"></td>
	     </tr>  
	     <tr>
	     	<td width="10%" align="center" height="90px;">访客类型:</td>
	        <td width="20%" align="left" height="90px;">
	        	<select name="visitorType" id="visitorType" class="span2 search-query">
		        	<option value="">全部</option>
		        	<option value="01">个人</option>
		        	<option value="02">团体</option>
		        </select>
	        </td>
	        <td width="10%" align="center" height="90px;">到访地点:</td>
	        <td width="20%" align="left" height="90px;">
		        <select name="checkInPlcae" id="checkInPlcae" class="span2 search-query">
		        	<option value="">全部</option>
		        </select>
	        </td>
	        <td width="10%" height="90px;"></td>
	        <td width="20%" height="90px;"><div style="width:30%;float: left;"><button type="button" onclick="pageJump('')">搜索</button></div><div style="width:30%;float:left;margin-left: 20px;"><button type="button" onclick="excel()">导出</button></div></td>
	     </tr> 
	  </table>
	</form>
  </div>
     <tableContent></tableContent>
</div>
		
	   <div data-role="footer"  data-theme="b" style="position:absolute;bottom: 0px;width: 100%;">
			    <div data-role="navbar" data-iconpos="left">
			      <ul>
			        <li><a href="javascript.void();" style="text-decoration: none;" onclick="main_list();" data-icon="home">主&nbsp;&nbsp;&nbsp;&nbsp;页</a></li>
			       	<li><a href="javascript.void();" style="text-decoration: none;" onclick="address()" data-icon="check">地&nbsp;&nbsp;&nbsp;&nbsp;址</a></li>
			        <li><a href="javascript.void();" style="text-decoration: none;" onclick="sign();" data-icon="check">签&nbsp;&nbsp;&nbsp;&nbsp;入</a></li>
			        <li><a href="javascript.void();" style="text-decoration: none;" onclick="sign_out();" data-icon="info">签&nbsp;&nbsp;&nbsp;&nbsp;出</a></li>
			      </ul>
			    </div>
			</div>
			
			<div id="loading" class></div>
			<div id="loading2">
				<div id="loading_mask" style="margin-left: 95px;">
				<div>
					<span style="font-size: 20px; font-family: 微软雅黑;">请选择打印模板:</span>
					<select id="module">
						<option value="1">老模板</option>
						<option value="2">新模板</option>
					</select>
				</div>
				<div class="loading_mask_btnDiv">
					<button type="button" onclick="chooseModule();" data-theme="b" tabindex="2">确定</button> 
					<button type="button" onclick="closeRoof()" data-theme="b" tabindex="2">关闭</button> 
				</div>
				</div>
			</div>	
	</div>
	</body>
	
	<script type="text/javascript">
	$(function(){//获取登录地点和名称
		$.ajax({
			url : "${root}control/addressControl/findAllInfor.do",
			type : "POST",
			dataType : "json",
			success : function(data) {
				$("#checkInPlcae option:gt(0)").remove();
				var addressInfor = data.addressInforLists;
				for (var i = 0; i < addressInfor.length; i++) {
					var addressName = addressInfor[i].addressName;
					var addressCode = addressInfor[i].addressCode;
					if(addressCode != "" && addressName != ""){
						$("#checkInPlcae").append("<option value='"+ addressCode + "'>" + addressName + "——" + addressCode +"</option>")
					}
				}
			}
	});
});
	/** 调用模板的方法 **/
	/* function callModule(data) {
		$("#visitorData").val(data);
		roof();
	} */
	
	/** 选择模板后点击确定后的方法  **/
	function callModule(data) {
		var addressName = $("#addressName").val();
		showModal01("确认打印吗？",function(){
			if(addressName == '智汇园'){ //新打印模板接口
				/* closeRoof(); */
				$.ajax({
					type : "POST",
					url : "${root}control/mainController/toNewPrinter.do",  
					data : {"visitorData" : data},
					dataType: "JSON",  
					success : function(data) {
						if(data.msg == 'success'){
							showModal("打印成功！");
						}else{
							showModal("打印失败！");
						}
					}
				});
			}else{ //老打印模板接口
				/* closeRoof(); */
				window.open("${root}control/mainController/toPrintPage.do?data="+data, "windowName", "height=600, width=600, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
			}
		});
	}
		
	
  	function pageJump(falg) {
  		var date1 = $("#start_time").val();
		var date2 = $("#end_time").val();
		var date01 = new Date(date1.replace(/\-/gi, "/")).getTime();
		var date02 = new Date(date2.replace(/\-/gi, "/")).getTime();
		if (date01 >= date02) {
			showModal("结束时间应大于开始时间！");
			return;
	}
        var visitor_name=$("#visitor_name").val();
        var visitor_phone=$("#visitor_phone").val();
        var start_time=$("#start_time").val();
        var end_time=$("#end_time").val();
        var host=$("#host").val();
        var license_plate_number=$("#license_plate_number").val();
        var visitorType = $("#visitorType option:selected").val();
        var checkInPlcae = $("#checkInPlcae option:selected").val();
  	  	if($("#startRow").val()!=undefined){
  	  	    var pageIndex = $("#pageIndex").val();
  	  	    if(falg == ''){
  	  	    	 pageIndex = 1;
  	  	    }
  	  		url='${root}control/mainController/table_list.do?startRow='+$("#startRow").val()+'&endRow='+$("#endRow").val()+"&page="+pageIndex+"&pageSize="+$("#pageSize").val();
  	  	  }else{
  		    url='${root}control/mainController/table_list.do?default_time='+"ddd";
  	  	 }
        var data="&visitorType="+visitorType+"&checkInPlcae="+checkInPlcae+"&visitor_name="+visitor_name+"&visitor_phone="+visitor_phone+"&start_time="+start_time+"&end_time="+end_time+"&host="+host+"&license_plate_number="+license_plate_number+"&falg="+falg;
        	$.ajax({
        		type : "POST",
        		url: url,  
        		data:data,
        		dataType: "html",  
        		success : function(jsonStr) {
	           		$("tableContent").html(jsonStr);
        		}
        	});
        	
 	}

 	
 	 $('.form_datetime').datetimepicker({
        language:'zh-CN',
        weekStart: 1,
        todayBtn:  1,
  		autoclose: 1,
  		todayHighlight:1,
  		startView: 2,
  		forceParse:0,
  		minuteStep: 1,
         showMeridian: 1
      });

	  	
		function excel(){
	        var visitor_name=$("#visitor_name").val();
	        var visitor_phone=$("#visitor_phone").val();
	        var start_time=$("#start_time").val();
	        var end_time=$("#end_time").val();
	        var host=$("#host").val();
	        var license_plate_number=$("#license_plate_number").val();
	        var visitorType = $("#visitorType option:selected").val();
	        var checkInPlcae = $("#checkInPlcae option:selected").val();
	        var data="&visitorType="+visitorType+"&checkInPlcae="+checkInPlcae+"&visitor_name="+visitor_name+"&visitor_phone="+visitor_phone+"&start_time="+start_time+"&end_time="+end_time+"&host="+host+"&license_plate_number="+license_plate_number;
			var url=root+'/control/mainController/excel.do';
			var htm_td="";
			   $.ajax({
					type : "POST",
					url: url,  
					data:data,
					dataType: "",  
					success : function(jsonStr) {
						window.open(root+jsonStr);
					}
				});
			}	
		function showModal(content) {
	  		var d = dialog({
	  			title : '提示',
	  			content : content,
	  			lock : true,
	  			ok : function () {}
	  		});
	  		d.showModal();
	  	}
		function showModal01(content,ofunc) {
	  		if(ofunc == null || ofunc == ""){
	  			var ofunc = function(){};
	  		}
	  		
	  		var d = dialog({
	  			title : '提示',
	  			content : content,
	  			lock : true,
	  			ok : ofunc,
	  			cancel : function () {}
	  		});
	  		d.showModal();
	  	}
	</script>
</html>