
var extBarcode = null;

$j.extend(loxia.regional['zh-CN'],{
	OPERATE_FAILED					: "操作失败",
	OPERATE_SUCCESS					: "操作成功",
	SURE_CANCEL						: "是否确认修改？",
	OPERATE_ZZ						: "只能输入正整数！"
});

function showDetail(tag){
	var tr = $j(tag).parents("tr");
	var id = tr.attr("id");
	$j("#role-list").addClass("hidden");
	$j("#btn").removeClass("hidden");
	$j("#order-List").removeClass("hidden");
	var sta=$j("#tbl-role-list").jqGrid("getRowData",id);
	$j("#categoryCode").html(sta["categoryCode"]);
	$j("#categoryName").html(sta["categoryName"]);
	$j("#optionValue").val(sta['optionValue']);
	tempId = id;
}
var tempId = null;
$j(document).ready(function (){
	$j("#tbl-role-list").jqGrid({
		url:$j("body").attr("contextpath")+"/findSecKillMaintain.json",
		datatype: "json",
		colNames: ["id","常量集编码","常量集名称","常量值","是否可用"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name:"categoryCode",index:"categoryCode",  width: 150,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
		           {name:"categoryName",index:"categoryName", width:95,resizable:true},
		           {name:"optionValue",index:"optionValue", width:150,resizable:true},
		           {name:"isAvailables",index:"isAvailables", width:150,resizable:true},
		           
		           //{name:"optionValue", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"编辑", onclick:"editDetial(this,event);"}}}
		           ],
		caption: "秒杀订单计数器功能维护列表",
		sortname: 'id',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	//修改 常量值
	$j("#search").click(function(){
		var optionValue=$j("#optionValue").val();
		var s=/^[0-9]{1,20}$/;  //只能输入正整数
	   if(!s.test(optionValue)){
		   jumbo.showMsg(loxia.getLocaleMsg("OPERATE_ZZ"));
		   return false;
	   }
		var data = {};
		data["id"] = tempId;
		 data["optionValue"]=optionValue;
		if(window.confirm(loxia.getLocaleMsg("SURE_CANCEL"))){
			 loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/updateOptionValue.json",
					   data,
		  				{
						        success:function () {
			          	    	jumbo.showMsg(loxia.getLocaleMsg("OPERATE_SUCCESS"));
			          	    	//刷新
			          	  	    $j("#tbl-role-list").jqGrid('setGridParam').trigger("reloadGrid");
			          	    	$j("#role-list").removeClass("hidden");
			          			$j("#order-List").addClass("hidden");
			          			$j("#btn").addClass("hidden");
			          			//$j("#tbl-inbound-purchase tr[id='"+tempId+"']").remove();
			  	           },
			               error:function(){
			                	jumbo.showMsg(loxia.getLocaleMsg("OPERATE_FAILED"));	//操作失败！
			               }
		  			});
		}
	
	});
	//返回
	$j("#back").click(function(){
		$j("#role-list").removeClass("hidden");
		$j("#order-List").addClass("hidden");
		$j("#btn").addClass("hidden");
	});
	
});