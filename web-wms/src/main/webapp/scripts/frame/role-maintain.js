var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	ROLE_NAME_INPUT : "请输入角色的名字",
	ROLE_NAME_SIZE : "输入角色的名字大于100个字符，请重新输入",
	DESCRIBE_SIZE : "输入角色的名字大于100个字符，请重新输入",
	ORGANIZATION_TYPE_INPUT : "请输入具体的组织类型",
	OPERATE_SUCCESS : "操作成功！",
	OPERATE_FAILED : "操作失败！",
	NAME_REPEAT : "和已有角色名字重复了，请重新输入角色名字！",
	
	DATA_DETAIL_SELECT : "请选择具体数据！！",
	DATA_ERROR : "数据异常！",
	
	SURE_DELETE : "是否确定删除？",
	DELETE_SUCCESS : "删除成功",
	DELETE_FAILED : "删除失败！",
	
	OUTYPE_DISPLAY_NAME : "组织类型",
	NAME : "角色名称",
	OUTYPE_ID : "组织ID",
	DESCRIPTION : "描述",
	ISAVAILABLE : "角色使用状态",
	ROLE_LIST : "角色列表"	
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

//checkmaster
function checkDescribe(value,obj){
	if(value.length > 100){
		return loxia.getLocaleMsg("DESCRIBE_SIZE");
	}
	return loxia.SUCCESS;
}

