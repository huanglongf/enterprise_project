
$j(document).ready(function (){
	
	$j("#tbl-waittingList").jqGrid({
		url:$j("body").attr("contextpath") + "/queryZoonSort.json",
		datatype: "json",
		colNames: ["id","仓库区域编码","仓库区域名称","序列","修改"],
		colModel: [ 
		           {name: "id", index: "id", width: 120, resizable: true,hidden:true},
 				   {name: "code", index: "code",width:100, resizable:true},
		           {name: "name", index: "name", width: 120,  resizable: true},
		           {name: "seq", index: "seq", width: 120,  resizable: true},
	               {name:  "btn1",width:100,resizable:true}
		           ],
		rowNum: 10,
		rowList:[10,20,40],
	   	sortname: 'id',
	    pager: '#pager_query',
	   // multiselect: true,
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete: function(){
			var btn1 = '<div><button type="button" style="width:100px;" class="confirm" id="print" name="btnDel" loxiaType="button" onclick="showS(this);">'+"修改"+'</button></div>';
			var ids = $j("#tbl-waittingList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"btn1":btn1});
			}
			loxia.initContext($j(this));
		}
	});
	


	$j("#search").click(function(){
		var code=document.getElementById("code").value;
		var name=document.getElementById("name").value;
		 var obj = {};
		 obj["zoonCommand.code"]=code;
		 obj["zoonCommand.name"]=name;
		 var url=$j("body").attr("contextpath") + "/queryZoonSort.json";
		 $j("#tbl-waittingList").jqGrid('setGridParam',{
			url:url,
			postData:obj,
			page:1
		}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#div1 input,#div1 select").val("");
	});
	
	$j("#showZoon").dialog({title: "修改序列", modal:true,closeOnEscape:false, autoOpen: false, width: 760});//弹窗属性设置

	
	$j("#exitZoon").click(function(){//提交
		 $j("#seq").val("");
		 $j("#showZoon").hide();
		 $j("#showZoon").dialog("close");
	});
	
	$j("#commitZoon").click(function(){//提交
		var zId=document.getElementById("zId").value;
		var seq=document.getElementById("seq").value;
		if(seq ==null ){
		}else{
			 if(!/^[0-9]*$/.test(seq)){
	             jumbo.showMsg("请输入正确的序列");return;
	            }
		}
		 var obj = {};
		 obj["zoonCommand.id"]=zId;
		 obj["zoonCommand.seq"]=seq;
		 var url=$j("body").attr("contextpath") + "/commitZoon.json";
		 var rs= loxia.syncXhrPost(url,obj);
		 if(rs !=null){
						 if(rs["brand"] == '1'){
							  $j("#seq").val("");
							    $j("#showZoon").hide();
								$j("#showZoon").dialog("close");
							 var url=$j("body").attr("contextpath") + "/queryZoonSort.json";
							 $j("#tbl-waittingList").jqGrid('setGridParam',{
								url:url,
								postData:obj,
								page:1
							}).trigger("reloadGrid");
						//	 jumbo.showMsg("修改ok");
						 }else{
							 jumbo.showMsg("修改失败");
						 }
		 }else{
			 jumbo.showMsg("修改失败");
		 }
		 
	});
	
});

function showS(obj){
	var id = $j(obj).parents("tr").attr("id");
	var data=$j("#tbl-waittingList").jqGrid("getRowData",id);
	 $j("#name1").text(data['name']);
	 $j("#zId").val(data['id']);
	 $j("#seq").val(data['seq']);
	 $j("#showZoon").dialog("open");
	 $j("#showZoon").show();
}

 
