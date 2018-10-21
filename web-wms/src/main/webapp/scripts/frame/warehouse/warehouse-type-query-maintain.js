
$j(document).ready(function (){
	
	$j("#tbl-waittingList").jqGrid({
		url:$j("body").attr("contextpath") + "/queryLocationType.json",
		datatype: "json",
		colNames: ["id","库位类型名称","库位类型编码","创建日期","创建人","操作"],
		colModel: [ 
		           {name: "id", index: "id", width: 120, resizable: true,hidden:true},
 				   {name: "name", index: "name", width: 120, resizable: true},
		           {name: "code", index: "code", width: 120,  resizable: true},
 				   {name: "createTime", index: "createTime", width: 120, resizable: true},
		           {name: "userName", index: "userName", width: 120,  resizable: true},
		           {name: "update", index: "update", width: 100,  resizable: true}
		           ],
//		caption: "店铺列表",
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
		loadComplete: function(){
			var update = '<div><button type="button" style="width:100px;" class="confirm" id="update" name="update" loxiaType="button" onclick="update(this);">'+"修改"+'</button></div>';
			var ids = $j("#tbl-waittingList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"update":update});
			}
		}
	});
//	var url=$j("body").attr("contextpath") + "/queryLocationType.json";
//	$j("#tbl-waittingList").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
	$j("#showType").dialog({title: "修改", modal:true,closeOnEscape:false, autoOpen: false, width: 300});//弹窗属性设置
	
	$j("#save").click(function(){
		 var name=document.getElementById("name").value;
		 var code=document.getElementById("code").value;
		 if(name==''){
			 jumbo.showMsg("请添加库位类型名称");return;
		 }
		 if(code==''){
			 jumbo.showMsg("请添加库位类型编码");return;
		 }
		 var obj = {};
		 obj["pdaLocationType.name"]=name;
		 obj["pdaLocationType.code"]=code;
		 var rs= loxia.syncXhrPost($j("body").attr("contextpath") + "/savePdaLocationType.json",obj);
		 if(rs["brand"] == '2'){
			 jumbo.showMsg("库位类型编码已存在");
		 }else if(rs["brand"] == '3'){
			 jumbo.showMsg("库位类型名称已存在");
		 }
		 else if(rs["brand"] == '-1'){
			 jumbo.showMsg("系统异常!");
		 }else if(rs["brand"]=='1'){
			 $j("#name").val("");
			 $j("#code").val("");
			 var url=$j("body").attr("contextpath") + "/queryLocationType.json";
			 $j("#tbl-waittingList").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
		 };
	});
	
	$j("#btnTypeClose").click(function(){//关闭指令明细
		$j("#showType").hide();
		$j("#showType").dialog("close");
	});
	
	$j("#del").click(function(){//批量删除
		var ids = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		var postData = {};
		if (ids && ids.length>0) {
			postData["ids"]=ids.join(",");
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/delPdaLocationType.json"),postData);
			if(rs["brand"]=='-1'){
				 jumbo.showMsg("系统异常!");
			}else if(rs["brand"]=='1'){
				 var url=$j("body").attr("contextpath") + "/queryLocationType.json";
				 $j("#tbl-waittingList").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
			}else{
				 jumbo.showMsg(rs["brand"]);
			}
		}
	});
});

function update(obj){//修改跳转
		var id = $j(obj).parents("tr").attr("id");
		var data=$j("#tbl-waittingList").jqGrid("getRowData",id);
		 $j("#typeId").val(id);
		 $j("#name2").val(data["name"]);
		 $j("#code2").val(data["code"]);
		 $j("#showType").show();
		 $j("#showType").dialog("open");
		 
}

function updateType(){//修改库位类型
	 var id=document.getElementById("typeId").value;
	 var name=document.getElementById("name2").value;
	 var code=document.getElementById("code2").value;
	 if(id==''){
		 jumbo.showMsg("系统异常id");return;
	 }
	 if(name==''){
		 jumbo.showMsg("请添加库位类型名称");return;
	 }
	 if(code==''){
		 jumbo.showMsg("请添加库位类型编码");return;
	 }
	 var obj = {};
	 obj["pdaLocationType.id"]=id;
	 obj["pdaLocationType.name"]=name;
	 obj["pdaLocationType.code"]=code;
	 var rs= loxia.syncXhrPost($j("body").attr("contextpath") + "/updatePdaLocationType.json",obj);
	 if(rs["brand"]=='2'){
		 jumbo.showMsg("库位类型编码已存在");
	 }
	 else if(rs["brand"]=='3'){
		 jumbo.showMsg("库位类型名称已存在");
	 }
	 else if(rs["brand"]=='-1'){
		 jumbo.showMsg("系统异常!");
	 }else if(rs["brand"]=='1'){
		 $j("#showType").hide();
		 $j("#showType").dialog("close");
		 var url=$j("body").attr("contextpath") + "/queryLocationType.json";
		 $j("#tbl-waittingList").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
	 };
}

	






 