$j(document).ready(function (){
    var boollist = loxia.syncXhr(window.parent.$j("body").attr("contextpath")+"/formateroptions.json",{categoryCode:"status"});
     var userid = $j("#userid").val();
	$j("#tbl-rolelist").jqGrid({
		url:window.parent.$j("body").attr("contextpath")+"/json/findrolelist.json",
		datatype: "json",
		//colNames: ["ID","组织类型","角色名称","组织ID","描述","角色使用状态"],
		colNames: ["ID",i18("OUTYPE_DISPLAY_NAME"),i18("NAME"),i18("OUTYPE_ID"),i18("DESCRIPTION"),i18("ISAVAILABLE")],
		colModel: [{name: "id", index: "id", hidden: true},
			        {name: "ouTypeDisplayName",index:"r.ouType.displayName",  width: 180, resizable: true},		         
		            {name: "name", width: 180, resizable: true},
		             {name: "ouTypeid",  width: 180,hidden: true},
		            {name: "description",  width: 250, resizable: true,sortable:false},
		            {name: "isAvailable", index:"r.IS_AVAILABLE" , width: 150,sortable:false,resizable: true,formatter:'select',editoptions:boollist}			
			   ],
		caption: i18("ROLE_LIST"),//角色列表
		postData:{"columns":"id,ouTypeDisplayName,name,ouTypeid,description,isAvailable"},
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
   	pager: '#pager9',
   	height:jumbo.getHeight(),
   	sortname: 'r.isAvailable desc,r.ouType.id,r.name',
   	viewrecords: true,
   	rownumbers:true,
	sortorder: "desc", 
	multiselect: true,
	jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-rolelist",{"isAvailable":"status"});
	  
});

  function queryRole(){
	  var name = $j("#myform1 #name").val();
	  var addOuType = $j("#myform1 #addOuType").val();
	 var urlx=window.parent.$j("body").attr("contextpath")+"/json/findrolelist.json";
       var d = {'role.name':name,'role.ouType.id':addOuType};
     jQuery("#tbl-rolelist").jqGrid('setGridParam',
     {url:urlx,postData:d}).trigger("reloadGrid",[{page:1}]); 	  
     jumbo.bindTableExportBtn("tbl-rolelist",{"isAvailable":"status"},urlx,d);
   }
  function toaddRole(){
	 $j("#f1").addClass("hidden");
	 $j("#f2").removeClass("hidden");
	//处理保存按钮 
	  $j("#f3-1").removeClass("hidden");
	  $j("#f3-2").addClass("hidden");
	//清空修改和增加的共用的Div
	  var f = document.forms["myform2"];
	  f.reset();
  }
  function addRole(){	      
	   if(ztrim($j("#myform2 #name").val())==''){
        		 jumbo.showMsg(loxia.getLocaleMsg("ROLE_NAME_INPUT"));//请输入角色的名字
        		 return ;
         }

	    if($j("#myform2 #name").val().length>100){
	    	 jumbo.showMsg(loxia.getLocaleMsg("ROLE_NAME_SIZE"));//输入角色的名字大于100个字符，请重新输入
        	 return ;
	    }
	   if($j("#myform2 #addOuType").val()==''){
        		 jumbo.showMsg(loxia.getLocaleMsg("ORGANIZATION_TYPE_INPUT"));//请输入具体的组织类型
        		 return ;
        	 }
	     var name = $j("#myform2 #name").val();
	     var addOuType = $j("#myform2 #addOuType").val();
	     var description = $j("#myform2 #description").val();
	  $j("#f2").addClass("hidden");
	  $j("#f3").removeClass("hidden");
	  $j("#myform3 #name").val(replaceJs(name));
	  $j("#myform3 #description").val(replaceJs(description));
	  $j("#ouname").empty();
	  $j("#ouname").append($j("#myform2 #addOuType option:selected").text());
	  $j("#myform3 #ouid").val(addOuType);
	  $j("#myform3 #id").val("");
	 //加载树
	  getAddRree(addOuType);
  }
  function modifyRole(str){
	 var n=$j("#"+str).tree('getChecked');
      var data = {};
     	for(var i=0,d;(d=n[i]);i++){
     	   var nx = d.id;
     	   var m =nx.split(":");
     	   var x =m[1];
     	   if(x!='null'){
    		data["privlist[" + i + "].acl"] = x;
    		}
    	}  
        	 data["role.id"]=$j("#myform3 #id").val();
        	 data["role.name"]= replaceJs(ztrim($j("#myform3 #name").val()));
        	 if($j("#myform3 #name").val().length>100){
	    	 jumbo.showMsg(loxia.getLocaleMsg("ROLE_NAME_SIZE"));//输入角色的名字大于100个字符，请重新输入
        	 return ;
	        }
        	  data["role.ouType.id"]= $j("#myform3 #ouid").val();
        	 data["role.description"]=replaceJs($j("#myform3 #description").val());
        	 if($j("#myform3 #name").val()==''){
        		 jumbo.showMsg(loxia.getLocaleMsg("ROLE_NAME_INPUT"));//请输入角色的名字
        		 return ;
        	 }
           loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/modifyrole.json",
              data,
				{
			   success:function (data) {
        	    if(data.rs=='true'){
        	    	 backMRole();
        	    	 queryRole();
	        	jumbo.showMsg($j("#myform3 #name").val()+loxia.getLocaleMsg("OPERATE_SUCCESS"));//操作成功！	        	
	        	 }else {
	        	jumbo.showMsg($j("#myform3 #name").val()+loxia.getLocaleMsg("NAME_REPEAT"));	 //和已有角色名字重复了，请重新输入角色名字！
	        	 }
			   },
               error:function(){
				 jumbo.showMsg(loxia.getLocaleMsg("OPERATE_FAILED"));	//操作失败！
               }
			});
  }
  function backMRole(){
	  $j("#f3").addClass("hidden");
	  $j("#f1").removeClass("hidden"); 	  
  }
  /**
   * 进入修改div
   */
  function tomodifyRole(){
	   //处理保存按钮 
	  $j("#f3-2").removeClass("hidden");
	  $j("#f3-1").addClass("hidden");
	   var ids = jQuery("#tbl-rolelist").jqGrid('getGridParam','selarrrow');
	     var id ;
	     if(ids.length==0){
    	 jumbo.showMsg(loxia.getLocaleMsg("DATA_DETAIL_SELECT"));	//请选择具体数据！！
    	 return
     }
	    id=ids[0]; 
	   var datalist = jQuery("#tbl-rolelist").getRowData(id);
	   var postData = {};
	   postData["role.id"]=id;
	       loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/findrolebyid.json",
              postData,
				{
			   success:function (data) { 
	    	    $j("#myform3 #name").val(datalist.name);
	            $j("#myform3 #description").val(datalist.description);	
	            $j("#ouname").empty();
	            $j("#ouname").append(datalist["ouTypeDisplayName"]);
	            $j("#myform3 #ouid").val(datalist.ouid);
	            $j("#myform3 #id").val(datalist.id);
	        	$j("#f1").addClass("hidden");
	            $j("#f3").removeClass("hidden");
	            getModifytree(datalist["ouTypeid"],data,id);
			   },
               error:function(){
				 jumbo.showMsg(loxia.getLocaleMsg("DATA_ERROR"));	//数据异常！
               }
			});
	  
  }
   var treeI=0;
   function getAddRree(ouTypeId){
	   $j("#authoritytree1").empty();
	   $j("#authoritytree").tree({
		url : window.parent.$j("body").attr("contextpath")+"/json/findprivilegelist.json?role.ouType.id="+ouTypeId,
		checkbox:true
    	});	
  }
  function getModifytree(ouTypeId,data,roleId){
	   $j("#authoritytree").empty();
	   $j("#authoritytree1").tree({
		url : window.parent.$j("body").attr("contextpath")+"/json/findprivilegelist.json?role.ouType.id="+ouTypeId+"&role.id="+roleId,
		checkbox:true,
		onLoadSuccess:function(){
		  $j.each($j("#authoritytree1 .tree-node"),function(i,e){
			  if($j("#authoritytree1").tree('getNode',e).attributes.checked)
				  $j("#authoritytree1").tree('check',e);
		  });
//		  if(treeI==1){
//		  setNoneCheck(data);
//		   }
//		   treeI++;
		}
    	});	
  }
    function  setNoneCheck(data){
	  for(var i=0;i<data.length;i++){
	   var n=$j("#authoritytree1").tree('find',data[i]);
	   $j("#authoritytree1").tree('check',n.target);
	  }	
	  treeI=0;
  }
    function deleteRole(){
    	 var ids = jQuery("#tbl-rolelist").jqGrid('getGridParam','selarrrow');
    	   if(ids.length==0){
	    	 jumbo.showMsg(loxia.getLocaleMsg("DATA_DETAIL_SELECT"));//请选择具体数据
	    	 return;
	     }
       if(window.confirm(loxia.getLocaleMsg("SURE_DELETE"))){	 //是否确定删除？
    	    var data = {};
    	    $j.each(ids,function(i, item) {
    		 data["roleList[" + i + "].id"] = item;
    	  });
     
          loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/delelerolelist.json",
				data,
				{
			   success:function (data) {
        	     if(data.rs=='true'){
        	    	  queryRole();	
        	     jumbo.showMsg(loxia.getLocaleMsg("DELETE_SUCCESS"));	 //删除成功！			   
			     }
			   },
               error:function(){
				 jumbo.showMsg(loxia.getLocaleMsg("DELETE_FAILED"));	//删除失败！
               }
			});    
		}  	
    }
    
    function viewRole(){
    	 var ids = jQuery("#tbl-rolelist").jqGrid('getGridParam','selarrrow');
    	  if(ids.length==0){
	    	 jumbo.showMsg(loxia.getLocaleMsg("DATA_DETAIL_SELECT")); //请选择具体数据
	    	 return;
	     }
    	 $j.each(ids,function(i, item) {
    		 viewRoleInfo(item);
    	  });
    }
    function viewRoleInfo(id){
    var d = $j("#tbl-rolelist").jqGrid('getRowData',id)
     var url =window.parent.$j("body").attr("contextpath")+"/auth/rolemaintaindetailentry.do?role.id="+id;    
     	jumbo.openFrame("frm-" + (new Date()).getTime().toString(),d.name,url);
    }
    
    function backMRole2(){
    	$j("#f4").addClass("hidden");
	    $j("#f1").removeClass("hidden"); 
    }
    
    
    
