
$j.extend(loxia.regional['zh-CN'], {
	ENTITY_EXCELFILE				:	"正确的excel导入文件"
});
$j(document).ready(function (){
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/queryLocationTypeList.json");
	
	
	$j("#typeName").append("<option value=''>请选择</option>");
	for(var i=0;i<result.length;i++){
		$j("#typeName").append("<option value="+result[i].id+">"+result[i].name+"</option>");
	};
	
	
	$j("#typeName").change(function(){
		var id=$j("#typeName").val();
		if("请选择"!=id){
			$j("#typeCode").empty();
			var obj = {};
			obj["id"]=id;
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/queryLocationTypeNameList.json",obj);
			$j("#typeCode").append("<option value="+result.id+">"+result.code+"</option>");
		}
		
	});
	
	$j("#typeCode").append("<option value=''>请选择</option>");
	for(var i=0;i<result.length;i++){
		$j("#typeCode").append("<option value="+result[i].id+">"+result[i].code+"</option>");
	}
	
	$j("#typeName2").change(function(){
		var id=$j("#typeName2").val();
		if("请选择"!=id){
			$j("#typeCode2").empty();
			var obj = {};
			obj["id"]=id;
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/queryLocationTypeNameList.json",obj);
			$j("#typeCode2").append("<option value="+result.id+">"+result.code+"</option>");
		}
		
	});
	$j("#typeName2").append("<option value=''>请选择</option>");
	for(var i=0;i<result.length;i++){
		$j("#typeName2").append("<option value="+result[i].id+">"+result[i].name+"</option>");
	}
	
	
	$j("#typeCode2").append("<option value=''>请选择</option>");
	for(var i=0;i<result.length;i++){
		$j("#typeCode2").append("<option value="+result[i].code+">"+result[i].code+"</option>");
	}
	
	$j("#btnSearchSKU").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	$j("#btnSearchSKU2").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	
	$j("#tbl-waittingList").jqGrid({
		url:$j("body").attr("contextpath") + "/queryLocationVolumeTypeList.json",
		datatype: "json",
		colNames: ["id","库位类型名称","库位类型编码","货号","SKU编码","SKU名称","容量"],
		colModel: [ 
		           {name: "id", index: "id", width: 120, resizable: true,hidden:true},
 				   {name: "typeName", index: "typeName", width: 120, resizable: true},
		           {name: "typeCode", index: "typeCode", width: 120,  resizable: true},
		           {name: "supplierCode", index: "supplierCode", width: 120,  resizable: true},
		           {name: "skuCode", index: "skuCode", width: 120,  resizable: true},
		           {name: "skuName", index: "skuName", width: 120,  resizable: true},
		           {name: "qty", index: "qty", width: 120,  resizable: true}
		           ],
		rowNum: 10,
		rowList:[10,20,40],
	   	sortname: 'id',
	    pager: '#pager_query',
	    multiselect: true,
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
	});
	


	$j("#search").click(function(){
		 var obj = {};
		 var supplierCode=$j("#supplierCode").val().trim();
		 var skuBarcode=$j("#skuCode").val().trim();
		 var skuName=$j("#skuName").val().trim();
		 var typeNameId=$j("#typeName").val().trim();
		 var typeCodeId=$j("#typeCode").val().trim();
		 obj["pdaSkuLocTypeCapCommand.supplierCode"]=supplierCode;
		 obj["pdaSkuLocTypeCapCommand.skuCode"]=skuBarcode;
		 obj["pdaSkuLocTypeCapCommand.skuName"]=skuName;
		 obj["pdaSkuLocTypeCapCommand.typeName"]=typeNameId;
		 obj["pdaSkuLocTypeCapCommand.typeCode"]=typeCodeId;
		 var url=$j("body").attr("contextpath") + "/queryLocationVolumeTypeList.json";
		 $j("#tbl-waittingList").jqGrid('setGridParam',{
			url:url,
			postData:obj,
			page:1
		}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#div1 input,#div1 select").val("");
		$j("#typeCode").val("");
	});
	
	
	$j("#save").click(function(){
		 var supplierCode=$j("#supplierCode2").val().trim();
		 var skuCode=$j("#skuCode2").val().trim();
		 var skuName=$j("#skuName2").val().trim();
		 var typeName=$j("#typeName2").val().trim();
		 var typeCode=$j("#typeCode2").val().trim();
		 var qty=$j("#qty").val().trim();
		 if(null==typeCode||''==typeCode){
			 jumbo.showMsg("请添加库位类型编码");return;
		 }
		 if(null==typeName||''==typeName){
			 jumbo.showMsg("请添加库位类型名称");return;
		 }
		 if(null==qty||''==qty){
			 jumbo.showMsg("请添加容量");return;
		 } if((null==supplierCode||''==supplierCode)&&(null==skuCode||''==skuCode)){
			 jumbo.showMsg("货号和SKU编码至少填写一个");return;
		 }
		 
		 
		 var obj = {};
		 if(null==supplierCode||''==supplierCode||undefined==supplierCode){
			 obj["pdaSkuLocTypeCapCommand.supplierCode"]="";
		 }else{
			 obj["pdaSkuLocTypeCapCommand.supplierCode"]=supplierCode;
		 }
		 if(null==skuCode||''==skuCode||undefined==skuCode){
			 obj["pdaSkuLocTypeCapCommand.skuCode"]="";
		 }else{
			 obj["pdaSkuLocTypeCapCommand.skuCode"]=skuCode;
		 }
		
		 obj["pdaSkuLocTypeCapCommand.id"]=typeName;
		 obj["pdaSkuLocTypeCapCommand.id"]=typeCode;
		 obj["pdaSkuLocTypeCapCommand.qty"]=qty;
		 var rs= loxia.syncXhrPost($j("body").attr("contextpath") + "/savePdaSkuLocTypeCapBinding.json",obj);
		 if(rs["flag"] == 'success'){
			 $j("#supplierCode2").val("");
			 $j("#skuCode2").val("");
			 $j("#skuName2").val("");
             $j("#qty").val("");
			 var url=$j("body").attr("contextpath") + "/queryLocationVolumeTypeList.json";
			 $j("#tbl-waittingList").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
		 }else if(rs["flag"] == 'none'){
			 jumbo.showMsg("填入的货号或者SKU编码错误");
		 }
		 else {
			 jumbo.showMsg("系统异常!");
			
		 };
	});
	
	$j("#del").click(function(){//批量删除
		var ids = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		var postData = {};
		if (ids && ids.length>0) {
			if(window.confirm('确定要取消此单？')){
			    postData["ids"]=ids.join(",");
			    var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/delPdaSkuLocTypeCapBinding.json"),postData);
			    if(rs["flag"]!='success'){
				     jumbo.showMsg("系统异常!");
			     }else{
				     var url=$j("body").attr("contextpath") + "/queryLocationVolumeTypeList.json";
				     $j("#tbl-waittingList").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
			     }
		     }
		}else{
			 jumbo.showMsg("请勾选要删除的列");
		}
	});
	

	$j("#import").click(function(){//导入
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("ENTITY_EXCELFILE"));
			return;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/savePdaSkuLocTypeCapeBindingImport.json"));
		//loxia.submitForm("importForm");
		$j("#importForm").submit();
		$j("#search").trigger("click");
		/*var url=$j("body").attr("contextpath") + "/queryLocationVolumeTypeList.json";
		$j("#tbl-waittingList").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");*/
	});
	

});


