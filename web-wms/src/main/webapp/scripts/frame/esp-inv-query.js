var IS_INBOUND_INVOICE = "";
var INVOICE_DIV_ID = "";
var IS_INVOICE_CODE = "0";
var INVOICE_ERROR = "";
var PO_CODE="";

function getOPInvoiceCode(){
	return IS_INVOICE_CODE == 1;
}

function invoiceInfoInit(isii,invoiceDiv,po){
	IS_INBOUND_INVOICE = isii;
	INVOICE_DIV_ID = invoiceDiv;
	IS_INVOICE_CODE = "0";
	PO_CODE = po;
	if(IS_INBOUND_INVOICE == 1){
		initInvoicePOtype();
		$j("#"+INVOICE_DIV_ID).removeClass("hidden");
		$j("#"+INVOICE_DIV_ID+"_invoiceNumber").val("");
		$j("#"+INVOICE_DIV_ID+"_select2TD").addClass("hidden");
		$j("#"+INVOICE_DIV_ID+"_selectInv").html('<option value="">请选择发票号</option>');
		var pl = loxia.syncXhrPost($j("body").attr("contextpath") + "/findPoTypeByPo.json",{"po":po});
		if(pl){
			if(pl.result == "error"){
				jumbo.showMsg(pl.msg);
			}else if(pl.TYPE == '5'){
				$j("#"+INVOICE_DIV_ID+"_select2TD").removeClass("hidden");
				if(pl.invData.length > 0 ){
					for(var i in pl.invData){
						$j('<option value="'+pl.invData[i].invoiceNum+'" dutyPercentage="'+pl.invData[i].dutyPercentage+'" miscFeePercentage="'+pl.invData[i].miscFeePercentage+'">'+pl.invData[i].invoiceNum+'</option>').appendTo($j("#"+INVOICE_DIV_ID+"_selectInv"));
					}
					$j("#"+INVOICE_DIV_ID+"_selectInv").change(selectChanager);
				}else{
					$j("#"+INVOICE_DIV_ID+"_selectInv").addClass("hidden");
					$j("#invoice_invoiceNumber").removeAttr("readonly");
				}
			} else if(pl.invData){
				$j("#"+INVOICE_DIV_ID+"_invoiceNumber").val(pl.invData.invoiceNumber);
				$j("#"+INVOICE_DIV_ID+"_dutyPercentage").val(pl.invData.dutyPercentage);
				$j("#"+INVOICE_DIV_ID+"_miscFeePercentage").val(pl.invData.miscFeePercentage);
				IS_INVOICE_CODE = "1";
			} 
		} else {
			INVOICE_ERROR = "获取发票号数据出错!";
			jumbo.showMsg(INVOICE_ERROR);
		}
	} else {
		$j("#"+INVOICE_DIV_ID).addClass("hidden");
	}
}

function selectChanager(){
	var val=$j("#"+INVOICE_DIV_ID+"_selectInv").val();
	if(val != ""){
		var dutyPercentage = $j("#"+INVOICE_DIV_ID+"_selectInv").find("option[value="+val+"]").attr("dutyPercentage");
		var miscFeePercentage = $j("#"+INVOICE_DIV_ID+"_selectInv").find("option[value="+val+"]").attr("miscFeePercentage");
		$j("#"+INVOICE_DIV_ID+"_invoiceNumber").val(val);
		$j("#"+INVOICE_DIV_ID+"_dutyPercentage").val(dutyPercentage);
		$j("#"+INVOICE_DIV_ID+"_miscFeePercentage").val(miscFeePercentage);
		IS_INVOICE_CODE = "1";
	} else {
		$j("#"+INVOICE_DIV_ID+"_invoiceNumber").val("");
		initInvoicePOtype();
	}
}


function initInvoicePOtype(){
	$j("#"+INVOICE_DIV_ID+"_dutyPercentage").val("");
	$j("#"+INVOICE_DIV_ID+"_miscFeePercentage").val("");
	IS_INVOICE_CODE = "0";
}

function invoiceNumberValue(key){
	var val = $j("#"+INVOICE_DIV_ID+"_invoiceNumber").val();
	var po = PO_CODE;
	if(po.substr(0,2) == '54'){
		if(val.substr(0,2) == "WN"){
			loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/findInvoiceNum.json",
				{"ipCommand.invoiceNum":val},
				{
					success:function (data) {
					    if(data&&data.msg){
					    	jumbo.showMsg(data.msg);	
					    }else{
					    	if(data == null){
					    		jumbo.showMsg("此发票号对应的系数没有维护，请先维护");	
					    		initInvoicePOtype();
					    	}else{
					    		$j("#"+INVOICE_DIV_ID+"_dutyPercentage").val(data.dutyPercentage);
					    		$j("#"+INVOICE_DIV_ID+"_miscFeePercentage").val(data.miscFeePercentage);
					    		IS_INVOICE_CODE = "1";
					    	}
					    }
					   }, 
						error:function(data){
						jumbo.showMsg("此发票号对应的系数没有维护，请先维护");	
						initInvoicePOtype();
		               }
				}); 
		} else {
			jumbo.showMsg("发票号不正确！");	
			initInvoicePOtype();
		}
	} else {
		jumbo.showMsg("发票号不正确！");	
		initInvoicePOtype();
	}
}
