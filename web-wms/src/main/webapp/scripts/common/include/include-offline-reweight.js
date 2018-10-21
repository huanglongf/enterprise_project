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
	restart();
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
	 
	 $j("#packageWeight").keydown(function(evt) {//手工重量转OK确定
			if (evt.keyCode === 13) {
				$j("#okSubmit").focus();
			}
		});
//	 $j("#volume").keydown(function(evt) {//体积转OK确定
//			if (evt.keyCode === 13) {
//				$j("#okSubmit").focus();
//			}
//		});
	 
	
	$j("#shopQueryDialog2").dialog({title: "线下包裹称重", modal:true, autoOpen: false, width: 600,closeOnEscape: false,open: function(event, ui){$j(".ui-dialog-titlebar-close").hide();}});
	restart();
	$j("#btnShopQueryClose2").click(function(){
		$j("#shopQueryDialog2").hide();
		$j("#shopQueryDialog2").dialog("close");
	});
	
	$j("#restWeight").click(function(){//点击重启称
		restart();
	});
	
/*	$j("#transNo").keydown(function(evt) {
		if (evt.keyCode === 13) {
			$j("#skuId").focus();
		}
});*/
	
	$j("#okSubmit").keydown(function(event){
		var value = $j("#okSubmit").val();
		if(event.keyCode == 13){
			if (value == BARCODE_CONFIRM){
				submit();
			}else if(value != "" ) {
				 jumbo.showMsg("输入的确认条码不正确，请重新输入.");return;
			}else if(value == "" ) {
				 jumbo.showMsg("请输入确认条码.");return;
			}
		}
	});
	

	
	$j("#zhiSubmit").click(function(){//提交纸质面单
		submit();
	})
	
	
	function submit(){//提交公共方法
		 var id=document.getElementById("reId").value;//快递单号
		 var transNo=document.getElementById("reTransNo").value;//快递单号
		 var skuId=document.getElementById("skuId").value;//库存id
		 var autoWeigth=document.getElementById("autoWeigth").value;//自动重量
		 var packageWeight=document.getElementById("packageWeight").value;//手动重量
		 var volume=document.getElementById("volume").value;//体积
		 
		 if(skuId==null ||skuId=='' ){
			 jumbo.showMsg("请扫描耗材");return;
		 }
//		 if(!/^[0-9]*$/.test(skuId)){
//			 $j("#skuId").val('');
//			 $j("#okSubmit").val('');
//			 $j("#skuId").focus();
//             jumbo.showMsg("耗材,不是一个合法的数字要求不符合要求");return;
//            }
		 if(autoWeigth=='' && packageWeight==''){
			 $j("#okSubmit").val('');
			 $j("#packageWeight").val('');
			 $j("#packageWeight").focus();
			 jumbo.showMsg("请填写重量");return;
		 }
		 if(packageWeight==''){
			 packageWeight=autoWeigth;
		 }
		 if(!/^([1-9]\d{0,}|0)(\.\d{1,3})?$/.test(packageWeight)){//验证重量
			 $j("#okSubmit").val('');
			 $j("#packageWeight").val('');
			 $j("#packageWeight").focus();
			 jumbo.showMsg("重量,不是一个合法的数字要求不符合要求");return;
		 }
		 if(volume==''){
			 $j("#okSubmit").val('');
			 jumbo.showMsg("请填写体积");return;
		 }
//		 if(!/^[0-9]*\.{0,1}[0-9]*$/.test(volume)){
//			 $j("#okSubmit").val('');
//			 jumbo.showMsg("体积,不是一个合法的数字或精度要求不符合要求");return;
//			}
//		 return;
		 var transPackage = {};
		 transPackage["skuId2"]=skuId;
		 transPackage["transPackage.packageWeight"]=packageWeight;
		 transPackage["transPackage.volume"]=volume;
		 transPackage["transPackage.transNo"]=transNo;
		 transPackage["transPackage.id"]=id;
		 var rs= loxia.syncXhrPost($j("body").attr("contextpath") + "/updateTransPackage.json",transPackage);
		 if(rs["msg"]=="success"){
			 $j("#shopQueryDialog2").hide();
			 $j("#shopQueryDialog2").dialog("close");
			   window.location.reload();
		 }else{
			 jumbo.showMsg("重启重包裹失败");
		 }
	}
	
	$j("#autoWeigth").click(function(){//重启称
			restart();
		});
	
	
});