/**
 * auth : fuzh
 * 处理提示信息的js控件
 * 需要引入，相对应的dialog。js,dialog.ui.css
 * 
 */

//有遮罩层
function fmsShow(msg){
	var d = dialog({
	    title: '提示',
	    content: msg,
	    lock : true,
		ok : function() {
		}
	});
	d.showModal();
}


function showModalFun(content, fun) {
	var d = dialog({
		title : '提示',
		content : content,
		lock : true,
		ok : function() {
		}
	});
	d.showModal();
}
//无遮罩层，此方法用与返回信息时使用
function fmsShowNoBlank(msg){
	var d = dialog({
		fixed:true,
	    title: '提示',
	    content: msg,
	    ok : function() {
		}
	});
	d.show();
}
//控制对话框关闭
function fmdShowHideTime(msg,second){
	var d = dialog({
		fixed:true,
	    title: '提示',
		content:msg
	});
	d.show();
	setTimeout(function(){d.close().remove()},second*1000)
}



