$j(document).ready(function(){	
	 var id = $j("#myform4 #id").val();
	  $j("#authoritytree").tree({
		url : window.parent.$j("body").attr("contextpath")+"/json/findroleprivilegelist.json?role.id="+id
    });	
});