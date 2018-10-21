
$j(document).ready(function (){
	
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/queryLocationTypeList.json");
	$j("#code").append("<option value=''>请选择</option>");
	for(var i=0;i<result.length;i++){
		$j("#code").append("<option value="+result[i].code+">"+result[i].code+"</option>");
	}
	$j("#code2").append("<option value=''>请选择</option>");
	for(var i=0;i<result.length;i++){
		$j("#code2").append("<option value="+result[i].code+">"+result[i].code+"</option>");
	}
	
	$j("#name").append("<option value=''>请选择</option>");
	for(var i=0;i<result.length;i++){
		$j("#name").append("<option value="+result[i].name+">"+result[i].name+"</option>");
	}
	$j("#name2").append("<option value=''>请选择</option>");
	for(var i=0;i<result.length;i++){
		$j("#name2").append("<option value="+result[i].name+">"+result[i].name+"</option>");
	}
	
	
	
	
	
	$j("#tbl-waittingList").jqGrid({
		url:$j("body").attr("contextpath") + "/queryLocationTypeBinding.json",
		datatype: "json",
		colNames: ["id","库位类型名称","库位类型编码","库位编码"],
		colModel: [ 
		           {name: "id", index: "id", width: 120, resizable: true,hidden:true},
 				   {name: "name", index: "name", width: 120, resizable: true},
		           {name: "code", index: "code", width: 120,  resizable: true},
		           {name: "locationCode", index: "locationCode", width: 120,  resizable: true}
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
		var locationCode=document.getElementById("locationCode").value;
		var name=document.getElementById("name").value;
		var code=document.getElementById("code").value;
		 var obj = {};
		 obj["pdaLocationTypeCommand.locationCode"]=locationCode;
		 obj["pdaLocationTypeCommand.name"]=name;
		 obj["pdaLocationTypeCommand.code"]=code;
		 var url=$j("body").attr("contextpath") + "/queryLocationTypeBinding.json";
		 $j("#tbl-waittingList").jqGrid('setGridParam',{
			url:url,
			postData:obj,
			page:1
		}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#div1 input,#div1 select").val("");
	});
	
	
	$j("#save").click(function(){
		 var locationCode=document.getElementById("locationCode2").value;
		 var name=document.getElementById("name2").value;
		 var code=document.getElementById("code2").value;
		 if(locationCode==''){
			 jumbo.showMsg("请添加库位编码");return;
		 }
		 if(name==''){
			 jumbo.showMsg("请添加库位类型名称");return;
		 }
		 if(code==''){
			 jumbo.showMsg("请添加库位类型编码");return;
		 }
		 var obj = {};
		 obj["pdaLocationTypeCommand.locationCode"]=locationCode;
		 obj["pdaLocationTypeCommand.name"]=name;
		 obj["pdaLocationTypeCommand.code"]=code;
		 var rs= loxia.syncXhrPost($j("body").attr("contextpath") + "/savePdaLocationTypeBinding.json",obj);
		 if(rs["brand"] == '3'){
			 jumbo.showMsg("库位编码不存在");
		 }else if(rs["brand"] == '2'){
			 jumbo.showMsg("该库位已经绑定!");
		 }else if(rs["brand"] == '-1'){
			 jumbo.showMsg("系统异常!");
		 }else if(rs["brand"]=='1'){
			 $j("#div1 input,#div1 select").val("");
			 var url=$j("body").attr("contextpath") + "/queryLocationTypeBinding.json";
			 $j("#tbl-waittingList").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
		 };
	});
	
	$j("#del").click(function(){//批量删除
		var ids = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		var postData = {};
		if (ids && ids.length>0) {
			postData["ids"]=ids.join(",");
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/delPdaLocationTypeBinding.json"),postData);
			if(rs["brand"]=='-1'){
				 jumbo.showMsg("系统异常!");
			}else if(rs["brand"]=='1'){
				 var url=$j("body").attr("contextpath") + "/queryLocationTypeBinding.json";
				 $j("#tbl-waittingList").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
			}
		}
	});
	

	$j("#import").click(function(){//导入
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");
			return;
		}
//		alert(0);
		loxia.lockPage();
		$j("#importForm2").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/savePdaLocationTypeBindingImport.json"));
		loxia.submitForm("importForm2");
//		$j("#importForm").submit();
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






 