function name2(){//库位类型名称 onchange
	var name=document.getElementById("name2").value;
	if(name==''){
		$j("#code2 option").each(function (){
		    if($j(this).text()=="请选择"){
		        $j(this).attr('selected',true);
		    }
			});
	}else{
			 var obj={};
			 obj["pdaLocationType.name"]=name;
			 var rs= loxia.syncXhrPost($j("body").attr("contextpath") + "/queryLocationTypeByNameCodeOuId.json",obj);
			 $j("#code2 option").each(function (){
				    if($j(this).text()==rs["type"]["code"]){
				        $j(this).attr('selected',true);
				    }
					});
			
		
	}
}

function code2(){//库位类型编码  onchange
	var code=document.getElementById("code2").value;
	if(code==''){
		$j("#name2 option").each(function (){
		    if($j(this).text()=="请选择"){
		        $j(this).attr('selected',true);
		    }
			});
	}else{
		 var obj={};
		 obj["pdaLocationType.code"]=code;
		 var rs= loxia.syncXhrPost($j("body").attr("contextpath") + "/queryLocationTypeByNameCodeOuId.json",obj);
		 $j("#name2 option").each(function (){
			    if($j(this).text()==rs["type"]["name"]){
			        $j(this).attr('selected',true);
			    }
				});
	}
}






 
