$j.extend(loxia.regional['zh-CN'],{		
});
$j(document).ready(function(){
	
	// 初始化商品类型
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/auto/findOccupyOu.do");
	for(var idx in result){
	    	$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#ouId"));
	}
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
		$j("#queryForm select").val(0);
	});
	
	
	var statusValue={"value":"1:新建;5:占用;"};
	$j("#tbl_turnoverboxlist").jqGrid({
		datatype:"json",
		colNames:["ID","周转箱编码","状态","配货清单编码","作业单号","仓库名称","操作"],
		colModel:[
					{name: "id", index: "id", hidden: true},
					{name:"code", index:"code",width:100, resizable:true},
					{name:"statusIntValue",index:"statusIntValue",width:100,resizable:true,formatter:'select',editoptions:statusValue},
					{name:"pickingListCode",index:"pickingListCode",width:100,resizable:true},
					{name:"staCode",index:"staCode",width:100,resizable:true},
					{name:"ouName",index:"ouName",width:100,resizable:true},
					{name:"idForBtn", width: 220,resizable:true}
		          ],
        caption: "现有周转箱列表",
        rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
  	   	sortname: 'c.id',
  	   	height:"auto",
  	    multiselect: false,
  	    viewrecords: true,
 		rownumbers:true,
  		sortorder: "asc",
  		pager:"#pager",
  		jsonReader: { repeatitems : false, id: "0" },
  		gridComplete: function(){
			var ids = $j("#tbl_turnoverboxlist").jqGrid('getDataIDs');
			var btn1 = '<div name="divCancelBtn" style="float: left"><button type="button" class="confirm" name="btnPrint" loxiaType="button">打印条码</button></div>';
			var btn2 = '<div style="float: left"><button type="button" name="btnReset" loxiaType="button">重置状态</button></div>';
			for(var i=0;i < ids.length;i++){
				var btn = btn1 + btn2;
				var data=$j("#tbl_turnoverboxlist").jqGrid("getRowData",ids[i]);
				if(data["statusIntValue"] != "5"){
					btn = btn1;
				}
				$j("#tbl_turnoverboxlist").jqGrid('setRowData',ids[i],{"idForBtn":btn});
			}
			loxia.initContext($j(this));
			$j("button[name='btnPrint']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				printBarCode(id);
			});
			$j("button[name='btnReset']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				resetStatus(id);
			});
  		}
	});
	$j("#new").click(function(){
		$j("#div1").addClass("hidden");
		$j("#div2").removeClass("hidden");
	});
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
	});
	$j("#back").click(function(){
		$j("#div1").removeClass("hidden");
		$j("#div2").addClass("hidden");
	});
	$j("#save").click(function(){
		var tn1 = $j("#tn1").val();
		if(tn1==""){
			loxia.tip($j("#tn1"),"请填写周转箱编码!");
			return;
		}else{
			if(tn1.length>10){
				loxia.tip($j("#tn1"),"编码长度不能超过10位!");
				return;
			}
		}
		var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/createnewturnbox.json?code="+tn1);
		if(rs && rs.result == "success"){
			jumbo.showMsg("新增周转箱成功!");
			$j("#tn1").val("");
			$j("#query").trigger("click");
		}else{
			jumbo.showMsg(rs.msg);
		}
	});
	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("queryForm");
		$j("#tbl_turnoverboxlist").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/getallturnoverbox.do",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
});
//周转箱条码打印
function printBarCode(id){
	var url = $j("body").attr("contextpath") + "/json/printturnoverboxbarcode.do?tnId=" + id;
	printBZ(loxia.encodeUrl(url),true);
}
//周转箱状态重置
function resetStatus(id){
	if(confirm("确认重置状态?")){
		var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/resetturnoverboxstatus.json?tnId="+id);
		if(rs!=null&&rs.result=="success"){
			$j("#query").trigger("click");
		}else{
			jumbo.showMsg(rs.msg);
		}
	}	
}