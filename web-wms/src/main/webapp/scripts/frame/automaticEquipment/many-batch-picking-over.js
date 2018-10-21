var slipCode="";
var barCode="";
$j(document).ready(function(){
	$j("#slipCode").focus();
	$j("#slipCode").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
			setFocusOn("barCode");
		}
		if(event.keyCode === 13){
			slipCode=$j("#slipCode").val();
			if(slipCode==""){
				loxia.tip(this,"请扫描拣货单上面的条码!");
			}else{
				//initPlzList();
				setFocusOn("barCode");
			}
		}
	});
	$j("#barCode").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
			setFocusOn("slipCode");
		}
		if(event.keyCode === 13){
			barCode=$j("#barCode").val();
			if(barCode==""){
				loxia.tip(this,"请扫描周转箱编码!");
			}else{
				slipCode=$j("#slipCode").val();
				if(slipCode==""){
					loxia.tip($j("#slipCode"),"请扫描拣货单上面的条码!");
					setFocusOn("slipCode");
				}else{
					if(confirm("确认要完成？")){ 
						
						//校验是否可以绑定并提示结果
						var rs = loxia.syncXhr($j("body").attr("contextpath") + "/auto/pickingListAndZoneOver.do",{"slipCode":slipCode,"code":barCode});
						if(rs!=null&&rs.result=="success"){
							if(rs.over!=null&&rs.over!=""){
								jumbo.showMsg(rs.over);
							}else{
								jumbo.showMsg("请求已成功发送！");
							}
						}else{
							jumbo.showMsg(rs.msg);
							setFocusOn("slipCode");
						}
					}
				}
			}
		}
	});
	
	 
	
	/*$j("#tb1-plz-list").jqGrid({
		//url:$j("body").attr("contextpath")+"/json/findZoonByParams.json",
		datatype: "json",
		colNames: ["ID","配货批编码","仓储区域编码","状态"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "pickingListCode", index: "pickingListCode", hidden: false,sortable:false},
		           {name: "whZoneCode", index: "whZoneCode", hidden: false,sortable:false},	
		           {name: "status", index: "status", hidden: false,formatter:'select',editoptions:{value:"true:已完成;false:未完成"},sortable:false}
		           ],
		caption: '区域表',
	   	//sortname: 'sta.create_time',
	   	//sortorder: "desc",
	   	multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	  
		});*/
	
});
function setFocusOn(flag){
	if(flag=="barCode"){
		barCode=""
	}else{
		slipCode="";
	}
	$j("#"+flag).val("");
	$j("#"+flag).focus();
}

function initPlzList(){
	$j("#tb1-plz-list").jqGrid('setGridParam',{  
		url:$j("body").attr("contextpath")+"/auto/findZoonByParams.do",
        datatype:'json',  
        postData:{
        	"slipCode":slipCode
        } //发送数据  
    }).trigger("reloadGrid"); //重新载入
}