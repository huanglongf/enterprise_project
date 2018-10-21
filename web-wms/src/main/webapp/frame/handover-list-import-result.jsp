<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<script type="text/javascript">


$j(document).ready(function (){
    loxia.init({debug: true, region: 'zh-CN'});
    var data = ${hoList};
    var removeByTrackingNo = ${removeByTrackingNo == null? 'null' : removeByTrackingNo};
    var removeBylpcodePre = ${removeBylpcodePre == null? 'null' : removeBylpcodePre};
	var removeBylpcode = ${removeBylpcode == null? 'null' : removeBylpcode};
	var removeBySta = ${removeBySta == null? 'null' : removeBySta};
	var packageCount = ${packageCount};
	var totalWeight = ${totalWeight};
	
	$j("#packageCount",window.parent.document).html(packageCount);
	$j("#weight",window.parent.document).html(totalWeight);
	
    $j("#tbl-goodsList tr[id]",window.parent.document).remove();
    var j = 0;
    for(var d in data){
        j++;
    	$j("#tbl-goodsList",window.parent.document).jqGrid('addRowData',data[d].id,data[d]);	
   	}
   	if(j==0){
   	   	$j("#createOrder",window.parent.document).addClass("hidden");
   	}else{
   		$j("#createOrder",window.parent.document).removeClass("hidden");
   	}
	var i = 0;
    $j("#tbl-removeInfo tr[id]",window.parent.document).remove();
    if(removeByTrackingNo != null){
	    for(var d in removeByTrackingNo){
	        i++;
	    	$j("#tbl-removeInfo",window.parent.document).jqGrid('addRowData',i,{"trackingNo":removeByTrackingNo[d],"msg":"快递单号不存在或没有出库或已经创建完交接清单"});	
	   	}
    }
    if(removeBylpcodePre != null){
	    for(var d in removeBylpcodePre){
	        i++;
	    	$j("#tbl-removeInfo",window.parent.document).jqGrid('addRowData',i,{"trackingNo":removeBylpcodePre[d],"msg":"快递单号是预售订单"});	
	   	}
    }
    if(removeBylpcode != null){
	    for(var d in removeBylpcode){
	        i++;
	    	$j("#tbl-removeInfo",window.parent.document).jqGrid('addRowData',i,{"trackingNo":removeBylpcode[d],"msg":"物流交接商与快递单不匹配或作业单已完成"});	
	   	}
    }
    if(removeBySta != null){
	    for(var d in removeBySta){
	        i++;
	    	$j("#tbl-removeInfo",window.parent.document).jqGrid('addRowData',i,{"trackingNo":removeBySta[d],"msg":"作业单不存在或其中非所有快递单被交接"});	
	   	}
    }
});
</script>
</body>
</html>