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
/*	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_shop_query_dialog").jqGrid({
		url:$j("body").attr("contextpath") + "/getbichannelinfo.json",
		datatype: "json",
		colNames: ["id","innerShopCode","渠道编码","渠道名称"],
		colModel: [ {name: "id", width: 100,hidden: true},
		           {name: "code", width: 100,hidden: true},
		           {name: "shopCode", index: "shop_code", width: 120, resizable: true},
		           {name: "name", index: "name", width: 250,  resizable: true}
		           ],
		caption: "店铺列表",
		rowNum: 10,
		rowList:[10,20,40],
	   	sortname: 'id',
	    pager: '#tbl_shop_query_dialog_pager',
	    multiselect: false,
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});*/
	
	restart();
	
	$j("#btnShopQueryClose2").click(function(){
//		window.location.reload();
//		window.parent.location.reload();
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
//	$j("#skuId").keydown(function(evt) {//耗材转手工重量
//		if (evt.keyCode === 13) {
//			$j("#packageWeight").focus();
//		}
//	});
	
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
//	$j("#volume").keydown(function(evt) {//体积转OK
//		if (evt.keyCode === 13) {
//			$j("#okSubmit2").focus();
//		}
//	});

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

function submit2(){
	 var transNo='';
	 var brand='0';//0:保存 1：跟新
	if(document.getElementById("transNo")!=undefined){
		transNo=document.getElementById("transNo").value;//快递单号
	}else{
		brand='1';
	}
	 var skuId=document.getElementById("skuId").value;//库存id
	 var autoWeigth=document.getElementById("autoWeigth").value;//自动重量
	 var packageWeight=document.getElementById("packageWeight").value;//手动重量
	 var volume=document.getElementById("volume").value;//体积
	 var nowNum=document.getElementById("nowNum").value;//当前数量
	 var tNum=document.getElementById("tNum").value;//总共数量
	 var orderId=document.getElementById("orderId").value;//快递订单ID
	 
	 if(packageWeight==''){
		 packageWeight=autoWeigth;
	 }
	 
	 if(document.getElementById("transNo")!=undefined){
			if(transNo==null || transNo==''){
				 $j("#okSubmit2").val('');
				  $j("#transNo").focus();
		          jumbo.showMsg("请填写运单号");return;
			}
		}
	 
	  if(skuId==null ||skuId=='' ){
		  $j("#okSubmit2").val('');
		  $j("#skuId").focus();
          jumbo.showMsg("请扫描耗材");return;
      }
//      if(!/^[0-9]*$/.test(skuId)){
//    	  $j("#skuId").val('');
//    	  $j("#okSubmit2").val('');
//		  $j("#skuId").focus();
//          jumbo.showMsg("耗材,不是一个合法的数字要求不符合要求");return;
//         }
      if(autoWeigth=='' && packageWeight==''){
    	   $j("#okSubmit2").val('');
			 $j("#packageWeight").val('');
			 $j("#packageWeight").focus();
          jumbo.showMsg("请填写重量");return;
      }
	  if(packageWeight=='0' || packageWeight=='0.0' || packageWeight=='0.00' || packageWeight=='0.000'){
			$j("#okSubmit2").val('');
			 $j("#packageWeight").val('');
			 $j("#packageWeight").focus();
		  jumbo.showMsg("重量,不能不为0");return;
	  }
      if(!/^([1-9]\d{0,}|0)(\.\d{1,3})?$/.test(packageWeight)){//验证重量
    	  	$j("#okSubmit2").val('');
			 $j("#packageWeight").val('');
			 $j("#packageWeight").focus();
			 jumbo.showMsg("重量,不是一个合法的数字要求不符合要求");return;
		 }
      if(volume==''){
    	  $j("#okSubmit2").val('');
          jumbo.showMsg("请填写体积");return;
      }
//      if(!/^[0-9]*\.{0,1}[0-9]*$/.test(volume)){
//    	  $j("#okSubmit2").val('');
//          jumbo.showMsg("体积,不是一个合法的数字或精度要求不符合要求");return;
//         }
	 
	 var transPackage = {};
	 transPackage["skuId2"]=skuId;
	 transPackage["transPackage.packageWeight"]=packageWeight;
	 transPackage["transPackage.volume"]=volume;
	 transPackage["transPackage.order.id"]=orderId;
	 transPackage["transPackage.transNo"]=transNo;
	 transPackage["brand"]=brand;
	 var rs= loxia.syncXhrPost($j("body").attr("contextpath") + "/insertTransPackage.json",transPackage);
	 if(rs["msg"]=="success"){
		 $j("#autoWeigth").val("");
		 $j("#packageWeight").val("");
		 $j("#volume").val("");
		 $j("#skuId").val("");
		 $j("#transNo").val("");
		 /////////////打印电子面单///////////////////////
		 if(document.getElementById("transNo")==undefined){
			 surfaceprint(rs["pack"]["id"]);
		 }
		 if(parseInt(tNum)==parseInt(nowNum)){
			 if(document.getElementById("transNo")!=undefined){
				 location.replace(location.href);
			 }else{
//				 console.log("打印完请按确定1..");
				 setTimeout(function () {
					 alert("打印完请按确定");
			     location.replace(location.href);
			     }, 1000);
//			     console.log("打印完请按确定2");
			 }
			 return;
		 }
		 nowNum++;
		 $j("#nowNum").val(nowNum);
		 $j("#yNum").val(tNum+"-"+nowNum);
		 $j("#okSubmit2").val('');
		if(document.getElementById("transNo")!=undefined){
				 $j("#transNo").focus();
			 }else{
				 $j("#skuId").focus();
			 }
	 }else if(rs["brand"]=="1"){
		   jumbo.showMsg("运单号已存在");
           $j("#transNo").val('');
           $j("#okSubmit2").val('');
           $j("#transNo").focus();
	 }  else{
		 jumbo.showMsg("保存包裹失败");
	 }
}

//打印电子面单
 function surfaceprint(id){
 	loxia.lockPage();
// 	jumbo.showMsg("打印中，请等待...");
 	var url = $j("body").attr("contextpath") + "/printSingleOrderDetailOutMode12.json?id=" + id;
 	printBZ(loxia.encodeUrl(url),true);
// 	console.log("打印中，请等待...");
 	loxia.unlockPage();
 }