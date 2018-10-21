$j(document).ready(function (){
	$j("#tabs").tabs({});
	loadDistriButionArea();
	getDistriButionAreaLoc();
	getDistriButionAreaType();
	getTransActionType();
	load();
});
$j("#butionArea").click(function(){
	document.getElementById("distri_butionArea").style.display = "";
	document.getElementById("distri_butionArea_Log").style.display = "none";
	document.getElementById("distri_butionArea_Type").style.display = "none";
});
$j("#butionArea_Log").click(function(){
	document.getElementById("distri_butionArea").style.display = "none";
	document.getElementById("distri_butionArea_Log").style.display = "";
	document.getElementById("distri_butionArea_Type").style.display = "none";
});
$j("#butionArea_Type").click(function(){
	document.getElementById("distri_butionArea").style.display = "none";
	document.getElementById("distri_butionArea_Log").style.display = "none";
	document.getElementById("distri_butionArea_Type").style.display = "";
});
//加载分配区域
function loadDistriButionArea(){
	$j("#tbl-bution-area-list").jqGrid({
		url:$j("body").attr("contextpath") + "/getDistriButionAreaList.json",
		datatype:"json",
		colNames:["id","分配区域编码","分配区域名称","编辑","删除"],	
		colModel:[{name: "id", index: "id", hidden: true,align:"center"},
			      {name:"distriButionAreaCode",index:"distriButionAreaCode",width: 150,resizable: true,align:"center"},
		          {name:"distriButionAreaName",index:"distriButionAreaName",width: 150,resizable: true,align:"center"},
		          {name:"operator", width:150, resizable: true,formatter:function(v,x,r){return "<button type='button' loxiaType='button' onclick='editDetial(this)'>编辑</button>";},align:"center"},
		          {name:"btnOperation",width:150,resizable:true,align:"center"}       
		          ],
	    caption:"分配区域新建",
		multiselect: false,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		width:"auto",
		height:"auto",
		pager:"#pager_query",	   
	    sortorder: "createTime desc",
		viewrecords: true,
		rownumbers:true,   
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnOperation" loxiaType="button" onclick="deleteSku(this);">'+"删除	"+'</button></div>';
			var ids = $j("#tbl-bution-area-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-bution-area-list").jqGrid('setRowData',ids[i],{"btnOperation":btn1});
			}
			loxia.initContext($j(this));
		}
	});  
   }  
   //新增按钮
     $j("#add").click(function(){
    	$j("#txtDistriButionAreaCode").val("");
    	$j("#txtDistriButionAreaName").val("");
    	 $j("#modifyform").dialog({
             height:230,
             width:400,
             resizable:false,
             modal:true,  //这里就是控制弹出为模态
             buttons:{
                 "保存":function(){
                	 var success = insertDetial();
              	     if(success === 1){
              		   $j(this).dialog("close");
              	     }             	     
                 },
                 "取消":function(){$j(this).dialog("close");}
             }
         });
	  });
   
   //保存按钮
   function insertDetial(){
	  var success = 0;
	  var postData = getPostData();
	  var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/insertDistriButionArea.json", postData); 
  	  if(result.msg === "3"){
 		 jumbo.showMsg("分配区域名称已存在！");
  		 return;  
  	  }else if(result.msg === "2"){
  		 jumbo.showMsg("分配区域编码已存在！");
  		 return;
  	   }else if(result.msg === "1"){
  		   success = 1
  		   jumbo.showMsg("新增成功！");
  		   $j("#tbl-bution-area-list").trigger("reloadGrid");
  		   return success;
  	    }else if(result.msg === "0"){
  	       jumbo.showMsg("新增失败");
  		   return;
  	     } 
  	  }
   
   //编辑分配区域编码
   function editDetial(obj,event){
	   var row = $j(obj).parent().parent().attr("id");
	   var data = $j("#tbl-bution-area-list").jqGrid("getRowData",row);   
	   $j("#txtDistriButionAreaCode").val(data.distriButionAreaCode);
	   $j("#txtDistriButionAreaName").val(data.distriButionAreaName);
	   $j("#modifyform").dialog({
           height:230,
           width:400,
           resizable:false,
           modal:true,  //这里就是控制弹出为模态
           buttons:{
               "确定":function(){
            	   var success = updateDetial(row);
            	   if(success === 1){
            		   $j(this).dialog("close");
            	   }
               },
               "取消":function(){$j(this).dialog("close");}
           }
       });
    }
   //更新按钮
   function updateDetial(id){
	   var success = 0;
	   var postData = {};
	   var distriButionAreaCode = $j("#txtDistriButionAreaCode").val();
	   var distriButionAreaName = $j("#txtDistriButionAreaName").val();
	   postData["id"] = id;
	   if("" === distriButionAreaCode){
		   jumbo.showMsg("分配区域编码不能为空！");
		   return;
	   }else{
		   postData["distriButionAreaCode"] = distriButionAreaCode;
	   }
	   if("" === distriButionAreaName){
		   jumbo.showMsg("分配区域名称不能为空！");
		   return;
	   }else{
		   postData["distriButionAreaName"] = distriButionAreaName;  
	   }
	   var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/updateDistriButionArea.json", postData);
	   if(result.msg === "3"){
		   jumbo.showMsg("分配区域名称已存在！！！");
		   return;
	   }else if(result.msg === "2"){
		   jumbo.showMsg("分配区域编码已存在 ！！！");
		   return;
	   }else if(result.msg === "1"){
		   success = 1;
		   jumbo.showMsg("编辑成功");
		   $j("#tbl-bution-area-list").trigger("reloadGrid");
		   return success;
	   }else if(result.msg === "0"){
		   jumbo.showMsg("编辑失败！");
		   return;
	   }
		
   }

	//删除分配区域编码
	function deleteSku(tag){
		 var postData = {};
		 var id = $j(tag).parents("tr").attr("id");
		 postData["id"] = id;
		 var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteDistriButionArea.json", postData);
		 if(result.msg === "1"){
			 $j("#tbl-bution-area-list").find("tr[id="+id+"]").remove();
			 getDistriButionAreaLoc();
			 getDistriButionAreaType();
			 getTransActionType();
			 load();
		 }else{
			 jumbo.showMsg("删除失败！");
		 } 
	}
 
 
	
  //查询按钮
  $j("#find").click(function(){
	var distriButionAreaCode = document.getElementById("distri_bution_area_code").value;
	var distriButionAreaName = document.getElementById("distri_bution_area_name").value;
	var postData={};
	postData["distriButionAreaCode"] = distriButionAreaCode;
	postData["distriButionAreaName"] = distriButionAreaName;
	debugger;
	var url=$j("body").attr("contextpath")+"/findDistriButionAreaList.json";
	$j("#tbl-bution-area-list").jqGrid('setGridParam',{
		datatype:'json',
		url:url,
		postData:postData,
		page:1
	}).trigger("reloadGrid");
/*	$j("#distri_bution_area_code").val("");
	$j("#distri_bution_area_name").val("")*/
 });
  
  //取得数据
  function getPostData(){
	   var postData = {};
 	   var distriButionAreaCode = $j("#txtDistriButionAreaCode").val();
 	   var distriButionAreaName = $j("#txtDistriButionAreaName").val();
 	   postData["distriButionAreaCode"] = distriButionAreaCode;  
       postData["distriButionAreaName"] = distriButionAreaName;     
 	  return postData;
  }
  
  
  //判断judge
  function getJudge(distriButionAreaCode,distriButionAreaName){
	  var flag = false;
	  var postData = {};
	  postData["distriButionAreaCode"] = distriButionAreaCode;
	  var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/judgeDistriButionArea.json", postData);
	  if(result != null){
		  var distriButionAreaNames = result.data;
		  if(distriButionAreaNames === distriButionAreaName){
	    	  flag = true;
		  }
	  }
      return flag;
  }
  
  /**************************************************************区域绑定库位begin**********************************************/
  function getDistriButionAreaLoc(){
		$j("#tbl-Log-group-list").jqGrid({
			url:$j("body").attr("contextpath") + "/getDistriButionAreaLoc.json",
			datatype:"json",
			colNames:["id","库区","库位","分配区域编码","分配区域名称","创建时间","创建人","操作"],	
			colModel:[{name: "id", index: "id", hidden: true,align:"center"},
				      {name:"codeName",index:"codeName",width: 150,resizable: true,align:"center"},
			          {name:"code",index:"code",width: 150,resizable: true,align:"center"},
			          {name:"distriButionAreaCode",index:"distriButionAreaCode",width: 150,resizable: true,align:"center"},
			          {name:"distriButionAreaName",index:"distriButionAreaName",width: 150,resizable: true,align:"center"},
			          {name:"createTime",index:"createTime",width: 150,resizable: true,align:"center"},
			          {name:"createUser",index:"createUser",width: 150,resizable: true,align:"center"},
			          {name:"btnOperation",width:80,resizable:true,align:"center"}       
			          ],
		    caption:"区域绑定库位",
			multiselect: false,
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			width:"auto",
			height:"auto",
			pager:"#pager_log",	   
		    sortorder: "createTime desc",
			viewrecords: true,
			rownumbers:true,
			multiselect: true,
			jsonReader: { repeatitems : false, id: "0" },
			gridComplete: function(){
				var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnOperation" loxiaType="button" onclick="singDeleteLoc(this);">'+"删除	"+'</button></div>';
				var ids = $j("#tbl-Log-group-list").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					$j("#tbl-Log-group-list").jqGrid('setRowData',ids[i],{"btnOperation":btn1});
				}
				loxia.initContext($j(this));
			}
		});  
      
  }
  //加载查询条件
  function loadDistriBution(){
	  var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/loadDistriBution.json");
	  var nameList = result.name;
	  var distriButionNameAndCodeList = result.distriButionNameAndCode;
  }
  //查询按钮
  $j("#distri_butionArea_Log_search").click(function(){
		var locCodeName = $j("#distri_butionArea_Log_selectkq").val();
		var locCode = $j("#distri_butionArea_Log_inputkw").val();
		var locDistriButionAreaCode = $j("#distri_butionArea_Log_selectfpqbm").val();
		var locDistriButionAreaName = $j("#distri_butionArea_Log_selectfpqymc").val();
		var postData={};
		postData["locCodeName"] = locCodeName;
		postData["locCode"] = locCode;
		postData["locDistriButionAreaCode"] = locDistriButionAreaCode;
		postData["locDistriButionAreaName"] = locDistriButionAreaName;
		var url=$j("body").attr("contextpath")+"/findDistriButionAreaLoc.json";
		$j("#tbl-Log-group-list").jqGrid('setGridParam',{
			datatype:'json',
			url:url,
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	 });
  
    //批量删除
  $j("#distri_butionArea_Log_delete").click(function(){
	  var rowData =  $j("#tbl-Log-group-list").jqGrid('getGridParam','selarrrow');
	  for(var i=0;i<rowData.length;i++){
		  var locId = rowData[i];
		  locDelete(locId);
	  }
   });
    //单条删除
	function singDeleteLoc(tag){
		 var id = $j(tag).parents("tr").attr("id");
		 locDelete(id);
	}
	function locDelete(locId){
		 var postData = {};
		 postData["locId"] = locId;
		 var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteDistriButionAreaLoc.json", postData);
		 if(result.msg === "1"){
			 $j("#tbl-Log-group-list").find("tr[id="+locId+"]").remove();
		 }else{
			 jumbo.showMsg("删除失败！");
		 } 
	}
	//重置按钮
	$j("#distri_butionArea_Log_reset").click(function(){
		$j("#distri_butionArea_Log_selectkq").val("");
		$j("#distri_butionArea_Log_inputkw").val("");
		$j("#distri_butionArea_Log_selectfpqymc").val("");
		$j("#distri_butionArea_Log_selectfpqbm").val("");
	});
  /**************************************************************区域绑定作业类型begin**********************************************/
  function getDistriButionAreaType(){
		$j("#tbl-type-group-list").jqGrid({
			url:$j("body").attr("contextpath") + "/getDistriButionAreaType.json",
			datatype:"json",
			colNames:["id","分配区域编码","分配区域名称","已绑定的作业类型数目"],	
			colModel:[{name: "id", index: "id", hidden: true,align:"center"},
				      {name:"distriButionAreaCode",index:"distriButionAreaCode",width: 150,resizable: true,align:"center"},
			          {name:"distriButionAreaName",index:"distriButionAreaName",width: 150,resizable: true,align:"center"},
			          {name:"num",index:"num",width: 150,resizable: true,formatter:function(v,x,r){
			        	  return "<a style='color:blue;text-decoration:underline;' onclick='checkBindingDetai("+r.id+")'>"+r.num+"</a>";
			        	  },align:"center"
			          },      
			          ],
		    caption:"区域绑定作业类型",
			multiselect: false,
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			width:"auto",
			height:"auto",
			pager:"#pager_type",	   
		    sortorder: "desc",
			viewrecords: true,
			rownumbers:true,
			multiselect: true,
			jsonReader: { repeatitems : false, id: "0" }
		});
    }
	//查询分配区域编码
	$j("#distri_butionArea_type_find").click(function(){
		var typeDistriButionAreaCode = $j("#distri_butionArea_type_code").val();
		var typeDistriButionAreaName = $j("#distri_butionArea_type_name").val();
		var postData={};
		postData["typeDistriButionAreaCode"] = typeDistriButionAreaCode;
		postData["typeDistriButionAreaName"] = typeDistriButionAreaName;
		var url=$j("body").attr("contextpath")+"/findDistriButionAreaType.json";
		$j("#tbl-type-group-list").jqGrid('setGridParam',{
			datatype:'json',
			url:url,
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	 });
		
   //作业类型加载查询
	function getTransActionType(){
		   $j("#tbl-transaction-type-list").jqGrid({
				url:$j("body").attr("contextpath") + "/getTransActionType.json",
				datatype:"json",
				colNames:["id","作业类型编码","作业类型名称","描述"],	
				colModel:[{name: "id", index: "id", hidden: true,align:"center"},
					      {name:"code",index:"code",width: 150,resizable: true,align:"center"},
				          {name:"name",index:"name",width: 150,resizable: true,align:"center"},
				          {name:"description",index:"description",width: 150,resizable: true,align:"center"},      
				          ],
			    caption:"作业类型列表",
				multiselect: false,
				rowNum: jumbo.getPageSize(),
				rowList:jumbo.getPageSizeList(),
				width:"auto",
				height:"auto",
				pager:"#pager_transaction-type",	   
			    sortorder: "desc",
				viewrecords: true,
				rownumbers:true,
				multiselect: true,
				jsonReader: { repeatitems : false, id: "0" }
			});
		}
		
   //区域绑定作业类型
	$j("#binding_transaction-type").click(function(){
		var postData = gettransActionTypePostData();
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/bindingTransAction.json", postData);
		if(result.msg === "1"){
			jumbo.showMsg("绑定成功");
			$j("#tbl-type-group-list").trigger("reloadGrid");
			$j("#tbl-transaction-type-list").trigger("reloadGrid");
		}else if(result.msg === "2"){
			jumbo.showMsg("绑定已存在,不能重复绑定！！！！！！");
		}
	});
	//取消绑定
	$j("#cancel_binding").click(function(){
		var postData = gettransActionTypePostData();
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/cancelBinding.json", postData);
		if(result.msg === "1"){
			jumbo.showMsg("取消绑定成功");
			$j("#tbl-type-group-list").trigger("reloadGrid");
			$j("#tbl-transaction-type-list").trigger("reloadGrid");
		}else{
			jumbo.showMsg("发生了错误请联系管理员");
		}
	});
	//取得数据
    function gettransActionTypePostData(){
		var distriButionAreaIds = $j("#tbl-type-group-list").jqGrid('getGridParam','selarrrow').toString();
		var transActionTypeIds =  $j("#tbl-transaction-type-list").jqGrid('getGridParam','selarrrow').toString();
		var postData = {};
		if(distriButionAreaIds != ""){
			postData["distriButionAreaIds"] = distriButionAreaIds;
		}else{
			jumbo.showMsg("分配区域列表至少需要选择一个");
			return;
		}
		if(transActionTypeIds != ""){
			postData["transActionTypeIds"] = transActionTypeIds;
		}else{
			jumbo.showMsg("作业类型至少选择一个");
			return;
		}
		return postData;
    }
    //查看分页类型
    function checkBindingDetai(id){
    	//取得绑定作业类型
    	findBingdingDetai(id);
    	$j("#binding_detai").dialog({
            height:450,
            width:490,
            resizable:false,
            modal:true,  
            buttons:{}
        });
    }
    function findBingdingDetai(tag){
    	var postData = {};
   	    var id = tag;
   	    postData["bingdingDetaiId"] = id;
   	    loadBingdingDetai(postData);
		$j("#tbl-binding_detai-list").jqGrid('setGridParam',{
				datatype:'json',
				url:$j("body").attr("contextpath") + "/findBingdingDetai.json",
				postData:postData
			}).trigger("reloadGrid");
    }
    function load(){
		var postData = {};
		postData["bingdingDetaiId"] = '';
		loadBingdingDetai(postData);
    }
    function loadBingdingDetai(postData){
    	$j("#tbl-binding_detai-list").jqGrid({
			url:$j("body").attr("contextpath") + "/findBingdingDetai.json",
			datatype:"json",
			postData:postData,
			colNames:["分配区域编码","分配区域名称","作业类型编码","作业类型名称"],	
			colModel:[{name:"distriButionAreaCode", index:"distriButionAreaCode", hidden: true,align:"center"},
				      {name:"distriButionAreaName",index:"distriButionAreaName",width: 150,resizable: true,align:"center"},
			          {name:"code",index:"code",width: 150,resizable: true,align:"center"},
			          {name:"name",index:"name",width: 150,resizable: true,align:"center"},      
			          ],
		    caption:"明细列表",
			multiselect: false,
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			width:"auto",
			height:"auto",
			pager:"#pager_binding_detai",	   
		    sortorder: "desc",
			viewrecords: true,
			rownumbers:true,
			autoencode:true,//对url进行编码
			jsonReader: { repeatitems : false, id: "0" }
		});
    }
   //库位导入
	$j("#import").click(function(){
		var file=$j.trim($j("#file").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			jumbo.showMsg("请选择文件");
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
			return false;
		}else{
			$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/saveDwhDistriButionAreaLocImport.do"));
			loxia.submitForm("importForm");
		}
		$j("#tbl-Log-group-list").jqGrid('setGridParam',{
			page:1}).trigger("reloadGrid");
	});
	//库位导出
	$j("#distri_butionArea_Log_export").click(function(){
		var locCodeName = $j("#distri_butionArea_Log_selectkq").val();
		var locCode = $j("#distri_butionArea_Log_inputkw").val();
		var locDistriButionAreaCode = $j("#distri_butionArea_Log_selectfpqbm").val();
		var locDistriButionAreaName = $j("#distri_butionArea_Log_selectfpqymc").val();
		var postData={};
		postData["locCodeName"] = locCodeName;
		postData["locCode"] = locCode;
		postData["locDistriButionAreaCode"] = locDistriButionAreaCode;
		postData["locDistriButionAreaName"] = locDistriButionAreaName;
		//var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/exportDistriBution.json", postData);
		var result = $j("#export").attr("src",$j("body").attr("contextpath") + "/warehouse/exportDistriBution.do"+"?locCodeName"+locCodeName+"&locCode"+locCode+"&locDistriButionAreaCode"+locDistriButionAreaCode+"&locDistriButionAreaName"+locDistriButionAreaName);
	});