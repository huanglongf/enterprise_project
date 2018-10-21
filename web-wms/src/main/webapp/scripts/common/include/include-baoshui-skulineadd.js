var INCLUDE_SEL_ID = "";
var INCLUDE_SEL_VAL_CODE = "";
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(valCode == 'innerShopCode'){
		INCLUDE_SEL_VAL_CODE = 'code'
	}
}

$j(document).ready(function (){
	$j("#shopQueryDialog2").dialog({title: "线下包裹称重", modal:true, autoOpen: false, width: 600,closeOnEscape: false,open: function(event, ui){$j(".ui-dialog-titlebar-close").hide();}});
	restart();
	
	$j("#btnShopQueryClose2").click(function(){
		location.replace(location.href);
	});
	
	$j("#restWeight").click(function(){//点击重启称
		restart();
	});
	
	$j("#transNo").keydown(function(evt) {//运单号转耗材
		if (evt.keyCode === 13) {
			 evt.preventDefault();
	         var transNo=$j("#transNo").val();
	         var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getOneTransPackage2.do",{"transNo":transNo});
	            if(rs["brand"]=="yes"){
	            	$j("#skuId").focus();
	            }else{
	                jumbo.showMsg("运单号已存在");
	                $j("#transNo").val('');
	                $j("#transNo").focus();
	            }
			
		}
	});
	
	 $j("#skuId").keydown(function(evt) {//扫描转手工重量
			if (evt.keyCode === 13) {
				 evt.preventDefault();
		         var skuId=$j("#skuId").val();
		         var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getVolumnByBarCode.do",{"skuId":skuId});
		            if(rs["brand"]=="1"){
		            	 jumbo.showMsg(rs["tip"]);
		            	 $j("#skuId").val('');
			             $j("#skuId").focus();
		            }else{
		            	 $j("#volume").val(rs["volumn"]);
		            	$j("#packageWeight").focus();
		            }
			}
		});
	
		$j("#packageWeight").keydown(function(evt) {//手工重量转OK
			if (evt.keyCode === 13) {
				$j("#okSubmit2").focus();
			}
		});

		

	$j("#okSubmit2").keydown(function(event){
		var value = $j("#okSubmit2").val();
		if(event.keyCode == 13){
			if (value == BARCODE_CONFIRM){
				submit2();
			}else if(value != "" ) {
				 jumbo.showMsg("输入的确认条码不正确，请重新输入.");return;
			}else if(value == "" ) {
				 jumbo.showMsg("请输入确认条码.");return;
			}
		}
	});
	
	
	
	$j("#printDeliveryInfo").click(function(){//打印物流电子面单
		if('1' == isPostpositionExpressBill && '0' == isPostpositionPackingPage) return;
	    loxia.lockPage();
		var staId = $j("#showDetail").attr("staId");
		var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + staId;
		printBZ(loxia.encodeUrl(url),true);					
	    loxia.unlockPage();
});
	
	$j("#zhiSubmit").click(function(){//提交纸质面单
		submit2();
	})
	
	
	
});

function submit2(){}

//打印电子面单
 function surfaceprint(id){
 	loxia.lockPage();
// 	jumbo.showMsg("打印中，请等待...");
 	var url = $j("body").attr("contextpath") + "/printSingleOrderDetailOutMode12.json?id=" + id;
 	printBZ(loxia.encodeUrl(url),true);
// 	console.log("打印中，请等待...");
 	loxia.unlockPage();
 }