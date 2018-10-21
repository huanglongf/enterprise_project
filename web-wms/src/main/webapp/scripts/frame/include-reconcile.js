$j.extend(loxia.regional['zh-CN'],{
	TYPE_NULL:"请选费用类型",
	ADDMSG_NULL:"费用明细不能为空",
	ADDMSG_LENGTH:"费用明细不能大于26个字符",
	ADDTOTAL_NULL:"金额不能为空"
});

function deleteRow(obj,lid,total,type){
	var postData = {'lineId':lid};
	loxia.syncXhr(baseUrl + "/delReconcilementById.json",postData);
	if(type==4)
	{
		var apply=$j.trim($j("#applycount").html());
		$j("#applycount").text(parseInt(apply)-parseInt(total));
	}
	else if(type==5)
	{
		var bzcount=$j.trim($j("#bzcount").html())
		$j("#bzcount").text(parseInt(bzcount)-parseInt(total));
	}
	$j(obj).parent().parent().remove();
}

function tableAddRow(tbl,msg,total,url,lid,type){
	var html = '<tr><td align="center">' + msg
		+ '</td><td align="right">' + total + '</td><td align="center"><a href="' + url
		+ '">下载</a></td>' 
		+ '<td align="center"><button name="btnDelete" loxiaType="button" onclick="deleteRow(this,'+lid+','+total+','+type+')" class="confirm">删除</button></td></tr>';
	$j(html).appendTo(tbl);
	loxia.initContext(tbl);
}

function tableAddRow2(tbl,msg,total,url,lid,type){
	var html = '<tr><td align="center">' + msg
		+ '</td><td align="right">' + total + '</td><td align="center"><a href="' + url
		+ '">下载</a></td><td align="center"></td></tr>';
	$j(html).appendTo(tbl);
	loxia.initContext(tbl);
}

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
var baseUrl = $j("body").attr("contextpath")+"/json";

function showOrderList()
{
	var val = $j.trim($j("#detialId").val());
	var postData = {'rid':val};
	var data=loxia.syncXhr(baseUrl + "/findOrderSumByRid.json",postData);
	if(data)
	{
		$j("#total_actual").text(data.ordersum.totalActual);
		$j("#alipay_cost").text(data.ordersum.alipayCost);
		$j("#tb_cost").text(data.ordersum.tbCost);
		$j("#score_cost").text(data.ordersum.scoreCost);
		$j("#actual_transfer_fee").text(data.ordersum.actualTransferFee);
		$j("#offl_return_cost").text(data.ordersum.offlReturnCost);
		$j("#credit_card_cost").text(data.ordersum.creditCardCost);
		$j("#other_cost").text(data.ordersum.otherCost);
		$j("#brokerageCost").text(data.ordersum.brokerageCost);
		$j("#presentCost").text(data.ordersum.presentCost);
		$j("#lotteryTicketCost").text(data.ordersum.lotteryTicketCost);
		$j("#olReturnCost").text(data.ordersum.olReturnCost);
		$j("#earnestCost").text(data.ordersum.earnestCost);
		$j("#b2cOnline").text(data.ordersum.b2cOnline);
	}

	
}
$j(document).ready(function (){
	$j("#dialog_add").dialog({title: "添加新费用项", modal:true, autoOpen: false, width: 400});
	$j("#dialogAddClose").click(function(){
		$j("#dialog_add").dialog("close");
	});
	
	$j("#btnAdd").click(function(){
		$j("#dialog_add").dialog("open");
		$j("#dialog_add input,#dialog_add select").val("");
		
	});
	
$j("#dialogBtnAdd").click(function(){
	
	var type = $j("#selAddType").val();
	var addMsg=$j.trim($j("#addMsg").val());
	var addTotal=$j.trim($j("#addTotal").val());
	if(type != "4"&&type != "5"){
		jumbo.showMsg(i18("TYPE_NULL"));
		loxia.unlockPage();
		return;
	}
	if(addMsg== ""){
		jumbo.showMsg(i18("ADDMSG_NULL"));
		loxia.unlockPage();
		return;
	}
	if(addMsg.length>26){
		jumbo.showMsg(i18("ADDMSG_LENGTH"));
		loxia.unlockPage();
		return;
	}
	if(addTotal== ""){
		jumbo.showMsg(i18("ADDTOTAL_NULL"));
		loxia.unlockPage();
		return;
	}
	$j("#dialog_add").dialog("close");
	var filename = $j("#tnFile").val();
	var val = $j.trim($j("#detialId").val());
	$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/finance/addreconcileLine.do?rid="+val+"&&reconcileline.fileName="+filename));
	loxia.submitForm("importForm");
});
});
function addReconcileLine()
{
	
	 var lid = $j.trim($j("#detialId").val());
	 var val = $j.trim($j("#ishidden").val());
	 var linlist=loxia.syncXhr(baseUrl + "/findReconcileList.json",{"rid":lid});
	 var bzlist=loxia.syncXhr(baseUrl + "/findReconcileCompanyList.json",{"rid":lid});
	 
	 var url=$j("body").attr("contextpath") + "/finance/exportReconcileList.do?rid="+lid; 
	 
		if(linlist)
		{
			var sysfee=linlist.sysfee==undefined?0:linlist.sysfee;
			tableAddRow2($j("#tblApply"),"订单金额总计",sysfee,url,0,4)
			var apply=0;
			if(linlist.resultList)
			{
			var a=linlist.resultList;
			apply=apply+sysfee;
			if(a.length<=0)
			{
				$j("#applycount").text(apply);
			}
		
			for(var i=0;i<a.length;i++)
			{
				var obj=a[i];
				apply=apply+obj.fee;
				$j("#applycount").text(apply);
				var url=$j("body").attr("contextpath") + "/finance/downloadFile.do?lineId="+obj.id;
				
				if(val==1)
				{
				tableAddRow2($j("#tblApply"),obj.explain,obj.fee,url,obj.id,4);
				}
				else
				{
				tableAddRow($j("#tblApply"),obj.explain,obj.fee,url,obj.id,4);
				}
				
			}
			}
		}
		if(bzlist)
		{
			var bz=0;
			var b=bzlist.resultList;
			for(var j=0;j<b.length;j++)
			{
				var objbz=b[j];
				bz=bz+objbz.fee;
				$j("#bzcount").text(bz);
				var url=$j("body").attr("contextpath") + "/finance/downloadFile.do?lineId="+objbz.id;
				if(val==1)
				{
				tableAddRow2($j("#tblBZ"),objbz.explain,objbz.fee,url,objbz.id,5);
				}else
				{
					tableAddRow($j("#tblBZ"),objbz.explain,objbz.fee,url,objbz.id,5);
				}
			}
		}
}