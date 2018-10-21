var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	CODE : "集货口编码",
	NAME : "集货口名称",
	CREATE_TIME : "创建时间",
	ROLE_LIST : "集货口列表"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

var pointId = ""; // 规则ID
// 规则明细
function showRoleLine(tag){
	$j("#div1").addClass("hidden");
	$j("#div2").removeClass("hidden");
	if(tag != null){
		var tr = $j(tag).parents("tr");
		var id = tr.attr("id");
		pointId = id;
		var code= $j("#tbl-shipping-point").getCell(id,"code");
		var name= $j("#tbl-shipping-point").getCell(id,"name");
		var wsc= $j("#tbl-shipping-point").getCell(id,"wcsCode");
		var pointType= $j("#tbl-shipping-point").getCell(id,"pointType");
		var maxAssumeNumber= $j("#tbl-shipping-point").getCell(id,"maxAssumeNumber");
		$j("#roleCode1").attr("value",code);
		$j("#roleName1").attr("value",name);
		$j("#roleWsc1").attr("value",wsc);
		$j("#roleCode2").html(code);
		$j("#roleCode1").addClass("hidden");
		$j("#roleCode2").removeClass("hidden");
		if(pointType==1){
			$j("#pointType").attr("checked","checked");
			$j("#inputNumber").removeClass("hidden");
			$j("#labelMaxAssumeNumber").removeClass("hidden");
			$j("#maxAssumeNumber").val(maxAssumeNumber);
			$j("#pointType").unbind("click");
			//绑定新的点击事件
			$j("#pointType").bind("click",function(){
				if(($j("#pointType").attr("checked"))){
					$j("#inputNumber").removeClass("hidden");
					$j("#labelMaxAssumeNumber").removeClass("hidden");
				}else{
					$j("#inputNumber").addClass("hidden");
					$j("#labelMaxAssumeNumber").addClass("hidden");
				};
			});
		   //$j("#div3").removeClass("hidden");
           //$j("button[action=delete]").remove();
           //$j("button[action=add]").remove();
			
		}else{
			$j("#pointType").unbind("click");
			//绑定新的点击事件
			$j("#pointType").bind("click",function(){
				if(($j("#pointType").attr("checked"))){
					$j("#inputNumber").removeClass("hidden");
					$j("#labelMaxAssumeNumber").removeClass("hidden");
				}else{
					$j("#inputNumber").addClass("hidden");
					$j("#labelMaxAssumeNumber").addClass("hidden");
				};
			});
			$j("#inputNumber").addClass("hidden");
			$j("#labelMaxAssumeNumber").addClass("hidden");
			$j("#div3").addClass("hidden");
		}
	}else{
		$j("#roleCode2").addClass("hidden");
		$j("#roleCode1").removeClass("hidden");
		$j("#roleCode1").focus();
	}
}
//返回
function back(){
	$j("#div1").removeClass("hidden");
	$j("#div2").addClass("hidden");
	$j("#form_info input").val("");
	$j("#warehouseShippingpointsForm input").val("");
	$j("#pointType").removeAttr("checked");
	$j("#inputNumber").addClass("hidden");
	$j("#labelMaxAssumeNumber").addClass("hidden");
	$j("#div3").addClass("hidden");
	pointId = "";
	var postData = loxia._ajaxFormToObj("form_query");  
	$j("#tbl-shipping-point").jqGrid('setGridParam',{
		url:$j("body").attr("contextpath")+"/shippingPointQuery.json",postData:postData}).trigger("reloadGrid");
}
//反填主导集货口code
function getPointCode(){
	$j("#pointCodeHidden").val($j("#roleCode1").val());
}

