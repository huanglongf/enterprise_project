<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>


<script type="text/javascript">
$j(document).ready(function (){
		window.parent.loxia.unlockPage();
	    if($j("#msg").val()=="success"){
		window.parent.clear();
		window.parent.jumbo.showMsg("导入成功");
		
		var invcheckid = ${invcheckid == null? 'null' : invcheckid};
		var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckStatus"});
		//if(rs && rs.result == 'success'){
		if(invcheckid && invcheckid != null){
			window.parent.setInvcheckid(invcheckid);
			
			//$j("#invcheckid").val(invcheckid);
			if(invcheckid != ''){
				var postData = {};
				postData["invcheckid"] = invcheckid; 
				var url = $j("body").attr("contextpath") + "/findinvchecklinedetial.json";
				window.parent.$j("#tbl_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(url),page:1,postData:postData}).trigger("reloadGrid");
				var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/findinvcheckinfobyid.json?invcheckid=" + invcheckid);
				var value=intStatus.value,map={};
				if(value&&value.length>0){
					var array=value.split(";");
					$j.each(array,function(i,e){
						if(e.length>0){
							var each=e.split(":");
							map[each[0]]=each[1];
						}
					});
				}
				if(result && result.invcheck){
					window.parent.callback(result.invcheck, map[result.invcheck.intStatus]);
					//window.parent.jumbo.showMsg(i18("SUCCESS")); // 操作成功
				}
			}
		}else {
			window.parent.jumbo.showMsg("invcheckid is null");
		}
    }else{
    	window.parent.setInvcheckid("");
    	window.parent.jumbo.showMsg("导入失败  <br/>" + $j("#msg").val(), 5000, "info", window.parent.parent);
    }  
}); 
</script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<input type="hidden" name="msg" value="<s:property value="#request.msg"/>" id="msg" />
</body>
</html>