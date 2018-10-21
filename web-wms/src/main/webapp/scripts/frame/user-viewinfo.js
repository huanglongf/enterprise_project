$j.extend(loxia.regional['zh-CN'],{
	//colNames: ["ID",],
	//colNames: ["ID","","","","",""],
	ROLE_NAME : "角色",
	ROLE_ID : "角色ID",
	OU_NAME : "组织",
	OU_ID : "组织ID",
	ISDEFAULT : "是否默认",
	TRUE : "是",
	FALSE : "否",
	ROLE_LIST : "角色列表"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

var $j = jQuery.noConflict();
$j(document).ready(function (){
     var userid = $j("#userid").val();
	$j("#tbl-userlist").jqGrid({
		url:window.parent.$j("body").attr("contextpath")+"/json/findrolelistbyid.do?user.id="+userid,
		datatype: "json",
		//colNames: ["ID","角色","角色ID","组织","组织ID","是否默认"],
		colNames: ["ID",i18("ROLE_NAME"),i18("ROLE_ID"),i18("OU_NAME"),i18("OU_ID"),i18("ISDEFAULT")],
		colModel: [{name: "id", index: "id", hidden: true},
		            {name: "roleName",  width: 180, resizable: true,sortable:false},
		            {name: "roleid", width: 100, hidden: true,sortable:false},
		            {name: "ouName",  width: 180, resizable: true,sortable:false},
		            {name: "ouid",  width: 150, hidden: true,sortable:false},
				    {name: "isDefault", width: 150,sortable:false, resizable: true,formatter:'select',editoptions:{value:"true:"+i18("TRUE")+";false:"+i18("FALSE")}}
				   ],
		caption: i18("ROLE_LIST"),//角色列表
   	postData:{"columns":"id,roleName,roleid,ouid,ouName,isDefault"},
     gridComplete: function() {
         var d =  $j("#tbl-userlist").getRowData();
		  for(var i=0;i<d.length;i++){
	          var dd = d[i];
			  var isD = dd["isDefault"];
			  var idx = dd["id"];
              if(isD=='true'){
              $j("#tbl-userlist #"+idx).addClass("ui-state-highlight");
			  }
		  }
        },	
        rowNum:-1,
        height:"auto",
	jsonReader: { repeatitems : false, id: "0" }
	});	 
});