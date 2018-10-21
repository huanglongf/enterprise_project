$j(document).ready(function (){
	$j("#btnSearch").click(function(){//查询店铺
		var type=document.getElementById("centerType").value; 
		if(type==''){
			 jumbo.showMsg("请中心类型");
		}else if(type=='1'){//部门
			$j("#departQueryDialog").dialog("open");
		}else{//店铺
			$j("#shopQueryDialog").dialog("open");
		}
	});
	

	
	$j("#tbl-waittingList").jqGrid({
		url:$j("body").attr("contextpath") + "/pickingListLogQueryDo.json",
		datatype: "json",
		colNames: ["ID","登录账号","配货批次号","仓库区域","拣货开始时间","拣货完成时间","区域拣货件数","实际拣货件数"],
		colModel: [
				   {name: "id", index: "id", hidden: true},
				   {name: "userName", index: "userName",width:200,resizable:true},
				   {name: "code", index: "code",width:200,resizable:true},
		           {name: "zoonName", index: "zoonName",width:200,resizable:true},
		           {name: "startTime", index: "startTime",width:200,resizable:true},
		           {name: "endTime", index: "endTime",width:200,resizable:true},
				   {name: "pQty", index: "userName",width:200,resizable:true},
				   {name: "qty", index: "userName",width:200,resizable:true}
		           ],
		caption: "用户拣货结果列表",//
		pager:"#pager_query",
		rowNum: 10,
		rowList:[10,20,40],
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
//		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" }
	});
	//jumbo.bindTableExportBtn("tbl-waittingList",{});
	  jumbo.bindTableExportBtn("tbl-waittingList",{});
	

	//查询按钮功能 根据作业单号和状态查询
	$j("#btn-query").click(function(){
		 var createTime=document.getElementById("createTime").value;
		 var createTime2=document.getElementById("createTime2").value;
		 
		 var startTime=document.getElementById("startTime").value;
		 var startTime2=document.getElementById("startTime2").value;
		 
		 var pCode=document.getElementById("pCode").value;
		 var userName=document.getElementById("userName").value;
		 
		 if(createTime !="" && createTime2 !=""){
			 if(createTime>createTime2){
				 jumbo.showMsg("创建开始时间不大于结束时间");return;
			 }
		 }
		 
		 if(startTime !="" && startTime2 !=""){
			 if(startTime>startTime2){
				 jumbo.showMsg("拣货开始时间不大于结束时间");return;
			 }
		 }

		 
		 var postData={};
		 postData["createTime"]=createTime;
		 postData["createTime2"]=createTime2;
		 postData["startTime"]=startTime;
		 postData["startTime2"]=startTime2;
		 postData["userName"]=userName;
		 postData["pCode"]=pCode;

		 
		 var url=window.$j("body").attr("contextpath")+"/json/pickingListLogQueryDo.do";

		 $j("#tbl-waittingList").jqGrid('setGridParam',{
			url:url,
			postData:postData,
			page:1
		}).trigger("reloadGrid");
//		jumbo.bindTableExportBtn("tbl-waittingList",{"isnotLandTrans":"isLandTrans","businessType":"businessType","costCenterType":"costCenterType"},url,postData);
	  //  bindTableExportBtn("tbl-waittingList",{"isnotLandTrans":"isLandTrans","businessType":"businessType","costCenterType":"costCenterType"},url,postData);
		    jumbo.bindTableExportBtn("tbl-waittingList",{},url,postData);
		 
	});
	
	//重置
	$j("#reset").click(function(){
		$j("#div1 input,#div1 select").val("");
	});
	
});


/**
 * 导出
 */
/*function bindTableExportBtn(table,optionCols,url,postData,postCheck){
	var $t = loxia.isString(table)?$j("#" + table):$j(table),
		url = url || $t.jqGrid("getGridParam","url"),
		$c = $t.parents("div.ui-jqgrid");
	var html = [];
	html.push('<a class="ui-jqgrid-titlebar-export HeaderButton"' +  
			' href="javascript:;" title="导出" role="link" style="float:left;">' + 
			'<span class="ui-icon ui-icon-comment"><!-- --></span></a>');
	html.push("<form action="+url+" type='post'>");
	html.push(_constructHidden("isExcel","true"));
	html.push(_constructHidden("page","1"));
	html.push(_constructHidden("rows","-1"));
	html.push(_constructHidden("sidx",""));
	html.push(_constructHidden("sord",""));
	html.push(_constructHidden("caption",$t.jqGrid("getGridParam","caption")));
	var cm=$t.jqGrid("getGridParam","colModel"),cn=$t.jqGrid("getGridParam","colNames"),index=-1;
	var filter={"id":"id","rn":"","subgrid":"","cb":"","receiver":"receiver","receiverTel":"receiverTel","receiverProvince":"receiverProvince","receiverCity":"receiverCity","receiverArea":"receiverArea","receiverAddress":"receiverAddress",
			"btn2":"btn2","print":"print","btn3":"btn3"};
	$j.each(cm,function(i,e){
		if(e.hidden==false&&!(e.name in filter)){
			index++;
			html.push(jumbo._constructHidden("colModel["+index+"]",e.name));
			html.push(jumbo._constructHidden("colNames["+index+"]",cn[i]));
		}
	});
	if(optionCols){
		for(var k in optionCols){
			html.push(_constructHidden("columnOption." + k,optionCols[k]));
		}
	}
	if(postData){
		for(var k in postData){
			html.push(_constructHidden(k,postData[k]));
		}
	}
	html.push("</form>");
	if($c.find(".ui-jqgrid-titlebar-export").length>0){
		$c.find(".ui-jqgrid-titlebar-export").remove();
		$c.find("form").remove();
	}
	$c.find(".ui-jqgrid-titlebar .ui-jqgrid-titlebar-close").after(html.join(""));
	$c.find(".ui-jqgrid-titlebar-export").hover(function(){
		$j(this).addClass("ui-state-hover");
	},function(){
		$j(this).removeClass("ui-state-hover");
	});
	$c.find(".ui-jqgrid-titlebar-export").click(function(){
		var num;
		var text = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(2)>div").text();
		if(text == "" || text == "无数据显示"){
			alert("没有要导出的数据!");
			return;
		}
		if(text==""){
			var num1 = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(1)>table>tbody>tr>td:eq(3)>span").text().replace(" ","");
			var num2 = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(1)>table>tbody>tr>td:eq(7)>select").val();
			num = parseInt(num1)*parseInt(num2);
		}else{
			var d = text.indexOf("共");
			if(d>0){
				var num=text.substring(d+2,text.length-2);
				num=num.replace(new RegExp(" ", 'g'), "");
			}
		}
		if(num>20000){
			alert("系统设定最多导出20000条数据!");
			return;
		}
		if(typeof(postCheck)==="function"){
			if(postCheck()== false){
				return false;
			}
		}
		$c.find(".ui-jqgrid-titlebar input[name='sidx']").val($t.jqGrid("getGridParam","sortname"));
		$c.find(".ui-jqgrid-titlebar input[name='sord']").val($t.jqGrid("getGridParam","sortorder"));
		$c.find(".ui-jqgrid-titlebar form")[0].submit();
		return false;
	});
}
 
*/