$j(document).ready(function (){
	$j("#roleCode1").attr("disabled",false);
	$j("#div2").addClass("hidden");
	$j("#div3").addClass("hidden");
	$j("#tbl-shipping-point").jqGrid({
		url:window.$j("body").attr("contextpath")+"/json/shippingPointQuery.do",
		datatype: "json",
		colNames: ["ID",i18("CODE"),i18("NAME"),"WCS编码","是否开启负载均衡","创建人","最后修改人",i18("CREATE_TIME"),"maxAssumeNumber"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"showRoleLine"}, resizable: true},
		           {name: "name", index: "name", width: 120, resizable: true},
		           {name: "wcsCode", index: "wcsCode", width: 120, resizable: true},
		           {name: "pointType", index: "pointType", width: 120, edittype:'select', formatter:'select', editoptions:{value:'0:否;1:是;2:是'},align:"center", resizable: true},
		           {name: "createName", index: "createName", width: 120, resizable: true},
		           {name: "lastName", index: "lastName", width: 120, resizable: true},
		           {name: "createTime", index: "createTime", width: 120, resizable: true},
		           {name: "maxAssumeNumber", index: "maxAssumeNumber", hidden: true}],
		caption: i18("ROLE_LIST"),//集货点列表
	   	sortname: 'id',
	   	pager:"#pager",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
		multiselect: true,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" },
		//开启子表格支持 
		subGrid: true,
		subGridRowExpanded: function(subgrid_id, row_id) {
			var subgrid_table_id, pager_id;
			subgrid_table_id = subgrid_id+"_t";
			pager_id = "p_"+subgrid_table_id;
			$j("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
			$j("#"+subgrid_table_id).jqGrid({
				url:window.$j("body").attr("contextpath")+"/json/assumeShippingPointQuery.do?refShippingPointId="+row_id,
				datatype: "json",
				colNames: ["ID",'集货口(组)编码','集货口名称','WCS编码','单次循环最大负载量','创建时间'],
				colModel: [
				    {name: "id", index: "id", hidden: true},
				    {name:"code",index:"code",width:90,editable:true,editrules:{required:true},editoptions:{size:20},resizable: true},
					{name:"name",index:"name",width:120,editable:true,editrules:{required:true},resizable: true},
					{name:"wcsCode",index:"wcsCode",width:120,editable:true,editrules:{required:true},align:"left",resizable: true},
					{name:"maxAssumeNumber",index:"maxAssumeNumber",width:120,editable:true,editrules:{required:true,number:true,minValue:1},align:"center",resizable: true},
					{name:"createTime",index:"createTime",width:120,align:"left",resizable: true},
				],
			   	rowNum:20,
			   	pager: pager_id,
			   	sortname: 'create_time',
			    sortorder: "asc",
			    rownumbers:true,
			    height: "auto",
			    pgbuttons:false,//是否显示翻页按钮
			    pginput:false,//是否显示跳转页面的输入框
			    jsonReader: { repeatitems : false, id: "0" }
			});
			$j("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,
					{edit:true,add:true,del:true,search:false,searchtext: "查找", addtext: "添加",edittext: "编辑",deltext: "删除", refreshtext:"刷新"},//options
					{// 编辑
						height:180,
						url:$j("body").attr("contextpath") + "/json/saveOrUpdatePoint.json?",
						closeAfterEdit: true,
						reloadAfterSubmit:true,
						afterSubmit :function (response, postdata) {
							var jsonResult=JSON.parse(response.responseText);
							 if(jsonResult.result =='success'){
							    	if(jsonResult.result2 == "1"){
							    		return [false, "保存失败：集货口名称已存在！"];
							    	}else if(jsonResult.result2 == "2"){
							    		return [false, "保存失败：集货口编码已存在！"];
							    	}else{
							    		return [true, ""];
							    	}
							    }else {
							    	if(JSON.parse(response.responseText).msg != null){
										return [false, JSON.parse(response.responseText).msg ];
									}else{
										return [false, "操作失败！" ];
									}
							    }
			            }
					}, 
					{//添加
						mtype:"GET",
						url:$j("body").attr("contextpath") + "/json/saveOrUpdatePoint.json?pointType=2&refShippingPointId="+row_id,
			            closeAfterAdd: true,
			            reloadAfterSubmit:true,
			            afterSubmit :function (response, postdata) {
							var jsonResult=JSON.parse(response.responseText);
							 if(jsonResult.result =='success'){
							    	if(jsonResult.result2 == "1"){
							    		return [false, "保存失败：集货口名称已存在！"];
							    	}else if(jsonResult.result2 == "2"){
							    		return [false, "保存失败：集货口编码已存在！"];
							    	}else{
							    		return [true, ""];
							    	}
							    }else {
							    	if(JSON.parse(response.responseText).msg != null){
										return [false, JSON.parse(response.responseText).msg ];
									}else{
										return [false, "操作失败！" ];
									}
							    }
			            }
			        },
			        {//删除
			        	url:$j("body").attr("contextpath")+"/deleteShippingPoint.json",
			            closeAfterDelete :true,
			            reloadAfterSubmit : true,
			            afterSubmit :function (response, postdata) {
							var jsonResult=JSON.parse(response.responseText);
							 if(jsonResult.result =='success'){
							    		return [true, ""];
							    }else {
							    	if(JSON.parse(response.responseText).msg != null){
										return [false, JSON.parse(response.responseText).msg ];
									}else{
										return [false, "操作失败！"];
									}
							    }
			            }
			        },
					{}//search 
			);
		}
	});
	
	//查询
	$j("#search").click(function(){
		 var postData = loxia._ajaxFormToObj("form_query");  
			$j("#tbl-shipping-point").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/shippingPointQuery.json",page:1,postData:postData}).trigger("reloadGrid");
		});
	
	//重置
	$j("#reset").click(function(){
		$j("#form_query input").val("");
	});
	
	//新增
	$j("#add").click(function(){
		$j("#inputNumber").addClass("hidden");
		$j("#labelMaxAssumeNumber").addClass("hidden");
		//勾选负载均衡集货口选项
	    $j("#pointType").bind("click",function(){
			if(($j("#pointType").attr("checked"))){
				$j("#inputNumber").removeClass("hidden");
				$j("#labelMaxAssumeNumber").removeClass("hidden");
				$j("#div3").removeClass("hidden");
			}else{
				$j("#inputNumber").addClass("hidden");
				$j("#labelMaxAssumeNumber").addClass("hidden");
				$j("#div3").addClass("hidden");
			};
		});
		showRoleLine(null);
	});
	
	// 返回
	$j("#back").click(function(){
		back();
	});
	
	$j("#saveRole").click(function(){
		var postData = loxia._ajaxFormToObj("form_info");
		postData["pointId"] = pointId;
		var errorMsg = loxia.validateForm("form_info");
		if(errorMsg.length == 0){
		    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/saveOrUpdatePoint.json",postData);
		    if(rs.result=='success'){
		    	if(rs.result2 == "1"){
		    		jumbo.showMsg("保存失败：集货口名称已存在！");
		    	}else if(rs.result2 == "2"){
		    		jumbo.showMsg("保存失败：集货口编码已存在！");
		    	}else{
		    		jumbo.showMsg("操作成功！");
		    		if(!$j("#pointType").attr("checked")){
		    			window.location.reload();
		    			$j("#form_info input").val("");
		    			pointId = "";
		    		}
		    	}
		    }else {
		    	if(rs.msg != null){
					jumbo.showMsg(rs.msg);
				}else{
					jumbo.showMsg("操作失败！");
				}
		    }
		}else{
			jumbo.showMsg(errorMsg);
		}
	});
	
   //删除按钮
	$j("button[action=delete]").live("click",function(){ 
	//$j("div.tbl-action-bar :last-child").live("click",function(){
		var uncheck=0;
		for(var i=1;i<=$j("input[type=checkbox]:gt(0)").length;i++){
			if(!$j("input[type=checkbox]:eq("+i+")").is(":checked")){
				uncheck++;
			}
		}
		if(uncheck == $j("input[type=checkbox]:gt(0)").length){
			jumbo.showMsg(loxia.getLocaleMsg("请选择要删除的行！"));
		}
	});
	
   //负载均衡集货口列表的form提交
	$j("button[id=refForm]").live("click",function(){
		var errorMsg = loxia.validateForm("warehouseShippingpointsForm");
		if(errorMsg.length == 0){
			$j.each($j(".ui-loxia-table table tr.add"),function(i,item){
				$j(this).find(":input").each(function(){
					$j(this).attr("name",$j(this).attr("name").replace(/\(.*\)/ig,"["+i+"]"));
				});
			});
		    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/saveOrUpdateAssumePoint.json",loxia._ajaxFormToObj("warehouseShippingpointsForm"));
		    if(rs.result=='success'){
		    	if(rs.result2 == "1"){
		    		jumbo.showMsg("保存失败：集货口名称已存在！");
		    	}else if(rs.result2 == "2"){
		    		jumbo.showMsg("保存失败：集货口编码已存在！");
		    	}else if(rs.result2 == "3"){
		    		jumbo.showMsg("保存失败：主导的负载均衡集货口不存在，请先点第一个保存按钮！");
		    	}else{
		    		jumbo.showMsg("操作成功！");
		    		window.location.reload();
	    			$j("#form_info input").val("");
	    			pointId = "";
		    	}
		    }else {
		    	if(rs.msg != null){
					jumbo.showMsg(rs.msg);
				}else{
					jumbo.showMsg("操作失败！");
				}
		    }
		}else{
			jumbo.showMsg(errorMsg);
		}
	});
	
	
	
	
	
	
	/**
	 * (批量)删除
	 */
	$j("#batchRemove").click(function(){
		var arrIds = [];
		var ids = $j("#tbl-shipping-point").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			for ( var i in ids) {
				var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/findshippingpointforcascadebyrefid.do", {"refShippingPointId" : ids[i]});
				if (null != result && result.shippingPointId.length > 0) {
					for (var int = 0; int < result.shippingPointId.length; int++) {
						arrIds.push(result.shippingPointId[int]);
					}
				}
			}
			if (null != arrIds && arrIds.length > 0) {
				var arrStr= arrIds.join(",");
			}
			var res = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/batchremove.do", {"arrIds" : arrStr});
			if (null != res && res.result == 'success') {
				var url = window.$j("body").attr("contextpath")+"/json/shippingPointQuery.do";
				$j("#tbl-shipping-point").jqGrid('setGridParam', {url:url,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
				jumbo.showMsg("删除成功!");
			}else {
				jumbo.showMsg(res.msg);
			}
		}else {
			jumbo.showMsg("请勾选需要删除的数据");
		}
	});
});

