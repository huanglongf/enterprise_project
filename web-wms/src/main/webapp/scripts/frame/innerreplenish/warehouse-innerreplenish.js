$j.extend(loxia.regional['zh-CN'],{
	CODE:"编码",
	CREATETIME:"创建时间",
	TYPE:"类型",
	STATUS:"状态",
	WARNINGPRE:"安全警戒线",
	OPERATE:"操作",
	DATAERROR:"请输入1-100之间的整数!",
	SYSTEMERROR:"新增补货报表出现错误!"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
$j(document).ready(function(){
	$j("#wh_re_table").jqGrid({
		url:$j("body").attr("contextpath")+"/findallwhreplenish.json",
		datatype: "json",
		colNames: ["ID",i18("CODE"),i18("CREATETIME"),i18("TYPE"),i18("STATUS"),i18("WARNINGPRE"),i18("OPERATE")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "code", index: "code", width: 120, resizable: true},
		           {name: "createTime",index:"createTime",width:120,resizable:true,sortable:false},
		           {name: "stringType", index: "stringType", width: 90, resizable: true,sortable:false},
		           {name: "stringStatus", index: "stringStatus", width: 40, resizable: true,sortable:false},
	               {name: "warningPre", index: "warningPre", width: 80, resizable: true,sortable:false},
	               {name: "idForBtn",width:100,resizable:true,sortable:false}],
	   	sortname: 'id',
	   	pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		gridComplete: function(){
			var ids = $j("#wh_re_table").jqGrid('getDataIDs');
			var btn1 = '<button style="width:100px" class="confirm" name="btnCancel" loxiaType="button">取消</button>';
			var btn2 = '<button style="width:100px" class="confirm" name="btnExport" loxiaType="button">导出报表</button>';
			for(var i=0;i<ids.length;i++){
				var data=$j("#wh_re_table").jqGrid("getRowData",ids[i]);
				if(data["stringStatus"]=="新建"){
					$j("#wh_re_table").jqGrid('setRowData',ids[i],{"idForBtn":btn1});
				}else if(data["stringStatus"]=="已完成"){
					$j("#wh_re_table").jqGrid('setRowData',ids[i],{"idForBtn":btn2});
				}
			}
			loxia.initContext($j(this));
			$j("button[name='btnCancel']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				if(!window.confirm("确定取消吗?")){//您确定要作废？
					return false;
				}
				cancelOrder(id);
			});
			$j("button[name='btnExport']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				exportOrder(id);
			});
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#apply").click(function(){
		if(!loxia.byId("wrWarningPre").check()){
			loxia.tip($j("#wrWarningPre"),i18("DATAERROR"));
			return;
		}
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/newwhreplenish.json",loxia._ajaxFormToObj("dataForm"));
		if(rs.rs&&rs.rs=="success"){
			$j("#wrWarningPre").val("");
			if(rs.flag==0){
				jumbo.showMsg("已覆盖4小时之前未完成的单据!");
			}else if(rs.flag==1){
				jumbo.showMsg("有进行中申请，本次申请不处理");
			}else if(rs.flag==2){
				jumbo.showMsg("已覆盖之前申请!");
			}else{
				jumbo.showMsg("新建申请成功!");
			}
			$j("#wh_re_table").jqGrid('setGridParam',
					{url:window.$j("body").attr("contextpath")+"/findallwhreplenish.json",page:1}
			).trigger("reloadGrid");
		}else{
			jumbo.showMsg(i18("SYSTEMERROR"));
			$j("#wrWarningPre").val("");
			$j("#wrWarningPre")
		}
	});
	$j("#wrType").change(function(){
		if($j(this).val()=="NORMAL"){
			$j("#wrWarningPre").attr("disabled",false);
		}else{
			$j("#wrWarningPre").attr("disabled",true);
		}
	});
});
function cancelOrder(id){
	var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/cancelreplenishorder.json",{"wrId":id});
	if(rs.rs&&rs.rs=="success"){
		$j("#wh_re_table").jqGrid('setGridParam',
				{url:window.$j("body").attr("contextpath")+"/findallwhreplenish.json",page:1}
		).trigger("reloadGrid");
	}else{
		jumbo.showMsg("取消失败");
		return ;
	}
}
function exportOrder(id){
	$j("#export").attr("src",$j("body").attr("contextpath") + "/warehouse/exportreplenishorder.do?wrId=" + id);
}