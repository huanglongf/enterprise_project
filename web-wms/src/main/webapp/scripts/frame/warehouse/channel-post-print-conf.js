$j.extend(loxia.regional['zh-CN'], {
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

$j(document).ready(function (){
	$j("#tbl-channel-ref-list").jqGrid({
		url:$j("body").attr("contextpath")+"/findAllChannelRefByOuId.json",
		datatype: "json",
		colNames: ["ID","渠道编码","渠道名称","创建时间","后置打印面单","后置打印装箱清单"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name:"code",index:"code", width:95,resizable:true},
		           {name:"name",index:"name", width:95,resizable:true},
		           {name:"createTime",index:"createTime", width:150,resizable:true},
		           {name:"isPostExpressBill", width: 90, resizable: true,formatter:function(v,x,r){return "<input type='checkbox' name = 'isPostExpressBill'/>";}},
		           {name:"isPostPackingPage", width: 100, resizable: true,formatter:function(v,x,r){return "<input type='checkbox' name = 'isPostPackingPage' onclick='validatePostPackingPage(this)'/>";}}
		           ],
		caption: "仓库关联渠道列表",
		sortname: 'channel.create_time',
	   	sortorder: "desc",
	   	shrinkToFit:false,
		height:"auto",
	   	//multiselect: false,
	    rowNum: -1,
		//rowList:jumbo.getPageSizeList(),
	   	//pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var channelList = loxia.syncXhrPost($j("body").attr("contextpath")+"/findAllPostPrintConf.json");
			if(channelList){
				for(var i = 0; i < channelList.length; i++){
					var id = channelList[i].channelId;
					var isPostPackingPage = channelList[i].isPostPage;
					var isPostExpressBill = channelList[i].isPostBill;
					if(1 == isPostPackingPage){
						$j("#"+ id +" input[name='isPostPackingPage']").attr("checked","checked");
					}
					if(1 == isPostExpressBill){
						$j("#"+ id +" input[name='isPostExpressBill']").attr("checked","checked");
					}
				}
			}
		}
	});
	$j("#save").click(function(){
//		var selected = $j("#tbl-channel-ref-list").find("input[type='checkbox']:checked");
//		if(0 == selected.length){
//			jumbo.showMsg("请选择后置打印配置!");
//			return false;
//		}
		var ids = $j("#tbl-channel-ref-list").jqGrid('getDataIDs');
		var postData = {};
		var index = 0;
		for(var i=0;i < ids.length;i++){
			var datas = $j("#tbl-channel-ref-list").jqGrid('getRowData',ids[i]);
			var isPostPackingPage = $j("#"+datas["id"]+" input[name='isPostPackingPage']").attr('checked');
			var isPostExpressBill = $j("#"+datas["id"]+" input[name='isPostExpressBill']").attr('checked');
			if(true == isPostPackingPage || true == isPostExpressBill){
				postData["channelWhRefList[" + index + "].shop.id"]=datas["id"];
				postData["channelWhRefList[" + index + "].isPostPackingPage"]=false;
				postData["channelWhRefList[" + index + "].isPostExpressBill"]=false;
				if(true == isPostPackingPage){
					postData["channelWhRefList[" + index + "].isPostPackingPage"]=true;
				}
				if(true == isPostExpressBill){
					postData["channelWhRefList[" + index + "].isPostExpressBill"]=true;
				}
				index++;
			}
		}
		if(confirm("确定保存已修改的内容？")){
			var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/updatePostPrintConf.json",postData);
			if(rs && rs["msg"] == "success"){
				jumbo.showMsg("渠道后置打印配置成功！");
			}else{
				errors=[];
				errors.push("渠道后置打印配置失败！");
				errors.push(rs["msg"]);
				jumbo.showMsg(errors.join("<br/>"));		
			}
		}
	});
});
function validatePostPackingPage(obj){
	var channelId = $j(obj).parent().parent().attr("id");
	var specialAction = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getBiChannelSpecialActionByCidTypePackingPage.json?channelId="+channelId);
	if(specialAction && specialAction.msg != "none"){
		var templateType = specialAction.templateType;
		if(null != templateType && "" != templateType){
			$j("#"+ channelId +" input[name='isPostPackingPage']").attr("checked", "");
			jumbo.showMsg("该渠道不允许维护后置打印装箱清单！");
			return false;
		}
	}
}