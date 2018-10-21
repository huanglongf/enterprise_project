// 面板加载页面
function loadingPanel(panel, url){
	loadingStyle();
	panel.load(url,{},function(){
		cancelLoadingStyle();
	});
};

// 中央面板加载页面
function loadingCenterPanel(url){
	loadingPanel($("#centerPanel"), url);
	
};
function loadingCenterPanel (url,id){
	$('#'+id).modal('hide');
	loadingPanel($("#centerPanel"), url);
}

function openDiv(url){
	loadingStyle();
	$("#centerPanel").load(url,{},function(){
		cancelLoadingStyle();
	});
};

function openIdDiv(id, url){
	loadingStyle();
	$("#" + id).load(url,{},function(){
		cancelLoadingStyle();
	});
};
