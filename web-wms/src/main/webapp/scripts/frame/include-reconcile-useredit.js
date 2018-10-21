$j.extend(loxia.regional['zh-CN'],{
	DATA_DETAIL_SELECT:"请选择具体的单据信息",
	DEl_ERROR:"移除订单操作失败",
	DEl_SUCCESS:"订单移除成功"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
var baseUrl = $j("body").attr("contextpath")+"/json";

//日期
function getDate(date){
	var s = new Date(date.substring(0,10));
	return s;
}
//验证
function checkDate(){
	var sDate = $j("#stardate").val();
	var eDate = $j("#enddate").val();
	if(sDate!="" && eDate!=""){
		if (getDate(sDate)>getDate(eDate)){
		jumbo.showMsg("起始时间必须小于结束时间！");//
		return false;
	}}else if(sDate!="" && eDate==""){
		var pDate = $j("#preEndDate").val().replace(new RegExp("/","gm"),"-");
			if (getDate(sDate)>getDate(pDate)){
			jumbo.showMsg("起始时间必须小于上一账期结束时间("+pDate+")");//
			return false;
		}
	}
	/*if($j("#stardate").val()==""){
		jumbo.showMsg("开始输入不能为空");
		return false;
	}*/
	/*if($j("#enddate").val()==""){
		jumbo.showMsg("结束日期输入不能为空");
		return false;
	}*/
	return true;
}


$j(document).ready(function (){
	
	var baseUrl = $j("body").attr("contextpath")+"/json";
	$j("#dialog_user_edit").dialog({title: "自定义编辑", modal:true, autoOpen: false, width: 920});

	$j("button[name='btnDelete']").click(function(){
		deleteRow(this);
	});
	
	// btnQuery 查询
	$j("#btnQuery").click(function(){
		if(!checkDate()){
		   return;	
		}
		var postData = {};
		var del = $j("#ifDel").val();
		if(del=='1' || del=='3'){
			//控制结束日期小于上一个对账结束日期
			if($j("#stardate").val()!=""){
				 postData["saleordercomm.starteDate"]=$j("#stardate").val();
			}
			if($j("#enddate").val()==""){
				 postData["saleordercomm.endDate"]=$j("#preEndDate").val().replace(new RegExp("/","gm"),"-");
			}else{
				
		/*		if (getDate($j("#enddate").val())>getDate(pDate)){
					jumbo.showMsg("结束日期("+$j("#enddate").val()+")不能大于上一个对账结束日期("+pDate+")!");
				   return;
				}*/
				postData["saleordercomm.endDate"]=$j("#enddate").val();
			}
			postData["saleordercomm.code"]=$j("#code").val();
			postData["saleordercomm.billsNo"]=$j("#tb_code").val();
			postData["saleordercomm.productNo"]=$j("#sku_code").val();
			postData["saleordercomm.vendorNo"]=$j("#supply_code").val();
			postData["rid"]=$j("#detialId").val();	
			postData["rouid"]=$j("#rouid").val();
			
		 $j("#tbl_so_edit").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findReconcileOrderListByRid.json"),postData:postData,page:1}).trigger("reloadGrid");
		}
		else if(del=='2')
	 {
			var val = $j.trim($j("#detialId").val());
			var Data = {};
			Data["saleordercomm.starteDate"]=$j("#stardate").val();
			Data["saleordercomm.endDate"]=$j("#enddate").val();
			Data["saleordercomm.code"]=$j("#code").val();
			Data["saleordercomm.billsNo"]=$j("#tb_code").val();
			Data["saleordercomm.productNo"]=$j("#sku_code").val();
			Data["saleordercomm.vendorNo"]=$j("#supply_code").val();
			Data["rouid"]=$j("#rouid").val();
			Data["rid"]=val;
            var urlx=window.parent.$j("body").attr("contextpath")+"/findReconcileOrderListByRid.json";
			$j("#tbl_so_edit").jqGrid('setGridParam',{url:urlx,postData:Data}).trigger("reloadGrid");
	 }
	});
	
	//删除选中订单
	$j("#delOrderref").click(function(){

				 var ids = jQuery("#tbl_so_edit").jqGrid('getGridParam','selarrrow');
			       if(ids.length==0){
			    	 jumbo.showMsg(loxia.getLocaleMsg("DATA_DETAIL_SELECT"));// 请选择具体数据！！
			    	 return
			     }
			       var orderlist;
			       for(var i=0;i<ids.length;i++)
			    	   {
			    	     if(orderlist)
			    	    	 {
			    	    	 orderlist=orderlist+","+ids[i];
			    	    	 }
			    	     else
			    	    	{
			    	    	 orderlist=ids[i];
			    	    	}
			    	   }
			       var postData = {};
			   	postData["ridList"] = orderlist;
			  var data = loxia.syncXhr(baseUrl + "/delReconcileOrderRefByid.json",postData);
			  
			  if(data){
					if(data.result=="success"){
						jumbo.showMsg(i18("DEl_SUCCESS"));
						 var val = $j.trim($j("#detialId").val());
						 $j.trim($j("#detialId").val());
						 var urlx=window.parent.$j("body").attr("contextpath")+"/findReconcileOrderListByRid.json";
					     var Data = {};
					     Data['rid']=val;
					     Data["rouid"]=$j("#rouid").val();
					     var del = $j("#ifDel").val();
					     if (del=='1'){
					    	 $j("#tbl_so_edit").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findReconcileOrderListByRid.json"),postData:Data,page:1}).trigger("reloadGrid");
					      }else{
					    	  	//$j("#tbl_so").jqGrid('setGridParam',{url:urlx,postData:Data}).trigger("reloadGrid");
					            $j("#tbl_so_edit").jqGrid('setGridParam',{url:urlx,postData:Data}).trigger("reloadGrid");
					      }	
					}else if(data.result=="error"){
						jumbo.showMsg(i18("DEl_ERROR"));
			        	}
				}
			  else
				  {
				  jumbo.showMsg(i18("DEl_ERROR"));
				  }
	        });
	
	
	$j("#btnClose").click(function(){
		$j("#dialog_user_edit").dialog("close");
		 var del = $j("#ifDel").val();
		 var postData = {};
	 		postData["rid"] = $j.trim($j("#detialId").val());
	     if (del=='1'){
	 		$j("#tbl_so_view").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findReconcileOrderListByRid.json"),postData:postData,page:1}).trigger("reloadGrid");
	     }else if(del=='2' || del=='3'){
	    	 $j("#tbl_so").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findReconcileOrderListByRid.json"),postData:postData,page:1}).trigger("reloadGrid"); 
	     }
	});  

	$j("#btnEditOrder").click(function(){
		$j("#stardate").val("");
		$j("#enddate").val("");
		$j("#code").val("");
		$j("#tb_code").val("");
		$j("#sku_code").val("");
		$j("#supply_code").val("");
		
		$j("#tbl_so_edit").jqGrid({
			datatype: "json",
			colNames: ["ID","订单号","淘宝订单号","发货时间","收款时间","订单状态","订单销售金额","代收邮费","线下退款","支付宝收入","淘宝佣金","积分返点","信用卡手续费",
			           "淘宝客佣金代扣款","捐赠支出","彩票","线上退款金额","保证金理赔金额","其它手续费","预付款金额","B2CONLINE"],
			colModel: [
	            {name: "id", index: "id", hidden: true},		         
	           {name: "code", index: "code", width: 120, resizable: true},
	           {name: "outerOrderCode", index: "outer_order_code", width: 120, resizable: true},
	           {name: "deliveryTime", index: "delivery_time",  width: 150, resizable: true},
	           {name: "paymentTime", index: "payment_time",  width: 150, resizable: true},
	           {name: "statusName", index: "status", width: 60, resizable: true},
	           {name: "totalActual", index: "total_actual", width: 80, resizable: true},
	           {name: "actualTransferFee", index: "actual_transfer_fee", width: 80, resizable: true},
	           {name: "offlReturnCost", index: "offl_return_cost", width: 80, resizable: true},
	           {name: "alipayCost", index: "alipay_cost", width: 80, resizable: true},
	           {name: "tbCost", index: "tb_cost", width: 80, resizable: true},
	           {name: "scoreCost", index: "score_cost", width: 80, resizable: true},
	           {name: "creditCardCost", index: "credit_card_cost", width: 80, resizable: true},
	           {name: "brokerageCost", index: "brokerage_cost", width: 120, resizable: true},
	           {name: "presentCost", index: "present_cost", width: 80, resizable: true},
	           {name: "lotteryTicketCost", index: "lottery_ticket_cost", width: 80, resizable: true},
	           {name: "olReturnCost", index: "ol_return_cost", width: 80, resizable: true},
	           {name: "earnestCost", index: "earnest_cost", width: 120, resizable: true},
	           {name: "otherCost", index: "other_cost", width: 80, resizable: true},
	           {name: "pcard", index: "pcard", hidden: true, width: 80, resizable: true},
	           {name: "b2cOnline", index: "b2cOnline", width: 80, resizable: true}
	           ],
			caption: "对账订单列表",
			sortname: 'id',
		    multiselect: true,
		    height:"auto",
		    rowNum: jumbo.getPageSize(),
		    rowList:jumbo.getPageSizeList(),
		    pager:"#tbl_so_page",
			sortorder: "desc",
			viewrecords: true,
			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
		$j("#tbl_so_edit tr[id]").remove();
		$j("#dialog_user_edit").dialog("open");
	});

	
});