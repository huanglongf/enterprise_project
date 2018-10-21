function findGroup() {
	$(".search-table").load("${root}control/groupMessageController/search.do?pageName=table&tableName=tb_shop_group&startRow="
		+ $("#startRow").val()
		+ "&endRow="
		+ $("#startRow").val()
		+ "&page="
		+ $("#pageIndex").val()
		+ "&pageSize="
		+ $("#pageSize").val());
}

function upBg(id){
	$("*[id*=tr_]").css("background","#ffffff");
	$("#tr_"+id).css("background","#C6E2FF");
}

//修改主表信息
function updateSgroup(id,group_code,group_name,superior,if_allot,instruction,status){
	var data = "&id="+id+"&group_code="+group_code+"&group_name="+group_name+"&superior="+superior+"&if_allot="+if_allot+"&instruction="+instruction+"&status="+status;
	openDivs('${root}control/shopGroupController/updateSgroup.do?',data);
}
function upBgbyId(id){
	$("*[id*=tr_]").css("background","#ffffff");
	$("#tr_"+id).css("background","#C6E2FF");
}	